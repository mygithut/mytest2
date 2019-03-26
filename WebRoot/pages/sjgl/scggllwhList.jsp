<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.entity.FtpPublicRate"%>
<%@page import="java.util.ArrayList,com.dhcc.ftp.util.*"%>
<%@page import="java.util.List,com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.FtpUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>�г���������ά��</title>
		<jsp:include page="../commonJs.jsp" />
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="" style="display: none;">
			<%
				List<FtpPublicRate> list = (List<FtpPublicRate>) request.getAttribute("list");
			%>
		<div align="center">
		<table id="tableList">
				<thead>
				<tr>
					<th width="100">
						����
					</th>
					<th width="100">
						����
					</th>
					<th width="100">
						����(%)
					</th>
					<th width="130">
						���ʸ�������(%)<br/>-��˽���/����
					</th>
					<th width="130">
						���ʸ�������(%)<br/>-�Թ����
					</th>
					<th width="100">
						����
					</th>
                    <th width="80">
						����
					</th>
				</tr>
				</thead>
				<tbody>
				<%
					for (FtpPublicRate ftpPublicRate : list) {
				%>
				<tr>
					<td align="center">
						<%if (ftpPublicRate.getRateNo().substring(0,1).equals("2")){
							out.print("���");
						}else if (ftpPublicRate.getRateNo().substring(0,1).equals("1")) {
							out.print("����");
						}else if(ftpPublicRate.getRateNo().equals("3")){out.print("���׼������");
						}else if(ftpPublicRate.getRateNo().equals("4")){out.print("���׼��������");
						}%>
					</td>
					<td align="center">
						<%=FtpUtil.getRateTermType(ftpPublicRate.getRateNo())%>
					</td>
					<td align="center">
						<%=FormatUtil.formatDoubleE(ftpPublicRate.getRate()*100)%>
					</td>
					<td align="center">
						<%=(ftpPublicRate.getRateNo().substring(0,1).equals("1")||ftpPublicRate.getRateNo().substring(0,1).equals("2"))?ftpPublicRate.getFloatPercent() == null ? "" :FormatUtil.formatDoubleE(ftpPublicRate.getFloatPercent()*100):"-"%>
					</td>
					<td align="center">
						<%=(ftpPublicRate.getRateNo().substring(0,1).equals("2"))?ftpPublicRate.getFloatPercentDg() == null ? "" :FormatUtil.formatDoubleE(ftpPublicRate.getFloatPercentDg()*100):"-"%>
					</td>
					<td align="center">
						<%=CastUtil.trimNull(ftpPublicRate.getAdDate())%>
					</td>
					<td align="center">						
					<a href="javascript:doEdit('<%=ftpPublicRate.getRateId()%>')">�༭</a>
					</td>
				</tr>
				<%
					}
				%>
				</tbody>
			</table>
			</div>
		</form>

<script language="javascript">
    j(function(){
    document.getElementById("form1").style.display="block";
	    j('#tableList').flexigrid({
	    		height: 390,width:800,
	    		title: '�г����������б�'});
    });
	function doEdit(rateId){
		art.dialog.open('<%=request.getContextPath()%>/SCGGLLWH_Query.action?rateId='+rateId+'&Rnd='+Math.random(), {
		    title: '�г���������ά��',
		    width: 550,
		    height:220
		});
}
   
</script>
	</body>
</html>
