
basedev.baseRegion.DEPT_ROOT_ID = 'CN';
basedev.baseRegion.DEPT_ROOT_NAME = '远成集团';

/** 此模块主页面的ID */
basedev.baseRegion.CONTENT_ID  = 'T_basedev-baseRegionIndex_content';
basedev.baseRegion.FORMAT_TIME  = 'Y-m-d H:i:s'; //格式化时间字符串
basedev.baseRegion.REGION_LEVEL = null;

basedev.baseRegion.REGION_PROVINCE = '';
basedev.baseRegion.REGION_CITY = '';
basedev.baseRegion.REGION_DISTRICT = '';
// 是否启用的渲染方法
basedev.baseRegion.blFlagRenderer = function(val) {
	if (val === 1) {
		return '是';
	} else {
		return '否';
	}
	return '';
}


/** 行政区域信息MODEL */
Ext.define('BaseData.baseRegion.BaseRegionModule', {
	extend : 'Ext.data.Model',
	fields : [ {
        name : 'regionId',
        type : 'string'
    }, {
        name : 'regionCode',
        type : 'string'
    }, {
        name : 'regionName',
        type : 'string'
    }, {
        name : 'regionParent',
        type : 'string'
    }, {
        name : 'regionParentName',
        type : 'string'
    }, {
        name : 'regionLevel',
        type : 'string'
    }, {
        name : 'regionLevelName',
        type : 'string'
    }, {
        name : 'regionPinyin',
        type : 'string'
    }, {
        name : 'ishot',
        type : 'number'
    }, {
        name : 'remark',
        type : 'string'
    }, {
        name : 'countryCode',
        type : 'string'
    }, {
        name : 'province',
        type : 'string'
    },{
        name : 'provinceCode',
        type : 'string'
    },{
        name : 'city',
        type : 'string'
    },{
        name : 'cityCode',
        type : 'string'
    },{
        name : 'district',
        type : 'string'
    },{
        name : 'districtCode',
        type : 'string'
    },{
        name : 'town',
        type : 'string'
    },{
        name : 'townCode',
        type : 'string'
    },{
        name : 'remark',
        type : 'string'
    }, {
        name : 'createUserCode'
    }, {
        name : 'modifyUserCode'
    }, {
        name : 'createUserName'
    }, {
        name : 'modifyUserName'
    }, {
        name : 'createOrgName'
    }, {
        name : 'modifyOrgName'
    }, {
        name : 'createTime',
        defaultValue : new Date(),
        convert : dateConvert,
        type : 'number'
    }, {
        name : 'modifyTime',
        defaultValue : new Date(),
        convert : dateConvert,
        type : 'number'
    }, {
        name : 'delFlag',
        type:'string'
    } ]
});

/*------------------------------------树的相关功能级元素-----------------------------------------------------------*/

/** 定义行政区域树的store */
Ext.define('BaseData.baseRegion.BaseRegionTreeStore', {
	extend: 'Ext.data.TreeStore',
	proxy: {
		type : 'ajax',
		//url : baseRegion.realPath('loadTree.do')
	       url:'../basedev/loadTree.do'
	},
	root: {
		id : basedev.baseRegion.DEPT_ROOT_ID,
		text : '行政区域树',
		expanded : true
	},
	constructor: function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/**
 * 树的面板
 */
Ext.define('Dpap.organization.DepartmentTreePanel',{
	extend  : 'Ext.tree.Panel',
	title :'行政区域',
	frame : true,
	titleCollapse : true,
	useArrows : true,
	animate : true,
	treeLeftKeyAction : function(node, record, item, index, e) {
		var queryFormPanel =Ext.getCmp(basedev.baseRegion.CONTENT_ID).getQueryForm();
		var queryForm = queryFormPanel.getForm();
		if(record.data.root){
			queryForm.findField("regionId").setValue('');
			queryForm.findField("regionCode").setValue('');
			queryForm.findField("regionName").setValue('');
			queryForm.findField("regionPinyin").setValue('');
			queryForm.findField("remark").setValue('');
			return;
		}
		if(record.raw.entity.regionLevel =='PROVINCE'){
			
			basedev.baseRegion.REGION_PROVINCE  = record.raw.entity.regionName;
			queryForm.findField("regionName").setFieldLabel("省份名称");
            queryForm.findField("regionPinyin").setFieldLabel("省份拼音");

		}
		if(record.raw.entity.regionLevel =='CITY'){
		   basedev.baseRegion.REGION_CITY = record.raw.entity.regionName;
			queryForm.findField("regionName").setFieldLabel("市级名称");
			queryForm.findField("regionPinyin").setFieldLabel("市拼音");
			/*if(basedev.baseRegion.REGION_PROVINCE !='' && basedev.baseRegion.REGION_PROVINCE != null){
				if(queryForm.findField("be_RegionName")!=null && queryForm.findField("be_RegionName") != undefined){
				    queryFormPanel.remove('be_RegionName');
				}
				var fd = new Ext.form.TextField({
                     fieldLabel: '所属省份',
                    name: 'be_RegionName'
                });
				queryFormPanel.items.add(queryFormPanel.items.getCount(), fd);
				queryFormPanel.doLayout();
				queryFormPanel.getForm().findField("be_RegionName").setValue(basedev.baseRegion.REGION_PROVINCE);
			}*/
		}
		if(record.raw.entity.regionLevel =='DISTRICT'){
		       basedev.baseRegion.REGION_DISTRICT = record.raw.entity.regionName;
		    queryForm.findField("regionName").setFieldLabel("区县名称");
            queryForm.findField("regionPinyin").setFieldLabel("区县拼音");
		}
		if(record.raw.entity.regionLevel =='TOWN'){
			queryForm.findField("regionName").setFieldLabel("乡/街道名称");
            queryForm.findField("regionPinyin").setFieldLabel("乡/街道拼音");
		}
		queryForm.findField("regionId").setValue(record.raw.entity.regionId);
        queryForm.findField("regionCode").setValue(record.raw.entity.regionCode);
        queryForm.findField("regionName").setValue(record.raw.entity.regionName);
        queryForm.findField("regionPinyin").setValue(record.raw.entity.regionPinyin);   
        queryForm.findField("remark").setValue(record.raw.entity.remark);
	//	Ext.getCmp(basedev.baseRegion.CONTENT_ID).getEmpGrid().getPagingToolbar().moveFirst();
		
	},
	treeDbLeftKeyAction : function(node, record, item, index, e) {
		if(record.data.root){
			return;
		}
	},
	changeStatusDeptToString : function(value){
		if(value==true){
		   return value = basedev.baseRegion.i18n('dpap.organization.work');
		}else if(value==false){
		   return value = basedev.baseRegion.i18n('dpap.organization.unWork');
		}
	},
	deptNameQueryField : null,
	getDeptNameQueryField : function() {
		if (Ext.isEmpty(this.deptNameQueryField)) {
			this.deptNameQueryField = Ext.create('Ext.form.field.Text', {
				xtype : 'textfield',
				fieldLabel : '',
				labelPad : 2,
				labelWidth : 40,
				name : 'regionNameQuery',
				width : 150
			});
		}

		return this.deptNameQueryField;
	},
	expandNodes: [],
	expandPaths: function(pathArray) {
		var me = this;
		me.collapseAll();
		var path;
		for (var i = 0; i < pathArray.length; i++) {
			path = pathArray[i];
			me.expandPath(path, 'id', '/', function(success, lastNode){
				if (success) {
					me.expandNodes.push(lastNode);
				}
			});	    						
		}
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.store = Ext.create('BaseData.baseRegion.BaseRegionTreeStore');
		// 监听事件
		me.listeners = {
			itemclick : me.treeLeftKeyAction,
			afteritemexpand: function(node, index, item, eOpts){
				var expandNodes = me.expandNodes,
					flag = true,
					view = me.getView();
				if(expandNodes.length==0){
					return;
				}
				for(var i=0;i<expandNodes.length;i++){
					if(expandNodes[i]==null){
						flag = false;
						continue;
					}
					var nodeHtmlEl = me.getView().getNode(expandNodes[i]),
						nodeEl = Ext.get(nodeHtmlEl);
					if(Ext.isEmpty(nodeEl)){
						continue;
					}
					var divHtmlEl = nodeEl.query('div')[0],
					    divEl = Ext.get(divHtmlEl);
					divEl.highlight("ff0000", { attr: 'color', duration: 2592000000 });
				}
				if(flag){
					me.expandNodes = [];
				}
			}
		};
		me.tbar = [me.getDeptNameQueryField(), {
			xtype : 'label',
			width : 3
		}, {
			text : basedev.baseRegion.i18n('dpap.organization.find'),
			plugins: {
		        ptype: 'buttondisabledplugin',
		        seconds: 2
		    },
			handler : function() {
				var regionName = me.getDeptNameQueryField().getValue();
				if (regionName == null || Ext.String.trim(regionName) == '') {
					Ext.ux.Toast.msg(
							basedev.baseRegion.i18n('dpap.organization.message'), 
							basedev.baseRegion.i18n('dpap.organization.InvalidQueryParam'),
							'error');
					return;
				}
				if (!/^[^\|"'<>%]*$/.test(regionName)) {
					Ext.ux.Toast.msg(
							basedev.baseRegion.i18n('dpap.organization.message'), 
							basedev.baseRegion.i18n('dpap.organization.InvalidQueryParam'),
							'error');
					return;
				}
			if(regionName.length<2){
			Ext.ux.Toast.msg(
                                basedev.baseRegion.i18n('dpap.organization.message'), 
                               "最少输入2个字符", 'error');
                               return ;
			}
				Ext.Ajax.request({
					//url : organization.realPath("querySeq.do"),
					url:'../basedev/querySeq.do',
					jsonData : {'regionName' : regionName},
					success : function(response) {
						json = Ext.decode(response.responseText);
						me.expandPaths(json);
					},
		            exception : function(response) {
		            	var json = Ext.decode(response.responseText); 
			        	Ext.ux.Toast.msg(
			        			basedev.baseRegion.i18n('dpap.organization.message'), 
			        			json.message, 'error');
		            }
				});
			}
		} ];
		me.callParent([cfg]);
	}
});

/**
 * 基本信息表单
 */
Ext.define('BaseData.baseRegion.QueryForm', {
	extend  : 'Ext.form.Panel',
	title : '基本信息',
	frame : true,
	defaultType : 'textfield',
	layout : 'column',
	defaults : {
		columnWidth : .5,
		margin : '5 10 5 10'
	},
	items: [{
	   name : 'regionId',
        xtype : 'hidden'
	},{
		name : 'regionCode',
		xtype : 'hidden'
	},{
		fieldLabel : '省份名称',
		readOnly:true,
		name : 'regionName',
		xtype : 'textfield'
	},{
		fieldLabel :'省份拼音',
		name : 'regionPinyin',
		readOnly:true,
		xtype : 'textfield'
	},{
        fieldLabel :'备注',
        readOnly:true,
        name : 'remark',
        xtype : 'textarea'
    }],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

/*---------------------------------启动项-------------------------------------------*/
/**
 * 启动加载的页面元素及布局
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp(basedev.baseRegion.CONTENT_ID)) {
		return;
	}
	var deptTree = Ext.create('Dpap.organization.DepartmentTreePanel', {
		height: 717,
		columnWidth: .3
	});
	var queryForm = Ext.create('BaseData.baseRegion.QueryForm');
//	var empGrid = Ext.create('Dpap.organization.EmpGrid');
	var content = Ext.create('Ext.panel.Panel', {
		id : basedev.baseRegion.CONTENT_ID,
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'column',
		getQueryForm : function() {
			return queryForm;
		},
	/*	getEmpGrid : function() {
			return empGrid;
		},*/
		getDeptTree : function() {
			return deptTree;
		},
		items : [ deptTree, {
			xtype : 'container',
			columnWidth: 0.7,
			items : [ queryForm/*, empGrid*/ ]
		} ]
	});
	
	Ext.getCmp('T_basedev-baseRegionIndex').add(content);
});