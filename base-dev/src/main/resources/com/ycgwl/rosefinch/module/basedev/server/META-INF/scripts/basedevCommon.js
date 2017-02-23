/*
组织机构单选公共组件
*/
/** 组织机构model */
Ext.define('BaseData.commonSelector.BaseOrgModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'orgCode',
		type : 'string'
	}, {
		name : 'orgName',
		type : 'string'
	}, {
		name : 'orderBy',
		type : 'int'
	} ]
});

/** 组织机构store */
Ext.define('BaseData.commonSelector.BaseOrgStore', {
	extend : 'Ext.data.Store',
	model : 'BaseData.commonSelector.BaseOrgModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../basedev/getPaginationBaseOrgList.do',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	}
});

/** 组织机构单选公共选择器 */
Ext.define('BaseData.commonSelector.OrgSelector', {
	extend : 'Dpap.commonSelector.CommonCombSelector',
	alias : 'widget.commonOrgSelector',
	listWidth : 200,// 设置下拉框宽度
	displayField : 'orgName',// 显示名称
	valueField : 'orgCode',// 值
	queryParam : 'orgName',// 查询参数
	showContent : '{orgName}&nbsp;&nbsp;&nbsp;{orgCode}',// 显示表格列
	typeMode: 1, // 1.显示菜单和页面元素(默认)；2.全部；3.显示菜单；4.不显示页面元素；
	queryCaching : false,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('BaseData.commonSelector.BaseOrgStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = {
				'q_sl_orgName': operation.params.orgName,
				'q_int_blFlag' : cfg.blFlag
			};
			Ext.apply(operation, {
				params : searchParams
			});
		});
		me.callParent([cfg]);
	}
});




/*
门店单选公共组件
可通过门店名称、简称、编码前后模糊匹配
可通过网点类型，需配置siteType获取某些类型的门店
示例：
查询一级、二级财务中心
{
	xtype : 'commonOrgSelector',
	name : 'orgCode',
	fieldLabel : '所属部门',
	siteType : '1,2'
}
*/
/** 网点model */
Ext.define('BaseData.commonSelector.BaseSiteModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'siteCode',
		type : 'string'
	}, {
		name : 'siteName',
		type : 'string'
	},{
		name : 'siteNameShort',
		type : 'string'
	},{
		name:'siteType',
		type:'int'
	},{
		name:'siteKind',
		type:'int'
	},{
		name : 'upFinanceCenter',//所属财务中心
		type : 'string'
	}]
});

/** 网点store */
Ext.define('BaseData.commonSelector.BaseSiteStore', {
	extend : 'Ext.data.Store',
	model : 'BaseData.commonSelector.BaseSiteModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../basedev/getPaginationBaseSiteList.do',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	}
});

/** 网点单选公共选择器 */
Ext.define('BaseData.commonSelector.SiteSelector', {
	extend : 'Dpap.commonSelector.CommonCombSelector',
	alias : 'widget.commonSiteSelector',
	listWidth : 200,// 设置下拉框宽度
	displayField : 'siteNameShort',// 显示名称
	valueField : 'siteCode',// 值
	queryParam : 'siteName',// 查询参数
	finOutSiteName:null,
	showContent : '{siteNameShort}&nbsp;&nbsp;&nbsp;{siteCode}',// 显示表格列
	typeMode: 1, // 1.显示菜单和页面元素(默认)；2.全部；3.显示菜单；4.不显示页面元素；
	queryCaching : false,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('BaseData.commonSelector.BaseSiteStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var siteName = operation.params.siteName;
            if(me.finOutSiteName!=null){
                siteName = me.finOutSiteName;
            }
			var searchParams = {
				'q_str_siteName' : siteName,
				'q_str_siteType' : cfg.siteType,
				'q_int_siteKind' : cfg.siteKind,
				'q_int_blFlag' : cfg.blFlag
			};
			Ext.apply(operation, {
				params : searchParams
			});
		});
		me.callParent([cfg]);
	}
});




/*
级联查询当前登录用户所属网点及所有下级网点
*/
/** 所属网点store */
Ext.define('BaseData.commonSelector.OwnerSiteStore', {
	extend : 'Ext.data.Store',
	model : 'BaseData.commonSelector.BaseSiteModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../basedev/queryOwnerSiteCascade.do',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	}
});

/** 所属网点单选公共选择器 */
Ext.define('BaseData.commonSelector.OwnerSiteSelector', {
	extend : 'Dpap.commonSelector.CommonCombSelector',
	alias : 'widget.commonOwnerSiteSelector',
	listWidth : 200,// 设置下拉框宽度
	displayField : 'siteNameShort',// 显示名称
	valueField : 'siteCode',// 值
	queryParam : 'siteNameShort',// 查询参数
	showContent : '{siteNameShort}&nbsp;&nbsp;&nbsp;{siteCode}',// 显示表格列
	typeMode: 1, // 1.显示菜单和页面元素(默认)；2.全部；3.显示菜单；4.不显示页面元素；
	queryCaching : false,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('BaseData.commonSelector.OwnerSiteStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			
			var searchParams = {
				'q_str_siteNameShort': operation.params.siteNameShort,
				'q_str_siteCode' : cfg.siteCode,
				'q_int_siteKind' : cfg.siteKind,
				'q_int_blFlag' : cfg.blFlag
			};
			Ext.apply(operation, {
				params : searchParams
			});
		});
		me.callParent([cfg]);
	}
});





/*
产品单选公共选择器
*/
/**产品MODEL*/
Ext.define('BaseData.commonSelector.BaseProductModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'productCode',
		type : 'string'
	}, {
		name : 'productName',
		type : 'string'
	}]
});

/** 产品store */
Ext.define('BaseData.commonSelector.BaseProductStore', {
	extend : 'Ext.data.Store',
	model : 'BaseData.commonSelector.BaseProductModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../basedev/getComboProductList.do',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	}
});

/**产品单选公共选择器 */
Ext.define('BaseData.commonSelector.ProductSelector', {
	extend : 'Dpap.commonSelector.CommonCombSelector',
	alias : 'widget.commonProductSelector',
	listWidth : 200,// 设置下拉框宽度
	displayField : 'productName',// 显示名称
	valueField : 'productCode',// 值
	queryParam : 'productName',// 查询参数
	showContent : '{productName}&nbsp;&nbsp;&nbsp;{productCode}',// 显示表格列
	typeMode: 1, // 1.显示菜单和页面元素(默认)；2.全部；3.显示菜单；4.不显示页面元素；
	queryCaching : false,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('BaseData.commonSelector.BaseProductStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = {
				'q_sl_productName': operation.params.productName,
				'q_str_productLevelStr' : cfg.productLevel,
				'q_int_blFlag' : cfg.blFlag,
				'q_int_status' : cfg.status
			};
			Ext.apply(operation, {
				params : searchParams
			});
		});
		me.callParent([cfg]);
	}
});





/***********************************************************财务中心公共选择装置***************************************************/
/** 财务中心store */
Ext.define('BaseData.commonSelector.upFinaceCenterStore', {
	extend : 'Ext.data.Store',
	model : 'BaseData.commonSelector.BaseSiteModel',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : '../basedev/getUpFinaceCenterPage.do',
		actionMethods : 'POST',
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	}
});

/** 财务中心选公共选择器 */
Ext.define('BaseData.commonSelector.upFinaceCenterSelector', {
	extend : 'Dpap.commonSelector.CommonCombSelector',
	alias : 'widget.commonUpFinaceCenterSelector',
	listWidth : 200,// 设置下拉框宽度
	displayField : 'siteName',// 显示名称
	valueField : 'siteCode',// 值
	blSite:null,
	queryParam : 'q_sl_siteName',// 查询参数
	showContent : '{siteName}&nbsp;&nbsp;&nbsp;{siteCode}',// 显示表格列
	typeMode: 1, // 1.显示菜单和页面元素(默认)；2.全部；3.显示菜单；4.不显示页面元素；
	queryCaching : false,
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('BaseData.commonSelector.upFinaceCenterStore');
		me.store.addListener('beforeload', function(store, operation, eOpts) {
			var searchParams = {
				'q_int_blSite': me.blSite
			};
			Ext.apply(operation, {
				params : searchParams
			});
		});
		me.callParent([cfg]);
	}
});

