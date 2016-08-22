package com.emc.ehc.cloudkeeper.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.emc.ehc.cloudkeeper.client.factory.ClientFactory;
import com.emc.ehc.cloudkeeper.client.factory.ZooKeeperClientUtil;
import com.emc.ehc.cloudkeeper.connection.SSHConnection;
import com.emc.ehc.cloudkeeper.controller.utils.Object2ByteUtils;
import com.emc.ehc.cloudkeeper.event.VROEventWatcher;
import com.emc.ehc.cloudkeeper.model.User;
import com.emc.ehc.cloudkeeper.model.VRORegisterResponse;
import com.emc.ehc.cloudkeeper.model.ZooKeeperServerModel;
import com.emc.ehc.cloudkeeper.model.vROConnectionModel;
import com.emc.ehc.cloudkeeper.vRO.VROConnection;
import com.emc.ehc.cloudkeeper.vRO.VROHealthCheck;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Aug 18, 2016 4:19:51 PM
 * 
 */
@RestController
@RequestMapping(value = "/vRO")
public class vROConnectionController {

    @Autowired
    private static ZooKeeperServerModel zkServer;

    private static CuratorFramework client = ClientFactory.getSimpleClient(zkServer.getHost() + ":" + zkServer.getPort());

    /*
     * @RequestMapping(value = "", method = RequestMethod.GET)
     * public List<vROConnectionModel> getUserList() {
     * List<vROConnectionModel> r = new ArrayList<vROConnectionModel>(vros.values());
     * return r;
     * }
     */

    @RequestMapping(value = "", method = RequestMethod.POST)// headers = {"content-type=application/json"},
    public VRORegisterResponse postUser(@RequestBody vROConnectionModel vro) {
        try {
            String path = "/EHC/vRO/" + vro.getName();
            if(!ZooKeeperClientUtil.isPathExist(client, path)) {
                store(vro);
                registerWatcher(vro);
            }
            return new VRORegisterResponse(vro.getName(), vro.getHost(), true);
        } catch (Exception e) {
            return new VRORegisterResponse(vro.getName(), vro.getHost(), false);
        }
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public vROConnectionModel getVRO(@PathVariable String name) throws Exception {
        return get(name);
    }

    @RequestMapping(value = "/{name}/status", method = RequestMethod.GET)
    public boolean getVROStatus(@PathVariable String name) throws Exception {
        if (!client.isStarted()) {
            client.start();
        }
        String path = "/EHC/vRO/" + name + "/status";
        boolean pathExist = ZooKeeperClientUtil.isPathExist(client, path);
        if (!pathExist) {
            return false;
        }
        byte[] payload = client.getData().forPath(path);
        String info = new String(payload);
        return info.equalsIgnoreCase("true");
    }

    private void store(vROConnectionModel vro) throws Exception {
        VROConnection vroConnect = new VROConnection(vro.getName(), vro.getHost(), vro.getUsername(), vro.getPassword());
        SSHConnection sshConnect = new SSHConnection(vro.getSshConnection().getHost(), vro.getSshConnection().getUsername(),
                vro.getSshConnection().getPassword());
        vroConnect.setSshConnection(sshConnect);
        if (!client.isStarted()) {
            client.start();
        }
        String path = "/EHC/vRO/" + vro.getName();
        ZooKeeperClientUtil.createNode(client, path);
        byte[] payload = Object2ByteUtils.serialize(vro);
        client.setData().forPath(path, payload);
    }

    private vROConnectionModel get(String name) throws Exception {
        if (!client.isStarted()) {
            client.start();
        }
        String path = "/EHC/vRO/" + name;
        boolean pathExist = ZooKeeperClientUtil.isPathExist(client, path);
        if (!pathExist) {
            return null;
        }
        byte[] payload = client.getData().forPath(path);
        vROConnectionModel vro = (vROConnectionModel) Object2ByteUtils.deserialize(payload);
        return vro;
    }

    private void registerWatcher(vROConnectionModel vro) throws Exception {

        String path = "/EHC/vRO/" + vro.getName() + "/status";
        ZooKeeperClientUtil.createNode(client, path, "true".getBytes());

        VROEventWatcher.vROServiceShutDown(client, vro);
        new Thread(new VROHealthCheck(client, vro)).start();
    }
}
