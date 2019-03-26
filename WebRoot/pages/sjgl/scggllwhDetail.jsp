<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.entity.FtpPublicRate,java.text.*" %>
<%@page import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.FtpUtil,com.dhcc.ftp.util.*"%>
<html>
<meta http-equiv="expires" content="0" /> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
<meta http-equiv="pragram" content="no-cache" /> 
<head>
<title>市场公共利率维护</title>
<jsp:include page="../commonJs.jsp" />
<jsp:include page="../commonDatePicker.jsp" />
</head>
<body>
<br/>
<form action="<%=request.getContextPath()%>/SCGGLLWH_save.action"  method="post" name="saveForm" id="saveForm">
<%
FtpPublicRate ftpPublicRate = (FtpPublicRate)request.getAttribute("ftpPublicRate");
String todayDate = CommonFunctions.dateModifyD(String.valueOf(CommonFunctions.GetDBTodayDate()),1);//获取数据库当前日期+1
%>
			<table class="table" width="70%" align="center">
			  	<tr>
					<th width="20%" align="right">
						类型：
					</th>
					<td width="30%">
					    
						<input type="text" readonly="readonly" id="type" name="type" value="<%if (ftpPublicRate.getRateNo().substring(0,1).equals("2")){
							out.print("存款");
						}else if (ftpPublicRate.getRateNo().substring(0,1).equals("1")) {
							out.print("贷款");
						}else if(ftpPublicRate.getRateNo().equals("3")){out.print("存款准备金率");
						}else if(ftpPublicRate.getRateNo().equals("4")){out.print("存款准备金利率");
						}%>" />
					</td>
				</tr>
				<%if(!ftpPublicRate.getRateNo().equals("3") && !ftpPublicRate.getRateNo().equals("4")) { %>
				<tr>
					<th width="20%" align="right">
						期限：
					</th>
					<td width="30%">
						<input type="text" readonly="readonly" id="rateNo1" name="rateNo1" value="<%=FtpUtil.getRateTermType(ftpPublicRate.getRateNo())%>" />
					</td>
				</tr>
				<%} %>
				<tr>
					<th width="20%" align="right">
						利率(%)：
					</th>
					<td width="30%">
						<input type="text" id="rate" name="rate" onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=FormatUtil.formatDoubleE(ftpPublicRate.getRate()*100) %>" />
					</td>
				</tr>
				<%if(!ftpPublicRate.getRateNo().equals("3") && !ftpPublicRate.getRateNo().equals("4")) { %>
				<tr>
					<th width="20%" align="right">
						利率浮动比率(%)-对私存款/贷款：
					</th>
					<td width="30%">
						<input type="text" id="floatPercent" onkeyup="value=value.replace(/[^\d\.]/g,'')" name="floatPercent" value="<%=ftpPublicRate.getFloatPercent() == null ? "" :FormatUtil.formatDoubleE(ftpPublicRate.getFloatPercent()*100) %>" />
					</td>
				</tr>
				<%}else{ %>
				<input type="hidden" id="floatPercent" name="floatPercent" value="0" />
				<%} %>
				<%if(ftpPublicRate.getRateNo().substring(0,1).equals("2")) { %>
				<tr>
					<th width="20%" align="right">
						利率浮动比率(%)-对公存款：
					</th>
					<td width="30%">
					    <input type="text" id="floatPercentDg" onkeyup="value=value.replace(/[^\d\.]/g,'')" name="floatPercentDg" value="<%=ftpPublicRate.getFloatPercentDg() == null ? "" :FormatUtil.formatDoubleE(ftpPublicRate.getFloatPercentDg()*100) %>" />
					</td>
				</tr>
				<%}else{ %>
				<input type="hidden" id="floatPercentDg" name="floatPercentDg" value="0" />
				<%} %>
				<tr>
					<th width="20%" align="right">
						日期：
					</th>
					<td width="30%">
						<input type="text" name="adDate" id="adDate" maxlength="10" value="<%=ftpPublicRate.getAdDate() == null ? todayDate : ftpPublicRate.getAdDate()%>" size="10" /> 
<%-- 						<input type="text" name="adDate" id="adDate" maxlength="10" value="<%=todayDate%>" size="10" /> --%> 
<%--		<img style="CURSOR:hand" src="/ftp/pages/images/calendar.gif" width="16" height="16" alt="date" align="absmiddle" onClick="getDate0('adDate')">--%>
					</td>
				</tr>
				
				<tr>
					<td height="41" colspan="4">
						<div align="center">
							<input type="button" name="Submit1" value="保&nbsp;&nbsp;存"
								onClick="save(this.form)" class="button">
							&nbsp;&nbsp;
							<input type="reset" name="Reset" value="重&nbsp;&nbsp;置" class="button">
						</div>
					</td>
				</tr>
			</table>
			<input type="hidden" id="rateNo" name="rateNo" value="<%=ftpPublicRate.getRateNo() %>"/>
			<input type="hidden" id="rateId" name="rateId" value="<%=ftpPublicRate.getRateId() %>"/>
		</form>
</body>
<script type="text/javascript">	
j(function() {
    j("#adDate").datepicker(
	{
		changeMonth: true,
		changeYear: true,
		dateFormat: 'yymmdd',
		showOn: 'button', 
		buttonImage: 'pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
		buttonImageOnly: true
	});//如果是计算机日期，则不需要传递now参数，后台默认使用计算机日期
});
function save(FormName) {
	if (!(isNull(document.getElementById("rate"), "利率"))) {
		return false;			
    }
    $('saveForm').request({   
	       method:"post",
	       parameters:{t:new Date().getTime()},
           onSuccess: function() 
           {
	    	   art.dialog({
              	    title:'成功',
          		    icon: 'succeed',
          		    content: '保存成功！',
          		    ok: function () {
          		    	ok();
          		        return true;
          		    }
           	 });
	           	 }
          }
       );
    }
function ok(){
    window.parent.location.reload();
}
</script>
</html>
