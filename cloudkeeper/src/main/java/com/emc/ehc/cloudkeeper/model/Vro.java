package com.emc.ehc.cloudkeeper.model;

import java.io.Serializable;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Aug 26, 2016 11:45:16 AM
 * 
 */

public class Vro implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -640861659259119341L;
    private String name;
    private String host;
    private String username;
    private String password;
    private SshConnection sshConnection;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public SshConnection getSshConnection() {
        return sshConnection;
    }

    public void setSshConnection(SshConnection sshConnection) {
        this.sshConnection = sshConnection;
    }

}
