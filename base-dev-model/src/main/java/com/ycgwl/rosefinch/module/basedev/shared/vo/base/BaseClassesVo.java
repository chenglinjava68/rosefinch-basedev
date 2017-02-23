package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @Title TODO
 * @Description TODO
 *　@Company　远成快运
 * @author panxiaoling
 * @date 2016年11月28日下午2:01:56
 */
public class BaseClassesVo implements Serializable {
    private static final long serialVersionUID = -5243592957374579598L;
    // 班次ID
    private Long id;
    // 班次名称
    private String classesName;
    // 车线编号
    private String lineCode;
    // 车牌编号
    private String vehicelCode;
    // 车牌号
    private String vehicelNo;
    // 出发时间（hh:mi）
    private String departureTime;
    // 到达时间（hh:mi）
    private String arriveTime;
    // 车型
    private Integer carType;
    // 出发周期：1、周一至周五2、每天3、周六周日
    private Integer cycle;
    // 启用标识
    private Integer blFlag;
    // 创建人
    private String createUserCode;
    // 创建时间
    private Date createTime;
    // 修改人
    private String modifyUserCode;
    // 修改时间
    private Date modifyTime;
    // 备注
    private String remark;
    // 是否删除0正常,1删除
    private Integer delFlag;
    // 路线名字
    private String lineName;
    // 出发周期名字
    private String cycleName;
    // 班次编号
    private String classesCode;
    //明细集合
    private List<BaseClassesDetailVo> classesDetailLists;

    public List<BaseClassesDetailVo> getClassesDetailLists() {
        return classesDetailLists;
    }
    public void setClassesDetailLists(List<BaseClassesDetailVo> classesDetailLists) {
        this.classesDetailLists = classesDetailLists;
    }
    /**
     * @return the vehicelCode
     */
    public String getVehicelCode() {
        return vehicelCode;
    }
    /**
     * @param vehicelCode the vehicelCode to set
     */
    public void setVehicelCode(String vehicelCode) {
        this.vehicelCode = vehicelCode;
    }
    /**
     * @return the classesCode
     */
    public String getClassesCode() {
        return classesCode;
    }
    /**
     * @param classesCode the classesCode to set
     */
    public void setClassesCode(String classesCode) {
        this.classesCode = classesCode;
    }
    public String getCycleName() {
        return cycleName;
    }
    public void setCycleName(String cycleName) {
        this.cycleName = cycleName;
    }
    public String getLineName() {
        return lineName;
    }
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getClassesName() {
        return classesName;
    }
    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }


    /**
     * @return the lineCode
     */
    public String getLineCode() {
        return lineCode;
    }
    /**
     * @param lineCode the lineCode to set
     */
    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }
    /**
     * @return the vehicelNo
     */
    public String getVehicelNo() {
        return vehicelNo;
    }
    /**
     * @param vehicelNo the vehicelNo to set
     */
    public void setVehicelNo(String vehicelNo) {
        this.vehicelNo = vehicelNo;
    }
    public String getDepartureTime() {
        return departureTime;
    }
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
    public String getArriveTime() {
        return arriveTime;
    }
    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }
    public Integer getCarType() {
        return carType;
    }
    public void setCarType(Integer carType) {
        this.carType = carType;
    }
    public Integer getCycle() {
        return cycle;
    }
    public void setCycle(Integer cycle) {
        this.cycle = cycle;
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
    public Integer getDelFlag() {
        return delFlag;
    }
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
