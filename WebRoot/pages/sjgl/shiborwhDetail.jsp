<%@ page contentType="text/html;charset=GBK"%>
<%@ page
	import="java.sql.*,java.util.*,com.dhcc.ftp.entity.FtpShibor,java.text.*"%>
<%@page
	import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil,com.dhcc.ftp.util.CommonFunctions"%>
<html>
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
	<meta http-equiv="pragram" content="no-cache" />
	<head>
		<title>添加/修改SHIBOR数据</title>
		<jsp:include page="../commonJs.jsp" />
<jsp:include page="../commonDatePicker.jsp" />
	</head>
	<body>
		<br />
		<form action="" method="post">
			<%
				FtpShibor ftpShibor = (FtpShibor) request.getAttribute("ftpshibor");
			    String todayDate = CommonFunctions.dateModifyD(String.valueOf(CommonFunctions.GetDBTodayDate()),1);//获取数据库当前日期+1
            %>
			<table class="table" width="70%" align="center">
				<tr>
					<th width="20%" align="right">
						利率(%)：
					</th>
					<td width="30%">
						<input type="text" id="Shibor" name="Shibor"  onkeyup="value=value.replace(/[^\d\.]/g,'')"
							value="<%=ftpShibor == null ? "" : CommonFunctions.doublecut(
					ftpShibor.getShiborRate() * 100, 4)%>" />
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						期限：
					</th>
					<td width="30%">
						<select style="width: 100" disabled id="ShiborTerm" name="ShiborTerm">
							<option value="">
								请选择
							</option>
							<option value="O/N">
								O/N
							</option>
							<option value="1W">
								1W
							</option>
							<option value="2W">
								2W
							</option>
							<option value="1M">
								1M
							</option>
							<option value="3M">
								3M
							</option>
							<option value="6M">
								6M
							</option>
							<option value="9M">
								9M
							</option>
							<option value="1Y">
								1Y
							</option>
							
						</select>
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						日期：
					</th>
					<td width="30%">
						<input type="text" name="ShiborDate" id="ShiborDate"
							maxlength="10"  disabled
							value="<%=ftpShibor == null ? todayDate : CastUtil.trimNull(ftpShibor.getShiborDate())%>"
					
							size="10" />
<%--						<img style="CURSOR: hand" src="/ftp/pages/images/calendar.gif"--%>
<%--							width="16" height="16" alt="date" align="absmiddle"--%>
<%--							onClick="getDate0('ShiborDate')">--%>
					</td>
					</td>
				</tr>

				<tr>
					<td height="41" colspan="4">
						<div align="center">
							<input type="button" name="Submit1" value="保&nbsp;&nbsp;存"
								onClick="save(this.form)" class="button">
							&nbsp;&nbsp;
							<input type="button" onclick="javaScript:window.location.reload();" name="Reset" value="重&nbsp;&nbsp;置" class="button">
						</div>
					</td>
				</tr>
			</table>
			<input type="hidden" id="ShiborId" name="ShiborId"
				value="<%=ftpShibor == null ? "" : CastUtil.trimNull(ftpShibor
					.getShiborId())%>" />
		</form>
	</body>
	<script type="text/javascript">	
<%--	j(function() {--%>
<%--        j("#ShiborDate").datepicker(--%>
<%--    	{--%>
<%--			changeMonth: true,--%>
<%--			changeYear: true,--%>
<%--    		dateFormat: 'yymmdd',--%>
<%--    		showOn: 'button', --%>
<%--    		buttonImage: '<%=request.getContextPath()%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',--%>
<%--    		buttonImageOnly: true--%>
<%--    	});--%>
<%--    });--%>
<%if (ftpShibor != null) {%>
for(var i=0;i<$("ShiborTerm").length;i++){
	if($("ShiborTerm").options[i].value=="<%=ftpShibor.getShiborTerm()%>"){
		$("ShiborTerm").selectedIndex=i;break;}
}
<%}%>

function save(FormName) {
	 
    if(!(isNull(FormName.Shibor,"SHIBOR数据"))) {
		return false;			
    }
    if(!(isNull(FormName.ShiborTerm,"期限"))) {
		return false;			
    }
    if(!(isNull(FormName.ShiborDate,"日期"))) {
		return false;			
    }
	    var url = "<%=request.getContextPath()%>/SHIBORWH_save.action";
	   
        new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {ShiborId:FormName.ShiborId.value,Shibor:FormName.Shibor.value,ShiborTerm:FormName.ShiborTerm.value,ShiborDate:FormName.ShiborDate.value,t:new Date().getTime()},
          onSuccess: function() 
           {
	    	   art.dialog({
              	    title:'成功',
          		    icon: 'succeed',
          		    content: '保存成功！',
          		    ok: function () {
          		    	ok();
          		        return true;
          		    }
           	 });
	           	 }
          }
       );
    }

function ok(){
    window.parent.location.reload();
}
</script>
</html>
