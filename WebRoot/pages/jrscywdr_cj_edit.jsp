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
	JrCj jrcj=(JrCj)request.getAttribute("jrcj");
	String acId=jrcj==null?"":jrcj.getAcId();
	System.out.println("jsp中acId==========="+acId);
	
	 %>
		
		<form action="<%=request.getContextPath()%>/JRSCYWDRCJ_update.action"  method="post" id="update" name="update" >	    
		<table  width="80%" align="center" border="0" class="table">
			                <tr>
								<td align="right">序号：</td>
								<td><%=jrcj.getAcId() == null ? "" : (jrcj.getAcId().indexOf("_")==-1?jrcj.getAcId():jrcj.getAcId().substring(0,jrcj.getAcId().indexOf("_")))%>
								<input type="hidden" name="acId" id="acId" value="<%=jrcj.getAcId() == null ? "" : jrcj.getAcId()%>"/>
								<td align="right" >机构：</td>
								<td >
										<div id='comboxWithTree1' style="width: 300px;"></div>
									<input name="brNo" id="brNo" type="hidden" value="<%=jrcj.getBrNo()==null?"":jrcj.getBrNo() %>" />
									<input name="brName" id="brName" type="hidden"  value="<%=jrcj.getBrName() + "[" + jrcj.getBrNo() + "]"%>" />
								
								</td>
							</tr>
							<tr>
								
								<td align="right">产品名称：</td>
								<td>
									<select name="prdtNo" id="prdtNo" >
										<option value="P1031@@10130100">拆放境内银行同业</option>
										<option value="P1032@@10130200">拆放境内非银行同业</option>
										<option value="P1033@@10130300">拆放境外同业</option>
										<option value="P1034@@10140100">拆放省内</option>
										<option value="P1035@@10140200">拆放省外</option>
										<option value="P2084@@20190101">国有商业银行拆入</option>
										<option value="P2085@@20190102">政策性银行拆入</option>
										<option value="P2086@@20190103">股份制银行拆入</option>
										<option value="P2087@@20190104">金融性公司拆入</option>
										<option value="P2088@@20190105">银行同业拆入-其它</option>
										<option value="P2089@@20190200">非银行同业拆入</option>
										<option value="P2090@@20190300">境外同业拆入</option>
										<option value="P2091@@20200000">系统内拆入</option>
									</select>
									<input type="hidden" name="prdtName" id="prdtName" />
								</td>
								<td align="right">交易员：</td>
								<td>
									<input type="hidden" name="khjl" id="khjl" value="<%=jrcj.getKhjl()==null?"":jrcj.getKhjl()%>"/>
									<input type="text" name="empName" id="empName" readonly="readonly"  value="<%=jrcj.getEmpName()==null?"":jrcj.getEmpName()%>"/>
									<input type="button" value="选择" class="red button" onclick="javascript:doSelEmp()" />
								</td>
<%--								<td align="right">科目号：</td>--%>
<%--								<td><input type="text" name="kmh" id="kmh" value="<%=jrcj.getKmh()==null?"":jrcj.getKmh()%>" /></td>--%>
							</tr>
							<tr>
								<td align="right">交易对手：</td>
								<td><input type="text" name="cusName" id="cusName"  value="<%=jrcj.getCusName()==null?"":jrcj.getCusName()%>"/></td>
								<td align="right">交易对手编号：</td>
								<td><input type="text" name="custNo" id="custNo" value="<%=jrcj.getCustNo()==null?"":jrcj.getCustNo()%>"/></td>
							</tr>
							<tr>
							    
							    <td align="right">首期交割日：</td>
								<td><input type="text" name="qxDate" id="qxDate" readonly="readonly" value="<%=jrcj.getQxDate()==null?"":jrcj.getQxDate()%>" /></td>
								<td align="right">到期交割日：</td>
								<td><input type="text" name="dqDate" id="dqDate" readonly="readonly" value="<%=jrcj.getDqDate()==null?"":jrcj.getDqDate()%>" /></td>
							</tr>
							<tr>
								<td align="right">拆借金额(元)：</td>
								<td><input type="text"  onblur="sameP(this)" onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="chaijieAmt" id="chaijieAmt" value="<%=jrcj.getChaijieAmt()==null?0:CommonFunctions.doubleFormat(jrcj.getChaijieAmt(),2)%>" /></td>
								<td align="right">余额(元)：</td>
								<td colspan="1"><input type="text" name="bal"   onkeyup="value=value.replace(/[^\d\.]/g,'')" id="bal" value="<%=jrcj.getBal()==null?"":CommonFunctions.doubleFormat(jrcj.getBal(),2)%>"/></td>
							</tr>
							<tr>
								<td align="right">拆借利率(%)：</td>
								<td colspan="1"><input type="text" name="rate"   onkeyup="value=value.replace(/[^\d\.]/g,'')" id="rate" value="<%=jrcj.getRate()==null?"":CommonFunctions.doubleFormat(jrcj.getRate()*100,2)%>"/></td>
								<td align="right">计息天数：</td>
								<td colspan="1">
									<%=jrcj.getJxts()==null?"":jrcj.getJxts()%>
								</td>
								
							</tr>
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
	    var minDate1 = '<%=CommonFunctions.GetDBSysDate()%>';
	    var maxDate2 = '<%=CommonFunctions.GetDBSysDate()%>';
	        j("#qxDate").datepicker(
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
				    j('#dqDate').datepicker('option', 'minDate',new Date(dateText.replace('-',',')));}
			});

			j("#dqDate").datepicker(
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
				    j('#qxDate').datepicker('option', 'maxDate', new Date(dateText.replace('-',',')));}
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
      
         if(!(isNull(FormName.custNo,"交易对手编号"))) {
     		return false;			
         }
         if(!(isNull(FormName.cusName,"交易对手"))) {
     		return false;			
         }
        
         if(!(isNull(FormName.rate,"利率"))) {
     		return false;			
         }
        
         if(!(isNull(FormName.qxDate,"首期交割日"))) {
     		return false;			
         }
         if(!(isNull(FormName.dqDate,"到期交割日"))) {
     		return false;			
         }
<%--         if(!(isNull(FormName.jxts,"计息天数"))) {--%>
<%--     		return false;			--%>
<%--         }--%>
         if(!(isNull(FormName.khjl,"交易员"))) {
     		return false;			
         }
         if(!(isNull(FormName.chaijieAmt,"金额"))) {
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
		var selectFlags = document.getElementById(id);
		for (var i=0; i<selectFlags.options.length; i++) {
			if(selectFlags.options[i].value==value){
				selectFlags.selectedIndex=i;
				return ;
			}
		}
	}
	seled('prdtNo','<%=jrcj.getPrdtNo()==null?"":jrcj.getPrdtNo()%>@@<%=jrcj.getKmh()==null?"":jrcj.getKmh()%>');	
	</script>
</html>

