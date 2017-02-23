package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;
import java.util.Date;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

@Table(value = "T_BASE_EMPLOYEE")
public class BaseEmployeeEntity implements Serializable {

    private static final long serialVersionUID = -5365639262964915289L;
    // 员工ID
    @Id
    @Column("ID")
    private Long id;
    // 员工编号
    @Column(value = "EMPLOYEE_CODE", like = true)
    private String employeeCode;
    // 员工姓名
    @Column(value = "EMPLOYEE_NAME", like = true)
    private String employeeName;
    // 所属网点
    @Column("OWNER_SITE")
    private String ownerSite;
    // 联系电话
    @Column("PHONE")
    private String phone;
    // 地址
    @Column("ADDRESS")
    private String address;
    // 员工类型
    @Column("EMPLOYEE_TYPE")
    private Integer employeeType;
    // 寄件提成
    @Column("BL_SEND_GAIN")
    private Double blSendGain;
    // 派件提成
    @Column("BL_DISPATCH_GAIN")
    private Double blDispatchGain;
    // 所属承包区
    @Column("OWNER_RANGE")
    private String ownerRange;
    // 是否启用
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
    // 所属部门
    @Column("ORG_CODE")
    private String orgCode;
    // 删除标示
    @Column("DEL_FLAG")
    private Integer delFlag;
    
    // 职位
    @Column("TITLE")
    private String title;
    
    
    // 从AD域中同步过来时是否忽略该员工
 	private Boolean ignore;
 	
 	// 标记位：true，分公司下级部门员工
 	private Boolean flag;
 	
 	
 	

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getOwnerSite() {
        return ownerSite;
    }

    public void setOwnerSite(String ownerSite) {
        this.ownerSite = ownerSite;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(Integer employeeType) {
        this.employeeType = employeeType;
    }

    public Double getBlSendGain() {
        return blSendGain;
    }

    public void setBlSendGain(Double blSendGain) {
        this.blSendGain = blSendGain;
    }

    public Double getBlDispatchGain() {
        return blDispatchGain;
    }

    public void setBlDispatchGain(Double blDispatchGain) {
        this.blDispatchGain = blDispatchGain;
    }

    public String getOwnerRange() {
        return ownerRange;
    }

    public void setOwnerRange(String ownerRange) {
        this.ownerRange = ownerRange;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

	public Boolean getIgnore() {
		return ignore;
	}

	public void setIgnore(Boolean ignore) {
		this.ignore = ignore;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
