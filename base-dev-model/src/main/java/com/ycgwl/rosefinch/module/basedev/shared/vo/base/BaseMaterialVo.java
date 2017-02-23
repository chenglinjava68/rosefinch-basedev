package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @Title:物料管理Vo
 * @Description:
 * @Company: 远成快运
 * @author HXL
 * @date 2016年11月9日 上午11:11:01
 */
public class BaseMaterialVo implements Serializable{

	private static final long serialVersionUID = 1938302373627934703L;
	//主键
	private Long id;
	//物料编码
	private String goodsCode;
	//物料名称
	private String goodsName;
	//物料类别
	private Integer category ;
	//单位数目
	private Long unitNumber;
	//单位名称
	private String unitName;
	//单价
	private BigDecimal unitMoney;
	//是否预付款
	private BigDecimal blBalance;
	//备注
	private String remark;
	//创建日期
	private Date createTime;
	//修改日期
	private Date modifyTime;
	//创建人编码
	private String createUserCode;
	//修改人编码
	private String modifyUserCode;
	//删除标识
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
