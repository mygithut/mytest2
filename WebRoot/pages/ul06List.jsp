<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312" %>
<%@ page
        import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.FtpBusinessInfo,com.dhcc.ftp.util.*"
        errorPage="" %>
<html>
<head>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
    <jsp:include page="commonJs.jsp"/>
    <script type="text/javascript" language=javascript
            src="<%=request.getContextPath()%>/pages/js/prototype.js"></script>
    <title>期限匹配</title>
    <style type="text/css">
        <!--
        .STYLE1 {
            font-size: medium
        }

        .STYLE2 {
            font-size: large
        }

        .STYLE3 {
            font-size: small;
            color: #2907F0;
        }

        -->
    </style>
</head>
<%
    String checkAll = (String) request.getAttribute("checkAll");
    PageUtil UL06Util = (PageUtil) request.getAttribute("UL06Util");
    List<FtpBusinessInfo> list = UL06Util.getList();
%>
<body>
<form name="form1" method="post">
    <table width="100%" border="0" align="center">
        <tr>
            <td>
                <table width="100%" align="left" class="table">
                    <tr>
                        <td class="middle_header">
                            <font style="padding-left:10px; color:#333; font-size:14px;font-weight:bold">查询列表</font>
                        </td>
                    </tr>
                    <tr>
                        <td align="left">
                            <div style="display: flex;justify-content: center;align-items: center">
                                <input style="display: block" type="checkbox" id="checkAll" name="checkAll" value="checkbox" onClick="onclick_all()"/>所有页全选 &nbsp;&nbsp;
                                <input style="display: block" name="doPrice" class="button" type="button" id="doPrice" height="20" onClick="ftp_compute()" value="定&nbsp;&nbsp;价"/>
                                <input style="display: block;margin-left: 10px" name="doExport" class="button" type="button" id="doExport" height="20" onClick="do_Export()" value="导&nbsp;&nbsp;出"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table width="2000" border="0" cellspacing="0" cellpadding="0" align="center" class="table"
                                   id="tbColor">
                                <tr>
                                    <th align="center" width="60">
                                        <input type="checkbox" name="all" value="checkbox" onClick="doCheck()"/>全选
                                    </th>
                                    <th width="40">
                                        序号
                                    </th>
                                    <th width="80">
                                        机构
                                    </th>
                                    <th width="50">
                                        柜员
                                    </th>
                                    <th width="80">
                                        客户经理
                                    </th>
                                    <th width="50">
                                        币种
                                    </th>
                                    <th width="140">
                                        业务账号
                                    </th>
                                    <th width="240">
                                        客户名
                                    </th>
                                    <th width="80">
                                        业务类型
                                    </th>
                                    <th width="250">
                                        产品名称
                                    </th>
                                    <th width="70">
                                        开户日期
                                    </th>
                                    <th width="100">
                                        金额
                                    </th>
                                    <th width="100">
                                        余额
                                    </th>
                                    <th width="70">
                                        利率(%)
                                    </th>
                                    <%--					<th width="80">--%>
                                    <%--						收付利息--%>
                                    <%--					</th>--%>
                                    <th width="80">
                                        期限(天)
                                    </th>
                                    <th width="70">
                                        到期日期
                                    </th>
                                    <th width="70">
                                        是否展期
                                    </th>
                                    <th width="70">
                                        五级分类
                                    </th>
                                    <%--		<th>--%>
                                    <%--			定价结果值--%>
                                    <%--		</th>--%>
                                    <%--		<th>--%>
                                    <%--			定价方法--%>
                                    <%--		</th>--%>
                                    <%--		<th>--%>
                                    <%--			曲线名--%>
                                    <%--		</th>--%>
                                    <%--		<th>--%>
                                    <%--			定价日期--%>
                                    <%--		</th>--%>
                                </tr>
                                <%
                                    for (int i = 0; i < list.size(); i++) {
                                %>
                                <tr>
                                    <td align="center"><input type="checkbox" name="checkbox" value="<%=i%>"/></td>
                                    <td align="center"><%=i + 1%>
                                    </td>
                                    <td align="center"><%=CastUtil.trimNull(list.get(i).getBrNo())%>
                                    </td>
                                    <td align="center"><%=CastUtil.trimNull(list.get(i).getTel())%>
                                    </td>
                                    <td align="center"><%=CastUtil.trimNull(list.get(i).getKhjl())%>
                                    </td>
                                    <td align="center"><%=CastUtil.trimNull(list.get(i).getCurNo())%>
                                    </td>
                                    <td align="center"><%=CastUtil.trimNull(list.get(i).getAcId().indexOf("-") == -1 ? list.get(i).getAcId() : list.get(i).getAcId().substring(0, list.get(i).getAcId().indexOf("-")))%>
                                    </td>
                                    <td align="center"><%=CastUtil.trimNull(list.get(i).getCustomName())%>
                                    </td>
                                    <td align="center"><%=CastUtil.trimNull(list.get(i).getBusinessName())%>
                                    </td>
                                    <td align="center"><%=CastUtil.trimNull(list.get(i).getPrdtName())%>
                                    </td>
                                    <td align="center"><%=CastUtil.trimNull(list.get(i).getOpnDate())%>
                                    </td>
                                    <td align="center"><%=FormatUtil.toMoney(list.get(i).getAmt())%>
                                    </td>
                                    <td align="center"><%=FormatUtil.toMoney(list.get(i).getBal())%>
                                    </td>
                                    <td align="center"><%=list.get(i).getRate() == null ? "" : CommonFunctions.doublecut(Double.valueOf(list.get(i).getRate()) * 100, 3)%>
                                    </td>
                                    <%--     	<td align="center"><%=CastUtil.trimNull(list.get(i).getSjsflx())%></td>--%>
                                    <td align="center"><%=list.get(i).getBusinessNo().equals("YW201") ? "-" : CastUtil.trimNull(list.get(i).getTerm())%>
                                    </td>
                                    <td align="center"><%=list.get(i).getBusinessNo().equals("YW201") ? "-" : CastUtil.trimNull(list.get(i).getMtrDate())%>
                                    </td>
                                    <td align="center"><%=(!list.get(i).getBusinessNo().equals("YW101")) ? "-" : (list.get(i).getIsZq().equals("0") ? "否" : "是")%>
                                    </td>
                                    <td align="center"><%=FtpUtil.getFivSys(list.get(i).getFivSts())%>
                                    </td>
                                </tr>
                                <% } %>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td align="right"><%=UL06Util.getPageLine()%>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
    <input type="hidden" name="date" id="date" value="<%=(String)request.getAttribute("opnDate2") %>"/>
</form>

</body>

<script type="text/javascript">
    jQuery(document).ready(function () {
        parent.parent.parent.parent.cancel();
    });
    var selectFlags = document.getElementsByName("checkbox");
    <%if (checkAll!=null && checkAll.equals("true")){ %>
    document.getElementById("checkAll").checked = true;
    j('all').checked = true;
    for (var i = 0; i < selectFlags.length; i++) {
        selectFlags[i].checked = true;//通过单击的按钮判断是选中还是未选
    }
    <%}else{%>
    document.getElementById("checkAll").checked = false;
    j('all').checked = false;
    for (var i = 0; i < selectFlags.length; i++) {
        selectFlags[i].checked = false;//通过单击的按钮判断是选中还是未选
    }
    <%}%>

    function doCheck() {
        var selectFlags = document.getElementsByName("checkbox");
        for (var i = 0; i < selectFlags.length; i++) {
            selectFlags[i].checked = window.event.srcElement.checked;//通过单击的按钮判断是选中还是未选
        }
    }

    function onclick_all() {
        var checkAll = document.getElementById("checkAll").checked;
        var brNo = window.parent.document.getElementById("brNo").value;
        var curNo = window.parent.document.getElementById("curNo").value;
        var businessNo = window.parent.document.getElementById("businessNo").value;
        var opnDate1 = window.parent.document.getElementById("opnDate1").value;
        var opnDate2 = window.parent.document.getElementById("opnDate2").value;
        var mtrDate1 = window.parent.document.getElementById("mtrDate1").value;
        var mtrDate2 = window.parent.document.getElementById("mtrDate2").value;
        window.location.href = '<%=request.getContextPath()%>/UL06_List.action?checkAll=' + checkAll + '&isQuery=0&brNo=' + brNo + '&curNo=' + curNo + '&businessNo=' + businessNo + '&opnDate1=' + opnDate1 + '&opnDate2=' + opnDate2 + '&mtrDate1=' + mtrDate1 + '&mtrDate2=' + mtrDate2;
    }

    function ftp_compute() {
        var checkAll = document.getElementById("checkAll").checked;
        var date = document.getElementById("date").value;
        var brNo = window.parent.document.getElementById("brNo").value;
        var manageLvl = window.parent.document.getElementById("manageLvl").value;
        if (checkAll) {
            window.location.href = '<%=request.getContextPath()%>/UL06_ftp_compute.action?date=' + date + '&checkAll=' + checkAll + '&brNo=' + brNo + '&manageLvl=' + manageLvl;
        } else {
            var o;
            o = document.getElementsByName("checkbox");
            var m = 0;
            var no = "";
            for (var i = 0; i < o.length; i++) {
                if (o[i].checked) {
                    no = no + o[i].value + ",";
                }
            }
            if (no == "") {
                alert('请先勾选要定价的业务行！');
                return;
            }

            var currentPage = document.getElementById("currentPage").value;
            var pageSize = document.getElementById("pageSize").value;
            window.location.href = '<%=request.getContextPath()%>/UL06_ftp_compute.action?date=' + date + '&currentPage=' + currentPage + '&pageSize=' + pageSize + '&no=' + no + '&checkAll=' + checkAll + '&brNo=' + brNo + '&manageLvl=' + manageLvl;
        }
        parent.parent.parent.parent.openNewDiv();
    }

    function do_Export() {

        document.form1.action = '<%=request.getContextPath()%>/pages/ul06ListExport.jsp';
        document.form1.submit();
    }

</script>
</html>
