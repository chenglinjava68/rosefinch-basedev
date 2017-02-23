package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;
import java.util.Date;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;
/**
 * T_UNLOAD_TIME 卸车时效  配置表 实体
 *
 * @author
 *
 */

@Table(value = "T_UNLOAD_TIME")
public class BaseUnloadTimeEntity  implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2587252083833580892L;
	@Id
	@Column("ID")
	private Long id;
	private String hiddenId;
	//时效名称
	@Column(value = "CONFIG_NAME", like = true)
	private String configName;
	@Column("CONFIG_CODE")
	private String configCode;
	//正班车合格卸车时间
	@Column("NORMAL_UNLOAD_TIME")
	private String nomalUnloadTime;
	private String nomalUnloadTimeName;
	//加班车合格卸车时间
	@Column("OVERTIME_UNLOAD_TIME")
	private String overtimeUnloadTime;
	private String overtimeUnloadTimeName;
	@Column("BL_FLAG")
	private Integer blFlag;
	@Column("CREATE_USER_CODE")
	private String createUserCode;
	//创建时间
	@Column("CREATE_TIME")
	private Date createTime;
	@Column("MODIFY_USER_CODE")
	private String modifyUserCode;
	//修改时间
	@Column("MODIFY_TIME")
	private Date modifyTime;
	@Column("REMARK")
	private String remark;
	@Column("DEL_FLAG")
	private Integer delFlag;
	private String siteNames;
	public String getSiteNames() {
		return siteNames;
	}
	public void setSiteNames(String siteNames) {
		this.siteNames = siteNames;
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

	public String getConfigName() {
		return configName;
	}
	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getNomalUnloadTimeName() {
		return nomalUnloadTimeName;
	}
	public void setNomalUnloadTimeName(String nomalUnloadTimeName) {
		this.nomalUnloadTimeName = nomalUnloadTimeName;
	}
	public String getOvertimeUnloadTimeName() {
		return overtimeUnloadTimeName;
	}
	public void setOvertimeUnloadTimeName(String overtimeUnloadTimeName) {
		this.overtimeUnloadTimeName = overtimeUnloadTimeName;
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
