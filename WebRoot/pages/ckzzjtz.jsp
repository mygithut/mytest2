<%@page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.util.CommonFunctions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String now=String.valueOf(CommonFunctions.GetDBSysDate());
%>

<html>
	<head>
	<style type="text/css">
	.ui-datepicker-calendar { 
		display: none; 
	}
	</style>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonDatePicker.jsp" />
		<jsp:include page="commonExt2.0.2.jsp" /><!-- 需放到prototype.js后面 -->

		<title>存款准备金调整维护</title>
	</head>
	<body>
		<div class="cr_header">
			当前位置：期限匹配->存款准备金调整
		</div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td>
			     <table width="1000"  class="table"  align="center">
					<tr>
						<td class="middle_header" colspan="4"><font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">查询</font>	</td>
					</tr>
					<tr>
					<td width="10%" align="right">日期 :  </td>
					<td>
						<input type="text" id="adjustDate" name="adjustDate"  value="<%=now.substring(0,6)+"01"%>" />
					</td>
					</tr>
			<tr>
		        <td align="center" colspan="4">
		        <input name="query" class="button" type="button" id="query" height="20" onClick="javascript:onSumbit()" value="查&nbsp;&nbsp;询" /> 
		        <input name="back" class="button" type="button" id="back" height="20" onClick="doClear()" value="重&nbsp;&nbsp;置" />
		          </td>
		      </tr>
			</table>
			</td>
		</tr>
			<tr>
				<td>
					<table width="1000" align="center">
						<tr>
							<td>
								<iframe src="" id="iframe" width="100%"	height="450" frameborder="no" marginwidth="0" marginheight="0"		scrolling="no" allowtransparency="yes" align="middle"></iframe>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
	<script type="text/javascript">
	j(function() {
        j("#adjustDate").datepicker(
    	{
    		changeMonth: true, 
    		changeYear: true, 
    		showButtonPanel: true, 
    		dateFormat: 'yymm01',
    		showOn: 'button', 
    		buttonImage: '<%=basePath%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
    		buttonImageOnly: true,
    		onClose: function(dateText, inst) { 
    		var month = j("#ui-datepicker-div .ui-datepicker-month :selected").val(); 
    		var year = j("#ui-datepicker-div .ui-datepicker-year :selected").val(); 
    		j(this).datepicker('setDate', new Date(year, month, 1)); 
    		} 
    	});
    });

    
	function onSumbit() {
		var adjustDate=document.getElementById('adjustDate').value;
		if (adjustDate == '') {
			alert("请选择时间!!"); 
			return;
		}
		window.frames.iframe.location.href = "<%=basePath%>/CKZZJTZ_getList.action?adjustDate="+adjustDate
	}
	</script>
</html>

