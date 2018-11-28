package com.hdsxtech.tjbhzc_datarecp_tcp.server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName:     ChatServer.java
 * @Description:   TODO
 * @author: Wangys
 * @date:2017年11月21日 上午10:43:53
 * 
 */
@Service("chatServer")
public class ChatServer implements InitializingBean {
    @Resource
    private EventListenner eventListenner;

    public void afterPropertiesSet() throws Exception {
        Configuration config = new Configuration();
        config.setPort(9004);
         
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setReuseAddress(true);
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        config.setSocketConfig(socketConfig);
        config.setHostname("localhost");
         
        SocketIOServer server = new SocketIOServer(config);
        server.addListeners(eventListenner);
        server.start();
        System.out.println("websocket启动正常");
    }
}