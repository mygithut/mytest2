<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>ȫ�����л���FTP����������</title>
<jsp:include page="../commonJs.jsp" />
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("qhsyjglrpmbReportList");
String minDate = (String)session.getAttribute("qhsyjglrpmbMinDate");
String maxDate = (String)session.getAttribute("qhsyjglrpmbMaxDate");
String brName = (String)session.getAttribute("qhsyjglrpmbBrName");

%>
<br/>
<%if(ylfxBbReportList == null) { %>
   <p>����ͳ�ƴ�������ϵ����Ա����</p>
<%}else{ 
	double zftplr = 0,zzcrjye=0,zfzrjye=0,zcbal=0,fzbal=0,
	      zcrjyehj=0,zcftplrhj=0,zclchj=0,fzrjyehj=0,fzftplrhj=0,fzlchj=0,ftplrhjsum=0,zcbalhj=0,fzbalhj=0,
			grhqbal=0,grdqbal=0,dwhqbal=0,dwdqbal=0,czxbal=0,yhkbal=0,yjhkbal=0,bzjbal=0,dsftplrhj=0.0,dgftplrhj=0.0,
			grhqrjye=0,grdqrjye=0,dwhqrjye=0,dwdqrjye=0,czxrjye=0,yhkrjye=0,yjhkrjye=0,bzjrjye=0,
			grhqftplr=0,grdqftplr=0,dwhqftplr=0,dwdqftplr=0,czxftplr=0,yhkftplr=0,yjhkftplr=0,bzjftplr=0,gsdkftplr=0,grdkftplr=0;
	for(YlfxBbReport entity : ylfxBbReportList) {
		zftplr += entity.getFtplrhj();
		zzcrjye += entity.getZcrjye();
		zfzrjye += entity.getFzrjye();
        zcbal+=entity.getZcbal();
		fzbal+=entity.getFzbal();

		zcrjyehj += entity.getZcrjye();
		zcbalhj += entity.getZcbal();//�ʲ����
		zcftplrhj += entity.getZcftplr();
		zclchj += entity.getZclc();
		fzrjyehj += entity.getFzrjye();
		fzftplrhj += entity.getFzftplr();
		fzlchj += entity.getFzlc();
		ftplrhjsum += entity.getFtplrhj();
		fzbalhj += entity.getFzbal();//��ծ���
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

		dsftplrhj += entity.getDsftplr();//��˽ftp����ϼ�
		dgftplrhj += entity.getDgftplr();//�Թ�ftp����ϼ�

		grdkftplr += entity.getGrdkftplr();//���˴���ftp����
		gsdkftplr += entity.getGsdkftplr();//��˾����ftp����
	}
%>
<div align="center">������ȫ��
&nbsp;&nbsp;&nbsp;&nbsp;����ʱ�Σ�<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
&nbsp;&nbsp;&nbsp;&nbsp;��λ����Ԫ��%</div>
<table id="tableList">
					<thead>
     <tr >
      <th align="center" width="300" >��������</th>
	  <th align="right" width="100" ><font  style="text-align: center ; width: 100% ;display: inline-block;">�ʲ����</font></th>
      <th align="right" width="100" ><font  style="text-align: center ; width: 100% ;display: inline-block;">�ʲ��վ����</font></th>
	  <th align="right" width="100" ><font  style="text-align: center ; width: 100% ;display: inline-block;">��ծ���</font></th>
	<th align="right" width="105" ><font style="text-align: center ;  width: 100% ;display: inline-block;">���˻������</font></th>
    <th align="right" width="105" ><font style="text-align: center ;  width: 100% ;display: inline-block;">���˶������</font></th>
    <th align="right" width="105" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��λ�������</font></th>
    <th align="right" width="105" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��λ�������</font></th>
    <th align="right" width="105" ><font style="text-align: center ;  width: 100% ;display: inline-block;">�����Դ�����</font></th>
    <th align="right" width="105" ><font style="text-align: center ;  width: 100% ;display: inline-block;">���п�������</font></th>
    <th align="right" width="105" ><font style="text-align: center ;  width: 100% ;display: inline-block;">Ӧ�������</font></th>
    <th align="right" width="105" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��֤�������</font></th>

    <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��ծ�վ����</font></th>
    <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">���˻����վ����</font></th>
    <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">���˶����վ����</font></th>
    <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��λ�����վ����</font></th>
    <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��λ�����վ����</font></th>
    <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">�����Դ���վ����</font></th>
    <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">���п�����վ����</font></th>
    <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">Ӧ�����վ����</font></th>
    <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��֤�����վ����</font></th>

		 <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">���˻���FTP����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">���˶���FTP����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��λ����FTP����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��λ����FTP����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">�����Դ��FTP����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">���п����FTP����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">Ӧ����FTP����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��֤����FTP����</font></th>

		 <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">���˴���FTP����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��˾����FTP����</font></th>

		 <th align="right" width="125" ><font  style="text-align: center ; width: 100% ;display: inline-block;">����ҵ��FTP����ϼ�</font></th>
	 <th align="right" width="125" ><font  style="text-align: center ; width: 100% ;display: inline-block;">��˾ҵ��FTP����ϼ�</font></th>
      <th align="right" width="100" ><font  style="text-align: center ; width: 100% ;display: inline-block;">FTP����ϼ�</font></th>
      <th align="right" width="100" ><font  style="text-align: center ; width: 100% ;display: inline-block;">FTP����ռ��(%)</font></th>
      <th align="center" width="100" ><font  style="text-align: center ; width: 100% ;display: inline-block;">����</font></th>
       </tr>
					</thead>
					<tbody>
     <% 
         for (int i = 0; i < ylfxBbReportList.size(); i++){
        	 YlfxBbReport entity = ylfxBbReportList.get(i);
     %>
        <tr>
     	<td align="center"><%=entity.getBrName()%>[<%=entity.getBrNo()%>]</td>
		<td align="center"><%=FormatUtil.toMoney(entity.getZcbal()/10000)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getZcrjye()/10000)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getFzbal()/10000)%></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getGrhqbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getGrdqbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwhqbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwdqbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getCzxbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYhkbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYjhkbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getBzjbal()/10000)%></font></td>
	    	<td align="center"><%=FormatUtil.toMoney(entity.getFzrjye()/10000)%></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getGrhqrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getGrdqrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwhqrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwdqrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getCzxrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYhkrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYjhkrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getBzjrjye()/10000)%></font></td>

			<td ><font ><%=FormatUtil.toMoney(entity.getGrhqftplr()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getGrdqftplr()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwhqftplr()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwdqftplr()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getCzxftplr()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYhkftplr()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYjhkftplr()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getBzjftplr()/10000)%></font></td>
			<td align="center"><%=FormatUtil.toMoney(entity.getGrdkftplr()/10000)%></td>
			<td align="center"><%=FormatUtil.toMoney(entity.getGsdkftplr()/10000)%></td>

			<td align="center"><%=FormatUtil.toMoney(entity.getDsftplr()/10000)%></td>
			<td align="center"><%=FormatUtil.toMoney(entity.getDgftplr()/10000)%></td>
	    	<td align="center"><%=FormatUtil.toMoney(entity.getFtplrhj()/10000)%></td>
		    <td align="center"><%=CommonFunctions.doublecut(entity.getFtplrhj()/zftplr*100,2)%></td>
		<td align="center"><%=i+1%></td>
		</tr>
     <% }
     %>
     <tr>
     	<td align="center"><font style="font-weight: bold">�ϼ�</font></td>
		<td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcbal/10000)%></font></td>
		 <td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcrjyehj/10000)%></font></td>
		<td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(fzbal/10000)%></font></td>
		     <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(grhqbal/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(grdqbal/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(dwhqbal/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(dwdqbal/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(czxbal/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(yhkbal/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(yjhkbal/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(bzjbal/10000)%></font></td>
		 <td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(fzrjyehj/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(grhqrjye/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(grdqrjye/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(dwhqrjye/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(dwdqrjye/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(czxrjye/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(yhkrjye/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(yjhkrjye/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(bzjrjye/10000)%></font></td>

			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(grhqftplr/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(grdqftplr/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(dwhqftplr/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(dwdqftplr/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(czxftplr/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(yhkftplr/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(yjhkftplr/10000)%></font></td>
			 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(bzjftplr/10000)%></font></td>
			 <td align="center"><%=FormatUtil.toMoney(grdkftplr/10000)%></td>
			 <td align="center"><%=FormatUtil.toMoney(gsdkftplr/10000)%></td>

		     <td align="center"><%=FormatUtil.toMoney(dsftplrhj/10000)%></td>
		     <td align="center"><%=FormatUtil.toMoney(dgftplrhj/10000)%></td>


		<td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(zftplr/10000)%></font></td>
		<td align="center"><font style="font-weight: bold"><%=CommonFunctions.doublecut(100,2)%></font></td>
		<td align="center"><font style="font-weight: bold">-</font></td>
		</tr>
					</tbody>
				</table>
<div align="center" width="1770">
<input type="button" name="Submit1" style="color: #fff; background-image: url(<%=request.getContextPath()%>/pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  onClick="doExport()" value="��&nbsp;&nbsp;��">
</div>
<%} %>
</form>
</body>

<script type="text/javascript">
window.onload=function(){
	j(function(){
	    j('#tableList').flexigrid({
	    		height: 230,width:900,
	    		title: 'ȫ�����л���FTP����������'});
	});
}

j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/reportBb_qhsyjglrpmb_export.jsp';
	window.parent.document.thisform.submit();
}
</script>
</html>
