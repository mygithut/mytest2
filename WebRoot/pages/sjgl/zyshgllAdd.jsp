<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.entity.Ftp1PledgeRepoRate,java.text.*" %>
<%@page import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil,com.dhcc.ftp.util.CommonFunctions"%>
<html>
<meta http-equiv="expires" content="0" /> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
<meta http-equiv="pragram" content="no-cache" /> 
<head>
<title>添加质押式回购利率设置</title>
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
						日期：
					</th>
					<td width="20%">
						<input type="text" name="repoDate" id="repoDate" maxlength="10" value="<%=ftpRepoRate == null ? CommonFunctions.GetDBSysDate() :CastUtil.trimNull(ftpRepoRate.getRepoDate())%>" size="15" /> 
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						期限&nbsp;
					</th>
					<th width="20%" align="right">
						质押式回购利率(%)
					</th>
			     </tr>
			     <tr>
			         <th>O/N：</th>
			         <td><input id="repoRate0N" name="repoRate" onblur="isFloat(this,'1年的收益值：')"/></td>
			     </tr>
			     <tr>
			         <th>1W：</th>
			         <td><input id="repoRate1W" name="repoRate" onblur="isFloat(this,'2年的收益值：')"/></td>
			     </tr>
			     <tr>
			         <th>2W：</th>
			         <td><input id="repoRate2W" name="repoRate" onblur="isFloat(this,'3年的收益值：')"/></td>
			     </tr>
			     <tr>
			         <th>3W：</th>
			         <td><input id="repoRate3W" name="repoRate" onblur="isFloat(this,'5年的收益值：')"/></td>
			     </tr>
			     <tr>
			         <th>1M：</th>
			         <td><input id="repoRate1M" name="repoRate" onblur="isFloat(this,'7年的收益值：')"/></td>
			     </tr>
			     <tr>
			         <th>2M：</th>
			         <td><input id="repoRate2M" name="repoRate" onblur="isFloat(this,'10年的收益值：')"/></td>
			     </tr>
			     <tr>
			         <th>3M：</th>
			         <td><input id="repoRate3M" name="repoRate" onblur="isFloat(this,'15年的收益值：')"/></td>
			     </tr>
				<tr>
					<td height="41" colspan="4">
						<div align="center">
							<input type="button" name="Submit1" value="保&nbsp;&nbsp;存"
								onClick="save(this.form)" class="button">
							&nbsp;&nbsp;
							<input type="button" name="Reset" onClick="javaScript:window.location.reload();" value="重&nbsp;&nbsp;置" class="button">
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
          	    title:'提示',
      		    icon: 'succeed',
      		    content: '请补全质押式回购利率！',
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
              	    title:'成功',
          		    icon: 'succeed',
          		    content: '保存成功！',
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
