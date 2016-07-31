package com.emc.ehc.cloudkeeper.vRO;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Callable;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.curator.framework.CuratorFramework;

import com.emc.ehc.cloudkeeper.client.factory.ZooKeeperClientUtil;


/**
* @author Nick Zhu E-mail: nick.zhu@emc.com
* @version build time：Jul 31, 2016 10:41:12 PM
* 
*/
public class VROHealthCheck implements Callable<Boolean> {
    CuratorFramework client;
    VROConnection vROConnection;

    public VROHealthCheck(CuratorFramework client, VROConnection vROConnection)
            throws Exception {
        this.client = client;
        this.vROConnection = vROConnection;
    }

    public Boolean call() {

        while (true) {

            String info = null;
            Client rsClient = ClientBuilder.newClient();

            try {
                // VRO health status API


                // https://your_orchestrator_server_IP_or_DNS_name:8281/vco/api/about
                String hostPort = this.vROConnection.getHost() + ":8281";
                Response response = rsClient
                        .target("https://" + hostPort + "/vco/api/about")
                        .request(MediaType.APPLICATION_XML).get();

                if (response.getStatus() != 200) {
                    String path = "/EHC/dev19/vRO/" + vROConnection.getHost();
                    client.setData().forPath(path, "false".getBytes());
                } else {
                    String path = "/EHC/dev19/vRO/Config";
                    String payload = "{host : " + vROConnection.getHost() + "}";
                    if(!ZooKeeperClientUtil.isPathExist(client, path)) {
                        ZooKeeperClientUtil.createNode(client, path, payload.getBytes());
                    }
                }

                    
                
                Thread.sleep(5000);

            } catch (Exception e) {
               

            }

        }

    }
}
