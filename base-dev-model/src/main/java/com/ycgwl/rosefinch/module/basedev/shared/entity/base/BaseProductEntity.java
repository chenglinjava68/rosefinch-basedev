/*
 * Copyright (C) 2016 YcExpress.
 * All rights reserved.
 */
package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

import com.ycgwl.framework.springmvc.entity.BizBaseEntity;

/**
 * TODO: DOCUMENT ME!
 *
 * @author guoh.mao
 */
@Table(value = "T_BASE_PRODUCT")
public class BaseProductEntity extends BizBaseEntity implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1662249849563433628L;

	@Id
	@Column("ID")
	private Long id;

	private String hiddenId;

	// 产品编码
	@Column("PRODUCT_CODE")
	private String productCode;
	// 产品名称
	@Column(value="PRODUCT_NAME",like=true)
	private String productName;
	// 父级产品编码
	@Column("UP_PRODUCT_CODE")
	private String upProductCode;
	// 父级产品名称
	private String upProductName;
	// 匹配模式
	@Column("REGION_MATCH_MODE")
	private Integer regionMatchMode;
	// 匹配模式 名称
	private String regionMatchModeName;
	// 状态
	@Column("STATUS")
	private Integer status;
	// 状态 名称
	private String statusName;

	// 备注
	@Column("REMARK")
	private String remark;
	//产品级别
	@Column("PRODUCT_LEVEL")
	private Integer productLevel;
    @Column("BL_FLAG")
	private Integer blFlag;//启用标识
	//产品级别名称
	private String productLevelName;


	private String productLevelStr;



	public Integer getBlFlag() {
		return blFlag;
	}
	public void setBlFlag(Integer blFlag) {
		this.blFlag = blFlag;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHiddenId() {
		return   this.id.toString();
	}
	public void setHiddenId(String hiddenId) {
		this.hiddenId = hiddenId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getUpProductCode() {
		return upProductCode;
	}
	public void setUpProductCode(String upProductCode) {
		this.upProductCode = upProductCode;
	}
	public String getUpProductName() {
		return upProductName;
	}
	public void setUpProductName(String upProductName) {
		this.upProductName = upProductName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getRegionMatchMode() {
		return regionMatchMode;
	}
	public void setRegionMatchMode(Integer regionMatchMode) {
		this.regionMatchMode = regionMatchMode;
	}
	public String getRegionMatchModeName() {
		return regionMatchModeName;
	}
	public void setRegionMatchModeName(String regionMatchModeName) {
		this.regionMatchModeName = regionMatchModeName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getProductLevel() {
		return productLevel;
	}
	public void setProductLevel(Integer productLevel) {
		this.productLevel = productLevel;
	}
	public String getProductLevelName() {
		return productLevelName;
	}
	public void setProductLevelName(String productLevelName) {
		this.productLevelName = productLevelName;
	}
	public String getProductLevelStr() {
		return productLevelStr;
	}
	public void setProductLevelStr(String productLevelStr) {
		this.productLevelStr = productLevelStr;
	}

}
