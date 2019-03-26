<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.*"%>
<html>
	<head>
		<title>期限匹配-产品定价公布栏</title>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/css/inpage.css"
			type="text/css">
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonDatePicker.jsp" />
		</script>
	</head>
	<body>
		<%
String currentDate = DateUtil.getCurrentDay();
String beforeDate = CommonFunctions.dateModifyD(currentDate, -10);
%>
		<div class="cr_header">
			当前位置：期限匹配->产品定价公布栏
		</div>
		<form name="thisform" action="" method="post">
			<table width="80%" border="0" align="center">
				<tr>
					<td>
						<table width="900" align="left" class="table">
							<tr>
								<td class="middle_header" colspan="4">
									<font
										style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">查询</font>
								</td>
							</tr>
							<tr>
								<td width="35%" align="right">
									查询时间段：
								</td>
								<td width="15%">
									<input type="text" name="startDate" id="startDate"
										value="<%=beforeDate %>" maxlength="8" size="8" />
								</td>
								<td width="10%" align="right">
									到：
								</td>
								<td width="40%">
									<input type="text" name="endDate" id="endDate"
										value="<%=currentDate %>" maxlength="8" size="8" />
								</td>
							</tr>
							<tr>
								<td align="center" colspan="4">
									<input name="query" class="button" type="button" id="query"
										height="20" onClick="onclick_query()" value="查&nbsp;&nbsp;询" />
									<input name="back" class="button" type="button" id="back"
										height="20" onClick="onclick_back()" value="重&nbsp;&nbsp;置" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="left">
						<iframe src="cpdjfbl_list.action?startDate=<%= beforeDate%>&endDate=<%= currentDate%>" id="downframe" name="downframe" width="900" height="350"
							frameborder="no" border="0" marginwidth="0" marginheight="0"
							scrolling="no" allowtransparency="yes" align="middle"></iframe>
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript" language="javascript">
	jQuery(function(){// dom元素加载完毕
		jQuery(".table tr:even").addClass("tr-bg1");
		jQuery(".table tr:odd").addClass("tr-bg2");
	});
/*起始日期-结束日期，日期面板生产js*/
j(function() {
    var startDate = '<%=beforeDate%>';
    var endDate = '<%=currentDate%>';
        j("#startDate").datepicker(
			{
				changeMonth: true,
				changeYear: true,
				dateFormat: 'yymmdd',
				showOn: 'button', 
				maxDate: new Date(endDate.substring(0,4),parseFloat(endDate.substring(4,6))-1,endDate.substring(6,8)),
				buttonImage: 'pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
				buttonImageOnly: true,
			    onSelect: function(dateText, inst) {
			    dateText = dateText.substring(0,4)+'-'+dateText.substring(4,6)+'-'+dateText.substring(6,8);
			    j('#wrkTime2').datepicker('option', 'minDate',new Date(dateText.replace('-',',')));}
		});

		j("#endDate").datepicker(
			{
				changeMonth: true,
				changeYear: true,
				dateFormat: 'yymmdd',
				showOn: 'button', 
				buttonImage: 'pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
				buttonImageOnly: true,
				minDate: new Date(startDate.substring(0,4),parseFloat(startDate.substring(4,6))-1,startDate.substring(6,8)),
			    onSelect: function(dateText, inst) {
				dateText = dateText.substring(0,4)+'-'+dateText.substring(4,6)+'-'+dateText.substring(6,8);
			    j('#wrkTime1').datepicker('option', 'maxDate', new Date(dateText.replace('-',',')));}
		});
	});
function onclick_query(){
	   var startDate=document.getElementById("startDate").value;
	   var endDate=document.getElementById("endDate").value;
	   window.frames.downframe.location.href ='<%=request.getContextPath()%>/cpdjfbl_list.action?startDate='+startDate+'&endDate='+endDate;
}
function onclick_back(){
	   window.location.reload();
}
</script>
</html>
