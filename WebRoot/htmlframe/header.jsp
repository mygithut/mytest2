<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page
	import="com.dhcc.ftp.entity.TelMst,com.dhcc.ftp.util.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<link rel="stylesheet" type="text/css"
			href="css/main_jquery_style.css" />
		<title></title>
		<%TelMst user = (TelMst) request.getSession().getAttribute("userBean"); 
			List menuList = MenuTreeUtil.getFirstMenu(user.getRoleMst().getRoleNo()); 
			int num = 1;//默认第一个一级菜单被选中 %>
	</head>
	<body >
	<table width="100%" border="0" cellspacing="0" >
	<tr>
	<td style="width:430px;background: url(/ftp/htmlframe/images/header_bg1.jpg) no-repeat left center;">
	</td>
	<td style="background: url(/ftp/htmlframe/images/header_bg2.jpg) repeat-x  center;"></td>
	<td style="width:805px;height:89px;background: url(/ftp/htmlframe/images/header_bg3.jpg) no-repeat right center;">
	<div id="content">
			<div id="main">
					<div id="menu">
						<ul>
							<li id="href99">
								<a id="a99" href="#" onclick="loguser()"> <img
										style="padding-top: 10px; * padding-top: 5px; padding-bottom: 2px;"
										src="images/slides/Z.png" /> <br>
										退出系统</br> </a>
							</li>
							<%for(int i = menuList.size()-1; i >=0; i--){ 
                    //for(int i = 0; i < menuList.size(); i++){
				             Object object = menuList.get(i);
				             Object[] obj = (Object[])object;
			         	%>
							<li id="href<%=i+1 %>">
								<a id="a<%=i+1 %>"
									href="left_center.jsp?menu_parent=<%=obj[0] %>"
									target="centerFrame" onclick="changed('<%=i+1 %>')"> <img
										style="padding-top: 10px; * padding-top: 5px; padding-bottom: 2px; * padding-bottom: 2px"
										src="images/slides/<%=obj[0] %>.png" /> <br><%=obj[1] %>
								</a>
							</li>
							<%} %>
							
						</ul>
					</div>
			</div>
		</div>
		</td>
	</tr></table>
		

		<script>
    document.getElementById("href1").className ='li_hover';
    document.getElementById("a1").className ='a_selected';
	function modifypwd() {
		var tlrno = '<%=user.getTelNo()%>';
		var sreturn = self.showModalDialog('<%=request.getContextPath()%>/telmst_pwd.action?tlrno='+tlrno, "_self", "dialogWidth=35;dialogHeight=16;");
	}
	function loguser() {
		if(confirm('确定注销当前登录用户？'))
		window.top.open("<%=request.getContextPath()%>/logoutSession.jsp", "_self");
	}
	//点击链接后为被选中样式
	function changed(idt) {
	    document.getElementById("href"+idt).className ='li_hover';
	    //tp.style.background = 'images/nav_move.gif';
		for(var i=1;i<10;i++) {
		    var nametu2="href"+i;
		    if(i!=idt) {
		        var tp2=document.getElementById('href'+i);
			    if(tp2!=undefined) tp2.className ='li';
		    }
		}
	}
</script>
	</body>
</html>
