<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page
	import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.FtpProductMethodRel,com.dhcc.ftp.util.*"
	errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<jsp:include page="commonJs.jsp" />
		<title>����ƥ��-����ƥ���Ʒ���۷�������</title>
	</head>
	<%
		PageUtil ftpProductMethodRelUtil = (PageUtil) request
				.getAttribute("ftpProductMethodRelUtil");
		List ftpProductMethodRelList = ftpProductMethodRelUtil.getList();
	%>
	<body>
		<form name="form1" id="form1" method="post" style="display: none;">

			<table id="tableList">
				<thead>
					<tr>
					    <th width="50">
							����
						</th>
						<th width="50">
							���
						</th>
						<th width="100">
							ҵ������
						</th>
						<th width="230">
							��Ʒ����
						</th>
						<th width="100">
							���嶨�۷�������
						</th>
<!--						<th width="70">-->
<!--							��������(%)-->
<!--						</th>-->
						<th width="150">
							����������
						</th>
						<th width="70">
							�ο�����(��)
						</th>
						<th width="70">
							ָ������ֵ(%)
						</th>
						<th width="70">
							�̶�����ֵ(%)
						</th>
						<th width="90">
							�����Է��ռӵ�(%)
						</th>
						<th width="90">
							����ռ�üӵ�(%)
						</th>
					</tr>
				</thead>
				<%
					for (int i = 0; i < ftpProductMethodRelList.size(); i++) {
						Object obj = ftpProductMethodRelList.get(i);
						Object[] o = (Object[]) obj;
				%>
				<tbody>
					<tr>
					    <td align="center">
							<a href="javascript:doEdit('<%=o[9]%>','<%=o[11]%>')"
								id="<%=o[9]%>">�༭</a>
						</td>
						<td align="center"><%=i + 1%></td>
						<td align="center"><%=CastUtil.trimNull(o[1])%></td>
						<td align="center"><%=CastUtil.trimNull(o[2])%></td>
						<td align="center"><%=CastUtil.trimNull(o[3])%></td>
<!--						<td align="center"><%=FormatUtil.formatDoubleE(Double.valueOf(o[4].toString()) * 100)%></td>-->
						<td align="center"><%=CastUtil.trimNull(o[5]).equals("") ? "��" : CastUtil
						.trimNull(o[5])%></td>
						<td align="center"><%=CastUtil.trimNull(o[6].toString().equals("0")
						? "��"
						: o[6])%></td>
						<td align="center"><%=CastUtil.trimNull(o[10]).equals("02") ? (CommonFunctions.doubleFormat(Double.valueOf(o[7] == null ? "0" : o[7].toString()) * 100,4)) : "��"%></td>
						<td align="center"><%=CastUtil.trimNull(o[10]).equals("08") ? (CommonFunctions.doubleFormat(Double.valueOf(o[8] == null ? "0" : o[8].toString()) * 100,4)) : "��"%></td>
								
							<td align="center"><%=CastUtil.trimNull(o[14]).equals("0200")?(FormatUtil.formatDoubleE(Double.valueOf(o[12]
						.toString()) * 100)):"-"%></td>
							<td align="center"><%=CastUtil.trimNull(o[14]).equals("0200")?(FormatUtil.formatDoubleE(Double.valueOf(o[13]
						.toString()) * 100)):"-"%></td>
					</tr>
				</tbody>
				<%
					}
				%>
			</table>
			<table border="0" width="90%" class="tb1"
				style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
				align="center">
				<tr>
					<td align="right"><%=ftpProductMethodRelUtil.getPageLine()%></td>
				</tr>
			</table>
		</form>

	</body>

	<script type="text/javascript">
	document.getElementById("form1").style.display="block";
    j(function(){
	    j('#tableList').flexigrid({
	    		height: 250,width:1000,
	    		title: '�����б�'});
    });
	function doEdit(productNo, brNo) {
		//window.location.href="QXPPCPDJFFPZ_getFtpProductMethodRel.action?productNo="+productNo;
		art.dialog.open('<%=request.getContextPath()%>/QXPPCPDJFFPZ_getFtpProductMethodRel.action?productNo=' + productNo + '&brNo=' + brNo + '&rand='+ Math.random(), {
	       title: '����',
	       width: 750,
	       height:250
	   });

	}
</script>
</html>
