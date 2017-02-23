package com.ycgwl.rosefinch.module.basedev.shared.vo.base;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
public class BaseConfigVo implements Serializable{


	private static final long serialVersionUID = 5384315556493268121L;
		//配置ID
		private Long configId;
		//配置编号
		private String configCode;
		//配置名称
		private String configName;
		//配置值
		private String configValue;
		//用户是否可编辑（0：否 1：是）
		private String blUserEdit;
		//备注
		private String remark;
		//创建人工号
		private String createUserCode;
		//创建时间
		 private Date createTime;
		//修改人工号
		 private String modifyUserCode;
		//修改时间
		 private Date modifyTime;
		 //删除标识 0-正常  1-删除
		 private Integer delFlag;

		 private List<Long> idList;
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
        public List<Long> getIdList() {
            return idList;
        }
        public void setIdList(List<Long> idList) {
            this.idList = idList;
        }


}
