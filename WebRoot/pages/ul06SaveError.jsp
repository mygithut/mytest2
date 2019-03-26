
<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.FtpBusinessInfo,com.dhcc.ftp.util.*" errorPage="" %>
<html>
<head>
<jsp:include page="commonJs.jsp" />
<title>期限匹配--保存</title>
<style type="text/css">
<!--
.STYLE1 {font-size: medium}
.STYLE2 {font-size: large}
.STYLE3 {font-size: small;color:#2907F0; }
-->
</style>
</head>
<%  

    PageUtil ULFtpError06Util = (PageUtil)session.getAttribute("ULFtpError06Util"); 
    List<FtpBusinessInfo> list = ULFtpError06Util.getList(); 
%>
<body>
<form name="chooseform" method="post">
<%if(list.size() == 0) {%>
没有定价失败的结果！！
<%}else{ %>
 <table width="2100" cellspacing="0" cellpadding="0" align="center" class="table" id="tbColor">	
	<tr >
        <th width="40">
			序号
		</th>
		<th width="80">
			机构
		</th>
		<th width="50">
			柜员
		</th>
		<th width="70">
			客户经理
		</th>
		<th width="50">
			币种
		</th>
		<th width="150">
		           业务账号
		</th>
		<th width="250">
			客户名
		</th>
		<th width="150">
			业务类型
		</th>
		<th width="270">
			产品名称
		</th>
		<th width="70">
			开户日期
		</th>
		<th width="110">
			金额
		</th>
		<th width="110">
			余额
		</th>
		<th width="50">
			利率(%)
		</th>
		<th width="70">
			期限(天)
		</th>
		<th width="70">
			到期日期
		</th>
		<th width="70">
			是否展期
		</th>
		<th width="70">
			五级分类
		</th>
		<th width="90">
			定价结果值(%)
		</th>
		<th width="100">
			定价方法
		</th>
		<th width="150">
			曲线名
		</th>
		<th width="80">
			定价系统日期
		</th>
	</tr>
	<% 
	 for (int i = 0; i < list.size(); i++){
	 %>
	 <tr>
<%--	 	<td align="center"><input type="checkbox" name="checkbox" value="<%=i%>" /></td>--%>
     	<td align="center"><%=i+1%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getBrNo())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getTel())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getKhjl())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getCurNo())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getAcId())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getCustomName())%></td>
     	<td align="center"><%=CastUtil.trimNull(FtpUtil.getBusinessName(list.get(i).getBusinessNo().toString()))%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getPrdtName())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getOpnDate())%></td>
     	<td align="center"><%=FormatUtil.toMoney(list.get(i).getAmt())%></td>
     	<td align="center"><%=FormatUtil.toMoney(list.get(i).getBal())%></td>
     	<td align="center"><%=list.get(i).getRate() == null ? "" :CommonFunctions.doublecut(Double.valueOf(list.get(i).getRate())*100,3)%></td>
     	<td align="center"><%=list.get(i).getBusinessNo().equals("YW201")?"-":CastUtil.trimNull(list.get(i).getTerm())%></td>
     	<td align="center"><%=list.get(i).getBusinessNo().equals("YW201")?"-":CastUtil.trimNull(list.get(i).getMtrDate())%></td>
     	<td align="center"><%=(!list.get(i).getBusinessNo().equals("YW101"))?"-":(list.get(i).getIsZq().equals("0")?"否":"是")%></td>
     	<td align="center"><%=FtpUtil.getFivSys(list.get(i).getFivSts())%></td>
     	<td align="center"><%if(list.get(i).getFtpPrice()==null){
     		    out.print("");
     		}else if (Double.isNaN(list.get(i).getFtpPrice())){
     			out.print(Double.NaN);
     		}else{
     			out.print(CommonFunctions.doublecut(list.get(i).getFtpPrice()*100,3));
     		}%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getMethodName())%></td>
     	<td align="center"><%=FtpUtil.getCurveName_byCurveNo(list.get(i).getCurveNo())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getWrkDate())%></td>
	</tr>
	<% } %>
</table>
<table border="0" width="100%" class="tb1" style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0" align="center">
 	<tr><td align="right"><%=ULFtpError06Util.getPageLine()%></td></tr>
</table>
<%} %>
		</form>

</body>

<script type="text/javascript">	

</script>
</html>
