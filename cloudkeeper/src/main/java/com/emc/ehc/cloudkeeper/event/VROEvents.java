package com.emc.ehc.cloudkeeper.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Jul 30, 2016 11:10:25 PM
 * 
 */
public class VROEvents {

    public static void vROServiceShutDown(final CuratorFramework client, final String path) throws Exception {
        /**
         * 在注册监听器的时候，如果传入此参数，当事件触发时，逻辑由线程池处理
         */
        ExecutorService pool = Executors.newFixedThreadPool(2);

        /**
         * 监听数据节点的变化情况
         */
        final NodeCache nodeCache = new NodeCache(client, path, false);
        nodeCache.start(true);
        NodeCacheListener listener = new NodeCacheListener() {

            public void nodeChanged() throws Exception {
                byte[] payload = client.getData().forPath(path);
                System.out.println(payload.toString());
            }
        };
        nodeCache.getListenable().addListener(listener, pool);
    }
}
