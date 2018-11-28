package com.hdsxtech.tjbhzc_datarecp_tcp.server;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("eventListenner")
public class EventListenner {

    @Resource(name = "clientCache")
    private SocketIOClientCache clientCache;
 
    @Resource(name = "socketIOResponse")
    private SocketIOResponse socketIOResponse;
     
    @OnConnect
    public void onConnect(SocketIOClient client) {
       System.out.println("建立连接");
    }
 
    @OnEvent("OnMSG")
    public void onSync(SocketIOClient client, WebsocketMsg bean) {

		System.out.println("OnMsg ... ");

     /*   String username = null;
        String password = null;
        try {
			username = DESUtil.decryption(bean.getUsername(), "12345678");
			password = DESUtil.decryption(bean.getPassword(), "12345678");
		} catch (Exception e) {
			e.printStackTrace();
		}
        if(StringUtils.equals(username, "admin") && StringUtils.equals(password, "123456")){
        	ConcurrentHashMap<String, Client> clientMap = ClientManager.getClient();
        	ChannelHandlerContext channel = null;
        	for (String key : clientMap.keySet()) {
        		if(clientMap.get(key).getZdid().contains(bean.getZdid())){
        			channel = clientMap.get(key).getChannel();
        		}
        	}
        	MsgBean msg808_r = null;
        	if(channel != null){
        		if("8300".equals(bean.getMsgType())){
        			msg808_r = new MsgBean(MessageID.MESSAGE_VOICE_ID, ConstantUtils.KEY_CENTER);
        			msg808_r.getBody().getByID(ConstantUtils.VOICE_BODY_BJ_ID).setValue(2);
        			msg808_r.getBody().getByID(ConstantUtils.VOICE_BODY_CN_ID).setValue(bean.getContent());
//					SocketClientManager.sendEvent(client,"语音");
        		}else{
        			msg808_r = new MsgBean(MessageID.MESSAGE_PHONO_ID, ConstantUtils.KEY_CENTER);
        			msg808_r.getBody().getByID(ConstantUtils.PHONO_BODY_TDID_ID).setValue(2);
        			msg808_r.getBody().getByID(ConstantUtils.PHONO_BODY_PSML_ID).setValue(1);
        			msg808_r.getBody().getByID(ConstantUtils.PHONO_BODY_PSJG_ID).setValue(null);
        			msg808_r.getBody().getByID(ConstantUtils.PHONO_BODY_BCBZ_ID).setValue(0);
        			msg808_r.getBody().getByID(ConstantUtils.PHONO_BODY_FBL_ID).setValue(0x03);
        			msg808_r.getBody().getByID(ConstantUtils.PHONO_BODY_TX_ID).setValue(1);
        			msg808_r.getBody().getByID(ConstantUtils.PHONO_BODY_LD_ID).setValue(0);
        			msg808_r.getBody().getByID(ConstantUtils.PHONO_BODY_DBD_ID).setValue(0);
        			msg808_r.getBody().getByID(ConstantUtils.PHONO_BODY_BHD_ID).setValue(0);
        			msg808_r.getBody().getByID(ConstantUtils.PHONO_BODY_SD_ID).setValue(0);
//					SocketClientManager.sendEvent(client,"照片");
        		}
        		ClientManager.sendAnswerData(msg808_r, channel);
        	}
        	SocketClientManager.addClient(client,bean);
        	clientCache.addClient(client, bean);

        }*/
    }
 
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {

    	System.out.println("关闭连接");
    }
}
