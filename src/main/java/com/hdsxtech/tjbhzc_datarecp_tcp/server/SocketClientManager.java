package com.hdsxtech.tjbhzc_datarecp_tcp.server;

import com.corundumstudio.socketio.SocketIOClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName:     SocketIOClientCache.java
 * @Description:   TODO
 * @author: Wangys
 * @date:2017年11月21日 上午10:43:16
 * 
 */
public class SocketClientManager {
    //String：EventType类型
    private static Map<String,SocketIOClient> clients=new ConcurrentHashMap<String,SocketIOClient>();
 
    //用户发送消息添加
    public static void addClient(SocketIOClient client,WebsocketMsg msgBean){
        clients.put(msgBean.getZdid()+msgBean.getMsgType(),client);
    }
 
    //用户退出时移除
    public static void remove(WebsocketMsg msgBean) {
        clients.remove(msgBean.getZdid());
    }
     
    //获取所有
    public static SocketIOClient getClient(String to) {
        return clients.get(to);
    }

    //获取所有
    public static Map<String,SocketIOClient> getAllClient() {
        return clients;
    }

    //发送消息
    public static void sendEvent(SocketIOClient client, String context) {
        System.out.println("推送消息");
        client.sendEvent("OnMSG", context);
    }
}