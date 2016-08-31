package com.emc.ehc.cloudkeeper.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.emc.ehc.cloudkeeper.event.VROEventWatcher;
import com.emc.ehc.cloudkeeper.factory.ZookeeperClientFactory;
import com.emc.ehc.cloudkeeper.model.Vro;
import com.emc.ehc.cloudkeeper.model.ZookeeperServer;
import com.emc.ehc.cloudkeeper.utils.Object2ByteUtils;
import com.emc.ehc.cloudkeeper.utils.ZooKeeperClientUtils;


/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Aug 25, 2016 3:48:22 PM
 * 
 */

@Service
public class ZookeeperService {

    // private static Logger logger = LoggerFactory.getLogger(ZookeeperService.class);

    @Autowired
    private ZookeeperServer zkServer;
    private CuratorFramework zkClient;
    
    @PostConstruct
    public void init() {
        zkClient = ZookeeperClientFactory.getSimpleClient(getConnection());
        zkClient.start();
    }

    private String getConnection() {
        return zkServer.getHost() + ":" + zkServer.getPort();
    }
    
    public void registerVroWatcher(Vro vro) throws Exception {
        String path = "/EHC/vRO/" + vro.getName() + "/status";
        ZooKeeperClientUtils.createNode(zkClient, path, "true".getBytes());

        VROEventWatcher.vROServiceShutDown(zkClient, vro);
    }
    
    @Async
    public <T extends Serializable> void setData(String path, T t) throws Exception {
        //if(client == null)  init();
        byte[] payload = Object2ByteUtils.serialize(t);
        
        if(ZooKeeperClientUtils.isPathExist(zkClient, path)) {
            return;
        }
        ZooKeeperClientUtils.createNode(zkClient, path);
        zkClient.setData().forPath(path, payload);
    }
    
    
    public <T extends Serializable> T getData(String path) throws Exception {
        //if(client == null)  init();
        byte[] payload = zkClient.getData().forPath(path);
        
        @SuppressWarnings("unchecked")
        T object = (T) Object2ByteUtils.deserialize(payload);

        return object;
    }
    
    public List<String> getChildrenPath(String path) throws Exception {
        return zkClient.getChildren().forPath(path);
    }
    
    @SuppressWarnings("deprecation")
    public String getState() {
        //if(client == null)  init();
        return zkClient.isStarted() ? "success" : "failure";
    }
}
