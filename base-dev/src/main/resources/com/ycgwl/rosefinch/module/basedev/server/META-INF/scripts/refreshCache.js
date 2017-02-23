/** 此模块主页面的ID */
basedev.refreshCache.CONTENT_ID  = 'T_basedev-refreshCacheIndex_content';
basedev.refreshCache.FORMAT_TIME  = 'Y-m-d H:i:s'; //格式化时间字符串

/**
 * 查询表单
 */
Ext.define('BaseData.refreshCache.refreshCacheForm', {
    extend:'Ext.form.Panel',
    id: 'BaseData_refreshCache_refreshCacheForm_ID',
    frame : true,
    title: '缓存刷新管理界面',//
     height:300,
    defaults: {
        labelWidth:60,
        margin:'5 10 5 10'
    
    },
    region:'center',
    layout : {
        type:'column',
        pack: 'center',
        align:'center'
    },  
    defaultType : 'textfield',
    siteSelectWindow:null,
    initComponent: function(){
        var me = this;
        me.items = [
            {
                fieldLabel:' 本页面的作用主要是对系统中的缓存信息进行手工刷新，以获取到最新的更改数据',
                labelWidth : 600,
                //style:'color:red;font-size:50px;',
                labelStyle:"color:red;font-size:22px;font-weight:bold;",
                  columnWidth: 1,
                  readOnly:true
                } ,{
            name: 'cacheType',
            xtype : 'dictcombo',
            dictType : 'REFRESH_CACHE_TYPE',
            fieldLabel:'选择要刷新的缓存',
            labelStyle:"font-weight:bold;",
            value:'ALL',
            anyRecords:[{'valueCode':'ALL','valueName':'全部'}],
            labelWidth : 110,
            emptyText:"请选择要刷新的缓存信息...",
             allowBlank : false,
            columnWidth: .4
        },{
            xtype : 'button',
            cls: 'yellow_button',
            text:"刷新",
            width : 70,
            // 查询按钮的响应事件：
            handler: function() {
                if(me.getForm().isValid()){
                var refreshWindow =  Ext.create('BaseData.refreshCache.RefreshCacheWindow');
                refreshWindow.editData = me.getForm().findField('cacheType').getValue();
                refreshWindow.show();
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
Ext.define('BaseData.refreshCache.refreshCacheInsertForm', {
    extend:'Ext.form.Panel',
    id: 'BaseData_refreshCache_refreshCacheInsertForm_ID',
    frame : true,
    defaults: {
        labelWidth:60,
        margin:'5 10 5 10'
    
    },
    defaultType : 'textfield',
    siteSelectWindow:null,
    initComponent: function(){
        var me = this;
        me.items = [{
        name: 'cacheType',
        hidden:true
        }, {
                fieldLabel:'如果不输入表示刷新该分类下的全部缓存(选择全部表示刷新所有的分类)',
                labelWidth :300,
                //style:'color:red;font-size:50px;',
                labelStyle:"color:red;font-size:15px;font-weight:bold;",
                  readOnly:true
                } ,{
            name: 'keyValue',
            xtype : 'textfield',
            fieldLabel:'输入要刷新的缓存key',
            labelStyle:"font-weight:bold;",
            labelWidth : 140
        }]
        me.callParent();
    }
});
  Ext.define('BaseData.refreshCache.RefreshCacheWindow', {
        extend : 'Ext.window.Window',
        closable : true,
        modal : true,
        closeAction : 'destroy',
        resizable : false,
        title : '刷新缓存输入窗口',
        height : 260,
        width : 400,
        editData : null,
        refreshCacheForm : null,//
        getRefreshCacheForm : function() {
            if (Ext.isEmpty(this.refreshCacheForm)) {
                this.refreshCacheForm = Ext
                        .create('BaseData.refreshCache.refreshCacheInsertForm');
                return this.refreshCacheForm;
            } else {
                return this.refreshCacheForm;
            }
        },
        initComponent : function() {
            var me = this;
            var refreshCacheForm = me.getRefreshCacheForm();
            me.items = [refreshCacheForm],
            me.buttons = [{
                        text : '关闭',
                        handler : function() {
                            me.close();
                        }
                    }, {
                        text : '刷新',
                        cls : 'yellow_button',
                        handler : me.refreshAccount,
                        scope : me
                    }];
            // 监听事件
            me.listeners = {
                beforeshow : function() {
                    refreshCacheForm.getForm().reset();
                    refreshCacheForm.getForm().findField('cacheType')
                            .setValue(me.editData);
                },
                beforeclose : function() {
                    refreshCacheForm.getForm().reset();
                }

            };
            me.callParent();
        },
        refreshAccount : function() {
            var me = this, refreshForm = me.down('form').getForm();
              if(refreshForm.isValid()){
                      Ext.Ajax.request({
                url: '../basedev/refeshCache.do',
                method: 'post',
                params : {
                        cacheType:refreshForm.findField('cacheType').getValue(),
                        keyValue:refreshForm.findField('keyValue').getValue()
                },
                success : function(response) {
                	me.close();
                    var json = Ext.decode(response.responseText);
                  //  if(json.success){
                    Ext.ux.Toast.msg(basedev.refreshCache.i18n('basedev.notice.msginfo'), json.msg);
                    //}
                },
                exception : function(response) {
                    me.close();
                    var json = Ext.decode(response.responseText); 
                    Ext.ux.Toast.msg(basedev.refreshCache.i18n('basedev.notice.msginfo'), json.msg);
                }
            });
            }
        }
    });
Ext.onReady(function() {
    Ext.QuickTips.init();
    if (Ext.getCmp(basedev.refreshCache.CONTENT_ID)) {
        return;
    };
    var searchForm = Ext.create('BaseData.refreshCache.refreshCacheForm');
    var content = Ext.create('Ext.panel.Panel', {
        id: basedev.refreshCache.CONTENT_ID,
        height:350,
        cls: "panelContentNToolbar",
        bodyCls: 'panelContentNToolbar-body',
        getSearchForm: function() {
            return searchForm;
        },
        items: [
            searchForm
        ]
    });
    Ext.getCmp('T_basedev-refreshCacheIndex').add(content);
});