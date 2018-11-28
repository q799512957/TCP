package com.hdsxtech.tjbhzc_datarecp_tcp.server.handler;

import com.hdsxtech.tjbhzc_datarecp_tcp.factory.MsgBean;
import com.hdsxtech.tjbhzc_datarecp_tcp.msg.IHandler;
import com.hdsxtech.tjbhzc_datarecp_tcp.server.ClientManager;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.ConstantUtils;
import com.hdsxtech.tjbhzc_datarecp_tcp.util.Constants;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 客户端车辆状态信息handler
 *
 */
public class Handler01 implements IHandler {


    private Logger logger = LoggerFactory.getLogger(Handler01.class);

    public static int SUCCESS = 1;
    public static int ID_ERROR = 2;
    public static int TIME_ERROR = 3;
    public static int POSITION_ERROR = 4;


    public void doHandle(MsgBean msg, ChannelHandlerContext ctx) {
        logger.info("处理客称重消息：");
        try {
            boolean result = false;
      //      System.out.println("车辆消息："+msg);
  /*          Object lsh = msg.getHeader().getByID(ConstantUtils.HEADER_LSH_ID).getValue();
            Object msdId = msg.getHeader().getByID(ConstantUtils.HEADER_MSGID_ID).getValue();
            int sx = (int)msg.getHeader().getByID(ConstantUtils.HEADER_SX_ID).getValue();
            //分包标识（暂时未用）
            int fb = (sx & 0x2000) >> 13;
            //加密标识（暂时未用）
            int jm = (sx & 0xe00) >> 10;
            //消息体长度（暂时未用）
            int length = sx & 0x1ff;

             // 设备编码
            String pageType = msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_PACKAGE_TYPE).getValue().toString();
            byte[] bcheckNo = (byte[])msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_EQUIPT_ID).getValue();
            String checkNo = new String(bcheckNo);
            String checkType = msg.getBody().getByID(ConstantUtils.CHECKWEIGHT_EQUIPT_STATUS).getValue().toString();
*/
            byte[] equiptid=(byte[])msg.getBody().getByID(ConstantUtils.HEARTBEAT_EQUIPT_ID).getValue();
            int year=(int)msg.getBody().getByID(ConstantUtils.HEARTBEAT_YEAR).getValue();
            int month=(int)msg.getBody().getByID(ConstantUtils.HEARTBEAT_MONTH).getValue();
            int day=(int)msg.getBody().getByID(ConstantUtils.HEARTBEAT_DAY).getValue();
            int hour=(int)msg.getBody().getByID(ConstantUtils.HEARTBEAT_HOUR).getValue();
            int minute=(int)msg.getBody().getByID(ConstantUtils.HEARTBEAT_MINUTES).getValue();
            int second=(int)msg.getBody().getByID(ConstantUtils.HEARTBEAT_SECONDS).getValue();
            byte [] ip = (byte[])msg.getBody().getByID(ConstantUtils.HEARTBEAT_IP).getValue();
            double ptx = (double)msg.getBody().getByID(ConstantUtils.HEARTBEAT_PTX).getValue();
            double pty = (double)msg.getBody().getByID(ConstantUtils.HEARTBEAT_PTY).getValue();

            logger.info(
                    "服务器收到的数据："
                    + "equiptId : " + new String(equiptid) + "\n"
                    + "year : " + year + "\n"
                    + "month : " + month + "\n"
                    + "day : " + day + " \n"
                    + "hour : " + hour + "\n"
                    + "minute : " + minute + "\n"
                    + "second : " + second + "\n"
                    + "ip : " + ip + "\n"
                    + "ptx : " + ptx + "\n"
                    + "pty : " + pty
            );

            // 返回给客户端的消息
            MsgBean response = new MsgBean(msg, 0x02, ConstantUtils.KEY_CENTER);
            response.getBody().getByID(ConstantUtils.HEARTBEAT_ANWSER_EQUIPT_ID).setValue(equiptid);

            int r = 0;

           if( !ConstantUtils.isEquiptIdExist(new String(equiptid)) ) { // 查询无此设备
               response.getBody().getByID(ConstantUtils.HEARTBEAT_ANWSER_DATA).setValue(ID_ERROR);
               ClientManager.sendAnswerData(response, ctx);
               return;
           }

           if( !ConstantUtils.isRightHeartTime( year,month,day, hour, minute,second) ) {
               response.getBody().getByID(ConstantUtils.HEARTBEAT_ANWSER_DATA).setValue(TIME_ERROR);
               ClientManager.sendAnswerData(response, ctx);
               return;
           }

           if( !ConstantUtils.isRightPosition(ptx,pty) ) {
               response.getBody().getByID(ConstantUtils.HEARTBEAT_ANWSER_DATA).setValue(POSITION_ERROR);
               ClientManager.sendAnswerData(response, ctx);
               return;
           }

            response.getBody().getByID(ConstantUtils.HEARTBEAT_ANWSER_DATA).setValue(SUCCESS);
            ClientManager.sendAnswerData(response, ctx);

           // ClientManager.addClient(ctx, null);
          //  logger.info("收到客户端传来的单车数据：" + pageType +":" +checkNo + ":"+checkType);
        } catch (NumberFormatException e) {
            logger.error("心跳数据处理异常");
            e.printStackTrace();
        }
    }

    @Override
    public void doHandle(byte[] m, ChannelHandlerContext ctx) {

    }
}
