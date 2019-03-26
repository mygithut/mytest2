<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312" %>
<%@ page
        import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst" %>
<html>
<head>
    <title>统计报表-全行所有机构FTP利润排名表</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
    <jsp:include page="../commonJs.jsp"/>
    <jsp:include page="../commonDatePicker.jsp"/>
    <style type="text/css">
        .ui-datepicker-calendar {
            display: none;
        }
    </style>
</head>
<body>
<div class="cr_header">
    当前位置：统计报表->机构报表->全行所有机构FTP利润排名表
</div>
<% TelMst telmst = (TelMst) request.getSession().getAttribute("userBean");

    String nowDate = String.valueOf(CommonFunctions.GetDBTodayDate());
%>
<form name="thisform" action="" method="post">
    <table width="80%" border="0" align="center">
        <tr>
            <td>
                <table width="900" border="0" align="left" class="table">
                    <tr>
                        <td class="middle_header" colspan="4">
                            <font
                                    style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">查询</font>
                        </td>
                    </tr>
                    <tr>
                        <td width="15%" align="right">
                            考核维度：
                        </td>
                        <td width="15%">
                            <select id="assessScope" style="width: 100" onchange="changeAssessScope(this.value)">
                                <option value="-1">
                                    月度
                                </option>
                                <option value="-3">
                                    季度
                                </option>
                                <option value="-12">
                                    年度
                                </option>
                            </select>
                        </td>
                        <td width="15%" align="right">
                            日&nbsp;&nbsp;期：
                        </td>
                        <td width="15%">
                            <select style="width: 100;" name="date" id="date">
                            </select>
                        </td>
                       <%-- <td width="15%" align="right">机构统计级别：</td>
                        <td>
                            <select id="brCountLvl" style="width:100">
                                <option value="1">支行</option>
                                <option value="0">分理处</option>
                            </select>
                        </td>--%>
                    </tr>
                    <c:choose>
                        <c:when test="${sessionScope.userBean.telNo=='200001'||sessionScope.userBean.telNo=='000000'||sessionScope.userBean.telNo=='200002'}">
                            <tr>
                                <td align="right" colspan="2">
                                    <input type="button" name="Submit1" class="button"
                                           onClick="doQuery()" value="查&nbsp;&nbsp;询">
                                </td>
                                <td align="left" colspan="2">
                                    <input type="button" name="Submit1" class="button"
                                           onClick="doUpdate()" value="更新统计报表">
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td align="center" colspan="4">
                                    <input type="button" name="Submit1" class="button"
                                           onClick="doQuery()" value="查&nbsp;&nbsp;询">
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </table>
            </td>
        </tr>
        <tr height="350">
            <td align="left" height="350">
                <iframe src="" id="downFrame" width="900" height="100%"
                        frameborder="no" border="0" marginwidth="0" marginheight="0"
                        scrolling="no" align="middle"></iframe>
            </td>
        </tr>
    </table>
</form>
</body>
<script type="text/javascript">
    jQuery(function () {// dom元素加载完毕
        jQuery(".table tr:even").addClass("tr-bg1");
        jQuery(".table tr:odd").addClass("tr-bg2");
    });

    function changeAssessScope(assessScope) {
        fillSelectLast('date', '<%=request.getContextPath()%>/fillSelect_getReportDate.action?assessScope=' + assessScope + '&nowDate=<%=nowDate%>', '<%=nowDate%>');
    }

    function doQuery() {
        if (!isNull(document.getElementById("date"), "日期")) {
            return;
        }
        var date = document.getElementById("date").value;
        var assessScope = document.getElementById("assessScope");
        var assessScopeText = assessScope.options[assessScope.selectedIndex].text;
       /* var brCountLvl = document.getElementById("brCountLvl");*/
        var brCountLvl = "1";
      /*  var brCountLvlText = brCountLvl.options[brCountLvl.selectedIndex].text;*/
        var brCountLvlText = "支行";
        //直接将action计算完的页面赋值给iframe：当action花时太长时(大概6、7分钟以上时)，就会出现action计算得到的页面最终实际没有赋值给iframe【action计算的页面自身是会加载形成的，但形成后没有赋值给iframe】
        parent.parent.parent.openNewDiv();
        var url = "<%=request.getContextPath()%>/REPORTBB_qhsyjglrpmbReport.action";
        new Ajax.Request(url, {
            method: 'post',
            parameters: {
                assessScopeText: assessScopeText,
                date: date,
                assessScope: assessScope.value,
                brCountLvl: brCountLvl,
                brCountLvlText: brCountLvlText
            },
            onSuccess: function () {
                document.getElementById("downFrame").src = "pages/tjbb/reportBb_qhsyjglrpmb_list.jsp";
            }
        });
    }

    function doUpdate() {
        parent.parent.parent.openNewDiv();
        var brNo = <%=telmst.getBrMst().getBrNo()%>;
        var manageLvl =<%=telmst.getBrMst().getManageLvl()%>;
        var date = document.getElementById("date").value;
        var assessScope = document.getElementById("assessScope").value;
        var url = "<%=request.getContextPath()%>/REPORTBB_updateTjbb.action";
        new Ajax.Request(url, {
            method: 'post',
            parameters: {
                brNo: brNo, manageLvl: manageLvl, date: date, assessScope: assessScope, t: new Date().getTime()
            },
            onSuccess: function (res) {
                parent.parent.parent.cancel();
                if (res.responseText == '1') {
                    art.dialog({
                        title: '成功',
                        icon: 'succeed',
                        content: '报表更新成功，请稍后查看！',
                        cancelVal: '关闭',
                        cancel: true
                    });
                } else {
                    art.dialog({
                        title: '提示',
                        icon: 'warning',
                        content: '报表正在更新，请稍后再试！',
                        cancelVal: '关闭',
                        cancel: true
                    });
                }

            }
        });
    }

    fillSelectLast('date', '<%=request.getContextPath()%>/fillSelect_getReportDate.action?assessScope=-1&nowDate=<%=nowDate%>', '<%=nowDate%>');

</script>
</html>
