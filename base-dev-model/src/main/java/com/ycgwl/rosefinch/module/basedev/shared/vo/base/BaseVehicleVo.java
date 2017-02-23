package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
*
* @Title:车辆管理VO
* @Description:
* @Company: 远成快运
* @author caijue
* @date 2016年11月21日  下午2:57
*
*/

public class BaseVehicleVo implements Serializable{

    private static final long serialVersionUID = -5731624666615157390L;

    private Long id;

//    private String runNo;//班次号

    private String vehicelCode;//车牌编号

    private String vehicelNo;//车牌号

    private Integer ownerType;//所有类型

    private String vehicelType;//车辆类型

    private Integer blTest;//是否考核

    private String ownerOrg;//所属机构

    private String ownerCom;//所属公司

    private Integer vehicelModel;//车型

    private Integer boxType;//车厢类型

    private Double boxSize;//货箱大小

    private Double boxTons;//载重

    private Integer blRun;//是否正常运营

    private String createUserCode;//创建人

    private Date createTime;//创建时间

    private String modifyUserCode;//修改人

    private Date modifyTime;//修改时间

    private String remark;//备注

    private Integer delFlag;//删除标识

    private List<Long> idList;

    private Integer blFlag;//启用标识


//    private String runName;

    /**
     * @return the blFlag
     */
    public Integer getBlFlag() {
        return blFlag;
    }

    /**
     * @param blFlag the blFlag to set
     */
    public void setBlFlag(Integer blFlag) {
        this.blFlag = blFlag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getRunNo() {
//        return runNo;
//    }
//
//    public void setRunNo(String runNo) {
//        this.runNo = runNo;
//    }

    public String getVehicelNo() {
        return vehicelNo;
    }

    public void setVehicelNo(String vehicelNo) {
        this.vehicelNo = vehicelNo;
    }

    public Integer getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(Integer ownerType) {
        this.ownerType = ownerType;
    }

    public String getVehicelType() {
        return vehicelType;
    }

    public void setVehicelType(String vehicelType) {
        this.vehicelType = vehicelType;
    }

    public Integer getBlTest() {
        return blTest;
    }

    public void setBlTest(Integer blTest) {
        this.blTest = blTest;
    }

    public String getOwnerOrg() {
        return ownerOrg;
    }

    public void setOwnerOrg(String ownerOrg) {
        this.ownerOrg = ownerOrg;
    }

    public String getOwnerCom() {
        return ownerCom;
    }

    public void setOwnerCom(String ownerCom) {
        this.ownerCom = ownerCom;
    }

    public Integer getVehicelModel() {
        return vehicelModel;
    }

    public void setVehicelModel(Integer vehicelModel) {
        this.vehicelModel = vehicelModel;
    }

    public Integer getBoxType() {
        return boxType;
    }

    public void setBoxType(Integer boxType) {
        this.boxType = boxType;
    }

    public Double getBoxSize() {
        return boxSize;
    }

    public void setBoxSize(Double boxSize) {
        this.boxSize = boxSize;
    }

    public Double getBoxTons() {
        return boxTons;
    }

    public void setBoxTons(Double boxTons) {
        this.boxTons = boxTons;
    }

    public Integer getBlRun() {
        return blRun;
    }

    public void setBlRun(Integer blRun) {
        this.blRun = blRun;
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

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public String getVehicelCode() {
        return vehicelCode;
    }

    public void setVehicelCode(String vehicelCode) {
        this.vehicelCode = vehicelCode;
    }

//    public String getRunName() {
//        return runName;
//    }
//
//    public void setRunName(String runName) {
//        this.runName = runName;
//    }
}
