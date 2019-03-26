Ext.onReady(function(){
    // basic tabs 1, built from existing content
    var tabs = new Ext.TabPanel({
        renderTo: 'tabs',
        width:800,
        activeTab: 0,
        frame:true,
        defaults:{autoHeight: true},
        items:[{
            id:"tab1",
            contentEl:'tab1',
            title: '基本信息',
            autoScroll:true
        },{
            id:"tab12",
            contentEl:'tab1',
            title: '基本信息2',
            autoScroll:true
        }]
    });

});
