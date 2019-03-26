<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import="com.dhcc.ftp.util.*,com.dhcc.ftp.entity.FtpProductMethodRel,java.util.*"%>
	<head>
		<title>产品定价公布栏</title>
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="">
			<%
			String currentDate = DateUtil.getCurrentDay();
			String filename = new String(("产品定价公布结果-公布日期"+currentDate).getBytes("GBK"),"ISO-8859-1");   
			response.addHeader("Content-Disposition", "filename=" + filename + ".xls"); 
			double[][] result = (double[][]) session.getAttribute("result");
			String[][] prdtInfo = (String[][])session.getAttribute("prdtInfo");
			String[] termType = (String[]) session.getAttribute("termType");
			%>
		<table border="1" cellspacing="0" cellpadding="0" align="center" width="4600">
		        <tr>
		            <td colspan="<%=termType.length+2 %>" style="height:25px"><font
										style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">定价公布结果-公布日期<%=currentDate %></font></td>
		        </tr>
				<tr bgcolor="yellow">
				    <th width="60">
						序号
					</th>
				    <th width="400">
						产品/期限
					</th>
					<th width="60">
						活期
					</th>
				<%for(int i = 1; i < termType.length; i++) {%>
				    <th width="60">
						<%=termType[i] %>
					</th>
				<%} %>
				</tr>
				<%
					for (int j = 0; j < result.length; j++) {
				%>
				<tr>
				    <td align="center">
						<%=j+1%>
					</td>
					<td align="center">
						<%=prdtInfo[j][1].indexOf("-净利差")==-1?prdtInfo[j][1]:prdtInfo[j][1].substring(0,prdtInfo[j][1].indexOf("-净利差"))+"["+prdtInfo[j][0]+"]"%><font color="blue"><%=prdtInfo[j][1].indexOf("-净利差")==-1?"":"-净利差" %></font>
					</td>
					<%for(int k = 0; k < result[j].length; k++) { %>
					<td align="center">
						<%if (Double.isNaN(result[j][k])){ out.print("-");
     		            }else{ out.print(CommonFunctions.doublecut(result[j][k]*100,3)+"%");
     		            }%>
					</td>
					<%} %>
				</tr>
				<%
					}
				%>
			</table>
			
		</form>

	</body>
</html>
