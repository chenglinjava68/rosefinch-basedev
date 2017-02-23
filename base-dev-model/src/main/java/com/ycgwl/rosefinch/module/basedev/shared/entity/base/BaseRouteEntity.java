package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;
import java.util.Date;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

/**
 *
 * @Title:路由维护
 * @Description:
 * @Company: 远成快运
 * @author caijue
 * @date 2016年11月28日 下午11:23
 *
 */
@Table(value = "T_BASE_ROUTE", dynamicInsert = true, dynamicUpdate = true)
public class BaseRouteEntity implements Serializable {

    private static final long serialVersionUID = -6160693011031180901L;

    @Id
    @Column("ID")
    private Long id;

    @Column(value = "ROUTE_CODE")
    private String routeCode;//路由编号

    @Column(value = "ROUTE_NAME", like = true)
    private String routeName;//路由名称

    @Column("PRODUCT_CODE")
    private String productCode;//产品类型编码

    @Column("BEGIN_SITE_CODE")
    private String beginSiteCode;//起始网点

    @Column("END_SITE_CODE")
    private String endSiteCode;//目的网点

    @Column("BEGIN_REGION_CODE")
    private String beginRegionCode;//起始行政区域编码

    @Column("END_REGION_CODE")
    private String endRegionCode;//到达行政区域编码

    @Column("OWNER_ORG")
    private String ownerOrg;//路由所属机构

    @Column("TOTAL_STAY_TIME")
    private Long totalStayTime;//总停留时间

    @Column("TOTAL_MILEAGE")
    private Double totalMileage;//总路程

    @Column("SITE_COUNT")
    private Integer siteCount;//途径站总数

    @Column("TOTAL_TIME")
    private Long totalTime;//总时间

    @Column("CREATE_USER_CODE")
    private String createUserCode;//创建人

    @Column("CREATE_TIME")
    private Date createTime;//创建时间

    @Column("MODIFY_USER_CODE")
    private String modifyUserCode;//修改人

    @Column("MODIFY_TIME")
    private Date modifyTime;//修改时间

    @Column("REMARK")
    private String remark;//备注

    @Column("DEL_FLAG")
    private Integer delFlag;//是否删除0正常,1删除

    @Column("BL_FLAG")
    private Integer blFlag;//启用标识

    private String productName;//产品名称

    private String beginSiteName;//起始网点名称

    private String endSiteName;//目的网点名称

    private String beginRegionName;//起始行政区域名称
    private String beginRegionCodeStr;

    private String endRegionName;//到达行政区域名称
    private String endRegionCodeStr;

    private String ownerOrgName;//路由所属机构名称

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getBeginSiteCode() {
        return beginSiteCode;
    }

    public void setBeginSiteCode(String beginSiteCode) {
        this.beginSiteCode = beginSiteCode;
    }

    public String getEndSiteCode() {
        return endSiteCode;
    }

    public void setEndSiteCode(String endSiteCode) {
        this.endSiteCode = endSiteCode;
    }

    public String getBeginRegionCode() {
        return beginRegionCode;
    }

    public void setBeginRegionCode(String beginRegionCode) {
        this.beginRegionCode = beginRegionCode;
    }

    public String getEndRegionCode() {
        return endRegionCode;
    }

    public void setEndRegionCode(String endRegionCode) {
        this.endRegionCode = endRegionCode;
    }

    public String getOwnerOrg() {
        return ownerOrg;
    }

    public void setOwnerOrg(String ownerOrg) {
        this.ownerOrg = ownerOrg;
    }

    public Long getTotalStayTime() {
        return totalStayTime;
    }

    public void setTotalStayTime(Long totalStayTime) {
        this.totalStayTime = totalStayTime;
    }

    public Double getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(Double totalMileage) {
        this.totalMileage = totalMileage;
    }

    public Integer getSiteCount() {
        return siteCount;
    }

    public void setSiteCount(Integer siteCount) {
        this.siteCount = siteCount;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBeginSiteName() {
        return beginSiteName;
    }

    public void setBeginSiteName(String beginSiteName) {
        this.beginSiteName = beginSiteName;
    }

    public String getEndSiteName() {
        return endSiteName;
    }

    public void setEndSiteName(String endSiteName) {
        this.endSiteName = endSiteName;
    }

    public String getBeginRegionName() {
        return beginRegionName;
    }

    public void setBeginRegionName(String beginRegionName) {
        this.beginRegionName = beginRegionName;
    }

    public String getEndRegionName() {
        return endRegionName;
    }

    public void setEndRegionName(String endRegionName) {
        this.endRegionName = endRegionName;
    }

    public String getOwnerOrgName() {
        return ownerOrgName;
    }

    public void setOwnerOrgName(String ownerOrgName) {
        this.ownerOrgName = ownerOrgName;
    }

    public String getBeginRegionCodeStr() {
        return beginRegionCodeStr;
    }

    public void setBeginRegionCodeStr(String beginRegionCodeStr) {
        this.beginRegionCodeStr = beginRegionCodeStr;
    }

    public String getEndRegionCodeStr() {
        return endRegionCodeStr;
    }

    public void setEndRegionCodeStr(String endRegionCodeStr) {
        this.endRegionCodeStr = endRegionCodeStr;
    }

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }
}
