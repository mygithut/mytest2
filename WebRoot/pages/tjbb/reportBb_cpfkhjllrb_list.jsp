<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>
<html>
  <head>
        <title>��Ʒ�ֿͻ�����FTP�����</title>
          <link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
          <jsp:include page="../commonJs.jsp" />
  </head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("cpfkhjllrbReportList");
String minDate = (String)session.getAttribute("cpfkhjllrbMinDate");
String maxDate = (String)session.getAttribute("cpfkhjllrbMaxDate");
String brName = (String)session.getAttribute("cpfkhjllrbBrName");
String businessName = (String)session.getAttribute("cpfkhjllrbBusinessName");
String prdtCtgName = (String)session.getAttribute("cpfkhjllrbPrdtCtgName");
String prdtCtgNo = (String)session.getAttribute("cpfkhjllrbPrdtCtgNo");
String prdtName = (String)session.getAttribute("cpfkhjllrbPrdtName");
String prdtNo = (String)session.getAttribute("cpfkhjllrbPrdtNo");

int days = CommonFunctions.daysSubtract(maxDate, minDate);//����
%>
<br/>
<%if(ylfxBbReportList == null) { %>
   <p>����ͳ�ƴ�������ϵ����Ա����</p>
<%}else{ 
	double zftplr = 0;//�ܵ�ftp����
	for(YlfxBbReport ylfxBbReport : ylfxBbReportList) {
		zftplr += ylfxBbReport.getFtplr();
	}
%>
	     <div align="center">������<%= brName%>
	        &nbsp;&nbsp;&nbsp;&nbsp;����ʱ�Σ�<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
	        &nbsp;&nbsp;&nbsp;&nbsp;��λ����Ԫ��%(������)
	     </div>

	<div align="center" style="width: 100%;height: 220px; overflow-x: scroll">
		<table align="left" width="1350px" class="table" cellpadding="0" cellspacing="0" >
			<tr>
				<td class="middle_header" colspan="8">
					<font style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold"><%=(prdtNo==null||prdtNo.equals(""))?((prdtCtgNo==null||prdtCtgNo.equals(""))?businessName:prdtCtgName):prdtName%>-��Ʒ�ֿͻ�����FTP����� </font>
				</td>
			</tr>
			<tr>
				<th align="center" width="350">�ͻ���������</th>
				<th align="center" width="200" >��������</th>
				<th align="center" width="150" >���</th>
				<th align="center" width="150" >�վ����</th>
				<th align="center" width="150" >����(%)</th>
				<th align="center" width="150" >FTP����</th>
				<th align="center" width="150" >FTP����ռ��(%)</th>
				<th align="center" width="150" >����</th>
			</tr>

			<%
				double rjye=0, ftplr=0, lc=0,bal=0;
				for (int i = 0; i < ylfxBbReportList.size(); i++){
					YlfxBbReport entity = ylfxBbReportList.get(i);
					rjye += entity.getRjye();
					ftplr += entity.getFtplr();
					bal += entity.getBal();
			%>
			<tr>
				<td align="center" width="350"><%=entity.getEmpName()%>[<%=entity.getEmpNo()%>]</td>
				<td align="center" width="200"><%=entity.getBrName()%>[<%=entity.getBrNo()%>]</td>
				<td align="right" width="150"><%=FormatUtil.toMoney(entity.getBal()/10000)%></td>
				<td align="right" width="150"><%=FormatUtil.toMoney(entity.getRjye()/10000)%></td>
				<td align="right" width="150"><%=CommonFunctions.doublecut(entity.getLc()*100,6)%></td>
				<td align="right" width="150"><%=FormatUtil.toMoney(entity.getFtplr()/10000)%></td>
				<td align="right" width="150"><%=CommonFunctions.doublecut(entity.getFtplr()/zftplr*100,2)%></td>
				<td align="center" width="150"><%=i+1%></td>
			</tr>
			<%} %>
			<tr>
				<td align="center" width="350"><font style="font-weight: bold">�ϼ�</font></td>
				<td align="center" width="200"><font style="font-weight: bold">-</font></td>
				<td align="right" width="150"><font style="font-weight: bold"><%=FormatUtil.toMoney(bal/10000)%></font></td>
				<td align="right" width="150"><font style="font-weight: bold"><%=FormatUtil.toMoney(rjye/10000)%></font></td>
				<td align="right" width="150"><font style="font-weight: bold"><%=CommonFunctions.doublecut(rjye==0?0.00:ftplr/rjye*360/days*100,6)%></font></td>
				<td align="right" width="150"><font style="font-weight: bold"><%=FormatUtil.toMoney(ftplr/10000)%></font></td>
				<td align="right" width="150"><font style="font-weight: bold"><%=CommonFunctions.doublecut(100,2)%></font></td>
				<td align="center" width="150"><font style="font-weight: bold">-</font></td>
			</tr>
		</table>
	</div>
	<div align="center" width="1350">
		<input type="button" name="Submit1" style="color: #fff; background-image: url(<%=request.getContextPath()%>/pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
			   onClick="doExport()" value="��&nbsp;&nbsp;��">
	</div>
</form>
<%} %>
</body>

<script type="text/javascript">


j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/reportBb_cpfkhjllrb_export.jsp';
	window.parent.document.thisform.submit();
}
</script>
</html>
