
<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.FtpQxppResult,com.dhcc.ftp.util.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="commonJs.jsp" />
<title>多资金池-历史价格查看</title>
</head>
<% 
 	PageUtil resultPriceUtil =(PageUtil)request.getAttribute("resultPriceUtil"); 
 	List list = resultPriceUtil.getList(); 
%>
<body>
<form name="form1" method="post">

			<table id="tableList">
			<thead>
	<tr >
		<th width="250">
			机构
		</th>
		<th width="200">
			资金池
		</th>
		<th width="100">
			定价结果(%)
		</th>
		<th width="100">
			定价日期
		</th>
		<th width="150">
			操作
		</th>
	</tr>
	</thead>	
	<% 
	 for (int i = 0; i < list.size(); i++){
		 Object[] o = (Object[]) list.get(i);
		 String poolName = FtpUtil.CastPool_NameByPool_no(o[2].toString(),"");
	 %>
	 <tbody>
	 <tr>
     	<td align="center"><%=CastUtil.trimNull(o[0])%></td>
     	<td align="center"><%=poolName%></td>
     	<td align="center"><%=CommonFunctions.doublecut(Double.valueOf(o[3].toString())*100,3)%></td>
     	<td align="center"><%=CastUtil.trimNull(o[4])%></td>
     	<td align="center">
     	    <a href="javascript:showHistoryTransPrice('<%=o[2] %>','<%=o[5] %>','<%=o[4] %>','<%=o[0] %>','<%=poolName %>')">历史转移价格</a>
<%--     	<input type="button" name="Submit1" value="历史转移价格" onclick="showHistoryTransPrice('<%=o[2] %>','<%=o[5] %>','<%=o[4] %>')" class="button"/>--%>
		</td>
	</tr>
	</tbody>
	<% } %>
</table>
<table border="0" width="80%" class="tb1"
			style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
			align="center">
			<tr>
</td></tr>
 	<tr><td align="right"><%=resultPriceUtil.getPageLine()%></td></tr>
</td>
</tr>
</table>
</form>

</body>

<script type="text/javascript">	
j(function(){
    j('#tableList').flexigrid({
    		height: 260,width:900,title: '多资金池定价结果查询列表'});
});

function showHistoryTransPrice(poolNo,brNo,date,brName,poolName) {

	art.dialog.open('<%=request.getContextPath()%>/UL03_history.action?poolNo='+poolNo+'&currencySelectId=01&brNo='+brNo+'&date='+date+'&random='+<%=Math.random()%>, {
	    title: brName+'-'+poolName+'历史转移价格',
	    width: 800,
	    height:400
	});

}

</script>
</html>
