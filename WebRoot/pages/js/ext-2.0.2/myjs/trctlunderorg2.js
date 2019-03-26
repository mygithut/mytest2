Ext.BLANK_IMAGE_URL = '/personnel/personnel/js/ext2/resources/images/default/s.gif';
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
        emptyText:'请点击选择!',
        tpl: "<tpl for='.'><div style='height:200px'><div id='tree1'></div></div></tpl>",   
        selectedClass:'',   
        onSelect:Ext.emptyFn   
    });   
    var tree_ = new Ext.tree.TreePanel({   
           loader:new Ext.tree.TreeLoader({
          dataUrl:'tlrctlOrgTreeAction2.action'
        }),
          border:false,
        autoScroll : true,
          rootVisible:false,
         root:new Ext.tree.AsyncTreeNode({text: 'root',id:'-1'})   
      });   
      tree_.on('click',function(node){   
         /*这里node.id 的组成：brno_ficode*/
         var leaf=node.leaf;
         if (!leaf){
        	 alert("请选择子级！！");
         }else{
        	 comboxWithTree_.setValue(node.text);
             var t=node.id;
             temp = t.split("_"); 
             //var noflag = Ext.get("noflag").dom.value;
             Ext.get("businessNo").dom.value =temp[0];
             Ext.get("name3").dom.value =temp[2];
             Ext.get("name4").dom.value =temp[3];
             Ext.get("curveMethodNo").dom.value =temp[4];
             Ext.get("ftpMethodNo").dom.value =temp[5];
             Ext.get("curvePointNo").dom.value =temp[6];
             //Ext.get(noflag).dom.value =temp[2];
             //Ext.get(noflag).dom.fireEvent('onchange');
             if(document.all("ficode")){
         	   Ext.get("ficode").dom.value = temp[1];
             }  
      	     comboxWithTree_.collapse(); 
      	     if(temp[4]=="null"){
      	        var img1 = document.getElementById("getTransPrice"); 
      	        img1.setAttribute("disabled","disabled");
      	        document.getElementById("transPrice").readOnly=false;
      	     }else{
      	    	
      	     }
      	  }
      });   
      comboxWithTree_.on('expand',function(){   
      tree_.render('tree1');});   
      comboxWithTree_.render('comboxWithTree2');  
    }
);