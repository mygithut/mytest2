<%@ page contentType="text/html;charset=GBK"%>
<%@page
	import="java.util.List,com.dhcc.ftp.util.*,com.dhcc.ftp.entity.YlfxBbReport"%>
<%@ page import="java.util.Map" %>

<head>
	<title>�Թ�ҵ��FTP������ϸ��</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
	<jsp:include page="../commonJs.jsp" />
</head>
<body>
	<form id="form1" name="form1" method="get" action="">
		<%
PageUtil gsywblrmxbReportUtil = (PageUtil)session.getAttribute("gsywblrmxbReportUtil");
List<YlfxBbReport> ylfxBbReportList = gsywblrmxbReportUtil.getList();
String minDate = (String)session.getAttribute("gsywblrmxbminDate");
String maxDate = (String)session.getAttribute("gsywblrmxbdate");
Integer currentPage = (Integer)request.getAttribute("page");
Integer pageSize = (Integer)request.getAttribute("pageSize");
currentPage=currentPage==null?1:currentPage;
pageSize=pageSize==null?100:pageSize;

int days = CommonFunctions.daysSubtract(maxDate, minDate);//����
%>
		<br />
		<%if(ylfxBbReportList == null) { %>
		<p>
			����ͳ�ƴ�������ϵ����Ա����
		</p>
		<%}else{ 
	double zftplr = 0;//�ܵ�ftp����
	for(YlfxBbReport ylfxBbReport : ylfxBbReportList) {
		zftplr += ylfxBbReport.getFtplr();
	}
%>
		<div align="center">
			����ʱ�Σ�<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
			&nbsp;&nbsp;&nbsp;&nbsp;��λ��Ԫ��%(������)
		</div>
		<table border="0" align="left" width="100%" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
					<div style="width: 1000; height: 260; overflow: auto;" id="dv">
						<table id="tableList" class="table" align="left" width="2400px"
							cellpadding="0" cellspacing="0">
							<tr>
								<td class="middle_header" colspan="16">
									<font
										style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">�Թ�ҵ��FTP������ϸ��
									</font>
								</td>
							</tr>
							<tr>
								<th align="center" width="50" class="td_relative">
									���
								</th>
								<th align="center" width="350" class="td_relative">
									��������
								</th>
								<th align="center" width="350" class="td_relative">
									�ͻ���������
								</th>
								<th align="center" width="250" class="td_relative">
									�˺�
								</th>
								<th align="center" width="150" class="td_relative">
									ҵ������
								</th>
								<th align="center" width="250" class="td_relative">
									��Ʒ����
								</th>
								<th align="center" width="250" class="td_relative">
									�ͻ�����
								</th>
								<th align="center" width="70" class="td_relative">
									��������
								</th>
								<th align="center" width="70" class="td_relative">
									��������
								</th>
								<th align="right" width="150" class="td_relative">
									���
								</th>
								<th align="right" width="150" class="td_relative">
									�վ����
								</th>
								<th align="right" width="150" class="td_relative">
									�վ�������
								</th>
								<th align="right" width="70" class="td_relative">
									����(%)
								</th>
								<th align="right" width="80" class="td_relative">
									FTP�۸�(%)
								</th>
								<th align="right" width="70" class="td_relative">
									����(%)
								</th>
								<th align="right" width="150" class="td_relative">
									FTP����
								</th>
							</tr>

							<%  
         double rjye=0, ftplr=0, ratefz=0,ftpfz=0,bal=0;
         for (int i = 0; i < ylfxBbReportList.size(); i++){
        	 YlfxBbReport entity = ylfxBbReportList.get(i);
        	 ratefz += entity.getRjye()*entity.getRate();
        	 ftpfz += entity.getRjye()*(entity.getFtp()==-999?0:entity.getFtp());
        	 rjye += entity.getRjye();
        	 ftplr += entity.getFtplr();
			 bal += entity.getBal();
     %>
							<tr>
								<td align="center"><%=i+1%></td>
								<td align="center"><%=entity.getBrName()+"["+entity.getBrNo()+"]"%></td>
								<td align="center"><%=entity.getEmpName()+"["+entity.getEmpNo()+"]"%></td>
								<td align="center"><%=entity.getAcId()%></td>
								<td align="center"><%=entity.getBusinessName()%></td>
								<td align="center"><%=entity.getPrdtName()%></td>
								<td align="center"><%=entity.getCustName()%></td>
								<td align="center"><%=(entity.getOpnDate().equals("")||entity.getOpnDate()==null||entity.getOpnDate().equals("null"))?"-":entity.getOpnDate()%></td>
								<td align="center"><%=(entity.getMtrDate().equals("")||entity.getMtrDate()==null||entity.getMtrDate().equals("null"))?"-":entity.getMtrDate()%></td>
								<td align="right"><%=FormatUtil.toMoney(entity.getBal())%></td>
								<td align="right"><%=FormatUtil.toMoney(entity.getRjye())%></td>
								<td align="right"><%=FormatUtil.toMoney(entity.getRjye()*days)%></td>
								<td align="right"><%=CommonFunctions.doublecut(entity.getRate()*100,6)%></td>
								<td align="right"><%=entity.getFtp()==-999?"-":CommonFunctions.doublecut(entity.getFtp()*100,6)%></td>
								<td align="right"><%=CommonFunctions.doublecut(entity.getLc()*100,6)%></td>
								<td align="right"><%=FormatUtil.toMoney(entity.getFtplr())%></td>
							</tr>
							<%} %>
							<tr>
								<td align="center">
									<font style="font-weight: bold">�ϼ�</font>
								</td>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="center">
									<font style="font-weight: bold">-</font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=FormatUtil.toMoney(bal)%></font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=FormatUtil.toMoney(rjye)%></font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=FormatUtil.toMoney(rjye*days)%></font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=CommonFunctions.doublecut(ratefz/rjye*100,6)%></font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=CommonFunctions.doublecut(ftpfz/rjye*100,6)%></font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=CommonFunctions.doublecut(ftplr/rjye*360/days*100,6)%></font>
								</td>
								<td align="right">
									<font style="font-weight: bold"><%=FormatUtil.toMoney(ftplr)%></font>
								</td>
							</tr>


						</table>
					</div>
				</td>
			</tr>
			<tr>
				<td >
					<table border="0" width="100%" class="tb1"
						style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
						align="center">
						<tr>
							<td align="right"><%=gsywblrmxbReportUtil.getPageLine()%>
							</td>
						</tr>
					</table>

				</td>
			</tr>
			<tr>
				<td >
					<div align="center" width="1000">
						<input type="button" name="Submit1"
							style="color: #fff; background-image: url(<%=request.getContextPath()%>/pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
							onClick="doExport()" value="��&nbsp;&nbsp;��">
					</div>
				</td>
			</tr>
			<%} %>
		</table>
</body>

<script type="text/javascript">




j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
/*
    j(function(){
        j('#tableList').flexigrid({
            height: 250,width:1050,
            title: '��˾ҵ��FTP������ϸ��'});
    });*/
});
function doExport() {
	<%-- window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/reportBb_khjllrmxb_export.jsp';
	window.parent.document.thisform.submit(); --%>
	var page="<%=currentPage%>";
	window.location.href='<%=request.getContextPath()%>/REPORTBB_gsywblrmxbExport.action?page='+page;
}
</script>
<style type="text/css">
.td_relative,.middle_header {
	background-color: #CCCCCC;
	top: expression(document.getElementById ( "dv") . scrollTop-1 );
	position: relative;
	z-index: 1
}
</style>
</html>
