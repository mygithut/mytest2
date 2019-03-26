
<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.FtpBusinessInfo,com.dhcc.ftp.util.*" errorPage="" %>
<html>
<head>
<jsp:include page="commonJs.jsp" />
<title>期限匹配业务模拟定价-成功列表</title>
<style type="text/css">
<!--
.STYLE1 {font-size: medium}
.STYLE2 {font-size: large}
.STYLE3 {font-size: small;color:#2907F0; }
-->
</style>
</head>
<%  
    List<FtpBusinessInfo> list = (List<FtpBusinessInfo>)session.getAttribute("ftped_data_successList"); 
%>
<body>
<form name="chooseform" method="post">
<%if(list.size() == 0) {%>
没有定价成功的结果！！
<%}else{ %>
 <table width="1500" border="0" cellspacing="0" cellpadding="0" align="center" class="table" id="tbColor">
	<tr >
       <th>
			序号
		</th>
		<th>
			机构
		</th>
		<th>
			币种
		</th>
		<th>
			业务类型
		</th>
		<th>
			产品名称
		</th>
		<th>
			金额
		</th>
		<th>
			利率(%)
		</th>
		<th>
			期限(天)
		</th>
		<th>
			开始日期
		</th>
		<th>
			到期日期
		</th>
		<th>
			还款周期(天)
		</th>
		<th>
			折旧周期(天)
		</th>
		<th>
			残值率(%)
		</th>
		<th>
			定价结果值(%)
		</th>
		<th>
			定价方法
		</th>
		<th>
			曲线名
		</th>
		<th>
			<font color="blue">当年经济利润</font>
		</th>
		<th>
			<font color="blue">总经济利润</font>
		</th>
	</tr>
	<% 
	 for (int i = 0; i < list.size(); i++){
		 System.out.println("list.get(i).getMtrDate()="+list.get(i).getMtrDate());
	 %>
	 <tr>
<%--	 	<td align="center"><input type="checkbox" name="checkbox" value="<%=i%>" /></td>--%>
     	<td align="center"><%=i+1%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getBrNo())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getCurName())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getBusinessName())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getPrdtName())%></td>
     	<td align="center"><%=FormatUtil.toMoney(list.get(i).getAmt())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getRate())%></td>
     	<td align="center"><%=list.get(i).getMtrDate().equals("/")?"/" :CastUtil.trimNull(list.get(i).getTerm())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getOpnDate())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getMtrDate())%></td>
     	<td align="center"><%=list.get(i).getHkTerm()%></td>
     	<td align="center"><%=list.get(i).getZjTerm()%></td>
     	<td align="center"><%=list.get(i).getScrapValueRate()%></td>
     	<td align="center"><%if(list.get(i).getFtpPrice()==null){
     		    out.print("");
     		}else if (Double.isNaN(list.get(i).getFtpPrice())){
     			out.print(Double.NaN);
     		}else{
     			out.print(CommonFunctions.doublecut(list.get(i).getFtpPrice()*100,3));
     		}%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getMethodName())%></td>
     	<td align="center"><%=FtpUtil.getCurveName_byCurveNo(list.get(i).getCurveNo())%></td>
     	<td align="center"><font color="blue"><%=FormatUtil.toMoney(list.get(i).getCurYearProfit())%></font></td>
     	<td align="center"><font color="blue"><%
     	    if(list.get(i).getMtrDate().equals("/")) {
 		        out.print("/");
     	    }else{
     	    	out.print(FormatUtil.toMoney(list.get(i).getTotalProfit()));
     	    }%></font></td>
	</tr>
	<% } %>
</table>
<%} %>
		</form>

</body>

<script type="text/javascript">	

</script>
</html>
