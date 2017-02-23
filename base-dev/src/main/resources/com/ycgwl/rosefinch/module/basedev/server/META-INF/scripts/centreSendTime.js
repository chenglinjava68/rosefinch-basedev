
/*派件时效(转运、分拨)  by qkj*/
/** 此模块主页面的ID */
basedev.centreSendTime.CONTENT_ID  = 'T_basedev-centreSendTime_content';
basedev.centreSendTime.FORMAT_TIME = 'G:i'; // 格式化时间字符串



/** 区域Model */
Ext.define('BaseData.centreSendTime.centreSendTimeModule', {
	extend : 'Ext.data.Model',
	fields : [{
        name : 'id',		//分拨时效ID
        type : 'long'
    }, {
        name : 'configCode',	// 时效编号
        type : 'string'
    }, {
        name : 'configName',	 // 时效名称
        type : 'string'
    }, {
        name : 'oneArrivalTimeS',	// 到达1开始时间
        type : 'string'
    }, {
        name : 'oneArrivalTimeE',	// 到达1结束时间
        type : 'string'
    },{
        name : 'oneDispatchEndTime',	// 到达1日常合格签收时间日常合格签收时间
        type : 'string'
    },{
        name : 'oneDispatchMonthEndTime',	// 一派月度合格签收时间
        type : 'string'
    },{
        name : 'twoArrivalTimeS',	 // 到达2开始时间
        type : 'string'
    },{
        name : 'twoArrivalTimeE',	 // 到达2结束时间
        type : 'string'
    },{
        name : 'twoDispatchEndTime',	// 到达2日常合格签收时间段日常合格签收时间
        type : 'string'
    },
    {
        name : 'twoDispatchMonthEndTime',	// 二派月度合格签收时间
        type : 'string'
    }, 
    {
        name : 'oneDispatchRate',	 // 一派比率
        type : 'string'
    },{
        name : 'twoDispatchRate',	 // 二派比率
        type : 'string'
    },{
        name : 'blFlag',	 // 是否启用
        type : 'int'
    },{
        name : 'remark',	 // 备注
        type : 'string'
    },{
        name : 'createUserCode',	 //创建人
        type : 'string'
    },{
        name : 'modifyUserCode',	 //修改人
        type : 'string'
    }, {
        name : 'createTime',
        defaultValue : new Date(),		//创建时间
        convert : dateConvert,
        type : 'number'
    },{
        name : 'modifyTime',
        defaultValue : new Date(),	 //修改时间
        convert : dateConvert,
        type : 'number'
    },{
        name : 'delFlag',	 //状态
        type : 'int'
    },{
        name : 'siteCode',	 ///网点编码
        type : 'string'
    },{
        name : 'siteNames',	///关联表  siteNames
        type : 'string'
    }
    ]
});

/**
 *  查询store
 */
Ext.define('BaseData.centreSendTime.centreSendTimeStore',{
	extend: 'Ext.data.Store',
	pageSize:10,
	model : 'BaseData.centreSendTime.centreSendTimeModule',
	autoLoad:true,
	proxy : {
		url : '../basedev/getCentreSendTimeList.do',
		type : 'ajax',
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp("BaseData_centreSendTime_centreSendTimeForm_ID"); //查询表单
			if (queryForm != null) {
				var q_sl_configName = queryForm.getForm().findField('q_str_configName').getValue();

				Ext.apply(operation, {
					params : {
						'q_sl_configName' : q_sl_configName		//模糊查询写法
                      
					}
				});	
			}
		}
	}
});

/**
 * 查询表单
 */
Ext.define('BaseData.centreSendTime.centreSendTimeForm', {
	extend:'Ext.form.Panel',
	id: 'BaseData_centreSendTime_centreSendTimeForm_ID',
	frame : true,
	title: '查询条件',//查询
	defaults: {
		labelWidth:60,
		margin:'5 10 5 10'
	},
	layout : 'column',	
	defaultType : 'textfield',
	siteSelectWindow:null,
	initComponent: function(){
		var me = this;
		
		me.items = [{
            name: 'q_str_configName',
            fieldLabel:'时效名称',
            columnWidth: .2
        },{
			xtype : 'button',
			cls: 'yellow_button',
			text:"查询",
			width : 70,
			// 查询按钮的响应事件：
			handler: function() {
				if(me.getForm().isValid()){
					var selectResultPanel = Ext.getCmp("BaseData_centreSendTime_centreSendTimePageElementGridPanel");
					selectResultPanel.setVisible(true);
					selectResultPanel.getPagingToolbar().moveFirst();
				}
			}
		}
        ];
		me.callParent();
	}
});

/**
 * 列表panel
 */
Ext.define('BaseData.centreSendTime.centreSendTimePageElementGridPanel', {
	extend: 'Ext.grid.Panel',
	id:'BaseData_centreSendTime_centreSendTimePageElementGridPanel',
	title : '查询结果',
	frame: true,
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	selType : "rowmodel", // 选择类型设置为：行选择
	selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SIMPLE"}),//选择方框
	multiSelect:true,   //设置成多选
	stripeRows : true, // 交替行效果
	centreSendTimeAddWindow : null,
	centreSendTimeChangeWindow: null,
	
//  查看 窗口
	seeTimeWindow : null,
	getseeTimeWindow : function(){
		
		this.seeTimeWindow = Ext.create('Basedev.centreSendTime.seeTimeWindow');
		
		return this.seeTimeWindow;
	},
	
	
	
	
	getcentreSendTimeAddWindow : function(){
		this.centreSendTimeAddWindow = Ext.create('BaseData.centreSendTime.centreSendTimeAddWindow');
		return this.centreSendTimeAddWindow;
	},
	getcentreSendTimeChangeWindow : function(data){
		this.centreSendTimeChangeWindow = Ext.create('BaseData.centreSendTime.centreSendTimeChangeWindow');
		this.centreSendTimeChangeWindow.editData = data;
		return this.centreSendTimeChangeWindow;
	},
	// 批量启用按钮
	startButton: null,
	getStartButton: function() {
		var me = this;
		if(Ext.isEmpty(me.startButton)){
			me.startButton = Ext.create('Ext.Button', {
				text :'启用',//批量启用
				handler : function() {
					var selectList = me.getSelectionModel().getSelection();
					if(Ext.isEmpty(selectList)){
						Ext.ux.Toast.msg("提示", '请选择要操作的数据');
						return;
					}
					for(var i=0;i<selectList.length;i++){
						if(selectList[i].data.blFlag == 1){
							Ext.ux.Toast.msg("提示", '只能操作已停用的数据，请确认');
							return;
						}
						
					}
					var ids=[];
					for (var int = 0; int < selectList.length; int++) {
						ids[int] = selectList[int].data.id;
					}
					Ext.Msg.confirm("确认", "确认启用吗？",
					function(btn){
						if(btn=='yes'){
							Ext.Ajax.request({
								url : '../basedev/batchStartCentreById.do',
								params: {
									'ids':ids
								},
								async: false,
								success : function(response) {
									var json = Ext.JSON.decode(response.responseText); 
									Ext.ux.Toast.msg("提示", json.msg, 'success');
									var selectResultPanel = Ext.getCmp("BaseData_centreSendTime_centreSendTimePageElementGridPanel");
									selectResultPanel.getPagingToolbar().moveFirst();
								},
								exception : function(response) {
									var json = Ext.decode(response.responseText); 
									Ext.ux.Toast.msg("提示", json.msg, 'error');
								}
							});
						}
				})}
			});
		}
		return this.startButton;
	},
	// 批量停用按钮
	closeButton: null,
	getCloseButton: function() {
		var me = this;
		if(Ext.isEmpty(me.closeButton)){
			me.closeButton = Ext.create('Ext.Button', {
				text :'停用',//批量停用
				handler : function() {
					var selectList = me.getSelectionModel().getSelection();
					if(Ext.isEmpty(selectList)){
						Ext.ux.Toast.msg("提示", '请选择要操作的数据');
						return;
					}
					for(var i=0;i<selectList.length;i++){
						if(selectList[i].data.blFlag == 0){
							Ext.ux.Toast.msg("提示", '只能操作已启用的数据，请确认');
							return;
						}
						
					}
					var ids=[];
					for (var int = 0; int < selectList.length; int++) {
						ids[int] = selectList[int].data.id;
					}
					Ext.Msg.confirm("确认", "确认停用吗？",
					function(btn){
						if(btn=='yes'){
							Ext.Ajax.request({
								url : '../basedev/batchCloseCentreById.do',
								params: {
									'ids':ids
								},
								async: false,
								success : function(response) {
									var json = Ext.JSON.decode(response.responseText); 
									Ext.ux.Toast.msg("提示", json.msg, 'success');
									var selectResultPanel = Ext.getCmp("BaseData_centreSendTime_centreSendTimePageElementGridPanel");
									selectResultPanel.getPagingToolbar().moveFirst();
								},
								exception : function(response) {
									var json = Ext.decode(response.responseText); 
									Ext.ux.Toast.msg("提示", json.msg, 'error');
								}
							});
						}
				})}
			});
		}
		return this.closeButton;
	},
	// 批量删除按钮
	batchDeleteButton: null,
	getBatchDeleteButton: function() {
		var me = this;
		if(Ext.isEmpty(me.batchDeleteButton)){
			me.batchDeleteButton = Ext.create('Ext.Button', {
				text :'删除',//批量删除
				handler : function() {
					var selectList = me.getSelectionModel().getSelection();
					if(Ext.isEmpty(selectList)){
						Ext.ux.Toast.msg("提示", '请选择要操作的数据');
						return;
					}
					
					var ids=[];
					for (var int = 0; int < selectList.length; int++) {
						ids[int] = selectList[int].data.id;
					}
					Ext.Msg.confirm("提示", "你确定要删除已选项吗？",
					function(btn){
						if(btn=='yes'){
							Ext.Ajax.request({
								url : '../basedev/batchDeleteCentreSendTimeById.do',
								params: {
									'ids':ids
								},
								async: false,
								success : function(response) {
									var json = Ext.JSON.decode(response.responseText); 
									Ext.ux.Toast.msg("提示", json.msg, 'success');
									var selectResultPanel = Ext.getCmp("BaseData_centreSendTime_centreSendTimePageElementGridPanel");
									selectResultPanel.getPagingToolbar().moveFirst();
								},
								exception : function(response) {
									var json = Ext.decode(response.responseText); 
									Ext.ux.Toast.msg("提示", json.msg, 'error');
								}
							});
						}
				})}
			});
		}
		return this.batchDeleteButton;
	},
	
	

	// 新增按钮
	addButton: null,
	getAddButton: function() {
		var me = this;
		if(Ext.isEmpty(me.addButton)){
			me.addButton = Ext.create('Ext.Button', {
				text :'新增',//新增
				handler : function() {
					
					me.getcentreSendTimeAddWindow().show();
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
		me.store = Ext.create('BaseData.centreSendTime.centreSendTimeStore');
		me.bbar = me.getPagingToolbar();
		me.tbar = [me.getAddButton(),/*me.getBatchDeleteButton(),*/me.getStartButton(),me.getCloseButton()];
		me.columns = [
		    {
		   	xtype : 'rownumberer',
		   	text: '序号',
		   	width:40
		   	},           
			{
			xtype:'actioncolumn',
			text: '操作',
			width:50,
			items: [
{
	iconCls : 'deppon_icons_showdetail',
	tooltip : '查看',
	handler : function(gridView, rowIndex, colIndex) { // 查看
		var seeTimeWindow = Ext.getCmp("BaseData_centreSendTime_centreSendTimePageElementGridPanel").getseeTimeWindow();
		seeTimeWindow.setTitle('查看派件时效(转运/分拨)');
		var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
		var viewTimeForm = seeTimeWindow.getViewTimeForm();
		var viewTimeModel = Ext.create('BaseData.centreSendTime.centreSendTimeModule',rowInfo.raw);
		viewTimeForm.loadRecord(viewTimeModel);
		viewTimeForm.setShowValue(rowInfo);
		//加载门店集合
        Ext.Ajax.request({
            url : "../basedev/getOrgsByCentreTime.do",
            params : {
                'configCode' :rowInfo.data.configCode
            },
            success : function(response) {
                var result = Ext.JSON.decode(response.responseText);
                if(result.success){
                	viewTimeForm.setSiteNamesShowValue(result.data.siteNames);
                }else{
                    Ext.ux.Toast.msg('提示', result.msg, 'error'); 
                }
            },
            failure : function(response) {
                Ext.ux.Toast.msg('提示',response.responseText, 'error');
            }}
        );
		// 显示窗口
        seeTimeWindow.show();
	}
}, 			        
			    { 
				iconCls: 'deppon_icons_edit',
				tooltip: '修改',//修改
				handler: function(grid, rowIndex, colIndex) { // 修改按钮
					var rowInfo = Ext.getCmp('BaseData_centreSendTime_centreSendTimePageElementGridPanel').store.getAt(rowIndex);
					var sid = rowInfo.data.id;
					
					 
					Ext.Ajax.request({
						url : "../basedev/preUpdateCentreSendTime.do",
						params : {
							'id' : sid
						},
						success : function(response) {
							var data  =  Ext.JSON.decode(response.responseText);
							if(data.success){
								var xWindow = me.getcentreSendTimeChangeWindow(data);
								xWindow.show();
							}else{
								
								Ext.Msg.alert("查询回显数据失败"); 
							}
						},
						exception : function(response) {
							var json = Ext.decode(response.responseText);
						}}
					);
				}
			}/*, {
				iconCls: 'deppon_icons_delete',
				tooltip:'删除',//删除
				handler: function(grid, rowIndex, colIndex) {
					删除
					var rowInfo = Ext.getCmp('BaseData_centreSendTime_centreSendTimePageElementGridPanel').store.getAt(rowIndex);
					var sId = rowInfo.data.id;
					
					Ext.Msg.confirm('删除', '是否删除',
					function(btn){
						if(btn=='yes'){
							Ext.Ajax.request({
								url : '../basedev/deleteCentreSendTime.do',
								params: {
									'id': sId
									
								},
								async: false,
								success : function(response) {
									var json = Ext.decode(response.responseText); 
									Ext.ux.Toast.msg(basedev.centreSendTime.i18n('basedev.notice.msginfo'), json.msg);
									var selectResultPanel = Ext.getCmp("BaseData_centreSendTime_centreSendTimePageElementGridPanel");
									selectResultPanel.getPagingToolbar().moveFirst();
								},
								exception : function(response) {
									var json = Ext.decode(response.responseText); 
									Ext.ux.Toast.msg(basedev.centreSendTime.i18n('basedev.notice.msginfo'), json.msg);
								}
							});
						}
					});
				}
			}*/]
		}, {
            hidden : true,
            dataIndex : 'id'
        },{
            hidden : true,
            dataIndex : 'rId'
        },{
            text : '时效编号',
            width: 100,
            dataIndex : 'configCode'
        },{
            text : '时效名称',
            width: 100,
            dataIndex : 'configName'
        },/*{
            text : '分派/转运',
            width: 200,
            dataIndex : 'siteNames'
        },*/{
            text : '一派到达开始时间',
            width: 150,
            dataIndex : 'oneArrivalTimeS'
        },
        {
            text : '一派到达结束时间',
            width: 150,
            dataIndex : 'oneArrivalTimeE'
        },{
            text : '一派日常合格签收时间',
            width: 150,
            dataIndex : 'oneDispatchEndTime'
        },
        {
            text : '一派月度合格签收时间',
            width: 150,
            dataIndex : 'oneDispatchMonthEndTime'
        },{
            text : '二派到达开始时间',
            width: 150,
            dataIndex : 'twoArrivalTimeS'
        },{
            text : '二到达结束时间',
            width: 150,
            dataIndex : 'twoArrivalTimeE'
        },{
            text : '二派日合格签收时间',
            width: 150,
            dataIndex : 'twoDispatchEndTime'
        },
        {
            text : '二派月合格签收时间',
            width: 150,
            dataIndex : 'twoDispatchMonthEndTime'
        }
        ,{
            text : '一派权重',
            width: 100,
            dataIndex : 'oneDispatchRate'
        },{
            text : '二派权重',
            width: 100,
            dataIndex : 'twoDispatchRate'
        },{
    		text : '启用',
    		width: 100,
    	 dataIndex : 'blFlag',
         renderer:function(value){
             if(value == '1') return '是';
             if(value == '0') return '否';
             return value;
         }
    },
        {
            text : '创建人',
            width: 100,
            dataIndex : 'createUserCode'
        },{
        	xtype : 'datecolumn',
            text : '创建时间',
            format :'Y-m-d H:i:s',
            width:130,
            dataIndex : 'createTime'
        },{
            text : '修改人',
            width: 100,
            dataIndex : 'modifyUserCode'
        },
        {
        	xtype : 'datecolumn',
            text : '修改时间',
            format :'Y-m-d H:i:s',
            width:130,
            dataIndex : 'modifyTime'
        },
        {
            text : '备注',
            width: 100,
            dataIndex : 'remark'
        }
        ];
		
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});

//增加查看窗口
/**
 * 查看窗口
 */
Ext.define('Basedev.centreSendTime.seeTimeWindow', {
	extend: 'Ext.window.Window',
	width: 1000,
	modal: true,
	closeAction: 'hide',
	viewTimeForm : null,
	getViewTimeForm: function(){
		if (Ext.isEmpty(this.viewTimeForm)) {
			this.viewTimeForm = Ext.create("Basedev.centreSendTime.viewTimeForm");
		}
		return this.viewTimeForm;
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
			me.getViewTimeForm()
	    ];
		me.buttons = [
			me.getCancelButton()
		];
		me.callParent([cfg]);
	}
});

/**
 * 配置查看表单
 */
Ext.define('Basedev.centreSendTime.viewTimeForm', {
	extend : 'Ext.form.Panel',
	id : Basedev.centreSendTime.viewTimeForm_id,
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
		name: 'oneArrivalTimeS',
		fieldLabel: '一派到达开始时间'
	}, {
		name: 'oneArrivalTimeE',
		fieldLabel: '一派到达结束时间'
	},{
		name: 'oneDispatchEndTime',
		fieldLabel: '一派日常合格签收时间'
	},{
		name: 'oneDispatchMonthEndTime',
		fieldLabel: '一派月度合格签收时间'
	},{
		name: 'twoArrivalTimeS',
		fieldLabel: '二派到达开始时间'
	},{
		name: 'twoArrivalTimeE',
		fieldLabel: '二到达结束时间'
	},{
		name: 'twoDispatchEndTime',
		fieldLabel: '二派日合格签收时间'
	},
	{
		name: 'twoDispatchMonthEndTime',
		fieldLabel: '二派月合格签收时间'
	},
	{
		name: 'oneDispatchRate',
		fieldLabel: '一派权重'
	},{
		name: 'twoDispatchRate',
		colspan: 3,
		fieldLabel: '二派权重'
	},
	{
		 xtype : 'textareafield',
         grow : false,
         name: 'remark',
         height:120,
         maxLength : 80,
         colspan: 3,
         fieldLabel: '备注'
	},
	{
       name: 'siteNames',
       xtype: 'textareafield',
       fieldLabel: '门店',
       grow: true,
       growMax: 310,
       colspan: 3,
       height:120,
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
 * 定义表单(新增)
 */
Ext.define('BaseData.centreSendTime.centreSendTimeAddForm', {
	extend: 'Ext.form.Panel',
	defaultType: 'textfield',
	layout:'column',
	defaults: {
    	margin:'5 10 5 10',
		labelWidth: 70,  
	    validateOnChange: false,
		width: 750
	},
	initComponent: function() {
		var me = this;
		var pone = Ext.create('BaseData.centreSendTime.p1');
		var ptwo= Ext.create('BaseData.centreSendTime.p2');
		var pthree= Ext.create('BaseData.centreSendTime.p3');
		me.items = [pone,ptwo,pthree];
		me.callParent();
	}
});
/**
 * 定义一个修改使用的updatepanel
 */
Ext.define('BaseData.centreSendTime.updatepanel', {
	extend: 'Ext.form.Panel',
	title : '设置',
	id: 'BaseData.centreSendTime.updatepanel',
	defaultType: 'textfield',
	defaults: {
    	margin:'5 10 5 10',
		labelWidth: 70,
	    validateOnChange: false,
		width: 250
	},
	layout:'column',
	initComponent: function() {
		var me = this;
		me.items = [{
			xtype: 'textfield',
            name: 'id',
            hidden : true,
            allowBlank: true
			}, { 
			xtype: 'textfield',
			name: 'configCode',
			fieldLabel: '时效编号',
			maxLength: 20,
			allowBlank: false,
			readOnly:true
		},
		{
			xtype: 'textfield',
			name: 'configName',
			fieldLabel: '时效名称',
			maxLength: 20,
			allowBlank: false ,

			validateOnBlur : true,
			validator : function(field){
				if(!field){
					return true;
				}
				
				var configCode= me.getForm().findField('configCode').getValue();
		    	var paramsObj = {configCode : configCode, configName : field};
				var valid = false;
				Ext.Ajax.request({
					url : basedev.realPath('getConfigNameByCentreInsert2.do'),
					params: paramsObj,
					async : false,
					success : function(response) {
						var result = Ext.JSON.decode(response.responseText);
						if(result.success){
							var data =result.data;
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
		},
		{
			 xtype : 'textareafield',
	         grow : false,
	         name: 'remark',
	         maxLength : 80,
	         height:120,
//	         width:30,
	         fieldLabel: '备注'
	     },{
			xtype: 'checkbox',
			name: 'blFlag',
			fieldLabel: '启用',
			checked:true,	//默认选中
			inputValue: 1,
			uncheckedValue:0,
			allowBlank: true
		}
        ];
		me.callParent();
	}
});

/**
 * panel1
 */
Ext.define('BaseData.centreSendTime.p1', {
	extend: 'Ext.form.Panel',
	title : '设置',
	id: 'BaseData.centreSendTime.p1',
	defaultType: 'textfield',
	defaults: {
    	margin:'5 10 5 10',
		labelWidth: 70,
	    validateOnChange: false,
		width: 850
	},
//	layout:'column',
	layout : {
	    type : 'column',
	    columns : 2
	},
	initComponent: function() {
		var me = this;
		me.items = [{
			xtype: 'textfield',
            name: 'id',
            hidden : true,
            allowBlank: true
			}, { 
			xtype: 'textfield',
			name: 'configCode',
			columnWidth: .3,
			maxLength: 20,
			fieldLabel: '时效编号',
			allowBlank: false,
			validateOnBlur : true,
			regex : /^[A-Za-z0-9]+$/,
			regexText : '该输入项只能输入数字和字母',
			validator : function(field){
				if(!field){
					return true;
				}
				var paramsObj = {configCode : field};
				var valid = false;
				Ext.Ajax.request({
				url : basedev.realPath('getConfigCodeByCentreInsert.do'),
				params: paramsObj,
				async : false,
				success : function(response) {
					var result = Ext.JSON.decode(response.responseText);
					if(result.success){
						var data =result.data;
						if(data != null && data !=''){
							
//								Ext.ux.Toast.msg('提示', result.msg);
							
						}else{
							valid = true;
						}
					}else{
						valid = true;
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
		},
		{
			xtype: 'textfield',
			columnWidth: .3,
			name: 'configName',
			fieldLabel: '时效名称',
			maxLength: 20,
			allowBlank: false ,
			validateOnBlur : true,
			validator : function(field){
				if(!field){
					return true;
				}
				var paramsObj = {configName : field};
				var valid = false;
				Ext.Ajax.request({
					url : basedev.realPath('getConfigNameByCentreInsert.do'),
					params: paramsObj,
					async : false,
					success : function(response) {
						var result = Ext.JSON.decode(response.responseText);
						if(result.success){
							
							var data =result.data;
							if(data != null && data !=''){
//								Ext.ux.Toast.msg('提示', result.msg);
							}else{
								valid = true;
							}
						}else{
							valid = true;
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
		},
		{
			 xtype : 'textareafield',
	         grow : false,
	         name: 'remark',
	         maxLength : 80,
//	         width:30,
	         columnWidth: .5,
	         height:120,
	         fieldLabel: '备注'
	     },{
			xtype: 'checkbox',
			name: 'blFlag',
			fieldLabel: '启用',
			columnWidth: .5,
			checked:true,	//默认选中
			inputValue: 1,
			uncheckedValue:0,
			allowBlank: true
		}
        ];
		me.callParent();
	}
});
/**
 * panel2
 */
Ext.define('BaseData.centreSendTime.p2', {
	extend: 'Ext.form.Panel',
	title : '时效设置',
	id: 'BaseData.centreSendTime.p2',
	defaultType: 'textfield',
	defaults: {
    	margin:'10 10 10 10',
//		labelWidth: 70,
	    validateOnChange: false,
		width: 850
	},
//	layout:'column',
	layout : {
	    type : 'column',
	    columns : 2
	},
	initComponent: function() {
		var me = this;
		me.items = [
            {
            	fieldLabel: '一派 到达时间区域1 上一天',
        		name : 'oneArrivalTimeS',
                xtype: 'timefield',
                labelSeparator: ':',
                pickerMaxHeight: 200,
                labelWidth: 190,
                columnWidth: .4,
                increment: 30,
                format: basedev.centreSendTime.FORMAT_TIME,
                allowBlank: true,
                editable : false,
                submitFormat: basedev.centreSendTime.FORMAT_TIME
            },{
    		fieldLabel: '至  当天',
    		name : 'oneArrivalTimeE',
            xtype: 'timefield',
            labelSeparator: ':',
            pickerMaxHeight: 200,
            increment: 30,
            format: basedev.centreSendTime.FORMAT_TIME,
            allowBlank: true,
            editable : false,
            labelWidth: 70,
            columnWidth: .4,
            submitFormat: basedev.centreSendTime.FORMAT_TIME
            },{
            	fieldLabel: '日常合格签收时间  当天(之前)',
        		name : 'oneDispatchEndTime',
                xtype: 'timefield',
                labelSeparator: ':',
                pickerMaxHeight: 200,
                increment: 30,
                format: basedev.centreSendTime.FORMAT_TIME,
                allowBlank: true,
                editable : false,
                labelWidth: 200,
                columnWidth: .4,
                submitFormat: basedev.centreSendTime.FORMAT_TIME
            },{
            	fieldLabel: '月度合格签收时间 当天(之前)',
        		name : 'oneDispatchMonthEndTime',
                xtype: 'timefield',
                labelSeparator: ':',
                pickerMaxHeight: 200,
                increment: 30,
                format: basedev.centreSendTime.FORMAT_TIME,
                allowBlank: true,
                editable : false,
                labelWidth: 200,
                columnWidth: .4,
                submitFormat: basedev.centreSendTime.FORMAT_TIME
          
        },{
        	fieldLabel: '二派  到达时间区域2',
    		name : 'twoArrivalTimeS',
            xtype: 'timefield',
            labelSeparator: ':',
            pickerMaxHeight: 200,
            increment: 30,
            format: basedev.centreSendTime.FORMAT_TIME,
            allowBlank: true,
            editable : false,
            labelWidth: 150,
            columnWidth: .4,
            submitFormat: basedev.centreSendTime.FORMAT_TIME
        },{
        	fieldLabel: '至',
    		name : 'twoArrivalTimeE',
            xtype: 'timefield',
            labelSeparator: ':',
            pickerMaxHeight: 200,
            increment: 30,
            format: basedev.centreSendTime.FORMAT_TIME,
            allowBlank: true,
            editable : false,
            labelWidth: 70,
            columnWidth: .4,
            submitFormat: basedev.centreSendTime.FORMAT_TIME
        },{
        	fieldLabel: '二派合格签收时间   当天(之前)',
    		name : 'twoDispatchEndTime',
            xtype: 'timefield',
            labelSeparator: ':',
            pickerMaxHeight: 200,
            increment: 30,
            format: basedev.centreSendTime.FORMAT_TIME,
            allowBlank: true,
            editable : false,
            labelWidth: 190,
            columnWidth: .4,
            submitFormat: basedev.centreSendTime.FORMAT_TIME
        },
        {
        	fieldLabel: '二派月合格签收时间当天(之前)',
    		name : 'twoDispatchMonthEndTime',
            xtype: 'timefield',
            labelSeparator: ':',
            pickerMaxHeight: 200,
            increment: 30,
            format: basedev.centreSendTime.FORMAT_TIME,
            allowBlank: true,
            editable : false,
            labelWidth: 210,
            columnWidth: .4,
            submitFormat: basedev.centreSendTime.FORMAT_TIME
      
    },
       {
            	xtype:'numberfield',
            	name: 'oneDispatchRate',
    			allowBlank: true,
    			editable : true,
    			decimalPrecision: 2, // 精确地小数点后两位
                allowDecimals: true,
                maxValue: 1, // 最大值
                minValue: 0,
                labelWidth: 70,
                columnWidth: .4,
                fieldLabel: '一派权重'
        },{
        	xtype:'numberfield',
        	name: 'twoDispatchRate',
			allowBlank: true,
			editable : true,
			decimalPrecision: 2, // 精确地小数点后两位
            allowDecimals: true,
            maxValue: 1, // 最大值
            minValue: 0,
            labelWidth: 70,
            columnWidth: .4,
            fieldLabel: '二派权重'
        } 
        ];
		me.callParent();
	}
});
/**
 * panel3
 */
Ext.define('BaseData.centreSendTime.p3', {
	extend: 'Ext.form.Panel',
	title : '使用站点',
	siteCodes: null,
	id: 'BaseData.centreSendTime.p3',
	defaultType: 'textfield',
	defaults: {
    	margin:'5 10 5 10',
		labelWidth: 70,
	    validateOnChange: false,
		width: 250
	},
	// 添加按钮
	addButton: null,
	getAddButton: function() {
		var me = this;
		if(Ext.isEmpty(me.addButton)){
			me.addButton = Ext.create('Ext.Button', {
				text :'添加',//添加
				width:50, 
				height:30,
				handler : function() {
					//获取p3看它是否有值
					var flag1= Ext.getCmp("BaseData.centreSendTime.updatepanel");
					var flag2= Ext.getCmp("BaseData.centreSendTime.p1");
					
					if(flag1== undefined)
					{//新增
					var centreSendTimeSelectWindow =Ext.create('BaseData.centreSendTime.centreSendTimeSelectWindow');
					centreSendTimeSelectWindow.show();
					}
					if(flag2== undefined){//修改
						var centreSendTimeSelectWindow =Ext.create('BaseData.centreSendTime.centreSendTimeSelectChangeWindow');
						centreSendTimeSelectWindow.show();
					}
				}
			});
		}
		return this.addButton;
	},
	layout:'column',
	initComponent: function() {
		var me = this;
		var addButton=me.getAddButton();
		me.items = [{
			xtype: 'textfield',
			name: 'rId',
			hidden : true,
			allowBlank: true
			},
           /* {	
            xtype: 'textfield',
            name: 'siteCode',
            hidden : true   
        },*/{
			 xtype : 'textareafield',
	         grow : true,
	         width:400,
	         name: 'siteNames',
	         height:120,
	         readOnly: true,
	         readOnlyCls: '',
	         fieldLabel: '使用转运/分拨中心',
	         allowBlank: false
	     },addButton
			
        ];
		me.callParent();
	}
});



/**
 * 
 * 定义修改表单
 */
Ext.define('BaseData.centreSendTime.centreSendTimeChangeForm', {
	extend: 'Ext.form.Panel',
	defaultType: 'textfield',
	layout:'column',
	defaults: {
    	margin:'5 10 5 10',
		colspan : 1,
		labelWidth: 70,
	    validateOnChange: false,
		width: 750
	},
    
	initComponent: function() {
		var me = this;
		var pone = Ext.create('BaseData.centreSendTime.updatepanel');
		var ptwo= Ext.create('BaseData.centreSendTime.p2');
		var pthree= Ext.create('BaseData.centreSendTime.p3');
		me.items = [pone,ptwo,pthree];
		me.callParent();
	}
});
/**
 * 使用分拨/转运的窗口(新增)
 */

Ext.define('BaseData.centreSendTime.centreSendTimeSelectWindow', {
    extend: 'Ext.window.Window',
    width: 800,
    modal: true,
    siteCodes: null,
    closeAction: 'destroy',
    siteCodes: null,
    rootNode:{
        id : '999999',
        parentId : '-1',
        text : '快运本部',
        checked : null,
        expanded : true
    },
    panel: null,
    getPanel: function(){
    	var param = '';
        this.panel = Ext.create('Rosefinch.Basedev.BaseSitePanelSelectPanel',{
           treeUrl: '../basedev/getPriceRegionTree2.do',      
       });
       return this.panel;
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
                     var myp3= Ext.getCmp("BaseData.centreSendTime.p3");  
                     myp3.getForm().findField('siteNames').setValue(siteNames);
                     myp3.siteCodes=record;
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
                var s=Ext.getCmp("BaseData.centreSendTime.p3");
                me.siteCodes=s.siteCodes;
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
                me.panel.getLoadDeptTree().setRootNode(me.rootNode);
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
/**
 * 使用分拨/转运的窗口(修改)
 */

Ext.define('BaseData.centreSendTime.centreSendTimeSelectChangeWindow', {
    extend: 'Ext.window.Window',
    width: 800,
    modal: true,
    siteCodes: null,
    closeAction: 'destroy',
    siteCodes: null,
    rootNode:{
        id : '999999',
        parentId : '-1',
        text : '快运本部',
        checked : null,
        expanded : true
    },
    panel: null,
    getPanel: function(){
    	var param = '';
    	var updatepanel=Ext.getCmp("BaseData.centreSendTime.updatepanel");
    	param = updatepanel.getForm().findField('configCode').getValue();
        this.panel = Ext.create('Rosefinch.Basedev.BaseSitePanelSelectPanel',{
           treeUrl: '../basedev/getPriceRegionTree2.do', 
           treeparams: param
           
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
                     var myp3= Ext.getCmp("BaseData.centreSendTime.p3");  
                     myp3.getForm().findField('siteNames').setValue(siteNames);
                     myp3.siteCodes=record;
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
                var s=Ext.getCmp("BaseData.centreSendTime.p3");
                me.siteCodes=s.siteCodes;
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
                me.panel.getLoadDeptTree().setRootNode(me.rootNode);
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
/**
 * 
 * 新增窗口
 * 
 */
Ext.define('BaseData.centreSendTime.centreSendTimeAddWindow',{
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	closeAction : 'destroy',
	resizable:false,
	title : '分拨/转运派件时效新增',
	height:850,
	width:850,
	layout:'column',
	addForm:null,//
	getAddForm:function(){
		if(Ext.isEmpty(this.addForm)){
			this.addForm = Ext.create('BaseData.centreSendTime.centreSendTimeAddForm');
			return this.addForm;
		}else{
			return this.addForm;
		}
	},
	
	initComponent: function() {
		var me = this;
		var addForm = me.getAddForm();
		me.items = [addForm],
		me.buttons = [{
			text:'取消',
			handler: function() {
				me.close();
			}
		},{
			text: '保存',
			cls:'yellow_button',
			handler: me.addRegion,
			scope: me
		}];
		//监听事件
		me.listeners = {
			beforeshow:function(){
				addForm.getForm().reset();
			},
			beforeclose:function(){
				
			}
		};
		me.callParent();
	},
	addRegion:function(){
		var me = this,
		centreSendTimeForm = me.down('form').getForm();
		var poneForm = Ext.getCmp("BaseData.centreSendTime.p1");
		var ptwoForm = Ext.getCmp("BaseData.centreSendTime.p2");
		var pthreeForm = Ext.getCmp("BaseData.centreSendTime.p3");
		var blFlag =  poneForm .getValues()['blFlag'];
        if( blFlag == false){
        	blFlag = 0;
        }else{
        	blFlag = 1;
        }
        var data ={
        		configCode:poneForm.getValues()['configCode'],
            	configName:poneForm.getValues()['configName'],
            	blFlag:blFlag,
            	remark:poneForm.getValues()['remark'],
            	
            	oneArrivalTimeS:ptwoForm.getValues()['oneArrivalTimeS'],
            	oneArrivalTimeE:ptwoForm.getValues()['oneArrivalTimeE'],
            	oneDispatchEndTime:ptwoForm.getValues()['oneDispatchEndTime'],
            	oneDispatchMonthEndTime:ptwoForm.getValues()['oneDispatchMonthEndTime'],
            	twoArrivalTimeS:ptwoForm.getValues()['twoArrivalTimeS'],
            	twoArrivalTimeE:ptwoForm.getValues()['twoArrivalTimeE'],
            	twoDispatchEndTime:ptwoForm.getValues()['twoDispatchEndTime'],
            	twoDispatchMonthEndTime:ptwoForm.getValues()['twoDispatchMonthEndTime'],
            	oneDispatchRate:ptwoForm.getValues()['oneDispatchRate'],
            	twoDispatchRate:ptwoForm.getValues()['twoDispatchRate'],
            	siteCodeList:pthreeForm.siteCodes
        };
		if (centreSendTimeForm.isValid()){
				Ext.Ajax.request({
                url: '../basedev/addCentreSendTime.do',
                method: 'post',
                jsonData : Ext.JSON.encode(data),
                success : function(response) {
                    me.close();
                    var json = Ext.decode(response.responseText); 
                    Ext.ux.Toast.msg(basedev.centreSendTime.i18n('basedev.notice.msginfo'), json.msg);
                    var selectResultPanel = Ext.getCmp("BaseData_centreSendTime_centreSendTimePageElementGridPanel");
                    selectResultPanel.getPagingToolbar().moveFirst();
                },
                exception : function(response) {
                    me.close();
                    var json = Ext.decode(response.responseText); 
                    Ext.ux.Toast.msg(basedev.centreSendTime.i18n('basedev.notice.msginfo'), json.msg);
                }
            });
			}
		}
	}
);

/**
 * 修改窗口
 * 
 */
Ext.define('BaseData.centreSendTime.centreSendTimeChangeWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	closeAction : 'destroy',
	resizable:false,
	title : '编辑分拨/转运派件时效',
	width:800,
	height:850,
	editData:null,//修改数据 
	changeForm:null,
	getChangeForm:function(){
		if(Ext.isEmpty(this.changeForm)){
			this.changeForm = Ext.create('BaseData.centreSendTime.centreSendTimeChangeForm');
			return this.changeForm;
		}else{
			return this.changeForm;
		}
	},
	initComponent: function() {
		var me = this;
		var changeForm = me.getChangeForm();
		me.items = [ changeForm],
        me.buttons = [{
        	text:'取消',
        	handler: function() {
        		me.close();
        	}
         },{
        	 text: '保存',
        	 cls:'yellow_button',
        	 handler: me.updatecentreSendTime,
        	 scope: me
         }];
		me.listeners = {
			beforeshow:function(){
				var poneChangeForm = Ext.getCmp("BaseData.centreSendTime.updatepanel");
				var ptwoChangeForm = Ext.getCmp("BaseData.centreSendTime.p2");
				var pthreeChangeForm = Ext.getCmp("BaseData.centreSendTime.p3");
				changeForm.getForm().reset();
				poneChangeForm.getForm().findField('id').setValue(me.editData.data.id);
				poneChangeForm.getForm().findField('configCode').setValue(me.editData.data.configCode);
				poneChangeForm.getForm().findField('configName').setValue(me.editData.data.configName);
				poneChangeForm.getForm().findField('remark').setValue(me.editData.data.remark);
				

				//启用
			    if(me.editData.data.blFlag==0){
			    	poneChangeForm.getForm().findField('blFlag').setValue(false);
            }else{
            	//停用
            		poneChangeForm.getForm().findField('blFlag').setValue(true);
              }
				
				ptwoChangeForm.getForm().findField('oneArrivalTimeS').setValue(me.editData.data.oneArrivalTimeS);
				ptwoChangeForm.getForm().findField('oneArrivalTimeE').setValue(me.editData.data.oneArrivalTimeE);
				ptwoChangeForm.getForm().findField('oneDispatchEndTime').setValue(me.editData.data.oneDispatchEndTime);
				ptwoChangeForm.getForm().findField('oneDispatchMonthEndTime').setValue(me.editData.data.oneDispatchMonthEndTime);
				ptwoChangeForm.getForm().findField('twoArrivalTimeS').setValue(me.editData.data.twoArrivalTimeS);
				ptwoChangeForm.getForm().findField('twoArrivalTimeE').setValue(me.editData.data.twoArrivalTimeE);
				ptwoChangeForm.getForm().findField('twoDispatchEndTime').setValue(me.editData.data.twoDispatchEndTime);
				ptwoChangeForm.getForm().findField('twoDispatchMonthEndTime').setValue(me.editData.data.twoDispatchMonthEndTime);
				
				ptwoChangeForm.getForm().findField('oneDispatchRate').setValue(me.editData.data.oneDispatchRate);
				ptwoChangeForm.getForm().findField('twoDispatchRate').setValue(me.editData.data.twoDispatchRate);
				

				pthreeChangeForm.getForm().findField('siteNames').setValue(me.editData.data.siteNames);
				pthreeChangeForm.siteCodes=me.editData.data.siteCodeList

			},
			beforeclose:function(){
				if(!Ext.isEmpty(changeForm.store)){
					me.editData = null;
				}
				
			}
		};
		me.callParent();
	},
	updatecentreSendTime:function(){
		var me = this,
		centreSendTimeForm = me.down('form').getForm();
		var poneChangeForm = Ext.getCmp("BaseData.centreSendTime.updatepanel");
		var ptwoChangeForm = Ext.getCmp("BaseData.centreSendTime.p2");
		var pthreeChangeForm = Ext.getCmp("BaseData.centreSendTime.p3");
		var blFlag = poneChangeForm .getValues()['blFlag'];
		
		if( blFlag == false){
			blFlag = 0;
		}else{
			blFlag = 1;
		}
		var data={
				id:poneChangeForm.getValues()['id'],
				configCode	:poneChangeForm.getValues()['configCode'],
				configName	:poneChangeForm.getValues()['configName'],
				remark	:poneChangeForm.getValues()['remark'],
				blFlag	:blFlag,
				
				oneArrivalTimeS	:ptwoChangeForm.getValues()['oneArrivalTimeS'],
				oneArrivalTimeE	:ptwoChangeForm.getValues()['oneArrivalTimeE'],
				oneDispatchEndTime	:ptwoChangeForm.getValues()['oneDispatchEndTime'],
				oneDispatchMonthEndTime	:ptwoChangeForm.getValues()['oneDispatchMonthEndTime'],
				twoArrivalTimeS	:ptwoChangeForm.getValues()['twoArrivalTimeS'],
				twoArrivalTimeE	:ptwoChangeForm.getValues()['twoArrivalTimeE'],
				twoDispatchEndTime:ptwoChangeForm.getValues()['twoDispatchEndTime'],
				twoDispatchMonthEndTime	:ptwoChangeForm.getValues()['twoDispatchMonthEndTime'],
				oneDispatchRate	:ptwoChangeForm.getValues()['oneDispatchRate'],
				twoDispatchRate	:ptwoChangeForm.getValues()['twoDispatchRate'],
				
				siteCodeList:pthreeChangeForm.siteCodes
			
		};
		if (centreSendTimeForm.isValid()){
			Ext.Ajax.request({
				url: '../basedev/updateCentreSendTime.do',
				method: 'post',
				 jsonData : Ext.JSON.encode(data),
				success : function(response) {
					me.close();
					var json = Ext.decode(response.responseText); 
					Ext.ux.Toast.msg("提示", json.msg);
					var selectResultPanel = Ext.getCmp("BaseData_centreSendTime_centreSendTimePageElementGridPanel");
					selectResultPanel.getPagingToolbar().moveFirst();
				},
				exception : function(response) {
					me.close();
					var json = Ext.decode(response.responseText); 
					Ext.ux.Toast.msg("提示", json.msg);
				}
			});
		}
	}
});
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp(basedev.centreSendTime.CONTENT_ID)) {
		return;
	};
	var searchForm = Ext.create('BaseData.centreSendTime.centreSendTimeForm');
	var resultGrid = Ext.create('BaseData.centreSendTime.centreSendTimePageElementGridPanel');
	var content = Ext.create('Ext.panel.Panel', {
		id: basedev.centreSendTime.CONTENT_ID,
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
	Ext.getCmp('T_basedev-centreSendTime').add(content);
});