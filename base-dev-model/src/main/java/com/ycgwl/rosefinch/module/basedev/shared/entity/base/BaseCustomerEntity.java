package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;
import java.math.BigDecimal;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

import com.ycgwl.framework.springmvc.entity.BizBaseEntity;

/**
 *
 * Title:
 * Description:客户资料实体
 * @Company: 远成快运
 * @author qiaokangjun
 * @date 2016年10月25日  下午2:06:47
 *
 */
@Table(value="T_BASE_CUSTOMER")
public class BaseCustomerEntity extends BizBaseEntity implements Serializable{

		/**
     *
     */
    private static final long serialVersionUID = -982603848933239704L;
        // 客户id
		@Id
		@Column("CUSTOMER_ID")
		private Long customerId;
		//客户编号
		@Column("CUSTOMER_CODE")
		private String customerCode;
		//客户名称
		@Column("CUSTOMER_NAME")
		private String customerName;
		//客户全称
		@Column("CUSTOMER_FULL_NAME")
		private String customerFullName;

		//客户结算类型 现金-支票/月结
		@Column("CUSTOMER_BALANCE_MODE")
		private Integer customerBalanceMode;

		//客户联系人
		@Column("CUSTOMER_LINKMAN")
		private String customerLinkman;

		//客户电话
		@Column("CUSTOMER_PHONE")
		private String customerPhone;

		//客户传真
		@Column("CUSTOMER_FAX")
		private String customerFax;
		//客户地址
		@Column("CUSTOMER_ADDRESS")
		private String customerAddress;
		//客户寄件地
		@Column("CUS_SEND_ADDRESS")
		private String cusSendAddress;
		//客户所属网点
		@Column("OWNER_SITE_CODE")
		private String ownerSiteCode;
		//客户折扣
		@Column("CUSTOMER_REBATE")
		private Double customerRebate;
		//客户密码
		@Column("BALANCE_PASSWORD")
		private String balancePassword;
		//客户/网点/转运代理
		@Column("CUSTOMER_TYPE")
		private Integer customerType;
		//业务员
		@Column("OPERATION_EMPLOYEE")
		private String operationEmployee;
		//业务员编号
		@Column("EMPLOYEE_CODE")
		private String employeeCode;
		//邮政编号
		@Column("POST_CODE")
		private String postCode;
		//所属国家
		@Column("COUNTRY")
		private String country;
		//所属省份
		@Column("PROVINCE")
		private String province;
		//城市 网点所在城市
		@Column("CITY")
		private String city;
		//所属区县
		@Column("COUNTY")
		private String county;
		//网址
		@Column("URL")
		private String url;
		//E-Mail
		@Column("EMAIL")
		private String email;
		//开户行
		@Column("BANK")
		private String bank;
		//开户帐号
		@Column("ACCOUNT")
		private String account;
		//开户人
		@Column("HOLDER")
		private String holder;
		//身份证号码
		@Column("ID_CODE")
		private String idCode;
		//开户行地址
		@Column("BANK_ADDRESS")
		private String bankAddress;
		//市场专员
		@Column("MARKET_MAN")
		private String marketMan;
		//客户分类,短信发送界面树形显示
		@Column("CUSTOMER_SITE_TYPE")
		private String customerSiteType;
		//固定电话
		@Column("CUSTOMER_PHONE1")
		private String customerPhone1;
		//客户所属大区
		@Column("CUSTOMER_RANG")
		private String customerRang;
		//客户行业
		@Column("CUSTOMER_OWNER_TYPE")
		private String customerOwnerType;
		//公司地址
		@Column("COMPANY_ADDRESS")
		private String companyAddress;
		//公司名称
		@Column("COMPANY_NAME")
		private String companyName;
		//会员渠道
		@Column("MEMBER_CHANNELS")
		private String memberChannels;
		//VIP会员编号
		@Column("VIP_MEMBER_ID")
		private String vipMemberId;
		//费用合计
		@Column("TOTA_COST")
		private BigDecimal totaCost;
		//启用标识
		@Column("BL_OPEN")
		private Integer blOpen;
		//行业总类
		@Column("INDUSTRY_CLASS")
		private String industryClass;
		//行业细分
		@Column("INDUSTRY_DETAILS")
		private String industryDetails;
		//是否全网大客户
		@Column("BL_BAG_CUSTOMER")
		private Integer blBagCustomer;
		//REBATE_NUMBER
		@Column("REBATE_NUMBER")
		private Integer rebateNumber;
		//折扣率启用标识
		@Column("BL_REBATE_NUMBER")
		private Integer blRebateNumber;
		//客户预付款是否开启
		@Column("BL_MONEY_OPEN")
		private Integer blMoneyOpen;
		//月结客户前端只能选现付和到付
		@Column("CASH_CUSTOMER")
		private Integer cashCustomer;
		//备注
		@Column("REMARK")
		private String remark;
		public Long getCustomerId() {
			return customerId;
		}
		public void setCustomerId(Long customerId) {
			this.customerId = customerId;
		}
		public String getCustomerCode() {
			return customerCode;
		}
		public void setCustomerCode(String customerCode) {
			this.customerCode = customerCode;
		}
		public String getCustomerName() {
			return customerName;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public String getCustomerFullName() {
			return customerFullName;
		}
		public void setCustomerFullName(String customerFullName) {
			this.customerFullName = customerFullName;
		}
		public Integer getCustomerBalanceMode() {
			return customerBalanceMode;
		}
		public void setCustomerBalanceMode(Integer customerBalanceMode) {
			this.customerBalanceMode = customerBalanceMode;
		}
		public String getCustomerLinkman() {
			return customerLinkman;
		}
		public void setCustomerLinkman(String customerLinkman) {
			this.customerLinkman = customerLinkman;
		}
		public String getCustomerPhone() {
			return customerPhone;
		}
		public void setCustomerPhone(String customerPhone) {
			this.customerPhone = customerPhone;
		}
		public String getCustomerFax() {
			return customerFax;
		}
		public void setCustomerFax(String customerFax) {
			this.customerFax = customerFax;
		}
		public String getCustomerAddress() {
			return customerAddress;
		}
		public void setCustomerAddress(String customerAddress) {
			this.customerAddress = customerAddress;
		}
		public String getCusSendAddress() {
			return cusSendAddress;
		}
		public void setCusSendAddress(String cusSendAddress) {
			this.cusSendAddress = cusSendAddress;
		}
		public String getOwnerSiteCode() {
			return ownerSiteCode;
		}
		public void setOwnerSiteCode(String ownerSiteCode) {
			this.ownerSiteCode = ownerSiteCode;
		}
		public Double getCustomerRebate() {
			return customerRebate;
		}
		public void setCustomerRebate(Double customerRebate) {
			this.customerRebate = customerRebate;
		}
		public String getBalancePassword() {
			return balancePassword;
		}
		public void setBalancePassword(String balancePassword) {
			this.balancePassword = balancePassword;
		}
		public Integer getCustomerType() {
			return customerType;
		}
		public void setCustomerType(Integer customerType) {
			this.customerType = customerType;
		}
		public String getOperationEmployee() {
			return operationEmployee;
		}
		public void setOperationEmployee(String operationEmployee) {
			this.operationEmployee = operationEmployee;
		}
		public String getEmployeeCode() {
			return employeeCode;
		}
		public void setEmployeeCode(String employeeCode) {
			this.employeeCode = employeeCode;
		}
		public String getPostCode() {
			return postCode;
		}
		public void setPostCode(String postCode) {
			this.postCode = postCode;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getCounty() {
			return county;
		}
		public void setCounty(String county) {
			this.county = county;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getBank() {
			return bank;
		}
		public void setBank(String bank) {
			this.bank = bank;
		}
		public String getAccount() {
			return account;
		}
		public void setAccount(String account) {
			this.account = account;
		}
		public String getHolder() {
			return holder;
		}
		public void setHolder(String holder) {
			this.holder = holder;
		}
		public String getIdCode() {
			return idCode;
		}
		public void setIdCode(String idCode) {
			this.idCode = idCode;
		}
		public String getBankAddress() {
			return bankAddress;
		}
		public void setBankAddress(String bankAddress) {
			this.bankAddress = bankAddress;
		}
		public String getMarketMan() {
			return marketMan;
		}
		public void setMarketMan(String marketMan) {
			this.marketMan = marketMan;
		}
		public String getCustomerSiteType() {
			return customerSiteType;
		}
		public void setCustomerSiteType(String customerSiteType) {
			this.customerSiteType = customerSiteType;
		}
		public String getCustomerPhone1() {
			return customerPhone1;
		}
		public void setCustomerPhone1(String customerPhone1) {
			this.customerPhone1 = customerPhone1;
		}
		public String getCustomerRang() {
			return customerRang;
		}
		public void setCustomerRang(String customerRang) {
			this.customerRang = customerRang;
		}
		public String getCustomerOwnerType() {
			return customerOwnerType;
		}
		public void setCustomerOwnerType(String customerOwnerType) {
			this.customerOwnerType = customerOwnerType;
		}
		public String getCompanyAddress() {
			return companyAddress;
		}
		public void setCompanyAddress(String companyAddress) {
			this.companyAddress = companyAddress;
		}
		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public String getMemberChannels() {
			return memberChannels;
		}
		public void setMemberChannels(String memberChannels) {
			this.memberChannels = memberChannels;
		}
		public String getVipMemberId() {
			return vipMemberId;
		}
		public void setVipMemberId(String vipMemberId) {
			this.vipMemberId = vipMemberId;
		}
		public BigDecimal getTotaCost() {
			return totaCost;
		}
		public void setTotaCost(BigDecimal totaCost) {
			this.totaCost = totaCost;
		}
		public Integer getBlOpen() {
			return blOpen;
		}
		public void setBlOpen(Integer blOpen) {
			this.blOpen = blOpen;
		}
		public String getIndustryClass() {
			return industryClass;
		}
		public void setIndustryClass(String industryClass) {
			this.industryClass = industryClass;
		}
		public String getIndustryDetails() {
			return industryDetails;
		}
		public void setIndustryDetails(String industryDetails) {
			this.industryDetails = industryDetails;
		}
		public Integer getBlBagCustomer() {
			return blBagCustomer;
		}
		public void setBlBagCustomer(Integer blBagCustomer) {
			this.blBagCustomer = blBagCustomer;
		}
		public Integer getRebateNumber() {
			return rebateNumber;
		}
		public void setRebateNumber(Integer rebateNumber) {
			this.rebateNumber = rebateNumber;
		}
		public Integer getBlRebateNumber() {
			return blRebateNumber;
		}
		public void setBlRebateNumber(Integer blRebateNumber) {
			this.blRebateNumber = blRebateNumber;
		}
		public Integer getBlMoneyOpen() {
			return blMoneyOpen;
		}
		public void setBlMoneyOpen(Integer blMoneyOpen) {
			this.blMoneyOpen = blMoneyOpen;
		}
		public Integer getCashCustomer() {
			return cashCustomer;
		}
		public void setCashCustomer(Integer cashCustomer) {
			this.cashCustomer = cashCustomer;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}






























}
