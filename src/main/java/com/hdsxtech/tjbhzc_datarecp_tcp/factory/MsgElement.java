package com.hdsxtech.tjbhzc_datarecp_tcp.factory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class MsgElement implements Cloneable {
    private int length;

    @XmlAttribute(name = "id")
    private String id;

    @XmlAttribute(name = "msgname")
    private String msgname;

    public Integer getShortID() {
        String s = this.id.substring(2);
        return Integer.parseInt(s, 16);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMsgname() {
        return msgname;
    }

    public void setMsgname(String msgname) {
        this.msgname = msgname;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @XmlElement(name = "field")
    private List<MsgField> fields;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        MsgElement obj = (MsgElement) super.clone();
        obj.fields = new ArrayList<MsgField>();
        if (this.fields != null) {
            for (MsgField fld : this.fields) {
                obj.fields.add((MsgField) fld.clone());
            }
        }
        return obj;
    }

    /**
     * 消息长度
     *
     * @return
     */
    public int getLength() {
        if (this.length == 0) {
            for (MsgField fld : this.fields)
                this.length += fld.getLength();
        }
        return this.length;
    }

    @Override
    public String toString() {
        String str = "msgname: " + msgname + " id: " + id + fields;
        return str;
    }

    /**
     * 根据消息ID获取字段值
     *
     * @param id
     * @return
     */
    public MsgField getByID(String id) {
        for (MsgField fld : this.fields) {
            if (fld.getId().equalsIgnoreCase(id))

                return fld;
        }
        return null;
    }

    public List<MsgField> getFields() {
        return fields;
    }

    public void setFields(List<MsgField> fields) {
        this.fields = fields;
    }

    /**
     * 消息解码
     *
     * @param b
     * @param offset
     * @return
     */
    public boolean frombytes(byte[] b, int offset) {
        int filed_length = 0;
        for (MsgField fld : fields) {
            filed_length = fld.frombytes(b, offset);
            offset += filed_length;
        }
        return true;
    }

    /**
     * 消息解码
     *
     * @param b
     * @return
     */
    public boolean frombytes(byte[] b) {
        int offset = 0;
        for (MsgField fld : fields) {
            fld.frombytes(b, offset);
            offset += fld.getLength();
        }
        return true;
    }

    /**
     * 消息编码
     *
     * @return
     */
    public byte[] tobytes() {
        int offset = 0;
        byte[] result = new byte[this.getLength()];
        for (MsgField fld : this.fields) {
            byte[] b = fld.toBytes();
            System.arraycopy(b, 0, result, offset, b.length);
            offset += b.length;
        }
        return result;
    }
}
