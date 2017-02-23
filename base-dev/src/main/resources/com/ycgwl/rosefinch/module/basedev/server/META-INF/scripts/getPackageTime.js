basedev.getPackageTime.TAB_ID="T_basedev-getPackageTimeIndex";  // 配置管理标签页ID
basedev.getPackageTime.CONTENT_ID = "T_basedev-getPackageTimeIndex_content";  // 配置管理内容panel ID
// 配置查询表单   
basedev.getPackageTime.QUERY_BASE_CONFIG_FORM_ID = "T_basedev-querygetPackageTimeForm";
// 配置列表
basedev.getPackageTime.QUERY_BASE_CONFIG_RESULT_GRID_ID = "T_basedev-querygetPackageTimeResultGrid";

basedev.getPackageTime.STATE_ADD = 'add';   // 新增
basedev.getPackageTime.STATE_UPDATE = 'update';   // 修改

basedev.getPackageTime.BASE_CONFIG_FORM_ID = "T_basedev-getPackageTimeForm";
basedev.getPackageTime.EDIT_BASE_CONFIG_FORM_ID = "T_basedev-editgetPackageTimeForm";
basedev.getPackageTime.FORMAT_TIME = 'G:i'; // 格式化时间字符串

basedev.getPackageTime.crateParam = function(param){
	var index = 0;
	var paramStr = "";
	for ( var p in param) {
		var name = p;
		var val = param[p];
		if (val != null && val != '') {
			if(val == 'ALL'){
				continue;
			}
			if (index == 0) {
				paramStr = paramStr + "?" + name + "=" + val;
			}
			else {
				paramStr = paramStr + "&" + name + "=" + val;
			}
			index++;
		}
	}
	return paramStr;
}

/**
 * 查询条件
 */
Ext.define('Basedev.getPackageTime.QuerygetPackageTimeForm',{
	extend:'Ext.form.Panel',
	id : basedev.getPackageTime.QUERY_BASE_CONFIG_FORM_ID,
	frame : true,
	title: '查询条件',
	layout : 'column',	
	defaultType : 'textfield',
	initComponent: function(){
		var me = this;
		me.items = [{
    		name: 'configName',
    		fieldLabel: '时效名称',
    		labelWidth: 90,					
    		columnWidth: .3
    	}, {
    		xtype : 'button',
			cls: 'yellow_button',
			text: '查询',
			width : 70,
			handler: function() {
				var selectResultPanel = Ext.getCmp(basedev.getPackageTime.QUERY_BASE_CONFIG_RESULT_GRID_ID);
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
Ext.define('Basedev.getPackageTime.getPackageTimeModel', {
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
		name : 'orderTime1Start',
		type : 'string'
	}, {
		name : 'orderTime1End',
		type : 'string'
	}, {
		name : 'qualifiedGetPackageTime1',
		type : 'string'
	}, {
		name : 'orderTime2Start',
		type : 'string'
	}, {
		name : 'orderTime2End',
		type : 'string'
	}, {
		name : 'qualifiedGetPackageTime2',
		type : 'string'
	}, {
		name : 'createUserCode',
		type : 'string'
	}, {
		name : 'createTime',
		type : 'date',
		convert : dateConvert
	}, {
		name : 'modifyUserCode',
		type : 'string'
	}, {
		name : 'modifyTime',
		type : 'date',
		convert : dateConvert
	}, {
		name: 'remark',
		type: 'string'
	}, {
		name: 'delFlag',
		type: 'string'
	}, {
		name: 'blFlag',
		type: 'string'
	}, {
		name: 'qualifiedGetPackageTime1Name',
		type: 'string'
	}, {
	   name: 'siteNames',
	   type: 'string'
	}]
});

/**
 * 配置查看
 */
Ext.define('Basedev.getPackageTime.getPackageTimeForm', {
	extend : 'Ext.form.Panel',
	id : basedev.getPackageTime.BASE_CONFIG_FORM_ID,
//	title : '查看配置',
	frame : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth : 100,
		readOnly : true,
		width: 260
	},
	layout : {
		type : 'table',
		columns : 3
	},
	defaultType : 'textfield',

	items : [{
		name : 'configCode',
		fieldLabel : '时效编号'
	}, {
		name : 'configName',
		fieldLabel : '时效名称'
	}, {
		name: 'blFlag',
		fieldLabel: '启用'
	}, { 
		name: 'orderTime1Start',
		fieldLabel: '下单时间1'
	}, {
		name: 'orderTime1End',
		fieldLabel: '至'
	}, {
		xtype: 'dictcombo',
		dictType : 'QUALIFIED_GETPACKAGE_TIME',
		name: 'qualifiedGetPackageTime1',
		fieldLabel: '合格揽件时间1'
	}, {
		name: 'orderTime2Start',
		fieldLabel: '下单时间2'
	}, {
		name: 'orderTime2End',
		fieldLabel: '至'
	}, {
		name: 'qualifiedGetPackageTime2',
		fieldLabel: '合格揽件时间2'
	}, {
		name: 'remark',
		fieldLabel: '备注',
		xtype: 'textareafield',
        colspan: 3,
        grow:true,
        growMin: 32,
        growMax: 50,
        width:820
	},/* {
		name : 'createUserCode',
		fieldLabel : '创建人工号'
	}, {
		name : 'modifyUserCode',
		fieldLabel : '修改人工号'
	}, */{
       name: 'siteNames',
       xtype: 'textareafield',
       fieldLabel: '门店',
       grow: true,
       growMax: 310,
       colspan: 3,
       width: 820
    }],
	setShowValue: function(recode){
		var stateName = recode.data.blFlag == '1' ? '是' : '否';
		this.getForm().findField("blFlag").setValue(stateName);
	},
	setSiteNamesShowValue: function(siteNames){
       this.getForm().findField('siteNames').setValue(siteNames);
    }
});



/**
 * 配置Store
 */
Ext.define('Basedev.getPackageTime.getPackageTimeStore',{
	extend:'Ext.data.Store',
	model: 'Basedev.getPackageTime.getPackageTimeModel',
	pageSize: 10,
	autoLoad: false,
	proxy: {
		type: 'ajax',
		actionMethods: 'POST',
		url : basedev.realPath("getPaginationPackageTimeList.do"),
		reader: {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp(basedev.getPackageTime.QUERY_BASE_CONFIG_FORM_ID);
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
Ext.define('Basedev.getPackageTime.QuerygetPackageTimeResultGrid',{
	extend: 'Ext.grid.Panel',
	id: basedev.getPackageTime.QUERY_BASE_CONFIG_RESULT_GRID_ID,
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
        text: '序号',
        width: 50,
        xtype: 'rownumberer',
        align: 'center',
        draggable: true
    }, {
		xtype : 'actioncolumn',
		width: 100,
		text : '操作',
		align : 'center',
		items : [{
			iconCls : 'deppon_icons_showdetail',
			tooltip : '查看',
			handler : function(gridView, rowIndex, colIndex) { // 查看
				var getPackageTimeWindow = Ext.getCmp(basedev.getPackageTime.QUERY_BASE_CONFIG_RESULT_GRID_ID).getgetPackageTimeWindow();
				getPackageTimeWindow.setTitle('查看揽件时效');
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
				var getPackageTimeForm = getPackageTimeWindow.getgetPackageTimeForm();
				var getPackageTimeModel = Ext.create('Basedev.getPackageTime.getPackageTimeModel',rowInfo.raw);
				getPackageTimeForm.loadRecord(getPackageTimeModel);
				getPackageTimeForm.setShowValue(rowInfo);
				//加载门店集合
                Ext.Ajax.request({
                    url : "../basedev/getOrgsByPackageTime.do",
                    params : {
                        'configCode' :rowInfo.data.configCode
                    },
                    success : function(response) {
                        var result = Ext.JSON.decode(response.responseText);
                        if(result.success){
                            getPackageTimeForm.setSiteNamesShowValue(result.data.siteNames);
                        }else{
                            Ext.ux.Toast.msg('提示', result.msg, 'error'); 
                        }
                    },
                    failure : function(response) {
                        Ext.ux.Toast.msg('提示',response.responseText, 'error');
                    }}
                );
				// 显示窗口
				getPackageTimeWindow.show();
			}
		}, {
			iconCls : 'deppon_icons_edit',
			tooltip : '修改',// 修改
			handler : function(gridView, rowIndex, colIndex) {
				var editEaseConfigWindow = Ext.getCmp(basedev.getPackageTime.QUERY_BASE_CONFIG_RESULT_GRID_ID).getEditgetPackageTimeWindow();
				editEaseConfigWindow.setTitle('编辑揽件时效');
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
				editEaseConfigWindow.getEditgetPackageTimeForm().setOperatorType(basedev.getPackageTime.STATE_UPDATE, rowInfo);
				//加载门店集合
				Ext.Ajax.request({
                    url : "../basedev/getOrgsByPackageTime.do",
                    params : {
                        'configCode' :rowInfo.data.configCode
                    },
                    success : function(response) {
                        var result = Ext.JSON.decode(response.responseText);
                        if(result.success){
                            editEaseConfigWindow.getEditgetPackageTimeForm().siteCodes = result.data.siteCodes;
                            editEaseConfigWindow.getEditgetPackageTimeForm().setSiteNamesShowValue(result.data.siteNames);
                        }else{
                            Ext.ux.Toast.msg('提示', result.msg, 'error'); 
                        }
                    },
                    failure : function(response) {
                        Ext.ux.Toast.msg('提示',response.responseText, 'error');
                    }}
                );
				// 打开窗口
				editEaseConfigWindow.show();
			}
		}/*, {
			iconCls : 'deppon_icons_delete',
			tooltip : '删除',// 删除
			handler : function(grid, rowIndex, colIndex) {
				 删除 
				var rowInfo = Ext
						.getCmp(basedev.getPackageTime.QUERY_BASE_CONFIG_RESULT_GRID_ID).store
						.getAt(rowIndex);
				
				var configId = rowInfo.data.configId;
				
				Ext.Msg.confirm('确认',
						'确认删除吗？',
						function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : basedev.realPath('/deletePackageTime.do'),
									params : {
										configId: configId
									},
									success : function(response) {
							        	var result = Ext.JSON.decode(response.responseText);
							        	
							        	if(result.success){
							        		Ext.ux.Toast.msg('提示', '删除成功');
											var grid = Ext.getCmp(basedev.getPackageTime.QUERY_BASE_CONFIG_RESULT_GRID_ID);
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
		text : '时效编号',
		width: 100,
		dataIndex : 'configCode'
	}, {
		text : '时效名称',
		width: 150,
		dataIndex : 'configName'
	}, {
		text: '下单时间1开始时间',
		width: 150,
		dataIndex: 'orderTime1Start'
	}, {
		text: '下单时间1结束时间',
		width: 150,
		dataIndex: 'orderTime1End'
	}, {
		text: '合格揽件时间1',
		width: 150,
		dataIndex: 'qualifiedGetPackageTime1Name'
	}, {
		text: '下单时间2开始时间',
		width: 150,
		dataIndex: 'orderTime2Start'
	}, {
		text: '下单时间2结束时间',
		width: 150,
		dataIndex: 'orderTime2End'
	}, {
		text: '合格揽件时间2',
		width: 150,
		dataIndex: 'qualifiedGetPackageTime2'
	}, {
		text: '启用',
		width: 100,
		dataIndex: 'blFlag',
		renderer: function(value){
			return value == '1' ? '是' : '否';
		}
	}, {
		text : '创建人',
		width: 100,
		dataIndex : 'createUserCode'
	}, {
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		text : '创建时间',
		width: 150,
		dataIndex : 'createTime'
	},
	{
		text : '修改人',
		width: 100,
		dataIndex : 'modifyUserCode'
	},
	{
		xtype : 'datecolumn',
		format : 'Y-m-d H:i:s',
		text : '修改时间',
		width: 150,
		dataIndex : 'modifyTime'
	}],
    
	getPackageTimeWindow : null,
	getgetPackageTimeWindow : function(){
		me = this;
		if(Ext.isEmpty(me.getPackageTimeWindow)){
			me.getPackageTimeWindow = Ext.create('Basedev.getPackageTime.getPackageTimeWindow');
		}
		return me.getPackageTimeWindow;
	},
	editgetPackageTimeWindow : null,
	getEditgetPackageTimeWindow : function(){
		me = this;
		if(Ext.isEmpty(me.editgetPackageTimeWindow)){
			me.editgetPackageTimeWindow = Ext.create('Basedev.getPackageTime.EditgetPackageTimeWindow');
		}
		return me.editgetPackageTimeWindow;
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
	setUpdateBlFlag: function(blFlag, blFlagName){
		var selectList = this.getSelectionModel().getSelection();
		if(Ext.isEmpty(selectList)){
			Ext.ux.Toast.msg('提示', '请选择要操作的数据');
			return;
		}
		var showMessage = '只能操作已停用的数据，请确认';
		if(blFlag == 0){
			showMessage = '只能操作已启用的数据，请确认';
		}
		var ids = [];
		for(var i = 0; i < selectList.length; i++){
			if(selectList[i].data.blFlag == blFlag){
				Ext.ux.Toast.msg('提示', showMessage);
				return;
			}
			ids[i] = selectList[i].data.configId;
		}
		Ext.Msg.confirm('确认', '确认'+ blFlagName + '吗？', function(btn){
			if (btn == 'yes') {
				var paramsObj = {idList: ids, blFlag: blFlag};
				Ext.Ajax.request({
				    url : basedev.realPath('/batchUpdatePackageStateById.do'),
				    jsonData : Ext.JSON.encode(paramsObj),
				    async : false,
				    headers: {
				    	'Content-Type': 'application/json',
				    	'Accept': 'application/json'
				    },
				    success : function(response) {
				        var result = Ext.JSON.decode(response.responseText);
				    	if(result.success){
				    	   Ext.ux.Toast.msg('提示', result.msg);
				    	   var grid = Ext.getCmp(basedev.getPackageTime.QUERY_BASE_CONFIG_RESULT_GRID_ID);
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
	},
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Basedev.getPackageTime.getPackageTimeStore');
		me.tbar = [{
			text: '新增',
			handler: function(){
				var editgetPackageTimeWindow = me.getEditgetPackageTimeWindow();
				editgetPackageTimeWindow.setTitle('新增揽件时效');
				var editgetPackageTimeForm = editgetPackageTimeWindow.getEditgetPackageTimeForm();
				editgetPackageTimeForm.setOperatorType(basedev.getPackageTime.STATE_ADD);	
				editgetPackageTimeForm.siteCodes = null;
				editgetPackageTimeWindow.show();
			}
		}/*, {
			text: '删除',
			handler: function(){
				var selectList = me.getSelectionModel().getSelection();
				if(Ext.isEmpty(selectList)){
					Ext.ux.Toast.msg('提示', '请选择要操作的数据');
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
								url : basedev.realPath('/batchDeletePackageTimeById.do'),
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
										var grid = Ext.getCmp(basedev.getPackageTime.QUERY_BASE_CONFIG_RESULT_GRID_ID);
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
		}*/, {
			text: '启用',
			handler: function(){
				me.setUpdateBlFlag(1, '启用');
			}
		}, {
			text: '停用',
			handler: function(){
				me.setUpdateBlFlag(0, '停用');
			}
		}];
		me.bbar = me.getPagingToolbar();
		me.listeners = {
            beforedestroy: function(){
                if(!Ext.isEmpty(me.getPackageTimeWindow)){
                	me.getPackageTimeWindow.removeAll();
                    me.getPackageTimeWindow.destroy();
                }
                if(!Ext.isEmpty(me.editgetPackageTimeWindow)){
                    me.editgetPackageTimeWindow.removeAll();
                    me.editgetPackageTimeWindow.destroy();
                }
            }
        };
		me.callParent(cfg);
	}
});


/**
 * 查看窗口
 */
Ext.define('Basedev.getPackageTime.getPackageTimeWindow', {
	extend: 'Ext.window.Window',
	width: 900,
	modal: true,
	closeAction: 'hide',
	getPackageTimeForm : null,
	getgetPackageTimeForm: function(){
		if (Ext.isEmpty(this.getPackageTimeForm)) {
			this.getPackageTimeForm = Ext.create("Basedev.getPackageTime.getPackageTimeForm");
		}
		return this.getPackageTimeForm;
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
			me.getgetPackageTimeForm()
	    ];
		me.buttons = [
			me.getCancelButton()
		];
		me.callParent([cfg]);
	}
});


Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp(basedev.getPackageTime.CONTENT_ID)) {
		return;
	};
	
	var querygetPackageTimeForm = Ext.create('Basedev.getPackageTime.QuerygetPackageTimeForm');
	var querygetPackageTimeResultGrid = Ext.create('Basedev.getPackageTime.QuerygetPackageTimeResultGrid');
	
	var content = Ext.create('Ext.panel.Panel', {
		id: basedev.getPackageTime.CONTENT_ID,
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		getQuerygetPackageTimeForm: function() {
			return querygetPackageTimeForm;
		},
		getQuerygetPackageTimeResultGrid: function() {
			return querygetPackageTimeResultGrid;
		},
		items: [
			querygetPackageTimeForm,
			querygetPackageTimeResultGrid
		]
	});
	
	Ext.getCmp(basedev.getPackageTime.TAB_ID).add(content);
	// 加载表格数据
	querygetPackageTimeResultGrid.getStore().load();
});

/**
 * 配置新增/编辑
 */
Ext.define('Basedev.getPackageTime.EditgetPackageTimeForm',{
	extend:'Ext.form.Panel',
	id : basedev.getPackageTime.EDIT_BASE_CONFIG_FORM_ID,
	frame: true,
    defaults: {
    	margin:'5 20 5 15',
    	labelWidth: 100,
    	allowBlank: true,
	    validateOnChange: false
    },
    layout : {
    	type : 'table',
    	columns: 3
    },
	defaultType : 'textfield',
	siteCodes: null,
	operatorType : null,
	setOperatorType : function(state,record){
		this.operatorType = state;
		var editgetPackageTimeForm = this.getForm();
		// 表单重置
		editgetPackageTimeForm.reset();
		
		if(state == basedev.getPackageTime.STATE_ADD){
			var getPackageTimeModel = Ext.create('Basedev.getPackageTime.getPackageTimeModel');
			getPackageTimeModel.data.blFlag = 1;
			editgetPackageTimeForm.loadRecord(getPackageTimeModel);
			this.setFormFieldsReadOnly(false);
		} else if(state == basedev.getPackageTime.STATE_UPDATE){
			var getPackageTimeModel = Ext.create('Basedev.getPackageTime.getPackageTimeModel',record.raw);
			editgetPackageTimeForm.loadRecord(getPackageTimeModel);
			this.setFormFieldsReadOnly(true);
		}
	},
	getOperatorType : function(){
		return this.operatorType;
	},
	setFormFieldsReadOnly: function(flag) {
		var form = this.getForm();
		form.findField('configCode').setReadOnly(flag);
	},
	setSiteNamesShowValue: function(siteNames){
	   this.getForm().findField('siteNames').setValue(siteNames);
	},
	treeWindow : null,
    getTreeWindow : function(){
        var me = this;
//        if(Ext.isEmpty(me.treeWindow)){
            me.treeWindow = Ext.create('Basedev.getPackageTime.showTreeWindow');
//        }
        return me.treeWindow;
    },
	initComponent: function(){
	   var me = this;
       me.items = [{
            xtype : 'textfield',
            name : 'configId',
            hidden : true
        }, {
            xtype : 'textfield',
            name : 'configCode',
            fieldLabel : '时效编号',
            maxLength : 20,
            allowBlank: false,
            validateOnBlur : true,
            regex : /^[A-Za-z0-9]+$/,
            regexText : '该输入项只能输入数字和字母',
            validator : function(field){
                if(!field){
                    return true;
                }
                
                var editgetPackageTimeInfoForm = Ext.getCmp(basedev.getPackageTime.EDIT_BASE_CONFIG_FORM_ID);
                var state = editgetPackageTimeInfoForm.getOperatorType();
                if(basedev.getPackageTime.STATE_UPDATE == state){
                    return true;
                }
                
                var paramsObj = {configCode : field};
                var valid = false;
                Ext.Ajax.request({
                    url : basedev.realPath('uniqueCheckByPackageTimeCode.do'),
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
                return '该时效编号已存在';
            }
        }, {
            xtype : 'textfield',
            name : 'configName',
            fieldLabel : '时效名称',
            maxLength : 15,
            allowBlank: false,
            validateOnBlur : true,
            validator : function(field){
                if(!field){
                    return true;
                }
                
                var editgetPackageTimeInfoForm = Ext.getCmp(basedev.getPackageTime.EDIT_BASE_CONFIG_FORM_ID);
                
                var configCode = editgetPackageTimeInfoForm.getForm().findField("configCode").getValue();
                var paramsObj = {configCode : configCode, configName : field, state : editgetPackageTimeInfoForm.getOperatorType()};
                
                var valid = false;
                Ext.Ajax.request({
                    url : basedev.realPath('uniqueCheckByPackageTimeName.do'),
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
                return '该时效名称已存在';
            }
        },{
            xtype: 'checkbox',
            name: 'blFlag',
            boxLabel: '启用',
            inputValue: '1',
            uncheckedValue: '0'
        },{
            fieldLabel: '下单时间1',
            name : 'orderTime1Start',
            xtype: 'timefield',
            labelSeparator: ':',
            pickerMaxHeight: 200,
            increment: 30,
            format: basedev.getPackageTime.FORMAT_TIME,
            allowBlank: false,
            editable : false,
            submitFormat: basedev.getPackageTime.FORMAT_TIME
        },{
            fieldLabel: '至',
            name : 'orderTime1End',
            xtype: 'timefield',
            labelSeparator: ':',
            pickerMaxHeight: 200,
            increment: 30,
            format: basedev.getPackageTime.FORMAT_TIME,
            allowBlank: false,
            editable : false,
            submitFormat: basedev.getPackageTime.FORMAT_TIME
        },{
            xtype : 'dictcombo',
            dictType : 'QUALIFIED_GETPACKAGE_TIME',
            name: 'qualifiedGetPackageTime1',
            fieldLabel:'合格揽件时间1',
            editable : false,
            allowBlank: false
        },{
            fieldLabel: '下单时间2',
            name : 'orderTime2Start',
            xtype: 'timefield',
            labelSeparator: ':',
            pickerMaxHeight: 200,
            increment: 30,
            format: basedev.getPackageTime.FORMAT_TIME,
            allowBlank: false,
            editable : false,
            submitFormat: basedev.getPackageTime.FORMAT_TIME
        },{
            fieldLabel: '至',
            name : 'orderTime2End',
            xtype: 'timefield',
            labelSeparator: ':',
            pickerMaxHeight: 200,
            increment: 30,
            format: basedev.getPackageTime.FORMAT_TIME,
            allowBlank: false,
            editable : false,
            submitFormat: basedev.getPackageTime.FORMAT_TIME
        },{
            fieldLabel: '合格揽件时间2',
            name : 'qualifiedGetPackageTime2',
            xtype: 'timefield',
            labelSeparator: ':',
            pickerMaxHeight: 200,
            increment: 30,
            format: basedev.getPackageTime.FORMAT_TIME,
            allowBlank: false,
            editable : false,
            submitFormat: basedev.getPackageTime.FORMAT_TIME
        },{
            name: 'remark',
            fieldLabel: '备注',
            xtype: 'textfield',
            maxLength : 80,
            allowBlank: true,
            colspan: 3,
            width: 550
        },{
            fieldLabel: '<font style="color:red">*</font>门店',
            name: 'siteNames',
            xtype: 'textareafield',
            colspan: 2,
            width : 550,
            grow: true,
            growMax: 310,
            growMin: 120,
            readOnly: true,
            readOnlyCls: '',
            allowBlank: false
        },{
            xtype : 'button',
            text: '添加',
            width : 70,
            handler: function() {
                var treeWindow = me.getTreeWindow();
                treeWindow.setTitle('添加门店');
                treeWindow.siteCodes = me.siteCodes;
                treeWindow.show();
            }
            
        }];
        me.callParent();
	}
});

Ext.define('Basedev.getPackageTime.showTreeWindow', {
    extend: 'Ext.window.Window',
    width: 800,
    modal: true,
    closeAction: 'destroy',
    siteCodes: null,
    /*rootNode:{
        id : '999999',
        parentId : '-1',
        text : '快运本部',
        checked : null,
        expanded : true
    },*/
    panel: null,
    getPanel: function(){
    	var param = '';
    	var editForm = Ext.getCmp(basedev.getPackageTime.EDIT_BASE_CONFIG_FORM_ID);
    	if(editForm.getOperatorType() == basedev.getPackageTime.STATE_UPDATE){
    	   param = editForm.getForm().findField('configCode').getValue();   
    	}
        this.panel = Ext.create('Rosefinch.Basedev.BaseSitePanelSelectPanel',{
           treeUrl: '../basedev/getPackageTimeTree.do',
           treeparams: {'id': param, 'flag': true}
       });
       return this.panel;
    },// 取消按钮
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
        if (Ext.isEmpty(me.saveButton)) {
            me.saveButton = Ext.create('Ext.button.Button',{
                cls:'yellow_button',
                text: '应用',
                handler: function() {
                    var rightStore = me.panel.getSelectedDeptPanel().getStore();
                     var record=[];
                     var siteNames = '';
                     for (var i = 0; i < rightStore.getCount(); i++) {
                        var s= rightStore.getAt(i).data;
                        if(siteNames){
                            siteNames += "，" + s.siteName;    
                        } else {
                            siteNames = s.siteName;   
                        }
                        var _site = {
                            siteName:s.siteName,             
                            siteCode:s.siteCode
                        };
                        record[i] = _site;
                    }
                    var editForm = Ext.getCmp(basedev.getPackageTime.EDIT_BASE_CONFIG_FORM_ID);
                    editForm.getForm().findField('siteNames').setValue(siteNames);
                    editForm.siteCodes = record;
                    me.close();
                }
            });
        }
        return this.saveButton;
    },
    constructor: function(config){
        var me = this,
            cfg = Ext.apply({}, config);
        me.items = [
            me.getPanel()
        ];
        me.buttons = [
            me.getCancelButton(),
            me.getSaveButton()
        ];
        me.listeners = {
        	beforeshow: function(){
                var store = me.panel.getSelectedDeptPanel().getStore();
                var siteList = me.siteCodes;
                if(!Ext.isEmpty(siteList)){
                    for (var i = 0; i < siteList.length; i++) {
                        var ins_rec = Ext.create('Rosefinch.Basedev.BaseSitePanelSelectPanel.Model',{
                            siteCode:siteList[i].siteCode,
                            siteName:siteList[i].siteName
                        }); 
                        store.add(ins_rec);
                    }
                }
//                me.panel.getLoadDeptTree().setRootNode(me.rootNode);
            },
            beforeclose: function(){
                me.panel.getSelectedDeptPanel().getStore().removeAll(); 
                var nodes = me.panel.getLoadDeptTree().getChecked(); 
                if (nodes && nodes.length) { 
                    for (var i = 0; i < nodes.length; i++) { 
                        nodes[i].data.checked = false;
                        nodes[i].updateInfo({checked:false});
                    } 
                }                   
                me.panel.getLoadDeptTree().collapseAll();
            }
        };
        me.callParent([cfg]);
    }
});

Ext.define('Basedev.getPackageTime.EditgetPackageTimeWindow', {
	extend: 'Ext.window.Window',
	width: 970,
	modal: true,
	closeAction: 'hide',
	editgetPackageTimeForm : null,
	getEditgetPackageTimeForm: function(){
		if (Ext.isEmpty(this.editgetPackageTimeForm)) {
			this.editgetPackageTimeForm = Ext.create("Basedev.getPackageTime.EditgetPackageTimeForm");
		}
		return this.editgetPackageTimeForm;
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
					var getPackageTimeForm = me.getEditgetPackageTimeForm().getForm();
					var siteCodes = me.getEditgetPackageTimeForm().siteCodes;
					// 校验产品表单
					if (!getPackageTimeForm.isValid()) {
						return;
					}
					
					var data = getPackageTimeForm.getValues();
					data.siteCodeList = siteCodes;
					var url = '';
					if (me.getEditgetPackageTimeForm().getOperatorType() == basedev.getPackageTime.STATE_ADD) {
						url = basedev.realPath('insertPackageTime.do');
					} else {
						url = basedev.realPath('updatePackageTime.do');
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
					        	
					        	var grid = Ext.getCmp(basedev.getPackageTime.QUERY_BASE_CONFIG_RESULT_GRID_ID);
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
			me.getEditgetPackageTimeForm()
	    ];
		me.buttons = [
			me.getCancelButton(),
			me.getSaveButton()
		];
		me.callParent([cfg]);
	}
});
