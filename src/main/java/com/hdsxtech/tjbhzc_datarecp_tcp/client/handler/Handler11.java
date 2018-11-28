package com.hdsxtech.tjbhzc_datarecp_tcp.client.handler;

import com.hdsxtech.tjbhzc_datarecp_tcp.client.thread.CheckWeightMsgThread;
import com.hdsxtech.tjbhzc_datarecp_tcp.client.thread.HeartBeatMsgThread;
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
public class Handler11 implements IMsgHandle {

    private Logger logger = LoggerFactory.getLogger(Handler11.class);
    private int reconnectdealy = Integer.parseInt(PropertiesUtil.getProperties().getProperty("reconnectdealy"));
    private int heartbeatdelay = Integer.parseInt(PropertiesUtil.getProperties().getProperty("tcp.heartbeatdelay"));// 心跳间隔
    private int locationdelay = Integer.parseInt(PropertiesUtil.getProperties().getProperty("locationdelay"));      // 发送车辆位置的间隔

    @Override
    public void DoHandle(MsgBean msg) {

        Object equiptId = msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_EQUIPT_ID).getValue();
        Object year = msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_YEAR).getValue();
        Object month = msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_MONTH).getValue();
        Object day = msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_DAY).getValue();
        Object data_no = msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_DATA_NO).getValue();
        int result = (int)msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_RESPONSE_RESULT).getValue();

        if( result == 0x02 ) {
            logger.warn("设备ID 未注册");
            HeartBeatMsgThread.getInstance().run(reconnectdealy,heartbeatdelay);
        } else if( result == 0x03 ) {
            logger.warn("客户端时间错误");
            HeartBeatMsgThread.getInstance().run(reconnectdealy,heartbeatdelay);
        } else if( result == 0xFFFF ) {
            logger.info(data_no + ": 服务器正常返回 ");
        }

        logger.info(
        "客户端接收到的返回数据: "
        + "equiptId: " + equiptId
        + "year: " + year
        + "month: " + month
        + "day: " + day
        + "data_no: "  + data_no
        + "result: " + result
        );
    }
}
