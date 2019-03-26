<%@ page contentType="text/html;charset=GBK"%>
<%@ page
	import="java.sql.*,java.util.*,java.text.*"%>
<%@page
	import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil,com.dhcc.ftp.util.CommonFunctions"%>
<html>
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
	<meta http-equiv="pragram" content="no-cache" />
	<head>
		<title>添加/修改国债数据</title>
		<jsp:include page="../commonJs.jsp" />
<jsp:include page="../commonDatePicker.jsp" />
	</head>
	<body>
		<br />
		<form action="" method="post">
			<%
			    String todayDate = String.valueOf(CommonFunctions.GetDBTodayDate());//获取数据库当前日期
            %>
			<table class="table" width="60%" align="center">
				 <tr>
					<th width="20%" align="right">
						日期：
					</th>
					<td width="20%">
						<input type="text" name="stockDate" id="stockDate" maxlength="10" readonly  value="<%=todayDate%>" size="15" /> 
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						期限&nbsp;
					</th>
					<th width="20%"  align="left">
						国债收益率(%)
					</th>
			     </tr>
			     <tr>
			         <th>6M：</th>
			         <td><input id="stockYield6M" name="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>9M：</th>
			         <td><input id="stockYield9M" name="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>1Y：</th>
			         <td><input id="stockYield1Y" name="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>2Y：</th>
			         <td><input id="stockYield2Y" name="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>3Y：</th>
			         <td><input id="stockYield3Y" name="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>4Y：</th>
			         <td><input id="stockYield4Y" name="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>5Y：</th>
			         <td><input id="stockYield5Y" name="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>6Y：</th>
			         <td><input id="stockYield6Y" name="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>7Y：</th>
			         <td><input id="stockYield7Y" name="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>8Y：</th>
			         <td><input id="stockYield8Y" name="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>9Y：</th>
			         <td><input id="stockYield9Y" name="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>10Y：</th>
			         <td><input id="stockYield10Y" name="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>15Y：</th>
			         <td><input id="stockYield15Y" name="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>20Y：</th>
			         <td><input id="stockYield20Y" name="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
			     <tr>
			         <th>30Y：</th>
			         <td><input id="stockYield30Y" name="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
			     </tr>
				<tr>
					<td height="41" colspan="4">
						<div align="center">
							<input type="button" name="Submit1" value="保&nbsp;&nbsp;存"
								onClick="javascript:save(this.form)" class="button">
							&nbsp;&nbsp;
							<input type="button" onclick="javaScript:window.location.reload();" name="Reset" value="重&nbsp;&nbsp;置" class="button">
						</div>
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript">	
	j(function() {
        j("#stockDate").datepicker(
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
    var stockYield = document.getElementsByName("stockYield");
    var rates="";
    for(var i=0;i<stockYield.length;i++){
    	if(stockYield[i].value==""){
    		art.dialog({
           	    title:'失败',
       		    icon: 'error',
       		    content: '请补全国债收益率！',
       		    cancelVal: '关闭',
       		    cancel: true
        	 });
        	return;
    	}
    	rates+=stockYield[i].value+",";
    }
   
    var stockDate = document.getElementById("stockDate").value;
	var url = "<%=request.getContextPath()%>/GZWH_save.action";
        new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {
                       stock:rates,
                       stockDate:stockDate,
                       t:new Date().getTime()
                       },
                      
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
