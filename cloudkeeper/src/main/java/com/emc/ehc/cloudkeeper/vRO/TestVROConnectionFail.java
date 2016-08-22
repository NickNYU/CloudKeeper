package com.emc.ehc.cloudkeeper.vRO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.curator.framework.CuratorFramework;

import com.emc.ehc.cloudkeeper.client.factory.ClientFactory;
import com.emc.ehc.cloudkeeper.client.factory.ZooKeeperClientUtil;
import com.emc.ehc.cloudkeeper.connection.SSHConnection;

/**
* @author Nick Zhu E-mail: nick.zhu@emc.com
* @version build time：Jul 31, 2016 10:19:13 PM
* 
*/
public class TestVROConnectionFail {
    
    public static void main(String[] args) throws Exception {
        
        String host = "10.103.201.79";
        String username = "root";
        String password = "Emsee123!";
        SSHConnection ssh = new SSHConnection(host, username, password);
        VROConnection vROConnection = new VROConnection("", host, "app_vco_vcenter@ppindigo.lab.local", password);
        vROConnection.setSshConnection(ssh);
        
        CuratorFramework client = ClientFactory.getSimpleClient("127.0.0.1:2181");
        client.start();
        ZooKeeperClientUtil.createNode(client, "/EHC/dev19/vRO/10.103.201.79");
        
        registerWatcher(vROConnection, client);
        
        new Thread(new VROHealthCheck(client, vROConnection)).start();
        
    }
    
    public static void registerWatcher(VROConnection vROConnection, CuratorFramework client) throws Exception {
        
        String path = "/EHC/vRO/" + vROConnection.getHost() + "/health";
        ZooKeeperClientUtil.createNode(client, path, "true".getBytes());
        
        vROConnection.getvROWatcher().vROServiceShutDown(client, path);
    }
}
