function getButtonName(value){
	var array = value.split("-");
	//此处只显示前面4个字符
	return array[array.length-1].substr(0,4);
}

//对象模型
Ext.define('Rosefinch.ECS.AddressModel',{
	extend:'Ext.data.Model',
	fields:[{
			name:'code',
			type:'string'
		},{
			name:'name',
			type:'string'
		},{
			name:'parentDistrictName',
			type:'string'
		},{
			name:'parentDistrictCode',
			type:'string'
		}]
});
//panel
Ext.define('Rosefinch.ECS.AddressPanel',{
	extend:'Ext.panel.Panel',
	hideCollapseTool:false,
	alias : ['widget.addressPanel'],
	baseCls:'area',
	//每页显示条数
	blockCnt:20,
	//该面板对应store
	store:null,
	width:320,
	height:400,
	//上一页
	prePanel:null,
	//下一页
	nextPanel:null,
	//内容面板
	contentPanel:null,
	//分页查询时参数
	_pageParam:null,
	getStore:function(){
		if(this.store == null){
			Ext.log("Rosefinch.ECS.AddressPanel: store is not initialized ");
		}
		return this.store;
	},
	layout : 'border',
	 defaults: {
	    bodyStyle:'padding:2px',
	    	layout:'fit'
	 },
	 getPrePanel:function(){
		return this.prePanel; 
	 },
	 getNextPanel:function(){
			return this.prePanel; 
		 },
	 getContentPanel:function(){
		 return this.contentPanel;
	 },
	 initComponent:function(){
		 var me = this;
		 var preBtn =Ext.create('Ext.panel.Panel', {
				xtype:'panel',
				layout:'fit',
				region:'west',
				cls:'pre naviBtn',
				items:[
					{
					xtype:'button',
					columnWidth:1,
					text:'<',
					handler:function(){
						var totalCnt = me.getStore().getTotalCount();
						var pageSize = me.getStore().pageSize;
						var curPage = me.getStore().currentPage;
						if(curPage <= 1){
							return false;
						}
						var param = {};
						Ext.apply(param,me._pageParam);
						me.getStore().previousPage({params:param});
					}
					}
				]
		 	});
		 var nextBtn =Ext.create('Ext.panel.Panel', {
				xtype:'panel',
				layout:'fit',
				region:'east',
				cls:'next naviBtn',
				items:[
					{
					xtype:'button',
					columnWidth:1,
					text:'>',
					handler:function(){
						var totalCnt = me.getStore().getTotalCount();
						var pageSize = me.getStore().pageSize;
						var curPage = me.getStore().currentPage;
						var totalPage = parseInt((totalCnt-1)/pageSize) + 1;
						if(curPage >= totalPage){
							return false;
						}else{
							var _code = me.getStore().xwin.dock._lastCode;
							var param = {};
							Ext.apply(param,me._pageParam);
							me.getStore().nextPage({params:param});
					}
				}
					}
		 		]
		 	});
		 var contentPanel =Ext.create('Ext.panel.Panel', {
					xtype:'panel',
					cls:'content',
					region: 'center',
					layout:{
						type:'table',
						columns:4
					}
		 });
		 this.items=[preBtn,contentPanel,nextBtn];
		 this.prePanel = preBtn;
		 this.nextPanel = nextBtn;
		 this.contentPanel = contentPanel;
		 this.callParent();
	 },
	 constructor:function(config){ 
		 	var me = this, cfg = Ext.apply({}, config);
		 	me.callParent([cfg]);
	 }
});

//下拉列表查询store
Ext.define('Rosefinch.ECS.CitySearchStore', {
		extend:'Ext.data.Store',
		model : 'Rosefinch.ECS.AddressModel',
		autoLoad : false,
		dock:null,
		proxy : {
			sync:false,
			type : 'ajax',
			url : '../basedev/getCityVoListByPingyingAndHanzi.do',
			actionMethods : 'post',// 否则可能会出现中文乱码
			reader : {
				type : 'json',
				root : 'list'
			}
		},
		listeners:{
			load:function(store,record){
				if(this.dock == null){
					return ;
				}
				if(this.dock.xwin != null){
					this.dock.xwin.close();
				}
			}
		},
		constructor:function(config){
			var cfg = {} || config;
			this.dock = cfg.dock || null;
			this.callParent([cfg]);
		}
	});

//常用城市面板的store
Ext.define('Rosefinch.ECS.AddressHotCityStore', {
	extend:'Ext.data.Store',
	model :'Rosefinch.ECS.AddressModel',
	xwin:null,
	autoLoad:false,
	proxy : {
		type : 'ajax',
		url :  '../basedev/geVoListByQueryPageVo.do',
		method:'post',
		reader : {
			type : 'json',
			root : 'list',
			totalProperty:'count'
		}
	} ,
	listeners:{
		beforeload : function(store, operation, eOpts) {	
			Ext.apply(operation, {
				params : {
					'isHot' : 1,
					'regionLevel' : 'CITY',
					'regionParent':null
				}
			});	
		},
		load:function(store,record){
	        var me = this;
	        var hotPannel = me.xwin.tabPanel.getHotCityPanel().getContentPanel();
	        hotPannel.removeAll();
			for(var i=0;i<record.length;i++){
				hotPannel.add({
					code:record[i].data.code,
					parentName:record[i].data.parentDistrictName,
					parentCode:record[i].data.parentDistrictCode,
					record:record[i],
					btnText:record[i].data.name,
	            	xtype:'button',
	            	width:70,
	            	text:getButtonName(record[i].data.name),
	            	handler : function(button,e) {
	            		button.up().items.each(function(btn){
	            			btn.removeClass('yellow_button');
	            		});
	            		button.addClass('yellow_button');
	            		var target = me.xwin.dock;
	            		var win = me.xwin;
	            		target.city = button.btnText;
	            		target.cityCode = button.code;
	            		target.province = button.parentName;
	            		target.provinceCode = button.parentCode;
	            		target.area = null;
	            		target.areaCode = null;
	            		target.street = null;
	            		target.streetCode = null;
	            		//显示hotCity panel
	            	
	            		//设置自动切换页签
	            		//每次都需要将上次界面清空
	            		/*if(Ext.isEmpty(Ext.data.StoreManager.lookup(win.getHotCityPanel().getStore())){
	            			new areaStore({'storeId':id+'AreaStore','tab':me.tab});
	            		}*/
	            		var areaStore = win.tabPanel.getAreaPanel().getStore();
	            		var areaPanel = win.tabPanel.getAreaPanel();
	            		var contentPanel = areaPanel.getContentPanel();
	            		var pageSize = areaPanel.blockCnt;
	            		if(areaStore == null){
	            			areaStore = areaPanel.store = Ext.create("Rosefinch.ECS.AddressAreaStore",{pageSize:pageSize,xwin:win});	
	            		}
	            		contentPanel.removeAll();
	            		areaPanel._pageParam = {'regionParent':button.code};
	            		var model = Ext.create("Rosefinch.ECS.AddressModel");
	            		model.data.code = target.cityCode,
	            		model.data.name = target.province+target.spliter+target.city;
	            		target.setValue(model);
	            		target._lastCode = button.code;
	            		win.tabPanel.activeAreaPanel();
	            		areaPanel.store.load({params:{'start':0,'limit':pageSize,'regionParent':button.code}});
	            		
	            	}
	            });
			}
		}
	},
	constructor:function(config){
		var cfg = {};
		cfg = Ext.apply(cfg,config);
		this.xwin = cfg.xwin || null;
		this.callParent([cfg]);
	
	}
});	
//省份信息store	
Ext.define('Rosefinch.ECS.AddressProvinceStore', {
					extend:'Ext.data.Store',
					model : 'Rosefinch.ECS.AddressModel',
					xwin:null,
					proxy : {
						type : 'ajax',
						url : '../basedev/geVoListByQueryPageVo.do',
						actionMethods : 'post',// 否则可能会出现中文乱码
						reader : {
							type : 'json',
							root : 'list',
							totalProperty:'count'
						}
					},
					listeners:{
						beforeload : function(store, operation, eOpts) {	
							Ext.apply(operation, {
								params : {
									'regionLevel' : 'PROVINCE'
								}
							});	
						},
						load:function(store,record){
							var me = this;
							var container = me.xwin.tabPanel.getProvincePanel().getContentPanel();
							container.removeAll();
							for(var i= 0 ; i < record.length ; i++){
								container.add({
									code:record[i].data.code,
					            	xtype:'button',
					            	width:70,
					            	record:record[i],
					            	btnText:record[i].data.name,
					            	tooltipType:'title',
					            	tooltip:record[i].data.name,
					            	text:getButtonName(record[i].data.name),
					            	handler : function(button,e) {
					            		button.up().items.each(function(btn){
					            			btn.removeClass('yellow_button');
					            		});
					            		button.addClass('yellow_button');
					            		var xwin = me.xwin;
					            		var target = me.xwin.dock;
					            		//设置自动切换页签
					            		target.province = button.btnText;
					            		target.provinceCode = button.code;
					            		target.city = null;
					            		target.cityCode = null;
					            		target.area = null;
					            		target.areaCode = null;
					            		target.street = null;
					            		target.streetCode = null;
					            		var cityPanel = xwin.tabPanel.getCityPanel();
					            		var pageSize = cityPanel.blockCnt;
					            		//每次都需要将上次界面清空
					            		if(cityPanel.store == null){
					            			cityPanel.store = Ext.create("Rosefinch.ECS.AddressCityStore",{pageSize:pageSize,xwin:xwin});
					            		}	
					            		var contentPanel  = cityPanel.getContentPanel();
					            		contentPanel.removeAll();
					            		cityPanel._pageParam = {'regionParent':button.code};
						            		var model = Ext.create("Rosefinch.ECS.AddressModel");
						            		model.data.code = target.provinceCode,
						            		model.data.name = target.province;						           
						            		target.setValue(model);
						            		target._lastCode = button.code;
						            		xwin.tabPanel.activeCityPanel();
					            		cityPanel.store.load({params:{'regionParent':button.code,
					            			start:0,limit:pageSize}});
					            		
					            	}
								}
					           );
							 }
							
						}
					},
					constructor:function(config){
						var cfg = {};
						cfg = Ext.apply(cfg,config);
						this.xwin = cfg.xwin || null;
						this.callParent([cfg]);
					
					}
		});
					
				
				
	
//城市面板
//城市面板store,  城市面板是根据所选省份带出来的	
Ext.define('Rosefinch.ECS.AddressCityStore', {
	extend:'Ext.data.Store',
	model : 'Rosefinch.ECS.AddressModel',
	xwin:null,
	autoLoad : false,
	proxy : {
		type : 'ajax',
		url :'../basedev/geVoListByQueryPageVo.do',
		actionMethods : 'post',// 否则可能会出现中文乱码
		reader : {
			type : 'json',
			root : 'list',
			totalProperty:'count'
		}
	},
	listeners:{
		load:function(store,record){
			var me = this;
			var areaPanel = me.xwin.tabPanel.getAreaPanel();
    		var target = me.xwin.dock;
			if(record.length <= 0){
        		me.xwin.close();
        		return;
			}
			target._lastCode = null;
			//动态将后台查询的数据，绘制到面板上
			var container = me.xwin.tabPanel.getCityPanel().getContentPanel();
			container.removeAll();
			for(var i=0;i<record.length;i++){
				container.add({
					code:record[i].data.code,
					parentName:record[i].data.parentDistrictName,
					parentCode:record[i].data.parentDistrictCode,
	            	xtype:'button',
	            	width:70,
	            	record:record[i],
	            	btnText:record[i].data.name,
	            	tooltipType:'title',
	            	tooltip:record[i].data.name,
	            	text:getButtonName(record[i].data.name),
	            	handler : function(button,e) {
	            		button.up().items.each(function(btn){
	            			btn.removeClass('yellow_button');
	            		});
	            		button.addClass('yellow_button');
	            		//设置自动切换页签
	            		var areaStore = areaPanel.getStore();
	            		var pageSize = areaPanel.blockCnt;
	            		target.province = button.parentName;
	            		target.provinceCode = button.parentCode;
	            		target.city = button.btnText;
	            		target.cityCode = button.code;
	            		target.area = null;
	            		target.areaCode = null;
	            		target.street = null;
	            		target.streetCode = null;
	            		if(areaStore == null){
	            			areaStore = areaPanel.store = Ext.create("Rosefinch.ECS.AddressAreaStore",{'pageSize':pageSize,xwin:me.xwin});
	            		}
	            	/**/
	    				//点击具体城市需要自动切换到其对应的区域上，需要将区域面板清空，重新绘制具体区域
	            		areaPanel.getContentPanel().removeAll();
	            		
	            		//同时需要将下拉框的值清空，将城市面板所有数据给下拉框store，同时让下拉框显示当前所选城市的名称
	            		areaPanel._pageParam = {'regionParent':button.code};
	            		var model = Ext.create("Rosefinch.ECS.AddressModel");
	            		model.data.code = target.cityCode ;
	            		model.data.name = target.province+target.spliter+target.city;
	            		target.setValue(model);
	            		target._lastCode = button.code;
	            		me.xwin.tabPanel.activeAreaPanel();
	            		//根据所点击的城市id，查询具体区域，在查询时，会在区域的load事件中绘制区域面板
	            		
	            		areaStore.load({params:{'regionParent':button.code,
	            			'start':0,'limit':pageSize}});
	            		
	            	}
	            });
			}
		}
	},
	constructor:function(config){
		var cfg = {};
		cfg = Ext.apply(cfg,config);
		this.xwin = cfg.xwin || null;
		this.callParent([cfg]);
	}
});



//区域面板的store
Ext.define('Rosefinch.ECS.AddressAreaStore', {
	extend:'Ext.data.Store',
	model : 'Rosefinch.ECS.AddressModel',
	xwin:null,
	autoLoad : false,
	proxy : {
		type : 'ajax',
		url :  '../basedev/geVoListByQueryPageVo.do',
		method:'post',
		reader : {
			type : 'json',
			root : 'list',
			totalProperty:'count'
		}
	},
	listeners:{
		load:function(store,record){
			var me = this;
			var container = me.xwin.tabPanel.getAreaPanel().getContentPanel();
			var win = me.xwin;
    		var showStreet = me.xwin.dock.showStreet;
    		var target = win.dock;
    		if(record.length <=0){
        		win.close();
        		return;
    		}
    		
    		target._lastCode = null;
    		container.removeAll();
				for(var i=0;i<record.length;i++){
					container.add({
						code:record[i].data.code,
						parentCode:record[i].data.parentDistrictCode,
		            	xtype:'button',
		            	record:record[i],
		            	btnText:record[i].data.name,
		            	width:70,
		            	tooltipType:'title',
		            	tooltip:record[i].data.name,
		            	text:getButtonName(record[i].data.name),
		            	handler : function(button,e) {
		            		button.up().items.each(function(btn){
		            			btn.removeClass('yellow_button');
		            		});
		            		button.addClass('yellow_button');
		            		target.street = null;
		            		target.streetCode = null;
		            		target.area = button.btnText;
		            		target.areaCode = button.code;
		            		var val = target.province + target.spliter + target.city + target.spliter + target.area;
		            		var valCode = target.areaCode;
		            		//Ext.getCmp(id).setValue(val); 
		            		if(showStreet == false || showStreet == null){//不展示街道面板
		            			var nR = {};
			            		Ext.apply(nR,button.record);
			            		nR.data.name= val;
			            		nR.data.code = valCode;
			            		target._lastCode = button.code;
			            		target.setValue(nR);
			            		win.close();
			            		 
		            		}else{
		            			var streetPanel = win.tabPanel.getStreetPanel();
		            			var stStore = streetPanel.getStore();
		            			var pageSize = streetPanel.blockCnt;
		            			if(stStore == null){
		            			   stStore =  streetPanel.store = Ext.create("Rosefinch.ECS.AddressStreetStore",{'pageSize':pageSize,'xwin':win});
		            			}
		            			streetPanel.getContentPanel().removeAll();
		            			streetPanel._pageParam = {'regionParent':button.code};
		            			var model = Ext.create("Rosefinch.ECS.AddressModel");
			            		model.data.code = valCode,
			            		model.data.name = val;
			            		target.setValue(model);
		            			target._lastCode = button.code;
		            			win.tabPanel.activeStreetPanel();
		            			stStore.load({params:{'regionParent':button.code,
			            			'start':0,'limit':pageSize}});
		            			
		            			
		            		}
		            	
		            	
						}
					});
				}
			
			
		}
	},
	constructor:function(config){
		var cfg = {};
		cfg = Ext.apply(cfg,config);
		this.xwin = cfg.xwin || null;
		this.callParent([cfg]);
	}
});


//区域面板的store
Ext.define('Rosefinch.ECS.AddressStreetStore', {
	extend:'Ext.data.Store',
	model : 'Rosefinch.ECS.AddressModel',
	xwin:null,
	autoLoad : false,
	proxy : {
		type : 'ajax',
		url :  '../basedev/geVoListByQueryPageVo.do',
		method:'post',
		reader : {
			type : 'json',
			root : 'list',
			totalProperty:'count'
		}
	},
	listeners:{
		load:function(store,record){
			var me = this;
			var target = me.xwin.dock;
			if(record.length <= 0){
        		me.xwin.close();
        		return ;
			}
			var container = me.xwin.tabPanel.getStreetPanel().getContentPanel();
			container.removeAll();
			for(var i=0;i<record.length;i++){
				container.add({
					code:record[i].data.code,
					parentCode:record[i].data.parentDistrictCode,
	            	xtype:'button',
	            	record:record[i],
	            	btnText:record[i].data.name,
	            	width:70,
	            	tooltipType:'title',
	            	tooltip:record[i].data.name,
	            	text:getButtonName(record[i].data.name),
	            	handler : function(button,e) {
	            		button.up().items.each(function(btn){
	            			btn.removeClass('yellow_button');
	            		});
	            		button.addClass('yellow_button');
	            		
	            		target.street = button.btnText;
	            		target.streetCode = button.code;
	            		var val = target.province + target.spliter + target.city + target.spliter + target.area + target.spliter + target.street;
	            		var valCode =target.streetCode;
	            		var nR = {};
	            		Ext.apply(nR,button.record);
	            		nR.data.code = valCode;
            			nR.data.name = val;
	            		target.setValue(nR);
	            		target._lastCode = button.code;
	            		me.xwin.close();
					}
				});
			}
		}
	},
	constructor:function(config){
		var cfg = {};
		cfg = Ext.apply(cfg,config);
		this.xwin = cfg.xwin || null;
		this.callParent([cfg]);
	}
});



	//定义省市区页签
 Ext.define('Rosefinch.ECS.AreaTabPanel', {
	extend:'Ext.tab.Panel',
	activeTab : 0,
	xwin:null,
	//热门城市
	hotCityText:' 热门城市 ',
	//省
	provinceText:' 省份 ',
	//市
	cityText:' 城市 ',
	//区
	areaText:' 区/县 ',
	//街道
	streetText: ' 乡镇/街道 ',
	//热门城市面板
	hotCityPanel:null,
	//省份面板
	provincePanel:null,
	//城市面板
	cityPanel:null,
	//区域面板
	areaPanel:null,
	//街道面板
	streetPanel:null,
	
	listeners:{
		'beforetabchange':function(panel,newCard,oldCard){
			//未打开上级情况下，禁止打开下级选项卡
			if(newCard == this.getCityPanel() && this.xwin.dock.provinceCode == null){
				return false;
			}else if(newCard == this.getAreaPanel() && this.xwin.dock.cityCode == null){
				return false;
			}else if(newCard == this.getStreetPanel() && this.xwin.dock.areaCode == null){
				return false;
			}
				
		}
	},
	
	getHotCityPanel:function(){
		return this.hotCityPanel;
	},
	getProvincePanel:function(){
		return this.provincePanel;
	},
	getCityPanel:function(){
		return this.cityPanel;
	},
	getAreaPanel:function(){
		return this.areaPanel;
	},
	getStreetPanel:function(){
		return this.streetPanel;
	},
	activeAreaPanel:function(){
		if(this.areaPanel != null){
			this.setActiveTab(this.areaPanel);
		}else{
			Ext.Error.raise("Rosefinch.ECS.AreaTabPanel:could not acive areaPanel……");
		}
	},
	activeHotCityPanel:function(){
		if(this.hotCityPanel != null){
			this.setActiveTab(this.hotCityPanel);
		}else{
			Ext.Error.raise("Rosefinch.ECS.AreaTabPanel:could not acive hotCityPanel……");
		}
	},
	activeProvincePanel:function(){
		if(this.provincePanel != null){
			this.setActiveTab(this.provincePanel);
		}else{
			Ext.Error.raise("Rosefinch.ECS.AreaTabPanel:could not acive provincePanel……");
		}
	},
	activeCityPanel:function(){
		if(this.hotCityPanel != null){
			this.setActiveTab(this.cityPanel);
		}else{
			Ext.Error.raise("Rosefinch.ECS.AreaTabPanel:could not acive cityPanel……");
		}
	},
	activeStreetPanel:function(){
		//如果上级未打开，禁止打开下级
		if(this.streetPanel != null){
			this.setActiveTab(this.streetPanel);
		}else{
			Ext.Error.raise("Rosefinch.ECS.AreaTabPanel:could not acive streePanel……");
		}
	},
	getItems:function(){
		var me = this;
		var arrays = new Array();
		//常用页签
		var hotCityPanel = Ext.create("Rosefinch.ECS.AddressPanel",{
				title : me.hotCityText,
				width:550,
				height:235,
				//默认激活时候，给该页签加载数据到面板上，绘制面板
				listeners:{
					activate:function(){
						if(this.store == null){
							this.store = Ext.create("Rosefinch.ECS.AddressHotCityStore",{
								xwin:me.xwin,pageSize:this.blockCnt
							});
						}
						
						if(this.store.data.length == 0){
							this.store.load({'start':0,'limit':this.blockCnt});
							this._pageParam = {};
						}
					}
				}
			});
		this.hotCityPanel = hotCityPanel;
		//省份页签	
		var provincePanel = Ext.create("Rosefinch.ECS.AddressPanel",{
				title : me.provinceText,
				width:550,
				height:235,
				xwin:me.xwin,
				//默认激活时候，给该页签加载数据到面板上，绘制面板
				 listeners:{
					 activate:function(){
							if(this.store == null){
								this.store = Ext.create("Rosefinch.ECS.AddressProvinceStore",{
									xwin:me.xwin,pageSize:this.blockCnt
								});
							}
							if(this.store.data.length == 0){
								this.store.load({'start':0,'limit':this.blockCnt});
								this._pageParam = {};
							}
						}
				} 
			});
		this.provincePanel = provincePanel;
		arrays.push(hotCityPanel);
		arrays.push(provincePanel);
		
		//城市页签	
		var cityPanel = Ext.create("Rosefinch.ECS.AddressPanel",{
				title : me.cityText,
				width:550,
				xwin:me.xwin,
				height:235,
				 listeners:{
					
				} 
			});
		arrays.push(cityPanel);
		this.cityPanel = cityPanel;
		//区域页签	
		var areaPanel = Ext.create("Rosefinch.ECS.AddressPanel",{
				title : me.areaText,
				xwin:me.xwin,
				width:550,
				height:235,
				listeners:{
					 
				} 
			});
		arrays.push(areaPanel);
		this.areaPanel = areaPanel;
		var streetPanel = Ext.create("Rosefinch.ECS.AddressPanel",{
				title : me.streetText,
				xwin:me.xwin,
				width:550,
				height:235,
				beforeactivate:function(){
					
				}
			});
		
		//此处主要是根据外面传递进来的tabPanel参数，来控制显示面板个数，如只显示省份等	
		if(this.xwin.dock.showStreet){
			arrays.push(streetPanel);
			this.streetPanel = streetPanel;
		}
		return arrays;
	},
	initComponent:function(){
    	var me = this;
    	me.items = me.getItems();
    	this.callParent();
   },
   constructor:function(config){
		var cfg = {};
		cfg = Ext.apply(cfg,config);
		this.xwin = cfg.xwin || null;
		this.callParent([cfg]);
	}
});
 
 
	//弹出省市区页签的窗口
 Ext.define('Rosefinch.ECS.AreaTabWindow',{
 	extend:'Ext.window.Window',
 	width : 550,
		height : 285,
		cls:'provincepanel',
		//目标组件，即window所依附组件
		dock:null,
		//tab面板
		tabPanel:null,
		resizable:false,
		draggable:false,
		closeAction:'hidden',
		initComponent:function(){
	    	var me = this;
	    	this.tabPanel = Ext.create('Rosefinch.ECS.AreaTabPanel',{
				'xwin':me
			});
	    	me.items = [this.tabPanel];
	    	this.callParent();
		},
		constructor:function(config){
			var me = this;
			me.dock = config.dock;
			this.callParent([config]);
		}
 });
 
 
 Ext.define('Rosefinch.ECS.AddressSelector',{
		extend : 'Ext.form.ComboBox',
		alias : ['widget.addressselector','widget.addressSelector','widget.areaaddresscombox'],
		alternateClassName:['Ecs.admin.AreaAddressCombox'],
		//弹出窗口
		xwin:null,
		//是否显示街道
		showStreet:false,
		//width : 220,
		spliter:'-',
		fieldLabel :' Area ',
		labelWidth : 40,
		labelSeparator:'',//剔除“：”
		labelPad:1,
		labelAlign:'left',
		readOnly:false,
		queryMode : 'remote',
		queryParam : 'name',// 指定向后台传参数的变量名称。Extjs4默认为query;
		emptyText : '请输入城市名(中文/拼音)',
		selectedValue:null,//改字段主要是针对查询和修改时，传递到
		triggerAction : 'all',
		minChars : 1,//输入1个字节，就开始模糊查询
		hideTrigger : true,
		forceSelection : true,
		name : 'queryParam',
		displayField : 'name',
		typeAhead : false,
		province:null,//省
		city:null,//市
		area:null,//区域
		street:null,
		provinceCode:null,
		cityCode:null,
		areaCode:null,
		streetCode:null,
		valueField : 'code',
		//保存当前Code；
		_lastCode:null,
		getStore:function(){
			var me = this;
			var st = Ext.create("Rosefinch.ECS.CitySearchStore",{'dock':me});
			return st;
		},
		initComponent:function(){
	    	var me = this;
	    	me.xwin = Ext.create('Rosefinch.ECS.AreaTabWindow',{dock:me});
	    	this.callParent();
	   },
	   constructor:function(config){
		   var cfg = {};
		   var me = this;
		   Ext.apply(cfg,config);
		   me.store = me.getStore();
		   this.showStreet = cfg.showStreet || false;
		   this.callParent([cfg]);
	   },
	   //设置value（格式： {"code":"310000-310100-310106","name":"上海-上海市-静安区"}）
	   setRegionValue:function(object){
		   if(Ext.isEmpty(object)){
			   return;
		   }
		   if(Ext.isEmpty(object.code)){
			   return;
		   }
		   if(Ext.isEmpty(object.name)){
			   return;
		   }
		   var codes = object.code.split(this.spliter);//获取code
		   var names = object.name.split(this.spliter);//获取name
		   if(codes.length==0||names.length==0||codes.length!=names.length){
			   return;
		   }
		   if(codes.length==1){
			   this.province = names[0];
			   this.provinceCode = codes[0];
			   var record =  new Rosefinch.ECS.AddressModel({"name":object.name,"code":this.provinceCode});
			   this.setValue(record);
		   }else if(codes.length==2){
			   this.province = names[0];
			   this.provinceCode = codes[0];
			   this.city = names[1];
			   this.cityCode = codes[1];
			   var record =  new Rosefinch.ECS.AddressModel({"name":object.name,"code":this.cityCode});
			   this.setValue(record);
		   }else if(codes.length==3){
			   this.province = names[0];
			   this.provinceCode = codes[0];
			   this.city = names[1];
			   this.cityCode = codes[1];
			   this.area = names[2];
			   this.areaCode = codes[2];
			   var record =  new Rosefinch.ECS.AddressModel({"name":object.name,"code":this.areaCode});
			   this.setValue(record);
		   }else{
			   this.province = names[0];
			   this.provinceCode = codes[0];
			   this.city = names[1];
			   this.cityCode = codes[1];
			   this.area = names[2];
			   this.areaCode = codes[2];
			   this.street = names[3];
			   this.streetCode = codes[3];
			   var record =  new Rosefinch.ECS.AddressModel({"name":object.name,"code":this.streetCode});
			   this.setValue(record);
		   }
	   },
		listeners : {
			'focus' : function(th) {
				th.xwin.show();
				th.xwin.alignTo(th);
				th.xwin.getEl().on('mouseleave',function(){
					th.xwin.close();
					th.blur();
				});
				
			},
			'click':function(th){
				th.xwin.show();
				th.xwin.alignTo(th);
				th.xwin.getEl().on('mouseleave',function(){
					th.xwin.close();
				});
			},
			//下拉空输入查询时，关闭省市区面板
			'change' : function(th, newValue, oldValue, eOpts) {
				/*if(newValue != '' && newValue != null){
					if(th.xwin != null){
						th.xwin.close();
					}
				}*/
			},
			//选择城市时，会自动弹出区域面板，供选择
			'select':function(combo,records,eOpts){
				var me = this;
				me.city = records[0].data.name;
				me.cityCode = records[0].data.code;
				me.provinceCode=records[0].data.parentDistrictCode;
				me.province = records[0].data.parentDistrictName;
				me.area = null;
				me.areaCode = null;
				me.street = null;
				me.streetCode = null;
				me._lastCode = records[0].data.code;
				
				var tabPan = me.xwin.tabPanel; 
				
				tabPan.getAreaPanel().getContentPanel().removeAll();
				var pageSize = tabPan.getAreaPanel().blockCnt;
	    		if(tabPan.getAreaPanel().getStore() == null){
	    			tabPan.getAreaPanel().store = Ext.create("Rosefinch.ECS.AddressAreaStore",{'xwin':me.xwin,'pageSize':pageSize});
	    		}
	    		
	    		tabPan.getAreaPanel().store.load({params:{'regionParent':records[0].data.code,
     			'start':1,'limit':pageSize}});
	    		tabPan.getAreaPanel()._pageParam = {'regionParent':records[0].data.code};
	    		var val = me.province+me.spliter+me.city;
	    		var nR = {};
	    		Ext.apply(nR,records[0]);
	    		nR.data.name= val;
	    		//
	    		nR.data.code =me.cityCode;
	    		me.setValue(nR);
	    		me.xwin.show();
	    		tabPan.activeAreaPanel();
			},
			expand:function(combo, record, index){
				if(combo.xwin != null){
					combo.xwin.close();
				}
			},
			//改方法主要是在渲染完后，针对查询和修改时，需要将值填充到下拉空面板中
			render:function(){

			}
		}
	});