basedev.baseRoute.TAB_ID="T_basedev-baseRouteIndex";  // 路由维护标签页ID
basedev.baseRoute.CONTENT_ID = "T_basedev-baseRouteIndex_content";  // 路由内容panel ID

basedev.baseRoute.QUERY_BASE_ROUTE_FORM_ID = "T_basedev-querybaseRouteForm";// 路由查询表单   

basedev.baseRoute.QUERY_BASE_ROUTE_RESULT_GRID_ID = "T_basedev-querybaseRouteResultGrid";// 路由列表

basedev.baseRoute.STATE_ADD = 'add';   // 新增
basedev.baseRoute.STATE_UPDATE = 'update';   // 修改

basedev.baseRoute.BASE_ROUTE_FORM_ID = "T_basedev-baseRouteForm";
basedev.baseRoute.EDIT_BASE_ROUTE_FORM_ID = "T_basedev-editbaseRouteForm";//编辑路由表单

basedev.baseRoute.QUERY_BASE_ROUTE_DETAIL_RESULT_GRID_ID = "T_basedev-querybaseRouteDetailGrid";//编辑明细表格

basedev.baseRoute.EDIT_BASE_ROUTE_DETAIL_RESULT_FORM_ID = "T_basedev-editbaseRouteDetailGrid";//编辑明细表格

basedev.baseRoute.BASE_ROUTE_DETAIL_RESULT_GRID_ID = 'T_basedev-baseRouteDetailGrid';//路由明细查看

basedev.baseRoute.VIATYPE_BEGIN = 1;//起点路由
basedev.baseRoute.VIATYPE_THROUGH = 2;//途经路由
basedev.baseRoute.VIATYPE_END = 3;//终点路由

basedev.baseRoute.currSiteCode = '';
basedev.baseRoute.captureFlag = false;

//拦截函数  
basedev.baseRoute.captureFunction = function(eventName){  
    if(eventName == 'change' && basedev.baseRoute.captureFlag){//事件名称是change则返回false终止事件的执行  
        return false;  
    }  
    return true;  
}

basedev.baseRoute.addRouteSite = function(siteName, siteCode, viaType, viaTypeName){
    var baseRouteDetailStore = Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_DETAIL_RESULT_GRID_ID).store;
    var index = baseRouteDetailStore.find('viaType',viaType);
    if(index == -1){
    	var record = {'rownum':1,'currSiteCode':siteCode,'currSiteName':siteName,'viaType':viaType, 'viaTypeName': viaTypeName, 'arriveMileage': 0, 'arriveTime':0, 'stayTime': 0};
    	if(viaType == basedev.baseRoute.VIATYPE_BEGIN){
    	   baseRouteDetailStore.insert(0,record);
    	   //刷新序号
    	   Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_DETAIL_RESULT_GRID_ID).getView().refresh();
    	} else {
    	   baseRouteDetailStore.add(record);
    	}
    } else {
        baseRouteDetailStore.getAt(index).set("currSiteCode", siteCode);
        baseRouteDetailStore.getAt(index).set("currSiteName", siteName);    
    }
}

basedev.baseRoute.addRouteRegion = function(regionName, regionCode, viaType, viaTypeName){
    var baseRouteDetailStore = Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_DETAIL_RESULT_GRID_ID).store;
    var index = baseRouteDetailStore.find('viaType',viaType);
    if(index == -1){
    	var record = {'rownum':1,'currRegionCode':regionCode,'currRegionName':regionName,'viaType':viaType, 'viaTypeName': viaTypeName, 'arriveMileage': 0, 'arriveTime':0, 'stayTime': 0}
        if(viaType == basedev.baseRoute.VIATYPE_BEGIN){
            baseRouteDetailStore.insert(0,record);
            //刷新序号
            Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_DETAIL_RESULT_GRID_ID).getView().refresh();
        } else {
            baseRouteDetailStore.add(record);
        }
    } else {
        baseRouteDetailStore.getAt(index).set("currRegionCode", regionCode);
        baseRouteDetailStore.getAt(index).set("currRegionName", regionName);    
    }
}

basedev.baseRoute.getRegionCodeStr = function(currRegion){
    //拼接行政区域code
    var currRegionCodeStr = "";
    if(currRegion.getValue()){
        if(currRegion.provinceCode){
            currRegionCodeStr = currRegion.provinceCode;
        }
        if(currRegion.cityCode){
            currRegionCodeStr += "-" + currRegion.cityCode;
        }
        if(currRegion.areaCode){
            currRegionCodeStr += "-" + currRegion.areaCode;
        }
    }
    return currRegionCodeStr;
}

basedev.baseRoute.BaseRouteDetailVo = function(viaType, currSiteCode, currRegionCode, arriveMileage, arriveTime, stayTime, classType, remark, nextSiteCode, nextRegionCode, orderBy) {
    this.viaType = viaType;
    this.currSiteCode = currSiteCode;
    this.currRegionCode = currRegionCode;
    this.arriveMileage = arriveMileage;
    this.arriveTime = arriveTime;
    this.stayTime = stayTime;
    this.classType = classType;
    this.remark = remark;
    this.nextSiteCode = nextSiteCode;
    this.nextRegionCode = nextRegionCode;
    this.orderBy = orderBy;
}

basedev.baseRoute.getTimeStr = function(minutes){
	if(minutes<60){
	   return minutes + "分";
	} else {
		var timeStr = Math.floor(minutes/60) + "小时";
		if(minutes%60 !=0){
		  timeStr += (minutes%60) + "分"  
		}
		return timeStr;
	}
}

/**
 * 查询条件
 */
Ext.define('Basedev.baseRoute.QuerybaseRouteForm',{
	extend:'Ext.form.Panel',
	id : basedev.baseRoute.QUERY_BASE_ROUTE_FORM_ID,
	frame : true,
	title: '查询条件',
	layout : {
	   type: 'table',
	   columns : 3,
	   tableAttrs : {
            style : {
                width : '95%'
            }
        }
	},
	defaults : {
        margin : '10 20 10 10',
        labelWidth : 100,
        width: 280
    },
	defaultType : 'textfield',
	initComponent: function(){
		var me = this;
		me.items = [{
    		name: 'routeName',
    		fieldLabel: '路由名称'
    	}, {
    		xtype : 'commonSiteSelector',
            name : 'beginSiteCode',
            fieldLabel : '起始转运/分拨中心',
            labelWidth : 120,
            siteType : '5,6'
    	}, {
            xtype : 'commonSiteSelector',
            name : 'endSiteCode',
            fieldLabel : '目的转运/分拨中心',
            labelWidth : 120,
            siteType : '5,6'
        }, {
            xtype : 'commonProductSelector',
            name : 'productCode',
            fieldLabel : '产品类型'
        }, {
            xtype : 'addressselector',
            name : 'beginRegionCode',
            fieldLabel : '起始行政区域',
            labelWidth : 120,
            labelPad: 5,
            labelSeparator:':'
            
        }, {
            xtype : 'addressselector',
            name : 'endRegionCode',
            fieldLabel : '到达行政区域',
            labelWidth : 120,
            labelPad: 5,
            labelSeparator:':'
        }];
		me.buttons = [{
                text : '重置',
                width : 100,
                handler : function() {
                    var queryFrom = this.up('form').getForm();

                    // 重置
                    queryFrom.reset();
                }
            }, {
                cls : 'yellow_button',
                text : '查询',
                width : 100,
                handler : function() {
                    var queryFrom = this.up('form').getForm();
                    if (!queryFrom.isValid()) {
                        return;
                    }

                    var selectResultPanel = Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_RESULT_GRID_ID);
                    selectResultPanel.setVisible(true);
                    selectResultPanel.getPagingToolbar().moveFirst();
                }
            }],
		me.callParent();
	}
});

/**
 * 路由model
 */
Ext.define('Basedev.baseRoute.baseRouteModel', {
	extend : 'Ext.data.Model',
	fields : [{
		name : 'id',
		type : 'string'
	}, {
        name : 'routeCode',//路由编号
        type : 'string'
    }, {
		name : 'routeName',//路由名称
		type : 'string'
	}, {
		name : 'productCode',//产品类型编码
		type : 'string'
	}, {
		name : 'productName',//产品类型名称
		type : 'string'
	}, {
		name : 'beginSiteCode'//起始转运/分拨中心
	}, {
    	name : 'beginSiteName',//起始转运/分拨中心名称
    	type : 'string'
	}, {
		name : 'endSiteCode'//目的转运/分拨中心
	}, {
		name : 'endSiteName',//目的转运/分拨中心名称
		type : 'string'
	}, {
	   name : 'beginRegionCode'//起始行政区域编码
	}, {
	   name : 'beginRegionName',//起始行政区域名称
	   type : 'string'
	}, {
       name : 'beginRegionCodeStr',//起始行政区域编码
       type : 'string'
    }, {
	   name : 'endRegionCode'//到达行政区域编码
	}, {
	   name : 'endRegionName',//到达行政区域名称
	   type : 'string'
	},  {
       name : 'endRegionCodeStr',//到达行政区域编码
       type : 'string'
    }, {
	   name : 'ownerOrg'//路由所属机构
	}, {
	   name : 'ownerOrgName',//路由所属机构名称
	   type : 'string'
	}, {
	   name : 'totalStayTime',//总停留时间
	   type : 'string'
	}, {
	   name : 'totalMileage',//总路程
	   type : 'string'
	}, {
	   name : 'siteCount',//途经总数
	   type : 'string'
	}, {
	   name : 'totalTime',//总时间
	   type : 'string'
	}, {
	   name : 'blFlag',//启用状态
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
	}]
});

/**
 * 路由明细model
 */
Ext.define('Basedev.baseRoute.baseRouteDetailModel', {
    extend : 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    }, {
        name : 'routeId',//路由Id
        type : 'string'
    }, {
        name : 'orderBy',//途经排序位置
        type : 'string'
    }, {
        name : 'viaType',//途经类型（1：起点路由   2：途经路由   3：终点路由）
        type : 'string'
    }, {
        name : 'viaTypeName',//途经类型名称
        type : 'string'
    }, {
        name : 'currSiteCode'//起始转运/分拨中心
    }, {
        name : 'currSiteName',//起始转运/分拨中心名称
        type : 'string'
    }, {
        name : 'nextSiteCode'//目的转运/分拨中心
    }, {
        name : 'nextSiteName',//目的转运/分拨中心名称
        type : 'string'
    }, {
       name : 'currRegionCode'//起始行政区域编码
    }, {
       name : 'currRegionName',//起始行政区域名称
       type : 'string'
    }, {
       name : 'currRegionCodeStr',//起始行政区域编码
       type : 'string'
    }, {
       name : 'nextRegionCode'//到达行政区域编码
    }, {
       name : 'nextRegionName',//到达行政区域名称
       type : 'string'
    },  {
       name : 'nextRegionCodeStr',//到达行政区域编码
       type : 'string'
    }, {
       name : 'stayTime',//停留时间
       type : 'string'
    }, {
       name : 'arriveMileage',//行驶里程
       type : 'string'
    }, {
       name : 'arriveTime',//驶到用时
       type : 'string'
    }, {
       name : 'classType',//运输方式
       type : 'string'
    }, {
       name : 'classTypeName',//运输方式名
       type : 'string'
    }, {
        name: 'remark',//备注
        type: 'string'
    }]
});


/**
 * 路由Store
 */
Ext.define('Basedev.baseRoute.baseRouteStore',{
	extend:'Ext.data.Store',
	model: 'Basedev.baseRoute.baseRouteModel',
	pageSize: 10,
	autoLoad: false,
	proxy: {
		type: 'ajax',
		actionMethods: 'POST',
		url : basedev.realPath("getPaginationBaseRouteList.do"),
		reader: {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_FORM_ID);
			if (queryForm != null) {
				var p_name = queryForm.getForm().findField('routeName').getValue();
				var beginSiteCode = queryForm.getForm().findField('beginSiteCode').getValue();
				var endSiteCode = queryForm.getForm().findField('endSiteCode').getValue();
				var productCode = queryForm.getForm().findField('productCode').getValue();
				var beginRegionCode = queryForm.getForm().findField('beginRegionCode').getValue();
				var endRegionCode = queryForm.getForm().findField('endRegionCode').getValue();
				Ext.apply(operation, {
					params : {
						'q_sl_routeName' : p_name,
						'q_str_beginSiteCode' : beginSiteCode,
						'q_str_endSiteCode' : endSiteCode,
						'q_str_productCode' : productCode,
						'q_str_beginRegionCode' : beginRegionCode,
						'q_str_endRegionCode' : endRegionCode
					}
				});	
			}
		}
	}
});

/**
 * 路由表格
 */
Ext.define('Basedev.baseRoute.QuerybaseRouteResultGrid',{
	extend: 'Ext.grid.Panel',
	id: basedev.baseRoute.QUERY_BASE_ROUTE_RESULT_GRID_ID,
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
        align: 'center',
        xtype: 'rownumberer',
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
				var baseRouteWindow = Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_RESULT_GRID_ID).getbaseRouteWindow();
				baseRouteWindow.setTitle('查看路由');
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
				var baseRouteForm = baseRouteWindow.getbaseRouteForm();
				var baseRouteModel = Ext.create('Basedev.baseRoute.baseRouteModel',rowInfo.raw);
				baseRouteForm.loadRecord(baseRouteModel);
				baseRouteForm.setShowValue(rowInfo);
				//加载路由明细记录
                var routeCode = rowInfo.raw.routeCode;
                Ext.Ajax.request({
                    url:'../basedev/queryBaseRouteDetailList.do',
                    params:{routeCode:routeCode},
                    success: function(response){
                        var result = Ext.JSON.decode(response.responseText);
                        if(result.success){
                            var baseRouteDetailGrid = Ext.getCmp(basedev.baseRoute.BASE_ROUTE_DETAIL_RESULT_GRID_ID);
                            baseRouteDetailGrid.getStore().loadData(result.data);
                        }else{
                            Ext.ux.Toast.msg('提示', result.msg, 'error'); 
                        }
                    },
                    failure: function(response){
                        Ext.ux.Toast.msg('提示',response.responseText, 'error');
                    }
                });
				// 显示窗口
				baseRouteWindow.show();
			}
		}, {
			iconCls : 'deppon_icons_edit',
			tooltip : '修改',// 修改
			handler : function(gridView, rowIndex, colIndex) {
				var editEaseConfigWindow = Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_RESULT_GRID_ID).getEditbaseRouteWindow();
				editEaseConfigWindow.setTitle('编辑路由');
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
				editEaseConfigWindow.getEditbaseRouteForm().setOperatorType(basedev.baseRoute.STATE_UPDATE, rowInfo);
				//加载路由明细记录
				var routeCode = rowInfo.raw.routeCode;
				Ext.Ajax.request({
				    url:'../basedev/queryBaseRouteDetailList.do',
				    params:{routeCode:routeCode},
				    success: function(response){
				        var result = Ext.JSON.decode(response.responseText);
                        if(result.success){
                        	var baseRouteDetailGrid = Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_DETAIL_RESULT_GRID_ID);
                            baseRouteDetailGrid.getStore().loadData(result.data);
                        }else{
                            Ext.ux.Toast.msg('提示', result.msg, 'error'); 
                        }
				    },
				    failure: function(response){
				    	Ext.ux.Toast.msg('提示',response.responseText, 'error');
				    }
				});
				// 打开窗口
				editEaseConfigWindow.show();
			}
		}/*, {
			iconCls : 'deppon_icons_delete',
			tooltip : '删除',// 删除
			handler : function(grid, rowIndex, colIndex) {
				 删除 
				var rowInfo = Ext
						.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_RESULT_GRID_ID).store
						.getAt(rowIndex);
				
				var routeId = rowInfo.data.id;
				
				Ext.Msg.confirm('确认',
					'确认删除吗？',
					function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : basedev.realPath('/deleteBaseRoute.do'),
								params : {
									id: routeId
								},
								success : function(response) {
						        	var result = Ext.JSON.decode(response.responseText);
						        	
						        	if(result.success){
						        		Ext.ux.Toast.msg('提示', '删除成功');
										var grid = Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_RESULT_GRID_ID);
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
		}*/]
	}, {
		hidden : true,
		dataIndex : 'id'
	}, {
		text: '路由编号',
		width: 100,
		dataIndex: 'routeCode'
	}, {
		text : '路由名称',
		width: 150,
		dataIndex : 'routeName'
	}, {
		text : '产品类型',
		width: 100,
		dataIndex : 'productName'
	}, {
		text: '所属机构',
		width: 150,
		dataIndex: 'ownerOrgName'
	}, {
		text: '起始转运/分拨中心',
		width: 150,
		dataIndex: 'beginSiteName'
	}, {
		text: '目的转运/分拨中心',
		width: 150,
		dataIndex: 'endSiteName'
	}, {
		text: '起始行政区域',
		width: 200,
		dataIndex: 'beginRegionName'
	}, {
		text: '到达行政区域',
		width: 200,
		dataIndex: 'endRegionName'
	}, {
		text: '总停留时间',
		width: 100,
		dataIndex: 'totalStayTime',
		renderer: function(value){
		  return basedev.baseRoute.getTimeStr(value);
		}
	}, {
        text: '总路程',
        width: 100,
        dataIndex: 'totalMileage',
        renderer: function(value){
            return value + "公里";
        }
    }, {
        text: '途经站总数',
        width: 100,
        dataIndex: 'siteCount'
    }, {
        text: '总时间',
        width: 100,
        dataIndex: 'totalTime',
        renderer: function(value){
            return basedev.baseRoute.getTimeStr(value);
        }
    }, {
        text: '启用',
        width: 80,
        dataIndex: 'blFlag',
        renderer: function(value){
            return value == 1 ? '是' : '否';
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
    
	baseRouteWindow : null,
	getbaseRouteWindow : function(){
		me = this;
//		if(Ext.isEmpty(me.baseRouteWindow)){
			me.baseRouteWindow = Ext.create('Basedev.baseRoute.baseRouteWindow');
//		}
		return me.baseRouteWindow;
	},
	editbaseRouteWindow : null,
	getEditbaseRouteWindow : function(){
		me = this;
//		if(Ext.isEmpty(me.editbaseRouteWindow)){
			me.editbaseRouteWindow = Ext.create('Basedev.baseRoute.EditbaseRouteWindow');
//		}
		return me.editbaseRouteWindow;
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
                url : basedev.realPath('/batchUpdateRouteBlFlagById.do'),
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
                        var grid = Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_RESULT_GRID_ID);
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
		me.store = Ext.create('Basedev.baseRoute.baseRouteStore');
		me.tbar = [{
			text: '新增',
			handler: function(){
				var editbaseRouteWindow = me.getEditbaseRouteWindow();
				editbaseRouteWindow.setTitle('新增路由');
				var editbaseRouteForm = editbaseRouteWindow.getEditbaseRouteForm();
				editbaseRouteForm.setOperatorType(basedev.baseRoute.STATE_ADD);	
				editbaseRouteWindow.show();
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
								url : basedev.realPath('/batchDeleteBaseRouteById.do'),
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
										var grid = Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_RESULT_GRID_ID);
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
/*		me.listeners = {
            beforedestroy: function(){
                if(!Ext.isEmpty(me.baseRouteWindow)){
                	me.baseRouteWindow.removeAll();
                    me.baseRouteWindow.destroy();
                }
                if(!Ext.isEmpty(me.editbaseRouteWindow)){
                    me.editbaseRouteWindow.removeAll();
                    me.editbaseRouteWindow.destroy();
                }
            }
        };*/
		me.callParent(cfg);
	}
});

/**
 * 路由查看
 */
Ext.define('Basedev.baseRoute.baseRouteForm', {
    extend : 'Ext.form.Panel',
    title:'基本信息',
    id : basedev.baseRoute.BASE_ROUTE_FORM_ID,
    frame : true,
    defaults : {
        margin : '5 10 5 10',
        labelWidth : 110,
        readOnly : true,
        width: 280
    },
    layout : {
        type : 'table',
        columns : 3
    },
    defaultType : 'textfield',

    items : [{
        name: 'routeCode',
        fieldLabel: '路由编号'
    },{
        name : 'routeName',
        fieldLabel : '路由名称'
    }, {
        name: 'productName',
        fieldLabel:'产品类型'
    },{ 
        name: 'beginSiteName',
        labelWidth : 130,
        fieldLabel: '起始转运/分拨中心'
    }, {
        name: 'endSiteName',
        labelWidth : 130,
        fieldLabel: '目的转运/分拨中心'
    },  {
        name: 'ownerOrgName',
        fieldLabel: '所属机构'
    }, {
        name: 'beginRegionName',
        fieldLabel: '起始行政区域'
    }, {
        name: 'endRegionName',
        fieldLabel: '到达行政区域'
    }, {
        name: 'totalStayTime',
        fieldLabel: '总停留时间'
    }, {
        name: 'totalMileage',
        fieldLabel: '总路程'
    }, {
        name: 'totalTime',
        fieldLabel: '总时间'
    }, {
        name: 'siteCount',
        fieldLabel: '途经站总数'
    },{
        name: 'blFlag',
        fieldLabel: '启用',
        colspan: 3
    }, {
        name: 'remark',
        xtype: 'textareafield',
        fieldLabel: '备注',
        colspan: 3,
        width:870,
        height: 55
    } /*, {
        name : 'createUserCode',
        fieldLabel : '创建人工号'
    }, {
        name : 'modifyUserCode',
        fieldLabel : '修改人工号'
    }*/],
    setShowValue: function(record){
    	var form = this.getForm();
        form.findField('blFlag').setValue(record.data.blFlag == 1 ? '是' : '否');
        form.findField('totalMileage').setValue(record.data.totalMileage + '公里');
        form.findField('totalTime').setValue(basedev.baseRoute.getTimeStr(record.data.totalTime));
        form.findField('totalStayTime').setValue(basedev.baseRoute.getTimeStr(record.data.totalStayTime));
    }
});

Ext.define('Basedev.baseRoute.baseRouteDetailGrid',{
    extend: 'Ext.grid.Panel',
    id: basedev.baseRoute.BASE_ROUTE_DETAIL_RESULT_GRID_ID,
    frame: true,
    title:'详细信息',
    sortableColumns: false,
    enableColumnHide:false,
    enableColumnMove:false,
    height: 300,
    stripeRows : true, // 交替行效果
    params: null,
    columns:[{
        text: '序号',
        width: 50,
        xtype: 'rownumberer',
        draggable: true
    }, {
        text: '途经类型',
        width: 80,
        dataIndex: 'viaTypeName'
    }, {
        text: '当前转运/分拨中心',
        width: 160,
        dataIndex: 'currSiteName'
    }, {
        text: '当前行政区域',
        width: 160,
        dataIndex: 'currRegionName'
    }, {
        text: '下一转运/分拨中心',
        width: 160,
        dataIndex: 'nextSiteName'
    }, {
        text: '下一行政区域',
        width: 160,
        dataIndex: 'nextRegionName'
    }, {
        text: '驶到里程(公里)',
        width: 110,
        dataIndex: 'arriveMileage'
    }, {
        text: '驶到用时(分钟)',
        width: 110,
        dataIndex: 'arriveTime'
    }, {
        text: '停留时间(分钟)',
        width: 110,
        dataIndex: 'stayTime'
    }, {
        text: '运输方式',
        width: 100,
        dataIndex: 'classTypeName'
    },{
        text: '备注',
        width: 280,
        dataIndex:'remark'
    }],
    constructor: function(config){
        var me = this;
        me.store = Ext.create('Ext.data.Store', {
            storeId:'simpsonsStore',
            fields:['id','routeId','orderBy','currSiteCode', 'currSiteName','nextSiteName','currRegionCode','currRegionName','nextRegionName','viaType','viaTypeName','arriveMileage', 'arriveTime', 'stayTime', 'classType','classTypeName','remark']           
        });
        var cfg = Ext.apply({}, config);
        me.callParent([cfg]);
    }
});


/**
 * 查看窗口
 */
Ext.define('Basedev.baseRoute.baseRouteWindow', {
    extend: 'Ext.window.Window',
    width: 960,
    modal: true,
    closeAction: 'destroy',
    baseRouteForm : null,
    getbaseRouteForm: function(){
        if (Ext.isEmpty(this.baseRouteForm)) {
            this.baseRouteForm = Ext.create("Basedev.baseRoute.baseRouteForm");
        }
        return this.baseRouteForm;
    },
    baseRouteDetailGrid: null,
    getBaseRouteDetailGrid: function(){
    	if (Ext.isEmpty(this.baseRouteDetailGrid)) {
            this.baseRouteDetailGrid = Ext.create("Basedev.baseRoute.baseRouteDetailGrid");
        }
        return this.baseRouteDetailGrid;
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
            me.getbaseRouteForm(), me.getBaseRouteDetailGrid()
        ];
        me.buttons = [
            me.getCancelButton()
        ];
        me.callParent([cfg]);
    }
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp(basedev.baseRoute.CONTENT_ID)) {
		return;
	};
	
	var querybaseRouteForm = Ext.create('Basedev.baseRoute.QuerybaseRouteForm');
	var querybaseRouteResultGrid = Ext.create('Basedev.baseRoute.QuerybaseRouteResultGrid');
	
	var content = Ext.create('Ext.panel.Panel', {
		id: basedev.baseRoute.CONTENT_ID,
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		getQuerybaseRouteForm: function() {
			return querybaseRouteForm;
		},
		getQuerybaseRouteResultGrid: function() {
			return querybaseRouteResultGrid;
		},
		items: [
			querybaseRouteForm,
			querybaseRouteResultGrid
		]
	});
	
	Ext.getCmp(basedev.baseRoute.TAB_ID).add(content);
	// 加载表格数据
	querybaseRouteResultGrid.getStore().load();
});

/**
 * 配置新增/编辑
 */
Ext.define('Basedev.baseRoute.EditbaseRouteForm',{
	extend:'Ext.form.Panel',
	id : basedev.baseRoute.EDIT_BASE_ROUTE_FORM_ID,
	frame: true,
	title:'基本信息',
    defaults: {
    	margin:'5 15 5 15',
    	labelWidth: 120,
    	allowBlank: true,
	    validateOnChange: false
    },
    layout : {
    	type : 'table',
    	columns: 3
    },
	defaultType : 'textfield',
	operatorType : null,
	setOperatorType : function(state,record){
		this.operatorType = state;
		var editbaseRouteForm = this.getForm();
		// 表单重置
		editbaseRouteForm.reset();
		
		if(state == basedev.baseRoute.STATE_ADD){
			var baseRouteModel = Ext.create('Basedev.baseRoute.baseRouteModel');
			baseRouteModel.data.blFlag = 1;
			editbaseRouteForm.loadRecord(baseRouteModel);
			editbaseRouteForm.findField('routeCode').setReadOnly(false);
		} else if(state == basedev.baseRoute.STATE_UPDATE){
			var baseRouteModel = Ext.create('Basedev.baseRoute.baseRouteModel',record.raw);
			
			//所属机构
			if(record.data.ownerOrg){
    			var comboboxBaseOrgModel = Ext.create('BaseData.commonSelector.BaseOrgModel');
                comboboxBaseOrgModel.data.orgCode = record.data.ownerOrg;
                comboboxBaseOrgModel.data.orgName = record.data.ownerOrgName;
                baseRouteModel.data.ownerOrg = comboboxBaseOrgModel;
			}
			
			//产品类型
			if(record.data.productCode){
			     var comboxBaseProductModel = Ext.create('BaseData.commonSelector.BaseProductModel');
			     comboxBaseProductModel.data.productCode = record.data.productCode;
			     comboxBaseProductModel.data.productName = record.data.productName;
			     baseRouteModel.data.productCode = comboxBaseProductModel;
			}
			
			//起始转运/分拨中心
			if(record.data.beginSiteCode){
			     var comboxBaseSiteModel = Ext.create('BaseData.commonSelector.BaseSiteModel');
			     comboxBaseSiteModel.data.siteCode = record.data.beginSiteCode;
			     comboxBaseSiteModel.data.siteNameShort = record.data.beginSiteName;
			     baseRouteModel.data.beginSiteCode = comboxBaseSiteModel;
			}
			
			//目的转运/分拨中心
			if(record.data.endSiteCode){
                 var comboxBaseSiteModel = Ext.create('BaseData.commonSelector.BaseSiteModel');
                 comboxBaseSiteModel.data.siteCode = record.data.endSiteCode;
                 comboxBaseSiteModel.data.siteNameShort = record.data.endSiteName;
                 baseRouteModel.data.endSiteCode = comboxBaseSiteModel;
            }
			editbaseRouteForm.loadRecord(baseRouteModel);
			editbaseRouteForm.findField('routeCode').setReadOnly(true);
			basedev.baseRoute.captureFlag = true;
			//为biginRegionObj对象添加拦截器  
			var beginRegionObj = Ext.getCmp(basedev.baseRoute.EDIT_BASE_ROUTE_FORM_ID).getForm().findField("beginRegionCode");
            Ext.util.Observable.capture(beginRegionObj, basedev.baseRoute.captureFunction);
            //为endRegionObj对象添加拦截器  
            var endRegionObj = Ext.getCmp(basedev.baseRoute.EDIT_BASE_ROUTE_FORM_ID).getForm().findField("endRegionCode");
            Ext.util.Observable.capture(endRegionObj, basedev.baseRoute.captureFunction);
            
			var beginRegion = {
                code : record.data.beginRegionCodeStr,
                name : record.data.beginRegionName
            };
            editbaseRouteForm.findField("beginRegionCode").setRegionValue(beginRegion);
            
            var endRegion = {
                code : record.data.endRegionCodeStr,
                name : record.data.endRegionName
            };
            editbaseRouteForm.findField("endRegionCode").setRegionValue(endRegion);
            basedev.baseRoute.captureFlag = false;
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
            id: 'basedev.baseRoute.routeId',
            hidden : true
        },{
            xtype: 'textfield',
            name: 'routeCode',
            fieldLabel: '路由编号',
            maxLength : 20,
            allowBlank: false,
            validateOnBlur : true,
            regex : /^[A-Za-z0-9]+$/,
            regexText : '该输入项只能输入数字和字母',
            validator : function(field){
                if(!field){
                    return true;
                }
                
                var editVehicleForm = Ext.getCmp(basedev.baseRoute.EDIT_BASE_ROUTE_FORM_ID);
                var state = editVehicleForm.getOperatorType();
                if(basedev.baseRoute.STATE_UPDATE == state){
                    return true;
                }
                
                var paramsObj = {routeCode : field};
                var valid = false;
                Ext.Ajax.request({
                    url : basedev.realPath('uniqueCheckByRouteCode.do'),
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
                return '该路由编号已存在';
            }
        },{
            xtype : 'textfield',
            name : 'routeName',
            fieldLabel : '路由名称',
            maxLength : 13,
            allowBlank: false,
            validateOnBlur : true,
            validator : function(field){
                if(!field){
                    return true;
                }
                var editbaseRouteInfoForm = Ext.getCmp(basedev.baseRoute.EDIT_BASE_ROUTE_FORM_ID);
                var routeId = Ext.getCmp('basedev.baseRoute.routeId').getValue();
                var paramsObj = {id : routeId, routeName : field};
                var valid = false;
                Ext.Ajax.request({
                    url : basedev.realPath('uniqueCheckbaseRouteByRouteName.do'),
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
                return '该路由名称已存在';
            }
        },{
            xtype : 'commonProductSelector',
            name : 'productCode',
            fieldLabel : '产品类型',
            allowBlank: false,
            blFlag : 1,
            status : 1
        },{
            xtype : 'commonSiteSelector',
            name : 'beginSiteCode',
            fieldLabel : '起始转运/分拨中心',
            labelWidth : 120,
            siteType : '5,6',
            blFlag : 1,
            allowBlank: false,
            listeners: {
                select: function(combo,record,opts){
                    basedev.baseRoute.addRouteSite(record[0].data.siteNameShort,record[0].data.siteCode,basedev.baseRoute.VIATYPE_BEGIN,'起点');
                }
            }
        },{
            xtype : 'commonSiteSelector',
            name : 'endSiteCode',
            fieldLabel : '目的转运/分拨中心',
            labelWidth : 120,
            siteType : '5,6',
            blFlag : 1,
            allowBlank: false,
            listeners: {
                select: function(combo,record,opts){
                	basedev.baseRoute.addRouteSite(record[0].data.siteNameShort,record[0].data.siteCode,basedev.baseRoute.VIATYPE_END,'终点');
                }
            }
        },{
            xtype : 'commonOrgSelector',
            name : 'ownerOrg',
            fieldLabel : '所属机构',
            blFlag : 1
        },{
            xtype : 'addressselector',
            name : 'beginRegionCode',
            fieldLabel : '起始行政区域',
            labelPad: 5,
            labelSeparator:':',
            maxLength : 100
        },{
            xtype : 'addressselector',
            name : 'endRegionCode',
            fieldLabel : '到达行政区域',
            labelPad: 5,
            labelSeparator:':',
            maxLength : 100
        },{
            xtype: 'checkbox',
            name: 'blFlag',
            boxLabel: '启用',
            inputValue: '1',
            uncheckedValue: '0'
        },{
            name: 'remark',
            fieldLabel: '备注',
            xtype: 'textareafield',
            maxLength : 100,
            colspan: 3,
            height: 55,
            width: 580
        }];
        
        me.callParent();
	}
});

/**
 * 路由明细新增/编辑
 */
Ext.define('Basedev.baseRoute.EditbaseRouteDetailForm',{
    extend:'Ext.form.Panel',
    id: basedev.baseRoute.EDIT_BASE_ROUTE_DETAIL_RESULT_FORM_ID,
    frame: true,
    detailIndex: 0,
    defaults: {
        margin:'5 10 5 10',
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
    setOperatorType : function(rowIndex,state,record){
    	basedev.baseRoute.currSiteCode = '';
        this.operatorType = state;
        var editbaseRouteDetailForm = this.getForm();
        // 表单重置
        editbaseRouteDetailForm.reset();
        this.detailIndex = rowIndex;
        if(state == basedev.baseRoute.STATE_ADD){
            var baseRouteDetailModel = Ext.create('Basedev.baseRoute.baseRouteDetailModel');
            editbaseRouteDetailForm.loadRecord(baseRouteDetailModel);
        } else if(state == basedev.baseRoute.STATE_UPDATE){
        	basedev.baseRoute.currSiteCode = record.data.currSiteCode;
            var baseRouteDetailModel = Ext.create('Basedev.baseRoute.baseRouteDetailModel',record.data);
            
            //当前转运/分拨中心
            if(record.data.currSiteCode){
                 var comboxBaseSiteModel = Ext.create('BaseData.commonSelector.BaseSiteModel');
                 comboxBaseSiteModel.data.siteCode = record.data.currSiteCode;
                 comboxBaseSiteModel.data.siteNameShort = record.data.currSiteName;
                 baseRouteDetailModel.data.currSiteCode = comboxBaseSiteModel;
            }
            
            editbaseRouteDetailForm.loadRecord(baseRouteDetailModel);
            
            if(record.data.viaType == basedev.baseRoute.VIATYPE_END || record.data.viaType == basedev.baseRoute.VIATYPE_BEGIN){
                editbaseRouteDetailForm.findField('currSiteCode').setReadOnly(true);
                editbaseRouteDetailForm.findField("currRegionCode").setVisible(false);
                var currRegionNameField = editbaseRouteDetailForm.findField("currRegionName");
                currRegionNameField.setVisible(true);
                currRegionNameField.hideLabel = false;
                /*var baseRouteForm = Ext.getCmp(basedev.baseRoute.EDIT_BASE_ROUTE_FORM_ID).getForm();
                var currRegion = null; //行政区域           
                if(record.data.viaType == basedev.baseRoute.VIATYPE_BEGIN){
                    currRegion = baseRouteForm.findField("beginRegionCode");
                } else {
                    currRegion = baseRouteForm.findField("endRegionCode");    
                }
                 //拼接行政区域code
                var currRegionCode = currRegion.getValue();
                var currRegionCodeStr = "";
                if(currRegionCode){
                    if(currRegion.provinceCode){
                        currRegionCodeStr = currRegion.provinceCode;
                    }
                    if(currRegion.cityCode){
                        currRegionCodeStr += "-" + currRegion.cityCode;
                    }
                    if(currRegion.areaCode){
                        currRegionCodeStr += "-" + currRegion.areaCode;
                    }
                }
                var currRegion = {
                    code : currRegionCodeStr,
                    name : record.data.currRegionName
                };
                var currRegionField = editbaseRouteDetailForm.findField("currRegionCode");
                currRegionField.setRegionValue(currRegion);
                currRegionField.setDisabled(true);*/
            } else {
                 var currRegion = {
                    code : record.data.currRegionCodeStr,
                    name : record.data.currRegionName
                };
                editbaseRouteDetailForm.findField("currRegionCode").setRegionValue(currRegion);
            }
            
        }
    },
    getOperatorType : function(){
        return this.operatorType;
    },
    items : [{
            xtype : 'commonSiteSelector',
            name : 'currSiteCode',
            fieldLabel : '转运/分拨中心',
            siteType : '5,6',
            blFlag : 1,
            validateOnBlur : true,
            validator : function(field){
                var form = Ext.getCmp(basedev.baseRoute.EDIT_BASE_ROUTE_DETAIL_RESULT_FORM_ID);
                if(!field){
                    return '请选择转运/分拨中心';        
                }
                var currSiteCode = form.getForm().findField('currSiteCode').getValue();
                if(form.getOperatorType() == basedev.baseRoute.STATE_UPDATE){
                    if(basedev.baseRoute.currSiteCode==currSiteCode){
                        return true;
                    }
                }
                
                var sameFlag = 0;
                var detailGrid = Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_DETAIL_RESULT_GRID_ID);
                //循环store，判断转运/分拨中心是否存在
                detailGrid.getStore().each(function(record){
                   if(record.data.currSiteCode == currSiteCode){
                       sameFlag = 1;
                       return;
                   }
                });
                if(sameFlag == 1){
                   return '已存在相同的转运/分拨中心';
                }
            	return true;
            }
        },{
            xtype : 'addressselector',
            name : 'currRegionCode',
            fieldLabel : '行政区域',
            labelPad: 5,
            labelSeparator:':',
            maxLength : 100
        },{
            xtype : 'textfield',
            name : 'currRegionName',
            fieldLabel : '行政区域',
            readOnly: true,
            hidden: true,
            hideLabel: true
        },{
            xtype: 'numberfield',
            name: 'arriveMileage',
            fieldLabel: '驶到里程',
            decimalPrecision: 2, // 精确地小数点后两位
            allowDecimals: true,
            minValue: 0,
            maxValue: 999999.99
        },{
            xtype: 'numberfield',
            name: 'arriveTime',
            fieldLabel: '驶到用时',
            allowDecimals: false,
            minValue: 0,
            maxValue: 9999999999
        },{
            xtype: 'numberfield',
            name: 'stayTime',
            fieldLabel: '停留时间',
            allowDecimals: false,
            minValue: 0,
            maxValue: 9999999999
        },{
            xtype : 'dictcombo',
            dictType : 'BASE_ROUTE_CLASSTYPE',
            name: 'classType',
            fieldLabel:'运输方式',
            editable : false
        },{
            name: 'remark',
            fieldLabel: '备注',
            xtype: 'textareafield',
            maxLength : 60,
            colspan: 2,
            height: 55,
            width: 530
        }]
});

Ext.define('Basedev.baseRoute.QuerybaseRouteDetailGrid',{
    extend: 'Ext.grid.Panel',
    id: basedev.baseRoute.QUERY_BASE_ROUTE_DETAIL_RESULT_GRID_ID,
//    width: 855,
    frame: true,
    title:'详细信息',
    sortableColumns: false,
    enableColumnHide:false,
    enableColumnMove:false,
    stripeRows : true, // 交替行效果
    autoScroll: true,
    height: 300,
    params: null,
    columns:[{
        text: '序号',
        flex: 0.08,
        xtype: 'rownumberer',
        draggable: true
    }, {
        xtype : 'actioncolumn',
//        width: 100,
        text : '操作',
        align : 'center',
        items : [{
            iconCls : 'deppon_icons_newnode',//deppon_icons_movedown ↓
            tooltip : '新增下一站',
            handler : function(gridView, rowIndex, colIndex) {
                var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
                if(rowInfo.data.viaType == basedev.baseRoute.VIATYPE_END){
                    Ext.ux.Toast.msg('提示', '终点路由后不可增加');
                } else {
                	var editBaseRouteDetailWindow = Ext.create('Basedev.baseRoute.EditbaseRouteDetailWindow');
                    editBaseRouteDetailWindow.setTitle('新增站点');
                    editBaseRouteDetailWindow.getEditBaseRouteDetailForm().setOperatorType(rowIndex,basedev.baseRoute.STATE_ADD);
                    editBaseRouteDetailWindow.show();      
                }
            }
        },{
            iconCls : 'deppon_icons_edit',
            tooltip : '修改',// 修改
            handler : function(gridView, rowIndex, colIndex) {
                var editBaseRouteDetailWindow = Ext.create('Basedev.baseRoute.EditbaseRouteDetailWindow');
                editBaseRouteDetailWindow.setTitle('编辑站点');
                var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
                editBaseRouteDetailWindow.getEditBaseRouteDetailForm().setOperatorType(rowIndex, basedev.baseRoute.STATE_UPDATE, rowInfo);
                // 打开窗口
                editBaseRouteDetailWindow.show();
            }
        },{
            iconCls : 'deppon_icons_delete',
            tooltip : '删除',
            handler : function(gridView, rowIndex, colIndex) {
            	var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
                if(rowInfo.data.viaType == basedev.baseRoute.VIATYPE_END || rowInfo.data.viaType == basedev.baseRoute.VIATYPE_BEGIN){
                    Ext.ux.Toast.msg('提示', '不可删除');
                } else {
                    var store = gridView.up('grid').getStore();
                    store.remove(store.getAt(rowIndex));
                    Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_DETAIL_RESULT_GRID_ID).getView().refresh();    
                }
            }
         }]
    }, {
        text: '转运/分拨中心',
        flex: 0.15,
        dataIndex: 'currSiteName'
    }, {
        text: '途经类型',
        flex: 0.1,
        dataIndex: 'viaTypeName'
    }, {
        text: '所属行政区域',
        flex: 0.2,
        dataIndex: 'currRegionName'
    }, {
        text: '驶到里程(公里)',
        flex: 0.15,
        dataIndex: 'arriveMileage'
    }, {
        text: '驶到用时(分钟)',
        flex: 0.15,
        dataIndex: 'arriveTime'
    }, {
        text: '停留时间(分钟)',
        flex: 0.15,
        dataIndex: 'stayTime'
    }, {
        text: '运输方式',
        flex: 0.1,
        dataIndex: 'classTypeName'
    }, {
        text: '备注',
        flex: 0.15,
        dataIndex: 'remark'
    }],
    editRouteDetailWindow : null,
    getEditRouteDetailWindow : function(){
        me = this;
//        if(Ext.isEmpty(me.editRouteDetailWindow)){
            me.editRouteDetailWindow = Ext.create('Basedev.baseRoute.EditbaseRouteDetailWindow');
//        }
        return me.editRouteDetailWindow;
    },
    constructor: function(config){
        var me = this;
        me.store = Ext.create('Ext.data.Store', {
            storeId:'simpsonsStore',
            fields:['id','routeCode','orderBy','currSiteCode', 'currSiteName','currRegionCode','currRegionName','currRegionCodeStr','viaType','viaTypeName','arriveMileage', 'arriveTime', 'stayTime', 'classType','classTypeName','remark']           
        });
/*        me.tbar = [{
            text: '新增',
            handler: function(){
                var editBaseRouteDetailWindow = Ext.create('Basedev.baseRoute.EditbaseRouteDetailWindow');
                editBaseRouteDetailWindow.setTitle('新增转运/分拨中心');
                editBaseRouteDetailWindow.getEditBaseRouteDetailForm().setOperatorType(0,basedev.baseRoute.STATE_ADD);
                editBaseRouteDetailWindow.show();
            }
        }];*/
        var cfg = Ext.apply({}, config);
        me.callParent([cfg]);
    }
});

Ext.define('Basedev.baseRoute.EditbaseRouteDetailWindow', {
    extend: 'Ext.window.Window',
    width: 650,
    modal: true,
    closeAction: 'destroy',
    editBaseRouteDetailForm : null,
    getEditBaseRouteDetailForm: function(){
        if (Ext.isEmpty(this.editBaseRouteDetailForm)) {
            this.editBaseRouteDetailForm = Ext.create("Basedev.baseRoute.EditbaseRouteDetailForm");
        }
        return this.editBaseRouteDetailForm;
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
                    var baseRouteDetailForm = me.getEditBaseRouteDetailForm().getForm();
                    // 校验表单
                    if (!baseRouteDetailForm.isValid()) {
                        return;
                    }
                    var currRegion = baseRouteDetailForm.findField("currRegionCode"); //行政区域                   
                    var currSite = baseRouteDetailForm.findField("currSiteCode");//转运/分拨中心
                    var classType = baseRouteDetailForm.findField("classType");//运输方式
                    var arriveMileage = baseRouteDetailForm.findField("arriveMileage").getValue();//驶到里程
                    arriveMileage = arriveMileage == null ? 0 : arriveMileage;
                    var arriveTime = baseRouteDetailForm.findField("arriveTime").getValue();//驶到用时
                    arriveTime = arriveTime == null ? 0 : arriveTime;
                    var stayTime = baseRouteDetailForm.findField("stayTime").getValue();//停留时间
                    stayTime = stayTime == null ? 0 : stayTime;
                    var remark = baseRouteDetailForm.findField("remark").getValue();//备注
                    var baseRouteDetailStore = Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_DETAIL_RESULT_GRID_ID).store;
                    var index = me.getEditBaseRouteDetailForm().detailIndex;
                    
                    //新增转运/分拨中心
                    if(me.getEditBaseRouteDetailForm().getOperatorType()==basedev.baseRoute.STATE_ADD){
                    	var currRegionCodeStr = basedev.baseRoute.getRegionCodeStr(currRegion);
                        var record = {'rownum':1,'currSiteCode':currSite.getValue(),'currSiteName':currSite.getRawValue(),
                        'currRegionCode':currRegion.getValue(),'currRegionName':currRegion.getRawValue(),'currRegionCodeStr':currRegionCodeStr,
                        'viaType':basedev.baseRoute.VIATYPE_THROUGH,'viaTypeName': '途经',
                        'classType':classType.getValue(),'classTypeName': classType.getRawValue(),
                        'arriveMileage':arriveMileage,'arriveTime':arriveTime,'stayTime':stayTime,'remark':remark};
                        baseRouteDetailStore.insert(parseInt(index)+1,record);  
                        Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_DETAIL_RESULT_GRID_ID).getView().refresh();
                    } else {
                    	var record = baseRouteDetailStore.getAt(index);
                        if(record.data.viaType != basedev.baseRoute.VIATYPE_END && record.data.viaType != basedev.baseRoute.VIATYPE_BEGIN){
                        	var currRegionCodeStr = basedev.baseRoute.getRegionCodeStr(currRegion);
                            record.set('currSiteCode',currSite.getValue());
                            record.set('currSiteName',currSite.getRawValue());
                            record.set('currRegionCode',currRegion.getValue());
                            record.set('currRegionName',currRegion.getRawValue());
                            record.set('currRegionCodeStr',currRegionCodeStr);
                        }
                        record.set('classType',classType.getValue());
                        record.set('classTypeName',classType.getRawValue());
                        record.set('arriveMileage',arriveMileage);
                        record.set('arriveTime',arriveTime);
                        record.set('stayTime',stayTime);
                        record.set('remark',remark);
                    }
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
             me.getEditBaseRouteDetailForm()
        ];
        me.buttons = [
            me.getCancelButton(),
            me.getSaveButton()
        ];
        
        me.callParent([cfg]);
    }
});

Ext.define('Basedev.baseRoute.EditbaseRouteWindow', {
	extend: 'Ext.window.Window',
	width: 1030,
	modal: true,
	closeAction: 'destroy',
	editbaseRouteForm : null,
	getEditbaseRouteForm: function(){
		if (Ext.isEmpty(this.editbaseRouteForm)) {
			this.editbaseRouteForm = Ext.create("Basedev.baseRoute.EditbaseRouteForm");
		}
		return this.editbaseRouteForm;
	},
    editbaseRouteGrid: null,
    getEditbaseRouteGrid: function(){
        if(Ext.isEmpty(this.editbaseRouteGrid)){
            this.editbaseRouteGrid = Ext.create("Basedev.baseRoute.QuerybaseRouteDetailGrid");
        }
        return this.editbaseRouteGrid;
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
					var baseRouteForm = me.getEditbaseRouteForm().getForm();
					// 校验表单
					if (!baseRouteForm.isValid()) {
						return;
					}
					
					var baseRouteDetailStore = Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_DETAIL_RESULT_GRID_ID).store;
					var firstFlag = true;
					if(baseRouteDetailStore.getAt(0).get('viaType') != basedev.baseRoute.VIATYPE_BEGIN){
					   firstFlag = false;   
					}
					var baseRouteDetailVos = [];
                    for (var i = 0; i < baseRouteDetailStore.getCount(); i++) {
                    	var s= baseRouteDetailStore.getAt(i);
                    	var viaType = s.get('viaType');
                        var orderBy = i + 1;
                        if(!firstFlag){
                            if(viaType == basedev.baseRoute.VIATYPE_BEGIN){
                                orderBy = 1;    
                            } else if(viaType == basedev.baseRoute.VIATYPE_END){
                                orderBy = baseRouteDetailStore.getCount();
                            } else {
                                orderBy = i + 2;
                            }
                        }
                        var nextRoute = baseRouteDetailStore.getAt(i+1);
                        var nextSiteCode = '-';
                        var nextRegionCode = '';
                        if(nextRoute){
                            nextSiteCode = nextRoute.get('currSiteCode');
                            nextRegionCode = nextRoute.get('currRegionCode');
                        }
                        var baseRouteDetailVo = new basedev.baseRoute.BaseRouteDetailVo(viaType, s.get('currSiteCode'), s.get('currRegionCode'), s.get('arriveMileage'), s.get('arriveTime'), s.get('stayTime'), s.get('classType'), s.get('remark'), nextSiteCode, nextRegionCode, orderBy);
                        baseRouteDetailVos.push(baseRouteDetailVo);
                    }
                    
					var data = baseRouteForm.getValues();
					data.detailList = baseRouteDetailVos;
					// 设置起止行政区域编码
                    data.beginRegionCode = baseRouteForm.findField("beginRegionCode").getValue();
                    data.endRegionCode = baseRouteForm.findField("endRegionCode").getValue();
					var url = basedev.realPath('updateBaseRoute.do');
					if (me.getEditbaseRouteForm().getOperatorType() == basedev.baseRoute.STATE_ADD) {
						url = basedev.realPath('insertBaseRoute.do');
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
					        	
					        	var grid = Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_RESULT_GRID_ID);
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
			me.getEditbaseRouteForm(),me.getEditbaseRouteGrid()
	    ];
		me.buttons = [
			me.getCancelButton(),
			me.getSaveButton()
		];
    	Ext.getCmp(basedev.baseRoute.EDIT_BASE_ROUTE_FORM_ID).getForm().findField("beginRegionCode").on('change', function(combo,record,opts){
            basedev.baseRoute.addRouteRegion(combo.getRawValue(),combo.getValue(),basedev.baseRoute.VIATYPE_BEGIN, '起点');
/*            console.log("code:"+combo.getValue());
            console.log("name:"+combo.getRawValue());*/
        });
		Ext.getCmp(basedev.baseRoute.EDIT_BASE_ROUTE_FORM_ID).getForm().findField("endRegionCode").on('change', function(combo,record,opts){
			basedev.baseRoute.addRouteRegion(combo.getRawValue(),combo.getValue(),basedev.baseRoute.VIATYPE_END, '终点');
        });
		
		me.listeners = {
            beforeclose: function(){
                Ext.getCmp(basedev.baseRoute.QUERY_BASE_ROUTE_DETAIL_RESULT_GRID_ID).getStore().removeAll(); 
            }
        };
		me.callParent([cfg]);
	}
});
