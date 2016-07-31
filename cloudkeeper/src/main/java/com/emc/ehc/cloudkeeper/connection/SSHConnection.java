package com.emc.ehc.cloudkeeper.connection;

import java.io.InputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
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

    private static Logger logger = LoggerFactory.getLogger(SSHConnection.class);

    public SSHConnection() {
        super();
    }

    public SSHConnection(String host, String username, String password) {
        super(host, PORT, username, password);
    }

    public void exec(String cmd) {
        if (session == null || !isConnected()) {
            connect();
        }
        Channel channel = null;
        try {
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(cmd);
            //channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] tmp=new byte[1024];
            while(true){
              while(in.available()>0){
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                System.out.print(new String(tmp, 0, i));
              }
              if(channel.isClosed()){
                if(in.available()>0) continue; 
                System.out.println("exit-status: "+channel.getExitStatus());
                break;
              }
              try{Thread.sleep(1000);}catch(Exception ee){}
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            channel.disconnect();
            close();
        }
    }

    @Override
    public void connect() {

        try {
            session = jsch.getSession(super.username, super.host, super.port);
            session.setPassword(password);
            
            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            
            session.connect();
        } catch (JSchException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void close() {
        session.disconnect();
    }

    @Override
    public boolean isConnected() {
        return session.isConnected();
    }

}
