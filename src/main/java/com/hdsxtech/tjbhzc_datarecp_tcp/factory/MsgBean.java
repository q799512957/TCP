package com.hdsxtech.tjbhzc_datarecp_tcp.factory;


import com.hdsxtech.tjbhzc_datarecp_tcp.util.ConstantUtils;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * 协议消息
 */
public class MsgBean implements Serializable {
    private static final long serialVersionUID = 3822901623800354926L;
    private static final Logger logger = LoggerFactory.getLogger(MsgBean.class);
    private MsgElement header;
    private MsgElement body;

    public MsgBean(int id, String key_msg) {
        this.header = CenterMsgConfig.getInstance().get808HeaderCopy(key_msg);
        this.header.getByID(ConstantUtils.HEADER_BSW_ID).setValue(0xaa);
       // this.header.getByID(ConstantUtils.HEADER_BSW_ID2).setValue(0xaa);
        this.header.getByID(ConstantUtils.HEADER_MSGID_ID).setValue(id);
     /*   this.header.getByID(ConstantUtils.HEADER_SX_ID).setValue(getXxtsx(0,0, getLength() + 1));
        this.header.getByID(ConstantUtils.HEADER_LSH_ID).setValue(Constants.getMsg_sn());
        this.header.getByID(ConstantUtils.HEADER_ZDSBM_ID).setValue("12345678");
        this.header.getByID(ConstantUtils.HEADER_BBH_ID).setValue(1);
        this.header.getByID(ConstantUtils.HEADER_FZX_ID).setValue((byte) 0x04);*/

        this.body = CenterMsgConfig.getInstance().get808BodyCopyById(id, key_msg);
    }

    public MsgBean(MsgBean msgBean, int id, String key_msg) {
        this.header = msgBean.getHeader();
        this.header.getByID(ConstantUtils.HEADER_BSW_ID).setValue(0xaa);
        //this.header.getByID(ConstantUtils.HEADER_BSW_ID2).setValue(0xaa);
        this.header.getByID(ConstantUtils.HEADER_MSGID_ID).setValue(id);
   //     this.header.getByID(ConstantUtils.HEADER_LSH_ID).setValue(Constants.getMsg_sn());
        this.body = CenterMsgConfig.getInstance().get808BodyCopyById(id, key_msg);
    }

    public int getLength() {
        if (this.body != null) {
            return this.header.getLength() + this.body.getLength();
        } else {
            return this.header.getLength();
        }
    }

    @Override
    public String toString() {
        return "MsgBean [消息头: " + header + ", 消息体: " + body + "]";
    }

    public MsgElement getHeader() {
        return header;
    }

    public void setHeader(MsgElement header) {
        this.header = header;
    }

    public MsgElement getBody() {
        return body;
    }

    public void setBody(MsgElement body) {
        this.body = body;
    }

    /**
     * 消息编码
     *
     * @return
     */
    public byte[] tobytes() {

        byte [] header_bytes = new byte[2];
        header_bytes[0] = (byte)170;
        header_bytes[1] = (byte)170;
     //   header.getByID(ConstantUtils.HEADER_BSW_ID).setValue(0xaa);
     //   header.getByID(ConstantUtils.HEADER_BSW_ID2).setValue(0xaa);
        header.getByID(ConstantUtils.HEADER_BSW_ID).setValue(header_bytes);
        header.getByID(ConstantUtils.HEADER_LENGTH_ID).setValue(new Long(getLength() + 1));
    //    header.getByID(ConstantUtils.HEADER_SX_ID).setValue(getXxtsx(0, 0, getLength() + 1));
        byte[] bytes_header = header.tobytes();
        if (body != null) {
            byte[] bytes_body = body.tobytes();
            ByteBuffer buffer = ByteBuffer.allocate(getLength() + 1);
            for (byte b : bytes_header) {
                buffer.put(b);
            }

            for (byte b : bytes_body) {
                buffer.put(b);
            }

            byte mac = 0;
            for (byte b : bytes_header) {
                mac ^= b;
            }

            for (byte b : bytes_body) {
                mac ^= b;
            }
            buffer.put(mac);
            int pos = buffer.position();
            buffer.position(0);
            byte[] result = new byte[pos];
            buffer.get(result, 0, pos);
            return result;
        } else {
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            for (byte b : bytes_header) {
                buffer.put(b);
            }

            byte mac = 0;
            for (byte b : bytes_header) {
                mac ^= b;
            }

            buffer.put(mac);
            int pos = buffer.position();
            buffer.position(0);
            byte[] result = new byte[pos];
            buffer.get(result, 0, pos);
            return result;
        }
    }

    /**
     * 消息解码
     *
     * @param b
     * @return
     */
    public boolean frombytes(byte[] b, String key_msg) {
        if (header == null)
            header = (MsgHeader808) CenterMsgConfig.getInstance().get808HeaderCopy(key_msg);

        if (!header.frombytes(b)) {
            logger.info("消息头解析失败");
            return false;
        }

        int msgid = (int) header.getByID(ConstantUtils.HEADER_MSGID_ID).getValue();
        body = CenterMsgConfig.getInstance().get808BodyCopyById(msgid, key_msg);
        if (body != null) {
            // 如果传输的是图片 ， 给 content Filed 的length 赋值
            if ( msgid == MessageID.MESSAGE_CHECKIMG_ID) {
                Long image_length = (Long)header.getByID(ConstantUtils.HEADER_LENGTH_ID).getValue() - 76 - header.getLength() ;
                // 38 是 非 图像字段 一共所占字节 2 暂不清楚 为何会少了两个字节
                body.getByID(ConstantUtils.CHECKIMG_CONTENT).setLength(image_length.intValue());
            }
            if (!body.frombytes(b, header.getLength())) {
                logger.info("消息体解析失败");
                return false;
            }
        }
        return true;
    }

    /**
     * 验证mac
     *
     * @param b
     * @return
     */
    public static boolean checkMac(byte[] b) {
        byte v = 0;
        for (int i = 0; i < b.length - 1; i++) {
            v ^= b[i];
        }
        Constants.byteToBit(b[b.length - 1]);
        if (v == b[b.length - 1])
            return true;
        return false;
    }
    /**
     * 获取消息体属性
     * @param fb
     * @param sjjm
     * @param length
     * @return
     */
    public static int getXxtsx(int fb,int sjjm,int length){
    	int xxtxs = (int) (fb*Math.pow(2, 13)+sjjm*Math.pow(2,10)+length);
		return xxtxs;
    }
}
