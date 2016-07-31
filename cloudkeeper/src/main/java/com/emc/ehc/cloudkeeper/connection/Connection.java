package com.emc.ehc.cloudkeeper.connection;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Jul 30, 2016 6:11:20 PM
 * 
 */
public abstract class Connection {

    protected String host;
    protected int port;
    protected String username;
    protected String password;

    protected Connection(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    protected Connection() {
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

    public void setPort(int port) {
        this.port = port;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public abstract void connect();

    public abstract void close();

    public abstract boolean isConnected();

}
