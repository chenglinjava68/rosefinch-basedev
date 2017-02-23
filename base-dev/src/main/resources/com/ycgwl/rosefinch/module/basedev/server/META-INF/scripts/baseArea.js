/** 此模块主页面的ID */
basedev.baseArea.CONTENT_ID  = 'T_basedev-baseArea_content';
basedev.baseArea.FORMAT_TIME  = 'Y-m-d H:i:s'; //格式化时间字符串
basedev.baseArea.REGION_LEVEL = null;

/** 区域Model */
Ext.define('BaseData.baseArea.baseAreaModule', {
	extend : 'Ext.data.Model',
	fields : [{
        name : 'areaId',
        type : 'long'
    }, {
        name : 'areaCode',
        type : 'string'
    }, {
        name : 'areaName',
        type : 'string'
    }, {
        name : 'remark',
        type : 'string'
    }, {
        name : 'blFlag',
        type : 'int'
    },{
        name : 'createUserCode',
        type : 'string'
    },{
        name : 'modifyUserCode',
        type : 'string'
    },{
        name : 'createTime',
        defaultValue : new Date(),
        convert : dateConvert,
        type : 'number'
    },{
        name : 'modifyTime',
        defaultValue : new Date(),
        convert : dateConvert,
        type : 'number'
    }]
});

/**
 *  查询store
 */
Ext.define('BaseData.baseArea.baseAreaStore',{
	extend: 'Ext.data.Store',
	pageSize:10,
	model : 'BaseData.baseArea.baseAreaModule',
	autoLoad:true,
	proxy : {
		url : '../basedev/getBaseAreaList.do',
		type : 'ajax',
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp("BaseData_baseArea_baseAreaForm_ID"); //查询表单
			if (queryForm != null) {
				var q_str_areaCode = queryForm.getForm().findField('q_str_areaCode').getValue();
                var q_str_areaName = queryForm.getForm().findField('q_str_areaName').getValue();
				Ext.apply(operation, {
					params : {
						'q_str_areaCode' : q_str_areaCode,
                        'q_str_areaName':q_str_areaName
					}
				});	
			}
		}
	}
});

/**
 * 查询表单
 */
Ext.define('BaseData.baseArea.baseAreaForm', {
	extend:'Ext.form.Panel',
	id: 'BaseData_baseArea_baseAreaForm_ID',
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
            name: 'q_str_areaCode',
            fieldLabel:'片区编号',
            columnWidth: .2
        },{
            name: 'q_str_areaName',
            fieldLabel:'片区名称',
            columnWidth: .2

        },{
			xtype : 'button',
			cls: 'yellow_button',
			text:"查询",
			width : 70,
			// 查询按钮的响应事件：
			handler: function() {
				if(me.getForm().isValid()){
					var selectResultPanel = Ext.getCmp("BaseData_baseArea_baseAreaPageElementGridPanel");
					selectResultPanel.setVisible(true);
					selectResultPanel.getPagingToolbar().moveFirst();
				}
			}
		},{
			xtype : 'button',
			text:"重置",
			width : 60,
			handler: function() {
				me.getForm().reset();
			}
		}];
		me.callParent();
	}
});

/**
 * 列表panel
 */
Ext.define('BaseData.baseArea.baseAreaPageElementGridPanel', {
	extend: 'Ext.grid.Panel',
	id:'BaseData_baseArea_baseAreaPageElementGridPanel',
	title : '查询结果',
	frame: true,
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SIMPLE"}),
	//selType : "rowmodel", // 选择类型设置为：行选择
	stripeRows : true, // 交替行效果
	baseAreaAddWindow : null,
	baseAreaChangeWindow: null,
	getbaseAreaAddWindow : function(){
		if(this.baseAreaAddWindow==null){
			this.baseAreaAddWindow = Ext.create('BaseData.baseArea.baseAreaAddWindow');
		}
		return this.baseAreaAddWindow;
	},
	getbaseAreaChangeWindow : function(data){
		if(this.baseAreaChangeWindow==null){
			this.baseAreaChangeWindow = Ext.create('BaseData.baseArea.baseAreaChangeWindow');
		}
		this.baseAreaChangeWindow.editData = data;
		return this.baseAreaChangeWindow;
	},

	// 新增按钮
	addButton: null,
	getAddButton: function() {
		var me = this;
		if(Ext.isEmpty(me.addButton)){
			me.addButton = Ext.create('Ext.Button', {
				text :'新增',//新增
				handler : function() {
					me.getbaseAreaAddWindow().show();
				}
			});
		}
		return this.addButton;
	},// 删除
    deleteButton: null,
    getDeleteButton: function(){
        var me = this;
        if(Ext.isEmpty(me.deleteButton)){
            me.deleteButton = Ext.create('Ext.Button', {
                text :'删除',//
                handler : function() {
                    var accList = me.getSelectionModel().getSelection();
                        if (accList == null || accList.length==0) {
                            Ext.ux.Toast.msg("提示", '请选择要删除的记录');
                            return ;
                        } else {
                        	 var areaId =[];
                        	 var areaCode =[];
                            for(var i=0;i<accList.length;i++){
                               areaId[i] = accList[i].get('areaId');
                               areaCode[i]=accList[i].get('areaCode');
                            }
                       Ext.Msg.confirm('删除', '是否批量删除',
                    function(btn){
                        if(btn=='yes'){
                            Ext.Ajax.request({
                                url : "../basedev/batchDeleteById.do",
                                params : {
                                    'areaId' :areaId,
                                    'areaCode':areaCode
                                },
                                success : function(response) {
                                    var data = Ext.JSON
                                            .decode(response.responseText);
                                    if (data.success) {
                                        Ext.ux.Toast.msg("提示", data.msg);
                                        var selectResultPanel = Ext
                                                .getCmp("BaseData_baseArea_baseAreaPageElementGridPanel");
                                        selectResultPanel.getPagingToolbar()
                                                .moveFirst();
                                    } else {
                                        Ext.ux.Toast.msg("提示", data.msg);
                                        return;
                                    }
                                },
                                exception : function(response) {
                                    var json = Ext
                                            .decode(response.responseText);
                                    Ext.ux.Toast.msg("提示", json);
                                }
                            });
                        }
                    });
                        }
               }
        });
      }
        return this.deleteButton;
    },
	// 启用按钮
    startButton: null,
    getStartButton: function(){
        var me = this;
        if(Ext.isEmpty(me.startButton)){
            me.startButton = Ext.create('Ext.Button', {
                text :'启用',//
                handler : function() {
                    var accList = me.getSelectionModel().getSelection();
                        if (accList == null || accList.length==0) {
                            Ext.ux.Toast.msg("提示", '请选择要操作的数据');
                            return ;
                        } else {
                        	  var account = accList[0];
                        	   var areaId =[];
                        	   for(var j= 0 ;j<accList.length;j++){
                        	   	      if(accList[j].get('blFlag') ==1 || accList[j].get('blFlag') == '1'){
                        	   	      	 Ext.ux.Toast.msg("提示", '只能选择已经停用的数据，请确认');
                                 return;
                        	   	      }
                        	   }
                            for(var i=0;i<accList.length;i++){
                               areaId[i] = accList[i].get('areaId');
                            }
                        	   Ext.Msg.confirm('启用', '是否批量启用',
                    function(btn){
                        if(btn=='yes'){
                             Ext.Ajax.request({
                                url : "../basedev/stopAndstartAreaById.do",
                                params : {
                                    'areaId' : areaId,
                                    'typeValue':"startArea"
                                },
                                success : function(response) {
                                    var data = Ext.JSON
                                            .decode(response.responseText);
                                    if (data.success) {
                                        Ext.ux.Toast.msg("提示", data.msg);
                                        var selectResultPanel = Ext
                                                .getCmp("BaseData_baseArea_baseAreaPageElementGridPanel");
                                        selectResultPanel.getPagingToolbar()
                                                .moveFirst();
                                    } else {
                                        Ext.ux.Toast.msg("提示", data.msg);
                                        return;
                                    }
                                },
                                exception : function(response) {
                                    var json = Ext
                                            .decode(response.responseText);
                                    Ext.ux.Toast.msg("提示", json);
                                }
                            });
                        }
                    });
                        }
               }
        });
      }
        return this.startButton;
    },
    // 停用按钮
    stopButton: null,
    getStopButton: function(){
        var me = this;
        if(Ext.isEmpty(me.stopButton)){
            me.stopButton = Ext.create('Ext.Button', {
                text :'停用',//
                handler : function() {
                    var accList = me.getSelectionModel().getSelection();
                        if (accList == null || accList.length==0) {
                            Ext.ux.Toast.msg("提示", '请选择要操作的数据');
                            return ;
                        }else {
                            var account = accList[0];
                            //状态(1：启用，0：停用)
                               var areaId =[];
                               for(var j= 0 ;j<accList.length;j++){
                                      if(accList[j].get('blFlag') ==0 || accList[j].get('blFlag') == '0'){
                                         Ext.ux.Toast.msg("提示", '只能操作已经启用的数据，请确认');
                                         return;
                                      }
                               }
                            for(var i=0;i<accList.length;i++){
                               areaId[i] = accList[i].get('areaId');
                            }
                            Ext.Msg.confirm('停用', '是否批量停用',
                    function(btn){
                        if(btn=='yes'){
                            Ext.Ajax.request({
                                url : "../basedev/stopAndstartAreaById.do",
                                params : {
                                    'areaId' :areaId,
                                    'typeValue':"stopArea"
                                },
                                success : function(response) {
                                    var data = Ext.JSON
                                            .decode(response.responseText);
                                    if (data.success) {
                                        Ext.ux.Toast.msg("提示", data.msg);
                                        var selectResultPanel = Ext
                                                .getCmp("BaseData_baseArea_baseAreaPageElementGridPanel");
                                        selectResultPanel.getPagingToolbar()
                                                .moveFirst();
                                    } else {
                                        Ext.ux.Toast.msg("提示", data.msg);
                                        return;
                                    }
                                },
                                exception : function(response) {
                                    var json = Ext
                                            .decode(response.responseText);
                                    Ext.ux.Toast.msg("提示", json);
                                }
                            });
                        }
                    });  
                        }
               }
        });
      }
        return this.stopButton;
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
		me.store = Ext.create('BaseData.baseArea.baseAreaStore');
		me.bbar = me.getPagingToolbar();
		me.tbar = [me.getAddButton()/*,me.getDeleteButton()*/,me.getStartButton(),me.getStopButton()];
		me.columns = [
			{xtype: "rownumberer",
             text: "序号",
             width:40
             },{
			xtype:'actioncolumn',
			text: '操作',
			width:50,
			items: [
			{ 
				iconCls: 'deppon_icons_edit',
				tooltip: '修改片区',//修改
				handler: function(grid, rowIndex, colIndex) { // 修改按钮
					var rec = grid.getStore().getAt(rowIndex); 
					Ext.Ajax.request({
						url : "../basedev/preUpdateArea.do",
						params : {
							'areaId' : rec.get('areaId')
						},
						success : function(response) {
							var data  =  Ext.JSON.decode(response.responseText);
							if(data.success){
								var xWindow = me.getbaseAreaChangeWindow(data);
								xWindow.show();
							}else{
								Ext.Msg.alert(basedev.baseArea.i18n('dpap.message.share.alertInfo'),basedev.baseArea.i18n('dpap.message.instationMsg.plsChoiceMsg')); 
							}
						},
						exception : function(response) {
							var json = Ext.decode(response.responseText);
						}}
					);
				}
			}/*, {
				iconCls: 'deppon_icons_delete',
				tooltip:'删除片区',//删除
				handler: function(grid, rowIndex, colIndex) {
					删除
					var rowInfo = Ext.getCmp('BaseData_baseArea_baseAreaPageElementGridPanel').store.getAt(rowIndex);
					var areaId = rowInfo.data.areaId;
					var areaCode = rowInfo.data.areaCode;
					Ext.Msg.confirm('删除', '是否删除',
					function(btn){
						if(btn=='yes'){
							Ext.Ajax.request({
								url : '../basedev/deleteArea.do',
								params: {
									'areaId': areaId,
									'areaCode':areaCode
								},
								async: false,
								success : function(response) {
									var json = Ext.decode(response.responseText); 
									Ext.ux.Toast.msg(basedev.baseArea.i18n('basedev.notice.msginfo'), json.msg);
									if(json.success){
    									var selectResultPanel = Ext.getCmp("BaseData_baseArea_baseAreaPageElementGridPanel");
    									selectResultPanel.getPagingToolbar().moveFirst();
									}
								},
								exception : function(response) {
									var json = Ext.decode(response.responseText); 
									Ext.ux.Toast.msg(basedev.baseArea.i18n('basedev.notice.msginfo'), json.msg);
								}
							});
						}
					});
				}
			}*/]
		}, {
            hidden : true,
            dataIndex : 'areaId'
        },{
            text : '片区编号',
            dataIndex : 'areaCode'
        },{
            text : '片区名称',
            dataIndex : 'areaName'
        },
        {
            text : '启用',
            dataIndex : 'blFlag',
            renderer:function(value){
                if(value == '0') return '否';
                if(value == '1') return '是';
                return value;
            }
        },
        {
            text : '创建人',
            dataIndex : 'createUserCode'
        }, 
        {
            xtype : 'datecolumn',
            text : '创建时间',
            format :'Y-m-d H:i:s',
            width:130,
            dataIndex : 'createTime'
        },
        {
            text : '修改人',
            dataIndex : 'modifyUserCode'
        },
       {
        	xtype : 'datecolumn',
            text : '修改时间',
            format :'Y-m-d H:i:s',
            width:130,
            dataIndex : 'modifyTime'
        },{
            text : '备注',
            dataIndex : 'remark',
            flex:1
        }];
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


/**
 * 定义表单(新增)
 */
Ext.define('BaseData.baseArea.baseAreaAddForm', {
	extend: 'Ext.form.Panel',
	defaultType: 'textfield',
	defaults: {
    	margin:'5 10 5 10',
		colspan : 1,
		labelWidth: 70,
	   // validateOnBlur: true,
	    validateOnChange: false,
		width: 200
	},
    layout : {
    	type : 'table',
    	columns: 2
    },
	initComponent: function() {
		var me = this;
		me.items = [{ 
            xtype: 'textfield',
          //  msgTarget: 'side',
            name: 'areaCode',
            fieldLabel: '片区编号',
            allowBlank: false,
           validateOnBlur : true,
           maxLength:30,
            regex : /^[A-Za-z0-9]+$/,
            regexText : '该输入项只能输入数字和字母',
        validator : function(field){
            if(!field){
                return true;
            }
            var paramsObj = {areaCode : field};
            var valid = false;
            Ext.Ajax.request({
                url : basedev.realPath('getAreaCodeByInsert.do'),
                params: paramsObj,
                async : false,
                success : function(response) {
                    var result = Ext.JSON.decode(response.responseText);
                    if(result.success){
                    	var data =result.data;
                        if(data != null && data !=''){
                        	//Ext.ux.Toast.msg('提示', result.msg);
                        }else{
                        valid = true;
                        }
                    }else{
                         valid = true;
                    }
                },
                failure : function(response) {
                    Ext.ux.Toast.msg('提示',response.responseText, 'error');
                }
            });
            
            if(valid){
                return true;
            }
            return '该片区编号已存在';
         }
        },
        {
            xtype: 'textfield',
           // msgTarget: 'side',
            name: 'areaName',
            fieldLabel: '片区名称',
            allowBlank: false ,
            maxLength:10,
            validateOnBlur : true,
        validator : function(field){
            if(!field){
                return true;
            }
            var paramsObj = {areaName : field};
            var valid = false;
            Ext.Ajax.request({
                url : basedev.realPath('getAreaNameByInsert.do'),
                params: paramsObj,
                async : false,
                success : function(response) {
                    var result = Ext.JSON.decode(response.responseText);
                    if(result.success){
                        var data =result.data;
                        if(data != null && data !=''){
                           // Ext.ux.Toast.msg('提示', result.msg);
                        }else{
                        valid = true;
                        }
                    }else{
                         valid = true;
                    }
                },
                failure : function(response) {
                    Ext.ux.Toast.msg('提示',response.responseText, 'error');
                }
            });
            
            if(valid){
                return true;
            }
            return '该片区名称已存在';
         }
        },{
            xtype: 'textfield',
            name: 'remark',
              maxLength:30,
            fieldLabel: '备注'
        },{
            xtype: 'checkbox',
            name: 'blFlag',
            value:true,
            fieldLabel: '启用',
            allowBlank: true
        }];
		me.callParent();
	}
});
/**
 * 
 * 定义修改表单
 */
Ext.define('BaseData.baseArea.baseAreaChangeForm', {
	extend: 'Ext.form.Panel',
	defaultType: 'textfield',
	defaults: {
    	margin:'5 10 5 10',
		colspan : 1,
		labelWidth: 70,
	   // validateOnBlur: true,
	    validateOnChange: false,
		width: 200
	},
    layout : {
    	type : 'table',
    	columns: 2
    },
	initComponent: function() {
		var me = this;
		me.items = [{ 
            xtype: 'textfield',
            name: 'areaId',
            hidden : true,
             allowBlank: true 
            },{ 
            xtype: 'textfield',
            name: 'areaCode',
            fieldLabel: '片区编号',
            readOnly:true,
            allowBlank: false
         //   maxLength:30,
           /*,
              validateOnBlur : true,
        regex : /^[A-Za-z0-9]+$/,
        regexText : '该输入项只能输入数字和字母',
        validator : function(field){
            if(!field){
                return true;
            }
             var form = me.getForm();
            var areaId = form.findField('areaId').getValue();
            var paramsObj = {areaCode : field,areaId:areaId};
            var valid = false;
            Ext.Ajax.request({
                url : basedev.realPath('getAreaCodeByInsert.do'),
                params: paramsObj,
                async : false,
                success : function(response) {
                    var result = Ext.JSON.decode(response.responseText);
                    if(result.success){
                       var data =result.data;
                        if(data != null && data !=''){
                        	
                        	if(data.areaCode == field && data.areaId == areaId){
                        		valid = true;
                        	}else{
                            //Ext.ux.Toast.msg('提示', result.msg);
                        	}
                        }else{
                        valid = true;
                        }
                    }else{
                         valid = true;
                    }
                },
                failure : function(response) {
                    Ext.ux.Toast.msg('提示',response.responseText, 'error');
                }
            });
            
            if(valid){
                return true;
            }
            return '该片区编号已存在';
         }*/
        },{
            name: 'areaName',
            fieldLabel: '片区名称',
            allowBlank: false ,
            maxLength:10,
            validateOnBlur : true,
        validator : function(field){
            if(!field){
                return true;
            }
             var form = me.getForm();
            var areaId = form.findField('areaId').getValue();
            var paramsObj = {areaName : field};
            var valid = false;
            Ext.Ajax.request({
                url : basedev.realPath('getAreaNameByInsert.do'),
                params: paramsObj,
                async : false,
                success : function(response) {
                    var result = Ext.JSON.decode(response.responseText);
                    if(result.success){
                        var data =result.data;
                        if(data != null && data !=''){
                            if(data.areaName == field && data.areaId == areaId){
                                valid = true;
                            }else{
                           // Ext.ux.Toast.msg('提示', result.msg);
                            }
                        }else{
                        valid = true;
                        }
                    }else{
                         valid = true;
                    }
                },
                failure : function(response) {
                    Ext.ux.Toast.msg('提示',response.responseText, 'error');
                }
            });
            
            if(valid){
                return true;
            }
            return '该片区名称已存在';
         }
        },{
            xtype: 'textfield',
            name: 'remark',
            fieldLabel: '备注',
            maxLength:30,
            allowBlank: true 
        },{
            xtype: 'checkbox',
            name: 'blFlag',
            fieldLabel: '启用',
            value:true,
            allowBlank: true 
        }/*,{
            xtype: 'textfield',
            name: 'createUserCode',
            hidden : true,
            allowBlank: true 
        },
        {
            xtype:'datefield',
            name: 'createTime',
            hidden : true,
            allowBlank: true 
        }*/];
		me.callParent();
	}
});
/**
 * 
 * 新增窗口
 * 
 */
Ext.define('BaseData.baseArea.baseAreaAddWindow',{
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	closeAction : 'hide',
	resizable:false,
	title : '新增片区',
	height:320,
	width:550,
	addForm:null,//
	getAddForm:function(){
		if(Ext.isEmpty(this.addForm)){
			this.addForm = Ext.create('BaseData.baseArea.baseAreaAddForm');
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
				addForm.getForm().findField('blFlag').setValue(true);
			},
			beforeclose:function(){
				basedev.baseArea.REGION_LEVEL = null;
			}
		};
		me.callParent();
	},
	addRegion:function(){
		var me = this,
		baseAreaForm = me.down('form').getForm();
		var blFlag =  baseAreaForm.findField('blFlag').getValue();
        if( blFlag == true){
             blFlag = 1;
        }else{
            blFlag = 0;
        }
		if (baseAreaForm.isValid()){
				Ext.Ajax.request({
                url: '../basedev/addArea.do',
                method: 'post',
                params : {
                    areaCode:baseAreaForm.getValues()['areaCode'],
                    areaName:baseAreaForm.getValues()['areaName'],
                    remark:baseAreaForm.getValues()['remark'],
                    blFlag:blFlag
                },
                success : function(response) {
                    me.close();
                    var json = Ext.decode(response.responseText);
                    if(json.success){
                    Ext.ux.Toast.msg(basedev.baseArea.i18n('basedev.notice.msginfo'), json.msg);
                    var selectResultPanel = Ext.getCmp("BaseData_baseArea_baseAreaPageElementGridPanel");
                    selectResultPanel.getPagingToolbar().moveFirst();
                    }
                },
                exception : function(response) {
                    me.close();
                    var json = Ext.decode(response.responseText); 
                    Ext.ux.Toast.msg(basedev.baseArea.i18n('basedev.notice.msginfo'), json.msg);
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
Ext.define('BaseData.baseArea.baseAreaChangeWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	closeAction : 'hide',
	resizable:false,
	title : '修改片区',
	height:320,
	width:550,
	editData:null,//修改数据 
	changeForm:null,
	getChangeForm:function(){
		if(Ext.isEmpty(this.changeForm)){
			this.changeForm = Ext.create('BaseData.baseArea.baseAreaChangeForm');
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
        	 handler: me.updatebaseArea,
        	 scope: me
         }];
		me.listeners = {
			beforeshow:function(){
				changeForm.getForm().reset();
				changeForm.getForm().findField('areaId').setValue(me.editData.data.areaId);
				changeForm.getForm().findField('areaCode').setValue(me.editData.data.areaCode);
				changeForm.getForm().findField('areaName').setValue(me.editData.data.areaName);
				//changeForm.getForm().findField('createUserCode').setValue(me.editData.data.createUserCode);
				//changeForm.getForm().findField('createTime').setValue(me.editData.data.createTime);
				changeForm.getForm().findField('remark').setValue(me.editData.data.remark);
				    //启用
				    if(me.editData.data.blFlag==1){
                    changeForm.getForm().findField('blFlag').setValue(true);
                }else{
                	//停用
                    changeForm.getForm().findField('blFlag').setValue(false);
                  }
			},
			beforeclose:function(){
				if(!Ext.isEmpty(changeForm.store)){
					me.editData = null;
				}
			}
		};
		me.callParent();
	},
	updatebaseArea:function(){
		var me = this,
		baseAreaForm = me.down('form').getForm();
		var blFlag =  baseAreaForm.findField('blFlag').getValue();
		if( blFlag == true){
		     blFlag = 1;
		}else{
			blFlag = 0;
		}
		if (baseAreaForm.isValid()){
			Ext.Ajax.request({
				url: '../basedev/updateArea.do',
				method: 'post',
				params : {
					areaId :baseAreaForm.getValues()['areaId'],
					areaCode :baseAreaForm.getValues()['areaCode'],
					areaName :baseAreaForm.getValues()['areaName'],
					remark :baseAreaForm.getValues()['remark'],
					blFlag : blFlag
				},
				success : function(response) {
					me.close();
					var json = Ext.decode(response.responseText); 
					Ext.ux.Toast.msg(basedev.baseArea.i18n('basedev.notice.msginfo'), json.msg);
					if(json.success){
    					var selectResultPanel = Ext.getCmp("BaseData_baseArea_baseAreaPageElementGridPanel");
    					selectResultPanel.getPagingToolbar().moveFirst();
					}
				},
				exception : function(response) {
					me.close();
					var json = Ext.decode(response.responseText); 
					Ext.ux.Toast.msg(basedev.baseArea.i18n('basedev.notice.msginfo'), json.msg);
				}
			});
		}
	}
});
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp(basedev.baseArea.CONTENT_ID)) {
		return;
	};
	var searchForm = Ext.create('BaseData.baseArea.baseAreaForm');
	var resultGrid = Ext.create('BaseData.baseArea.baseAreaPageElementGridPanel');
	var content = Ext.create('Ext.panel.Panel', {
		id: basedev.baseArea.CONTENT_ID,
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
	Ext.getCmp('T_basedev-baseArea').add(content);
});