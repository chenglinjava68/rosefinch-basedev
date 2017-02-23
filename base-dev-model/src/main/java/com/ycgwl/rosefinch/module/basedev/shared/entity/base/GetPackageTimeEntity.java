package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;
import java.util.Date;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

/**
 *
 * @Title:T_GET_PACKAGE_TIME配置表Entity实体
 * @Description:
 * @Company: 远成快运
 * @author caijue
 * @date 2016年11月1日  下午4:07
 *
 */
@Table(value="T_GET_PACKAGE_TIME",dynamicInsert=true,dynamicUpdate=true)
public class GetPackageTimeEntity implements Serializable{

    private static final long serialVersionUID = 1071810245420966228L;

    //ID
    @Id
    @Column("ID")
    private Long configId;

    //时效名称
    @Column(value = "CONFIG_NAME", like = true)
    private String configName;

    //时效编码
    @Column("CONFIG_CODE")
    private String configCode;

    //下单时间1开始时间
    @Column("ORDER_TIME1_START")
    private String orderTime1Start;

    //下单时间1截止时间
    @Column("ORDER_TIME1_END")
    private String orderTime1End;

    //合格揽件时间1
    @Column("QUALIFIED_GET_PACKAGE_TIME1")
    private Integer qualifiedGetPackageTime1;

    //下单时间2开始时间
    @Column("ORDER_TIME2_START")
    private String orderTime2Start;

    //下单时间2截止时间
    @Column("ORDER_TIME2_END")
    private String orderTime2End;

    //合格揽件时间2
    @Column("QUALIFIED_GET_PACKAGE_TIME2")
    private String qualifiedGetPackageTime2;

    //是否启用
    @Column("BL_FLAG")
    private Integer blFlag;

    //创建人
    @Column("CREATE_USER_CODE")
    private String createUserCode;

    //创建时间
    @Column("CREATE_TIME")
    private Date createTime;

    //修改人
    @Column("MODIFY_USER_CODE")
    private String modifyUserCode;

    //修改时间
    @Column("MODIFY_TIME")
    private Date modifyTime;

    //备注
    @Column("REMARK")
    private String remark;

    //删除标识 0-正常  1-删除
    @Column("DEL_FLAG")
    private Integer delFlag;

    private String qualifiedGetPackageTime1Name;

    private String siteNames;

//    private List<String> siteCodeList;

    public String getSiteNames() {
        return siteNames;
    }

    public void setSiteNames(String siteNames) {
        this.siteNames = siteNames;
    }

//    public List<String> getSiteCodeList() {
//        return siteCodeList;
//    }
//
//    public void setSiteCodeList(List<String> siteCodeList) {
//        this.siteCodeList = siteCodeList;
//    }

    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(Long configId) {
        this.configId = configId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigCode() {
        return configCode;
    }

    public void setConfigCode(String configCode) {
        this.configCode = configCode;
    }

    public String getOrderTime1Start() {
        return orderTime1Start;
    }

    public void setOrderTime1Start(String orderTime1Start) {
        this.orderTime1Start = orderTime1Start;
    }

    public String getOrderTime1End() {
        return orderTime1End;
    }

    public void setOrderTime1End(String orderTime1End) {
        this.orderTime1End = orderTime1End;
    }

    public Integer getQualifiedGetPackageTime1() {
        return qualifiedGetPackageTime1;
    }

    public void setQualifiedGetPackageTime1(Integer qualifiedGetPackageTime1) {
        this.qualifiedGetPackageTime1 = qualifiedGetPackageTime1;
    }

    public String getOrderTime2Start() {
        return orderTime2Start;
    }

    public void setOrderTime2Start(String orderTime2Start) {
        this.orderTime2Start = orderTime2Start;
    }

    public String getOrderTime2End() {
        return orderTime2End;
    }

    public void setOrderTime2End(String orderTime2End) {
        this.orderTime2End = orderTime2End;
    }

    public String getQualifiedGetPackageTime2() {
        return qualifiedGetPackageTime2;
    }

    public void setQualifiedGetPackageTime2(String qualifiedGetPackageTime2) {
        this.qualifiedGetPackageTime2 = qualifiedGetPackageTime2;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getBlFlag() {
        return blFlag;
    }

    public void setBlFlag(Integer blFlag) {
        this.blFlag = blFlag;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getQualifiedGetPackageTime1Name() {
        return qualifiedGetPackageTime1Name;
    }

    public void setQualifiedGetPackageTime1Name(String qualifiedGetPackageTime1Name) {
        this.qualifiedGetPackageTime1Name = qualifiedGetPackageTime1Name;
    }

}
