<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst" %>
<html>
<head>
<title>���ʽ��-��ʷ�۸�鿴</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="commonJs.jsp" />
</head>
<body>
<div class="cr_header">
			��ǰλ�ã����ʽ��->���ʽ�ض��۽���鿴
		</div>
<form name="thisform" action="" method="post">
<table width="80%" border="0" align="center">
		   <tr>
		      <td align="left">
  <table width="900" border="0" align="left" class="table">
    <tr>
				<td class="middle_header" colspan="4">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">��ѯ</font>
				</td>
			</tr>
	<tr>
		<td width="30%" align="right">�������ƣ�</td>
		<td colspan="3">
		<select name="brNo" id="brNo">
		            </select>
		</td>
	</tr>
	<tr>
				<td colspan="6" align="center">
					ÿҳ��ʾ
					    <input type="text" name="pageSize" id="pageSize" value="10" size="5" />
						<input type="button" name="Submit1" class="button" onClick="doQuery()" value="��&nbsp;&nbsp;ѯ">
				</td>
			</tr>
		</table>
		      </td>
		   </tr>
		   <tr height="350">
		      <td align="left">
		        <iframe src="" id="downFrame" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" align="middle"></iframe> 
		      </td>
		   </td>
		</table>
 </form>
</body>
<script type="text/javascript">	
fillSelect('brNo','fillSelect_getBrNoByLvl2');
function doQuery(){
	if(!(isNull(document.getElementById("brNo"),"����"))) {
		return  ;			
	}
	   var brNo =document.getElementById("brNo").value;
	   window.frames.downFrame.location.href ='<%=request.getContextPath()%>/UL01_getResultPrice.action?brNo='+brNo+'&pageSize='+document.getElementById('pageSize').value;
	}
	function onclick_back(){
	   window.parent.location.reload();
	}
	function getPrdtName(businessNo) {
		var business = $(businessNo).value;
		if (business != '') {
			$("prdtNo").disabled = false;
			document.getElementById("prdtNo").options.length=0;
			document.getElementById("prdtNo").add(new Option("--��ѡ��--",""));
		   	var prdtNameList ;
		   	var url = '<%=request.getContextPath()%>/UL07_getPrdtName.action?businessNo='+business;
		    new Ajax.Request( 
		    url, 
		     {   
		      method: 'post',   
		      parameters: {t:new Date().getTime()},
		      onSuccess: function(transport) 
		       {  
		    	  prdtNameList = transport.responseText.split(",");
		          $A(prdtNameList).each(
		             function(index){
		             	//Option������ǰ����text��������value
		                  var opt= new Option(index.split('|')[1],index.split('|')[0]);
				          document.getElementById("prdtNo").add(opt);
		             }
		          );
		        }
		      }
		   );
		}else {
			$("prdtNo").value = '';
			$("prdtNo").disabled = true;
		}
		

	}
</script>
</html>
