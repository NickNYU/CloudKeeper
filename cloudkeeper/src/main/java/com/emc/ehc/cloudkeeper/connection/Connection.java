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

    protected void setHost(String host) {
        this.host = host;
    }

    protected String getUsername() {
        return username;
    }

    protected void setUsername(String username) {
        this.username = username;
    }

    protected void setPort(int port) {
        this.port = port;
    }

    protected void setPassword(String password) {
        this.password = password;
    }

    protected String getHost() {
        return host;
    }

    protected int getPort() {
        return port;
    }

    protected String getPassword() {
        return password;
    }

    protected abstract void connect();

    protected abstract void close();

    protected abstract boolean isConnected();

}
