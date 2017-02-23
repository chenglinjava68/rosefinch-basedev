package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @Title:揽件时效对应的VO
 * @Description:
 * @Company: 远成快运
 * @author caijue
 * @date 2016年11月1日  下午4:32
 *
 */
public class GetPackageTimeVo implements Serializable{

    private static final long serialVersionUID = -7990381574912720164L;

    private Long configId;

    private String configName;

    private String configCode;

    //下单时间1开始时间
    private String orderTime1Start;

    //下单时间1截止时间
    private String orderTime1End;

    //合格揽件时间1
    private Integer qualifiedGetPackageTime1;

    //下单时间2开始时间
    private String orderTime2Start;

    //下单时间2截止时间
    private String orderTime2End;

    //合格揽件时间2
    private String qualifiedGetPackageTime2;

    //是否启用
    private Integer blFlag;

    //创建人
    private String createUserCode;

    //创建时间
    private Date createTime;

    //修改人
    private String modifyUserCode;

    //修改时间
    private Date modifyTime;

    //备注
    private String remark;

    //删除标识 0-正常  1-删除
    private Integer delFlag;

    private List<Long> idList;

    private List<GetPackageTimeReleVo> siteCodeList;

    private String siteNames;

    public String getSiteNames() {
        return siteNames;
    }

    public void setSiteNames(String siteNames) {
        this.siteNames = siteNames;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

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

    public Integer getQualifiedGetPackageTime1() {
        return qualifiedGetPackageTime1;
    }

    public void setQualifiedGetPackageTime1(Integer qualifiedGetPackageTime1) {
        this.qualifiedGetPackageTime1 = qualifiedGetPackageTime1;
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

    public List<GetPackageTimeReleVo> getSiteCodeList() {
        return siteCodeList;
    }

    public void setSiteCodeList(List<GetPackageTimeReleVo> siteCodeList) {
        this.siteCodeList = siteCodeList;
    }

}
