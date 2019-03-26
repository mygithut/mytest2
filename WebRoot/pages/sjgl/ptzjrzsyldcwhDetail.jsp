<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.entity.Ftp1OfRateSpread,java.text.*" %>
<%@page import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil,com.dhcc.ftp.util.CommonFunctions"%>
<html>
<meta http-equiv="expires" content="0" /> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
<meta http-equiv="pragram" content="no-cache" /> 
<head>
<title>修改普通债与金融债收益率点差</title>
<jsp:include page="../commonJs.jsp" />
<jsp:include page="../commonDatePicker.jsp" />
</head>
<body>
<br/>
<form action=""  method="post">
<%
Ftp1OfRateSpread ftpRateSpread = (Ftp1OfRateSpread)request.getAttribute("ftpRateSpread");
 %>
			<table class="table" width="70%" align="center">
				<tr>
					<th width="20%" align="right">
						普通债与金融债收益率点差(%):
					</th>
					<td width="30%">
						<input type="text" id="spreadRate" name="spreadRate" onblur="isFloat(this,'普通债与金融债收益率点差(%)')" value="<%=ftpRateSpread == null ? "" : CommonFunctions.doublecut(ftpRateSpread.getSpreadRate()*100,4) %>" />
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						期限：
					</th>
					<td width="30%">
						<select style="width: 100" id="spreadTerm" name="spreadTerm">
						<option value="">请选择</option>
						<option value="2Y">2Y</option>
						<option value="3Y">3Y</option>
						<option value="5Y">5Y</option>
					    <option value="7Y">7Y</option>
					    <option value="10Y">10Y</option>
						<option value="15Y">15Y</option>
						<option value="20Y">20Y</option>
						<option value="30Y">30Y</option>
					    </select>
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						日期：
					</th>
					<td width="30%">
						<input type="text" name="spreadDate" id="spreadDate" maxlength="10" value="<%=ftpRateSpread == null ? CommonFunctions.GetDBSysDate() :CastUtil.trimNull(ftpRateSpread.getSpreadDate())%>" size="10" /> 
<%--		<img style="CURSOR:hand" src="/ftp/pages/images/calendar.gif" width="16" height="16" alt="date" align="absmiddle" onClick="getDate0('finacialDate')"></td>--%>
					</td>
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
			<input type="hidden" id="spreadId" name="spreadId" value="<%=ftpRateSpread == null ? "" : CastUtil.trimNull(ftpRateSpread.getSpreadId()) %>"  />
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
<%if(ftpRateSpread!=null){%>
for(var i=0;i<$("spreadTerm").length;i++){
	if($("spreadTerm").options[i].value=="<%=ftpRateSpread.getTermType()%>"){
		$("spreadTerm").selectedIndex=i;break;}
}
<%}%>

function save(FormName) {
    if(!(isNull(FormName.spreadRate,"收益点差"))) {
		return false;			
    }
    if(!(isNull(FormName.spreadTerm,"期限"))) {
		return false;			
    }
    if(!(isNull(FormName.spreadDate,"日期"))) {
		return false;			
    }
	    var url = "<%=request.getContextPath()%>/PTZJRZSYLDCWH_save.action";
        new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {spreadId:FormName.spreadId.value,spreadRate:FormName.spreadRate.value,spreadTerm:FormName.spreadTerm.value,spreadDate:FormName.spreadDate.value,t:new Date().getTime()},
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
