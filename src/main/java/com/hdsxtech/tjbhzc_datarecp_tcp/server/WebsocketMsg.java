package com.hdsxtech.tjbhzc_datarecp_tcp.server;
/**
 * @ClassName:     MsgBean.java
 * @Description:   TODO
 * @author: Wangys
 * @date:2017年11月21日 上午10:47:18
 * 
 */
public class WebsocketMsg { 
    private String username;
    private String password;
    private String zdid;
    private String content;
    private String msgType;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getZdid() {
		return zdid;
	}
	public void setZdid(String zdid) {
		this.zdid = zdid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	@Override
	public String toString() {
		return "WebsocketMsg [username=" + username + ", password=" + password
				+ ", zdid=" + zdid + ", content=" + content + ", msgType="
				+ msgType + "]";
	}
    
    
     
}