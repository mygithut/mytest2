<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>

<head>
<title>ȫ�����л���FTP������ܱ�</title>
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
/* String filename = new String(("ȫ�����л���FTP������ܱ�").getBytes("GBK"),"ISO-8859-1");  */  
List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("qhsyjglrhzbReportList");
String minDate = (String)session.getAttribute("qhsyjglrhzbMinDate");
String maxDate = (String)session.getAttribute("qhsyjglrhzbMaxDate");
String brName = (String)session.getAttribute("qhsyjglrhzbBrName");
String exportName=(String)session.getAttribute("exportName");
String filename = new String((exportName).getBytes("GBK"),"ISO-8859-1");
response.addHeader("Content-Disposition", "filename=" + filename + ".xls");
int days = CommonFunctions.daysSubtract(maxDate, minDate);//����
%>
<br/>
<%if(ylfxBbReportList == null) { %>
   <p>����ͳ�ƴ�������ϵ����Ա����</p>
<%}else{ %>
<div align="center"><font style="font-size:20px;font-weight:bold;">ȫ�����л���FTP������ܱ�</font></div>
<div align="center">������<%= brName%>
&nbsp;&nbsp;&nbsp;&nbsp;����ʱ�Σ�<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
&nbsp;&nbsp;&nbsp;&nbsp;��λ����Ԫ��%(������)</div>
<%-- <%double zcrjyehj=0,zcftplrhj=0,zclchj=0,fzrjyehj=0,fzftplrhj=0,fzlchj=0,ftplrhjsum=0; %> --%>
<%double zcrjyehj=0,zcftplrhj=0,zclchj=0,fzrjyehj=0,fzftplrhj=0,fzlchj=0,ftplrhjsum=0,zcbalhj=0,fzbalhj=0,
		 grhqbal=0,grdqbal=0,dwhqbal=0,dwdqbal=0,czxbal=0,yhkbal=0,yjhkbal=0,bzjbal=0,
		 grhqrjye=0,grdqrjye=0,dwhqrjye=0,dwdqrjye=0,czxrjye=0,yhkrjye=0,yjhkrjye=0,bzjrjye=0
	; %>
<table border="1">
     <tr >
      <th align="center" width="400" >��������</th>
      <th align="center" width="120" ><font style="font-weight: bold">�ʲ����</font></th>
      <th align="center" width="120" ><font style="font-weight: bold">�ʲ��վ����</font></th>
      <th align="center" width="120" >�ʲ�����(%)</th>
      <th align="center" width="100" >�ʲ�FTP����</th>
      <th align="center" width="100" >��ծ���</th>
      <th align="center" width="120" ><font style="font-weight: bold">��ծ�վ����</font></th>
      <th align="center" width="100" >��ծ����(%)</th>
      <th align="center" width="100" >��ծFTP����</th>
      <th align="center" width="120" ><font style="font-weight: bold">FTP����ϼ�</font></th>
       </tr>
<%--      <tr>
      <th align="center" width="250" >����</th>
      <th align="right" width="125" ><font  style="text-align: center ; font-weight: bold;width: 100% ;display: inline-block;">�ʲ����</font></th>
      <th align="right" width="125" ><font  style="text-align: center ; font-weight: bold;width: 100% ;display: inline-block;">�ʲ��վ����</font></th>
      <th align="right" width="105"><font style="text-align: center ; width: 100% ;display: inline-block; ">�ʲ�����(%)</font></th>
      <th align="right" width="105" ><font style="text-align: center ; width: 100% ;display: inline-block; ">�ʲ�FTP����</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">���</font></th>
         <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">���˻������</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">���˶������</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">��λ�������</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">��λ�������</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">�����Դ�����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">���п�������</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">Ӧ�������</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">��֤�������</font></th>

		<th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">�վ����</font></th>
		 <th align="right" width="135" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">���˻����վ����</font></th>
		 <th align="right" width="135" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">���˶����վ����</font></th>
		 <th align="right" width="135" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">��λ�����վ����</font></th>
		 <th align="right" width="135" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">��λ�����վ����</font></th>
		 <th align="right" width="150" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">�����Դ���վ����</font></th>
		 <th align="right" width="150" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">���п�����վ����</font></th>
		 <th align="right" width="135" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">Ӧ�����վ����</font></th>
		 <th align="right" width="150" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">��֤�����վ����</font></th>
      <th align="right" width="100" ><font style="text-align: center ; width: 100% ;display: inline-block; ">��ծ����(%)</font></th>
      <th align="right" width="100" ><font style="text-align: center ; width: 100% ;display: inline-block; ">��ծFTP����</font></th>
      <th align="right" width="100" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">FTP����ϼ�</font></th>
 
     </tr> --%> 
     <% 
         for (YlfxBbReport entity:ylfxBbReportList){
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

  %> 
        <tr>
     	<td align="center"><%=entity.getBrName()%><%=entity.getBrNo().equals("")?"":"["+entity.getBrNo()+"]"%></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(entity.getZcbal()/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(entity.getZcrjye()/10000)%></font></td>
		<td align="right"><%=CommonFunctions.doublecut(entity.getZclc()*100,3)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getZcftplr()/10000)%></td>
		
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(entity.getFzbal()/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(entity.getFzrjye()/10000)%></font></td>
		<td align="right"><%=CommonFunctions.doublecut(entity.getFzlc()*100,3)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getFzftplr()/10000)%></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(entity.getFtplrhj()/10000)%></font></td>
		</tr> 
<%-- 		<tr>
     	<td align="center"><%=entity.getBrName()%><%=entity.getBrNo().equals("")?"":"["+entity.getBrNo()+"]"%></td>
		<td align="right"><font ><%=FormatUtil.toMoney(entity.getZcbal()/10000)%></font></td>
		<td align="right"><font ><%=FormatUtil.toMoney(entity.getZcrjye()/10000)%></font></td>
		<td align="right"><%=CommonFunctions.doublecut(entity.getZclc()*100,3)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getZcftplr()/10000)%></td>

		<td ><font ><%=FormatUtil.toMoney(entity.getFzbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getGrhqbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getGrdqbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getDwhqbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getDwdqbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getCzxbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getYhkbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getYjhkbal()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getBzjbal()/10000)%></font></td>
		<td align="right"><font ><%=FormatUtil.toMoney(entity.getFzrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getGrhqrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getGrdqrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getDwhqrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getDwdqrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getCzxrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getYhkrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getYjhkrjye()/10000)%></font></td>
			<td align="right"><font ><%=FormatUtil.toMoney(entity.getBzjrjye()/10000)%></font></td>
		<td align="right"><%=CommonFunctions.doublecut(entity.getFzlc()*100,3)%></td>
		<td align="right"><%=FormatUtil.toMoney(entity.getFzftplr()/10000)%></td>
		<td align="right"><font ><%=FormatUtil.toMoney(entity.getFtplrhj()/10000)%></font></td>
		</tr> --%>
     <% }%>
<%--      <tr>
  	 <td align="center"><font style="font-weight: bold">�ϼ�</font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcrjyehj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(zcrjyehj==0?0.00:zcftplrhj/zcrjyehj*360/days*100,3)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcftplrhj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(fzrjyehj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(fzrjyehj==0?0.00:fzftplrhj/fzrjyehj*360/days*100,3)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(fzftplrhj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(ftplrhjsum/10000)%></font></td>
		</tr> --%>
		     <tr>
  	 <td align="center"><font style="font-weight: bold">�ϼ�</font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcbalhj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcrjyehj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(zcrjyehj==0?0.00:zcftplrhj/zcrjyehj*360/days*100,3)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(zcftplrhj/10000)%></font></td>

		 <td ><font ><%=FormatUtil.toMoney(fzbalhj/10000)%></font></td>

		 <%-- <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(grhqbal/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(grdqbal/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(dwhqbal/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(dwdqbal/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(czxbal/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(yhkbal/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(yjhkbal/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(bzjbal/10000)%></font></td>
 --%>
		 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(fzrjyehj/10000)%></font></td>

		 <%-- <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(grhqrjye/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(grdqrjye/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(dwhqrjye/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(dwdqrjye/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(czxrjye/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(yhkrjye/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(yjhkrjye/10000)%></font></td>
		 <td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(bzjrjye/10000)%></font></td>
 --%>
		<td align="right"><font style="font-weight: bold"><%=CommonFunctions.doublecut(fzrjyehj==0?0.00:fzftplrhj/fzrjyehj*360/days*100,3)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(fzftplrhj/10000)%></font></td>
		<td align="right"><font style="font-weight: bold"><%=FormatUtil.toMoney(ftplrhjsum/10000)%></font></td>
		</tr>
</table>
<%} %>
</form>
</body>

</html>
