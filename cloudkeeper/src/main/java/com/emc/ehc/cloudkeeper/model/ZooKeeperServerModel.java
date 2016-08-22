package com.emc.ehc.cloudkeeper.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Aug 22, 2016 5:40:23 PM
 * 
 */
@Component
public class ZooKeeperServerModel {

    @Value("${zkServer.host}")
    private String host;

    @Value("${zkServer.port}")
    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

}
