package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;
import java.util.Date;
import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;
/**
 * 
 * Title:T_BASE_CONFIG配置表Entity实体
 * Description:
 * @Company: 远成快运  
 * @author qiaokangjun
 * @date 2016年8月11日  上午10:26:51
 *
 */
@Table(value="T_BASE_CONFIG",dynamicInsert=true,dynamicUpdate=true)
public class BaseConfigEntity implements Serializable{
	
	private static final long serialVersionUID = -5243592957374579598L;
		//配置ID
		@Id
		@Column("CONFIG_ID")
		private Long configId;
		//配置编号
		@Column("CONFIG_CODE")
	    private String configCode;
		//配置名称
		@Column(value="CONFIG_NAME",like=true)
		private String configName;
		//配置值
		@Column("CONFIG_VALUE")
		private String configValue;
		//用户是否可编辑（0：否 1：是）
		@Column("BL_USER_EDIT")
		private String blUserEdit;
		//备注
		@Column("REMARK")
		private String remark;
		//创建人工号
		@Column("CREATE_USER_CODE")
		private String createUserCode;
		//创建时间
		@Column("CREATE_TIME")
	    private Date createTime;
		//修改人工号
		@Column("MODIFY_USER_CODE")
		private String modifyUserCode;
		//修改时间
		@Column("MODIFY_TIME")
		 private Date modifyTime;
		 //删除标识 0-正常  1-删除
		@Column("DEL_FLAG")
	    private Integer delFlag;
		public Long getConfigId() {
			return configId;
		}
		public void setConfigId(Long configId) {
			this.configId = configId;
		}
		public String getConfigCode() {
			return configCode;
		}
		public void setConfigCode(String configCode) {
			this.configCode = configCode;
		}
		public String getConfigName() {
			return configName;
		}
		public void setConfigName(String configName) {
			this.configName = configName;
		}
		public String getConfigValue() {
			return configValue;
		}
		public void setConfigValue(String configValue) {
			this.configValue = configValue;
		}
		public String getBlUserEdit() {
			return blUserEdit;
		}
		public void setBlUserEdit(String blUserEdit) {
			this.blUserEdit = blUserEdit;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
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
		public Integer getDelFlag() {
			return delFlag;
		}
		public void setDelFlag(Integer delFlag) {
			this.delFlag = delFlag;
		}
		@Override
		public String toString() {
			return "ConfigEntity [configId=" + configId + ", configCode=" + configCode + ", configName=" + configName
					+ ", configValue=" + configValue + ", blUserEdit=" + blUserEdit + ", remark=" + remark
					+ ", createUserCode=" + createUserCode + ", createTime=" + createTime + ", modifyUserCode="
					+ modifyUserCode + ", modifyTime=" + modifyTime + ", delFlag=" + delFlag + "]";
		}
		

}
