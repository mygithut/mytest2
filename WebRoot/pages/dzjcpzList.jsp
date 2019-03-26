<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.entity.FtpPoolInfo,com.dhcc.ftp.entity.FtpBusinessPrdt" errorPage=""%>
<html>
	<head>
		<jsp:include page="commonJs.jsp" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<title>���ʽ��->���ʽ������</title>
	</head>
	<%
	List<FtpPoolInfo> ftpPoolInfoList =(List<FtpPoolInfo>)request.getAttribute("ftpPoolInfoList");
    %>
	<body>
		<form name="chooseform" method="post">
		<table width="100%" align="center" class="table">
			<tr>
				<td class="middle_header" colspan="3">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">����</font>
				</td>
			</tr>
		    <tr><td width="10%">
			  <table border="0" cellspacing="0" cellpadding="0"
				align="center" class="table" id="tbColor" width="100%">
				<tr>
				    <td class="middle_header" >
					     <font style="padding-left:10px">�ʽ������</font>
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
					     <font style="padding-left:10px">�ʽ������</font>
				       </td>
			        </tr>
			        <tr>
			           <td align="center">���</td>
			           <td align="center" width="40%">�ʽ������</td>
			           <td align="center" width="40%">����ҵ�����</td>
			        </tr>
			        <%
			        FtpPoolInfo ftpPoolInfo = new FtpPoolInfo();
			        if(ftpPoolInfoList!=null && ftpPoolInfoList.size() > 0)
			        	ftpPoolInfo = ftpPoolInfoList.get(0);
			        %>
			        <tr>
			        	<td align="center"><input type="hidden" name="poolNo" id="poolNo101" value="101" />101</td>
			        	<td align="center"><input type="text" name="poolName" id="poolName101" readonly="readonly" value="���ʽ��"/></td>
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
		   <input type="button" value="��&nbsp;&nbsp;��" onclick="doSave()" class="button">
		  </td>
		</tr>
		</form>
	</body>

	<script type="text/javascript">
    function doSave() {
        if($('poolNum').value == '') {
            alert("����δ�����κ����ã��޷����棡��");
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
                alert('���Ϊ��'+poolName[i].id.substr(8, 3)+'�����ʽ������Ϊ�գ������룡��');
                return false;
            }else {
            	poolNames.push(poolName[i].value);
            }
        }
        for(var i = 0; i < contentObject.length; i++) {
            if(contentObject[i].value == '') {
                alert('���Ϊ��'+contentObject[i].id.substr(13, 3)+'�����ʽ�ذ���ҵ�����δ���ã������ã���');
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
	               	    title:'�ɹ�',
	           		    icon: 'succeed',
	           		    content: '����ɹ���',
	           		    cancelVal: '�ر�',
	           		    cancel: true
	            	 });
			}
		});
    }
    </script>
</html>
