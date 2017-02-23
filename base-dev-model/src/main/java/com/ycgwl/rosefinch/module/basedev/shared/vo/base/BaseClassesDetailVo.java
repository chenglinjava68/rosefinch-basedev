package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;

/**
 *
 * @Title TODO
 * @Description TODO
 *　@Company　远成快运
 * @author panxiaoling
 * @date 2016年11月28日下午1:54:55
 */
public class BaseClassesDetailVo implements Serializable {
    private static final long serialVersionUID = -5243592957374579598L;
    // 班次ID
    private Long id;
    // 班次主表ID
    private String classesCode;
    // 途经点排序位置
    private Integer orderBy;
    // 途经类型（1：起点   2：途经   3：终点）
    private Integer viaType;
    // 当前网点
    private String currSiteCode;
    // 下一网点
    private String nextSiteCode;
    // 当前行政区域编码
    private String currRegionCode;
    // 下一行政区域编码
    private String nextRegionCode;
    // 发车时间
    private String departTime;
    // 到达时间
    private String arrivalTime;
    // 驶到用时
    private Integer arriveTime;
    // 停留时间
    private Integer stayTime;
    // 驶到里程
    private Double arriveMileage;
    // 运输方式
    private  Integer classType;
    // 运输方式名字
    private  String classTypeName;
    // 备注
    private String remark;
    // 车线id
    private String lineCode;
    //车线名字
    private String lineName;
    // 途径名称
    private String viaTypeName;
    // 当前网点名称
    private String currSiteName;
    // 下个网点名称
    private String nextSiteName;
    // 当前行政区域名称
    private String currRegionName;
    // 下个行政区域名称
    private String nextRegionName;
    // 班次名称
    private String classesName;
    // 上个行政区域编码名称
    private String currRegionCodeStr;
    // 下个行政区域编码名称
    private String nextRegionCodeStr;

    public String getClassesName() {
        return classesName;
    }
    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }
    public String getViaTypeName() {
        return viaTypeName;
    }
    public String getCurrRegionCodeStr() {
        return currRegionCodeStr;
    }
    public void setCurrRegionCodeStr(String currRegionCodeStr) {
        this.currRegionCodeStr = currRegionCodeStr;
    }
    public String getNextRegionCodeStr() {
        return nextRegionCodeStr;
    }
    public void setNextRegionCodeStr(String nextRegionCodeStr) {
        this.nextRegionCodeStr = nextRegionCodeStr;
    }
    public void setViaTypeName(String viaTypeName) {
        this.viaTypeName = viaTypeName;
    }
    public String getLineName() {
        return lineName;
    }
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
    public String getClassTypeName() {
        return classTypeName;
    }
    public void setClassTypeName(String classTypeName) {
        this.classTypeName = classTypeName;
    }
    public String getCurrSiteName() {
        return currSiteName;
    }
    public void setCurrSiteName(String currSiteName) {
        this.currSiteName = currSiteName;
    }
    public String getNextSiteName() {
        return nextSiteName;
    }
    public void setNextSiteName(String nextSiteName) {
        this.nextSiteName = nextSiteName;
    }
    public String getCurrRegionName() {
        return currRegionName;
    }
    public void setCurrRegionName(String currRegionName) {
        this.currRegionName = currRegionName;
    }
    public String getNextRegionName() {
        return nextRegionName;
    }
    public void setNextRegionName(String nextRegionName) {
        this.nextRegionName = nextRegionName;
    }


    /**
     * @return the lineCode
     */
    public String getLineCode() {
        return lineCode;
    }
    /**
     * @param lineCode the lineCode to set
     */
    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }
    /**
     * @return the classesCode
     */
    public String getClassesCode() {
        return classesCode;
    }
    /**
     * @param classesCode the classesCode to set
     */
    public void setClassesCode(String classesCode) {
        this.classesCode = classesCode;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getOrderBy() {
        return orderBy;
    }
    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
    public Integer getViaType() {
        return viaType;
    }
    public void setViaType(Integer viaType) {
        this.viaType = viaType;
    }
    public String getCurrSiteCode() {
        return currSiteCode;
    }
    public void setCurrSiteCode(String currSiteCode) {
        this.currSiteCode = currSiteCode;
    }
    public String getNextSiteCode() {
        return nextSiteCode;
    }
    public void setNextSiteCode(String nextSiteCode) {
        this.nextSiteCode = nextSiteCode;
    }
    public String getCurrRegionCode() {
        return currRegionCode;
    }
    public void setCurrRegionCode(String currRegionCode) {
        this.currRegionCode = currRegionCode;
    }
    public String getNextRegionCode() {
        return nextRegionCode;
    }
    public void setNextRegionCode(String nextRegionCode) {
        this.nextRegionCode = nextRegionCode;
    }

    public String getDepartTime() {
        return departTime;
    }
    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }
    public String getArrivalTime() {
        return arrivalTime;
    }
    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    public Integer getArriveTime() {
        return arriveTime;
    }
    public void setArriveTime(Integer arriveTime) {
        this.arriveTime = arriveTime;
    }
    public Integer getStayTime() {
        return stayTime;
    }
    public void setStayTime(Integer stayTime) {
        this.stayTime = stayTime;
    }
    public Double getArriveMileage() {
        return arriveMileage;
    }
    public void setArriveMileage(Double arriveMileage) {
        this.arriveMileage = arriveMileage;
    }

    public Integer getClassType() {
        return classType;
    }
    public void setClassType(Integer classType) {
        this.classType = classType;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }


}
