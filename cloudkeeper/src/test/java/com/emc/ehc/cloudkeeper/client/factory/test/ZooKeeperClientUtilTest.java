package com.emc.ehc.cloudkeeper.client.factory.test;

import org.apache.curator.framework.CuratorFramework;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.emc.ehc.cloudkeeper.client.factory.ClientFactory;
import com.emc.ehc.cloudkeeper.client.factory.ZooKeeperClientUtil;

/**
* @author Nick Zhu E-mail: nick.zhu@emc.com
* @version build time：Jul 30, 2016 10:14:44 AM
* 
*/
public class ZooKeeperClientUtilTest {

    private CuratorFramework client;
    private String PATH = "/EHC";
    
    @Before public void before() {
        this.client = ClientFactory.getSimpleClient("127.0.0.1:2181");
        client.start();
    }
    
    @Test public void testCreateNode() throws Exception {
        ZooKeeperClientUtil.createNode(client, PATH);
    }
    
    @Test public void testIsPathExist() throws Exception {
        ZooKeeperClientUtil.isPathExist(client, PATH);
    }
    
    @Test public void testCreateNodeWithPayload() throws Exception {
        ZooKeeperClientUtil.createNode(client, "/EHC/Hello", "Hello World".getBytes());
    }
    
    @Test public void testDeleteNode() throws Exception {
        ZooKeeperClientUtil.deleteNode(client, "/NotExsits");
    }
    
    @After public void after() {
        if(this.client != null) {
            client.close();
        }
    }
}
