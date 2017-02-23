package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;
import java.util.List;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

import com.ycgwl.framework.springmvc.entity.BizBaseEntity;

@Table(value="T_BASE_APP_INFO")
public class BaseAppInfoEntity extends BizBaseEntity implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5047919208160954016L;
	// 应用系统ID
	@Id
	@Column("APP_ID")
	private Long appId;
	//应用系统KEY
	@Column("APP_KEY")
	private String appKey;
	//应用系统名称
	@Column("APP_NAME")
	private String appName;
	//密钥
	@Column("APP_SECRET")
	private String appSecret;
	//http请求过期时间(单位：毫秒)
	@Column("TIMEOUT_MILLIS")
	private Integer timeoutMillis;
	//时间戳验证过期时间范围(单位：分钟)
	@Column("TIMESTAMP_MINUTES")
	private Integer timestampMinutes;
	//允许访问IP白名单列表
	@Column("IP_WHITE_LIST")
	private String ipWhiteList;
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
	//明细List
	List<BaseInterfaceInfoEntity> baseInterfaceInfoList;

	public List<BaseInterfaceInfoEntity> getBaseInterfaceInfoList() {
		return baseInterfaceInfoList;
	}
	public void setBaseInterfaceInfoList(List<BaseInterfaceInfoEntity> baseInterfaceInfoList) {
		this.baseInterfaceInfoList = baseInterfaceInfoList;
	}

	public Integer getTimeoutMillis() {
		return timeoutMillis;
	}
	public void setTimeoutMillis(Integer timeoutMillis) {
		this.timeoutMillis = timeoutMillis;
	}
	public Integer getTimestampMinutes() {
		return timestampMinutes;
	}
	public void setTimestampMinutes(Integer timestampMinutes) {
		this.timestampMinutes = timestampMinutes;
	}
	public String getIpWhiteList() {
		return ipWhiteList;
	}
	public void setIpWhiteList(String ipWhiteList) {
		this.ipWhiteList = ipWhiteList;
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
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}


}
