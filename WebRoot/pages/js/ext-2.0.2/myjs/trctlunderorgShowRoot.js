Ext.BLANK_IMAGE_URL = '/ftp/pages/js/ext-2.0.2/resources/images/default/s.gif';
Ext.onReady(function(){
var comboxWithTree_ = new Ext.form.ComboBox({   
        name:'comboxname',
        store:new Ext.data.SimpleStore({fields:[],data:[[]]}),   
        editable:false,   
        mode: 'local',   
        triggerAction:'all',  
        queryDelay: 400,  
        maxHeight: 200, 
        width: 280, 
        emptyText:'请选择...',
        tpl: "<tpl for='.'><div style='height:200px'><div id='tree1'></div></div></tpl>",   
        selectedClass:'',   
        onSelect:Ext.emptyFn   
    });   
    var tree_ = new Ext.tree.TreePanel({   
           loader:new Ext.tree.TreeLoader({
          dataUrl:'/ftp/tlrctlOrgTreeAction.action'
        }),
          border:false,   
          rootVisible:true,
        autoScroll : true,
         root:new Ext.tree.AsyncTreeNode({text: '请选择...',id:'-1'})   
      });   
      tree_.on('click',function(node){   
         comboxWithTree_.setValue(node.text);
         /*这里node.id 的组成：brno_ficode*/
         var t=node.id;
         temp = t.split("_"); /*分割出机构号和级别，temp[0]为机构号、temp[1]为级别*/        
         if (temp[0] == "-1") {//如果是根节点，则返回空值
        	 Ext.get("brNo").dom.value = "";         
         }else {
        	 Ext.get("brNo").dom.value = temp[0];
         } 
     	 comboxWithTree_.collapse(); 
      });   
      comboxWithTree_.on('expand',function(){   
      tree_.render('tree1');});   
      comboxWithTree_.render('comboxWithTree1');  
    }
);