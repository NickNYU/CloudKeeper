package com.emc.ehc.cloudkeeper.response;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Aug 26, 2016 12:03:26 PM
 * 
 */
public class VroResponse {
    private String name;
    private String hostname;
    private boolean registered;

    public VroResponse(String name, String hostname, boolean registered) {
        this.name = name;
        this.hostname = hostname;
        this.registered = registered;
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

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

}
