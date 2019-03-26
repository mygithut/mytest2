<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.*,com.dhcc.ftp.entity.YlfxBbReport"%>

<head>
<title>���˰��ҿͻ�����FTP�����</title>
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
String filename = new String(("���˰��ҿͻ�����FTP�����").getBytes("GBK"),"ISO-8859-1");   
response.addHeader("Content-Disposition", "filename=" + filename + ".xls"); 
PageUtil grajkhjllrbReportUtil = (PageUtil)session.getAttribute("grajkhjllrbReportUtil");
List<YlfxBbReport> ylfxBbReportList = grajkhjllrbReportUtil.getList();
String minDate = (String)session.getAttribute("grajkhjllrbMinDate");
String maxDate = (String)session.getAttribute("grajkhjllrbMaxDate");
String empName = (String)session.getAttribute("grajkhjllrbEmpName");
String empNo = (String)session.getAttribute("grajkhjllrbEmpNo");
String brName = (String)session.getAttribute("grajkhjllrbBrName");

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
<div align="center"><font style="font-size:20px;font-weight:bold;">���˰��ҿͻ�����FTP�����</font></div>
<div align="center">������<%= brName%>&nbsp;&nbsp;&nbsp;&nbsp;<%if(empNo!=null&&!empNo.equals("")){ %>�ͻ�����<%= empName%>[<%= empNo%>] <%} %>
&nbsp;&nbsp;&nbsp;&nbsp;����ʱ�Σ�<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
&nbsp;&nbsp;&nbsp;&nbsp;��λ��Ԫ��%(������)</div>
<table border="1">
     <tr >
      <th align="center" width="50" >���</th>
      <th align="center" width="300" >�ϼ�����</th>
      <th align="center" width="260" >�Ͳ����</th>
      <th align="center" width="150" >�ͻ�����</th>
      <th align="center" width="150" >�˺�</th>
      <th align="center" width="280" >��Ʒ����</th>
      <th align="center" width="250" >�ͻ�����</th>
      <th align="center" width="90" >��������</th>
      <th align="center" width="90" >��������</th>
      <th align="center" width="120" >�վ����</th>
      <th align="center" width="150" >�վ�������</th>
      <th align="center" width="100" >��ͬ����(%)</th>
      <th align="center" width="100" >FTP�۸�(%)</th>
      <th align="center" width="100" >����(%)</th>
      <th align="center" width="100" >FTP����</th>
       </tr>
     <%  
         double rjye=0, ftplr=0, ratefz=0,ftpfz=0;
         for (int i = 0; i < ylfxBbReportList.size(); i++){
        	 YlfxBbReport entity = ylfxBbReportList.get(i);
        	 ratefz += entity.getRjye()*entity.getRate();
        	 ftpfz += entity.getRjye()*(entity.getFtp()==-999?0:entity.getFtp());
        	 rjye += entity.getRjye();
        	 ftplr += entity.getFtplr();
     %>
        <tr>
     	<td align="center"><%=i+1%></td>
     	<td align="center"><%=entity.getEmpSuperBrName()+"["+entity.getEmpSuperBrNo()+"]"%></td>
		<td align="center"><%=entity.getEmpBrName()+"["+entity.getEmpBrNo()+"]"%></td>
		<td align="center"><%=entity.getEmpName()+"["+entity.getEmpNo()+"]"%></td>
		<td align="center">&nbsp;<%=entity.getAcId()%></td>
     	<td align="center"><%=entity.getPrdtName()%></td>
     	<td align="center"><%=entity.getCustName()+"["+entity.getCustNo()+"]"%></td>
     	<td align="center"><%=(entity.getOpnDate().equals("")||entity.getOpnDate()==null||entity.getOpnDate().equals("null"))?"-":entity.getOpnDate()%></td>
     	<td align="center"><%=(entity.getMtrDate().equals("")||entity.getMtrDate()==null||entity.getMtrDate().equals("null"))?"-":entity.getMtrDate()%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getRjye())%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getRjye()*days)%></td>
		<td align="right"><%=CommonFunctions.doublecut(entity.getRate(),3)%></td>
		<td align="right"><%=entity.getFtp()==-999?"-":CommonFunctions.doublecut(entity.getFtp()*100,3)%></td>
		<td align="right"><%=CommonFunctions.doublecut(entity.getLc()*100,3)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getFtplr())%></td>
		</tr>
		<%} %>
		<tr>
     	<td align="center"><font style="font-weight: bold">�ϼ�</font></td>
     	<td align="center"><font style="font-weight: bold">-</font></td>
     	<td align="center"><font style="font-weight: bold">-</font></td>
     	<td align="center"><font style="font-weight: bold">-</font></td>
     	<td align="center"><font style="font-weight: bold">-</font></td>
     	<td align="center"><font style="font-weight: bold">-</font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(rjye)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(rjye*days)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(ratefz/rjye,3)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(ftpfz/rjye*100,3)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(ftplr/rjye*360/days*100,3)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(ftplr)%></font></td>
		</tr>
				</table>
<%} %>
</form>
</body>

</html>
