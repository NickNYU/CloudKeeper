package com.emc.ehc.cloudkeeper.client.factory;

import org.apache.curator.framework.CuratorFramework;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Jul 30, 2016 9:49:26 AM
 * 
 */
public class ZooKeeperClientUtil {

    /**
     * @param client : zookeeper client
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean isPathExist(CuratorFramework client, String path) throws Exception {
        return client.checkExists().forPath(path) != null;
    }

    /**
     * @param client
     * @param path
     * @param payload
     * @throws Exception
     */
    public static void createNode(CuratorFramework client, String path, byte[] payload) throws Exception {
        if (isPathExist(client, path)) {
            System.out.println(path + " already exists");
        } else {
            client.create().creatingParentsIfNeeded().forPath(path, payload);
        }
    }

    /**
     * @param client
     * @param path
     * @throws Exception
     */
    public static void createNode(CuratorFramework client, String path) throws Exception {
        if (isPathExist(client, path)) {
            System.out.println(path + " already exists");
        } else {
            client.create().creatingParentsIfNeeded().forPath(path);
        }
    }

    public static void deleteNode(CuratorFramework client, String path) throws Exception {
        if (isPathExist(client, path)) {
            client.delete().forPath(path);
        } else {
            System.out.println(path + " doesn't exsits");
        }
    }

}
