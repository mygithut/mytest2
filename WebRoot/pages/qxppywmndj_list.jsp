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
	</head>
	<body>
		<form name="thisform" action="" method="post">
			<table width="100%" border="0" align="center">
				<tr>
					<td>
						<table width="100%" align="left" class="table">
							<tr>
								<td class="middle_header" colspan="6">
									<font
										style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">ҵ��ģ���б�</font>
								</td>
							</tr>
							<tr>
							    <td align="center" colspan="6">
							       <table style="display:none" width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="table" id="table1" name="table1">
	                                 <tr>
										 <th width="20%">��������</th>
										 <th width="10%">ҵ������</th>
										 <th width="10%">��Ʒ����</th>
										 <th width="10%">����</th>
										 <th width="10%">��ʼ����</th>
										 <th width="10%">��������</th>
										 <th width="10%">���</th>
										 <th width="10%">����(%)</th>
										 <th width="10%">ɾ��</th>
                                     </tr>
                                     <tr style="display:none">
                                        <td align="center">
								        </td>
								        <td align="center">
								        </td>
								        <td align="center">
								        </td>
								        <td align="center">
								        </td>
								        <td align="center">
					                    </td>
					                    <td align="center">
					                    </td>
					                    <td align="center">
					                    </td>
					                    <td align="center">
					                    </td>
					                    <td align="center">
					                    </td>
                                     </tr>
                                   </table>
                                   <br/>
                                   <table style="display:none" width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="table" id="table2" name="table2">
	                                 <tr>
                                        <th width="200">��������</th>
                                        <th width="200">ҵ������</th>
                                        <th width="300">��Ʒ����</th>
                                        <th width="100">����</th>
                                        <th width="200">��ʼ����</th>
                                        <th width="200">��������</th>
                                        <th width="200">���</th>
                                        <th width="100">����(%)</th>
                                        <th width="100">��������(��)</th>
                                        <th width="100">ɾ��</th>
                                     </tr>
                                     <tr style="display:none">
                                        <td align="center">
								        </td>
								        <td align="center">
								        </td>
								        <td align="center">
								        </td>
								        <td align="center">
					                    </td>
					                    <td align="center">
					                    </td>
								        <td align="center">
								        </td>
					                    <td align="center">
					                    </td>
					                    <td align="center">
					                    </td>
					                    <td align="center">
					                    </td>
					                    <td align="center">
					                    </td>
                                     </tr>
                                   </table>
                                   <br/>
                                   <table style="display:none" width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="table" id="table3" name="table3">
	                                 <tr>
                                        <th>��������</th>
                                        <th>ҵ������</th>
                                        <th>��Ʒ����</th>
                                        <th>����</th>
                                        <th>��ʼ����</th>
                                        <th>��������</th>
                                        <th>���</th>
                                        <th>����(%)</th>
                                        <th>�۾�����(��)</th>
                                        <th>��ֵ��(%)</th>
                                        <th>ɾ��</th>
                                     </tr>
                                     <tr style="display:none">
                                        <td align="center">
								        </td>
								        <td align="center">
								        </td>
								        <td align="center">
								        </td>
								        <td align="center">
								        </td>
								        <td align="center">
					                    </td>
					                    <td align="center">
					                    </td>
					                    <td align="center">
					                    </td>
					                    <td align="center">
					                    </td>
					                    <td align="center">
					                    </td>
					                    <td align="center">
					                    </td>
					                    <td align="center">
					                    </td>
                                     </tr>
                                   </table>
							    </td>
							</tr>
							<tr>
								<td align="center" colspan="6">
                                    <input style="display:none" name="price" id="price" class="button" type="button" id="price" height="20" onClick=doPrice(); value="��������" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

		</form>
	</body>
	<script type="text/javascript" language="javascript">
	//ɾ����
	function deleteRow(obj) {
		var row = obj.parentNode.parentNode;
		var table = row.parentNode;
		var rowIndex = row.rowIndex;
		table.deleteRow(rowIndex);
	}
	function doPrice() {
		var brNos = document.getElementsByName("brNo");
	    var brNames = document.getElementsByName("brName");
	    var manageLvls = document.getElementsByName("manageLvl");
		var businessNos = document.getElementsByName("businessNo");
		var businessNames = document.getElementsByName("businessName");
		var productNos = document.getElementsByName("productNo");
		var productNames = document.getElementsByName("productName");
		var curNos = document.getElementsByName("curNo");
		var curNames = document.getElementsByName("curName");
		var amts = document.getElementsByName("amt");
		var rates = document.getElementsByName("rate");
		var opnDates = document.getElementsByName("opnDate");
		var mtrDates = document.getElementsByName("mtrDate");
		var hkTerms = document.getElementsByName("hkTerm");
		var zjTerms = document.getElementsByName("zjTerm");
		var scrapValueRates = document.getElementsByName("scrapValueRate");
		if(businessNos.length == 0){
            alert("��������Ҫ���۵�ģ��ҵ����Ϣ��");
            return;
		}
		var brNo='', brName='', manageLvl='', businessNo='', businessName='', productNo='', productName='', 
		curNo='', curName='', amt='', rate='', opnDate='', mtrDate='', hkTerm='', zjTerm='',scrapValueRate='';
		for(var i = 0; i < businessNos.length; i++) {
			brNo += brNos[i].value + ",";	
			brName += brNames[i].value + ",";
			manageLvl += manageLvls[i].value + ",";
			businessNo += businessNos[i].value + ",";	
			businessName += businessNames[i].value + ",";
			productNo += productNos[i].value + ",";
			productName += productNames[i].value + ",";
			curNo += curNos[i].value + ",";
			curName += curNames[i].value + ",";
			amt += amts[i].value + ",";
			rate += rates[i].value + ",";
			opnDate += opnDates[i].value + ",";
			mtrDate += mtrDates[i].value + ",";
			hkTerm += hkTerms[i].value + ",";
			zjTerm += zjTerms[i].value + ",";
			scrapValueRate += scrapValueRates[i].value + ",";
		}
		var form1 = window.parent.document.form1;
		form1.action = "<%=request.getContextPath()%>/QXPPYWMNDJ_ftp_compute_mn.action?brNo="+brNo+"&brName="+encodeURI(brName)+"&manageLvl="+manageLvl+"&businessNo="+businessNo+"&businessName="+encodeURI(businessName)+"&productNo="+productNo+"&productName="+encodeURI(productName)+"&curNo="+curNo+"&curName="+encodeURI(curName)+"&amt="+amt+"&rate="+rate+"&opnDate="+opnDate+"&mtrDate="+mtrDate+"&hkTerm="+hkTerm+"&zjTerm="+zjTerm+"&scrapValueRate="+scrapValueRate;
		form1.submit();
		parent.parent.parent.parent.openNewDiv();
	}
</script>
</html>
