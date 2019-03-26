<%@ page contentType="text/html;charset=GBK"%>
<%@ page language="java"
	import="com.dhcc.ftp.entity.*,java.util.*,com.dhcc.ftp.util.*"
	session="true"%>
<%@page import="com.dhcc.ftp.util.PageUtil"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%  String brNo =(String)request.getAttribute("brNo"); 
	PageUtil BrnoUtil =(PageUtil)request.getAttribute("BrnoUtil"); 
	List<BrMst> brlist = BrnoUtil.getList();
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<TITLE>机构管理</TITLE>
		<meta http-equiv="Content-Type" content="text/html">
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
		<meta http-equiv="pragram" content="no-cache" />
		<jsp:include page="../commonJs.jsp"></jsp:include>
	</head>
	<body leftmargin="0" topmargin="0">
	<form action="" id="form1" method="get" style="display: none;">
		<table id="tableList">
			<thead>
				<tr>
					<th align="center" width="100">
						机构编号
					</th>
					<th align="center" width="250">
						机构名称
					</th>
					<th align="center" width="80">
						机构负责人
					</th>
					<th align="center" width="100">
						机构代码
					</th>

					<th align="center" width="100">
						联系电话
					</th>

					<th align="center" width="100">
						选择
					</th>
				</tr>
			</thead>
			<%
								 for (BrMst brmst:brlist) {
									%>
			<tbody>
				<tr onclick="javascript:myselect('<%=brmst.getBrNo()%>')">

					<td align="center">
						<font size="2"><%=brmst.getBrNo()%></font>
					</td>
					<td align="center">
						<font size="2"><%=brmst.getBrName()%></font>
					</td>
					<td align="center">
						<font size="2"><%=CastUtil.trimNull(brmst.getChargePersonName())%></font>
					</td>

					<td align="center">
						<font size="2"><%=CastUtil.trimNull(brmst.getFbrCode())%></font>
					</td>

					<td align="center">
						<font size="2"><%=CastUtil.trimNull(brmst.getConPhone())%></font>
					</td>

					<td align="center">
						<input type="radio" name="brno" id="<%=brmst.getBrNo()%>">
					</td>
				</tr>
			</tbody>
			<%
											brmst=null;
											}
											brlist=null;
										%>
		</table>
		<table border="0" width="100%" class="tb1"
			style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
			align="center">
			<tr>
				<td align="right"><%=BrnoUtil.getPageLine()%></td>
			</tr>
		</table>
		<input type="hidden" name="selectbrno" id="selectbrno" value="">
		</form>
	</body>

	<script type="text/javascript" language="JavaScript1.2">
	j(function(){
	document.getElementById("form1").style.display="block";
	    j('#tableList').flexigrid({
	    		height: 240,width:800,
	    		title: '机构管理',
	    		buttons : [
	   			   		{name: '新增', bclass: 'add', onpress : add},
	   			   		{name: '查看详情', bclass: 'edit', onpress : my_detail},
	   			   		{name: '删除', bclass: 'delete', onpress : my_del}
<%--	   			   		{name: '岗位维护', bclass: 'edit', onpress : myPost}--%>
	   			   		]});
	});
function myselect(tranloanno)
{
document.all("selectbrno").value=tranloanno;
document.getElementById(tranloanno).checked=true;
}
function myPost(){
	var brNo=document.getElementById("selectbrno").value;
	if (brNo==null || brNo.length==0){
		alert("请选择一项！");
		return false;
	}
	window.parent.location.href="<%=basePath%>FtpBrPostRel_List.action?brNo="+brNo;
}
function my_detail()
{
 var brno=document.getElementById("selectbrno").value;
 //brno= document.getElementsByName("brno");
 if (brno==null || brno.length==0)
   {
	alert("请选择一项！");
	return false;
   }else
   {
		art.dialog.open('<%=request.getContextPath()%>/brmst_detail.action?brNo='+brno+'&superBrNo=<%=brNo%>&random='+<%=Math.random()%>, {
		    title: '机构管理',
		    width: 700,
		    height:350
		});
    }
}


//删除
function my_del()
{
 var brno= document.getElementById("selectbrno").value;
 if (brno==null || brno.length==0) {
	alert("请选择一项！");
	return false;
   }else {
	   art.dialog({
		    title:'删除机构',
  		    icon: 'question',
		    content: '确定执行此操作？',
		    ok: function () {
		       var url = "<%=request.getContextPath()%>/brmst_del.action";
			   new Ajax.Request(url, {
				   method : 'post',
				   parameters : {
				       brNo : brno
				   },
				   onSuccess : function() {
			    	   art.dialog({
		               	    title:'成功',
		           		    icon: 'succeed',
		           		    content: '删除成功！',
		           		    ok: function () {
		           		    	ok();
		           		        return true;
		           		    }
		               });
				  }
			  });
		   },
	       cancelVal: '关闭',
	       cancel: true //为true等价于function(){}
	   });
     }
}
function ok () {
	window.parent.location.reload();
	//window.open("brmst_list.action?brNo="+brNo,"_self","");
}
function my_back(){
	self.open("<%=request.getContextPath()%>/distribute.action?url=JGGL&method=<%=Math.random()%>","_self","");
}
function add() {
	art.dialog.open('<%=request.getContextPath()%>/brmst_addBrno.action?brNo=<%=brNo%>&random='+<%=Math.random()%>, {
	    title: '新增机构',
	    width: 700,
	    height:350
	});
}
</script>