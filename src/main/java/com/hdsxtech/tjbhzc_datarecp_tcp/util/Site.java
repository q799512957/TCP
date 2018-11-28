//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.hdsxtech.tjbhzc_datarecp_tcp.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class Site {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String site_id;// 站点标识

    private String site_code;// 站点代码

    private String site_name;// 站点名称

    private String site_type;// 代码分类

    private String dist_code;// 行政区划代码

    private String road_id;// 路线标识

    private String org_id;// 机构标识

    private String company_id;// 企业标识

    private float zh;// 桩号

    private float longitude;// 经度

    private float latitude;// 纬度

    private float altitude;// 海拔

    private String tel;// 联系电话

    private Timestamp build_date;// 建站日期

    private Timestamp expired;// 过期时间

    private String desc_info;// 备注

    private String create_by;// 创建人

    private Timestamp create_time;// 创建时间

    private String update_by;// 更新人

    private Timestamp update_time;// 更新时间

    private String master;// 站长

    private String assistant;// 副站长

    private int line1;// 初检车道数

    private int line2;// 复检车道数

    private int line3;// 预检车道数

    private int parks;// 停车卸货场数

    private float parks_area;// 停车卸货面积(平米)

    private float levy_area;// 征地面积(平米)

    private float wait_area;// 待征地面积(平米)

    private float hire_area;// 租地面积(平米)

    private float house_area;// 房屋面积(平米)

    private float total_area;// 总占地面积(平米)

    private String cargo_type;// 货物分类

    private int cargo_output;// 货物产量

    private String site_img;// 站点图片

    private int site_gi;// 总投资

    private String gczbs;// 监测站

    private String xsfx;// 行驶方向

    private String site_status;// 建设状态

    private String axleload_code;// 交调点代码

    private Timestamp recently_trans_time;// 最近传输的数据时间

    private Timestamp recently_check_time;// 最近检测数据时间

    private Timestamp recently_conn_time;// 最近联网时间

    private String site_code_up;// 上传的站点代码

    public Site() {
    }

    public String getSite_id() {
        return this.site_id;
    }

    public String getSite_code() {
        return this.site_code;
    }

    public String getSite_name() {
        return this.site_name;
    }

    public String getSite_type() {
        return this.site_type;
    }

    public String getDist_code() {
        return this.dist_code;
    }

    public String getRoad_id() {
        return this.road_id;
    }

    public String getOrg_id() {
        return this.org_id;
    }

    public String getCompany_id() {
        return this.company_id;
    }

    public float getZh() {
        return this.zh;
    }

    public float getLongitude() {
        return this.longitude;
    }

    public float getLatitude() {
        return this.latitude;
    }

    public float getAltitude() {
        return this.altitude;
    }

    public String getTel() {
        return this.tel;
    }

    public Timestamp getBuild_date() {
        return this.build_date;
    }

    public Timestamp getExpired() {
        return this.expired;
    }

    public String getDesc_info() {
        return this.desc_info;
    }

    public String getCreate_by() {
        return this.create_by;
    }

    public Timestamp getCreate_time() {
        return this.create_time;
    }

    public String getUpdate_by() {
        return this.update_by;
    }

    public Timestamp getUpdate_time() {
        return this.update_time;
    }

    public String getMaster() {
        return this.master;
    }

    public String getAssistant() {
        return this.assistant;
    }

    public int getLine1() {
        return this.line1;
    }

    public int getLine2() {
        return this.line2;
    }

    public int getLine3() {
        return this.line3;
    }

    public int getParks() {
        return this.parks;
    }

    public float getParks_area() {
        return this.parks_area;
    }

    public float getLevy_area() {
        return this.levy_area;
    }

    public float getWait_area() {
        return this.wait_area;
    }

    public float getHire_area() {
        return this.hire_area;
    }

    public float getHouse_area() {
        return this.house_area;
    }

    public float getTotal_area() {
        return this.total_area;
    }

    public String getCargo_type() {
        return this.cargo_type;
    }

    public int getCargo_output() {
        return this.cargo_output;
    }

    public String getSite_img() {
        return this.site_img;
    }

    public int getSite_gi() {
        return this.site_gi;
    }

    public String getGczbs() {
        return this.gczbs;
    }

    public String getXsfx() {
        return this.xsfx;
    }

    public String getSite_status() {
        return this.site_status;
    }

    public String getAxleload_code() {
        return this.axleload_code;
    }

    public Timestamp getRecently_trans_time() {
        return this.recently_trans_time;
    }

    public Timestamp getRecently_check_time() {
        return this.recently_check_time;
    }

    public Timestamp getRecently_conn_time() {
        return this.recently_conn_time;
    }

    public String getSite_code_up() {
        return this.site_code_up;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public void setSite_code(String site_code) {
        this.site_code = site_code;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public void setSite_type(String site_type) {
        this.site_type = site_type;
    }

    public void setDist_code(String dist_code) {
        this.dist_code = dist_code;
    }

    public void setRoad_id(String road_id) {
        this.road_id = road_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public void setZh(float zh) {
        this.zh = zh;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setAltitude(float altitude) {
        this.altitude = altitude;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setBuild_date(Timestamp build_date) {
        this.build_date = build_date;
    }

    public void setExpired(Timestamp expired) {
        this.expired = expired;
    }

    public void setDesc_info(String desc_info) {
        this.desc_info = desc_info;
    }

    public void setCreate_by(String create_by) {
        this.create_by = create_by;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public void setUpdate_by(String update_by) {
        this.update_by = update_by;
    }

    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public void setAssistant(String assistant) {
        this.assistant = assistant;
    }

    public void setLine1(int line1) {
        this.line1 = line1;
    }

    public void setLine2(int line2) {
        this.line2 = line2;
    }

    public void setLine3(int line3) {
        this.line3 = line3;
    }

    public void setParks(int parks) {
        this.parks = parks;
    }

    public void setParks_area(float parks_area) {
        this.parks_area = parks_area;
    }

    public void setLevy_area(float levy_area) {
        this.levy_area = levy_area;
    }

    public void setWait_area(float wait_area) {
        this.wait_area = wait_area;
    }

    public void setHire_area(float hire_area) {
        this.hire_area = hire_area;
    }

    public void setHouse_area(float house_area) {
        this.house_area = house_area;
    }

    public void setTotal_area(float total_area) {
        this.total_area = total_area;
    }

    public void setCargo_type(String cargo_type) {
        this.cargo_type = cargo_type;
    }

    public void setCargo_output(int cargo_output) {
        this.cargo_output = cargo_output;
    }

    public void setSite_img(String site_img) {
        this.site_img = site_img;
    }

    public void setSite_gi(int site_gi) {
        this.site_gi = site_gi;
    }

    public void setGczbs(String gczbs) {
        this.gczbs = gczbs;
    }

    public void setXsfx(String xsfx) {
        this.xsfx = xsfx;
    }

    public void setSite_status(String site_status) {
        this.site_status = site_status;
    }

    public void setAxleload_code(String axleload_code) {
        this.axleload_code = axleload_code;
    }

    public void setRecently_trans_time(Timestamp recently_trans_time) {
        this.recently_trans_time = recently_trans_time;
    }

    public void setRecently_check_time(Timestamp recently_check_time) {
        this.recently_check_time = recently_check_time;
    }

    public void setRecently_conn_time(Timestamp recently_conn_time) {
        this.recently_conn_time = recently_conn_time;
    }

    public void setSite_code_up(String site_code_up) {
        this.site_code_up = site_code_up;
    }
}
