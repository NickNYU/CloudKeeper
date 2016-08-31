package com.emc.ehc.cloudkeeper.utils;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.emc.ehc.cloudkeeper.model.SshConnection;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
* @author Nick Zhu E-mail: nick.zhu@emc.com
* @version build time：Aug 31, 2016 9:50:31 AM
* 
*/
public class SshUtils {
    
    private final static Logger logger = LoggerFactory.getLogger(SshUtils.class);
    
    private final static int PORT = 22;
    private static Session session;
    private static JSch jsch = new JSch();
    
    
    public static String exec(SshConnection ssh, String cmd) {
        if (session == null || !isConnected()) {
            connect(ssh);
        }
        Channel channel = null;
        try {
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(cmd);
            // channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();
            channel.connect();
            byte[] tmp = new byte[1024];
            StringBuffer sb = new StringBuffer();
            while (true) {
                
                while (in.available() > 0) {
                    int readLength = in.read(tmp, 0, 1024);
                    if (readLength < 0)
                        break;
                    String data = new String(tmp, 0, readLength);
                    sb.append(data);
                }
                if (channel.isClosed()) {
                    if (in.available() > 0)
                        continue;
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(500);
                } catch (Exception ee) {
                }
            }
            return sb.toString();
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            channel.disconnect();
            close();
        }
        return null;
    }
    
    private static void connect(SshConnection ssh) {

        try {
            session = jsch.getSession(ssh.getUsername(), ssh.getHost(), PORT);
            session.setPassword(ssh.getPassword());

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            session.connect();
        } catch (JSchException e) {
            logger.error(e.getMessage());
        }
    }

    
    private static void close() {
        session.disconnect();
    }

    
    private static boolean isConnected() {
        return session.isConnected();
    }
}
