package com.emc.ehc.cloudkeeper.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by NickNYU on 8/24/2016.
 */
@Component
@ConfigurationProperties(prefix = "zookeeperServer")
public class ZookeeperServer {
    private String host;
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
