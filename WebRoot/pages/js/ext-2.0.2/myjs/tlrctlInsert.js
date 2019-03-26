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
        emptyText:'如果查询本机构的信息,可以不用点击选择!',
        tpl: "<tpl for='.'><div style='height:200px'><div id='tree1'></div></div></tpl>",   
        selectedClass:'',   
        onSelect:Ext.emptyFn   
    });   
    var tree_ = new Ext.tree.TreePanel({   
           loader:new Ext.tree.TreeLoader({
          dataUrl:'tlrctlOrgTreeAction.do?insertTlrctl=99'
        }),
          border:false,   
          rootVisible:false,
         root:new Ext.tree.AsyncTreeNode({text: 'root',id:'-1'})   
      });   
      tree_.on('click',function(node){   
         comboxWithTree_.setValue(node.text); 
         var noflag = Ext.get("noflag").dom.value;
         var t = node.id;
        // Ext.get(orgnoIdName).dom.value = 
         Ext.get(noflag).dom.value =t.substring(0, 5);
      comboxWithTree_.collapse(); 
      });   
      comboxWithTree_.on('expand',function(){   
      tree_.render('tree1');});   
      comboxWithTree_.render('comboxWithTree1');  
      comboxWithTree_.on('blur',function(){
    //  alert(1111111);
      changRole();
      });
    }
);