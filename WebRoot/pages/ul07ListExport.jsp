<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.FtpQxppResult,com.dhcc.ftp.util.*" errorPage="" %>
<html>
<head>
</head>
<% 
String filename = new String(("期限匹配历史定价-业务列表").getBytes("GBK"),"ISO-8859-1");   
response.addHeader("Content-Disposition", "filename=" + filename + ".xls"); 
System.out.println("开始获取list");
List<FtpQxppResult> list = (List)session.getAttribute("list"); 
%>
<body>
<form name="form1" method="post">

 <table border="1" cellspacing="0" cellpadding="0" align="center">	
   <tr><td align="center" colspan="16"><font style="color:#333; font-size:18px;font-weight:bold">期限匹配历史定价-业务列表</font></td></tr>
	<tr >
       <th>
			序号
		</th>
		<th>
			机构
		</th>
		<th>
			操作员
		</th>
		<th>
			币种
		</th>
		<th>
			业务账号
		</th>
		<th>
			客户名
		</th>
		<th>
			业务类型
		</th>
		<th>
			产品名称
		</th>
		<th>
			开户日期
		</th>
		<th>
			金额
		</th>
		<th>
			余额
		</th>
		<th>
			利率(%)
		</th>
		<th>
			期限（天）
		</th>
		<th>
			到期日期
		</th>
		<th>
			定价结果（%）
		</th>
		<th>
		    定价方法
		</th>
		<th>
			曲线名
		</th>
		<th>
			定价操作员
		</th>
		<th>
			定价系统日期
		</th>
		<th>
			实际定价时间
		</th>
<%--		<th>--%>
<%--			定价结果值--%>
<%--		</th>--%>
<%--		<th>--%>
<%--			定价方法--%>
<%--		</th>--%>
<%--		<th>--%>
<%--			曲线名--%>
<%--		</th>--%>
<%--		<th>--%>
<%--			定价日期--%>
<%--		</th>--%>
	</tr>
	<% 
	 for (int i = 0; i < list.size(); i++){
	 %>
	 <tr>
     	<td align="center"><%=i+1%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getBrNo())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getTelNo())%></td>
     	
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getCurNo()).equals("01") ? "人民币" : ""%></td>
     	
     		<td align="center">&nbsp;<%=CastUtil.trimNull(list.get(i).getAcId())%></td>
     <td align="center"><%=CastUtil.trimNull(list.get(i).getCusName())%></td>
     	<td align="center"><%=CastUtil.trimNull(FtpUtil.getBusinessName(list.get(i).getBusinessNo().toString()))%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getProductName())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getOpnDate())%></td>
     	<td align="center"><%=FormatUtil.toMoney(list.get(i).getBal())%></td>
     	<td align="center"><%=FormatUtil.toMoney(list.get(i).getAmt())%></td>
     	<td align="center"><%=list.get(i).getRate() == null ? "" :CommonFunctions.doublecut(Double.valueOf(list.get(i).getRate())*100,3)%></td>
     	
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getTerm())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getMtrDate())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getFtpPrice())%></td>
     	<td align="center"><%=FtpUtil.getMethodName_byMethodNo(list.get(i).getMethodNo())%></td>

     	<td align="center"><%=FtpUtil.getCurveName_byCurveNo(list.get(i).getCurveNo())%></td>
     	
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getFtpTelNo())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getWrkSysDate())%></td>
     	<td align="center">&nbsp;<%=CastUtil.trimNull(list.get(i).getWrkTime())%></td>
	</tr>
	<% } %>
</table>
		</form>

</body>

</html>
