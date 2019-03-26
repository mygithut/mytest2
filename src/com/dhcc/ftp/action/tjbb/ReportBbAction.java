package com.dhcc.ftp.action.tjbb;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhcc.ftp.entity.*;
import com.dhcc.ftp.util.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.action.BoBuilder;
import com.dhcc.ftp.cache.CacheOperation;
import com.dhcc.ftp.dao.DaoFactory;

/**
 * FTP盈利分析统计报表
 */
public class ReportBbAction extends BoBuilder {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer assessScope;
    private String date;
    private String brNo;
    private String assessScopeText;
    private String brName;
    private String manageLvl;
    private String businessNo;
    private String businessName;
    private String prdtCtgNo;
    private String prdtCtgName;
    private String prdtNo;
    private String prdtName;
    private String empNo;
    private String empName;
    private String brCountLvl;
    private String brCountLvlText;
    private Integer isMx;//是否是查看子机构的盈利分析
    private Integer isFirst;
    private String prdtType;
    DaoFactory df = new DaoFactory();
    private int page = 1;
    private int pageSize = 10;
    private int rowsCount = -1;
    private PageUtil resultPriceUtil = null;
    HttpServletRequest request = ServletActionContext.getRequest();
    HttpServletResponse response = ServletActionContext.getResponse();

    public String execute() throws Exception {
        return super.execute();
    }

    /**
     * 全行所有机构FTP利润汇总
     *
     * @return
     * @throws Exception
     */
    public String qhsyjglrhzbReport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();

        String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点

        List<YlfxBbReport> qhsyjglrhzbReportList = reportBbBO.brPayOffProfile(request, minDate, date, brNo, brCountLvl, manageLvl, assessScope, isMx);

        request.getSession().setAttribute("qhsyjglrhzbReportList", qhsyjglrhzbReportList);
        if (minDate != null) request.getSession().setAttribute("qhsyjglrhzbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("qhsyjglrhzbMaxDate", date);
//		if(brName != null)request.getSession().setAttribute("qhsyjglrhzbBrName", brName);
        if (brName != null) {
            if (1 == isMx || "true".equals(request.getParameter("isBack"))) { //对是否是"明细"和"返回"操作进行判断;明细和返回时用url传参需要进行转码
                brName = CommonFunctions.Chinese_CodeChange(brName);
            }
            request.getSession().setAttribute("qhsyjglrhzbBrName", brName);
        }
        request.getSession().setAttribute("qhsyjglrhzbIsFirst", isFirst);
        String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
        String exportName = assessScopeName + "_" + date + "_" + brName + "_全行所有机构FTP利润汇总表";
        request.getSession().setAttribute("exportName", exportName);
        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "qhsyjglrhzbList";
    }

    /**
     * 全行所有机构FTP利润排名
     *
     * @return
     * @throws Exception
     */
    public String qhsyjglrpmbReport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();
        String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点
        List<YlfxBbReport> qhsyjglrpmbReportList = reportBbBO.brPayOffRanking(request, minDate, date, brCountLvl, assessScope);
        request.getSession().setAttribute("qhsyjglrpmbReportList", qhsyjglrpmbReportList);
        if (minDate != null) request.getSession().setAttribute("qhsyjglrpmbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("qhsyjglrpmbMaxDate", date);
        if (brCountLvl != null) request.getSession().setAttribute("qhsyjglrpmbBrCountLvl", brCountLvl);
        String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
        String brCountName = "支行";
        if ("1".equals(brCountLvl)) {
            brCountName = "支行";
        } else {
            brCountName = "分理处";
        }
        String exportName = assessScopeName + "_" + date + "_" + brCountName + "_全行所有机构FTP利润排名表";
        request.getSession().setAttribute("exportName", exportName);
        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "qhsyjglrpmbList";
    }

    /**
     * 机构业务条线FTP利润汇总表
     *
     * @return
     * @throws Exception
     */
    public String jgywtxlrhzbReport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();
        String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点
        List<YlfxBbReport> jgywtxlrhzbReportList = reportBbBO.busPayOffProfile(request, minDate, date, brNo, manageLvl, assessScope);
        request.getSession().setAttribute("jgywtxlrhzbReportList", jgywtxlrhzbReportList);
        if (minDate != null) request.getSession().setAttribute("jgywtxlrhzbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("jgywtxlrhzbMaxDate", date);
        if (brName != null) request.getSession().setAttribute("jgywtxlrhzbBrName", brName);
        String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
        String exportName = assessScopeName + "_" + date + "_" + brName + "_机构业务条线FTP利润汇总表";
        request.getSession().setAttribute("exportName", exportName);
        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "jgywtxlrhzbList";
    }

    /**
     * 机构各条线下分产品FTP利润明细表
     *
     * @return
     * @throws Exception
     */
    public String jggtxxfcplrmxbReport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();
        String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点
        List<YlfxBbReport> jggtxxfcplrmxbReportList = reportBbBO.prdtPayOffProfile(request, minDate, date, brNo, manageLvl, assessScope, businessNo);
        request.getSession().setAttribute("jggtxxfcplrmxbReportList", jggtxxfcplrmxbReportList);
        if (minDate != null) request.getSession().setAttribute("jggtxxfcplrmxbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("jggtxxfcplrmxbMaxDate", date);
        if (businessName != null) {
            businessName = CommonFunctions.Chinese_CodeChange(businessName);
            request.getSession().setAttribute("jggtxxfcplrmxbBusinessName", businessName);
        }
        brName = CommonFunctions.Chinese_CodeChange(brName);;
        String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
        String exportName = assessScopeName + "_" + date + "_" + brName + "_机构各条线下分产品FTP利润明细表";
        request.getSession().setAttribute("exportName", exportName);

        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "jggtxxfcplrmxbList";
    }

    /**
     * 产品分机构FTP利润表
     *
     * @return
     * @throws Exception
     */
    public String cpfjglrbReport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();
        String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点
        List<YlfxBbReport> cpfjglrbReportList = reportBbBO.prdtBrProfile(request, minDate, date, brNo, manageLvl, assessScope, businessNo, prdtCtgNo, prdtNo, isMx);
        request.getSession().setAttribute("cpfjglrbReportList", cpfjglrbReportList);
        if (minDate != null) request.getSession().setAttribute("cpfjglrbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("cpfjglrbMaxDate", date);
//		if(brName != null)request.getSession().setAttribute("cpfjglrbBrName", brName);
        if (brName != null) {
            if (1 == isMx || "true".equals(request.getParameter("isBack"))) { //对是否是"明细"和"返回"操作进行判断;明细和返回时用url传参需要进行转码
                brName = CommonFunctions.Chinese_CodeChange(brName);
            }
            request.getSession().setAttribute("cpfjglrbBrName", brName);
        }
        if (businessName != null) request.getSession().setAttribute("cpfjglrbBusinessName", businessName);
        if (prdtCtgName != null) request.getSession().setAttribute("cpfjglrbPrdtCtgName", prdtCtgName);
        if (prdtCtgNo != null) request.getSession().setAttribute("cpfjglrbPrdtCtgNo", prdtCtgNo);
        if (prdtName != null) request.getSession().setAttribute("cpfjglrbPrdtName", prdtName);
        if (prdtNo != null) request.getSession().setAttribute("cpfjglrbPrdtNo", prdtNo);
        String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
        String exportName = assessScopeName + "_" + date + "_" + brName + "_产品分机构FTP利润表";
        request.getSession().setAttribute("exportName", exportName);
        request.getSession().setAttribute("cpfjglrbIsFirst", isFirst);
        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "cpfjglrbList";
    }

    /**
     * 产品分客户经理FTP利润表
     *
     * @return
     * @throws Exception
     */
    public String cpfkhjllrbReport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();
        String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点
        List<YlfxBbReport> cpfkhjllrbReportList = reportBbBO.prdtEmpProfile(request, minDate, date, brNo, manageLvl, assessScope, businessNo, prdtCtgNo, prdtNo);
        request.getSession().setAttribute("cpfkhjllrbReportList", cpfkhjllrbReportList);
        if (minDate != null) request.getSession().setAttribute("cpfkhjllrbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("cpfkhjllrbMaxDate", date);
        if (brName != null) request.getSession().setAttribute("cpfkhjllrbBrName", brName);
        if (businessName != null) request.getSession().setAttribute("cpfkhjllrbBusinessName", businessName);
        if (prdtCtgName != null) request.getSession().setAttribute("cpfkhjllrbPrdtCtgName", prdtCtgName);
        if (prdtCtgNo != null) request.getSession().setAttribute("cpfkhjllrbPrdtCtgNo", prdtCtgNo);
        if (prdtName != null) request.getSession().setAttribute("cpfkhjllrbPrdtName", prdtName);
        if (prdtNo != null) request.getSession().setAttribute("cpfkhjllrbPrdtNo", prdtNo);

        String bbName = ((prdtNo == null || prdtNo.equals("")) ? ((prdtCtgNo == null || prdtCtgNo.equals("")) ? businessName : prdtCtgName) : prdtName) + "-产品分客户经理FTP利润表";
        String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
        String exportName = assessScopeName + "_" + date + "_" + brName + "_" + bbName;
        request.getSession().setAttribute("exportName", exportName);
        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "cpfkhjllrbList";
    }

    /**
     * 客户经理分产品FTP利润表
     *
     * @return
     * @throws Exception
     */
    public String khjlfcplrbReport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();
        String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点
        List<YlfxBbReport> khjlfcplrbReportList = reportBbBO.empPrdtProfile(request, minDate, date, empNo, assessScope);
        request.getSession().setAttribute("khjlfcplrbReportList", khjlfcplrbReportList);
        if (minDate != null) request.getSession().setAttribute("khjlfcplrbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("khjlfcplrbMaxDate", date);
        if (empName != null) request.getSession().setAttribute("khjlfcplrbEmpName", empName);
        if (empNo != null) request.getSession().setAttribute("khjlfcplrbEmpNo", empNo);
        String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
        String exportName = assessScopeName + "_" + date + "_" + empName + "_客户经理分产品FTP利润表";
        request.getSession().setAttribute("exportName", exportName);
        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "khjlfcplrbList";
    }

    /**
     * 客户经理分客户FTP利润表
     *
     * @return
     * @throws Exception
     */
    public String khjlfkhlrbReport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();
        String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点
        String pageName = request.getContextPath() + "/REPORTBB_khjlfkhlrbReport.action?empNo=" + empNo + "&date=" + date + "&assessScope=" + assessScope;
        PageUtil khjlfkhlrbReportUtil = reportBbBO.empCusProfile(request, minDate, date, empNo, assessScope, pageName, pageSize, page, false);
        request.getSession().setAttribute("khjlfkhlrbReportUtil", khjlfkhlrbReportUtil);
        if (minDate != null) request.getSession().setAttribute("khjlfkhlrbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("khjlfkhlrbMaxDate", date);
        if (empName != null) request.getSession().setAttribute("khjlfkhlrbEmpName", empName);
        if (assessScope != null) request.getSession().setAttribute("khjlfkhlrbAssessScope", assessScope);
        if (empNo != null) request.getSession().setAttribute("khjlfkhlrbEmpNo", empNo);
//		String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
//		String exportName=assessScopeName+"_"+date+"_"+empName+"_客户经理分客户FTP利润表";
//		request.getSession().setAttribute("exportName", exportName);
        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "khjlfkhlrbList";
    }

    /**
     * 客户经理分客户FTP利润表--导出
     *
     * @return
     * @throws Exception
     */
    public String khjlfkhlrbExport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();

        String minDate = (String) request.getSession().getAttribute("khjlfkhlrbMinDate");
        date = (String) request.getSession().getAttribute("khjlfkhlrbMaxDate");
        empNo = (String) request.getSession().getAttribute("khjlfkhlrbEmpNo");
        empName = (String) request.getSession().getAttribute("khjlfkhlrbEmpName");
        assessScope = (Integer) request.getSession().getAttribute("khjlfkhlrbAssessScope");

        PageUtil khjlfkhlrbReportUtil = reportBbBO.empCusProfile(request, minDate, date, empNo, assessScope, null, pageSize, page, true);
        request.getSession().setAttribute("khjlfkhlrbReportUtil", khjlfkhlrbReportUtil);

        if (minDate != null) request.getSession().setAttribute("khjlfkhlrbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("khjlfkhlrbMaxDate", date);
        if (empName != null) request.getSession().setAttribute("khjlfkhlrbEmpName", empName);
        if (assessScope != null) request.getSession().setAttribute("khjlfkhlrbAssessScope", assessScope);
        if (empNo != null) request.getSession().setAttribute("khjlfkhlrbEmpNo", empNo);
        String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
        String exportName = assessScopeName + "_" + date + "_" + empName + "_客户经理分客户FTP利润表";
        request.getSession().setAttribute("exportName", exportName);

        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "khjlfkhlrbListExport";
    }


    /**
     * 客户经理FTP利润明细表
     *
     * @return
     * @throws Exception
     */
    public String khjllrmxbReport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();
        String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点
        String pageName = request.getContextPath() + "/REPORTBB_khjllrmxbReport.action?empNo=" + empNo + "&date=" + date + "&assessScope=" + assessScope + "&businessNo=" + businessNo + "&prdtCtgNo=" + prdtCtgNo + "&prdtNo=" + prdtNo;

        PageUtil khjllrmxbReportUtil = reportBbBO.empDetailProfile(request, minDate, date, empNo, assessScope, businessNo, prdtCtgNo, prdtNo, pageName, pageSize, page, false);
        request.getSession().setAttribute("khjllrmxbReportUtil", khjllrmxbReportUtil);

        if (minDate != null) request.getSession().setAttribute("khjllrmxbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("khjllrmxbMaxDate", date);
        if (empNo != null) request.getSession().setAttribute("khjllrmxbEmpNo", empNo);
        if (empName != null) request.getSession().setAttribute("khjllrmxbEmpName", empName);
        if (assessScope != null) request.getSession().setAttribute("khjllrmxbEmpAssessScope", assessScope);
        if (businessNo != null) request.getSession().setAttribute("khjllrmxbEmpBusinessNo", businessNo);
        if (prdtCtgNo != null) request.getSession().setAttribute("khjllrmxbEmpPrdtCtgNo", prdtCtgNo);
        if (prdtNo != null) request.getSession().setAttribute("khjllrmxbEmpPrdtNo", prdtNo);
//		String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
//		String exportName=assessScopeName+"_"+date+"_"+empName+"_客户经理FTP利润明细表";
//		request.getSession().setAttribute("exportName", exportName);
        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "khjllrmxbList";
    }

    /**
     * 客户经理FTP利润明细表--导出
     *
     * @return
     * @throws Exception
     */
    public String khjllrmxbExport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();

        String minDate = (String) request.getSession().getAttribute("khjllrmxbMinDate");
        date = (String) request.getSession().getAttribute("khjllrmxbMaxDate");
        empNo = (String) request.getSession().getAttribute("khjllrmxbEmpNo");
        empName = (String) request.getSession().getAttribute("khjllrmxbEmpName");
        assessScope = (Integer) request.getSession().getAttribute("khjllrmxbEmpAssessScope");
        businessNo = (String) request.getSession().getAttribute("khjllrmxbEmpBusinessNo");
        prdtCtgNo = (String) request.getSession().getAttribute("khjllrmxbEmpPrdtCtgNo");
        prdtNo = (String) request.getSession().getAttribute("khjllrmxbEmpPrdtNo");

        PageUtil khjllrmxbReportUtil = reportBbBO.empDetailProfile(request, minDate, date, empNo, assessScope, businessNo, prdtCtgNo, prdtNo, null, pageSize, page, true);

        request.getSession().setAttribute("khjllrmxbReportUtil", khjllrmxbReportUtil);
        if (minDate != null) request.getSession().setAttribute("khjllrmxbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("khjllrmxbMaxDate", date);
        if (empName != null) request.getSession().setAttribute("khjllrmxbEmpName", empName);
        if (empNo != null) request.getSession().setAttribute("khjllrmxbEmpNo", empNo);
        String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
        String exportName = assessScopeName + "_" + date + "_" + empName + "_客户经理FTP利润明细表";
        request.getSession().setAttribute("exportName", exportName);

        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "khjllrmxbListExport";
    }


    /**
     * 对公业务FTP利润明细表
     *
     * @return
     * @throws Exception
     */
    public String gsywbmxbReport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();
        String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点
        String pageName = request.getContextPath() + "/REPORTBB_gsywbmxbReport.action?brNo=" + brNo + "&date=" + date + "&assessScope=" + assessScope + "&businessNo=" + businessNo + "&prdtCtgNo=" + prdtCtgNo + "&prdtNo=" + prdtNo + "&manageLvl=" + manageLvl;
        PageUtil gsywblrmxbReportUtil = reportBbBO.gsywbDetailProfile(request, minDate, date, brNo, assessScope, businessNo, prdtCtgNo, prdtNo, pageName, pageSize, page, manageLvl, false);
        request.getSession().setAttribute("gsywblrmxbReportUtil", gsywblrmxbReportUtil);
        if (minDate != null) request.getSession().setAttribute("gsywblrmxbminDate", minDate);
        if (date != null) request.getSession().setAttribute("gsywblrmxbdate", date);
        if (brNo != null) request.getSession().setAttribute("gsywblrmxbbrNo", brNo);
        if (brName != null) request.getSession().setAttribute("gsywblrmxbbrName", brName);
        if (assessScope != null) request.getSession().setAttribute("gsywblrmxbassessScope", assessScope);
        if (businessNo != null) request.getSession().setAttribute("gsywblrmxbbusinessNo", businessNo);
        if (prdtCtgNo != null) request.getSession().setAttribute("gsywblrmxbprdtCtgNo", prdtCtgNo);
        if (prdtNo != null) request.getSession().setAttribute("gsywblrmxbprdtNo", prdtNo);
        if (manageLvl != null) {
            request.getSession().setAttribute("gsywblrmxbManageLvl", manageLvl);
        }

        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "gsywblrmxbList";
    }

    /**
     * 对公业务FTP利润明细表-导出
     *
     * @return
     * @throws Exception
     */

    public String gsywblrmxbExport() throws Exception {

        String title = "对公业务FTP利润明细表";
        String minDate = (String) request.getSession().getAttribute("gsywblrmxbminDate");
        date = (String) request.getSession().getAttribute("gsywblrmxbdate");
        brName = (String) request.getSession().getAttribute("gsywblrmxbbrName");
        brNo = (String) request.getSession().getAttribute("gsywblrmxbbrNo");
        assessScope = (Integer) request.getSession().getAttribute("gsywblrmxbassessScope");
        businessNo = (String) request.getSession().getAttribute("gsywblrmxbbusinessNo");
        prdtCtgNo = (String) request.getSession().getAttribute("gsywblrmxbprdtCtgNo");
        prdtNo = (String) request.getSession().getAttribute("gsywblrmxbprdtNo");
        manageLvl = (String) request.getSession().getAttribute("gsywblrmxbManageLvl");

        PageUtil gsywblrmxbReportUtil = reportBbBO.gsywbDetailProfile(request, minDate, date, brNo, assessScope, businessNo, prdtCtgNo, prdtNo, null, pageSize, page, manageLvl, true);
//		PageUtil gsywblrmxbReportUtil = reportBbBO.gsywbDetailProfile(request, minDate, date, brNo, assessScope, businessNo, prdtCtgNo, prdtNo,null,pageSize,1,manageLvl);
        List<YlfxBbReport> gsywblrmxbExportList = gsywblrmxbReportUtil.getList();
        String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
        String exportName = assessScopeName + "_" + date + "_" + brName + "_" + title;
        //创建工作簿
        HSSFWorkbook workbook = reportBbBO.getGsywblrmxbWorkbook(gsywblrmxbExportList, brName, minDate, date, title);

        if (workbook != null) {
            //输出EXCEL
            ExcelExport.workbookInputStream(response, workbook, exportName);
        }
        request.getSession().removeAttribute("sykhjllrmxbReportUtil");
        return null;
    }


    /**
     * 银行客户经理统计报表
     *
     * @return
     * @throws Exception
     */
    public String yhkhjltjbbReport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();
        String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点
        String pageName = request.getContextPath() + "/REPORTBB_yhkhjltjbbReport.action?date=" + date + "&brNo=" + brNo + "&manageLvl=" + manageLvl + "&assessScope=" + assessScope + "&businessNo=" + businessNo + "&prdtCtgNo=" + prdtCtgNo + "&prdtNo=" + prdtNo;
        PageUtil sykhjllrmxbReportUtil = reportBbBO.allEmpDetailProfile(request, minDate, date, brNo, manageLvl, assessScope, businessNo, prdtCtgNo, prdtNo, pageName, pageSize, page, false);
        request.getSession().setAttribute("sykhjllrmxbReportUtil", sykhjllrmxbReportUtil);
        if (minDate != null) request.getSession().setAttribute("sykhjllrmxbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("sykhjllrmxbMaxDate", date);
        if (brName != null) request.getSession().setAttribute("sykhjllrmxbBrName", brName);
        if (brNo != null) request.getSession().setAttribute("sykhjllrmxbBrNo", brNo);
        if (manageLvl != null) request.getSession().setAttribute("sykhjllrmxbManageLvl", manageLvl);
        if (assessScope != null) request.getSession().setAttribute("sykhjllrmxbAssessScope", assessScope);
        if (businessNo != null) request.getSession().setAttribute("sykhjllrmxbBusinessNo", businessNo);
        if (prdtCtgNo != null) request.getSession().setAttribute("sykhjllrmxbPrdtCtgNo", prdtCtgNo);
        if (prdtNo != null) request.getSession().setAttribute("sykhjllrmxbPrdtNo", prdtNo);
        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "yhkhjltjbbList";
    }

    /**
     * 银行客户经理统计报表-导出
     *
     * @return
     * @throws Exception
     */

    public String yhkhjltjbbExport() throws Exception {

        String title = "银行客户经理FTP利润明细表";
        String minDate = (String) request.getSession().getAttribute("sykhjllrmxbMinDate");
        brName = (String) request.getSession().getAttribute("sykhjllrmxbBrName");
        brNo = (String) request.getSession().getAttribute("sykhjllrmxbBrNo");
        manageLvl = (String) request.getSession().getAttribute("sykhjllrmxbManageLvl");
        date = (String) request.getSession().getAttribute("sykhjllrmxbMaxDate");
        assessScope = (Integer) request.getSession().getAttribute("sykhjllrmxbAssessScope");
        businessNo = (String) request.getSession().getAttribute("sykhjllrmxbBusinessNo");
        prdtCtgNo = (String) request.getSession().getAttribute("sykhjllrmxbPrdtCtgNo");
        prdtNo = (String) request.getSession().getAttribute("sykhjllrmxbPrdtNo");
        PageUtil sykhjllrmxbReportUtil = reportBbBO.allEmpDetailProfile(request, minDate, date, brNo, manageLvl, assessScope, businessNo, prdtCtgNo, prdtNo, null, pageSize, page, true);
//		PageUtil sykhjllrmxbReportUtil = reportBbBO.allEmpDetailProfile(request, minDate, date, brNo, manageLvl, assessScope, businessNo, prdtCtgNo, prdtNo,null,pageSize,1, true);
        List<YlfxBbReport> sykhjllrmxbExportList = sykhjllrmxbReportUtil.getList();
        String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
        String exportName = assessScopeName + "_" + date + "_" + brName + "_" + title;
        //创建工作簿
        HSSFWorkbook workbook = reportBbBO.getYhkhjltjbbWorkbook(sykhjllrmxbExportList, brName, minDate, date, title);

        if (workbook != null) {
            //输出EXCEL
//			ExcelExport.workbookInputStream(response, workbook, title);
            ExcelExport.workbookInputStream(response, workbook, exportName);
        }
        request.getSession().removeAttribute("sykhjllrmxbReportUtil");
        return null;
    }

    /**
     * 客户经理利润排名表
     *
     * @return
     * @throws Exception
     */
    public String khjllrpmbReport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();
        String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点
        List<YlfxBbReport> khjllrpmbReportList = reportBbBO.brEmpOrderProfile(request, minDate, date, brNo, manageLvl, assessScope);
        request.getSession().setAttribute("khjllrpmbReportList", khjllrpmbReportList);
        if (minDate != null) request.getSession().setAttribute("khjllrpmbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("khjllrpmbMaxDate", date);
        if (brName != null) request.getSession().setAttribute("khjllrpmbBrName", brName);
        String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
        String exportName = assessScopeName + "_" + date + "_" + brName + "_客户经理FTP利润排名表";
        request.getSession().setAttribute("exportName", exportName);
        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "khjllrpmbList";
    }

    /**
     * 个人按揭机构FTP利润表
     *
     * @return
     * @throws Exception
     */
    public String grajjglrbReport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();
        String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点
        List<YlfxBbReport> grajjglrbReportList = reportBbBO.grajBrProfile(request, minDate, date, brNo, manageLvl, assessScope, isMx);
        request.getSession().setAttribute("grajjglrbReportList", grajjglrbReportList);
        if (minDate != null) request.getSession().setAttribute("grajjglrbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("grajjglrbMaxDate", date);
        if (brName != null) {
            if (1 == isMx || "true".equals(request.getParameter("isBack"))) { //对是否是"明细"和"返回"进行判断;明细和返回时用url传参需要进行转码
                brName = CommonFunctions.Chinese_CodeChange(brName);
            }
            request.getSession().setAttribute("grajjglrbBrName", brName);
        }
        request.getSession().setAttribute("grajjglrbIsFirst", isFirst);
        String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
        String exportName = assessScopeName + "_" + date + "_" + brName + "_个人按揭机构FTP利润表";
        request.getSession().setAttribute("exportName", exportName);
        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "grajjglrbList";
    }


    /**
     * 个人按揭分客户经理FTP利润明细表
     *
     * @return
     * @throws Exception
     */
    public String grajkhjllrbReport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();
        String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点
        String pageName = request.getContextPath() + "/REPORTBB_grajkhjllrbReport.action?brNo=" + brNo + "&manageLvl=" + manageLvl + "&empNo=" + empNo + "&date=" + date + "&assessScope=" + assessScope;
        PageUtil grajkhjllrbReportUtil = reportBbBO.grajEmpDetailProfile(request, minDate, date, brNo, manageLvl, empNo, assessScope, pageName, pageSize, page, false);
        request.getSession().setAttribute("grajkhjllrbReportUtil", grajkhjllrbReportUtil);
        if (brNo != null) request.getSession().setAttribute("grajkhjllrbBrNo", brNo);
        if (manageLvl != null) request.getSession().setAttribute("grajkhjllrbManageLvl", manageLvl);
        if (assessScope != null) request.getSession().setAttribute("grajkhjllrbAssessScope", assessScope);
        if (minDate != null) request.getSession().setAttribute("grajkhjllrbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("grajkhjllrbMaxDate", date);
        if (empName != null) request.getSession().setAttribute("grajkhjllrbEmpName", empName);
        if (empNo != null) request.getSession().setAttribute("grajkhjllrbEmpNo", empNo);
        if (brName != null) request.getSession().setAttribute("grajkhjllrbBrName", brName);
        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "grajkhjllrbList";
    }

    /**
     * 个人按揭分客户经理FTP利润明细表-导出
     *
     * @return
     * @throws Exception
     */

    public String grajkhjllrbExport() throws Exception {

        String title = "个人按揭分客户经理FTP利润明细表";
        String minDate = (String) request.getSession().getAttribute("grajkhjllrbMinDate");
        brName = (String) request.getSession().getAttribute("grajkhjllrbBrName");
        brNo = (String) request.getSession().getAttribute("grajkhjllrbBrNo");
        manageLvl = (String) request.getSession().getAttribute("grajkhjllrbManageLvl");
        date = (String) request.getSession().getAttribute("grajkhjllrbMaxDate");
        assessScope = (Integer) request.getSession().getAttribute("grajkhjllrbAssessScope");
        empNo = (String) request.getSession().getAttribute("grajkhjllrbEmpNo");
        empName = (String) request.getSession().getAttribute("grajkhjllrbEmpName");
        PageUtil grajkhjllrbReportUtil = reportBbBO.grajEmpDetailProfile(request, minDate, date, brNo, manageLvl, empNo, assessScope, null, pageSize, 1, true);
        List<YlfxBbReport> grajkhjllrbExportList = grajkhjllrbReportUtil.getList();
        String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
        String exportName = assessScopeName + "_" + date + "_" + brName + "_" + title;
        //创建工作簿
        HSSFWorkbook workbook = reportBbBO.getGrajkhjllrbWorkbook(grajkhjllrbExportList, brName, empName, empNo, minDate, date, title);

        if (workbook != null) {
            //输出EXCEL
            ExcelExport.workbookInputStream(response, workbook, exportName);
            //		ExcelExport.workbookInputStream(response, workbook, title);
        }
        request.getSession().removeAttribute("grajkhjllrbReportUtil");
        return null;
    }

    /**
     * 保证金存款客户经理FTP利润明细表
     *
     * @return
     * @throws Exception
     */
    public String bzjckkhjllrbReport() throws Exception {
        String STime = CommonFunctions.GetCurrentTime();
        String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点
        String pageName = request.getContextPath() + "/REPORTBB_bzjckkhjllrbReport.action?brNo=" + brNo + "&manageLvl=" + manageLvl + "&empNo=" + empNo + "&date=" + date + "&assessScope=" + assessScope;
        PageUtil bzjckkhjllrbReportUtil = reportBbBO.bzjckEmpDetailProfile(request, minDate, date, brNo, manageLvl, empNo, assessScope, pageName, pageSize, page, false);
        request.getSession().setAttribute("bzjckkhjllrbReportUtil", bzjckkhjllrbReportUtil);
        if (brNo != null) request.getSession().setAttribute("bzjckkhjllrbBrNo", brNo);
        if (manageLvl != null) request.getSession().setAttribute("bzjckkhjllrbManageLvl", manageLvl);
        if (assessScope != null) request.getSession().setAttribute("bzjckkhjllrbAssessScope", assessScope);
        if (minDate != null) request.getSession().setAttribute("bzjckkhjllrbMinDate", minDate);
        if (date != null) request.getSession().setAttribute("bzjckkhjllrbMaxDate", date);
        if (empName != null) request.getSession().setAttribute("bzjckkhjllrbEmpName", empName);
        if (empNo != null) request.getSession().setAttribute("bzjckkhjllrbEmpNo", empNo);
        if (brName != null) request.getSession().setAttribute("bzjckkhjllrbBrName", brName);
        String ETime = CommonFunctions.GetCurrentTime();
        int costTime = CommonFunctions.GetCostTimeInSecond(STime, ETime);
        int CostFen = costTime / 60;
        int CostMiao = costTime % 60;
        System.out.println("耗时" + CostFen + "分" + CostMiao + "秒");
        return "bzjckkhjllrbList";
    }

    /**
     * 保证金存款客户经理FTP利润明细表-导出
     *
     * @return
     * @throws Exception
     */

    public String bzjckkhjllrbExport() throws Exception {

        String title = "保证金存款客户经理FTP利润明细表";
        String minDate = (String) request.getSession().getAttribute("bzjckkhjllrbMinDate");
        brName = (String) request.getSession().getAttribute("bzjckkhjllrbBrName");
        brNo = (String) request.getSession().getAttribute("bzjckkhjllrbBrNo");
        manageLvl = (String) request.getSession().getAttribute("bzjckkhjllrbManageLvl");
        date = (String) request.getSession().getAttribute("bzjckkhjllrbMaxDate");
        assessScope = (Integer) request.getSession().getAttribute("bzjckkhjllrbAssessScope");
        empNo = (String) request.getSession().getAttribute("bzjckkhjllrbEmpNo");
        empName = (String) request.getSession().getAttribute("bzjckkhjllrbEmpName");
        PageUtil bzjckkhjllrbReportUtil = reportBbBO.bzjckEmpDetailProfile(request, minDate, date, brNo, manageLvl, empNo, assessScope, null, pageSize, page, true);
//		PageUtil bzjckkhjllrbReportUtil = reportBbBO.bzjckEmpDetailProfile(request, minDate, date, brNo, manageLvl, empNo, assessScope, null,pageSize,1);
        List<YlfxBbReport> bzjckkhjllrbExportList = bzjckkhjllrbReportUtil.getList();
        String assessScopeName = -1 == assessScope ? "月度" : (-3 == assessScope ? "季度" : "年度");
        String exportName = assessScopeName + "_" + date + "_" + brName + "_" + title;
        //创建工作簿
        HSSFWorkbook workbook = reportBbBO.getBzjckkhjllrbWorkbook(bzjckkhjllrbExportList, brName, empName, empNo, minDate, date, title);

        if (workbook != null) {
            //输出EXCEL
//			ExcelExport.workbookInputStream(response, workbook, title);
            ExcelExport.workbookInputStream(response, workbook, exportName);
        }
        request.getSession().removeAttribute("bzjckkhjllrbReportUtil");
        return null;
    }

    //清除缓存
    public String clearCache() throws Exception {
        /*String xlsBrNo = FtpUtil.getXlsBrNo(brNo, manageLvl);// 县联社
		date="20150930";
		System.out.println("date="+date);
		//String minDate = CommonFunctions.dateModifyM(date, assessScope);
		String minDate = reportBbBO.getMinDate(date, assessScope);//获取报表时段左端点
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(minDate))//如果跨过当年，则只取当年的数据，从1月份开始
			minDate = minDate.substring(0,4)+"1231";
		
		System.out.println("县联社：" + xlsBrNo);
		CacheOperation co = CacheOperation.getInstance();//缓存
		System.out.println("minDate="+minDate);
		//移除对应县联社date日期下的数据
	    co.removeCacheData(reportBbBO, "getQxppResultList",new Object[]{xlsBrNo, minDate, date});*/

        //20151009改成刷新全部缓存
        CacheOperation co = CacheOperation.getInstance();//缓存
        co.removeAllCacheData();
        return null;
    }


    /**
     * 更新统计报表
     *
     * @return
     * @throws Exception
     */
    public String updateTjbb() throws Exception {

        boolean isExcute = false;
        if (FtpUtil.isDone) {
            isExcute = true;
            FtpUtil.isDone=false;
            FutureTask futureTask = new FutureTask(new Callable() {
                public Object call() {
                    String toDay = String.valueOf(CommonFunctions.GetCurrentDateInLong());
                    String hsql = "from FtpEmpAccRel where substr(relTime,1,8)='" + toDay + "'";
                    List<FtpEmpAccRel> ftpEmpAccRelList = df.query(hsql, null);
                    if(ftpEmpAccRelList.size()>0){
                        List<FtpTjbbResult> ftpTjbbResultList = new ArrayList<FtpTjbbResult>();
                        String sql = "select max(cyc_date) from ftp.TJBB_RESULT";
                        List dateList = df.query1(sql, null);
                        String accIds ="";
                        final String cyc_date = (String) dateList.get(0);
                        accIds+=("(");
                        for (FtpEmpAccRel ftpEmpAccRel : ftpEmpAccRelList) {
                            accIds+=("'" + ftpEmpAccRel.getAccId() + "',");
                        }
                        if (accIds.endsWith(",")) {
                            accIds=accIds.substring(0, accIds.length() - 1);
                        }
                        accIds+=(")");

                        //删除统计报表数据
                        String sql1 = " delete from FtpTjbbResult where ftpTjbbResultComposite.cycDate='" + cyc_date + "' and ftpTjbbResultComposite.acId in " + accIds;
                        boolean deleteFlag = df.execute(sql1, null);

                        //map,key<账号>,<统计报表实体>
                        Map<String, FtpTjbbResultTemp> ftpTjbbResultMap = new HashMap<String, FtpTjbbResultTemp>();
                        if (deleteFlag) {
                            //取出统计报表临时表
                            sql1 = " select * from ftp.TJBB_RESULT_TEMP where cyc_date='" + cyc_date + "' and ac_id in " + accIds;
                            List dcfhqList = df.query1(sql1, null);
                            if (dcfhqList.size() > 0) {
                                for (Object object : dcfhqList) {
                                    Object[] obj = (Object[]) object;
                                    FtpTjbbResultTemp ftpTjbbResult = new FtpTjbbResultTemp();
                                    ftpTjbbResult.setID(obj[0] == null ? "" : obj[0].toString());
                                    ftpTjbbResult.setCycDate(obj[1] == null ? "" : obj[1].toString());
                                    ftpTjbbResult.setAcId(obj[2] == null ? "" : obj[2].toString().trim());
                                    ftpTjbbResult.setBrNo(obj[3] == null ? "" : obj[3].toString());
                                    ftpTjbbResult.setPrdtNo(obj[4] == null ? "" : obj[4].toString());
                                    ftpTjbbResult.setRate(obj[5] == null ? 0.00 : Double.valueOf(obj[5].toString()));
                                    ftpTjbbResult.setFtpPrice(obj[6] == null ? 0.00 : Double.valueOf(obj[6].toString()));
                                    ftpTjbbResult.setAverateBalM(obj[7] == null ? 0.00 : Double.valueOf(obj[7].toString()));
                                    ftpTjbbResult.setAverateBalQ(obj[8] == null ? 0.00 : Double.valueOf(obj[8].toString()));
                                    ftpTjbbResult.setAverateBalY(obj[9] == null ? 0.00 : Double.valueOf(obj[9].toString()));
                                    ftpTjbbResult.setBal(obj[10] == null ? 0.00 : Double.valueOf(obj[10].toString()));
                                    ftpTjbbResult.setBusinessNo(obj[11] == null ? "" : obj[11].toString());
                                    ftpTjbbResult.setKmh(obj[12] == null ? "" : obj[12].toString());
                                    ftpTjbbResult.setCustNo(obj[14] == null ? "" : obj[14].toString());
                                    ftpTjbbResult.setIsZq(obj[13] == null ? "" : obj[13].toString());
                                    ftpTjbbResultMap.put(obj[2].toString().trim(), ftpTjbbResult);
                                }
                            }
                        }


                        for (int i = 0; i < ftpEmpAccRelList.size(); i++) {
                            FtpEmpAccRel po = ftpEmpAccRelList.get(i);
                            String accId = String.valueOf(po.getAccId()).trim();
                            FtpTjbbResultTemp ftpTjbbResultTemp = ftpTjbbResultMap.get(accId.trim());//日均余额
                            if (ftpTjbbResultTemp == null) {
                                continue;
                            }
                            Double bal = ftpTjbbResultTemp.getBal();
                            Double rjye = ftpTjbbResultTemp.getAverateBalM();
                            String[] empNos = String.valueOf(po.getEmpNo()).split("@");
                            String[] rates = String.valueOf(po.getRate()).split("@");
                            //处理分配方式为固定金额的分配比例
                            double[] ratios = new double[rates.length];//日均余额分割比例
                            double[] ratios_bal = new double[rates.length];//余额分割比例
                            for (int g = 0; g < rates.length; g++) {
                                ratios[g] = Double.valueOf(rates[g]);
                                ratios_bal[g] = Double.valueOf(rates[g]);
                            }

                            if (String.valueOf(po.getRelType()).equals("2")) {//分配方式为"固定金额"时
                                double sumAmt = 0;//sum分配方式为固定金额时的分割金额总和
                                double sumAmt_bal = 0;//sum分配方式为固定金额时的分割金额总和
                                for (int h = 0; h < ratios.length; h++) {
                                    sumAmt += ratios[h];
                                    sumAmt_bal+=ratios[h];
                                }
                                if (sumAmt < rjye) {//如果总和<日均余额，则第一个客户经理分配到金额=日均余额-其他经理分配金额和
                                    ratios[0] = rjye - (sumAmt - ratios[0]);
                                    sumAmt = rjye;
                                }

                                if(sumAmt_bal < bal){//如果总和<余额，则第一个客户经理分配到金额=余额-其他经理分配金额和
                                    ratios_bal[0]=bal-(sumAmt_bal - ratios_bal[0]);
                                    sumAmt_bal=bal;
                                }

                                //如果金额总和>=日均余额，不需要处理金额直接按金额所占比例进行计算，
                                for (int h = 0; h < ratios.length; h++) {
                                    ratios[h] = ratios[h] / sumAmt;//计算比例
                                }

                                for (int h = 0; h < ratios_bal.length; h++) {
                                    ratios_bal[h] = ratios_bal[h] / sumAmt_bal;//计算比例
                                }
                            }

                            double r_whole = 0;
                            for (int j = 0; j < empNos.length; j++) {
                                r_whole += ratios[j];
                            }

                            //存入结果列表
                            for (int k = 0; k < empNos.length; k++) {
                                FtpTjbbResultComposite ftpTjbbResultComposite = new FtpTjbbResultComposite();
                                FtpTjbbResult ftpTjbbResult = new FtpTjbbResult();
                                ftpTjbbResultComposite.setID(ftpTjbbResultTemp.getID());
                                ftpTjbbResultComposite.setCycDate(ftpTjbbResultTemp.getCycDate());
                                ftpTjbbResultComposite.setAcId(ftpTjbbResultTemp.getAcId());
                                ftpTjbbResult.setBrNo(ftpTjbbResultTemp.getBrNo());
                                ftpTjbbResultComposite.setCumNo(empNos[k]);
                                ftpTjbbResult.setFtpTjbbResultComposite(ftpTjbbResultComposite);
                                ftpTjbbResult.setPrdtNo(ftpTjbbResultTemp.getPrdtNo());
                                ftpTjbbResult.setFtpPrice(ftpTjbbResultTemp.getFtpPrice());
                                ftpTjbbResult.setRate(ftpTjbbResultTemp.getRate());
                                ftpTjbbResult.setAverateBalM(ftpTjbbResultTemp.getAverateBalM() * ratios[k]);
                                ftpTjbbResult.setAverateBalQ(ftpTjbbResultTemp.getAverateBalQ() * ratios[k]);
                                ftpTjbbResult.setAverateBalY(ftpTjbbResultTemp.getAverateBalY() * ratios[k]);
                                ftpTjbbResult.setBal(ftpTjbbResultTemp.getBal() * ratios_bal[k]);
                                ftpTjbbResult.setCustNo(ftpTjbbResultTemp.getCustNo());
                                ftpTjbbResult.setBusinessNo(ftpTjbbResultTemp.getBusinessNo());
                                ftpTjbbResult.setKmh(ftpTjbbResultTemp.getKmh());
                                ftpTjbbResult.setIsZq(ftpTjbbResultTemp.getIsZq());
                                ftpTjbbResultList.add(ftpTjbbResult);
                            }
                        }
                        df.insert_s(ftpTjbbResultList);
                        String sql_delete = " delete from  ftp.TJBB_RESULT_PRDT where cyc_date= '"+cyc_date+"'";
                        df.execute1(sql_delete);
                        String sql_update = " MERGE INTO ftp.TJBB_RESULT_PRDT l" +
                                " using ( SELECT br_no,prdt_no,cum_no, substr(cust_no,1,1) as cust_type,SUM(AVERATE_BAL_M) AS AVERATE_BAL_M,SUM(AVERATE_BAL_Q) AS AVERATE_BAL_Q,SUM(AVERATE_BAL_Y) AS AVERATE_BAL_Y,SUM(bal) as bal," +
                                "  MAX(BUSINESS_NO) as BUSINESS_NO,(case when substr(prdt_no,2,1)='1'then (case when sum(AVERATE_BAL_M)!=0 then sum((rate-FTP_PRICE)*AVERATE_BAL_M)/sum(AVERATE_BAL_M)" +
                                "  else 0 end)  else (case when sum(AVERATE_BAL_M)!=0   then sum((FTP_PRICE-rate)*AVERATE_BAL_M)/sum(AVERATE_BAL_M) else 0 end)  end ) as ftp_Rate_M," +
                                "   (case when substr(prdt_no,2,1)='1' then (case when sum(AVERATE_BAL_Q)!=0 then sum((rate-FTP_PRICE)*AVERATE_BAL_Q)/sum(AVERATE_BAL_Q) else 0 end)  " +
                                "  else  (case when sum(AVERATE_BAL_Q)!=0 then sum((FTP_PRICE-rate)*AVERATE_BAL_Q)/sum(AVERATE_BAL_Q) else 0 end)  end ) as ftp_Rate_Q," +
                                " (case when substr(prdt_no,2,1)='1' then (case when sum(AVERATE_BAL_Y)!=0  then sum((rate-FTP_PRICE)*AVERATE_BAL_Y)/sum(AVERATE_BAL_Y)" +
                                "   else 0 end)   else (case when sum(AVERATE_BAL_Y)!=0  then sum((FTP_PRICE-rate)*AVERATE_BAL_Y)/sum(AVERATE_BAL_Y) else 0 end)   end ) as ftp_Rate_Y" +
                                "  FROM ftp.TJBB_RESULT WHERE cyc_date='" + cyc_date + "' GROUP BY br_no,prdt_no,cum_no, substr(cust_no,1,1) ) lv" +
                                " ON  l.br_no=lv.br_no and l.cyc_date='" + cyc_date + "' and l.prdt_no=lv.prdt_no and l.cum_no=lv.cum_no and l.cust_type=lv.cust_type" +
                                " WHEN not  MATCHED THEN insert values('0','"+cyc_date+"',lv.BR_NO,lv.CUM_NO,lv.PRDT_NO,lv.FTP_RATE_M,lv.FTP_RATE_Q,lv.FTP_RATE_Y,lv.AVERATE_BAL_M,lv.AVERATE_BAL_Q,lv.AVERATE_BAL_Y,lv.bal,lv.BUSINESS_NO,lv.CUST_TYPE)";
                         df.execute1(sql_update);
                    }
                    return true;
                }
            });
            final Future future = FtpUtil.executorService.submit(futureTask);
            FutureTask futureTaskReult = new FutureTask(new Callable() {
                public Object call() throws InterruptedException {
                    while (!future.isDone()) {
                        FtpUtil.isDone = future.isDone();
                        System.out.println("统计报表更新尚未完成。。");
                        Thread.sleep(1000 * 10);
                    }
                    FtpUtil.isDone = future.isDone();
                    System.out.println("统计报表更完成。。");
                    return FtpUtil.isDone;
                }
            });
            FtpUtil.executorService.submit(futureTaskReult);
        }

        if (isExcute) {
            getResponse().getWriter().write("1");
        } else {
            getResponse().getWriter().write("0");
        }

        return null;
    }

    public String getBrNo() {
        return brNo;
    }

    public void setBrNo(String brNo) {
        this.brNo = brNo;
    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRowsCount() {
        return rowsCount;
    }

    public void setRowsCount(int rowsCount) {
        this.rowsCount = rowsCount;
    }

    public PageUtil getResultPriceUtil() {
        return resultPriceUtil;
    }

    public void setResultPriceUtil(PageUtil resultPriceUtil) {
        this.resultPriceUtil = resultPriceUtil;
    }

    public Integer getAssessScope() {
        return assessScope;
    }

    public void setAssessScope(Integer assessScope) {
        this.assessScope = assessScope;
    }

    public String getManageLvl() {
        return manageLvl;
    }

    public void setManageLvl(String manageLvl) {
        this.manageLvl = manageLvl;
    }

    public Integer getIsMx() {
        return isMx;
    }

    public void setIsMx(Integer isMx) {
        this.isMx = isMx;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        //date = CommonFunctions.dateModifyM(date, -1);//处理到上个月的月底
        this.date = date;
    }

    public String getPrdtType() {
        return prdtType;
    }

    public void setPrdtType(String prdtType) {
        this.prdtType = prdtType;
    }

    public String getBrCountLvl() {
        return brCountLvl;
    }

    public void setBrCountLvl(String brCountLvl) {
        this.brCountLvl = brCountLvl;
    }

    public String getAssessScopeText() {
        return assessScopeText;
    }

    public void setAssessScopeText(String assessScopeText) throws UnsupportedEncodingException {
        this.assessScopeText = CommonFunctions.Chinese_CodeChange(assessScopeText);
    }

    public String getBrName() {
        return brName;
    }

    public void setBrName(String brName) {
        this.brName = brName;
    }

    public String getBrCountLvlText() {
        return brCountLvlText;
    }

    public void setBrCountLvlText(String brCountLvlText) throws UnsupportedEncodingException {
        this.brCountLvlText = CommonFunctions.Chinese_CodeChange(brCountLvlText);
    }

    public String getBusinessNo() {
        return businessNo;
    }

    public void setBusinessNo(String businessNo) {
        this.businessNo = businessNo;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPrdtNo() {
        return prdtNo;
    }

    public void setPrdtNo(String prdtNo) {
        this.prdtNo = prdtNo;
    }

    public String getPrdtName() {
        return prdtName;
    }

    public void setPrdtName(String prdtName) {
        this.prdtName = prdtName;
    }

    public String getPrdtCtgNo() {
        return prdtCtgNo;
    }

    public void setPrdtCtgNo(String prdtCtgNo) {
        this.prdtCtgNo = prdtCtgNo;
    }

    public String getPrdtCtgName() {
        return prdtCtgName;
    }

    public void setPrdtCtgName(String prdtCtgName) {
        this.prdtCtgName = prdtCtgName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }
}
