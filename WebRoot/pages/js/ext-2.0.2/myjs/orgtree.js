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
        emptyText:'请选择撤消机构...',
        tpl: "<tpl for='.'><div style='height:200px'><div id='tree1'></div></div></tpl>",   
        selectedClass:'',   
        onSelect:Ext.emptyFn   
    });   
    var tree_ = new Ext.tree.TreePanel({   
           loader:new Ext.tree.TreeLoader({
          dataUrl:'tlrctlOrgTreeAction.do'
        }),
          border:false,   
          rootVisible:false,
         root:new Ext.tree.AsyncTreeNode({text: 'root',id:'-1'})   
      });   
      tree_.on('click',function(node){   
         comboxWithTree_.setValue(node.text); 
         var noflag = Ext.get("noflag").dom.value;
         var t = node.id;
         Ext.get(noflag).dom.value =t.substring(0, 10);  
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
        emptyText:'请选择合并机构...',
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
         var noflag = Ext.get("noflag1").dom.value;
          var t = node.id;
         Ext.get(noflag).dom.value =t.substring(0, 10);  
        //Ext.get(noflag).dom.value =node.id;  
      comboxWithTree.collapse(); 
      });   
      comboxWithTree.on('expand',function(){   
      tree.render('tree');});   
      comboxWithTree.render('comboxWithTree2');   
    }
);