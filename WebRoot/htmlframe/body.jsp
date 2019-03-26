<%--
@description tiles/body.jsp
@date 2011-04-21
--%>
<%@ page contentType="text/html;charset=GBK"%>
<html>
	<head>
		<style type="text/css">
		.background {
	        position: absolute;
	        left: 0;
         	top: 0;
	        width: 100%;
	        height: 100%;
	        background: url('<%=request.getContextPath()%>/htmlframe/images/center_bg.jpg') bottom left no-repeat;
        }
		</style>
	    <jsp:include page="../pages/commonExt2.0.2.jsp" />
	</head>
	<body>
		<div id="tabs">
           <div id="blank" class="background">
           </div>
		</div>
	</body>
	<script type="text/javascript">
	var height=self.parent.parent.hheight-117;
	var tabs,count=1;
	Ext.onReady(function(){
	    tabs = new Ext.TabPanel({
	        renderTo:'tabs',
	        resizeTabs:true, // turn on tab resizing
	        minTabWidth: 115,
	        tabWidth:125,
	        activeTab: 0,
	        enableTabScroll:true,
	        width:'100%',
	        height:height,
	        defaults: {autoScroll:true},
            items:[
               {contentEl:'blank',id:'welcome', title: '欢迎登陆'}
             ],
             plugins: new Ext.ux.TabCloseMenu()
	    }); 

	});
    function addTab(title, href,id){
        tabs.add({
        	header  :false,
            id:href,
            title: title,
            iconCls: 'tabs',
            html:'<iframe src="'+href+'" frameborder="0" scrolling="no" width="100%" height="100%"></iframe>',
            closable:true
        }).show();
    }
    Ext.ux.TabCloseMenu = function(){
        var tabs, menu, ctxItem;
        this.init = function(tp){
            tabs = tp;
            tabs.on('contextmenu', onContextMenu);
        }
        function onContextMenu(ts, item, e){
            if(!menu){ // create context menu on first right click
                menu = new Ext.menu.Menu([{
                    id: tabs.id + '-close',
                    text: '关闭标签',
                    handler : function(){
                        tabs.remove(ctxItem);
                    }
                },{
                    id: tabs.id + '-close-others',
                    text: '关闭其他标签',
                    handler : function(){
                        tabs.items.each(function(item){
                            if(item.closable && item != ctxItem){
                                tabs.remove(item);
                            }
                        });
                    }
                },{
                    id: tabs.id + '-close-all',
                    text: '关闭全部标签',
                    handler : function(){
                        tabs.items.each(function(item){
                            if(item.closable){
                                tabs.remove(item);
                            }
                        });
                    }
                }
<%--                ,{--%>
<%--                    id: tabs.id + '-open',--%>
<%--                    text: '打开当前标签',--%>
<%--                    handler : function(){--%>
<%--	                    tabs.add({--%>
<%--	                    	header  :false,--%>
<%--	                        title: ctxItem.title,--%>
<%--	                        iconCls: 'tabs',--%>
<%--	                        id:ctxItem.id+"#"+count,--%>
<%--	                        html:'<iframe src='+ctxItem.id+' frameborder="0" scrolling="no" width="100%" height="100%"></iframe>',--%>
<%--	                        closable:true--%>
<%--	                    }).show();	 --%>
<%--	                    count++;--%>
<%--                    }--%>
<%--                }--%>
                ]);
            }
            ctxItem = item;
            var items = menu.items;
            items.get(tabs.id + '-close').setDisabled(!item.closable);
            var disableOthers = true;
            tabs.items.each(function(){
                if(this != item && this.closable){
                    disableOthers = false;
                    return false;
                }
            });
            items.get(tabs.id + '-close-others').setDisabled(disableOthers);
            var disableAll = true;
            tabs.items.each(function(){
                if(this.closable){
                    disableAll = false;
                    return false;
                }
            });
            items.get(tabs.id + '-close-all').setDisabled(disableAll);
<%--            items.get(tabs.id + '-open').setDisabled(!item.closable);--%>
            menu.showAt(e.getPoint());
        }
    };
	</script>
</html>
