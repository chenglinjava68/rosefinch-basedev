package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

/**
 *
 * @Title T_BASE_CLASSES_DETAIL
 * @Description 班次详情表实体
 *　@Company　远成快运
 * @author panxiaoling
 * @date 2016年11月28日上午11:14:10
 */
@Table(value = "T_BASE_CLASSES_DETAIL", dynamicInsert = true, dynamicUpdate = true)
public class BaseClassesDetailEntity implements Serializable {
    private static final long serialVersionUID = -5243592957374579598L;
    // 班次ID
    @Id
    @Column("ID")
    private Long id;
    // 班次主表ID
    @Column("CLASSES_CODE")
    private String classesCode;
    // 途经点排序位置
    @Column("ORDER_BY")
    private Integer orderBy;
    // 途经类型（1：起点   2：途经
    @Column("VIA_TYPE")
    private Integer viaType;
    // 当前网点
    @Column("CURR_SITE_CODE")
    private String currSiteCode;
    // 下一网点
    @Column("NEXT_SITE_CODE")
    private String nextSiteCode;
    // 当前行政区域编码
    @Column("CURR_REGION_CODE")
    private String currRegionCode;
    // 下一行政区域编码
    @Column("NEXT_REGION_CODE")
    private String nextRegionCode;
    // 发车时间
    @Column("DEPART_TIME")
    private String departTime;
    // 到达时间
    @Column("ARRIVAL_TIME")
    private String arrivalTime;
    // 驶到用时
    @Column("ARRIVE_TIME")
    private Integer arriveTime;
    // 停留时间
    @Column("STAY_TIME")
    private Integer stayTime;
    // 驶到里程
    @Column("ARRIVE_MILEAGE")
    private Double arriveMileage;
    // 运输方式
    @Column("CLASS_TYPE")
    private  Integer classType;
    // 备注
    @Column("REMARK")
    private String remark;
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
