<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page
	import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.entity.TelMst"%>
<html>
	<head>
		<title>期限匹配-期限匹配产品定价方法配置</title>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/themes/green/css/core.css"
			type="text/css">
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/css/inpage.css"
			type="text/css">
		<jsp:include page="commonJs.jsp" />
	</head>
	<body>
	<%
	TelMst telmst = (TelMst) request.getSession().getAttribute("userBean");
	%>
		<div class="cr_header">
			当前位置：期限匹配->期限匹配产品定价方法配置
		</div>
		<form name="thisform" action="" method="post">
			<table width="90%" border="0" align="center">
				<tr>
					<td>
						<table width="1000" align="left" class="table">
							<tr>
								<td class="middle_header" colspan="4">
									<font
										style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">查询</font>
								</td>
							</tr>
							<tr>
								<td width="20%" align="right">机构名称：</td>
								<td width="30%">
								<select name="brNo" id="brNo"></select>
								</td>
								<td width="20%" align="right">
									选择币种：
								</td>
								<td>
									<select style="width: 120" name="curNo" id="curNo">
									</select>
								</td>
								
							</tr>
							<tr>
								<td width="20%" align="right">
									业务类型：
								</td>
								<td width="30%">
									<select name="businessNo" id="businessNo"
										onchange="getPrdtName(this.id)">
									</select>
								</td>
								<td width="20%" align="right">
									产品名称：
								</td>
								<td>
									<select name="productNo" id="productNo" disabled="disabled">
										<option value="">
											先选择业务类型
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td align="center" colspan="4">
									<input name="query" class="button" type="button" id="query"
										height="20" onClick=
	onclick_query();
value="查&nbsp;&nbsp;询" />
									<input name="back" class="button" type="button" id="back"
										height="20" onClick=
	onclick_back();
value="重&nbsp;&nbsp;置" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>
						<iframe src="" id="downframe" name="downframe" width="1000"
							height="350" frameborder="no" border="0" marginwidth="0"
							marginheight="0" scrolling="no" allowtransparency="yes"
							align="middle"></iframe>
					</td>
				</tr>
			</table>

		</form>
	</body>
	<script type="text/javascript" language="javascript">
	jQuery(function(){// dom元素加载完毕
		jQuery(".table tr:even").addClass("tr-bg1");
		jQuery(".table tr:odd").addClass("tr-bg2");
	});
	fillSelect('curNo', '<%=request.getContextPath()%>/queryCurrency.action');
    fillSelect('businessNo', '<%=request.getContextPath()%>/fillSelect_getBusinessName.action');
    fillSelectLast('brNo','fillSelect_getBrNoByLvl2_sylqx','<%=FtpUtil.getXlsBrNo(telmst.getBrMst().getBrNo(),telmst.getBrMst().getManageLvl())%>');
	function onclick_query() {
	    var brNo =document.getElementById("brNo").value;
	    //alert(brNo);
		var curNo = document.getElementById("curNo").value;
		var businessNo = document.getElementById("businessNo").value;
		var productNo = document.getElementById("productNo").value;   
		window.frames.downframe.location.href = '<%=request.getContextPath()%>/QXPPCPDJFFPZ_list.action?brNo='+brNo+'&curNo='
				+ curNo
				+ '&businessNo='
				+ businessNo
				+ '&productNo='
				+ productNo;
	}
	function onclick_back() {
		window.location.reload();
	}
	function getPrdtName(businessNo) {
	    var business = $(businessNo).value;
		if (business != '') {
			$("productNo").disabled = false;
			document.getElementById("productNo").options.length = 0;
			document.getElementById("productNo").add(new Option("--请选择--", ""));
			var prdtNameList;
			var url = '<%=request.getContextPath()%>/fillSelect_getPrdtName.action?businessNo=' + business;
			new Ajax.Request(url, {
				method : 'post',
				parameters : {
					t : new Date().getTime()
				},
				onSuccess : function(transport) {
					prdtNameList = transport.responseText.split(",");
					$A(prdtNameList).each(function(index) {
						//Option参数，前面是text，后面是value
							var opt = new Option(index.split('|')[1], index
									.split('|')[0]);
							document.getElementById("productNo").add(opt);
						});
				}
			});
		} else {
			$("productNo").disabled = true;
		}

	}
</script>
</html>
