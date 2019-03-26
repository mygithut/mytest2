<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.entity.Ftp1BankBillsRate,java.text.*" %>
<%@page import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil,com.dhcc.ftp.util.CommonFunctions"%>
<html>
<meta http-equiv="expires" content="0" /> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
<meta http-equiv="pragram" content="no-cache" /> 
<head>
<title>修改央行票据利率</title>
<jsp:include page="../commonJs.jsp" />
<jsp:include page="../commonDatePicker.jsp" />
</head>
<body>
<br/>
<form action=""  method="post">
<%
Ftp1BankBillsRate ftpBankRate = (Ftp1BankBillsRate)request.getAttribute("ftpBankRate");
 %>
			<table class="table" width="70%" align="center">
				<tr>
					<th width="20%" align="right">
						央行票据利率(%):
					</th>
					<td width="30%">
						<input type="text" id="billsRate" name="billsRate" onblur="isFloat(this,'央行票据利率(%)')" value="<%=ftpBankRate == null ? "" : CommonFunctions.doublecut(ftpBankRate.getBillsRate()*100,4) %>" />
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						期限：
					</th>
					<td width="30%">
						<select style="width: 100" id="billsTerm" name="billsTerm">
						<option value="">请选择</option>
						<option value="6M">6M</option>
						<option value="9M">9M</option>
						<option value="1Y">1Y</option>
					    <option value="2Y">2Y</option>
					    <option value="3Y">3Y</option>
					    </select>
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						日期：
					</th>
					<td width="30%">
						<input type="text" name="billsDate" id="billsDate" maxlength="10" value="<%=ftpBankRate == null ? CommonFunctions.GetDBSysDate() :CastUtil.trimNull(ftpBankRate.getBillsDate())%>" size="10" /> 
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
			<input type="hidden" id="billsId" name="billsId" value="<%=ftpBankRate == null ? "" : CastUtil.trimNull(ftpBankRate.getBillsId()) %>"  />
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
		now:'<%=ftpBankRate == null ? CommonFunctions.GetDBSysDate() :CastUtil.trimNull(ftpBankRate.getBillsDate())%>'
	});
});
<%if(ftpBankRate!=null){%>
for(var i=0;i<$("billsTerm").length;i++){
	if($("billsTerm").options[i].value=="<%=ftpBankRate.getTermType()%>"){
		$("billsTerm").selectedIndex=i;break;}
}
<%}%>

function save(FormName) {
    if(!(isNull(FormName.billsRate,"票据利率"))) {
		return false;			
    }
    if(!(isNull(FormName.billsTerm,"期限"))) {
		return false;			
    }
    if(!(isNull(FormName.billsDate,"日期"))) {
		return false;			
    }
	    var url = "<%=request.getContextPath()%>/YHPJLL_save.action";
        new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {billsId:FormName.billsId.value,billsRate:FormName.billsRate.value,billsTerm:FormName.billsTerm.value,billsDate:FormName.billsDate.value,t:new Date().getTime()},
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
