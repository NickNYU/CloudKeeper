package com.emc.ehc.cloudkeeper.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;

import com.emc.ehc.cloudkeeper.client.factory.ZooKeeperClientUtil;
import com.emc.ehc.cloudkeeper.connection.SSHConnection;
import com.emc.ehc.cloudkeeper.model.ConnectionModel;
import com.emc.ehc.cloudkeeper.model.vROConnectionModel;
import com.emc.ehc.cloudkeeper.vRO.VROConnection;
import com.emc.ehc.cloudkeeper.vRO.VROHealthCheck;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Jul 30, 2016 11:10:25 PM
 * 
 */
public class VROEventWatcher {

    public static void vROServiceShutDown(final CuratorFramework client, vROConnectionModel vro) throws Exception {
        /**
         * 在注册监听器的时候，如果传入此参数，当事件触发时，逻辑由线程池处理
         */
        ExecutorService pool = Executors.newFixedThreadPool(2);
        
        /**
         * 监听数据节点的变化情况
         */
        String path = "/EHC/vRO/" + vro.getName() + "/status";
        
        final NodeCache nodeCache = new NodeCache(client, path, false);
        nodeCache.start(true);
        NodeCacheListener listener = new NodeCacheListener() {

            public void nodeChanged() throws Exception {
                byte[] payload = client.getData().forPath(path);
                String info = new String(payload);
                System.out.format("The health status of vRO is : %s\n", info);

                if ("false".equals(info)) {
                    Thread.sleep(30000);
                    String cmd = "service vco-server restart";
                    
                    ConnectionModel connection = vro.getSshConnection();
                    SSHConnection vROSsh = new SSHConnection(connection.getHost(), connection.getUsername(), connection.getPassword());

                    vROSsh.exec(cmd);

                    client.setData().forPath(path, "true".getBytes());
                    new Thread(new VROHealthCheck(client, vro)).start();
                }
            }
        };
        nodeCache.getListenable().addListener(listener, pool);

    }
}
