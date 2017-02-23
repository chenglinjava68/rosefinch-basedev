package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;

public class BaseOrgVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8432410844306034296L;
	// 组织CODE
	private String orgCode;
	// 组织名称
	private String orgName;
	// 组织简称
	private String orgNameShort;
	// 排序
	private Integer orderBy;
	// 父级组织
	private String upOrgCode;
	// 组织类型
	private String orgType;
	// 本位币币别
	private String defaultCurrency;
	// 所属国家
//	private String country;
	// 所属省份
	private String province;
	// 城市 网点所在城市
	private String city;
	// 所属区（县）
	private String county;
	// 网点地址
	private String orgAddress;
	// 是否启用
	private Integer blFlag;
	// 转账通知手机
	private String transferNotifyMobile;
	// 备注
	private String remark;
	
	
	public String getOrgCode() {
		return orgCode;
	}
	public String getOrgName() {
		return orgName;
	}
	public String getOrgNameShort() {
		return orgNameShort;
	}
	public Integer getOrderBy() {
		return orderBy;
	}
	public String getUpOrgCode() {
		return upOrgCode;
	}
	public String getOrgType() {
		return orgType;
	}
	public String getDefaultCurrency() {
		return defaultCurrency;
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public String getCounty() {
		return county;
	}
	public String getOrgAddress() {
		return orgAddress;
	}
	public Integer getBlFlag() {
		return blFlag;
	}
	public String getTransferNotifyMobile() {
		return transferNotifyMobile;
	}
	public String getRemark() {
		return remark;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public void setOrgNameShort(String orgNameShort) {
		this.orgNameShort = orgNameShort;
	}
	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}
	public void setUpOrgCode(String upOrgCode) {
		this.upOrgCode = upOrgCode;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}
	public void setBlFlag(Integer blFlag) {
		this.blFlag = blFlag;
	}
	public void setTransferNotifyMobile(String transferNotifyMobile) {
		this.transferNotifyMobile = transferNotifyMobile;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
