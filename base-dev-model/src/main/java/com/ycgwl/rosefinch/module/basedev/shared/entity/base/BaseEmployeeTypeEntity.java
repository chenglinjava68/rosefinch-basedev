package com.ycgwl.rosefinch.module.basedev.shared.entity.base;

import java.io.Serializable;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

//员工类型表
@Table(value = "T_BASE_EMPLOYEE_TYPE")
public class BaseEmployeeTypeEntity implements Serializable {
    private static final long serialVersionUID = -5365639262964915289L;
    // 员工ID
    @Id
    @Column("ID")
    private Long id;
    // 员工编号
    @Column("EMPLOYEE_CODE")
    private String employeeCode;
    // 员工类型
    @Column("EMPLOYEE_TYPE")
    private Integer employeeType;

    public Integer getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(Integer employeeType) {
        this.employeeType = employeeType;
    }

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
}
