<%@page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page	import="java.util.ArrayList,java.util.List,com.dhcc.ftp.util.*"%>
<%@page import="java.util.Map"%>
<%@page import="com.dhcc.ftp.entity.FtpInterestMarginDivide"%>
<%@page import="java.util.HashMap"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Map<String,FtpInterestMarginDivide> map=(Map<String,FtpInterestMarginDivide>)request.getAttribute("divideMap");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>���������ָ��������</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
		<style type="text/css">
		input{ width: 50px; border: 1px solid #cfcfcf; text-align: center;}
		input.button {width: 60px;}
		table tr td,table tr th{ text-align: center;	}
		</style>
	</head>
	<body>
	<div class="cr_header" style="margin: 0px 0px 20px 0px;" >
			��ǰλ�ã�����������->���������ָ��������
		</div>
		<form id="form1" name="form1" method="post" action="<%=basePath%>/CKLLFGBLSZ_save.action">
				<table class="table"  cellpadding="0" cellspacing="0" align="center" width="95%">
					<thead>
						<tr>
							<th width="80">
								�ؼ���
							</th>
							<th width="80">
								����
							</th>
							<th width="100">
								3����
						    </th>
							<th width="100">
								6����
							</td>
							<th width="100">
								9����
							</td>
							<th width="100">
								1��
							</th>
						     <th width="100">
								2��
						    </th>
						     <th width="100">
								3��
						    </th>
<%--						     <th width="100">--%>
<%--								4��--%>
<%--						    </th>--%>
						     <th width="100">
								5��
						    </th>
<%--						     <th width="100">--%>
<%--								6��--%>
<%--						    </th>--%>
<%--						     <th width="100">--%>
<%--								7��--%>
<%--						    </th>--%>
<%--						     <th width="100">--%>
<%--								8��--%>
<%--						    </th>--%>
<%--						     <th width="100">--%>
<%--								9��--%>
<%--						    </th>--%>
						     <th width="100">
								10��
						    </th>
						</tr>
					</thead>
					<tbody>
					<tr>
						<th>���</th>
						<td></td>
						<td>
							<!-- 3�� -->
							<input  name="pos[0].id" id="pos[0].id" value="<%=CastUtil.trimNull(map.get("3M-02").getId()) %>" type="hidden"/>
							<input  name="pos[0].rate" id="3M-02"  maxlength="4" onblur="javascript:sumV('3M','02')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("3M-02").getRate(),3) %>" type="text"/>
						</td>
						<td>
							<!-- 6�� -->
							<input  name="pos[1].id" id="pos[1].id"  value="<%=CastUtil.trimNull(map.get("6M-02").getId()) %>" type="hidden"/>
							<input  name="pos[1].rate" id="6M-02" maxlength="4"   onblur="javascript:sumV('6M','02')" onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("6M-02").getRate(),3) %>" type="text"/>
						</td>
						<td>
							<!-- 9�� -->
							<input  name="pos[2].id" id="pos[2].id"  value="<%=CastUtil.trimNull(map.get("9M-02").getId()) %>" type="hidden"/>
							<input  name="pos[2].rate" id="9M-02" maxlength="4"   onblur="javascript:sumV('9M','02')" onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("9M-02").getRate(),3) %>" type="text"/>
						</td>
						<td>
							<!-- 1�� -->
							<input  name="pos[3].id" id="pos[3].id"  value="<%=CastUtil.trimNull(map.get("1Y-02").getId()) %>" type="hidden"/>
							<input  name="pos[3].rate" id="1Y-02"    maxlength="4"  onblur="javascript:sumV('1Y','02')" onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("1Y-02").getRate(),3) %>" type="text"/>
						</td>
						<td>
							<!-- 2�� -->
							<input  name="pos[4].id" id="pos[4].id"  value="<%=CastUtil.trimNull(map.get("2Y-02").getId()) %>" type="hidden"/>
							<input  name="pos[4].rate" id="2Y-02"    maxlength="4"  onblur="javascript:sumV('2Y','02')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("2Y-02").getRate(),3) %>" type="text"/>
						</td>
						<td>
							<!-- 3�� -->
							<input  name="pos[5].id" id="pos[5].id"  value="<%=CastUtil.trimNull(map.get("3Y-02").getId()) %>" type="hidden"/>
							<input  name="pos[5].rate" id="3Y-02"   maxlength="4"   onblur="javascript:sumV('3Y','02')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("3Y-02").getRate(),3) %>" type="text"/>
						</td>
<%--						<td>--%>
<%--							<!-- 4�� -->--%>
<%--							<input  name="pos[24].id" id="pos[24].id"  value="<%=CastUtil.trimNull(map.get("4Y-02").getId()) %>" type="hidden"/>--%>
<%--							<input  name="pos[24].rate" id="4Y-02"   maxlength="4"   onblur="javascript:sumV('4Y','02')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("4Y-02").getRate(),3) %>" type="text"/>--%>
<%--						</td>--%>
						<td>
							<!-- 5�� -->
							<input  name="pos[6].id" id="pos[6].id"  value="<%=CastUtil.trimNull(map.get("5Y-02").getId()) %>" type="hidden"/>
							<input  name="pos[6].rate" id="5Y-02"    maxlength="4"  onblur="javascript:sumV('5Y','02')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("5Y-02").getRate(),3) %>" type="text"/>
						</td>
<%--						<td>--%>
<%--							<!-- 6�� -->--%>
<%--							<input  name="pos[13].id" id="pos[13].id"  value="<%=CastUtil.trimNull(map.get("6Y-02").getId()) %>" type="hidden"/>--%>
<%--							<input  name="pos[13].rate" id="6Y-02"    maxlength="4"  onblur="javascript:sumV('6Y','02')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("6Y-02").getRate(),3) %>" type="text"/>--%>
<%--						</td>--%>
<%--						<td>--%>
<%--							<!-- 7�� -->--%>
<%--							<input  name="pos[15].id" id="pos[15].id"  value="<%=CastUtil.trimNull(map.get("7Y-02").getId()) %>" type="hidden"/>--%>
<%--							<input  name="pos[15].rate" id="7Y-02"    maxlength="4"  onblur="javascript:sumV('7Y','02')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("7Y-02").getRate(),3) %>" type="text"/>--%>
<%--						</td>--%>
<%--						<td>--%>
<%--							<!-- 8�� -->--%>
<%--							<input  name="pos[16].id" id="pos[16].id"  value="<%=CastUtil.trimNull(map.get("8Y-02").getId()) %>" type="hidden"/>--%>
<%--							<input  name="pos[16].rate" id="8Y-02"    maxlength="4"  onblur="javascript:sumV('8Y','02')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("8Y-02").getRate(),3) %>" type="text"/>--%>
<%--						</td>--%>
<%--						<td>--%>
<%--							<!-- 9�� -->--%>
<%--							<input  name="pos[17].id" id="pos[17].id"  value="<%=CastUtil.trimNull(map.get("9Y-02").getId()) %>" type="hidden"/>--%>
<%--							<input  name="pos[17].rate" id="9Y-02"    maxlength="4"  onblur="javascript:sumV('9Y','02')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("9Y-02").getRate(),3) %>" type="text"/>--%>
<%--						</td>--%>
						<td>
							<!-- 10�� -->
							<input  name="pos[7].id" id="pos[7].id"  value="<%=CastUtil.trimNull(map.get("10Y-02").getId()) %>" type="hidden"/>
							<input  name="pos[7].rate" id="10Y-02"    maxlength="4"  onblur="javascript:sumV('10Y','02')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("10Y-02").getRate(),3) %>" type="text"/>
						</td>
					</tr>
					
					<tr>
						<th>����</th>
						<td></td>
						<td>
							<!-- 3�� -->
							<input  name="pos[8].id" id="pos[8].id"   value="<%=CastUtil.trimNull(map.get("3M-01").getId()) %>" type="hidden"/>
							<input  name="pos[8].rate" id="3M-01"    maxlength="4"  onblur="javascript:sumV('3M','01')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("3M-01").getRate(),3) %>" type="text"/>
						</td>
						<td>
							<!-- 6�� -->
							<input  name="pos[9].id" id="pos[9].id"  value="<%=CastUtil.trimNull(map.get("6M-01").getId()) %>" type="hidden"/>
							<input  name="pos[9].rate" id="6M-01"   maxlength="4" onblur="javascript:sumV('6M','01')" onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("6M-01").getRate(),3) %>" type="text"/>
						</td>
						<td>
							<!-- 6�� -->
							<input  name="pos[10].id" id="pos[10].id"  value="<%=CastUtil.trimNull(map.get("9M-01").getId()) %>" type="hidden"/>
							<input  name="pos[10].rate" id="9M-01"   maxlength="4" onblur="javascript:sumV('9M','01')" onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("9M-01").getRate(),3) %>" type="text"/>
						</td>
						<td>
							<!-- 1�� -->
							<input  name="pos[11].id" id="pos[11].id"  value="<%=CastUtil.trimNull(map.get("1Y-01").getId()) %>" type="hidden"/>
							<input  name="pos[11].rate" id="1Y-01"   maxlength="4"  onblur="javascript:sumV('1Y','01')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("1Y-01").getRate(),3) %>" type="text"/>
						</td>
						<td>
							<!-- 2�� -->
							<input  name="pos[12].id" id="pos[12].id"  value="<%=CastUtil.trimNull(map.get("2Y-01").getId()) %>" type="hidden"/>
							<input  name="pos[12].rate" id="2Y-01"   maxlength="4"  onblur="javascript:sumV('2Y','01')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("2Y-01").getRate(),3) %>" type="text"/>
						</td>
						<td>
							<!-- 3�� -->
							<input  name="pos[13].id" id="pos[13].id"  value="<%=CastUtil.trimNull(map.get("3Y-01").getId()) %>" type="hidden"/>
							<input  name="pos[13].rate" id="3Y-01"   maxlength="4"  onblur="javascript:sumV('3Y','01')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("3Y-01").getRate(),3) %>" type="text"/>
						</td>
<%--						<td>--%>
<%--							<!-- 4�� -->--%>
<%--							<input  name="pos[25].id" id="pos[25].id"  value="<%=CastUtil.trimNull(map.get("4Y-01").getId()) %>" type="hidden"/>--%>
<%--							<input  name="pos[25].rate" id="4Y-01"   maxlength="4"  onblur="javascript:sumV('4Y','01')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("4Y-01").getRate(),3) %>" type="text"/>--%>
<%--						</td>--%>
						<td>
							<!-- 5�� -->
							<input  name="pos[14].id" id="pos[14].id"  value="<%=CastUtil.trimNull(map.get("5Y-01").getId()) %>" type="hidden"/>
							<input  name="pos[14].rate" id="5Y-01"   maxlength="4"  onblur="javascript:sumV('5Y','01')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("5Y-01").getRate(),3) %>" type="text"/>
						</td>
<%--						<td>--%>
<%--							<!-- 6�� -->--%>
<%--							<input  name="pos[6].id" id="pos[6].id"  value="<%=CastUtil.trimNull(map.get("6Y-01").getId()) %>" type="hidden"/>--%>
<%--							<input  name="pos[6].rate" id="6Y-01"   maxlength="4"  onblur="javascript:sumV('6Y','01')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("6Y-01").getRate(),3) %>" type="text"/>--%>
<%--						</td>--%>
<%--						<td>--%>
<%--							<!-- 7�� -->--%>
<%--							<input  name="pos[20].id" id="pos[20].id"  value="<%=CastUtil.trimNull(map.get("7Y-01").getId()) %>" type="hidden"/>--%>
<%--							<input  name="pos[20].rate" id="7Y-01"   maxlength="4"  onblur="javascript:sumV('7Y','01')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("7Y-01").getRate(),3) %>" type="text"/>--%>
<%--						</td>--%>
<%--						<td>--%>
<%--							<!-- 8�� -->--%>
<%--							<input  name="pos[21].id" id="pos[21].id"  value="<%=CastUtil.trimNull(map.get("8Y-01").getId()) %>" type="hidden"/>--%>
<%--							<input  name="pos[21].rate" id="8Y-01"   maxlength="4"  onblur="javascript:sumV('8Y','01')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("8Y-01").getRate(),3) %>" type="text"/>--%>
<%--						</td>--%>
<%--						<td>--%>
<%--							<!-- 9�� -->--%>
<%--							<input  name="pos[22].id" id="pos[22].id"  value="<%=CastUtil.trimNull(map.get("9Y-01").getId()) %>" type="hidden"/>--%>
<%--							<input  name="pos[22].rate" id="9Y-01"   maxlength="4"  onblur="javascript:sumV('9Y','01')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("9Y-01").getRate(),3) %>" type="text"/>--%>
<%--						</td>--%>
						<td>
							<!-- 10�� -->
							<input  name="pos[15].id" id="pos[15].id"  value="<%=CastUtil.trimNull(map.get("10Y-01").getId()) %>" type="hidden"/>
							<input  name="pos[15].rate" id="10Y-01"   maxlength="4"  onblur="javascript:sumV('10Y','01')"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=CommonFunctions.doublecut(map.get("10Y-01").getRate(),3) %>" type="text"/>
						</td>
					</tr>
					<tr>
						<td colspan="15" align="center">
							<input name="query" class="button" type="button" id="query" height="20" onClick="javascript:doSubmit();" value="��&nbsp;&nbsp;��" /> 
		      				<input name="back" class="button" type="reset" id="back" height="20"  value="��&nbsp;&nbsp;��" />
						</td>
					</tr>
					</tbody>
				</table>
		</form>
	</body>
<script language="javascript">
function doSubmit(){
	var types=new Array("3M","6M","9M","1Y","2Y","3Y","5Y","10Y");
	for(var i=0;i<types.length;i++){
		var objName1=types[i]+"-01",objName2=types[i]+"-02";
		var obj1=document.getElementById(objName1).value;
		var obj2=document.getElementById(objName2).value;
		//alert(obj1);
		//alert(obj2);
		var result=parseFloat(obj1,10) + parseFloat(obj2,10);
		//alert(result);
		if(result!=1){
			types[i]=types[i].replace("M","����").replace("Y","��");
			var content="["+types[i]+"]  �����㷨����!!����!!   (��ͬ���޵Ĵ������ӵ���1)";
			 art.dialog({
		         title:'��ʾ',
		         icon: 'error',
		         content: content,
		         ok: function () {
		          	document.getElementById(objName1).select();
		          }
		      });
			return;
		}
	}
	document.getElementById("form1").submit();
} 	  
function sumV(val1,val2){
	var obj1=document.getElementById(val1+"-"+val2);
	var obj2Id=val1;
	if(val2=='01'){
		obj2Id+='-02';
	}else{
		obj2Id+='-01';
	}
	if(obj1.value==""){
		obj1.value=0;
	}
	var obj2=document.getElementById(obj2Id);
	var param=1-parseFloat(obj1.value,10).toFixed(3);
	if(param<0){
		alert("���ݲ��ܴ���1��С��0 !!! ����");
		/* obj1.select(); */
		return ;
	}else{
	obj1.select();
	}
	obj1.value=parseFloat(obj1.value,10).toFixed(2);
	obj2.value=parseFloat(param,10).toFixed(2);
}
</script>
</html>
