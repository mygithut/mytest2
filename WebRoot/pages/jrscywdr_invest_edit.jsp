<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.*,com.dhcc.ftp.util.*" errorPage="" %>

<html>
	<head>
			<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonExt2.0.2.jsp" /><!-- 需放到prototype.js后面 -->
		<jsp:include page="commonDatePicker.jsp" />
		<script type="text/javascript" src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'></script>
		
		
		
		<title>金融市场业务导入</title>
	</head>
	<body>
	<%
		JrInvest jrinvest = (JrInvest) request.getAttribute("jrinvest");
		String acId = jrinvest == null ? "" : jrinvest.getAcId();
		System.out.println("jsp中acId===========" + acId);
	%>
		
		<form action="<%=request.getContextPath()%>/JRSCYWDRINVEST_update.action"  method="post" id="update" name="update" >	    
		<table  width="90%" align="center" border="0" class="table">
			                <tr>
								<td align="right">序号：</td>
								<td><%=jrinvest.getAcId() == null ? "" : (jrinvest.getAcId().indexOf("_")==-1?jrinvest.getAcId():jrinvest.getAcId().substring(0,jrinvest.getAcId().indexOf("_")))%>
								<input type="hidden" name="acId" id="acId" value="<%=jrinvest.getAcId() == null ? "" : jrinvest.getAcId()%>"/>
								</td>
								<td align="right" >机构号：</td>
								<td >
										<div id='comboxWithTree1' style="width: 300px;"></div>
									<input name="brNo" id="brNo" type="hidden" value="<%=jrinvest.getBrNo()==null?"":jrinvest.getBrNo() %>" />
									<input name="brName" id="brName" type="hidden"  value="<%=jrinvest.getBrName() + "[" + jrinvest.getBrNo() + "]"%>" />
								
								</td>
							</tr>
							<tr>
								<td align="right">产品名称：</td>
								<td>
									<select name="prdtNo" id="prdtNo"> 
										<option value="P1192@@15010101">持有至到期投资-国债</option>
										<option value="P1193@@15010102">持有至到期投资-央行债券</option>
										<option value="P1194@@15010103">持有至到期投资-政策性银行债</option>
										<option value="P1195@@15010104">持有至到期投资-中央公共企业债券</option>
										<option value="P1196@@15010105">持有至到期投资-商业银行债券</option>
										<option value="P1197@@15010106">持有至到期投资-地方政府债券</option>
										<option value="P1198@@15010107">持有至到期投资-长期投资</option>
										<option value="P1226@@15310101">应收款项类投资-凭证式国债</option>
										<option value="P1227@@15310102">应收款项类投资-定向央票</option>
										<option value="P1228@@15310103">应收款项类投资-其他</option>
									</select>
									<input type="hidden" name="prdtName" id="prdtName" />
								</td>
									<td align="right">交易员：</td>
								<td colspan="3">
									<input type="hidden" name="khjl" id="khjl" value="<%=jrinvest.getKhjl()==null?"":jrinvest.getKhjl()%>"/>
									<input type="text" name="empName" id="empName" readonly="readonly"  value="<%=jrinvest.getEmpName()==null?"":jrinvest.getEmpName()%>"/>
									<input type="button" value="选择" class="red button" onclick="javascript:doSelEmp()" />
								</td>
							</tr>
							<tr>
								<td align="right">交易对手：</td>
								<td><input type="text" name="cusName" id="cusName"  value="<%=jrinvest.getCusName() == null ? "" : jrinvest.getCusName()%>"/></td>
								<td align="right">交易对手编号：</td>
								<td><input type="text" name="custNo" id="custNo" value="<%=jrinvest.getCustNo() == null ? "" : jrinvest.getCustNo()%>"/></td>
							</tr>
							<tr>
							    
							</tr>
							<tr>
								<td align="right">起息日：</td>
								<td><input type="text" name="startDate" id="startDate" readonly="readonly" value="<%=jrinvest.getStartDate() == null ? "" : jrinvest.getStartDate()%>" /></td>
							    <td align="right">到期日：</td>
								<td colspan="3"><input type="text" name="endDate" id="endDate" readonly="readonly" value="<%=jrinvest.getEndDate() == null ? "" : jrinvest.getEndDate()%>"/></td>
							</tr>
							<tr>
								<td align="right">票面金额(元)：</td>
								<td colspan="1"><input type="text" name="holdPrincipal"   onkeyup="value=value.replace(/[^\d\.]/g,'')" id="holdPrincipal" value="<%=jrinvest.getHoldPrincipal() == null ? "" :CommonFunctions.doubleFormat( jrinvest.getHoldPrincipal(),2)%>"/></td>
							    <td align="right">票面利率(%)：</td>
								<td><input type="text" name="billYield" id="billYield"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  value="<%=jrinvest.getBillYield() == null ? "" : CommonFunctions.doubleFormat(jrinvest.getBillYield()*100,2)%>" /></td>
							</tr>
							<tr>
								<td align="right">成交金额(元)：</td>
								<td><input type="text" name="dealAmt" id="dealAmt" onblur="sameP(this)"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  value="<%=jrinvest.getDealAmt() == null ? "" : CommonFunctions.doubleFormat(jrinvest.getDealAmt(),2)%>" /></td>
								<td align="right">余额(元)：</td>
								<td colspan="1"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  value="<%=jrinvest.getBal() == null ? "" : CommonFunctions.doubleFormat(jrinvest.getBal(),2)%>" name="bal" id="bal" /></td>
							</tr>
							<tr>
							    <td align="right">计息天数：</td>
								<td colspan="3">
									  <%=jrinvest.getJxts() == null ? "" : jrinvest.getJxts()%></td>
							</tr>
			<tr>
				<td align="center"  colspan="4">
					<input type="button" value="保&nbsp;&nbsp;存" class="red button" onclick="javascript:doSave(this.form)" />
				</td>
			</tr>
		</table>
	</form>
	</body>
	<script type="text/javascript">
	/*起始日期-结束日期，日期面板生产js*/
	j(function() {
	    var minDate1 = '<%=CommonFunctions.GetDBSysDate()%>';
	    var maxDate2 = '<%=CommonFunctions.GetDBSysDate()%>';
	        j("#startDate").datepicker(
				{
					changeMonth: true,
					changeYear: true,
					dateFormat: 'yymmdd',
					showOn: 'button', 
					maxDate: new Date(maxDate2.substring(0,4),parseFloat(maxDate2.substring(4,6))-1,maxDate2.substring(6,8)),
					buttonImage: '<%=request.getContextPath()%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
					buttonImageOnly: true,
				    onSelect: function(dateText, inst) {
				    dateText = dateText.substring(0,4)+'-'+dateText.substring(4,6)+'-'+dateText.substring(6,8);
				    j('#endDate').datepicker('option', 'minDate',new Date(dateText.replace('-',',')));}
			});

			j("#endDate").datepicker(
				{
					changeMonth: true,
					changeYear: true,
					dateFormat: 'yymmdd',
					showOn: 'button', 
					buttonImage: '<%=request.getContextPath()%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
					buttonImageOnly: true,
					minDate: new Date(minDate1.substring(0,4),parseFloat(minDate1.substring(4,6))-1,minDate1.substring(6,8)),
				    onSelect: function(dateText, inst) {
					dateText = dateText.substring(0,4)+'-'+dateText.substring(4,6)+'-'+dateText.substring(6,8);
				    j('#startDate').datepicker('option', 'maxDate', new Date(dateText.replace('-',',')));}
			});
		});
	function sameP(obj){
		var result=obj.value;
		if(result=="")
			result=0;
		document.getElementById("bal").value=parseFloat(result,10);
	}
    function doSave(FormName) {   
    	 if(!(isNull(FormName.acId,"序号"))) {
     		return false;			
         }
         if(!(isNull(FormName.brNo,"所属机构"))) {
     		return false;			
         }
         if(!(isNull(FormName.prdtNo,"产品名称"))) {
     		return false;			
         }
<%--         if(!(isNull(FormName.kmh,"科目号"))) {--%>
<%--     		return false;			--%>
<%--         }--%>
         if(!(isNull(FormName.cusName,"交易对手"))) {
     		return false;			
         }
         if(!(isNull(FormName.custNo,"交易对手编号"))) {
     		return false;			
         }
         if(!(isNull(FormName.holdPrincipal,"票面金额"))) {
     		return false;			
         }
         if(!(isNull(FormName.billYield,"票面利率"))) {
     		return false;			
         }
         if(!(isNull(FormName.startDate,"买入日期"))) {
     		return false;			
         }
         if(!(isNull(FormName.endDate,"到期日"))) {
     		return false;			
         }
         if(!(isNull(FormName.dealAmt,"成本金额"))) {
     		return false;			
         }
<%--         if(!(isNull(FormName.jxts,"计息天数"))) {--%>
<%--     		return false;			--%>
<%--         }--%>
alert(FormName.khjl.value);
alert(FormName.empName.value);
         if(!(isNull(FormName.empName,"交易员"))) {
     		return false;			
         }
         if(!(isNull(FormName.bal,"余额"))) {
             return false;			
     }
     	var obj =document.getElementById("prdtNo");
         document.getElementById("prdtName").value=obj.options[obj.selectedIndex].text;
		$('update').request({   
		       method:"post",
		       onSuccess : function(response) {
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
		   });	
    }
    function ok(){
		  window.parent.location.reload();
	}
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
		    		document.getElementById("khjl").value=path[0];
		    		document.getElementById("empName").value=path[1];
		    	}
		     }
		});
    }
	function seled(id,value){
		var selectFlags = document.getElementById(id);
		for (var i=0; i<selectFlags.options.length; i++) {
			//alert(selectFlags.options[i].value);
			if(selectFlags.options[i].value==value){
				//alert(2);
				selectFlags.selectedIndex=i;
				return ;
			}
		}
	}
	seled('prdtNo','<%=jrinvest.getPrdtNo()==null?"":jrinvest.getPrdtNo()%>@@<%=jrinvest.getKmh()==null?"":jrinvest.getKmh()%>');
	</script>
</html>

