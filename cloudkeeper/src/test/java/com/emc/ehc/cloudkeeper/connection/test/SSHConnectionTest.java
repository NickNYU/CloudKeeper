package com.emc.ehc.cloudkeeper.connection.test;

import org.junit.Test;

import com.emc.ehc.cloudkeeper.connection.SSHConnection;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Jul 31, 2016 9:33:23 PM
 * 
 */
public class SSHConnectionTest {
    
    @Test public void testExec() {
        String host = "10.103.201.79";
        String username = "root";
        String password = "Emsee123!";
        SSHConnection ssh = new SSHConnection(host, username, password);
        
        String cmd = "chkconfig vco-server";
        ssh.exec(cmd);
    }
}
