package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BaseUnloadTimeVo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 2968813459762512107L;
	private Long hiddenId;
	private String configName;
	private String configCode;
	//正班车合格卸车时间
	private String nomalUnloadTime;
	//加班车合格卸车时间
	private String overtimeUnloadTime;
	private Integer blFlag;
	private String createUserCode;
	//创建时间
	private Date createTime;
	private String modifyUserCode;
	//修改时间
	private Date modifyTime;
	private String remark;
	private Integer delFlag;
    private List<Long> idList;
    private List<BaseUnloadTimeReleVo> siteCodeList;
    private String siteNames;

	public List<BaseUnloadTimeReleVo> getSiteCodeList() {
		return siteCodeList;
	}
	public void setSiteCodeList(List<BaseUnloadTimeReleVo> siteCodeList) {
		this.siteCodeList = siteCodeList;
	}
	public String getSiteNames() {
		return siteNames;
	}
	public void setSiteNames(String siteNames) {
		this.siteNames = siteNames;
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
	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}
	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	public String getNomalUnloadTime() {
		return nomalUnloadTime;
	}
	public void setNomalUnloadTime(String nomalUnloadTime) {
		this.nomalUnloadTime = nomalUnloadTime;
	}
	public String getOvertimeUnloadTime() {
		return overtimeUnloadTime;
	}
	public void setOvertimeUnloadTime(String overtimeUnloadTime) {
		this.overtimeUnloadTime = overtimeUnloadTime;
	}
	public Integer getBlFlag() {
		return blFlag;
	}
	public void setBlFlag(Integer blFlag) {
		this.blFlag = blFlag;
	}
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}
