basedev.getUnloadTime.TAB_ID="T_basedev-getUnloadTimeIndex";  // 配置管理标签页ID
basedev.getUnloadTime.CONTENT_ID = "T_basedev-getUnloadTimeIndex_content";  // 配置管理内容panel ID
// 配置查询表单   
basedev.getUnloadTime.QUERY_BASE_CONFIG_FORM_ID = "T_basedev-querygetUnloadTimeForm";
// 配置列表
basedev.getUnloadTime.QUERY_BASE_CONFIG_RESULT_GRID_ID = "T_basedev-querygetUnloadTimeResultGrid";

basedev.getUnloadTime.STATE_ADD = 'add';   // 新增
basedev.getUnloadTime.STATE_UPDATE = 'update';   // 修改

basedev.getUnloadTime.BASE_CONFIG_FORM_ID = "T_basedev-getUnloadTimeForm";
basedev.getUnloadTime.EDIT_BASE_CONFIG_FORM_ID = "T_basedev-editgetUnloadTimeForm";
//basedev.getUnloadTime.FORMAT_TIME = 'G:i'; // 格式化时间字符串
basedev.getUnloadTime.crateParam = function(param){
    var index = 0;
    var paramStr = "";
    for ( var p in param) {
        var name = p;
        var val = param[p];
        if (val != null && val != '') {
            if(val == 'ALL'){
                continue;
            }
            if (index == 0) {
                paramStr = paramStr + "?" + name + "=" + val;
            }
            else {
                paramStr = paramStr + "&" + name + "=" + val;
            }
            index++;
        }
    }
    return paramStr;
}
/*
basedev.getUnloadTime.checkstrlenght = function(chars) {
    var sum = 0;
    for ( var i = 0; i < chars.length; i++) {
        var c = chars.charCodeAt(i);
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
            sum++;
        } else {
            sum += 3;
        }
    }
    return sum;
}*/

/**
 * 查询条件
 */
Ext.define('Basedev.getUnloadTime.QuerygetUnloadTimeForm',{
    extend:'Ext.form.Panel',
    id : basedev.getUnloadTime.QUERY_BASE_CONFIG_FORM_ID,
    frame : true,
    title: '查询条件',
    layout : 'column',  
    defaultType : 'textfield',
    initComponent: function(){
        var me = this;
        me.items = [{
            name: 'configName',
            fieldLabel: '时效名称',
            labelWidth: 90,                 
            columnWidth: .3
        }, {
            xtype : 'button',
            cls: 'yellow_button',
            text: '查询',
            width : 70,
            handler: function() {
                var selectResultPanel = Ext.getCmp(basedev.getUnloadTime.QUERY_BASE_CONFIG_RESULT_GRID_ID);
                selectResultPanel.setVisible(true);
                selectResultPanel.getPagingToolbar().moveFirst();
            }
        }];
        me.callParent();
    }
});

/**
 * 配置model
 */
Ext.define('Basedev.getUnloadTime.getUnloadTimeModel', {
    extend : 'Ext.data.Model',
    fields : [{
        name : 'hiddenId',
        type : 'string'
    },  {
        name : 'configName',
        type : 'string'
    },{
        name : 'configCode',
        type : 'string'
    }, {
        name : 'nomalUnloadTime',
        type : 'string'
    },{
        name : 'nomalUnloadTimeName',
        type : 'string'
    }, {
        name : 'overtimeUnloadTime',
        type : 'string'
    },{
        name : 'overtimeUnloadTimeName',
        type : 'string'
    }, {
        name : 'blFlag',
        type : 'string'
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
        name : 'delFlag',
        type : 'string'
    }, {
       name: 'siteNames',
       type: 'string'
    }]
});

/**
 * 配置查看
 */
Ext.define('Basedev.getUnloadTime.getUnloadTimeForm', {
    extend : 'Ext.form.Panel',
    id : basedev.getUnloadTime.BASE_CONFIG_FORM_ID,
//  title : '查看配置',
    frame : true,
    defaults : {
        margin : '5 10 5 10',
        labelWidth : 100,
        readOnly : true,
        width: 200
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
        xtype : 'dictcombo',
        dictType : 'BASE_NOMAL_UNLOAD_TIME',
        name : 'nomalUnloadTime',
        fieldLabel : '合格卸车时间1'
    },{
        xtype : 'dictcombo',
        dictType : 'BASE_OVERTIME_UNLOAD_TIME',
        name : 'overtimeUnloadTime',
        colspan: 2,
        fieldLabel : '合格卸车时间2'
    }, {
       name: 'siteNames',
       xtype: 'textareafield',
       fieldLabel: '门店',
        width : 550,
        height: 120,
        colspan: 3
    }, {
        name : 'remark',
        xtype: 'textareafield',
        fieldLabel : '备注',
        width : 550,
        height: 55,
        colspan: 3
    }],
    setShowValue: function(recode){
        var stateName = recode.data.blFlag == '1' ? '是' : '否';
        this.getForm().findField("blFlag").setValue(stateName);
        
    },setSiteNamesShowValue: function(siteNames){
       this.getForm().findField('siteNames').setValue(siteNames);
    }
});

/**
 * 配置新增/编辑
 */
Ext.define('Basedev.getUnloadTime.EditgetUnloadTimeForm',{
    extend:'Ext.form.Panel',
    id : basedev.getUnloadTime.EDIT_BASE_CONFIG_FORM_ID,
//  title: '查看产品',
    frame: true,
    defaults: {
        margin:'5 20 5 10',
        labelWidth: 100,
        allowBlank: true,
        validateOnChange: false
    },
    layout : {
        type : 'table',
        columns:3
    },
    defaultType : 'textfield',
    operatorType : null,
    siteCodes: null,
    setOperatorType : function(state,record){
        this.operatorType = state;
        var editgetUnloadTimeForm = this.getForm();
        // 表单重置
        editgetUnloadTimeForm.reset();
        
        if(state == basedev.getUnloadTime.STATE_ADD){
            var getUnloadTimeModel = Ext.create('Basedev.getUnloadTime.getUnloadTimeModel');
            getUnloadTimeModel.data.blFlag = 1;
            editgetUnloadTimeForm.loadRecord(getUnloadTimeModel);
            this.setFormFieldsReadOnly(false);
        } else if(state == basedev.getUnloadTime.STATE_UPDATE){
            var getUnloadTimeModel = Ext.create('Basedev.getUnloadTime.getUnloadTimeModel',record.raw);
            editgetUnloadTimeForm.loadRecord(getUnloadTimeModel);
            this.setFormFieldsReadOnly(true);
        }
    },
    getOperatorType : function(){
        return this.operatorType;
    },  
    setFormFieldsReadOnly: function(flag) {
        var form = this.getForm();
        form.findField('configCode').setReadOnly(flag);
    },
   setSiteNamesShowValue: function(siteNames){
       this.getForm().findField('siteNames').setValue(siteNames);
    },
     treeWindow : null,
      getTreeWindow : function(){
        var me = this;
//        if(Ext.isEmpty(me.treeWindow)){
            me.treeWindow = Ext.create('Basedev.getUnloadTime.showTreeWindow');
//        }
        return me.treeWindow;
    },
    initComponent: function(){
       var me = this;
       me.items = [{
        xtype : 'textfield',
        name : 'hiddenId',
        hidden : true
    },{
        xtype : 'textfield',
        name : 'configCode',
        fieldLabel : '时效编号',
        maxLength : 10,
        allowBlank: false,
        validateOnBlur : true,
        regex : /^[A-Za-z0-9]+$/,
        regexText : '该输入项只能输入数字和字母',
        validator : function(field){
            if(!field){
                return true;
            }
            
            var editgetUnloadTimeInfoForm = Ext.getCmp(basedev.getUnloadTime.EDIT_BASE_CONFIG_FORM_ID);
            var state = editgetUnloadTimeInfoForm.getOperatorType();
            if(basedev.getUnloadTime.STATE_UPDATE == state){
                return true;
            }
            
            var paramsObj = {configCode : field};
            var valid = false;
            Ext.Ajax.request({
                url : basedev.realPath('uniqueCheckByUnloadTimeCode.do'),
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
            return '该时效编号已存在';
        }
    }, {
        xtype : 'textfield',
        name : 'configName',
        fieldLabel : '时效名称',
        maxLength : 15,
        allowBlank: false,
        validateOnBlur : true,
        validator : function(field){
            if(!field){
                return true;
            }
            
            var editgetUnloadTimeInfoForm = Ext.getCmp(basedev.getUnloadTime.EDIT_BASE_CONFIG_FORM_ID);
            
            var configCode = editgetUnloadTimeInfoForm.getForm().findField("configCode").getValue();
            var paramsObj = {configCode : configCode, configName : field, state : editgetUnloadTimeInfoForm.getOperatorType()};
            
            var valid = false;
            Ext.Ajax.request({
                url : basedev.realPath('uniqueCheckByUnloadTimeName.do'),
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
            return '该时效名称已存在';
        }
    },{
        xtype: 'checkbox',
        name: 'blFlag',
        boxLabel: '启用',
        inputValue: '1',
        uncheckedValue: '0'
    },{
        xtype : 'dictcombo',
        dictType : 'BASE_NOMAL_UNLOAD_TIME',
        name: 'nomalUnloadTime',
        fieldLabel:'合格卸车时间1',
        editable : false,
        allowBlank: false
    },{
        xtype : 'dictcombo',
        dictType : 'BASE_OVERTIME_UNLOAD_TIME',
        name: 'overtimeUnloadTime',
        fieldLabel:'合格卸车时间2',
        editable : false,
        colspan: 2,
        allowBlank: false
    }, {
        name: 'remark',
        xtype: 'textareafield',
        fieldLabel: '备注',
        maxLength : 80,
        colspan: 3,
        width:550,
        height: 55
    },{
        fieldLabel: '<font style="color:red">*</font>门店',
        name: 'siteNames',
        xtype: 'textareafield',
        colspan: 2,
        width : 550,
        height: 120,
        readOnly: true,
        readOnlyCls: '',
        allowBlank: false
    },{
        xtype : 'button',
        text: '添加',
        width : 70,
        handler: function() {
        var treeWindow = me.getTreeWindow();
        treeWindow.setTitle('添加门店');
        treeWindow.siteCodes = me.siteCodes;
                treeWindow.show();
            }
            
        }];
         me.callParent();
    }
    
});


Ext.define('Basedev.getUnloadTime.showTreeWindow', {
    extend: 'Ext.window.Window',
    width: 800,
    modal: true,
    closeAction: 'destroy',
    siteCodes: null,
    panel: null,
    getPanel: function(){
        var param = '';
        var editForm = Ext.getCmp(basedev.getUnloadTime.EDIT_BASE_CONFIG_FORM_ID);
        if(editForm.getOperatorType() == basedev.getUnloadTime.STATE_UPDATE){
           param = editForm.getForm().findField('configCode').getValue();   
        }
        this.panel = Ext.create('Rosefinch.Basedev.BaseSitePanelSelectPanel',{
           treeUrl: '../basedev/getUnloadTimeTree.do',
           treeparams: {'id': param, 'flag': true}
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
                    var editForm = Ext.getCmp(basedev.getUnloadTime.EDIT_BASE_CONFIG_FORM_ID);
                    editForm.getForm().findField('siteNames').setValue(siteNames);
                    editForm.siteCodes = record;
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
 * 配置Store
 */
Ext.define('Basedev.getUnloadTime.getUnloadTimeStore',{
    extend:'Ext.data.Store',
    model: 'Basedev.getUnloadTime.getUnloadTimeModel',
    pageSize: 10,
    autoLoad: false,
    proxy: {
        type: 'ajax',
        actionMethods: 'POST',
        url : basedev.realPath("getPaginationgetUnloadTimeList.do"),
        reader: {
            type : 'json',
            root : 'list',
            totalProperty : 'count'
        }
    },
    listeners: {
        beforeload : function(store, operation, eOpts) {
            var queryForm = Ext.getCmp(basedev.getUnloadTime.QUERY_BASE_CONFIG_FORM_ID);
            if (queryForm != null) {
                var p_name = queryForm.getForm().findField('configName').getValue();
                
                Ext.apply(operation, {
                    params : {
                        'q_sl_configName' : p_name
                        
                    }
                }); 
            }
        }
    }
});

/**
 * 产品表格
 */
Ext.define('Basedev.getUnloadTime.QuerygetUnloadTimeResultGrid',{
    extend: 'Ext.grid.Panel',
    id: basedev.getUnloadTime.QUERY_BASE_CONFIG_RESULT_GRID_ID,
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
        // flex: 1,
        text : '操作',
        align : 'center',
        items : [{
            iconCls : 'deppon_icons_showdetail',
            tooltip : '查看',
            handler : function(gridView, rowIndex, colIndex) { // 查看
                var getUnloadTimeWindow = Ext.getCmp(basedev.getUnloadTime.QUERY_BASE_CONFIG_RESULT_GRID_ID).getgetUnloadTimeWindow();
                getUnloadTimeWindow.setTitle('查看卸车时效');
                var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
                var getUnloadTimeForm = getUnloadTimeWindow.getgetUnloadTimeForm();
                var getUnloadTimeModel = Ext.create('Basedev.getUnloadTime.getUnloadTimeModel',rowInfo.raw);
                getUnloadTimeForm.loadRecord(getUnloadTimeModel);
                getUnloadTimeForm.setShowValue(rowInfo);
                //加载门店集合
                Ext.Ajax.request({
                    url : "../basedev/getOrgsByUnloadTime.do",
                    params : {
                        'configCode' :rowInfo.data.configCode
                    },
                    success : function(response) {
                        var result = Ext.JSON.decode(response.responseText);
                        if(result.success){
                            getUnloadTimeForm.setSiteNamesShowValue(result.data.siteNames);
                        }else{
                            Ext.ux.Toast.msg('提示', result.msg, 'error'); 
                        }
                    },
                    failure : function(response) {
                        Ext.ux.Toast.msg('提示',response.responseText, 'error');
                    }}
                );
                // 显示窗口
                getUnloadTimeWindow.show();
            }
        }, {
            iconCls : 'deppon_icons_edit',
            tooltip : '修改',// 修改
            handler : function(gridView, rowIndex, colIndex) {
                var editEaseConfigWindow = Ext.getCmp(basedev.getUnloadTime.QUERY_BASE_CONFIG_RESULT_GRID_ID).getEditgetUnloadTimeWindow();
                editEaseConfigWindow.setTitle('编辑卸车时效');
                var rowInfo = gridView.up('grid').getStore().getAt(rowIndex);
                    editEaseConfigWindow.getEditgetUnloadTimeForm().setOperatorType(basedev.getUnloadTime.STATE_UPDATE, rowInfo);
                //加载门店集合
                Ext.Ajax.request({
                    url : "../basedev/getOrgsByUnloadTime.do",
                    params : {
                        'configCode' :rowInfo.data.configCode
                    },
                    success : function(response) {
                        var result = Ext.JSON.decode(response.responseText);
                        if(result.success){
                            editEaseConfigWindow.getEditgetUnloadTimeForm().siteCodes = result.data.siteCodes;
                            editEaseConfigWindow.getEditgetUnloadTimeForm().setSiteNamesShowValue(result.data.siteNames);
                        }else{
                            Ext.ux.Toast.msg('提示', result.msg, 'error'); 
                        }
                    },
                    failure : function(response) {
                        Ext.ux.Toast.msg('提示',response.responseText, 'error');
                    }}
                );
                    // 打开窗口
                    editEaseConfigWindow.show();
                  
            }
        }/*, {
            iconCls : 'deppon_icons_delete',
            tooltip : '删除',// 删除
            handler : function(grid, rowIndex, colIndex) {
                 删除 
                var rowInfo = Ext
                        .getCmp(basedev.getUnloadTime.QUERY_BASE_CONFIG_RESULT_GRID_ID).store
                        .getAt(rowIndex);
                
                var id = rowInfo.data.hiddenId;
                
                Ext.Msg.confirm('确认',
                        '确认删除吗？',
                        function(btn) {
                            if (btn == 'yes') {
                                Ext.Ajax.request({
                                    url : basedev.realPath('/deleteUnloadTime.do'),
                                    params : {
                                        id: id
                                    },
                                    success : function(response) {
                                        var result = Ext.JSON.decode(response.responseText);
                                        
                                        if(result.success){
                                            Ext.ux.Toast.msg('提示', '删除成功');
                                            var grid = Ext.getCmp(basedev.getUnloadTime.QUERY_BASE_CONFIG_RESULT_GRID_ID);
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
            }
        }*/]
    }, {
        hidden : true,
        dataIndex : 'id'
    }, {
        text : '时效编号',
        width : 100,
        
        dataIndex : 'configCode'
    }, {
        text : '时效名称',
        width : 150,
        
        dataIndex : 'configName'
    }, {
        text: '正班车合格卸车时间',
        width : 150,
        dataIndex: 'nomalUnloadTimeName'
    }, {
        text: '加班车合格卸车时间',
        width : 150,
        dataIndex: 'overtimeUnloadTimeName'
    },{
        text : '创建人',
        width : 150,
        dataIndex : 'createUserCode'
    },{
        xtype : 'datecolumn',
        format : 'Y-m-d H:i:s',
        text : '创建时间',
        width : 150,
        dataIndex : 'createTime'
    },{
        text : '修改人',
        width : 150,
        dataIndex : 'modifyUserCode'
    },{
        xtype : 'datecolumn',
        format : 'Y-m-d H:i:s',
        text : '修改时间',
        width : 150,
        dataIndex : 'modifyTime'
    },  {
        text: '启用',
        width : 100,
        dataIndex: 'blFlag',
        renderer: function(value){
            return value == '1' ? '是' : '否';
        }
    },{
        text : '备注',
        width : 150,
        dataIndex : 'remark'
    }],
    
    getUnloadTimeWindow : null,
    getgetUnloadTimeWindow : function(){
        me = this;
        if(Ext.isEmpty(me.getUnloadTimeWindow)){
            me.getUnloadTimeWindow = Ext.create('Basedev.getUnloadTime.getUnloadTimeWindow');
            
            
        }
        return me.getUnloadTimeWindow;
    },
    editgetUnloadTimeWindow : null,
    getEditgetUnloadTimeWindow : function(){
        me = this;
        //if(Ext.isEmpty(me.editgetUnloadTimeWindow)){
            me.editgetUnloadTimeWindow = Ext.create('Basedev.getUnloadTime.EditgetUnloadTimeWindow');
        //}
        return me.editgetUnloadTimeWindow;
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
    setUpdateBlFlag: function(blFlag,blFlagName){
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
            ids[i] = selectList[i].data.hiddenId;
        }
        Ext.Msg.confirm('确认', '确认'+ blFlagName + '吗？', function(btn){
            if (btn == 'yes') {
        var paramsObj = {idList: ids, blFlag: blFlag}
        Ext.Ajax.request({
            url : basedev.realPath('/batchUpdateUnloadTimeById.do'),
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
                    var grid = Ext.getCmp(basedev.getUnloadTime.QUERY_BASE_CONFIG_RESULT_GRID_ID);
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
        me.store = Ext.create('Basedev.getUnloadTime.getUnloadTimeStore');
        me.tbar = [{
            text: '新增',
            handler: function(){
                var editgetUnloadTimeWindow = me.getEditgetUnloadTimeWindow();
                editgetUnloadTimeWindow.setTitle('新增卸车时效');
                var editgetUnloadTimeForm = editgetUnloadTimeWindow.getEditgetUnloadTimeForm();
                editgetUnloadTimeForm.setOperatorType(basedev.getUnloadTime.STATE_ADD);
                editgetUnloadTimeForm.siteCodes = null;
                editgetUnloadTimeWindow.show();
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
                    ids[i] = selectList[i].data.hiddenId;
                }
                Ext.Msg.confirm('确认',
                    '确认删除吗？',
                    function(btn) {
                        if (btn == 'yes') {
                            var paramsObj = {idList: ids};
                            Ext.Ajax.request({
                                url : basedev.realPath('/batchDeleteUnloadTimeById.do'),
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
                                        var grid = Ext.getCmp(basedev.getUnloadTime.QUERY_BASE_CONFIG_RESULT_GRID_ID);
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
                me.setUpdateBlFlag(1,'启用');
            }
        }, {
            text: '停用',
            handler: function(){
                me.setUpdateBlFlag(0,'停用');
            }
        }];
        me.bbar = me.getPagingToolbar();
        me.listeners = {
            beforedestroy: function(){
                if(!Ext.isEmpty(me.getUnloadTimeWindow)){
                    me.getUnloadTimeWindow.removeAll();
                    me.getUnloadTimeWindow.destroy();
                }
                if(!Ext.isEmpty(me.editgetUnloadTimeWindow)){
                    me.editgetUnloadTimeWindow.removeAll();
                    me.editgetUnloadTimeWindow.destroy();
                }
            }
        };
        me.callParent(cfg);
    }
});


Ext.define('Basedev.getUnloadTime.getUnloadTimeWindow', {
    extend: 'Ext.window.Window',
    width: 850,
    modal: true,
    closeAction: 'hide',
    title : '查看卸车时效',
    getUnloadTimeForm : null,
    getgetUnloadTimeForm: function(){
        if (Ext.isEmpty(this.getUnloadTimeForm)) {
            this.getUnloadTimeForm = Ext.create("Basedev.getUnloadTime.getUnloadTimeForm");
        }
        return this.getUnloadTimeForm;
    },
    // 取消按钮
    cancelButton : null,
    getCancelButton:function(){
        var me = this;
        if (Ext.isEmpty(this.cancelButton)) {
            this.cancelButton = Ext.create('Ext.button.Button',{
                text: '取消',
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
            me.getgetUnloadTimeForm()
        ];
        me.buttons = [
            me.getCancelButton()
        ];
        me.callParent([cfg]);
    }
});


Ext.define('Basedev.getUnloadTime.EditgetUnloadTimeWindow', {
    extend: 'Ext.window.Window',
    width: 900,
    modal: true,
    closeAction: 'destroy',
    editgetUnloadTimeForm : null,
    getEditgetUnloadTimeForm: function(){
        if (Ext.isEmpty(this.editgetUnloadTimeForm)) {
            this.editgetUnloadTimeForm = Ext.create("Basedev.getUnloadTime.EditgetUnloadTimeForm");
        }
        return this.editgetUnloadTimeForm;
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
                    var getUnloadTimeForm = me.getEditgetUnloadTimeForm().getForm();
                    var siteCodes = me.getEditgetUnloadTimeForm().siteCodes;
                    // 校验产品表单
                    if (!getUnloadTimeForm.isValid()) {
                        return;
                    }
                    
                    var data = getUnloadTimeForm.getValues();
                    data.siteCodeList = siteCodes;
                    var url = '';
                    if (me.getEditgetUnloadTimeForm().getOperatorType() == basedev.getUnloadTime.STATE_ADD) {
                        url = basedev.realPath('insertGetUnloadTime.do');
                    } else {
                        url = basedev.realPath('updateGetUnloadTime.do');
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
                                
                                var grid = Ext.getCmp(basedev.getUnloadTime.QUERY_BASE_CONFIG_RESULT_GRID_ID);
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
            me.getEditgetUnloadTimeForm()
        ];
        me.buttons = [
            me.getCancelButton(),
            me.getSaveButton()
        ];
        me.callParent([cfg]);
    }
});

/**
 * 查看窗口
 */
Ext.define('Basedev.getUnloadTime.getUnloadTimeWindow', {
    extend: 'Ext.window.Window',
    width: 850,
    modal: true,
    closeAction: 'hide',
    getUnloadTimeForm : null,
    getgetUnloadTimeForm: function(){
        if (Ext.isEmpty(this.getUnloadTimeForm)) {
            this.getUnloadTimeForm = Ext.create("Basedev.getUnloadTime.getUnloadTimeForm");
        }
        return this.getUnloadTimeForm;
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
        me.getgetUnloadTimeForm()
    ];
    me.buttons = [
        me.getCancelButton()
    ];
    me.callParent([cfg]);
  }
});


Ext.onReady(function() {
    Ext.QuickTips.init();
    //Ext.Loader.setConfig({enabled:true});
    if (Ext.getCmp(basedev.getUnloadTime.CONTENT_ID)) {
        return;
    };
    
    var querygetUnloadTimeForm = Ext.create('Basedev.getUnloadTime.QuerygetUnloadTimeForm');
    var querygetUnloadTimeResultGrid = Ext.create('Basedev.getUnloadTime.QuerygetUnloadTimeResultGrid');
    
    var content = Ext.create('Ext.panel.Panel', {
        id: basedev.getUnloadTime.CONTENT_ID,
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        getQuerygetUnloadTimeForm: function() {
            return querygetUnloadTimeForm;
        },
        getQuerygetUnloadTimeResultGrid: function() {
            return querygetUnloadTimeResultGrid;
        },
        items: [
            querygetUnloadTimeForm,
            querygetUnloadTimeResultGrid
        ]
    });
    
    Ext.getCmp(basedev.getUnloadTime.TAB_ID).add(content);
    // 加载表格数据
    querygetUnloadTimeResultGrid.getStore().load();
});


