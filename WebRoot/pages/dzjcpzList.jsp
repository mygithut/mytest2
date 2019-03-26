<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.entity.FtpPoolInfo,com.dhcc.ftp.entity.FtpBusinessPrdt" errorPage=""%>
<html>
	<head>
		<jsp:include page="commonJs.jsp" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<title>单资金池->单资金池配置</title>
	</head>
	<%
	List<FtpPoolInfo> ftpPoolInfoList =(List<FtpPoolInfo>)request.getAttribute("ftpPoolInfoList");
    %>
	<body>
		<form name="chooseform" method="post">
		<table width="100%" align="center" class="table">
			<tr>
				<td class="middle_header" colspan="3">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">配置</font>
				</td>
			</tr>
		    <tr><td width="10%">
			  <table border="0" cellspacing="0" cellpadding="0"
				align="center" class="table" id="tbColor" width="100%">
				<tr>
				    <td class="middle_header" >
					     <font style="padding-left:10px">资金池数量</font>
				       </td>
				</tr>
		        <tr>
				    <td align="center">
				        <select id="poolNum" name="poolNum" style="width:70">
				           <option value="1">1</option>
				        </select>
			  </td>
		      
		    </tr>
	    </table>
	    </td>
	    <td width="45%">
		          <table width="100%" align="left" class="table" id="poolTable">
		            <tr>
				       <td class="middle_header" colspan="5">
					     <font style="padding-left:10px">资金池配置</font>
				       </td>
			        </tr>
			        <tr>
			           <td align="center">编号</td>
			           <td align="center" width="40%">资金池名称</td>
			           <td align="center" width="40%">包含业务对象</td>
			        </tr>
			        <%
			        FtpPoolInfo ftpPoolInfo = new FtpPoolInfo();
			        if(ftpPoolInfoList!=null && ftpPoolInfoList.size() > 0)
			        	ftpPoolInfo = ftpPoolInfoList.get(0);
			        %>
			        <tr>
			        	<td align="center"><input type="hidden" name="poolNo" id="poolNo101" value="101" />101</td>
			        	<td align="center"><input type="text" name="poolName" id="poolName101" readonly="readonly" value="单资金池"/></td>
			        	<td align="center"><input type="text" name="contentObject" id="contentObject101" value="<%=CastUtil.trimNull(ftpPoolInfo.getContentObject()) %>" readonly="readonly" /></td>
			        </tr>
		          </table>
	    </td>
	    <td width="45%">
	    <iframe src="<%=request.getContextPath()%>/DZJCPZ_getPrdt.action?poolNo=101" id="rightFrame" width="100%" height="250" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" align="middle"></iframe> 
	    </td>
	    </tr>
		<tr>
		  <td colspan="3" align="center">
		   <input type="button" value="保&nbsp;&nbsp;存" onclick="doSave()" class="button">
		  </td>
		</tr>
		</form>
	</body>

	<script type="text/javascript">
    function doSave() {
        if($('poolNum').value == '') {
            alert("您还未进行任何配置，无法保存！！");
            return false;
        }
        var poolNos = new Array();
        var poolNames = new Array();
        var poolTypes = new Array();
        var contentObjects = new Array();
        var poolNo = document.getElementsByName("poolNo");
        var poolType = document.getElementsByName("poolType");
        var poolName = document.getElementsByName("poolName");
        var contentObject = document.getElementsByName("contentObject");
        for(var i = 0; i < poolNo.length; i++) {
        	poolNos.push(poolNo[i].value);
        }
        for(var i = 0; i < poolName.length; i++) {
            if(poolName[i].value == '') {
                alert('编号为“'+poolName[i].id.substr(8, 3)+'”的资金池名称为空，请输入！！');
                return false;
            }else {
            	poolNames.push(poolName[i].value);
            }
        }
        for(var i = 0; i < contentObject.length; i++) {
            if(contentObject[i].value == '') {
                alert('编号为“'+contentObject[i].id.substr(13, 3)+'”的资金池包含业务对象未配置，请配置！！');
                return false;
            }else {
            	contentObjects.push(contentObject[i].value);
            }
        }
        var brNo = window.parent.document.getElementById("brNo").value;
        var url = "<%=request.getContextPath()%>/DZJCPZ_save.action";
		new Ajax.Request(url, {
			method : 'post',
			parameters : {
			brNo : brNo, poolNos : poolNos, poolNames : poolNames, contentObjects : contentObjects
			},
			onSuccess : function() {
		    	   art.dialog({
	               	    title:'成功',
	           		    icon: 'succeed',
	           		    content: '保存成功！',
	           		    cancelVal: '关闭',
	           		    cancel: true
	            	 });
			}
		});
    }
    </script>
</html>
