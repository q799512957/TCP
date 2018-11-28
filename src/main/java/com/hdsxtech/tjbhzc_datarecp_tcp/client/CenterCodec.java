package com.hdsxtech.tjbhzc_datarecp_tcp.client;

import com.hdsxtech.tjbhzc_datarecp_tcp.factory.DataTypeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class CenterCodec extends ByteToMessageCodec<byte[]> {
    private static final Logger logger = LoggerFactory.getLogger(CenterCodec.class);
    private static final byte HeadFlag = (byte)170;
    private static final byte EndFlag = (byte)238;
    private static final int Head_Length = 7;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
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
        byte[] msgbytes = new byte[dataLen.intValue()];
        buffer.readBytes(msgbytes);
        out.add(msgbytes);
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
        try {
            byte[] bt = msg;
            out.writeBytes(bt);
        } catch (Exception e1) {
            logger.error("连接后台服务handler---编码异常", e1);
            e1.printStackTrace();
        }
    }
}
