<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>
<head>
<title>全行所有机构FTP利润排名表</title>
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("qhsyjglrpmbReportList");
String minDate = (String)session.getAttribute("qhsyjglrpmbMinDate");
String maxDate = (String)session.getAttribute("qhsyjglrpmbMaxDate");
String brName = (String)session.getAttribute("qhsyjglrpmbBrName");
/* String filename = new String(("全行所有机构FTP利润排名表").getBytes("GBK"),"ISO-8859-1");   */
String exportname= (String)session.getAttribute("exportName");
String filename = new String((exportname).getBytes("GBK"),"ISO-8859-1");
response.addHeader("Content-Disposition", "filename=" +filename + ".xls"); 

%>
<br/>
<%if(ylfxBbReportList == null) { %>
   <p>数据统计错误，请联系管理员！！</p>
<%}else{ 
	double zftplr = 0,zzcrjye=0,zfzrjye=0,zcbal=0,fzbal=0;//总的ftp利润
	double zcrjyehj=0,zcftplrhj=0,zclchj=0,fzrjyehj=0,fzftplrhj=0,fzlchj=0,ftplrhjsum=0,zcbalhj=0,fzbalhj=0,
			grhqbal=0,grdqbal=0,dwhqbal=0,dwdqbal=0,czxbal=0,yhkbal=0,yjhkbal=0,bzjbal=0,dsftplrhj=0.0,dgftplrhj=0.0,
			grhqrjye=0,grdqrjye=0,dwhqrjye=0,dwdqrjye=0,czxrjye=0,yhkrjye=0,yjhkrjye=0,bzjrjye=0,
			grhqftplr=0,grdqftplr=0,dwhqftplr=0,dwdqftplr=0,czxftplr=0,yhkftplr=0,yjhkftplr=0,bzjftplr=0,gsdkftplr=0,grdkftplr=0;
	for(YlfxBbReport ylfxBbReport : ylfxBbReportList) {
		zftplr += ylfxBbReport.getFtplrhj();
		zzcrjye += ylfxBbReport.getZcrjye();
		zfzrjye += ylfxBbReport.getFzrjye();
		zcbal+=ylfxBbReport.getZcbal();
		fzbal+=ylfxBbReport.getFzbal();
		
		zcrjyehj += ylfxBbReport.getZcrjye();
		zcbalhj += ylfxBbReport.getZcbal();//资产余额
		zcftplrhj += ylfxBbReport.getZcftplr();
		zclchj += ylfxBbReport.getZclc();
		fzrjyehj += ylfxBbReport.getFzrjye();
		fzftplrhj += ylfxBbReport.getFzftplr();
		fzlchj += ylfxBbReport.getFzlc();
		ftplrhjsum += ylfxBbReport.getFtplrhj();
		fzbalhj += ylfxBbReport.getFzbal();//负债余额
		grhqbal += ylfxBbReport.getGrhqbal();//个人活期余额
		grdqbal += ylfxBbReport.getGrdqbal();//负债余额
		dwhqbal += ylfxBbReport.getDwhqbal();//负债余额
		dwdqbal += ylfxBbReport.getDwdqbal();//负债余额
		czxbal += ylfxBbReport.getCzxbal();//负债余额
		yhkbal += ylfxBbReport.getYhkbal();//负债余额
		yjhkbal += ylfxBbReport.getYjhkbal();//负债余额
		bzjbal += ylfxBbReport.getBzjbal();//负债余额

		grhqrjye += ylfxBbReport.getGrhqrjye();//个人活期日均余额
		grdqrjye += ylfxBbReport.getGrdqrjye();//负债日均余额
		dwhqrjye += ylfxBbReport.getDwhqrjye();//负债日均余额
		dwdqrjye += ylfxBbReport.getDwdqrjye();//负债日均余额
		czxrjye += ylfxBbReport.getCzxrjye();//负债日均余额
		yhkrjye += ylfxBbReport.getYhkrjye();//负债日均余额
		yjhkrjye += ylfxBbReport.getYjhkrjye();//负债日均余额
		bzjrjye += ylfxBbReport.getBzjrjye();//负债日均余额

		grhqftplr += ylfxBbReport.getGrhqftplr();//个人活期ftp利润
		grdqftplr += ylfxBbReport.getGrdqftplr();//负债ftp利润
		dwhqftplr += ylfxBbReport.getDwhqftplr();//负债ftp利润
		dwdqftplr += ylfxBbReport.getDwdqftplr();//负债ftp利润
		czxftplr += ylfxBbReport.getCzxftplr();//负债ftp利润
		yhkftplr += ylfxBbReport.getYhkftplr();//负债ftp利润
		yjhkftplr += ylfxBbReport.getYjhkftplr();//负债ftp利润
		bzjftplr += ylfxBbReport.getBzjftplr();//负债ftp利润

		grdkftplr += ylfxBbReport.getGrdkftplr();//个人贷款ftp利润
		gsdkftplr += ylfxBbReport.getGsdkftplr();//公司贷款ftp利润

		dsftplrhj += ylfxBbReport.getDsftplr();//对私ftp利润合计
		dgftplrhj += ylfxBbReport.getDgftplr();//对公ftp利润合计
	}
%>
<div align="center"><font style="font-size:20px;font-weight:bold;">全行所有机构FTP利润排名表</font></div>
<div align="center">机构：全行
&nbsp;&nbsp;&nbsp;&nbsp;报表时段：<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
&nbsp;&nbsp;&nbsp;&nbsp;单位：万元，%</div>
<table border="1">
     <tr >
      <th align="center" width="400" >机构名称</th>
<%--      <th align="center" width="100" >资产日均余额</th>
      <th align="center" width="100" >负债日均余额</th>
      <th align="center" width="150" >FTP利润合计</th>
      <th align="center" width="150" >FTP利润占比(%)</th>
      <th align="center" width="100" >排名</th> --%>
      <th align="center" width="115" >资产余额</th>
      <th align="center" width="115" >资产日均余额</th>
	  <th align="center" width="105" >负债余额</th>
	  <th align="center" width="110" >个人活期余额</th>
	  <th align="center" width="110" >个人定期余额</th>
	  <th align="center" width="140" >单位活期余额</th>
	  <th align="center" width="140" >单位定期余额</th>
	  <th align="center" width="150" >财政性存款余额</th>
	  <th align="center" width="150" >银行卡存款余额</th>
	  <th align="center" width="150" >应解汇款余额</th>
	  <th align="center" width="150" >保证金存款余额</th>
	  
	  <th align="center" width="110" >负债日均余额</th>
	  <th align="center" width="140" >个人活期日均余额</th>
	  <th align="center" width="140" >个人定期日均余额</th>
	  <th align="center" width="140" >单位活期日均余额</th>
	  <th align="center" width="140" >单位定期日均余额</th>
	  <th align="center" width="150" >财政性存款日均余额</th>
	  <th align="center" width="150" >银行卡存款日均余额</th>
	  <th align="center" width="140" >应解汇款日均余额</th>
	  <th align="center" width="150" >保证金存款日均余额</th>

		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">个人活期FTP利润</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">个人定期FTP利润</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">单位活期FTP利润</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">单位定期FTP利润</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">财政性存款FTP利润</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">银行卡存款FTP利润</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">应解汇款FTP利润</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">保证金存款FTP利润</font></th>

		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">个人贷款FTP利润</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">公司贷款FTP利润</font></th>

		 <th align="center" width="170" ><font  style="text-align: center ; width: 100% ;display: inline-block;">零售业务FTP利润合计</font></th>
		 <th align="center" width="170" ><font  style="text-align: center ; width: 100% ;display: inline-block;">公司业务FTP利润合计</font></th>
	  
      <th align="center" width="100" >FTP利润合计</th>
      <th align="center" width="125" >FTP利润占比(%)</th>
      <th align="center" width="100" >排名</th>
      
       </tr>
     <% 
         for (int i = 0; i < ylfxBbReportList.size(); i++){
        	 YlfxBbReport entity = ylfxBbReportList.get(i);
     %>
        <tr>
     	<td align="center"><%=entity.getBrName()%>[<%=entity.getBrNo()%>]</td>
		<td align="right"><%=FormatUtil.toMoney(entity.getZcbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getZcrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getFzbal()/10000)%></td>

		<td align="right"><%=FormatUtil.toMoney(entity.getGrhqbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getGrdqbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getDwhqbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getDwdqbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getCzxbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getYhkbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getYjhkbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getBzjbal()/10000)%></td>

		<td align="right"><%=FormatUtil.toMoney(entity.getFzrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getGrhqrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getGrdqrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getDwhqrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getDwdqrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getCzxrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getYhkrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getYjhkrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getBzjrjye()/10000)%></td>

			<td ><font ><%=FormatUtil.toMoney(entity.getGrhqftplr()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getGrdqftplr()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwhqftplr()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwdqftplr()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getCzxftplr()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYhkftplr()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYjhkftplr()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getBzjftplr()/10000)%></font></td>
			<td align="center"><%=FormatUtil.toMoney(entity.getGrdkftplr()/10000)%></td>
			<td align="center"><%=FormatUtil.toMoney(entity.getGsdkftplr()/10000)%></td>

			<td align="right"><%=FormatUtil.toMoney(entity.getDsftplr()/10000)%></td>
			<td align="right"><%=FormatUtil.toMoney(entity.getDgftplr()/10000)%></td>
		
		<td align="right"><%=FormatUtil.toMoney(entity.getFtplrhj()/10000)%></td>
		<td align="right"><%=CommonFunctions.doublecut(entity.getFtplrhj()/zftplr*100,2)%></td>
		<td align="center"><%=i+1%></td>
		</tr>
     <% }
     %>
     <tr>
     	<td align="center"><font style="font-weight: bold">合计</font></td>
<%-- 		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zzcrjye/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zfzrjye/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zftplr/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(100,2)%></font></td>
		 --%>
		 
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcbal/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcrjyehj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(fzbal/10000)%></font></td>
		
	     <td align="right"><%=FormatUtil.toMoney(grhqbal/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(grdqbal/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(dwhqbal/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(dwdqbal/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(czxbal/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(yhkbal/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(yjhkbal/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(bzjbal/10000)%></td>
		 <td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(fzrjyehj/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(grhqrjye/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(grdqrjye/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(dwhqrjye/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(dwdqrjye/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(czxrjye/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(yhkrjye/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(yjhkrjye/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(bzjrjye/10000)%></td>

		 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(grhqftplr/10000)%></font></td>
		 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(grdqftplr/10000)%></font></td>
		 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(dwhqftplr/10000)%></font></td>
		 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(dwdqftplr/10000)%></font></td>
		 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(czxftplr/10000)%></font></td>
		 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(yhkftplr/10000)%></font></td>
		 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(yjhkftplr/10000)%></font></td>
		 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(bzjftplr/10000)%></font></td>
		 <td align="center"><%=FormatUtil.toMoney(grdkftplr/10000)%></td>
		 <td align="center"><%=FormatUtil.toMoney(gsdkftplr/10000)%></td>

		 <td align="center"><%=FormatUtil.toMoney(dsftplrhj/10000)%></td>
		 <td align="center"><%=FormatUtil.toMoney(dgftplrhj/10000)%></td>
		 
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zftplr/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(100,2)%></font></td>
		<td align="center"><font style="font-weight: bold">-</font></td>
		</tr>
				</table>
<%} %>
</form>
</body>
</html>
