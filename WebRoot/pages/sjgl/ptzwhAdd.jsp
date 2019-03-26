<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.entity.Ftp1CommonYield,java.text.*" %>
<%@page import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil,com.dhcc.ftp.util.CommonFunctions"%>
<html>
<meta http-equiv="expires" content="0" /> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
<meta http-equiv="pragram" content="no-cache" /> 
<head>
<title>添加本行普通债收益率</title>
<jsp:include page="../commonJs.jsp" />
<jsp:include page="../commonDatePicker.jsp" />
</head>
<body>
<br/>
<form action=""  method="post">
<%
String todayDate = String.valueOf(CommonFunctions.GetDBTodayDate());//获取数据库当前日期
 %>
			<table class="table" width="60%" align="center">
			    <tr>
					<th width="20%" align="right">
						日期：
					</th>
					<td width="20%">
						<input type="text" name="commonDate" id="commonDate"  readonly  maxlength="10" value="<%=todayDate%>" size="15" /> 
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						期限&nbsp;
					</th>
					<th width="20%" align="right">
						收益率(%)
					</th>
			     </tr>
			     <tr>
			         <th>6M：</th>
			         <td><input id="commonRate6M" name="commonRate" onkeyup="value=value.replace(/[^\d\.]/g,'')" /></td>
			     </tr>
			     <tr>
			         <th>9M：</th>
			         <td><input id="commonRate9M" name="commonRate" onkeyup="value=value.replace(/[^\d\.]/g,'')" /></td>
			     </tr>
			     <tr>
			         <th>1Y：</th>
			         <td><input id="commonRate1Y" name="commonRate" onkeyup="value=value.replace(/[^\d\.]/g,'')" /></td>
			     </tr>
			     <tr>
			         <th>2Y：</th>
			         <td><input id="commonRate2Y" name="commonRate" onkeyup="value=value.replace(/[^\d\.]/g,'')" /></td>
			     </tr>
			     <tr>
			         <th>3Y：</th>
			         <td><input id="commonRate3Y" name="commonRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>4Y：</th>
			         <td><input id="commonRate4Y" name="commonRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>5Y：</th>
			         <td><input id="commonRate5Y" name="commonRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>6Y：</th>
			         <td><input id="commonRate6Y" name="commonRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>7Y：</th>
			         <td><input id="commonRate7Y" name="commonRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>8Y：</th>
			         <td><input id="commonRate8Y" name="commonRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>9Y：</th>
			         <td><input id="commonRate9Y" name="commonRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>10Y：</th>
			         <td><input id="commonRate10Y" name="commonRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>15Y：</th>
			         <td><input id="commonRate15Y" name="commonRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>20Y：</th>
			         <td><input id="commonRate20Y" name="commonRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>30Y：</th>
			         <td><input id="commonRate30Y" name="commonRate" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
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
    j("#commonDate").datepicker(
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
    var commonRates = document.getElementsByName("commonRate");
    var rates="";
    for(var i=0;i<commonRates.length;i++){
    	if(commonRates[i].value==""){
    		art.dialog({
          	    title:'提示',
      		    icon: 'error',
      		    content: '请补全收益率！',
      		    ok: function () {
      		        return;
      		    }
       	 });
        	return;
    	}
    	rates+=commonRates[i].value+",";
    }
   
    var commonDate = document.getElementById("commonDate").value;
	var url = "<%=request.getContextPath()%>/PTZWH_save.action";
        new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {
            commonRate:rates,
            commonDate:commonDate,
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
