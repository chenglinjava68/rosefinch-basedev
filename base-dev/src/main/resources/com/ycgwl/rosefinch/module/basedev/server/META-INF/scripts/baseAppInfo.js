/** 此模块主页面的ID */
basedev.baseAppInfo.CONTENT_ID  = 'T_basedev-baseAppInfo_content';
basedev.baseAppInfo.FORMAT_TIME  = 'Y-m-d H:i:s'; //格式化时间字符串

/** 主表model */
Ext.define('BaseData.baseAppInfo.BaseAppInfoModule', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'appId',
		type : 'long'
	}, {
		name : 'appKey',
		type : 'string'
	}, {
		name : 'appName',
		type : 'string'
	}, {
		name : 'appSecret',
		type : 'string'
	}, {
		name : 'createUserCode',
		type : 'string'
	},{
		name : 'modifyUserCode',
		type : 'string'
	},
	{
		name : 'timeoutMillis',
		type : 'int'
	},
	{
		name : 'timestampMinutes',
		type : 'int'
	},
	{
		name : 'ipWhiteList',
		type : 'string'
	}
	,{
		name : 'createTime',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'number'
	},{
		name : 'modifyTime',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'number'
	}]
});

/**
 *  查询store
 */
Ext.define('BaseData.baseAppInfo.BaseAppInfoStore',{
	extend: 'Ext.data.Store',
	model : 'BaseData.baseAppInfo.BaseAppInfoModule',
	autoLoad: true,
	proxy : {
		url : '../basedev/getBaseAppInfoList.do',
		type : 'ajax',
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp("BaseData_baseAppInfo_BaseAppInfoForm_ID"); //查询表单
			if (queryForm != null) { 
				var appKey = queryForm.getForm().findField('t_base_appKey').getValue();
				var appName = queryForm.getForm().findField('t_base_appName').getValue();

				Ext.apply(operation, {
					params : {
						'q_str_appKey' : appKey,
						'q_str_appName':appName,
						'q_int_delFlag':0
					}
				});	
			}
		}
	}
});



/**
 * 查询表单
 */
Ext.define('BaseData.baseAppInfo.BaseAppInfoForm', {
	extend:'Ext.form.Panel',
	id: 'BaseData_baseAppInfo_BaseAppInfoForm_ID',
	frame : true,
	title: '查询',//查询
	defaults: {
		margin:'5 10 5 10'
	},
	layout : 'column',	
	defaultType : 'textfield',
	siteSelectWindow:null,
	initComponent: function(){
		var me = this;
		me.items = [{

			name: 't_base_appKey',
			fieldLabel:'应用系统KEY',
			columnWidth: .2
		},
		{
			name: 't_base_appName',
			fieldLabel:'应用系统名称',
			columnWidth: .2

		},{
			xtype : 'button',
			cls: 'yellow_button',
			text:"查询",
			width : 70,
			// 查询按钮的响应事件：
			handler: function() {
				var selectResultPanel = Ext.getCmp("BaseData_BaseAppInfo_BaseAppInfoPageElementGridPanel");
				selectResultPanel.setVisible(true);
				selectResultPanel.getPagingToolbar().moveFirst();
			}
		}];
		me.callParent();
	}
});

/**
 * 列表panel
 */
Ext.define('BaseData.baseAppInfo.BaseAppInfoPageElementGridPanel', {
	extend: 'Ext.grid.Panel',
	id:'BaseData_BaseAppInfo_BaseAppInfoPageElementGridPanel',
	title : '明细列表',//
	frame: true,
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	selType : "rowmodel", // 选择类型设置为：行选择
	baseAppInfoChangeWindow : null,
	baseAppInfoAddWindow: null,

	getBaseAppInfoAddWindow : function(){
			this.baseAppInfoAddWindow = Ext.create('BaseData.BaseAppInfo.BaseAppInfoAddWindow');
		return this.baseAppInfoAddWindow;
	},
	getBaseAppInfoChangeWindow : function(data){
			this.baseAppInfoChangeWindow = Ext.create('BaseData.BaseAppInfo.BaseAppInfoChangeWindow');

		this.baseAppInfoChangeWindow.editData = data;
		return this.baseAppInfoChangeWindow;
	},


	// 新增按钮
	addButton: null,
	getAddButton: function() {
		var me = this;
		if(Ext.isEmpty(me.addButton)){
			me.addButton = Ext.create('Ext.Button', {
				text :'新增',//新增
				handler : function() {
					me.getBaseAppInfoAddWindow().show();
				}
			});
		}
		return this.addButton;
	},
	loadValuationStore: function(parentCode) {
		this.getStore().load();
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
	constructor : function(config) {
		var me = this;
		me.store = Ext.create('BaseData.baseAppInfo.BaseAppInfoStore');
		me.bbar = me.getPagingToolbar();
		me.tbar = [me.getAddButton()];
		me.columns = [{
			xtype:'actioncolumn',
			text: '修改',
			width:50,
			items: [{
				iconCls: 'deppon_icons_edit',
				tooltip: '修改',//修改
				handler: function(grid, rowIndex, colIndex) { // 修改按钮
					var rec = grid.getStore().getAt(rowIndex); 
					Ext.Ajax.request({
						url : "../basedev/getByAppKey.do",
						params : {
							'appKey' : rec.get('appKey')
						},
						success : function(response) {

							var data  =  Ext.JSON.decode(response.responseText);
							if(data.success){ 
								var xWindow = me.getBaseAppInfoChangeWindow(data);
								xWindow.show();
							}else{ 
								Ext.Msg.alert("获取数据失败"); 
							}
						},
						exception : function(response) {
							var json = Ext.decode(response.responseText);
						}
					});

				}
			}, {
				iconCls: 'deppon_icons_delete',
				tooltip:'删除',//删除
				handler: function(grid, rowIndex, colIndex) {
					/*删除*/
					var rowInfo = Ext.getCmp('BaseData_BaseAppInfo_BaseAppInfoPageElementGridPanel').store.getAt(rowIndex);
					var appId= rowInfo.data.appId;
					var appKey = rowInfo.data.appKey;
					Ext.Msg.confirm('yes', 
							'yes',
							function(btn){
						if(btn=='yes'){
							Ext.Ajax.request({
								url : '../basedev/deleteBaseAppInfoById.do',
								params: {
									'appId': appId,
									'appKey':appKey
								},
								async: false,
								success : function(response) {
									var json = Ext.decode(response.responseText); 
									Ext.ux.Toast.msg('提示', json.msg, 'success');
									var selectResultPanel = Ext.getCmp("BaseData_BaseAppInfo_BaseAppInfoPageElementGridPanel");
									selectResultPanel.getPagingToolbar().moveFirst();

								},
								exception : function(response) {
									var json = Ext.decode(response.responseText); 
									Ext.ux.Toast.msg('提示', json.msg, 'error');
								}
							});
						}
					});

				}
			}]
		}, {
			hidden : true,
			text:'应用系统ID',
			dataIndex : 'appId'
		},{
			text : '应用系统KEY',
			dataIndex : 'appKey'
		},{
			text : '应用系统名称',
			dataIndex : 'appName'
		},
		{
			text : '密钥',
			dataIndex : 'appSecret'
		},
		{
			text : '创建人',
			dataIndex : 'createUserCode'
		},
		{
			text : '修改人',
			dataIndex : 'modifyUserCode'
		},
		{
			xtype: 'datecolumn',
			format : 'Y-m-d H:i:s',
			width:130,
			text : '创建时间',
			dataIndex : 'createTime'
		},{
			
			xtype: 'datecolumn',
			format : 'Y-m-d H:i:s',
			width:130,
			text : '修改时间',
			dataIndex : 'modifyTime'
		}

		];
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
/**
 * 新增接口信息表单
 */
Ext.define('BaseData.BaseInterfaceInfo.AddChangeForm', {
	extend: 'Ext.form.Panel',
	defaultType: 'textfield',
	defaults: {
		labelWidth: 100,
		width: 320
	},
	initComponent: function() {
		var me = this;	
		me.items = [{ 
			xtype: 'textfield',
			name: 'interfaceCode',
			fieldLabel: '接口编号',
			allowBlank: false  
		},	
		{
			xtype: 'textfield',
			name: 'interfaceName',
			fieldLabel: '接口名称',
			allowBlank: false 
		},
		{
			xtype: 'textfield',
			name: 'productName',
			fieldLabel: '产品名称',
			allowBlank: false  
		},{
			xtype: 'textfield',
			name: 'url',
			fieldLabel: '主URL',
			allowBlank: false 
		},{
			name: 'returnUrl',
			fieldLabel: '页面回调',
			allowBlank: true
		},
		{
			name: 'notifyUrl',
			fieldLabel: '后台回调',
			allowBlank: true 
		},
		{
			name: 'pre1',
			fieldLabel: '备用参数1',
			allowBlank: true 
		}
		];
		me.callParent();
	}
});
/**
 * 接口信息窗口
 */
Ext.define('BaseData.BaseInterfaceInfo.AddChangeWindow',{
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	closeAction : 'destroy',
	resizable:false,
	title : '新增窗口',
	height:600,
	width:400,
	addForm:null,
	editData:null,
	getAddForm:function(){
			this.addForm = Ext.create('BaseData.BaseInterfaceInfo.AddChangeForm');
			return this.addForm;
	},
	initComponent: function() {
		var me = this;
		var addForm = me.getAddForm();
		me.items = [ addForm];
		me.buttons = [{
			text:'取消',
			handler: function() {
				me.close();
			}
		},{
			text: '保存',
			cls:'yellow_button',
			handler: me.addBaseInterfaceInfo,
			scope: me
		}
		];
		//监听事件
		me.listeners = {
				beforeshow:function(){
					var form = addForm.getForm();
					form.reset();					
					if(!Ext.isEmpty(me.editData)){
						form.findField('interfaceCode').setValue(me.editData.data.interfaceCode);
						form.findField('interfaceName').setValue(me.editData.data.interfaceName);
						form.findField('url').setValue(me.editData.data.url);					
						form.findField('returnUrl').setValue(me.editData.data.returnUrl);				
						form.findField('notifyUrl').setValue(me.editData.data.notifyUrl);
						form.findField('pre1').setValue(me.editData.data.pre1);
						form.findField('productName').setValue(me.editData.data.productName);
					}
				}
		};
		me.callParent();
	},
	addBaseInterfaceInfo:function(){
		var me = this;
		var gridstore = Ext.getCmp('BaseData_BaseInterfaceInfo_GridPanel').getStore();
		if(!Ext.isEmpty(me.editData)){
			gridstore.remove(me.editData);
		}
		var form = me.addForm.getForm();
			var ins_rec = Ext.create('BaseData.BaseAppInfo.BaseInterfaceInfoModel',{
				interfaceCode:form.findField('interfaceCode').getValue()+'',
				interfaceName:form.findField('interfaceName').getValue()+'',
				url:form.findField('url').getValue()+'',
				returnUrl:form.findField('returnUrl').getValue()+'',
				notifyUrl:form.findField('notifyUrl').getValue()+'',
				pre1:form.findField('pre1').getValue()+'',
				productName:form.findField('productName').getValue()+'',
			}); 			
			gridstore.add(ins_rec);
		
		
		me.close();
	
	}
});

/**
 * 接口信息列表panel
 */
Ext.define('BaseData.baseAppInfo.BaseInterfaceInfoGridPanel', {
	extend: 'Ext.grid.Panel',
	id:'BaseData_BaseInterfaceInfo_GridPanel',
	title : '接口信息列表',//
	frame: true,
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	selType : "rowmodel", // 选择类型设置为：行选择
	baseInterfaceInfoChangeWindow : null,
	getWindow:function(data){
		this.baseInterfaceInfoChangeWindow = Ext.create('BaseData.BaseInterfaceInfo.AddChangeWindow');
		this.baseInterfaceInfoChangeWindow.editData = data;
		return this.baseInterfaceInfoChangeWindow;
	},
	// 新增按钮
	addButton: null,
	getAddButton: function() {
		var me = this;
		if(Ext.isEmpty(me.addButton)){
			me.addButton = Ext.create('Ext.Button', {
				text :'新增',//新增
				handler : function() {
					me.getWindow(null).show();
				}
			});
		}
		return this.addButton;
	},
	constructor : function(config) {
		var me = this;
		me.tbar = [me.getAddButton()];
		me.store = Ext.create('Ext.data.Store', {
			model:'BaseData.BaseAppInfo.BaseInterfaceInfoModel'
		});
		me.columns = [
		 {
			xtype:'actioncolumn',
			text: '修改',
			width:50,
			items: [{
				iconCls: 'deppon_icons_edit',
				tooltip: '修改',//修改
				handler: function(grid, rowIndex, colIndex) { // 修改按钮
					var rec = grid.getStore().getAt(rowIndex);
					me.getWindow(rec).show();
				}
			}, {
				iconCls: 'deppon_icons_delete',
				tooltip:'删除',//删除
				handler: function(grid, rowIndex, colIndex) {
						/*删除*/
						var rec = grid.getStore().getAt(rowIndex);
						grid.getStore().remove(rec);
					}

				
			}]
		 },
		 {
				dataIndex:'interfaceId',
				hidden:true
			},{
				text : '接口名称',
				dataIndex:'interfaceName'
			}
			];
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
Ext.define('BaseData.BaseAppInfo.BaseInterfaceInfoModel',{
		extend : 'Ext.data.Model',
		fields : [{
			name : 'interfaceId',
			type : 'long'
		}, 
		{
			name : 'productName',
			type : 'string'
		}, {
			name : 'interfaceCode',
			type : 'string'
		}, {
			name : 'interfaceName',
			type : 'string'
		}, {
			name : 'url',
			type : 'string'
		}, {
			name : 'appKey',
			type : 'string'
		},{
			name : 'returnUrl',
			type : 'string'
		},
		{
			name : 'notifyUrl',
			type : 'string'
		},
		{
			name : 'pre1',
			type : 'string'
		}]
});
/**
 * 定义表单(新增)
 * 
 * 
 */
Ext.define('BaseData.BaseAppInfo.BaseAppInfoAddForm', {
	id: 'BaseData.BaseAppInfo.BaseAppInfoAddForm',
	extend: 'Ext.form.Panel',
	defaultType: 'textfield',

	defaults: {
		labelWidth: 100,
		width: 320
	},
	initComponent: function() {
		var me = this;
		me.items = [{ 
			xtype: 'textfield',
			name: 'appKey',
			fieldLabel: '应用系统KEY',
			allowBlank: false  
		},
		{
			xtype: 'textfield',
			name: 'appName',
			fieldLabel: '应用系统名称',
			allowBlank: false 
		},{
			xtype: 'textfield',
			name: 'appSecret',
			fieldLabel: '密钥',
			allowBlank: false 
		},{
			name: 'timeoutMillis',
			fieldLabel: 'http请求过期时间',
			allowBlank: true
		},
		{
			name: 'timestampMinutes',
			fieldLabel: '时间戳验证过期时间范围',
			allowBlank: true 
		},
		{
			name: 'ipWhiteList',
			fieldLabel: '允许访问IP白名单列表',
			allowBlank: true 
		}
		];
		me.callParent();
	}
});

/**
 * 
 * 定义修改表单
 */
Ext.define('BaseData.BaseAppInfo.BaseAppInfoChangeForm', {
	id:'BaseData.BaseAppInfo.BaseAppInfoChangeForm',
	extend: 'Ext.form.Panel',
	defaultType: 'textfield',

	defaults: {
		labelWidth: 100,
		width: 320
	},
	initComponent: function() {
		var me = this;
		me.items = [{ 
			xtype: 'textfield',
			name: 'appId',
			fieldLabel: '应用系统ID',
			hidden : true,
			allowBlank: false  // 表单项非空  			
		},{ 
			xtype: 'textfield',
			name: 'appKey',
			fieldLabel: '应用系统KEY',
			allowBlank: false  
		},
		{
			name: 'appName',
			fieldLabel: '应用系统名称',
			allowBlank: false ,
		},
		{
			name: 'appSecret',
			fieldLabel: '密钥',
			allowBlank: false ,
		},
		{
			name: 'timeoutMillis',
			fieldLabel: 'http请求过期时间',
			allowBlank: true
		},
		{
			name: 'timestampMinutes',
			fieldLabel: '时间戳验证过期时间范围',
			allowBlank: true 
		},
		{
			name: 'ipWhiteList',
			fieldLabel: '允许访问IP白名单列表',
			allowBlank: true 
		}
		];
		me.callParent();
	}
});
/**
 * 
 * 新增窗口
 * 
 */
Ext.define('BaseData.BaseAppInfo.BaseAppInfoAddWindow',{
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	closeAction : 'destroy',
	resizable:false,
	title : '新增窗口',
	height:600,
	width:400,
	addForm:null,//
	addGrid:null,
	getAddGrid:function(){
		this.addGrid = Ext.create('BaseData.baseAppInfo.BaseInterfaceInfoGridPanel');
		return this.addGrid;
	},
	getAddForm:function(){
			this.addForm = Ext.create('BaseData.BaseAppInfo.BaseAppInfoAddForm');
			return this.addForm;
	},
	initComponent: function() {
		var me = this;
		var addForm = me.getAddForm();
		var addGrid =me.getAddGrid();
		me.items = [ addForm,addGrid],

		me.buttons = [{
			text:'取消',
			handler: function() {
				me.close();
			}
		},{
			text: '保存',
			cls:'yellow_button',
			handler: me.addBaseAppInfo,
			scope: me
		}
		];
		//监听事件
		me.listeners = {
				beforeshow:function(){
					addForm.getForm().reset();
				}

		};
		me.callParent();
	},
	addBaseAppInfo:function(){
		var me = this;
		var baseAppInfoForm = me.down('form').getForm();
		var baseInterfaceInfoGrid = me.addGrid;
		var gridStore = baseInterfaceInfoGrid.getStore();
		var record=[];
		if(!Ext.isEmpty(gridStore)){
			for (var i = 0; i < gridStore.getCount(); i++) {
				var s= gridStore.getAt(i); 
				data = {
						interfaceCode:s.get('interfaceCode'),
						interfaceName:s.get('interfaceName'),
						url:s.get('url'),
						returnUrl:s.get('returnUrl'),
						notifyUrl:s.get('notifyUrl'),
						pre1:s.get('pre1'),
						productName:s.get('productName')
				};
				record[i]=data;
			}
		}
		if (baseAppInfoForm.isValid()){
			var datas = {
					appKey :baseAppInfoForm.findField('appKey').getValue(),
					appName:baseAppInfoForm.findField('appName').getValue(),
					appSecret:baseAppInfoForm.findField('appSecret').getValue(),
					timeoutMillis:baseAppInfoForm.findField('timeoutMillis').getValue(),
					timestampMinutes:baseAppInfoForm.findField('timestampMinutes').getValue(),
					ipWhiteList:baseAppInfoForm.findField('ipWhiteList').getValue(),
					baseInterfaceInfoList:record
			};			
			Ext.Ajax.request({
				url: '../basedev/addBaseAppInfo.do',
				method: 'post',
		        jsonData : Ext.JSON.encode(datas),
		        headers: {
		        	'Content-Type': 'application/json', 'Accept': 'application/json'
		        },			
				success : function(form,action) {  
					var respText = Ext.JSON.decode(form.responseText); //把字符串变为json格式  
					var msg=respText.msg; //获取里面的值的方法与上面稍有不同  
					Ext.MessageBox.alert('提示',msg);  
					if(respText.success){
						me.close();
						var selectResultPanel = Ext.getCmp("BaseData_BaseAppInfo_BaseAppInfoPageElementGridPanel");
						selectResultPanel.getPagingToolbar().moveFirst();
					}
				},  
				failure : function(response,options) {  
					var json = Ext.decode(response.responseText); 
					Ext.ux.Toast.msg('提示', json.msg, 'error'); 
				}
			});
		}
	}
});

/**
 * 修改窗口
 * 
 */
Ext.define('BaseData.BaseAppInfo.BaseAppInfoChangeWindow',{
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	closeAction : 'destroy',
	resizable:false,
	title : '修改窗口',
	height:600,
	width:400,
	editData:null,//修改数据 
	changeForm:null,
	changeGrid:null,
	getChangeForm:function(){	
			this.changeForm = Ext.create('BaseData.BaseAppInfo.BaseAppInfoChangeForm');
			return this.changeForm;
	},
	getChangeGrid: function(){	
		this.changeGrid = Ext.create('BaseData.baseAppInfo.BaseInterfaceInfoGridPanel');
		return this.changeGrid;
		},
	initComponent: function() {
		var me = this;
		var changeForm = me.getChangeForm();
		var changGrid  = me.getChangeGrid();
		me.items = [ changeForm,changGrid];

		me.buttons = [{
			text:'取消',
			handler: function() {
				me.close();
			}
		},{
			text: '保存',
			cls:'yellow_button',
			handler: me.updateBaseAppInfo,
			scope: me
		}];
		me.listeners = {
				beforeshow:function(){
					me.changeForm.getForm().reset();
					if(!Ext.isEmpty(me.editData)){
						var list = me.editData.data.baseInterfaceInfoList;
						if(!Ext.isEmpty(list)){
							for(var i=0;i<list.length; i++){
								var ins_rec = Ext.create('BaseData.BaseAppInfo.BaseInterfaceInfoModel',{
									interfaceCode:list[i].interfaceCode+'',
									interfaceName:list[i].interfaceName+'',
									url:list[i].url+'',
									returnUrl:list[i].returnUrl+'',
									notifyUrl:list[i].notifyUrl+'',
									pre1:list[i].pre1+'',
									productName:list[i].productName
								}); 	
								me.changeGrid.getStore().add(ins_rec);
							}
						}
						changeForm.getForm().findField('appId').setValue(me.editData.data.appId);
						changeForm.getForm().findField('appKey').setValue(me.editData.data.appKey);
						changeForm.getForm().findField('appName').setValue(me.editData.data.appName);					
						changeForm.getForm().findField('appSecret').setValue(me.editData.data.appSecret);				
						changeForm.getForm().findField('timeoutMillis').setValue(me.editData.data.timeoutMillis);
						changeForm.getForm().findField('timestampMinutes').setValue(me.editData.data.timestampMinutes);
						changeForm.getForm().findField('ipWhiteList').setValue(me.editData.data.ipWhiteList);						
					}
				},
				beforeclose:function(){
					if(!Ext.isEmpty(changeForm.store)){
						me.editData=null;
					}
				}
		};
		me.callParent();

	},
	updateBaseAppInfo:function(){
		var me = this;
		var baseAppInfoForm = me.down('form').getForm();
		var baseInterfaceInfoGrid = me.changeGrid;
		var gridStore = baseInterfaceInfoGrid.getStore();
		var record=[];
		if(!Ext.isEmpty(gridStore)){
			for (var i = 0; i < gridStore.getCount(); i++) {
				var s= gridStore.getAt(i); 
				data = {
						interfaceCode:s.get('interfaceCode'),
						interfaceName:s.get('interfaceName'),
						url:s.get('url'),
						returnUrl:s.get('returnUrl'),
						notifyUrl:s.get('notifyUrl'),
						pre1:s.get('pre1'),
						productName:s.get('productName')
				};
				record[i]=data;
			}
		}
		if (baseAppInfoForm.isValid()){
			var datas = {
					appId:baseAppInfoForm.findField('appId').getValue(),
					appKey :baseAppInfoForm.findField('appKey').getValue(),
					appName:baseAppInfoForm.findField('appName').getValue(),
					appSecret:baseAppInfoForm.findField('appSecret').getValue(),
					timeoutMillis:baseAppInfoForm.findField('timeoutMillis').getValue(),
					timestampMinutes:baseAppInfoForm.findField('timestampMinutes').getValue(),
					ipWhiteList:baseAppInfoForm.findField('ipWhiteList').getValue(),
					baseInterfaceInfoList:record
			};
			Ext.Ajax.request({
				url: '../basedev/updateBaseAppInfoById.do',
				method: 'post',
			    jsonData : Ext.JSON.encode(datas),
			        headers: {
			        	'Content-Type': 'application/json', 'Accept': 'application/json'
			        },		
				success : function(form,action) {  
					var respText = Ext.JSON.decode(form.responseText); //把字符串变为json格式  
					var msg=respText.msg; //获取里面的值的方法与上面稍有不同  
					if(respText.success){
						me.close();
						var selectResultPanel = Ext.getCmp("BaseData_BaseAppInfo_BaseAppInfoPageElementGridPanel");
						selectResultPanel.getPagingToolbar().moveFirst();
					}
					Ext.MessageBox.alert('提示',respText.msg);  
				},  
				failure : function(response,options) {  
					var respText = Ext.JSON.decode(response.responseText); //吧字符串变为json格式  
					var msg=respText.msg;  
					Ext.MessageBox.alert('提示',msg);  

				}
			});
		}
	}
}
);


Ext.onReady(function() {
	Ext.QuickTips.init();

	if (Ext.getCmp(basedev.baseAppInfo.CONTENT_ID)) {
		return;
	};

	var searchForm = Ext.create('BaseData.baseAppInfo.BaseAppInfoForm');
	var resultGrid = Ext.create('BaseData.baseAppInfo.BaseAppInfoPageElementGridPanel');

	var content = Ext.create('Ext.panel.Panel', {
		id: basedev.baseAppInfo.CONTENT_ID,
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		getSearchForm: function() {
			return searchForm;
		},
		getResultGrid: function() {
			return resultGrid;
		},
		items: [
		        searchForm,
		        resultGrid
		        ]
	});

	Ext.getCmp("T_basedev-baseAppInfo").add(content);
});