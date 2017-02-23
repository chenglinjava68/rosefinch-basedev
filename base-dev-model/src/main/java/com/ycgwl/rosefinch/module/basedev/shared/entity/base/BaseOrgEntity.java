package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

import com.ycgwl.framework.springmvc.entity.BizBaseEntity;

@Table(value="T_BASE_ORG")
public class BaseOrgEntity extends BizBaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7861472678120715581L;
	// 主键
	@Id
	@Column("ORG_ID")
	private Long orgId;
	// 组织CODE
	@Column("ORG_CODE")
	private String orgCode;
	// 组织名称
	@Column(value="ORG_NAME",like=true)
	private String orgName;
	// 组织简称
	@Column("ORG_NAME_SHORT")
	private String orgNameShort;
	// 排序
	@Column("ORDER_BY")
	private Integer orderBy;
	// 父级组织
	@Column("UP_ORG_CODE")
	private String upOrgCode;
	// 上级组织名称
	private String upOrgName;
	// 组织类型
	@Column("ORG_TYPE")
	private String orgType;
	// 组织类型名称
	private String orgTypeName;
	// 本位币币别
	@Column("DEFAULT_CURRENCY")
	private String defaultCurrency;
	// 本位币币别名称
	private String defaultCurrencyName;
	// 所属国家
	@Column("COUNTRY")
	private String country;
	// 所属省份
	@Column("PROVINCE")
	private String province;
	// 所属省份名称
	private String provinceName;
	// 城市 网点所在城市
	@Column("CITY")
	private String city;
	// 城市 名称
	private String cityName;
	// 所属区（县）
	@Column("COUNTY")
	private String county;
	// 所属区（县）名称
	private String countyName;
	// 网点地址
	@Column("ORG_ADDRESS")
	private String orgAddress;
	// 是否启用
	@Column("BL_FLAG")
	private Integer blFlag;
	// 转账通知手机
	@Column("TRANSFER_NOTIFY_MOBILE")
	private String transferNotifyMobile;
	// 备注
	@Column("REMARK")
	private String remark;
	
	// AD域中唯一标识
	@Column("GUID")
	private String guid;
	
	
	private Boolean hasChildren;   // 是否有子节点
	
	
	// 从AD域中同步过来时是否忽略该组织
	private Boolean ignore;
	
	
	public Long getOrgId() {
		return orgId;
	}
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
	public String getCountry() {
		return country;
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
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
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
	public void setCountry(String country) {
		this.country = country;
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
	public String getUpOrgName() {
		return upOrgName;
	}
	public String getOrgTypeName() {
		return orgTypeName;
	}
	public String getDefaultCurrencyName() {
		return defaultCurrencyName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setUpOrgName(String upOrgName) {
		this.upOrgName = upOrgName;
	}
	public void setOrgTypeName(String orgTypeName) {
		this.orgTypeName = orgTypeName;
	}
	public void setDefaultCurrencyName(String defaultCurrencyName) {
		this.defaultCurrencyName = defaultCurrencyName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public Boolean getHasChildren() {
		return hasChildren;
	}
	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	public Boolean getIgnore() {
		return ignore;
	}
	public void setIgnore(Boolean ignore) {
		this.ignore = ignore;
	}
	
}
