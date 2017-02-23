// 管理内容panel
basedev.classes.CONTENT_ID = "T_basedev-classes_content"; 
// ID
// 查询表单
basedev.classes.QUERY_BASE_CLASSES_FORM_ID = "T_basedev-queryclassesForm";
// 列表
basedev.classes.QUERY_BASE_CLASSES_RESULT_GRID_ID = "T_basedev-queryclassesResultGrid";
// 新增
basedev.classes.STATE_ADD = 'add'; 
// 修改
basedev.classes.STATE_UPDATE = 'update'; 
//详细列表(查看)
basedev.classes.QUERY_BASE_CLASSESDETAIL_RESULT_GRID_ID1="T_basedev-queryclassesDetailGrid1";
//详细列表(修改/新增)
basedev.classes.QUERY_BASE_CLASSESDETAIL_RESULT_GRID_ID2="T_basedev-queryclassesDetailGrid2";
basedev.classes.BASE_CLASSES_FORM_ID = "T_basedev-classesForm";
//新增表格
basedev.classes.ADD_BASE_CLASSES_FORM_ID = "T_basedev-addclassesForm";
//修改表格
basedev.classes.EDIT_BASE_CLASSES_FORM_ID = "T_basedev-editclassesForm";
//时间格式
basedev.classes.FORMAT_TIME = 'G:i'; 

/**
 * 车线model
 */
Ext.define('BaseData.commonSelector.BasebasedevbasedevLineModel', {
    extend : 'Ext.data.Model',
    fields : [ {
        name : 'lineName',
        type : 'string'
    }, {
        name : 'lineCode',
        type : 'string'
    }]
});

/** 车线store */
Ext.define('BaseData.commonSelector.BasebasedevbasedevLineStore', {
    extend : 'Ext.data.Store',
    model : 'BaseData.commonSelector.BasebasedevbasedevLineModel',
    pageSize : 10,
    proxy : {
        type : 'ajax',
        url : basedev.realPath("queryCarLineForClasses.do"),
        actionMethods : 'POST',
        reader : {
            type : 'json',
            root : 'list',
            totalProperty : 'count'
        }
    }
});

/**车线选择器 */
Ext.define('BaseData.commonSelector.BasebasedevbasedevLineSelector', {
    extend : 'Dpap.commonSelector.CommonCombSelector',
    alias : 'widget.commonBasebasedevbasedevLineSelector',
    listWidth : 200,// 设置下拉框宽度
    displayField : 'lineName',// 显示名称
    valueField : 'lineCode',// 值
    queryParam : 'lineName',// 查询参数
    showContent : '{lineName}&nbsp;&nbsp;&nbsp;{lineCode}',// 显示表格列
    typeMode: 1, // 1.显示菜单和页面元素(默认)；2.全部；3.显示菜单；4.不显示页面元素；
    queryCaching : false,
    constructor : function(config) {
        var me = this, cfg = Ext.apply({}, config);
        me.store = Ext.create('BaseData.commonSelector.BasebasedevbasedevLineStore');
        me.store.addListener('beforeload', function(store, operation, eOpts) {
            var searchParams = {
                'q_sl_lineName': operation.params.lineName
            };
            Ext.apply(operation, {
                params : searchParams
            });
        });
        me.callParent([cfg]);
    }
});

/**
 * 车辆model
 */
Ext.define('BaseData.commonSelector.BasebasedevbasedevVehicleModel', {
    extend : 'Ext.data.Model',
    fields : [ {
        name : 'vehicelNo',
        type : 'string'
    },{
        name : 'vehicelCode',
        type : 'string'
    }]
});

/** 车辆store */
Ext.define('BaseData.commonSelector.BasebasedevbasedevVehicleStore', {
    extend : 'Ext.data.Store',
    model : 'BaseData.commonSelector.BasebasedevbasedevVehicleModel',
    pageSize : 10,
    proxy : {
        type : 'ajax',
        url : basedev.realPath("queryVehicelNo.do"),
        actionMethods : 'POST',
        reader : {
            type : 'json',
            root : 'list',
            totalProperty : 'count'
        }
    }
});

/**车辆选择器 */
Ext.define('BaseData.commonSelector.BasebasedevbasedevVehicleSelector', {
    extend : 'Dpap.commonSelector.CommonCombSelector',
    alias : 'widget.commonBasebasedevbasedevVehicleSelector',
    listWidth : 200,// 设置下拉框宽度
    displayField : 'vehicelNo',// 显示名称
    valueField : 'vehicelCode',// 值
    queryParam : 'vehicelNo',// 查询参数
    showContent : '{vehicelNo}&nbsp;&nbsp;&nbsp;{vehicelCode}',// 显示表格列
    typeMode: 1, // 1.显示菜单和页面元素(默认)；2.全部；3.显示菜单；4.不显示页面元素；
    queryCaching : false,
    constructor : function(config) {
        var me = this, cfg = Ext.apply({}, config);
        me.store = Ext.create('BaseData.commonSelector.BasebasedevbasedevVehicleStore');
        me.store.addListener('beforeload', function(store, operation, eOpts) {
            var searchParams = {
                'q_sl_vehicelNo': operation.params.vehicelNo,
                'q_int_blFlag': cfg.blFlag
            };
            Ext.apply(operation, {
                params : searchParams
            });
        });
        me.callParent([cfg]);
    }
});



/**
 * 车次详细model
 */
Ext.define('basedev.classes.classesDetailModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'long'
	}, {
		name : 'classesName',
		type : 'string'
	},  {
        name : 'classesCode',
        type : 'string'
    }, {
		name : 'orderBy',
		type : 'number'
	},
	{
		name : 'viaType',
		type : 'number'
	}, {
		name : 'viaTypeName',
		type : 'string'
	}, {
		name : 'currSiteCode',
		type : 'string'
	}, {
		name : 'currSiteName',
		type : 'string'
	}, {
		name : 'nextSiteCode',
		type : 'string'
	}, {
		name : 'nextSiteName',
		type : 'string'
	}, {
		name : 'currRegionCode',
		type : 'string'
	}, {
		name : 'currRegionName',
		type : 'string'
	}, {
		name : 'nextRegionCode',
		type : 'string'
	}, {
		name : 'nextRegionName',
		type : 'string'
	},
	{
		name : 'departTime',
		type : 'string'
	}, {
		name : 'arrivalTime',
		type : 'string'
	}, {
		name : 'stayTime',
		type : 'number'
	},
	{
		name : 'arriveMileage',
		type : 'double'
	},
	{
		name : 'arriveTime',
		type : 'number'
	},
	{
		name : 'classType',
		type : 'string'
	},
	{
		name : 'classTypeName',
		type : 'string'
	},
	{
		name : 'delFlag',
		type : 'number'
	}]
});

/**
 * 班次详情查看表格1
 */
Ext.define('basedev.classes.QueryclassesDetailGrid1', {
    extend : 'Ext.grid.Panel',
    id:basedev.classes.QUERY_BASE_CLASSESDETAIL_RESULT_GRID_ID1,
    title:'详细信息',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	// 设置表格不可排序
	sortableColumns : false,
	// 去掉排序的倒三角
	enableColumnHide : false,
	// 设置“如果查询的结果为空，则提示”
	emptyText : '没有数据',
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
      			hidden : true,
      			dataIndex : 'id',
      			align : 'center'
      		}, 
      		 {
      			hidden : true,
      			dataIndex : 'classesCode',
      			align : 'center'
      		},
      		{
      			text : '班次名称',
      			align : 'center',
      			dataIndex : 'classesName',
      			width : 80
      		},{
      			text : '途径点排序',
      			dataIndex : 'orderBy',
      			align : 'center',
      			width : 80
      		}, 
      		{
      			hidden : true,
      			dataIndex : 'viaType',
      			align : 'center'
      		}, 
      		{
      			text : '途径类型',
      			dataIndex : 'viaTypeName',
      			align : 'center',
      			width : 80
      		}, 
      		{
      			hidden : true,
      			dataIndex : 'currSiteCode',
      			align : 'center'
      		},
      		{
      			text : '当前转运/分拨中心',
      			dataIndex : 'currSiteName',
      			align : 'center',
      			width : 160
      		},
      		{
      			hidden : true,
      			dataIndex : 'nextSiteCode',
      			align : 'center'
      		},
      		{
      			text : '下一转运/分拨中心',
      			dataIndex : 'nextSiteName',
      			align : 'center',
      			width : 160
      		}, 
      		{
      			hidden : true,
      			dataIndex : 'currRegionCode',
      			align : 'center'
      		},
      		{
      			text : '当前行政区域',
      			dataIndex : 'currRegionName',
      			align : 'center',
      			width : 160
      		},
      		{
      			hidden : true,
      			dataIndex : 'nextRegionCode',
      			align : 'center'
      		},
      		{
      			text : '下一行政区域',
      			dataIndex : 'nextRegionName',
      			align : 'center',
      			width : 160
      		}, {
      			text : '出发时间',
      			dataIndex : 'departTime',
      			align : 'center',
      			width : 80
      		},{
      			text : '到达时间',
      			dataIndex : 'arrivalTime',
      			align : 'center',
      			width : 80
      		}, 
      		 
      		 {
      			text : '驶到里程(千米/Km)',
      			dataIndex : 'arriveMileage',
      			align : 'center',
      			width : 120
      		},
      		 {
      			text : '停留时间(分/m)',
      			dataIndex : 'stayTime',
      			align : 'center',
      			width : 100
      		},
      		 {
      			text : '驶到用时(分/m)',
      			dataIndex : 'arriveTime',
      			align : 'center',
      			width : 100
      		},
      		{
      			hidden : true,
      			dataIndex : 'classType',
      			align : 'center'
      		}, 
      		{
      			text : '运输方式',
      			dataIndex : 'classTypeName',
      			align : 'center',
      			width : 80
      		},
      		{
      			text : '备注',
      			dataIndex : 'remark',
      			align : 'center',
      			width : 80
      		}],
      	    constructor: function(config){
      	        var me = this;
      	        me.store = Ext.create('Ext.data.Store', {
      	            storeId:'simpsonsStore',
      	            fields:['id','classesCode','classesName','orderBy','viaType','viaTypeName', 'currSiteCode','currSiteName','nextSiteCode','nextSiteName','currRegionName','currRegionCode','nextRegionCode','nextRegionName','departTime','arrivalTime','arriveMileage', 'arriveTime', 'remark', 'stayTime','arriveMileage','classType','classTypeName']           
      	        });
      	        var cfg = Ext.apply({}, config);
      	        me.callParent([cfg]);
      	    }
});


/**
 * 班次详情查看表格2
 */
Ext.define('basedev.classes.QueryclassesDetailGrid2', {
    extend : 'Ext.grid.Panel',
    id:basedev.classes.QUERY_BASE_CLASSESDETAIL_RESULT_GRID_ID2,
    title:'详细信息',
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	// 设置表格不可排序
	sortableColumns : false,
	// 去掉排序的倒三角
	enableColumnHide : false,
	// 设置“如果查询的结果为空，则提示”
	emptyText : '没有数据',
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
			 },{
      			text : '途径点排序',
      			dataIndex : 'orderBy',
      			align : 'center',
      			width : 80
      		}, 
      		{
      			hidden : true,
      			dataIndex : 'viaType',
      			align : 'center'
      		}, 
      		{
      			text : '途径类型',
      			dataIndex : 'viaTypeName',
      			align : 'center',
      			width : 80
      		}, 
      		{
      			hidden : true,
      			dataIndex : 'currSiteCode',
      			align : 'center'
      		},
      		{
      			text : '当前转运/分拨中心',
      			dataIndex : 'currSiteName',
      			align : 'center',
      			width : 160
      		},
      		{
      			hidden : true,
      			dataIndex : 'nextSiteCode',
      			align : 'center'
      		},
      		{
      			text : '下一转运/分拨中心',
      			dataIndex : 'nextSiteName',
      			align : 'center',
      			width : 160
      		}, 
      		{
      			hidden : true,
      			dataIndex : 'currRegionCode',
      			align : 'center'
      		},
      		{
      			text : '当前行政区域',
      			dataIndex : 'currRegionName',
      			align : 'center',
      			width : 160
      		},
      		{
      			hidden : true,
      			dataIndex : 'nextRegionCode',
      			align : 'center'
      		},
      		{
      			text : '下一行政区域',
      			dataIndex : 'nextRegionName',
      			align : 'center',
      			width : 160
      		},
      		 {
      			text : '驶到里程(千米/Km)',
      			dataIndex : 'arriveMileage',
      			align : 'center',
      			width : 120
      		},
      		 {
      			text : '停留时间(分/m)',
      			dataIndex : 'stayTime',
      			align : 'center',
      			width : 100
      		},
      		 {
      			text : '驶到用时(分/m)',
      			dataIndex : 'arriveTime',
      			align : 'center',
      			width : 100
      		},
      		{
      			hidden : true,
      			dataIndex : 'classType',
      			align : 'center'
      		}, 
      		{
      			text : '运输方式',
      			dataIndex : 'classTypeName',
      			align : 'center',
      			width : 80
      		},
      		{
      			text : '备注',
      			dataIndex : 'remark',
      			align : 'center',
      			width : 80
      		}],
      	    constructor: function(config){
      	        var me = this;
      	        me.store = Ext.create('Ext.data.Store', {
      	            storeId:'simpsonsStore',
      	            fields:['orderBy','viaType','viaTypeName', 'currSiteCode','currSiteName','nextSiteCode','nextSiteName','currRegionName','currRegionCode','nextRegionCode','nextRegionName','arriveMileage', 'arriveTime', 'remark', 'stayTime','arriveMileage','classType','classTypeName']           
      	        });
      	        var cfg = Ext.apply({}, config);
      	        me.callParent([cfg]);
      	    }
});

/**
 * 车次model
 */
Ext.define('basedev.classes.classesModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		type : 'long'
	}, {
		name : 'lineCode',
		type : 'string'
	}, {
		name : 'classesName',
		type : 'string'
	}, {
        name : 'classesCode',
        type : 'string'
    }, {
		name : 'vehicelNo',
		type : 'string'
	},
	{
        name : 'vehicelCode',
        type : 'string'
    },
	{
		name : 'departureTime',
		type : 'string'
	}, {
		name : 'arriveTime',
		type : 'string'
	}, {
		name : 'carType',
		type : 'string'
	}, {
		name : 'cycle',
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
		name : 'remark',
		type : 'string'
	}, {
		name : 'lineName',
		type : 'string'
	},
	{
		name : 'cycleName',
		type : 'string'
	},
	{
		name : 'delFlag',
		type : 'number'
	}]
});

/**
 * 查询条件
 */
Ext.define('basedev.classes.QueryclassesForm',
  {
	extend : 'Ext.form.Panel',
	id : basedev.classes.QUERY_BASE_CLASSES_FORM_ID,
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
		    xtype : 'commonBasebasedevbasedevVehicleSelector',
            name : 'vehicelCode',
            fieldLabel : '车辆',
            columnWidth : .3
		},
		{
			name : 'classesName',
			fieldLabel : '班次名称',
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
				var selectResultPanel = Ext.getCmp(basedev.classes.QUERY_BASE_CLASSES_RESULT_GRID_ID);
				selectResultPanel.setVisible(true);
				selectResultPanel.getPagingToolbar().moveFirst();
			}
		}];
	  me.callParent();
	}
  });

/**
 * 班次Store
 */
Ext.define('basedev.classes.classesStore', {
	extend : 'Ext.data.Store',
	model : 'basedev.classes.classesModel',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : basedev.realPath("queryClassesBySomeIf.do"),
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp(basedev.classes.QUERY_BASE_CLASSES_FORM_ID);
			if (queryForm != null) {
				var p_classesName = queryForm.getForm().findField('classesName').getValue();
				var p_vehicelCode = queryForm.getForm().findField('vehicelCode').getValue();
				Ext.apply(operation, {
                       params : {
						'q_str_classesName' : p_classesName,
						'q_str_vehicelCode' : p_vehicelCode
					}
				});
			}
		}
	}
 });

/**
 * 查看表单
 */
Ext.define('basedev.classes.classesForm', {
	extend : 'Ext.form.Panel',
	id : basedev.classes.BASE_CLASSES_FORM_ID,
	title : '基本信息',
	frame : true,
	defaults : {
		    margin : '10 10 10 10',
	        labelWidth : 90,
	        readOnly : true,
	        width: 260
	},
	layout : {
		type : 'table',
		columns : 3
	},
	defaultType : 'textfield',
	items : [ {
		xtype : 'textfield',
		name : 'id',
		hidden : true
	}, {
        xtype : 'textfield',
        name : 'classesCode',
        fieldLabel : '班次编号'
    },{
		xtype : 'textfield',
		name : 'classesName',
		fieldLabel : '班次名称'
	},{
		xtype : 'textfield',
	    name : 'lineName',
		fieldLabel : '车线'
	},{
		xtype : 'textfield',
	    name : 'vehicelNo',
		fieldLabel : '车辆'
	},{
		fieldLabel: '出发时间',
        name : 'departureTime',
        xtype: 'timefield',
        labelSeparator: ':',
        pickerMaxHeight: 200,
        increment: 30,
        format: basedev.classes.FORMAT_TIME,
        editable : false,
        submitFormat: basedev.classes.FORMAT_TIME
	},
	{
		xtype : 'textfield',
		name : 'arriveTime',
		fieldLabel : '到达时间'
	},
	{
		xtype : "dictcombo",
		dictType : 'BASE_CLASSES_CYCLE',
		name : 'cycle',
		fieldLabel : '出发周期'
	},{
        name : 'blFlag',
        fieldLabel : '启用',// 启用（0：否 1：是）
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
        allowBlank : false,
        colspan: 2
    }, {
		xtype: 'textareafield',
		name : 'remark',
		fieldLabel : '备注',
		colspan: 2,
	    width:500,
	    height: 55
	}]
});
/**
 * 编辑表单
 */
Ext.define('basedev.classes.EditclassesForm',
	{
		extend : 'Ext.form.Panel',
		id : basedev.classes.EDIT_BASE_CLASSES_FORM_ID,
		title : '基本信息',
		frame : true,
		defaults : {
			margin:'5 15 5 15',
	    	labelWidth: 100,
	    	allowBlank: true,
		    validateOnChange: false
		},
		layout : {
			type : 'table',
			columns : 3
		},
		defaultType : 'textfield',
		items : [
			{
				xtype : 'textfield',
				name : 'id',
				hidden : true,
				id : 'basedev.classes.classesId'
			},{
                xtype : 'textfield',
                name : 'classesCode',
                fieldLabel : '班次编号',
                readOnly : true
            },
			{
				xtype : 'textfield',
				name : 'classesName',
				fieldLabel : '班次名称',
				maxLength : 33,
				allowBlank : false,
				validateOnBlur : true,
				validator : function(field) {
					if (!field) {
						return true;
					}
					var editclassesInfoForm = Ext.getCmp(basedev.classes.EDIT_BASE_CLASSES_FORM_ID);
					var id= Ext.getCmp('basedev.classes.classesId').getValue();
					var state = editclassesInfoForm.getOperatorType();
					var paramsObj = {
							classesName : field,
							id : id,
							state : state
						};
					var valid = false;
					Ext.Ajax.request({
						url : basedev.realPath('uniqueCheckByClassesName.do'),
						params : paramsObj,
						async : false,//同步
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
					return '该车次名称已存在';
				}
			},{
				xtype : 'commonBasebasedevbasedevLineSelector',
				name : 'lineCode',
				fieldLabel : '车线',
				allowBlank: false,
				listeners : {
				select : function(combo) {
					var value = combo.getValue();
					var paramsObj = {
							lineCode : value
					};
					Ext.Ajax.request({
					    url:'../basedev/queryCarLineDetailByLineId.do',
					    params : paramsObj,
					    success: function(response){
					        var result = Ext.JSON.decode(response.responseText);
	                        if(result.success){
	                        	var baseClassesDetailGrid = Ext.getCmp(basedev.classes.QUERY_BASE_CLASSESDETAIL_RESULT_GRID_ID2);
	                        	baseClassesDetailGrid.getStore().loadData(result.data);
	                        }else{
	                            Ext.ux.Toast.msg('提示', result.msg, 'error'); 
	                        }
					    },
					    failure: function(response){
					    	Ext.ux.Toast.msg('提示',response.responseText, 'error');
					    }
					});
				}
				}
			},
			{
				xtype : 'commonBasebasedevbasedevVehicleSelector',
				name : 'vehicelCode',
				fieldLabel : '车辆',
				blFlag:1,
				allowBlank: false
			},
			 {
				
				fieldLabel: '出发时间',
	            name : 'departureTime',
	            xtype: 'timefield',
	            labelSeparator: ':',
	            pickerMaxHeight: 200,
	            increment: 30,
	            format: basedev.classes.FORMAT_TIME,
	            allowBlank: false,
	            editable : false,
	            submitFormat: basedev.classes.FORMAT_TIME
			},
			{
				xtype : "dictcombo",
				dictType : 'BASE_CLASSES_CYCLE',
				name : 'cycle',
				fieldLabel : '出发周期',
				allowBlank : false,
				editable : false
			},
			{
				xtype: 'checkbox',
		        name: 'blFlag',
		        boxLabel: '启用',
		        inputValue: '1',
		        uncheckedValue: '0'
			},
			{
				xtype : 'textareafield',
				name : 'remark',
				fieldLabel : '备注',
				maxLength : 333,
		        colspan: 3,
		        height: 55,
		        width: 540
			}],
		operatorType : null,
		setOperatorType : function(state, record) {
			this.operatorType = state;
			var editclassesForm = this.getForm();
			// 表单重置
			editclassesForm.reset();
				var classesModel = Ext.create('basedev.classes.classesModel',record.raw);
				if(record.raw.lineCode != null){
					var comboboxclassesModel = Ext.create('BaseData.commonSelector.BasebasedevbasedevLineModel');
					comboboxclassesModel.data.lineCode= record.raw.lineCode;
					comboboxclassesModel.data.lineName = record.raw.lineName;
					classesModel.data.lineCode = comboboxclassesModel;
		        }
		        if(record.raw.vehicelCode != null){
					var comboboxclassesModel = Ext.create('BaseData.commonSelector.BasebasedevbasedevVehicleModel');
					comboboxclassesModel.data.vehicelNo = record.raw.vehicelNo;
					comboboxclassesModel.data.vehicelCode = record.raw.vehicelCode;
					classesModel.data.vehicelCode = comboboxclassesModel;
			     }
				editclassesForm.loadRecord(classesModel);
		},
		getOperatorType : function() {
		  return this.operatorType;
		}
	}
);

/**
 * 新增表单
 */
Ext.define('basedev.classes.AddclassesForm',
	{
		extend : 'Ext.form.Panel',
		id : basedev.classes.ADD_BASE_CLASSES_FORM_ID,
		title : '基本信息',
		frame : true,
		defaults : {
			margin:'5 15 5 15',
	    	labelWidth: 100,
	    	allowBlank: true,
		    validateOnChange: false
		},
		layout : {
			type : 'table',
			columns : 3
		},
		defaultType : 'textfield',
		items : [
		{
                xtype : 'textfield',
                name : 'classesCode',
                fieldLabel : '班次编号',
                maxLength : 16,
                allowBlank : false,
                validateOnBlur : true,
                regex : /^[A-Za-z0-9]+$/,
                regexText : '该输入项只能输入数字和字母',
                validator : function(field) {
                    if (!field) {
                        return true;
                    }
                   var addclassesInfoForm = Ext.getCmp(basedev.classes.ADD_BASE_CLASSES_FORM_ID);
                    var paramsObj = {
                        classesCode : field
                    };
                    var valid = false;
                    Ext.Ajax.request({
                        url : basedev.realPath('uniqueCheckByClassesCode.do'),
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
                    return '该班次编号已存在';
                }
            },
			{
				xtype : 'textfield',
				name : 'classesName',
				fieldLabel : '班次名称',
				maxLength : 33,
				allowBlank : false,
				validateOnBlur : true,
				validator : function(field) {
					if (!field) {
						return true;
					}
					var addclassesInfoForm = Ext.getCmp(basedev.classes.ADD_BASE_CLASSES_FORM_ID);
					var state = addclassesInfoForm.getOperatorType();
					var paramsObj = {
							classesName : field,
							state : state
						};
					var valid = false;
					Ext.Ajax.request({
						url : basedev.realPath('uniqueCheckByClassesName.do'),
						params : paramsObj,
						async : false,//同步
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
					return '该车次名称已存在';
				}
			},{
				xtype : 'commonBasebasedevbasedevLineSelector',
				name : 'lineCode',
				fieldLabel : '车线',
				allowBlank: false,
			    listeners : {
					select : function(combo) {
						var value = combo.getValue();
						var paramsObj = {
								lineCode : value
						};
						Ext.Ajax.request({
						    url:'../basedev/queryCarLineDetailByLineId.do',
						    params : paramsObj,
						    success: function(response){
						        var result = Ext.JSON.decode(response.responseText);
		                        if(result.success){
		                        	var baseClassesDetailGrid = Ext.getCmp(basedev.classes.QUERY_BASE_CLASSESDETAIL_RESULT_GRID_ID2);
		                        	baseClassesDetailGrid.getStore().loadData(result.data);
		                        }else{
		                            Ext.ux.Toast.msg('提示', result.msg, 'error'); 
		                        }
						    },
						    failure: function(response){
						    	Ext.ux.Toast.msg('提示',response.responseText, 'error');
						    }
						});
					}
			   }
			},
			{
				xtype : 'commonBasebasedevbasedevVehicleSelector',
				name : 'vehicelCode',
				allowBlank: false,
				fieldLabel : '车辆',
				blFlag:1
			},
			 {
				
				fieldLabel: '出发时间',
	            name : 'departureTime',
	            xtype: 'timefield',
	            labelSeparator: ':',
	            pickerMaxHeight: 200,
	            increment: 30,
	            format: basedev.classes.FORMAT_TIME,
	            allowBlank: false,
	            editable : false,
	            submitFormat: basedev.classes.FORMAT_TIME
			},
			{
				xtype : "dictcombo",
				dictType : 'BASE_CLASSES_CYCLE',
				name : 'cycle',
				fieldLabel : '出发周期',
				allowBlank : false,
				editable : false
			},
			{
				xtype: 'checkbox',
		        name: 'blFlag',
		        boxLabel: '启用',
		        inputValue: '1',
		        uncheckedValue: '0'
			},
			{
				xtype : 'textareafield',
				name : 'remark',
				fieldLabel : '备注',
				maxLength : 333,
		        colspan: 3,
		        height: 55,
		        width: 540
			}],
		operatorType : null,
		setOperatorType : function(state, record) {
			this.operatorType = state;
			var addclassesForm = this.getForm();
			// 表单重置
			addclassesForm.reset();
			var classesModel = Ext.create('basedev.classes.classesModel');
			classesModel.data.blFlag = 1;
			addclassesForm.loadRecord(classesModel);
		   },
		getOperatorType : function() {
		  return this.operatorType;
		}
	});


/**
 * 查看表格
 */
Ext.define('basedev.classes.QueryclassesResultGrid',
{
	extend : 'Ext.grid.Panel',
	id : basedev.classes.QUERY_BASE_CLASSES_RESULT_GRID_ID,
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
			    var classesWindow = Ext.getCmp(basedev.classes.QUERY_BASE_CLASSES_RESULT_GRID_ID).getclassesWindow();
                var classesModel = Ext.create('basedev.classes.classesModel',rowInfo.raw);
                var classesForm = classesWindow.getclassesForm();
        		classesForm.loadRecord(classesModel);
        		//加载明细记录
				var classesCode = rowInfo.raw.classesCode;
				Ext.Ajax.request({
				    url:'../basedev/queryClassesDetailByClassesId.do',
				    params:{classesCode:classesCode},
				    success: function(response){
				        var result = Ext.JSON.decode(response.responseText);
                        if(result.success){
                        	var baseClassesDetailGrid = Ext.getCmp(basedev.classes.QUERY_BASE_CLASSESDETAIL_RESULT_GRID_ID1);
                        	baseClassesDetailGrid.getStore().loadData(result.data);
                        }else{
                            Ext.ux.Toast.msg('提示', result.msg, 'error'); 
                        }
				    },
				    failure: function(response){
				    	Ext.ux.Toast.msg('提示',response.responseText, 'error');
				    }
				});
        		classesWindow.show();
			 }
			},
			{

				iconCls : 'deppon_icons_edit',
				tooltip : '修改',
				handler : function(gridView,rowIndex, colIndex) {
					var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
	                var editclassesWindow = Ext.getCmp(basedev.classes.QUERY_BASE_CLASSES_RESULT_GRID_ID).getEditclassesWindow();
	        		editclassesWindow.getEditclassesForm().setOperatorType(basedev.classes.STATE_UPDATE,rowInfo);
	        		var lineCode = rowInfo.raw.lineCode;
					Ext.Ajax.request({
					    url:'../basedev/queryCarLineDetailByLineId.do',
					    params:{lineCode:lineCode},
					    success: function(response){
					        var result = Ext.JSON.decode(response.responseText);
	                        if(result.success){
	                        	var baseClassesDetailGrid = Ext.getCmp(basedev.classes.QUERY_BASE_CLASSESDETAIL_RESULT_GRID_ID2);
	                        	baseClassesDetailGrid.getStore().loadData(result.data);
	                        }else{
	                            Ext.ux.Toast.msg('提示', result.msg, 'error'); 
	                        }
					    },
					    failure: function(response){
					    	Ext.ux.Toast.msg('提示',response.responseText, 'error');
					    }
					});
	        		editclassesWindow.show();
				} 
		}
		/*{
				iconCls : 'deppon_icons_delete',
				tooltip : '删除',
				handler : function(grid, rowIndex,colIndex) {
				 //删除 
				var rowInfo = Ext.getCmp(basedev.classes.QUERY_BASE_CLASSES_RESULT_GRID_ID).store.getAt(rowIndex);
				var id = rowInfo.data.id;
				Ext.Msg.confirm('确认','确认删除吗？',function(btn) {
					if (btn == 'yes') {
					 Ext.Ajax.request({
						url : basedev.realPath('/deleteBaseClasses.do'),
						params : {
							id : id
						},
						success : function(response) {
							var result = Ext.JSON.decode(response.responseText);
                                if (result.success) {
								Ext.ux.Toast.msg('提示','删除成功');
								var grid = Ext.getCmp(basedev.classes.QUERY_BASE_CLASSES_RESULT_GRID_ID);
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
		},{
            text : '班次编号',
            align : 'center',
            dataIndex : 'classesCode',
            width : 120
        }, {
			text : '班次名称',
			align : 'center',
			dataIndex : 'classesName',
			width : 120
		},{
			text : '车线',
			dataIndex : 'lineName',
			align : 'center',
			width : 120
		},
		{
			dataIndex : 'lineCode',
			hidden : true
		},
		{
			text : '车辆',
			dataIndex : 'vehicelNo',
			align : 'center',
			width : 120
		}, 
		{
			text : '出发时间',
			dataIndex : 'departureTime',
			align : 'center',
			width : 150
		},
		{
			text : '到达时间',
			dataIndex : 'arriveTime',
			align : 'center',
			width : 150
		}, 
		{
			text : '出发周期',
			dataIndex : 'cycleName',
			align : 'center',
			width : 150
		},
		{
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
        },
	    {
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
		},{
			xtype : 'datecolumn',
			format : 'Y-m-d H:i:s',
			text : '修改时间',
			dataIndex : 'modifyTime',
			align : 'center',
			width : 150
		}, 
		 {
			text : '备注',
			dataIndex : 'remark',
			align : 'center',
			width : 150
		},
		{
			hidden : true,
			dataIndex : 'cycle',
			align : 'center'
		}],
	classesWindow : null,
	getclassesWindow : function() {
		me = this;
	    me.classesWindow = Ext.create('basedev.classes.classesWindow');
		return me.classesWindow;
	},
	editclassesWindow : null,
	getEditclassesWindow : function() {
		me = this;
		me.editclassesWindow = Ext.create('basedev.classes.EditclassesWindow');
		//me.editclassesWindow.editDate = data;
		return me.editclassesWindow;
	},
	addclassesWindow : null,
	getAddclassesWindow : function() {
		me = this;
		me.addclassesWindow = Ext.create('basedev.classes.AddclassesWindow');
		//me.editclassesWindow.editDate = data;
		return me.addclassesWindow;
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
		me.store = Ext.create('basedev.classes.classesStore');
		me.tbar = [
		 {
			text : '新增',
			xtype : 'button',
			handler : function() {
				var addclassesWindow = me.getAddclassesWindow();
				var addclassesForm = addclassesWindow.getAddclassesForm();
				addclassesForm.setOperatorType(basedev.classes.STATE_ADD);
				addclassesWindow.show();
			}
		 },
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
					 Ext.ux.Toast.msg('提示','部分车次是启用状态不能删除！请重新选择！','error');
					 return;
				}
					
				ids[r] = records[r].data.id;
				
			}
			Ext.Msg.confirm('确认','确认删除吗？',function(btn) {
				if (btn == 'yes') {
				 Ext.Ajax.request({
					url : basedev.realPath('/deleteSomeClasses.do'),
					jsonData : Ext.JSON.encode(ids),//传递参数
			        headers: {
			              'Content-Type': 'application/json',
			              'Accept': 'application/json'
			        },
					success : function(response) {
						var result = Ext.JSON.decode(response.responseText);
                        if (result.success) {
						Ext.ux.Toast.msg('提示','删除成功');
						var grid = Ext.getCmp(basedev.classes.QUERY_BASE_CLASSES_RESULT_GRID_ID);
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
					url : basedev.realPath('/startSomeClasses.do'),
					jsonData : Ext.JSON.encode(ids),
			        headers: {
			              'Content-Type': 'application/json',
			              'Accept': 'application/json'
			        },
					success : function(response) {
						var result = Ext.JSON.decode(response.responseText);
                        if (result.success) {
						Ext.ux.Toast.msg('提示','启用成功');
						var grid = Ext.getCmp(basedev.classes.QUERY_BASE_CLASSES_RESULT_GRID_ID);
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
					url : basedev.realPath('/stopSomeClasses.do'),
					jsonData : Ext.JSON.encode(ids),
			        headers: {
			              'Content-Type': 'application/json',
			              'Accept': 'application/json'
			        },
					success : function(response) {
						var result = Ext.JSON.decode(response.responseText);
                        if (result.success) {
						Ext.ux.Toast.msg('提示','停用成功');
						var grid = Ext.getCmp(basedev.classes.QUERY_BASE_CLASSES_RESULT_GRID_ID);
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



/**
 * 查看窗口
 */
 
Ext.define('basedev.classes.classesWindow', {
	extend : 'Ext.window.Window',
	width : 880,
	modal : true,
	closeAction : 'destroy',
	title : '查看班次',
	classesForm : null,
	getclassesForm : function() {
		if (Ext.isEmpty(this.classesForm)) {
			this.classesForm = Ext.create("basedev.classes.classesForm");
		}
		return this.classesForm;
	},
	 editbaseClassesGrid: null,
	  getEditbaseClassesGrid: function(){
	       if(Ext.isEmpty(this.editbaseClassesGrid)){
	            this.editbaseClassesGrid = Ext.create("basedev.classes.QueryclassesDetailGrid1");
	        }
	        return this.editbaseClassesGrid;
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
		me.items = [ me.getclassesForm(),me.getEditbaseClassesGrid() ];
		me.buttons = [ me.getCancelButton() ];
		me.callParent([ cfg ]);
	}
});



/**
 * 修改窗口
 */
 
Ext.define('basedev.classes.EditclassesWindow',
  {
	extend : 'Ext.window.Window',
	width : 960,
	modal : true,
	title : '编辑班次',
	//editData:null,
	closeAction : 'destroy',
	editclassesForm : null,
	getEditclassesForm : function() {
		if (Ext.isEmpty(this.editclassesForm)) {
			this.editclassesForm = Ext.create("basedev.classes.EditclassesForm");
		}
		return this.editclassesForm;
	},
	 editbaseClassesGrid: null,
	  getEditbaseClassesGrid: function(){
	       if(Ext.isEmpty(this.editbaseClassesGrid)){
	            this.editbaseClassesGrid = Ext.create("basedev.classes.QueryclassesDetailGrid2");
	        }
	        return this.editbaseClassesGrid;
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
				var classesForm = me.getEditclassesForm().getForm();
				// 校验产品表单
				var data = classesForm.getValues();
				if (!classesForm.isValid()) {
					return;
				}
				var url = '';
				url = basedev.realPath('updateBaseClasses.do');
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
						var grid = Ext.getCmp(basedev.classes.QUERY_BASE_CLASSES_RESULT_GRID_ID);
						// 加载表格
						grid.getStore().load();//加载总页面
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
		me.items = [ me.getEditclassesForm(),me.getEditbaseClassesGrid( )];
		me.buttons = [ me.getCancelButton(), me.getSaveButton() ];
		me.callParent([ cfg ]);
	}
 });

/**
 * 新增窗口
 */
 
Ext.define('basedev.classes.AddclassesWindow',
  {
	extend : 'Ext.window.Window',
	width : 960,
	modal : true,
	title : '新增班次',
	//editData:null,
	closeAction : 'destroy',
	addclassesForm : null,
	getAddclassesForm : function() {
		if (Ext.isEmpty(this.addclassesForm)) {
			this.addclassesForm = Ext.create("basedev.classes.AddclassesForm");
		}
		return this.addclassesForm;
	},
	 editbaseClassesGrid: null,
	  getEditbaseClassesGrid: function(){
	       if(Ext.isEmpty(this.editbaseClassesGrid)){
	            this.editbaseClassesGrid = Ext.create("basedev.classes.QueryclassesDetailGrid2");
	        }
	        return this.editbaseClassesGrid;
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
				var classesForm = me.getAddclassesForm().getForm();
				// 校验产品表单
				var data = classesForm.getValues();
				if (!classesForm.isValid()) {
					return;
				}
				var url = '';
				url = basedev.realPath('insertBaseClasses.do');
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
						var grid = Ext.getCmp(basedev.classes.QUERY_BASE_CLASSES_RESULT_GRID_ID);
						grid.getStore().load();//加载总页面
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
		me.items = [ me.getAddclassesForm(),me.getEditbaseClassesGrid()];
		me.buttons = [ me.getCancelButton(), me.getSaveButton() ];
		me.callParent([ cfg ]);
	}
 });



/**
 * 程序入口
 */
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp(basedev.classes.CONTENT_ID)) {
		return;
	};
	var queryclassesForm = Ext.create('basedev.classes.QueryclassesForm');
	var queryclassesResultGrid = Ext.create('basedev.classes.QueryclassesResultGrid');
	var content = Ext.create('Ext.panel.Panel', {
		id : basedev.classes.CONTENT_ID,
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		getQueryclassesForm : function() {
			return queryclassesForm;
		},
		getQueryclassesResultGrid : function() {
			return queryclassesResultGrid;
		},
		items : [ queryclassesForm, queryclassesResultGrid ]
	});

	Ext.getCmp('T_basedev-classes').add(content);
	// 加载表格数据
	queryclassesResultGrid.getStore().load();
});
