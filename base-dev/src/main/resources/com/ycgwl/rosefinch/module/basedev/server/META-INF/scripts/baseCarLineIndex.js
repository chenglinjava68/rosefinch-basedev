basedev.baseCarLine.TAB_ID="T_basedev-baseCarLineIndex";  // 车线维护标签页ID
basedev.baseCarLine.CONTENT_ID = "T_basedev-baseCarLineIndex_content";  // 车线内容panel ID

basedev.baseCarLine.QUERY_BASE_CARLINE_FORM_ID = "T_basedev-querybaseCarLineForm";// 车线查询表单   

basedev.baseCarLine.QUERY_BASE_CARLINE_RESULT_GRID_ID = "T_basedev-querybaseCarLineResultGrid";// 车线列表

basedev.baseCarLine.STATE_ADD = 'add';   // 新增
basedev.baseCarLine.STATE_UPDATE = 'update';   // 修改

basedev.baseCarLine.BASE_CARLINE_FORM_ID = "T_basedev-baseCarLineForm";
basedev.baseCarLine.EDIT_BASE_CARLINE_FORM_ID = "T_basedev-editbaseCarLineForm";//编辑车线表单

basedev.baseCarLine.QUERY_BASE_CARLINE_DETAIL_CARLINE_GRID_ID = "T_basedev-querybaseCarLineDetailGrid";//编辑明细表格

basedev.baseCarLine.EDIT_BASE_CARLINE_DETAIL_RESULT_FORM_ID = "T_basedev-editbaseCarLineDetailGrid";//编辑明细表格

basedev.baseCarLine.BASE_CARLINE_DETAIL_CARLINE_GRID_ID = 'T_basedev-baseCarLineDetailGrid';//车线明细查看

basedev.baseCarLine.VIATYPE_BEGIN = 1;//起点车线
basedev.baseCarLine.VIATYPE_THROUGH = 2;//途经车线
basedev.baseCarLine.VIATYPE_END = 3;//终点车线

basedev.baseCarLine.currSiteCode = '';
basedev.baseCarLine.captureFlag = false;

//拦截函数  
basedev.baseCarLine.captureFunction = function(eventName){  
    if(eventName == 'change' && basedev.baseCarLine.captureFlag){//事件名称是change则返回false终止事件的执行  
        return false;  
    }  
    return true;  
}  

basedev.baseCarLine.addLineSite = function(siteName, siteCode, viaType, viaTypeName){
    var baseCarLineDetailStore = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_DETAIL_CARLINE_GRID_ID).store;
    var index = baseCarLineDetailStore.find('viaType',viaType);
    if(index == -1){
    	var record = {'rownum':1,'currSiteCode':siteCode,'currSiteName':siteName,'viaType':viaType, 'viaTypeName': viaTypeName};
    	if(viaType == basedev.baseCarLine.VIATYPE_BEGIN){
    	   baseCarLineDetailStore.insert(0,record);
    	   //刷新序号
    	   Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_DETAIL_CARLINE_GRID_ID).getView().refresh();
    	} else {
    	   baseCarLineDetailStore.add(record);
    	}
    } else {
        baseCarLineDetailStore.getAt(index).set("currSiteCode", siteCode);
        baseCarLineDetailStore.getAt(index).set("currSiteName", siteName);    
    }
}

basedev.baseCarLine.addLineRegion = function(regionName, regionCode, viaType, viaTypeName){
    var baseCarLineDetailStore = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_DETAIL_CARLINE_GRID_ID).store;
    var index = baseCarLineDetailStore.find('viaType',viaType);
    if(index == -1){
    	var record = {'rownum':1,'currRegionCode':regionCode,'currRegionName':regionName,'viaType':viaType, 'viaTypeName': viaTypeName}
        if(viaType == basedev.baseCarLine.VIATYPE_BEGIN){
            baseCarLineDetailStore.insert(0,record);
            //刷新序号
            Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_DETAIL_CARLINE_GRID_ID).getView().refresh();
        } else {
            baseCarLineDetailStore.add(record);
        }
    } else {
        baseCarLineDetailStore.getAt(index).set("currRegionCode", regionCode);
        baseCarLineDetailStore.getAt(index).set("currRegionName", regionName);    
    }
}

basedev.baseCarLine.getRegionCodeStr = function(currRegion){
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

basedev.baseCarLine.baseCarLineDetailVo = function(viaType, currSiteCode, currRegionCode, arriveMileage, arriveTime, stayTime, classType, remark, nextSiteCode, nextRegionCode,orderBy) {
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

basedev.baseCarLine.getTimeStr = function(minutes){
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
Ext.define('Basedev.baseCarLine.QuerybaseCarLineForm',{
	extend:'Ext.form.Panel',
	id : basedev.baseCarLine.QUERY_BASE_CARLINE_FORM_ID,
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
        labelWidth : 115,
        width: 280
    },
	defaultType : 'textfield',
	initComponent: function(){
		var me = this;
		me.items = [{
            name: 'lineCode',
            fieldLabel: '车线编码'
        },{
    		name: 'lineName',
    		fieldLabel: '车线名称'
    	}, {
    		xtype : 'commonSiteSelector',
            name : 'beginSiteCode',
            fieldLabel : '起始转运/分拨中心',
            siteType : '5,6'
    	}, {
            xtype : 'commonSiteSelector',
            name : 'endSiteCode',
            fieldLabel : '目的转运/分拨中心',
            siteType : '5,6'
        }, {
            xtype : 'addressselector',
            name : 'beginRegionCode',
            fieldLabel : '起始行政区域',
            labelPad: 5,
            labelSeparator:':'
        }, {
            xtype : 'addressselector',
            name : 'endRegionCode',
            fieldLabel : '到达行政区域',
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

                    var selectResultPanel = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_RESULT_GRID_ID);
                    selectResultPanel.setVisible(true);
                    selectResultPanel.getPagingToolbar().moveFirst();
                }
            }],
		me.callParent();
	}
});

/**
 * 车线model
 */
Ext.define('Basedev.baseCarLine.baseCarLineModel', {
	extend : 'Ext.data.Model',
	 fields : [{
        name : 'id',
        type : 'string'
    }, {
        name : 'lineName',//车线名称
        type : 'string'
    }, {
        name : 'lineCode',//车线编码
        type : 'string'
    }, {
        name : 'lineType',//车线类型
        type : 'string'
    }, {
        name : 'beginRegionCode'//起始行政区域
    },{
        name : 'beginRegionName',//起始行政区域
        type : 'string'
    }, {
       name : 'beginRegionCodeStr',//起始行政区域编码
       type : 'string'
    }, {
        name : 'endRegionCode'//到达行政区域
    }, {
        name : 'endRegionName',//到达行政区域名称
        type : 'string'
    }, {
       name : 'endRegionCodeStr',//到达行政区域编码
       type : 'string'
    },{
        name : 'beginSiteCode'//起始转运/分拨中心
    },{
        name : 'beginSiteName',//起始转运/分拨中心名称
        type : 'string'
    },{
        name : 'endSiteCode'//目的转运/分拨中心
    },{
        name : 'endSiteName',//目的转运/分拨中心名称
        type : 'string'
    }, {
        name : 'ownerOrg'
       // type : 'string'
    }, {
        name : 'loaderDetial',//装载范围明细 展示用
        type : 'string'
    }, {
        name : 'totalStayTime',//总停留时间
        type : 'string'
    }, {
        name : 'totalMileage',//总路程
        type : 'string'
    }, {
        name : 'siteCount',//途径站总数
        type : 'string'
    },  {
        name : 'totalTime',//总停留时间
        type : 'string'
    }, {
        name : 'createUserCode',//创建人
        type : 'string'
    }, {
       name : 'createTime',//创建时间
       type : 'date',
       convert : dateConvert
    }, {
       name : 'modifyUserCode',//修改人
       type : 'string'
    }, {
       name : 'modifyTime',//修改时间
       type : 'date',
       convert : dateConvert
    }, {
       name : 'remark',//备注
       type : 'string'
    }, {
        name : 'delFlag',
        type : 'string'
    }, {
       name : 'blFlag',//启用状态
       type : 'int'
    }, {
        name: 'ownerOrgName',
        type: 'string'
    }, {
        name: 'carLineTypeName',
        type: 'string'
    }]
});

/**
 * 车线明细model
 */
Ext.define('Basedev.baseCarLine.baseCarLineDetailModel', {
    extend : 'Ext.data.Model',
    fields : [{
        name : 'id',
        type : 'string'
    }, {
        name : 'lineId',//车线Id
        type : 'string'
    }, {
        name : 'orderBy',//途经排序位置
        type : 'string'
    }, {
        name : 'viaType',//途经类型（1：起点车线   2：途经车线   3：终点车线）
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
 * 车线Store
 */
Ext.define('Basedev.baseCarLine.baseCarLineStore',{
	extend:'Ext.data.Store',
	model: 'Basedev.baseCarLine.baseCarLineModel',
	pageSize: 10,
	autoLoad: false,
	proxy: {
		type: 'ajax',
		actionMethods: 'POST',
		url : basedev.realPath("getPaginationbaseCarLineList.do"),
		reader: {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_FORM_ID);
			if (queryForm != null) {
				var lineCode = queryForm.getForm().findField('lineCode').getValue();
				var p_name = queryForm.getForm().findField('lineName').getValue();
				var beginSiteCode = queryForm.getForm().findField('beginSiteCode').getValue();
				var endSiteCode = queryForm.getForm().findField('endSiteCode').getValue();
				var beginRegionCode = queryForm.getForm().findField('beginRegionCode').getValue();
				var endRegionCode = queryForm.getForm().findField('endRegionCode').getValue();
				Ext.apply(operation, {
					params : {
						'q_sl_lineCode' : lineCode,
						'q_sl_lineName' : p_name,
						'q_str_beginSiteCode' : beginSiteCode,
						'q_str_endSiteCode' : endSiteCode,
						'q_str_beginRegionCode' : beginRegionCode,
						'q_str_endRegionCode' : endRegionCode
					}
				});	
			}
		}
	}
});

/**
 * 车线表格
 */
Ext.define('Basedev.baseCarLine.QuerybaseCarLineResultGrid',{
	extend: 'Ext.grid.Panel',
	id: basedev.baseCarLine.QUERY_BASE_CARLINE_RESULT_GRID_ID,
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
				var baseCarLineWindow = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_RESULT_GRID_ID).getbaseCarLineWindow();
				baseCarLineWindow.setTitle('查看车线');
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
				var baseCarLineForm = baseCarLineWindow.getbaseCarLineForm();
				var baseCarLineModel = Ext.create('Basedev.baseCarLine.baseCarLineModel',rowInfo.raw);
				baseCarLineForm.loadRecord(baseCarLineModel);
				baseCarLineForm.setShowValue(rowInfo);
				//加载车线明细记录
                var lineCode = rowInfo.raw.lineCode;
                Ext.Ajax.request({
                    url:'../basedev/querybaseCarLineDetailList.do',
                    params:{lineCode:lineCode},
                    success: function(response){
                        var result = Ext.JSON.decode(response.responseText);
                        if(result.success){
                            var baseCarLineDetailGrid = Ext.getCmp(basedev.baseCarLine.BASE_CARLINE_DETAIL_CARLINE_GRID_ID);
                            baseCarLineDetailGrid.getStore().loadData(result.data);
                        }else{
                            Ext.ux.Toast.msg('提示', result.msg, 'error'); 
                        }
                    },
                    failure: function(response){
                        Ext.ux.Toast.msg('提示',response.responseText, 'error');
                    }
                });
				// 显示窗口
				baseCarLineWindow.show();
			}
		}, {
			iconCls : 'deppon_icons_edit',
			tooltip : '修改',// 修改
			handler : function(gridView, rowIndex, colIndex) {
				var editEaseConfigWindow = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_RESULT_GRID_ID).getEditbaseCarLineWindow();
				editEaseConfigWindow.setTitle('编辑车线');
				var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
				editEaseConfigWindow.getEditbaseCarLineForm().setOperatorType(basedev.baseCarLine.STATE_UPDATE, rowInfo);
				//加载车线明细记录
				var lineCode = rowInfo.raw.lineCode;
				Ext.Ajax.request({
				    url:'../basedev/querybaseCarLineDetailList.do',
				    params:{lineCode:lineCode},
				    success: function(response){
				        var result = Ext.JSON.decode(response.responseText);
                        if(result.success){
                        	var baseCarLineDetailGrid = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_DETAIL_CARLINE_GRID_ID);
                            baseCarLineDetailGrid.getStore().loadData(result.data);
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
						.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_RESULT_GRID_ID).store
						.getAt(rowIndex);
				
				var lineCode = rowInfo.data.id;
				
				Ext.Msg.confirm('确认',
					'确认删除吗？',
					function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : basedev.realPath('/deletebaseCarLine.do'),
								params : {
									id: lineCode
								},
								success : function(response) {
						        	var result = Ext.JSON.decode(response.responseText);
						        	
						        	if(result.success){
						        		Ext.ux.Toast.msg('提示', '删除成功');
										var grid = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_RESULT_GRID_ID);
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
	},  {
		text : '车线编码',
		width: 100,
		dataIndex : 'lineCode'
	},{
        text : '车线名称',
        width: 150,
        dataIndex : 'lineName'
    },{
        text : '车线类型',
        width: 100,
        dataIndex : 'carLineTypeName'
    },  {
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
		  return basedev.baseCarLine.getTimeStr(value);
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
            return basedev.baseCarLine.getTimeStr(value);
        }
    }, {
        text: '装载范围明细',
        width: 150,
        dataIndex: 'loaderDetial'
        
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
	},
    {
        text : '备注',
        width: 100,
        dataIndex : 'remark'
    }],
    
	baseCarLineWindow : null,
	getbaseCarLineWindow : function(){
		me = this;
//		if(Ext.isEmpty(me.baseCarLineWindow)){
			me.baseCarLineWindow = Ext.create('Basedev.baseCarLine.baseCarLineWindow');
//		}
		return me.baseCarLineWindow;
	},
	editbaseCarLineWindow : null,
	getEditbaseCarLineWindow : function(){
		me = this;
//		if(Ext.isEmpty(me.editbaseCarLineWindow)){
			me.editbaseCarLineWindow = Ext.create('Basedev.baseCarLine.EditbaseCarLineWindow');
//		}
		return me.editbaseCarLineWindow;
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
            var paramsObj = {idList: ids, blFlag: blFlag}
            Ext.Ajax.request({
                url : basedev.realPath('/batchUpdateCarLineBlFlagById.do'),
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
                        var grid = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_RESULT_GRID_ID);
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
		me.store = Ext.create('Basedev.baseCarLine.baseCarLineStore');
		me.tbar = [{
			text: '新增',
			handler: function(){
				var editbaseCarLineWindow = me.getEditbaseCarLineWindow();
				editbaseCarLineWindow.setTitle('新增车线');
				var editbaseCarLineForm = editbaseCarLineWindow.getEditbaseCarLineForm();
				editbaseCarLineForm.setOperatorType(basedev.baseCarLine.STATE_ADD);	
				editbaseCarLineWindow.show();
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
								url : basedev.realPath('/batchDeletebaseCarLineById.do'),
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
										var grid = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_RESULT_GRID_ID);
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
                if(!Ext.isEmpty(me.baseCarLineWindow)){
                	me.baseCarLineWindow.removeAll();
                    me.baseCarLineWindow.destroy();
                }
                if(!Ext.isEmpty(me.editbaseCarLineWindow)){
                    me.editbaseCarLineWindow.removeAll();
                    me.editbaseCarLineWindow.destroy();
                }
            }
        };*/
		me.callParent(cfg);
	}
});

/**
 * 车线查看
 */
Ext.define('Basedev.baseCarLine.baseCarLineForm', {
    extend : 'Ext.form.Panel',
    title:'基本信息',
    id : basedev.baseCarLine.BASE_CARLINE_FORM_ID,
    frame : true,
    defaults : {
        margin : '10 10 10 10',
        labelWidth : 115,
        readOnly : true,
        width: 260
    },
    layout : {
        type : 'table',
        columns : 3
    },
    defaultType : 'textfield',

    items : [{
        name : 'lineCode',
        fieldLabel : '车线编码'
    }, {
    	
        name: 'lineName',
        fieldLabel:'车线名称'
    },{
        name: 'blFlag',
        fieldLabel: '启用'
    },{
        name: 'carLineTypeName',
        fieldLabel:'车线类型'
    }, {
        name: 'ownerOrgName',
        fieldLabel: '所属机构'
    }, { 
        name: 'beginSiteName',
        fieldLabel: '起始转运/分拨中心'
    }, {
        name: 'endSiteName',
        fieldLabel: '目的转运/分拨中心'
    },{
        name: 'beginRegionName',
        fieldLabel: '起始行政区域',
        width: 300
    }, {
        name: 'totalMileage',
        fieldLabel: '总路程'
    }, {
        name: 'totalStayTime',
        fieldLabel: '总停留时间'
    }, {
        name: 'endRegionName',
        fieldLabel: '到达行政区域',
        width: 300
    }, {
        name: 'totalTime',
        fieldLabel: '总时间'
    }, {
        name: 'siteCount',
        fieldLabel: '途经站总数',
        colspan: 3
    }, {
        name: 'loaderDetial',
        xtype: 'textareafield',
        fieldLabel: '装载范围明细',
        colspan: 3,
        width:700,
        height: 55
    },{
        name: 'remark',
        xtype: 'textareafield',
        fieldLabel: '备注',
        colspan: 3,
        width:700,
        height: 55
    }],
    setShowValue: function(record){
    	var form = this.getForm();
        form.findField('blFlag').setValue(record.data.blFlag == 1 ? '是' : '否');
        form.findField('totalMileage').setValue(record.data.totalMileage + '公里');
        form.findField('totalTime').setValue(basedev.baseCarLine.getTimeStr(record.data.totalTime));
        form.findField('totalStayTime').setValue(basedev.baseCarLine.getTimeStr(record.data.totalStayTime));
    }
});

Ext.define('Basedev.baseCarLine.baseCarLineDetailGrid',{
    extend: 'Ext.grid.Panel',
    id: basedev.baseCarLine.BASE_CARLINE_DETAIL_CARLINE_GRID_ID,
    frame: true,
    title:'详细信息',
    sortableColumns: false,
    enableColumnHide:false,
    enableColumnMove:false,
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
        width: 130,
        dataIndex: 'currSiteName'
    }, {
        text: '当前行政区域',
        width: 160,
        dataIndex: 'currRegionName'
    }, {
        text: '下一转运/分拨中心',
        width: 130,
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
            fields:['id','lineId','orderBy','currSiteCode', 'currSiteName','nextSiteName','currRegionCode','currRegionName','nextRegionName','viaType','viaTypeName','arriveMileage', 'arriveTime', 'stayTime', 'classType','classTypeName','remark']           
        });
        var cfg = Ext.apply({}, config);
        me.callParent([cfg]);
    }
});


/**
 * 查看窗口
 */
Ext.define('Basedev.baseCarLine.baseCarLineWindow', {
    extend: 'Ext.window.Window',
    width: 940,
    modal: true,
    closeAction: 'destroy',
    baseCarLineForm : null,
    getbaseCarLineForm: function(){
        if (Ext.isEmpty(this.baseCarLineForm)) {
            this.baseCarLineForm = Ext.create("Basedev.baseCarLine.baseCarLineForm");
        }
        return this.baseCarLineForm;
    },
    baseCarLineDetailGrid: null,
    getbaseCarLineDetailGrid: function(){
    	if (Ext.isEmpty(this.baseCarLineDetailGrid)) {
            this.baseCarLineDetailGrid = Ext.create("Basedev.baseCarLine.baseCarLineDetailGrid");
        }
        return this.baseCarLineDetailGrid;
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
            me.getbaseCarLineForm(), me.getbaseCarLineDetailGrid()
        ];
        me.buttons = [
            me.getCancelButton()
        ];
        me.callParent([cfg]);
    }
});

Ext.onReady(function() {
	Ext.QuickTips.init();
	
	if (Ext.getCmp(basedev.baseCarLine.CONTENT_ID)) {
		return;
	};
	
	var querybaseCarLineForm = Ext.create('Basedev.baseCarLine.QuerybaseCarLineForm');
	var querybaseCarLineResultGrid = Ext.create('Basedev.baseCarLine.QuerybaseCarLineResultGrid');
	
	var content = Ext.create('Ext.panel.Panel', {
		id: basedev.baseCarLine.CONTENT_ID,
		cls: "panelContentNToolbar",
		bodyCls: 'panelContentNToolbar-body',
		getQuerybaseCarLineForm: function() {
			return querybaseCarLineForm;
		},
		getQuerybaseCarLineResultGrid: function() {
			return querybaseCarLineResultGrid;
		},
		items: [
			querybaseCarLineForm,
			querybaseCarLineResultGrid
		]
	});
	
	Ext.getCmp(basedev.baseCarLine.TAB_ID).add(content);
	// 加载表格数据
	querybaseCarLineResultGrid.getStore().load();
});

/**
 * 配置新增/编辑
 */
Ext.define('Basedev.baseCarLine.EditbaseCarLineForm',{
	extend:'Ext.form.Panel',
	id : basedev.baseCarLine.EDIT_BASE_CARLINE_FORM_ID,
	frame: true,
    defaults: {
    	margin:'5 15 5 15',
    	labelWidth: 115,
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
		var editbaseCarLineForm = this.getForm();
		// 表单重置
		editbaseCarLineForm.reset();
		
		if(state == basedev.baseCarLine.STATE_ADD){
			var baseCarLineModel = Ext.create('Basedev.baseCarLine.baseCarLineModel');
			   baseCarLineModel.data.blFlag = 1;
			    editbaseCarLineForm.loadRecord(baseCarLineModel);
			    editbaseCarLineForm.findField('lineCode').setReadOnly(false);
		} else if(state == basedev.baseCarLine.STATE_UPDATE){
			var baseCarLineModel = Ext.create('Basedev.baseCarLine.baseCarLineModel',record.raw);
			
			//所属机构
			if(record.data.ownerOrg){
    			var comboboxBaseOrgModel = Ext.create('BaseData.commonSelector.BaseOrgModel');
                comboboxBaseOrgModel.data.orgCode = record.data.ownerOrg;
                comboboxBaseOrgModel.data.orgName = record.data.ownerOrgName;
                baseCarLineModel.data.ownerOrg = comboboxBaseOrgModel;
			}
			
			
			//起始转运/分拨中心
			if(record.data.beginSiteCode){
			     var comboxBaseSiteModel = Ext.create('BaseData.commonSelector.BaseSiteModel');
			     comboxBaseSiteModel.data.siteCode = record.data.beginSiteCode;
			     comboxBaseSiteModel.data.siteNameShort = record.data.beginSiteName;
			     baseCarLineModel.data.beginSiteCode = comboxBaseSiteModel;
			}
			
			//目的转运/分拨中心
			if(record.data.endSiteCode){
                 var comboxBaseSiteModel = Ext.create('BaseData.commonSelector.BaseSiteModel');
                 comboxBaseSiteModel.data.siteCode = record.data.endSiteCode;
                 comboxBaseSiteModel.data.siteNameShort = record.data.endSiteName;
                 baseCarLineModel.data.endSiteCode = comboxBaseSiteModel;
            }
			editbaseCarLineForm.loadRecord(baseCarLineModel);
			editbaseCarLineForm.findField('lineCode').setReadOnly(true);
			basedev.baseCarLine.captureFlag = true;
			//为biginRegionObj对象添加拦截器  
			var beginRegionObj = Ext.getCmp(basedev.baseCarLine.EDIT_BASE_CARLINE_FORM_ID).getForm().findField("beginRegionCode");
            Ext.util.Observable.capture(beginRegionObj, basedev.baseCarLine.captureFunction);
           //为endRegionObj对象添加拦截器  
            var endRegionObj = Ext.getCmp(basedev.baseCarLine.EDIT_BASE_CARLINE_FORM_ID).getForm().findField("endRegionCode");
            Ext.util.Observable.capture(endRegionObj, basedev.baseCarLine.captureFunction);
            
			var beginRegion = {
                code : record.data.beginRegionCodeStr,
                name : record.data.beginRegionName
            };
            editbaseCarLineForm.findField("beginRegionCode").setRegionValue(beginRegion);
            
            var endRegion = {
                code : record.data.endRegionCodeStr,
                name : record.data.endRegionName
            };
            editbaseCarLineForm.findField("endRegionCode").setRegionValue(endRegion);
            basedev.baseCarLine.captureFlag = false;
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
            id: 'basedev.baseCarLine.lineId',
            hidden : true
        }, {
            xtype : 'textfield',
            name : 'lineName',
            fieldLabel : '车线名称',
            maxLength : 40,
            allowBlank: false,
            validateOnBlur : true,
            validator : function(field){
                if(!field){
                    return true;
                }
                var editbaseCarLineInfoForm = Ext.getCmp(basedev.baseCarLine.EDIT_BASE_CARLINE_FORM_ID);
                var lineId = Ext.getCmp('basedev.baseCarLine.lineId').getValue();
                var paramsObj = {id : lineId, lineName : field};
                var valid = false;
                Ext.Ajax.request({
                    url : basedev.realPath('uniqueCheckbaseCarLineBylineName.do'),
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
                return '该车线名称已存在';
            }
        },{
        xtype : 'textfield',
        name : 'lineCode',
        fieldLabel : '车线编码',
        maxLength : 15,
        allowBlank: false,
        validateOnBlur : true,
        regex : /^[A-Za-z0-9]+$/,
        regexText : '该输入项只能输入数字和字母',
        validator : function(field){
            if(!field){
                return true;
            }
            
            var editbaseCarLineInfoForm = Ext.getCmp(basedev.baseCarLine.EDIT_BASE_CARLINE_FORM_ID);
            var state = editbaseCarLineInfoForm.getOperatorType();
            if(basedev.baseCarLine.STATE_UPDATE == state){
                return true;
            }
            
            var paramsObj = {lineCode : field};
            var valid = false;
            Ext.Ajax.request({
                url : basedev.realPath('uniqueCheckByLineCode.do'),
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
            return '该车线编码已存在';
        }
    },{
            xtype : 'dictcombo',
            dictType : 'BASE_CARLINE_TYPE',
            name : 'lineType',
            fieldLabel : '车线类型',
            editable : false,
            allowBlank: false
        },{
            xtype : 'commonOrgSelector',
            name : 'ownerOrg',
            blFlag:1,
            fieldLabel : '所属机构'
        },{
            xtype : 'commonSiteSelector',
            name : 'beginSiteCode',
            fieldLabel : '起始转运/分拨中心',
            siteType : '5,6',
            blFlag:1,
            allowBlank: false,
            listeners: {
                select: function(combo,record,opts){
                    basedev.baseCarLine.addLineSite(record[0].data.siteNameShort,record[0].data.siteCode,basedev.baseCarLine.VIATYPE_BEGIN,'起点');
                }
            }
        },{
            xtype : 'commonSiteSelector',
            name : 'endSiteCode',
            fieldLabel : '目的转运/分拨中心',
            siteType : '5,6',
            blFlag:1,
            allowBlank: false,
            listeners: {
                select: function(combo,record,opts){
                	basedev.baseCarLine.addLineSite(record[0].data.siteNameShort,record[0].data.siteCode,basedev.baseCarLine.VIATYPE_END,'终点');
                }
            }
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
            maxLength : 100,
            colspan: 2
        },{
            name: 'loaderDetial',
            fieldLabel: '装载范围明细',
            xtype: 'textareafield',
            maxLength : 300,
            colspan: 2,
            height: 55,
            width: 540
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
            width: 540
        }];
        
        me.callParent();
	}
});

/**
 * 车线明细新增/编辑
 */
Ext.define('Basedev.baseCarLine.EditbaseCarLineDetailForm',{
    extend:'Ext.form.Panel',
    id: basedev.baseCarLine.EDIT_BASE_CARLINE_DETAIL_RESULT_FORM_ID,
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
    	basedev.baseCarLine.currSiteCode = '';
        this.operatorType = state;
        var editbaseCarLineDetailForm = this.getForm();
        // 表单重置
        editbaseCarLineDetailForm.reset();
        this.detailIndex = rowIndex;
        if(state == basedev.baseCarLine.STATE_ADD){
            var baseCarLineDetailModel = Ext.create('Basedev.baseCarLine.baseCarLineDetailModel');
            editbaseCarLineDetailForm.loadRecord(baseCarLineDetailModel);
        } else if(state == basedev.baseCarLine.STATE_UPDATE){
        	basedev.baseCarLine.currSiteCode = record.data.currSiteCode;
            var baseCarLineDetailModel = Ext.create('Basedev.baseCarLine.baseCarLineDetailModel',record.data);
            
            //当前转运/分拨中心
            if(record.data.currSiteCode){
                 var comboxBaseSiteModel = Ext.create('BaseData.commonSelector.BaseSiteModel');
                 comboxBaseSiteModel.data.siteCode = record.data.currSiteCode;
                 comboxBaseSiteModel.data.siteNameShort = record.data.currSiteName;
                 baseCarLineDetailModel.data.currSiteCode = comboxBaseSiteModel;
            }
            
            editbaseCarLineDetailForm.loadRecord(baseCarLineDetailModel);
            
            if(record.data.viaType == basedev.baseCarLine.VIATYPE_END || record.data.viaType == basedev.baseCarLine.VIATYPE_BEGIN){
                editbaseCarLineDetailForm.findField('currSiteCode').setReadOnly(true);
                editbaseCarLineDetailForm.findField("currRegionCode").setVisible(false);
                var currRegionNameField = editbaseCarLineDetailForm.findField("currRegionName");
                currRegionNameField.setVisible(true);
                currRegionNameField.hideLabel = false;
                /*var baseCarLineForm = Ext.getCmp(basedev.baseCarLine.EDIT_BASE_CARLINE_FORM_ID).getForm();
                var currRegion = null; //行政区域           
                if(record.data.viaType == basedev.baseCarLine.VIATYPE_BEGIN){
                    currRegion = baseCarLineForm.findField("beginRegionCode");
                } else {
                    currRegion = baseCarLineForm.findField("endRegionCode");    
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
                var currRegionField = editbaseCarLineDetailForm.findField("currRegionCode");
                currRegionField.setRegionValue(currRegion);
                currRegionField.setDisabled(true);*/
            } else {
                 var currRegion = {
                    code : record.data.currRegionCodeStr,
                    name : record.data.currRegionName
                };
                editbaseCarLineDetailForm.findField("currRegionCode").setRegionValue(currRegion);
            }
        }
    },
    getOperatorType : function(){
        return this.operatorType;
    },
    items : [{
            xtype : 'commonSiteSelector',
            name : 'currSiteCode',
            fieldLabel : '站点',
            siteType : '5,6',
            allowBlank: false,
            validateOnBlur : true,
            validator : function(field){  
                var form = Ext.getCmp(basedev.baseCarLine.EDIT_BASE_CARLINE_DETAIL_RESULT_FORM_ID);
                if(!field){
                    return '请选择转运/分拨中心';        
                }
                var currSiteCode = form.getForm().findField('currSiteCode').getValue();
                if(form.getOperatorType() == basedev.baseCarLine.STATE_UPDATE){
                    if(basedev.baseCarLine.currSiteCode==currSiteCode){
                        return true;
                    }
                }
                var sameFlag = 0;
                var detailGrid = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_DETAIL_CARLINE_GRID_ID);
                //循环store，判断网点是否存在
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
            fieldLabel : '<font style="color:red">*</font>行政区域',
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
            width: 540
        }]
});

Ext.define('Basedev.baseCarLine.QuerybaseCarLineDetailGrid',{
    extend: 'Ext.grid.Panel',
    id: basedev.baseCarLine.QUERY_BASE_CARLINE_DETAIL_CARLINE_GRID_ID,
//    width: 855,
    frame: true,
    title:'详细信息',
    sortableColumns: false,
    enableColumnHide:false,
    enableColumnMove:false,
    stripeRows : true, // 交替行效果
    params: null,
    columns:[{
        text: '序号',
        flex: 0.08,
        xtype: 'rownumberer',
        draggable: true
    }, {
        xtype : 'actioncolumn',
        width: 100,
        text : '操作',
        align : 'center',
        items : [{
            iconCls : 'deppon_icons_newnode',//deppon_icons_movedown ↓
            tooltip : '新增下一站',
            handler : function(gridView, rowIndex, colIndex) {
                var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
                if(rowInfo.data.viaType == basedev.baseCarLine.VIATYPE_END){
                    Ext.ux.Toast.msg('提示', '终点车线后不可增加');
                } else {
                	var editbaseCarLineDetailWindow = Ext.create('Basedev.baseCarLine.EditbaseCarLineDetailWindow');
                    editbaseCarLineDetailWindow.setTitle('新增站点');
                    editbaseCarLineDetailWindow.getEditbaseCarLineDetailForm().setOperatorType(rowIndex,basedev.baseCarLine.STATE_ADD);
                    editbaseCarLineDetailWindow.show();      
                }
            }
        },{
            iconCls : 'deppon_icons_edit',
            tooltip : '修改',// 修改
            handler : function(gridView, rowIndex, colIndex) {
                var editbaseCarLineDetailWindow = Ext.create('Basedev.baseCarLine.EditbaseCarLineDetailWindow');
                editbaseCarLineDetailWindow.setTitle('编辑站点');
                var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
                editbaseCarLineDetailWindow.getEditbaseCarLineDetailForm().setOperatorType(rowIndex, basedev.baseCarLine.STATE_UPDATE, rowInfo);
                // 打开窗口
                editbaseCarLineDetailWindow.show();
            }
        },{
            iconCls : 'deppon_icons_delete',
            tooltip : '删除',
            handler : function(gridView, rowIndex, colIndex) {
            	var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
                if(rowInfo.data.viaType == basedev.baseCarLine.VIATYPE_END || rowInfo.data.viaType == basedev.baseCarLine.VIATYPE_BEGIN){
                    Ext.ux.Toast.msg('提示', '不可删除');
                } else {
                    var store = gridView.up('grid').getStore();
                    Ext.Msg.confirm('确认','确认删除吗？',function(btn) {
                        if (btn == 'yes') {
                        	     store.remove(store.getAt(rowIndex));
                               Ext.ux.Toast.msg('提示', '删除成功');
                               var grid = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_DETAIL_CARLINE_GRID_ID);
                                 // 加载表格
                                   grid.getView().refresh();
                                    }
                    }
                );
                  //  store.remove(store.getAt(rowIndex));
                   // Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_DETAIL_CARLINE_GRID_ID).getView().refresh();    
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
        flex: 0.3,
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
    editCarLineDetailWindow : null,
    getEditCarLineDetailWindow : function(){
        me = this;
//        if(Ext.isEmpty(me.editRouteDetailWindow)){
            me.editCarLineDetailWindow = Ext.create('Basedev.baseCarLine.EditbaseCarLineDetailWindow');
//        }
        return me.editCarLineDetailWindow;
    },
    constructor: function(config){
        var me = this;
        me.store = Ext.create('Ext.data.Store', {
            storeId:'simpsonsStore',
            fields:['id','lineCode','orderBy','currSiteCode', 'currSiteName','currRegionCode','currRegionName','currRegionCodeStr','viaType','viaTypeName','arriveMileage', 'arriveTime', 'stayTime', 'classType','classTypeName','remark']           
        });
/*        me.tbar = [{
            text: '新增',
            handler: function(){
                var editbaseCarLineDetailWindow = Ext.create('Basedev.baseCarLine.EditbaseCarLineDetailWindow');
                editbaseCarLineDetailWindow.setTitle('新增网点');
                editbaseCarLineDetailWindow.getEditbaseCarLineDetailForm().setOperatorType(0,basedev.baseCarLine.STATE_ADD);
                editbaseCarLineDetailWindow.show();
            }
        }];*/
        var cfg = Ext.apply({}, config);
        me.callParent([cfg]);
    }
});

Ext.define('Basedev.baseCarLine.EditbaseCarLineDetailWindow', {
    extend: 'Ext.window.Window',
    width: 650,
    modal: true,
    closeAction: 'destroy',
    editbaseCarLineDetailForm : null,
    getEditbaseCarLineDetailForm: function(){
        if (Ext.isEmpty(this.editbaseCarLineDetailForm)) {
            this.editbaseCarLineDetailForm = Ext.create("Basedev.baseCarLine.EditbaseCarLineDetailForm");
        }
        return this.editbaseCarLineDetailForm;
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
                    var baseCarLineDetailForm = me.getEditbaseCarLineDetailForm().getForm();
                    // 校验表单
                    if (!baseCarLineDetailForm.isValid()) {
                        return;
                    }
                    var currRegion = baseCarLineDetailForm.findField("currRegionCode"); //行政区域                   
                    var currSite = baseCarLineDetailForm.findField("currSiteCode");//网点
                    var classType = baseCarLineDetailForm.findField("classType");//运输方式
                    var arriveMileage = baseCarLineDetailForm.findField("arriveMileage").getValue();//驶到里程
                    var arriveTime = baseCarLineDetailForm.findField("arriveTime").getValue();//驶到用时
                    var stayTime = baseCarLineDetailForm.findField("stayTime").getValue();//停留时间
                    var remark = baseCarLineDetailForm.findField("remark").getValue();//备注
                    var baseCarLineDetailStore = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_DETAIL_CARLINE_GRID_ID).store;
                    var index = me.getEditbaseCarLineDetailForm().detailIndex;
                    
                    //新增网点
                    if(me.getEditbaseCarLineDetailForm().getOperatorType()==basedev.baseCarLine.STATE_ADD){
                    	var currRegionCodeStr = basedev.baseCarLine.getRegionCodeStr(currRegion);
                        var record = {'rownum':1,'currSiteCode':currSite.getValue(),'currSiteName':currSite.getRawValue(),
                        'currRegionCode':currRegion.getValue(),'currRegionName':currRegion.getRawValue(),'currRegionCodeStr':currRegionCodeStr,
                        'viaType':basedev.baseCarLine.VIATYPE_THROUGH,'viaTypeName': '途经',
                        'classType':classType.getValue(),'classTypeName': classType.getRawValue(),
                        'arriveMileage':arriveMileage,'arriveTime':arriveTime,'stayTime':stayTime,'remark':remark};
                        baseCarLineDetailStore.insert(parseInt(index)+1,record);  
                        Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_DETAIL_CARLINE_GRID_ID).getView().refresh();
                    } else {
                    	var record = baseCarLineDetailStore.getAt(index);
                        if(record.data.viaType != basedev.baseCarLine.VIATYPE_END && record.data.viaType != basedev.baseCarLine.VIATYPE_BEGIN){
                        	var currRegionCodeStr = basedev.baseCarLine.getRegionCodeStr(currRegion);
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
             me.getEditbaseCarLineDetailForm()
        ];
        me.buttons = [
            me.getCancelButton(),
            me.getSaveButton()
        ];
        
        me.callParent([cfg]);
    }
});

Ext.define('Basedev.baseCarLine.EditbaseCarLineWindow', {
	extend: 'Ext.window.Window',
	width: 970,
	modal: true,
	closeAction: 'destroy',
	editbaseCarLineForm : null,
	getEditbaseCarLineForm: function(){
		if (Ext.isEmpty(this.editbaseCarLineForm)) {
			this.editbaseCarLineForm = Ext.create("Basedev.baseCarLine.EditbaseCarLineForm");
		}
		return this.editbaseCarLineForm;
	},
    editbaseCarLineGrid: null,
    getEditbaseCarLineGrid: function(){
        if(Ext.isEmpty(this.editbaseCarLineGrid)){
            this.editbaseCarLineGrid = Ext.create("Basedev.baseCarLine.QuerybaseCarLineDetailGrid");
        }
        return this.editbaseCarLineGrid;
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
					var baseCarLineForm = me.getEditbaseCarLineForm().getForm();
					// 校验表单
					if (!baseCarLineForm.isValid()) {
						return;
					}
				/*	
					var baseCarLineDetailStore = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_DETAIL_CARLINE_GRID_ID).store;
					var baseCarLineDetailVos = [];
                    for (var i = 0; i < baseCarLineDetailStore.getCount(); i++) {
                        var nextLine = baseCarLineDetailStore.getAt(i+1);
                        var nextSiteCode = '-';
                        var nextRegionCode = '';
                        if(nextLine){
                            nextSiteCode = nextLine.get('currSiteCode');
                            nextRegionCode = nextLine.get('currRegionCode');
                        }
                        var s= baseCarLineDetailStore.getAt(i);
                        var baseCarLineDetailVo = new basedev.baseCarLine.baseCarLineDetailVo(s.get('viaType'), s.get('currSiteCode'), s.get('currRegionCode'), s.get('arriveMileage'), s.get('arriveTime'), s.get('stayTime'), s.get('classType'), s.get('remark'), nextSiteCode, nextRegionCode);
                        baseCarLineDetailVos.push(baseCarLineDetailVo);
                    }
                    */
					   
                    var baseCarLineDetailStore = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_DETAIL_CARLINE_GRID_ID).store;
                    var firstFlag = true;
                    if(baseCarLineDetailStore.getAt(0).get('viaType') != basedev.baseCarLine.VIATYPE_BEGIN){
                       firstFlag = false;   
                    }
                    var baseCarLineDetailVos = [];
                    for (var i = 0; i < baseCarLineDetailStore.getCount(); i++) {
                        var s= baseCarLineDetailStore.getAt(i);
                        var viaType = s.get('viaType');
                        var orderBy = i + 1;
                        if(!firstFlag){
                            if(viaType == basedev.baseCarLine.VIATYPE_BEGIN){
                                orderBy = 1;    
                            } else if(viaType == basedev.baseCarLine.VIATYPE_END){
                                orderBy = baseCarLineDetailStore.getCount();
                            } else {
                                orderBy = i + 2;
                            }
                        }
                        var nextLine = baseCarLineDetailStore.getAt(i+1);
                        var nextSiteCode = '-';
                        var nextRegionCode = '';
                        if(nextLine){
                            nextSiteCode = nextLine.get('currSiteCode');
                            nextRegionCode = nextLine.get('currRegionCode');
                        }
                        var baseCarLineDetailVo = new basedev.baseCarLine.baseCarLineDetailVo(viaType, s.get('currSiteCode'), s.get('currRegionCode'), s.get('arriveMileage'), s.get('arriveTime'), s.get('stayTime'), s.get('classType'), s.get('remark'), nextSiteCode, nextRegionCode, orderBy);
                        baseCarLineDetailVos.push(baseCarLineDetailVo);
                    }
                    
					var data = baseCarLineForm.getValues();
					data.detailList = baseCarLineDetailVos;
					// 设置起止行政区域编码
                    data.beginRegionCode = baseCarLineForm.findField("beginRegionCode").getValue();
                    data.endRegionCode = baseCarLineForm.findField("endRegionCode").getValue();
					var url = basedev.realPath('updatebaseCarLine.do');
					if (me.getEditbaseCarLineForm().getOperatorType() == basedev.baseCarLine.STATE_ADD) {
						url = basedev.realPath('insertbaseCarLine.do');
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
					        	
					        	var grid = Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_RESULT_GRID_ID);
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
			me.getEditbaseCarLineForm(),me.getEditbaseCarLineGrid()
	    ];
		me.buttons = [
			me.getCancelButton(),
			me.getSaveButton()
		];
    	Ext.getCmp(basedev.baseCarLine.EDIT_BASE_CARLINE_FORM_ID).getForm().findField("beginRegionCode").on('change', function(combo,record,opts){
            basedev.baseCarLine.addLineRegion(combo.getRawValue(),combo.getValue(),basedev.baseCarLine.VIATYPE_BEGIN, '起点');
/*            console.log("code:"+combo.getValue());
            console.log("name:"+combo.getRawValue());*/
        });
		Ext.getCmp(basedev.baseCarLine.EDIT_BASE_CARLINE_FORM_ID).getForm().findField("endRegionCode").on('change', function(combo,record,opts){
			basedev.baseCarLine.addLineRegion(combo.getRawValue(),combo.getValue(),basedev.baseCarLine.VIATYPE_END, '终点');
        });
		
		me.listeners = {
            beforeclose: function(){
                Ext.getCmp(basedev.baseCarLine.QUERY_BASE_CARLINE_DETAIL_CARLINE_GRID_ID).getStore().removeAll(); 
            }
        };
		me.callParent([cfg]);
	}
});
