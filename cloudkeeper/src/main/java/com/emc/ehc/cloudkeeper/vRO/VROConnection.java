package com.emc.ehc.cloudkeeper.vRO;

import com.emc.ehc.cloudkeeper.connection.Connection;
import com.emc.ehc.cloudkeeper.connection.SSHConnection;
import com.emc.ehc.cloudkeeper.event.VROEvents;

/**
* @author Nick Zhu E-mail: nick.zhu@emc.com
* @version build time：Jul 30, 2016 6:01:08 PM
* 
*/
public class VROConnection extends Connection {
    
    private final static int PORT = 8281;
    private String name;
    private SSHConnection sshConnection;
    private VROEvents vROWatcher = new VROEvents();
    
    public VROEvents getvROWatcher() {
        return vROWatcher;
    }
    public VROConnection() {
        super();
        vROWatcher.register(this);
    }
    public VROConnection(String name, String host, String username, String password) {
        super(host, PORT, username, password);
        vROWatcher.register(this);
    }
    
    public SSHConnection getSshConnection() {
        return sshConnection;
    }
    public void setSshConnection(SSHConnection sshConnection) {
        this.sshConnection = sshConnection;
    }
    
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public void connect() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isConnected() {
        // TODO Auto-generated method stub
        return false;
    }
}
