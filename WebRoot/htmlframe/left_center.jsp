<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@page import="com.dhcc.ftp.util.MenuTreeUtil"%>
<%String isFirst = (String)request.getParameter("isFirst");//是否是第一次进入系统
  String menu_parent=(String)request.getParameter("menu_parent");//获取
  String roleno =(String)session.getAttribute("roleno");
  Map<String, String[][]> menu = MenuTreeUtil.getChildMenu(menu_parent, roleno);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<link rel="stylesheet" type="text/css" href="css/menu.css" />
<script type="text/javascript" language=javascript src="<%=request.getContextPath()%>/pages/js/jquery-1.3.2.min.js"></script>
</head>
<body>
		<div id="content">
			<ul id="expmenu-freebie">
				<li>
					<ul class="expmenu">
					<%
					  //Set keySet = menu.keySet();
					if(menu != null) {
						int m = 0;
					  String[] keySet = menu.keySet().toArray(new String[0]);
					  for (int j = 0; j  < keySet.length; j++) {
					  %>
						<li id="li">
							<div class="header">
								<span class="label"><%=keySet[j] %></span>
								<span <%if(j == 0){%> class="arrow up"<%}else{ %>class="arrow down"<%} %>></span>
							</div>
							<ul class="menu" <%if(j != 0){%> style="display: none"<%} %>>
							<%String[][] childMenu = menu.get(keySet[j]);
							  for(int i = 0; i < childMenu.length; i++) {
								  
							%>
								<li id="a<%=m %>" name="<%=childMenu[i][0] %>" show="<%=childMenu[i][1] %>" url="<%=childMenu[i][2] %>">
								<span class="arrow left"></span>
								<span class="menu_li"><%=childMenu[i][1] %></span>
								</li>
								<%m++;} %>
							</ul>
						</li>
					<% }
					}%>
						
					</ul>
				</li>
			</ul>
		</div>
	</body>
<script>
var isFirst = <%=isFirst%>;

$(document).ready(function(){
<%--	 var target = $("#a0");//默认选中第一个--%>
<%--	 if (isFirst == null && target != null) {--%>
<%--		 target.addClass("selected");--%>
<%--		 window.parent.frames.rightFrame.addTab(target.attr("show"), target.attr("url"), target.attr("id"));--%>
<%--			 if(target.attr("name") == 'C013'||target.attr("name") == 'C011'||target.attr("name") == 'C021') {//贷款申请审批、贷款审批、提前还款审批，要先进行数据库的操作，所以要打开遮罩--%>
<%--					parent.parent.openNewDiv();--%>
<%--			 }--%>
<%--		 $('ul.expmenu li ul li').not(target).removeClass("selected");--%>
<%--			 target.parent().find("ul.menu li").not(selected).removeClass("selected");--%>
<%--	 }--%>
	/* 单击某个二级菜单的响应方法 */
	$('div.header').click(function(){
		//改变以选中二级菜单的样式
		var arrow = $(this).find("span.arrow");
		if(arrow.hasClass("up")) {
			arrow.removeClass("up");
			arrow.addClass("down");
		}else if(arrow.hasClass("down")) {
			arrow.removeClass("down");
			arrow.addClass("up");
		}
		//获取当前的二级菜单对应的ul
		var dropDown = $(this).parent().find("ul.menu");
		//先关闭所有其它的二级菜单
		$('ul.menu').not(dropDown).slideUp('slow');
		//更改所有其它二级菜单的样式为未选中
		$('span.arrow').not(arrow).removeClass("up");
		$('span.arrow').not(arrow).addClass("down");
		//当前二级菜单的做出响应
		dropDown.slideToggle('slow');

		//对应的第一个三级菜单选中
<%--		var first = $(this).parent().find("ul.menu li:first");//获取第一个三级菜单--%>
<%--		first.addClass("selected");--%>
<%--		 $('ul.expmenu li ul li').not(first).removeClass("selected");--%>
<%--		window.parent.frames.rightFrame.addTab(first.attr("show"), first.attr("url"), first.attr("id"));--%>
<%--			if(first.attr("name") == 'C013'||first.attr("name") == 'C011'||first.attr("name") == 'C021') {//贷款申请审批、贷款审批、提前还款审批，要先进行数据库的操作，所以要打开遮罩--%>
<%--				parent.parent.openNewDiv();--%>
<%--		    }--%>
	})
	$('ul.expmenu li ul li').click(function(){
		var selected = $(this);
		selected.addClass("selected");
		window.parent.frames.rightFrame.addTab(selected.attr("show"), selected.attr("url"), selected.attr("id"));
		$('ul.expmenu li ul li').not(selected).removeClass("selected");
<%--			if(selected.attr("name") == 'C013'||selected.attr("name") == 'C011'||selected.attr("name") == 'C021') {//贷款申请审批、贷款审批、提前还款审批，要先进行数据库的操作，所以要打开遮罩--%>
<%--				parent.parent.openNewDiv();--%>
<%--		    }--%>
	})
});
<%-- var isFirst = <%=isFirst%>;--%>
<%-- var target = $('ul li:first-child a');//默认选中第一个--%>
<%-- if (isFirst == null && target != null) {--%>
<%--	 var link = target.attr("link");--%>
<%--	 var name = target.attr("name");--%>
<%--	 var id = target.attr("id");--%>
<%--	 doClick(name,link,id);--%>
<%--	 target.parent("li").addClass("selected");--%>
<%-- }--%>
<%-- $('ul li').click(function(){--%>
<%--		var selected = $(this);--%>
<%--		selected.addClass("selected");--%>
<%--		$('ul li').not(selected).removeClass("selected");--%>
<%-- })--%>
<%-- //var count = 1;--%>
<%-- function doClick(title, href, id) {--%>
<%--	 // var tab = window.parent.tabs.getComponent(id);--%>
<%--	//  if(tab == null) {--%>
<%--		 self.parent.frames["rightFrame"].addTab(title, href, id);--%>
<%--	 // }else {--%>
<%--	//	 window.parent.tabs.setActiveTab(tab);--%>
<%--	 // }--%>
<%-- }--%>
<%-- function selected(id){--%>
<%-- 	var selected = $('#'+id);--%>
<%--	selected.addClass("selected");--%>
<%--	$('ul li').not(selected).removeClass("selected");--%>
<%-- }--%>
</script>
