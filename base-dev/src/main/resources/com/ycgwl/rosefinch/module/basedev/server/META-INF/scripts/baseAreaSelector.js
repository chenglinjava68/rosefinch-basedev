/*****************片区*******************/
/** 片区model */
Ext.define('BaseData.commonSelector.BaseAreaModel', {
    extend : 'Ext.data.Model',
    fields : [ {
        name : 'areaId',
        type : 'long'
    }, {
        name : 'areaCode',
        type : 'string'
    },{
        name : 'areaName',
        type : 'string'
    }, {
        name : 'orderBy',
        type : 'int'
    } ]
});

/** 片区store */
Ext.define('BaseData.commonSelector.BaseAreaStore', {
    extend : 'Ext.data.Store',
    model : 'BaseData.commonSelector.BaseAreaModel',
    pageSize : 10,
    proxy : {
        type : 'ajax',
        url : '../basedev/getBaseAreaList.do',
        actionMethods : 'POST',
        reader : {
            type : 'json',
            root : 'list',
            totalProperty : 'count'
        }
    }
});

/** 片区单选公共选择器 */
Ext.define('BaseData.commonSelector.AreaSelector', {
    extend : 'Dpap.commonSelector.CommonCombSelector',
    alias : 'widget.commonAreaSelector',
    listWidth : 150,// 设置下拉框宽度
    displayField : 'areaName',// 显示名称
    valueField : 'areaCode',// 值
    queryParam : 'areaName',// 查询参数
    showContent : '{areaName}',// 显示表格列
    typeMode: 1, // 1.显示菜单和页面元素(默认)；2.全部；3.显示菜单；4.不显示页面元素；
    queryCaching : false,
    constructor : function(config) {
        var me = this, cfg = Ext.apply({}, config);
        me.store = Ext.create('BaseData.commonSelector.BaseAreaStore');
        me.store.addListener('beforeload', function(store, operation, eOpts) {
            var searchParams = {
                'q_sl_areaName': operation.params.areaName,
                'q_int_blFlag': cfg.blFlag
            };
            Ext.apply(operation, {
                params : searchParams
            });
        });
        me.callParent([cfg]);
    }
});

