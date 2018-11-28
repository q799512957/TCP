package com.hdsxtech.tjbhzc_datarecp_tcp.factory;


import com.hdsxtech.tjbhzc_datarecp_tcp.msg.IMsg;
import com.hdsxtech.tjbhzc_datarecp_tcp.msg.util.CRC16_CUI;
import com.hdsxtech.tjbhzc_datarecp_tcp.msg.util.Encrypt;

import java.nio.ByteBuffer;

public abstract class AbsMsg implements IMsg {

    static long msg_sn = 0;// 自主增加的消息流水号
    public long MSG_GNSSCENTERID;// 下级平台接入码
    public static byte[] VERSION_FLAG = new byte[]{0x01, 0x02, 0x0F};// 协议版本标识
    public byte ENCRYPT_FLAG = 0;// 消息加密标识，0：不加密，1：加密

    ByteBuffer buffer = ByteBuffer.allocate(10 * 1024 * 1024);

    protected MsgHeader head;

    public AbsMsg() {
        this.head = new MsgHeader();
        this.head.setMsg_id(getMsgID());
        this.head.setMsg_sn(msg_sn);
        if (msg_sn == 0xFFFF) {
            msg_sn = 0;
        } else {
            msg_sn++;
        }
        this.head.setMsg_gnsscenterid(MSG_GNSSCENTERID);
        this.head.setEncrypt_flag(ENCRYPT_FLAG);
    }

    public byte[] toBytes() {
        byte[] body = bodytoBytes();

        if (this.head.getEncrypt_flag() == 1) {
            this.head.setEncrypt_key(Encrypt.getRandom32long());
            body = Encrypt.encryptUtil(this.head.getEncrypt_key(), body,
                    body.length, this.head.getM1(), this.head.getIA1(), this.head.getIC1());
        }

        this.head.setMsg_length(1 + this.head.getHeadLen() + body.length + 2
                + 1);

        byte[] head = this.head.tobytes();

        buffer.position(0);
        buffer.put(head);
        buffer.put(body);
        byte[] head_body = new byte[buffer.position()];
        buffer.position(0);
        buffer.get(head_body);
        byte[] crc16 = CRC16_CUI.getCRCCRC16_CCITT(head_body);

        head = encode(head);
        body = encode(body);
        crc16 = encode(crc16);

        buffer.position(0);
        buffer.put((byte) 0x5b);
        buffer.put(head);
        buffer.put(body);
        buffer.put(crc16);
        buffer.put((byte) 0x5d);
        byte[] result = new byte[buffer.position()];
        buffer.position(0);
        buffer.get(result);
        return result;
    }

    /**
     * 编码转义
     *
     * @param bytes
     * @return
     */
    private byte[] encode(byte[] bytes) {

        buffer.position(0);
        for (byte b : bytes) {
            if (b == 0x5b) {
                buffer.put((byte) 0x5a);
                buffer.put((byte) 0x01);
            } else if (b == 0x5a) {
                buffer.put((byte) 0x5a);
                buffer.put((byte) 0x02);
            } else if (b == 0x5d) {
                buffer.put((byte) 0x5e);
                buffer.put((byte) 0x01);
            } else if (b == 0x5e) {
                buffer.put((byte) 0x5e);
                buffer.put((byte) 0x02);
            } else {
                buffer.put(b);
            }
        }
        byte[] result = new byte[buffer.position()];
        buffer.position(0);
        buffer.get(result);
        return result;
    }

    public boolean fromBytes(byte[] b) {

        ByteBuffer bf = ByteBuffer.wrap(b);
        byte[] body = new byte[b.length - this.head.getHeadLen() - 2];
        bf.position(this.head.getHeadLen());
        bf.get(body);
        if (this.head.getEncrypt_flag() == 1) {
            body = Encrypt.encryptUtil(this.head.getEncrypt_key(), body,
                    body.length, this.head.getM1(), this.head.getIA1(), this.head.getIC1());
        }
        if (!bodyfromBytes(body))
            return false;
        return true;
    }

    /**
     * 获取消息ID
     *
     * @return
     */
    protected abstract int getMsgID();

    /**
     * 消息体转换为字节数组
     *
     * @return
     */
    public abstract byte[] bodytoBytes();

    /**
     * 字节数组转换为消息体
     *
     * @param b
     * @return
     */
    public abstract boolean bodyfromBytes(byte[] b);

    public MsgHeader getHead() {
        return head;
    }

    public void setHead(MsgHeader head) {
        this.head = head;
    }

}
