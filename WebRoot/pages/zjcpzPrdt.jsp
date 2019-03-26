<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.entity.FtpPoolInfo,com.dhcc.ftp.entity.FtpBusinessPrdt" errorPage=""%>
<html>
	<head>
		<jsp:include page="commonJs.jsp" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<title>多资金池->多资金池配置-产品列表</title>
	</head>
	<%
	String poolNo = (String)request.getAttribute("poolNo");
	List<FtpBusinessPrdt> ftpBusinessPrdtList =(List<FtpBusinessPrdt>)request.getAttribute("ftpBusinessPrdtList");
    %>
	<body>
		<form name="chooseform" method="post">
		<table width="100%" align="center" class="table">
			<tr>
				<td class="middle_header" colspan="4">
					编号<%=poolNo %>--产品选择
				</td>
			</tr>
		    <tr>
		        <td width="15%" align="center">
		                            <input type="checkbox" name="all" value="checkbox"
							onClick="checkAll()" />  全选
		        </td>
                <td width="10%" align="center">
		                             序号
		        </td>
                <td width="20%" align="center">
		                              产品编号
		        </td>
                <td width="55%" align="center">
		                              产品名称
		        </td>
	        </tr>
	        <%
	        int i =0;
	        for(FtpBusinessPrdt ftpBusinessPrdt : ftpBusinessPrdtList) { %>
		    <tr>
		        <td align="center">
		            <input type="checkbox" id="<%=ftpBusinessPrdt.getPrdtNo() %>" name="<%=ftpBusinessPrdt.getPrdtNo() %>" value="<%=ftpBusinessPrdt.getPrdtNo() %>" onclick="doChoose(this)">
		        </td>
                <td align="center">
		            <%=i+1 %>
		        </td>
                <td align="center">
		             <%=ftpBusinessPrdt.getPrdtNo() %>
		        </td>
                <td align="left">
		             <%=ftpBusinessPrdt.getPrdtName() %>
		        </td>
	        </tr>
		    <%i++;} %>
		    </table>
		</form>
	</body>

	<script type="text/javascript">
	//将已经选择的，在下次进入该页面时自动选中
	var content = window.parent.document.getElementById("contentObject<%=poolNo%>").value;
	if (content != '') {
		content = content.replace(new RegExp(/\'/g),'');
		var contentObj = content.split('+');
		for(var i = 0; contentObj != null && i < contentObj.length; i++) {
	        $(contentObj[i]).checked = true;
		}
	}
	
    function doChoose(prdtNo) {
    	var contentObject = window.parent.document.getElementById("contentObject<%=poolNo%>");
    	var content = contentObject.value;
    	//如果是 选中，则在“包含业务对象”中添加对应的产品
    	if(prdtNo.checked) {
    		if(content == '') contentObject.value += '\''+prdtNo.value+'\'';
        	else contentObject.value += "+\'" + prdtNo.value+'\'';
    	}else {
        	if(content.indexOf("\'"+prdtNo.value + "\'+") != -1) contentObject.value = content.replace("\'"+prdtNo.value + "\'+", "");
        	else if(content.indexOf("+\'" + prdtNo.value+"\'") != -1) contentObject.value = content.replace("+\'" + prdtNo.value+"\'", "");
        	else if(content = "\'"+prdtNo.value+"\'") contentObject.value = "";
    	}
    	
    }
    function checkAll() {
    	var contentObject = window.parent.document.getElementById("contentObject<%=poolNo%>");
    	contentObject.value = "";
    	var content = "";
    	var selectFlags = document.getElementsByTagName("input");
    	for (var i=1; i<selectFlags.length; i++) {
        	if(selectFlags[i].type=="checkbox") {
        		selectFlags[i].checked = window.event.srcElement.checked;//通过单击的按钮判断是选中还是未选
        		if(selectFlags[i].checked == true)content += "\'"+selectFlags[i].value+"\'+";
        	}
    	}
    	if(content.length > 0)
    	content = content.substr(0,content.length-1);
    	contentObject.value = content;
    }
    </script>
</html>
