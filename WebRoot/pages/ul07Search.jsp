<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst" %>
<html>
<head>
<title>����ƥ��-��ʷ�۸�鿴</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="commonJs.jsp" />
<jsp:include page="commonExt2.0.2.jsp" /><!-- ��ŵ�prototype.js���� -->
<jsp:include page="commonDatePicker.jsp" />
<script type="text/javascript" src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'>
</script>
</head>
<body>
<%
TelMst telmst = (TelMst) request.getSession().getAttribute("userBean"); 
long sysDate = CommonFunctions.GetDBSysDate();
%>
 <div class="cr_header">
			��ǰλ�ã�����ƥ��->����ƥ����ʷ�۸�鿴
		</div>
<form name="thisform" action="" method="post">
 <table width="95%" border="0" align="center">
  <tr>
		      <td>
		        <table width="90%" align="center" class="table">
			<tr>
				<td class="middle_header" colspan="8">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">��ѯ</font>
				</td>
			</tr>
	<tr>
		<td width="10%" align="right">�������ƣ�</td>
		<td colspan="3" width="40%">
		<div id='comboxWithTree1'></div>
		<input name="brNo" id="brNo" type="hidden" value="<%=telmst.getBrMst().getBrNo()%>" />
		<input name="manageLvl" id="manageLvl" type="hidden" value="<%=telmst.getBrMst().getManageLvl()%>" />
		<input name="brName" id="brName" type="hidden"  value="<%=telmst.getBrMst().getBrName() + "[" + telmst.getBrMst().getBrNo() + "]"%>" />
		</td>
		<td width="10%" align="right">����ϵͳ���ڣ� </td>
		<td width="15%"><input type="text" name="wrkTime1" id="wrkTime1" value="<%=CommonFunctions.GetDBSysDate() %>" maxlength="8" size="8" />
<%--		 <img style="CURSOR:hand" src="/ftp/pages/images/calendar.gif" width="16" height="16" alt="date" align="absmiddle" onClick="getDate0('wrkTime1')">--%>
		 </td>
	    <td width="10%" align="right">�� </td>
		<td width="15%"><input type="text" name="wrkTime2" id="wrkTime2" value="<%=CommonFunctions.GetDBSysDate() %>"  maxlength="8" size="8" /> 
<%--		<img style="CURSOR:hand" src="/ftp/pages/images/calendar.gif" width="16" height="16" alt="date" align="absmiddle" onClick="getDate0('wrkTime2')">--%>
		</td>
	
		
	</tr>
	<tr>
	    <td width="10%" align="right">ҵ�����ͣ� </td>
		<td>
			<select name="businessNo" id="businessNo" onchange="getPrdtName(this.id)">
			</select>
		</td>
		<td width="10%" align="right">��Ʒ���ƣ� </td>
		<td colspan="3">
			<select name="prdtNo" id="prdtNo" disabled="disabled">
			<option value="">��ѡ��ҵ������</option>
			</select>
		</td>
		<td width="10%" align="right">ѡ����֣� </td>
		<td>
				<select style="width: 120" name="curNo" id="curNo">
				</select>
		</td>
		</tr>
      <tr>
        <td align="center" colspan="8">
        <input name="query" class="button" type="button" id="query" height="20" onClick="onclick_query()" value="��&nbsp;&nbsp;ѯ" /> 
        <input name="back" class="button" type="button" id="back" height="20" onClick="onclick_back()" value="��&nbsp;&nbsp;��" /> 
      <%--  <input name="back" class="button" type="button" id="back" height="20" onClick="do_Export()" value="����" />  --%>
          </td>
      </tr>
      </table>
      </td>
      </tr>
      <tr>
        <td align="center">
        <iframe src="" id="downframe"  name="downframe" width="90%" height="350" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" align="middle"></iframe>
        </td>
      </tr>
  </table>
 </form>
</body>
<script type="text/javascript" language="javascript">
jQuery(function(){// domԪ�ؼ������
	jQuery(".table tr:even").addClass("tr-bg1");
	jQuery(".table tr:odd").addClass("tr-bg2");
});
fillSelect('businessNo','<%=request.getContextPath()%>/fillSelect_getBusinessName.action');
/*��ʼ����-�������ڣ������������js*/
j(function() {
    var minDate1 = '<%=sysDate%>';
    var maxDate2 = '<%=sysDate%>';
        j("#wrkTime1").datepicker(
			{
				changeMonth: true,
				changeYear: true,
				dateFormat: 'yymmdd',
				showOn: 'button', 
				maxDate: new Date(maxDate2.substring(0,4),parseFloat(maxDate2.substring(4,6))-1,maxDate2.substring(6,8)),
				buttonImage: 'pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
				buttonImageOnly: true,
			    onSelect: function(dateText, inst) {
			    dateText = dateText.substring(0,4)+'-'+dateText.substring(4,6)+'-'+dateText.substring(6,8);
			    j('#wrkTime2').datepicker('option', 'minDate',new Date(dateText.replace('-',',')));}
		});

		j("#wrkTime2").datepicker(
			{
				changeMonth: true,
				changeYear: true,
				dateFormat: 'yymmdd',
				showOn: 'button', 
				buttonImage: 'pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
				buttonImageOnly: true,
				minDate: new Date(minDate1.substring(0,4),parseFloat(minDate1.substring(4,6))-1,minDate1.substring(6,8)),
			    onSelect: function(dateText, inst) {
				dateText = dateText.substring(0,4)+'-'+dateText.substring(4,6)+'-'+dateText.substring(6,8);
			    j('#wrkTime1').datepicker('option', 'maxDate', new Date(dateText.replace('-',',')));}
		});
	});
function onclick_query(){
	if(!(isNull(document.getElementById("brNo"),"����"))) {
		return  ;			
	}
	   var brNo =document.getElementById("brNo").value;
	   var curNo =document.getElementById("curNo").value;
	   var businessNo =document.getElementById("businessNo").value;	 
	   var prdtNo=document.getElementById("prdtNo").value;
	   var wrkTime1=document.getElementById("wrkTime1").value;
	   var wrkTime2=document.getElementById("wrkTime2").value;
	   if(brNo!=''||curNo!=''||businessNo!=''||prdtNo!=''||wrkTime1!=''||wrkTime2!=''){
	       window.frames.downframe.location.href ='<%=request.getContextPath()%>/UL07_List.action?isQuery=1&brNo='+brNo+'&curNo='+curNo+'&businessNo='+businessNo+'&prdtNo='+prdtNo+'&wrkTime1='+wrkTime1+'&wrkTime2='+wrkTime2;
	   }else{
	       alert("�������ѯ������");
	       return false;
	   }
	     parent.parent.parent.openNewDiv();
	}
	function onclick_back(){
	   window.location.reload();
	}
	function getPrdtName(businessNo) {
		var business = $(businessNo).value;
		if (business != '') {
			$("prdtNo").disabled = false;
			document.getElementById("prdtNo").options.length=0;
			document.getElementById("prdtNo").add(new Option("--��ѡ��--",""));
		   	var prdtNameList ;
		   	var url = '<%=request.getContextPath()%>/fillSelect_getPrdtName.action?businessNo='+business;
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
function do_Export() {
 
	document.form1.action='<%=request.getContextPath()%>/pages/ul07ListExport.jsp';
	document.form1.submit();
}
</script>
</html>
<script type="text/javascript" language="javascript">fillSelect('curNo','<%=request.getContextPath()%>/queryCurrency.action');</script>
