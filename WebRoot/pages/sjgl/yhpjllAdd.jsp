<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.entity.Ftp1BankBillsRate,java.text.*" %>
<%@page import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil,com.dhcc.ftp.util.CommonFunctions"%>
<html>
<meta http-equiv="expires" content="0" /> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
<meta http-equiv="pragram" content="no-cache" /> 
<head>
<title>�������Ʊ������</title>
<jsp:include page="../commonJs.jsp" />
<jsp:include page="../commonDatePicker.jsp" />
</head>
<body>
<br/>
<form action=""  method="post">
<%
Ftp1BankBillsRate ftpBankRate = (Ftp1BankBillsRate)request.getAttribute("ftpBankRate");
 %>
			<table class="table" width="60%" align="center">
			    <tr>
					<th width="20%" align="right">
						���ڣ�
					</th>
					<td width="20%">
						<input type="text" name="billsDate" id="billsDate" maxlength="10" value="<%=ftpBankRate == null ? CommonFunctions.GetDBSysDate() :CastUtil.trimNull(ftpBankRate.getBillsDate())%>" size="15" /> 
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						����&nbsp;
					</th>
					<th width="20%" align="right">
						����Ʊ������(%)
					</th>
			     </tr>
			     <tr>
			         <th>6M��</th>
			         <td><input id="billsRate0N" name="billsRate" onblur="isFloat(this,'6M������Ʊ�����ʣ�')"/></td>
			     </tr>
			     <tr>
			         <th>9M��</th>
			         <td><input id="billsRate1W" name="billsRate" onblur="isFloat(this,'9M������Ʊ�����ʣ�')"/></td>
			     </tr>
			     <tr>
			         <th>1Y��</th>
			         <td><input id="billsRate2W" name="billsRate" onblur="isFloat(this,'1Y������Ʊ�����ʣ�')"/></td>
			     </tr>
			     <tr>
			         <th>2Y��</th>
			         <td><input id="billsRate3W" name="billsRate" onblur="isFloat(this,'2Y������Ʊ�����ʣ�')"/></td>
			     </tr>
			     <tr>
			         <th>3Y��</th>
			         <td><input id="billsRate1M" name="billsRate" onblur="isFloat(this,'3Y������Ʊ�����ʣ�')"/></td>
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
    j("#billsDate").datepicker(
	{
		dateFormat: 'yymmdd',
		showOn: 'button', 
		buttonImage: '<%=request.getContextPath()%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
		buttonImageOnly: true,
		now:'<%=ftpBankRate == null ? CommonFunctions.GetDBSysDate() :CastUtil.trimNull(ftpBankRate.getBillsDate())%>'
	});
});
function save(FormName) {
    var billsRates = document.getElementsByName("billsRate");
    var rates="";
    for(var i=0;i<billsRates.length;i++){
    	if(billsRates[i].value==""){
    		art.dialog({
          	    title:'��ʾ',
      		    icon: 'succeed',
      		    content: '�벹ȫ����Ʊ�����ʣ�',
      		    ok: function () {
      		        return;
      		    }
       	 });
        	return;
    	}
    	rates+=billsRates[i].value+",";
    }
   
    var billsDate = document.getElementById("billsDate").value;
	var url = "<%=request.getContextPath()%>/YHPJLL_save.action";
        new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {
            billsRate:rates,
            billsDate:billsDate,
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
