package com.emc.ehc.cloudkeeper.response;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Aug 26, 2016 9:17:34 PM
 * 
 */
public class VroStatusResponse {
    private String name;
    private String hostname;
    private boolean healthy;

    public VroStatusResponse(String name, String hostname, boolean healthy) {
        this.name = name;
        this.hostname = hostname;
        this.healthy = healthy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }

}
