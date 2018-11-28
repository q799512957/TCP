package com.hdsxtech.tjbhzc_datarecp_tcp.factory;

import com.hdsxtech.tjbhzc_datarecp_tcp.msg.IMsgHead;
import com.hdsxtech.tjbhzc_datarecp_tcp.msg.util.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 *
 * 消息头实现
 *
 */
public class MsgHeader implements IMsgHead, Serializable {
    private static final Logger logger = LoggerFactory.getLogger(MsgHeader.class);
    private static final long serialVersionUID = -5208570060734118764L;

    /**
     * 数据长度（包括头标识、消息头、消息体、CRC校验码和尾标识） UINT32
     */
    private long msg_length;
    /**
     * 消息序列号，占4byte，用于接收方检测是否有信息的丢失。上级平台和下级平台按自己发送数据包的个数计数，互不影响。程序开始运行时等于零，
     * 发送第一帧数据时开始计数，到最大数后自动归零 UINT32
     */
    private long msg_sn;
    /**
     * 业务数据类型的标识 UINT16
     */
    private int msg_id;
    /**
     * 下级平台接入码，上级平台给下级平台分配的唯一标识号 UINT32
     */
    private long msg_gnsscenterid;
    /**
     * 协议版本号标识，上下级平台之间采用的标准协议版本编号，占3byte。例如：0x01 0x02 0x0F 表示的版本号是V1.2.15 BYTES
     */
    private byte[] version_flag;
    /**
     * 消息加密标识位：0 表示消息不加密；1 表示消息加密，说明对后续相应业务的数据体采用ENCRYPT_KEY对应的密钥进行加密处理 BYTE
     */
    private byte encrypt_flag = 0;
    /**
     * 消息体加密的密钥，占4byte UINT32
     */
    private long encrypt_key;
    private long M1;
    private long IA1;
    private long IC1;

    /**
     * 消息头长度
     */
    public int getHeadLen() {
        return 4 + 4 + 2 + 4 + 3 + 1 + 4;
    }

    public byte[] tobytes() {
        ByteBuffer b = ByteBuffer.allocate(getHeadLen());
        b.put(Converter.unSigned32LongToBigBytes(msg_length));
        b.put(Converter.unSigned32LongToBigBytes(msg_sn));
        b.put(Converter.unSigned16IntToBigBytes(msg_id));
        b.put(Converter.unSigned32LongToBigBytes(msg_gnsscenterid));
        b.put(version_flag);
        b.put(encrypt_flag);
        b.put(Converter.unSigned32LongToBigBytes(encrypt_key));
        return b.array();
    }

    public boolean frombytes(byte[] b) {
        try {
            int offset = 0;

            this.msg_length = Converter.bigBytes2Unsigned32Long(b, offset);
            offset += 4;

            this.msg_sn = Converter.bigBytes2Unsigned32Long(b, offset);
            offset += 4;

            this.msg_id = Converter.bigBytes2Unsigned16Int(b, offset);
            offset += 2;

            this.msg_gnsscenterid = Converter
                    .bigBytes2Unsigned32Long(b, offset);
            offset += 4;

            this.version_flag = new byte[3];
            System.arraycopy(b, offset, this.version_flag, 0, 3);
            offset += 3;

            this.encrypt_flag = b[offset];
            offset += 1;

            this.encrypt_key = Converter.bigBytes2Unsigned32Long(b, offset);
            offset += 4;

            return true;
        } catch (Exception e) {
            logger.error("消息头解析错误", e);
        }
        return false;

    }

    public long getMsg_length() {
        return msg_length;
    }

    public void setMsg_length(long msg_length) {
        this.msg_length = msg_length;
    }

    public long getMsg_sn() {
        return msg_sn;
    }

    public void setMsg_sn(long msg_sn) {
        this.msg_sn = msg_sn;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public long getMsg_gnsscenterid() {
        return msg_gnsscenterid;
    }

    public void setMsg_gnsscenterid(long msg_gnsscenterid) {
        this.msg_gnsscenterid = msg_gnsscenterid;
    }

    public byte[] getVersion_flag() {
        return version_flag;
    }

    public void setVersion_flag(byte[] version_flag) {
        this.version_flag = version_flag;
    }

    public byte getEncrypt_flag() {
        return encrypt_flag;
    }

    public void setEncrypt_flag(byte encrypt_flag) {
        this.encrypt_flag = encrypt_flag;
    }

    public long getEncrypt_key() {
        return encrypt_key;
    }

    public void setEncrypt_key(long encrypt_key) {
        this.encrypt_key = encrypt_key;
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

    @Override
    public String toString() {
        return "MsgHeader [msg_length=" + msg_length + ", msg_sn=" + msg_sn
                + ", msg_id=" + msg_id + ", msg_gnsscenterid="
                + msg_gnsscenterid + ", version_flag="
                + Arrays.toString(version_flag) + ", encrypt_flag="
                + encrypt_flag + ", encrypt_key=" + encrypt_key + "]";
    }

}
