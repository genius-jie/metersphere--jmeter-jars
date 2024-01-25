package com.zlj.utils;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

public class LinuxsshUtill {

    //定义单例
    private static Session session;
    private static LinuxsshUtill linuxsshUtill;

    private LinuxsshUtill() {
    }


    public static synchronized LinuxsshUtill getInstance(String host, int port, String user, String password) throws JSchException {

        if (linuxsshUtill == null) {
            JSch jsch = new JSch();
            session = jsch.getSession(user, host, port);
            session.setConfig("StrictHostKeyChecking", "no");
            session.setServerAliveInterval(60000);
            session.setTimeout(30000);
            session.setPassword(password);
            session.connect();
            linuxsshUtill = new LinuxsshUtill();
        }
        return linuxsshUtill;
    }

    public String exeCommand(String command) throws JSchException, IOException {
        ChannelExec channelExec = null;
        String out = "";
        try {
            channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand(command);
            channelExec.setInputStream(null);
            channelExec.setErrStream(System.err);
            channelExec.connect();
            //获取流
            InputStream in = channelExec.getInputStream();
            out = IOUtils.toString(in, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSchException e) {
            e.printStackTrace();
        } finally {//最后流和连接的关闭
            channelExec.disconnect();
//            session.disconnect();
            return out;
        }
    }
}
