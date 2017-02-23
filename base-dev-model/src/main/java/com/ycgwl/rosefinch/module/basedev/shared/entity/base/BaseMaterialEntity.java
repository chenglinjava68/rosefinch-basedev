package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;
@Table(value="T_MATERIAL_ITEM")
public class BaseMaterialEntity implements Serializable{

	private static final long serialVersionUID = 8801661567084104859L;

	//主键
	@Id
	@Column("ID")
	private Long id;
	//物料编码
	@Column(value = "GOODS_CODE" ,like=true)
	private String goodsCode;
	//物料名称
	@Column(value = "GOODS_NAME",like=true)
	private String goodsName;
	//物料类别
	@Column("CATEGORY")
	private Integer category ;
	//物料类别名称
	private String categoryName;
	//单位数目
	@Column("UNIT_NUMBER")
	private Long unitNumber;
	//单位名称
	@Column("UNIT_NAME")
	private String unitName;
	//单价
	@Column("UNIT_MONEY")
	private BigDecimal unitMoney;
	//是否预付款
	@Column("BL_BALANCE")
	private BigDecimal blBalance;
	//备注
	@Column("REMARK")
	private String remark;
	//创建日期
	@Column("CREATE_TIME")
	private Date createTime;
	//修改日期
	@Column("MODIFY_TIME")
	private Date modifyTime;
	//创建人编码
	@Column("CREATE_USER_CODE")
	private String createUserCode;
	//修改人编码
	@Column("MODIFY_USER_CODE")
	private String modifyUserCode;
	//删除标识
	@Column("DEL_FLAG")
	private Integer delFlag;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Long getUnitNumber() {
		return unitNumber;
	}
	public void setUnitNumber(Long unitNumber) {
		this.unitNumber = unitNumber;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public BigDecimal getUnitMoney() {
		return unitMoney;
	}
	public void setUnitMoney(BigDecimal unitMoney) {
		this.unitMoney = unitMoney;
	}
	public BigDecimal getBlBalance() {
		return blBalance;
	}
	public void setBlBalance(BigDecimal blBalance) {
		this.blBalance = blBalance;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getCreateUserCode() {
		return createUserCode;
	}
	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
	public String getModifyUserCode() {
		return modifyUserCode;
	}
	public void setModifyUserCode(String modifyUserCode) {
		this.modifyUserCode = modifyUserCode;
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

}
