<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="com.dhcc.ftp.entity.TelMst"%>
<%
	TelMst telmst = (TelMst) request.getSession().getAttribute("userBean");
    String acId = (String)request.getParameter("acId");
%>
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
		<form action="<%=request.getContextPath()%>/JRSCYWDRTY_saveTy.action" method="post" id="update" name="update" >	    
		<table  width="90%" align="center" border="0" class="table">
			                <tr>
								<td align="right">序号：</td>
								<td><%=acId %>
								<input type="hidden" name="acId" id="acId" value="<%=acId %>" /></td>
								<td align="right" >机构：</td>
								<td >
									<div id='comboxWithTree1' style="width: 300px;"></div>
									<input name="brNo" id="brNo" type="hidden" value="<%=telmst.getBrMst().getBrNo()%>" />
									<input name="brName" id="brName" type="hidden"  value="<%=telmst.getBrMst().getBrName() + "[" + telmst.getBrMst().getBrNo() + "]"%>" />
								</td>
							</tr>
							<tr>
								<td align="right">产品名称：</td>
								<td>
									<select  name="prdtNo" id="prdtNo">
										<option value="P1006@@10110102">存放工行约期存款</option>
										<option value="P1008@@10110104">存放农行约期存款</option>
										<option value="P1010@@10110106">存放中行约期存款</option>
										<option value="P1012@@10110108">存放建行约期存款</option>
										<option value="P1014@@10110110">存放政策性银行约期存款</option>
										<option value="P1016@@10110112">存放股份制银行约期存款</option>
										<option value="P1017@@10110113-1">存放境内银行同业一般存款</option>
										<option value="P1018@@10110113-2">存放境内银行同业约期存款</option>
										<option value="P1019@@10110200-1">存放境内非银行同业一般存款</option>
										<option value="P1020@@10110200-2">存放境内非银行同业约期存款</option>
										<option value="P1021@@10110300-1">存放境外同业一般存款</option>
										<option value="P1022@@10110300-2">存放境外同业约期存款</option>
										<option value="P1025@@10120103">放省联社约期存款</option>
										<option value="P1027@@10120202">存放地市级约期存款</option>
										<option value="P1029@@10120302">存放县级约期存款</option>
										<option value="P2072@@20170100-1">境内银行同业存放活期</option>
										<option value="P2073@@20170100-2">境内银行同业存放定期</option>
										<option value="P2074@@20170200-1">境内非银行同业存放活期</option>
										<option value="P2075@@20170200-2">境内非银行同业存放定期</option>
										<option value="P2076@@20170300-1">境外同业存放活期</option>
										<option value="P2077@@20170300-2">境外同业存放定期</option>
										<option value="P2079@@20180102">系统内地市级上存约期存款</option>
										<option value="P2081@@20180202">系统内县级上存约期存款</option>
										<option value="P2083@@20180302">系统内上存省联社存约期存款</option>
									</select>
									<input type="hidden" name="prdtName" id="prdtName" />
								</td>
								<td align="right">交易员：</td>
								<td>
									<input type="hidden" name="khjl" id="khjl" />
									<input type="text" name="empName" id="empName" readonly="readonly" />
									<input type="button" value="选择" class="red button" onclick="javascript:doSelEmp()" />
								</td>
<%--								<td align="right">科目号：</td>--%>
<%--								<td>--%>
<%--									<input type="text" name="kmh" id="kmh" />--%>
<%--								</td>--%>
<%--								<td align="right">产品名称：</td>--%>
<%--								<td><input type="text" name="prdtName" id="prdtName" /></td>--%>
<%--								<td align="right">产品编码：</td>--%>
<%--								<td><input type="text" name="orgnlbusntyp" id="orgnlbusntyp" /></td>--%>
							</tr>
<%--							<tr>--%>
<%--								<td align="right" width="25%">币种：</td>--%>
<%--								<td width="25%">--%>
<%--									<select style="width: 100" name="curNo" id="curNo">	</select>--%>
<%--								</td>--%>
							
<%--							</tr>--%>
							<tr>
								<td align="right">交易对手：</td>
								<td><input type="text" name="cusName" id="cusName" /></td>
								<td align="right">交易对手编号：</td>
								<td><input type="text" name="custNo" id="custNo" /></td>
							</tr>
						
								
							
							<tr>
							    
							    <td align="right">开始日：</td>
								<td><input type="text" name="startDate" id="startDate" readonly="readonly"/></td>
								<td align="right">到期日：</td>
								<td><input type="text" name="endDate" id="endDate" readonly="readonly" /></td>
							</tr>
							<tr>
								<td align="right">金额(元)：</td>
								<td colspan="1"><input type="text"  onblur="sameP(this)" onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="amt" id="amt" /></td>
								<td align="right">余额(元)：</td>
								<td colspan="1"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="bal" id="bal" /></td>
							</tr>
							<tr>
								<td align="right">利率(%)：</td>
								<td colspan="3"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="rate" id="rate" /></td>
							</tr>
<%--							<tr>--%>
<%--							    --%>
<%--							    <td align="right">计息天数：</td>--%>
<%--								<td colspan="3"><input type="text" name="days" maxlength="5"  onkeyup="value=value.replace(/[^\d\.]/g,'')" id="days" /></td>--%>
<%--							  	--%>
<%--							</tr>--%>
<%--							<tr>--%>
<%--							    --%>
<%--							</tr>--%>
			<tr>
				<td align="center" colspan="4">
					<input type="button" value="保&nbsp;&nbsp;存" class="red button" onclick="javascript:checkPo(this.form)" />
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
	
	function checkPo(FormName){
		var url = "<%=request.getContextPath()%>/JRSCYWDRTY_checkId.action";
		var acId=document.getElementById("acId").value;
		   new Ajax.Request( 
		        url, 
		         {  
		          method: 'post',   
		          parameters: {
		        	acId:acId,
		            t:new Date().getTime()
		            },
		          onSuccess: function(val){
		        	  if(val.responseText=='ok'){
		        		  doSave(FormName);
		        	  }else{
		        		  art.dialog({
			              	    title:'提示',
			          		    icon: 'error',
			          		    content: '此编号已被占用！',
			          		    ok: function () {
			          		    	document.getElementById("acId").select();
			          		    }
			           	 	});
		        	  }
			         }
		          });
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
<%--        if(!(isNull(FormName.kmh,"科目号"))) {--%>
<%--    		return false;			--%>
<%--        }--%>
        if(!(isNull(FormName.cusName,"交易对手"))) {
    		return false;			
        }
        if(!(isNull(FormName.custNo,"交易对手编号"))) {
    		return false;			
        }
        if(!(isNull(FormName.amt,"金额"))) {
    		return false;			
        }
        if(!(isNull(FormName.rate,"利率"))) {
    		return false;			
        }
        if(!(isNull(FormName.startDate,"开始日"))) {
    		return false;			
        }
        if(!(isNull(FormName.endDate,"结束日"))) {
    		return false;			
        }
        if(!(isNull(FormName.khjl,"交易员"))) {
    		return false;			
        }
        if(!(isNull(FormName.bal,"余额"))) {
            return false;			
    }
<%--        if(!(isNull(FormName.days,"天数"))) {--%>
<%--    		return false;			--%>
<%--        }--%>
    	var obj =document.getElementById("prdtNo");
    	//alert(obj.options[obj.selectedIndex].text);
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
	function ok(){
		  window.parent.location.reload();
	}
	function seled(id,value){
		var selectFlags = document.getElementsByName(id);
		for (var i=0; i<selectFlags.length; i++) {
			if(selectFlags[i].value==value){
				selectFlags[i].checked = true;
			}
		}
	}
	</script>
	
</html>

