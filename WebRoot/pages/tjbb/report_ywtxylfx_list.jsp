<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List"%>
<%@ page
	import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxReport,com.dhcc.ftp.util.FormatUtil"%>
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>ҵ������ӯ������</title>
		<jsp:include page="../commonJs.jsp" />
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="">
			<%
List<YlfxReport> ylfxReportList = (List<YlfxReport>)session.getAttribute("ylfxReportList");
String prcMode = (String)session.getAttribute("prcMode");
String tjType = (String)session.getAttribute("tjType");
String date = (String)session.getAttribute("date");
%>
			<br />
			<%if(ylfxReportList == null) { %>
			<p>
				���۲��Ի��ʽ��δ���ã���
			</p>
			<%}else{ %>
			���۲��ԣ�<%if(prcMode.equals("1")){out.print("���ʽ��");
}else if(prcMode.equals("2")){out.print("˫�ʽ��");
}else if(prcMode.equals("3")){out.print("���ʽ��");
}else if(prcMode.equals("4")){out.print("����ƥ��");} %>&nbsp;&nbsp;&nbsp;&nbsp;ͳ�����ͣ�<%=tjType.equals("1")?"����":"����" %>&nbsp;&nbsp;&nbsp;&nbsp;��λ����Ԫ
				<table id="tableList">
					<thead>
						<tr>
							<th align="center" width="110">
								<font style="font-weight: bold">ҵ������</font>
							</th>
							<th align="center" width="80">
								�ʲ����
							</th>
							<th align="center" width="80">
								��Ϣ����
							</th>
							<th align="center" width="80">
								��Ϣ��(%)
							</th>
							<th align="center" width="80">
								ƽ������(��)
							</th>
							<th align="center" width="95">
								�ʲ�ת�Ƽ۸�(%)
							</th>
							<th align="center" width="90">
								�ʲ�������(%)
							</th>
							<th align="center" width="80">
								�ʲ�ת��֧��
							</th>
							<th align="center" width="80">
								�ʲ�������
							</th>
							<th align="center" width="110">
								<font style="font-weight: bold">ҵ������</font>
							</th>
							<th align="center" width="80">
								��ծ���
							</th>
							<th align="center" width="80">
								��Ϣ֧��
							</th>
							<th align="center" width="80">
								��Ϣ��(%)
							</th>
							<th align="center" width="80">
								ƽ������(��)
							</th>
							<th align="center" width="95">
								��ծת�Ƽ۸�(%)
							</th>
							<th align="center" width="90">
								��ծ������(%)
							</th>
							<th align="center" width="80">
								��ծת������
							</th>
							<th align="center" width="80">
								��ծ������
							</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<% //�ʲ��ȸ�ծ������
         for (int i = 0; i < 6; i++){
        	 YlfxReport entityZc = ylfxReportList.get(i);
        	 YlfxReport entityFz = ylfxReportList.get(i+8);
     %>
							<td align="center">
								<%if(entityZc.getBusinessName().equals("����")) {%>
								<a href="javascript:doQuery('dk')"><font
									style="font-weight: bold">����</font>
								</a>
								<%}else if(entityZc.getBusinessName().equals("Ͷ��ҵ��")) {%>
								<a href="javascript:doQuery('tzyw')"><font
									style="font-weight: bold">Ͷ��ҵ��</font>
								</a>
								<%}else { %>
								<font style="font-weight: bold"><%=entityZc.getBusinessName()%></font>
								<%} %>
							</td>
							<td align="center"><%=entityZc.getZcye() == null ? 0.00 : FormatUtil.toMoney(entityZc.getZcye()/10000)%></td>
							<td align="center"><%=entityZc.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entityZc.getLxsr()/10000)%></td>
							<td align="center"><%=entityZc.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entityZc.getSxl()*100,2)%></td>
							<td align="center"><%=entityZc.getZcpjqx() == null ? 0.00 : entityZc.getZcpjqx()%></td>
							<td align="center"><%=entityZc.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entityZc.getZczyjg()*100,2)%></td>
							<td align="center"><%=entityZc.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entityZc.getZcjlc()*100,2)%></td>
							<td align="center"><%=entityZc.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entityZc.getZczyzc()/10000)%></td>
							<td align="center"><%=entityZc.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entityZc.getZcjsr()/10000)%></td>
							<td align="center">
								<%if(entityFz.getBusinessName().equals("���")) {%>
								<a href="javascript:doQuery('ck')"><font
									style="font-weight: bold">���</font>
								</a>
								<%}else { %>
								<font style="font-weight: bold"><%=entityFz.getBusinessName()%></font>
								<%} %>
							</td>
							<td align="center"><%=entityFz.getFzye() == null ? 0.00 : FormatUtil.toMoney(entityFz.getFzye()/10000)%></td>
							<td align="center"><%=entityFz.getLxzc() == null ? 0.00 : FormatUtil.toMoney(entityFz.getLxzc()/10000)%></td>
							<td align="center"><%=entityFz.getFxl() == null ? 0.00 : CommonFunctions.doublecut(entityFz.getFxl()*100,2)%></td>
							<td align="center"><%=entityFz.getFzpjqx() == null ? 0.00 : entityFz.getFzpjqx()%></td>
							<td align="center"><%=entityFz.getFzzyjg() == null ? 0.00 : CommonFunctions.doublecut(entityFz.getFzzyjg()*100,2)%></td>
							<td align="center"><%=entityFz.getFzjlc() == null ? 0.00 : CommonFunctions.doublecut(entityFz.getFzjlc()*100,2)%></td>
							<td align="center"><%=entityFz.getFzzysr() == null ? 0.00 : FormatUtil.toMoney(entityFz.getFzzysr()/10000)%></td>
							<td align="center"><%=entityFz.getFzjsr() == null ? 0.00 : FormatUtil.toMoney(entityFz.getFzjsr()/10000)%></td>
						</tr>
						<% }
     %>
						<% 
        	 YlfxReport entityZc2 = ylfxReportList.get(6);
     %>
						<td align="center">
							<font style="font-weight: bold"><%=entityZc2.getBusinessName()%></font>
						</td>
						<td align="center"><%=entityZc2.getZcye() == null ? 0.00 : FormatUtil.toMoney(entityZc2.getZcye()/10000)%></td>
						<td align="center"><%=entityZc2.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entityZc2.getLxsr()/10000)%></td>
						<td align="center"><%=entityZc2.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entityZc2.getSxl()*100,2)%></td>
						<td align="center"><%=entityZc2.getZcpjqx() == null ? 0.00 : entityZc2.getZcpjqx()%></td>
						<td align="center"><%=entityZc2.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entityZc2.getZczyjg()*100,2)%></td>
						<td align="center"><%=entityZc2.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entityZc2.getZcjlc()*100,2)%></td>
						<td align="center"><%=entityZc2.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entityZc2.getZczyzc()/10000)%></td>
						<td align="center"><%=entityZc2.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entityZc2.getZcjsr()/10000)%></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						</tr>
						<% 
     YlfxReport entityZc = ylfxReportList.get(7);
     %>
						<td align="center">
							<font style="font-weight: bold"><%=entityZc.getBusinessName()%></font>
						</td>
						<td align="center"><%=entityZc.getZcye() == null ? 0.00 : FormatUtil.toMoney(entityZc.getZcye()/10000)%></td>
						<td align="center"><%=entityZc.getLxsr() == null ? 0.00 : FormatUtil.toMoney(entityZc.getLxsr()/10000)%></td>
						<td align="center"><%=entityZc.getSxl() == null ? 0.00 : CommonFunctions.doublecut(entityZc.getSxl()*100,2)%></td>
						<td align="center"><%=entityZc.getZcpjqx() == null ? 0.00 : entityZc.getZcpjqx()%></td>
						<td align="center"><%=entityZc.getZczyjg() == null ? 0.00 : CommonFunctions.doublecut(entityZc.getZczyjg()*100,2)%></td>
						<td align="center"><%=entityZc.getZcjlc() == null ? 0.00 : CommonFunctions.doublecut(entityZc.getZcjlc()*100,2)%></td>
						<td align="center"><%=entityZc.getZczyzc() == null ? 0.00 : FormatUtil.toMoney(entityZc.getZczyzc()/10000)%></td>
						<td align="center"><%=entityZc.getZcjsr() == null ? 0.00 : FormatUtil.toMoney(entityZc.getZcjsr()/10000)%></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						</tr>
					</tbody>
				</table>
			<%} %>
			<div align="center">
				<input type="button" name="Submit1"
					style="color: #fff; background-image: url(pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					onClick="doExport()" value="��&nbsp;&nbsp;��">
			</div>
		</form>
	</body>

	<script type="text/javascript">
j(function(){
    j('#tableList').flexigrid({
    		height: 230,width:900,
    		title: 'ҵ������ӯ����������'});
});
j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doQuery(prdtType){
	var brNo = window.parent.document.getElementById("brNo").value;
	var manageLvl = window.parent.document.getElementById("manageLvl").value;
	var date = <%=date%>;
	var tjType = <%=tjType%>;
	var assessScope = window.parent.document.getElementById("assessScope").value;
	window.location.href ='<%=request.getContextPath()%>/REPORT_cpylfxReport.action?brNo='+brNo+'&manageLvl='+manageLvl+'&tjType='+tjType+'&date='+date+'&assessScope='+assessScope+'&prdtType='+prdtType;
	parent.parent.parent.parent.openNewDiv();
}
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/report_ywtxylfx_export.jsp';
	window.parent.document.thisform.submit();
}
</script>
</html>
