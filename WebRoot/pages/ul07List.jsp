
<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.FtpQxppResult,com.dhcc.ftp.util.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="commonJs.jsp" />
<title>����ƥ��-��ʷ�۸�鿴</title>
</head>
<%
	PageUtil UL07Util = (PageUtil) request.getAttribute("UL07Util");
	List<FtpQxppResult> list = UL07Util.getList();
%>
<body>
<form name="form1" method="post" id="form1" style="display: none;">
	
<%--			<tr><td align="center">--%>
<%--           <input name="add" class="button" type="button" id="add" height="20" onClick="ftpHistory()" value="�鿴��ʷ" />              --%>
<%--  </td></tr>--%>
 <table id="tableList">
	<thead>
	<tr >
<%--	<th align="center" Name="basedataid">--%>
<%--    ѡ�� --%>
<%--    </th>--%>
        <th width="50">
			���
		</th>
		<th width="100">
			����
		</th>
		<th width="80">
			����Ա
		</th>
		<th width="50">
			����
		</th>
		<th width="150">
			ҵ���˺�
		</th>
		<th width="100">
			ҵ������
		</th>
		<th width="250">
			��Ʒ����
		</th>
		<th width="80">
			��������
		</th>
		<th width="100">
			���
		</th>
		<th width="100">
			���
		</th>
		<th width="50">
			����(%)
		</th>
		<th width="50">
			����(��)
		</th>
		<th width="80">
			��������
		</th>
		<th width="80">
			���۽��ֵ(%)
		</th>
<!--		<th>-->
<!--			���۴���-->
<!--		</th>-->
		<th width="150">
			���۷���
		</th>
		<th width="150">
			������
		</th>
		<th width="80">
			���۲���Ա
		</th>
		<th width="80">
			����ϵͳ����
		</th>
		<th width="120">
			ʵ�ʶ���ʱ��
		</th>
	</tr>
	</thead>
	<%
		for (int i = 0; i < list.size(); i++) {
	%>
	 <tbody>
	 <tr>
<%--	 	<td align="center"><input type="checkbox" name="checkbox" onclick="doSelected(this)" value="<%=i%>"  /></td>--%>
     	<td align="center"><%=i + 1%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getBrNo())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getTelNo())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getCurNo()).equals("01") ? "�����" : ""%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getAcId())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i)
										.getBusinessName())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getProductName())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getOpnDate())%></td>
     	<td align="center"><%=FormatUtil.toMoney(list.get(i).getAmt())%></td>
     	<td align="center"><%=FormatUtil.toMoney(list.get(i).getBal())%></td>
     	<td align="center"><%=list.get(i).getRate() == null ? ""
						: CommonFunctions.doublecut(
								list.get(i).getRate() * 100, 3)%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getTerm())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getMtrDate())%></td>
     	<td align="center"><%
     		if (list.get(i).getFtpPrice() == null) {
     				out.print("");
     			} else if (Double.isNaN(list.get(i).getFtpPrice())) {
     				out.print(Double.NaN);
     			} else {
     				out.print(CommonFunctions.doublecut(list.get(i)
     						.getFtpPrice() * 100, 3));
     			}
     	%></td>
<!--     	<td align="center"><%=CastUtil.trimNull(list.get(i).getWrkNum())%></td>-->
     	<td align="center"><%=CastUtil.trimNull(FtpUtil
								.getMethodName_byMethodNo(list.get(i)
										.getMethodNo()))%></td>
     	<td align="center"><%=FtpUtil.getCurveName_byCurveNo(list.get(i)
								.getCurveNo())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getFtpTelNo())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getWrkSysDate())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getWrkTime().substring(
								0, 8))
						+ "-"
						+ CastUtil.trimNull(list.get(i).getWrkTime().substring(
								8))%></td>    	
	</tr>
	</tbody>
	<%
		}
	%>
</table>
<table border="0" width="100%" class="tb1"
			style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
			align="center">
			<tr><td align="right"><%=UL07Util.getPageLine()%></td></tr>
</td>
</tr>
</table>
</form>

</body>

<script type="text/javascript">	
jQuery(document).ready(function(){ 
document.getElementById("form1").style.display="block";
	parent.parent.parent.parent.cancel();
});
j(function(){
    j('#tableList').flexigrid({
    		height: 240,width:1700,
    		title: '����ƥ����ʷ�۸�鿴�б�',
    		buttons : [
   			   		{name: '����', bclass: 'export', onpress : do_Export}
   			   		]});
});

function doSelected(obj) {
	var o;
	var num = 0;
	o = document.getElementsByName("checkbox");
	for(var i = 0;i < o.length; i++){
		if(o[i].checked){
		    num++;
		}
	}
	if (num > 10) {
		alert("���鿴ʮ����¼����");
        obj.checked = false;
	}
}

function ftpHistory(){ 
	var o;
	o = document.getElementsByName("checkbox");
	var m =0;
	var no="";
	for(var i=0;i<o.length;i++){
	   if(o[i].checked){
		   no = no +o[i].value+",";
	   }
	}
	if(no==""){
		alert('���ȹ�ѡҪ�鿴��ҵ���У�');
		return;
	}
	var currentPage = $("currentPage").value;
	var pageSize = $("pageSize").value;

	art.dialog.open('<%=request.getContextPath()%>/UL07_ftpHistory.action?currentPage='+currentPage+'&pageSize='+pageSize+'&no='+encodeURIComponent(no)+'&random='+<%=Math.random()%>, {
		    title: '����ƥ����ʷ�۸�',
		    width: 920,
		    height:450
	});
}
function do_Export() {
    <%session.setAttribute("list",list);%>
	document.form1.action='<%=request.getContextPath()%>/pages/ul07ListExport.jsp';
	document.form1.submit();
	   <%/*session.removeAttribute("list");
	   System.out.println("���list");
	   */	  
	  %>
	
}

</script>
</html>
