<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.FtpBusinessInfo,com.dhcc.ftp.util.*" errorPage="" %>
<html>
<head>
</head>
<% 
String filename = new String(("定价-业务列表").getBytes("GBK"),"ISO-8859-1");   
response.addHeader("Content-Disposition", "filename=" + filename + ".xls");  
List<FtpBusinessInfo> list = (List<FtpBusinessInfo>)session.getAttribute("ftpBusinessInfoList"); 
%>
<body>
<form name="form1" method="post">

 <table border="1" cellspacing="0" cellpadding="0" align="center">	
   <tr><td align="center" colspan="17"><font style="color:#333; font-size:18px;font-weight:bold">定价-业务列表</font></td></tr>
	<tr >
       <th>
			序号
		</th>
		<th>
			机构
		</th>
		<th>
			柜员
		</th>
		<th>
			客户经理
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
			期限(天)
		</th>
		<th>
			到期日期
		</th>
		<th>
			是否展期
		</th>
		<th>
			五级分类
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
     	<td align="center">&nbsp;<%=CastUtil.trimNull(list.get(i).getTel())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getKhjl())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getCurNo())%></td>
     	<td align="center">&nbsp;<%=CastUtil.trimNull(list.get(i).getAcId())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getCustomName())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getBusinessName())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getPrdtName())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getOpnDate())%></td>
     	<td align="center"><%=FormatUtil.toMoney(list.get(i).getAmt())%></td>
     	<td align="center"><%=FormatUtil.toMoney(list.get(i).getBal())%></td>
     	<td align="center"><%=list.get(i).getRate() == null ? "" :CommonFunctions.doublecut(Double.valueOf(list.get(i).getRate())*100,3)%></td>
     	<td align="center"><%=list.get(i).getBusinessNo().equals("YW201")?"-":CastUtil.trimNull(list.get(i).getTerm())%></td>
     	<td align="center"><%=list.get(i).getBusinessNo().equals("YW201")?"-":CastUtil.trimNull(list.get(i).getMtrDate())%></td>
     	<td align="center"><%=(!list.get(i).getBusinessNo().equals("YW101"))?"-":(list.get(i).getIsZq().equals("0")?"否":"是")%></td>
        <td align="center"><%=FtpUtil.getFivSys(list.get(i).getFivSts())%></td>
	</tr>
	<% } %>
</table>
		</form>

</body>

</html>
