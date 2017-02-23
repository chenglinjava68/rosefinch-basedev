/**************************************网点选择公共组件*******************************************/
Ext.define('Rosefinch.Basedev.BaseSitePanelSelectPanel', {
    extend : 'Ext.panel.Panel',
    id : 'Rosefinch_Basedev_BaseSitePanelSelectPanel_Id',
    height : 655,
    panelUrl:null,
    treeUrl:null,
    frame : true,
    treeparams:null,
    columnWidth : 0.99,
//  title : '部门选择',
    layout : 'column',
    layout : {
        type : 'hbox',
        align : 'stretch'
    },
    defaults : {
        readOnly : false
    },
    selectedDeptPanel:null,
    getSelectedDeptPanel:function(){
        var me = this;
        if(me.selectedDeptPanel == null){
            me.selectedDeptPanel = Ext.create('Rosefinch.Basedev.BaseSitePanelSelectPanel.SelectedSitePanel');
            me.selectedDeptPanel.panelUrl = me.panelUrl;
        }
        return me.selectedDeptPanel ;
    },
    selectButtonPanel:null,
    getSelectButtonPanel:function(){
        var me = this;
        if(me.selectButtonPanel == null){
            me.selectButtonPanel = Ext.create('Rosefinch.Basedev.BaseSitePanelSelectPanel.ButtonPanel');
        }
        return me.selectButtonPanel ;
    },
    loadDeptTree:null,
    getLoadDeptTree:function(){
        var me =this;
        if(me.loadDeptTree == null){
            me.loadDeptTree = Ext.create('Rosefinch.Basedev.BaseSitePanelSelect.Grid',{
                url:me.treeUrl,
                params: me.treeparams
            }               
            );
        
        }
        return me.loadDeptTree ;
    },
    initComponent : function(config) {
        var me = this, 
        cfg = Ext.apply({}, config),
        deptTree = me.getLoadDeptTree();
        me.getSelectButtonPanel().setRightMove(me.getSelectedDeptPanel());
        me.getSelectButtonPanel().setLeftMove(deptTree);
        me.items = [deptTree, me.getSelectButtonPanel(),me.getSelectedDeptPanel()]
        me.callParent([cfg]);
    }
});

//已选部门列表
Ext.define('Rosefinch.Basedev.BaseSitePanelSelectPanel.SelectedSitePanel', {
    extend : 'Ext.grid.Panel',
    id : 'Rosefinch_Basedev_BaseSitePanelSelectPanel_SelectedSitePanel_Id',
    sortableColumns : false,
    enableColumnHide : false,
    enableColumnMove : false,
    store:null,
    width : 300,
    flex : 1,
    panelUrl:null,
    frame : true,
    selModel: {
        selection: "rowmodel",
        mode: "MULTI"
    },
    deleButton:null,
    getDelBut:function(){
        var me = this;
        if(Ext.isEmpty(me.deleButton)){
            me.deleButton = Ext.create('Ext.Button', {
                text :'反选',//反选
                handler : function() {
                    me.selectOthers();
                }
            });
        }
        return this.deleButton;
    },
    selectOthers: function() {
        var testGrid=Ext.getCmp('Rosefinch_Basedev_BaseSitePanelSelectPanel_SelectedSitePanel_Id');
        var records=testGrid.getStore().getRange();
        var selectModel=testGrid.getSelectionModel();
        for(var i=0;i<records.length;i++) {
            var record=records[i];
            if(selectModel.isSelected(record)) {
                selectModel.deselect(record);
            }else {
                selectModel.select(record,true);    
            }
        }
    },  
    columns : [{
        header : '已选网点',
        dataIndex : 'siteName',
        flex : 1,
        titlehidden : true
    },{
        hidden : true,
        dataIndex : 'siteCode'
    }],
    constructor : function(config) {
        var me = this, cfg = Ext.apply({}, config);
        me.selModel = Ext.create('Ext.selection.CheckboxModel');
//      me.tbar=[me.getDelBut()];
        me.listeners = {
                scrollershow : function(scroller) {
                    if (scroller && scroller.scrollEl) {
                        scroller.clearManagedListeners();
                        scroller.mon(scroller.scrollEl, 'scroll',
                                scroller.onElScroll, scroller);
                    }
                },
                itemdblclick : function(ths, record, item, index, e, eOpts) {
                    var buttonPanel = Ext.getCmp("Rosefinch_Basedev_BaseSitePanelSelectPanel_ButtonPanel_Id");
                    buttonPanel.leftMove();
                }
        };
        if(Ext.isEmpty(me.userUrl)){
            me.store = null;
        }else{
            me.store = Ext.create('Rosefinch.Basedev.BaseSitePanelSelectPanel.Store',{
                userUrl:me.userUrl
            });
        }
        me.callParent([cfg]);
    }
});
Ext.define('Rosefinch.Basedev.BaseSitePanelSelectPanel.Model', {
    extend: 'Ext.data.Model',
    fields : [  {name: 'siteCode',  type: 'string'},
                {name: 'siteName',  type: 'string'},
                {name: 'siteType',  type: 'int'}]
});
//初始化用户权限部门
Ext.define('Rosefinch.Basedev.BaseSitePanelSelectPanel.Store', {
    extend : 'Ext.data.Store',
    model :'Rosefinch.Basedev.BaseSitePanelSelectPanel.Model',
    panelUrl:null,
    proxy : {
        type : 'ajax',
        url : this.panel,
        actionMethods : 'post'
    },
    constructor : function(config) {
        var me = this, 
        cfg = Ext.apply({}, config);
        me.callParent([cfg]);
    }   
});
//按钮面板
Ext.define('Rosefinch.Basedev.BaseSitePanelSelectPanel.ButtonPanel', {
    extend : 'Ext.panel.Panel',
    id:'Rosefinch_Basedev_BaseSitePanelSelectPanel_ButtonPanel_Id',
    // height : 150,
    width : 80,
    // frame:true,
    // 左移框
    leftMoveFrame : null,
    // 右移框
    rightMoveFrame : null,

    setLeftMove : function(a_moveFrame) {
        this.leftMoveFrame = a_moveFrame;
    },
    getLeftMove : function() {
        return this.leftMoveFrame;
    },
    setRightMove : function(a_moveFrame) {
        this.rightMoveFrame = a_moveFrame;
    },
    getRightMove : function() {
        return this.rightMoveFrame;
    },
    getTreeAllNode : function() {
        var me = this;
        var nodes = new Array();
        var rootNode = me.getLeftMove().getRootNode();
        me.getNode(rootNode, nodes);
        return nodes;
    },
    getNode : function(node, nodes) {
        var me = this;
        nodes.push(node);
        if (node.hasChildNodes) {
            node.eachChild(function(cNode) {
                // nodes.push(cNode);
                me.getNode(cNode, nodes);
            })
        }
        return nodes;
    },
    // 右移全部
    rightMoveAll : function() {
        var me = this;
        var treeNodes = me.getTreeAllNode();
        var leftStore = me.getLeftMove().getStore();
        var rightStore = me.getRightMove().getStore();
        rightStore.removeAll();
        Ext.Array.each(treeNodes, function(item, index, allNodes) {
            if (!Ext.isEmpty(item.data.parentId)) {
                var ins_rec = Ext.create('Rosefinch.Basedev.BaseSitePanelSelectPanel.Model',{
                    siteCode:item.data.id+'',
                    siteName:item.data.text+''
//                  siteType:item.raw.entity.orgType
                }); 
                rightStore.insert(0,ins_rec);
            }
        });
    },
    // 右移
    rightMove : function() {
        var me = this;
        var rightStore = this.getRightMove().getStore();
        var checked = me.getLeftMove().getView().getChecked();
        if (checked.length < 1) {
            return;
        }
        var selections = this.getRightMove().getStore().data.items;
        if(Ext.isEmpty(selections)){
            Ext.Array.each(checked, function(item, index, allItems) {               
                    var ins_rec = Ext.create('Rosefinch.Basedev.BaseSitePanelSelectPanel.Model',{
                        siteCode:item.data.id+'',
                        siteName:item.data.text+''
//                      siteType:item.raw.entity.orgType
                    });                     
                                    
                        rightStore.add(ins_rec);                
                }
            );
        }else{  
        Ext.Array.each(checked, function(item, index, allItems) {
            var flag  =  true;
                Ext.Array.each(selections, function(gridItem, gridIndex,
                        gridItems) {                
                    if (item.data.id==gridItem.data.siteCode) {                     
                            flag = false;                                                                       
                    }                               
                }); 
                if(flag){
                    var ins_rec2 = Ext.create('Rosefinch.Basedev.BaseSitePanelSelectPanel.Model',{
                        siteCode:item.data.id+'',
                        siteName:item.data.text+''
//                      siteType:item.raw.entity.orgType
                    }); 
                    rightStore.add(ins_rec2);
                }
        });
        }
    },
    // 左移全部
    leftMoveAll : function() {
        var me = this;
        var rightStore = this.getRightMove().getStore();
        var leftStore = this.getLeftMove().getStore();
        rightStore.removeAll();
    },
    // 左移
    leftMove : function() {
        var me = this;
        var selections = this.getRightMove().getSelectionModel().getSelection();
        if (selections.length < 1) {
            return;
        }
        var treeNodes = me.getTreeAllNode();
        var leftStore = me.getLeftMove().getStore();
        var rightStore = me.getRightMove().getStore();
        Ext.Array.each(selections, function(gridItem, girdIndex, allRows) {
            Ext.Array.each(treeNodes, function(treeItem, treeIndex,allNodes) {
                if (treeItem.data.id == gridItem.data.siteCode) {
                    treeItem.data.checked = false;
                    treeItem.updateInfo({
                        checked : false
                    });
                }
            });
        });
        this.getRightMove().getStore().remove(selections);
    },
    constructor : function(config) {
        var me = this, cfg = Ext.apply({}, config);
        me.defaults = {
                xtype : 'button',
                width : 60,
                disabled : false,
                height : 20,
                margin : '8 0 0 10'
        };
        me.items = [{
            text : '>>',
            margin : '50 0 0 10',
            handler : function() {
                me.rightMoveAll();
            }
        }, {
            text : '>',
            handler : function() {
                me.rightMove();
            }
        }, {
            text : '<',
            handler : function() {
                me.leftMove();
            }
        }, {
            text : '<<',
            handler : function() {
                me.leftMoveAll();
            }
        }]
        me.callParent([cfg]);
    }
});


//定义树形结构
Ext.define('Rosefinch.Basedev.BaseSitePanelSelect.Grid', {
    extend : 'Ext.grid.Panel',
    id:'Rosefinch_Basedev_BaseSiteSelector_Grid_ID',
    width : 340,
    cls: 'autoHeight',
    bodyCls: 'autoHeight',
    // 设置表格不可排序
    sortableColumns: false,
    // 去掉排序的倒三角
    enableColumnHide: false,
    selModel:Ext.create('Ext.selection.CheckboxModel',{mode:"SIMPLE"}),
    // 设置“如果查询的结果为空，则提示”
    stripeRows : true, // 交替行效果
    collapsible: true,
    animCollapse: true,
    frame: true,
    
     columns : [{
        text: '序号',
        width: 50,
        xtype: 'rownumberer',
        align: 'center',
        draggable: true
    }, {
        text : '编码',
        width: 100,
        dataIndex : 'siteCode'
    },{
        text : '简称',
        width: 150,
        dataIndex : 'siteName'
    }],
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
    deptNameQueryField1 : null,
    getDeptNameQueryField1 : function(){
        if(this.deptNameQueryField1==null){
            this.deptNameQueryField1 = Ext.create('Ext.form.field.Text',{
                xtype : 'textfield',
                labelWidth : 40,
                name : 'deptNameQuery1',
                emptyText:'门店简称或编号',
                width : 160
            });
        }
        return this.deptNameQueryField1;
    },
  
    constructor : function(config) {
        var me = this, cfg = Ext.apply({}, config);
        me.url = cfg.url;
        me.params = cfg.params;
       
        me.tbar = [
                    me.getDeptNameQueryField1(),
                    {
                        xtype : 'button',
                        text : '查询',
                        plugins : Ext
                                .create(
                                        'Deppon.ux.ButtonLimitingPlugin',
                                        {
                                            seconds : 3
                                        }),
                        handler : function() {
                            var siteName = me
                                    .getDeptNameQueryField1()
                                    .getValue();
                            
                            if ((Ext.String
                                    .trim(siteName) == null
                                    || Ext.String
                                            .trim(siteName) == "")/*&&(Ext.String
                                                    .trim(siteCode) == null
                                                    || Ext.String
                                                            .trim(siteCode) == "")*/) {
                                Ext.ux.Toast.msg('提示','查询条件不能为空','error');
                                return;
                            }
                            
                            if (!/^[^\|"'<>%@#!&\$\*]*$/
                                    .test(siteName)) {
                                Ext.ux.Toast.msg('提示','输入值不能包含特殊符号','error');
                                return;
                            }
                            /*if (!/^[^\|"'<>%@#!&\$\*]*$/
                                    .test(siteCode)) {
                                Ext.ux.Toast.msg('提示','输入值不能包含特殊符号','error');
                                return;
                            }*/
                            
                            Ext.Ajax.request({
                                        url : "../basedev/getSiteListByName.do",
                                        params : {
                                            'siteName' : Ext.String.trim(siteName)/*,
                                            'siteCode' : Ext.String.trim(siteCode)*/
                                        },
                                        success : function(response) {
                                            var jsonArr = Ext.decode(response.responseText);
                                            if(Ext.isEmpty(jsonArr)){
                                                Ext.ux.Toast.msg('提示','没有查到相关网点','error');
                                                return;
                                            }
                                            
                                            me.expandPaths(jsonArr);
                                            /*var rightStore = Ext.getCmp("Rosefinch_Basedev_BaseSitePanelSelectPanel_SelectedSitePanel_Id").getStore();
                                            var selections = rightStore.data.items;
                                            if(Ext.isEmpty(selections)) {
                                                for (var i = 0; i < jsonArr.length; i++) {
                                                    var ins_rec = Ext.create('Rosefinch.Basedev.BaseSitePanelSelectPanel.Model',{
                                                        siteCode:jsonArr[i].orgCode+'',
                                                        siteName:jsonArr[i].orgName+'',
                                                        siteType:jsonArr[i].orgType+''
                                                    }); 
                                                    rightStore.add(ins_rec);
                                                }
                                            }else{
                                                    for (var i = 0; i < jsonArr.length; i++) {
                                                        var flag  =  true;
                                                        Ext.Array.each(selections, function(gridItem, gridIndex,
                                                                gridItems) {                
                                                            if (jsonArr[i].orgCode==gridItem.data.siteCode) {                       
                                                                    flag = false;                                                                       
                                                            }                               
                                                        }); 
                                                        if(flag){
                                                            var ins_rec2 = Ext.create('Rosefinch.Basedev.BaseSitePanelSelectPanel.Model',{
                                                                siteCode:jsonArr[i].orgCode+'',
                                                                siteName:jsonArr[i].orgName+'',
                                                                siteType:jsonArr[i].orgType+''
                                                            }); 
                                                            rightStore.add(ins_rec2);
                                                        }                                           
                                                    }
                                                }   */                                  
                                        },
                                        exception : function(response) {
                                            var json = Ext.decode(response.responseText);
                                            Ext.ux.Toast.msg('提示',json.msg,'error');
                                        }
                            });
                        }
                    }/*,{
                       xtype: 'button',
                       text: '全部展开',
                       plugins : Ext
                                .create(
                                        'Deppon.ux.ButtonLimitingPlugin',
                                        {
                                            seconds : 80
                                        }),
                       handler: function(){
                           me.expandAll();
                       }
                    },{
                       xtype: 'button',
                       text: '全部收起',
                       handler: function(){
                        me.collapseAll();
                       }
                    }*/];
        me.bbar = me.getPagingToolbar();
    //    me.store = Ext.create('Rosefinch.BaseDev.SiteSelector.Store',{url:me.url,id:me.params});
        me.callParent([cfg]);

    }
});
/**
 * 定义功能树的store
 */
Ext.define('Rosefinch.BaseDev.SiteSelector.Store', {
    extend : 'Ext.data.Store',
    pageSize: 10,
    autoLoad: false,
    proxy: {
        type: 'ajax',
        actionMethods: 'POST',
        url : basedev.realPath("getPaginationBaseSiteLists.do"),
        reader: {
            type : 'json',
            root : 'list',
            totalProperty : 'count'
        }
    },
    constructor : function(config) {
        var me = this, 
        cfg = Ext.apply({}, config);
        
        me.callParent([cfg]);
    }   
});
    
