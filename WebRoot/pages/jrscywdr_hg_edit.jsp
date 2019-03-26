<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.util.CommonFunctions"%>
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
		JrHg jrhg = (JrHg) request.getAttribute("jrhg");
		String acId = jrhg == null ? "" : jrhg.getAcId();
		System.out.println("jsp中acId===========" + acId);
	%>
		
		<form action="<%=request.getContextPath()%>/JRSCYWDRHG_update.action"  method="post" id="update" name="update" >	    
		<table  width="90%" align="center" border="0" class="table">
			                <tr>
								<td align="right">序号：</td>
								<td><%=jrhg.getAcId() == null ? "" : (jrhg.getAcId().indexOf("_")==-1?jrhg.getAcId():jrhg.getAcId().substring(0,jrhg.getAcId().indexOf("_")))%>
								<input type="hidden" name="acId" id="acId" value="<%=jrhg.getAcId() == null ? "" : jrhg.getAcId()%>"/>
								</td>
								<td align="right">
									机构：
								</td>
								<td >
									<div id='comboxWithTree1' style="width: 300px;"></div>
									<input type="hidden" name="brNo" id="brNo"	value="<%=jrhg.getBrNo() == null ? "" : jrhg.getBrNo()%>" />
									<input name="brName" id="brName" type="hidden"  value="<%=jrhg.getBrName() == null ? "" : jrhg.getBrName()%>[<%=jrhg.getBrNo() == null ? "" : jrhg.getBrNo()%>]" />
								</td>
							</tr>
							<tr>
								<td align="right">
									产品名称 ：
								</td>
								<td>
									<select name="prdtNo" id="prdtNo" >
										<option value="P1068@@11110100">买入返售债券</option>
										<option value="P1069@@11110200">买入返售信贷资产</option>
										<option value="P1070@@11110300">买入返售票据</option>
										<option value="P1071@@11110400">买入返售金融资产-其它</option>
										<option value="P2099@@21110100">卖出回购债券款</option>
										<option value="P2100@@21110200">卖出回购贷款</option>
										<option value="P2101@@21110300">卖出回购票据款</option>
										<option value="P2102@@21110400">其他卖出回购金融资产款项</option>
									</select>
									<input type="hidden" name="prdtName" id="prdtName" />
								</td>
								<td align="right">交易员：</td>
								<td>
									<input type="hidden" name="khjl" id="khjl" value="<%=jrhg.getKhjl()==null?"":jrhg.getKhjl()%>"/>
									<input type="text" name="empName" id="empName" readonly="readonly"  value="<%=jrhg.getEmpName()==null?"":jrhg.getEmpName()%>"/>
									<input type="button" value="选择" class="red button" onclick="javascript:doSelEmp()" />
								</td>
							</tr>
							<tr>
								<td align="right">交易对手：</td>
								<td><input type="text" name="cusName" id="cusName"  value="<%=jrhg.getCusName() == null ? "" : jrhg.getCusName()%>"/></td>
								<td align="right">交易对手编号：</td>
								<td><input type="text" name="custNo" id="custNo" value="<%=jrhg.getCustNo() == null ? "" : jrhg.getCustNo()%>"/></td>
							</tr>
							<tr>
							    <td align="right">首期交割日：</td>
								<td><input type="text" name="sqjgr" id="sqjgr"  readonly value="<%=jrhg.getSqjgr() == null ? "" : jrhg.getSqjgr()%>" /></td>
								<td align="right">到期交割日：</td>
								<td><input type="text" name="dqjgr" id="dqjgr" readonly value="<%=jrhg.getDqjgr() == null ? "" : jrhg.getDqjgr()%>" /></td>
							</tr>
							<tr>
							    <td align="right">票号：</td>
								<td><input type="text" name="ph" id="ph"  value="<%=jrhg.getPh() == null ? "" : jrhg.getPh()%>"/></td>
							    <td align="right">票面金额(元)：</td>
								<td><input type="text" name="qmje" id="qmje"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=jrhg.getQmje() == null ? "" : CommonFunctions.doubleFormat(jrhg.getQmje(),2)%>"/></td>
							</tr>
							<tr>
								<td align="right">成交金额(元)：</td>
								<td colspan="1"><input type="text" onblur="sameP(this)" name="cjje" id="cjje" onkeyup="value=value.replace(/[^\d\.]/g,'')"  value="<%=jrhg.getCjje() == null ? "" : CommonFunctions.doubleFormat(jrhg.getCjje(),2)%>"/></td>
								<td align="right">余额(元)：</td>
								<td colspan="1"><input type="text" name="bal" id="bal" onkeyup="value=value.replace(/[^\d\.]/g,'')"  value="<%=jrhg.getBal() == null ? "" : CommonFunctions.doubleFormat(jrhg.getBal(),2)%>"/></td>
							</tr>
							<tr>
								<td align="right">回购利率(%)：</td>
								<td colspan="1"><input type="text" name="hglv" id="hglv"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=jrhg.getHglv() == null ? "" :  CommonFunctions.doubleFormat(jrhg.getHglv()*100,2)%>"/></td>
							    <td align="right">计息天数：</td>
								<td colspan="1">
								<%=jrhg.getJyts() == null ? "" : jrhg.getJyts()%>
								</td>
							   
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
	    var minDate1 = '<%=CommonFunctions.GetDBSysDate()%>';
	    var maxDate2 = '<%=CommonFunctions.GetDBSysDate()%>';
	        j("#sqjgr").datepicker(
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
				    j('#dqjgr').datepicker('option', 'minDate',new Date(dateText.replace('-',',')));}
			});

			j("#dqjgr").datepicker(
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
				    j('#sqjgr').datepicker('option', 'maxDate', new Date(dateText.replace('-',',')));}
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
           if(!(isNull(FormName.cusName,"交易对手"))) {
       		return false;			
           }
           if(!(isNull(FormName.custNo,"交易对手编号"))) {
       		return false;			
           }
           if(!(isNull(FormName.ph,"票号"))) {
       		return false;			
           }
           if(!(isNull(FormName.qmje,"票面金额"))) {
       		return false;			
           }
           if(!(isNull(FormName.hglv,"回购利率(年)"))) {
       		return false;			
           }
           if(!(isNull(FormName.cjje,"成交金额"))) {
       		return false;			
           }
           if(!(isNull(FormName.sqjgr,"首期交割日"))) {
       		return false;			
           }
           if(!(isNull(FormName.dqjgr,"到期交割日"))) {
       		return false;			
           }
<%--            if(!(isNull(FormName.jyts,"计息天数"))) {--%>
<%--         		return false;			--%>
<%--             }--%>
           if(!(isNull(FormName.khjl,"交易员"))) {
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
			//alert(selectFlags.options[i].value);
			if(selectFlags.options[i].value==value){
				//alert(2);
				selectFlags.selectedIndex=i;
				return ;
			}
		}
	}
	seled('prdtNo','<%=jrhg.getPrdtNo()==null?"":jrhg.getPrdtNo()%>@@<%=jrhg.getKmh()==null?"":jrhg.getKmh()%>');
		</script>
</html>

