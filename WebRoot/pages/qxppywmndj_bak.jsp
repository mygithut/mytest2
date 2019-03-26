<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page
	import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst"%>
<html>
	<head>
		<title>����ƥ��-����ƥ��ҵ��ģ�ⶨ��</title>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/themes/green/css/core.css"
			type="text/css">
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/css/inpage.css"
			type="text/css">
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonExt2.0.2.jsp" /><!-- ��ŵ�prototype.js���� -->
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'></script>
	</head>
	<body>
	<% TelMst telmst = (TelMst) request.getSession().getAttribute("userBean"); %>
		<div class="cr_header">
			��ǰλ�ã�����ƥ��->����ƥ��ҵ��ģ�ⶨ��
		</div>
		<form name="thisform" action="" method="post">
			<table width="90%" border="0" align="center">
				<tr>
					<td>
						<table width="1000" align="left" class="table">
							<tr>
								<td class="middle_header" colspan="6">
									<font
										style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">ҵ��ģ�ⶨ��</font>
								</td>
							</tr>
							<tr>
								<td width="10%" align="right">
									�������ƣ�
								</td>
								<td colspan="3">
									<div id='comboxWithTree1'></div>
									<input name="brNo" id="brNo" type="hidden"
										value="<%=telmst.getBrMst().getBrNo()%>" />
									<input name="manageLvl" id="manageLvl" type="hidden"
										value="<%=telmst.getBrMst().getManageLvl()%>" />
					                <input name="brName" id="brName" type="hidden" 
					                    value="<%=telmst.getBrMst().getBrName() + "[" + telmst.getBrMst().getBrNo() + "]"%>" />
								</td>
								<td width="10%" align="right">
									ѡ����֣�
								</td>
								<td>
									<select style="width: 120" name="curNo" id="curNo">
									</select>
								</td>
							</tr>
							<tr>
							    <td align="center" colspan="6">
							       <table width="98%" border="0" cellspacing="0" cellpadding="0" align="center" class="table" id="tableList">	
	                                 <tr>
                                        <th>ҵ������</th>
                                        <th>��Ʒ����</th>
                                        <th>��������</th>
                                        <th>ɾ��</th>
                                     </tr>
                                     <tr style="display:none">
                                        <td align="center">
									        <select name="businessNo" id="businessNo" onchange="getPrdtName(this.id)">
									        </select>
								        </td>
								        <td align="center">
									        <select name="productNo" id="productNo" disabled="disabled">
										      <option value="">
											            ��ѡ��ҵ������
										      </option>
									        </select>
								        </td>
								        <td align="center">
					                    	<input readonly="readonly" type="text" name="adDate" maxlength="10" value="<%=CommonFunctions.GetDBSysDate()%>" size="10" /> 
		                                    <img style="CURSOR:hand" src="/ftp/pages/images/calendar.gif" width="16" height="16" alt="date" align="absmiddle" onClick="getDate0('adDate')">
					                    </td>
					                    <td align="center">
					                    </td>
                                     </tr>
                                   </table>
							    </td>
							</tr>
							<tr>
								<td align="center" colspan="6">
									<input name="add" class="button" type="button" id="add" height="20" onClick="addRow(document.getElementById('tableList'))" value="��&nbsp;&nbsp;��" /> 
                                    &nbsp;&nbsp;
                                    <input name="query" class="button" type="button" id="query" height="20" onClick=onclick_query(); value="��&nbsp;&nbsp;��" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

		</form>
	</body>
	<script type="text/javascript" language="javascript">
	fillSelect('curNo', '<%=request.getContextPath()%>/queryCurrency.action');
<%--	fillSelect('businessNo', '<%=request.getContextPath()%>/fillSelect_getBusinessName.action');--%>
	//�����
	function addRow(table){
	var lastRow = table.rows[1];
	var newRow = lastRow.cloneNode(true);

	newRow.style.display = "block";
	table.tBodies[0].appendChild(newRow);
	newRow.cells[0].innerHTML="<select name=\"businessNo\" id=\"businessNo_"+newRow.rowIndex+"\" onchange=\"getPrdtName(this.id)\"></select>";
	newRow.cells[1].innerHTML="<select name=\"productNo\" id=\"productNo_"+newRow.rowIndex+"\" disabled=\"disabled\"><option value=\"\">��ѡ��ҵ������</option></select>";
	fillSelect('businessNo_'+newRow.rowIndex, '<%=request.getContextPath()%>/fillSelect_getBusinessName.action');
	newRow.cells[2].innerHTML="<input readonly=\"readonly\" type=\"text\" name=\"openDate_"+newRow.rowIndex+"\" id=\"openDate_"+newRow.rowIndex+"\" maxlength=\"10\" value=\"<%=CommonFunctions.GetDBSysDate()%>\" size=\"10\" /> <img style=\"CURSOR:hand\" src=\"<%=request.getContextPath()%>/pages/images/calendar.gif\" width=\"16\" height=\"16\" alt=\"date\" align=\"absmiddle\" onClick=\"getDate0('openDate_"+newRow.rowIndex+"')\">";
	newRow.cells[3].innerHTML="<input class=\"button\" type=\"button\" height=\"20\" onClick=deleteRow("+newRow.rowIndex+"); value=\"ɾ��\" />";
	}
	//ɾ����
	function deleteRow(index) {
        var table = $("tableList");
        table.deleteRow(index);
	}
	function getPrdtName(businessNo) {
		var businessNos = businessNo.split("_");
		var nos = businessNos[1];
		var businessValue = $(businessNo).value;
		if ($(businessNo).value != '') {
			var productNo = 'productNo_'+nos;
			$(productNo).disabled = false;
			document.getElementById(productNo).options.length = 0;
			document.getElementById(productNo).add(new Option("--��ѡ��--", ""));
			var prdtNameList;
			var url = '<%=request.getContextPath()%>/fillSelect_getPrdtName.action?businessNo=' + businessValue;
			new Ajax.Request(url, {
				method : 'post',
				parameters : {
					t : new Date().getTime()
				},
				onSuccess : function(transport) {
					prdtNameList = transport.responseText.split(",");
					$A(prdtNameList).each(function(index) {
						//Option������ǰ����text��������value
							var opt = new Option(index.split('|')[1], index
									.split('|')[0]);
							document.getElementById(productNo).add(opt);
						});
				}
			});
		} else {
			$(productNo).disabled = true;
		}
	}
</script>
</html>
