<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.FormatUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<jsp:include page="../commonJs.jsp" />
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
System.out.println("###### ��ʼ���ػ�����ӯ�������б�ҳ��...");
List<YlfxReport> ylfxReportList = (List<YlfxReport>)session.getAttribute("ylfxReportList");
//Integer isFirst = (Integer)request.getAttribute("isFirst");//�Ƿ���ʾ�����ء���ť
Integer isFirst = Integer.valueOf(request.getParameter("isFirst"));//�Ƿ���ʾ�����ء���ť
String prcMode = (String)session.getAttribute("prcMode");
%>
<br/>
<%if(ylfxReportList == null) { %>
   <p>���۲��Ի��ʽ��δ���ã���</p>
<%}else{ %>
���۲��ԣ�<%if(prcMode.equals("1")){out.print("���ʽ��");
}else if(prcMode.equals("2")){out.print("˫�ʽ��");
}else if(prcMode.equals("3")){out.print("���ʽ��");
}else if(prcMode.equals("4")){out.print("����ƥ��");} %>&nbsp;&nbsp;&nbsp;&nbsp;��λ����Ԫ
<table id="tableList">
					<thead>
     <tr >
      <th align="center" width="270" >����</th>
      <th align="center" width="80" ><font style="font-weight:bold">�ʲ����</font></th>
      <th align="center" width="80" >��Ϣ����</th>
      <th align="center" width="80" >��Ϣ��(%)</th>
      <th align="center" width="80" >ƽ������(��)</th>
      <th align="center" width="100" >�ʲ�ת�Ƽ۸�(%)</th>
      <th align="center" width="90" >�ʲ�������(%)</th>
      <th align="center" width="80" >�ʲ�ת��֧��</th>
      <th align="center" width="80" >�ʲ�������</th>
      <th align="center" width="80" ><font style="font-weight:bold">��ծ���</font></th>
      <th align="center" width="80" >��Ϣ֧��</th>
      <th align="center" width="80" >��Ϣ��(%)</th>
      <th align="center" width="80" >ƽ������(��)</th>
      <th align="center" width="100" >��ծת�Ƽ۸�(%)</th>
      <th align="center" width="90" >��ծ������(%)</th>
      <th align="center" width="80" >��ծת������</th>
      <th align="center" width="80" >��ծ������</th>
      <th align="center" width="80" ><font style="font-weight:bold">������</font></th>
      <th align="center" width="80" >��ϸ</th>
       </tr>
       </thead><tbody>
     <% 
         for (YlfxReport entity:ylfxReportList){
     %>
     
        <tr>
     	<td align="center"><%=entity.getBrName()%></td>
		<td align="center"><font style="font-weight:bold"><%=FormatUtil.toMoney(entity.getZcye()/10000)%></font></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getLxsr()/10000)%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getSxl()*100,3)%></td>
		<td align="center"><%=entity.getZcpjqx()%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getZczyjg()*100,3)%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getZcjlc()*100,3)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getZczyzc()/10000)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getZcjsr()/10000)%></td>
		<td align="center"><font style="font-weight:bold"><%=FormatUtil.toMoney(entity.getFzye()/10000)%></font></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getLxzc()/10000)%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getFxl()*100,3)%></td>
		<td align="center"><%=entity.getFzpjqx()%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getFzzyjg()*100,3)%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getFzjlc()*100,3)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getFzzysr()/10000)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getFzjsr()/10000)%></td>
		<td align="center"><font style="font-weight:bold"><%=FormatUtil.toMoney(entity.getJsr()/10000)%></font></td>
		<td align="center">
		<%if(!entity.getManageLvl().equals("0")) { %>
		<a href="javascript:doQuery('<%=entity.getBrNo() %>','<%=entity.getManageLvl() %>')">��ϸ</a>
		<%}else{ %>
		��
		<%} %>
		</td>
		</tr>
     <% }
     %></tbody>
</table>
<%} %>

<div align="center" width="1770">
<input type="button" name="Submit1" style="color: #fff; background-image: url(../themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  onClick="doExport()" value="��&nbsp;&nbsp;��">
<%if(isFirst == 0){ %>
<input type="button" name="Submit2" style="color: #fff; background-image: url(../themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					 onclick="javascript: history.back();" value="��&nbsp;&nbsp;��">	
<%} %>
</div>
</form>
</body>

<script type="text/javascript">
parent.parent.parent.parent.cancel();

j(function(){
    j('#tableList').flexigrid({
    		height: 230,width:900,
    		title: '������ӯ����������_�Է���'});
});

function doQuery(brNo, manageLvl){
	   var date = window.parent.document.getElementById("date").value;
	   var assessScope = window.parent.document.getElementById("assessScope").value;
	   //window.location.href ='/ftp/REPORTSFB_jgzylfxReport.action?isMx=1&brNo='+brNo+'&manageLvl='+manageLvl+'&date='+date+'&assessScope='+assessScope+'&isFirst=0';
	   parent.parent.parent.parent.openNewDiv();
	   var url = "<%=request.getContextPath()%>/REPORTSFB_jgzylfxReport.action";
	    new Ajax.Request(url, {
			method : 'post',
			parameters : {
			 	brNo:brNo,manageLvl:manageLvl,date:date,assessScope:assessScope,isMx:1,t:new Date().getTime()
			},
			onSuccess : function() {
<%--				alert('��̨����ɹ�');--%>
				window.location.href="report_jgzylfx_sfb_list.jsp?isFirst=0"; 
		    }
	    });
}
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/ssbb/report_jgzylfx_sfb_export.jsp';
	window.parent.document.thisform.submit();
}
</script>
</html>
