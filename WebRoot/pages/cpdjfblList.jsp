<%@page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page	import="java.util.ArrayList,java.util.List,com.dhcc.ftp.util.*"%>
<%@page import="com.dhcc.ftp.entity.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Ա������ά��</title>
		<jsp:include page="commonJs.jsp" />
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="">
			<%
				PageUtil ftpPrdtUtil = (PageUtil) request.getAttribute("ftpPrdtUtil");
				List<FtpPrdtPricePublishMain> list = (List<FtpPrdtPricePublishMain>)ftpPrdtUtil.getList();
				
				if (list == null) {
					out.print("�����ݣ�");
				} else {
			%>
				<table align="left" id="tableList">
					<thead>
						<tr>
							<th width="150">
								��������
							</th>
							<th width="150">
								����ʱ��
							</th>
							<th width="150">
								���չ�������
							</th>
							<th width="150">
								��������Ա
							</th>
						    <th width="150">
								��ϸ
						    </th>
						</tr>
					</thead>
					<%
					for (FtpPrdtPricePublishMain ftpPrdtPricePublishMain : list) {
				%>
					<tbody>
						<tr>
							<td align="center">
								<%=ftpPrdtPricePublishMain.getPublishTime().substring(0,8)%>
							</td>
							<td align="center">
								<%=ftpPrdtPricePublishMain.getPublishTime().substring(8,14)%>
							</td>
							<td align="center">
								<%=ftpPrdtPricePublishMain.getPublishNum()%>
							</td>
							<td align="center">
								<%=ftpPrdtPricePublishMain.getTelMst().getTelName()+"["+ftpPrdtPricePublishMain.getTelMst().getTelNo()+"]"%>
							<td align="center">
								<a href="javascript:doQuery('<%=ftpPrdtPricePublishMain.getPublishTime().substring(0,8)%>')" >��ϸ</a>
<%-- 								<a href="javascript:doQuery('<%=ftpPrdtPricePublishMain.getPublishTime().substring(0,8)%>')" class="button">��ϸ</a> --%>
							</td>
						</tr>
					</tbody>
					<%
					}
					}
				%>
				</table>
			<table border="0" width="800" class="tb1" align="center">
				<tr>
					<td align="right"><%=ftpPrdtUtil.getPageLine()%></td>
				</tr>
			</table>
		</form>

		<script language="javascript">
	    j(function(){
		    j('#tableList').flexigrid({
		    		height: 230,width:900,
		    		theFirstIsShown: false,//��һ�����Ƿ���ʾ�����б����Ƿ����(ĳЩ�б��һ����ȫѡ��ť)
		    		title: '�����б�',
		    		buttons : [
		    			   		{name: '��������', bclass: 'add', onpress : doAdd}
		    			   		]});
	    });

		function doAdd(){
		     parent.parent.parent.parent.openNewDiv();
			 window.parent.location.href='<%=request.getContextPath()%>/cpdjfbl_ftpCompute.action?isSave=1&Rnd='+Math.random();
		}
		function doQuery(currentDate){
		     parent.parent.parent.parent.openNewDiv();
			 window.parent.location.href='<%=request.getContextPath()%>/cpdjfbl_detail.action?isSave=0&currentDate='+currentDate+'&Rnd='+Math.random();
		}

</script>
	</body>
</html>
