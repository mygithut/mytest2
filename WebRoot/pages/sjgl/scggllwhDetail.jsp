<%@ page contentType="text/html;charset=gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.entity.FtpPublicRate,java.text.*" %>
<%@page import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.FtpUtil,com.dhcc.ftp.util.*"%>
<html>
<meta http-equiv="expires" content="0" /> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
<meta http-equiv="pragram" content="no-cache" /> 
<head>
<title>�г���������ά��</title>
<jsp:include page="../commonJs.jsp" />
<jsp:include page="../commonDatePicker.jsp" />
</head>
<body>
<br/>
<form action="<%=request.getContextPath()%>/SCGGLLWH_save.action"  method="post" name="saveForm" id="saveForm">
<%
FtpPublicRate ftpPublicRate = (FtpPublicRate)request.getAttribute("ftpPublicRate");
String todayDate = CommonFunctions.dateModifyD(String.valueOf(CommonFunctions.GetDBTodayDate()),1);//��ȡ���ݿ⵱ǰ����+1
%>
			<table class="table" width="70%" align="center">
			  	<tr>
					<th width="20%" align="right">
						���ͣ�
					</th>
					<td width="30%">
					    
						<input type="text" readonly="readonly" id="type" name="type" value="<%if (ftpPublicRate.getRateNo().substring(0,1).equals("2")){
							out.print("���");
						}else if (ftpPublicRate.getRateNo().substring(0,1).equals("1")) {
							out.print("����");
						}else if(ftpPublicRate.getRateNo().equals("3")){out.print("���׼������");
						}else if(ftpPublicRate.getRateNo().equals("4")){out.print("���׼��������");
						}%>" />
					</td>
				</tr>
				<%if(!ftpPublicRate.getRateNo().equals("3") && !ftpPublicRate.getRateNo().equals("4")) { %>
				<tr>
					<th width="20%" align="right">
						���ޣ�
					</th>
					<td width="30%">
						<input type="text" readonly="readonly" id="rateNo1" name="rateNo1" value="<%=FtpUtil.getRateTermType(ftpPublicRate.getRateNo())%>" />
					</td>
				</tr>
				<%} %>
				<tr>
					<th width="20%" align="right">
						����(%)��
					</th>
					<td width="30%">
						<input type="text" id="rate" name="rate" onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=FormatUtil.formatDoubleE(ftpPublicRate.getRate()*100) %>" />
					</td>
				</tr>
				<%if(!ftpPublicRate.getRateNo().equals("3") && !ftpPublicRate.getRateNo().equals("4")) { %>
				<tr>
					<th width="20%" align="right">
						���ʸ�������(%)-��˽���/���
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
						���ʸ�������(%)-�Թ���
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
						���ڣ�
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
							<input type="button" name="Submit1" value="��&nbsp;&nbsp;��"
								onClick="save(this.form)" class="button">
							&nbsp;&nbsp;
							<input type="reset" name="Reset" value="��&nbsp;&nbsp;��" class="button">
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
	});//����Ǽ�������ڣ�����Ҫ����now��������̨Ĭ��ʹ�ü��������
});
function save(FormName) {
	if (!(isNull(document.getElementById("rate"), "����"))) {
		return false;			
    }
    $('saveForm').request({   
	       method:"post",
	       parameters:{t:new Date().getTime()},
           onSuccess: function() 
           {
	    	   art.dialog({
              	    title:'�ɹ�',
          		    icon: 'succeed',
          		    content: '����ɹ���',
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
