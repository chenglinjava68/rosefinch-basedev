/*
 * Copyright (C) 2016 Wro4j.
 * All rights reserved.
 */
package com.ycgwl.rosefinch.module.basedev.shared.constant.base;

/**
 * TODO: DOCUMENT ME!
 *
 * @author guoh.mao
 */
public class BaseOrgConstant {
	/*****************组织管理********************/
	// 一级财务中心
	public static final Integer FIRST_LEVEL_FINANCE_CENTER = 1;
	// 二级财务中心
	public static final Integer SECOND_LEVEL_FINANCE_CENTER = 2;
	// 一级网点
	public static final Integer FIRST_LEVEL_SITE = 3;
	// 二级网点
	public static final Integer SECOND_LEVEL_SITE = 4;
	// 一级分拨中心
	public static final Integer FIRST_LEVEL_DISTRIBUTION_CENTER = 5;
	// 二级分拨中心
	public static final Integer SECOND_LEVEL_DISTRIBUTION_CENTER = 6;
	// 同行
	public static final Integer COMPETITOR = 7;
	// 三级网点
	public static final Integer THIRD_LEVEL_SITE = 8;

	// 快运本部临时ID
	public static final String YC_EXPRESS_TEMP_CODE = "ycExpress";
	// 快运本部名称
	public static final String YC_EXPRESS_NAME = "快运本部";

	// 根节点ID
	public static final String YC_GROUP_CODE = "YCG";
	// 跟节点名称
	public static final String YC_GROUP_NAME = "远成集团";

	//++++++++++++++网点类型++++++++++++++
	// 公司
	public static final Integer ORG_TYPE_COMPANY = 1;
	// 职能部门
	public static final Integer ORG_TYPE_DEPT = 2;
	// 大区
	public static final Integer ORG_TYPE_BIG_AREA = 3;
	// 片区
	public static final Integer ORG_TYPE_AREA = 4;
	// 行政区
	public static final Integer ORG_TYPE_REGION = 5;
	// 转运或分拨中心
	public static final Integer ORG_TYPE_TRANSFER_DISTRIBUTION_CENTER = 6;
	// 门店
	public static final Integer ORG_TYPE_SHOP = 7;
	// 代收点
	public static final Integer ORG_TYPE_COLLECTION_POINT = 8;
	// 本部
	public static final Integer ORG_TYPE_YCEXPRESS = 9;
	//++++++++++++++++++++++++++++++++++


	// 结算路由类型（0：三级网点到二级网点 1：二级级网点到一级网点   2：一级网点到分公司   3：分公司到快运本部   4：快运本部到快运本部）
	public static final Integer THIRD_LEVEL_SITE_LINE_TYPE = 0;
	public static final Integer SECOND_LEVEL_SITE_LINE_TYPE = 1;
	public static final Integer FIRST_LEVEL_SITE_LINE_TYPE = 2;
	public static final Integer SECOND_LEVEL_FINANCE_CENTER_LINE_TYPE = 3;
	public static final Integer FIRST_LEVEL_FINANCE_CENTER_LINE_TYPE = 4;


	/*************************************************/

	/*****************数据字典类型别名********************/
	// 组织类型
	public static final String TYPE_ALIAS_ORG_TYPE="ORG_TYPE";
	// 本位币币别
	public static final String TYPE_ALIAS_DEFAULT_CURRENCY="DEFAULT_CURRENCY";
	// 匹配模式
	public static final String TYPE_ALIAS_REGION_MATCH_MODE="REGION_MATCH_MODE";
	//产品级别
	public static final String TYPE_ALIAS_REGION_PRODUCT_LEVEL="BASE_PRODUCT_LEVEL";
	// 产品状态
	public static final String TYPE_ALIAS_BASE_PRODUCT_STATUS="BASE_PRODUCT_STATUS";
	// 网点属性
	public static final String TYPE_ALIAS_SITE_KIND="SITE_KIND";
	// 网点类型
	public static final String TYPE_ALIAS_SITE_TYPE="SITE_TYPE";
	// 业务类型
	public static final String TYPE_ALIAS_SITE_SERVICES_TYPE="SITE_SERVICES_TYPE";
	//审核状态(结算明细录入导入)
	public static final String TYPE_ALIAS_FIN_ACCOUNT_OPER_INFO_AUDIT_STATUS="FIN_ACCOUNT_OPER_INFO_AUDIT_STATUS";
	//区间类型
	public static final String TYPE_ALIAS_PRICE_REGION_TYPE="PRICE_REGION_TYPE";
	//报价类型
	public static final String TYPE_ALIAS_PRICE_TYPE="PRICE_TYPE";
	//审核类型
	public static final String TYPE_ALIAS_AUDIT_TYPE="AUDIT_TYPE";
	//行政区域类型
	public static final String TYPE_ALIAS_REGION_LEVEL = "REGION_LEVEL";
	//揽件时效
	public static final String TYPE_ALIAS_QUALIFIED_GETPACKAGE_TIME = "QUALIFIED_GETPACKAGE_TIME";
	//车辆维护-所有类型
	public static final String TYPE_ALIAS_BASE_VEHICLE_ALL_TYPE = "BASE_VEHICLE_ALL_TYPE";
	//车辆维护-车型
	public static final String TYPE_ALIAS_BASE_VEHICLE_MODEL_TYPE = "BASE_VEHICLE_MODEL_TYPE";
	//车辆维护-车厢类型
	public static final String TYPE_ALIAS_BASE_VEHICLE_BOX_TYPE = "BASE_VEHICLE_BOX_TYPE";
    //车线维护 -车线类型
	public static final String TYPE_ALIAS_BASE_CARLINE_LINE_TYPE="BASE_CARLINE_TYPE";
    //车辆管理—途径类型
    public static final String BASE_VEHICLE_VIA_TYPE = "BASE_VEHICLE_VIA_TYPE";
    //车辆管理—运输方式
    public static final String BASE_ROUTE_CLASSTYPE = "BASE_ROUTE_CLASSTYPE";
    //车辆管理—出发周期
    public static final String BASE_CLASSES_CYCLE = "BASE_CLASSES_CYCLE";

	/*************************************************/

	/*****************其他********************/
	//用于delfalg正常表示
	public static final Integer ZERO = 0;
	//用于delfalg表示
	public static final String  DEL_FLAG = "delFlag";
	// 分隔符
	public static final String SPLIT_SEPARATOR = ",";

	// 行政区域名称分隔符
	// 示例：湖北省-武汉市-洪山区，湖南省-长沙市
	public static final String REGION_NAME_SEPARATOR = "-";


	/*************************************************/

	/*************资金路由********************/
    public static final int LINE_TYPE_0=0; //三级网点到二级网点

    public static final int LINE_TYPE_1=1; //二级网点到一级网点

    public static final int LINE_TYPE_2=2;//一级网点到分公司

    public static final int LINE_TYPE_3=3;//分公司到快运本部

	/***************************************/

    public static final int SITE_KIND_1=1;//直营网点

    public static final int SITE_KIND_2=2;//加盟网点

    // 中国行政区域编码
    public static final String REGION_CODE_CHINA="CN";

    public static final String YC_EXPRESS_CODE = "999999";
}


