package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;
import java.util.Date;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

@Table(value="T_BASE_SITE_FIN_LINE")
public class BaseSiteFinLineEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5084504957701650971L;
	// 主键
	@Id
	@Column("ID")
	private Long id;
	// 网点code
	@Column("SITE_CODE")
	private String siteCode;
	// 子网点
	@Column("SITE_SON")
	private String siteSon;
	// 父网点
	@Column("SITE_PARENT")
	private String siteParent;
	
	// 结算路由类型（0：三级网点到二级网点 1：二级级网点到一级网点   2：一级网点到分公司   3：分公司到快运本部   4：快运本部到快运本部）
	@Column("LINE_TYPE")
	private Integer lineType;
	// 有效时间开始时间
	@Column("PERIOD_BEGIN_TIME")
	private Date periodBeginTime;
	// 有效时间结束时间
	@Column("PERIOD_END_TIME")
	private Date periodEndTime;
	// 删除标识 0-正常  1-删除
	@Column("DEL_FLAG")
	private Integer delFlag;
	
	public Long getId() {
		return id;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public String getSiteSon() {
		return siteSon;
	}
	public String getSiteParent() {
		return siteParent;
	}
	public Date getPeriodBeginTime() {
		return periodBeginTime;
	}
	public Date getPeriodEndTime() {
		return periodEndTime;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public void setSiteSon(String siteSon) {
		this.siteSon = siteSon;
	}
	public void setSiteParent(String siteParent) {
		this.siteParent = siteParent;
	}
	public void setPeriodBeginTime(Date periodBeginTime) {
		this.periodBeginTime = periodBeginTime;
	}
	public void setPeriodEndTime(Date periodEndTime) {
		this.periodEndTime = periodEndTime;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
	public Integer getLineType() {
		return lineType;
	}
	public void setLineType(Integer lineType) {
		this.lineType = lineType;
	}
}
