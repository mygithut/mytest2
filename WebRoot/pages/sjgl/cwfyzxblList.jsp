<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.entity.FtpMidItem"%>
<%@page import="java.util.ArrayList,com.dhcc.ftp.util.*"%>
<%@page import="java.util.List,com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.FtpUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>����������¼</title>
		<jsp:include page="../commonJs.jsp" />
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="">
			<%
			List list = (List) request.getAttribute("list");
			%>
			<div align="center">
		    <table id="tableList">
		    <thead>
				<tr >
					<th width="100">
						��Ŀ���
					</td>
					<th width="200">
						��Ŀ����
					</td>
					<th width="100">
						��Ŀֵ(%)
					</th>
					<th width="100">
						����
					</th>
                    <th width="100">
						����
					</th>
				</tr>
			</thead>
				<%
					for (int i = 0; i < list.size(); i++) {
						Object obj = list.get(i);
						Object[] o = (Object[])obj;
				%>
			<tbody>
				<tr>
					<td align="center">
						<%=o[0]%>
					</td>
					<td align="center">
						<%=o[1]%>
					</td>
					<td align="center">
						<%=CommonFunctions.doublecut(Double.valueOf(o[2].toString()) * 100, 2)%>
					</td>
					<td align="center">
						<%=o[3]%>
					</td>
					<td align="center">						
					<a href="javascript:doEdit(<%=o[0]%>)">�༭</a>
					</td>
				</tr>
			</tbody>
				<%
					}
				%>
			</table>
			</div>
		</form>

<script language="javascript">
j(function(){
    j('#tableList').flexigrid({
    		height: 350,width:800,
    		theFirstIsShown: false,//��һ�����Ƿ���ʾ�����б����Ƿ����(ĳЩ�б��һ����ȫѡ��ť)
    		title: '������������б�'});
});
	function doEdit(itemNo){
		art.dialog.open('<%=request.getContextPath()%>/CWFYZXBL_Query.action?itemNo='+itemNo, {
		    title: '����������¼',
		    width: 550,
		    height:220
		});
	}
   
    function close(){
	    window.location.reload();
    }
</script>
	</body>
</html>
