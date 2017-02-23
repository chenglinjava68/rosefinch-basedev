package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;
@Table(value = "T_UNLOAD_TIME_RELE")
public class BaseUnloadTimeReleEntity  implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 8621818474860264604L;
	@Id
    @Column("ID")
	private Long id;
	private String hiddenId;
	public String getHiddenId() {
		return this.id.toString();
	}
	public void setHiddenId(String hiddenId) {
		this.hiddenId = hiddenId;
	}
	//卸车时效编码
	 @Column("CONFIG_CODE")
	private String configCode;
	//网点编码
	 @Column("SITE_CODE")
	private String siteCode;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getConfigCode() {
		return configCode;
	}
	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

}
