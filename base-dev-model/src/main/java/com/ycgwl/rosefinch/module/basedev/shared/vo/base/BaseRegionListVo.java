/*
 * Copyright (C) 2016 YcExpress.
 * All rights reserved.
 */
package com.ycgwl.rosefinch.module.basedev.shared.vo.base;

import java.io.Serializable;
import java.util.List;

public class BaseRegionListVo implements Serializable {
	private static final long serialVersionUID = 1L;
	//编码
	private String code;
	//名称
	private String name;
	//上级名称
	private String parentDistrictName;
	//上级编码
	private String parentDistrictCode;	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentDistrictName() {
		return parentDistrictName;
	}
	public void setParentDistrictName(String parentDistrictName) {
		this.parentDistrictName = parentDistrictName;
	}
	public String getParentDistrictCode() {
		return parentDistrictCode;
	}
	public void setParentDistrictCode(String parentDistrictCode) {
		this.parentDistrictCode = parentDistrictCode;
	}
	

}

