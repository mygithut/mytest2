<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.ftp.util.*,com.dhcc.ftp.util.*,java.util.Map,java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Expires " content="0 ">
        <meta http-equiv="Cache-Control " content="no-cache,must-revalidate ">
        <meta http-equiv="Pragma " content="no-cache ">
		<title>收益率曲线-历史</title>
		<jsp:include page="commonJs.jsp" />
 </head>
	<body>
     <%
     PageUtil UL04Util = (PageUtil)request.getAttribute("UL04Util");
     List list = UL04Util.getList();

     String pastNo = "";
     %>
	<form action="" id="form1" style="display: none;">    
	<div align="center">
		<table id="tableList">
			<thead>
				<tr>
	   <th width="100"><input type="checkbox" name="all" value="curve"
							onClick="checkAll()" />
						全选</th>
	   <th width="200">曲线名称</th>
	   <th width="100">市场类型</th>
	   <th width="250">机构名称</th>
	   <th width="100">日期</th>
	 </tr>
	 </thead>
	 <%
	 for (int i = 0; i < list.size(); i++) { 
	       Object[] o = (Object[]) list.get(i);
	 %>
	 <tbody>
	 <tr>
	     <td align="center"><input type="checkbox" id="curve" name="curve" value="<%=o[1] %>|<%=o[2] %>|<%=o[3] %>|<%=o[4] %>|<%=o[5] %>"/></td>
	     <td align="center"><%=o[1] %></td>
	     <td align="center"><%=o[2].equals("01")? "存贷款":"市场" %></td>
	     <td align="center"><%=o[7] %></td>
	     <td align="center"><%=o[4] %></td>
	     
	 </tr>
	 </tbody>
	 <%} %>
	</table>
	<table border="0" width="90%" class="tb1"
				style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
				align="center">
				<tr>
					<td align="right"><%=UL04Util.getPageLine()%></td>
				</tr>
	</table>
	</div>
</form>	
	<script type="text/javascript">
	document.getElementById("form1").style.display="block";
	j(function(){
	    j('#tableList').flexigrid({
	    		height: 200,width:900,
	    		theFirstIsShown: false,//第一列在是否显示下拉列表中是否出现(某些列表第一列是全选按钮)
	    		title: '查询列表',
	    		buttons : [
	      			    {name: '查看', bclass: 'list', onpress : detail}
	   			   		]});
	});
function detail(no)
{
	var o;
	   o = document.getElementsByName("curve");
	   var m =0;
	   var curve="";
	   for(var i=0;i<o.length;i++){
	       if(o[i].checked){
	    	   curve = curve +o[i].value+",";
	       }
	   }
	   if (curve==""){
		  alert("请至少选择一条曲线！");
		  return false;
	   }else{
		   art.dialog.open('<%=request.getContextPath()%>/UL04_curve_history.action?curve='+curve+'&random='+<%=Math.random()%>, {
			    title: '曲线历史',
			    width: 920,
			    height:390
			});
	   }
} 
function checkAll() {
	var selectFlags = document.getElementsByName("curve");
	for (var i=0; i<selectFlags.length; i++) {
		selectFlags[i].checked = window.event.srcElement.checked;//通过单击的按钮判断是选中还是未选
	}
}
function onclick_back(){
	   window.parent.location.href='<%=request.getContextPath()%>/distribute.action?url=SYLQXLSCK';
	}
		</script>		
	</body>
</html>
