package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

import com.ycgwl.framework.springmvc.entity.BizBaseEntity;



/**
 *
 * @Title 接口管理 Entity
 * @Description 关于数据库表 T_BASE_INTERFACE_INFO 的 Entity
 * @Company: 远成快运
 * @author DongQL
 * @date
 *
 */


@Table(value="T_BASE_INTERFACE_INFO", dynamicInsert= true, dynamicUpdate=true)
public class BaseInterfaceInfoEntity extends BizBaseEntity implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 6171533600044841412L;
	//主键
	@Id
	@Column("INTERFACE_ID")
	private Long interfaceId;
	//接口编码
	@Column("INTERFACE_CODE")
	private String interfaceCode;
	//接口名字
	@Column("INTERFACE_NAME")
	private String interfaceName;
	//访问接口链接
	@Column("URL")
	private String url;
	//应用系统KEY
	@Column("APP_KEY")
	private String appKey;
	//页面回调URL
	@Column("PRODUCT_NAME")
	private String productName;
	//页面回调URL
	@Column("RETURN_URL")
	private String returnUrl;
	//后台回调URl
	@Column("NOTIFY_URL")
	private String notifyUrl;
	//预留字段
	@Column("PRE_1")
	private String pre1;
	@Column("PRE_2")
	private String pre2;
	@Column("PRE_3")
	private String pre3;
	@Column("PRE_4")
	private String pre4;
	@Column("PRE_5")
	private String pre5;
	@Column("REMARK")
	private String remark;


	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getPre1() {
		return pre1;
	}
	public void setPre1(String pre1) {
		this.pre1 = pre1;
	}
	public String getPre2() {
		return pre2;
	}
	public void setPre2(String pre2) {
		this.pre2 = pre2;
	}
	public String getPre3() {
		return pre3;
	}
	public void setPre3(String pre3) {
		this.pre3 = pre3;
	}
	public String getPre4() {
		return pre4;
	}
	public void setPre4(String pre4) {
		this.pre4 = pre4;
	}
	public String getPre5() {
		return pre5;
	}
	public void setPre5(String pre5) {
		this.pre5 = pre5;
	}
	public Long getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(Long interfaceId) {
		this.interfaceId = interfaceId;
	}
	public String getInterfaceCode() {
		return interfaceCode;
	}
	public void setInterfaceCode(String interfaceCode) {
		this.interfaceCode = interfaceCode;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
}










