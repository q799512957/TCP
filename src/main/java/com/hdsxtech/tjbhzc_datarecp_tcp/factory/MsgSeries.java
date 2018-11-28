package com.hdsxtech.tjbhzc_datarecp_tcp.factory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息配置
 *
 * @author wangxiri
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class MsgSeries {

    Map<Integer, MsgElement> bodysMap = new ConcurrentHashMap<Integer, MsgElement>();
    private boolean isInited = false;

    @XmlAttribute(name = "type", required = true)
    private String type;

    @XmlElement(name = "header")
    private MsgElement header;

    @XmlElement(name = "body")
    private List<MsgElement> MsgBodys;

    /**
     * 根据消息id获取消息体模板
     *
     * @param id
     * @return
     */
    public MsgElement getBodyByID(int id) {
        if (!isInited) {
            for (MsgElement e : this.MsgBodys) {
                this.bodysMap.put(e.getShortID(), e);
            }
            this.isInited = true;
        }
        try {
            if (this.bodysMap.get(id) != null) {
                return (MsgElement) this.bodysMap.get(id).clone();
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MsgElement getHeader() {
        try {
            return (MsgElement) header.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setHeader(MsgElement header) {
        this.header = header;
    }

    public List<MsgElement> getMsgBodys() {
        return MsgBodys;
    }

    public void setMsgBodys(List<MsgElement> msgBodys) {
        MsgBodys = msgBodys;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MsgSeries [type=" + type + ", header=" + header + ", MsgBodys=" + MsgBodys + "]";
    }

}
