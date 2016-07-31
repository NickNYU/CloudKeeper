package com.emc.ehc.cloudkeeper.event;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;



/**
* @author Nick Zhu E-mail: nick.zhu@emc.com
* @version build time：Jul 30, 2016 11:28:22 PM
* 
*/
public class VROEventsOperation {
    
    private static CuratorFramework client;
    private static final String PATH = "/EHC";
    
    public static void main(String[] args) throws Exception {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient("127.0.0.1:2181", retryPolicy);
        client.start();
        new VROEvents().vROServiceShutDown(client, PATH);
        Thread.sleep(10000);
        
        test();
        try {
        while(true) {
            
        }
        } finally {
            CloseableUtils.closeQuietly(client);
        }
        //client.close();
    }
    
    public static void test() {
        //this.client = ClientFactory.getSimpleClient("127.0.0.1:2181");
        
        try {
            client.setData().forPath(PATH, "This is a forth test for the input".getBytes());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
    }
}
