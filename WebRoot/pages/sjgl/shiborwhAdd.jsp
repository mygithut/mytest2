<%@ page contentType="text/html;charset=GBK"%>
<%@ page
	import="java.sql.*,java.util.*,com.dhcc.ftp.entity.FtpShibor,java.text.*"%>
<%@page
	import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil,com.dhcc.ftp.util.CommonFunctions"%>
<html>
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
	<meta http-equiv="pragram" content="no-cache" />
	<head>
		<title>���/�޸�SHIBOR����</title>
		<jsp:include page="../commonJs.jsp" />
<jsp:include page="../commonDatePicker.jsp" />
	</head>
	<body>
		<br />
		<form action="" method="post">
			<%
				FtpShibor ftpShibor = (FtpShibor) request.getAttribute("ftpshibor");
			    String todayDate = String.valueOf(CommonFunctions.GetDBTodayDate());//��ȡ���ݿ⵱ǰ����
            %>
			<table class="table" width="60%" align="center">
				 <tr>
					<th width="20%" align="right">
						���ڣ�
					</th>
					<td width="20%">
						<input type="text" name="ShiborDate" id="ShiborDate" maxlength="10" readonly  value="<%=todayDate%>" size="15" /> 
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						����&nbsp;
					</th>
					<th width="20%"  align="left">
						Shibor����(%)
					</th>
			     </tr>
			     <tr>
			         <th>O/N��</th>
			         <td><input id="shiborRate0N" name="shiborRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>1W��</th>
			         <td><input id="shiborRate1W" name="shiborRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>2W��</th>
			         <td><input id="shiborRate2W" name="shiborRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>1M��</th>
			         <td><input id="shiborRate1M" name="shiborRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>3M��</th>
			         <td><input id="shiborRate3M" name="shiborRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>6M��</th>
			         <td><input id="shiborRate6M" name="shiborRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>9M��</th>
			         <td><input id="shiborRate9M" name="shiborRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>1Y��</th>
			         <td><input id="shiborRate1Y" name="shiborRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
				<tr>
					<td height="41" colspan="4">
						<div align="center">
							<input type="button" name="Submit1" value="��&nbsp;&nbsp;��"
								onClick="javascript:save(this.form)" class="button">
							&nbsp;&nbsp;
							<input type="button" onclick="javaScript:window.location.reload();" name="Reset" value="��&nbsp;&nbsp;��" class="button">
						</div>
					</td>
				</tr>
			</table>
			<input type="hidden" id="ShiborId" name="ShiborId"
				value="<%=ftpShibor == null ? "" : CastUtil.trimNull(ftpShibor
					.getShiborId())%>" />
		</form>
	</body>
	<script type="text/javascript">	
	j(function() {
        j("#ShiborDate").datepicker(
    	{
			changeMonth: true,
			changeYear: true,
    		dateFormat: 'yymmdd',
    		showOn: 'button', 
    		buttonImage: '<%=request.getContextPath()%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
    		buttonImageOnly: true
    	});
    });


function save(FormName) {
    var shiborRate = document.getElementsByName("shiborRate");
    var rates="";
    for(var i=0;i<shiborRate.length;i++){
    	if(shiborRate[i].value==""){
    		art.dialog({
           	    title:'ʧ��',
       		    icon: 'error',
       		    content: '�벹ȫShibor���ʣ�',
       		    cancelVal: '�ر�',
       		    cancel: true
        	 });
        	return;
    	}
    	rates+=shiborRate[i].value+",";
    }
   
    var ShiborDate = document.getElementById("ShiborDate").value;
	var url = "<%=request.getContextPath()%>/SHIBORWH_save.action";
        new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {
                       Shibor:rates,
                       ShiborDate:ShiborDate,
                       t:new Date().getTime()
                       },
                      
          onSuccess: function() 
           {
	    	   art.dialog({
              	    title:'�ɹ�',
          		    icon: 'succeed',
          		    content: '����ɹ���',
          		    ok: function () {
	    		        close();
          		        return true;
          		    }
           	 });
	           	 }
          }
       );
        
    }
function close(){
    window.parent.location.reload();
}
</script>
</html>
