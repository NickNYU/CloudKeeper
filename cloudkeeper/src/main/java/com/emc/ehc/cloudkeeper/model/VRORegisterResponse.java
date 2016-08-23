package com.emc.ehc.cloudkeeper.model;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Aug 22, 2016 6:02:36 PM
 * 
 */
public class VRORegisterResponse {
    private String name;
    private String hostname;
    private boolean registered;
    
    public VRORegisterResponse(String name, String hostname, boolean register) {
        super();
        this.name = name;
        this.hostname = hostname;
        this.registered = register;
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