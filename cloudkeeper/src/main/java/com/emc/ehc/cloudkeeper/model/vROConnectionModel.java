package com.emc.ehc.cloudkeeper.model;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Aug 18, 2016 4:17:57 PM
 * 
 */
public class vROConnectionModel {
    private String name;
    
    private String host;

    private String username;

    private String password;

    private ConnectionModel sshConnection;

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

    public ConnectionModel getSshConnection() {
        return sshConnection;
    }

    public void setSshConnection(ConnectionModel sshConnection) {
        this.sshConnection = sshConnection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
