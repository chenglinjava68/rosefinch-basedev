package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;
import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

import com.ycgwl.framework.springmvc.entity.BizBaseEntity;

/**
 * 行政区域基础资料
 * @author IT-71664-Zhangxingwang
 * @date 2016-8-23 10:06:11
 */
@Table(value="T_BASE_REGION")
public class BaseRegionEntity extends BizBaseEntity implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 7175232186002211040L;
	// 主键
	@Id
	@Column("REGION_ID")
	private Long regionId;
	// 区域编码
	@Column("REGION_CODE")
	private String regionCode;
	// 区域名称
	@Column(value="REGION_NAME", like=true)
	private String regionName;
	// 区域父编码
	@Column("REGION_PARENT")
	private String regionParent;
	// 区域拼音
	@Column("REGION_PINYIN")
	private String regionPinyin;
	// 区域等级
	@Column("REGION_LEVEL")
	private String regionLevel;
	// 区域编码
	@Column("IS_HOT")
	private Integer ishot;
	// 备注
	@Column("REMARK")
	private String remark;
	
	private String regionParentName;
	
	private String createUserName;
	
	private String modifyUserName;
	
	private String createOrgName;
	
	private String modifyOrgName;
	
	private String regionLevelName;
	
	/**
	 * @return the regionLevel
	 */
	public String getRegionLevel() {
		return regionLevel;
	}
	/**
	 * @param regionLevel the regionLevel to set
	 */
	public void setRegionLevel(String regionLevel) {
		this.regionLevel = regionLevel;
	}
	/**
	 * @return the regionId
	 */
	public Long getRegionId() {
		return regionId;
	}
	/**
	 * @param regionId the regionId to set
	 */
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	/**
	 * @return the regionCode
	 */
	public String getRegionCode() {
		return regionCode;
	}
	/**
	 * @param regionCode the regionCode to set
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	/**
	 * @return the regionName
	 */
	public String getRegionName() {
		return regionName;
	}
	/**
	 * @param regionName the regionName to set
	 */
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	/**
	 * @return the regionParent
	 */
	public String getRegionParent() {
		return regionParent;
	}
	/**
	 * @param regionParent the regionParent to set
	 */
	public void setRegionParent(String regionParent) {
		this.regionParent = regionParent;
	}
	/**
	 * @return the regionPinyin
	 */
	public String getRegionPinyin() {
		return regionPinyin;
	}
	/**
	 * @param regionPinyin the regionPinyin to set
	 */
	public void setRegionPinyin(String regionPinyin) {
		this.regionPinyin = regionPinyin;
	}
	/**
	 * @return the ishot
	 */
	public Integer getIshot() {
		return ishot;
	}
	/**
	 * @param ishot the ishot to set
	 */
	public void setIshot(Integer ishot) {
		this.ishot = ishot;
	}
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRegionParentName() {
		return regionParentName;
	}
	public void setRegionParentName(String regionParentName) {
		this.regionParentName = regionParentName;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getModifyUserName() {
		return modifyUserName;
	}
	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}
	public String getCreateOrgName() {
		return createOrgName;
	}
	public void setCreateOrgName(String createOrgName) {
		this.createOrgName = createOrgName;
	}
	public String getModifyOrgName() {
		return modifyOrgName;
	}
	public void setModifyOrgName(String modifyOrgName) {
		this.modifyOrgName = modifyOrgName;
	}
	public String getRegionLevelName() {
		return regionLevelName;
	}
	public void setRegionLevelName(String regionLevelName) {
		this.regionLevelName = regionLevelName;
	}
}


