<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page
	import="java.text.*,java.util.Map,java.util.Date,com.dhcc.ftp.entity.TelMst,com.dhcc.ftp.util.*"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/css/inpage.css"
			type="text/css">
		<jsp:include page="commonJs.jsp" />
	</head>
	<body>
		<div class="cr_header">
			��ǰλ�ã�����������->��������������
		</div>
		<form action="" method="get">
			<%  
			TelMst telmst = (TelMst) request.getSession().getAttribute("userBean");
            String dataDate = String.valueOf(CommonFunctions.GetDBTodayDate());//��ȡ���ݿ⵱ǰ����
            String cputeDate =  String.valueOf(CommonFunctions.GetCurrentDateInLong());
%>
			<table width="90%" align="center" class="table">
				<tr>
					<td class="middle_header" colspan="7">
						<font
							style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">��������</font>
					</td>
				</tr>
				<tr>
					<td width="10%">
						<p align="right">
							�������ͣ�
						</p>
					</td>
					<td width="25%">
						<select style="width: 140" name="curveType" id="curveType" onchange="doCurveChange(this.id)">
							<option value="">
								--��ѡ��--
							</option>
							<option value="1">
								���������������
							</option>
							<option value="2">
								�г�����������
							</option>
							<option value="3">
								����Ϣ�ʲ�����������
							</option>
							<option value="4">
								ͬҵ����������
							</option>
						</select>
					</td>
					<td width="10%" align="right">�������ƣ�</td>
		            <td width="20%">
			               <select name="brNo" id="brNo"></select>
		            </td>
					<td width="10%">
						<p align="right">
							��&nbsp;&nbsp;�ڣ�
						</p>
					</td>
					<td width="15%">
						<input type="text" name="date" id="date" disabled="disabled" maxlength="10"
							value="<%=dataDate %>" size="10" />
					</td>
					<td width="10%" align="center">
						<input type="button" name="Submit1" id="Submit1" value="��������"
							onclick="priceCalculate(this.form)" class="button">
					</td>
				</tr>
			</table>
			<div align="center"><iframe src="" id="downframe" name="downframe" width="1000" height="370"
							frameborder="no" border="0" marginwidth="0" marginheight="0"
							scrolling="auto" allowtransparency="yes" align="middle"></iframe>
			</div>
			
		</form>
	</body>
</html>
<script type="text/javascript" language="javascript">
fillSelectLast('brNo','fillSelect_getBrNoByLvl2_sylqx','<%=FtpUtil.getXlsBrNo_sylqx(telmst.getBrMst().getBrNo(),telmst.getBrMst().getManageLvl())%>');
function doCurveChange(id) {
	if ($(id).value == '1') {
		$('date').value = <%=dataDate%>;
	}
	if ($(id).value == '2') {
		$('date').value = <%=cputeDate%>;
	}
	if ($(id).value == '') {
		$('date').value = <%=dataDate%>;
	}
}
function priceCalculate(FormName)
{
	if(!(isNull(FormName.curveType,"��������"))) {
		FormName.curveType.focus();
		return;			
	}
	if(!(isNull(FormName.brNo,"����"))) {
		FormName.brNo.focus();
		return;			
	}
	parent.parent.parent.openNewDiv();
	window.frames.downframe.location.href='<%=request.getContextPath()%>/UL04_calculate.action?curveType='+$('curveType').value+'&date='+$('date').value+'&brNo='+$('brNo').value;
}   
</script>
