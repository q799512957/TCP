package com.hdsxtech.tjbhzc_datarecp_tcp.client.handler;

import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MsgBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.ConstantUtils;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *  处理图片的返回消息的handler
 */
public class Handler12 implements IMsgHandle {

    private Logger logger = LoggerFactory.getLogger(Handler12.class);
    private int reconnectdealy = Integer.parseInt(PropertiesUtil.getProperties().getProperty("reconnectdealy"));
    private int heartbeatdelay = Integer.parseInt(PropertiesUtil.getProperties().getProperty("tcp.heartbeatdelay"));// 心跳间隔
    private int locationdelay = Integer.parseInt(PropertiesUtil.getProperties().getProperty("locationdelay"));      // 发送车辆位置的间隔

    @Override
    public void DoHandle(MsgBean msg) {

        Object equiptId = msg.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_EQUIPT_ID).getValue();
        Object year = msg.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_YEAR).getValue();
        Object month = msg.getBody().getByID(ConstantUtils.CHECKIMG_RESPONSE_MONTH);
        Object day = msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_DAY);
        Object dataNO = msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_DATA_NO);
        byte result = (byte)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_RESULT).getValue();
     //   int result = (int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_RESULT).getValue();

        if( result == 0x02 ) {
            logger.warn("设备ID 未注册");
        } else if( result == 0x03 ) {
            logger.warn("客户端时间错误");
        } else if( result == 0xFFFF ) {
            logger.info(dataNO + ": 服务器正常返回 ");
        }

        logger.info(
                "服务器端返回处理图片的数据: "
                + " equiptId: " + equiptId
                + " year : " + year
                + " month : " + month
                + " day : " + day
                + " dataNo : " + dataNO
                + " result : " + result
        );

    }
}
