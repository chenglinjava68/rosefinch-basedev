basedev.baseSite.EMPLOYEE_SELECTOR_PANEL='bs-employee-selector-panel';
basedev.baseSite.QUERY_BASE_EMPLOYEE_RESULT_GRID_ID='bs-emp-grid';
basedev.baseSite.EMPLOYEE_SELECTOR_BUTTON_PANEL='bs-employee-selector-button-panel';
basedev.baseSite.SELECTED_EMPLOYEE_PANEL='bs-selected-employee-panel';

// 全局变量，保存选中的门店的编码
basedev.baseSite.selecedSiteCode = "";

Ext.define('Rosefinch.Basedev.EmployeeSelectorPanel', {
	extend : 'Ext.panel.Panel',
	id : basedev.baseSite.EMPLOYEE_SELECTOR_PANEL,
	width : 1100,
	height : 550,
	panelUrl:null,
	treeUrl:null,
	frame : true,
	treeparams:null,
//	columnWidth : 0.99,
//	layout : 'column',
	layout : {
		type : 'hbox',
		align : 'stretch'
	},
	defaults : {
		readOnly : false
	},
	baseEmployeeGrid : null,
	getBaseEmployeeGrid : function(){
		var me = this;
		if(Ext.isEmpty(me.baseEmployeeGrid)){
			me.baseEmployeeGrid = Ext.create('Rosefinch.basedev.BaseEmployeeGrid');
		}
		return me.baseEmployeeGrid ;
	},
	selectButtonPanel:null,
	getSelectButtonPanel:function(){
		var me = this;
		if(Ext.isEmpty(me.selectButtonPanel)){
			me.selectButtonPanel = Ext.create('Rosefinch.Basedev.EmployeeSelectorButtonPanel');
		}
		return me.selectButtonPanel ;
	},
	selectedEmployeePanel:null,
	getSelectedEmployeePanel:function(){
		var me = this;
		if(Ext.isEmpty(me.selectedEmployeePanel)){
			me.selectedEmployeePanel = Ext.create('Rosefinch.Basedev.SelectedEmployeePanel');
		}
		return me.selectedEmployeePanel ;
	},
	
	initComponent : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		
		var empGrid = me.getBaseEmployeeGrid();
		var selEmpPanel = me.getSelectedEmployeePanel();
		
		me.getSelectButtonPanel().setRightMove(selEmpPanel);
		me.getSelectButtonPanel().setLeftMove(empGrid);
		
		me.items = [empGrid, me.getSelectButtonPanel(),selEmpPanel];
		
		
		// 先加载数据
		empGrid.getStore().load();
		
		selEmpPanel.getStore().load();
		
		me.callParent([cfg]);
	}
});

//-----------------门店表格start--------------------
/**
 * 员工表格
 */
Ext.define('Rosefinch.basedev.BaseEmployeeGrid',
{
	extend : 'Ext.grid.Panel',
	id : basedev.baseSite.QUERY_BASE_EMPLOYEE_RESULT_GRID_ID,
	cls : 'autoHeight',
	bodyCls : 'autoHeight',
	multiSelect : true,
	// 设置表格不可排序
	sortableColumns : false,
	// 去掉排序的倒三角
	enableColumnHide : false,
	// 设置“如果查询的结果为空，则提示”
//	emptyText : '没有数据',
//	selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"MULTI"}),
	stripeRows : true, // 交替行效果
//	collapsible : true,
	animCollapse : true,
	frame : true,
	width : 570,
	columns : [
		 {
	    	text : '序号',
	    	xtype: 'rownumberer',
	    	align : 'center',
	        width : 50
    	}, {
			hidden : true,
			dataIndex : 'id',
			align : 'center'
		}, {
			text : '员工编号',
			align : 'center',
			dataIndex : 'employeeCode',
			flex : 0.2
		}, {
			text : '员工名称',
			align : 'center',
			dataIndex : 'employeeName',
			flex : 0.2
		},{
			text : '所属门店',
			dataIndex : 'siteName',
			align : 'center',
			flex : 0.3
		}, {
			text : '所属部门',
			dataIndex : 'orgName',
			align : 'center',
			flex : 0.3
		}/*, {
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
        }*/],
	pagingToolbar : null,
	getPagingToolbar : function() {
		var me = this;
		if (me.pagingToolbar == null) {
		/*me.pagingToolbar = Ext.create(
			'Deppon.StandardPaging', {
			store : me.store
		  });*/
		  
		  me.pagingToolbar = Ext.create('Deppon.StandardPaging', {
				store : me.store
			});
		}
		
	 return me.pagingToolbar;
	},
	deptNameQueryField : null,
	getDeptNameQueryField : function(){
		if(this.deptNameQueryField==null){
			this.deptNameQueryField = Ext.create('Ext.form.field.Text',{
				xtype : 'textfield',
				labelWidth : 80,
				name : 'deptNameQuery',
				width : 250
			});
		}
		return this.deptNameQueryField;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.store = Ext.create('Rosefinch.basedev.baseEmployeeStore');
		
		var cb = Ext.create('Ext.selection.CheckboxModel',{mode:"MULTI"});
		me.selModel = cb;
		
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
							var empName = me
									.getDeptNameQueryField()
									.getValue();
							
							/*if (Ext.String
									.trim(empName) == null
									|| Ext.String
											.trim(empName) == "") {
								Ext.ux.Toast.msg('提示','查询条件不能为空','error');
								return;
							}*/
							
							if (!/^[^\|"'<>%@#!&\$\*]*$/
									.test(empName)) {
								Ext.ux.Toast.msg('提示','输入值不能包含特殊符号','error');
								return;
							}
							
							var selectResultPanel = Ext.getCmp(basedev.baseSite.QUERY_BASE_EMPLOYEE_RESULT_GRID_ID);
							selectResultPanel.setVisible(true);
							selectResultPanel.getPagingToolbar().moveFirst();
						}
					} ];
		me.bbar = me.getPagingToolbar();
		me.callParent(cfg);
	}
});


Ext.define('Rosefinch.basedev.BaseEmployeeModel', {
	extend : 'Ext.data.Model',
	fields : [{
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
		name : 'orgName',
		type : 'string'
	}, {
		name : 'siteName',
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
		name : 'delFlag',
		type : 'number'
	}]
});


Ext.define('Rosefinch.basedev.baseEmployeeStore', {
	extend : 'Ext.data.Store',
	model : 'Rosefinch.basedev.BaseEmployeeModel',
	pageSize : 10,
	autoLoad : false,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : basedev.realPath("queryEmployeeByNameOrCode.do"),
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners : {
		beforeload : function(store, operation, eOpts) {
			var empName = Ext.getCmp(basedev.baseSite.QUERY_BASE_EMPLOYEE_RESULT_GRID_ID).getDeptNameQueryField().getValue();
			
			Ext.apply(operation, {
				params : {
					'q_str_employeeName' : empName,
					'q_int_blFlag' : 1
				}
			});
		}
	}
});
//-----------------门店表格end--------------------


//-----------------按钮面板start--------------------
//按钮面板
Ext.define('Rosefinch.Basedev.EmployeeSelectorButtonPanel', {
	extend : 'Ext.panel.Panel',
	id:basedev.baseSite.EMPLOYEE_SELECTOR_BUTTON_PANEL,
	// height : 150,
	width : 70,
	// frame:true,
	// 左移框
	leftMoveFrame : null,
	// 右移框
	rightMoveFrame : null,

	setLeftMove : function(a_moveFrame) {
		this.leftMoveFrame = a_moveFrame;
	},
	getLeftMove : function() {
		return this.leftMoveFrame;
	},
	setRightMove : function(a_moveFrame) {
		this.rightMoveFrame = a_moveFrame;
	},
	getRightMove : function() {
		return this.rightMoveFrame;
	},
	// 右移全部
	rightMoveAll : function() {
		var me = this;
		
		var leftStore = me.getLeftMove().getStore();
		var rightStore = me.getRightMove().getStore();
		
//		rightStore.removeAll();
		
		
		// 遍历
		leftStore.each(function(record){
			var num = rightStore.findExact('employeeCode',record.raw.employeeCode);
			
			// 没有重复
			if(num == -1){
				var ins_rec = Ext.create('Rosefinch.Basedev.SelectedBaseEmployeeModel',{
					employeeCode:record.raw.employeeCode,
					employeeName:record.raw.employeeName
				});
				
				var count = rightStore.getCount();
				
				rightStore.insert(count,ins_rec);
			}
		});
		
	},
	// 右移
	rightMove : function() {
		var me = this;
		
		var leftGrid = me.getLeftMove();
		
		var leftStore = me.getLeftMove().getStore();
		var rightStore = me.getRightMove().getStore();
		
		var selectList = leftGrid.getSelectionModel().getSelection();
        if(Ext.isEmpty(selectList)){
            return;
        }
        
        for(var i = 0; i < selectList.length; i++){
			var num = rightStore.findExact('employeeCode',selectList[i].data.employeeCode);
			
			// 没有重复
			if(num == -1){
				var ins_rec = Ext.create('Rosefinch.Basedev.SelectedBaseEmployeeModel',{
					employeeCode:selectList[i].data.employeeCode,
					employeeName:selectList[i].data.employeeName
				});
				
				var count = rightStore.getCount();
				rightStore.insert(count,ins_rec);
			}
        }
	},
	// 左移全部
	leftMoveAll : function() {
//		var me = this;
		var rightStore = this.getRightMove().getStore();
		rightStore.removeAll();
	},
	// 左移
	leftMove : function() {
		var me = this;
		var rightGrid = me.getRightMove();
		var leftStore = me.getLeftMove().getStore();
		
		var selectList = rightGrid.getSelectionModel().getSelection();
        if(Ext.isEmpty(selectList)){
            return;
        }
		
        for(var i = 0; i < selectList.length; i++){
			var num = leftStore.findExact('employeeCode',selectList[i].data.employeeCode);
			
			// 如果找到了
			if(num != -1){
				var rec = leftStore.findRecord('employeeCode',selectList[i].data.employeeCode);
				me.getLeftMove().getSelectionModel().deselect(rec);
			}
        }
        
        // 移除掉选中的
        this.getRightMove().getStore().remove(selectList);
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.defaults = {
				xtype : 'button',
				width : 60,
				disabled : false,
				height : 20,
				margin : '8 0 0 10'
		};
		me.items = [{
			text : '>>',
			margin : '50 0 0 10',
			handler : function() {
				me.rightMoveAll();
			}
		}, {
			text : '>',
			handler : function() {
				me.rightMove();
			}
		}, {
			text : '<',
			handler : function() {
				me.leftMove();
			}
		}, {
			text : '<<',
			handler : function() {
				me.leftMoveAll();
			}
		}]
		me.callParent([cfg]);
	}
});
//-----------------按钮面板end--------------------





//-----------------已选员工列表start-----------------------
//已选部门列表
Ext.define('Rosefinch.Basedev.SelectedEmployeePanel', {
	extend : 'Ext.grid.Panel',
	id : basedev.baseSite.SELECTED_EMPLOYEE_PANEL,
	sortableColumns : false,
	enableColumnHide : false,
	enableColumnMove : false,
	multiSelect : true,
//	store:null,
	width : 380,
	flex : 1,
	panelUrl:null,
	frame : true,
	columns : [{
		header : '员工编号',
//		hidden : true,
		dataIndex : 'employeeCode',
		flex : 0.5,
		width : 120
	}, {
		header : '员工名称',
		dataIndex : 'employeeName',
//		flex : 1,
//		titlehidden : true,
		flex : 0.5,
		width : 120
	} ],
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		
		var cb = Ext.create('Ext.selection.CheckboxModel',{mode:"MULTI"});
		me.selModel = cb;
		
		me.store = Ext.create('Rosefinch.Basedev.SelectedBaseEmployeeStore');
		
		me.callParent([cfg]);
	}
});


Ext.define('Rosefinch.Basedev.SelectedBaseEmployeeModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'employeeCode',
		type : 'string'
	}, {
		name : 'employeeName',
		type : 'string'
	}]
});


//初始化已选择员工store
Ext.define('Rosefinch.Basedev.SelectedBaseEmployeeStore', {
	extend : 'Ext.data.Store',
	model :'Rosefinch.Basedev.SelectedBaseEmployeeModel',
	panelUrl:null,
	proxy : {
		type : 'ajax',
		actionMethods : 'POST',
		url : basedev.realPath("getEmployeesByOwnerSite.do"),
		reader : {
			type : 'json',
			root : 'data'
		}
	},
	constructor : function(config) {
		var me = this, 
		cfg = Ext.apply({}, config);
		
		me.listeners = {
			beforeload : function(store, operation, eOpts) {
				var siteCode = basedev.baseSite.selecedSiteCode;
				
				Ext.apply(operation, {
					params : {
						'siteCode' : siteCode
					}
				});
			}
		};
		
		me.callParent([cfg]);
	}	
});
//-----------------已选员工列表end-----------------------


