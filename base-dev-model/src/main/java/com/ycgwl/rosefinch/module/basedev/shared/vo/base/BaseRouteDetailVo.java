package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;

/**
*
* @Title:路由维护明细表VO
* @Description:
* @Company: 远成快运
* @author caijue
* @date 2016年11月28日 下午3:23
*
*/
public class BaseRouteDetailVo implements Serializable{

    private static final long serialVersionUID = 7000723374608826095L;

    private Long id;

    private Long routeId;//路由ID

    private Integer orderBy;//途经排序位置

    private Integer viaType;//途经类型（1：起点路由   2：途经路由   3：终点路由）

    private String currSiteCode;//当前网点

    private String nextSiteCode;//下一网点

    private String currRegionCode;//当前行政区域编码

    private String nextRegionCode;//下一行政区域编码

    private Double arriveMileage;//驶到里程

    private Long arriveTime;//驶到用时

    private Long stayTime;//停留时间

    private Integer classType;//运输方式

    private String remark;//备注

    private String currSiteName;//当前网点名称

    private String nextSiteName;//下一网点名称

    private String currRegionName; //当前行政区域名称

    private String nextRegionName;//下一行政区域名称

    private String classTypeName;//运输方式名称

    private String viaTypeName;//途径类型名称

    private String currRegionCodeStr;

    private String nextRegionCodeStr;

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

    public Double getArriveMileage() {
        return arriveMileage;
    }

    public void setArriveMileage(Double arriveMileage) {
        this.arriveMileage = arriveMileage;
    }

    public Long getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Long arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Long getStayTime() {
        return stayTime;
    }

    public void setStayTime(Long stayTime) {
        this.stayTime = stayTime;
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

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
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

    public String getClassTypeName() {
        return classTypeName;
    }

    public void setClassTypeName(String classTypeName) {
        this.classTypeName = classTypeName;
    }

    public String getViaTypeName() {
        return viaTypeName;
    }

    public void setViaTypeName(String viaTypeName) {
        this.viaTypeName = viaTypeName;
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


}
