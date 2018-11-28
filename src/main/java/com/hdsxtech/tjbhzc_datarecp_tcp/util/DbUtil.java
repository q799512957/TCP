package com.hdsxtech.tjbhzc_datarecp_tcp.util;

import com.hdsxtech.tjbhzc_datarecp_tcp.bean.DdCaptureImage;
import com.hdsxtech.tjbhzc_datarecp_tcp.bean.DdCheckWeight;
import com.hdsxtech.tjbhzc_datarecp_tcp.server.Traffic;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class DbUtil {


    Logger logger = LoggerFactory.getLogger(DbUtil.class);

    public static Connection getConnection(){
        Connection conn=null;
        String className = PropertiesUtil.getProperties().getProperty("db.classname");
        try {
            Class.forName(className);//找到oracle驱动器所在的类
            String url = PropertiesUtil.getProperties().getProperty("db.url");
            String username = PropertiesUtil.getProperties().getProperty("db.username");
            String password = PropertiesUtil.getProperties().getProperty("db.password");
            conn= DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(PreparedStatement pstmt){
        if(pstmt !=null){
            try {
                pstmt.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs){
        if(rs !=null){
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 查询所有可以使用的IP 地址
     * @return
     */
    public static List<String> getCityEquiptIDs() {

        Connection conn = getConnection();
        String sql = PropertiesUtil.getProperties().getProperty("getEquiptIDsSql");

        List<String> ids = new ArrayList<String>();

        PreparedStatement pstmt  =  null;
        ResultSet rs = null;
        try
        {
            pstmt  =  conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                ids.add(rs.getString("ID"));
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if(rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ids;
    }


    public boolean insertCheckWeightData(DdCheckWeight checkWeight) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(checkWeight.getCheckTime());
        Connection conn = getConnection();

        String sql =   " INSERT INTO DD_CHECK_WEIGHT( "+
                       " CHECK_NO,CHECK_TYPE,SITE_ID,EQUIP_ID,LINE,VEHICLE_NO,VEHICLE_TYPE, "+
                       " AXLES,CHECK_RESULT,SPEED,CHECK_TIME,TOTAL,WEIGHT,LIMIT_TOTAL,FLOAT_TOTAL, "+
                       " OVER_TOTAL,UNLOAD_TOTAL,CREATE_TIME,  "+
                       " WEIGHT1,WEIGHT2,WEIGHT3,WEIGHT4,WEIGHT5,WEIGHT6,WEIGHT_OTHER,OLD_CHECK_NO, "+
                       " COLOR,HEIGHT,WIDTH,LENGTH "+
                       " ) VALUES('" +
                       checkWeight.getCheckNo() + "'," +
                       checkWeight.getCheckType() +  "," +
                       checkWeight.getSiteId() + ",'" +
                       checkWeight.getEquipId() +  "'," +
                       checkWeight.getLine() + ",'" +
                       checkWeight.getVehicleNo() + "'," +
                       checkWeight.getVehicleType() + "," +
                       checkWeight.getAxles() +  "," +
                       checkWeight.getCheckResult() +  "," +
                       checkWeight.getSpeed() +  "," +
                        "to_date( '" + sdf.format(checkWeight.getCheckTime()) +  "','yyyy-mm-dd,hh24:mi:ss' )," +
                       checkWeight.getTotal() + "," +
                       checkWeight.getWeight() + "," +
                       checkWeight.getLimitTotal() +  "," +
                       checkWeight.getFloatTotal() + "," +
                       checkWeight.getOverTotal() +  "," +
                       checkWeight.getUnloadTotal() +  "," +
//                       checkWeight.getCreateBy() +  "," +
                       "to_date( " + checkWeight.getCreateTime() +  ",'yyyy-mm-dd,hh24:mi:ss' )," +
                       checkWeight.getWeight1() +  "," +
                       checkWeight.getWeight2() +  "," +
                       checkWeight.getWeight3() +  "," +
                       checkWeight.getWeight4() +  "," +
                       checkWeight.getWeight5() +  "," +
                       checkWeight.getWeight6() +  "," +
                       checkWeight.getWeightOther() +  "," +
                       checkWeight.getOldCheckNo() +  "," +
                       checkWeight.getColor() +  "," +
                       checkWeight.getHeight() + "," +
                       checkWeight.getWidth() + "," +
                       checkWeight.getLength()  +
                                " )";
        try {

           PreparedStatement ps = conn.prepareStatement(sql);
            System.out.println(sql);
           return ps.execute();

        } catch (SQLException e) {
        logger.warn("插入称重数据出错");
        e.printStackTrace();
    }
        return false;
}

    public boolean insertCheckImageData(DdCaptureImage ddCaptureImage) {
        Connection conn = getConnection();
        String sql = " INSERT INTO DD_CAPTURE_IMAGE " +
                " (KEY,CHECK_NO,TYPE,NAME,EXT,IMG_SIZE,EXPIRED,DESC_INFO,CHECK_TIME,CHECK_TYPE,LINE )" +
                " VALUES('" +
                ddCaptureImage.getKey() + "','" +
                ddCaptureImage.getCheckNo() + "'," +
                ddCaptureImage.getType() + ",'" +
                ddCaptureImage.getName() + "'," +
                ddCaptureImage.getExt()  + "," +
                ddCaptureImage.getImgSize() + "," +
                "null," +
                ddCaptureImage.getDescInfo() + "," +
                "to_date( '" + ddCaptureImage.getCheckTime() + "','yyyy-mm-dd,hh24:mi:ss') ," +
                ddCaptureImage.getCheckType() + "," +
                ddCaptureImage.getLine() +
                ")" ;
        try {
            System.out.println(sql);
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.execute();
        } catch (SQLException e) {
            logger.warn(" 插入图片信息出错！ ");
            e.printStackTrace();
        }
        return false;
    }
//一堆sql
    public int insertServerSingleTrafficData(Traffic aTraffic, int aCheckType) throws Exception {
        Connection conn = getConnection();
        PreparedStatement ps=null;
        int weight_other = 0;
        String tableName = "DD_CHECK_WEIGHT";
        if (aTraffic.getTotalWeight() > 600000) {
            this.logger.error("重量异常:");
            this.logger.error("SITE_ID:" + aTraffic.getStationID());
            this.logger.error("VEHICLE_NO:" + aTraffic.getPlate());
            this.logger.error("VEHICLE_TYPE:" + aTraffic.getVehicleType());
            this.logger.error("AXLES:" + aTraffic.getAxleCount());
            this.logger.error("SPEED:" + aTraffic.getVehicleSpeed());
            this.logger.error("CHECK_TIME:" + aTraffic.getPassTime());
            this.logger.error("TOTAL:" + aTraffic.getTotalWeight());
            return 0;
        } else {
            try {
                String distCode = "";
                if (!aTraffic.getStationCode().equals("")) {
                    distCode = this.getDistCodeFormZZ(aTraffic.getStationCode());
                } else {
                    distCode = this.getDistCode(aTraffic.getEquipCode());
                }

                String checkNo = DataIdGenerator.generate32Length(distCode, aTraffic.getPassTime());
                StringBuffer str = new StringBuffer("");
                weight_other = aTraffic.getTotalWeight();
                int weightLimite;
                for (weightLimite = 0; weightLimite < aTraffic.getAxleCount(); ++weightLimite) {
                    if (weightLimite < 6) {
                        weight_other -= aTraffic.getSingleAxleWeight()[weightLimite];
                    }
                }

                if (aTraffic.getTotalWeight() == weight_other) {
                    weight_other = 0;
                }
                int overLimite = 0;
                if (aTraffic.getTotalWeight() > weightLimite) {
                    overLimite = aTraffic.getTotalWeight() - weightLimite;
                }
                weightLimite = getWeightLimit(aTraffic.getAxleCount());
                int checkResult=0;
                if (overLimite > 0) {
                    checkResult=2;
                } else {
                    checkResult=1;
                }
                DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String sql="insert into " + tableName + " (CHECK_NO,CHECK_TYPE,SITE_ID,EQUIP_ID,LINE,VEHICLE_NO,VEHICLE_TYPE,AXLES,CHECK_RESULT," + "SPEED,CHECK_TIME,TOTAL,WEIGHT,LIMIT_TOTAL,FLOAT_TOTAL,OVER_TOTAL,UNLOAD_TOTAL," + "CREATE_BY,CREATE_TIME,UPDATE_BY,UPDATE_TIME," + "WEIGHT1,WEIGHT2,WEIGHT3,WEIGHT4,WEIGHT5,WEIGHT6,WEIGHT_OTHER,OLD_CHECK_NO)" + " values('"+
                        checkNo+ "','" +
                        aCheckType+ "','" +
                        aTraffic.getStationID()+ "','" +
                        null+ "','" +
                        aTraffic.getLineCode()+ "','" +
                        aTraffic.getPlate()+ "','" +
                        aTraffic.getVehicleType()+ "','" +
                        aTraffic.getAxleCount()+ "'," +
                        checkResult+ ",'" +
                        aTraffic.getVehicleSpeed()+ "'," +
                        "to_date( '" +df.format(aTraffic.getPassTime().getTime()) + "','yyyy-mm-dd,hh24:mi:ss') ," +
                        Integer.valueOf(aTraffic.getTotalWeight())+ "," +
                        Integer.valueOf(20000)+ "," +
                        Integer.valueOf(weightLimite)+ "," +
                        Integer.valueOf(0)+ "," +
                        Integer.valueOf(overLimite)+ "," +
                        Integer.valueOf(0)+ ",'" +
                        "HSCK"+ "'," +
                        "to_date( '" +  df.format(new Date()) + "','yyyy-mm-dd,hh24:mi:ss') ,'" +
                        "HSCK"+ "'," +
                        "to_date( '" +  df.format(new Date()) + "','yyyy-mm-dd,hh24:mi:ss') ," +
                        Integer.valueOf(aTraffic.getSingleAxleWeight()[0])+ "," +
                        Integer.valueOf(aTraffic.getSingleAxleWeight()[1])+ "," +
                        Integer.valueOf(aTraffic.getSingleAxleWeight()[2])+ "," +
                        Integer.valueOf(aTraffic.getSingleAxleWeight()[3])+ "," +
                        Integer.valueOf(aTraffic.getSingleAxleWeight()[4])+ "," +
                        Integer.valueOf(aTraffic.getSingleAxleWeight()[5])+ "," +
                        Integer.valueOf(weight_other)+ "," +
                        Integer.valueOf(aTraffic.getSingleVehicleID())+
                        ")" ;
                try {
                    System.out.println(sql);
                    ps = conn.prepareStatement(sql);
                    ps.execute();
                } catch (SQLException e) {
                    logger.warn(" 插入重量信息出错！ ");
                    e.printStackTrace();
                    return 0;
                }finally {
                    ps.close();
                    conn.close();

                }
            } catch (Exception var15) {
                var15.printStackTrace();
            }
        }
        return 1;
    }
    public String getDistCode(String stationid) {
        Connection conn = getConnection();
        PreparedStatement pstmt  =  null;
        ResultSet rs = null;
        String sql = " select DIST_CODE from SYS_SITE where SITE_CODE_UP= '"+stationid+"'";
        String dist = "";
        try{
            pstmt  =  conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                dist=rs.getString("DIST_CODE");
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            if(pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if(rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        String pre="";
        if(StringUtils.isNotEmpty(dist)){
            pre= dist.substring(0, 2) + "00";
        }
        return pre;
    }

    public String getDistCodeFormZZ(String stationCode) {
        Connection conn = getConnection();
        PreparedStatement pstmt  =  null;
        ResultSet rs = null;
        String sql = " select DIST_CODE from SYS_SITE where SITE_CODE_UP = '"+stationCode+"'";
        String dist = "";
        try
        {
            pstmt  =  conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                dist=rs.getString("DIST_CODE");
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if(pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if(rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        String pre = dist.substring(0, 2) + "00";
        return pre;
    }
    public Site getZCStation(String aEquipCode, StringBuffer aStationID) throws Exception {
        String sql = " select * from SYS_SITE where SITE_CODE_UP ='"+aEquipCode+"'";
        Site site = new Site();
        Connection conn = getConnection();
        PreparedStatement pstmt  =  null;
        ResultSet rs = null;
        try
        {
            pstmt  =  conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                aStationID.append(rs.getString("SITE_ID"));
                site.setSite_type(rs.getString("SITE_TYPE_CODE"));
                site.setSite_code(rs.getString("SITE_CODE"));
                site.setSite_id(rs.getString("SITE_ID"));
                site.setSite_code_up(rs.getString("SITE_CODE_UP"));
            }
        }catch(Exception ex)
        {
            throw new Exception("查询治超站点和设备中的ID!" + ex.toString());
        }
        finally
        {
            if(pstmt != null)
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            if(rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return site;
    }
    public int insertImageTrafficData(String key, Traffic aTraffic) throws Exception {
        Connection conn = getConnection();
        PreparedStatement ps=null;
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tableName = "DD_CAPTURE_IMAGE";
        List<Map<String, Object>> list = new ArrayList();
        String desc = "";
        String fileExt = "jpg";
        switch (aTraffic.getImageType()) {
            case 1:
                desc = "车头第一张";
                break;
            case 2:
                desc = "车头第二张";
                break;
            case 17:
                desc = "45度角照片";
                break;
            case 33:
                desc = "侧面";
                break;
            case 49:
                desc = "车尾";
                break;
            case 65:
                desc = "车牌";
                break;
            case 129:
                desc = "视频";
                fileExt = "mp4";
                break;
            default:
                desc = "非现场图片";
        }

        if (aTraffic.getImage().length == 0) {
            return 0;
        } else {
            try {
                String sql = " insert into " + tableName + " (KEY,TYPE,NAME,EXT,IMG_SIZE,EXPIRED,DESC_INFO," + "UPDATE_BY,DATA_ID,CHECK_TIME,CHECK_NO,SITE_ID)" + " values('" +
                        key+ "','" +
                        12+ "','" +
                        aTraffic.getPlate() + "." + fileExt+ "','" +
                        fileExt+ "','" +
                        aTraffic.getImage().length+ "'," +
                        "to_date( '" +  df.format(aTraffic.getPassTime().getTime()) + "','yyyy-mm-dd,hh24:mi:ss') ,'" +
                        desc+ "','" +
                        "GQZP"+ "','" + "'," +
                        "to_date( '" +  df.format(aTraffic.getPassTime().getTime()) + "','yyyy-mm-dd,hh24:mi:ss') ,'" +
                        aTraffic.getSingleVehicleID()+ "','" +
                        aTraffic.getStationID()+ "'" +
                        ")";
                System.out.println(sql);
                ps = (PreparedStatement) conn.prepareStatement(sql);
                ps.execute();
            } catch (SQLException e) {
                logger.warn(" 插入图片信息出错！ ");
                e.printStackTrace();
                return 0;
            }finally {
                ps.close();
                conn.close();

            }
        }
        return 1;
    }
//    public int getCountFormWeightShow(Traffic myTF) {
//        String sql="";
//        String s = " select count(*) from DAT_CHECK_WEIGHT_SHOW where SITE_ID = :SITE_ID  and CHECK_TIME = :CHECK_TIME and TOTAL = :TOTAL  and LINE = :LINE and SPEED = :SPEED ";
//        Connection conn = getConnection();
//        Map<String, Object> params = new HashMap();
//        params.put("SITE_ID", myTF.getStationID());
//        params.put("CHECK_TIME", myTF.getPassTime());
//        params.put("TOTAL", Integer.valueOf(myTF.getTotalWeight()));
//        params.put("LINE", Integer.valueOf(myTF.getLineCode()));
//        params.put("SPEED", Integer.valueOf(myTF.getVehicleSpeed()));
//        int c = Integer.parseInt(((String) this.namejdbctemp.queryForObject(s, params, String.class)).toString());
//        return c;
//    }

    public static int getWeightLimit(int axles) {
        switch (axles) {
            case 1:
            case 2:
                return 18000;
            case 3:
                return 27000;
            case 4:
                return 36000;
            case 5:
                return 43000;
            default:
                return 49000;
        }
    }
}