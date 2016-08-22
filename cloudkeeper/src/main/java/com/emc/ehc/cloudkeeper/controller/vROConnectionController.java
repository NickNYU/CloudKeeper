package com.emc.ehc.cloudkeeper.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.emc.ehc.cloudkeeper.client.factory.ClientFactory;
import com.emc.ehc.cloudkeeper.client.factory.ZooKeeperClientUtil;
import com.emc.ehc.cloudkeeper.connection.SSHConnection;
import com.emc.ehc.cloudkeeper.controller.utils.Object2ByteUtils;
import com.emc.ehc.cloudkeeper.model.User;
import com.emc.ehc.cloudkeeper.model.vROConnectionModel;
import com.emc.ehc.cloudkeeper.vRO.VROConnection;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Aug 18, 2016 4:19:51 PM
 * 
 */
@RestController
@RequestMapping(value = "/vRO")
public class vROConnectionController {
    static Map<String, vROConnectionModel> vros = Collections.synchronizedMap(new HashMap<String, vROConnectionModel>());

    private static CuratorFramework client = ClientFactory.getSimpleClient("127.0.0.1:2181");

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<vROConnectionModel> getUserList() {
        List<vROConnectionModel> r = new ArrayList<vROConnectionModel>(vros.values());
        return r;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)// headers = {"content-type=application/json"},
    public String postUser(@RequestBody vROConnectionModel vro) throws Exception {
        store(vro);
        return "success";
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public vROConnectionModel getVRO(@PathVariable String name) throws Exception {
        return get(name);
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
}
