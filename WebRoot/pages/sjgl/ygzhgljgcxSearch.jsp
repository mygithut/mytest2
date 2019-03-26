<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst" %>
<html>
<head>
<title>数据管理-员工账户关联结果查询</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="../commonJs.jsp" />
<jsp:include page="../commonExt2.0.2.jsp" /><!-- 需放到prototype.js后面 -->
<jsp:include page="../commonDatePicker.jsp" />
<script type="text/javascript" src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'>
</script>
</head>
<body>
<%
TelMst telmst = (TelMst) request.getSession().getAttribute("userBean"); 
String maxDate = String.valueOf(CommonFunctions.GetDBTodayDate());
String minDate = CommonFunctions.dateModifyD(maxDate, 0);
%>
<div class="cr_header">
			当前位置：数据管理-员工账户关联结果查询
		</div>
<form name="thisform" action="" method="post">
<!--  <table width="95%" border="0" align="center" id="ygzhgl" class="table"> -->
 <table width="95%" border="0" align="center" id="ygzhgl" >
  <tr>
		      <td>
		        <table width="100%" align="left" class="table">
			<tr>
				<td class="middle_header" colspan="8">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">查询</font>
				</td>
			</tr>
	<tr>
		<td width="10%" align="right">员工所属机构：</td>
		<td colspan="3" width="40%">
		<div id='comboxWithTree1'></div>
		<input name="brNo" id="brNo" type="hidden" value="<%=telmst.getBrMst().getBrNo()%>" />
		<input name="manageLvl" id="manageLvl" type="hidden" value="<%=telmst.getBrMst().getManageLvl()%>" />
		<input name="brName" id="brName" type="hidden"  value="<%=telmst.getBrMst().getBrName() + "[" + telmst.getBrMst().getBrNo() + "]"%>" />
		</td>
		<td width="10%" align="right">开户日期段： </td>
		<td width="15%"><input type="text" name="wrkTime1" id="wrkTime1" value="<%=minDate %>" maxlength="8" size="8" />
		 </td>
	    <td width="10%" align="right">到 </td>
		<td width="15%"><input type="text" name="wrkTime2" id="wrkTime2" value="<%=maxDate %>"  maxlength="8" size="8" /> 
		</td>
	</tr>
	<tr>
	    <td width="10%" align="right">业务类型： </td>
		<td>
			<select name="businessNo" id="businessNo" onchange="getPrdtName(this.id)">
			</select>
		</td>
		<td width="10%" align="right">产品名称： </td>
		<td >
			<select name="prdtNo" id="prdtNo" disabled="disabled">
			<option value="">先选择业务类型</option>
			</select>
		</td>
		<td width="10%" align="right">业务账号： </td>
		<td>
			<input name="accId" id="accId" type="text" value="" />
		</td>
		<td width="10%" align="right">客户名称：</td>
        <td><input type="text" id="cusName" name="cusName" /></td>
	</tr>
	<tr>
		<td width="10%" align="right">客户经理：</td>
		<td colspan="7">
								<!-- 如果对应角色级别为低级，则只能查看本操作员对应的员工 -->
								<%if(telmst.getRoleMst() != null && telmst.getRoleMst().getRoleLvl().equals("1")) {%>
									<input type="hidden" name="empNo" id="empNo" value="<%=telmst.getFtpEmpInfo()==null?"":telmst.getFtpEmpInfo().getEmpNo() %>" />
									<input type="text" name="empName" id="empName" value="<%=telmst.getFtpEmpInfo()==null?"":telmst.getFtpEmpInfo().getEmpName() %>" readonly="readonly" size="15"/>
								<%}else{ %>
									<input type="hidden" name="empNo" id="empNo" value="" />
									<input type="text" name="empName" id="empName" readonly="readonly" size="15" disabled="disabled" onfocus=this.blur value="请选择客户经理"/>
									<input type="button" value="选&nbsp;择" class="red button" onclick="javascript:doSelEmp()" />
								<%} %>
								</td>
	</tr>	
    <tr>
        <td align="center" colspan="8">
			每页显示
		<input type="text" name="pageSize" onblur="isInt(this,'页码:')" id="pageSize" value="100" size="5" />
        <input name="query" class="button" type="button" id="query" height="20" onClick="onclick_query()" value="查&nbsp;&nbsp;询" /> 
        <input name="back" class="button" type="button" id="back" height="20" onClick="onclick_back()" value="重&nbsp;&nbsp;置" /> 
<%--        <%if(telmst.getTelNo().equals("000000")||telmst.getTelNo().equals("200001")||telmst.getTelNo().equals("200002")) {%>--%>
        <input name="back" class="button" type="button" id="back" height="20" onClick="onclick_export()" value="导&nbsp;&nbsp;出" />
     <%--   <%} %>--%>
      <%--  <input name="back" class="button" type="button" id="back" height="20" onClick="do_Export()" value="导出" />  --%>
          </td>
    </tr>
    </table>
      </td>
      </tr>
      <tr>
        <td align="left">
        <iframe src="" id="downframe" name="downframe" width="100%" height="350" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" align="middle"></iframe>
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
    var minDate1 = '<%=minDate%>';
    var maxDate2 = '<%=maxDate%>';
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
function doSelEmp(){
	art.dialog.open('<%=request.getContextPath()%>/pages/selEmpS.jsp?Rnd='+Math.random(), {
	    title: '选择',
	    width: 800,
	    height:400,
	    id:'sel',
	    close: function (){
    		var paths = art.dialog.data('bValue');
    		if(paths!=null&&paths!=""){
	    		var path=paths.split("@@");
	    		document.getElementById("empNo").value=path[0];
	    		document.getElementById("empName").value=path[1];
	    	}
	     }
	});
}
function onclick_query(){
	if(!(isNull(document.getElementById("brNo"),"机构"))) {
		return  ;			
	}
	   var manageLvl =document.getElementById("manageLvl").value;
	   var brNo =document.getElementById("brNo").value;
	   var businessNo =document.getElementById("businessNo").value;	 
	   var prdtNo=document.getElementById("prdtNo").value;
	   var wrkTime1=document.getElementById("wrkTime1").value;
	   var wrkTime2=document.getElementById("wrkTime2").value;
	   var empNo=document.getElementById("empNo").value;
	   var accId=document.getElementById("accId").value;
	   var cusName=document.getElementById("cusName").value;
	   window.frames.downframe.location.href ='YGZHGLJGCX_List.action?brNo='+brNo+'&manageLvl='+manageLvl+'&accId='+accId+'&businessNo='+businessNo+'&prdtNo='+prdtNo+'&wrkTime1='+wrkTime1+'&wrkTime2='+wrkTime2+'&empNo='+empNo+'&cusName='+encodeURI(cusName)+"&pageSize="+j('#pageSize').val();
	   parent.parent.parent.openNewDiv();
	}
	
function isRelated1(){
	var isRelated = document.getElementById("isRelated").value;
	if(isRelated!=''&&parseInt(isRelated)==1){
		document.getElementById("emp1").style.display="block";
		document.getElementById("empName").style.display="block";
	}else {
		document.getElementById("emp1").style.display="none";
		document.getElementById("empName").style.display="none";
		document.getElementById("empName").value="";
<%--		document.getElementById("empName").value="";--%>
	}
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
function onclick_back(){
	   window.location.reload();
	}

function onclick_export() {
	   var manageLvl =document.getElementById("manageLvl").value;
	   var brNo =document.getElementById("brNo").value;
	   var wrkTime1=document.getElementById("wrkTime1").value;
	   var wrkTime2=document.getElementById("wrkTime2").value;
	   
	   var businessNo =document.getElementById("businessNo").value;	 
	   var prdtNo=document.getElementById("prdtNo").value;
	   var empNo=document.getElementById("empNo").value;
	   var accId=document.getElementById("accId").value;
	   var cusName=document.getElementById("cusName").value;
	   
	window.location.href='<%=request.getContextPath()%>/YGZHGLJGCX_Export.action?brNo='+brNo+'&manageLvl='+manageLvl+'&wrkTime1='+wrkTime1+'&wrkTime2='+wrkTime2+'&businessNo='+businessNo+'&prdtNo='+prdtNo+'&empNo='+empNo+'&accId='+accId+'&cusName='+encodeURI(cusName);
}

</script>
</html>
