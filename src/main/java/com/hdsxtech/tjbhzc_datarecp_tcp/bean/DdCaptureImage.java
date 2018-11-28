package com.hdsxtech.tjbhzc_datarecp_tcp.bean;

import java.util.Arrays;
import java.util.Date;

public class DdCaptureImage {

    private String key;

    private String type;

    private String name;

    private String ext;

    private String imgSize;

    private String expired;

    private String descInfo;

    private String updateBy;

    private String dataId;

    private String checkTime;

    private String checkNo;

    private String siteId;

    private String line; // 车道

    private String equiptId; // 设备ID

    private String checkType; //  设备错误代码

    private byte[] content; // 文件内容


    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getEquiptId() {
        return equiptId;
    }

    public void setEquiptId(String equiptId) {
        this.equiptId = equiptId;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    /**
     * @return KEY
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key
     */
    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    /**
     * @return TYPE
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return NAME
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * @return EXT
     */
    public String getExt() {
        return ext;
    }

    /**
     * @param ext
     */
    public void setExt(String ext) {
        this.ext = ext == null ? null : ext.trim();
    }

    /**
     * @return IMG_SIZE
     */
    public String getImgSize() {
        return imgSize;
    }

    /**
     * @param imgSize
     */
    public void setImgSize(String imgSize) {
        this.imgSize = imgSize == null ? null : imgSize.trim();
    }

    /**
     * @return DESC_INFO
     */
    public String getDescInfo() {
        return descInfo;
    }

    /**
     * @param descInfo
     */
    public void setDescInfo(String descInfo) {
        this.descInfo = descInfo == null ? null : descInfo.trim();
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
     * @return DATA_ID
     */
    public String getDataId() {
        return dataId;
    }

    /**
     * @param dataId
     */
    public void setDataId(String dataId) {
        this.dataId = dataId == null ? null : dataId.trim();
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

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public DdCaptureImage(){}

    public DdCaptureImage(String key, String type, String name, String ext, String imgSize, String expired, String descInfo, String updateBy, String dataId, String checkTime, String checkNo, String siteId, String line, String equiptId, String checkType, byte[] content) {
        this.key = key;
        this.type = type;
        this.name = name;
        this.ext = ext;
        this.imgSize = imgSize;
        this.expired = expired;
        this.descInfo = descInfo;
        this.updateBy = updateBy;
        this.dataId = dataId;
        this.checkTime = checkTime;
        this.checkNo = checkNo;
        this.siteId = siteId;
        this.line = line;
        this.equiptId = equiptId;
        this.checkType = checkType;
        this.content = content;
    }

    @Override
    public String toString() {
        return "DdCaptureImage{" +
                "key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", ext='" + ext + '\'' +
                ", imgSize='" + imgSize + '\'' +
                ", expired='" + expired + '\'' +
                ", descInfo='" + descInfo + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", dataId='" + dataId + '\'' +
                ", checkTime='" + checkTime + '\'' +
                ", checkNo='" + checkNo + '\'' +
                ", siteId='" + siteId + '\'' +
                ", line='" + line + '\'' +
                ", equiptId='" + equiptId + '\'' +
                ", checkType='" + checkType + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}