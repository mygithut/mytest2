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
		<TITLE>��������</TITLE>
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
						�������
					</th>
					<th align="center" width="250">
						��������
					</th>
					<th align="center" width="80">
						����������
					</th>
					<th align="center" width="100">
						��������
					</th>

					<th align="center" width="100">
						��ϵ�绰
					</th>

					<th align="center" width="100">
						ѡ��
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
	    		title: '��������',
	    		buttons : [
	   			   		{name: '����', bclass: 'add', onpress : add},
	   			   		{name: '�鿴����', bclass: 'edit', onpress : my_detail},
	   			   		{name: 'ɾ��', bclass: 'delete', onpress : my_del}
<%--	   			   		{name: '��λά��', bclass: 'edit', onpress : myPost}--%>
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
		alert("��ѡ��һ�");
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
	alert("��ѡ��һ�");
	return false;
   }else
   {
		art.dialog.open('<%=request.getContextPath()%>/brmst_detail.action?brNo='+brno+'&superBrNo=<%=brNo%>&random='+<%=Math.random()%>, {
		    title: '��������',
		    width: 700,
		    height:350
		});
    }
}


//ɾ��
function my_del()
{
 var brno= document.getElementById("selectbrno").value;
 if (brno==null || brno.length==0) {
	alert("��ѡ��һ�");
	return false;
   }else {
	   art.dialog({
		    title:'ɾ������',
  		    icon: 'question',
		    content: 'ȷ��ִ�д˲�����',
		    ok: function () {
		       var url = "<%=request.getContextPath()%>/brmst_del.action";
			   new Ajax.Request(url, {
				   method : 'post',
				   parameters : {
				       brNo : brno
				   },
				   onSuccess : function() {
			    	   art.dialog({
		               	    title:'�ɹ�',
		           		    icon: 'succeed',
		           		    content: 'ɾ���ɹ���',
		           		    ok: function () {
		           		    	ok();
		           		        return true;
		           		    }
		               });
				  }
			  });
		   },
	       cancelVal: '�ر�',
	       cancel: true //Ϊtrue�ȼ���function(){}
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
	    title: '��������',
	    width: 700,
	    height:350
	});
}
</script>