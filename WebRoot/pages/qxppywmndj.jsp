<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page
	import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst"%>
<html>
	<head>
		<title>期限匹配-期限匹配业务模拟定价</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonExt2.0.2.jsp" /><!-- 需放到prototype.js后面 -->
		<jsp:include page="commonDatePicker.jsp" />
	    <script type="text/javascript"
			src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg_qxppywmndj.js'></script>
	</head>
	<body>
	<div class="cr_header">
		当前位置：期限匹配->期限匹配业务模拟定价
	</div>
	<form name="form1" action="" method="post">
	</form>
	<table width="90%" align="center" class="table">
		<tr>
			<td class="middle_header" colspan="6">
				<font
						style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">业务模拟新增</font>
			</td>
		</tr>
		<tr>
			<td width="10%" align="right">
				机构名称：
			</td>
			<td colspan="3">
				<div id='comboxWithTree1' name="comboxWithTree1" onkeyup="getMethodNo()"></div>
				<input name="brName" id="brName" type="hidden"
					   value="<%=((TelMst) request.getSession().getAttribute("userBean")).getBrMst().getBrName()%>[<%=((TelMst) request.getSession().getAttribute("userBean")).getBrMst().getBrNo()%>]"/>
				<input name="brNo" id="brNo" type="hidden"
					   value="<%=((TelMst) request.getSession().getAttribute("userBean")).getBrMst().getBrNo()%>"/>
				<input name="manageLvl" id="manageLvl" type="hidden"
					   value="<%=((TelMst) request.getSession().getAttribute("userBean")).getBrMst().getManageLvl()%>"/>
			</td>
			<td width="10%" align="right">
				选择币种：
			</td>
			<td>
				<select style="width: 120" name="curNo" id="curNo">
				</select>
			</td>

		</tr>
		<tr>
			<td width="10%" align="right">
				业务类型：
			</td>
			<td width="22%">
				<select name="businessNo" id="businessNo"
						onchange="getPrdtName(this.id)">
				</select>
			</td>
			<td width="10%" align="right">
				产品名称：
			</td>
			<td>
				<select name="productNo" id="productNo" disabled="disabled" onchange="getMethodNo()">
					<option value="">
						先选择业务类型
					</option>
				</select>
			</td>
			<td width="10%" align="right">
				金额：
			</td>
			<td width="22%">
				<input type="text" name="amt" id="amt" onkeyup="value=value.replace(/[^\d\.]/g,'')" maxlength="10"
					   value="" size="10" onblur="isFloat(this,'金额')"/>
			</td>
		</tr>
		<tr>
			<td width="10%" align="right">
				利率(%)：
			</td>
			<td>
				<input type="text" name="rate" id="rate" onkeyup="value=value.replace(/[^\d\.]/g,'')" value="" size="10"
					   onblur="isFloat(this,'利率(%)')"/>
			</td>
			<td width="10%" align="right" height="15">
				开始日期：
			</td>
			<td>
				<input type="text" id="opnDate" name="opnDate" maxlength="10"
					   value="<%=CommonFunctions.GetCurrentDateInLong()%>" size="10"/>
			</td>
			<td width="10%" align="right">
				<font id="mtrDateFont">到期日期：</font>
			</td>
			<td id="mtrDateTd">
				<input type="text" id="mtrDate" name="mtrDate" maxlength="10"
					   value="<%=CommonFunctions.dateModifyY(String.valueOf(CommonFunctions.GetCurrentDateInLong()),1)%>"
					   size="10"/>
			</td>
		</tr>
		<tr id="hkzq" style="display: none">
			<td width="10%" align="right">
				还款周期(天)：
			</td>
			<td colspan="5">
				<input type="text" name="hkTerm" id="hkTerm" value="" size="10" onblur="isFloat(this,'还款周期(天)')"/>
			</td>
		</tr>
		<tr style="display:none" id="zjzq">
			<td width="10%" align="right">
				折旧周期(天)：
			</td>
			<td>
				<input type="text" name="zjTerm" id="zjTerm" value="" onkeyup="value=value.replace(/[^\d\.]/g,'')"
					   size="10" onblur="isFloat(this,'折旧周期(天)')"/>
			</td>
			<td width="10%" align="right">
				残值率(%)：
			</td>
			<td colspan="3">
				<input type="text" name="scrapValueRate" id="scrapValueRate" value="" size="10"
					   onblur="isFloat(this,'残值率(%)')"/>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="6">
				<input name="query" class="button" type="button" id="query"
					   height="20" onClick=doAdd(); value="新&nbsp;&nbsp;增"/>
			</td>
		</tr>

		<tr>
			<td align="center" colspan="6" style="background-color: white">
				<iframe src="<%=request.getContextPath()%>/pages/qxppywmndj_list.jsp" id="downframe" name="downframe"
						width="100%"
						height="300" frameborder="no" border="0" marginwidth="0"
						marginheight="0" scrolling="auto" allowtransparency="yes"
						align="left"></iframe>
			</td>
		</tr>
	</table>
	</body>
	<script type="text/javascript" language="javascript">
	jQuery(function(){// dom元素加载完毕
		jQuery(".table tr:even").addClass("tr-bg1");
		jQuery(".table tr:odd").addClass("tr-bg2");
	});	
	fillSelectLast('curNo', '<%=request.getContextPath()%>/queryCurrency.action','01');
    fillSelect('businessNo', '<%=request.getContextPath()%>/fillSelect_getBusinessName.action');

	var img = "";
    /*起始日期-结束日期，日期面板生产js*/
    j(function() {
        //在 JavaScript 中，month 要比实际的月份数字小 1,也就是用 0 表示 1 月，用 1 表示 2 月……
        var minDate1 = '<%=CommonFunctions.GetCurrentDateInLong()%>';
        var maxDate2 = '<%=CommonFunctions.dateModifyY(String.valueOf(CommonFunctions.GetCurrentDateInLong()),1)%>';
        j("#opnDate").datepicker(
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
    			    j('#mtrDate').datepicker('option', 'minDate',new Date(dateText.replace('-',',')));}
    		});

    		j("#mtrDate").datepicker(
    			{
    				changeMonth: true,
    				changeYear: true,
    				dateFormat: 'yymmdd',
    				showOn: 'button', 
    				buttonImage: 'pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
    				buttonImageOnly: true,
    				buttonImageId: 'mtrDateImg',
    				minDate: new Date(minDate1.substring(0,4),parseFloat(minDate1.substring(4,6))-1,minDate1.substring(6,8)),
    			    onSelect: function(dateText, inst) {
    				dateText = dateText.substring(0,4)+'-'+dateText.substring(4,6)+'-'+dateText.substring(6,8);
    			    j('#opnDate').datepicker('option', 'maxDate', new Date(dateText.replace('-',',')));}
    		});
    	});
    function doAdd(){
    	var table = window.frames.downframe.document.getElementById('table1');
    	if($("manageLvl").value == '4') {
            alert("请选择县联社及以下的机构！");
            return;
 	    }
 	    if($("curNo").value == '') {
            alert("请选择币种！");
            curNo.focus();
            return;
 	    }
 	    if($("businessNo").value == '') {
            alert("请选择业务类型！");
            return;
 	    }
 	    if($("productNo").value == '') {
            alert("请选择产品！");
            return;
 	    }
	    if($("amt").value == '') {
	        alert("请输入金额！");
	        $("amt").focus();
	        return;
		}
	    if($("rate").value == '') {
	        alert("请输入利率！");
	        $("rate").focus();
	        return;
		}
	    if($("hkzq").style.display=='table-row') {
		    if($("hkTerm").value == '') {
			    alert("请输入还款周期！");
			    $("hkTerm").focus();
			    return;
		    }else {
		    	table = window.frames.downframe.document.getElementById('table2');
		    }
	       
		}
	    if($("zjzq").style.display=='table-row') {
		    if($("zjTerm").value == '') {
			    alert("请输入折旧周期！");
			    $("zjTerm").focus();
			    return;
		    }else if($("scrapValueRate").value == '') {
			    alert("请输入残值率！");
			    $("scrapValueRate").focus();
			    return;
		    }else {
		    	table = window.frames.downframe.document.getElementById('table3');
		    }
	       
		}
	    if(table.style.display == 'none') 
		    table.style.display = 'block';
	    var price = window.frames.downframe.document.getElementById('price');//定价按钮
		if(price.style.display == 'none') 
			price.style.display = 'block';
 	    addRow(table);
	}
  //添加行
	function addRow(table){
		var lastRow = table.rows[1];
		var newRow = lastRow.cloneNode(true);

		newRow.style.display = "";
		table.tBodies[0].appendChild(newRow);
		newRow.cells[0].innerHTML=jQuery('input[name="comboxname"]')[0].value+
		"<input name=\"brNo\" id=\"brNo\" type=\"hidden\" value=\""+$('brNo').value+"\"/>"+
		"<input name=\"brName\" id=\"brName\" type=\"hidden\" value=\""+jQuery('input[name="comboxname"]')[0].value+"\"/>"+
		"<input name=\"manageLvl\" id=\"manageLvl\" type=\"hidden\" value=\""+$('manageLvl').value+"\"/>";
		newRow.cells[1].innerHTML=$('businessNo').options[$('businessNo').selectedIndex].text+
	    "<input name=\"businessName\" id=\"bustinessName\" type=\"hidden\" value=\""+$('businessNo').options[$('businessNo').selectedIndex].text+"\"/>"+
	    "<input name=\"businessNo\" id=\"businessNo\" type=\"hidden\" value=\""+$('businessNo').value+"\"/>";
	    newRow.cells[2].innerHTML=$('productNo').options[$('productNo').selectedIndex].text+
	    "<input name=\"productName\" id=\"productName\" type=\"hidden\" value=\""+$('productNo').options[$('productNo').selectedIndex].text+"\"/>"+
	    "<input name=\"productNo\" id=\"productNo\" type=\"hidden\" value=\""+$('productNo').value+"\"/>";
	    newRow.cells[3].innerHTML=$('curNo').options[$('curNo').selectedIndex].text+
	    "<input name=\"curName\" id=\"curName\" type=\"hidden\" value=\""+$('curNo').options[$('curNo').selectedIndex].text+"\"/>"+
	    "<input name=\"curNo\" id=\"curNo\" type=\"hidden\" value=\""+$('curNo').value+"\"/>";
	    newRow.cells[4].innerHTML=$('opnDate').value+
	    "<input name=\"opnDate\" id=\"opnDate\" type=\"hidden\" value=\""+$('opnDate').value+"\"/>";
		newRow.cells[5].innerHTML=$('mtrDate').value+
	    "<input name=\"mtrDate\" id=\"mtrDate\" type=\"hidden\" value=\""+$('mtrDate').value+"\"/>";
	    newRow.cells[6].innerHTML=transformNumToMoney($('amt').value)+
	    "<input name=\"amt\" id=\"amt\" type=\"hidden\" value=\""+$('amt').value+"\"/>";
	    newRow.cells[7].innerHTML=$('rate').value+
	    "<input name=\"rate\" id=\"rate\" type=\"hidden\" value=\""+$('rate').value+"\"/>";
		if(table.id=='table1'){
		newRow.cells[8].innerHTML="<input class=\"button\" type=\"button\" height=\"20\" onClick=deleteRow(this); value=\"删除\" />"+
	    "<input name=\"hkTerm\" id=\"hkTerm\" type=\"hidden\" value=\"/\"/>"+
	    "<input name=\"zjTerm\" id=\"zjTerm\" type=\"hidden\" value=\"/\"/>"+
	    "<input name=\"scrapValueRate\" id=\"scrapValueRate\" type=\"hidden\" value=\"/\"/>";
		}else if(table.id =='table2'){
			newRow.cells[8].innerHTML=$('hkTerm').value;
			newRow.cells[9].innerHTML="<input class=\"button\" type=\"button\" height=\"20\" onClick=deleteRow(this); value=\"删除\" />"+
		    "<input name=\"hkTerm\" id=\"hkTerm\" type=\"hidden\" value=\""+$('hkTerm').value+"\"/>"+
		    "<input name=\"zjTerm\" id=\"zjTerm\" type=\"hidden\" value=\"/\"/>"+
		    "<input name=\"scrapValueRate\" id=\"scrapValueRate\" type=\"hidden\" value=\"/\"/>";
		}else if(table.id =='table3'){
			newRow.cells[8].innerHTML=$('zjTerm').value;
		    newRow.cells[9].innerHTML=$('scrapValueRate').value;
			newRow.cells[10].innerHTML="<input class=\"button\" type=\"button\" height=\"20\" onClick=deleteRow(this); value=\"删除\" />"+
		    "<input name=\"hkTerm\" id=\"hkTerm\" type=\"hidden\" value=\"/\"/>"+
		    "<input name=\"zjTerm\" id=\"zjTerm\" type=\"hidden\" value=\""+$('zjTerm').value+"\"/>"+
		    "<input name=\"scrapValueRate\" id=\"scrapValueRate\" type=\"hidden\" value=\""+$('scrapValueRate').value+"\"/>";
		}
		//将输入的内容清空
		//$('businessNo').selectedIndex = 0;
		//$('productNo').selectedIndex = 0;
		//$('productNo').disabled = true;
		//$('amt').value = "";
		//$("rate").disabled = false;
		//$("rate").value = "";
		//$('hkTerm').value = "";
		//$('zjTerm').value = "";
		//$('scrapValueRate').value = "";
		//$("hkzq").style.display = "none";
		//$("zjzq").style.display = "none";
		//$('opnDate').value = "<%=CommonFunctions.GetCurrentDateInLong()%>";
		//$('mtrDate').value = "<%=CommonFunctions.dateModifyY(String.valueOf(CommonFunctions.GetCurrentDateInLong()),1)%>";
		
    }
	function onclick_back() {
		window.location.reload();
	}
	function getPrdtName(businessNo) {
		var business = $(businessNo).value;
		if(business == 'YW201' || business == 'YW107') {//如果是活期或者是现金，到期日期设置为disabled
			document.getElementById("mtrDateFont").style.display = "none";
			j("#mtrDate").hide();//日期文本框和日期控件同时使用hide和show方法，可解决img显示后换行的问题
			document.getElementById("mtrDate").value = "/";
			j("#mtrDateImg").hide();
			document.getElementById("mtrDateTd").innerHtml="&nbsp;";
		}else {
			document.getElementById("mtrDateFont").style.display = "block";
			j("#mtrDate").show();
			document.getElementById("mtrDate").value = "<%=CommonFunctions.dateModifyY(String.valueOf(CommonFunctions.GetCurrentDateInLong()),1)%>";
            j("#mtrDateImg").show();
		}
		getMethodNo();
    	if (business != '') {
			$("productNo").disabled = false;
			document.getElementById("productNo").options.length = 0;
			document.getElementById("productNo").add(new Option("--请选择--", ""));
			var prdtNameList;
			var url = '<%=request.getContextPath()%>/fillSelect_getPrdtName.action?businessNo=' + business;
			new Ajax.Request(url, {
				method : 'post',
				parameters : {
					t : new Date().getTime()
				},
				onSuccess : function(transport) {
					prdtNameList = transport.responseText.split(",");
					$A(prdtNameList).each(function(index) {
						//Option参数，前面是text，后面是value
							var opt = new Option(index.split('|')[1], index
									.split('|')[0]);
							document.getElementById("productNo").add(opt);
						});
				}
			});
		} else {
			$("productNo").disabled = true;
		}
	}
	function getMethodNo(){
        var prdtNo = $("productNo").value;
        if(prdtNo != '') {
        	if($("manageLvl").value == '4') {
                alert("请选择县联社及以下的机构！！");
                return;
     	    }
        	var url = "<%=request.getContextPath()%>/QXPPYWMNDJ_getMethodNo.action";
    		new Ajax.Request(url, {
    			method : 'post',
    			parameters : {
    			    brNo : $("brNo").value,prdtNo:prdtNo
    			},
    			onSuccess : function(resp) {
        			var methodNo = resp.responseText;
        			if(methodNo == '04'||(methodNo == '05'&&prdtNo.substring(0,4) == 'P109')) {
        				$("hkzq").style.display = "table-row";
        				$("hkTerm").style.display = "table-row";
        				$("zjzq").style.display = "none";
        				$("zjTerm").style.display = "none";
        				$("rate").disabled = false;
        				$("rate").value = "";
        			}else if(methodNo == '05'&&prdtNo.substring(0,4) != 'P109') {
        				$("zjzq").style.display = "table-row";
        				$("zjTerm").style.display = "table-row";
        				$("hkzq").style.display = "none";
        				$("hkTerm").style.display = "none";
        				$("rate").disabled = true;
        				$("rate").value = "0";
        			}else {
        				$("hkzq").style.display = "none";
        				$("hkTerm").style.display = "none";
        				$("zjzq").style.display = "none";
        				$("zjTerm").style.display = "none";
        				$("rate").disabled = false;
        				$("rate").value = "";
        			}
    			}
    		});
        }
    }
</script>
</html>
