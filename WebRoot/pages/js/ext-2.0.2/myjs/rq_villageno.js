Ext.onReady(
function(){
var comboxWithTree = new Ext.form.ComboBox({   
	    id:'villageCombox',
        store:new Ext.data.SimpleStore({fields:[],data:[[]]}),   
        editable:false,   
        mode: 'local',   
        triggerAction:'all',  
        maxHeight: 200, 
        width: 280, 
        emptyText:'请选择所在地区......',
        tpl: "<tpl for='.'><div style='height:200px'><div id='tree'></div></div></tpl>",   
        selectedClass:'',   
        onSelect:Ext.emptyFn   
    });   
    var tree = new Ext.tree.TreePanel({   
           loader:new Ext.tree.TreeLoader({
          dataUrl:'../rqtreeAction.do'
        }),
          border:false,   
          rootVisible:false,
         root:new Ext.tree.AsyncTreeNode({text: '行政村',id:'-1'})   
      });   
      tree.on('click',function(node){ 
       if(node.id.length > 4) {
          if(node.id.length == 10){
          comboxWithTree.setValue((node.parentNode).parentNode.parentNode.text+"-"+(node.parentNode).parentNode.text+"-"+node.parentNode.text+"-"+ node.text);   
          }
          else if(node.id.length == 8){
          comboxWithTree.setValue((node.parentNode).parentNode.text+"-"+node.parentNode.text+"-"+ node.text);   
          }else{
          comboxWithTree.setValue(node.parentNode.text+"-"+ node.text);   
          }
         var villageDomId = Ext.get("villageDomId").dom.value;
         Ext.get(villageDomId).dom.value =node.id;
         var villageNameDomId = Ext.get("villageNameDomId").dom.value;
         if(villageNameDomId != 'none'){
         Ext.get(villageNameDomId).dom.value = Ext.get("villageCombox").dom.value;
         }
         comboxWithTree.collapse();   
          }
      });   
    comboxWithTree.on('expand',function(){   
        tree.render('tree');   
      });   
    comboxWithTree.render('comboxWithTree');
       });