<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.entity.FtpStockYield,java.text.*" %>
<%@page import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil,com.dhcc.ftp.util.CommonFunctions"%>
<html>
<meta http-equiv="expires" content="0" /> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
<meta http-equiv="pragram" content="no-cache" /> 
<head>
<title>���/�޸Ĺ�ծ������</title>
<jsp:include page="../commonJs.jsp" />
<jsp:include page="../commonDatePicker.jsp" />
</head>
<body>
<br/>
<form action=""  method="post">
<%
FtpStockYield ftpStock = (FtpStockYield)request.getAttribute("ftpStock");
String todayDate = CommonFunctions.dateModifyD(String.valueOf(CommonFunctions.GetDBTodayDate()),1);//��ȡ���ݿ⵱ǰ����+1
 %>
			<table class="table" width="70%" align="center">
				<tr>
					<th width="20%" align="right">
						��ծ������(%)��
					</th>
					<td width="30%">
						<input type="text" id="stockYield" onkeyup="value=value.replace(/[^\d\.]/g,'')" name="stockYield"  value="<%=ftpStock == null ? "" : CommonFunctions.doublecut(ftpStock.getStockYield()*100,4) %>" />
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						���ޣ�
					</th>
					<td width="30%">
						<select style="width: 100" id="stockTerm" name="stockTerm" disabled>
						<option value="">��ѡ��</option>
						<option value="6M">6M</option>
						<option value="9M">9M</option>
						<option value="1Y">1Y</option>
						<option value="2Y">2Y</option>
						<option value="3Y">3Y</option>
						<option value="4Y">4Y</option>
					    <option value="5Y">5Y</option>
					    <option value="6Y">6Y</option>
					    <option value="7Y">7Y</option>
					    <option value="8Y">8Y</option>
					    <option value="9Y">9Y</option>
						<option value="10Y">10Y</option>
						<option value="15Y">15Y</option>
					    <option value="20Y">20Y</option>
					    <option value="30Y">30Y</option>
					    </select>
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						���ڣ�
					</th>
					<td width="30%">
						<input type="text" name="stockDate" disabled id="stockDate" maxlength="10" value="<%=ftpStock == null ? todayDate :CastUtil.trimNull(ftpStock.getStockDate())%>" size="10" /> 
<%--		<img style="CURSOR:hand" src="/ftp/pages/images/calendar.gif" width="16" height="16" alt="date" align="absmiddle" onClick="getDate0('stockDate')">--%>
					</td>
				</tr>
				
				<tr>
					<td height="41" colspan="4">
						<div align="center">
							<input type="button" name="Submit1" value="��&nbsp;&nbsp;��"
								onClick="save(this.form)" class="button">
							&nbsp;&nbsp;
							<input type="button" onclick="javaScript:window.location.reload();" name="Reset" value="��&nbsp;&nbsp;��" class="button">
						</div>
					</td>
				</tr>
			</table>
			<input type="hidden" id="stockId" name="stockId" value="<%=ftpStock == null ? "" : CastUtil.trimNull(ftpStock.getStockId()) %>"  />
		</form>
</body>
<script type="text/javascript">	
<%--j(function() {--%>
<%--    j("#stockDate").datepicker(--%>
<%--	{--%>
<%--		changeMonth: true,--%>
<%--		changeYear: true,--%>
<%--		dateFormat: 'yymmdd',--%>
<%--		showOn: 'button', --%>
<%--		buttonImage: '<%=request.getContextPath()%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',--%>
<%--		buttonImageOnly: true--%>
<%--	});--%>
<%--});--%>
<%if(ftpStock!=null){%>
for(var i=0;i<$("stockTerm").length;i++){
	if($("stockTerm").options[i].value=="<%=ftpStock.getStockTerm()%>"){
		$("stockTerm").selectedIndex=i;break;}
}
<%}%>

function save(FormName) {
    if(!(isNull(FormName.stockYield,"��ծ������"))) {
		return false;			
    }
    if(!(isNull(FormName.stockTerm,"����"))) {
		return false;			
    }
    if(!(isNull(FormName.stockDate,"����"))) {
		return false;			
    }
	    var url = "<%=request.getContextPath()%>/GZWH_save.action";
        new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {stockId:FormName.stockId.value,stockYield:FormName.stockYield.value,stockTerm:FormName.stockTerm.value,stockDate:FormName.stockDate.value,t:new Date().getTime()},
          onSuccess: function() 
           {
	    	   art.dialog({
              	    title:'�ɹ�',
          		    icon: 'succeed',
          		    content: '����ɹ���',
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
