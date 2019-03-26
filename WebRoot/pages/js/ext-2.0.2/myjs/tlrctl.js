
Ext.BLANK_IMAGE_URL = "../../ext2/resources/images/default/s.gif";
Ext.onReady(function () {
	var comboxWithTree_ = new Ext.form.ComboBox({
		name:"comboxname", 
		store:new Ext.data.SimpleStore({fields:[], data:[[]]}), 
		editable:false, 
		mode:"local", 
		triggerAction:"all", 
		maxHeight:200, 
		width:250, 
		emptyText:"\u4fe1\u7528\u793e\u7684\u64cd\u4f5c\u5458\uff0c\u53ef\u4ee5\u4e0d\u7528\u9009\u62e9\u67e5\u8be2", 
		tpl:"<tpl for='.'><div style='height:200px'><div id='tree1'></div></div></tpl>", 
		selectedClass:"", 
		onSelect:Ext.emptyFn
	});
	
	var tree_ = new Ext.tree.TreePanel({
		loader:new Ext.tree.TreeLoader({dataUrl:"../orguptlrctlTreeAction.do"}), 
		border:false, 
		rootVisible:false, 
		root:new Ext.tree.AsyncTreeNode({text:"root", id:"-1"})
	});
	tree_.on("click", function (node) {
		var orgnoIdName = Ext.get("orgnoIdName").dom.value;
		var userIdName = Ext.get("userIdName").dom.value;
		if (node.leaf) {
			comboxWithTree_.setValue(node.parentNode.text + "-" + node.text);
			Ext.get(userIdName).dom.value = node.id;
			Ext.get(orgnoIdName).dom.value = node.parentNode.id.substring(0, 5);
			if (document.all("ficode")) {
				Ext.get("ficode").dom.value = node.parentNode.id.substring(6, 7);
			}
			//node.parentNode.id = node.parentNode.id.substring(0, 5);
			comboxWithTree_.collapse();
		} else {
      //second,choosing org				
			comboxWithTree_.setValue(node.text);
			var tmp = node.id;
			//node.id = t.substring(0,t.length-1); alert(node.id);
			Ext.get(orgnoIdName).dom.value = tmp.substring(0, 5);
			if (document.all("ficode")) {
				Ext.get("ficode").dom.value = tmp.substring(6, 7);
			}
			Ext.get(userIdName).dom.value = "";
			comboxWithTree_.collapse();
		}
	});
	comboxWithTree_.on("expand", function () {   
		tree_.render("tree1");
	});
	comboxWithTree_.render("comboxWithTree1");
});

