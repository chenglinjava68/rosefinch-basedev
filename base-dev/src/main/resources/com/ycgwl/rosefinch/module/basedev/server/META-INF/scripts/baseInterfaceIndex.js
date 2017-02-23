basedev.baseInterface.TAB_ID = "T_basedev-baseInterfaceIndex"; // 接口管理标签页ID
basedev.baseInterface.CONTENT_ID = "T_basedev-baseInterfaceIndex_content"; // 接口管理内容panel
																			// ID
// 接口查询表单
basedev.baseInterface.QUERY_BASE_INTERFACE_FORM_ID = "T_basedev-queryBaseInterfaceForm";
// 接口列表
basedev.baseInterface.QUERY_BASE_INTERFACE_RESULT_GRID_ID = "T_basedev-queryBaseInterfaceResultGrid";
                      
basedev.baseInterface.STATE_ADD = 'add'; // 新增
basedev.baseInterface.STATE_UPDATE = 'update'; // 修改

basedev.baseInterface.BASE_INTERFACE_FORM_ID = "T_basedev-baseInterfaceForm";
basedev.baseInterface.EDIT_BASE_INTERFACE_FORM_ID = "T_basedev-editBaseInterfaceForm";


/**
 * 接口 Model
 */
Ext.define('Basedev.baseInterface.BaseInterfaceModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		type : 'string',
		name : 'interfaceId'
	},{
		type : 'string',
		name : 'interfaceName'
	}, {
		type : 'string',
		name : 'interfaceCode'
	}, {
		type : 'string',
		name : 'url'
	}, {
		type : 'string',
		name : 'appKey'
	}, {
		name : 'createTime',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'number'
	}, {
		type : 'string',
		name : 'createUserCode'
	}, {
		name : 'modifyTime',
		defaultValue : new Date(),
		convert : dateConvert,
		type : 'number'
	}, {
		type : 'string',
		name : 'modifyUserCode'
	}, {
		type : 'string',
		name : 'remark'
	} ]
});

/**
 * 接口 Store   store是一个为Ext器件提供record对象的存储容器,行为和属性都很象数据表
 */
Ext.define('Basedev.baseInterface.BaseInterfaceStore',
				{
					extend : 'Ext.data.Store',
					model : 'Basedev.baseInterface.BaseInterfaceModel',
					pageSize : 10,
					autoLoad : false,
					proxy : {
						type : 'ajax',
						actionMethods : 'POST',
						url : basedev.realPath("baseInterface.do"),
						reader : {
							type : 'json',
							root : 'list',
							totalProperty : 'count' ////////////////////////////////
						}
					},
					listeners : {
						beforeload : function(store, operation, eOpts) {
							var queryForm = Ext.getCmp(basedev.baseInterface.QUERY_BASE_INTERFACE_FORM_ID);
							if (queryForm != null) {
								var interfaceName = queryForm.getForm().findField('interfaceName').getValue();
								var interfaceCode = queryForm.getForm().findField('interfaceCode').getValue();
								var url = queryForm.getForm().findField('url').getValue();
								var appKey = queryForm.getForm().findField('appKey').getValue();
								var createUserCode = queryForm.getForm().findField('createUserCode').getValue();
								var modifyUserCode = queryForm.getForm().findField('modifyUserCode').getValue();
								Ext.apply(operation, {
									params : {
										'q_str_interfaceName' : interfaceName,
										'q_str_interfaceCode' : interfaceCode,
										'q_str_url' : url,
										'q_str_appKey' : appKey,
										'q_str_createUserCode' : createUserCode,
										'q_str_modifyUserCode' : modifyUserCode
									}
								});
							}
						}
					}
				});

/**
 * 定义有查询条件的Form
 */
Ext.define('Basedev.baseInterface.QueryBaseInterfaceForm',{
					extend : 'Ext.form.Panel',
					id : basedev.baseInterface.QUERY_BASE_INTERFACE_FORM_ID,
					frame : true,
					title : '查询条件',
					/*
					 * defaults: { margin:'5 10 5 10' },
					 */
					layout : 'column', // 列布局，跟table标签的效果有些类似，可以将元素按照表格是行,列形式展现。
					defaultType : 'textfield',
					// 进行组件初始化操作
					initComponent : function() {
						var me = this;
						me.items = [
								{
									name : 'interfaceName',
									fieldLabel : '接口名称',
									labelWidth : 70,
									columnWidth : .3
								},{
									name : 'interfaceCode',
									fieldLabel : '接口简码',
									labelWidth : 70,
									columnWidth : .3
								},{
									name : 'url',
									fieldLabel : 'url',
									labelWidth : 70,
									columnWidth : .3
								},{
									name : 'appKey',
									fieldLabel : '应用主键',
									labelWidth : 70,
									columnWidth : .3
								},{
									name : 'createUserCode',
									fieldLabel : '创建人',
									labelWidth : 70,
									columnWidth : .3
								},{
									name : 'modifyUserCode',
									fieldLabel : '修改人',
									labelWidth : 70,
									columnWidth : .3
								},
								{
									xtype : 'button',
									cls : 'yellow_button',
									text : '查询',
									width : 70,
									handler : function() {
										var selectResultPanel = Ext.getCmp(basedev.baseInterface.QUERY_BASE_INTERFACE_RESULT_GRID_ID);
										selectResultPanel.setVisible(true);
										selectResultPanel.getPagingToolbar().moveFirst();
									}
								// getCmp方法用来获得一个Ext组件，也就是一个已经在页面中初始化了的Component或其子类的对象，getCmp方法中只有一个参数，也就是组件的id。
								// •getCmp方法其实是Ext.ComponentMgr.get方法的简写形式。
								} ];
						me.callParent();// 调用超类
					}
				});

/**
 * 接口 列表 表格 //////////////////////////////////////////////////////////仅显示出来，没有数据
 */
Ext.define('Basedev.baseInterface.QueryBaseInterfaceResultGrid',{
					extend : 'Ext.grid.Panel',
					id : basedev.baseInterface.QUERY_BASE_INTERFACE_RESULT_GRID_ID,
					title : '接口查询结果',
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
					/*
					 * layout: { type: 'fit' },
					 */
					columns : [
							{
								xtype : 'actioncolumn',
								// flex: 1,
								text : '操作',
								align : 'center',
								items : [
										{
											iconCls : 'deppon_icons_edit',
											tooltip : '修改',// 修改
											handler : function(gridView,rowIndex, colIndex) {
												var editEaseInterfaceWindow = Ext.getCmp(basedev.baseInterface.QUERY_BASE_INTERFACE_RESULT_GRID_ID)
														.getEditBaseInterfaceWindow();
												editEaseInterfaceWindow.setTitle('编辑产品');
												var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);

												editEaseInterfaceWindow.getEditBaseInterfaceForm().setOperatorType(
																basedev.baseInterface.STATE_UPDATE,rowInfo);
												// 打开窗口
												editEaseInterfaceWindow.show();
											}
										},
										{
											iconCls : 'deppon_icons_delete',
											tooltip : '删除',// 删除
											handler : function(grid, rowIndex,colIndex) {
												/* 删除 */
												var rowInfo = Ext.getCmp(basedev.baseInterface.QUERY_BASE_INTERFACE_RESULT_GRID_ID).store.getAt(rowIndex);
												var interfaceId = rowInfo.data.interfaceId;
												var appKey = rowInfo.data.appKey;
												Ext.Msg.confirm('确认','确认删除吗？',function(btn) {
																	if (btn == 'yes') {
																		Ext.Ajax.request({
//																					url : basedev.realPath('baseDelInterfaceById.do'),
																			        url : '../basedev/baseDelInterfaceById.do',
																					method : 'post',
																					params : {
																						'interfaceId': interfaceId,
																						'appKey':appKey
																					},
																					success : function(response) {
																						var result = Ext.JSON.decode(response.responseText);
																						Ext.ux.Toast.msg('123', json.msg, 'success');
																						if (result.success) {
																							Ext.ux.Toast.msg('提示','删除成功');
																							var grid = Ext.getCmp(basedev.baseInterface.QUERY_BASE_INTERFACE_RESULT_GRID_ID);
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
										} ]
							}, {
								hidden : true,
								dataIndex : 'interfaceId'
							}, {
								text : '接口名称',
								// width : .3,
								sortable : true,
								flex : 25 / 100,
								// renderer : change,
								dataIndex : 'interfaceName' // dataIndex
															// ：设置列与数据集中数据记录的对应关系，值为数据记录中的字段名称。如果没有设置该项则使用列索引与数据记录中字段的索引进行对应
							}, {
								text : '接口简码',
								//hidden : true,
								dataIndex : 'interfaceCode'
							}, {
								text : 'url',
								// width : .3,
								//sortable : true,
								flex : 25 / 100,
								// renderer : change,
								dataIndex : 'url'
							}, {
								text : '应用主键',
								dataIndex : 'appKey'
							}, {
								format : 'Y-m-d H:i:s',
								text : '创建时间',
								// width : .3,
								sortable : true,
								flex : 25 / 100,
								// renderer : change,
								dataIndex : 'createTime'
							}, {
								text : '创建人',
								// width : .3,
								//sortable : true,
								flex : 25 / 100,
								// renderer : change,
								dataIndex : 'createUserCode'
							},{
								xtype : 'datecolumn',
								format : 'Y-m-d H:i:s',
								text : '修改时间',
								flex : 25 / 100,
								dataIndex : 'modifyTime'
							}, {
								text : '修改人',
								// width : .3,
								//sortable : true,
								flex : 25 / 100,
								// renderer : change,
								dataIndex : 'modifyUserCode'
							} ],
					 /**
					  * 编辑接口的窗口
					  */
					editBaseInterfaceWindow : null,
					getEditBaseInterfaceWindow : function() {
						me = this;
						if (Ext.isEmpty(me.editBaseInterfaceWindow)) {
							me.editBaseInterfaceWindow = Ext.create('Basedev.baseInterface.EditBaseInterfaceWindow');
						}
						return me.editBaseInterfaceWindow;
					},
                    /**
                     * 分页 展示信息
                     */
					pagingToolbar : null,
					getPagingToolbar : function() {
						var me = this;
						if (me.pagingToolbar == null) {
							me.pagingToolbar = Ext.create(
									'Deppon.StandardPaging', {   /////////////////////////////////
										store : me.store
									});
						}

						return me.pagingToolbar;
					},
					
					constructor : function(config) {
						var me = this, cfg = Ext.apply({}, config);
						me.store = Ext
								.create('Basedev.baseInterface.BaseInterfaceStore');
						me.tbar = [ {
							text : '新增',
							handler : function() {
								var editBaseInterfaceWindow = me.getEditBaseInterfaceWindow();
								editBaseInterfaceWindow.setTitle('新增接口');
								
								var editBaseInterfaceForm = editBaseInterfaceWindow.getEditBaseInterfaceForm();
								editBaseInterfaceForm.setOperatorType(basedev.baseInterface.STATE_ADD);
								editBaseInterfaceWindow.show();
							}
						} ];
						me.bbar = me.getPagingToolbar();
						me.callParent(cfg);
					}
				});



/**
 * 接口Form
 */
Ext.define('Basedev.baseInterface.BaseInterfaceForm', {
	extend : 'Ext.form.Panel',
	id : basedev.baseInterface.BASE_INTERFACE_FORM_ID,
	// title : '查看接口',
	frame : true,
	defaults : {
		margin : '5 10 5 10', // 用于设置对象标签之间距离间隔 (上下左右)
		labelWidth : 120,
		readOnly : true
	},
	layout : {
		type : 'table',
		columns : 2
	},
	defaultType : 'textfield',

	items : [ {
		xtype : 'textfield',
		name : 'interfaceCode',
		fieldLabel : '接口编码'
	}, {
		xtype : 'textfield',
		name : 'interfaceName',
		fieldLabel : '接口名称'
	}, {
		xtype : "textfield",
		name : 'url',
		fieldLabel : 'url'
	}, {
		xtype : "textfield",
		name : 'appKey',
		fieldLabel : '应用主键'
	}, {
		xtype : "textfield",
		name : 'createTime',
		fieldLable : '创建时间'
	}, {
		xtype : "textfield",
		name : 'createUserCode',
		fieldLable : '创建人'
	}, {
		xtype : "textfield",
		name : 'modifyUserCode',
		fieldLable : '修改人'
	}, {
		xtype : "textfield",
		name : 'modifyTime',
		fieldLable : '修改时间'
	}, {
		xtype : 'textfield',
		name : 'remark',
		fieldLabel : '备注'
	} ]
});


/**
 * 用来编辑的Form框架
 */
Ext.define('Basedev.baseInterface.EditBaseInterfaceWindow',{
					extend : 'Ext.window.Window',
					width : 700,
					modal : true,
					closeAction : 'hide',
					editBaseInterfaceForm : null,
					getEditBaseInterfaceForm : function() {
						if (Ext.isEmpty(this.editBaseInterfaceForm)) {
							this.editBaseInterfaceForm = Ext.create("Basedev.baseInterface.EditBaseInterfaceForm");
						}        
						return this.editBaseInterfaceForm;
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
											me.hide();
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
							this.saveButton = Ext
									.create(
											'Ext.button.Button',
											{
												cls : 'yellow_button',
												text : '保存',
												handler : function() {
													var baseInterfaceForm = me.getEditBaseInterfaceForm().getForm();

													// 校验产品表单
													if (!baseInterfaceForm.isValid()) {
														return;
													}

													var data = baseInterfaceForm.getValues();

													var url = '';
													if (me.getEditBaseInterfaceForm().getOperatorType() == basedev.baseInterface.STATE_ADD) {
														url = basedev.realPath('baseInsertInterface.do'); // ///////////////////////
													} else {
														url = basedev.realPath('baseUpdateInterface.do'); // ///////////////////////
													}

													Ext.Ajax.request({
																url : url,
																params : {interfaceId :me.getEditBaseInterfaceForm().getValues()['interfaceId'],
																	interfaceCode :me.getEditBaseInterfaceForm().getValues()['interfaceCode'],
																	interfaceName:me.getEditBaseInterfaceForm().getValues()['interfaceName'],
																	url:me.getEditBaseInterfaceForm().getValues()['url'],
																	appKey:me.getEditBaseInterfaceForm().getValues()['appKey'],
																	remark:me.getEditBaseInterfaceForm().getValues()['remark'],
																    operationType:"1"						
															},
//																headers : {
//																	'Content-Type' : 'application/json',
//																	'Accept' : 'application/json'
//																},
																success : function(response) {
																	var result = Ext.JSON.decode(response.responseText);

																	if (result.success) {
																		Ext.ux.Toast.msg('提示','保存成功');
																		me.hide();

																		var grid = Ext.getCmp(basedev.baseInterface.QUERY_BASE_INTERFACE_RESULT_GRID_ID);
																		// 加载表格
																		grid.getStore().load();
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
						me.items = [ me.getEditBaseInterfaceForm() ];
						me.buttons = [ me.getCancelButton(), me.getSaveButton() ];
						me.callParent([ cfg ]);
					}
				});


/**
 * 接口 新增/编辑 的Form
 */
Ext.define('Basedev.baseInterface.EditBaseInterfaceForm',
		{extend : 'Ext.form.Panel',
					id : basedev.baseInterface.EDIT_BASE_INTERFACE_FORM_ID,
					title : '查看接口',
					frame : true,
					defaults : {
						margin : '5 10 5 10',
						labelWidth : 120,
						allowBlank : true,
						// validateOnBlur: true,
						validateOnChange : false
					},
					layout : {
						type : 'table',
						columns : 2
					},
					defaultType : 'textfield',

					items : [
							{
								xtype : 'textfield',
								name : 'interfaceId',
								fieldLabel : '接口Id',
								maxLength : 100,
								//allowBlank : false,
								validateOnBlur : true,
							},
							{
								xtype : 'textfield',
								name : 'interfaceCode',
								fieldLabel : '接口编码',
								maxLength : 100, // maxLength为最大输入长度，minLength为最小输入长度
								allowBlank : false, // 当allowBlank属性设置为false的时候，该输入项就为必填输入项
								validateOnBlur : true, //
								
							},
							{
								xtype : 'textfield',
								name : 'interfaceName',
								fieldLabel : '接口名称',
								maxLength : 100,
								allowBlank : false,
								validateOnBlur : true,
								
							}, {
								xtype : 'textfield',
								name : 'url',
								fieldLabel : 'url',
								maxLength : 100,
								allowBlank : false,
								colspan : 2
							}, {
								xtype : 'textfield',
								name : 'appKey',
								fieldLabel : '应用主键',
								maxLength : 100,
								allowBlank :false,
								colspan : 2
							}, {
								xtype : 'textfield',
								name : 'remark',
								fieldLabel : '备注',
								width : 500,
								maxLength : 100,
								allowBlank : true,
								colspan : 2
							} ],
							
					operatorType : null,
					setOperatorType : function(state, record) {
						this.operatorType = state;
						var editBaseInterfaceForm = this.getForm();
						// 表单重置
						editBaseInterfaceForm.reset();

						if (state == basedev.baseInterface.STATE_ADD) {
							var baseInterfaceModel = Ext.create('Basedev.baseInterface.BaseInterfaceModel');
							editBaseInterfaceForm.loadRecord(baseInterfaceModel);
							this.setFormFieldsReadOnly(true);
						} else if (state == basedev.baseInterface.STATE_UPDATE) {
							var baseInterfaceModel = Ext.create('Basedev.baseInterface.BaseInterfaceModel',record.raw);
							editBaseInterfaceForm.loadRecord(baseInterfaceModel);
							this.setFormFieldsReadOnly(true);
						}
					},
					getOperatorType : function() {
						return this.operatorType;
					},
					setFormFieldsReadOnly : function(flag) {
						var form = this.getForm();
						form.findField('interfaceId').setReadOnly(flag);
					}
				});


/**
 * 查看窗口
 */
Ext.define('Basedev.baseInterfaece.BaseInterfaceWindow', {
	extend : 'Ext.window.Window',
	width : 700,
	modal : true,
	closeAction : 'hide',
	baseInterfaceForm : null,
	getBaseInterfaceForm : function() {
		if (Ext.isEmpty(this.baseInterfaceorm)) {
			this.baseInterfaceForm = Ext.create("Basedev.baseInterface.BaseInterfaceForm");
		}
		return this.baseInterfaceForm;
	},
	// 关闭按钮
	cancelButton : null,
	getCancelButton : function() {
		var me = this;
		if (Ext.isEmpty(this.cancelButton)) {
			this.cancelButton = Ext.create('Ext.button.Button', {
				text : '关闭',
				handler : function() {
					me.hide();
				}
			});
		}

		return this.cancelButton;
	},
	constructor : function(config) {
		var me = this, cfg = Ext.apply({}, config);
		me.items = [ me.getBaseInterfaceForm() ];
		me.buttons = [ me.getCancelButton() ];
		me.callParent([ cfg ]);
	}
});



Ext.onReady(function() {
	Ext.QuickTips.init();

	if (Ext.getCmp(basedev.baseInterface.CONTENT_ID)) {
		return;
	}
	;

	var queryBaseInterfaceForm = Ext.create('Basedev.baseInterface.QueryBaseInterfaceForm');
	var queryBaseInterfaceResultGrid = Ext.create('Basedev.baseInterface.QueryBaseInterfaceResultGrid');

	var content = Ext.create('Ext.panel.Panel', {
		id : basedev.baseInterface.CONTENT_ID,
		cls : "panelContentNToolbar",
		bodyCls : 'panelContentNToolbar-body',
		getQueryBaseInterfaceForm : function() {
			return queryBaseInterfaceForm;
		},
		getQueryBaseInterfaceResultGrid : function() {
			return queryBaseInterfaceResultGrid;
		},
		items : [ queryBaseInterfaceForm, queryBaseInterfaceResultGrid ]
	});

	Ext.getCmp(basedev.baseInterface.TAB_ID).add(content);
	// 加载表格数据
	queryBaseInterfaceResultGrid.getStore().load();
});
