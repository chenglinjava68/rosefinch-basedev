basedev.baseOrg.TAB_ID="T_basedev-baseOrgIndex";  // 组织机构标签页ID
basedev.baseOrg.CONTENT_ID = "T_basedev-baseOrgIndex_content";  // 组织机构内容panel ID
basedev.baseOrg.STATE_ADD = 'add';   // 新增
basedev.baseOrg.STATE_UPDATE = 'update';   // 修改
basedev.baseOrg.ROOT_NODE_ID = 'YCG';   // 根节点id
basedev.baseOrg.ROOT_NODE_PARENT_ID = '-1';  // 根节点parentId
basedev.baseOrg.ROOT_NODE_NAME = '远成集团';  // 根节点名称

basedev.baseOrg.BL_FLAG_CHECKED='1';    // 组织机构启用
basedev.baseOrg.BL_FLAG_UNCHECKED='0';  // 组织机构停用

// 基本信息新增/修改 表单ID
basedev.baseOrg.EDIT_BASE_ORG_INFO_FORM_ID = "T_basedev-editBaseOrgInfoForm";

// 组织类型combobox
basedev.baseOrg.ORG_TYPE_COMBOBOX_ID = "T_basedev-orgTypeCombobox";
// 省combobox
basedev.baseOrg.REGION_PROVINCE_COMBOBOX_ID = "T_basedev-regionProvinceCombobox";
// 市combobox
basedev.baseOrg.REGION_CITY_COMBOBOX_ID = "T_basedev-regionCityCombobox";
// 区（县）combobox
basedev.baseOrg.REGION_DISTRICT_COMBOBOX_ID = "T_basedev-regionDistrictCombobox";


//-----------------------------------------------
/**
 * 省Store
 */
Ext.define('BaseData.region.ProvinceStore', {
	extend : 'Ext.data.Store',
//	autoDestroy : true,
//	autoLoad : true,
//	autoSync : true,
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
//	autoDestroy : true,
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
			var provinceCode = Ext.getCmp(basedev.baseOrg.REGION_PROVINCE_COMBOBOX_ID)
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
//	autoDestroy : true,
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
			var cityCode = Ext.getCmp(basedev.baseOrg.REGION_CITY_COMBOBOX_ID)
					.getValue();
	        Ext.apply(store.proxy.extraParams, {
	        	regionParent : cityCode
			});
		}
	}
});



// -----------------------------------
/**
 * 组织机构树 Store
 */
Ext.define('BaseData.baseOrg.DepartmentTreeStore', {
	extend : 'Ext.data.TreeStore',
	proxy : {
		type : 'ajax',
		url : basedev.realPath('getBaseOrgList.do')
	}/*,
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}*/
});

Ext.define('BaseData.baseOrg.DepartmentTreePanel',{
	extend  : 'Ext.tree.Panel',
	frame : true,
	frameHeader : true,
	titleCollapse : true,
	useArrows : true,
	animate : true,
	deptNameQueryField : null,
	getDeptNameQueryField : function(){
		if(this.deptNameQueryField==null){
			this.deptNameQueryField = Ext.create('Ext.form.field.Text',{
				xtype : 'textfield',
				labelWidth : 80,
				name : 'deptNameQuery',
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
	treeLeftKeyAction : function(node, record, index, e) {
		var content = Ext.getCmp(basedev.baseOrg.CONTENT_ID);
		
		var baseOrgInfoForm = content.getBaseOrgInfoForm();
		
		// ROOT节点直接禁用按钮
		if(record.data.root){
			// 表单重置
			baseOrgInfoForm.getForm().reset();
//			baseSiteInfoForm.getForm().reset();
			
			// “修改”按钮失效
//			baseOrgInfoForm.getUpdateButton().setDisabled(true);
			
			// “停用”按钮失效
			baseOrgInfoForm.getDisableButton().setDisabled(true);
			// “启用”按钮失效
			baseOrgInfoForm.getEnableButton().setDisabled(true);
			return;
		}
		
		var blFlag = record.raw.entity.blFlag;
		if(blFlag == '1'){   // 已启用
			baseOrgInfoForm.getDisableButton().setDisabled(false);
			baseOrgInfoForm.getEnableButton().setDisabled(true);
//			baseOrgInfoForm.getUpdateButton().setDisabled(false);
//			baseOrgInfoForm.getAddButton().setDisabled(false);
		} else {
			baseOrgInfoForm.getDisableButton().setDisabled(true);
			baseOrgInfoForm.getEnableButton().setDisabled(false);
//			baseOrgInfoForm.getUpdateButton().setDisabled(true);
//			baseOrgInfoForm.getAddButton().setDisabled(true);
		}
		
		baseOrgInfoForm.loadFormDataFromTree(record);
	},
	onRefreshTreeNodes: function(treeStore,parentId){
		var node = treeStore.getNodeById(parentId); 
		treeStore.load({
			node : node
		});
	},
	reFresh : function() {  // 刷新树节点
		var selects = this.getSelectionModel().getSelection();
		if(selects.length==0){
			return;
		}
		
		var record = selects[0];
		// 如果是root节点
		if (record.data.root) {
			this.onRefreshTreeNodes(this.getStore(), record.data.id);
			return;
		}
		
		this.onRefreshTreeNodes(this.getStore(), record.data.parentId);
	},
	constructor : function(config) {
		var me = this, 
			cfg = Ext.apply({}, config);
		me.store = Ext.create('BaseData.baseOrg.DepartmentTreeStore',{
			root : config.rootNode
		});
		// 监听事件
		me.listeners = {
				select : me.treeLeftKeyAction,
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
					
					// 高亮显示时间（单位：毫秒）
					divEl.highlight("ff0000", { attr: 'color', duration: 3600000 });
				}
				if(flag){
					me.expandNodes = [];
				}
			}
		};
		
		me.tbar = [
					me.getDeptNameQueryField(),
					{
						xtype : 'button',
						text : '查询',
						plugins : Ext
								.create(
										'Deppon.ux.ButtonLimitingPlugin',
										{
											seconds : 3
										}),
						handler : function() {
							var orgName = me
									.getDeptNameQueryField()
									.getValue();
							
							if (Ext.String
									.trim(orgName) == null
									|| Ext.String
											.trim(orgName) == "") {
								Ext.ux.Toast.msg('提示','查询条件不能为空','error');
								return;
							}
							
							
							if(Ext.String.trim(orgName).length <2){
								Ext.ux.Toast.msg('提示','输入值至少包含两个字符','error');
								return;
							}
							
							
							if (!/^[^\|"'<>%@#!&\$\*]*$/
									.test(orgName)) {
								Ext.ux.Toast.msg('提示','输入值不能包含特殊符号','error');
								return;
							}
							
							
							
							
							
							Ext.Ajax
									.request({
										url : basedev.realPath("queryByOrgName.do"),
										params : {
											'orgName' : orgName
										},
										success : function(response) {
											jsonArr = Ext.decode(response.responseText);
											me.expandPaths(jsonArr);
										},
										failure : function(response) {
											var json = Ext.decode(response.responseText);
											Ext.ux.Toast.msg('提示',json.message,'error');
										}
									});
						}
					},{
						xtype :'button',
						text : '刷新',
						handler : me.reFresh,
						scope : this
					} ];
		
		me.callParent([cfg]);
	}
});

Ext.define('BaseData.baseOrg.BaseOrgModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'orgCode'
	}, {
		name : 'orgNameShort'
	}, {
		name : 'orgName'
	}, {
		name : 'upOrgCode'
	}, {
		name : 'upOrgName'
	}, {
		name : 'orgType'
	}, {
		name : 'orgTypeName'
	}, {
		name : 'defaultCurrency'
	}, {
		name : 'defaultCurrencyName'
	}, {
		name : 'transferNotifyMobile'
	}, {
		name : 'blFlag'
	}, {
		name : 'orderBy'
	}, {
		name : 'province'
	}, {
		name : 'provinceName'
	}, {
		name : 'city'
	}, {
		name : 'cityName'
	}, {
		name : 'county'
	}, {
		name : 'countyName'
	}, {
		name : 'orgAddress'
	}, {
		name : 'remark'
	} ]
});

/**
 * 组织机构基本信息
 */
Ext.define('BaseData.baseOrg.BaseOrgInfoForm', {
	extend : 'Ext.form.Panel',
	title: '基本信息',
	frame: true,
    defaults: {
    	margin:'5 10 5 10',
    	labelWidth: 120,
    	readOnly: true
    },
    layout : {
    	type : 'table',
    	columns: 2
    },
    items : [ {
		xtype : 'textfield',
		name : 'orgCode',
		fieldLabel : '组织编号'
	}, {
		xtype : 'textfield',
		name : 'orgNameShort',
		fieldLabel : '组织简称'
	}, {
		xtype : 'textfield',
		name : 'orgName',
		fieldLabel : '组织全称'
	}, {
		xtype : 'textfield',
		name : 'upOrgName',
		fieldLabel : '上级组织'
	}, {
		xtype : "textfield",
		name : 'orgTypeName',
		fieldLabel : '组织类型'
	}, {
		xtype : "textfield",
		name : 'defaultCurrencyName',
		fieldLabel : '本位币币别'
	}, {
		xtype : 'textfield',
		name : 'transferNotifyMobile',
		fieldLabel : '转账通知手机'
	}, {
		xtype : 'checkbox',
		name : 'blFlag',
		boxLabel : '启用'
	}, {
		xtype : 'textfield',
		name : 'orderBy',
		fieldLabel : '排序号'
	}, /*{
		xtype : 'textfield',
		name : 'provinceName',
		fieldLabel : '省份'
	}, {
		xtype : "textfield",
		name : 'cityName',
		fieldLabel : '市'
	}, {
		xtype : "textfield",
		name : 'countyName',
		fieldLabel : '区（县）'
	}, {
		xtype : 'textfield',
		name : 'orgAddress',
		fieldLabel : '所在地址'
	},*/ {
		xtype : 'textfield',
		name : 'remark',
		fieldLabel : '备注'
	} ],
	// 新增按钮
	addButton: null,
	getAddButton: function() {
		var me = this;
		if(Ext.isEmpty(me.addButton)){
			me.addButton = Ext.create('Ext.Button', {
				text : '新增',
				handler : function() {
					var window = me.getBaseOrgInfoWindow();
					window.setTitle('新增组织机构');
					
					window.getEditBaseOrgInfoForm().setOperatorType(basedev.baseOrg.STATE_ADD);
					window.show();
				}
			});
		}
		return this.addButton;
	},
	
	// 修改按钮
	updateButton: null,
	getUpdateButton: function() {
		var me = this;
		if(Ext.isEmpty(me.updateButton)){
			me.updateButton = Ext.create('Ext.Button', {
				text : '修改',
				disabled: true,
				handler : function() {
					var window = me.getBaseOrgInfoWindow();
					var record = me.getRecord();
					window.setTitle('编辑组织机构');
					window.getEditBaseOrgInfoForm().setOperatorType(basedev.baseOrg.STATE_UPDATE, record);
					
					window.show();
				}
			});
		}
		return this.updateButton;

	},
	// 启用按钮
	enableButton: null,
	getEnableButton: function() {
		var me = this;
		if (Ext.isEmpty(me.enableButton)) {
			me.enableButton = Ext.create('Ext.Button', {
				text : '启用',
				disabled: true,
				handler : function() {
		    		var functionData = me.getRecord().getData();
		    		var record = me.getRecord();
		    		var orgCode = record.data.orgCode;
		    		
		    		var paramsObj = {orgCode : orgCode};
		    		
		    		Ext.Msg.confirm('确认',
						'确认启用吗？',
						function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
							        url : basedev.realPath('enableBaseOrg.do'),
							        params : paramsObj,
							        success : function(response) {
							        	var result = Ext.JSON.decode(response.responseText);
							        	
							        	if(result.success){
							        		Ext.ux.Toast.msg('提示', '启用成功');
							        		
							        		var treePanel = Ext.getCmp(basedev.baseOrg.CONTENT_ID).getDeptTree();
							        		
							        		treePanel.getStore().load({
								        	    scope: this,
								        	    node : treePanel.getStore().getNodeById(basedev.baseOrg.ROOT_NODE_ID),
								        	    callback: function(records, operation, success) {
								        	    	treePanel.expandPath(result.data[1], 'id', '/', function(success, lastNode){
								        				if (success) {
								        					treePanel.getSelectionModel().select(treePanel.getStore().getNodeById(result.data[0]));
								        				}
								        			});	   
								        	    }
								        	});
											
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
			});
		}
		
		return this.enableButton;
	},
	// 停用按钮
	disableButton: null,
	getDisableButton: function() {
		var me = this;
		if (Ext.isEmpty(me.disableButton)) {
			me.disableButton = Ext.create('Ext.Button', {
				text : '停用',
				disabled: true,
				handler : function() {
		    		var record = me.getRecord();
		    		var orgCode = record.data.orgCode;
		    		var paramsObj = {orgCode : orgCode};
		    		
		    		
		    		Ext.Msg.confirm('确认',
						'确认停用吗？',
						function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
							        url : basedev.realPath('disableBaseOrg.do'),
							        params: paramsObj,
							        success : function(response) {
							        	var result = Ext.JSON.decode(response.responseText);
							        	
							        	if(result.success){
							        		Ext.ux.Toast.msg('提示', '停用成功');
							        		
							        		var treePanel = Ext.getCmp(basedev.baseOrg.CONTENT_ID).getDeptTree();
							        		
											treePanel.getStore().load({
								        	    scope: this,
								        	    node : treePanel.getStore().getNodeById(basedev.baseOrg.ROOT_NODE_ID),
								        	    callback: function(records, operation, success) {
								        	    	treePanel.expandPath(result.data[1], 'id', '/', function(success, lastNode){
								        				if (success) {
								        					treePanel.getSelectionModel().select(treePanel.getStore().getNodeById(result.data[0]));
								        				}
								        			});	   
								        	    }
								        	});
											
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
			});
		}
		
		return this.disableButton;
	},
	/**
	 * 从功能树中加载数据到form表单中
	 */
	loadFormDataFromTree : function(record) {
		var me = this,form = me.getForm();
		
		/*
		var baseOrgModel = Ext.create('BaseData.baseOrg.BaseOrgModel', record.raw.entity);
		// 加载表单数据
		me.loadRecord(baseOrgModel);
		*/
		
		//---------------
		var paramsObj = {orgCode : record.raw.entity.orgCode};
		Ext.Ajax.request({
	        url : basedev.realPath('getBaseOrgEntity.do'),
	        params: paramsObj,
	        success : function(response) {
	        	var result = Ext.JSON.decode(response.responseText);
	        	
	        	if(result.success){
	        		var baseOrgModel = Ext.create('BaseData.baseOrg.BaseOrgModel', result.data);
	        		
	        		// 加载组织表单数据
	        		me.loadRecord(baseOrgModel);
	        	}else{
	        		Ext.ux.Toast.msg('提示', result.msg);
	        	}
	        },
	        failure : function(response) {
	        	Ext.ux.Toast.msg('提示',response.responseText, 'error');
	        }
	    });
	},
	baseOrgInfoWindow : null,
	getBaseOrgInfoWindow: function(){
//		if (Ext.isEmpty(this.baseOrgInfoWindow)) {
			this.baseOrgInfoWindow = Ext.create('BaseData.baseOrg.BaseOrgInfoWindow');			
//		}
		return this.baseOrgInfoWindow;
	},
	constructor : function(config) {
		var cfg = Ext.apply({}, config);
		this.tbar = [/*this.getAddButton(), this.getUpdateButton(), */this.getEnableButton(), this.getDisableButton()];
		this.callParent([cfg]); 
	}
});


Ext.define('BaseData.baseOrg.EditBaseOrgInfoForm', {
	extend : 'Ext.form.Panel',
	id : basedev.baseOrg.EDIT_BASE_ORG_INFO_FORM_ID,
//	title: '基本信息',
	frame: true,
    defaults: {
    	margin:'5 10 5 10',
    	labelWidth: 120,
    	allowBlank: false,
	    validateOnBlur: false,
	    validateOnChange: false
    },
    layout : {
    	type : 'table',
    	columns: 2
    },
    items : [ {
		xtype : 'textfield',
		name : 'orgCode',
		fieldLabel : '组织编号',
		maxLength:64,
		validateOnBlur : true,
		regex : /(^[A-Za-z0-9]{8}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{12}$)|(^[A-Za-z0-9]+$)/,
		regexText : '该输入项只能输入数字、字母及横杠的组合',
		validator : function(field){
			if(!field){
				return true;
			}
			
			var editBaseOrgInfoForm = Ext.getCmp(basedev.baseOrg.EDIT_BASE_ORG_INFO_FORM_ID);
			var state = editBaseOrgInfoForm.getOperatorType();
			if(basedev.baseOrg.STATE_UPDATE == state){
				return true;
			}
			
			var paramsObj = {orgCode : field};
			var valid = false;
			Ext.Ajax.request({
		        url : basedev.realPath('uniqueCheckByOrgCode.do'),
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
			return '该组织编号已存在';
		}
	}, {
		xtype : 'textfield',
		name : 'orgNameShort',
		fieldLabel : '组织简称',
		maxLength:15,
		validateOnBlur: true,
	    validateOnChange: false,
		validator : function(field){
			if(!field){
				return true;
			}
			
			var editBaseOrgInfoForm = Ext.getCmp(basedev.baseOrg.EDIT_BASE_ORG_INFO_FORM_ID);
			var orgCode = editBaseOrgInfoForm.getForm().findField("orgCode").getValue();
			
			// 上级组织编码
			var upOrgCode = this.up('form').getForm().findField("upOrgCode").getValue();
			
			var paramsObj = {
				orgCode : orgCode,
				orgNameShort : field,
				state : editBaseOrgInfoForm.getOperatorType(),
				upOrgCode : upOrgCode
			};
			
			var valid = false;
			Ext.Ajax.request({
		        url : basedev.realPath('uniqueCheckByOrgNameShort.do'),
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
			return '该组织简称已存在';
		}
	}, {
		xtype : 'textfield',
		name : 'orgName',
		fieldLabel : '组织全称',
		maxLength:33,
		validateOnBlur: true,
	    validateOnChange: false,
		validator : function(field){
			if(!field){
				return true;
			}
			
			var editBaseOrgInfoForm = Ext.getCmp(basedev.baseOrg.EDIT_BASE_ORG_INFO_FORM_ID);
			var orgCode = editBaseOrgInfoForm.getForm().findField("orgCode").getValue();
			
			// 上级组织编码
			var upOrgCode = this.up('form').getForm().findField("upOrgCode").getValue();
			
			var paramsObj = {
				orgCode : orgCode,
				orgName : field,
				state : editBaseOrgInfoForm.getOperatorType(),
				upOrgCode : upOrgCode
			};
			
			var valid = false;
			Ext.Ajax.request({
		        url : basedev.realPath('uniqueCheckByOrgName.do'),
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
			return '该组织全称已存在';
		}
	}, {
		xtype : 'commonOrgSelector',
		name : 'upOrgCode',
		fieldLabel : '上级组织'
	}, {
		id : basedev.baseOrg.ORG_TYPE_COMBOBOX_ID,
		xtype : "dictcombo",
		dictType : 'ORG_TYPE',
		name : 'orgType',
		fieldLabel : '组织类型',
		editable : false
	}, {
		xtype : "dictcombo",
		dictType : 'DEFAULT_CURRENCY',
		name : 'defaultCurrency',
		fieldLabel : '本位币币别',
		editable : false
	}, {
		xtype : 'textfield',
		name : 'transferNotifyMobile',
		fieldLabel : '转账通知手机',
		allowBlank: true,
		vtype : 'mobile'
	}, {
		xtype : 'checkboxfield',
		name : 'blFlag',
		boxLabel : '启用',
		inputValue: '1',
		uncheckedValue: '0'
	}, {
		xtype : 'numberfield',
		name : 'orderBy',
		fieldLabel : '排序号',
		minValue : 1
	}, /*{
		xtype : 'combo',
		id : basedev.baseOrg.REGION_PROVINCE_COMBOBOX_ID,
		name : 'province',
		fieldLabel : '省份',
		store : Ext.create('BaseData.region.ProvinceStore'),
		displayField : 'regionName',
		valueField : 'regionCode',
//		queryMode : 'local',
		editable : false,
		listeners: {
		    select: function(combo,records,eOpts) {
		    	var cityCombo = Ext.getCmp(basedev.baseOrg.REGION_CITY_COMBOBOX_ID);
		    	
		    	cityCombo.clearValue();
                var cityStore=cityCombo.getStore();
                cityStore.load();
                
                // 清除 区（县）
                var districtCombo = Ext.getCmp(basedev.baseOrg.REGION_DISTRICT_COMBOBOX_ID);
            	districtCombo.clearValue();
		    }
		}
	}, {
		xtype : "combo",
		id : basedev.baseOrg.REGION_CITY_COMBOBOX_ID,
		name : 'city',
		fieldLabel : '市',
		store : Ext.create('BaseData.region.CityStore'),
		queryMode : 'local',
		displayField : 'regionName',
		valueField : 'regionCode',
		editable : false,
		listeners: {
		    select: function(combo,records,eOpts) {
		    	var districtCombo = Ext.getCmp(basedev.baseOrg.REGION_DISTRICT_COMBOBOX_ID);
		    	
		    	districtCombo.clearValue();
                var districtStore=districtCombo.getStore();
                districtStore.load();
		    }
		}
	}, {
		xtype : "combo",
		id : basedev.baseOrg.REGION_DISTRICT_COMBOBOX_ID,
		name : 'county',
		fieldLabel : '区（县）',
		store : Ext.create('BaseData.region.DistrictStore'),
		queryMode : 'local',
		displayField : 'regionName',
		valueField : 'regionCode',
		editable : false
	}, {
		xtype : 'textfield',
		name : 'orgAddress',
		fieldLabel : '所在地址',
		maxLength : 200
	},*/ {
		xtype : 'textfield',
		name : 'remark',
		fieldLabel : '备注',
		allowBlank: true,
		maxLength : 200
	} ],
	operatorType : null,
	setOperatorType: function(state, record) {
		this.operatorType = state;
		var form = this.getForm();
		form.reset();
		
		var comboboxBaseOrgModel = Ext.create('BaseData.commonSelector.BaseOrgModel');
		
		if (state == basedev.baseOrg.STATE_ADD) {
			// 组织编号可编辑
			this.setFormFieldsReadOnly(false);
			record = Ext.create('BaseData.baseOrg.BaseOrgModel');
			
			//--------------start---------------
			// 触发select事件
			/*var orgTypeCombo = Ext.getCmp(basedev.baseOrg.ORG_TYPE_COMBOBOX_ID);
			record.data.valueCode = '';
			orgTypeCombo.fireEvent('select',orgTypeCombo,[record]);*/
			//---------------end--------------
			
			var content = Ext.getCmp(basedev.baseOrg.CONTENT_ID);
			var deptTree = content.getDeptTree();
			
			var selectionModel = deptTree.getSelectionModel().getSelection();
			
			if (selectionModel.length > 0) {
				comboboxBaseOrgModel.data.orgCode = (selectionModel[0]).data.id;
				comboboxBaseOrgModel.data.orgName = (selectionModel[0]).data.text;
			} else {
				comboboxBaseOrgModel.data.orgCode = basedev.baseOrg.ROOT_NODE_ID;
				comboboxBaseOrgModel.data.orgName = basedev.baseOrg.ROOT_NODE_NAME;
			}
			
			record.data.blFlag = basedev.baseOrg.BL_FLAG_CHECKED;
			
			// upOrgCode是上级组织
			record.data.upOrgCode = comboboxBaseOrgModel;
			
			form.loadRecord(record);
			
		} else {
			// 组织编号不可编辑
			this.setFormFieldsReadOnly(true);
			
			//--------------start---------------
			// 触发select事件
			/*var orgTypeCombo = Ext.getCmp(basedev.baseOrg.ORG_TYPE_COMBOBOX_ID);
			record.data.valueCode = record.data.orgType;
			orgTypeCombo.fireEvent('select',orgTypeCombo,[record]);*/
			//---------------end--------------
			
			comboboxBaseOrgModel.data.orgCode = record.data.upOrgCode;
			comboboxBaseOrgModel.data.orgName = record.data.upOrgName;
			// 上级组织
			record.data.upOrgCode = comboboxBaseOrgModel;
			
			// 加载数据
			form.loadRecord(record);
			
			/*var cityCombo = Ext.getCmp(basedev.baseOrg.REGION_CITY_COMBOBOX_ID);
	    	cityCombo.clearValue();
            var cityStore=cityCombo.getStore();
            cityStore.load(function(){
            	cityCombo.setValue(record.data.city);
            	
            	var districtCombo = Ext.getCmp(basedev.baseOrg.REGION_DISTRICT_COMBOBOX_ID);
            	districtCombo.clearValue();
				districtCombo.getStore().load(function(){
					districtCombo.setValue(record.data.county);
				});
            });*/
		}
		
	},
	getOperatorType : null,
	getOperatorType : function(){
		return this.operatorType;
	},
	
	setFormFieldsReadOnly: function(flag) {
		var form = this.getForm();
		form.findField('orgCode').setReadOnly(flag);
	},
	constructor : function(config) {
		var me = this;
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]); 
	}
});


Ext.define('BaseData.baseOrg.BaseOrgInfoWindow', {
	extend: 'Ext.window.Window',
	width: 700,
	modal: true,
	closeAction: 'destroy',
//	title : '新增组织机构',
	
	editBaseOrgInfoForm : null,
	getEditBaseOrgInfoForm: function(){
		if (Ext.isEmpty(this.editBaseOrgInfoForm)) {
			this.editBaseOrgInfoForm = Ext.create("BaseData.baseOrg.EditBaseOrgInfoForm");
		}
		return this.editBaseOrgInfoForm;
	},
	/*editBaseSiteInfoForm : null,
	getEditBaseSiteInfoForm: function(){
		if (Ext.isEmpty(this.editBaseSiteInfoForm)) {
			this.editBaseSiteInfoForm = Ext.create("BaseData.baseOrg.EditBaseSiteInfoForm");
		}
		return this.editBaseSiteInfoForm;
	},*/
	
	// 取消按钮
	cancelButton : null,
	getCancelButton:function(){
		var me = this;
		if (Ext.isEmpty(this.cancelButton)) {
			this.cancelButton = Ext.create('Ext.button.Button',{
				text: '取消',
				handler: function(){
					me.close();
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
					var orgForm = me.getEditBaseOrgInfoForm().getForm();
//					var siteForm = me.getEditBaseSiteInfoForm().getForm();
					
					// 校验基本信息表单
					if (!orgForm.isValid()) {
						return;
					}
					
					// 基本信息表单数据
					var data = orgForm.getValues();
					
					/*var orgTypeCombo = Ext.getCmp(basedev.baseOrg.ORG_TYPE_COMBOBOX_ID);
					var orgTyepValue = orgTypeCombo.getSubmitValue();*/
					
					// 是否是网点
			        /*if(orgTyepValue == '1' || orgTyepValue == '6' || orgTyepValue == '7' || orgTyepValue == '8'){
			        	// 校验网点信息表单
			        	if (!siteForm.isValid()) {
							return;
						}
			        	
			        	var siteFormData = siteForm.getValues();
			        	data.baseSiteVo = siteFormData;
			        }*/
					
					var url = '';
					if (me.getEditBaseOrgInfoForm().getOperatorType() == basedev.baseOrg.STATE_ADD) {
						url = basedev.realPath('insertBaseOrg.do');
					} else {
						url = basedev.realPath('updateBaseOrg.do');
					}
					
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
					        	me.close();
					        	
					        	var content = Ext.getCmp(basedev.baseOrg.CONTENT_ID);
								var treePanel = content.getDeptTree();
					        	
								treePanel.getStore().load({
					        	    scope: this,
					        	    node : treePanel.getStore().getNodeById(basedev.baseOrg.ROOT_NODE_ID),
					        	    callback: function(records, operation, success) {
					        	    	treePanel.expandPath(result.data[1], 'id', '/', function(success, lastNode){
					        				if (success) {
					        					treePanel.getSelectionModel().select(treePanel.getStore().getNodeById(result.data[0]));
					        				}
					        			});	   
					        	    }
					        	});
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
			me.getEditBaseOrgInfoForm()
	    ];
		me.buttons = [
			me.getCancelButton(),
			me.getSaveButton()
		];
		
		/*me.listeners = {
			beforedestroy : function(thisCom, eOpts ){
				if(me.editBaseOrgInfoForm){
					me.editBaseOrgInfoForm.removeAll();
					me.editBaseOrgInfoForm.destroy();
				}
			}
		};*/
		
		me.callParent([cfg]);
	}
});


Ext.onReady(function() {
	Ext.QuickTips.init();
	
	// ----------validators---------
	Ext.apply(Ext.form.field.VTypes, {
		// 手机
	    mobile: function(val, field) {
	    	var mobileRegex = /1\d{10}/;
	        return mobileRegex.test(val);
	    },
	    mobileText: '手机号码不正确'
	});
	// ----------------------------
	
	
	if (Ext.getCmp(basedev.baseOrg.CONTENT_ID)) {
		return;
	}
	
	var deptTree = Ext.create('BaseData.baseOrg.DepartmentTreePanel', {
		header : true,
		columnWidth : .3,
		height: 730,
		title : '组织架构',
		rootNode : {
			id : basedev.baseOrg.ROOT_NODE_ID,
			parentId : basedev.baseOrg.ROOT_NODE_PARENT_ID,
			text : basedev.baseOrg.ROOT_NODE_NAME,
			checked : null,
			expanded : true
		}
	});
		
	var baseOrgInfoForm = Ext.create("BaseData.baseOrg.BaseOrgInfoForm");
	
	var contentPanel = Ext.create('Ext.panel.Panel', {
		id : basedev.baseOrg.CONTENT_ID,
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		layout : 'column',
		getBaseOrgInfoForm : function(){
			return baseOrgInfoForm;
		},
		getDeptTree : function(){
			return deptTree;
		},
		items : [ deptTree, {
			xtype : 'container',
			margin : '0 0 0 15',
			columnWidth: .7,
			items : [ baseOrgInfoForm ]
		}]
	});
	
	Ext.getCmp(basedev.baseOrg.TAB_ID).add(contentPanel);
});

