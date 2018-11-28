package com.hdsxtech.tjbhzc_datarecp_tcp.client.handler;

import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MsgBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.ConstantUtils;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * 单车数据反馈的包
 * @author zhanglm
 *
 */
public class Handler02 implements IMsgHandle {

    private Logger logger = LoggerFactory.getLogger(Handler02.class);
    private int reconnectdealy = Integer.parseInt(PropertiesUtil.getProperties().getProperty("reconnectdealy"));
    private int heartbeatdelay = Integer.parseInt(PropertiesUtil.getProperties().getProperty("tcp.heartbeatdelay"));// 心跳间隔
    private int locationdelay = Integer.parseInt(PropertiesUtil.getProperties().getProperty("locationdelay"));      // 发送车辆位置的间隔

    @Override
    public void DoHandle(MsgBean msg) {

        Object equiptId = msg.getBody().getByID(ConstantUtils.HEARTBEAT_EQUIPT_ID).getValue();
        Object result = msg.getBody().getByID(ConstantUtils.HEARTBEAT_ANWSER_DATA).getValue();

        logger.info(
        "客户端接收到的返回数据: "
        + "equiptId: " + equiptId
        + "resault: " + result
        );
    }
}
