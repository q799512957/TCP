package com.hdsxtech.tjbhzc_datarecp_tcp.factory;



import com.hdsxtech.tjbhzc_datarecp_tcp.exception.MsgFactoryException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 消息模板容器
 *
 * @author wangxiri
 */
@XmlRootElement(name = "msgconfig")
@XmlAccessorType(XmlAccessType.FIELD)
public class CenterMsgConfig {
    private Map<String, MsgSeries> msgseriesMap = new ConcurrentHashMap<String, MsgSeries>();
    private boolean inited = false;
    private static CenterMsgConfig obj;

    @XmlElement(name = "msgseries")
    private List<MsgSeries> msgseriesList;

    public static CenterMsgConfig getInstance() {
        if (obj == null)
            obj = new CenterMsgConfig();
        return obj;
    }

    private void init() {
        if (!inited) {
            for (MsgSeries ms : this.msgseriesList) {
                this.msgseriesMap.put(ms.getType(), ms);
            }
        }
        inited = true;
    }

    @Override
    public String toString() {
        return "CenterMsgConfig [msgseriesList=" + msgseriesList + "]";
    }

    /**
     * 获取905消息头
     *
     * @return
     */
    public MsgElement get808HeaderCopy(String key_msg) {
        init();
        if (this.msgseriesMap.containsKey(key_msg)) {
            return this.msgseriesMap.get(key_msg).getHeader();
        } else {
            throw new MsgFactoryException("找不到808消息头配置");
        }
    }

    public MsgElement getElementByID(String id) {
        return null;
    }

    /**
     * 根据ID获取808消息体
     *
     * @param msgid
     * @return
     */
    public MsgElement get808BodyCopyById(int msgid, String key_msg) {
        init();
        if (this.msgseriesMap.containsKey(key_msg)) {
            return this.msgseriesMap.get(key_msg).getBodyByID(msgid);
        } else {
            throw new MsgFactoryException("找不到消息体。协议名称：" + key_msg + "  消息ID：" + Integer.toHexString(msgid));
        }
    }

    public void readFromXml(String path) {
        InputStream is = null;
        try {
            // 获取
            is = CenterMsgConfig.class.getResourceAsStream(path);
            if (is == null) {
                throw new MsgFactoryException("配置文件路径错误:" + path);
            }
            this.obj = this.readConfigFromStream(CenterMsgConfig.class, is);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T readConfigFromStream(Class<T> clazz, final InputStream dataStream) {
        try {
            JAXBContext jc = JAXBContext.newInstance(clazz);
            Unmarshaller u = jc.createUnmarshaller();
            return (T) u.unmarshal(dataStream);
        } catch (JAXBException e) {
            throw new MsgFactoryException(e);
        }
    }
}
