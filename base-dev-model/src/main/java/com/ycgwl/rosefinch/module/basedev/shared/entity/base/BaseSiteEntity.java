package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

import com.ycgwl.framework.springmvc.entity.BizBaseEntity;

@Table(value = "T_BASE_SITE")
public class BaseSiteEntity extends BizBaseEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2919541802078591084L;
	
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// 主键
	@Id
	@Column("SITE_ID")
	private Long siteId;
	
	
	private String hiddenId;
	
	
	//-------------来源于BaseOrgEntity--------------
	// 网点编号
	@Column(value="SITE_CODE",like=true)
	private String siteCode;
	// 网点名称
	@Column(value="SITE_NAME",like=true)
	private String siteName;
	// 网点简称
	@Column(value="SITE_NAME_SHORT",like=true)
	private String siteNameShort;
	// 排序使用
	@Column("ORDER_BY")
	private Integer orderBy;
	// 所属网点（即上级组织）
	@Column("UP_SITE")
	private String upSite;
	
	// 上级部门名称
	private String upSiteName;
	
	// 本币币别
	@Column("DEFAULT_CURRENCY")
	private Integer defaultCurrency;
	
	// 本位币币别名称
	private String defaultCurrencyName;
	
	
	// 所属国家
	@Column("COUNTRY")
	private String country;
	private String countryName;
	
	
	// 所属省份
	@Column("PROVINCE")
	private String province;
	private String provinceName;
	
	
	// 城市 网点所在城市
	@Column("CITY")
	private String city;
	private String cityName;
	
	
	// 所属区县
	@Column("COUNTY")
	private String county;
	private String countyName;
	
	
	// 所属大区
	@Column("BIG_AREA")
	private String bigArea;
	// 所属区域
	@Column("AREA_CODE")
	private String areaCode;
	private String areaName;
	
	
	// 网点地址
	@Column("SITE_ADDRESS")
	private String siteAddress;
	//------------------------------------
	
	// 所属财务中心
	@Column("UP_FINANCE_CENTER")
	private String upFinanceCenter;
	// 所属财务中心名称
	private String upFinanceCenterName;
	
	// 账单财务中心
	@Column("UP_SETTLE_CENTER")
	private String upSettleCenter;
	// 账单财务中心名称
	private String upSettleCenterName;
	
	// 网点类型
	@Column("SITE_TYPE")
	private Integer siteType;

	// 网点类型名称
	private String siteTypeName;

	// 网点属性
	@Column("SITE_KIND")
	private Integer siteKind;

	// 网点属性名称
	private String siteKindName;

	// 允许到付 0-不允许 1-允许
	@Column("BL_ALLOW_TOPAYMENT")
	private Integer blAllowTopayment;
	// 0：无服务 1：寄件服务 2：派件服务 3：寄派服务
	@Column("SITE_SERVICES_TYPE")
	private Integer siteServicesType;

	// 业务类型名称
	private String siteServicesTypeName;

	// 允许代收货款 0-不允许 1-允许
	@Column("BL_ALLOW_AGENT_MONEY")
	private Integer blAllowAgentMoney;
	// 是否启用物料限制
	@Column("BL_MATERIAL")
	private Integer blMaterial;
	// 预付款限制
	@Column("BL_PRE_LIMIT")
	private Integer blPreLimit;
	// 是否大客户拓展中心
	@Column("BL_BIG_CUSTOMER")
	private Integer blBigCustomer;
	// 代收货款限制金额
	@Column("GOODS_PAYMENT_LIMITED")
	private BigDecimal goodsPaymentLimited;
	// 定时达等业务类型权限
	@Column("BL_SERVICMODE")
	private Integer blServicmode;
	// WEB显示
	@Column("BL_WEB")
	private Integer blWeb;
	// 加盟时间
	@Column("JOIN_TIME")
	private Date joinTime;
	
	private String joinTimeStr;
	
	
	// 代签货单
	@Column("BL_ALLOW_AGENT_SIGN")
	private Integer blAllowAgentSign;
	// 全境承诺标识
	@Column("BL_ALL_PROMISE")
	private Integer blAllPromise;
	// 寄件转运中心
	@Column("SENDPIECE_TRANCENTER")
	private String sendpieceTrancenter;
	
	// 寄件转运中心 名称
	private String sendpieceTrancenterName;
	
	
	// 到件转运中心
	@Column("ARRIVEPIECE_TRANCENTER")
	private String arrivepieceTrancenter;
	
	// 到件转运中心 名称
	private String arrivepieceTrancenterName;
	
	// 仲裁部门
	@Column("BL_ARBITRATION_DEPARTMENT")
	private Integer blArbitrationDepartment;
	// 能否发送短信1：表示可以
	@Column("BL_MESSAGE")
    private Integer blMessage;
	// 发送标示 0未同步  1同步
	@Column("FLAG")
    private Integer flag;
	// 是否揽收
	@Column("BL_REC")
    private Integer blRec;
	// 是否派送
	@Column("BL_SEND")
    private Integer blSend;
	// 是否到件
	@Column("BL_COME")
    private Integer blCome;
	// 自提权限(1表示开通)
	@Column("CANSELFGET")
    private Integer canselfget;
	// 允许为交接清单下一站
	@Column("BL_PDA_TWO")
    private Integer blPdaTwo;
	
	// 是否启用
	@Column("BL_FLAG")
	private Integer blFlag;
	
	// 
	private String hiddenModifyTime;
	
	// 门店明细
	private BaseSiteDetailEntity baseSiteDetailEntity;
	
	
	public Long getSiteId() {
		return siteId;
	}
	public String getSiteCode() {
		return siteCode;
	}
	public String getSiteName() {
		return siteName;
	}
	public String getSiteNameShort() {
		return siteNameShort;
	}
	public Integer getOrderBy() {
		return orderBy;
	}
	public String getUpSite() {
		return upSite;
	}
	public String getUpFinanceCenter() {
		return upFinanceCenter;
	}
	public String getUpSettleCenter() {
		return upSettleCenter;
	}
	public String getUpFinanceCenterName() {
		return upFinanceCenterName;
	}
	public Integer getSiteType() {
		return siteType;
	}
	public String getSiteTypeName() {
		return siteTypeName;
	}
	public Integer getSiteKind() {
		return siteKind;
	}
	public String getSiteKindName() {
		return siteKindName;
	}
	public String getCountry() {
		return country;
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public String getCounty() {
		return county;
	}
	public String getBigArea() {
		return bigArea;
	}
	public String getSiteAddress() {
		return siteAddress;
	}
	public Integer getBlAllowTopayment() {
		return blAllowTopayment;
	}
	public Integer getSiteServicesType() {
		return siteServicesType;
	}
	public String getSiteServicesTypeName() {
		return siteServicesTypeName;
	}
	public Integer getBlAllowAgentMoney() {
		return blAllowAgentMoney;
	}
	public Integer getBlMaterial() {
		return blMaterial;
	}
	public Integer getBlPreLimit() {
		return blPreLimit;
	}
	public Integer getBlBigCustomer() {
		return blBigCustomer;
	}
	public BigDecimal getGoodsPaymentLimited() {
		return goodsPaymentLimited;
	}
	public Integer getBlServicmode() {
		return blServicmode;
	}
	public Integer getBlWeb() {
		return blWeb;
	}
	public Date getJoinTime() {
		return joinTime;
	}
	public Integer getBlAllowAgentSign() {
		return blAllowAgentSign;
	}
	public Integer getBlAllPromise() {
		return blAllPromise;
	}
	public String getSendpieceTrancenter() {
		return sendpieceTrancenter;
	}
	public String getArrivepieceTrancenter() {
		return arrivepieceTrancenter;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public void setSiteNameShort(String siteNameShort) {
		this.siteNameShort = siteNameShort;
	}
	public void setOrderBy(Integer orderBy) {
		this.orderBy = orderBy;
	}
	public void setUpSite(String upSite) {
		this.upSite = upSite;
	}
	public void setUpFinanceCenter(String upFinanceCenter) {
		this.upFinanceCenter = upFinanceCenter;
	}
	public void setUpSettleCenter(String upSettleCenter) {
		this.upSettleCenter = upSettleCenter;
	}
	public void setUpFinanceCenterName(String upFinanceCenterName) {
		this.upFinanceCenterName = upFinanceCenterName;
	}
	public void setSiteType(Integer siteType) {
		this.siteType = siteType;
	}
	public void setSiteTypeName(String siteTypeName) {
		this.siteTypeName = siteTypeName;
	}
	public void setSiteKind(Integer siteKind) {
		this.siteKind = siteKind;
	}
	public void setSiteKindName(String siteKindName) {
		this.siteKindName = siteKindName;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public void setBigArea(String bigArea) {
		this.bigArea = bigArea;
	}
	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}
	public void setBlAllowTopayment(Integer blAllowTopayment) {
		this.blAllowTopayment = blAllowTopayment;
	}
	public void setSiteServicesType(Integer siteServicesType) {
		this.siteServicesType = siteServicesType;
	}
	public void setSiteServicesTypeName(String siteServicesTypeName) {
		this.siteServicesTypeName = siteServicesTypeName;
	}
	public void setBlAllowAgentMoney(Integer blAllowAgentMoney) {
		this.blAllowAgentMoney = blAllowAgentMoney;
	}
	public void setBlMaterial(Integer blMaterial) {
		this.blMaterial = blMaterial;
	}
	public void setBlPreLimit(Integer blPreLimit) {
		this.blPreLimit = blPreLimit;
	}
	public void setBlBigCustomer(Integer blBigCustomer) {
		this.blBigCustomer = blBigCustomer;
	}
	public void setGoodsPaymentLimited(BigDecimal goodsPaymentLimited) {
		this.goodsPaymentLimited = goodsPaymentLimited;
	}
	public void setBlServicmode(Integer blServicmode) {
		this.blServicmode = blServicmode;
	}
	public void setBlWeb(Integer blWeb) {
		this.blWeb = blWeb;
	}
	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}
	public void setBlAllowAgentSign(Integer blAllowAgentSign) {
		this.blAllowAgentSign = blAllowAgentSign;
	}
	public void setBlAllPromise(Integer blAllPromise) {
		this.blAllPromise = blAllPromise;
	}
	public void setSendpieceTrancenter(String sendpieceTrancenter) {
		this.sendpieceTrancenter = sendpieceTrancenter;
	}
	public void setArrivepieceTrancenter(String arrivepieceTrancenter) {
		this.arrivepieceTrancenter = arrivepieceTrancenter;
	}
	public String getUpSettleCenterName() {
		return upSettleCenterName;
	}
	public void setUpSettleCenterName(String upSettleCenterName) {
		this.upSettleCenterName = upSettleCenterName;
	}
	public BaseSiteDetailEntity getBaseSiteDetailEntity() {
		return baseSiteDetailEntity;
	}
	public void setBaseSiteDetailEntity(BaseSiteDetailEntity baseSiteDetailEntity) {
		this.baseSiteDetailEntity = baseSiteDetailEntity;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public Integer getBlArbitrationDepartment() {
		return blArbitrationDepartment;
	}
	public Integer getBlMessage() {
		return blMessage;
	}
	public Integer getFlag() {
		return flag;
	}
	public Integer getBlRec() {
		return blRec;
	}
	public Integer getBlSend() {
		return blSend;
	}
	public Integer getBlCome() {
		return blCome;
	}
	public Integer getCanselfget() {
		return canselfget;
	}
	public Integer getBlPdaTwo() {
		return blPdaTwo;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public void setBlArbitrationDepartment(Integer blArbitrationDepartment) {
		this.blArbitrationDepartment = blArbitrationDepartment;
	}
	public void setBlMessage(Integer blMessage) {
		this.blMessage = blMessage;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public void setBlRec(Integer blRec) {
		this.blRec = blRec;
	}
	public void setBlSend(Integer blSend) {
		this.blSend = blSend;
	}
	public void setBlCome(Integer blCome) {
		this.blCome = blCome;
	}
	public void setCanselfget(Integer canselfget) {
		this.canselfget = canselfget;
	}
	public void setBlPdaTwo(Integer blPdaTwo) {
		this.blPdaTwo = blPdaTwo;
	}
	public Integer getDefaultCurrency() {
		return defaultCurrency;
	}
	public void setDefaultCurrency(Integer defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}
	public String getUpSiteName() {
		return upSiteName;
	}
	public void setUpSiteName(String upSiteName) {
		this.upSiteName = upSiteName;
	}
	public String getDefaultCurrencyName() {
		return defaultCurrencyName;
	}
	public String getCountryName() {
		return countryName;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public String getCountyName() {
		return countyName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setDefaultCurrencyName(String defaultCurrencyName) {
		this.defaultCurrencyName = defaultCurrencyName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public String getHiddenId() {
		if (StringUtils.isNotBlank(this.hiddenId)) {
			return this.hiddenId;
		}
		String idStr = null == siteId ? null : this.siteId.toString();
		this.setHiddenId(idStr);
		return idStr;
	}
	public void setHiddenId(String hiddenId) {
		this.hiddenId = hiddenId;
	}
	public String getHiddenModifyTime() {
		Date md = getModifyTime();
		if (null == md) {
			return this.hiddenModifyTime;
		}
		
		return String.valueOf(md.getTime());
	}
	public void setHiddenModifyTime(String hiddenModifyTime) {
		this.hiddenModifyTime = hiddenModifyTime;
	}
	
	public String getJoinTimeStr() {
		if (null != this.joinTime) {
			this.joinTimeStr = FORMAT.format(this.joinTime);
		}
		return this.joinTimeStr;
	}
	
	public void setJoinTimeStr(String joinTimeStr) {
		this.joinTimeStr = joinTimeStr;
	}
	public String getSendpieceTrancenterName() {
		return sendpieceTrancenterName;
	}
	public String getArrivepieceTrancenterName() {
		return arrivepieceTrancenterName;
	}
	public void setSendpieceTrancenterName(String sendpieceTrancenterName) {
		this.sendpieceTrancenterName = sendpieceTrancenterName;
	}
	public void setArrivepieceTrancenterName(String arrivepieceTrancenterName) {
		this.arrivepieceTrancenterName = arrivepieceTrancenterName;
	}
	public Integer getBlFlag() {
		return blFlag;
	}
	public void setBlFlag(Integer blFlag) {
		this.blFlag = blFlag;
	}
	
}
