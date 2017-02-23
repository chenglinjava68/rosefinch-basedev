package com.ycgwl.rosefinch.module.basedev.shared.exception.base;

import com.ycgwl.framework.exception.BusinessException;

/**
 * 员工异常类
 * @author 228238
 *
 */
public class EmpException extends BusinessException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称不能为空
	 */
	public static final String SPRINGMVCTEST_EMPNAME_IS_NOT_NULL = "dpap.springmvctest.empName.isNotNull";

	public EmpException(String errCode){
		super();
		super.errCode = errCode;
	}
}
