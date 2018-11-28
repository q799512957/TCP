package com.hdsxtech.tjbhzc_datarecp_tcp.bean;

import java.math.BigDecimal;
import java.util.Date;

public class DdCheckWeight {

    private String checkNo;

    private String checkType;

    private String siteId;

    private String equipId;

    private String line;

    private String vehicleNo;

    private String vehicleType;

    private String axles;

    private String checkResult;

    private String speed;

    private Date checkTime;

    private BigDecimal total;

    private BigDecimal weight;

    private BigDecimal limitTotal;

    private BigDecimal floatTotal;

    private BigDecimal overTotal;

    private BigDecimal unloadTotal;

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;

    private BigDecimal weight1;

    private BigDecimal weight2;

    private BigDecimal weight3;

    private BigDecimal weight4;

    private BigDecimal weight5;

    private BigDecimal weight6;

    private BigDecimal weightOther;

    private String oldCheckNo;

    private BigDecimal isSortOut;

    private String color;
    private int width;
    private int height;
    private int length;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return CHECK_NO
     */
    public String getCheckNo() {
        return checkNo;
    }

    /**
     * @param checkNo
     */
    public void setCheckNo(String checkNo) {
        this.checkNo = checkNo == null ? null : checkNo.trim();
    }

    /**
     * @return CHECK_TYPE
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * @param checkType
     */
    public void setCheckType(String checkType) {
        this.checkType = checkType == null ? null : checkType.trim();
    }

    /**
     * @return SITE_ID
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * @param siteId
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId == null ? null : siteId.trim();
    }

    /**
     * @return EQUIP_ID
     */
    public String getEquipId() {
        return equipId;
    }

    /**
     * @param equipId
     */
    public void setEquipId(String equipId) {
        this.equipId = equipId == null ? null : equipId.trim();
    }

    /**
     * @return LINE
     */
    public String getLine() {
        return line;
    }

    /**
     * @param line
     */
    public void setLine(String line) {
        this.line = line == null ? null : line.trim();
    }

    /**
     * @return VEHICLE_NO
     */
    public String getVehicleNo() {
        return vehicleNo;
    }

    /**
     * @param vehicleNo
     */
    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo == null ? null : vehicleNo.trim();
    }

    /**
     * @return VEHICLE_TYPE
     */
    public String getVehicleType() {
        return vehicleType;
    }

    /**
     * @param vehicleType
     */
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType == null ? null : vehicleType.trim();
    }

    /**
     * @return AXLES
     */
    public String getAxles() {
        return axles;
    }

    /**
     * @param axles
     */
    public void setAxles(String axles) {
        this.axles = axles == null ? null : axles.trim();
    }

    /**
     * @return CHECK_RESULT
     */
    public String getCheckResult() {
        return checkResult;
    }

    /**
     * @param checkResult
     */
    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult == null ? null : checkResult.trim();
    }

    /**
     * @return SPEED
     */
    public String getSpeed() {
        return speed;
    }

    /**
     * @param speed
     */
    public void setSpeed(String speed) {
        this.speed = speed == null ? null : speed.trim();
    }

    /**
     * @return CHECK_TIME
     */
    public Date getCheckTime() {
        return checkTime;
    }

    /**
     * @param checkTime
     */
    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    /**
     * @return TOTAL
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * @param total
     */
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    /**
     * @return WEIGHT
     */
    public BigDecimal getWeight() {
        return weight;
    }

    /**
     * @param weight
     */
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    /**
     * @return LIMIT_TOTAL
     */
    public BigDecimal getLimitTotal() {
        return limitTotal;
    }

    /**
     * @param limitTotal
     */
    public void setLimitTotal(BigDecimal limitTotal) {
        this.limitTotal = limitTotal;
    }

    /**
     * @return FLOAT_TOTAL
     */
    public BigDecimal getFloatTotal() {
        return floatTotal;
    }

    /**
     * @param floatTotal
     */
    public void setFloatTotal(BigDecimal floatTotal) {
        this.floatTotal = floatTotal;
    }

    /**
     * @return OVER_TOTAL
     */
    public BigDecimal getOverTotal() {
        return overTotal;
    }

    /**
     * @param overTotal
     */
    public void setOverTotal(BigDecimal overTotal) {
        this.overTotal = overTotal;
    }

    /**
     * @return UNLOAD_TOTAL
     */
    public BigDecimal getUnloadTotal() {
        return unloadTotal;
    }

    /**
     * @param unloadTotal
     */
    public void setUnloadTotal(BigDecimal unloadTotal) {
        this.unloadTotal = unloadTotal;
    }

    /**
     * @return CREATE_BY
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * @param createBy
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * @return UPDATE_BY
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * @param updateBy
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * @return WEIGHT1
     */
    public BigDecimal getWeight1() {
        return weight1;
    }

    /**
     * @param weight1
     */
    public void setWeight1(BigDecimal weight1) {
        this.weight1 = weight1;
    }

    /**
     * @return WEIGHT2
     */
    public BigDecimal getWeight2() {
        return weight2;
    }

    /**
     * @param weight2
     */
    public void setWeight2(BigDecimal weight2) {
        this.weight2 = weight2;
    }

    /**
     * @return WEIGHT3
     */
    public BigDecimal getWeight3() {
        return weight3;
    }

    /**
     * @param weight3
     */
    public void setWeight3(BigDecimal weight3) {
        this.weight3 = weight3;
    }

    /**
     * @return WEIGHT4
     */
    public BigDecimal getWeight4() {
        return weight4;
    }

    /**
     * @param weight4
     */
    public void setWeight4(BigDecimal weight4) {
        this.weight4 = weight4;
    }

    /**
     * @return WEIGHT5
     */
    public BigDecimal getWeight5() {
        return weight5;
    }

    /**
     * @param weight5
     */
    public void setWeight5(BigDecimal weight5) {
        this.weight5 = weight5;
    }

    /**
     * @return WEIGHT6
     */
    public BigDecimal getWeight6() {
        return weight6;
    }

    /**
     * @param weight6
     */
    public void setWeight6(BigDecimal weight6) {
        this.weight6 = weight6;
    }

    /**
     * @return WEIGHT_OTHER
     */
    public BigDecimal getWeightOther() {
        return weightOther;
    }

    /**
     * @param weightOther
     */
    public void setWeightOther(BigDecimal weightOther) {
        this.weightOther = weightOther;
    }

    /**
     * @return OLD_CHECK_NO
     */
    public String getOldCheckNo() {
        return oldCheckNo;
    }

    /**
     * @param oldCheckNo
     */
    public void setOldCheckNo(String oldCheckNo) {
        this.oldCheckNo = oldCheckNo == null ? null : oldCheckNo.trim();
    }

    /**
     * @return IS_SORT_OUT
     */
    public BigDecimal getIsSortOut() {
        return isSortOut;
    }

    /**
     * @param isSortOut
     */
    public void setIsSortOut(BigDecimal isSortOut) {
        this.isSortOut = isSortOut;
    }


    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public DdCheckWeight() {
    }

    public DdCheckWeight(String checkNo, String checkType, String siteId, String equipId, String line, String vehicleNo, String vehicleType, String axles, String checkResult, String speed, Date checkTime, BigDecimal total, BigDecimal weight, BigDecimal limitTotal, BigDecimal floatTotal, BigDecimal overTotal, BigDecimal unloadTotal, String createBy, String createTime, String updateBy, String updateTime, BigDecimal weight1, BigDecimal weight2, BigDecimal weight3, BigDecimal weight4, BigDecimal weight5, BigDecimal weight6, BigDecimal weightOther, String oldCheckNo, BigDecimal isSortOut, String color, int width, int height, int length) {
        this.checkNo = checkNo;
        this.checkType = checkType;
        this.siteId = siteId;
        this.equipId = equipId;
        this.line = line;
        this.vehicleNo = vehicleNo;
        this.vehicleType = vehicleType;
        this.axles = axles;
        this.checkResult = checkResult;
        this.speed = speed;
        this.checkTime = checkTime;
        this.total = total;
        this.weight = weight;
        this.limitTotal = limitTotal;
        this.floatTotal = floatTotal;
        this.overTotal = overTotal;
        this.unloadTotal = unloadTotal;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.weight1 = weight1;
        this.weight2 = weight2;
        this.weight3 = weight3;
        this.weight4 = weight4;
        this.weight5 = weight5;
        this.weight6 = weight6;
        this.weightOther = weightOther;
        this.oldCheckNo = oldCheckNo;
        this.isSortOut = isSortOut;
        this.color = color;
        this.width = width;
        this.height = height;
        this.length = length;
    }

    @Override
    public String toString() {
        return "DdCheckWeight{" +
                "checkNo='" + checkNo + '\'' +
                ", checkType='" + checkType + '\'' +
                ", siteId='" + siteId + '\'' +
                ", equipId='" + equipId + '\'' +
                ", line='" + line + '\'' +
                ", vehicleNo='" + vehicleNo + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", axles='" + axles + '\'' +
                ", checkResult='" + checkResult + '\'' +
                ", speed='" + speed + '\'' +
                ", checkTime=" + checkTime +
                ", total=" + total +
                ", weight=" + weight +
                ", limitTotal=" + limitTotal +
                ", floatTotal=" + floatTotal +
                ", overTotal=" + overTotal +
                ", unloadTotal=" + unloadTotal +
                ", createBy='" + createBy + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", weight1=" + weight1 +
                ", weight2=" + weight2 +
                ", weight3=" + weight3 +
                ", weight4=" + weight4 +
                ", weight5=" + weight5 +
                ", weight6=" + weight6 +
                ", weightOther=" + weightOther +
                ", oldCheckNo='" + oldCheckNo + '\'' +
                ", isSortOut=" + isSortOut +
                ", color='" + color + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", length=" + length +
                '}';
    }
}