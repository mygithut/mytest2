Ext.BLANK_IMAGE_URL = '../ext2/resources/images/default/s.gif';
Ext.onReady(function(){
var comboxWithTree_ = new Ext.form.ComboBox({   
        name:'comboxname',
        store:new Ext.data.SimpleStore({fields:[],data:[[]]}),   
        editable:false,   
        mode: 'local',   
        triggerAction:'all',  
        maxHeight: 200, 
        width: 280, 
        emptyText:'请选择操作员原所在机构...',
        tpl: "<tpl for='.'><div style='height:200px'><div id='tree1'></div></div></tpl>",   
        selectedClass:'',   
        onSelect:Ext.emptyFn   
    });   
    var tree_ = new Ext.tree.TreePanel({   
           loader:new Ext.tree.TreeLoader({
          dataUrl:'tlrctlOrgTreeAction.do'
        }),
          border:false,
        autoScroll : true,
          rootVisible:false,
         root:new Ext.tree.AsyncTreeNode({text: 'root',id:'-1'})   
      });   
      tree_.on('click',function(node){   
         comboxWithTree_.setValue(node.text); 
         var t=node.id;
         temp = t.split("_");
         var noflag = Ext.get("noflag").dom.value;
         Ext.get(noflag).dom.value =temp[0];
        // Ext.get(noflag).dom.value =node.id;  
      comboxWithTree_.collapse(); 
      });   
      comboxWithTree_.on('expand',function(){   
      tree_.render('tree1');});   
      comboxWithTree_.render('comboxWithTree1');
      
      var comboxWithTree = new Ext.form.ComboBox({   
        name:'comboxname2',
        store:new Ext.data.SimpleStore({fields:[],data:[[]]}),   
        editable:false,   
        mode: 'local',   
        triggerAction:'all',  
        maxHeight: 200, 
        width: 280, 
        emptyText:'请选择操作员现所在机构...',
        tpl: "<tpl for='.'><div style='height:200px'><div id='tree'></div></div></tpl>",   
        selectedClass:'',   
        onSelect:Ext.emptyFn   
    });   
    var tree = new Ext.tree.TreePanel({   
           loader:new Ext.tree.TreeLoader({
          dataUrl:'tlrctlOrgTreeAction.do'
        }),
          border:false,   
          rootVisible:false,
         root:new Ext.tree.AsyncTreeNode({text: 'root',id:'-1'})   
      });   
      tree.on('click',function(node){   
         comboxWithTree.setValue(node.text); 
         var t=node.id;
         temp = t.split("_");
         var noflag = Ext.get("noflag1").dom.value;
        // Ext.get(orgnoIdName).dom.value = 
         Ext.get(noflag).dom.value =temp[0];
         Ext.get(noflag).dom.fireEvent('onchange');
        // Ext.get(noflag).dom.value =node.id;  
      comboxWithTree.collapse(); 
      });   
      comboxWithTree.on('expand',function(){   
      tree.render('tree');});   
      comboxWithTree.render('comboxWithTree2');   
    }
);