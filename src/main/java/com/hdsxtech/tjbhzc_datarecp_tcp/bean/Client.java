package com.hdsxtech.tjbhzc_datarecp_tcp.bean;


import io.netty.channel.ChannelHandlerContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 客户端实体
 *
 */
public class Client {
    private ChannelHandlerContext channel;
    private int msgType;
    private Date lastUpTime = new Date();
    private boolean login = true;
    private int userid;
    private String password;
    private String ip;
    private String zdid;
    private long msg_gnsscenterid;
    private String xzqhdm;
    private long M1;
    private long IA1;
    private long IC1;
    private Map msgidMap = new HashMap<>();//未回复	消息id（因为异步的问题  可能会多次回复  客户端判断 或者加锁之类的解决办法）

    public String getZdid() {
		return zdid;
	}

	public void setZdid(String zdid) {
		this.zdid = zdid;
	}

	public String getXzqhdm() {
        return xzqhdm;
    }

    public void setXzqhdm(String xzqhdm) {
        this.xzqhdm = xzqhdm;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getMsg_gnsscenterid() {
        return msg_gnsscenterid;
    }

    public void setMsg_gnsscenterid(long msg_gnsscenterid) {
        this.msg_gnsscenterid = msg_gnsscenterid;
    }

    public long getM1() {
        return M1;
    }

    public void setM1(long m1) {
        M1 = m1;
    }

    public long getIA1() {
        return IA1;
    }

    public void setIA1(long iA1) {
        IA1 = iA1;
    }

    public long getIC1() {
        return IC1;
    }

    public void setIC1(long iC1) {
        IC1 = iC1;
    }

    public ChannelHandlerContext getChannel() {
        return channel;
    }

    public void setChannel(ChannelHandlerContext channel) {
        this.channel = channel;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public Date getLastUpTime() {
        return lastUpTime;
    }

    public void setLastUpTime(Date lastUpTime) {
        this.lastUpTime = lastUpTime;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public Map getMsgidMap() {
        return msgidMap;
    }

    public void setMsgidMap(Map msgidMap) {
        this.msgidMap = msgidMap;
    }

}
