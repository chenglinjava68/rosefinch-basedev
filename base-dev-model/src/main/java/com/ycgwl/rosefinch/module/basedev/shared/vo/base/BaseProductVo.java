/*
 * Copyright (C) 2016 YcExpress.
 * All rights reserved.
 */
package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * TODO: DOCUMENT ME!
 *
 * @author guoh.mao
 */
public class BaseProductVo implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long hiddenId;
   // private Long id;
	private String productCode;
	private String upProductCode;
	private String productName;
	private Integer regionMatchMode;
	private Integer status;

	private Integer blFlag;//启用标识
	private String createUserCode;//创建人
	private Date createTime;//创建时间
	private String modifyUserCode;//修改人
	private Date modifyTime;//修改时间
	private String remark;
	private Integer productLevel;
    private List<Long> idList;
	/*public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}*/
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getBlFlag() {
		return blFlag;
	}
	public void setBlFlag(Integer blFlag) {
		this.blFlag = blFlag;
	}
	public List<Long> getIdList() {
		return idList;
	}
	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}
	public Long getHiddenId() {
		return hiddenId;
	}
	public void setHiddenId(Long hiddenId) {
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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

}

