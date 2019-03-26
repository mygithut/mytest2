<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.*,com.dhcc.ftp.util.*" errorPage="" %>

<html>
	<head>
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonExt2.0.2.jsp" /><!-- 需放到prototype.js后面 -->
		<jsp:include page="commonDatePicker.jsp" />
		<script type="text/javascript" src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'></script>
		
	
		
		<title>金融市场业务导入</title>
	</head>
	<body>
	<%
		JrXqmm jrxqmm = (JrXqmm) request.getAttribute("jrXqmm");
		String acId = jrxqmm == null ? "" : jrxqmm.getAcId();
		System.out.println("jsp中acId===========" + acId);
	%>
		
		<form action="<%=request.getContextPath()%>/JRSCYWDRXQMM_update.action"  method="post" id="update" name="update" >	    
		<table  width="90%" align="center" border="0" class="table">
			                <tr>
								<td align="right">序号：</td>
								<td><input type="hidden" name="acId" id="acId" value="<%=jrxqmm.getAcId() == null ? "" : jrxqmm.getAcId()%>"/><%=jrxqmm.getAcId() == null ? "" : (jrxqmm.getAcId().indexOf("_")==-1?jrxqmm.getAcId():jrxqmm.getAcId().substring(0,jrxqmm.getAcId().indexOf("_")))%></td>
								<td align="right" >机构：</td>
								<td >
									<div id='comboxWithTree1' style="width: 300px;"></div>
									<input name="brNo" id="brNo" type="hidden" value="<%=jrxqmm.getBrNo()==null?"":jrxqmm.getBrNo() %>" />
									<input name="brName" id="brName" type="hidden"  value="<%=jrxqmm.getBrName() + "[" + jrxqmm.getBrNo() + "]"%>" />
								
								</td>
							</tr>
							<tr>
								<td align="right">交易对手：</td>
								<td><input type="text" name="cusName" id="cusName"  value="<%=jrxqmm.getCusName() == null ? "" : jrxqmm.getCusName()%>"/></td>
								<td align="right">交易对手编号：</td>
								<td><input type="text" name="custNo" id="custNo" value="<%=jrxqmm.getCustNo() == null ? "" : jrxqmm.getCustNo()%>"/></td>
							</tr>
							<tr>
								<td align="right">产品名称：</td>
								<td>
									<select name="prdtNo" id="prdtNo" >
										<option value="P1064@@11010101">交易性金融资产短期国债</option>
										<option value="P1065@@11010102">交易性金融资产短期金融债</option>
										<option value="P1067@@11010300">交易性金融资产其他</option>
										<option value="P1204@@15030101">可供出售金融资产-国债</option>
										<option value="P1205@@15030102">可供出售金融资产-央行债券</option>
										<option value="P1206@@15030103">可供出售金融资产-政策性银行债</option>
										<option value="P1207@@15030104">可供出售金融资产-中央公共企业债</option>
										<option value="P1208@@15030105">可供出售金融资产-商业银行债</option>
										<option value="P1209@@15030106">可供出售金融资产-地方政府债</option>
										<option value="P1210@@15030107">可供出售金融资产-其它</option>
										<option value="P2096@@21010101">以公允价值计量且其变动计入当期损益的金融负债</option>
										<option value="P2097@@21010102">其它交易性金融负债</option>
									</select>
									<input type="hidden" name="prdtName" id="prdtName" />
								</td>
								<td align="right">交易员：</td>
								<td colspan="1">
									<input type="hidden" name="khjl" id="khjl" value="<%=jrxqmm.getKhjl()==null?"":jrxqmm.getKhjl()%>"/>
									<input type="text" name="empName" id="empName" readonly="readonly"  value="<%=jrxqmm.getEmpName()==null?"":jrxqmm.getEmpName()%>"/>
									<input type="button" value="选择" class="red button" onclick="javascript:doSelEmp()" />
								</td>
							</tr>
							<tr>
								<td align="right">买入日期：</td>
								<td><input type="text" name="buyDate" readonly="readonly" id="buyDate" value="<%=jrxqmm.getBuyDate() == null ? "" : jrxqmm.getBuyDate()%>" /></td>
								<td align="right">成交金额(元)：</td>
								<td><input type="text"  onblur="sameP(this)" onkeyup="value=value.replace(/[^\d\.]/g,'')" name="fullpriceAmt" id="fullpriceAmt" value="<%=jrxqmm.getFullpriceAmt() == null ? "" :CommonFunctions.doubleFormat(jrxqmm.getFullpriceAmt(),2)%>"/></td>
							</tr>
							<tr>
							<td align="right">余额(元)：</td>
<td colspan="1"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  value="<%=jrxqmm.getBal() == null ? "" : CommonFunctions.doubleFormat(jrxqmm.getBal(),2)%>" name="bal" id="bal" /></td>
								<td align="right">票面金额(元)：</td>
								<td><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="bondAmt" id="bondAmt" value="<%=jrxqmm.getBondAmt() == null ? "" : CommonFunctions.doubleFormat(jrxqmm.getBondAmt(),2)%>" /></td>
							</tr>
							<tr>
							    <td align="right">票面利率(%)：</td>
								<td colspan="3"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="rate" id="rate" value="<%=jrxqmm.getRate() == null ? "" :CommonFunctions.doubleFormat( jrxqmm.getRate()*100,2)%>" /></td>
							</tr>
							
			<tr>
				<td align="center" colspan="4">
					<input type="button" value="保&nbsp;&nbsp;存" class="red button" onclick="javascript:doSave(this.form)" />
				</td>
			</tr>
		</table>
	</form>
	</body>
	<script type="text/javascript">
	/*起始日期-结束日期，日期面板生产js*/
	j(function() {
	        j("#buyDate").datepicker(
				{
					changeMonth: true,
					changeYear: true,
					dateFormat: 'yymmdd',
					showOn: 'button', 
					buttonImage: '<%=request.getContextPath()%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
					buttonImageOnly: true
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
<%--         if(!(isNull(FormName.kmh,"科目号"))) {--%>
<%--     		return false;			--%>
<%--         }--%>
         if(!(isNull(FormName.prdtNo,"产品名称"))) {
     		return false;			
         }
         if(!(isNull(FormName.custNo,"交易对手编号"))) {
     		return false;			
         }
         if(!(isNull(FormName.cusName,"交易对手"))) {
     		return false;			
         }
         if(!(isNull(FormName.buyDate,"买入日期"))) {
     		return false;			
         }
         if(!(isNull(FormName.fullpriceAmt,"成交金额"))) {
     		return false;			
         }
         if(!(isNull(FormName.rate,"票面利率"))) {
     		return false;			
         }
         if(!(isNull(FormName.bondAmt,"票面金额"))) {
     		return false;			
         }
        
         if(!(isNull(FormName.khjl,"交易员"))) {
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
	seled('prdtNo','<%=jrxqmm.getPrdtNo()==null?"":jrxqmm.getPrdtNo()%>@@<%=jrxqmm.getKmh()==null?"":jrxqmm.getKmh()%>');	
	</script>
</html>

