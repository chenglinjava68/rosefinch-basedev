package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

@Table(value="T_BASE_SITE_DETAIL")
public class BaseSiteDetailEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7804985794713989860L;
	// 主键，网点ID
	@Id
	@Column("SITE_DETAIL_ID")
	private Long siteDetailId;
	
	private String hiddenId;
	
	
	// 网点CODE
	@Column("SITE_CODE")
	private String siteCode;
	// 查询电话
	@Column("PHONE")
	private String phone;
	// 负责人
	@Column("PRINCIPAL")
	private String principal;
	// 经理
	@Column("MANAGER")
	private String manager;
	// 业务电话
	@Column("SALE_PHONE")
	private String salePhone;
	// 传真
	@Column("FAX")
	private String fax;
	// 不派送范围
	@Column("NOT_DISPATCH_RANGE")
	private String notDispatchRange;
	// 派送范围（服务区域）
	@Column("DISPATCH_RANGE")
	private String dispatchRange;
	// 特殊服务区域
	@Column("SPECSERVICE_RANGE")
	private String specserviceRange;
	// 派送时效限制
	@Column("DISPATCH_TIME_LIMIT")
	private String dispatchTimeLimit;
	// 经理手机
	@Column("MANAGER_PHONE")
	private String managerPhone;
	// 派送费用说明
	@Column("DISPATCH_MONEY_DESC")
	private String dispatchMoneyDesc;
	// EXIGENCE_PHONE
	@Column("EXIGENCE_PHONE")
	private String exigencePhone;
	// 财务账号
	@Column("FINANCIAL_ACCOUNT")
	private String financialAccount;
	// 网点简介
	@Column("SITE_DESC")
	private String siteDesc;
	// 允许使用的物料
	@Column("SITEMATERIAL")
	private String sitematerial;
	// 备注
	@Column("REMARK")
	private String remark;
	
	
	public Long getSiteDetailId() {
		return siteDetailId;
	}
	public void setSiteDetailId(Long siteDetailId) {
		this.siteDetailId = siteDetailId;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getSalePhone() {
		return salePhone;
	}
	public void setSalePhone(String salePhone) {
		this.salePhone = salePhone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getNotDispatchRange() {
		return notDispatchRange;
	}
	public void setNotDispatchRange(String notDispatchRange) {
		this.notDispatchRange = notDispatchRange;
	}
	public String getDispatchRange() {
		return dispatchRange;
	}
	public void setDispatchRange(String dispatchRange) {
		this.dispatchRange = dispatchRange;
	}
	public String getSpecserviceRange() {
		return specserviceRange;
	}
	public void setSpecserviceRange(String specserviceRange) {
		this.specserviceRange = specserviceRange;
	}
	public String getDispatchTimeLimit() {
		return dispatchTimeLimit;
	}
	public void setDispatchTimeLimit(String dispatchTimeLimit) {
		this.dispatchTimeLimit = dispatchTimeLimit;
	}
	public String getManagerPhone() {
		return managerPhone;
	}
	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}
	public String getDispatchMoneyDesc() {
		return dispatchMoneyDesc;
	}
	public void setDispatchMoneyDesc(String dispatchMoneyDesc) {
		this.dispatchMoneyDesc = dispatchMoneyDesc;
	}
	public String getExigencePhone() {
		return exigencePhone;
	}
	public void setExigencePhone(String exigencePhone) {
		this.exigencePhone = exigencePhone;
	}
	public String getFinancialAccount() {
		return financialAccount;
	}
	public void setFinancialAccount(String financialAccount) {
		this.financialAccount = financialAccount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSiteDesc() {
		return siteDesc;
	}
	public String getSitematerial() {
		return sitematerial;
	}
	public void setSiteDesc(String siteDesc) {
		this.siteDesc = siteDesc;
	}
	public void setSitematerial(String sitematerial) {
		this.sitematerial = sitematerial;
	}
	
	public String getHiddenId() {
		if (StringUtils.isNotBlank(this.hiddenId)) {
			return this.hiddenId;
		}
		
		String idStr = null == siteDetailId ? null : this.siteDetailId.toString();
		this.setHiddenId(idStr);
		
		return idStr;
	}

	public void setHiddenId(String hiddenId) {
		this.hiddenId = hiddenId;
	}
	
}
