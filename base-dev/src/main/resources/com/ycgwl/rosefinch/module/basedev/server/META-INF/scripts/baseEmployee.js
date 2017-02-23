// 管理内容panel
basedev.baseEmployee.CONTENT_ID = "T_basedev-baseEmployee_content"; 
// ID
// 查询表单
basedev.baseEmployee.QUERY_BASE_EMPLOYEE_FORM_ID = "T_basedev-querybaseEmployeeForm";
// 列表
basedev.baseEmployee.QUERY_BASE_EMPLOYEE_RESULT_GRID_ID = "T_basedev-querybaseEmployeeResultGrid";
// 新增
basedev.baseEmployee.STATE_ADD = 'add'; 
// 修改
basedev.baseEmployee.STATE_UPDATE = 'update'; 
basedev.baseEmployee.BASE_EMPLOYEE_FORM_ID = "T_basedev-baseEmployeeForm";
basedev.baseEmployee.EDIT_BASE_EMPLOYEE_FORM_ID = "T_basedev-editbaseEmployeeForm";
/**
 * model
 */
Ext.define('Basedev.baseEmployee.baseEmployeeModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'long'
	}, {
		name : 'employeeCode',
		type : 'string'
	}, {
		name : 'employeeName',
		type : 'string'
	}, {
		name : 'ownerSite',
		type : 'string'
	}, {
		name : 'phone',
		type : 'string'
	}, {
		name : 'address',
		type : 'string'
	}, {
		name : 'employeeType',
		type : 'string'
	}, {
		name : 'blSendGain',
		type : 'double'
	}, {
		name : 'blDispatchGain',
		type : 'double'
	}, {
		name : 'ownerRange',
		type : 'string'
	}, {
		name : 'blFlag',
		type : 'number'
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
		name : 'orgCode',
		type : 'string'
	}, {
		name : 'orgName',
		type : 'string'
	}, {
		name : 'siteName',
		type : 'string'
	},
	{
		name : 'delFlag',
		type : 'number'
	},
	{
		name : 'title',
		type : 'string'
	}]
});

/**
 * 查询条件
 */
Ext.define('Basedev.baseEmployee.QuerybaseEmployeeForm',
  {
	extend : 'Ext.form.Panel',
	id : basedev.baseEmployee.QUERY_BASE_EMPLOYEE_FORM_ID,
	frame : true,
	title : '查询条件',
	defaults : {
		labelWidth : 60,
		margin : '5 10 5 10'
	},
	layout : 'column',
	defaultType : 'textfield',
	initComponent : function() {
	   var me = this;
	   me.items = [
		{
			name : 'employeeCode',
			fieldLabel : '员工编号',
			columnWidth : .2
		},
		{
			name : 'employeeName',
			fieldLabel : '员工姓名',
			columnWidth : .2
		},
		{
			xtype : 'commonSiteSelector',
			name : 'ownerSite',
			fieldLabel : '所属门店',
			columnWidth : .3
		},
		{
			xtype : 'commonOrgSelector',
			name : 'orgCode',
			fieldLabel : '所属部门',
			columnWidth : .3
		},{
            xtype : 'button',
            text : "重置",
            width : 70,
            handler : function() {
            me.getForm().reset();
          }
        },
		{
			xtype : 'button',
			cls : 'yellow_button',
			text : '查询',
			width : 70,
			handler : function() {
				var selectResultPanel = Ext.getCmp(basedev.baseEmployee.QUERY_BASE_EMPLOYEE_RESULT_GRID_ID);
				selectResultPanel.setVisible(true);
				selectResultPanel.getPagingToolbar().moveFirst();
			}
		}];
	  me.callParent();
	}
  });

/**
 * Store
 */
Ext.define('Basedev.baseEmployee.baseEmployeeStore', {
	extend : 'Ext.data.Store',
	model : 'Basedev.baseEmployee.baseEmployeeModel',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : basedev.realPath("getPaginationBaseEmployeeList.do"),
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp(basedev.baseEmployee.QUERY_BASE_EMPLOYEE_FORM_ID);
			if (queryForm != null) {
				var p_employeeCode = queryForm.getForm().findField('employeeCode').getValue();
				var p_employeeName = queryForm.getForm().findField('employeeName').getValue();
				var p_ownerSite = queryForm.getForm().findField('ownerSite').getValue();
				var p_orgCode = queryForm.getForm().findField('orgCode').getValue();
				Ext.apply(operation, {
                       params : {
						'q_str_employeeCode' : p_employeeCode,
						'q_str_employeeName' : p_employeeName,
						'q_str_ownerSite' : p_ownerSite,
						'q_str_orgCode' : p_orgCode
					}
				});
			}
		}
	}
 });

/**
 * 查看表单
 */
Ext.define('Basedev.baseEmployee.baseEmployeeForm', {
	extend : 'Ext.form.Panel',
	id : basedev.baseEmployee.BASE_EMPLOYEE_FORM_ID,
	frame : true,
	defaults : {
		margin : '5 10 5 10',
		labelWidth :90,
		readOnly : true,
		width: 300
	},
	layout : {
		type : 'table',
		columns : 2
	},
	defaultType : 'textfield',
	items : [ {
		xtype : 'textfield',
		name : 'id',
		hidden : true
	}, {
		xtype : 'textfield',
		name : 'employeeCode',
		fieldLabel : '员工编号'
		}, {
		xtype : 'textfield',
		name : 'employeeName',
		fieldLabel : '员工名称'
	}, {
		xtype : 'textfield',
	    name : 'siteName',
		fieldLabel : '所属门店'
	},{
		xtype : 'textfield',
	    name : 'orgName',
		fieldLabel : '所属部门'
	}
	/*,{
		   xtype: 'dictcombo',
		   dictType : 'EMPLOYEE_TYPE',
		   multiSelect: true,//可以多选
		   queryMode: 'local',
		   name: 'employeeType',
		   queryMode: 'local',
		   triggerAction: 'all',
		   fieldLabel:'职位名称'
	}*/,{
		xtype : 'textfield',
		name : 'title',
		fieldLabel : '职位'
	}, {
		xtype : 'textfield',
		name : 'phone',
		fieldLabel : '电话'
	}, {
		xtype : 'textareafield',
		name : 'address',
		fieldLabel : '地址'
	}, {
	    name : 'blFlag',
		fieldLabel : '是否启用',// 是否启用（0：否 1：是）
		xtype : 'combo',
		store : Ext.create('Ext.data.Store', {
		fields : [ 'name', 'value' ],
		data : [ 
		    {'name' : '否','value' : 0},
		    {'name' : '是','value' : 1} 
		]
		}),
		queryMode : 'local',
		displayField : 'name',
		valueField : 'value',
		allowBlank : false
	}]
});
/**
 * 新增/编辑表单
 */
Ext.define('Basedev.baseEmployee.EditbaseEmployeeForm',
	{
		extend : 'Ext.form.Panel',
		id : basedev.baseEmployee.EDIT_BASE_EMPLOYEE_FORM_ID,
		frame : true,
		defaults : {
			margin : '5 10 5 10',
			labelWidth : 120,
			allowBlank : true,
			validateOnChange : false
		},
		layout : {
			type : 'table',
			columns : 2
		},
		defaultType : 'textfield',
		items : [
			{
				xtype : 'textfield',
				name : 'id',
				hidden : true
			},
			{
				xtype : 'textfield',
				name : 'employeeCode',
				fieldLabel : '员工编号',
				maxLength : 16,
				allowBlank : false,
				validateOnBlur : true,
				regex : /^[A-Za-z0-9]+$/,
				regexText : '该输入项只能输入数字和字母',
				validator : function(field) {
					if (!field) {
						return true;
					}
					var editbaseEmployeeInfoForm = Ext.getCmp(basedev.baseEmployee.EDIT_BASE_EMPLOYEE_FORM_ID);
					var state = editbaseEmployeeInfoForm.getOperatorType();
					if (basedev.baseEmployee.STATE_UPDATE == state) {
						return true;
					}
					var paramsObj = {
						employeeCode : field
					};
					var valid = false;
					Ext.Ajax.request({
						url : basedev.realPath('uniqueCheckByEmployeeCode.do'),
						params : paramsObj,
						async : false,
						success : function(response) {
							var result = Ext.JSON.decode(response.responseText);
							if (result.success) {
								if (result.data == 0) {
									valid = true;
								}
							} else {
								Ext.ux.Toast.msg('提示',result.msg);
							}
						},
						failure : function(response) {
							Ext.ux.Toast.msg('提示',response.responseText,'error');
						}
					});

					if (valid) {
						return true;
					}
					return '该员工编号已存在';
				}
			},
			{
				xtype : 'textfield',
				name : 'employeeName',
				fieldLabel : '员工名称',
				maxLength : 33,
				allowBlank : false,
				validateOnBlur : true,
				validator : function(field) {
					if (!field) {
						return true;
					}
					var editbaseEmployeeInfoForm = Ext.getCmp(basedev.baseEmployee.EDIT_BASE_EMPLOYEE_FORM_ID);
					var employeeCode = editbaseEmployeeInfoForm.getForm().findField("employeeCode").getValue();
					var paramsObj = {
						employeeCode : employeeCode,
						employeeName : field,
						state : editbaseEmployeeInfoForm.getOperatorType()
					};
					var valid = false;
					Ext.Ajax.request({
						url : basedev.realPath('uniqueCheckByEmployeeName.do'),
						params : paramsObj,
						async : false,
						success : function(response) {
							var result = Ext.JSON.decode(response.responseText);
							if (result.success) {
								if (result.data == 0) {
									valid = true;
								}
							} else {
								Ext.ux.Toast.msg('提示',
										result.msg);
							}
						},
						failure : function(response) {
							Ext.ux.Toast.msg('提示',response.responseText,'error');
						}
					});
					if (valid) {
						return true;
					}
					return '该员工名称已存在';
				}
			}, {
				xtype : 'textfield',
				name : 'phone',
				fieldLabel : '电话',
				allowBlank : true,
				regex : /^0?1[3|4|5|8][0-9]\d{8}$/,
				regexText : '该输入项只能输入数字'
			}, {
				xtype : 'textfield',
				name : 'address',
				fieldLabel : '地址',
				allowBlank : true
			}, {
				/*name : 'blFlag',
				fieldLabel : '是否启用',// 是否启用（0：否 1：是）
				xtype : 'combo',
				store : Ext.create('Ext.data.Store', {
					fields : [ 'name', 'value' ],
					data : [ 
					   {'name' : '是','value' : 1},
					   {'name' : '否','value' : 0} 
					]
				}),
				queryMode : 'local',
				displayField : 'name',
				valueField : 'value',
				allowBlank : false*/
				xtype: 'checkbox',
	            name: 'blFlag',
	            boxLabel: '启用',
	            inputValue: '1',
	            uncheckedValue: '0'

			},{
				   xtype: 'dictcombo',
				   dictType : 'EMPLOYEE_TYPE',
				   multiSelect: true,//可以多选
				   editable: false,
				   queryMode: 'local',
				   name: 'employeeType',
				   queryMode: 'local',
				   triggerAction: 'all',
				   fieldLabel:'职位名称'
			},{
				xtype : 'commonSiteSelector',
				name : 'ownerSite',
				fieldLabel : '所属门店'
			}, {
				xtype : 'commonOrgSelector',
				name : 'orgCode',
				fieldLabel : '所属部门'
			}],
		operatorType : null,
		setOperatorType : function(state, record) {
			this.operatorType = state;
			var editbaseEmployeeForm = this.getForm();
			// 表单重置
			editbaseEmployeeForm.reset();

			if (state == basedev.baseEmployee.STATE_ADD) {
				var baseEmployeeModel = Ext.create('Basedev.baseEmployee.baseEmployeeModel');
				editbaseEmployeeForm.loadRecord(baseEmployeeModel);
			} else if (state == basedev.baseEmployee.STATE_UPDATE) {
				var baseEmployeeModel = Ext.create('Basedev.baseEmployee.baseEmployeeModel',record.raw);
				if(record.raw.ownerSite != '0'){
					var comboboxBaseEmployeeModel = Ext.create('BaseData.commonSelector.BaseSiteModel');
					comboboxBaseEmployeeModel.data.siteCode = record.raw.ownerSite;
					comboboxBaseEmployeeModel.data.siteName = record.raw.siteName;
					baseEmployeeModel.data.ownerSite = comboboxBaseEmployeeModel;
				}
				if(record.raw.orgCode != '0'){
					var comboboxBaseEmployeeModel = Ext.create('BaseData.commonSelector.BaseOrgModel');
					comboboxBaseEmployeeModel.data.orgCode = record.raw.orgCode;
					comboboxBaseEmployeeModel.data.orgName = record.raw.orgName;
					baseEmployeeModel.data.orgCode = comboboxBaseEmployeeModel;
				}
				editbaseEmployeeForm.loadRecord(baseEmployeeModel);
			}
		},
		getOperatorType : function() {
		  return this.operatorType;
		}
	});




/**
 * 查看表格
 */
Ext.define(
'Basedev.baseEmployee.QuerybaseEmployeeResultGrid',
{
	extend : 'Ext.grid.Panel',
	id : basedev.baseEmployee.QUERY_BASE_EMPLOYEE_RESULT_GRID_ID,
	title : '查询结果',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	// 设置表格不可排序
	sortableColumns : false,
	// 去掉排序的倒三角
	enableColumnHide : false,
	// 设置“如果查询的结果为空，则提示”
	emptyText : '没有数据',
	selModel : Ext.create('Ext.selection.CheckboxModel', {
		mode : "SIMPLE"
	}),// 添加的复选按钮，用于批量操作
	stripeRows : true, // 交替行效果
	collapsible : true,
	animCollapse : true,
	frame : true,
	columns : [
		 {
			xtype : "rownumberer",
			text : "序号",
			align : 'center',
			width : 40
		 },
		 {
			xtype : 'actioncolumn',
			text : '操作',
			align : 'center',
			items : [{
			iconCls : 'deppon_icons_showdetail',
			tooltip : '查看',
			handler : function(gridView,rowIndex, colIndex) {
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
				Ext.Ajax.request({
                    url : basedev.realPath('getEmployeeTypeByEmployeeCode.do'),
                    params : {
                        'employeeCode' :rowInfo.data.employeeCode
                    },
                    success : function(response) {
                        var result = Ext.JSON.decode(response.responseText);
                        if(result.success){
                        	var baseEmployeeWindow = Ext.getCmp(basedev.baseEmployee.QUERY_BASE_EMPLOYEE_RESULT_GRID_ID).getbaseEmployeeWindow(result.data);
                        	var baseEmployeeModel = Ext.create('Basedev.baseEmployee.baseEmployeeModel',rowInfo.raw);
                        	var baseEmployeeForm = baseEmployeeWindow.getbaseEmployeeForm();
        					baseEmployeeForm.loadRecord(baseEmployeeModel);
        					baseEmployeeWindow.show();
                        }else{
                            Ext.ux.Toast.msg('提示', result.msg, 'error'); 
                        }
                    },
                    failure : function(response) {
                        Ext.ux.Toast.msg('提示',response.responseText, 'error');
                    }}
                );
			 }
			}/*,
			{
				iconCls : 'deppon_icons_edit',
				tooltip : '修改',
				handler : function(gridView,rowIndex, colIndex) {
					var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
					Ext.Ajax.request({
	                    url : basedev.realPath('getEmployeeTypeByEmployeeCode.do'),
	                    params : {
	                        'employeeCode' :rowInfo.data.employeeCode
	                    },
	                    success : function(response) {
	                        var result = Ext.JSON.decode(response.responseText);
	                        if(result.success){
	                        	var editbaseEmployeeWindow = Ext.getCmp(basedev.baseEmployee.QUERY_BASE_EMPLOYEE_RESULT_GRID_ID).getEditbaseEmployeeWindow(result.data);
	        					editbaseEmployeeWindow.setTitle('员工基本信息');
	        					editbaseEmployeeWindow.getEditbaseEmployeeForm().setOperatorType(basedev.baseEmployee.STATE_UPDATE,rowInfo);
	        					editbaseEmployeeWindow.show();
	                        }else{
	                            Ext.ux.Toast.msg('提示', result.msg, 'error'); 
	                        }
	                    },
	                    failure : function(response) {
	                        Ext.ux.Toast.msg('提示',response.responseText, 'error');
	                    }}
	                );
				}
			},
			{
				iconCls : 'deppon_icons_delete',
				tooltip : '删除',
				handler : function(grid, rowIndex,colIndex) {
				 删除 
				var rowInfo = Ext.getCmp(basedev.baseEmployee.QUERY_BASE_EMPLOYEE_RESULT_GRID_ID).store.getAt(rowIndex);
				var id = rowInfo.data.id;
				Ext.Msg.confirm('确认','确认删除吗？',function(btn) {
					if (btn == 'yes') {
					 Ext.Ajax.request({
						url : basedev.realPath('/deleteEmployee.do'),
						params : {
							id : id
						},
						success : function(response) {
							var result = Ext.JSON.decode(response.responseText);
                                if (result.success) {
								Ext.ux.Toast.msg('提示','删除成功');
								var grid = Ext.getCmp(basedev.baseEmployee.QUERY_BASE_EMPLOYEE_RESULT_GRID_ID);
								// 加载表格
								grid.getStore().load();
							} else {
								Ext.ux.Toast.msg('提示',result.msg,'error');
							}
						},
						failure : function(response) {
							Ext.ux.Toast.msg('提示',response.responseText,'error');
						}
					 });
					}
				});
			  }
			}*/]
		}, {
			hidden : true,
			dataIndex : 'id',
			align : 'center'
		}, {
			text : '员工编号',
			align : 'center',
			dataIndex : 'employeeCode',
			width : 120
		}, {
			text : '员工名称',
			align : 'center',
			dataIndex : 'employeeName',
			width : 120
		},{
			text : '所属门店',
			dataIndex : 'siteName',
			align : 'center',
			width : 150
		}, {
			text : '所属部门',
			dataIndex : 'orgName',
			align : 'center',
			width : 150
		}, {
			text : '电话',
			dataIndex : 'phone',
			align : 'center',
			width : 150

		}, {
			text : '地址',
			dataIndex : 'address',
			align : 'center',
			width : 150
		},{
            text : '启用',
            dataIndex : 'blFlag',
            renderer : function(value) {
                if (value == '0')
                    return '否';
                if (value == '1')
                    return '是';
                return value;
            },
            align : 'center',
            width : 120
        },{
            text : '创建人',
            dataIndex : 'createUserCode',
            align : 'center',
            width : 120
        },{
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
			text : '创建时间',
			dataIndex : 'createTime',
			align : 'center',
			width : 150
		}, {
			text : '修改人',
			dataIndex : 'modifyUserCode',
			align : 'center',
			width : 120
		}, {
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
			text : '修改时间',
			dataIndex : 'modifyTime',
			align : 'center',
			width : 150
		}],
	baseEmployeeWindow : null,
	getbaseEmployeeWindow : function(data) {
		me = this;
	    me.baseEmployeeWindow = Ext.create('Basedev.baseEmployee.baseEmployeeWindow');
	    me.baseEmployeeWindow.baseData = data;
		return me.baseEmployeeWindow;
	},
	editbaseEmployeeWindow : null,
	getEditbaseEmployeeWindow : function(data) {
		me = this;
		me.editbaseEmployeeWindow = Ext.create('Basedev.baseEmployee.EditbaseEmployeeWindow');
		me.editbaseEmployeeWindow.editData = data;
		return me.editbaseEmployeeWindow;
	},
	pagingToolbar : null,
	getPagingToolbar : function() {
		var me = this;
		if (me.pagingToolbar == null) {
		me.pagingToolbar = Ext.create(
			'Deppon.StandardPaging', {
			store : me.store
		  });
		}

	 return me.pagingToolbar;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Basedev.baseEmployee.baseEmployeeStore');
		me.tbar = [
		/* {
			text : '新增',
			xtype : 'button',
			handler : function() {
				var editbaseEmployeeWindow = me.getEditbaseEmployeeWindow(null);
				editbaseEmployeeWindow.setTitle('新增员工信息');
				var editbaseEmployeeForm = editbaseEmployeeWindow.getEditbaseEmployeeForm();
				editbaseEmployeeForm.setOperatorType(basedev.baseEmployee.STATE_ADD);
				editbaseEmployeeWindow.show();
			}
		 },*/
		/* {   
			text : '删除',//批量删除
			xtype : 'button',
			handler : function(grid, rowIndex,colIndex) {
			var records = me.getSelectionModel().getSelection();//获得选中的列
			if(records == null || records.length == 0){
				Ext.ux.Toast.msg('提示','请选择要操作的记录', 'error');
				return;
			}
			var ids=[];//数组
			for(var r=0;r<records.length;r++){
				if(records[r].data.blFlag!=0){
					 Ext.ux.Toast.msg('提示','部分员工是启用状态不能删除！请重新选择！','error');
					 return;
				}
				ids[r] = records[r].data.id;
			}
			Ext.Msg.confirm('确认','确认删除吗？',function(btn) {
				if (btn == 'yes') {
				 Ext.Ajax.request({
					url : basedev.realPath('/deleteSomeEmployee.do'),
					jsonData : Ext.JSON.encode(ids),//传递参数
			        headers: {
			              'Content-Type': 'application/json',
			              'Accept': 'application/json'
			        },
					success : function(response) {
						var result = Ext.JSON.decode(response.responseText);
                        if (result.success) {
						Ext.ux.Toast.msg('提示','删除成功');
						var grid = Ext.getCmp(basedev.baseEmployee.QUERY_BASE_EMPLOYEE_RESULT_GRID_ID);
						// 加载表格
						grid.getStore().load();
						} else {
							Ext.ux.Toast.msg('提示',result.msg,'error');
						}
					},
					failure : function(response) {
						Ext.ux.Toast.msg('提示',response.responseText,'error');
					}
				 });
				}
			});
			}
		},*/
		{   
			text : '启用',//批量启用
			xtype : 'button',
			handler : function(grid, rowIndex,colIndex) {
			var records = me.getSelectionModel().getSelection();//获得选中的列
			if(records == null || records.length == 0){
				Ext.ux.Toast.msg('提示','请选择要操作的记录', 'error');
				return;
			}
			var ids=[];//数组
			for(var r=0;r<records.length;r++){
				if(records[r].data.blFlag!=0){
					 Ext.ux.Toast.msg('提示','只能操作已停用的数据，请确认','error');
					 return;
				}
					
				ids[r] = records[r].data.id;
			}
			Ext.Msg.confirm('确认','确认启用吗？',function(btn) {
				if (btn == 'yes') {
				 Ext.Ajax.request({
					url : basedev.realPath('/startSomeEmployee.do'),
					jsonData : Ext.JSON.encode(ids),
			        headers: {
			              'Content-Type': 'application/json',
			              'Accept': 'application/json'
			        },
					success : function(response) {
						var result = Ext.JSON.decode(response.responseText);
                        if (result.success) {
						Ext.ux.Toast.msg('提示','启用成功');
						var grid = Ext.getCmp(basedev.baseEmployee.QUERY_BASE_EMPLOYEE_RESULT_GRID_ID);
						// 加载表格
						grid.getStore().load();
						} else {
							Ext.ux.Toast.msg('提示',result.msg,'error');
						}
					},
					failure : function(response) {
						Ext.ux.Toast.msg('提示',response.responseText,'error');
					}
				 });
				}
			});
			}
		},
		{   
			text : '停用',//批量停用
			xtype : 'button',
			handler : function(grid, rowIndex,colIndex) {
			var records = me.getSelectionModel().getSelection();//获得选中的列
			if(records == null || records.length == 0){
				Ext.ux.Toast.msg('提示','请选择要操作的记录', 'error');
				return;
			}
			var ids=[];//数组
			for(var r=0;r<records.length;r++){
				if(records[r].data.blFlag==0){
					 Ext.ux.Toast.msg('提示','只能操作已启用的数据，请确认','error');
					 return;
				}
					
				ids[r] = records[r].data.id;
				
			}
			Ext.Msg.confirm('确认','确认停用吗？',function(btn) {
				if (btn == 'yes') {
				 Ext.Ajax.request({
					url : basedev.realPath('/stopSomeEmployee.do'),
					jsonData : Ext.JSON.encode(ids),
			        headers: {
			              'Content-Type': 'application/json',
			              'Accept': 'application/json'
			        },
					success : function(response) {
						var result = Ext.JSON.decode(response.responseText);
                        if (result.success) {
						Ext.ux.Toast.msg('提示','停用成功');
						var grid = Ext.getCmp(basedev.baseEmployee.QUERY_BASE_EMPLOYEE_RESULT_GRID_ID);
						// 加载表格
						grid.getStore().load();
						} else {
							Ext.ux.Toast.msg('提示',result.msg,'error');
						}
					},
					failure : function(response) {
						Ext.ux.Toast.msg('提示',response.responseText,'error');
					}
				 });
				}
			});
			}
		} ];
		me.bbar = me.getPagingToolbar();
		me.callParent(cfg);
	}
});


/*
 * 查看窗口
 */
Ext.define('Basedev.baseEmployee.baseEmployeeWindow', {
	extend : 'Ext.window.Window',
	width : 700,
	modal : true,
	closeAction : 'destroy',
	baseData:null,
	title : '查看员工',
	baseEmployeeForm : null,
	getbaseEmployeeForm : function() {
		if (Ext.isEmpty(this.baseEmployeeForm)) {
			this.baseEmployeeForm = Ext.create("Basedev.baseEmployee.baseEmployeeForm");
		}
		return this.baseEmployeeForm;
	},
	// 关闭按钮
	cancelButton : null,
	getCancelButton : function() {
		var me = this;
		if (Ext.isEmpty(this.cancelButton)) {
			this.cancelButton = Ext.create('Ext.button.Button', {
			  text : '关闭',
			  handler : function() {
				me.close();
			 }
			});
		}
		return this.cancelButton;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getbaseEmployeeForm() ];
		me.buttons = [ me.getCancelButton() ];
		/*me.listeners = {
				beforeshow:function(){
					//me.getEditbaseEmployeeForm().getForm().reset();
					var baseForm = me.getbaseEmployeeForm().getForm();
					var array= new Array();
					var baseDatas = me.baseData;
					if(baseDatas != null){
					    var	len = baseDatas.length;
					    if(len >0){
					    	for(var i=0 ;i<len ;i++){
					    		array.push(baseDatas[i]+"");
							}
					    }
					    baseForm.findField('employeeType').setValue(array);
						}
					}
			};*/
		me.callParent([ cfg ]);
	}
});


/*
 * 修改/添加窗口
 */
Ext.define('Basedev.baseEmployee.EditbaseEmployeeWindow',
  {
	extend : 'Ext.window.Window',
	width : 700,
	modal : true,
	closeAction : 'destroy',
	editData:null,
	editbaseEmployeeForm : null,
	getEditbaseEmployeeForm : function() {
		if (Ext.isEmpty(this.editbaseEmployeeForm)) {
			this.editbaseEmployeeForm = Ext.create("Basedev.baseEmployee.EditbaseEmployeeForm");
		}
		return this.editbaseEmployeeForm;
	},
	// 取消按钮
	cancelButton : null,
	getCancelButton : function() {
		var me = this;
		if (Ext.isEmpty(this.cancelButton)) {
			this.cancelButton = Ext.create('Ext.button.Button',
			  {
				text : '取消',
				handler : function() {
					me.close();
			   }
			});
		}
		return this.cancelButton;
	},
	// 保存按钮
	saveButton : null,
	getSaveButton : function() {
		var me = this;
		if (Ext.isEmpty(this.saveButton)) {
			this.saveButton = Ext.create('Ext.button.Button',
			{
			cls : 'yellow_button',
			text : '保存',
			handler : function() {
				var baseEmployeeForm = me.getEditbaseEmployeeForm().getForm();
				// 校验产品表单
				if (!baseEmployeeForm.isValid()) {
					return;
				}
				var data ={
						 employeeTypeList : baseEmployeeForm.findField('employeeType').getValue(),
						 employeeCode : baseEmployeeForm.findField('employeeCode').getValue(),
						 employeeName : baseEmployeeForm.findField('employeeName').getValue(),
					     phone : baseEmployeeForm.findField('phone').getValue(),
						 address : baseEmployeeForm.findField('address').getValue(),
						 blFlag : baseEmployeeForm.findField('blFlag').getValue(),
						 ownerSite : baseEmployeeForm.findField('ownerSite').getValue(),
						 orgCode : baseEmployeeForm.findField('orgCode').getValue(),
						 id : baseEmployeeForm.findField('id').getValue()
				}
				var url = '';
				if (me.getEditbaseEmployeeForm().getOperatorType() == basedev.baseEmployee.STATE_ADD) {
					url = basedev.realPath('insertBaseEmployee.do');
				} else {
					url = basedev.realPath('updateBaseEmployee.do');
				}
				Ext.Ajax.request({
					url : url,
					jsonData : Ext.JSON.encode(data),
					headers : {
						'Content-Type' : 'application/json',
						'Accept' : 'application/json'
				},
				success : function(response) {
					var result = Ext.JSON.decode(response.responseText);
					if (result.success) {
						Ext.ux.Toast.msg('提示','保存成功');
						me.close();
						var grid = Ext.getCmp(basedev.baseEmployee.QUERY_BASE_EMPLOYEE_RESULT_GRID_ID);
						// 加载表格
						grid.getStore().load();
					} else {
						Ext.ux.Toast.msg('提示',result.msg);
					}
					},
					failure : function(response) {
						Ext.ux.Toast.msg('提示',response.responseText,'error');
					}
				});
				}
			});
		}
		return this.saveButton;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getEditbaseEmployeeForm() ];
		me.buttons = [ me.getCancelButton(), me.getSaveButton() ];
		me.listeners = {
				beforeshow:function(){
					//me.getEditbaseEmployeeForm().getForm().reset();
					var updateForm = me.getEditbaseEmployeeForm().getForm();
					var array= new Array();
					var editDatas = me.editData;
					if(editDatas != null){
					    var	len = editDatas.length;
					    if(len >0){
					    	for(var i=0 ;i<len ;i++){
					    		array.push(editDatas[i]+"");
							}
					    }
						updateForm.findField('employeeType').setValue(array);
						}
					}
			};
		me.callParent([ cfg ]);
	}
 });


/*
 * 程序入口
 */
Ext.onReady(function() {
	Ext.QuickTips.init();

	if (Ext.getCmp(basedev.baseEmployee.CONTENT_ID)) {
		return;
	};
	var querybaseEmployeeForm = Ext.create('Basedev.baseEmployee.QuerybaseEmployeeForm');
	var querybaseEmployeeResultGrid = Ext.create('Basedev.baseEmployee.QuerybaseEmployeeResultGrid');
	var content = Ext.create('Ext.panel.Panel', {
		id : basedev.baseEmployee.CONTENT_ID,
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		getQuerybaseEmployeeForm : function() {
			return querybaseEmployeeForm;
		},
		getQuerybaseEmployeeResultGrid : function() {
			return querybaseEmployeeResultGrid;
		},
		items : [ querybaseEmployeeForm, querybaseEmployeeResultGrid ]
	});

	Ext.getCmp('T_basedev-baseEmployee').add(content);
	// 加载表格数据
	querybaseEmployeeResultGrid.getStore().load();
});
