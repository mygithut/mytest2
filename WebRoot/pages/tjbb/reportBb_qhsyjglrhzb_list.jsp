<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>ȫ�����л���FTP������ܱ�</title>
	<jsp:include page="../commonJs.jsp" />
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("qhsyjglrhzbReportList");
Integer isFirst = (Integer)session.getAttribute("qhsyjglrhzbIsFirst");//�Ƿ���ʾ�����ء���ť
String minDate = (String)session.getAttribute("qhsyjglrhzbMinDate");
String maxDate = (String)session.getAttribute("qhsyjglrhzbMaxDate");
String brName = (String)session.getAttribute("qhsyjglrhzbBrName");

int days = CommonFunctions.daysSubtract(maxDate, minDate);//����
%>
<br/>
<%if(ylfxBbReportList == null) { %>
   <p>����ͳ�ƴ�������ϵ����Ա����</p>
<%}else{ %>
<div align="center">������<%= brName%>
&nbsp;&nbsp;&nbsp;&nbsp;����ʱ�Σ�<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
&nbsp;&nbsp;&nbsp;&nbsp;��λ����Ԫ��%(������)</div>
<%double zcrjyehj=0,zcftplrhj=0,zclchj=0,fzrjyehj=0,fzftplrhj=0,fzlchj=0,ftplrhjsum=0,zcbalhj=0,fzbalhj=0,
		 grhqbal=0,grdqbal=0,dwhqbal=0,dwdqbal=0,czxbal=0,yhkbal=0,yjhkbal=0,bzjbal=0,
		 grhqrjye=0,grdqrjye=0,dwhqrjye=0,dwdqrjye=0,czxrjye=0,yhkrjye=0,yjhkrjye=0,bzjrjye=0
	; %>
<table id="tableList">
	<thead>
     <tr >
      <th align="center" width="250" >��������</th>
      <th align="right" width="85" ><font  style="text-align: center ; width: 100% ;display: inline-block;">�ʲ����</font></th>
      <th align="right" width="85" ><font  style="text-align: center ; width: 100% ;display: inline-block;">�ʲ��վ����</font></th>
      <th align="right" width="80"><font style="text-align: center ; width: 100% ;display: inline-block; ">�ʲ�����(%)</font></th>
      <th align="right" width="80" ><font style="text-align: center ; width: 100% ;display: inline-block; ">�ʲ�FTP����</font></th>
		 <th align="right" width="105" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��ծ���</font></th>
        <%-- <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">���˻������</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">���˶������</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">��λ�������</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">��λ�������</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">�����Դ�����</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">���п�������</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">Ӧ�������</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">��֤�������</font></th>

		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">�վ����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">���˻����վ����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">���˶����վ����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">��λ�����վ����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">��λ�����վ����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">�����Դ���վ����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">���п�����վ����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">Ӧ�����վ����</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">��֤�����վ����</font></th>--%>
		 <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">��ծ�վ����</font></th>
      <th align="right" width="80" ><font style="text-align: center ; width: 100% ;display: inline-block; ">��ծ����(%)</font></th>
      <th align="right" width="80" ><font style="text-align: center ; width: 100% ;display: inline-block; ">��ծFTP����</font></th>
      <th align="right" width="85" ><font style="text-align: center ;  width: 100% ;display: inline-block;">FTP����ϼ�</font></th>
      
      <th align="center" width="80" >��ϸ</th>
       </tr>
       </thead>
	<tbody>
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
     	<td ><%=entity.getBrName()%><%=entity.getBrNo().equals("")?"":"["+entity.getBrNo()+"]"%></td>
		<td ><font ><%=FormatUtil.toMoney(entity.getZcbal()/10000)%></font></td>
		<td ><font ><%=FormatUtil.toMoney(entity.getZcrjye()/10000)%></font></td>
		<td ><%=CommonFunctions.doublecut(entity.getZclc()*100,6)%></td>
		<td ><%=FormatUtil.toMoney(entity.getZcftplr()/10000)%></td>

		<td ><font ><%=FormatUtil.toMoney(entity.getFzbal()/10000)%></font></td>
		<%--	<td ><font ><%=FormatUtil.toMoney(entity.getGrhqbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getGrdqbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwhqbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwdqbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getCzxbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYhkbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYjhkbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getBzjbal()/10000)%></font></td>--%>
		<td ><font ><%=FormatUtil.toMoney(entity.getFzrjye()/10000)%></font></td>
			<%--<td ><font ><%=FormatUtil.toMoney(entity.getGrhqrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getGrdqrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwhqrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwdqrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getCzxrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYhkrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYjhkrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getBzjrjye()/10000)%></font></td>--%>
		<td ><%=CommonFunctions.doublecut(entity.getFzlc()*100,6)%></td>
		<td ><%=FormatUtil.toMoney(entity.getFzftplr()/10000)%></td>
		<td ><font ><%=FormatUtil.toMoney(entity.getFtplrhj()/10000)%></font></td>
		<td align="center">
		<%if(!entity.isLeaf()) { %>
		<a href="javascript:doQuery('<%=entity.getBrNo() %>','<%=entity.getManageLvl() %>')">��ϸ</a>
		<%}else{ %>
		��
		<%} %>
		</td>
		</tr>
     <% }%>
     <tr>
  	 <td ><font >�ϼ�</font></td>
		 <td ><font style="font-weight: bold" ><%=FormatUtil.toMoney(zcbalhj/10000)%></font></td>
		<td ><font  style="font-weight: bold"><%=FormatUtil.toMoney(zcrjyehj/10000)%></font></td>
		<td ><font  style="font-weight: bold"><%=CommonFunctions.doublecut(zcrjyehj==0?0.00:zcftplrhj/zcrjyehj*360/days*100,3)%></font></td>
		<td ><font  style="font-weight: bold"><%=FormatUtil.toMoney(zcftplrhj/10000)%></font></td>

		 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(fzbalhj/10000)%></font></td>

		<%-- <td ><font ><%=FormatUtil.toMoney(grhqbal/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(grdqbal/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(dwhqbal/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(dwdqbal/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(czxbal/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(yhkbal/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(yjhkbal/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(bzjbal/10000)%></font></td>--%>

		 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(fzrjyehj/10000)%></font></td>

		<%-- <td ><font ><%=FormatUtil.toMoney(grhqrjye/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(grdqrjye/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(dwhqrjye/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(dwdqrjye/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(czxrjye/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(yhkrjye/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(yjhkrjye/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(bzjrjye/10000)%></font></td>--%>

		<td ><font style="font-weight: bold"><%=CommonFunctions.doublecut(fzrjyehj==0?0.00:fzftplrhj/fzrjyehj*360/days*100,3)%></font></td>
		<td ><font style="font-weight: bold"><%=FormatUtil.toMoney(fzftplrhj/10000)%></font></td>
		<td ><font style="font-weight: bold"><%=FormatUtil.toMoney(ftplrhjsum/10000)%></font></td>
		<td ><font style="font-weight: bold">-</font></td>
		</tr>
     </tbody>
</table>
<div align="center" width="1770">
<input type="button" name="Submit1" style="color: #fff; background-image: url(<%=request.getContextPath()%>/pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  onClick="doExport()" value="��&nbsp;&nbsp;��">
<%if(isFirst == 0){ %>
<input type="button" name="Submit2" style="color: #fff; background-image: url(pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					   onclick="doBack()" value="��&nbsp;&nbsp;��">	
<%} %>
</div>
<%} %>
</form>
</body>

<script type="text/javascript">
window.onload=function(){
	j(function(){
	    j('#tableList').flexigrid({
	    		height: 230,width:950,
	    		title: 'ȫ�����л���FTP������ܱ�'});
	});
}

j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/reportBb_qhsyjglrhzb_export.jsp';
	window.parent.document.thisform.submit();
}

function doQuery(brNo, manageLvl){
	   var date = <%=maxDate%>;
	   var assessScope = window.parent.document.getElementById("assessScope").value;
	   var jgName="<%= brName%>";
	   document.location.href ='<%=request.getContextPath()%>/REPORTBB_qhsyjglrhzbReport.action?isMx=1&brNo='+brNo+'&manageLvl='+manageLvl+'&date='+date+'&assessScope='+assessScope+'&isFirst=0'+'&brName='+encodeURI(jgName);
	   parent.parent.parent.parent.openNewDiv();
}

function doBack(){
	   var brNo = window.parent.document.getElementById("brNo").value;
	   var manageLvl = window.parent.document.getElementById("manageLvl").value;
	   var date = window.parent.document.getElementById("date").value;
	   var assessScope = window.parent.document.getElementById("assessScope").value;
	  /* var brCountLvl = window.parent.document.getElementById("brCountLvl").value;*/
	   var jgName="<%= brName%>";
	   document.location.href ='<%=request.getContextPath()%>/REPORTBB_qhsyjglrhzbReport.action?isMx=0&brNo='+brNo+'&manageLvl='+manageLvl+'&date='+date+'&assessScope='+assessScope+'&isFirst=1&brCountLvl=1&brName='+encodeURI(jgName)+"&isBack=true";
	   parent.parent.parent.parent.openNewDiv();
}
</script>
</html>
