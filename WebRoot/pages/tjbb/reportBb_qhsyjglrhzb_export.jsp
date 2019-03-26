<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>

<head>
<title>全行所有机构FTP利润汇总表</title>
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
/* String filename = new String(("全行所有机构FTP利润汇总表").getBytes("GBK"),"ISO-8859-1");  */  
List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("qhsyjglrhzbReportList");
String minDate = (String)session.getAttribute("qhsyjglrhzbMinDate");
String maxDate = (String)session.getAttribute("qhsyjglrhzbMaxDate");
String brName = (String)session.getAttribute("qhsyjglrhzbBrName");
String exportName=(String)session.getAttribute("exportName");
String filename = new String((exportName).getBytes("GBK"),"ISO-8859-1");
response.addHeader("Content-Disposition", "filename=" + filename + ".xls");
int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数
%>
<br/>
<%if(ylfxBbReportList == null) { %>
   <p>数据统计错误，请联系管理员！！</p>
<%}else{ %>
<div align="center"><font style="font-size:20px;font-weight:bold;">全行所有机构FTP利润汇总表</font></div>
<div align="center">机构：<%= brName%>
&nbsp;&nbsp;&nbsp;&nbsp;报表时段：<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
&nbsp;&nbsp;&nbsp;&nbsp;单位：万元，%(年利率)</div>
<%-- <%double zcrjyehj=0,zcftplrhj=0,zclchj=0,fzrjyehj=0,fzftplrhj=0,fzlchj=0,ftplrhjsum=0; %> --%>
<%double zcrjyehj=0,zcftplrhj=0,zclchj=0,fzrjyehj=0,fzftplrhj=0,fzlchj=0,ftplrhjsum=0,zcbalhj=0,fzbalhj=0,
		 grhqbal=0,grdqbal=0,dwhqbal=0,dwdqbal=0,czxbal=0,yhkbal=0,yjhkbal=0,bzjbal=0,
		 grhqrjye=0,grdqrjye=0,dwhqrjye=0,dwdqrjye=0,czxrjye=0,yhkrjye=0,yjhkrjye=0,bzjrjye=0
	; %>
<table border="1">
     <tr >
      <th align="center" width="400" >机构名称</th>
      <th align="center" width="120" ><font style="font-weight: bold">资产余额</font></th>
      <th align="center" width="120" ><font style="font-weight: bold">资产日均余额</font></th>
      <th align="center" width="120" >资产利差(%)</th>
      <th align="center" width="100" >资产FTP利润</th>
      <th align="center" width="100" >负债余额</th>
      <th align="center" width="120" ><font style="font-weight: bold">负债日均余额</font></th>
      <th align="center" width="100" >负债利差(%)</th>
      <th align="center" width="100" >负债FTP利润</th>
      <th align="center" width="120" ><font style="font-weight: bold">FTP利润合计</font></th>
       </tr>
<%--      <tr>
      <th align="center" width="250" >机构</th>
      <th align="right" width="125" ><font  style="text-align: center ; font-weight: bold;width: 100% ;display: inline-block;">资产余额</font></th>
      <th align="right" width="125" ><font  style="text-align: center ; font-weight: bold;width: 100% ;display: inline-block;">资产日均余额</font></th>
      <th align="right" width="105"><font style="text-align: center ; width: 100% ;display: inline-block; ">资产利差(%)</font></th>
      <th align="right" width="105" ><font style="text-align: center ; width: 100% ;display: inline-block; ">资产FTP利润</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">余额</font></th>
         <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">个人活期余额</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">个人定期余额</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">单位活期余额</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">单位定期余额</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">财政性存款余额</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">银行卡存款余额</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">应解汇款余额</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">保证金存款余额</font></th>

		<th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">日均余额</font></th>
		 <th align="right" width="135" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">个人活期日均余额</font></th>
		 <th align="right" width="135" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">个人定期日均余额</font></th>
		 <th align="right" width="135" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">单位活期日均余额</font></th>
		 <th align="right" width="135" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">单位定期日均余额</font></th>
		 <th align="right" width="150" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">财政性存款日均余额</font></th>
		 <th align="right" width="150" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">银行卡存款日均余额</font></th>
		 <th align="right" width="135" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">应解汇款日均余额</font></th>
		 <th align="right" width="150" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">保证金存款日均余额</font></th>
      <th align="right" width="100" ><font style="text-align: center ; width: 100% ;display: inline-block; ">负债利差(%)</font></th>
      <th align="right" width="100" ><font style="text-align: center ; width: 100% ;display: inline-block; ">负债FTP利润</font></th>
      <th align="right" width="100" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">FTP利润合计</font></th>
 
     </tr> --%> 
     <% 
         for (YlfxBbReport entity:ylfxBbReportList){
        	 zcrjyehj += entity.getZcrjye();
        	 zcbalhj += entity.getZcbal();//资产余额
 			 zcftplrhj += entity.getZcftplr();
 			 zclchj += entity.getZclc();
 			 fzrjyehj += entity.getFzrjye();
			 fzftplrhj += entity.getFzftplr();
			 fzlchj += entity.getFzlc();
			 ftplrhjsum += entity.getFtplrhj();
			 fzbalhj += entity.getFzbal();//负债余额
			 grhqbal += entity.getGrhqbal();//个人活期余额
			 grdqbal += entity.getGrdqbal();//负债余额
			 dwhqbal += entity.getDwhqbal();//负债余额
			 dwdqbal += entity.getDwdqbal();//负债余额
			 czxbal += entity.getCzxbal();//负债余额
			 yhkbal += entity.getYhkbal();//负债余额
			 yjhkbal += entity.getYjhkbal();//负债余额
			 bzjbal += entity.getBzjbal();//负债余额

			 grhqrjye += entity.getGrhqrjye();//个人活期日均余额
			 grdqrjye += entity.getGrdqrjye();//负债日均余额
			 dwhqrjye += entity.getDwhqrjye();//负债日均余额
			 dwdqrjye += entity.getDwdqrjye();//负债日均余额
			 czxrjye += entity.getCzxrjye();//负债日均余额
			 yhkrjye += entity.getYhkrjye();//负债日均余额
			 yjhkrjye += entity.getYjhkrjye();//负债日均余额
			 bzjrjye += entity.getBzjrjye();//负债日均余额

  %> 
        <tr>
     	<td align="center"><%=entity.getBrName()%><%=entity.getBrNo().equals("")?"":"["+entity.getBrNo()+"]"%></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(entity.getZcbal()/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(entity.getZcrjye()/10000)%></font></td>
		<td align="right"><%=CommonFunctions.doublecut(entity.getZclc()*100,3)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getZcftplr()/10000)%></td>
		
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(entity.getFzbal()/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(entity.getFzrjye()/10000)%></font></td>
		<td align="right"><%=CommonFunctions.doublecut(entity.getFzlc()*100,3)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getFzftplr()/10000)%></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(entity.getFtplrhj()/10000)%></font></td>
		</tr> 
<%-- 		<tr>
     	<td align="center"><%=entity.getBrName()%><%=entity.getBrNo().equals("")?"":"["+entity.getBrNo()+"]"%></td>
		<td align="right"><font ><%=FormatUtil.toMoney(entity.getZcbal()/10000)%></font></td>
		<td align="right"><font ><%=FormatUtil.toMoney(entity.getZcrjye()/10000)%></font></td>
		<td align="right"><%=CommonFunctions.doublecut(entity.getZclc()*100,3)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getZcftplr()/10000)%></td>

		<td ><font ><%=FormatUtil.toMoney(entity.getFzbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getGrhqbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getGrdqbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getDwhqbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getDwdqbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getCzxbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getYhkbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getYjhkbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getBzjbal()/10000)%></font></td>
		<td align="right"><font ><%=FormatUtil.toMoney(entity.getFzrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getGrhqrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getGrdqrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getDwhqrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getDwdqrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getCzxrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getYhkrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getYjhkrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getBzjrjye()/10000)%></font></td>
		<td align="right"><%=CommonFunctions.doublecut(entity.getFzlc()*100,3)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getFzftplr()/10000)%></td>
		<td align="right"><font ><%=FormatUtil.toMoney(entity.getFtplrhj()/10000)%></font></td>
		</tr> --%>
     <% }%>
<%--      <tr>
  	 <td align="center"><font style="font-weight: bold">合计</font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcrjyehj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(zcrjyehj==0?0.00:zcftplrhj/zcrjyehj*360/days*100,3)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcftplrhj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(fzrjyehj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(fzrjyehj==0?0.00:fzftplrhj/fzrjyehj*360/days*100,3)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(fzftplrhj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(ftplrhjsum/10000)%></font></td>
		</tr> --%>
		     <tr>
  	 <td align="center"><font style="font-weight: bold">合计</font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcbalhj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcrjyehj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(zcrjyehj==0?0.00:zcftplrhj/zcrjyehj*360/days*100,3)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcftplrhj/10000)%></font></td>

		 <td ><font ><%=FormatUtil.toMoney(fzbalhj/10000)%></font></td>

		 <%-- <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(grhqbal/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(grdqbal/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(dwhqbal/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(dwdqbal/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(czxbal/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(yhkbal/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(yjhkbal/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(bzjbal/10000)%></font></td>
 --%>
		 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(fzrjyehj/10000)%></font></td>

		 <%-- <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(grhqrjye/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(grdqrjye/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(dwhqrjye/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(dwdqrjye/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(czxrjye/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(yhkrjye/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(yjhkrjye/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(bzjrjye/10000)%></font></td>
 --%>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(fzrjyehj==0?0.00:fzftplrhj/fzrjyehj*360/days*100,3)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(fzftplrhj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(ftplrhjsum/10000)%></font></td>
		</tr>
</table>
<%} %>
</form>
</body>

</html>
