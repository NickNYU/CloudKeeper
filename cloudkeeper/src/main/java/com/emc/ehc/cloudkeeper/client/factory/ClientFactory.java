package com.emc.ehc.cloudkeeper.client.factory;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Jul 30, 2016 9:40:11 AM
 * 
 */
public class ClientFactory {

    /**
     * @param connect : Format "Zookeeper Server IP : Connection port"
     * @return
     */
    public static CuratorFramework getSimpleClient(String connect) {
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
        return CuratorFrameworkFactory.newClient(connect, retryPolicy);
    }

    /**
     * @param connect
     * @param sessionTimeoutMs
     * @param connectionTimeoutMs
     * @param retryPolicy
     * @return
     */
    public static CuratorFramework getDefinedClient(String connect, int sessionTimeoutMs, int connectionTimeoutMs,
            ExponentialBackoffRetry retryPolicy) {
        
        return CuratorFrameworkFactory.newClient(connect, sessionTimeoutMs, connectionTimeoutMs, retryPolicy);
    }
}
