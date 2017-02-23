package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;
import java.util.Date;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;
@Table(value="T_BASE_AREA")
public class BaseAreaEntity implements Serializable{

	private static final long serialVersionUID = -8917869279313727900L;
	//片区主键
	@Id
	@Column("AREA_ID")
	private Long  areaId;
	//片区编号
	@Column(value = "AREA_CODE",like=true)
	private String areaCode;
	//片区名称
	@Column(value = "AREA_NAME",like = true)
	private String areaName;
	//备注
	@Column("REMARK")
	private String remark;
	//创建时间
	@Column("CREATE_TIME")
	private Date createTime;
	//修改时间
	@Column("MODIFY_TIME")
	private Date modifyTime;
	//创建人
	@Column("CREATE_USER_CODE")
	private String createUserCode;
	//修改人
	@Column("MODIFY_USER_CODE")
	private String modifyUserCode;
	//删除标识
	@Column("DEL_FLAG")
	private Integer delFlag;
	//启用停用标识
	@Column("BL_FLAG")
	private Integer blFlag;
	public Long getAreaId() {
		return areaId;
	}
	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public Integer getBlFlag() {
		return blFlag;
	}
	public void setBlFlag(Integer blFlag) {
		this.blFlag = blFlag;
	}

}
