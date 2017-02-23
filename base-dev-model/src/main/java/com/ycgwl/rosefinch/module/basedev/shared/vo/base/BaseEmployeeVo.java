package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BaseEmployeeVo implements Serializable {
    private static final long serialVersionUID = 5384315556493268121L;
    // 编号
    private Long id;
    // 员工编号
    private String employeeCode;
    // 员工姓名
    private String employeeName;
    // 所属网点
    private String siteName;
    // 联系电话
    private String phone;
    // 地址
    private String address;
    // 员工类型
    //private Integer employeeType;
    // 是否启用
    private Integer blFlag;
    // 创建人
    private String createUserCode;
    
    // 职位
    private String title;
    
    /**
     * @return the createUserCode
     */
    public String getCreateUserCode() {
        return createUserCode;
    }

    /**
     * @param createUserCode the createUserCode to set
     */
    public void setCreateUserCode(String createUserCode) {
        this.createUserCode = createUserCode;
    }

    // 创建时间
    private Date createTime;
    // 修改人
    private String modifyUserCode;
    // 修改时间
    private Date modifyTime;
    // 所属部门
    private String orgName;
    // 部门编号
    private String orgCode;
    // 所属站点编号
    private String ownerSite;

    private List<Integer> employeeTypeList;

    /*private List<BaseEmployeeTypeVo> employeeTypeList;

    public List<BaseEmployeeTypeVo> getEmployeeTypeList() {
        return employeeTypeList;
    }

    public void setEmployeeTypeList(List<BaseEmployeeTypeVo> employeeTypeList) {
        this.employeeTypeList = employeeTypeList;
    }*/

    public Long getId() {
        return id;
    }

    public List<Integer> getEmployeeTypeList() {
        return employeeTypeList;
    }

    public void setEmployeeTypeList(List<Integer> employeeTypeList) {
        this.employeeTypeList = employeeTypeList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOwnerSite() {
        return ownerSite;
    }

    public void setOwnerSite(String ownerSite) {
        this.ownerSite = ownerSite;
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
    public Integer getBlFlag() {
        return blFlag;
    }

    public void setBlFlag(Integer blFlag) {
        this.blFlag = blFlag;
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

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
