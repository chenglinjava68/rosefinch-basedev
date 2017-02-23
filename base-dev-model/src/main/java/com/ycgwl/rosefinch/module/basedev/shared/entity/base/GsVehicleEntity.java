package com.ycgwl.rosefinch.module.basedev.shared.entity.base;


import java.io.Serializable;

import org.mybatis.spring.annotation.Column;
import org.mybatis.spring.annotation.Id;
import org.mybatis.spring.annotation.Table;

import com.ycgwl.framework.springmvc.entity.BizBaseEntity;
/**
 * 车辆实体
 * @author zb134373
 *
 */
@Table(value="T_BASE_GS_VEHICLE")
public class GsVehicleEntity extends BizBaseEntity implements Serializable{
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 2649645465711052297L;

	@Id
	@Column("ID")
	private Long Id;
	//车牌号
	@Column("C_CARNO")
	private String carNo;
	//所属公司
	@Column("C_COMPANY")
	private String company;
	//所属部门
	@Column("C_DEPARTMENT")
	private String department;
	//车辆类型(有哪些？）
	@Column("C_CARTYPE")
	private String carType;	
	
	//车型
	@Column("C_CARMODEL")
	private String carModel;	

	//货箱类别(有哪些?）
	@Column("C_BOXTYPE")
	private String boxType;	

	//货箱大小
	@Column("N_BOXSIZE")
	private String boxSize;	

	//货箱吨位
	@Column("N_BOXTONS")
	private String boxTons;	

	//车辆状态
	@Column("N_CARSTATE")
	private String carState;	

	//员工编号
	@Column("C_EMPLOYEECODE")
	private String employCode;	
	//0启用1禁用
	@Column("N_ENABLE")
	private String eNable;
	/**
	 * @return the id
	 */
	public Long getId() {
		return Id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		Id = id;
	}
	/**
	 * @return the carNo
	 */
	public String getCarNo() {
		return carNo;
	}
	/**
	 * @param carNo the carNo to set
	 */
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * @return the department
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * @param department the department to set
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * @return the carType
	 */
	public String getCarType() {
		return carType;
	}
	/**
	 * @param carType the carType to set
	 */
	public void setCarType(String carType) {
		this.carType = carType;
	}
	/**
	 * @return the carModel
	 */
	public String getCarModel() {
		return carModel;
	}
	/**
	 * @param carModel the carModel to set
	 */
	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}
	/**
	 * @return the boxType
	 */
	public String getBoxType() {
		return boxType;
	}
	/**
	 * @param boxType the boxType to set
	 */
	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}
	/**
	 * @return the boxSize
	 */
	public String getBoxSize() {
		return boxSize;
	}
	/**
	 * @param boxSize the boxSize to set
	 */
	public void setBoxSize(String boxSize) {
		this.boxSize = boxSize;
	}
	/**
	 * @return the boxTons
	 */
	public String getBoxTons() {
		return boxTons;
	}
	/**
	 * @param boxTons the boxTons to set
	 */
	public void setBoxTons(String boxTons) {
		this.boxTons = boxTons;
	}
	/**
	 * @return the carState
	 */
	public String getCarState() {
		return carState;
	}
	/**
	 * @param carState the carState to set
	 */
	public void setCarState(String carState) {
		this.carState = carState;
	}
	/**
	 * @return the employCode
	 */
	public String getEmployCode() {
		return employCode;
	}
	/**
	 * @param employCode the employCode to set
	 */
	public void setEmployCode(String employCode) {
		this.employCode = employCode;
	}
	/**
	 * @return the eNable
	 */
	public String geteNable() {
		return eNable;
	}
	/**
	 * @param eNable the eNable to set
	 */
	public void seteNable(String eNable) {
		this.eNable = eNable;
	}




}	
