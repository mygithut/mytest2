<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ page
        import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.util.*"
        errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>数据管理-员工账户关联结果查询</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
    <jsp:include page="../commonJs.jsp"/>
</head>
<%
    PageUtil YGZHGLUtil = (PageUtil) request.getAttribute("YGZHGLUtil");
    List list = YGZHGLUtil.getList();
    Map map = (Map) request.getAttribute("map");
    Map brMap = (Map) request.getAttribute("brMap");

%>
<body>
<form name="form1" method="post">

    <%--			<tr><td align="center">--%>
    <%--           <input name="add" class="button" type="button" id="add" height="20" onClick="ftpHistory()" value="查看历史" />              --%>
    <%--  </td></tr>--%>
    <div style="width:100%;height:290px;overflow-x:auto;overflow-y:auto">
        <table id="tableList" class="table" width="1700">
            <tr>
                <th width="200">
                    客户经理
                </th>
                <th width="150">
                    所属机构
                </th>
                <th width="150">
                    开户机构
                </th>
                <th width="150">
                    业务账号
                </th>
                <th width="180">
                    客户名
                </th>
                <%--		<th width="80">--%>
                <%--			柜员--%>
                <%--		</th>--%>
                <%--		<th width="100">--%>
                <%--			业务类型--%>
                <%--		</th>--%>
                <th width="250">
                    产品名称
                </th>
                <th width="80">
                    开户日期
                </th>
                <th width="100">
                    金额
                </th>
                <th width="100">
                    余额
                </th>
                <th width="80">
                    利率(%)
                </th>
                <th width="80">
                    期限(天)
                </th>
                <th width="80">
                    到期日期
                </th>
            </tr>
            <%
                for (int i = 0; i < list.size(); i++) {
                    Object[] obj = (Object[]) list.get(i);
            %>
            <tr>
                <td align="center" width="200">
                    <%
                        if (obj[13] != null && !obj[13].equals("") && !obj[13].equals("null")) {
                            String[] tempno = obj[13].toString().split("@");
                            String[] temprate = obj[14].toString().split("@");
                            for (int j = 0; j < tempno.length; j++) {
                    %>
                    <%=map.get(tempno[j]) == null ? "-" : map.get(tempno[j])%>(<%
                    if (map.get(tempno[j]) != null && j < tempno.length) {
                        if (Double.parseDouble(temprate[j]) <= 1.0 && obj[17].toString().equals("1")) {
                %><%=Double.parseDouble(temprate[j]) * 100.0%>%)<%} else { %><%=Integer.parseInt(temprate[j])%>)<%
                        }
                    }
                    if (j < tempno.length - 1) {
                %>|<%
                        } else {
                        }
                    }
                } else {
                %>-<%} %>
                </td><!-- 客户经理 -->
                <td align="center" width="150"><%=brMap.get(obj[19])%>[<%=CastUtil.trimNull(obj[19])%>]</td><!-- 机构 -->
                <td align="center" width="150"><%=brMap.get(obj[2])%>[<%=CastUtil.trimNull(obj[2])%>]</td><!-- 机构 -->
                <td align="center" width="150">
                    <%if (obj[1] == null) { %>
                    <%=CastUtil.trimNull(obj[18])%>
                    <%} else { %>
                    <%=CastUtil.trimNull(obj[1])%>
                    <%} %>
                </td><!-- 业务账号 -->
                <td align="center" width="180"><%=CastUtil.trimNull(obj[4])%>
                </td><!-- 客户名 -->
                <%--     	<td align="center"><%=CastUtil.trimNull(obj[5])%></td><!-- 柜员 -->--%>
                <%--     	<td align="center"><%=CastUtil.trimNull(obj[7])%></td><!-- 业务类型 -->--%>
                <td align="center" width="150"><%=CastUtil.trimNull(obj[5])%>
                </td><!-- 名称 -->
                <td align="center" width="80"><%=CastUtil.trimNull(obj[6])%>
                </td><!-- 开户日期 -->
                <td align="center" width="100"><%=FormatUtil.toMoney(CastUtil.trimNull(obj[7]))%>
                </td><!-- 金额 -->
                <td align="center" width="100"><%=FormatUtil.toMoney(CastUtil.trimNull(obj[8]))%>
                </td><!-- 余额 -->
                <td align="center"
                    width="80"><%=CommonFunctions.doublecut(Double.valueOf(CastUtil.trimNullOrBlank(obj[9])), 3)%>
                </td><!-- 利率(%) -->
                <td align="center" width="80"><%=CastUtil.trimNull(obj[10])%>
                </td><!-- 期限（天） -->
                <td align="center" width="80"><%=CastUtil.trimNull(obj[11])%>
                    <input type="hidden" name="prdtNo" value="<%=CastUtil.trimNull(obj[16])%>"/>
                    <input type="hidden" name="isRelated" value="<%=CastUtil.trimNull(obj[12])%>"/>
                </td><!-- 到期日期-->

            </tr>
            <%
                }
            %>
        </table>
    </div>
    <table border="0" width="80%" class="tb1"
           style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
           align="center">
        <tr>
            <td align="right"><%=YGZHGLUtil.getPageLine()%>
            </td>
        </tr>
    </table>
</form>

</body>

<script type="text/javascript">
    jQuery(document).ready(function () {
        parent.parent.parent.parent.cancel();
    });
    <%--j(function(){--%>
    <%--    j('#tableList').flexigrid({--%>
    <%--    		height: 240,width:1000,--%>
    <%--    		title: '员工账户关联列表',--%>
    <%--    		theFirstIsShown: false,--%>
    <%--    		buttons : [--%>
    <%--   			   		{name: '批量关联', bclass: 'export', onpress : do_Batch},--%>
    <%--   			      	{name: '取消关联', bclass: 'delete', onpress : do_delete}--%>
    <%--   			   		]});--%>
    <%--});--%>

    function doSelected(obj) {
        var o;
        var num = 0;
        o = document.getElementsByName("checkbox");
        for (var i = 0; i < o.length; i++) {
            if (o[i].checked) {
                num++;
            }
        }
        if (num > 10) {
            alert("最多查看十条记录！！");
            obj.checked = false;
        }
    }

    function doRelated(ac_id, empNo, rate, relType, prdtNo) {
        <%--	window.parent.location.href='<%=request.getContextPath()%>/pages/ygzhglEmpSearch.jsp?ac_id='+ac_id+'&empNo='+empNo+'&rate='+rate+'&Rnd='+Math.random();--%>
        <%--	art.dialog.open('<%=request.getContextPath()%>/pages/ygzhglEmpSearch.jsp?ac_id='+ac_id+'&empNo='+empNo+'&rate='+rate+'&Rnd='+Math.random(), {--%>
        art.dialog.open('<%=request.getContextPath()%>/pages/sjgl/ygzhglRelated.jsp?ac_id=' + ac_id + '&empNo=' + empNo + '&rate=' + rate + '&relType=' + relType + '&prdtNo=' + prdtNo + '&Rnd=' + Math.random(), {
            title: '员工账户关联操作，账户:' + ac_id,
            width: 850,
            height: 380
        });
        <%--   parent.parent.parent.parent.openNewDiv();--%>
    }

    function do_Batch() {
        var o = document.getElementsByName("checkbox");
        var p = document.getElementsByName("prdtNo");
        var ac_id = [];
        var prdtNo = "";
        for (var i = 0; i < o.length; i++) {
            if (o[i].checked) {
                ac_id.push(o[i].value);
                prdtNo += p[i].value + ",";
            }
        }
        if (ac_id.length == 0) {
            art.dialog({
                title: '提示',
                icon: 'error',
                content: '请选择一项！',
                ok: function () {
                    return;
                }
            });
            return;
        }

        <%--	alert("ac_ids:"+ac_id);--%>
        <%--    window.parent.location.href='<%=request.getContextPath()%>/pages/ygzhglEmpSearch.jsp?ac_ids='+ac_ids+'&Rnd='+Math.random();--%>
        <%--	art.dialog.open('<%=request.getContextPath()%>/pages/ygzhglEmpSearch.jsp?ac_ids='+ac_ids+'&Rnd='+Math.random(), {--%>
        <%--		    title: '员工账户关联操作',--%>
        <%--		    width: 650,--%>
        <%--		    height:380--%>
        <%--		});	--%>
        art.dialog.open('<%=request.getContextPath()%>/pages/sjgl/ygzhglRelated.jsp?ac_id=' + ac_id + '&prdtNo=' + prdtNo + '&Rnd=' + Math.random(), {
            title: '员工账户关联操作',
            width: 850,
            height: 380
        });
    }

    function do_delete() {
        var o;
        o = document.getElementsByName("checkbox");
        var isRelated = document.getElementsByName("isRelated");
        var ac_id = "";
        var num = 0;

        for (var i = 0; i < o.length; i++) {
            if (o[i].checked) {
                ac_id += o[i].value + ",";
                if (isRelated[i].value == '1') {
                    num++;
                }
            }
        }

        if (ac_id == "") {
            art.dialog({
                title: '提示',
                icon: 'error',
                content: '请选择一项！',
                ok: function () {
                    <%--   		        close();--%>
                    <%--      		        return true;--%>
                }
            });
            return false;
        } else if (num == 0) {
            art.dialog({
                title: '提示',
                icon: 'error',
                content: '请选择已关联项！',
                ok: function () {
                    <%--			        close();--%>
                    <%-- 		            return true;--%>
                }
            });
            return false;
        } else {
            art.dialog({
                title: '提示',
                icon: 'question',
                content: '确定要取消关联吗?',
                ok: function () {
                    var url = "<%=request.getContextPath()%>/YGZHGL_del.action";
                    new Ajax.Request(
                        url,
                        {
                            method: 'post',
                            parameters: {ac_id: ac_id, t: new Date().getTime()},
                            onSuccess: function (val) {
                                if (val.responseText == "ok") {
                                    art.dialog({
                                        title: '提示',
                                        icon: 'succeed',
                                        content: '操作成功！',
                                        ok: function () {
                                            close();
                                            return true;
                                        }
                                    });
                                } else {
                                    art.dialog({
                                        title: '提示',
                                        icon: 'error',
                                        content: '操作失败！',
                                        ok: function () {
                                            close();
                                            return true;
                                        }
                                    });
                                }

                            }
                        });
                },
                cancelVal: '关闭',
                cancel: true //为true等价于function(){}
            });
        }
    }

    function checkAll() {
        var selectFlags = document.getElementsByName("checkbox");
        for (var i = 0; i < selectFlags.length; i++) {
            selectFlags[i].checked = window.event.srcElement.checked;//通过单击的按钮判断是选中还是未选
        }
    }

    function close() {
        parent.parent.parent.parent.openNewDiv();
        window.location.reload();
    }
</script>
</html>
