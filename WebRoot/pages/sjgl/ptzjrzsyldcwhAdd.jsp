<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.entity.Ftp1OfRateSpread,java.text.*" %>
<%@page import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil,com.dhcc.ftp.util.CommonFunctions"%>
<html>
<meta http-equiv="expires" content="0" /> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
<meta http-equiv="pragram" content="no-cache" /> 
<head>
<title>�����Ѻʽ�ع���������</title>
<jsp:include page="../commonJs.jsp" />
<jsp:include page="../commonDatePicker.jsp" />
</head>
<body>
<br/>
<form action=""  method="post">
<%
Ftp1OfRateSpread ftpRateSpread = (Ftp1OfRateSpread)request.getAttribute("ftpRateSpread");
 %>
			<table class="table" width="60%" align="center">
			    <tr>
					<th width="20%" align="right">
						���ڣ�
					</th>
					<td width="20%">
						<input type="text" name="spreadDate" id="spreadDate" maxlength="10" value="<%=ftpRateSpread == null ? CommonFunctions.GetDBSysDate() :CastUtil.trimNull(ftpRateSpread.getSpreadDate())%>" size="15" /> 
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						����&nbsp;
					</th>
					<th width="20%" align="right">
						�����ʵ��(%)
					</th>
			     </tr>
			     <tr>
			         <th>2Y��</th>
			         <td><input id="spreadRate2Y" name="spreadRate" onblur="isFloat(this,'2��������ʵ�')"/></td>
			     </tr>
			     <tr>
			         <th>3Y��</th>
			         <td><input id="spreadRate3Y" name="spreadRate" onblur="isFloat(this,'3��������ʵ�')"/></td>
			     </tr>
			     <tr>
			         <th>5Y��</th>
			         <td><input id="spreadRate5Y" name="spreadRate" onblur="isFloat(this,'5����������ʵ�')"/></td>
			     </tr>
			     <tr>
			         <th>7Y��</th>
			         <td><input id="spreadRate7Y" name="spreadRate" onblur="isFloat(this,'7��������ʵ�')"/></td>
			     </tr>
			     <tr>
			         <th>10Y��</th>
			         <td><input id="spreadRate10Y" name="spreadRate" onblur="isFloat(this,'10��������ʵ�')"/></td>
			     </tr>
			     <tr>
			         <th>15Y��</th>
			         <td><input id="spreadRate15Y" name="spreadRate" onblur="isFloat(this,'15��������ʵ�')"/></td>
			     </tr>
			     <tr>
			         <th>20Y��</th>
			         <td><input id="spreadRate20Y" name="spreadRate" onblur="isFloat(this,'20��������ʵ�')"/></td>
			     <tr>
			     <tr>
			         <th>30Y��</th>
			         <td><input id="spreadRate30Y" name="spreadRate" onblur="isFloat(this,'30��������ʵ�')"/></td>
			     </tr>
				<tr>
					<td height="41" colspan="4">
						<div align="center">
							<input type="button" name="Submit1" value="��&nbsp;&nbsp;��"
								onClick="save(this.form)" class="button">
							&nbsp;&nbsp;
							<input type="button" name="Reset" onClick="javaScript:window.location.reload();" value="��&nbsp;&nbsp;��" class="button">
						</div>
					</td>
				</tr>
			</table>
		</form>
</body>
<script type="text/javascript">	
j(function() {
    j("#spreadDate").datepicker(
	{
		dateFormat: 'yymmdd',
		showOn: 'button', 
		buttonImage: '<%=request.getContextPath()%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
		buttonImageOnly: true,
		now:'<%=ftpRateSpread == null ? CommonFunctions.GetDBSysDate() :CastUtil.trimNull(ftpRateSpread.getSpreadDate())%>'
	});
});
function save(FormName) {
    var spreadRates = document.getElementsByName("spreadRate");
    var rates="";
    for(var i=0;i<spreadRates.length;i++){
    	if(spreadRates[i].value==""){
    		art.dialog({
          	    title:'��ʾ',
      		    icon: 'error',
      		    content: '�벹ȫ��ͨծ�����ծ�����ʵ�',
      		    ok: function () {
      		        return;
      		    }
       	 });
        	return;
    	}
    	rates+=spreadRates[i].value+",";
    }
   
    var spreadDate = document.getElementById("spreadDate").value;
	var url = "<%=request.getContextPath()%>/PTZJRZSYLDCWH_save.action";
        new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {
            spreadRate:rates,
            spreadDate:spreadDate,
            t:new Date().getTime()},
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
