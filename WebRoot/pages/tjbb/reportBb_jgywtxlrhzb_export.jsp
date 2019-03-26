<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import="java.util.List"%>
<%@ page
	import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>

	<head>
		<title>机构业务条线FTP利润汇总表</title>
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="">
			<%
			/* String filename = new String(("机构业务条线FTP利润汇总表").getBytes("GBK"),"ISO-8859-1"); */   
			List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("jgywtxlrhzbReportList");
			String minDate = (String)session.getAttribute("jgywtxlrhzbMinDate");
			String maxDate = (String)session.getAttribute("jgywtxlrhzbMaxDate");
			String brName = (String)session.getAttribute("jgywtxlrhzbBrName");
			String exportName=(String)session.getAttribute("exportName");
			String filename = new String((exportName).getBytes("GBK"),"ISO-8859-1");   
			response.addHeader("Content-Disposition", "filename=" + filename + ".xls"); 

			int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数
%>
			<br />
			<%if(ylfxBbReportList == null || ylfxBbReportList.size() == 0) { %>
			<p>
				数据统计错误，请联系管理员！！
			</p>
			<%}else{ %>
			<div align="center"><font style="font-size:20px;font-weight:bold;">机构业务条线FTP利润汇总表</font></div>
			<div align="center">
				机构：<%= brName%>
&nbsp;&nbsp;&nbsp;&nbsp;报表时段：<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单位：万元，%(年利率)
			</div>
			<table border="1">
				<thead>
					<tr>
						<th align="center" width="130">
							资产业务条线
						</th>
						<th align="right" width="115">
							<font  style="text-align: center ; width: 100% ;display: inline-block;">资产余额</font>
						</th>
						<th align="center" width="115">
							资产日均余额
						</th>
						<th align="center" width="100">
							资产利差(%)
						</th>
						<th align="center" width="100">
							资产FTP利润
						</th>
						<th align="center" width="130">
							负债业务条线
						</th>
						<th align="right" width="100">
							<font  style="text-align: center ; width: 100% ;display: inline-block;">负债余额</font>
						</th>
						<th align="center" width="100">
							负债日均余额
						</th>
						<th align="center" width="100">
							负债利差(%)
						</th>
						<th align="center" width="100">
							负债FTP利润
						</th>
						<th align="center" width="100">
							FTP利润合计
						</th>
					</tr>
					<tr>
						<% //资产为前8行，负债为后9行
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
						<font style="font-weight: bold">合计</font>
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
