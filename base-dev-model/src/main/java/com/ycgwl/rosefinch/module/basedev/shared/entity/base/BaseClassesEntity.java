package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

import com.ycgwl.rosefinch.module.basedev.shared.vo.base.BaseClassesDetailVo;

/**
 *
 * @Title T_BASE_CLASSES
 * @Description 车次表实体
 *　@Company　远成快运
 * @author panxiaoling
 * @date 2016年11月28日上午11:26:34
 */
@Table(value = "T_BASE_CLASSES", dynamicInsert = true, dynamicUpdate = true)
public class BaseClassesEntity implements Serializable {
    private static final long serialVersionUID = -5243592957374579598L;
    // 班次ID
    @Id
    @Column("ID")
    private Long id;
    // 班次名称
    @Column(value = "CLASSES_NAME", like = true)
    private String classesName;
    // 班次编号
    @Column("CLASSES_CODE")
    private String classesCode;
    // 车线主表ID
    @Column("LINE_CODE")
    private String lineCode;
    // 车辆
    @Column("VEHICEL_CODE")
    private String vehicelCode;
    // 出发时间（hh:mi）
    @Column("DEPARTURE_TIME")
    private String departureTime;
    // 到达时间（hh:mi）
    @Column("ARRIVE_TIME")
    private String arriveTime;
    // 车型
    @Column("CAR_TYPE")
    private Integer carType;
    // 出发周期：1、周一至周五2、每天3、周六周日
    @Column("CYCLE")
    private Integer cycle;
    // 启用标识
    @Column("BL_FLAG")
    private Integer blFlag;
    // 创建人
    @Column("CREATE_USER_CODE")
    private String createUserCode;
    // 创建时间
    @Column("CREATE_TIME")
    private Date createTime;
    // 修改人
    @Column("MODIFY_USER_CODE")
    private String modifyUserCode;
    // 修改时间
    @Column("MODIFY_TIME")
    private Date modifyTime;
    // 备注
    @Column("REMARK")
    private String remark;
    // 是否删除0正常,1删除
    @Column("DEL_FLAG")
    private Integer delFlag;
    //明细集合
    private List<BaseClassesDetailVo> classesDetailLists;
    public List<BaseClassesDetailVo> getClassesDetailLists() {
        return classesDetailLists;
    }
    public void setClassesDetailLists(List<BaseClassesDetailVo> classesDetailLists) {
        this.classesDetailLists = classesDetailLists;
    }
    public String getClassesName() {
        return classesName;
    }
    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }
    public Long getId() {
        return id;
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
    public void setId(Long id) {
        this.id = id;
    }
    public String getDepartureTime() {
        return departureTime;
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
