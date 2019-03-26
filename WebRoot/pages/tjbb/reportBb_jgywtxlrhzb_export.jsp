<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import="java.util.List"%>
<%@ page
	import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>

	<head>
		<title>����ҵ������FTP������ܱ�</title>
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="">
			<%
			/* String filename = new String(("����ҵ������FTP������ܱ�").getBytes("GBK"),"ISO-8859-1"); */   
			List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("jgywtxlrhzbReportList");
			String minDate = (String)session.getAttribute("jgywtxlrhzbMinDate");
			String maxDate = (String)session.getAttribute("jgywtxlrhzbMaxDate");
			String brName = (String)session.getAttribute("jgywtxlrhzbBrName");
			String exportName=(String)session.getAttribute("exportName");
			String filename = new String((exportName).getBytes("GBK"),"ISO-8859-1");   
			response.addHeader("Content-Disposition", "filename=" + filename + ".xls"); 

			int days = CommonFunctions.daysSubtract(maxDate, minDate);//����
%>
			<br />
			<%if(ylfxBbReportList == null || ylfxBbReportList.size() == 0) { %>
			<p>
				����ͳ�ƴ�������ϵ����Ա����
			</p>
			<%}else{ %>
			<div align="center"><font style="font-size:20px;font-weight:bold;">����ҵ������FTP������ܱ�</font></div>
			<div align="center">
				������<%= brName%>
&nbsp;&nbsp;&nbsp;&nbsp;����ʱ�Σ�<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��λ����Ԫ��%(������)
			</div>
			<table border="1">
				<thead>
					<tr>
						<th align="center" width="130">
							�ʲ�ҵ������
						</th>
						<th align="right" width="115">
							<font  style="text-align: center ; width: 100% ;display: inline-block;">�ʲ����</font>
						</th>
						<th align="center" width="115">
							�ʲ��վ����
						</th>
						<th align="center" width="100">
							�ʲ�����(%)
						</th>
						<th align="center" width="100">
							�ʲ�FTP����
						</th>
						<th align="center" width="130">
							��ծҵ������
						</th>
						<th align="right" width="100">
							<font  style="text-align: center ; width: 100% ;display: inline-block;">��ծ���</font>
						</th>
						<th align="center" width="100">
							��ծ�վ����
						</th>
						<th align="center" width="100">
							��ծ����(%)
						</th>
						<th align="center" width="100">
							��ծFTP����
						</th>
						<th align="center" width="100">
							FTP����ϼ�
						</th>
					</tr>
					<tr>
						<% //�ʲ�Ϊǰ8�У���ծΪ��9��
							double zcrjye=0,zcftplr=0,zclc=0,fzrjye=0,fzftplr=0,fzlc=0,sumftplr=0,zcbal=0,fzbal=0;
				         for (int i = 0; i < 8; i++){
				        	 YlfxBbReport entityZc = (i==8)?new YlfxBbReport():ylfxBbReportList.get(i);
				        	 YlfxBbReport entityFz = ylfxBbReportList.get(i+8);
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
							<font style="font-weight: bold"><%=entityZc.getBusinessName() %></font>
						</td>
						<td align="center"><%=entityZc.getBal() == null ? 0.00 : FormatUtil.toMoney(entityZc.getBal()/10000)%></td>
						<td align="center"><%=entityZc.getRjye() == null ? 0.00 : FormatUtil.toMoney(entityZc.getRjye()/10000)%></td>
						<td align="center"><%=entityZc.getLc() == null ? 0.00 : CommonFunctions.doublecut(entityZc.getLc()*100,3)%></td>
						<td align="center"><%=entityZc.getFtplr() == null ? 0.00 : FormatUtil.toMoney(entityZc.getFtplr()/10000)%></td>
						<%}else{ %>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
						<%} %>
						<td align="center">
							<font style="font-weight: bold"><%=entityFz.getBusinessName() %></font>
						</td>
						<td align="right"><%=entityFz.getBal() == null ? 0.00 : FormatUtil.toMoney(entityFz.getBal()/10000)%></td>
						<td align="right"><%=entityFz.getRjye() == null ? 0.00 : FormatUtil.toMoney(entityFz.getRjye()/10000)%></td>
						<td align="right"><%=entityFz.getLc() == null ? 0.00 : CommonFunctions.doublecut(entityFz.getLc()*100,3)%></td>
						<td align="right"><%=entityFz.getFtplr() == null ? 0.00 : FormatUtil.toMoney(entityFz.getFtplr()/10000)%></td>
						<td align="right">
							<font style="font-weight: bold"><%=FormatUtil.toMoney((entityZc.getFtplr() == null ? 0.00 : entityZc.getFtplr()/10000)+(entityFz.getFtplr() == null ? 0.00 : entityFz.getFtplr()/10000))%></font>
						</td>
					</tr>
					<% }
     %>
     	<tr>
					<td align="center">
						<font style="font-weight: bold">�ϼ�</font>
					</td>
					<td align="right">
						<font style="font-weight: bold"><%=FormatUtil.toMoney(zcbal/10000)%></font>
					</td>
					<td align="right">
						<font style="font-weight: bold"><%=FormatUtil.toMoney(zcrjye/10000)%></font>
					</td>
					<td align="right">
						<font style="font-weight: bold"><%=CommonFunctions.doublecut(zcrjye==0?0.00:zcftplr/zcrjye*360/days*100,3)%></font>
					</td>
					<td align="right">
						<font style="font-weight: bold"><%=FormatUtil.toMoney(zcftplr/10000)%></font>
					</td>
					<td align="center">
						<font style="font-weight: bold">-</font>
					</td>
					<td align="center">
						<font style="font-weight: bold"><%=FormatUtil.toMoney(fzbal/10000)%></font>
					</td>
					<td align="right">
						<font style="font-weight: bold"><%=FormatUtil.toMoney(fzrjye/10000)%></font>
					</td>
					<td align="right">
						<font style="font-weight: bold"><%=CommonFunctions.doublecut(fzrjye==0?0.00:fzftplr/fzrjye*360/days*100,3)%></font>
					</td>
					<td align="right">
						<font style="font-weight: bold"><%=FormatUtil.toMoney(fzftplr/10000)%></font>
					</td>
					<td align="right">
						<font style="font-weight: bold"><%=FormatUtil.toMoney(sumftplr/10000)%></font>
					</td>
					</tr>
				</tbody>
			</table>
			<%} %>
		</form>
	</body>

</html>
