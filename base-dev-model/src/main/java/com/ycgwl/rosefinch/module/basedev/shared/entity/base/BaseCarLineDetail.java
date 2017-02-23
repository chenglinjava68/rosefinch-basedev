package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;
@Table(value="T_BASE_CARLINE_DETAIL")
public class BaseCarLineDetail implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -880182158848219440L;
	@Id
	@Column("ID")//主键
	private Long id;
	@Column("LINE_CODE")//主键
	private String lineCode;
	@Column("ORDER_BY")
	private Integer orderBy;//车线排序位置
	@Column("VIA_TYPE")
	private Integer viaType;//途径类型
	@Column("CURR_SITE_CODE")
	private String currSiteCode;//当前网点
	@Column("NEXT_SITE_CODE")
	private String nextSiteCode;//下一网点
	@Column("CURR_REGION_CODE")
	private String currRegionCode;//当前行政区域
	@Column("NEXT_REGION_CODE")
	private String nextRegionCode;//下一行政区域
	@Column("ARRIVE_MILEAGE")
	private Double arriveMileage;//驶到里程
	@Column("ARRIVE_TIME")
	private Long arriveTime;//驶到用时
	@Column("STAY_TIME")
	private Long stayTime;//停留时间
	@Column("CLASS_TYPE")
	private Integer classType;//运输方式
	@Column("REMARK")
	private String remark;//备注
	private String currSiteName;//当前网点名称
    private String nextSiteName;//下一网点名称
	private String currRegionName; //当前行政区域名称
	private String nextRegionName;//下一行政区域名称
	private String classTypeName;//运输方式名称
	private String viaTypeName;//途径类型名称
	private String currRegionCodeStr;
	private String nextRegionCodeStr;

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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLineCode() {
		return lineCode;
	}
	public void setLineCode(String string) {
		this.lineCode = string;
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






}
