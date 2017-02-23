basedev.baseProduct.TAB_ID="T_basedev-baseProductIndex";  // 产品管理标签页ID
basedev.baseProduct.CONTENT_ID = "T_basedev-baseProductIndex_content";  // 产品管理内容panel ID
// 产品查询表单   
basedev.baseProduct.QUERY_BASE_PRODUCT_FORM_ID = "T_basedev-queryBaseProductForm";
// 产品列表
basedev.baseProduct.QUERY_BASE_PRODUCT_RESULT_GRID_ID = "T_basedev-queryBaseProductResultGrid";

basedev.baseProduct.STATE_ADD = 'add';   // 新增
basedev.baseProduct.STATE_UPDATE = 'update';   // 修改

basedev.baseProduct.BASE_PRODUCT_FORM_ID = "T_basedev-baseProductForm";
basedev.baseProduct.EDIT_BASE_PRODUCT_FORM_ID = "T_basedev-editBaseProductForm";

basedev.baseProduct.setUpProduct = function(level, upProduct){
	if (level == "1") { // 如果是一级产品
		upProduct.clearValue();
		upProduct.setDisabled(true);
	} else {
		upProduct.setDisabled(false);
	}
}


/**
 * 查询条件
 */
Ext.define('Basedev.baseProduct.QueryBaseProductForm',{
	extend:'Ext.form.Panel',
	id : basedev.baseProduct.QUERY_BASE_PRODUCT_FORM_ID,
	frame : true,
	title: '查询条件',
	/*defaults: {
		margin:'5 10 5 10'
	},*/
	layout : 'column',	
	defaultType : 'textfield',
	initComponent: function(){
		var me = this;
		me.items = [{
    		name: 'productName',
    		fieldLabel: '产品名称',
    		labelWidth: 90,					
    		columnWidth: .3
    	}, {
    		xtype : 'button',
			cls: 'yellow_button',
			text: '查询',
			width : 70,
			handler: function() {
				var selectResultPanel = Ext.getCmp(basedev.baseProduct.QUERY_BASE_PRODUCT_RESULT_GRID_ID);
				selectResultPanel.setVisible(true);
				selectResultPanel.getPagingToolbar().moveFirst();
			}
		}];
		me.callParent();
	}
});

/**
 * 产品model
 */
Ext.define('Basedev.baseProduct.BaseProductModel', {
	extend : 'Ext.data.Model',
	fields : [{
        name : 'id',
        type : 'string'
    },{
		name : 'hiddenId',
		type : 'string'
	}, {
		name : 'productCode',
		type : 'string'
	}, {
		name : 'productName',
		type : 'string'
	}, {
		name : 'upProductName',
		type : 'string'
	}, {
		name : 'regionMatchMode',
		type : 'string'
	}, {
		name : 'regionMatchModeName',
		type : 'string'
	}, {
		name : 'status',
		type : 'string'
	}, {
		name : 'statusName',
		type : 'string'
	}, {
		name : 'createTime',
		type : 'date',
		convert : dateConvert
	},{
        name : 'createUserCode',//创建人
        type : 'string'
    }, {
       name : 'modifyTime',//修改时间
       type : 'date',
       convert : dateConvert
    }, {
       name : 'modifyUserCode',//修改人
       type : 'string'
    }, {
		name : 'remark',
		type : 'string'
	}, {
		name : 'productLevel',
		type : 'string'
	}, {
       name : 'blFlag',//启用状态
       type : 'int'
    },  {
		name : 'productLevelName',
		type : 'string'
	}]
});

/**
 * 产品查看
 */
Ext.define('Basedev.baseProduct.BaseProductForm', {
	extend : 'Ext.form.Panel',
	id : basedev.baseProduct.BASE_PRODUCT_FORM_ID,
//	title : '查看产品',
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
		name : 'productCode',
		fieldLabel : '产品编码'
	}, {
		xtype : 'textfield',
		name : 'productName',
		fieldLabel : '产品名称'
	}, {
		xtype : 'textfield',
		name : 'productLevelName',
		fieldLabel : '产品级别'
	}, {
		xtype : 'textfield',
		name : 'upProductName',
		fieldLabel : '父级产品'
	}, {
		xtype : "textfield",
		name : 'regionMatchModeName',
		fieldLabel : '匹配模式'
	}, {
		xtype : "textfield",
		name : 'statusName',
		fieldLabel : '状态'
	}, {
        name: 'blFlag',
        fieldLabel: '启用'
    }, {
		xtype : 'textfield',
		name : 'remark',
		fieldLabel : '备注'
	}],
	setShowValue: function(record){
        var form = this.getForm();
        form.findField('blFlag').setValue(record.data.blFlag == 1 ? '是' : '否');
    }
});

/**
 * 产品新增/编辑
 */
Ext.define('Basedev.baseProduct.EditBaseProductForm',{
	extend:'Ext.form.Panel',
	id : basedev.baseProduct.EDIT_BASE_PRODUCT_FORM_ID,
//	title: '查看产品',
	frame: true,
    defaults: {
    	margin:'5 10 5 10',
    	labelWidth: 120,
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
		name : 'productCode',
		fieldLabel : '产品编码',
		maxLength : 10,
		allowBlank: false,
		validateOnBlur : true,
		regex : /^[A-Za-z0-9]+$/,
		regexText : '该输入项只能输入数字和字母',
		validator : function(field){
			if(!field){
				return true;
			}
			
			var editBaseProductInfoForm = Ext.getCmp(basedev.baseProduct.EDIT_BASE_PRODUCT_FORM_ID);
			var state = editBaseProductInfoForm.getOperatorType();
			if(basedev.baseProduct.STATE_UPDATE == state){
				return true;
			}
			
			var paramsObj = {productCode : field};
			var valid = false;
			Ext.Ajax.request({
		        url : basedev.realPath('uniqueCheckByProductCode.do'),
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
			return '该产品编号已存在';
		}
	}, {
		xtype : 'textfield',
		name : 'productName',
		fieldLabel : '产品名称',
		maxLength : 10,
		allowBlank: false,
		validateOnBlur : true,
		validator : function(field){
			if(!field){
				return true;
			}
			
			var editBaseProductInfoForm = Ext.getCmp(basedev.baseProduct.EDIT_BASE_PRODUCT_FORM_ID);
			
			var productCode = editBaseProductInfoForm.getForm().findField("productCode").getValue();
			var paramsObj = {productCode : productCode, productName : field, state : editBaseProductInfoForm.getOperatorType()};
			
			var valid = false;
			Ext.Ajax.request({
		        url : basedev.realPath('uniqueCheckByProductName.do'),
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
			return '该产品名称已存在';
		}
	},{
		xtype : "dictcombo",
		dictType : 'BASE_PRODUCT_LEVEL',
		name : 'productLevel',
		fieldLabel : '产品级别',
//		store : Ext.create('Basedev.baseProduct.RegionMatchModeStore'),
//		displayField : 'dictValue',
//		valueField : 'dictCode',
//		queryMode : 'local',
		
		allowBlank: false,
		editable : false,
		listeners: {
		    select: function(combo,records,eOpts) {
		    	var level = records[0].data.valueCode;
		    	// 父级产品
		    	var upProduct = this.up('form').getForm().findField("upProductCode");
				
				basedev.baseProduct.setUpProduct(level, upProduct);
		    }
		}
	},{
		xtype : 'commonProductSelector',
		name : 'upProductCode',
		fieldLabel : '父级产品',
		productLevel : '1'
	},{
		xtype : "dictcombo",
		dictType : 'REGION_MATCH_MODE',
		name : 'regionMatchMode',
		fieldLabel : '匹配模式',
//		store : Ext.create('Basedev.baseProduct.RegionMatchModeStore'),
//		displayField : 'dictValue',
//		valueField : 'dictCode',
//		queryMode : 'local',
		
		allowBlank: false,
		editable : false
	},{
		xtype : "dictcombo",
		dictType : 'BASE_PRODUCT_STATUS',
		name : 'status',
		fieldLabel : '状态',
		
//		store : Ext.create('Basedev.baseProduct.StatusStore'),
//		displayField : 'dictValue',
//		valueField : 'dictCode',
		
		allowBlank: false,
//		queryMode : 'local',
		editable : false
	},{
            xtype: 'checkbox',
            name: 'blFlag',
            boxLabel: '启用',
            inputValue: '1',
            uncheckedValue: '0'
        },{
		xtype : 'textfield',
		name : 'remark',
		fieldLabel : '备注',
//		width : 500,
		maxLength : 30,
		allowBlank: true
	}],
	operatorType : null,
	setOperatorType : function(state,record){
		this.operatorType = state;
		var editBaseProductForm = this.getForm();
		// 表单重置
		editBaseProductForm.reset();
		
		if(state == basedev.baseProduct.STATE_ADD){
			var upProduct = editBaseProductForm.findField("upProductCode");
			basedev.baseProduct.setUpProduct("", upProduct);
			
			var baseProductModel = Ext.create('Basedev.baseProduct.BaseProductModel');
			baseProductModel.data.blFlag = 1;
			editBaseProductForm.loadRecord(baseProductModel);
			//Ext.getCmp("basedev.baseProduct.blFlag").setValue(1)
			this.setFormFieldsReadOnly(false);
		} else if(state == basedev.baseProduct.STATE_UPDATE){
			var upProduct = editBaseProductForm.findField("upProductCode");
			basedev.baseProduct.setUpProduct(record.raw.productLevel, upProduct);
			
			var baseProductModel = Ext.create('Basedev.baseProduct.BaseProductModel',record.raw);
			
			// '0'是默认值
			if(record.raw.upProductCode != '0'){
				var comboboxBaseProductModel = Ext.create('BaseData.commonSelector.BaseProductModel');
				comboboxBaseProductModel.data.productCode = record.raw.upProductCode;
				comboboxBaseProductModel.data.productName = record.raw.upProductName;
				baseProductModel.data.upProductCode = comboboxBaseProductModel;
			}
			
			editBaseProductForm.loadRecord(baseProductModel);
			this.setFormFieldsReadOnly(true);
		}
	},
	getOperatorType : function(){
		return this.operatorType;
	},
	setFormFieldsReadOnly: function(flag) {
		var form = this.getForm();
		form.findField('productCode').setReadOnly(flag);
	}
});

/**
 * 产品Store
 */
Ext.define('Basedev.baseProduct.BaseProductStore',{
	extend:'Ext.data.Store',
	model: 'Basedev.baseProduct.BaseProductModel',
	pageSize: 10,
	autoLoad: false,
	proxy: {
		type: 'ajax',
		actionMethods: 'POST',
		url : basedev.realPath("getBaseProductList.do"),
		reader: {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp(basedev.baseProduct.QUERY_BASE_PRODUCT_FORM_ID);
			if (queryForm != null) {
				var p_name = queryForm.getForm().findField('productName').getValue();
				
				Ext.apply(operation, {
					params : {
						'q_sl_productName' : p_name
					}
				});	
			}
		}
	}
});

/**
 * 产品表格
 */
Ext.define('Basedev.baseProduct.QueryBaseProductResultGrid',{
	extend: 'Ext.grid.Panel',
	id: basedev.baseProduct.QUERY_BASE_PRODUCT_RESULT_GRID_ID,
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
				var baseProductWindow = Ext.getCmp(basedev.baseProduct.QUERY_BASE_PRODUCT_RESULT_GRID_ID).getBaseProductWindow();
				baseProductWindow.setTitle('查看产品');
				
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
				
				var baseProductForm = baseProductWindow.getBaseProductForm();
				var baseProductModel = Ext.create('Basedev.baseProduct.BaseProductModel',rowInfo.raw);
				baseProductForm.loadRecord(baseProductModel);
				baseProductForm.setShowValue(rowInfo);
				// 显示窗口
				baseProductWindow.show();
			}
		}, {
			iconCls : 'deppon_icons_edit',
			tooltip : '修改',// 修改
			handler : function(gridView, rowIndex, colIndex) {
				var editEaseProductWindow = Ext.getCmp(basedev.baseProduct.QUERY_BASE_PRODUCT_RESULT_GRID_ID).getEditBaseProductWindow();
				editEaseProductWindow.setTitle('编辑产品');
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
				
				editEaseProductWindow.getEditBaseProductForm().setOperatorType(basedev.baseProduct.STATE_UPDATE, rowInfo);
				// 打开窗口
				editEaseProductWindow.show();
			}
		}]
	}, {
		hidden : true,
		dataIndex : 'id'
	}, {
		text : '产品名称',
		// width : .3,
		width: 100,
		// renderer : change,
		dataIndex : 'productName'
	},/* {
		hidden : true,
		dataIndex : 'productLevel'
	},*/ {
		text : '产品级别',
		// width : .3,
		width: 100,
		// renderer : change,
		dataIndex : 'productLevelName'
	}, {
		text : '父级产品',
		// width : .3,
		width: 100,
		// renderer : change,
		dataIndex : 'upProductName'
	},/* {
		hidden : true,
		dataIndex : 'regionMatchMode'
	}, */{
		text : '匹配模式',
		// width : .3,
		width: 100,
		// renderer : change,
		dataIndex : 'regionMatchModeName'
	}, /*{
		hidden : true,
		dataIndex : 'status'
	},*/ {
		text : '状态',
		// width : .3,
		width: 100,
		// renderer : change,
		dataIndex : 'statusName'
	},{
        text: '启用',
        width: 80,
        dataIndex: 'blFlag',
        renderer: function(value){
            return value == 1 ? '是' : '否';
        }
    },{
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
    },{
        text : '备注',
        width: 100,
        dataIndex : 'remark'
    }],
    
	baseProductWindow : null,
	getBaseProductWindow : function(){
		me = this;
		//if(Ext.isEmpty(me.baseProductWindow)){
			me.baseProductWindow = Ext.create('Basedev.baseProduct.BaseProductWindow');
			
			
		//}
		return me.baseProductWindow;
	},
	editBaseProductWindow : null,
	getEditBaseProductWindow : function(){
		me = this;
		//if(Ext.isEmpty(me.editBaseProductWindow)){
			me.editBaseProductWindow = Ext.create('Basedev.baseProduct.EditBaseProductWindow');
	//	}
		return me.editBaseProductWindow;
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
            var paramsObj = {idList: ids, blFlag: blFlag}
            Ext.Ajax.request({
                url : basedev.realPath('/batchUpdateProductBlFlagById.do'),
                jsonData : Ext.JSON.encode(paramsObj),
                async : false,
                headers: {
                      'Content-Type': 'application/json',
                      'Accept': 'application/json'
                },
                success : function(response) {
                    var result = Ext.JSON.decode(response.responseText);
                    
                    if(result.success){
                        Ext.ux.Toast.msg('提示', blFlagName + '成功');
                        var grid = Ext.getCmp(basedev.baseProduct.QUERY_BASE_PRODUCT_RESULT_GRID_ID);
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
        });
    },
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.store = Ext.create('Basedev.baseProduct.BaseProductStore');
		me.tbar = [{
			text: '新增',
			handler: function(){
				var editBaseProductWindow = me.getEditBaseProductWindow();
				editBaseProductWindow.setTitle('新增产品');
				var editBaseProductForm = editBaseProductWindow.getEditBaseProductForm();
				editBaseProductForm.setOperatorType(basedev.baseProduct.STATE_ADD);
				
				editBaseProductWindow.show();
			}
		}, {
            text: '启用',
            handler: function(){
                me.setUpdateBlFlag(1, '启用');
            }
        }, {
            text: '停用',
            handler: function(){
                me.setUpdateBlFlag(0,'停用');
            }
        }];
		me.bbar = me.getPagingToolbar();
		me.callParent(cfg);
	}
});


Ext.define('Basedev.baseProduct.BaseProductWindow', {
	extend: 'Ext.window.Window',
	width: 700,
	modal: true,
	closeAction: 'destroy',
	title : '查看产品',
	baseProductForm : null,
	getBaseProductForm: function(){
		if (Ext.isEmpty(this.baseProductForm)) {
			this.baseProductForm = Ext.create("Basedev.baseProduct.BaseProductForm");
		}
		return this.baseProductForm;
	},
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
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
			me.getBaseProductForm()
	    ];
		me.buttons = [
			me.getCancelButton()
		];
		me.callParent([cfg]);
	}
});


Ext.define('Basedev.baseProduct.EditBaseProductWindow', {
	extend: 'Ext.window.Window',
	width: 700,
	modal: true,
	closeAction: 'destroy',
	editBaseProductForm : null,
	getEditBaseProductForm: function(){
		if (Ext.isEmpty(this.editBaseProductForm)) {
			this.editBaseProductForm = Ext.create("Basedev.baseProduct.EditBaseProductForm");
		}
		return this.editBaseProductForm;
	},
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
					var baseProductForm = me.getEditBaseProductForm().getForm();
					
					// 校验产品表单
					if (!baseProductForm.isValid()) {
						return;
					}
					
					var data = baseProductForm.getValues();
					
					var url = '';
					if (me.getEditBaseProductForm().getOperatorType() == basedev.baseProduct.STATE_ADD) {
						url = basedev.realPath('insertBaseProduct.do');
					} else {
						url = basedev.realPath('updateBaseProduct.do');
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
					        	
					        	var grid = Ext.getCmp(basedev.baseProduct.QUERY_BASE_PRODUCT_RESULT_GRID_ID);
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
			me.getEditBaseProductForm()
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
Ext.define('Basedev.baseProduct.BaseProductWindow', {
	extend: 'Ext.window.Window',
	width: 700,
	modal: true,
	closeAction: 'destroy',
	baseProductForm : null,
	getBaseProductForm: function(){
		if (Ext.isEmpty(this.baseProductForm)) {
			this.baseProductForm = Ext.create("Basedev.baseProduct.BaseProductForm");
		}
		return this.baseProductForm;
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
	constructor: function(config){
		var me = this,
			cfg = Ext.apply({}, config);
		me.items = [
			me.getBaseProductForm()
	    ];
		me.buttons = [
			me.getCancelButton()
		];
		me.callParent([cfg]);
	}
});


Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp(basedev.baseProduct.CONTENT_ID)) {
		return;
	};
	
	var queryBaseProductForm = Ext.create('Basedev.baseProduct.QueryBaseProductForm');
	var queryBaseProductResultGrid = Ext.create('Basedev.baseProduct.QueryBaseProductResultGrid');
	
	var content = Ext.create('Ext.panel.Panel', {
		id: basedev.baseProduct.CONTENT_ID,
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		getQueryBaseProductForm: function() {
			return queryBaseProductForm;
		},
		getQueryBaseProductResultGrid: function() {
			return queryBaseProductResultGrid;
		},
		items: [
			queryBaseProductForm,
			queryBaseProductResultGrid
		]
	});
	
	Ext.getCmp(basedev.baseProduct.TAB_ID).add(content);
	// 加载表格数据
	queryBaseProductResultGrid.getStore().load();
});

