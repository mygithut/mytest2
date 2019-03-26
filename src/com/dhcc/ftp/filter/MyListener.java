package com.dhcc.ftp.filter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;

import com.dhcc.ftp.bo.ReportBbBO;
import com.dhcc.ftp.cache.CacheOperation;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.ComSysParm;
import com.dhcc.ftp.entity.FtpEmpInfo;
import com.dhcc.ftp.entity.TelBrConfig;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.entity.YlfxBbReport;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.ExcelExport;
import com.dhcc.ftp.util.FormatUtil;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.PageUtil;

/**
 * Listener的方式在后台执行一线程
 * 
 * @author Champion.Wong
 * 
 */
public class MyListener implements ServletContextListener {
	

	public Map<String, List<BrMst>> brMstMap = this.getBrByEmpNo();
	public void contextInitialized(ServletContextEvent e) {

	 	System.out.println("缓存初始化");
		try {
				System.out.println("===========生成excel=================");
			   yhkhjltjbbExport();
			   System.out.println("============生成完毕================");
			    //getmonthcache();//#测试时注释掉
			    //getjicache();//#测试时注释掉
			    //getyearcache();//#测试时注释掉
			    
			    
		   } catch (Exception e1)	{
			e1.printStackTrace();
		}

	}

	/**
	 * 
	 * 月缓存
	 */
	public static void getmonthcache() {

		String STime = CommonFunctions.GetCurrentTime();
		String sql = "from ComSysParm order by sysDate DESC";
		List<ComSysParm> list = CommonFunctions.mydao.query(sql, null);
		ComSysParm com_sys_parm = new ComSysParm();
		if (list.size() == 0) {
			System.out.println("系统日期获取出错，请检查数据库公共参数表com_sys_parm！");
			return;
		}
		com_sys_parm = list.get(0);
		long oldTodayDate = com_sys_parm.getTodayDate();
		String newTodayDate = String.valueOf(oldTodayDate);

		String xlsBrNo = "3403111034";
		String minDate = CommonFunctions.dateModifyM(newTodayDate, -2);
		System.out.println(minDate);
		String maxDate = String.valueOf(CommonFunctions.dateModifyM(
				newTodayDate, -1));
		System.out.println(maxDate);
		long intervalTime = Long.valueOf("2592000000");// 缓存存放一个月
		int maxVisitCount = 0;// 不限制访问次数
		ReportBbBO rpb = new ReportBbBO();
		CacheOperation.getInstance().getCacheData(rpb, "getQxppResultList",
				new Object[] { xlsBrNo, minDate, maxDate }, intervalTime,
				maxVisitCount);

	}

	/**
	 * 
	 * 年缓存
	 */
	public static void getyearcache() {

		String STime = CommonFunctions.GetCurrentTime();
		String sql = "from ComSysParm order by sysDate DESC";
		List<ComSysParm> list = CommonFunctions.mydao.query(sql, null);
		ComSysParm com_sys_parm = new ComSysParm();
		if (list.size() == 0) {
			System.out.println("系统日期获取出错，请检查数据库公共参数表com_sys_parm！");
			return;
		}
		com_sys_parm = list.get(0);
		long oldTodayDate = com_sys_parm.getTodayDate();
		String newTodayDate = String.valueOf(oldTodayDate);

		String xlsBrNo = "3403111034";
		String minDate = CommonFunctions.dateModifyM(newTodayDate.substring(0,
				4)
				+ "0101", -1);
		System.out.println(minDate);
		String maxDate = CommonFunctions.dateModifyM(newTodayDate, -1);
		long intervalTime = Long.valueOf("2592000000");// 缓存存放一个月
		int maxVisitCount = 0;// 不限制访问次数
		ReportBbBO rpb = new ReportBbBO();
		CacheOperation.getInstance().getCacheData(rpb, "getQxppResultList",
				new Object[] { xlsBrNo, minDate, maxDate }, intervalTime,
				maxVisitCount);

	}

	/**
	 * 
	 * 季缓存
	 */
	public static void getjicache() {

		String STime = CommonFunctions.GetCurrentTime();
		String sql = "from ComSysParm order by sysDate DESC";
		List<ComSysParm> list = CommonFunctions.mydao.query(sql, null);
		ComSysParm com_sys_parm = new ComSysParm();
		if (list.size() == 0) {
			System.out.println("系统日期获取出错，请检查数据库公共参数表com_sys_parm！");
			return;
		}
		com_sys_parm = list.get(0);
		long oldTodayDate = com_sys_parm.getTodayDate();
		String newTodayDate = String.valueOf(oldTodayDate);
		String date = getMinDate(newTodayDate, -3);
		System.out.println(date);
		String xlsBrNo = "3403111034";
		String minDate = CommonFunctions.dateModifyM(date, -3);
		System.out.println(minDate);
		String maxDate = date;
		long intervalTime = Long.valueOf("2592000000");// 缓存存放一个月
		int maxVisitCount = 0;// 不限制访问次数
		ReportBbBO rpb = new ReportBbBO();
		CacheOperation.getInstance().getCacheData(rpb, "getQxppResultList",
				new Object[] { xlsBrNo, minDate, maxDate }, intervalTime,
				maxVisitCount);

	}

	/**
	  * 
	  * 每日时点余额
	  * @return
	  * 
	  * 
	  */
	public  List<String[]> getQxppResultList() {

		DaoFactory daoFactory = new DaoFactory(); 
		List<String[]> resultList = new ArrayList<String[]>();
		//以本机构为KEY，上级机构为Value的MAP
		Map<String,String> br_mst = new HashMap<String,String>();
		Map<String,String> brNameMap = new HashMap<String,String>();
		String sql = "select BR_NO,SUPER_BR_NO from ftp.br_mst ";
		List brList = daoFactory.query1(sql,null);
		for(Object obj :brList){
			
			Object[] object = (Object[])obj;
			br_mst.put((String)object[0],(String)object[1]);
		}
         sql = "select BR_NO,BR_NAME from ftp.br_mst ";
    	List brnameList = daoFactory.query1(sql,null);
    	for(Object obj :brnameList){
    		
    		Object[] object = (Object[])obj;
    		brNameMap.put((String)object[0],(String)object[1]);
    		
    	}
    	List<YlfxBbReport> Yllist = empPrdtProfile();
		if(Yllist.size() > 0) {
			for(YlfxBbReport ylfxBbReport : Yllist) {
			
				String[] result = new String[6];
				result[0] = brNameMap.get(br_mst.get(ylfxBbReport.getBrNo()));
				result[1] = ylfxBbReport.getBrName();
				result[2] = ylfxBbReport.getEmpName();
				result[3] = ylfxBbReport.getOpnDate();
				result[4] = String.valueOf(FormatUtil.toMoney(ylfxBbReport.getZcrjye()));
				result[5] = String.valueOf(FormatUtil.toMoney(ylfxBbReport.getFzrjye()));
				resultList.add(result);
			   }
			}
		Yllist.clear();System.gc();
		brnameList.clear();System.gc();
		brList.clear();System.gc();
		
		return resultList;
	}
	
	
	/**
	  * 以员工账户关联表为基准，获取日期范围内的账户的机构、产品、客户经理、日均余额、利润、账号、客户号
	  * @param xlsbrNo
	  * @param minDate日期左端点
	  * @param maxDate日期右端点
	  * @return
	  */
	public static List<String[]> getQxppResultList(String xlsbrNo) {
		List<String[]> resultList=new ArrayList<String[]>();
		//String prdtNo = this.getPrdtNoQxpp(xlsbrNo);//期限匹配下配置的所有产品
		DaoFactory daoFactory = new DaoFactory(); 
		Map<String, Double> yeMap = new HashMap<String, Double>();
		Map<String, String> businessMap = new HashMap<String, String>();
		//1.获取每个分账户的业务编号和余额
		String sql1 = "select AC_ID,BUSINESS_NO,BAL from ftp.FZH_HISTORY where Bal !=0";
		List dcfhqList = daoFactory.query1(sql1, null);
		if(dcfhqList.size() > 0) {
			for(Object object : dcfhqList) {
				Object[] obj = (Object[])object;
	            yeMap.put(obj[0].toString(), Double.valueOf(obj[2].toString()));
	            businessMap.put(obj[0].toString(), obj[1].toString());
			}
		}
		dcfhqList.clear(); System.gc();//释放dcfhqList所占的内存
		//2.获取员工对应的机构信息
		Map<String, String> empInfoMap = new HashMap<String, String>();
		String sql3 = "select emp_no, trim(br_no) from ftp.ftp_emp_info";
		List empList = daoFactory.query1(sql3, null);
		if(empList != null && empList.size() > 0) {
			for(Object object : empList) {
				Object[] obj = (Object[])object;
				empInfoMap.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
			}
		}
		empList.clear(); System.gc();//释放empList所占的内存
		//3.获取存贷款类业务客户经理关联信息
		String sql4 = "select acc_id, emp_no, rate, rel_type, prdt_no from ftp.ftp_emp_acc_rel" ;
		List accRelList = daoFactory.query1(sql4, null);
		String errorInfo = "";int num_yue_0=0;
		int num_yue_null=0;
		int num_yue_not0=0;
		int num_errorInfo=0;
		double rjye_whole=0;
		double rjye_rate_shao=0;
		double rjye_whole_mx=0;
		for(int x=0 ;x<accRelList.size();x++) {
			/*if((x+1)%10000==0){
				System.out.println("-----------------"+(x+1));
			}*/
			Object object=accRelList.get(x);
			Object[] obj = (Object[])object;
			String accId = String.valueOf(obj[0]).trim();
			Double rjye = yeMap.get(accId);//余额
			if(rjye==null){
				//System.out.println("余额为null的:"+accId);
				num_yue_null++;
			}else if(rjye==0){
				//System.out.println("余额为0的:"+accId);
				num_yue_0++;
			}else{
				rjye_whole+=rjye;
				num_yue_not0++;
			}
			rjye = ((rjye==null)?0.0:rjye);
			String[] empNos = String.valueOf(obj[1]).split("@");
			String[] rates = String.valueOf(obj[2]).split("@");
			//处理分配方式为固定金额的分配比例
			double[] ratios = new double[rates.length];//分割比例
			for(int g=0;g<rates.length;g++){
				ratios[g]=Double.valueOf(rates[g]);
			}
			if(String.valueOf(obj[3]).equals("2")) {//分配方式为"固定金额"时
				double sumAmt = 0;//sum：分配方式为固定金额时的分割金额总和
				for (int i = 0; i < ratios.length; i++) {
					//ratios[i] = Double.valueOf(rates[i]);
					sumAmt += ratios[i];
				}
				if(sumAmt < rjye) {//如果总和<余额，则第一个客户经理分配到金额=余额-其他经理分配金额和
					ratios[0] = rjye - (sumAmt-ratios[0]);
					sumAmt = rjye;
				}
				//如果金额总和>=余额，不需要处理金额直接按金额所占比例进行计算，
					
				for (int i = 0; i < ratios.length; i++) {
					ratios[i] = ratios[i]/sumAmt;//计算比例
				}
			}
			double r_whole=0;
			for(int i = 0; i < empNos.length; i++){
				r_whole+=ratios[i];
			}
			if(r_whole!=1){
				System.out.println(r_whole+"!=1,"+obj[1]+","+obj[2]+"-->"+String.valueOf(obj[0])+":"+rjye);
				rjye_rate_shao+=rjye*(1-r_whole);
			}
			//存入结果列表
			for (int i = 0; i < empNos.length; i++) {
				String[] result = new String[8];
				result[0] = empInfoMap.get(empNos[i]);//机构
				if(result[0] == null) {
					//errorInfo += obj[0]+",";
					num_errorInfo++;
					continue;
				}
				result[1] = String.valueOf(businessMap.get(obj[0].toString()));//业务
				result[2] = String.valueOf(empNos[i]);//客户经理编号
				double avebal = rjye*ratios[i];
				rjye_whole_mx+=avebal;
				result[3] = String.valueOf(avebal);//余额
				result[4] = String.valueOf(obj[0]);//账号
				if(avebal!=0){
					resultList.add(result);
				}
			}
		}
		System.out.println("num_yue_null="+num_yue_null);
		System.out.println("num_yue_0="+num_yue_0);
		System.out.println("num_yue_not0="+num_yue_not0);
		System.out.println("num_errorInfo="+num_errorInfo);
		System.out.println("rjye_whole="+rjye_whole);
		System.out.println("rjye_whole_mx="+rjye_whole_mx);
		System.out.println("rjye_rate_shao="+rjye_rate_shao);
		if(!errorInfo.equals("")) {
			System.out.println("账号为"+errorInfo+"<n>的员工账户关联配置有误，未纳入统计，请检查！");
		}
		accRelList.clear(); System.gc();//释放accRelList所占的内存
		empInfoMap.clear(); System.gc();
		return resultList;
	}
	
	  /**
	 * 客户经理
	 * @param request
	 * @param minDate
	 * @param date
	 * @param empNo
	 * @param assessScope
	 * @return
	 */
    public List<YlfxBbReport> empPrdtProfile() {
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		//从缓存中获取数据
	    DaoFactory daoFactory = new DaoFactory(); 
	    
	    String STime = CommonFunctions.GetCurrentTime();
		String sql = "from ComSysParm order by sysDate DESC";
		List<ComSysParm> daylist = CommonFunctions.mydao.query(sql, null);
		ComSysParm com_sys_parm = new ComSysParm();
		if (daylist.size() == 0) {
			System.out.println("系统日期获取出错，请检查数据库公共参数表com_sys_parm！");
			return null;
		}
		com_sys_parm = daylist.get(0);
		long oldTodayDate = com_sys_parm.getTodayDate();
		String newTodayDate = String.valueOf(oldTodayDate);
	    
		String brNos = this.getAllChildBr("3403111034", "2", "000000");
		List<String[]> ftpResultsList = getQxppResultList("3403111034");
		if(ftpResultsList == null) return null;
		Map<String, Double> rjyejlMap = new HashMap<String, Double>();//key:客户经理编号+业务编号的第3位
		//从结果集中获取某些客户经理的数据，根据客户经理编号汇总到map中
		for (String[] result : ftpResultsList) {
			if(brNos.indexOf(result[0])!= -1) {
				double rjye = Double.valueOf(result[3]);
				//余额
				if(rjyejlMap.get(result[2]+"-"+result[1].substring(2,3))==null){
					rjyejlMap.put(result[2]+"-"+result[1].substring(2,3), rjye);
				}else{
					rjyejlMap.put(result[2]+"-"+result[1].substring(2,3), rjye+rjyejlMap.get(result[2]+"-"+result[1].substring(2,3)));
				}
			}
		}
		
		ftpResultsList.clear();System.gc();
		
		//获取该机构下的所有客户经理
		String hsql1 = "from FtpEmpInfo where brMst.brNo  in ("+brNos+")";
		List<FtpEmpInfo> empList = daoFactory.query(hsql1, null);
        //循环客户经理，获取要统计的值
		for(FtpEmpInfo ftpEmpInfo : empList) {
			double zcrjye = rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-1")==null?0:rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-1");
			double fzrjye = rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-2");
			//只显示有值的
			if((zcrjye!=0||fzrjye!=0)) {
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(ftpEmpInfo.getEmpNo());
				ylfxBbReport.setEmpName(ftpEmpInfo.getEmpName());
				ylfxBbReport.setBrNo(ftpEmpInfo.getBrMst().getBrNo());
				ylfxBbReport.setBrName(ftpEmpInfo.getBrMst().getBrName());
				ylfxBbReport.setZcrjye(zcrjye);//资产余额
				ylfxBbReport.setFzrjye(fzrjye);//负债余额
				ylfxBbReport.setOpnDate(newTodayDate);//日期
				ylfxBbReportList.add(ylfxBbReport);
			}
		}
		
		empList.clear();System.gc();
		rjyejlMap.clear();System.gc();
		
	   return ylfxBbReportList;
	}
	
	
    /**
	 * 银行客户经理时点余额-导出
	 * @param reportList
	 * @param empName
	 * @param empNo
	 * @param minDate
	 * @param maxDate
	 * @param title
	 * @return
	 */
	public static HSSFWorkbook getWorkbook(List<String[]> resultList) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		String title = "客户经理每日时点余额";
		HSSFSheet sheet = workbook.createSheet(title);//生成一个sheet
		try {
	        // 表头合并
			sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 6 ));// 合并第一行第一到13列
			sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 6 ));// 合并第二行第一到13列
			//设置第一列机构单元格的宽度
	        sheet.setColumnWidth(0, 20*2*256);//长度乘以2是为了解决纯数字列宽度不足会显示科学计数法问题，乘以256得到的数据才是excel真实列宽。
			
			ExcelExport excelExport = new ExcelExport(workbook, sheet);
			excelExport.createRow(0); // 创建第一行
			excelExport.setCell(0, title, excelExport.getHeaderStyle());
			
			excelExport.createRow(1); // 创建第二行
			excelExport.setCell(0, "机构：蚌埠农商行时点余额报表                                                     单位：元，%(年利率)", excelExport.getTitleStyle());
			
			excelExport.createRow(2);//第三行，表头列
			sheet.setColumnWidth(0, 7*256);//设置第一列的宽度   
			sheet.setColumnWidth(4, 25*256);//设置第5列的宽度  
			sheet.setColumnWidth(5, 12*256);//设置第6列的宽度   
			excelExport.setCell(0, "序号", excelExport.headCenterNormalStyle);
			excelExport.setCell(1, "上级机构", excelExport.headCenterNormalStyle);
			excelExport.setCell(2, "机构", excelExport.headCenterNormalStyle);
			excelExport.setCell(3, "客户经理名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(4, "日期", excelExport.headCenterNormalStyle);
			excelExport.setCell(5, "资产余额", excelExport.headCenterNormalStyle);
			excelExport.setCell(6, "负债余额", excelExport.headCenterNormalStyle);
			if(resultList != null) {
				for (int j = 0; j < resultList.size(); j++) {
					
					String[] entity = resultList.get(j);
					excelExport.createRow(j+3);
					excelExport.setCell(0, j+1, excelExport.centerNormalStyle);
					excelExport.setCell(1, entity[0], excelExport.centerNormalStyle);
					excelExport.setCell(2, entity[1], excelExport.centerNormalStyle);
					excelExport.setCell(3, entity[2], excelExport.centerNormalStyle);
					excelExport.setCell(4, entity[3], excelExport.centerNormalStyle);
					excelExport.setCell(5, entity[4], excelExport.centerNormalStyle);
					excelExport.setCell(6, entity[5], excelExport.centerNormalStyle);
					
				}
			}
			sheet.getRow(0).setHeight((short)500);//设置第一行表格高度
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		resultList.clear();System.gc();
		return workbook;
	}
	
	/**
	 * 银行客户经理时点余额-导出
	 * @return
	 * @throws Exception
	 */

	public String yhkhjltjbbExport() throws Exception {
		
		String STime = CommonFunctions.GetCurrentTime();
		String sql = "from ComSysParm order by sysDate DESC";
		List<ComSysParm> daylist = CommonFunctions.mydao.query(sql, null);
		ComSysParm com_sys_parm = new ComSysParm();
		if (daylist.size() == 0) {
			System.out.println("系统日期获取出错，请检查数据库公共参数表com_sys_parm！");
			return null;
		}
		com_sys_parm = daylist.get(0);
		long oldTodayDate = com_sys_parm.getTodayDate();
		String newTodayDate = String.valueOf(oldTodayDate);
		 List<String[]> list = getQxppResultList();
		//创建工作簿
		HSSFWorkbook workbook = getWorkbook(list);
		if (workbook != null) {
			//输出EXCEL
			workbookInputStream(workbook,newTodayDate);
		}
		return null;
	}
	
	
    /**
	 * 创建workbook,将workbook写入到InputStream
	 * @param workbook
	 * @param fileName
	 */
	public static void workbookInputStream(HSSFWorkbook workbook,String date) {     
		 try {
			    String fileName = "ssye_"+date;
	            //byte[] fileNameByte = (fileName + ".xls").getBytes("GBK");
	            //String filename = new String(fileNameByte);
	            OutputStream baos = new FileOutputStream("//home//bengbu//ssye//"+fileName+".xls");
			    workbook.write(baos);
			    baos.flush();       
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	}
	
	
	
	/**
	 * 根据最大日期和统计维度获取最小日期
	 * 
	 * @param maxDate
	 * @param assessScope
	 *            -1月度-3季度-12年度
	 * @return
	 */
	public static String getMinDate(String maxDate, Integer assessScope) {
		int nowMonth = Integer.valueOf(String.valueOf(maxDate).substring(4, 6));// 当前月
		if (assessScope == -3) {// 季度
			if (nowMonth >= 1 && nowMonth <= 3)
				assessScope = 0 - nowMonth;
			else if (nowMonth >= 4 && nowMonth <= 6)
				assessScope = 3 - nowMonth;
			else if (nowMonth >= 7 && nowMonth <= 9)
				assessScope = 6 - nowMonth;
			else if (nowMonth >= 10 && nowMonth <= 12)
				assessScope = 9 - nowMonth;
		} else if (assessScope == -12) {// 年度
			assessScope = -nowMonth;
		}
		String minDate = CommonFunctions.dateModifyM(maxDate, assessScope);
		return minDate;
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * 根据机构编号，获取所有的下级机构+本身(不包括最高级别2的机构)，以,分隔
	 * @param br_no
	 * @return
	 */
	public String getAllChildBr(String br_no, String br_level, String tel_no) {
		
		 DaoFactory daoFactory = new DaoFactory(); 
		
		String br_nos="";
		if("2".equals(br_level)){//如果是总行，则获取所有的机构
			List<BrMst> brMstList = brMstMap.get(tel_no);//获取该操作员所辖的机构
			if(brMstList == null || brMstList.size() == 0) {//"高级<董事长>"级别，可以查看所有机构
				String sql="select br_no from ftp.br_mst where manage_lvl !='2'";
				List br_noList=daoFactory.query1(sql, null);
				for(Object obj:br_noList){				
					br_nos+="'"+obj.toString()+"',";		
				}
			}else {//行监事长||银行部经理
				for (BrMst brMst : brMstList) {
					br_nos+="'"+brMst.getBrNo()+"',";//先加上自己本身
					String sql="select br_no from ftp.br_mst where super_br_no='"+brMst.getBrNo()+"'";
					List br_noList=daoFactory.query1(sql, null);
					if (br_noList.size()>0) {
						for(Object obj:br_noList){				
							br_nos+="'"+obj.toString()+"',";		
						}
					}
				}
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
		}else if("1".equals(br_level)){
			br_nos="'"+br_no+"',";//先把自己本身加进去
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList=daoFactory.query1(sql, null);
			for(Object obj:br_noList){				
				br_nos+="'"+obj.toString()+"',";		
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
		}else{
			br_nos="'"+br_no+"'";
		}

		return br_nos;
	}
	
	/**
	 * 获取TelBrConfig表中，操作员所配置的机构
	 * @param telNo
	 * @return MAP(KEY:TelNo，VALUE:List<BrMst>)
	 */
	public Map<String, List<BrMst>> getBrByEmpNo() {
		
		
		DaoFactory daoFactory = new DaoFactory(); 
		Map<String, List<BrMst>> brMstMap = new HashMap<String, List<BrMst>>();
		String hsql1 = "from TelBrConfig";//从配置文件中获取所有已配置操作员的机构权限
		List<TelBrConfig> telBrConfigList = daoFactory.query(hsql1, null);
		if(telBrConfigList != null && telBrConfigList.size() > 0) {
			String hsql2 = "from BrMst order by brNo desc";//获取所有机构
			List<BrMst> brList = daoFactory.query(hsql2, null);
			for(TelBrConfig telBrConfig : telBrConfigList) {
				List<BrMst> brMstList = new ArrayList<BrMst>();
				String brNos = telBrConfig.getBrNos();//以@间隔
				for(BrMst brMst : brList) {
					if(brNos.indexOf(brMst.getBrNo()) != -1) {
						brMstList.add(brMst);
					}
				}
				brMstMap.put(telBrConfig.getTelNo(), brMstList);
			}
			
		}
		return brMstMap;
	}
	
}
