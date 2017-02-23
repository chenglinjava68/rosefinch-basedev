package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @Title:路由维护VO
 * @Description:
 * @Company: 远成快运
 * @author caijue
 * @date 2016年11月28日 下午3:23
 *
 */
public class BaseRouteVo implements Serializable {

    private static final long serialVersionUID = 8663562110226675570L;

    private Long id;

    private String routeCode;//路由编号

    private String routeName;//路由名称

    private String productCode;//产品类型编码

    private String beginSiteCode;//起始网点

    private String endSiteCode;//目的网点

    private String beginRegionCode;//起始行政区域编码

    private String endRegionCode;//到达行政区域编码

    private String ownerOrg;//路由所属机构

    private Long totalStayTime;//总停留时间

    private Double totalMileage;//总路程

    private Integer siteCount;//途径站总数

    private Long totalTime;//总时间

    private String createUserCode;//创建人

    private Date createTime;//创建时间

    private String modifyUserCode;//修改人

    private Date modifyTime;//修改时间

    private String remark;//备注

    private Integer delFlag;//是否删除0正常,1删除

    private Integer blFlag;//启用标识

    private List<Long> idList;

    //路由明细记录
    private List<BaseRouteDetailVo> detailList;

    public List<BaseRouteDetailVo> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<BaseRouteDetailVo> detailList) {
        this.detailList = detailList;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }


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

    public String getRouteCode() {
        return routeCode;
    }

    public void setRouteCode(String routeCode) {
        this.routeCode = routeCode;
    }

}
