basedev.baseSite.TAB_ID="T_basedev-baseSiteIndex";  // 门店管理标签页ID
basedev.baseSite.CONTENT_ID = "T_basedev-baseSiteIndex_content";  // 门店管理内容panel ID

// 门店查询表单 
basedev.baseSite.QUERY_BASE_SITE_FORM_ID = "bs-queryBaseSiteForm";
// 门店列表
basedev.baseSite.QUERY_BASE_SITE_RESULT_GRID_ID = "bs-queryBaseSiteResultGrid";

basedev.baseSite.STATE_ADD = 'add';   // 新增
basedev.baseSite.STATE_UPDATE = 'update';   // 修改

// 基本信息新增/编辑 表单
basedev.baseSite.EDIT_BASE_SITE_FORM_ID = "bs-editBaseSiteForm";
// 附加信息新增/编辑 表单
basedev.baseSite.EDIT_BASE_SITE_DETAIL_FORM_ID = "bs-editBaseSiteDetailForm";

// 省combobox
basedev.baseSite.REGION_PROVINCE_COMBOBOX_ID = "bs-regionProvinceCombobox";
// 市combobox
basedev.baseSite.REGION_CITY_COMBOBOX_ID = "bs-regionCityCombobox";
// 区（县）combobox
basedev.baseSite.REGION_DISTRICT_COMBOBOX_ID = "bs-regionDistrictCombobox";
// 门店类型
basedev.baseSite.SITE_TYPE_COMBOBOX_ID = "bs-siteTypeCombobox";
// 所属财务中心
basedev.baseSite.UP_FINANCE_CENTER_COMBOBOX_ID = "bs-upFinanceCenterCombobox";
// 账单财务中心
basedev.baseSite.UP_SETTLE_CENTER_COMBOBOX_ID = "bs-upSettleCenterCombobox";

// 所属财务中心
basedev.baseSite.UP_FINANCE_CENTER_COMBOBOX_ID = "bs-upFinanceCenterCombobox";
// 账单财务中心
basedev.baseSite.UP_SETTLE_CENTER_COMBOBOX_ID = "bs-upSettleCenterCombobox";

/**
 * 是/否 渲染
 * @param {} value
 * @return {String}
 */
basedev.baseSite.rendererYN = function(value){
	if(0 == value){
		return "否";
	}
	if(1 == value){
		return "是";
	}
	return "";
}


/**
 * 查询条件
 */
Ext.define('Basedev.baseSite.QueryBaseSiteForm', {
	extend : 'Ext.form.Panel',
	id : basedev.baseSite.QUERY_BASE_SITE_FORM_ID,
	frame : true,
	title : '查询条件',
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 150,
		validateOnChange : false
	},
	layout : {
		type : 'table',
		columns : 3,
		tableAttrs : {
			style : {
				width : '100%'
			}
		}
	},
	defaultType : 'textfield',
	items : [{
				xtype : 'textfield',
				name : 'siteCode',
				fieldLabel : '门店编号'
			}, {
				xtype : 'textfield',
				name : 'siteNameShort',
				fieldLabel : '门店简称'
			}, {
				xtype : "dictcombo",
				dictType : 'SITE_KIND',
				name : 'siteKind',
				fieldLabel : '门店属性'
			}, {
				xtype : 'commonSiteSelector',
				name : 'sendpieceTrancenter',
				fieldLabel : '寄件转运中心',
				siteType : '5,6'
			}, {
				xtype : 'commonSiteSelector',
				name : 'arrivepieceTrancenter',
				fieldLabel : '到件转运中心',
				siteType : '5,6'
			}],

	buttons : [{
				text : '重置',
				width : 100,
				handler : function() {
					var queryFrom = this.up('form').getForm();

					// 重置
					queryFrom.reset();
				}
			}, {
				cls : 'yellow_button',
				text : '查询',
				width : 100,
				handler : function() {
					var queryFrom = this.up('form').getForm();
					if (!queryFrom.isValid()) {
						return;
					}

					var gridPanel = Ext.getCmp(basedev.baseSite.QUERY_BASE_SITE_RESULT_GRID_ID);
					gridPanel.setVisible(true);
					gridPanel.getPagingToolbar().moveFirst();
				}
			}],
	constructor : function(config) {
		var cfg = Ext.apply({}, config);
		this.callParent([cfg]);
	}
});


/**
 * 门店model
 */
Ext.define('Basedev.baseSite.BaseSiteModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'hiddenId',
		type : 'string'
	}, {
		name : 'hiddenModifyTime',
		type : 'string'
	}, {
		name : 'siteCode',
		type : 'string'
	}, {
		name : 'siteName',
		type : 'string'
	}, {
		name : 'siteNameShort',
		type : 'string'
	}, {
		name : 'orderBy',
		type : 'int'
	}, {
		name : 'upSite',
		type : 'string'
	}, {
		name : 'upSiteName',
		type : 'string'
	}, {
		name : 'upFinanceCenter',
		type : 'string'
	}, {
		name : 'upFinanceCenterName',
		type : 'string'
	}, {
		name : 'upSettleCenter',
		type : 'string'
	}, {
		name : 'upSettleCenterName',
		type : 'string'
	}, {
		name : 'siteType',
		type : 'string'
	}, {
		name : 'siteTypeName',
		type : 'string'
	}, {
		name : 'siteKind',
		type : 'string'
	}, {
		name : 'siteKindName',
		type : 'string'
	}, {
		name : 'defaultCurrency',
		type : 'string'
	}, {
		name : 'defaultCurrencyName',
		type : 'string'
	}, {
		name : 'country',
		type : 'string'
	}, {
		name : 'countryName',
		type : 'string'
	}, {
		name : 'province',
		type : 'string'
	}, {
		name : 'provinceName',
		type : 'string'
	}, {
		name : 'city',
		type : 'string'
	}, {
		name : 'cityName',
		type : 'string'
	}, {
		name : 'county',
		type : 'String'
	}, {
		name : 'countyName',
		type : 'String'
	}, {
		name : 'bigArea',
		type : 'string'
	}, {
		name : 'areaCode',
		type : 'string'
	}, {
		name : 'areaName',
		type : 'string'
	}, {
		name : 'siteAddress',
		type : 'string'
	}, {
		name : 'blAllowTopayment',
		type : 'int'
	}, {
		name : 'siteServicesType',
		type : 'string'
	}, {
		name : 'siteServicesTypeName',
		type : 'string'
	}, {
		name : 'blAllowAgentMoney',
		type : 'int'
	}, {
		name : 'blMaterial',
		type : 'int'
	}, {
		name : 'blPreLimit',
		type : 'int'
	}, {
		name : 'blBigCustomer',
		type : 'int'
	}, {
		name : 'goodsPaymentLimited',
		type : 'String'
	}, {
		name : 'blServicmode',
		type : 'int'
	}, {
		name : 'blWeb',
		type : 'int'
	}, {
		name : 'joinTimeStr',
		type : 'string'
	}, {
		name : 'blAllowAgentSign',
		type : 'int'
	}, {
		name : 'blAllPromise',
		type : 'int'
	}, {
		name : 'sendpieceTrancenter',
		type : 'string'
	}, {
		name : 'sendpieceTrancenterName',
		type : 'string'
	}, {
		name : 'arrivepieceTrancenter',
		type : 'string'
	}, {
		name : 'arrivepieceTrancenterName',
		type : 'string'
	}, {
		name : 'createTime',
		type : 'date',
		convert : dateConvert
	}, {
		name : 'createUserCode',
		type : 'string'
	}, {
		name : 'modifyTime',
		type : 'date',
		convert : dateConvert
	}, {
		name : 'modifyUserCode',
		type : 'string'
	}, {
		name : 'delFlag',
		type : 'int'
	}, {
		name : 'blArbitrationDepartment',
		type : 'int'
	}, {
		name : 'blMessage',
		type : 'int'
	}, {
		name : 'flag',
		type : 'int'
	}, {
		name : 'blRec',
		type : 'int'
	}, {
		name : 'blSend',
		type : 'int'
	}, {
		name : 'blCome',
		type : 'int'
	}, {
		name : 'canselfget',
		type : 'int'
	}, {
		name : 'blPdaTwo',
		type : 'int'
	}, {
		name : 'blFlag',
		type : 'int'
	}, {
		name : 'baseSiteDetailEntity',
		type : 'object'
	}]
});


/**
 * 门店附加信息model
 */
Ext.define('Basedev.baseSite.BaseSiteDetailModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'hiddenId',
		type : 'string'
	}, {
		name : 'siteCode',
		type : 'string'
	}, {
		name : 'phone',
		type : 'string'
	}, {
		name : 'principal',
		type : 'string'
	}, {
		name : 'manager',
		type : 'string'
	}, {
		name : 'salePhone',
		type : 'string'
	}, {
		name : 'fax',
		type : 'string'
	}, {
		name : 'notDispatchRange',
		type : 'string'
	}, {
		name : 'specserviceRange',
		type : 'string'
	}, {
		name : 'dispatchTimeLimit',
		type : 'string'
	}, {
		name : 'managerPhone',
		type : 'string'
	}, {
		name : 'dispatchMoneyDesc',
		type : 'string'
	}, {
		name : 'exigencePhone',
		type : 'string'
	}, {
		name : 'financialAccount',
		type : 'string'
	}, {
		name : 'siteDesc',
		type : 'String'
	}, {
		name : 'sitematerial',
		type : 'string'
	}, {
		name : 'remark',
		type : 'string'
	}, {
		name : 'dispatchRange',
		type : 'string'
	}]
});


//-----------------------------------
/**
 * 省Store
 */
Ext.define('BaseData.region.ProvinceStore', {
	extend : 'Ext.data.Store',
	autoDestroy : true,
	autoLoad : true,
	autoSync : true,
	proxy : {
		type : 'ajax',
		url : basedev.realPath('getProvinceList.do'),
		reader : {
			type : 'json'
		}
	},
	fields : [ {
		type : 'string',
		name : 'regionCode'
	}, {
		type : 'string',
		name : 'regionName'
	} ]
});


/**
 * 市Store
 */
Ext.define('BaseData.region.CityStore', {
	extend : 'Ext.data.Store',
	autoDestroy : true,
//	autoLoad : true,
//	autoSync : true,
	proxy : {
		type : 'ajax',
		url : basedev.realPath('getSubRegionListByRegionParent.do'),
		reader : {
			type : 'json'
		}
	},
	fields : [ {
		type : 'string',
		name : 'regionCode'
	}, {
		type : 'string',
		name : 'regionName'
	} ],
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var provinceCode = Ext.getCmp(basedev.baseSite.REGION_PROVINCE_COMBOBOX_ID)
					.getValue();
	        Ext.apply(store.proxy.extraParams, {
					regionParent : provinceCode
			});
		}
	}
});

/**
 * 区（县）Store
 */
Ext.define('BaseData.region.DistrictStore', {
	extend : 'Ext.data.Store',
	autoDestroy : true,
//	autoLoad : true,
//	autoSync : true,
	proxy : {
		type : 'ajax',
		url : basedev.realPath('getSubRegionListByRegionParent.do'),
		reader : {
			type : 'json'
		}
	},
	fields : [ {
		type : 'string',
		name : 'regionCode'
	}, {
		type : 'string',
		name : 'regionName'
	} ],
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var cityCode = Ext.getCmp(basedev.baseSite.REGION_CITY_COMBOBOX_ID)
					.getValue();
	        Ext.apply(store.proxy.extraParams, {
	        	regionParent : cityCode
			});
		}
	}
});
// -----------------------------------


// ------------------------------
// combobx model
Ext.define('BaseData.baseSite.BaseSiteModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'siteCode',
		type : 'string'
	}, {
		name : 'siteName',
		type : 'string'
	}, {
		name : 'siteNameShort',
		type : 'string'
	}, {
		name : 'orderBy',
		type : 'int'
	} ]
});

/**
 * 所属财务中心Store
 */
Ext.define('BaseData.baseSite.UpFinanceCenterStore', {
	extend : 'Ext.data.Store',
	model : 'BaseData.baseSite.BaseSiteModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : basedev.realPath('getUpFinanceCenterList.do'),
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	}
});

Ext.define('BaseData.baseSite.UpFinanceCenterSelector', {
	extend : 'Dpap.commonSelector.CommonCombSelector',
	alias : 'widget.upFinanceCenterSelector',
	listWidth : 200,// 设置下拉框宽度
	displayField : 'siteNameShort',// 显示名称
	valueField : 'siteCode',// 值
	queryParam : 'siteNameShort',// 查询参数
	showContent : '{siteNameShort}&nbsp;&nbsp;&nbsp;{siteCode}',// 显示表格列
	typeMode: 1, // 1.显示菜单和页面元素(默认)；2.全部；3.显示菜单；4.不显示页面元素；
	queryCaching : false,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('BaseData.baseSite.UpFinanceCenterStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var siteTypeCombo = Ext.getCmp(basedev.baseSite.SITE_TYPE_COMBOBOX_ID);
			
			var searchParams = {
				'q_sl_siteNameShort' : operation.params.siteNameShort,
//				'q_sl_siteCode' : operation.params.siteName,
				'q_str_siteType' : siteTypeCombo.getValue()
			};
			Ext.apply(operation, {
				params : searchParams
			});
		});
		me.callParent([cfg]);
	}
});


/**
 * 账单财务中心Store
 */
Ext.define('BaseData.baseSite.UpSettleCenterStore', {
	extend : 'Ext.data.Store',
	model : 'BaseData.baseSite.BaseSiteModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : basedev.realPath('getUpSettleCenterList.do'),
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	}
});

/**
 * 账单财务中心选择器
 */
Ext.define('BaseData.baseSite.UpSettleCenterSelector', {
	extend : 'Dpap.commonSelector.CommonCombSelector',
	alias : 'widget.upSettleCenterSelector',
	listWidth : 200,// 设置下拉框宽度
	displayField : 'siteNameShort',// 显示名称
	valueField : 'siteCode',// 值
	queryParam : 'siteNameShort',// 查询参数
	showContent : '{siteNameShort}&nbsp;&nbsp;&nbsp;{siteCode}',// 显示表格列
	typeMode: 1, // 1.显示菜单和页面元素(默认)；2.全部；3.显示菜单；4.不显示页面元素；
	queryCaching : false,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('BaseData.baseSite.UpSettleCenterStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var siteTypeCombo = Ext.getCmp(basedev.baseSite.SITE_TYPE_COMBOBOX_ID);
			
			var searchParams = {
				'q_sl_siteNameShort' : operation.params.siteNameShort,
//				'q_sl_siteCode' : operation.params.siteName,
				'q_str_siteType' : siteTypeCombo.getValue()
			};
			Ext.apply(operation, {
				params : searchParams
			});
		});
		me.callParent([cfg]);
	}
});
// ------------------------------





/**
 * 门店新增/编辑
 */
Ext.define('Basedev.baseSite.EditBaseSiteForm',{
	extend:'Ext.form.Panel',
	id : basedev.baseSite.EDIT_BASE_SITE_FORM_ID,
	title: '基本信息',
	frame: true,
    defaults: {
    	margin:'5 10 5 10',
    	labelWidth: 110,
    	width : 280,
    	allowBlank: true,
//	    validateOnBlur: true,
	    validateOnChange: false
    },
    layout : {
    	type : 'table',
    	columns: 2
    },
	defaultType : 'textfield',
	
	items : [{
		xtype : 'textfield',
		name : 'hiddenId',
		hidden : true
	}, {
		xtype : 'textfield',
		name : 'hiddenModifyTime',
		hidden : true
	}, {
		xtype : 'textfield',
		name : 'siteCode',
		fieldLabel : '门店编号',
		maxLength : 50,
		allowBlank: false,
		validateOnBlur : true,
		regex : /^[A-Za-z0-9]+$/,
		regexText : '该输入项只能输入数字和字母',
		validator : function(field){
			if(!field){
				return true;
			}
			
			var editBaseSiteInfoForm = Ext.getCmp(basedev.baseSite.EDIT_BASE_SITE_FORM_ID);
			var state = editBaseSiteInfoForm.getOperatorType();
			
			// 编辑时
			if(basedev.baseSite.STATE_UPDATE == state) {
				return true;
			}
			
			var paramsObj = {siteCode : field};
			var valid = false;
			Ext.Ajax.request({
		        url : basedev.realPath('uniqueCheckBySiteCode.do'),
		        params: paramsObj,
		        async : false,
		        success : function(response) {
		        	var result = Ext.JSON.decode(response.responseText);
		        	if(result.success){
		        		if(result.data == 0){
		        			valid = true;
		        		}
		        	}else{
		        		Ext.ux.Toast.msg('提示', result.msg);
		        	}
		        },
		        failure : function(response) {
		        	Ext.ux.Toast.msg('提示',response.responseText, 'error');
		        }
		    });
			
			if(valid){
				return true;
			}
			return '该门店编号已存在';
		}
	}, {
		xtype : 'textfield',
		name : 'siteName',
		fieldLabel : '门店名称',
		maxLength : 30,
		allowBlank: false,
		validateOnBlur : true,
		validator : function(field){
			if(!field){
				return true;
			}
			
			var editBaseSiteInfoForm = Ext.getCmp(basedev.baseSite.EDIT_BASE_SITE_FORM_ID);
			
			var siteCode = editBaseSiteInfoForm.getForm().findField("siteCode").getValue();
			var paramsObj = {siteCode : siteCode, siteName : field, state : editBaseSiteInfoForm.getOperatorType()};
			
			var valid = false;
			Ext.Ajax.request({
		        url : basedev.realPath('uniqueCheckBySiteName.do'),
		        params: paramsObj,
		        async : false,
		        success : function(response) {
		        	var result = Ext.JSON.decode(response.responseText);
		        	if(result.success){
		        		if(result.data == 0){
		        			valid = true;
		        		}
		        	}else{
		        		Ext.ux.Toast.msg('提示', result.msg);
		        	}
		        },
		        failure : function(response) {
		        	Ext.ux.Toast.msg('提示',response.responseText, 'error');
		        }
		    });
			
			if(valid){
				return true;
			}
			return '该门店名称已存在';
		}
	},{
		xtype : 'textfield',
		name : 'siteNameShort',
		fieldLabel : '门店简称',
		maxLength : 15,
		allowBlank: false,
		validator : function(field){
			if(!field){
				return true;
			}
			
			var editBaseSiteInfoForm = Ext.getCmp(basedev.baseSite.EDIT_BASE_SITE_FORM_ID);
			var siteCode = editBaseSiteInfoForm.getForm().findField("siteCode").getValue();
			var paramsObj = {siteCode : siteCode, siteNameShort : field, state : editBaseSiteInfoForm.getOperatorType()};
			
			var valid = false;
			Ext.Ajax.request({
		        url : basedev.realPath('uniqueCheckBySiteNameShort.do'),
		        params: paramsObj,
		        async : false,
		        success : function(response) {
		        	var result = Ext.JSON.decode(response.responseText);
		        	if(result.success){
		        		if(result.data == 0){
		        			valid = true;
		        		}
		        	}else{
		        		Ext.ux.Toast.msg('提示', result.msg);
		        	}
		        },
		        failure : function(response) {
		        	Ext.ux.Toast.msg('提示',response.responseText, 'error');
		        }
		    });
			
			if(valid){
				return true;
			}
			return '该门店简称已存在';
		}
	},{
		xtype : 'numberfield',
		name : 'orderBy',
		fieldLabel : '排序号',
		allowDecimals: false,
		minValue : 1,
        maxValue: 999999,
        allowBlank: false
	},{
		xtype : "dictcombo",
		dictType : 'SITE_SERVICES_TYPE',
		name : 'siteServicesType',
		fieldLabel : '业务类型',
		editable : false
	},{
		xtype : 'commonOwnerSiteSelector',
		name : 'upSite',
		fieldLabel : '上级门店',
		blFlag : 1,
		allowBlank: false
	},{
		xtype : "dictcombo",
		dictType : 'SITE_KIND',
		name : 'siteKind',
		fieldLabel : '门店属性',
		allowBlank: false,
		editable : false
	},{
		xtype : "dictcombo",
		dictType : 'SITE_TYPE',
		id : basedev.baseSite.SITE_TYPE_COMBOBOX_ID,
		name : 'siteType',
		fieldLabel : '门店类型',
		allowBlank: false,
		editable : false,
		listeners: {
		    select: function(combo,records,eOpts) {
		    	var upFinanceCenterCombo = Ext.getCmp(basedev.baseSite.UP_FINANCE_CENTER_COMBOBOX_ID);
		    	upFinanceCenterCombo.clearValue();
		    	
		    	
		    	var upSettleCenterCombo = Ext.getCmp(basedev.baseSite.UP_SETTLE_CENTER_COMBOBOX_ID);
		    	upSettleCenterCombo.clearValue();
		    	
		    	
		    	var selValueCode = records[0].data.valueCode;
		    	
		    	// 一级财务中心
		    	if(selValueCode == 1){
		    		upFinanceCenterCombo.setDisabled(true);
		    		upSettleCenterCombo.setDisabled(true);
		    	} else {
		    		upFinanceCenterCombo.setDisabled(false);
		    		upSettleCenterCombo.setDisabled(false);
		    	}
		    }
		}
	},{
		xtype : 'upFinanceCenterSelector',
		id : basedev.baseSite.UP_FINANCE_CENTER_COMBOBOX_ID,
		name : 'upFinanceCenter',
		fieldLabel : '所属财务中心',
		allowBlank: false,
		queryCaching : false
	},{
		xtype : 'upSettleCenterSelector',
		id : basedev.baseSite.UP_SETTLE_CENTER_COMBOBOX_ID,
		name : 'upSettleCenter',
		fieldLabel : '账单财务中心',
		allowBlank: false,
		queryCaching : false
	},{
		xtype : "dictcombo",
		dictType : 'DEFAULT_CURRENCY',
		name : 'defaultCurrency',
		fieldLabel : '本位币币别',
		editable : false,
		allowBlank: false
	},{
		xtype : 'combo',
		id : basedev.baseSite.REGION_PROVINCE_COMBOBOX_ID,
		name : 'province',
		fieldLabel : '省份',
		store : Ext.create('BaseData.region.ProvinceStore'),
		displayField : 'regionName',
		valueField : 'regionCode',
//		queryMode : 'local',
		editable : false,
		allowBlank: false,
		listeners: {
		    select: function(combo,records,eOpts) {
		    	var cityCombo = Ext.getCmp(basedev.baseSite.REGION_CITY_COMBOBOX_ID);
		    	
		    	cityCombo.clearValue();
                var cityStore=cityCombo.getStore();
                cityStore.load();
                
                // 清除 区（县）
                var districtCombo = Ext.getCmp(basedev.baseSite.REGION_DISTRICT_COMBOBOX_ID);
            	districtCombo.clearValue();
		    }
		}
	}, {
		xtype : "combo",
		id : basedev.baseSite.REGION_CITY_COMBOBOX_ID,
		name : 'city',
		fieldLabel : '市',
		store : Ext.create('BaseData.region.CityStore'),
		queryMode : 'local',
		displayField : 'regionName',
		valueField : 'regionCode',
		editable : false,
		allowBlank : false,
		listeners: {
		    select: function(combo,records,eOpts) {
		    	var districtCombo = Ext.getCmp(basedev.baseSite.REGION_DISTRICT_COMBOBOX_ID);
		    	
		    	districtCombo.clearValue();
                var districtStore=districtCombo.getStore();
                districtStore.load();
		    }
		}
	}, {
		xtype : "combo",
		id : basedev.baseSite.REGION_DISTRICT_COMBOBOX_ID,
		name : 'county',
		fieldLabel : '区（县）',
		store : Ext.create('BaseData.region.DistrictStore'),
		queryMode : 'local',
		displayField : 'regionName',
		valueField : 'regionCode',
		editable : false,
		allowBlank: false
	},{
		xtype : 'textareafield',
		name : 'siteAddress',
		fieldLabel : '门店地址',
		width : 600,
		colspan : 2,
		maxLength : 250,
		allowBlank: false
	},{
		xtype : 'textfield',
		name : 'bigArea',
		fieldLabel : '所属大区',
		maxLength : 200
	},{
		xtype : 'commonAreaSelector',
		name : 'areaCode',
		fieldLabel : '所属片区',
		maxLength : 200,
		blFlag : 1
	},{
		xtype : 'checkbox',
		name : 'blFlag',
		boxLabel : '启用',
		inputValue: '1',
		uncheckedValue: '0',
		checked : true
	},{
		xtype : 'checkbox',
		name : 'blAllowTopayment',
		boxLabel : '允许到付',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blAllowAgentMoney',
		boxLabel : '允许代收货款',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blMaterial',
		boxLabel : '是否启用物料限制',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blPreLimit',
		boxLabel : '预付款限制',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blBigCustomer',
		boxLabel : '大客户拓展中心',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype: 'numberfield',
		name : 'goodsPaymentLimited',
		fieldLabel : '代收货款限制金额',
//		width : 500,
		allowBlank: true,
		decimalPrecision: 2, // 精确地小数点后两位
        allowDecimals: true,
        maxValue: 999999999.99,
        minValue: 0
	},{
		xtype : 'checkbox',
		name : 'blServicmode',
		boxLabel : '定时达等业务类型权限',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blWeb',
		boxLabel : 'WEB显示',
		inputValue: '1',
		uncheckedValue: '0'
	},{
//		id : "queryConsumerPrice-beginDate",
		xtype : 'datefield',
		name : 'joinTimeStr',
		fieldLabel : '加盟时间',
		format : 'Y-m-d H:i:s',
		validateOnBlur : true,
		listeners : {
			select : function(field, value, eOpts){
				var cd = new Date();
				
				value.setHours(cd.getHours());
				value.setMinutes(cd.getMinutes());
				value.setSeconds(cd.getSeconds());
				
				field.setValue(value);
			}
        }
	},{
		xtype : 'checkbox',
		name : 'blAllowAgentSign',
		boxLabel : '代签货单',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blAllPromise',
		boxLabel : '全境承诺标识',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'commonSiteSelector',
		name : 'sendpieceTrancenter',
		fieldLabel : '寄件转运中心',
		siteType : '5,6',
		allowBlank: true,
		blFlag : 1
	},{
		xtype : 'commonSiteSelector',
		name : 'arrivepieceTrancenter',
		fieldLabel : '到件转运中心',
		siteType : '5,6',
		allowBlank: true,
		blFlag : 1
	},{
		xtype : 'checkbox',
		name : 'blArbitrationDepartment',
		boxLabel : '仲裁部门',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blMessage',
		boxLabel : '能否发送短信',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'flag',
		boxLabel : '发送标示',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blRec',
		boxLabel : '是否揽收',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blSend',
		boxLabel : '是否派送',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blCome',
		boxLabel : '是否到件',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'canselfget',
		boxLabel : '自提权限',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blPdaTwo',
		boxLabel : '允许为交接清单下一站',
		inputValue: '1',
		uncheckedValue: '0'
	}],
	operatorType : null,
	setOperatorType : function(state,record){
		this.operatorType = state;
		var editBaseSiteForm = this.getForm();
		// 表单重置
		editBaseSiteForm.reset();
		
		if(state == basedev.baseSite.STATE_ADD){
			var baseSiteModel = Ext.create('Basedev.baseSite.BaseSiteModel');
			this.setFormFieldsReadOnly(false);
			
		} else if(state == basedev.baseSite.STATE_UPDATE){
			var baseSiteModel = Ext.create('Basedev.baseSite.BaseSiteModel',record);
			
			// --------上级门店-------
			var comboboxBaseOrgModel = Ext.create('BaseData.commonSelector.BaseSiteModel');
			comboboxBaseOrgModel.data.siteCode = record.upSite;
			comboboxBaseOrgModel.data.siteNameShort = record.upSiteName;
			baseSiteModel.data.upSite = comboboxBaseOrgModel;
			
			// --------所属财务中心-------
			// 所属财务中心
    		var comboboxBaseSiteModel = Ext.create('BaseData.baseSite.BaseSiteModel');
			comboboxBaseSiteModel.data.siteCode = record.upFinanceCenter;
			comboboxBaseSiteModel.data.siteNameShort = record.upFinanceCenterName;
			baseSiteModel.data.upFinanceCenter = comboboxBaseSiteModel;
			
			// --------账单财务中心-------
			var siteModel = Ext.create('BaseData.baseSite.BaseSiteModel');
			siteModel.data.siteCode = record.upSettleCenter;
			siteModel.data.siteNameShort = record.upSettleCenterName;
			baseSiteModel.data.upSettleCenter = siteModel;
			
			
			// --------所属片区-------
			var baModel = Ext.create('BaseData.commonSelector.BaseAreaModel');
			baModel.data.areaCode = record.areaCode;
			baModel.data.areaName = record.areaName;
			baseSiteModel.data.areaCode = baModel;
			
			
			// --------寄件转运中心-------
			var stModel = Ext.create('BaseData.baseSite.BaseSiteModel');
			stModel.data.siteCode = record.sendpieceTrancenter;
			stModel.data.siteNameShort = record.sendpieceTrancenterName;
			baseSiteModel.data.sendpieceTrancenter = stModel;
			
			// --------到件转运中心-------
			var apModel = Ext.create('BaseData.baseSite.BaseSiteModel');
			apModel.data.siteCode = record.arrivepieceTrancenter;
			apModel.data.siteNameShort = record.arrivepieceTrancenterName;
			baseSiteModel.data.arrivepieceTrancenter = apModel;
			
			editBaseSiteForm.loadRecord(baseSiteModel);
			this.setFormFieldsReadOnly(true);
			
			// --------------加载“市” 和 “区县”----------
			var cityCombo = Ext.getCmp(basedev.baseSite.REGION_CITY_COMBOBOX_ID);
	    	cityCombo.clearValue();
            var cityStore=cityCombo.getStore();
            cityStore.load(function(){
            	cityCombo.setValue(record.city);
            	
            	var districtCombo = Ext.getCmp(basedev.baseSite.REGION_DISTRICT_COMBOBOX_ID);
            	districtCombo.clearValue();
				districtCombo.getStore().load(function(){
					districtCombo.setValue(record.county);
				});
            });
            
            
            
			//-------------若为一级财务中心，所属财务中心、账单财务中心 不可用----------------
	    	var upFinanceCenterCombo = Ext.getCmp(basedev.baseSite.UP_FINANCE_CENTER_COMBOBOX_ID);
	    	var upSettleCenterCombo = Ext.getCmp(basedev.baseSite.UP_SETTLE_CENTER_COMBOBOX_ID);
	    	
	    	// 一级财务中心
	    	if(record.siteType == 1){
	    		upFinanceCenterCombo.setDisabled(true);
	    		upSettleCenterCombo.setDisabled(true);
	    	} else {
	    		upFinanceCenterCombo.setDisabled(false);
	    		upSettleCenterCombo.setDisabled(false);
	    	}
		    
            
		}
	},
	getOperatorType : function(){
		return this.operatorType;
	},
	setFormFieldsReadOnly: function(flag) {
		var form = this.getForm();
		form.findField('siteCode').setReadOnly(flag);
	}
});


/**
 * 门店附加信息新增/编辑
 */
Ext.define('Basedev.baseSite.EditBaseSiteDetailForm',{
	extend:'Ext.form.Panel',
	id : basedev.baseSite.EDIT_BASE_SITE_DETAIL_FORM_ID,
	title: '附加信息',
	frame: true,
    defaults: {
    	margin:'5 10 5 10',
    	labelWidth: 110,
    	width : 280,
    	allowBlank: true,
//	    validateOnBlur: true,
	    validateOnChange: false
    },
    layout : {
    	type : 'table',
    	columns: 2
    },
	defaultType : 'textfield',
	
	items : [{
		xtype : 'textfield',
		name : 'hiddenId',
		hidden : true
	}, {
		xtype : 'textfield',
		name : 'phone',
		fieldLabel : '查询电话',
		vtype : 'mobileOrPhone'
	},{
		xtype : 'textfield',
		name : 'principal',
		fieldLabel : '负责人',
//		width : 500,
		maxLength : 100,
		allowBlank: true
	}, {
		xtype : 'textfield',
		name : 'manager',
		fieldLabel : '经理'
	},{
		xtype : 'textfield',
		name : 'salePhone',
		fieldLabel : '业务电话',
//		width : 500,
		maxLength : 100,
		allowBlank: true,
		vtype : 'mobileOrPhone'
	}, {
		xtype : 'textfield',
		name : 'fax',
		fieldLabel : '传真'
	},{
		xtype : 'textfield',
		name : 'managerPhone',
		fieldLabel : '经理手机',
		vtype : 'mobile'
	},{
		xtype : 'textfield',
		name : 'exigencePhone',
		fieldLabel : '紧急电话',
		vtype : 'mobileOrPhone'
	},{
		xtype : 'textfield',
		name : 'financialAccount',
		fieldLabel : '财务账号',
//		width : 500,
		maxLength : 100,
		allowBlank: true
	},{
		xtype : 'textareafield',
		name : 'dispatchRange',
		fieldLabel : '派送范围',
		width : 600,
		height : 100,
		maxLength : 2000,
		allowBlank: true,
		colspan:2
	}, {
		xtype : 'textareafield',
		name : 'notDispatchRange',
		fieldLabel : '不派送范围',
//		width : 500,
		width : 600,
		height : 100,
		maxLength : 2000,
		allowBlank: true,
		colspan:2
	}, {
		xtype : 'textareafield',
		name : 'specserviceRange',
		width : 600,
		height : 100,
		fieldLabel : '特殊服务区域',
		maxLength : 2000,
		colspan:2
	},{
		xtype : 'textfield',
		name : 'dispatchTimeLimit',
		fieldLabel : '派送时效限制',
//		width : 500,
		maxLength : 100,
		allowBlank: true
	}, {
		xtype : 'textfield',
		name : 'dispatchMoneyDesc',
		fieldLabel : '派送费用说明',
//		width : 500,
		maxLength : 100,
		allowBlank: true
	}, {
		xtype : 'textareafield',
		name : 'siteDesc',
		fieldLabel : '门店简介',
		width : 600,
		maxLength : 500,
		colspan:2
	},{
		xtype : 'textareafield',
		name : 'sitematerial',
		fieldLabel : '允许门店用的物料',
		width : 600,
//		width : 500,
		maxLength : 500,
		allowBlank: true,
		colspan:2
	},{
		xtype : 'textareafield',
		name : 'remark',
		fieldLabel : '备注',
		width : 600,
//		width : 500,
		maxLength : 500,
		allowBlank: true,
		colspan:2
	}],
	operatorType : null,
	setOperatorType : function(state,record){
		this.operatorType = state;
		
		var editBaseSiteDetailForm = this.getForm();
		// 表单重置
		editBaseSiteDetailForm.reset();
		
		if(state == basedev.baseSite.STATE_ADD){
			// do nothing
		} else if(state == basedev.baseSite.STATE_UPDATE){
			var baseSiteDetailModel = Ext.create('Basedev.baseSite.BaseSiteDetailModel',record);
			editBaseSiteDetailForm.loadRecord(baseSiteDetailModel);
		}
	},
	getOperatorType : function(){
		return this.operatorType;
	}
});


/**
 * 门店Store
 */
Ext.define('Basedev.baseSite.BaseSiteStore',{
	extend:'Ext.data.Store',
	model: 'Basedev.baseSite.BaseSiteModel',
	pageSize: 10,
	autoLoad: false,
	proxy: {
		type: 'ajax',
		actionMethods: 'POST',
		url : basedev.realPath("queryBaseSite.do"),
		reader: {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp(basedev.baseSite.QUERY_BASE_SITE_FORM_ID).getForm();
			
			if (queryForm != null) {
				var siteCode = queryForm.findField('siteCode').getValue();
				var siteNameShort = queryForm.findField('siteNameShort').getValue();
				var siteKind = queryForm.findField('siteKind').getValue();
				var sendpieceTrancenter = queryForm.findField('sendpieceTrancenter').getValue();
				var arrivepieceTrancenter = queryForm.findField('arrivepieceTrancenter').getValue();
				
				Ext.apply(operation, {
					params : {
						'q_str_siteCode' : siteCode,
						'q_str_siteNameShort' : siteNameShort,
						'q_str_siteKind' : siteKind,
						'q_str_sendpieceTrancenter' : sendpieceTrancenter,
						'q_str_arrivepieceTrancenter' : arrivepieceTrancenter
					}
				});	
			}
		}
	}
});

/**
 * 门店表格
 */
Ext.define('Basedev.baseSite.QueryBaseSiteResultGrid',{
	extend: 'Ext.grid.Panel',
	id: basedev.baseSite.QUERY_BASE_SITE_RESULT_GRID_ID,
	title: '查询结果',
	cls: 'autoHeight',
	bodyCls: 'autoHeight',
	// 设置表格不可排序
	sortableColumns: false,
	// 去掉排序的倒三角
    enableColumnHide: false,
    // 设置“如果查询的结果为空，则提示”
    emptyText: '没有数据',
	stripeRows : true, // 交替行效果
	collapsible: true,
    animCollapse: true,
    frame: true,
    selModel : Ext.create('Ext.selection.CheckboxModel'),
    /*layout: {
	    type: 'fit'
	},*/
    columns : [{
    	text : '序号',
    	xtype: 'rownumberer',
    	align : 'center',
        width : 50
    	}, {
		xtype : 'actioncolumn',
		// flex: 1,
		text : '操作',
		align : 'center',
		items : [{
			iconCls : 'deppon_icons_showdetail',
			tooltip : '查看',
			handler : function(gridView, rowIndex, colIndex) { // 查看
				var baseSiteWindow = Ext.getCmp(basedev.baseSite.QUERY_BASE_SITE_RESULT_GRID_ID).getBaseSiteWindow();
				baseSiteWindow.setTitle('查看门店');
				
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
				
				var baseSiteForm = baseSiteWindow.getBaseSiteTab().getBaseSiteForm();
				var baseSiteDetailForm = baseSiteWindow.getBaseSiteTab().getBaseSiteDetailForm();
				
				baseSiteForm.setOperatorType(rowInfo.data);
				baseSiteDetailForm.setOperatorType(rowInfo.data.baseSiteDetailEntity);
				
				baseSiteWindow.show();
			}
		}, {
			iconCls : 'deppon_icons_edit',
			tooltip : '修改',// 修改
			handler : function(gridView, rowIndex, colIndex) {
				var editEaseSiteWindow = Ext.getCmp(basedev.baseSite.QUERY_BASE_SITE_RESULT_GRID_ID).getEditBaseSiteWindow();
				
				editEaseSiteWindow.setTitle('编辑门店');
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
				
				
				editEaseSiteWindow.getBaseSiteTab().getEditBaseSiteForm().setOperatorType(basedev.baseSite.STATE_UPDATE, rowInfo.data);
				editEaseSiteWindow.getBaseSiteTab().getEditBaseSiteDetailForm().setOperatorType(basedev.baseSite.STATE_UPDATE, rowInfo.data.baseSiteDetailEntity);
				// 打开窗口
				editEaseSiteWindow.show();
			}
		}, {
			iconCls : 'deppon_icons_export',
			tooltip : '关联员工',
			handler : function(gridView, rowIndex, colIndex) {
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
				
				basedev.baseSite.selecedSiteCode = rowInfo.raw.siteCode;
				
				var setEmployeeWindow = Ext.create('Basedev.baseSite.SetEmployeeWindow');
				setEmployeeWindow.setTitle('关联员工');
				
				setEmployeeWindow.setSiteCode(rowInfo.raw.siteCode);
				
				
				setEmployeeWindow.show();
			}
		}]
	}, {
		hidden : true,
		dataIndex : 'hiddenId'
	}, {
		hidden : true,
		dataIndex : 'hiddenModifyTime'
	}, {
		text : '门店编号',
		width : 150,
//		sortable : true,
		dataIndex : 'siteCode'
	}, {
		text : '门店名称',
		width : 150,
//		sortable : true,
		dataIndex : 'siteName'
	}, {
		text : '门店简称',
		width : 150,
//		sortable : true,
		dataIndex : 'siteNameShort'
	}, {
		text : '上级门店',
		width : 150,
//		sortable : true,
		dataIndex : 'upSiteName'
	}, {
		text : '所属财务中心',
		width : 150,
//		sortable : true,
		dataIndex : 'upFinanceCenterName'
	}, {
		text : '账单财务中心',
		width : 150,
//		sortable : true,
		dataIndex : 'upSettleCenterName'
	}, {
		text : '门店类型',
		width : 150,
//		sortable : true,
		dataIndex : 'siteTypeName'
	}, {
		text : '门店属性',
		width : 150,
//		sortable : true,
		dataIndex : 'siteKindName'
	}, {
		text : '本位币币别',
		width : 150,
//		sortable : true,
		dataIndex : 'defaultCurrencyName'
	}/*, {
		text : '所属国家',
		width : 150,
//		sortable : true,
		dataIndex : 'countryName'
	}*/, {
		text : '所属省份',
		width : 150,
//		sortable : true,
		dataIndex : 'provinceName'
	}, {
		text : '所在城市',
		width : 150,
//		sortable : true,
		dataIndex : 'cityName'
	}, {
		text : '区县',
		width : 150,
		sortable : true,
		dataIndex : 'countyName'
	}, {
		text : '所属大区',
		width : 150,
//		sortable : true,
		dataIndex : 'bigArea'
	}, {
		text : '所属片区',
		width : 150,
//		sortable : true,
		dataIndex : 'areaName'
	}, {
		text : '门店地址',
		width : 150,
//		sortable : true,
		dataIndex : 'siteAddress'
	}, {
		text : '允许到付',
		width : 150,
//		sortable : true,
		dataIndex : 'blAllowTopayment',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '服务类型',
		width : 150,
//		sortable : true,
		dataIndex : 'siteServicesTypeName'
	}, {
		text : '允许代收货款',
		width : 150,
//		sortable : true,
		dataIndex : 'blAllowAgentMoney',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '是否启用物料限制',
		width : 150,
//		sortable : true,
		dataIndex : 'blMaterial',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '预付款限制',
		width : 150,
//		sortable : true,
		dataIndex : 'blPreLimit',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '是否大客户拓展中心',
		width : 150,
//		sortable : true,
		dataIndex : 'blBigCustomer',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '代收货款限制金额',
		width : 150,
//		sortable : true,
		dataIndex : 'goodsPaymentLimited'
	}, {
		text : '定时达等业务类型权限',
		width : 150,
//		sortable : true,
		dataIndex : 'blServicmode',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : 'WEB显示',
		width : 150,
//		sortable : true,
		dataIndex : 'blWeb',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '加盟时间',
		width : 150,
//		sortable : true,
		dataIndex : 'joinTimeStr'
	}, {
		text : '代签货单',
		width : 150,
//		sortable : true,
		dataIndex : 'blAllowAgentSign',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '全境承诺标识',
		width : 150,
//		sortable : true,
		dataIndex : 'blAllPromise',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '寄件转运中心',
		width : 150,
//		sortable : true,
		dataIndex : 'sendpieceTrancenterName'
	}, {
		text : '到件转运中心',
		width : 150,
//		sortable : true,
		dataIndex : 'arrivepieceTrancenterName'
	}, {
		text : '仲裁部门',
		width : 150,
//		sortable : true,
		dataIndex : 'blArbitrationDepartment',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '能否发送短信',
		width : 150,
//		sortable : true,
		dataIndex : 'blMessage',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '发送标识',
		width : 150,
//		sortable : true,
		dataIndex : 'flag',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '是否揽收',
		width : 150,
//		sortable : true,
		dataIndex : 'blRec',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '是否派送',
		width : 150,
//		sortable : true,
		dataIndex : 'blSend',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '是否到件',
		width : 150,
//		sortable : true,
		dataIndex : 'blCome',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '自提权限',
		width : 150,
//		sortable : true,
		dataIndex : 'canselfget',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '允许为交接清单下一站',
		width : 150,
//		sortable : true,
		dataIndex : 'blPdaTwo',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '启用',
		width : 150,
//		sortable : true,
		dataIndex : 'blFlag',
		renderer : basedev.baseSite.rendererYN
	}, {
		text : '创建人',
		width : 150,
//		sortable : true,
		dataIndex : 'createUserCode'
	}, {
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		text : '创建时间',
		width : 150,
		dataIndex : 'createTime'
	}, {
		text : '修改人',
		width : 150,
//		sortable : true,
		dataIndex : 'modifyUserCode'
	}, {
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		text : '修改时间',
		width : 150,
		dataIndex : 'modifyTime'
	} ],
	baseSiteWindow : null,
	getBaseSiteWindow : function(){
		me = this;
		if(Ext.isEmpty(me.baseSiteWindow)){
			me.baseSiteWindow = Ext.create('Basedev.baseSite.BaseSiteWindow');
		}
		return me.baseSiteWindow;
	},
	editBaseSiteWindow : null,
	getEditBaseSiteWindow : function(){
		me = this;
		if(Ext.isEmpty(me.editBaseSiteWindow)){
			me.editBaseSiteWindow = Ext.create('Basedev.baseSite.EditBaseSiteWindow');
		}
		return me.editBaseSiteWindow;
	},
    
	pagingToolbar : null,
	getPagingToolbar : function() {
		var me = this;
		if (me.pagingToolbar == null) {
			me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : me.store
			});
		}
		
		return me.pagingToolbar;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Basedev.baseSite.BaseSiteStore');
		me.tbar = [{
			text: '新增',
			handler: function(){
				var editBaseSiteWindow = me.getEditBaseSiteWindow();
				editBaseSiteWindow.setTitle('新增门店');
				
				var editBaseSiteForm = editBaseSiteWindow.getBaseSiteTab().getEditBaseSiteForm();
				var editBaseSiteDetailForm = editBaseSiteWindow.getBaseSiteTab().getEditBaseSiteDetailForm();
				
				editBaseSiteForm.setOperatorType(basedev.baseSite.STATE_ADD);
				editBaseSiteDetailForm.setOperatorType(basedev.baseSite.STATE_ADD);
				
				editBaseSiteWindow.show();
			}
		}, {
			text : '启用',
			handler : function() {

				var records = me.getSelectionModel().getSelection();

				if (records.length == 0) {
					Ext.ux.Toast.msg('提示', '请选择要操作的数据', 'error');
				} else {
					var codes = [];
					var len = records.length;
					for (var i = 0; i < len; i++) {
						if (records[i].data.blFlag == 0) { // 停用的
							codes.push(records[i].data.siteCode);
						}
					}

					if (len > codes.length) {
						Ext.ux.Toast.msg('提示', '只能操作已停用的数据，请确认', 'error');
						return;
					}

					Ext.Msg.confirm('确认', '确认启用吗？', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : basedev.realPath('/batchUpdateBlFlag.do'),
								params : {
									codes : codes.join(','),
									blFlag : 1
								},
								success : function(response) {
									var result = Ext.JSON
											.decode(response.responseText);

									if (result.success) {
										Ext.ux.Toast.msg('提示', '启用成功');
										
										// 加载数据
										me.getStore().load();
									} else {
										Ext.ux.Toast.msg('提示', result.msg,
												'error');
									}
								},
								failure : function(response) {
									Ext.ux.Toast.msg('提示',
											response.responseText, 'error');
								}
							});

						}
					});
				}
			}
		}, {
			text : '停用',
			handler : function() {
				var records = me.getSelectionModel().getSelection();

				if (records.length == 0) {
					Ext.ux.Toast.msg('提示', '请选择要操作的记录', 'error');
				} else {
					var codes = [];
					var len = records.length;
					for (var i = 0; i < len; i++) {
						if (records[i].data.blFlag == 1) { // 启用的
							codes.push(records[i].data.siteCode);
						}
					}

					if (len > codes.length) {
						Ext.ux.Toast.msg('提示', '只能操作已启用的数据，请确认', 'error');
						return;
					}

					Ext.Msg.confirm('确认', '确认停用吗？', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : basedev.realPath('/batchUpdateBlFlag.do'),
								params : {
									codes : codes.join(','),
									blFlag : 0
								},
								success : function(response) {
									var result = Ext.JSON
											.decode(response.responseText);

									if (result.success) {
										Ext.ux.Toast.msg('提示', '停用成功');
										
										// 加载数据
										me.getStore().load();
									} else {
										Ext.ux.Toast.msg('提示', result.msg,
												'error');
									}
								},
								failure : function(response) {
									Ext.ux.Toast.msg('提示',
											response.responseText, 'error');
								}
							});
						}
					});
				}
			}
		} ];
		me.listeners = {
			beforedestroy : function(thisCom, eOpts ){
				if(me.baseSiteWindow){
					me.baseSiteWindow.removeAll();
					me.baseSiteWindow.destroy();
				}
				
				if(me.editBaseSiteWindow){
					me.editBaseSiteWindow.removeAll();
					me.editBaseSiteWindow.destroy();
				}
				
			}
		};
		me.bbar = me.getPagingToolbar();
		me.callParent(cfg);
	}
});




/**
 * 门店TAB
 */
Ext.define('Basedev.baseSite.EditBaseSiteTab', {
	extend: 'Ext.tab.Panel',
	width: 700,
	height : 880,
	activeTab: 0,
	editBaseSiteForm : null,
	getEditBaseSiteForm: function(){
		if (Ext.isEmpty(this.editBaseSiteForm)) {
			this.editBaseSiteForm = Ext.create("Basedev.baseSite.EditBaseSiteForm");
		}
		return this.editBaseSiteForm;
	},
	
	editBaseSiteDetailForm : null,
	getEditBaseSiteDetailForm: function(){
		if (Ext.isEmpty(this.editBaseSiteDetailForm)) {
			this.editBaseSiteDetailForm = Ext.create("Basedev.baseSite.EditBaseSiteDetailForm");
		}
		return this.editBaseSiteDetailForm;
	},
	
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
			me.getEditBaseSiteForm(),me.getEditBaseSiteDetailForm()
	    ];
	    
		me.callParent([cfg]);
	}
});

/**
 * 编辑窗口
 */
Ext.define('Basedev.baseSite.EditBaseSiteWindow', {
	extend: 'Ext.window.Window',
	width: 707,
//	height : 1200,
	modal: true,
	closeAction: 'hide',
	baseSiteTab : null,
	getBaseSiteTab: function(){
		if (Ext.isEmpty(this.baseSiteTab)) {
			this.baseSiteTab = Ext.create("Basedev.baseSite.EditBaseSiteTab");
		}
		return this.baseSiteTab;
	},
	
	// 取消按钮
	cancelButton : null,
	getCancelButton:function(){
		var me = this;
		if (Ext.isEmpty(this.cancelButton)) {
			this.cancelButton = Ext.create('Ext.button.Button',{
				text: '取消',
				handler: function(){
					me.hide();
				}
			});
		}
		
		return this.cancelButton;
	},
	// 保存按钮
	saveButton : null,
	getSaveButton: function(){
		var me = this;
		if (Ext.isEmpty(this.saveButton)) {
			this.saveButton = Ext.create('Ext.button.Button',{
				cls:'yellow_button',
				text: '保存',
				handler: function() {
					// 门店基本信息表单
					var baseSiteForm = Ext.getCmp(basedev.baseSite.EDIT_BASE_SITE_FORM_ID).getForm();
					// 校验门店表单
					if (!baseSiteForm.isValid()) {
						return;
					}
					
					// 附加信息表单
					var baseSiteDetailForm = Ext.getCmp(basedev.baseSite.EDIT_BASE_SITE_DETAIL_FORM_ID).getForm();
					// 校验附加信息
					if (!baseSiteForm.isValid()) {
						return;
					}
					
					// 准备好数据
					var data = baseSiteForm.getValues();
					data.baseSiteDetailVo=baseSiteDetailForm.getValues();
					
					
					var url = '';
					if (me.getBaseSiteTab().getEditBaseSiteForm().getOperatorType() == basedev.baseSite.STATE_ADD) {
						url = basedev.realPath('insertBaseSite.do');
					} else {
						url = basedev.realPath('updateBaseSite.do');
					}
					
					console.log(Ext.JSON.encode(data));
					
				    Ext.Ajax.request({
				        url : url,
				        jsonData : Ext.JSON.encode(data),
				        headers: {
				              'Content-Type': 'application/json',
				              'Accept': 'application/json'
				        },
				        success : function(response) {
				        	var result = Ext.JSON.decode(response.responseText);
				        	
				        	if(result.success){
				        		Ext.ux.Toast.msg('提示', '保存成功');
					        	me.hide();
					        	
					        	var grid = Ext.getCmp(basedev.baseSite.QUERY_BASE_SITE_RESULT_GRID_ID);
					        	// 重新加载表格
					        	grid.getStore().load();
				        	}else{
				        		Ext.ux.Toast.msg('提示', result.msg);
				        	}
				        },
				        failure : function(response) {
				        	Ext.ux.Toast.msg('提示',response.responseText, 'error');
				        }
				    });
				    
				    
				}
			});
		}
		return this.saveButton;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
			me.getBaseSiteTab()
	    ];
		me.buttons = [
			me.getCancelButton(),
			me.getSaveButton()
		];
		me.callParent([cfg]);
	}
});







//-----------------------------
/**
 * 门店查看表单
 */
Ext.define('Basedev.baseSite.BaseSiteForm',{
	extend:'Ext.form.Panel',
	id : basedev.baseSite.BASE_SITE_FORM_ID,
	title: '基本信息',
	frame: true,
    defaults: {
    	margin:'5 10 5 10',
    	labelWidth: 110,
    	width : 280,
    	allowBlank: true,
    	readOnly: true
    },
    layout : {
    	type : 'table',
    	columns: 2
    },
	defaultType : 'textfield',
	
	items : [{
		xtype : 'textfield',
		name : 'siteCode',
		fieldLabel : '门店编号'
	}, {
		xtype : 'textfield',
		name : 'siteName',
		fieldLabel : '门店名称'
	},{
		xtype : 'textfield',
		name : 'siteNameShort',
		fieldLabel : '门店简称'
	},{
		name : 'orderBy',
		fieldLabel : '排序号'
	},{
		name : 'siteServicesTypeName',
		fieldLabel : '业务类型'
	},{
		name : 'upSiteName',
		fieldLabel : '上级门店'
	},{
		name : 'siteKindName',
		fieldLabel : '门店属性'
	},{
		name : 'siteTypeName',
		fieldLabel : '门店类型'
	},{
		name : 'upFinanceCenterName',
		fieldLabel : '所属财务中心'
	},{
		name : 'upSettleCenterName',
		fieldLabel : '账单财务中心'
	},{
		name : 'defaultCurrencyName',
		fieldLabel : '本位币币别'
	},{
		name : 'provinceName',
		fieldLabel : '省份'
	}, {
		name : 'cityName',
		fieldLabel : '市'
	}, {
		name : 'countyName',
		fieldLabel : '区（县）'
	},{
		xtype : 'textareafield',
		name : 'siteAddress',
		fieldLabel : '门店地址',
		width : 600,
		colspan : 2,
		maxLength : 250,
		allowBlank: false
	},{
		xtype : 'textfield',
		name : 'bigArea',
		fieldLabel : '所属大区'
	},{
		xtype : 'textfield',
		name : 'areaName',
		fieldLabel : '所属片区'
	},{
		xtype : 'checkbox',
		name : 'blFlag',
		boxLabel : '启用',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blAllowTopayment',
		boxLabel : '允许到付',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blAllowAgentMoney',
		boxLabel : '允许代收货款',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blMaterial',
		boxLabel : '是否启用物料限制',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blPreLimit',
		boxLabel : '预付款限制',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blBigCustomer',
		boxLabel : '大客户拓展中心',
		inputValue: '1',
		uncheckedValue: '0'
	},{
//		xtype: 'numberfield',
		name : 'goodsPaymentLimited',
		fieldLabel : '代收货款限制金额'
	},{
		xtype : 'checkbox',
		name : 'blServicmode',
		boxLabel : '定时达等业务类型权限',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blWeb',
		boxLabel : 'WEB显示',
		inputValue: '1',
		uncheckedValue: '0'
	},{
//		id : "queryConsumerPrice-beginDate",
//		xtype : 'datefield',
		name : 'joinTimeStr',
		fieldLabel : '加盟时间'
	},{
		xtype : 'checkbox',
		name : 'blAllowAgentSign',
		boxLabel : '代签货单',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blAllPromise',
		boxLabel : '全境承诺标识',
		inputValue: '1',
		uncheckedValue: '0'
	},{
//		xtype : 'commonSiteSelector',
		name : 'sendpieceTrancenterName',
		fieldLabel : '寄件转运中心'
	},{
//		xtype : 'commonSiteSelector',
		name : 'arrivepieceTrancenterName',
		fieldLabel : '到件转运中心'
	},{
		xtype : 'checkbox',
		name : 'blArbitrationDepartment',
		boxLabel : '仲裁部门',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blMessage',
		boxLabel : '能否发送短信',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'flag',
		boxLabel : '发送标示',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blRec',
		boxLabel : '是否揽收',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blSend',
		boxLabel : '是否派送',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blCome',
		boxLabel : '是否到件',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'canselfget',
		boxLabel : '自提权限',
		inputValue: '1',
		uncheckedValue: '0'
	},{
		xtype : 'checkbox',
		name : 'blPdaTwo',
		boxLabel : '允许为交接清单下一站',
		inputValue: '1',
		uncheckedValue: '0'
	}],
	setOperatorType : function(record){
		var baseSiteForm = this.getForm();
		// 表单重置
//		baseSiteForm.reset();
		
		var baseSiteModel = Ext.create('Basedev.baseSite.BaseSiteModel',record);
		baseSiteForm.loadRecord(baseSiteModel);
	}
});


/**
 * 门店附加信息查看
 */
Ext.define('Basedev.baseSite.BaseSiteDetailForm',{
	extend:'Ext.form.Panel',
	id : basedev.baseSite.BASE_SITE_DETAIL_FORM_ID,
	title: '附加信息',
	frame: true,
    defaults: {
    	margin:'5 10 5 10',
    	labelWidth: 110,
    	width : 280,
    	allowBlank: true,
    	readOnly: true
    },
    layout : {
    	type : 'table',
    	columns: 2
    },
	defaultType : 'textfield',
	
	items : [{
		xtype : 'textfield',
		name : 'hiddenId',
		hidden : true
	}, {
		xtype : 'textfield',
		name : 'phone',
		fieldLabel : '查询电话'
	},{
		xtype : 'textfield',
		name : 'principal',
		fieldLabel : '负责人'
	}, {
		xtype : 'textfield',
		name : 'manager',
		fieldLabel : '经理'
	},{
		xtype : 'textfield',
		name : 'salePhone',
		fieldLabel : '业务电话'
	}, {
		xtype : 'textfield',
		name : 'fax',
		fieldLabel : '传真'
	},{
		xtype : 'textfield',
		name : 'managerPhone',
		fieldLabel : '经理手机'
	},{
		xtype : 'textfield',
		name : 'exigencePhone',
		fieldLabel : '紧急电话'
	},{
		xtype : 'textfield',
		name : 'financialAccount',
		fieldLabel : '财务账号'
	},{
		xtype : 'textareafield',
		name : 'dispatchRange',
		fieldLabel : '派送范围',
		width : 600,
		height : 100,
		maxLength : 2000,
		allowBlank: true,
		colspan:2
	}, {
		xtype : 'textareafield',
		name : 'notDispatchRange',
		fieldLabel : '不派送范围',
//		width : 500,
		width : 600,
		height : 100,
		maxLength : 2000,
		allowBlank: true,
		colspan:2
	}, {
		xtype : 'textareafield',
		name : 'specserviceRange',
		width : 600,
		height : 100,
		fieldLabel : '特殊服务区域',
		maxLength : 2000,
		colspan:2
	},{
		xtype : 'textfield',
		name : 'dispatchTimeLimit',
		fieldLabel : '派送时效限制'
	}, {
		xtype : 'textfield',
		name : 'dispatchMoneyDesc',
		fieldLabel : '派送费用说明'
	}, {
		xtype : 'textareafield',
		name : 'siteDesc',
		fieldLabel : '门店简介',
		width : 600,
		maxLength : 500,
		colspan:2
	},{
		xtype : 'textareafield',
		name : 'sitematerial',
		fieldLabel : '允许门店用的物料',
		width : 600,
//		width : 500,
		maxLength : 500,
		allowBlank: true,
		colspan:2
	},{
		xtype : 'textareafield',
		name : 'remark',
		fieldLabel : '备注',
		width : 600,
//		width : 500,
		maxLength : 500,
		allowBlank: true,
		colspan:2
	}],
	setOperatorType : function(record){
		var baseSiteDetailForm = this.getForm();
		// 表单重置
//		baseSiteDetailForm.reset();
		
		var baseSiteDetailModel = Ext.create('Basedev.baseSite.BaseSiteDetailModel',record);
		baseSiteDetailForm.loadRecord(baseSiteDetailModel);
	}
});


/**
 * 门店TAB（查看）
 */
Ext.define('Basedev.baseSite.BaseSiteTab', {
	extend: 'Ext.tab.Panel',
	width: 700,
	height : 880,
	activeTab: 0,
	margin : '10 10 10 10',
	baseSiteForm : null,
	getBaseSiteForm: function(){
		if (Ext.isEmpty(this.baseSiteForm)) {
			this.baseSiteForm = Ext.create("Basedev.baseSite.BaseSiteForm");
		}
		return this.baseSiteForm;
	},
	
	baseSiteDetailForm : null,
	getBaseSiteDetailForm: function(){
		if (Ext.isEmpty(this.baseSiteDetailForm)) {
			this.baseSiteDetailForm = Ext.create("Basedev.baseSite.BaseSiteDetailForm");
		}
		return this.baseSiteDetailForm;
	},
	
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
			me.getBaseSiteForm(),me.getBaseSiteDetailForm()
	    ];
		me.callParent([cfg]);
	}
});

/**
 * 查看窗口
 */
Ext.define('Basedev.baseSite.BaseSiteWindow', {
	extend: 'Ext.window.Window',
	width: 707,
//	height : 1000,
	modal: true,
	closeAction: 'hide',
	baseSiteTab : null,
	getBaseSiteTab: function(){
		if (Ext.isEmpty(this.baseSiteTab)) {
			this.baseSiteTab = Ext.create("Basedev.baseSite.BaseSiteTab");
		}
		return this.baseSiteTab;
	},
	
	// 关闭按钮
	cancelButton : null,
	getCancelButton:function(){
		var me = this;
		if (Ext.isEmpty(this.cancelButton)) {
			this.cancelButton = Ext.create('Ext.button.Button',{
				text: '关闭',
				handler: function(){
					me.hide();
				}
			});
		}
		
		return this.cancelButton;
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
			me.getBaseSiteTab()
	    ];
		me.buttons = [
			me.getCancelButton()
		];
		me.callParent([cfg]);
	}
});



//--------------设置员工---------------
Ext.define('Basedev.baseSite.SetEmployeeWindow', {
	extend: 'Ext.window.Window',
	width: 1100,
	height : 600,
	modal: true,
	closeAction: 'destroy',
	
	siteCode : null,
	setSiteCode : function(p_siteCode){
		this.siteCode = p_siteCode;
	},
	getSiteCode : function(){
		return this.siteCode;
	},
	employeeSelectorPanel : null,
	getEmployeeSelectorPanel: function(){
//		if (Ext.isEmpty(this.employeeSelectorPanel)) {
			this.employeeSelectorPanel = Ext.create("Rosefinch.Basedev.EmployeeSelectorPanel");
//		}
		return this.employeeSelectorPanel;
	},
	
	// 关闭按钮
	cancelButton : null,
	getCancelButton:function(){
		var me = this;
		if (Ext.isEmpty(this.cancelButton)) {
			this.cancelButton = Ext.create('Ext.button.Button',{
				text: '关闭',
				handler: function(){
					me.close();
				}
			});
		}
		
		return this.cancelButton;
	},
	
	// 保存按钮
	saveButton : null,
	getSaveButton:function(){
		var me = this;
		if (Ext.isEmpty(this.saveButton)) {
			this.saveButton = Ext.create('Ext.button.Button',{
				text: '保存',
				cls:'yellow_button',
				handler: function(){
					// 门店编码
					var sc = me.getSiteCode();
					
					var selEmpPanel = Ext.getCmp(basedev.baseSite.SELECTED_EMPLOYEE_PANEL);
					var selEmpPanelStore = selEmpPanel.getStore();
					
					var empCodeArr = [];
					selEmpPanelStore.each(function(record){
						empCodeArr.push(record.data.employeeCode);
					});
					
					var paramsObj = {
						siteCode : sc,
						empCodes : empCodeArr.join(',')
					};
					
					var valid = false;
					Ext.Ajax.request({
				        url : basedev.realPath('assosiateToEmployee.do'),
				        params: paramsObj,
				        async : false,
				        success : function(response) {
				        	var result = Ext.JSON.decode(response.responseText);
				        	if(result.success){
				        		Ext.ux.Toast.msg('提示', "保存成功");
				        		
				        		me.close();
				        	}else{
				        		Ext.ux.Toast.msg('提示', result.msg);
				        	}
				        },
				        failure : function(response) {
				        	Ext.ux.Toast.msg('提示',response.responseText, 'error');
				        }
				    });
				}
			});
		}
		
		return this.saveButton;
	},
	
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
			me.getEmployeeSelectorPanel()
	    ];
		me.buttons = [
			me.getSaveButton(), me.getCancelButton()
		];
		me.callParent([cfg]);
	}
});





//-----------------------------
Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp(basedev.baseSite.CONTENT_ID)) {
		return;
	};
	
	var queryBaseSiteForm = Ext.create('Basedev.baseSite.QueryBaseSiteForm');
	var queryBaseSiteResultGrid = Ext.create('Basedev.baseSite.QueryBaseSiteResultGrid');
	
	var content = Ext.create('Ext.panel.Panel', {
		id: basedev.baseSite.CONTENT_ID,
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		getQueryBaseSiteForm: function() {
			return queryBaseSiteForm;
		},
		getQueryBaseSiteResultGrid: function() {
			return queryBaseSiteResultGrid;
		},
		items: [
			queryBaseSiteForm,
			queryBaseSiteResultGrid
		]
	});
	
	Ext.getCmp(basedev.baseSite.TAB_ID).add(content);
	// 加载表格数据
	queryBaseSiteResultGrid.getStore().load();
});

