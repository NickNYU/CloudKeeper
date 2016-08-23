package com.emc.ehc.cloudkeeper.vRO;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Callable;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emc.ehc.cloudkeeper.client.factory.ZooKeeperClientUtil;
import com.emc.ehc.cloudkeeper.connection.RestClientFactory;
import com.emc.ehc.cloudkeeper.model.vROConnectionModel;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Jul 31, 2016 10:41:12 PM
 * 
 */
public class VROHealthCheck implements Runnable {
    CuratorFramework client;
    vROConnectionModel vro;

    private final static Logger logger = LoggerFactory.getLogger(VROHealthCheck.class);

    public VROHealthCheck(CuratorFramework client, vROConnectionModel vro) throws Exception {
        this.client = client;
        this.vro = vro;
    }

    public void run() {

        while (true) {

            String info = null;
            String username = vro.getUsername();
            String password = vro.getPassword();
            Client restClient = RestClientFactory.createClient(username, password);

            try {
                // VRO health status API

                // https://your_orchestrator_server_IP_or_DNS_name:8281/vco/api/about
                String hostPort = this.vro.getHost() + ":8281";
                Response response = restClient.target("https://" + hostPort + "/vco/api/about")
                        .request(MediaType.APPLICATION_JSON).get();

                System.out.println("vRO health check : " + response.getStatus());
                String path = "/EHC/vRO/" + vro.getName() + "/status";
                if (response.getStatus() == 200) {
                    client.setData().forPath(path, "true".getBytes());

                } else {
                    client.setData().forPath(path, "false".getBytes());
                }

                Thread.sleep(5000);

            } catch (Exception e) {

                String path = "/EHC/vRO/" + vro.getName() + "/status";
                try {
                    client.setData().forPath(path, "false".getBytes());
                    // run();
                    Thread.sleep(2000);
                    continue;
                } catch (Exception e1) {
                    logger.error("Exception occurred when set data for zookeeper vRO/Config : ", e);
                }

            }

        }

    }
}
