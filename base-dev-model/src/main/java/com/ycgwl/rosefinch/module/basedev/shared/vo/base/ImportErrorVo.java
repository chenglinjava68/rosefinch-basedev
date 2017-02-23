package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;

public class ImportErrorVo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8432410844306034296L;
	//第几条
	private Integer num;
	//错误数据
	private String errorData;
	//错误原因
	private String errorResion;
	/**
	 * @return the num
	 */
	public Integer getNum() {
		return num;
	}
	/**
	 * @param num the num to set
	 */
	public void setNum(Integer num) {
		this.num = num;
	}
	/**
	 * @return the errorData
	 */
	public String getErrorData() {
		return errorData;
	}
	/**
	 * @param errorData the errorData to set
	 */
	public void setErrorData(String errorData) {
		this.errorData = errorData;
	}
	/**
	 * @return the errorResion
	 */
	public String getErrorResion() {
		return errorResion;
	}
	/**
	 * @param errorResion the errorResion to set
	 */
	public void setErrorResion(String errorResion) {
		this.errorResion = errorResion;
	}
	
}
