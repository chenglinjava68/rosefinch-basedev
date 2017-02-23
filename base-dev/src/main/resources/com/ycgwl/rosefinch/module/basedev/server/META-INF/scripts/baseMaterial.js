/** 此模块主页面的ID */
basedev.baseMaterial.CONTENT_ID  = 'T_basedev-baseMaterial_content';
basedev.baseMaterial.FORMAT_TIME  = 'Y-m-d H:i:s'; //格式化时间字符串
basedev.baseMaterial.REGION_LEVEL = null;

/** 物料Model */
Ext.define('BaseData.baseMaterial.baseMaterialModule', {
	extend : 'Ext.data.Model',
	fields : [{
        name : 'id',
        type : 'long'
    }, {
        name : 'goodsCode',
        type : 'string'
    }, {
        name : 'goodsName',
        type : 'string'
    }, {//物料类别
        name : 'category',
        type : 'int'
    },{//物料类别名称
        name : 'categoryName',
        type : 'string'
    }, {
        name : 'unitNumber'
    }, {
        name : 'unitName'
    }, {
        name : 'unitMoney'
    }, {
        name : 'blBalance'
    }, {
        name : 'remark',
        type : 'string'
    }, {
        name : 'delFlag',
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
Ext.define('BaseData.baseMaterial.baseMaterialStore',{
	extend: 'Ext.data.Store',
	pageSize:10,
	model : 'BaseData.baseMaterial.baseMaterialModule',
	autoLoad:true,
	proxy : {
		url : '../basedev/getbaseMaterialList.do',
		type : 'ajax',
		reader : {
			type : 'json',
			root : 'list',
			totalProperty : 'count'
		}
	},
	listeners: {
		beforeload : function(store, operation, eOpts) {
			var queryForm = Ext.getCmp("BaseData_baseMaterial_baseMaterialForm_ID"); //查询表单
			if (queryForm != null) {
				var q_str_goodsCode = queryForm.getForm().findField('q_str_goodsCode').getValue();
                var q_str_goodsName = queryForm.getForm().findField('q_str_goodsName').getValue();
				Ext.apply(operation, {
					params : {
						'q_str_goodsCode' : q_str_goodsCode,
                        'q_str_goodsName':q_str_goodsName
					}
				});	
			}
		}
	}
});

/**
 * 查询表单
 */
Ext.define('BaseData.baseMaterial.baseMaterialForm', {
	extend:'Ext.form.Panel',
	id: 'BaseData_baseMaterial_baseMaterialForm_ID',
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
            name: 'q_str_goodsCode',
            fieldLabel:'物料编号',
            columnWidth: .2
        },{
            name: 'q_str_goodsName',
            fieldLabel:'物料名称',
            columnWidth: .2

        },{
			xtype : 'button',
			cls: 'yellow_button',
			text:"查询",
			width : 70,
			// 查询按钮的响应事件：
			handler: function() {
				if(me.getForm().isValid()){
					var selectResultPanel = Ext.getCmp("BaseData_baseMaterial_baseMaterialPageElementGridPanel");
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
Ext.define('BaseData.baseMaterial.baseMaterialPageElementGridPanel', {
	extend: 'Ext.grid.Panel',
	id:'BaseData_baseMaterial_baseMaterialPageElementGridPanel',
	title : '查询结果',
	frame: true,
	sortableColumns:false,
	enableColumnHide:false,
	enableColumnMove:false,
	selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SIMPLE"}),
	//selType : "rowmodel", // 选择类型设置为：行选择
	stripeRows : true, // 交替行效果
	baseMaterialAddWindow : null,
	baseMaterialChangeWindow: null,
	getbaseMaterialAddWindow : function(){
		if(this.baseMaterialAddWindow==null){
			this.baseMaterialAddWindow = Ext.create('BaseData.baseMaterial.baseMaterialAddWindow');
		}
		return this.baseMaterialAddWindow;
	},
	getbaseMaterialChangeWindow : function(data){
		if(this.baseMaterialChangeWindow==null){
			this.baseMaterialChangeWindow = Ext.create('BaseData.baseMaterial.baseMaterialChangeWindow');
		}
		this.baseMaterialChangeWindow.editData = data;
		return this.baseMaterialChangeWindow;
	},

	// 新增按钮
	addButton: null,
	getAddButton: function() {
		var me = this;
		if(Ext.isEmpty(me.addButton)){
			me.addButton = Ext.create('Ext.Button', {
				text :'新增',//新增
				handler : function() {
					me.getbaseMaterialAddWindow().show();
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
                            Ext.ux.Toast.msg("提示", '请选择要删除的数据');
                            return ;
                        } else {
                            var ids =[];
                            for(var i=0;i<accList.length;i++){
                               ids[i] = accList[i].get('id');
                            }
                              Ext.Msg.confirm('删除', '是否批量删除',
                    function(btn){
                        if(btn=='yes'){
                             Ext.Ajax.request({
                                url : "../basedev/batchDeleteByIds.do",
                                params : {
                                    'ids' :ids
                                },
                                success : function(response) {
                                    var data = Ext.JSON
                                            .decode(response.responseText);
                                    if (data.success) {
                                        Ext.ux.Toast.msg("提示", data.msg);
                                        var selectResultPanel = Ext
                                                .getCmp("BaseData_baseMaterial_baseMaterialPageElementGridPanel");
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
		me.store = Ext.create('BaseData.baseMaterial.baseMaterialStore');
		me.bbar = me.getPagingToolbar();
		me.tbar = [me.getAddButton()/*,me.getDeleteButton()*/];
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
				tooltip: '修改物料',//修改
				handler: function(grid, rowIndex, colIndex) { // 修改按钮
					var rec = grid.getStore().getAt(rowIndex); 
					Ext.Ajax.request({
						url : "../basedev/preUpdateGoods.do",
						params : {
							'id' : rec.get('id')
						},
						success : function(response) {
							var data  =  Ext.JSON.decode(response.responseText);
							if(data.success){
								var xWindow = me.getbaseMaterialChangeWindow(data);
								xWindow.show();
							}else{
								Ext.Msg.alert(basedev.baseMaterial.i18n('dpap.message.share.alertInfo'),basedev.baseMaterial.i18n('dpap.message.instationMsg.plsChoiceMsg')); 
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
					var rowInfo = Ext.getCmp('BaseData_baseMaterial_baseMaterialPageElementGridPanel').store.getAt(rowIndex);
					var id = rowInfo.data.id;
					Ext.Msg.confirm('删除', '是否删除',
					function(btn){
						if(btn=='yes'){
							Ext.Ajax.request({
								url : '../basedev/deletegoods.do',
								params: {
									'id': id
								},
								async: false,
								success : function(response) {
									var json = Ext.decode(response.responseText); 
									Ext.ux.Toast.msg(basedev.baseMaterial.i18n('basedev.notice.msginfo'), json.msg);
									if(json.success){
									var selectResultPanel = Ext.getCmp("BaseData_baseMaterial_baseMaterialPageElementGridPanel");
									selectResultPanel.getPagingToolbar().moveFirst();
									}
								},
								exception : function(response) {
									var json = Ext.decode(response.responseText); 
									Ext.ux.Toast.msg(basedev.baseMaterial.i18n('basedev.notice.msginfo'), json.msg);
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
            text : '物料编号',
            dataIndex : 'goodsCode'
        },{
            text : '物料名称',
            dataIndex : 'goodsName'
        },{
            text : '单位数量',
            dataIndex : 'unitNumber'
        },{
            text : '单位',
            dataIndex : 'unitName'
        },{
            text : '单价',
            dataIndex : 'unitMoney'
        },{
            hidden : true,
            dataIndex : 'category'
        },{
            text : '物料类别',
            dataIndex : 'categoryName'
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
            dataIndex : 'remark'
        }];
		var cfg = Ext.apply({}, config);
		me.callParent([cfg]);
	}
});


/**
 * 定义表单(新增)
 */
Ext.define('BaseData.baseMaterial.baseMaterialAddForm', {
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
            name: 'goodsCode',
            fieldLabel: '物料编号',
            allowBlank: false,
             maxLength:15,
           validateOnBlur : true,
        regex : /^[A-Za-z0-9]+$/,
       // regexText : '该输入项只能输入数字',
        validator : function(field){
            if(!field){
                return true;
            }
            if(!/^[A-Za-z0-9]+$/.test(field)){
            	return "该项的输入只能是数字或者字母";
            }
              if(field.length>15){
                return ;
            }
            var paramsObj = {goodsCode : field};
            var valid = false;
            Ext.Ajax.request({
                url : basedev.realPath('getMaterialCodeByInsert.do'),
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
            return '该物料编号已存在';
         }
        },
        {
            xtype: 'textfield',
           // msgTarget: 'side',
            name: 'goodsName',
            fieldLabel: '物料名称',
             maxLength:10,
            allowBlank: false ,
            validateOnBlur : true,
        validator : function(field){
            if(!field){
                return true;
            }
            var paramsObj = {goodsName : field};
            var valid = false;
            Ext.Ajax.request({
                url : basedev.realPath('getMaterialNameByInsert.do'),
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
            return '该物料名称已存在';
         }
        },{
            xtype: 'numberfield',
            name: 'unitNumber',
             maxLength:10,
            fieldLabel: '单位数量',
            minValue : 0,//可输入的最小值
            decimalPrecision: 0,
             allowBlank: true
        },{
            xtype: 'textfield',
            name: 'unitName',
            fieldLabel: '单位',
              maxLength:3,
             allowBlank: true
        },{
            xtype: 'numberfield',
            name: 'unitMoney',
            fieldLabel: '单价',
            minValue : 0,//可输入的最小值
              maxLength:10,
             allowBlank: true
        },{
            dictType : 'MATERIAL_TYPE',
            xtype : "dictcombo",
            name: 'category',
            editable:false,
            fieldLabel: '物料类别',
            allowBlank: false
        },{
            xtype: 'textarea',
            name: 'remark',
            fieldLabel: '备注',
            maxLength:300,
            allowBlank: true
        }];
		me.callParent();
	}
});
/**
 * 
 * 定义修改表单
 */
Ext.define('BaseData.baseMaterial.baseMaterialChangeForm', {
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
            name: 'id',
            hidden : true,
             allowBlank: true 
            },{ 
            xtype: 'textfield',
            name: 'goodsCode',
            readOnly:true,
            fieldLabel: '物料编号',
            allowBlank: false
           /*  maxLength:15,
              validateOnBlur : true,
        regex : /^[0-9]+$/,
    //    regexText : '该输入项只能输入数字',
        validator : function(field){
            if(!field){
                return true;
            }
              if(!/^[0-9]+$/.test(field)){
                return "该输入项只能输入数字";
            }
            if(field.length>15){
            	return ;
            }
              if(field<=0){
                return "不能输入小于零的数字";
            }
        
            var id= me.getForm().findField('id').getValue();
            var paramsObj = {goodsCode : field};
            var valid = false;
            Ext.Ajax.request({
                url : basedev.realPath('getMaterialCodeByInsert.do'),
                params: paramsObj,
                async : false,
                success : function(response) {
                    var result = Ext.JSON.decode(response.responseText);
                    if(result.success){
                       var data =result.data;
                        if(data != null && data !=''){
                        	if(data.goodsCode == field && data.id == id){
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
            return '该物料编号已存在';
         }*/
        },{
            name: 'goodsName',
            fieldLabel: '物料名称',
            allowBlank: false ,
             maxLength:10,
            validateOnBlur : true,
        validator : function(field){
            if(!field){
                return true;
            }
             var id= me.getForm().findField('id').getValue();
            var paramsObj = {goodsName : field};
            var valid = false;
            Ext.Ajax.request({
                url : basedev.realPath('getMaterialNameByInsert.do'),
                params: paramsObj,
                async : false,
                success : function(response) {
                    var result = Ext.JSON.decode(response.responseText);
                    if(result.success){
                        var data =result.data;
                        if(data != null && data !=''){
                            if(data.goodsName == field && data.id == id){
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
            return '该物料名称已存在';
         }
        },{
            xtype: 'numberfield',
            name: 'unitNumber',
            fieldLabel: '单位数量',
             maxLength:10,
             minValue : 0,//可输入的最小值
             decimalPrecision: 0,
             allowBlank: true
        },{
            xtype: 'textfield',
            name: 'unitName',
            fieldLabel: '单位',
             maxLength:3,
             allowBlank: true
        },{
            xtype: 'numberfield',
            name: 'unitMoney',
            fieldLabel: '单价',
             maxLength:10,
             minValue : 0,//可输入的最小值
             allowBlank: true
        },{
            dictType : 'MATERIAL_TYPE',
            xtype : "dictcombo",
            name: 'category',
            editable:false,
            fieldLabel: '物料类别',
            allowBlank: false
        },{
            xtype: 'textarea',
            name: 'remark',
            fieldLabel: '备注',
            maxLength:300,
            allowBlank: true
        }];
		me.callParent();
	}
});
/**
 * 
 * 新增窗口
 * 
 */
Ext.define('BaseData.baseMaterial.baseMaterialAddWindow',{
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	closeAction : 'hide',
	resizable:false,
	title : '新增物料',
	height:320,
	width:550,
	addForm:null,//
	getAddForm:function(){
		if(Ext.isEmpty(this.addForm)){
			this.addForm = Ext.create('BaseData.baseMaterial.baseMaterialAddForm');
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
				basedev.baseMaterial.REGION_LEVEL = null;
			}
		};
		me.callParent();
	},
	addRegion:function(){
		var me = this,
		baseMaterialForm = me.down('form').getForm();
		if (baseMaterialForm.isValid()){
				Ext.Ajax.request({
                url: '../basedev/addGoods.do',
                method: 'post',
                params : {
                    goodsCode:baseMaterialForm.getValues()['goodsCode'],
                    goodsName:baseMaterialForm.getValues()['goodsName'],
                    unitNumber:baseMaterialForm.getValues()['unitNumber'],
                    unitName:baseMaterialForm.getValues()['unitName'],
                    unitMoney:baseMaterialForm.getValues()['unitMoney'],
                    category:baseMaterialForm.getValues()['category'],
                    remark:baseMaterialForm.getValues()['remark']
                },
                success : function(response) {
                    me.close();
                    var json = Ext.decode(response.responseText);
                     Ext.ux.Toast.msg(basedev.baseMaterial.i18n('basedev.notice.msginfo'), json.msg);
                    if(json.success){
                    	 var selectResultPanel = Ext.getCmp("BaseData_baseMaterial_baseMaterialPageElementGridPanel");
                    selectResultPanel.getPagingToolbar().moveFirst();
                    }
                },
                exception : function(response) {
                    me.close();
                    var json = Ext.decode(response.responseText); 
                    Ext.ux.Toast.msg(basedev.baseMaterial.i18n('basedev.notice.msginfo'), json.msg);
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
Ext.define('BaseData.baseMaterial.baseMaterialChangeWindow', {
	extend : 'Ext.window.Window',
	closable : true,
	modal : true,
	closeAction : 'hide',
	resizable:false,
	title : '修改物料',
	height:320,
	width:550,
	editData:null,//修改数据 
	changeForm:null,
	getChangeForm:function(){
		if(Ext.isEmpty(this.changeForm)){
			this.changeForm = Ext.create('BaseData.baseMaterial.baseMaterialChangeForm');
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
        	 handler: me.updatebaseMaterial,
        	 scope: me
         }];
		me.listeners = {
			beforeshow:function(){
				changeForm.getForm().reset();
				changeForm.getForm().findField('id').setValue(me.editData.data.id);
				changeForm.getForm().findField('goodsCode').setValue(me.editData.data.goodsCode);
				changeForm.getForm().findField('goodsName').setValue(me.editData.data.goodsName);
				changeForm.getForm().findField('unitNumber').setValue(me.editData.data.unitNumber);
				changeForm.getForm().findField('unitName').setValue(me.editData.data.unitName);
				changeForm.getForm().findField('unitMoney').setValue(me.editData.data.unitMoney);
				changeForm.getForm().findField('category').setValue(me.editData.data.category+'');
				changeForm.getForm().findField('remark').setValue(me.editData.data.remark);
				//changeForm.getForm().findField('goodsCode').disable();
			},
			beforeclose:function(){
				if(!Ext.isEmpty(changeForm.store)){
					me.editData = null;
				}
			}
		};
		me.callParent();
	},
	updatebaseMaterial:function(){
		var me = this,
		baseMaterialForm = me.down('form').getForm();
		if (baseMaterialForm.isValid()){
			Ext.Ajax.request({
				url: '../basedev/updateGoods.do',
				method: 'post',
				params : {
					id :baseMaterialForm.getValues()['id'],
				    goodsCode:baseMaterialForm.getValues()['goodsCode'],
                    goodsName:baseMaterialForm.getValues()['goodsName'],
                    unitNumber:baseMaterialForm.getValues()['unitNumber'],
                    unitName:baseMaterialForm.getValues()['unitName'],
                    unitMoney:baseMaterialForm.getValues()['unitMoney'],
                    category:baseMaterialForm.getValues()['category'],
                    remark:baseMaterialForm.getValues()['remark']
				},
				success : function(response) {
					me.close();
					var json = Ext.decode(response.responseText); 
					Ext.ux.Toast.msg(basedev.baseMaterial.i18n('basedev.notice.msginfo'), json.msg);
					if(json.success){
    					var selectResultPanel = Ext.getCmp("BaseData_baseMaterial_baseMaterialPageElementGridPanel");
    					selectResultPanel.getPagingToolbar().moveFirst();
					}
					
				},
				exception : function(response) {
					me.close();
					var json = Ext.decode(response.responseText); 
					Ext.ux.Toast.msg(basedev.baseMaterial.i18n('basedev.notice.msginfo'), json.msg);
				}
			});
		}
	}
});
Ext.onReady(function() {
	Ext.QuickTips.init();
	if (Ext.getCmp(basedev.baseMaterial.CONTENT_ID)) {
		return;
	};
	var searchForm = Ext.create('BaseData.baseMaterial.baseMaterialForm');
	var resultGrid = Ext.create('BaseData.baseMaterial.baseMaterialPageElementGridPanel');
	var content = Ext.create('Ext.panel.Panel', {
		id: basedev.baseMaterial.CONTENT_ID,
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
	Ext.getCmp('T_basedev-baseMaterial').add(content);
});