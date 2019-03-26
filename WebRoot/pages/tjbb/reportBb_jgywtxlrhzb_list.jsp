<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List"%>
<%@ page
	import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>����ҵ������FTP������ܱ�</title>
		<jsp:include page="../commonJs.jsp" />
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="">
			<%
			List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("jgywtxlrhzbReportList");
			String minDate = (String)session.getAttribute("jgywtxlrhzbMinDate");
			String maxDate = (String)session.getAttribute("jgywtxlrhzbMaxDate");
			String brName = (String)session.getAttribute("jgywtxlrhzbBrName");

			int days = CommonFunctions.daysSubtract(maxDate, minDate);//����
%>
			<br />
			<%if(ylfxBbReportList == null || ylfxBbReportList.size() == 0) { %>
			<p>
				����ͳ�ƴ�������ϵ����Ա����
			</p>
			<%}else{ %>
			<div align="center">
				������<%= brName%>
&nbsp;&nbsp;&nbsp;&nbsp;����ʱ�Σ�<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
				&nbsp;&nbsp;&nbsp;&nbsp;��λ����Ԫ��%(������)
			</div>
			<table id="tableList">
				<thead>
					<tr>
						<th align="center" width="130">
							�ʲ�ҵ������
						</th>
						<th align="right" width="85">
							<font  style="text-align: center ; width: 100% ;display: inline-block;">�ʲ����</font>
						</th>
						<th align="right" width="85">
							<font  style="text-align: center ; width: 100% ;display: inline-block;">�ʲ��վ����</font>
						</th>
						<th align="right" width="85">
							<font  style="text-align: center ; width: 100% ;display: inline-block;">�ʲ�����(%)</font>
						</th>
						<th align="right" width="85">
							<font  style="text-align: center ; width: 100% ;display: inline-block;">�ʲ�FTP����</font>
						</th>
						<th align="center" width="130">
							��ծҵ������
						</th>
						<th align="right" width="85">
							<font  style="text-align: center ; width: 100% ;display: inline-block;">��ծ���</font>
						</th>
						<th align="right" width="85">
							<font  style="text-align: center ; width: 100% ;display: inline-block;">��ծ�վ����</font>
						</th>
						<th align="right" width="85">
							<font  style="text-align: center ; width: 100% ;display: inline-block;">��ծ����(%)</font>
						</th>
						<th align="right" width="85">
							<font  style="text-align: center ; width: 100% ;display: inline-block;">��ծFTP����</font>
						</th>
						<th align="right" width="85">
							<font  style="text-align: center ; width: 100% ;display: inline-block;">FTP����ϼ�</font>
						</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<% //�ʲ�Ϊǰ8�У���ծΪ��9��
							double zcrjye=0,zcftplr=0,zclc=0,fzrjye=0,fzftplr=0,fzlc=0,sumftplr=0,zcbal=0,fzbal=0;
         for (int i = 0; i < 8; i++){
        	 YlfxBbReport entityZc = (i==8)?new YlfxBbReport():ylfxBbReportList.get(i);
        	 YlfxBbReport entityFz = (i==8)?new YlfxBbReport(): ylfxBbReportList.get(i+8);
        	 zcrjye+=entityZc.getRjye() == null ? 0.00:entityZc.getRjye();
        	 zcftplr+=entityZc.getFtplr() == null ? 0.00:entityZc.getFtplr();
			 zcbal+=entityZc.getBal();

        	 fzrjye+=entityFz.getRjye() == null ? 0.00:entityFz.getRjye();
        	 fzftplr+=entityFz.getFtplr() == null ? 0.00:entityFz.getFtplr();
			 fzbal+=entityFz.getBal();
        	 
        	 sumftplr+=(entityZc.getFtplr() == null ? 0.00:entityZc.getFtplr())+(entityFz.getFtplr() == null ? 0.00:entityFz.getFtplr());
        	 
     %>
            <%if(entityZc.getBusinessNo()!=null){ %>
						<td align="center">
							<a href="javascript:doQuery('<%=entityZc.getBusinessNo() %>','<%=entityZc.getBusinessName() %>')"><font
								style="font-weight: bold"><%=entityZc.getBusinessName() %></font>
							</a>
						</td>
						<td align="center"><%=entityZc.getBal() == null ? 0.00 : FormatUtil.toMoney(entityZc.getBal()/10000)%></td>
						<td align="center"><%=entityZc.getRjye() == null ? 0.00 : FormatUtil.toMoney(entityZc.getRjye()/10000)%></td>
						<td align="center"><%=entityZc.getLc() == null ? 0.00 : CommonFunctions.doublecut(entityZc.getLc()*100,6)%></td>
						<td align="center"><%=entityZc.getFtplr() == null ? 0.00 : FormatUtil.toMoney(entityZc.getFtplr()/10000)%></td>
						<%}else{ %>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
						<%} %>
						<td align="center">
							<a href="javascript:doQuery('<%=entityFz.getBusinessNo() %>','<%=entityFz.getBusinessName() %>')"><font
								style="font-weight: bold"><%=entityFz.getBusinessName() %></font>
							</a>
						</td>
						<td align="center"><%=entityFz.getBal() == null ? 0.00 : FormatUtil.toMoney(entityFz.getBal()/10000)%></td>
						<td align="center"><%=entityFz.getRjye() == null ? 0.00 : FormatUtil.toMoney(entityFz.getRjye()/10000)%></td>
						<td align="center"><%=entityFz.getLc() == null ? 0.00 : CommonFunctions.doublecut(entityFz.getLc()*100,6)%></td>
						<td align="center"><%=entityFz.getFtplr() == null ? 0.00 : FormatUtil.toMoney(entityFz.getFtplr()/10000)%></td>
						<td align="center">
							<font style="font-weight: bold"><%=FormatUtil.toMoney((entityZc.getFtplr() == null ? 0.00 : entityZc.getFtplr()/10000)+(entityFz.getFtplr() == null ? 0.00 : entityFz.getFtplr()/10000))%></font>
						</td>
					</tr>
					<% }
     %>
					<td align="center">
						<font style="font-weight: bold">�ϼ�</font>
					</td>
					<td align="center">
						<font style="font-weight: bold"><%=FormatUtil.toMoney(zcbal/10000)%></font>
					</td>
					<td align="center">
						<font style="font-weight: bold"><%=FormatUtil.toMoney(zcrjye/10000)%></font>
					</td>
					<td align="center">
						<font style="font-weight: bold"><%=CommonFunctions.doublecut(zcrjye==0?0.00:zcftplr/zcrjye*360/days*100,6)%></font>
					</td>
					<td align="center">
						<font style="font-weight: bold"><%=FormatUtil.toMoney(zcftplr/10000)%></font>
					</td>
					<td align="center">
						<font style="font-weight: bold">-</font>
					</td>
					<td align="center">
						<font style="font-weight: bold"><%=FormatUtil.toMoney(fzbal/10000)%></font>
					</td>
					<td align="center">
						<font style="font-weight: bold"><%=FormatUtil.toMoney(fzrjye/10000)%></font>
					</td>
					<td align="center">
						<font style="font-weight: bold"><%=CommonFunctions.doublecut(fzrjye==0?0.00:fzftplr/fzrjye*360/days*100,6)%></font>
					</td>
					<td align="center">
						<font style="font-weight: bold"><%=FormatUtil.toMoney(fzftplr/10000)%></font>
					</td>
					<td align="center">
						<font style="font-weight: bold"><%=FormatUtil.toMoney(sumftplr/10000)%></font>
					</td>
					</tr>
				</tbody>
			</table>
			<%} %>
			<div align="center">
				<input type="button" name="Submit1"
					style="color: #fff; background-image: url(<%=request.getContextPath()%>/pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					onClick="doExport()" value="��&nbsp;&nbsp;��">
			</div>
		</form>
	</body>

	<script type="text/javascript">
j(function(){
    j('#tableList').flexigrid({
    		height: 230,width:900,
    		title: '����ҵ������FTP������ܱ�'});
});
j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doQuery(businessNo,businessName){
	var brNo = window.parent.document.getElementById("brNo").value;
	var manageLvl = window.parent.document.getElementById("manageLvl").value;
	var date = <%=maxDate%>;
	var assessScope = window.parent.document.getElementById("assessScope").value;
	var jgName=window.parent.document.getElementById("brName").value;
	window.location.href ='<%=request.getContextPath()%>/REPORTBB_jggtxxfcplrmxbReport.action?brNo='+brNo+'&manageLvl='+manageLvl+'&businessNo='+businessNo+'&businessName='+encodeURI(businessName)+'&date='+date+'&assessScope='+assessScope+'&brName='+encodeURI(jgName);
	parent.parent.parent.parent.openNewDiv();
}
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/reportBb_jgywtxlrhzb_export.jsp';
	window.parent.document.thisform.submit();
}
</script>
</html>
