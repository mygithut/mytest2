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
        emptyText:'��ѡ��...',
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
         root:new Ext.tree.AsyncTreeNode({text: '��ѡ��...',id:'-1'})   
      });   
      tree_.on('click',function(node){   
         comboxWithTree_.setValue(node.text);
         /*����node.id ����ɣ�brno_ficode*/
         var t=node.id;
         temp = t.split("_"); /*�ָ�������źͼ���temp[0]Ϊ�����š�temp[1]Ϊ����*/        
         if (temp[0] == "-1") {//����Ǹ��ڵ㣬�򷵻ؿ�ֵ
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