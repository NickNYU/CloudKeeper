package com.emc.ehc.cloudkeeper.model;

import java.io.Serializable;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Aug 26, 2016 11:50:38 AM
 * 
 */
public class SshConnection implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 175957882826998875L;
    
    private String host;
    private String username;
    private String password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
