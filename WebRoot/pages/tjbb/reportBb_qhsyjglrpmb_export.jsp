<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>
<head>
<title>ȫ�����л���FTP����������</title>
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("qhsyjglrpmbReportList");
String minDate = (String)session.getAttribute("qhsyjglrpmbMinDate");
String maxDate = (String)session.getAttribute("qhsyjglrpmbMaxDate");
String brName = (String)session.getAttribute("qhsyjglrpmbBrName");
/* String filename = new String(("ȫ�����л���FTP����������").getBytes("GBK"),"ISO-8859-1");   */
String exportname= (String)session.getAttribute("exportName");
String filename = new String((exportname).getBytes("GBK"),"ISO-8859-1");
response.addHeader("Content-Disposition", "filename=" +filename + ".xls"); 

%>
<br/>
<%if(ylfxBbReportList == null) { %>
   <p>����ͳ�ƴ�������ϵ����Ա����</p>
<%}else{ 
	double zftplr = 0,zzcrjye=0,zfzrjye=0,zcbal=0,fzbal=0;//�ܵ�ftp����
	double zcrjyehj=0,zcftplrhj=0,zclchj=0,fzrjyehj=0,fzftplrhj=0,fzlchj=0,ftplrhjsum=0,zcbalhj=0,fzbalhj=0,
			grhqbal=0,grdqbal=0,dwhqbal=0,dwdqbal=0,czxbal=0,yhkbal=0,yjhkbal=0,bzjbal=0,dsftplrhj=0.0,dgftplrhj=0.0,
			grhqrjye=0,grdqrjye=0,dwhqrjye=0,dwdqrjye=0,czxrjye=0,yhkrjye=0,yjhkrjye=0,bzjrjye=0,
			grhqftplr=0,grdqftplr=0,dwhqftplr=0,dwdqftplr=0,czxftplr=0,yhkftplr=0,yjhkftplr=0,bzjftplr=0,gsdkftplr=0,grdkftplr=0;
	for(YlfxBbReport ylfxBbReport : ylfxBbReportList) {
		zftplr += ylfxBbReport.getFtplrhj();
		zzcrjye += ylfxBbReport.getZcrjye();
		zfzrjye += ylfxBbReport.getFzrjye();
		zcbal+=ylfxBbReport.getZcbal();
		fzbal+=ylfxBbReport.getFzbal();
		
		zcrjyehj += ylfxBbReport.getZcrjye();
		zcbalhj += ylfxBbReport.getZcbal();//�ʲ����
		zcftplrhj += ylfxBbReport.getZcftplr();
		zclchj += ylfxBbReport.getZclc();
		fzrjyehj += ylfxBbReport.getFzrjye();
		fzftplrhj += ylfxBbReport.getFzftplr();
		fzlchj += ylfxBbReport.getFzlc();
		ftplrhjsum += ylfxBbReport.getFtplrhj();
		fzbalhj += ylfxBbReport.getFzbal();//��ծ���
		grhqbal += ylfxBbReport.getGrhqbal();//���˻������
		grdqbal += ylfxBbReport.getGrdqbal();//��ծ���
		dwhqbal += ylfxBbReport.getDwhqbal();//��ծ���
		dwdqbal += ylfxBbReport.getDwdqbal();//��ծ���
		czxbal += ylfxBbReport.getCzxbal();//��ծ���
		yhkbal += ylfxBbReport.getYhkbal();//��ծ���
		yjhkbal += ylfxBbReport.getYjhkbal();//��ծ���
		bzjbal += ylfxBbReport.getBzjbal();//��ծ���

		grhqrjye += ylfxBbReport.getGrhqrjye();//���˻����վ����
		grdqrjye += ylfxBbReport.getGrdqrjye();//��ծ�վ����
		dwhqrjye += ylfxBbReport.getDwhqrjye();//��ծ�վ����
		dwdqrjye += ylfxBbReport.getDwdqrjye();//��ծ�վ����
		czxrjye += ylfxBbReport.getCzxrjye();//��ծ�վ����
		yhkrjye += ylfxBbReport.getYhkrjye();//��ծ�վ����
		yjhkrjye += ylfxBbReport.getYjhkrjye();//��ծ�վ����
		bzjrjye += ylfxBbReport.getBzjrjye();//��ծ�վ����

		grhqftplr += ylfxBbReport.getGrhqftplr();//���˻���ftp����
		grdqftplr += ylfxBbReport.getGrdqftplr();//��ծftp����
		dwhqftplr += ylfxBbReport.getDwhqftplr();//��ծftp����
		dwdqftplr += ylfxBbReport.getDwdqftplr();//��ծftp����
		czxftplr += ylfxBbReport.getCzxftplr();//��ծftp����
		yhkftplr += ylfxBbReport.getYhkftplr();//��ծftp����
		yjhkftplr += ylfxBbReport.getYjhkftplr();//��ծftp����
		bzjftplr += ylfxBbReport.getBzjftplr();//��ծftp����

		grdkftplr += ylfxBbReport.getGrdkftplr();//���˴���ftp����
		gsdkftplr += ylfxBbReport.getGsdkftplr();//��˾����ftp����

		dsftplrhj += ylfxBbReport.getDsftplr();//��˽ftp����ϼ�
		dgftplrhj += ylfxBbReport.getDgftplr();//�Թ�ftp����ϼ�
	}
%>
<div align="center"><font style="font-size:20px;font-weight:bold;">ȫ�����л���FTP����������</font></div>
<div align="center">������ȫ��
&nbsp;&nbsp;&nbsp;&nbsp;����ʱ�Σ�<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
&nbsp;&nbsp;&nbsp;&nbsp;��λ����Ԫ��%</div>
<table border="1">
     <tr >
      <th align="center" width="400" >��������</th>
<%--      <th align="center" width="100" >�ʲ��վ����</th>
      <th align="center" width="100" >��ծ�վ����</th>
      <th align="center" width="150" >FTP����ϼ�</th>
      <th align="center" width="150" >FTP����ռ��(%)</th>
      <th align="center" width="100" >����</th> --%>
      <th align="center" width="115" >�ʲ����</th>
      <th align="center" width="115" >�ʲ��վ����</th>
	  <th align="center" width="105" >��ծ���</th>
	  <th align="center" width="110" >���˻������</th>
	  <th align="center" width="110" >���˶������</th>
	  <th align="center" width="140" >��λ�������</th>
	  <th align="center" width="140" >��λ�������</th>
	  <th align="center" width="150" >�����Դ�����</th>
	  <th align="center" width="150" >���п�������</th>
	  <th align="center" width="150" >Ӧ�������</th>
	  <th align="center" width="150" >��֤�������</th>
	  
	  <th align="center" width="110" >��ծ�վ����</th>
	  <th align="center" width="140" >���˻����վ����</th>
	  <th align="center" width="140" >���˶����վ����</th>
	  <th align="center" width="140" >��λ�����վ����</th>
	  <th align="center" width="140" >��λ�����վ����</th>
	  <th align="center" width="150" >�����Դ���վ����</th>
	  <th align="center" width="150" >���п�����վ����</th>
	  <th align="center" width="140" >Ӧ�����վ����</th>
	  <th align="center" width="150" >��֤�����վ����</th>

		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">���˻���FTP����</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">���˶���FTP����</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��λ����FTP����</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��λ����FTP����</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">�����Դ��FTP����</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">���п����FTP����</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">Ӧ����FTP����</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��֤����FTP����</font></th>

		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">���˴���FTP����</font></th>
		 <th align="right" width="150" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��˾����FTP����</font></th>

		 <th align="center" width="170" ><font  style="text-align: center ; width: 100% ;display: inline-block;">����ҵ��FTP����ϼ�</font></th>
		 <th align="center" width="170" ><font  style="text-align: center ; width: 100% ;display: inline-block;">��˾ҵ��FTP����ϼ�</font></th>
	  
      <th align="center" width="100" >FTP����ϼ�</th>
      <th align="center" width="125" >FTP����ռ��(%)</th>
      <th align="center" width="100" >����</th>
      
       </tr>
     <% 
         for (int i = 0; i < ylfxBbReportList.size(); i++){
        	 YlfxBbReport entity = ylfxBbReportList.get(i);
     %>
        <tr>
     	<td align="center"><%=entity.getBrName()%>[<%=entity.getBrNo()%>]</td>
		<td align="right"><%=FormatUtil.toMoney(entity.getZcbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getZcrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getFzbal()/10000)%></td>

		<td align="right"><%=FormatUtil.toMoney(entity.getGrhqbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getGrdqbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getDwhqbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getDwdqbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getCzxbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getYhkbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getYjhkbal()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getBzjbal()/10000)%></td>

		<td align="right"><%=FormatUtil.toMoney(entity.getFzrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getGrhqrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getGrdqrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getDwhqrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getDwdqrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getCzxrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getYhkrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getYjhkrjye()/10000)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getBzjrjye()/10000)%></td>

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

			<td align="right"><%=FormatUtil.toMoney(entity.getDsftplr()/10000)%></td>
			<td align="right"><%=FormatUtil.toMoney(entity.getDgftplr()/10000)%></td>
		
		<td align="right"><%=FormatUtil.toMoney(entity.getFtplrhj()/10000)%></td>
		<td align="right"><%=CommonFunctions.doublecut(entity.getFtplrhj()/zftplr*100,2)%></td>
		<td align="center"><%=i+1%></td>
		</tr>
     <% }
     %>
     <tr>
     	<td align="center"><font style="font-weight: bold">�ϼ�</font></td>
<%-- 		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zzcrjye/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zfzrjye/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zftplr/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(100,2)%></font></td>
		 --%>
		 
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcbal/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcrjyehj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(fzbal/10000)%></font></td>
		
	     <td align="right"><%=FormatUtil.toMoney(grhqbal/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(grdqbal/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(dwhqbal/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(dwdqbal/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(czxbal/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(yhkbal/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(yjhkbal/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(bzjbal/10000)%></td>
		 <td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(fzrjyehj/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(grhqrjye/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(grdqrjye/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(dwhqrjye/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(dwdqrjye/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(czxrjye/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(yhkrjye/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(yjhkrjye/10000)%></td>
		 <td align="right"><%=FormatUtil.toMoney(bzjrjye/10000)%></td>

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
		 
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zftplr/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(100,2)%></font></td>
		<td align="center"><font style="font-weight: bold">-</font></td>
		</tr>
				</table>
<%} %>
</form>
</body>
</html>
