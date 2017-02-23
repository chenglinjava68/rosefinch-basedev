basedev.baseConfig.TAB_ID="T_basedev-baseConfigIndex";  // 配置管理标签页ID
basedev.baseConfig.CONTENT_ID = "T_basedev-baseConfigIndex_content";  // 配置管理内容panel ID
// 配置查询表单   
basedev.baseConfig.QUERY_BASE_CONFIG_FORM_ID = "T_basedev-querybaseConfigForm";
// 配置列表
basedev.baseConfig.QUERY_BASE_CONFIG_RESULT_GRID_ID = "T_basedev-querybaseConfigResultGrid";

basedev.baseConfig.STATE_ADD = 'add';   // 新增
basedev.baseConfig.STATE_UPDATE = 'update';   // 修改

basedev.baseConfig.BASE_CONFIG_FORM_ID = "T_basedev-baseConfigForm";
basedev.baseConfig.EDIT_BASE_CONFIG_FORM_ID = "T_basedev-editbaseConfigForm";

/**
 * 查询条件
 */
Ext.define('Basedev.baseConfig.QuerybaseConfigForm',{
	extend:'Ext.form.Panel',
	id : basedev.baseConfig.QUERY_BASE_CONFIG_FORM_ID,
	frame : true,
	title: '查询条件',
	layout : 'column',	
	defaultType : 'textfield',
	initComponent: function(){
		var me = this;
		me.items = [{
    		name: 'configName',
    		fieldLabel: '配置名称',
    		labelWidth: 90,					
    		columnWidth: .3
    	}, {
    		xtype : 'button',
			cls: 'yellow_button',
			text: '查询',
			width : 70,
			handler: function() {
				var selectResultPanel = Ext.getCmp(basedev.baseConfig.QUERY_BASE_CONFIG_RESULT_GRID_ID);
				selectResultPanel.setVisible(true);
				selectResultPanel.getPagingToolbar().moveFirst();
			}
		}];
		me.callParent();
	}
});

/**
 * 配置model
 */
Ext.define('Basedev.baseConfig.baseConfigModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'configId',
		type : 'string'
	}, {
		name : 'configCode',
		type : 'string'
	}, {
		name : 'configName',
		type : 'string'
	}, {
		name : 'configValue',
		type : 'string'
	}, {
		name : 'blUserEdit',
		type : 'number'
	}, {
		name : 'remark',
		type : 'string'
	}, {
		name : 'createUserCode',
		type : 'string'
	},{
		name : 'createUserName'
	},
	 {
		name : 'createTime',
		type : 'date',
		convert : dateConvert
	},
	 {
		name : 'modifyUserCode',
		type : 'string'
	}, {
		name : 'modifyTime',
		type : 'date',
		convert : dateConvert
	}]
});

/**
 * 配置查看
 */
Ext.define('Basedev.baseConfig.baseConfigForm', {
	extend : 'Ext.form.Panel',
	id : basedev.baseConfig.BASE_CONFIG_FORM_ID,
//	title : '查看配置',
	frame : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 120,
		readOnly : true
	},
	layout : {
		type : 'table',
		columns : 2
	},
	defaultType : 'textfield',

	items : [{
		xtype : 'textfield',
		name : 'configCode',
		fieldLabel : '配置编号'
	}, {
		xtype : 'textfield',
		name : 'configName',
		fieldLabel : '配置名称'
	}, {
		xtype : 'textfield',
		name : 'configValue',
		fieldLabel : '配置值'
	},
	/*{
		fieldLabel : '用户是否可编辑',
		name: 'blUserEdit',
		renderer: function(value){
			if(value=='0') return "否";
			if(value=='1') return "是";
			
			return value;
		}
	},
	*/
	, {
		xtype : "textfield",
		name : 'remark',
		fieldLabel : '备注'
	}, {
		xtype : 'textfield',
		name : 'createUserCode',
		fieldLabel : '创建人'
	},
	{
		xtype : 'textfield',
		name : 'modifyUserCode',
		fieldLabel : '修改人'
	}]
});

/**
 * 配置新增/编辑
 */
Ext.define('Basedev.baseConfig.EditbaseConfigForm',{
	extend:'Ext.form.Panel',
	id : basedev.baseConfig.EDIT_BASE_CONFIG_FORM_ID,
//	title: '查看产品',
	frame: true,
    defaults: {
    	margin:'5 10 5 10',
    	labelWidth: 120,
    	allowBlank: true,
	    validateOnChange: false
    },
    layout : {
    	type : 'table',
    	columns: 2
    },
	defaultType : 'textfield',
	
	items : [{
		xtype : 'textfield',
		name : 'configId',
		hidden : true
	}, {
		xtype : 'textfield',
		name : 'configCode',
		fieldLabel : '配置编号',
		maxLength : 50,
		allowBlank: false,
		validateOnBlur : true,
		regex : /^[A-Za-z0-9_]+$/,
		regexText : '该输入项只能输入数字和字母',
		validator : function(field){
			if(!field){
				return true;
			}
			
			var editbaseConfigInfoForm = Ext.getCmp(basedev.baseConfig.EDIT_BASE_CONFIG_FORM_ID);
			var state = editbaseConfigInfoForm.getOperatorType();
			if(basedev.baseConfig.STATE_UPDATE == state){
				return true;
			}
			
			var paramsObj = {configCode : field};
			var valid = false;
			Ext.Ajax.request({
		        url : basedev.realPath('uniqueCheckByConfigCode.do'),
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
			return '该配置编号已存在';
		}
	},, {
		xtype : 'textfield',
		name : 'configName',
		fieldLabel : '配置名称',
		maxLength : 50,
		allowBlank: false,
		validateOnBlur : true,
		validator : function(field){
			if(!field){
				return true;
			}
			
			var editbaseConfigInfoForm = Ext.getCmp(basedev.baseConfig.EDIT_BASE_CONFIG_FORM_ID);
			
			var configCode = editbaseConfigInfoForm.getForm().findField("configCode").getValue();
			var paramsObj = {configCode : configCode, configName : field, state : editbaseConfigInfoForm.getOperatorType()};
			
			var valid = false;
			Ext.Ajax.request({
		        url : basedev.realPath('uniqueCheckByConfigName.do'),
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
			return '该配置名称已存在';
		}
	},
	
	/*{
		xtype : 'textfield',
		name : 'configName',
		fieldLabel : '配置名称',
		allowBlank: false
	},	*/	
	{
		name: 'blUserEdit',
		fieldLabel: '用户是否可编辑',//用户是否可编辑（0：否 1：是）
		xtype:'combo',
		store: Ext.create('Ext.data.Store', {
			fields: ['name', 'value'],
			data : [
		        {'name':'否', 'value': 0},// 不可编辑
		        {'name':'是', 'value': 1}//可以编辑
	        ]
		}),
		queryMode: 'local',
		displayField: 'name',
		valueField: 'value',
		allowBlank: false,
		editable : false

	},{
		xtype : 'textfield',
		name : 'configValue',
		fieldLabel : '配置值',
		allowBlank: false
	},{
		xtype : 'textfield',
		name : 'remark',
		fieldLabel : '备注',
		maxLength : 100,
		allowBlank: true
	}
	
	],
	operatorType : null,
	setOperatorType : function(state,record){
		this.operatorType = state;
		var editbaseConfigForm = this.getForm();
		// 表单重置
		editbaseConfigForm.reset();
		
		if(state == basedev.baseConfig.STATE_ADD){
			var baseConfigModel = Ext.create('Basedev.baseConfig.baseConfigModel');
			editbaseConfigForm.loadRecord(baseConfigModel);
			this.setFormFieldsReadOnly(false);
		} else if(state == basedev.baseConfig.STATE_UPDATE){
			
			var baseConfigModel = Ext.create('Basedev.baseConfig.baseConfigModel',record.raw);
			
			if(record.raw.upConfigCode){
				var comboboxbaseConfigModel = Ext.create('BaseData.commonSelector.baseConfigModel');
				comboboxbaseConfigModel.data.ConfigCode = record.raw.upConfigCode;
				comboboxbaseConfigModel.data.ConfigName = record.raw.upConfigName;
				baseConfigModel.data.upConfigCode = comboboxbaseConfigModel;
			}
			
			editbaseConfigForm.loadRecord(baseConfigModel);
			this.setFormFieldsReadOnly(true);
		}
	},
	getOperatorType : function(){
		return this.operatorType;
	},
	setFormFieldsReadOnly: function(flag) {
		var form = this.getForm();
		form.findField('configCode').setReadOnly(flag);
	}
});

/**
 * 配置Store
 */
Ext.define('Basedev.baseConfig.baseConfigStore',{
	extend:'Ext.data.Store',
	model: 'Basedev.baseConfig.baseConfigModel',
	pageSize: 10,
	autoLoad: false,
	proxy: {
		type: 'ajax',
		actionMethods: 'POST',
		url : basedev.realPath("getPaginationBaseConfigList.do"),
		reader: {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp(basedev.baseConfig.QUERY_BASE_CONFIG_FORM_ID);
			if (queryForm != null) {
				var p_name = queryForm.getForm().findField('configName').getValue();
				
				Ext.apply(operation, {
					params : {
						'q_sl_configName' : p_name
						
					}
				});	
			}
		}
	}
});

/**
 * 产品表格
 */
Ext.define('Basedev.baseConfig.QuerybaseConfigResultGrid',{
	extend: 'Ext.grid.Panel',
	id: basedev.baseConfig.QUERY_BASE_CONFIG_RESULT_GRID_ID,
	title: '查询结果',
	cls: 'autoHeight',
	bodyCls: 'autoHeight',
	// 设置表格不可排序
	sortableColumns: false,
	// 去掉排序的倒三角
    enableColumnHide: false,
    selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SIMPLE"}),
    // 设置“如果查询的结果为空，则提示”
    emptyText: '没有数据',
	stripeRows : true, // 交替行效果
	collapsible: true,
    animCollapse: true,
    frame: true,
    /*layout: {
	    type: 'fit'
	},*/
    columns : [{
		xtype : 'actioncolumn',
		// flex: 1,
		text : '操作',
		align : 'center',
		items : [{
			iconCls : 'deppon_icons_showdetail',
			tooltip : '查看',
			handler : function(gridView, rowIndex, colIndex) { // 查看
				var baseConfigWindow = Ext.getCmp(basedev.baseConfig.QUERY_BASE_CONFIG_RESULT_GRID_ID).getbaseConfigWindow();
				baseConfigWindow.setTitle('查看产品');
				
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
			
				
				var baseConfigForm = baseConfigWindow.getbaseConfigForm();
				var baseConfigModel = Ext.create('Basedev.baseConfig.baseConfigModel',rowInfo.raw);
				baseConfigForm.loadRecord(baseConfigModel);
				// 显示窗口
				baseConfigWindow.show();
			}
		}, {
			iconCls : 'deppon_icons_edit',
			tooltip : '修改',// 修改
			handler : function(gridView, rowIndex, colIndex) {
				var editEaseConfigWindow = Ext.getCmp(basedev.baseConfig.QUERY_BASE_CONFIG_RESULT_GRID_ID).getEditbaseConfigWindow();
				editEaseConfigWindow.setTitle('编辑产品');
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
				// by  qkj  判断是否是可以编辑的状态
				var blUserEdit = rowInfo.data.blUserEdit;
				if(blUserEdit==1){
					editEaseConfigWindow.getEditbaseConfigForm().setOperatorType(basedev.baseConfig.STATE_UPDATE, rowInfo);
					// 打开窗口
					editEaseConfigWindow.show();
				}else{
					Ext.ux.Toast.msg('提示', '当前配置是不可以编辑的');
				}
				
				
			}
		}/*, {
			iconCls : 'deppon_icons_delete',
			tooltip : '删除',// 删除
			handler : function(grid, rowIndex, colIndex) {
				 删除 
				var rowInfo = Ext
						.getCmp(basedev.baseConfig.QUERY_BASE_CONFIG_RESULT_GRID_ID).store
						.getAt(rowIndex);
				
				var configId = rowInfo.data.configId;
				
				Ext.Msg.confirm('确认',
						'确认删除吗？',
						function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : basedev.realPath('/deleteConfig.do'),
									params : {
										configId: configId
									},
									success : function(response) {
							        	var result = Ext.JSON.decode(response.responseText);
							        	
							        	if(result.success){
							        		Ext.ux.Toast.msg('提示', '删除成功');
											var grid = Ext.getCmp(basedev.baseConfig.QUERY_BASE_CONFIG_RESULT_GRID_ID);
								        	// 加载表格
								        	grid.getStore().load();
							        	}else{
							        		Ext.ux.Toast.msg('提示', result.msg, 'error');
							        	}
							        },
							        failure : function(response) {
							        	Ext.ux.Toast.msg('提示',response.responseText, 'error');
							        }
								});
							}
						});
			}
		}*/]
	}, {
		hidden : true,
		dataIndex : 'id'
	}, {
		text : '配置编号',
		sortable : true,
		flex : 20 / 100,
		
		dataIndex : 'configCode'
	}, {
		text : '配置名称',
		sortable : true,
		flex : 20 / 100,
		
		dataIndex : 'configName'
	},
	{
		text : '配置值',
		sortable : true,
		flex : 20 / 100,
		
		dataIndex : 'configValue'
	},
	
	{
		text : '用户是否可编辑',
		sortable : true,
		flex : 20 / 100,
		dataIndex : 'blUserEdit',
		renderer: function(value){
			if(value=='0') return "否";
			if(value=='1') return "是";
			
			return value;
		}
	},
	
	
	{
		text : '创建人',
		sortable : true,
		flex : 20 / 100,
		
		dataIndex : 'createUserCode'
	},
	{
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		text : '创建时间',
		flex : 20 / 100,
		dataIndex : 'createTime'
	},
	{
		text : '修改人',
		sortable : true,
		flex : 20 / 100,
		dataIndex : 'modifyUserCode'
	},
	{
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		text : '修改时间',
		flex : 20 / 100,
		dataIndex : 'modifyTime'
	},{
		text : '备注',
		sortable : true,
		flex : 20 / 100,
		
		dataIndex : 'remark'
	}
	],
    
	baseConfigWindow : null,
	getbaseConfigWindow : function(){
		me = this;
		if(Ext.isEmpty(me.baseConfigWindow)){
			me.baseConfigWindow = Ext.create('Basedev.baseConfig.baseConfigWindow');
			
			
		}
		return me.baseConfigWindow;
	},
	editbaseConfigWindow : null,
	getEditbaseConfigWindow : function(){
		me = this;
		if(Ext.isEmpty(me.editbaseConfigWindow)){
			me.editbaseConfigWindow = Ext.create('Basedev.baseConfig.EditbaseConfigWindow');
		}
		return me.editbaseConfigWindow;
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
		me.store = Ext.create('Basedev.baseConfig.baseConfigStore');
		me.tbar = [{
			text: '新增',
			handler: function(){
				var editbaseConfigWindow = me.getEditbaseConfigWindow();
				editbaseConfigWindow.setTitle('新增产品');
				var editbaseConfigForm = editbaseConfigWindow.getEditbaseConfigForm();
				editbaseConfigForm.setOperatorType(basedev.baseConfig.STATE_ADD);	
				editbaseConfigWindow.show();
			}
		}/*, {
            text: '删除',
            handler: function(){
                var selectList = me.getSelectionModel().getSelection();
                if(Ext.isEmpty(selectList)){
                    Ext.ux.Toast.msg('提示', '请选择一行');
                    return;
                }
                var ids = [];
                for(var i = 0; i < selectList.length; i++){
                    ids[i] = selectList[i].data.configId;
                }
                Ext.Msg.confirm('确认',
                    '确认删除吗？',
                    function(btn) {
                        if (btn == 'yes') {
                            var paramsObj = {idList: ids};
                            Ext.Ajax.request({
                                url : basedev.realPath('/batchDeleteBaseConfigById.do'),
                                jsonData : Ext.JSON.encode(paramsObj),
                                async : false,
                                headers: {
                                      'Content-Type': 'application/json',
                                      'Accept': 'application/json'
                                },
                                success : function(response) {
                                    var result = Ext.JSON.decode(response.responseText);
                                    
                                    if(result.success){
                                        Ext.ux.Toast.msg('提示', '删除成功');
                                        var grid = Ext.getCmp(basedev.baseConfig.QUERY_BASE_CONFIG_RESULT_GRID_ID);
                                        // 加载表格
                                        grid.getStore().load();
                                    }else{
                                        Ext.ux.Toast.msg('提示', result.msg, 'error');
                                    }
                                },
                                failure : function(response) {
                                    Ext.ux.Toast.msg('提示',response.responseText, 'error');
                                }
                            });
                        }
                    }
                );
            }
		}*/
		];
		me.bbar = me.getPagingToolbar();
		me.listeners = {
            beforedestroy: function(){
                if(!Ext.isEmpty(me.baseConfigWindow)){
                    me.baseConfigWindow.removeAll();
                    me.baseConfigWindow.destroy();
                }
                if(!Ext.isEmpty(me.editbaseConfigWindow)){
                    me.editbaseConfigWindow.removeAll();
                    me.editbaseConfigWindow.destroy();
                }
            }
        };
		me.callParent(cfg);
	}
});


Ext.define('Basedev.baseConfig.baseConfigWindow', {
	extend: 'Ext.window.Window',
	width: 700,
	modal: true,
	closeAction: 'hide',
	title : '查看产品',
	baseConfigForm : null,
	getbaseConfigForm: function(){
		if (Ext.isEmpty(this.baseConfigForm)) {
			this.baseConfigForm = Ext.create("Basedev.baseConfig.baseConfigForm");
		}
		return this.baseConfigForm;
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
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
			me.getbaseConfigForm()
	    ];
		me.buttons = [
			me.getCancelButton()
		];
		me.callParent([cfg]);
	}
});


Ext.define('Basedev.baseConfig.EditbaseConfigWindow', {
	extend: 'Ext.window.Window',
	width: 700,
	modal: true,
	closeAction: 'hide',
	editbaseConfigForm : null,
	getEditbaseConfigForm: function(){
		if (Ext.isEmpty(this.editbaseConfigForm)) {
			this.editbaseConfigForm = Ext.create("Basedev.baseConfig.EditbaseConfigForm");
		}
		return this.editbaseConfigForm;
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
					var baseConfigForm = me.getEditbaseConfigForm().getForm();
					
					// 校验产品表单
					if (!baseConfigForm.isValid()) {
						return;
					}
					
					var data = baseConfigForm.getValues();
					
					var url = '';
					if (me.getEditbaseConfigForm().getOperatorType() == basedev.baseConfig.STATE_ADD) {
						url = basedev.realPath('insertBaseConfig.do');
					} else {
						url = basedev.realPath('updateBaseConfig.do');
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
					        	me.hide();
					        	
					        	var grid = Ext.getCmp(basedev.baseConfig.QUERY_BASE_CONFIG_RESULT_GRID_ID);
					        	// 加载表格
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
			me.getEditbaseConfigForm()
	    ];
		me.buttons = [
			me.getCancelButton(),
			me.getSaveButton()
		];
		me.callParent([cfg]);
	}
});

/**
 * 查看窗口
 */
Ext.define('Basedev.baseConfig.baseConfigWindow', {
	extend: 'Ext.window.Window',
	width: 700,
	modal: true,
	closeAction: 'hide',
	baseConfigForm : null,
	getbaseConfigForm: function(){
		if (Ext.isEmpty(this.baseConfigForm)) {
			this.baseConfigForm = Ext.create("Basedev.baseConfig.baseConfigForm");
		}
		return this.baseConfigForm;
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
			me.getbaseConfigForm()
	    ];
		me.buttons = [
			me.getCancelButton()
		];
		me.callParent([cfg]);
	}
});


Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp(basedev.baseConfig.CONTENT_ID)) {
		return;
	};
	
	var querybaseConfigForm = Ext.create('Basedev.baseConfig.QuerybaseConfigForm');
	var querybaseConfigResultGrid = Ext.create('Basedev.baseConfig.QuerybaseConfigResultGrid');
	
	var content = Ext.create('Ext.panel.Panel', {
		id: basedev.baseConfig.CONTENT_ID,
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		getQuerybaseConfigForm: function() {
			return querybaseConfigForm;
		},
		getQuerybaseConfigResultGrid: function() {
			return querybaseConfigResultGrid;
		},
		items: [
			querybaseConfigForm,
			querybaseConfigResultGrid
		]
	});
	
	Ext.getCmp(basedev.baseConfig.TAB_ID).add(content);
	// 加载表格数据
	querybaseConfigResultGrid.getStore().load();
});

