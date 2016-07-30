package com.emc.ehc.cloudkeeper.client.factory;

import org.apache.curator.framework.CuratorFramework;
import org.junit.Test;

/**
* @author Nick Zhu E-mail: nick.zhu@emc.com
* @version build time：Jul 30, 2016 10:08:52 AM
* 
*/


public class ClientFactoryTest {
    @Test
    public void testCreateSimpleClient() {
        CuratorFramework client = ClientFactory.getSimpleClient("127.0.0.1:2181");
        client.start();
        System.out.println(client.getNamespace());
    }
    
    
}
