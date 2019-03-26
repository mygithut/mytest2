<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.entity.Ftp1PledgeRepoRate,java.text.*" %>
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
Ftp1PledgeRepoRate ftpRepoRate = (Ftp1PledgeRepoRate)request.getAttribute("ftpPledgeRepo");
 %>
			<table class="table" width="70%" align="center">
				<tr>
					<th width="20%" align="right">
						质押式回购利率(%):
					</th>
					<td width="30%">
						<input type="text" id="repoRate" name="repoRate" onblur="isFloat(this,'质押式回购利率(%)')" value="<%=ftpRepoRate == null ? "" : CommonFunctions.doublecut(ftpRepoRate.getRepoRate()*100,4) %>" />
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						期限：
					</th>
					<td width="30%">
						<select style="width: 100" id="repoTerm" name="repoTerm">
						<option value="">请选择</option>
						<option value="O/N">O/N</option>
						<option value="1W">1W</option>
						<option value="2W">2W</option>
					    <option value="3W">3W</option>
					    <option value="1M">1M</option>
						<option value="2M">2M</option>
						<option value="3M">3M</option>
					    </select>
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						日期：
					</th>
					<td width="30%">
						<input type="text" name="repoDate" id="repoDate" maxlength="10" value="<%=ftpRepoRate == null ? CommonFunctions.GetDBSysDate() :CastUtil.trimNull(ftpRepoRate.getRepoDate())%>" size="10" /> 
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
			<input type="hidden" id="repoId" name="repoId" value="<%=ftpRepoRate == null ? "" : CastUtil.trimNull(ftpRepoRate.getRepoId()) %>"  />
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
<%if(ftpRepoRate!=null){%>
for(var i=0;i<$("repoTerm").length;i++){
	if($("repoTerm").options[i].value=="<%=ftpRepoRate.getTermType()%>"){
		$("repoTerm").selectedIndex=i;break;}
}
<%}%>

function save(FormName) {
    if(!(isNull(FormName.repoRate,"回购利率"))) {
		return false;			
    }
    if(!(isNull(FormName.repoTerm,"期限"))) {
		return false;			
    }
    if(!(isNull(FormName.repoDate,"日期"))) {
		return false;			
    }
	    var url = "<%=request.getContextPath()%>/ZYSHGLL_save.action";
        new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {repoId:FormName.repoId.value,repoRate:FormName.repoRate.value,repoTerm:FormName.repoTerm.value,repoDate:FormName.repoDate.value,t:new Date().getTime()},
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
