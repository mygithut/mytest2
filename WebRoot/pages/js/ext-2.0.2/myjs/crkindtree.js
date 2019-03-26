Ext.BLANK_IMAGE_URL = '../../ext2/resources/images/default/s.gif';
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
        emptyText:'如果查询全部业务，可以不用点击选择！',
        tpl: "<tpl for='.'><div style='height:200px'><div id='tree_crkind'></div></div></tpl>",   
        selectedClass:'',   
        onSelect:Ext.emptyFn   
    });   
    var tree_ = new Ext.tree.TreePanel({   
           loader:new Ext.tree.TreeLoader({
          dataUrl:'../crkindTreeList.do'
        }),
          border:false,   
          rootVisible:false,
         root:new Ext.tree.AsyncTreeNode({text: 'root',id:'-1'})   
      });   
      tree_.on('click',function(node){   
         comboxWithTree_.setValue(node.text);
	     //var noflag = Ext.get("kindflag").dom.value;
         Ext.get("crkind").dom.value = node.id;
     	 comboxWithTree_.collapse(); 
      });   
      comboxWithTree_.on('expand',function(){   
      tree_.render('tree_crkind');});   
      comboxWithTree_.render('comboxWithTree2');  
    }
);