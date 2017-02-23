package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;

public class EmpVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -736479830490863687L;
	
	/**
	 * 员工id
	 */
	private int emp_id;
	/**
	 * 员工名称
	 */
	private String  name;
	
	/**
	 * 员工年龄
	 */
	private int age;
	
	private String selectorParam;
	
	public String getSelectorParam() {
		return selectorParam;
	}

	public void setSelectorParam(String selectorParam) {
		this.selectorParam = selectorParam;
	}

	/**
	 * 获取员工Id
	 * @return
	 */
	public int getEmp_id() {
		return emp_id;
	}
	
	/**
	 * 设置员工Id
	 * @param emp_id
	 */
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	
	/**
	 * 设置员工名称
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置员工名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 获取年龄
	 * @return
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * 设置年龄
	 * @param age
	 */
	public void setAge(int age) {
		this.age = age;
	}

}
