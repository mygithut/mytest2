Ext.BLANK_IMAGE_URL = '../../ext2/resources/images/default/s.gif';
Ext.onReady(function(){
var comboxWithTree_ = new Ext.form.ComboBox({   
        name:'comboxname',
        store:new Ext.data.SimpleStore({fields:[],data:[[]]}),   
       editable:false,   
        mode: 'local',   
        triggerAction:'all',  
        maxHeight: 200, 
        width: 250, 
        emptyText:'信用社的操作员，可以不用选择查询',
        tpl: "<tpl for='.'><div style='height:200px'><div id='tree1'></div></div></tpl>",   
        selectedClass:'',   
        onSelect:Ext.emptyFn  
         
    }); 
    var tree_ = new Ext.tree.TreePanel({   
          loader:new Ext.tree.TreeLoader({
          dataUrl:'../orguptlrctlTreeAction.do'
        }),
          border:false,   
          rootVisible:false,
          root:new Ext.tree.AsyncTreeNode({text: 'root',id:'-1'})   
      }); 
        
      tree_.on('click',function(node){
    //  var orgnoIdName = Ext.get("orgnoIdName").dom.value;
      var userIdName = Ext.get("userIdName").dom.value;
      if(node.leaf && node.id != " "){ 
      //setvalue by chooseing 
      //Frist,chooseing user
     
       comboxWithTree_.setValue(node.parentNode.text+'-'+node.text); 
        Ext.get(userIdName).dom.value =node.id; 
     //   Ext.get(orgnoIdName).dom.value =node.parentNode.id.substring(0,10); 
        comboxWithTree_.collapse();
      
      }
      });   
      comboxWithTree_.on('expand',function(){   
     // var creditapp  =  Ext.get("creditapp").dom.value;
     // tree_.root.reload();
      tree_.render('tree1');
      });   
      comboxWithTree_.render('comboxWithTree1');  
    }
);