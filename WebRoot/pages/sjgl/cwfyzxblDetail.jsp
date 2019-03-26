<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.entity.FtpMidItem,java.text.*" %>
<%@page import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.FtpUtil,com.dhcc.ftp.util.*"%>
<html>
<meta http-equiv="expires" content="0" /> 
<meta http-equiv="cache-control" content="no-cache, must-revalidate" /> 
<meta http-equiv="pragram" content="no-cache" /> 
<head>
<title>财务费用杂项补录</title>
<jsp:include page="../commonJs.jsp" />
</head>
<body>
<br/>
<form action=""  method="post">
<%
FtpMidItem ftpMidItem = (FtpMidItem)request.getAttribute("ftpMidItem");
 %>
			<table class="table" width="70%" align="center">
			  	<tr>
					<th width="20%" align="right">
						项目编号：
					</th>
					<td width="30%">
					    <%=ftpMidItem.getItemNo()%>
						<input type="hidden" id="itemNo" name="itemNo" value="<%=ftpMidItem.getItemNo()%>" />
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						项目名称：
					</th>
					<td width="30%">
					    <%=ftpMidItem.getItemName()%>
						<input type="hidden" id="itemName" name="itemName" value="<%=ftpMidItem.getItemName()%>" />
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						项目值(%)：
					</th>
					<td width="30%">
						<input type="text" id="itemAmount" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="isFloat(this,'项目值(%)：')" name="itemAmount" value="<%=CommonFunctions.doublecut(ftpMidItem.getItemAmount() * 100, 2) %>" />
					</td>
				</tr>
				<tr>
					<td height="41" colspan="4">
						<div align="center">
							<input type="button" name="Submit1" value="保&nbsp;&nbsp;存"
								onClick="save(this.form)" class="button">
						</div>
					</td>
				</tr>
			</table>
		</form>
</body>
<script type="text/javascript">	

function save(FormName) {
	    var itemAmount = FormName.itemAmount;
	    var itemNo = FormName.itemNo.value;
	    
	    var url = "<%=request.getContextPath()%>/CWFYZXBL_save.action";
        new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {itemNo:FormName.itemNo.value,itemAmount:FormName.itemAmount.value,t:new Date().getTime()},
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
