<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.entity.FtpFinacialRate,java.text.*" %>
<%@page import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil,com.dhcc.ftp.util.CommonFunctions"%>
<html>
<meta http-equiv="expires" content="0" /> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
<meta http-equiv="pragram" content="no-cache" /> 
<head>
<title>添加/修改政策性金融债收益率</title>
<jsp:include page="../commonJs.jsp" />
<jsp:include page="../commonDatePicker.jsp" />
</head>
<body>
<br/>
<form action=""  method="post">
<%
FtpFinacialRate ftpFinacial = (FtpFinacialRate)request.getAttribute("ftpFinacialRate");
 %>
			<table class="table" width="60%" align="center">
			    <tr>
					<th width="20%" align="right">
						日期：
					</th>
					<td width="20%">
						<input type="text" name="finacialDate" id="finacialDate" maxlength="10" value="<%=ftpFinacial == null ? CommonFunctions.GetDBSysDate() :CastUtil.trimNull(ftpFinacial.getFinacialDate())%>" size="15" /> 
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						期限&nbsp;
					</th>
					<th width="20%" align="right">
						金融债收益率(%)
					</th>
			     </tr>
			     <tr>
			         <th>1年：</th>
			         <td><input id="finacialRate5" name="finacialRate" onblur="isFloat(this,'1年的收益值：')"/></td>
			     </tr>
			     <tr>
			         <th>2年：</th>
			         <td><input id="finacialRate5" name="finacialRate" onblur="isFloat(this,'2年的收益值：')"/></td>
			     </tr>
			     <tr>
			         <th>3年：</th>
			         <td><input id="finacialRate5" name="finacialRate" onblur="isFloat(this,'3年的收益值：')"/></td>
			     </tr>
			     <tr>
			         <th>5年：</th>
			         <td><input id="finacialRate5" name="finacialRate" onblur="isFloat(this,'5年的收益值：')"/></td>
			     </tr>
			     <tr>
			         <th>7年：</th>
			         <td><input id="finacialRate7" name="finacialRate" onblur="isFloat(this,'7年的收益值：')"/></td>
			     </tr>
			     <tr>
			         <th>10年：</th>
			         <td><input id="finacialRate10" name="finacialRate" onblur="isFloat(this,'10年的收益值：')"/></td>
			     <tr>
			         <th>15年：</th>
			         <td><input id="finacialRate15" name="finacialRate" onblur="isFloat(this,'15年的收益值：')"/></td>
			     </tr>
			     <tr>
			         <th>20年：</th>
			         <td><input id="finacialRate20" name="finacialRate" onblur="isFloat(this,'20年的收益值：')"/></td>
			     </tr>
			     <tr>
			         <th>30年：</th>
			         <td><input id="finacialRate30" name="finacialRate" onblur="isFloat(this,'30年的收益值：')"/></td>
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
    j("#finacialDate").datepicker(
	{
		dateFormat: 'yymmdd',
		showOn: 'button', 
		buttonImage: '<%=request.getContextPath()%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
		buttonImageOnly: true,
		now:'<%=ftpFinacial == null ? CommonFunctions.GetDBSysDate() :CastUtil.trimNull(ftpFinacial.getFinacialDate())%>'
	});
});
function save(FormName) {
    var finacialRates = document.getElementsByName("finacialRate");
    var rates="";
    for(var i=0;i<finacialRates.length;i++){
    	if(finacialRates[i].value==""){
    		art.dialog({
          	    title:'提示',
      		    icon: 'succeed',
      		    content: '请输入全部收益率！',
      		    ok: function () {
      		        return;
      		    }
       	 });
        	return;
    	}
    	rates+=finacialRates[i].value+",";
    }
   
    var finacialDate = document.getElementById("finacialDate").value;
	    var url = "<%=request.getContextPath()%>/ZCXJRZWH_save.action";
        new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {
            finacialRate:rates,
            finacialDate:finacialDate,
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
