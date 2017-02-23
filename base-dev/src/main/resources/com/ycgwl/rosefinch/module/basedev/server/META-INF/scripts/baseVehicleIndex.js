basedev.baseVehicle.TAB_ID="T_basedev-baseVehicleIndex";  // 车辆维护标签页ID
basedev.baseVehicle.CONTENT_ID = "T_basedev-baseVehicleIndex_content";  // 车辆内容panel ID

basedev.baseVehicle.QUERY_BASE_VEHICLE_FORM_ID = "T_basedev-querybaseVehicleForm";// 车辆查询表单   

basedev.baseVehicle.QUERY_BASE_VEHICLE_RESULT_GRID_ID = "T_basedev-querybaseVehicleResultGrid";// 车辆列表

basedev.baseVehicle.STATE_ADD = 'add';   // 新增
basedev.baseVehicle.STATE_UPDATE = 'update';   // 修改

basedev.baseVehicle.BASE_VEHICLE_FORM_ID = "T_basedev-baseVehicleForm";
basedev.baseVehicle.EDIT_BASE_VEHICLE_FORM_ID = "T_basedev-editbaseVehicleForm";//编辑车辆表单

/**
 * 查询条件
 */
Ext.define('Basedev.baseVehicle.QuerybaseVehicleForm',{
	extend:'Ext.form.Panel',
	id : basedev.baseVehicle.QUERY_BASE_VEHICLE_FORM_ID,
	frame : true,
	title: '查询条件',
	layout : 'column',	
	defaultType : 'textfield',
	initComponent: function(){
		var me = this;
		me.items = [{
    		name: 'vehicelNo',
    		fieldLabel: '车牌号',
    		labelWidth: 90,					
    		columnWidth: .3
    	}, {
    		xtype : 'button',
			cls: 'yellow_button',
			text: '查询',
			width : 70,
			handler: function() {
				var selectResultPanel = Ext.getCmp(basedev.baseVehicle.QUERY_BASE_VEHICLE_RESULT_GRID_ID);
				selectResultPanel.setVisible(true);
				selectResultPanel.getPagingToolbar().moveFirst();
			}
		}];
		me.callParent();
	}
});

/**
 * 车辆model
 */
Ext.define('Basedev.baseVehicle.baseVehicleModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'id',
		type : 'string'
	},/* {
		name : 'runNo'//班次号
	}, */{
		name : 'vehicelNo',//车牌号
		type : 'string'
	}, {
        name : 'vehicelCode',//车辆编码
        type : 'string'
    },{
		name : 'ownerType',//所有类型
		type : 'string'
	}, {
		name : 'vehicelType',//车辆类型
		type : 'string'
	}, {
		name : 'blTest',//是否考核
		type : 'int'
	}, {
    	name : 'ownerOrg'//所属机构
	}, {
		name : 'ownerCom',//所属公司
		type : 'string'
	}, {
		name : 'vehicelModel',//车型
		type : 'string'
	}, {
	   name : 'boxType',//车厢类型
	   type : 'string'
	}, {
	   name : 'boxSize',//货箱大小
	   type : 'string'
	}, {
	   name : 'boxTons',//载重
	   type : 'string'
	}, {
	   name : 'blRun',//是否正常运营
	   type : 'int'
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
        name: 'owerTypeName',//所有类型
        type: 'string'
    }, {
        name: 'vehicelModelName',//车型
        type: 'string'
    }, {
        name: 'boxTypeName',//车厢类型
        type: 'string'
    }, {
        name: 'ownerOrgName',
        type: 'string'
    }/*, {
        name: 'runName',
        type: 'string'
    }*/]
});

/**
 * 车辆Store
 */
Ext.define('Basedev.baseVehicle.baseVehicleStore',{
	extend:'Ext.data.Store',
	model: 'Basedev.baseVehicle.baseVehicleModel',
	pageSize: 10,
	autoLoad: false,
	proxy: {
		type: 'ajax',
		actionMethods: 'POST',
		url : basedev.realPath("getPaginationBaseVehicleList.do"),
		reader: {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp(basedev.baseVehicle.QUERY_BASE_VEHICLE_FORM_ID);
			if (queryForm != null) {
				var p_name = queryForm.getForm().findField('vehicelNo').getValue();
				
				Ext.apply(operation, {
					params : {
						'q_sl_vehicelNo' : p_name
					}
				});	
			}
		}
	}
});

/**
 * 车辆表格
 */
Ext.define('Basedev.baseVehicle.QuerybaseVehicleResultGrid',{
	extend: 'Ext.grid.Panel',
	id: basedev.baseVehicle.QUERY_BASE_VEHICLE_RESULT_GRID_ID,
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
				var baseVehicleWindow = Ext.getCmp(basedev.baseVehicle.QUERY_BASE_VEHICLE_RESULT_GRID_ID).getbaseVehicleWindow();
				baseVehicleWindow.setTitle('查看车辆');
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
				var baseVehicleForm = baseVehicleWindow.getbaseVehicleForm();
				var baseVehicleModel = Ext.create('Basedev.baseVehicle.baseVehicleModel',rowInfo.raw);
				baseVehicleForm.loadRecord(baseVehicleModel);
				baseVehicleForm.setShowValue(rowInfo);
				// 显示窗口
				baseVehicleWindow.show();
			}
		}, {
			iconCls : 'deppon_icons_edit',
			tooltip : '修改',// 修改
			handler : function(gridView, rowIndex, colIndex) {
				var editEaseConfigWindow = Ext.getCmp(basedev.baseVehicle.QUERY_BASE_VEHICLE_RESULT_GRID_ID).getEditbaseVehicleWindow();
				editEaseConfigWindow.setTitle('编辑车辆');
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
				editEaseConfigWindow.getEditbaseVehicleForm().setOperatorType(basedev.baseVehicle.STATE_UPDATE, rowInfo);
				// 打开窗口
				editEaseConfigWindow.show();
			}
		}/*, {
			iconCls : 'deppon_icons_delete',
			tooltip : '删除',// 删除
			handler : function(grid, rowIndex, colIndex) {
				 删除 
				var rowInfo = Ext
						.getCmp(basedev.baseVehicle.QUERY_BASE_VEHICLE_RESULT_GRID_ID).store
						.getAt(rowIndex);
				
				var vehicleId = rowInfo.data.id;
				
				Ext.Msg.confirm('确认',
						'确认删除吗？',
						function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : basedev.realPath('/deleteBaseVehicle.do'),
									params : {
										id: vehicleId
									},
									success : function(response) {
							        	var result = Ext.JSON.decode(response.responseText);
							        	
							        	if(result.success){
							        		Ext.ux.Toast.msg('提示', '删除成功');
											var grid = Ext.getCmp(basedev.baseVehicle.QUERY_BASE_VEHICLE_RESULT_GRID_ID);
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
	   text: '车辆编号',
	   width: 100,
	   dataIndex: 'vehicelCode'
	}, {
		text : '车牌号',
		width: 120,
		dataIndex : 'vehicelNo'
	}, {
		text : '所属机构',
		width: 180,
		dataIndex : 'ownerOrgName'
	}, {
		text: '所属公司',
		width: 180,
		dataIndex: 'ownerCom'
	}, {
		text: '车辆类型',
		width: 120,
		dataIndex: 'vehicelType'
	}, {
		text: '车型',
		width: 120,
		dataIndex: 'vehicelModelName'
	}, {
		text: '车厢类型',
		width: 120,
		dataIndex: 'boxTypeName'
	}, {
		text: '货箱大小(米)',
		width: 120,
		dataIndex: 'boxSize'
	}, {
		text: '载重(KG)',
		width: 120,
		dataIndex: 'boxTons'
	},{
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
	}, {
        text : '备注',
        width: 150,
        dataIndex : 'remark'
    }],
    
	baseVehicleWindow : null,
	getbaseVehicleWindow : function(){
		me = this;
		if(Ext.isEmpty(me.baseVehicleWindow)){
			me.baseVehicleWindow = Ext.create('Basedev.baseVehicle.baseVehicleWindow');
		}
		return me.baseVehicleWindow;
	},
	editbaseVehicleWindow : null,
	getEditbaseVehicleWindow : function(){
		me = this;
		if(Ext.isEmpty(me.editbaseVehicleWindow)){
			me.editbaseVehicleWindow = Ext.create('Basedev.baseVehicle.EditbaseVehicleWindow');
		}
		return me.editbaseVehicleWindow;
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
            ids[i] = selectList[i].data.id;
        }
        Ext.Msg.confirm('确认', '确认'+ blFlagName + '吗？', function(btn){
            if (btn == 'yes') {
                var paramsObj = {idList: ids, blFlag: blFlag};
                Ext.Ajax.request({
                    url : basedev.realPath('/batchUpdateVehicleBlFlagById.do'),
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
                           var grid = Ext.getCmp(basedev.baseVehicle.QUERY_BASE_VEHICLE_RESULT_GRID_ID);
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
		me.store = Ext.create('Basedev.baseVehicle.baseVehicleStore');
		me.tbar = [{
			text: '新增',
			handler: function(){
				var editbaseVehicleWindow = me.getEditbaseVehicleWindow();
				editbaseVehicleWindow.setTitle('新增车辆');
				var editbaseVehicleForm = editbaseVehicleWindow.getEditbaseVehicleForm();
				editbaseVehicleForm.setOperatorType(basedev.baseVehicle.STATE_ADD);	
				editbaseVehicleWindow.show();
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
					ids[i] = selectList[i].data.id;
				}
				Ext.Msg.confirm('确认',
					'确认删除吗？',
					function(btn) {
						if (btn == 'yes') {
							var paramsObj = {idList: ids};
							Ext.Ajax.request({
								url : basedev.realPath('/batchDeleteBaseVehicleById.do'),
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
										var grid = Ext.getCmp(basedev.baseVehicle.QUERY_BASE_VEHICLE_RESULT_GRID_ID);
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
                if(!Ext.isEmpty(me.baseVehicleWindow)){
                	me.baseVehicleWindow.removeAll();
                    me.baseVehicleWindow.destroy();
                }
                if(!Ext.isEmpty(me.editbaseVehicleWindow)){
                    me.editbaseVehicleWindow.removeAll();
                    me.editbaseVehicleWindow.destroy();
                }
            }
        };
		me.callParent(cfg);
	}
});

/**
 * 车辆查看
 */
Ext.define('Basedev.baseVehicle.baseVehicleForm', {
    extend : 'Ext.form.Panel',
    id : basedev.baseVehicle.BASE_VEHICLE_FORM_ID,
    frame : true,
    defaults : {
        margin : '5 10 5 5',
        labelWidth : 90,
        readOnly : true,
        width: 240
    },
    layout : {
        type : 'table',
        columns : 2
    },
    defaultType : 'textfield',

    items : [{
        name: 'vehicelCode',
        fieldLabel: '车辆编号'
    },{
        name : 'vehicelNo',
        fieldLabel : '车牌号'
    }, {
        name: 'owerTypeName',
        fieldLabel:'所属类型'
    }, { 
        name: 'vehicelType',
        fieldLabel: '车辆类型'
    }, {
        name: 'ownerOrgName',
        fieldLabel: '所属机构'
    }, {
        name: 'ownerCom',
        fieldLabel: '所属公司'
    }, {
        name: 'vehicelModelName',
        fieldLabel: '车型'
    }, {
        name: 'boxTypeName',
        fieldLabel: '车厢类型'
    }, {
        name: 'boxSize',
        fieldLabel: '货箱大小'
    }, {
        name: 'boxTons',
        fieldLabel: '载重'
    }, {
        name: 'blTest',
        fieldLabel: '考核'
    }, {
        name: 'blRun',
        fieldLabel: '正常运营',
        colspan: 1
    }, {
        name: 'blFlag',
        fieldLabel: '启用',
        colspan: 2
    },{
        name: 'remark',
        xtype: 'textareafield',
        fieldLabel: '备注',
        colspan: 2,
        width:480,
        height: 60
    }/*, {
        name : 'createUserCode',
        fieldLabel : '创建人工号'
    }, {
        name : 'modifyUserCode',
        fieldLabel : '修改人工号'
    }*/],
    setShowValue: function(recode){
    	var stateName = recode.data.blFlag == '1' ? '是' : '否';
        this.getForm().findField("blFlag").setValue(stateName);
        var blTestName = recode.data.blTest == 1 ? '是' : '否';
        this.getForm().findField("blTest").setValue(blTestName);
        var blRunName = recode.data.blRun == 1 ? '是' : '否';
        this.getForm().findField("blRun").setValue(blRunName);
        var boxTons = recode.data.boxTons;//货箱大小
        if(boxTons){
            this.getForm().findField("boxTons").setValue(boxTons+"KG");
        }
        var boxSize = recode.data.boxSize;//载重
        if(boxSize){
            this.getForm().findField("boxSize").setValue(boxSize+"米");
        }
    }
});


/**
 * 查看窗口
 */
Ext.define('Basedev.baseVehicle.baseVehicleWindow', {
    extend: 'Ext.window.Window',
    width: 570,
    modal: true,
    closeAction: 'hide',
    baseVehicleForm : null,
    getbaseVehicleForm: function(){
        if (Ext.isEmpty(this.baseVehicleForm)) {
            this.baseVehicleForm = Ext.create("Basedev.baseVehicle.baseVehicleForm");
        }
        return this.baseVehicleForm;
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
            me.getbaseVehicleForm()
        ];
        me.buttons = [
            me.getCancelButton()
        ];
        me.callParent([cfg]);
    }
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp(basedev.baseVehicle.CONTENT_ID)) {
		return;
	};
	
	var querybaseVehicleForm = Ext.create('Basedev.baseVehicle.QuerybaseVehicleForm');
	var querybaseVehicleResultGrid = Ext.create('Basedev.baseVehicle.QuerybaseVehicleResultGrid');
	
	var content = Ext.create('Ext.panel.Panel', {
		id: basedev.baseVehicle.CONTENT_ID,
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		getQuerybaseVehicleForm: function() {
			return querybaseVehicleForm;
		},
		getQuerybaseVehicleResultGrid: function() {
			return querybaseVehicleResultGrid;
		},
		items: [
			querybaseVehicleForm,
			querybaseVehicleResultGrid
		]
	});
	
	Ext.getCmp(basedev.baseVehicle.TAB_ID).add(content);
	// 加载表格数据
	querybaseVehicleResultGrid.getStore().load();
});

/**
 * 配置新增/编辑
 */
Ext.define('Basedev.baseVehicle.EditbaseVehicleForm',{
	extend:'Ext.form.Panel',
	id : basedev.baseVehicle.EDIT_BASE_VEHICLE_FORM_ID,
	frame: true,
    defaults: {
    	margin:'5 20 5 15',
    	labelWidth: 100,
    	allowBlank: true,
	    validateOnChange: false
    },
    layout : {
    	type : 'table',
    	columns: 2
    },
	defaultType : 'textfield',
	operatorType : null,
	setOperatorType : function(state,record){
		this.operatorType = state;
		var editbaseVehicleForm = this.getForm();
		// 表单重置
		editbaseVehicleForm.reset();
		
		if(state == basedev.baseVehicle.STATE_ADD){
			var baseVehicleModel = Ext.create('Basedev.baseVehicle.baseVehicleModel');
			baseVehicleModel.data.blFlag = 1;
			editbaseVehicleForm.loadRecord(baseVehicleModel);
			editbaseVehicleForm.findField('vehicelCode').setReadOnly(false);
		} else if(state == basedev.baseVehicle.STATE_UPDATE){
			var baseVehicleModel = Ext.create('Basedev.baseVehicle.baseVehicleModel',record.raw);
			
			if(record.data.ownerOrg){
    			var comboboxBaseOrgModel = Ext.create('BaseData.commonSelector.BaseOrgModel');
                comboboxBaseOrgModel.data.orgCode = record.data.ownerOrg;
                comboboxBaseOrgModel.data.orgName = record.data.ownerOrgName;
                baseVehicleModel.data.ownerOrg = comboboxBaseOrgModel;
			}
			editbaseVehicleForm.loadRecord(baseVehicleModel);
			editbaseVehicleForm.findField('vehicelCode').setReadOnly(true);
		}
	},
	getOperatorType : function(){
		return this.operatorType;
	},
	initComponent: function(){
	   var me = this;
       me.items = [{
            xtype : 'textfield',
            name : 'id',
            hidden : true,
            id : 'basedev.baseVehicle.vehicleId'
        },  {
            xtype: 'textfield',
            name: 'vehicelCode',
            fieldLabel: '车辆编号',
            maxLength : 20,
            allowBlank: false,
            validateOnBlur : true,
            regex : /^[A-Za-z0-9]+$/,
            regexText : '该输入项只能输入数字和字母',
            validator : function(field){
                if(!field){
                    return true;
                }
                
                var editVehicleForm = Ext.getCmp(basedev.baseVehicle.EDIT_BASE_VEHICLE_FORM_ID);
                var state = editVehicleForm.getOperatorType();
                if(basedev.baseVehicle.STATE_UPDATE == state){
                    return true;
                }
                
                var paramsObj = {vehicelCode : field};
                var valid = false;
                Ext.Ajax.request({
                    url : basedev.realPath('uniqueCheckByVehicleCode.do'),
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
                return '该车辆编号已存在';
            }
        },{
            xtype : 'textfield',
            name : 'vehicelNo',
            fieldLabel : '车牌号',
//            maxLength : 20,
            allowBlank: false,
            validateOnBlur : true,
            validator : function(field){
                if(!field){
                    return true;
                }
               /* var len = basedev.baseVehicle.checkstrlenght(field);
                if(len > 20){
                    return '长度不能超过20个字节（一个汉字占3个字节）';    
                }*/
                var result = false;
                var express = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[-·●.]{0,1}[A-Z0-9]{4,5}[A-Z0-9挂学警港澳]{1}$/;
                var result = express.test(field);
                if(!result){
                    return '输入的车牌号格式不正确';
                }
                var editbaseVehicleInfoForm = Ext.getCmp(basedev.baseVehicle.EDIT_BASE_VEHICLE_FORM_ID);
                var vehicleId = Ext.getCmp('basedev.baseVehicle.vehicleId').getValue();
                var paramsObj = {id : vehicleId, vehicelNo : field};
                var valid = false;
                Ext.Ajax.request({
                    url : basedev.realPath('uniqueCheckBaseVehicleByVehicleNo.do'),
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
                return '该车牌号已存在';
            }
        },{
            xtype : 'dictcombo',
            dictType : 'BASE_VEHICLE_ALL_TYPE',
            name: 'ownerType',
            fieldLabel:'所属类型',
            editable : false
        },{
            xtype: 'textfield',
            name: 'vehicelType',
            fieldLabel: '车辆类型',
            maxLength : 20
        },{
        	xtype : 'commonOrgSelector',
            name : 'ownerOrg',
            fieldLabel : '所属机构',
            blFlag : 1
        },{
            xtype: 'textfield',
            name: 'ownerCom',
            fieldLabel: '所属公司',
            maxLength : 30
        },{
            name: 'vehicelModel',
            fieldLabel: '车型',
            xtype : 'dictcombo',
            dictType : 'BASE_VEHICLE_MODEL_TYPE',
            editable : false
        },{
            name: 'boxType',
            fieldLabel: '车厢类型',
            xtype : 'dictcombo',
            dictType : 'BASE_VEHICLE_BOX_TYPE',
            editable : false
        },{
            xtype: 'numberfield',
            name: 'boxSize',
            fieldLabel: '货箱大小',
            decimalPrecision: 2, // 精确地小数点后两位
            allowDecimals: true,
            minValue: 0,
            maxValue: 999.99
        },{
            xtype: 'numberfield',
            name: 'boxTons',
            fieldLabel: '载重',
            decimalPrecision: 2, // 精确地小数点后两位
            allowDecimals: true,
            minValue: 0,
            maxValue: 99999999.99
        },{
            name: 'blTest',
            fieldLabel: '考核',
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
            editable : false
        },{
            xtype: 'checkbox',
            name: 'blFlag',
            boxLabel: '启用',
            inputValue: '1',
            uncheckedValue: '0'
        },{
            name: 'blRun',
            fieldLabel: '正常运营',
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
            editable : false,
            colspan: 2
        },{
            name: 'remark',
            fieldLabel: '备注',
            xtype: 'textareafield',
            maxLength : 100,
            colspan: 2,
            height: 55,
            width: 550
        }];
        me.callParent();
	}
});

Ext.define('Basedev.baseVehicle.EditbaseVehicleWindow', {
	extend: 'Ext.window.Window',
	width: 650,
	modal: true,
	closeAction: 'hide',
	editbaseVehicleForm : null,
	getEditbaseVehicleForm: function(){
		if (Ext.isEmpty(this.editbaseVehicleForm)) {
			this.editbaseVehicleForm = Ext.create("Basedev.baseVehicle.EditbaseVehicleForm");
		}
		return this.editbaseVehicleForm;
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
					var baseVehicleForm = me.getEditbaseVehicleForm().getForm();
					// 校验产品表单
					if (!baseVehicleForm.isValid()) {
						return;
					}
					var data = baseVehicleForm.getValues();
					var url = '';
					if (me.getEditbaseVehicleForm().getOperatorType() == basedev.baseVehicle.STATE_ADD) {
						url = basedev.realPath('insertBaseVehicle.do');
					} else {
						url = basedev.realPath('updateBaseVehicle.do');
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
					        	
					        	var grid = Ext.getCmp(basedev.baseVehicle.QUERY_BASE_VEHICLE_RESULT_GRID_ID);
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
			me.getEditbaseVehicleForm()
	    ];
		me.buttons = [
			me.getCancelButton(),
			me.getSaveButton()
		];
		me.callParent([cfg]);
	}
});
