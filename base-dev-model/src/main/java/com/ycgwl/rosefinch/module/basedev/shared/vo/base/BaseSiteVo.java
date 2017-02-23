package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;
import java.math.BigDecimal;

public class BaseSiteVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -132741445010718194L;
	
	// 隐藏的主键
	private String hiddenId;
	// 隐藏的修改时间
	private String hiddenModifyTime;
	
    private String siteCode;
    private String siteName;
    private String siteNameShort;
    private Integer orderBy;
    private String upSite;
    private String upFinanceCenter;
    private String upSettleCenter;
    private Integer siteType;
    private Integer siteKind;
    private Integer defaultCurrency;
    private String country;
    private String province;
    private String city;
    private String county;
    private String bigArea;
    private String areaCode;
    private String siteAddress;
    private Integer blAllowTopayment;
    private Integer siteServicesType;
    private Integer blAllowAgentMoney;
    private Integer blMaterial;
    private Integer blPreLimit;
    private Integer blBigCustomer;
    private BigDecimal goodsPaymentLimited;
    private Integer blServicmode;
    private Integer blWeb;
    private String joinTimeStr;
    private Integer blAllowAgentSign;
    private Integer blAllPromise;
    private String sendpieceTrancenter;
    private String arrivepieceTrancenter;
    private Integer blArbitrationDepartment;
    private Integer blMessage;
    private Integer flag;
    private Integer blRec;
    private Integer blSend;
    private Integer blCome;
    private Integer canselfget;
    private Integer blPdaTwo;
    private Integer blFlag;
    
    // 附加信息
    private BaseSiteDetailVo baseSiteDetailVo;
    
	public String getSiteCode() {
		return siteCode;
	}
	public String getSiteName() {
		return siteName;
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
	public Integer getSiteType() {
		return siteType;
	}
	public Integer getSiteKind() {
		return siteKind;
	}
	public Integer getDefaultCurrency() {
		return defaultCurrency;
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
	public String getAreaCode() {
		return areaCode;
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
	public Integer getBlServicmode() {
		return blServicmode;
	}
	public Integer getBlWeb() {
		return blWeb;
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
	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
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
	public void setSiteType(Integer siteType) {
		this.siteType = siteType;
	}
	public void setSiteKind(Integer siteKind) {
		this.siteKind = siteKind;
	}
	public void setDefaultCurrency(Integer defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
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
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
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
	public void setBlServicmode(Integer blServicmode) {
		this.blServicmode = blServicmode;
	}
	public void setBlWeb(Integer blWeb) {
		this.blWeb = blWeb;
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
	public BaseSiteDetailVo getBaseSiteDetailVo() {
		return baseSiteDetailVo;
	}
	public void setBaseSiteDetailVo(BaseSiteDetailVo baseSiteDetailVo) {
		this.baseSiteDetailVo = baseSiteDetailVo;
	}
	public String getSiteNameShort() {
		return siteNameShort;
	}
	public void setSiteNameShort(String siteNameShort) {
		this.siteNameShort = siteNameShort;
	}
	public String getHiddenId() {
		return hiddenId;
	}
	public void setHiddenId(String hiddenId) {
		this.hiddenId = hiddenId;
	}
	public String getHiddenModifyTime() {
		return hiddenModifyTime;
	}
	public void setHiddenModifyTime(String hiddenModifyTime) {
		this.hiddenModifyTime = hiddenModifyTime;
	}
	
	public BigDecimal getGoodsPaymentLimited() {
		return goodsPaymentLimited;
	}
	public void setGoodsPaymentLimited(BigDecimal goodsPaymentLimited) {
		this.goodsPaymentLimited = goodsPaymentLimited;
	}
	public String getJoinTimeStr() {
		return joinTimeStr;
	}
	public void setJoinTimeStr(String joinTimeStr) {
		this.joinTimeStr = joinTimeStr;
	}
	public Integer getBlFlag() {
		return blFlag;
	}
	public void setBlFlag(Integer blFlag) {
		this.blFlag = blFlag;
	}
	
}
