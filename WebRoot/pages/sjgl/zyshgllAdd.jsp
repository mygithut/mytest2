<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.entity.Ftp1PledgeRepoRate,java.text.*" %>
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
Ftp1PledgeRepoRate ftpRepoRate = (Ftp1PledgeRepoRate)request.getAttribute("ftpRepoRate");
 %>
			<table class="table" width="60%" align="center">
			    <tr>
					<th width="20%" align="right">
						���ڣ�
					</th>
					<td width="20%">
						<input type="text" name="repoDate" id="repoDate" maxlength="10" value="<%=ftpRepoRate == null ? CommonFunctions.GetDBSysDate() :CastUtil.trimNull(ftpRepoRate.getRepoDate())%>" size="15" /> 
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						����&nbsp;
					</th>
					<th width="20%" align="right">
						��Ѻʽ�ع�����(%)
					</th>
			     </tr>
			     <tr>
			         <th>O/N��</th>
			         <td><input id="repoRate0N" name="repoRate" onblur="isFloat(this,'1�������ֵ��')"/></td>
			     </tr>
			     <tr>
			         <th>1W��</th>
			         <td><input id="repoRate1W" name="repoRate" onblur="isFloat(this,'2�������ֵ��')"/></td>
			     </tr>
			     <tr>
			         <th>2W��</th>
			         <td><input id="repoRate2W" name="repoRate" onblur="isFloat(this,'3�������ֵ��')"/></td>
			     </tr>
			     <tr>
			         <th>3W��</th>
			         <td><input id="repoRate3W" name="repoRate" onblur="isFloat(this,'5�������ֵ��')"/></td>
			     </tr>
			     <tr>
			         <th>1M��</th>
			         <td><input id="repoRate1M" name="repoRate" onblur="isFloat(this,'7�������ֵ��')"/></td>
			     </tr>
			     <tr>
			         <th>2M��</th>
			         <td><input id="repoRate2M" name="repoRate" onblur="isFloat(this,'10�������ֵ��')"/></td>
			     </tr>
			     <tr>
			         <th>3M��</th>
			         <td><input id="repoRate3M" name="repoRate" onblur="isFloat(this,'15�������ֵ��')"/></td>
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
    j("#repoDate").datepicker(
	{
		dateFormat: 'yymmdd',
		showOn: 'button', 
		buttonImage: '<%=request.getContextPath()%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
		buttonImageOnly: true,
		now:'<%=ftpRepoRate == null ? CommonFunctions.GetDBSysDate() :CastUtil.trimNull(ftpRepoRate.getRepoDate())%>'
	});
});
function save(FormName) {
    var repoRates = document.getElementsByName("repoRate");
    var rates="";
    for(var i=0;i<repoRates.length;i++){
    	if(repoRates[i].value==""){
    		art.dialog({
          	    title:'��ʾ',
      		    icon: 'succeed',
      		    content: '�벹ȫ��Ѻʽ�ع����ʣ�',
      		    ok: function () {
      		        return;
      		    }
       	 });
        	return;
    	}
    	rates+=repoRates[i].value+",";
    }
   
    var repoDate = document.getElementById("repoDate").value;
	var url = "<%=request.getContextPath()%>/ZYSHGLL_save.action";
        new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {
            repoRate:rates,
            repoDate:repoDate,
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
