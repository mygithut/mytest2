<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>�ͻ�����FTP����������</title>
<jsp:include page="../commonJs.jsp" />
</head>
<body>
<form id="form1" name="form1" method="get" action="" style="display:none ;">
<%
List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("khjllrpmbReportList");
String minDate = (String)session.getAttribute("khjllrpmbMinDate");
String maxDate = (String)session.getAttribute("khjllrpmbMaxDate");
String brName = (String)session.getAttribute("khjllrpmbBrName");

int days = CommonFunctions.daysSubtract(maxDate, minDate);//����
%>
<br/>
<%if(ylfxBbReportList == null) { %>
   <p>����ͳ�ƴ�������ϵ����Ա����</p>
<%}else{ 
	double zftplr = 0,
			grhqbal=0,grdqbal=0,dwhqbal=0,dwdqbal=0,czxbal=0,yhkbal=0,yjhkbal=0,bzjbal=0,
			grhqrjye=0,grdqrjye=0,dwhqrjye=0,dwdqrjye=0,czxrjye=0,yhkrjye=0,yjhkrjye=0,bzjrjye=0,
			grhqftplr=0,grdqftplr=0,dwhqftplr=0,dwdqftplr=0,czxftplr=0,yhkftplr=0,yjhkftplr=0,bzjftplr=0,
			grdkftplr=0,gsdkftplr=0;
	for(YlfxBbReport ylfxBbReport : ylfxBbReportList) {
		zftplr += ylfxBbReport.getFtplr();
	}
%>
	<div align="center">������<%= brName%>
		&nbsp;&nbsp;&nbsp;&nbsp;����ʱ�Σ�<%= CommonFunctions.dateModifyD(minDate, 1)%>-<%= maxDate%>
		&nbsp;&nbsp;&nbsp;&nbsp;��λ��Ԫ��%(������)
	</div>
	<table id="tableList">
		<thead>
		<tr>
		<th align="center" width="200">
			�ͻ���������
		</th>
		<th align="center" width="250">
			��������
		</th>
		<th align="center" width="150">
			�ʲ����
		</th>

		<th align="center" width="150">
			���˻������
		</th>
		<th align="center" width="150">
			���˶������
		</th>
		<th align="center" width="150">
			��λ�������
		</th>
		<th align="center" width="150">
			��λ�������
		</th>
		<th align="center" width="150">
			���������
		</th>
		<th align="center" width="150">
			���п����
		</th>
		<th align="center" width="150">
			Ӧ�������
		</th>
		<th align="center" width="150">
			��֤�����
		</th>


		<th align="center" width="150">
			�ʲ��վ����
		</th>

		<th align="center" width="150">
			���˻����վ����
		</th>
		<th align="center" width="150">
			���˶����վ����
		</th>
		<th align="center" width="150">
			��λ�����վ����
		</th>
		<th align="center" width="150">
			��λ�����վ����
		</th>
		<th align="center" width="150">
			�������վ����
		</th>
		<th align="center" width="150">
			���п��վ����
		</th>
		<th align="center" width="150">
			Ӧ�����վ����
		</th>
		<th align="center" width="150">
			��֤���վ����
		</th>


		<th align="center" width="100">
			����(%)
		</th>

			<th align="center" width="150">
				���˻���FTP����
			</th>
			<th align="center" width="150">
				���˶���FTP����
			</th>
			<th align="center" width="150">
				��λ����FTP����
			</th>
			<th align="center" width="150">
				��λ����FTP����
			</th>
			<th align="center" width="150">
				������FTP����
			</th>
			<th align="center" width="150">
				���п�FTP����
			</th>
			<th align="center" width="150">
				Ӧ����FTP����
			</th>
			<th align="center" width="150">
				��֤��FTP����
			</th>

			<th align="center" width="150">
				���˴���FTP����
			</th>
			<th align="center" width="150">
				��˾����FTP����
			</th>

			<th align="center" width="150">
				����ҵ��FTP����ϼ�
			</th>
			<th align="center" width="150">
				��˾ҵ��FTP����ϼ�
			</th>
		<th align="center" width="150">
			FTP����ϼ�
		</th>
		<th align="center" width="120">
			����ռ��(%)
		</th>
		<th align="center" width="50">
			����
		</th>
		</tr>
		</thead>
		<tbody>
		<%
			double ftplr = 0, zzcrjye = 0, zfzrjye = 0, zcbal = 0, fzbal = 0, dsftplr = 0, dgftplr = 0;
			for (int i = 0; i < ylfxBbReportList.size(); i++) {
				YlfxBbReport entity = ylfxBbReportList.get(i);
				zzcrjye += entity.getZcrjye();
				zfzrjye += entity.getFzrjye();

				zcbal += entity.getZcbal();
				fzbal += entity.getFzbal();
				ftplr += entity.getFtplr();

				grhqbal += entity.getGrhqbal();//���˻������
				grdqbal += entity.getGrdqbal();//��ծ���
				dwhqbal += entity.getDwhqbal();//��ծ���
				dwdqbal += entity.getDwdqbal();//��ծ���
				czxbal += entity.getCzxbal();//��ծ���
				yhkbal += entity.getYhkbal();//��ծ���
				yjhkbal += entity.getYjhkbal();//��ծ���
				bzjbal += entity.getBzjbal();//��ծ���

				grhqrjye += entity.getGrhqrjye();//���˻����վ����
				grdqrjye += entity.getGrdqrjye();//��ծ�վ����
				dwhqrjye += entity.getDwhqrjye();//��ծ�վ����
				dwdqrjye += entity.getDwdqrjye();//��ծ�վ����
				czxrjye += entity.getCzxrjye();//��ծ�վ����
				yhkrjye += entity.getYhkrjye();//��ծ�վ����
				yjhkrjye += entity.getYjhkrjye();//��ծ�վ����
				bzjrjye += entity.getBzjrjye();//��ծ�վ����

				grhqftplr += entity.getGrhqftplr();//���˻���ftp����
				grdqftplr += entity.getGrdqftplr();//��ծftp����
				dwhqftplr += entity.getDwhqftplr();//��ծftp����
				dwdqftplr += entity.getDwdqftplr();//��ծftp����
				czxftplr += entity.getCzxftplr();//��ծftp����
				yhkftplr += entity.getYhkftplr();//��ծftp����
				yjhkftplr += entity.getYjhkftplr();//��ծftp����
				bzjftplr += entity.getBzjftplr();//��ծftp����

				grdkftplr += entity.getGrdkftplr();//���˴���ftp����
				gsdkftplr += entity.getGsdkftplr();//��˾����ftp����

				dsftplr += entity.getDsftplr();
				dgftplr += entity.getDgftplr();

		%>
		<tr>
			<td align="center" width="200"><%=entity.getEmpName()%>[<%=entity.getEmpNo()%>]
			</td>
			<td align="center" width="250"><%=entity.getBrName()%>[<%=entity.getBrNo()%>]
			</td>
			<td align="right" width="150"><%=FormatUtil.toMoney(entity.getZcbal())%>
			</td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getGrhqbal() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getGrdqbal() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getDwhqbal() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getDwdqbal() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getCzxbal() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getYhkbal() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getYjhkbal() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getBzjbal() )%>
			</font></td>
			<td align="right" width="150"><%=FormatUtil.toMoney(entity.getZcrjye())%>
			</td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getGrhqrjye() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getGrdqrjye() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getDwhqrjye() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getDwdqrjye() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getCzxrjye() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getYhkrjye() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getYjhkrjye() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getBzjrjye() )%>
			</font></td>
			<td align="right" width="100"><%=CommonFunctions.doublecut(entity.getLc() * 100, 3)%>
			</td>


			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getGrhqftplr() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getGrdqftplr() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getDwhqftplr() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getDwdqftplr() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getCzxftplr() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getYhkftplr() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getYjhkftplr() )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(entity.getBzjftplr() )%>
			</font></td>


			<td align="right" width="100"><%=FormatUtil.toMoney(entity.getGrdkftplr())%>
			</td>
			<td align="right" width="100"><%=FormatUtil.toMoney(entity.getGsdkftplr())%>
			</td>


			<td align="right" width="100"><%=FormatUtil.toMoney(entity.getDsftplr())%>
			</td>
			<td align="right" width="100"><%=FormatUtil.toMoney(entity.getDgftplr())%>
			</td>
			<td align="right" width="100"><%=FormatUtil.toMoney(entity.getFtplr())%>
			</td>
			<td align="right"
				width="120"><%=CommonFunctions.doublecut(entity.getFtplr() / zftplr * 100, 2)%>
			</td>
			<td align="center" width="50"><%=i + 1%>
			</td>
		</tr>
		<%} %>
		<tr>
			<td align="center" width="200">
				<font >�ϼ�</font>
			</td>
			<td align="center" width="250">
				<font >-</font>
			</td>
			<td align="right" width="150">
				<font ><%=FormatUtil.toMoney(zcbal)%>
				</font>
			</td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(grhqbal )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(grdqbal )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(dwhqbal )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(dwdqbal )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(czxbal )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(yhkbal )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(yjhkbal )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(bzjbal )%>
			</font></td>
			<td align="right" width="150">
				<font ><%=FormatUtil.toMoney(zzcrjye)%>
				</font>
			</td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(grhqrjye )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(grdqrjye )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(dwhqrjye )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(dwdqrjye )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(czxrjye )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(yhkrjye )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(yjhkrjye )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(bzjrjye )%>
			</font></td>

			<td align="right" width="100">
				<font ><%=CommonFunctions.doublecut((zzcrjye + zfzrjye) == 0 ? 0.00 : ftplr / (zzcrjye + zfzrjye) * 360 / days * 100, 3)%>
				</font>
			</td>


			<td align="right" width="150"><font ><%=FormatUtil.toMoney(grhqftplr )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(grdqftplr )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(dwhqftplr)%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(dwdqftplr )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(czxftplr )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(yhkftplr)%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(yjhkftplr )%>
			</font></td>
			<td align="right" width="150"><font ><%=FormatUtil.toMoney(bzjftplr)%>
			</font></td>

			<td align="right" width="100">
				<font ><%=FormatUtil.toMoney(grdkftplr)%>
				</font>
			</td>
			<td align="right" width="100">
				<font ><%=FormatUtil.toMoney(gsdkftplr)%>
				</font>
			</td>

			<td align="right" width="100">
				<font ><%=FormatUtil.toMoney(dsftplr)%>
				</font>
			</td>
			<td align="right" width="100">
				<font ><%=FormatUtil.toMoney(dgftplr)%>
				</font>
			</td>
			<td align="right" width="100">
				<font ><%=FormatUtil.toMoney(ftplr)%>
				</font>
			</td>
			<td align="right" width="120">
				<font ><%=CommonFunctions.doublecut(100, 2)%>
				</font>
			</td>
			<td align="center" width="50">
				<font >-</font>
			</td>
		</tr>
		</tbody>
	</table>

	<div align="center" width="1770">
		<input type="button" name="Submit1"
			   style="color: #fff; background-image: url(<%=request.getContextPath()%>/pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
			   onClick="doExport()" value="��&nbsp;&nbsp;��">
	</div>

	<%} %>
</form>

<script type="text/javascript">

    window.onload=function(){
    j(function(){
       j('#tableList').flexigrid({
                height: 230,width:970,
                title: '�ͻ�����FTP����������'});	
    	});
	};

j(document).ready(function(){ 
parent.parent.parent.parent.cancel();
document.getElementById("form1").style.display="";
});
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/reportBb_khjllrpmb_export.jsp';
	window.parent.document.thisform.submit();
}
</script>
</body>
</html>
