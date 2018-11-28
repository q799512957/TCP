package com.hdsxtech.tjbhzc_datarecp_tcp.server;

import com.hdsxtech.tjbhzc_datarecp_tcp.factory.DataTypeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;


/**
 *
 * 编解码
 *
 */
public class TcpCodec extends ByteToMessageCodec<byte[]> {
    private static final Logger logger = LoggerFactory.getLogger(TcpCodec.class);
    private static final byte HeadFlag = (byte)170;
    private static final byte EndFlag = (byte)238;
    private static final int Head_Length = 7;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
//        if(!buffer.hasArray()){
//            int len =  buffer.readableBytes();
//            byte[] arr = new byte[len];
//            buffer.getBytes(0, arr);
//            System.out.println(Arrays.toString(arr));
//        }
        try {
            if (!searchHead(buffer))
                return;
            int buffLen = buffer.readableBytes();
            int index = buffer.readerIndex();
            if (buffLen < Head_Length)
                return;
            byte[] dataLenBytes = new byte[4];
            buffer.getBytes(index + 2, dataLenBytes);
            Long dataLen = DataTypeUtil.byte2unsignInt32(dataLenBytes);
            if (buffLen < dataLen)
                return;
            byte[] msgbytes = new byte[buffer.readableBytes()];
            buffer.readBytes(msgbytes);
            //System.out.println(Arrays.toString(msgbytes));
            out.add(msgbytes);
            //logger.debug("收到消息并存入待处理队列:{}", msgbytes );

            //   out.add("abc".getBytes());

      //    int buffLen = buffer.readableBytes();
           /* int index = buffer.readerIndex();
            byte[] dataLenBytes = new byte[4];
            buffer.getBytes(index + 2, dataLenBytes);
            //Long dataLen = Converter.bytes2Unsigned32Long(dataLenBytes, 0);
            Long dataLen = com.hdsx.msg.DataTypeUtil.byte2unsignInt32(dataLenBytes) + 10 ;
            int buffLen = buffer.readableBytes();
            if( buffLen < dataLen ) {
                return;
            }
            byte [] ds = new byte[dataLen.intValue()];
            buffer.readBytes(ds);
            out.add(ds);*/
        //    logger.debug("收到消息并存入待处理队列:{}", ds);

        } catch (Exception e) {
            logger.error("解码失败！", e);
        }
    }

    private boolean searchHead(ByteBuf buffer) throws Exception {
        int i = 0;
        int buffLen = buffer.readableBytes();
        int readIndex = buffer.readerIndex();
        byte startHead = 0;
        byte startHead2 = 0;
        while (i < buffLen) {
            startHead = buffer.getByte(readIndex + i);
            startHead2 = buffer.getByte(readIndex + i + 1 );
            if ( startHead == HeadFlag && startHead2 == HeadFlag ) {
                if (i > 0) {
                    buffer.skipBytes( i+1 );
                }
                return true;
            }
            i++;
        }
        return false;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) throws Exception {
        byte[] bt;
        try {
            bt = msg;
            out.writeBytes(bt);
        } catch (Exception e) {
            logger.error("转码失败！", e);
        }
    }
}
