<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312" %>
<%@ page
        import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst" %>
<html>
<head>
    <title>ͳ�Ʊ���-ȫ�����л���FTP����������</title>
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
    ��ǰλ�ã�ͳ�Ʊ���->��������->ȫ�����л���FTP����������
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
                                    style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">��ѯ</font>
                        </td>
                    </tr>
                    <tr>
                        <td width="15%" align="right">
                            ����ά�ȣ�
                        </td>
                        <td width="15%">
                            <select id="assessScope" style="width: 100" onchange="changeAssessScope(this.value)">
                                <option value="-1">
                                    �¶�
                                </option>
                                <option value="-3">
                                    ����
                                </option>
                                <option value="-12">
                                    ���
                                </option>
                            </select>
                        </td>
                        <td width="15%" align="right">
                            ��&nbsp;&nbsp;�ڣ�
                        </td>
                        <td width="15%">
                            <select style="width: 100;" name="date" id="date">
                            </select>
                        </td>
                       <%-- <td width="15%" align="right">����ͳ�Ƽ���</td>
                        <td>
                            <select id="brCountLvl" style="width:100">
                                <option value="1">֧��</option>
                                <option value="0">����</option>
                            </select>
                        </td>--%>
                    </tr>
                    <c:choose>
                        <c:when test="${sessionScope.userBean.telNo=='200001'||sessionScope.userBean.telNo=='000000'||sessionScope.userBean.telNo=='200002'}">
                            <tr>
                                <td align="right" colspan="2">
                                    <input type="button" name="Submit1" class="button"
                                           onClick="doQuery()" value="��&nbsp;&nbsp;ѯ">
                                </td>
                                <td align="left" colspan="2">
                                    <input type="button" name="Submit1" class="button"
                                           onClick="doUpdate()" value="����ͳ�Ʊ���">
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td align="center" colspan="4">
                                    <input type="button" name="Submit1" class="button"
                                           onClick="doQuery()" value="��&nbsp;&nbsp;ѯ">
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
    jQuery(function () {// domԪ�ؼ������
        jQuery(".table tr:even").addClass("tr-bg1");
        jQuery(".table tr:odd").addClass("tr-bg2");
    });

    function changeAssessScope(assessScope) {
        fillSelectLast('date', '<%=request.getContextPath()%>/fillSelect_getReportDate.action?assessScope=' + assessScope + '&nowDate=<%=nowDate%>', '<%=nowDate%>');
    }

    function doQuery() {
        if (!isNull(document.getElementById("date"), "����")) {
            return;
        }
        var date = document.getElementById("date").value;
        var assessScope = document.getElementById("assessScope");
        var assessScopeText = assessScope.options[assessScope.selectedIndex].text;
       /* var brCountLvl = document.getElementById("brCountLvl");*/
        var brCountLvl = "1";
      /*  var brCountLvlText = brCountLvl.options[brCountLvl.selectedIndex].text;*/
        var brCountLvlText = "֧��";
        //ֱ�ӽ�action�������ҳ�渳ֵ��iframe����action��ʱ̫��ʱ(���6��7��������ʱ)���ͻ����action����õ���ҳ������ʵ��û�и�ֵ��iframe��action�����ҳ�������ǻ�����γɵģ����γɺ�û�и�ֵ��iframe��
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
                        title: '�ɹ�',
                        icon: 'succeed',
                        content: '������³ɹ������Ժ�鿴��',
                        cancelVal: '�ر�',
                        cancel: true
                    });
                } else {
                    art.dialog({
                        title: '��ʾ',
                        icon: 'warning',
                        content: '�������ڸ��£����Ժ����ԣ�',
                        cancelVal: '�ر�',
                        cancel: true
                    });
                }

            }
        });
    }

    fillSelectLast('date', '<%=request.getContextPath()%>/fillSelect_getReportDate.action?assessScope=-1&nowDate=<%=nowDate%>', '<%=nowDate%>');

</script>
</html>
