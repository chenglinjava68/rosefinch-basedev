package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;

public class BaseEmployeeTypeVo implements Serializable {
    private static final long serialVersionUID = 5384315556493268121L;
    private Long id;
    private String employeeCode;
    private Integer employeeType;
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
    public Integer getEmployeeType() {
        return employeeType;
    }
    public void setEmployeeType(Integer employeeType) {
        this.employeeType = employeeType;
    }

}
