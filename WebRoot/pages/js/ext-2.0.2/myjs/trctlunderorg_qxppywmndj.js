/*����ƥ��ҵ��ģ�ⶨ��
 * ѡ��ĳ��������Ҫִ�и��ݻ����Ͳ�Ʒ��Ż�ȡ���۷�����ŵķ���
 */
Ext.BLANK_IMAGE_URL = '/ftp/pages/js/ext-2.0.2/resources/images/default/s.gif';
Ext.onReady(function() {
			var comboxWithTree_ = new Ext.form.ComboBox(
					{
						name : 'comboxname',
						store : new Ext.data.SimpleStore( {
							fields : [],
							data : [ [] ]
						}),
						editable : false,
						mode : 'local',
						triggerAction : 'all',
						queryDelay : 400,
						maxHeight : 200,
						width : 400,
						emptyText : Ext.get("brName").dom.value,
						tpl : "<tpl for='.'><div style='height:300px'><div id='tree1'></div></div></tpl>",
						selectedClass : '',
						onSelect : Ext.emptyFn
			         }
			);
			
			var tree_ = new Ext.tree.TreePanel( {
				loader : new Ext.tree.TreeLoader( {
					dataUrl : '/ftp/tlrctlOrgTreeAction.action'
				}),
				border : false,
				rootVisible : false,
                autoScroll : true,
				root : new Ext.tree.AsyncTreeNode( {
					text : 'root',
					id : '-1'
				})
			});
			
			tree_.on('click', function(node) {
				comboxWithTree_.setValue(node.text);
				/* ����node.id ����ɣ�brno_ficode */
				var t = node.id;
				temp = t.split("_"); /* �ָ�������š������ϼ������ţ�temp[0]Ϊ�����š�temp[1]Ϊ����temp[2]Ϊ�ϼ������� */

				// var noflag = Ext.get("noflag").dom.value;
				Ext.get("brNo").dom.value = temp[0];
				// Ext.get("brno").dom.fireEvent('onchange');
				if (document.all("manageLvl")) {
					Ext.get("manageLvl").dom.value = temp[1];
				}
				if (document.all("brName")) {
					Ext.get("brName").dom.value = node.text;
				}
				comboxWithTree_.collapse();
				getMethodNo();//��ȡ��Ӧ�Ķ��۷���
			});
			
			comboxWithTree_.on('expand', function() {
				tree_.render('tree1');
			});
			
			comboxWithTree_.render('comboxWithTree1');
});