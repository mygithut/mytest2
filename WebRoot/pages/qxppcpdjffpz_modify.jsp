<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.entity.FtpProductMethodRel,java.text.*" %>
<%@page import="com.dhcc.ftp.util.*"%>
<html>
<head>
<meta http-equiv="Expires " content="0 ">
   <meta http-equiv="Cache-Control " content="no-cache ">
   <meta http-equiv="Pragma " content="no-cache ">
   <base target="_self">
<title>��������ƥ���Ʒ���۷���</title>
<jsp:include page="commonJs.jsp" />
</head>
<body>
<br/>
<form action="<%=request.getContextPath()%>/QXPPCPDJFFPZ_save.action"  method="post" name="save" id="save">
<%
FtpProductMethodRel ftpProductMethodRel = (FtpProductMethodRel)request.getAttribute("ftpProductMethodRel");
 %>
			<table class="table" width="95%" align="center">
				<tr>
					<th width="20%" align="right">
						ҵ�����ƣ�
					</th>
					<td width="30%">
					    <%=ftpProductMethodRel.getBusinessName() %>
					</td>
					<th width="20%" align="right">
						��Ʒ���ƣ�
					</th>
					<td width="30%">
						<%=ftpProductMethodRel.getProductName() %>
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						���۷�����
					</th>
					<td width="30%">
                        <select name="methodNo" id="methodNo" onchange="changeMethod(this.value)">
			         	</select>
					</td>
					<th width="20%" align="right">
						���������ߣ�
					</th>
					<td width="30%">
                        <select name="curveNo" id="curveNo"  onchange="changeCurveNo(this.value)">
			         	</select>
					</td>
				</tr>

				<tr id="ldck" style="display:none">
					<th width="20%" align="right">
						�����Է��ռӵ�(%)��
					</th>
					<td width="30%">
                         <input type="text" id="lrAdjustRate" name="lrAdjustRate"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=FormatUtil.formatDoubleE(ftpProductMethodRel.getLrAdjustRate()*100) %>" />
					</td>
					<th width="20%" align="right" >
					����ռ�üӵ�(%)��
					</th>
					<td width="30%">
                         <input type="text" id="epsAdjustRate" name="epsAdjustRate"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  value="<%=FormatUtil.formatDoubleE(ftpProductMethodRel.getEpsAdjustRate()*100) %>"/>
					</td>
				</tr>

<!--				<tr id="adjustRateTr">-->
<!--				   <th width="20%" align="right">��������(%)��</th>-->
<!--				   <td colspan="3">-->
<!--				   <input type="text" id="adjustRate" name="adjustRate" onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=FormatUtil.formatDoubleE(ftpProductMethodRel.getAdjustRate()*100) %>">-->
<!--				   </td>-->
<!--				</tr>-->
				<tr id="assignTermTr" style="display:none">
				   <th width="20%" align="right">�ο�����(��)��</th>
				   <td colspan="3">
				   <input type="text" id="assignTerm" name="assignTerm" value="<%=ftpProductMethodRel.getAssignTerm()%>">
				   </td>
				</tr>
				<tr id="appointRateTr" style="display:none">
				   <th width="20%" align="right">ָ������ֵ(%)��</th>
				   <td >
				   <input type="text" id="appointRate" name="appointRate" onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=FormatUtil.formatDoubleE((ftpProductMethodRel.getAppointRate()==null?0:ftpProductMethodRel.getAppointRate())*100) %>">
				   </td>

				   	<th width="20%" align="right">
						�Ƿ����۵�����
					</th>
					<td width="30%" >
                        <select name="istz" id="istz" style="width: 115" >
                         <option value='<%=ftpProductMethodRel.getIsTz() %>' selected="selected"><%=ftpProductMethodRel.getIsTz().equals("0")?"��":"��" %></option>
                         <option value='<%=ftpProductMethodRel.getIsTz().equals("0")?"1":"0" %>'><%=ftpProductMethodRel.getIsTz().equals("0")?"��":"��" %></option>
			         	</select>
					</td>
				</tr>





				<tr id="appointDeltaRateTr" style="display:none">
				   <th width="20%" align="right">�̶�����ֵ(%)��</th>
				   <td colspan="3">
				   <input type="text" id="appointDeltaRate" onkeyup="value=value.replace(/[^\d\.]/g,'')" name="appointDeltaRate" value="<%=FormatUtil.formatDoubleE((ftpProductMethodRel.getAppointDeltaRate()==null?0:ftpProductMethodRel.getAppointDeltaRate())*100) %>">
				   </td>
				</tr>
				<tr>
					<td height="41" colspan="4">
						<div align="center">
							<input type="button" name="Submit1" value="��&nbsp;&nbsp;��" onclick="doSave()" class="button">
							&nbsp;&nbsp;
							<input type="button" name="Submit1" value="��&nbsp;&nbsp;��" onclick="doReload()" class="button">
						</div>
					</td>
				</tr>
			</table>
			<input type="hidden" name="brNo" id="brNo" value="<%=ftpProductMethodRel.getBrNo() %>">
			<input type="hidden" name="productNo" id="productNo" value="<%=ftpProductMethodRel.getProductNo() %>">
		</form>
</body>
<script type="text/javascript">
fillSelectLast('methodNo','<%=request.getContextPath()%>/fillSelect_getMethodName.action?productNo=<%=ftpProductMethodRel.getProductNo()%>','<%=ftpProductMethodRel.getMethodNo()%>');

//�̶������ָ�����ʷ�û������������
fillSelectLast('curveNo','<%=request.getContextPath()%>/fillSelect_getCurveName.action?methodNo=<%=ftpProductMethodRel.getMethodNo()%>','<%=ftpProductMethodRel.getCurveNo()%>')
<%if(ftpProductMethodRel.getMethodNo().equals("06")) {//��������ʴ������Ҫ��ʾ���ο����ޡ��ı���%>
    $('assignTermTr').style.display = '';
    $('appointRateTr').style.display = 'none';
    $('appointDeltaRateTr').style.display = 'none';
    $('ldck').style.display = 'none';
<%}%>
<%if(ftpProductMethodRel.getMethodNo().equals("02")) {//�����ָ�����ʷ���Ҫ��ʾ��ָ������ֵ���ı���%>
$('assignTermTr').style.display = 'none';
$('appointRateTr').style.display = '';
$('appointDeltaRateTr').style.display = 'none';
$('ldck').style.display = 'none';
<%}%>
<%if(ftpProductMethodRel.getMethodNo().equals("08")) {//����ǹ̶������Ҫ��ʾ���̶�����ֵ���ı���%>
$('assignTermTr').style.display = 'none';
$('appointRateTr').style.display = 'none';
$('appointDeltaRateTr').style.display = '';
$('ldck').style.display = 'none';
<%}%>
<%if(ftpProductMethodRel.getCurveNo().equals("0200")) {//����ǹ̶������Ҫ��ʾ���̶�����ֵ���ı���%>
$('assignTermTr').style.display = 'none';
$('appointRateTr').style.display = 'none';
$('appointDeltaRateTr').style.display = 'none';
$('ldck').style.display = '';
<%}%>
function changeMethod(methodNo) {
	//ת��Ϊָ�����ʷ��������������ߡ��������Ϊ�գ����ο����ޡ��ı�����ʾ����ʾ��ָ������ֵ���ı���
	if(methodNo == '02') {
		fillSelectLast('curveNo','<%=request.getContextPath()%>/fillSelect_getCurveName.action?methodNo='+methodNo,'��');
	//	fillSelectLast('istz','<%=request.getContextPath()%>/fillSelect_getDkIsTz.action?productNo=<%=ftpProductMethodRel.getProductNo()%>','<%=ftpProductMethodRel.getIsTz()%>');
		$('assignTerm').value = "0";
		$('appointRateTr').style.display = '';
		if(<%=ftpProductMethodRel.getBusinessNo().startsWith("YW2")%>){
		 var appointLoad =  document.getElementById('istz');
		       appointLoad.disabled = true;
		}
        $('assignTermTr').style.display = 'none';
        $('appointRateTr').style.display = '';
        $('appointDeltaRateTr').style.display = 'none';
        $('ldck').style.display = 'none';
	}
	//���ת��Ϊ���ʴ����(������������)������ʾ�����������ߡ���������ʾ���ο����ޡ��ı��򣬲���ʾ��ָ������ֵ���ı���
	if(methodNo == '06') {
		fillSelectLast('curveNo','<%=request.getContextPath()%>/fillSelect_getCurveName.action?methodNo='+methodNo,'');
		$('appointRate').value = "0";
        $('assignTermTr').style.display = '';
        $('appointRateTr').style.display = 'none';
        $('appointDeltaRateTr').style.display = 'none';
        $('ldck').style.display = 'none';
	}
	//����Ӽ�Ȩ���ʷ�ת��Ϊָ�����ʷ�������ʾ��ָ������ֵ���ı���
<%--	if(oldMethodNo == '07' && methodNo == '02') {--%>
<%--		$('appointRateTr').style.display = "block";--%>
<%--	}--%>
	//���ת��Ϊ��Ȩ���ʷ�������ʾ��ָ������ֵ���ı���
	if(methodNo == '07') {
		fillSelectLast('curveNo','<%=request.getContextPath()%>/fillSelect_getCurveName.action?methodNo='+methodNo,'');
		$('appointRate').value = "0";
        $('assignTermTr').style.display = 'none';
        $('appointRateTr').style.display = 'none';
        $('appointDeltaRateTr').style.display = 'none';
        $('ldck').style.display = 'none';
	}
	//���ת��Ϊ�̶�����������������ߡ��������Ϊ�գ���ʾ���̶�����ֵ���ı���
	if(methodNo == '08') {
		fillSelectLast('curveNo','<%=request.getContextPath()%>/fillSelect_getCurveName.action?methodNo='+methodNo,'��');
        $('assignTermTr').style.display = 'none';
        $('appointRateTr').style.display = 'none';
        $('appointDeltaRateTr').style.display = '';
        $('ldck').style.display = 'none';
	}
	//���ת��Ϊԭʼ����ƥ�䷨������ʾ���̶�����ֵ���ı���
	if(methodNo == '01'||methodNo == '04'||methodNo == '03'||methodNo == '05') {
		fillSelectLast('curveNo','<%=request.getContextPath()%>/fillSelect_getCurveName.action?methodNo='+methodNo,'');
		$('appointDeltaRate').value = "0";
		$('istz').options[$('istz').selectedIndex].value = '0';

        $('assignTermTr').style.display = 'none';
        $('appointRateTr').style.display = 'none';
        $('appointDeltaRateTr').style.display = 'none';
        $('ldck').style.display = 'none';
	}
}

function changeCurveNo(curveNo){
    if(curveNo=='0200'){
       $('ldck').style.display = '';
    }else{
       $('ldck').style.display = "none";
    }
}
function doSave() {
	if($('methodNo').value==''){
        alert("�����۷���������Ϊ�գ�");
        return;
    }
	if($('curveNo').value==''){
        alert("�����������ߡ�����Ϊ�գ�");
        return;
    }
<%--	if(isNaN($('adjustRate').value)){--%>
<%--        alert("���������ʡ�����Ϊ���֣�");--%>
<%--        return;--%>
<%--    }--%>
	if(isNaN($('appointRate').value)){
        alert("��ָ������ֵ������Ϊ���֣�");
        return;
    }
	if(isNaN($('assignTerm').value)){
        alert("���ο����ޡ�����Ϊ���֣�");
        return;
    }
	if(isNaN($('appointDeltaRate').value)){
        alert("���̶�����ֵ������Ϊ���֣�");
        return;
    }
    if(document.getElementById("curveNo").value!='0200'){
    	document.getElementById("epsAdjustRate").value ="";
    	document.getElementById("lrAdjustRate").value ="";

    }
	var methodName = $('methodNo').options[$('methodNo').selectedIndex].text;
	var availableMethod;//�������ö��۷���
	var availableMethodNo;//�������ö��۷������
	var isTz = $('istz').options[$('istz').selectedIndex].value;
	for(var i=0;i<$("methodNo").length;i++){
		if($("methodNo").options[i].value!='' && $("methodNo").options[i].value!=$('methodNo').value){
			availableMethodNo = $("methodNo").options[i].value;
			availableMethod = $('methodNo').options[i].text;
			break;
		}
	}
	$('save').request({
        method:"post",
        parameters:{methodName : methodName,availableMethodNo:availableMethodNo,availableMethod:availableMethod,isTz:isTz, t:new Date().getTime()},
        onComplete: function(){
	    	   art.dialog({
              	    title:'�ɹ�',
          		    icon: 'succeed',
          		    content: '����ɹ���',
          		    ok: function () {
          		    	ok();
          		        return true;
          		    }
           	 });
      }
    });
}
function ok() {
    window.parent.frames.downframe.location.reload();
}
function doReload() {
    window.location.reload();
}
</script>
</html>
