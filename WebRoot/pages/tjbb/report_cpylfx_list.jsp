<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.FormatUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>�����ӯ������</title>
<jsp:include page="../commonJs.jsp" />
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
List<YlfxReport> cpylfxReportList = (List<YlfxReport>)session.getAttribute("cpylfxReportList");
String prdtType = (String)session.getAttribute("prdtType");
String prcMode = (String)session.getAttribute("prcMode");
String tjType = (String)session.getAttribute("tjType");
%>
<br/>
<%if(cpylfxReportList == null) { %>
   <p>���۲��Ի��ʽ��δ���ã���</p>
<%}else{ %>
���۲��ԣ�<%if(prcMode.equals("1")){out.print("���ʽ��");
}else if(prcMode.equals("2")){out.print("˫�ʽ��");
}else if(prcMode.equals("3")){out.print("���ʽ��");
}else if(prcMode.equals("4")){out.print("����ƥ��");} %>&nbsp;&nbsp;&nbsp;&nbsp;ͳ�����ͣ�<%=tjType.equals("1")?"����":"����" %>&nbsp;&nbsp;&nbsp;&nbsp;��λ����Ԫ
<table id="tableList">
					
     <%if(prdtType.equals("ck")) { %>
     <thead>
     <tr >
      <th align="center" width="220" >��Ʒ��������</th>
      <th align="center" width="75" >���</th>
      <th align="center" width="75" >��Ϣ֧��</th>
      <th align="center" width="65" >��Ϣ��(%)</th>
      <th align="center" width="80" >ƽ������(��)</th>
      <th align="center" width="100" >��ծת�Ƽ۸�(%)</th>
      <th align="center" width="90" >��ծ������(%)</th>
      <th align="center" width="80" >��ծת������</th>
      <th align="center" width="80" >��ծ������</th>
       </tr>
       </thead>
       <tbody>
 <%for(int i = 0; i < cpylfxReportList.size(); i++) {  %>
       <tr>
       <%YlfxReport entity = cpylfxReportList.get(i); %>
		<td align="center"><%=entity.getPrdtName()%></td>
		<td align="center"><%=entity.getFzye() == null ? 0.00 : FormatUtil.toMoney(entity.getFzye()/10000)%></td>
		<td align="center"><%=entity.getLxzc() == null ? 0.00 : FormatUtil.toMoney(entity.getLxzc()/10000)%></td>
		<td align="center"><%=entity.getFxl() == null ? 0.00 : CommonFunctions.doublecut(entity.getFxl()*100,3)%></td>
		<td align="center"><%=entity.getFzpjqx() == null ? 0.00 : entity.getFzpjqx()%></td>
		<td align="center"><%=entity.getFzzyjg() == null ? 0.00 : CommonFunctions.doublecut(entity.getFzzyjg()*100,3)%></td>
		<td align="center"><%=entity.getFzjlc() == null ? 0.00 : CommonFunctions.doublecut(entity.getFzjlc()*100,3)%></td>
		<td align="center"><%=entity.getFzzysr() == null ? 0.00 : FormatUtil.toMoney(entity.getFzzysr()/10000)%></td>
		<td align="center"><%=entity.getFzjsr() == null ? 0.00 : FormatUtil.toMoney(entity.getFzjsr()/10000)%></td>
       </tr>
       <%} %>
       </tbody>
       <%}else if(prdtType.equals("dk") || prdtType.equals("tzyw")){ %>
       <thead>
       <tr >
      <th align="center" width="220" >��Ʒ��������</th>
      <th align="center" width="75" >���</th>
      <th align="center" width="75" >��Ϣ����</th>
      <th align="center" width="70" >��Ϣ��(%)</th>
      <th align="center" width="80" >ƽ������(��)</th>
      <th align="center" width="100" >�ʲ�ת�Ƽ۸�(%)</th>
      <th align="center" width="90" >�ʲ�������(%)</th>
      <th align="center" width="80" >�ʲ�ת��֧��</th>
      <th align="center" width="80" >�ʲ�������</th>
       </tr>
       </thead>
       <tbody>
<%for(int i = 0; i < cpylfxReportList.size(); i++) {%>
       <tr>
       <%YlfxReport entity = cpylfxReportList.get(i); %>
		<td align="center"><%=entity.getPrdtName()%></td>
		<td align="center"><%=entity.getZcye() == null ? 0.00 : FormatUtil.toMoney(entity.getZcye()/10000)%></td>
		<td align="center"><%=entity.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entity.getLxsr()/10000)%></td>
		<td align="center"><%=entity.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entity.getSxl()*100,3)%></td>
		<td align="center"><%=entity.getZcpjqx() == null ? 0.00 : entity.getZcpjqx()%></td>
		<td align="center"><%=entity.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entity.getZczyjg()*100,3)%></td>
		<td align="center"><%=entity.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entity.getZcjlc()*100,3)%></td>
		<td align="center"><%=entity.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entity.getZczyzc()/10000)%></td>
		<td align="center"><%=entity.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entity.getZcjsr()/10000)%></td>
       </tr>
       <%} %>
       </tbody>
       
       <%} %>
</table>
<%} %>
<div align="center">
<input type="button" name="Submit1" style="color: #fff; background-image: url(pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  onClick="doExport()" value="��&nbsp;&nbsp;��">
<input type="button" name="Submit2" style="color: #fff; background-image: url(pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  onclick="javascript: history.back();" value="��&nbsp;&nbsp;��">	</div>
</form>
</body>
<script type="text/javascript">
j(function(){
    j('#tableList').flexigrid({
    		height: 230,width:900,
    		title: '<%if(prdtType.equals("ck")) {out.print("���");}else if(prdtType.equals("dk")) {out.print("����");}else if(prdtType.equals("tzyw")) {out.print("Ͷ��");}
    			 %>ҵ��ӯ����������'});
});
j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/report_cpylfx_export.jsp';
	window.parent.document.thisform.submit();
}
</script>
</html>
