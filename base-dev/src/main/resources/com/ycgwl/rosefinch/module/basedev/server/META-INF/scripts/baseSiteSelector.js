/**
 * 
 * 定义网点选择装置的面板
 * 
 */
Ext.define('Rosefinch.baseDev.baseSiteSelectorPanel',{
	extend : 'Ext.panel.Panel',
	cls : "panelContentNToolbar",
	id:'Rosefinch_baseDev_baseSiteSelectorPanel_id',
	bodyCls : 'panelContentNToolbar-body',
	alias : 'widget.baseSitePanelSelector',
	bodyPadding: 5, 
	title: '网点选择',
	selectForm:null,
	resultGrid:null,
	getSelectForm:function(){
		if(Ext.isEmpty(this.selectForm)){
			this.selectForm = Ext.create('Rosefinch.baseDev.baseSiteSelectorForm');
		}
		return this.selectForm;
	},
	getResultGrid:function(){
		if(Ext.isEmpty(this.resultGrid)){
			this.resultGrid = Ext.create('Rosefinch.baseDev.baseSiteSelectorGrid');
		}
		return this.resultGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items=[me.getSelectForm(),
		          me.getResultGrid()
		          ];		
		me.callParent([cfg]);

	}
});

/**
 * 
 * 定义网点选择器的查询form
 * 
 */
Ext.define('Rosefinch.baseDev.baseSiteSelectorForm', {
	extend:'Ext.form.Panel',
	id:'Rosefinch_baseDev_baseSiteSelectorForm_Id',
	frame : true,
	//title: '请输入网点编码/名称模糊查询',//查询
	layout : 'column',	
	defaultType : 'textfield',
	//这个参数根据模块需求设置（多个类型用”,“隔开）
	siteType:null,
	initComponent: function(){
		var me = this;	
		me.items = [
		            {
		            	name: 'q_str_siteType',

		            	xtype:'textfield',
		            	hidden:true,
		            	columnWidth: 0
		            },
		            {
		            	name: 'q_int_flag',
		            	xtype:'textfield',
		            	hidden:true,
		            	columnWidth: 0
		            },{
		            	name: 'q_str_siteShortName',
		            	fieldLabel:'网点简称',
		            	xtype:'textfield',
		            	columnWidth: .5
		            },{
		            	name: 'q_str_siteCode',
		            	fieldLabel:'网点编号',
		            	xtype:'textfield',
		            	columnWidth: .5		            	
		            },
		            {
		            	name: 'q_str_city',
		            	fieldLabel:'所属城市',
		            	xtype:'textfield',
		            	columnWidth: .5		            	
		            },
		            {
		            	xtype:'commonSiteSelector',
		            	name: 'q_str_upFinanceCenter',
		            	fieldLabel:'上级中心',		 
		            	siteType:'1,2',
		            	blSite:1,
		            	columnWidth: .5	
		            },
		            {
		            	xtype : 'button',
		            	cls: 'yellow_button',
		            	text:"查询",
		            	width : 50,
		            	// 查询按钮的响应事件：
		            	handler: function() {
		            		if(me.getForm().isValid()){
		            			var selectResultPanel = Ext.getCmp("Rosefinch_baseDev_baseSiteSelectorGrid_Id");
		            			var regionType = selectResultPanel.up('window').down('form').getForm().findField('regionType').getValue();		
		            			if(Ext.isEmpty(regionType)){
		            				Ext.Msg.alert("提示","请先选择区间类型>_<"); 
		            				return;
		            			}
		            			selectResultPanel.loadValuationStore();
		            			selectResultPanel.setVisible(true);
		            			selectResultPanel.getPagingToolbar().moveFirst();
		            		}
		            	}
		            },{
		            	xtype : 'button',
		            	text:"重置",
		            	width : 50,
		            	handler: function() {
		            		me.getForm().reset();
		            	}
		            }];
		me.callParent();
	}
});
/***
 * 网点信息的Model
 * 
 * 
 */
Ext.define('Rosefinch.baseDev.baseSiteSelectorModel', {
	extend : 'Ext.data.Model',
	
	fields : [{
		name : 'siteId',
		type : 'long'
	},{
		name : 'siteCode'
	},{
		name : 'siteName'
	},
	{
		name : 'siteNameShort'
	},{
		name : 'siteType',
		type : 'int'
	},{
		name : 'upFinanceCenter'
	}
	]
});
/**
 * 查询结果的store
 */
Ext.define('Rosefinch.baseDev.baseSiteSelectorStore',{
	extend: 'Ext.data.Store',
	model : 'Rosefinch.baseDev.baseSiteSelectorModel',
	pageSize:10,
	proxy : {
		url : '../basedev/getSitePageListByCondition.do',
		type : 'ajax',		
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp("Rosefinch_baseDev_baseSiteSelectorForm_Id");
			var grid  = Ext.getCmp('Rosefinch_baseDev_baseSiteSelectorGrid_Id');
			var regionType = grid.up('window').down('form').getForm().findField('regionType').getValue();		
			if(Ext.isEmpty(regionType)){
				Ext.Msg.alert("提示","请先选择区间类型>_<"); 
				return;
			}
			var siteType=null;
			if(regionType==2){
				siteType='5';
			}else{
				siteType='3';
			}
			if (queryForm != null) {
				Ext.apply(operation, {
					params : {
						'q_str_siteCode':queryForm.getForm().findField('q_str_siteCode').getValue(),
						'q_str_siteShortName':queryForm.getForm().findField('q_str_siteShortName').getValue(),
						//过滤类型
						'q_str_city':queryForm.getForm().findField('q_str_city').getValue(),
						//传到后台的网点类型（实例：1，2，3）
						'q_str_siteType':siteType,
						'q_str_upFinanceCenter':queryForm.getForm().findField('q_str_upFinanceCenter').getValue(),
						//是否需要取当前网点下属网点
						'q_int_flag':queryForm.getForm().findField('q_int_flag').getValue()
					}
				});	
			}
		}
	}
});


/**
 * 定义查询结果表
 */

Ext.define('Rosefinch.baseDev.baseSiteSelectorGrid', {
	extend: 'Ext.grid.Panel',
	id:'Rosefinch_baseDev_baseSiteSelectorGrid_Id',
	frame: true,
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	selType : "rowmodel", 
	selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SIMPLE"}),
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
	addButton:null,
	getAddInfoButton:function(){
		var me = this;
		if(Ext.isEmpty(me.addButton)){
			me.addButton = Ext.create('Ext.Button', {
				text :'添加选中项',//新增
				handler : function() {				
					var tf = this.up('window').down('form').getForm().findField('siteNameList');
					var tfh = this.up('window').down('form').getForm().findField('siteCodeList');
					var str = tfh.getValue();
					var selectList = me.getSelectionModel().getSelection();
					for (var i = 0; i < selectList.length; i++) {			
						if(str.indexOf(selectList[i].get('siteCode')+';')==-1){
							tfh.setValue(tfh.getValue()+selectList[i].get('siteCode')+';');
							tf.setValue(tf.getValue()+selectList[i].get('siteName')+';')
						}
					}
				}
			});
		}
		return this.addButton;
	},
	delButton:null,
	getDelButton:function(){
		var me = this;
		if(Ext.isEmpty(me.delButton)){
			me.delButton = Ext.create('Ext.Button', {
				text :'移除选中项',
				handler : function() {
					var tf = this.up('window').down('form').getForm().findField('siteNameList');
					var tfh = this.up('window').down('form').getForm().findField('siteCodeList');
					var str = tfh.getValue();
					var selectList = me.getSelectionModel().getSelection();
					for (var i = 0; i < selectList.length; i++) {
						if(str.indexOf(selectList[i].get('siteCode')+';')!=-1){
							tfh.setValue(tfh.getValue().replace(selectList[i].get('siteCode')+';',''));
							tf.setValue(tf.getValue().replace(selectList[i].get('siteName')+';',''))
						}	
						
					}
				}
			});
		}
		return this.delButton;
	},
	constructor : function(config) {
		var me = this;
		me.store = Ext.create('Rosefinch.baseDev.baseSiteSelectorStore');
		me.bbar = me.getPagingToolbar();
		me.tbar = [me.getAddInfoButton(),me.getDelButton()];
		me.columns = [
			        {
			        	text : '网点简称',
			        	dataIndex : 'siteNameShort'
			        },
			        {
			        	text : '网点编号',
			        	dataIndex : 'siteCode'
			        },
			        {
			        	text : '网点全称',
			        	dataIndex : 'siteName'
			        }			        
		];
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});
/**************************************修改的面板**********************************************/
/**
 * 
 * 定义网点选择装置的面板
 * 
 */
Ext.define('Rosefinch.baseDev.baseSiteChangeSelectorPanel',{
	extend : 'Ext.panel.Panel',
	cls : "panelContentNToolbar",
	id:'Rosefinch_baseDev_baseSiteChangeSelectorPanel_id',
	bodyCls : 'panelContentNToolbar-body',
	alias : 'widget.baseSitePanelSelector',
	bodyPadding: 5, 
	title: '网点选择',
	selectForm:null,
	resultGrid:null,
	getSelectForm:function(){
		if(Ext.isEmpty(this.selectForm)){
			this.selectForm = Ext.create('Rosefinch.baseDev.baseSiteChangeSelectorForm');
		}
		return this.selectForm;
	},
	getResultGrid:function(){
		if(Ext.isEmpty(this.resultGrid)){
			this.resultGrid = Ext.create('Rosefinch.baseDev.baseSiteChangeSelectorGrid');
		}
		return this.resultGrid;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items=[me.getSelectForm(),
		          me.getResultGrid()
		          ];		
		me.callParent([cfg]);

	}
});

/**
 * 
 * 定义网点选择器的查询form
 * 
 */
Ext.define('Rosefinch.baseDev.baseSiteChangeSelectorForm', {
	extend:'Ext.form.Panel',
	id:'Rosefinch_baseDev_baseSiteChangeSelectorForm_Id',
	frame : true,
	//title: '请输入网点编码/名称模糊查询',//查询
	layout : 'column',	
	defaultType : 'textfield',
	//这个参数根据模块需求设置（多个类型用”,“隔开）
	siteType:null,
	initComponent: function(){
		var me = this;	
		me.items = [
		            {
		            	name: 'q_str_siteType',

		            	xtype:'textfield',
		            	hidden:true,
		            	columnWidth: 0
		            },
		            {
		            	name: 'q_int_flag',
		            	xtype:'textfield',
		            	hidden:true,
		            	columnWidth: 0
		            },{
		            	name: 'q_str_siteShortName',
		            	fieldLabel:'网点简称',
		            	xtype:'textfield',
		            	columnWidth: .5
		            },{
		            	name: 'q_str_siteCode',
		            	fieldLabel:'网点编号',
		            	xtype:'textfield',
		            	columnWidth: .5		            	
		            },
		            {
		            	name: 'q_str_city',
		            	fieldLabel:'所属城市',
		            	xtype:'textfield',
		            	columnWidth: .5		            	
		            },
		            {
		            	xtype:'commonSiteSelector',
		            	name: 'q_str_upFinanceCenter',
		            	fieldLabel:'上级中心',		 
		            	siteType:'1,2',
		            	blSite:1,
		            	columnWidth: .5	
		            },
		            {
		            	xtype : 'button',
		            	cls: 'yellow_button',
		            	text:"查询",
		            	width : 50,
		            	// 查询按钮的响应事件：
		            	handler: function() {
		            		if(me.getForm().isValid()){
		            			var selectResultPanel = Ext.getCmp("Rosefinch_baseDev_baseSiteChangeSelectorGrid_Id");
		            			var regionType = selectResultPanel.up('window').down('form').getForm().findField('regionType').getValue();		
		            			if(Ext.isEmpty(regionType)){
		            				Ext.Msg.alert("提示","请先选择区间类型>_<"); 
		            				return;
		            			}
		            			selectResultPanel.loadValuationStore();
		            			selectResultPanel.setVisible(true);
		            			selectResultPanel.getPagingToolbar().moveFirst();
		            		}
		            	}
		            },{
		            	xtype : 'button',
		            	text:"重置",
		            	width : 50,
		            	handler: function() {
		            		me.getForm().reset();
		            	}
		            }];
		me.callParent();
	}
});
/***
 * 网点信息的Model
 * 
 * 
 */
Ext.define('Rosefinch.baseDev.baseSiteChangeSelectorModel', {
	extend : 'Ext.data.Model',
	
	fields : [{
		name : 'siteId',
		type : 'long'
	},{
		name : 'siteCode'
	},{
		name : 'siteName'
	},
	{
		name : 'siteNameShort'
	},{
		name : 'siteType',
		type : 'int'
	},{
		name : 'upFinanceCenter'
	}
	]
});
/**
 * 查询结果的store
 */
Ext.define('Rosefinch.baseDev.baseSiteChangeSelectorStore',{
	extend: 'Ext.data.Store',
	model : 'Rosefinch.baseDev.baseSiteChangeSelectorModel',
	pageSize:10,
	proxy : {
		url : '../basedev/getSitePageListByCondition.do',
		type : 'ajax',		
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp("Rosefinch_baseDev_baseSiteChangeSelectorForm_Id");
			var grid  = Ext.getCmp('Rosefinch_baseDev_baseSiteChangeSelectorGrid_Id');
			var regionType = grid.up('window').down('form').getForm().findField('regionType').getValue();		
			if(Ext.isEmpty(regionType)){
				Ext.Msg.alert("提示","请先选择区间类型>_<"); 
				return;
			}
			var siteType=null;
			if(regionType==2){
				siteType='5';
			}else{
				siteType='3';
			}
			if (queryForm != null) {
				Ext.apply(operation, {
					params : {
						'q_str_siteCode':queryForm.getForm().findField('q_str_siteCode').getValue(),
						'q_str_siteShortName':queryForm.getForm().findField('q_str_siteShortName').getValue(),
						//过滤类型
						'q_str_city':queryForm.getForm().findField('q_str_city').getValue(),
						//传到后台的网点类型（实例：1，2，3）
						'q_str_siteType':siteType,
						'q_str_upFinanceCenter':queryForm.getForm().findField('q_str_upFinanceCenter').getValue(),
						//是否需要取当前网点下属网点
						'q_int_flag':queryForm.getForm().findField('q_int_flag').getValue()
					}
				});	
			}
		}
	}
});


/**
 * 定义查询结果表
 */

Ext.define('Rosefinch.baseDev.baseSiteChangeSelectorGrid', {
	extend: 'Ext.grid.Panel',
	id:'Rosefinch_baseDev_baseSiteChangeSelectorGrid_Id',
	frame: true,
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	selType : "rowmodel", 
	selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SIMPLE"}),
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
	addButton:null,
	getAddInfoButton:function(){
		var me = this;
		if(Ext.isEmpty(me.addButton)){
			me.addButton = Ext.create('Ext.Button', {
				text :'添加选中项',//新增
				handler : function() {				
					var tf = this.up('window').down('form').getForm().findField('siteNameList');
					var tfh = this.up('window').down('form').getForm().findField('siteCodeList');
					var str = tfh.getValue();
					var selectList = me.getSelectionModel().getSelection();
					for (var i = 0; i < selectList.length; i++) {			
						if(str.indexOf(selectList[i].get('siteCode')+';')==-1){
							tfh.setValue(tfh.getValue()+selectList[i].get('siteCode')+';');
							tf.setValue(tf.getValue()+selectList[i].get('siteName')+';')
						}
					}
				}
			});
		}
		return this.addButton;
	},
	delButton:null,
	getDelButton:function(){
		var me = this;
		if(Ext.isEmpty(me.delButton)){
			me.delButton = Ext.create('Ext.Button', {
				text :'移除选中项',
				handler : function() {
					var tf = this.up('window').down('form').getForm().findField('siteNameList');
					var tfh = this.up('window').down('form').getForm().findField('siteCodeList');
					var str = tfh.getValue();
					var selectList = me.getSelectionModel().getSelection();
					for (var i = 0; i < selectList.length; i++) {
						if(str.indexOf(selectList[i].get('siteCode')+';')!=-1){
							tfh.setValue(tfh.getValue().replace(selectList[i].get('siteCode')+';',''));
							tf.setValue(tf.getValue().replace(selectList[i].get('siteName')+';',''))
						}	
						
					}
				}
			});
		}
		return this.delButton;
	},
	constructor : function(config) {
		var me = this;
		me.store = Ext.create('Rosefinch.baseDev.baseSiteChangeSelectorStore');
		me.bbar = me.getPagingToolbar();
		me.tbar = [me.getAddInfoButton(),me.getDelButton()];
		me.columns = [
			        {
			        	text : '网点简称',
			        	dataIndex : 'siteNameShort'
			        },
			        {
			        	text : '网点编号',
			        	dataIndex : 'siteCode'
			        },
			        {
			        	text : '网点全称',
			        	dataIndex : 'siteName'
			        }			        
		];
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});