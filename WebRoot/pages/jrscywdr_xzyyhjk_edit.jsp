<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.*,com.dhcc.ftp.util.*" errorPage="" %>
<html>
	<head>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css"/>
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonExt2.0.2.jsp" /><!-- 需放到prototype.js后面 -->
		<jsp:include page="commonDatePicker.jsp" />
		<script type="text/javascript" src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'></script>
		<title>金融市场业务导入</title>
	</head>
	<body>
	<%
	JrXzyyhjk jrxzyyhjk=(JrXzyyhjk)request.getAttribute("jrxzyyhjk");
	String acId=jrxzyyhjk==null?"":jrxzyyhjk.getAcId();
	System.out.println("jsp中acId==========="+acId);
	
	 %>
		<form action="<%=request.getContextPath()%>/JRSCYWDRXZYYHJK_update.action"  method="post" id="update" name="update" >	    
		<table  width="80%" align="center" border="0" class="table">
			                <tr>
								<td align="right" >序号:</td>
								<td ><%=jrxzyyhjk.getAcId()==null?"":(jrxzyyhjk.getAcId().indexOf("_")==-1?jrxzyyhjk.getAcId():jrxzyyhjk.getAcId().substring(0,jrxzyyhjk.getAcId().indexOf("_"))) %><input type="hidden" name="acId" id="acId" value="<%=jrxzyyhjk.getAcId()==null?"":(jrxzyyhjk.getAcId().indexOf("_")==-1?jrxzyyhjk.getAcId():jrxzyyhjk.getAcId().substring(0,jrxzyyhjk.getAcId().indexOf("_"))) %>"/></td>
								<td align="right" >机构名称：</td>
								<td >
									<div id='comboxWithTree1' style="width: 300px;"></div>
									<input name="brNo" id="brNo" type="hidden" value="<%=jrxzyyhjk.getBrNo()==null?"":jrxzyyhjk.getBrNo() %>" />
									<input name="brName" id="brName" type="hidden"  value="<%=jrxzyyhjk.getBrName() + "[" + jrxzyyhjk.getBrNo() + "]"%>" />
								
								</td>
							</tr>
							<tr>
							    <td align="right">产品名称：</td>
								<td>
									<select  name="prdtNo" id="prdtNo">
									    <option value="P2069@@20150100">向中央银行借款</option>
									    <option value="P2070@@20150200">支农再贷款</option>
									    
									</select> 
									<input type="hidden" name="prdtName" id="prdtName" value="<%=jrxzyyhjk.getPrdtName()==null?"":jrxzyyhjk.getPrdtName()%>"/>
<%--								<input type="text" name="prdtNo" id="prdtNo" />--%>
								</td>
								<td align="right">交易员：</td>
							    <td>
									<input type="hidden" name="operator" id="operator" value="<%=jrxzyyhjk.getOperator()==null?"":jrxzyyhjk.getOperator()%>"/>
									<input type="text" name="operatorName" id="operatorName" readonly="readonly" value="<%=jrxzyyhjk.getOperatorName()==null?"":jrxzyyhjk.getOperatorName()%>"/>
									<input type="button" value="选择" class="red button" onclick="javascript:doSelEmp()" />
								</td>
							</tr>
							<tr>
								<td align="right">交易对手名称：</td>
								<td><input type="text" name="cusName" id="cusName" value="<%=jrxzyyhjk.getCusName()==null?"":jrxzyyhjk.getCusName()%>"/></td>
								<td align="right">交易对手编号：</td>
								<td><input type="text" name="custNo" id="custNo"  value="<%=jrxzyyhjk.getCustNo()==null?"":jrxzyyhjk.getCustNo()%>"/></td>
							</tr>
							<tr>
							    <td align="right">开始日期：</td>
								<td><input type="text" name="startDate" id="startDate" readonly="readonly" value="<%=jrxzyyhjk.getStartDate()==null?"":jrxzyyhjk.getStartDate()%>"/></td>
								<td align="right">到期日期：</td>
								<td><input type="text" name="endDate" id="endDate" readonly="readonly" value="<%=jrxzyyhjk.getEndDate()==null?"":jrxzyyhjk.getEndDate()%>"/></td>
							</tr>
							<tr>
								<td align="right">金额(元)：</td>
								<td><input type="text" onblur="sameP(this)" name="amt" id="amt" value="<%=jrxzyyhjk.getAmt()==null?"":CommonFunctions.doubleFormat(jrxzyyhjk.getAmt(),2)%>" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
							<td align="right">余额(元)：</td>
<td colspan="1"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  value="<%=jrxzyyhjk.getBal() == null ? "" : CommonFunctions.doubleFormat(jrxzyyhjk.getBal(),2)%>" name="bal" id="bal" /></td>
		</tr>
							<tr>
								<td align="right">利率(%)：</td>
								<td><input type="text" name="rate" id="rate" value="<%=jrxzyyhjk.getRate()==null?"":CommonFunctions.doubleFormat(jrxzyyhjk.getRate()*100,2)%>" onkeyup="value=value.replace(/[^\d\.]/g,'')"/></td>
								<td align="right">计息天数：</td>
								<td>
								<%=jrxzyyhjk.getDays()==null?"":jrxzyyhjk.getDays()%>
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
    function doSelEmp(){
        
    	art.dialog.open('<%=request.getContextPath()%>/pages/selEmpS.jsp?Rnd='+Math.random(), {
		    title: '选择',
		    width: 800,
		    height:400,
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
    function doSave(FormName) {
    	if(!(isNull(FormName.acId,"序号"))) {
    		return false;			
        }
        if(!(isNull(FormName.brNo,"机构号"))) {
    		return false;			
        }
        if(!(isNull(FormName.operator,"交易员"))) {
    		return false;			
        }
        if(!(isNull(FormName.cusName,"交易对手"))) {
    		return false;			
        }
        if(!(isNull(FormName.custNo,"交易对手编号"))) {
    		return false;			
        }
        if(!(isNull(FormName.startDate,"开始日期"))) {
    		return false;			
        }
        if(!(isNull(FormName.endDate,"到期日期"))) {
    		return false;			
        }
        if(!(isNull(FormName.prdtNo,"产品名称"))) {
    		return false;			
        }
<%--        if(!(isNull(FormName.days,"计息天数"))) {--%>
<%--    		return false;			--%>
<%--        }--%>
        if(!(isNull(FormName.amt,"金额"))) {
    		return false;			
        }
        if(!(isNull(FormName.rate,"利率"))) {
    		return false;			
        }
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
	seled('prdtNo','<%=jrxzyyhjk.getPrdtNo()==null?"":jrxzyyhjk.getPrdtNo()%>@@<%=jrxzyyhjk.getKmh()==null?"":jrxzyyhjk.getKmh()%>');	
<%--	fillSelectLast('prdtNo','<%=request.getContextPath()%>/fillSelect_getPrdtName.action?businessNo=YW206','<%=jrxzyyhjk.getPrdtNo()==null?"":jrxzyyhjk.getPrdtNo()%>');--%>
<%--	fillSelectLast('curNo','<%=request.getContextPath()%>/queryCurrency.action','<%=jrxzyyhjk.getCurNo()==null?"":jrxzyyhjk.getCurNo() %>');--%>
	</script>
</html>

