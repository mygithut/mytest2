<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst" %>
<html>
<head>
<title>期限匹配-历史价格查看</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="commonJs.jsp" />
<jsp:include page="commonExt2.0.2.jsp" /><!-- 需放到prototype.js后面 -->
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
			当前位置：期限匹配->期限匹配历史价格查看
		</div>
<form name="thisform" action="" method="post">
 <table width="95%" border="0" align="center">
  <tr>
		      <td>
		        <table width="90%" align="center" class="table">
			<tr>
				<td class="middle_header" colspan="8">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">查询</font>
				</td>
			</tr>
	<tr>
		<td width="10%" align="right">机构名称：</td>
		<td colspan="3" width="40%">
		<div id='comboxWithTree1'></div>
		<input name="brNo" id="brNo" type="hidden" value="<%=telmst.getBrMst().getBrNo()%>" />
		<input name="manageLvl" id="manageLvl" type="hidden" value="<%=telmst.getBrMst().getManageLvl()%>" />
		<input name="brName" id="brName" type="hidden"  value="<%=telmst.getBrMst().getBrName() + "[" + telmst.getBrMst().getBrNo() + "]"%>" />
		</td>
		<td width="10%" align="right">定价系统日期： </td>
		<td width="15%"><input type="text" name="wrkTime1" id="wrkTime1" value="<%=CommonFunctions.GetDBSysDate() %>" maxlength="8" size="8" />
<%--		 <img style="CURSOR:hand" src="/ftp/pages/images/calendar.gif" width="16" height="16" alt="date" align="absmiddle" onClick="getDate0('wrkTime1')">--%>
		 </td>
	    <td width="10%" align="right">到 </td>
		<td width="15%"><input type="text" name="wrkTime2" id="wrkTime2" value="<%=CommonFunctions.GetDBSysDate() %>"  maxlength="8" size="8" /> 
<%--		<img style="CURSOR:hand" src="/ftp/pages/images/calendar.gif" width="16" height="16" alt="date" align="absmiddle" onClick="getDate0('wrkTime2')">--%>
		</td>
	
		
	</tr>
	<tr>
	    <td width="10%" align="right">业务类型： </td>
		<td>
			<select name="businessNo" id="businessNo" onchange="getPrdtName(this.id)">
			</select>
		</td>
		<td width="10%" align="right">产品名称： </td>
		<td colspan="3">
			<select name="prdtNo" id="prdtNo" disabled="disabled">
			<option value="">先选择业务类型</option>
			</select>
		</td>
		<td width="10%" align="right">选择币种： </td>
		<td>
				<select style="width: 120" name="curNo" id="curNo">
				</select>
		</td>
		</tr>
      <tr>
        <td align="center" colspan="8">
        <input name="query" class="button" type="button" id="query" height="20" onClick="onclick_query()" value="查&nbsp;&nbsp;询" /> 
        <input name="back" class="button" type="button" id="back" height="20" onClick="onclick_back()" value="重&nbsp;&nbsp;置" /> 
      <%--  <input name="back" class="button" type="button" id="back" height="20" onClick="do_Export()" value="导出" />  --%>
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
jQuery(function(){// dom元素加载完毕
	jQuery(".table tr:even").addClass("tr-bg1");
	jQuery(".table tr:odd").addClass("tr-bg2");
});
fillSelect('businessNo','<%=request.getContextPath()%>/fillSelect_getBusinessName.action');
/*起始日期-结束日期，日期面板生产js*/
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
	if(!(isNull(document.getElementById("brNo"),"机构"))) {
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
	       alert("请输入查询条件！");
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
			document.getElementById("prdtNo").add(new Option("--请选择--",""));
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
		             	//Option参数，前面是text，后面是value
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
