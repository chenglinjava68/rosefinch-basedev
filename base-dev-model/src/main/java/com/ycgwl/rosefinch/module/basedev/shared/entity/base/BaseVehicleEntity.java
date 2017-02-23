package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;
import java.util.Date;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

/**
 *
 * @Title:车辆管理
 * @Description:
 * @Company: 远成快运
 * @author caijue
 * @date 2016年11月21日 下午2:57
 *
 */
@Table(value = "T_BASE_VEHICLE", dynamicInsert = true, dynamicUpdate = true)
public class BaseVehicleEntity implements Serializable {

    private static final long serialVersionUID = 3782817438827915260L;

    @Id
    @Column("ID")
    private Long id;

    /*
     * @Column("RUN_NO") private String runNo;//班次号
     */
    @Column(value="VEHICEL_CODE")
    private String vehicelCode;

    @Column(value = "VEHICEL_NO", like = true)
    private String vehicelNo;// 车牌号

    @Column("OWNER_TYPE")
    private Integer ownerType;// 所有类型

    @Column("VEHICEL_TYPE")
    private String vehicelType;// 车辆类型

    @Column("BL_TEST")
    private Integer blTest;// 是否考核

    @Column("OWNER_ORG")
    private String ownerOrg;// 所属机构

    @Column("OWNER_COM")
    private String ownerCom;// 所属公司

    @Column("VEHICEL_MODEL")
    private Integer vehicelModel;// 车型

    @Column("BOX_TYPE")
    private Integer boxType;// 车厢类型

    @Column("BOX_SIZE")
    private Double boxSize;// 货箱大小

    @Column("BOX_TONS")
    private Double boxTons;// 载重

    @Column("BL_RUN")
    private Integer blRun;// 是否正常运营

    @Column("CREATE_USER_CODE")
    private String createUserCode;// 创建人

    @Column("CREATE_TIME")
    private Date createTime;// 创建时间

    @Column("MODIFY_USER_CODE")
    private String modifyUserCode;// 修改人

    @Column("MODIFY_TIME")
    private Date modifyTime;// 修改时间

    @Column("REMARK")
    private String remark;// 备注

    @Column("DEL_FLAG")
    private Integer delFlag;// 删除标识

    private String owerTypeName;// 所有类型

    private String vehicelModelName;// 车型

    private String boxTypeName;// 车型类型

    private String ownerOrgName;// 组织结构名称

    private String ownerComName;// 公司名称

    private String runName;// 班次名称
    @Column("BL_FLAG")
    private Integer blFlag;//启用标识

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

    public String getRunName() {
        return runName;
    }

    public void setRunName(String runName) {
        this.runName = runName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*
     * public String getRunNo() { return runNo; }
     *
     * public void setRunNo(String runNo) { this.runNo = runNo; }
     */

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

    public String getOwerTypeName() {
        return owerTypeName;
    }

    public void setOwerTypeName(String owerTypeName) {
        this.owerTypeName = owerTypeName;
    }

    public String getVehicelModelName() {
        return vehicelModelName;
    }

    public void setVehicelModelName(String vehicelModelName) {
        this.vehicelModelName = vehicelModelName;
    }

    public String getBoxTypeName() {
        return boxTypeName;
    }

    public void setBoxTypeName(String boxTypeName) {
        this.boxTypeName = boxTypeName;
    }

    public String getOwnerOrgName() {
        return ownerOrgName;
    }

    public void setOwnerOrgName(String ownerOrgName) {
        this.ownerOrgName = ownerOrgName;
    }

    public String getOwnerComName() {
        return ownerComName;
    }

    public void setOwnerComName(String ownerComName) {
        this.ownerComName = ownerComName;
    }

    public String getVehicelCode() {
        return vehicelCode;
    }

    public void setVehicelCode(String vehicelCode) {
        this.vehicelCode = vehicelCode;
    }
}
