package com.emc.ehc.cloudkeeper.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Aug 25, 2016 4:41:56 PM
 * 
 */
@Component
@ConfigurationProperties(prefix = "zkServer")
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
