package com.emc.ehc.cloudkeeper.vRO;

import com.emc.ehc.cloudkeeper.connection.Connection;
import com.emc.ehc.cloudkeeper.connection.SSHConnection;
import com.emc.ehc.cloudkeeper.exception.ConnectionException;

/**
* @author Nick Zhu E-mail: nick.zhu@emc.com
* @version build time：Jul 30, 2016 6:01:08 PM
* 
*/
public class VROConnection extends Connection {
    
    private final static int PORT = 8281;
    private SSHConnection sshConnection;
    public VROConnection() {
        super();
    }
    public VROConnection(String host, String username, String password) {
        super(host, PORT, username, password);
    }
    
    public void setSSHConnection(SSHConnection sshConnection) {
        this.sshConnection = sshConnection;
    }
    
    
    @Override
    public void connect() throws ConnectionException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void close() throws ConnectionException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isConnected() {
        // TODO Auto-generated method stub
        return false;
    }
}
