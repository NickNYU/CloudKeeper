package com.emc.ehc.cloudkeeper.connection;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @author Nick Zhu E-mail: nick.zhu@emc.com
 * @version build time：Jul 30, 2016 6:21:00 PM
 * 
 */
public class SSHConnection extends Connection {

    private final static int PORT = 22;
    private Session session;
    private JSch jsch = new JSch();

    // private static Logger logger = LoggerFactory.getLogger(SSHConnection.class);

    public SSHConnection() {
        super();
    }

    public SSHConnection(String host, String username, String password) {
        super(host, PORT, username, password);
    }

    public void exec(String cmd) throws ConnectionException {
        if (!isConnected()) {
            connect();
        }
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(cmd);
        channel.setInputStream(null);
        ((ChannelExec) channel).setErrStream(System.err);

        InputStream in = channel.getInputStream();

        channel.connect();
    }

    @Override
    public void connect() throws ConnectionException {

        try {
            session = jsch.getSession(super.username, super.host, super.port);
            session.setPassword(password);
            session.connect();
        } catch (JSchException e) {
            // logger.error(e.getMessage());
        }
    }

    @Override
    public void close() throws ConnectionException {
        session.disconnect();
    }

    @Override
    public boolean isConnected() {
        return session.isConnected();
    }

}
