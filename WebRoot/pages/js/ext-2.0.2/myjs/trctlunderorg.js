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
        emptyText:'�����ѯ����������Ϣ�����Բ��õ��ѡ��',
        tpl: "<tpl for='.'><div style='height:200px'><div id='tree1'></div></div></tpl>",   
        selectedClass:'',   
        onSelect:Ext.emptyFn   
    });   
    var tree_ = new Ext.tree.TreePanel({   
           loader:new Ext.tree.TreeLoader({
          dataUrl:'tlrctlOrgTreeAction.action'
        }),
          border:false,
        autoScroll : true,
          rootVisible:true,
         root:new Ext.tree.AsyncTreeNode({text: 'root',id:'-1'})   
      });   
      tree_.on('click',function(node){   
         comboxWithTree_.setValue(node.text);
         /*����node.id ����ɣ�brno_ficode*/
         var t=node.id;
         temp = t.split("_"); /*�ָ�������źͼ���temp[0]Ϊ�����š�temp[1]Ϊ����*/        
         
         var noflag = Ext.get("noflag").dom.value;
         Ext.get(noflag).dom.value = temp[0];         
         
         if(document.all("ficode")){
         	Ext.get("ficode").dom.value = temp[1];
         }         
     	 comboxWithTree_.collapse(); 
      });   
      comboxWithTree_.on('expand',function(){   
      tree_.render('tree1');});   
      comboxWithTree_.render('comboxWithTree1');  
    }
);