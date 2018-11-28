package com.hdsxtech.tjbhzc_datarecp_tcp.server;

import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName:     SocketIOClientCache.java
 * @Description:   TODO
 * @author: Wangys
 * @date:2017年11月21日 上午10:43:16
 * 
 */
@Service("clientCache")
public class SocketIOClientCache {
    //String：EventType类型
    private Map<String,SocketIOClient> clients=new ConcurrentHashMap<String,SocketIOClient>();
 
    //用户发送消息添加
    public void addClient(SocketIOClient client,WebsocketMsg msgBean){
        clients.put(msgBean.getZdid()+msgBean.getMsgType()+String.valueOf(System.currentTimeMillis()),client);
    }
 
    //用户退出时移除
    public void remove(WebsocketMsg msgBean) {
        clients.remove(msgBean.getZdid());
    }
     
    //获取所有
    public  SocketIOClient getClient(String to) {
        return clients.get(to);
    }

    //获取所有
    public  Map<String,SocketIOClient> getAllClient() {
        return clients;
    }
}