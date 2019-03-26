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
 * Listener�ķ�ʽ�ں�ִ̨��һ�߳�
 * 
 * @author Champion.Wong
 * 
 */
public class MyListener implements ServletContextListener {
	

	public Map<String, List<BrMst>> brMstMap = this.getBrByEmpNo();
	public void contextInitialized(ServletContextEvent e) {

	 	System.out.println("�����ʼ��");
		try {
				System.out.println("===========����excel=================");
			   yhkhjltjbbExport();
			   System.out.println("============�������================");
			    //getmonthcache();//#����ʱע�͵�
			    //getjicache();//#����ʱע�͵�
			    //getyearcache();//#����ʱע�͵�
			    
			    
		   } catch (Exception e1)	{
			e1.printStackTrace();
		}

	}

	/**
	 * 
	 * �»���
	 */
	public static void getmonthcache() {

		String STime = CommonFunctions.GetCurrentTime();
		String sql = "from ComSysParm order by sysDate DESC";
		List<ComSysParm> list = CommonFunctions.mydao.query(sql, null);
		ComSysParm com_sys_parm = new ComSysParm();
		if (list.size() == 0) {
			System.out.println("ϵͳ���ڻ�ȡ�����������ݿ⹫��������com_sys_parm��");
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
		long intervalTime = Long.valueOf("2592000000");// ������һ����
		int maxVisitCount = 0;// �����Ʒ��ʴ���
		ReportBbBO rpb = new ReportBbBO();
		CacheOperation.getInstance().getCacheData(rpb, "getQxppResultList",
				new Object[] { xlsBrNo, minDate, maxDate }, intervalTime,
				maxVisitCount);

	}

	/**
	 * 
	 * �껺��
	 */
	public static void getyearcache() {

		String STime = CommonFunctions.GetCurrentTime();
		String sql = "from ComSysParm order by sysDate DESC";
		List<ComSysParm> list = CommonFunctions.mydao.query(sql, null);
		ComSysParm com_sys_parm = new ComSysParm();
		if (list.size() == 0) {
			System.out.println("ϵͳ���ڻ�ȡ�����������ݿ⹫��������com_sys_parm��");
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
		long intervalTime = Long.valueOf("2592000000");// ������һ����
		int maxVisitCount = 0;// �����Ʒ��ʴ���
		ReportBbBO rpb = new ReportBbBO();
		CacheOperation.getInstance().getCacheData(rpb, "getQxppResultList",
				new Object[] { xlsBrNo, minDate, maxDate }, intervalTime,
				maxVisitCount);

	}

	/**
	 * 
	 * ������
	 */
	public static void getjicache() {

		String STime = CommonFunctions.GetCurrentTime();
		String sql = "from ComSysParm order by sysDate DESC";
		List<ComSysParm> list = CommonFunctions.mydao.query(sql, null);
		ComSysParm com_sys_parm = new ComSysParm();
		if (list.size() == 0) {
			System.out.println("ϵͳ���ڻ�ȡ�����������ݿ⹫��������com_sys_parm��");
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
		long intervalTime = Long.valueOf("2592000000");// ������һ����
		int maxVisitCount = 0;// �����Ʒ��ʴ���
		ReportBbBO rpb = new ReportBbBO();
		CacheOperation.getInstance().getCacheData(rpb, "getQxppResultList",
				new Object[] { xlsBrNo, minDate, maxDate }, intervalTime,
				maxVisitCount);

	}

	/**
	  * 
	  * ÿ��ʱ�����
	  * @return
	  * 
	  * 
	  */
	public  List<String[]> getQxppResultList() {

		DaoFactory daoFactory = new DaoFactory(); 
		List<String[]> resultList = new ArrayList<String[]>();
		//�Ա�����ΪKEY���ϼ�����ΪValue��MAP
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
	  * ��Ա���˻�������Ϊ��׼����ȡ���ڷ�Χ�ڵ��˻��Ļ�������Ʒ���ͻ������վ��������˺š��ͻ���
	  * @param xlsbrNo
	  * @param minDate������˵�
	  * @param maxDate�����Ҷ˵�
	  * @return
	  */
	public static List<String[]> getQxppResultList(String xlsbrNo) {
		List<String[]> resultList=new ArrayList<String[]>();
		//String prdtNo = this.getPrdtNoQxpp(xlsbrNo);//����ƥ�������õ����в�Ʒ
		DaoFactory daoFactory = new DaoFactory(); 
		Map<String, Double> yeMap = new HashMap<String, Double>();
		Map<String, String> businessMap = new HashMap<String, String>();
		//1.��ȡÿ�����˻���ҵ���ź����
		String sql1 = "select AC_ID,BUSINESS_NO,BAL from ftp.FZH_HISTORY where Bal !=0";
		List dcfhqList = daoFactory.query1(sql1, null);
		if(dcfhqList.size() > 0) {
			for(Object object : dcfhqList) {
				Object[] obj = (Object[])object;
	            yeMap.put(obj[0].toString(), Double.valueOf(obj[2].toString()));
	            businessMap.put(obj[0].toString(), obj[1].toString());
			}
		}
		dcfhqList.clear(); System.gc();//�ͷ�dcfhqList��ռ���ڴ�
		//2.��ȡԱ����Ӧ�Ļ�����Ϣ
		Map<String, String> empInfoMap = new HashMap<String, String>();
		String sql3 = "select emp_no, trim(br_no) from ftp.ftp_emp_info";
		List empList = daoFactory.query1(sql3, null);
		if(empList != null && empList.size() > 0) {
			for(Object object : empList) {
				Object[] obj = (Object[])object;
				empInfoMap.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
			}
		}
		empList.clear(); System.gc();//�ͷ�empList��ռ���ڴ�
		//3.��ȡ�������ҵ��ͻ����������Ϣ
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
			Double rjye = yeMap.get(accId);//���
			if(rjye==null){
				//System.out.println("���Ϊnull��:"+accId);
				num_yue_null++;
			}else if(rjye==0){
				//System.out.println("���Ϊ0��:"+accId);
				num_yue_0++;
			}else{
				rjye_whole+=rjye;
				num_yue_not0++;
			}
			rjye = ((rjye==null)?0.0:rjye);
			String[] empNos = String.valueOf(obj[1]).split("@");
			String[] rates = String.valueOf(obj[2]).split("@");
			//������䷽ʽΪ�̶����ķ������
			double[] ratios = new double[rates.length];//�ָ����
			for(int g=0;g<rates.length;g++){
				ratios[g]=Double.valueOf(rates[g]);
			}
			if(String.valueOf(obj[3]).equals("2")) {//���䷽ʽΪ"�̶����"ʱ
				double sumAmt = 0;//sum�����䷽ʽΪ�̶����ʱ�ķָ����ܺ�
				for (int i = 0; i < ratios.length; i++) {
					//ratios[i] = Double.valueOf(rates[i]);
					sumAmt += ratios[i];
				}
				if(sumAmt < rjye) {//����ܺ�<�����һ���ͻ�������䵽���=���-��������������
					ratios[0] = rjye - (sumAmt-ratios[0]);
					sumAmt = rjye;
				}
				//�������ܺ�>=������Ҫ������ֱ�Ӱ������ռ�������м��㣬
					
				for (int i = 0; i < ratios.length; i++) {
					ratios[i] = ratios[i]/sumAmt;//�������
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
			//�������б�
			for (int i = 0; i < empNos.length; i++) {
				String[] result = new String[8];
				result[0] = empInfoMap.get(empNos[i]);//����
				if(result[0] == null) {
					//errorInfo += obj[0]+",";
					num_errorInfo++;
					continue;
				}
				result[1] = String.valueOf(businessMap.get(obj[0].toString()));//ҵ��
				result[2] = String.valueOf(empNos[i]);//�ͻ�������
				double avebal = rjye*ratios[i];
				rjye_whole_mx+=avebal;
				result[3] = String.valueOf(avebal);//���
				result[4] = String.valueOf(obj[0]);//�˺�
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
			System.out.println("�˺�Ϊ"+errorInfo+"<n>��Ա���˻�������������δ����ͳ�ƣ����飡");
		}
		accRelList.clear(); System.gc();//�ͷ�accRelList��ռ���ڴ�
		empInfoMap.clear(); System.gc();
		return resultList;
	}
	
	  /**
	 * �ͻ�����
	 * @param request
	 * @param minDate
	 * @param date
	 * @param empNo
	 * @param assessScope
	 * @return
	 */
    public List<YlfxBbReport> empPrdtProfile() {
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		//�ӻ����л�ȡ����
	    DaoFactory daoFactory = new DaoFactory(); 
	    
	    String STime = CommonFunctions.GetCurrentTime();
		String sql = "from ComSysParm order by sysDate DESC";
		List<ComSysParm> daylist = CommonFunctions.mydao.query(sql, null);
		ComSysParm com_sys_parm = new ComSysParm();
		if (daylist.size() == 0) {
			System.out.println("ϵͳ���ڻ�ȡ�����������ݿ⹫��������com_sys_parm��");
			return null;
		}
		com_sys_parm = daylist.get(0);
		long oldTodayDate = com_sys_parm.getTodayDate();
		String newTodayDate = String.valueOf(oldTodayDate);
	    
		String brNos = this.getAllChildBr("3403111034", "2", "000000");
		List<String[]> ftpResultsList = getQxppResultList("3403111034");
		if(ftpResultsList == null) return null;
		Map<String, Double> rjyejlMap = new HashMap<String, Double>();//key:�ͻ�������+ҵ���ŵĵ�3λ
		//�ӽ�����л�ȡĳЩ�ͻ���������ݣ����ݿͻ������Ż��ܵ�map��
		for (String[] result : ftpResultsList) {
			if(brNos.indexOf(result[0])!= -1) {
				double rjye = Double.valueOf(result[3]);
				//���
				if(rjyejlMap.get(result[2]+"-"+result[1].substring(2,3))==null){
					rjyejlMap.put(result[2]+"-"+result[1].substring(2,3), rjye);
				}else{
					rjyejlMap.put(result[2]+"-"+result[1].substring(2,3), rjye+rjyejlMap.get(result[2]+"-"+result[1].substring(2,3)));
				}
			}
		}
		
		ftpResultsList.clear();System.gc();
		
		//��ȡ�û����µ����пͻ�����
		String hsql1 = "from FtpEmpInfo where brMst.brNo  in ("+brNos+")";
		List<FtpEmpInfo> empList = daoFactory.query(hsql1, null);
        //ѭ���ͻ�������ȡҪͳ�Ƶ�ֵ
		for(FtpEmpInfo ftpEmpInfo : empList) {
			double zcrjye = rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-1")==null?0:rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-1");
			double fzrjye = rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-2");
			//ֻ��ʾ��ֵ��
			if((zcrjye!=0||fzrjye!=0)) {
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(ftpEmpInfo.getEmpNo());
				ylfxBbReport.setEmpName(ftpEmpInfo.getEmpName());
				ylfxBbReport.setBrNo(ftpEmpInfo.getBrMst().getBrNo());
				ylfxBbReport.setBrName(ftpEmpInfo.getBrMst().getBrName());
				ylfxBbReport.setZcrjye(zcrjye);//�ʲ����
				ylfxBbReport.setFzrjye(fzrjye);//��ծ���
				ylfxBbReport.setOpnDate(newTodayDate);//����
				ylfxBbReportList.add(ylfxBbReport);
			}
		}
		
		empList.clear();System.gc();
		rjyejlMap.clear();System.gc();
		
	   return ylfxBbReportList;
	}
	
	
    /**
	 * ���пͻ�����ʱ�����-����
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
		String title = "�ͻ�����ÿ��ʱ�����";
		HSSFSheet sheet = workbook.createSheet(title);//����һ��sheet
		try {
	        // ��ͷ�ϲ�
			sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 6 ));// �ϲ���һ�е�һ��13��
			sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 6 ));// �ϲ��ڶ��е�һ��13��
			//���õ�һ�л�����Ԫ��Ŀ��
	        sheet.setColumnWidth(0, 20*2*256);//���ȳ���2��Ϊ�˽���������п�Ȳ������ʾ��ѧ���������⣬����256�õ������ݲ���excel��ʵ�п�
			
			ExcelExport excelExport = new ExcelExport(workbook, sheet);
			excelExport.createRow(0); // ������һ��
			excelExport.setCell(0, title, excelExport.getHeaderStyle());
			
			excelExport.createRow(1); // �����ڶ���
			excelExport.setCell(0, "����������ũ����ʱ������                                                     ��λ��Ԫ��%(������)", excelExport.getTitleStyle());
			
			excelExport.createRow(2);//�����У���ͷ��
			sheet.setColumnWidth(0, 7*256);//���õ�һ�еĿ��   
			sheet.setColumnWidth(4, 25*256);//���õ�5�еĿ��  
			sheet.setColumnWidth(5, 12*256);//���õ�6�еĿ��   
			excelExport.setCell(0, "���", excelExport.headCenterNormalStyle);
			excelExport.setCell(1, "�ϼ�����", excelExport.headCenterNormalStyle);
			excelExport.setCell(2, "����", excelExport.headCenterNormalStyle);
			excelExport.setCell(3, "�ͻ���������", excelExport.headCenterNormalStyle);
			excelExport.setCell(4, "����", excelExport.headCenterNormalStyle);
			excelExport.setCell(5, "�ʲ����", excelExport.headCenterNormalStyle);
			excelExport.setCell(6, "��ծ���", excelExport.headCenterNormalStyle);
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
			sheet.getRow(0).setHeight((short)500);//���õ�һ�б��߶�
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		resultList.clear();System.gc();
		return workbook;
	}
	
	/**
	 * ���пͻ�����ʱ�����-����
	 * @return
	 * @throws Exception
	 */

	public String yhkhjltjbbExport() throws Exception {
		
		String STime = CommonFunctions.GetCurrentTime();
		String sql = "from ComSysParm order by sysDate DESC";
		List<ComSysParm> daylist = CommonFunctions.mydao.query(sql, null);
		ComSysParm com_sys_parm = new ComSysParm();
		if (daylist.size() == 0) {
			System.out.println("ϵͳ���ڻ�ȡ�����������ݿ⹫��������com_sys_parm��");
			return null;
		}
		com_sys_parm = daylist.get(0);
		long oldTodayDate = com_sys_parm.getTodayDate();
		String newTodayDate = String.valueOf(oldTodayDate);
		 List<String[]> list = getQxppResultList();
		//����������
		HSSFWorkbook workbook = getWorkbook(list);
		if (workbook != null) {
			//���EXCEL
			workbookInputStream(workbook,newTodayDate);
		}
		return null;
	}
	
	
    /**
	 * ����workbook,��workbookд�뵽InputStream
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
	 * ����������ں�ͳ��ά�Ȼ�ȡ��С����
	 * 
	 * @param maxDate
	 * @param assessScope
	 *            -1�¶�-3����-12���
	 * @return
	 */
	public static String getMinDate(String maxDate, Integer assessScope) {
		int nowMonth = Integer.valueOf(String.valueOf(maxDate).substring(4, 6));// ��ǰ��
		if (assessScope == -3) {// ����
			if (nowMonth >= 1 && nowMonth <= 3)
				assessScope = 0 - nowMonth;
			else if (nowMonth >= 4 && nowMonth <= 6)
				assessScope = 3 - nowMonth;
			else if (nowMonth >= 7 && nowMonth <= 9)
				assessScope = 6 - nowMonth;
			else if (nowMonth >= 10 && nowMonth <= 12)
				assessScope = 9 - nowMonth;
		} else if (assessScope == -12) {// ���
			assessScope = -nowMonth;
		}
		String minDate = CommonFunctions.dateModifyM(maxDate, assessScope);
		return minDate;
	}

	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}
	
	
	/**
	 * ���ݻ�����ţ���ȡ���е��¼�����+����(��������߼���2�Ļ���)����,�ָ�
	 * @param br_no
	 * @return
	 */
	public String getAllChildBr(String br_no, String br_level, String tel_no) {
		
		 DaoFactory daoFactory = new DaoFactory(); 
		
		String br_nos="";
		if("2".equals(br_level)){//��������У����ȡ���еĻ���
			List<BrMst> brMstList = brMstMap.get(tel_no);//��ȡ�ò���Ա��Ͻ�Ļ���
			if(brMstList == null || brMstList.size() == 0) {//"�߼�<���³�>"���𣬿��Բ鿴���л���
				String sql="select br_no from ftp.br_mst where manage_lvl !='2'";
				List br_noList=daoFactory.query1(sql, null);
				for(Object obj:br_noList){				
					br_nos+="'"+obj.toString()+"',";		
				}
			}else {//�м��³�||���в�����
				for (BrMst brMst : brMstList) {
					br_nos+="'"+brMst.getBrNo()+"',";//�ȼ����Լ�����
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
			br_nos="'"+br_no+"',";//�Ȱ��Լ�����ӽ�ȥ
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
	 * ��ȡTelBrConfig���У�����Ա�����õĻ���
	 * @param telNo
	 * @return MAP(KEY:TelNo��VALUE:List<BrMst>)
	 */
	public Map<String, List<BrMst>> getBrByEmpNo() {
		
		
		DaoFactory daoFactory = new DaoFactory(); 
		Map<String, List<BrMst>> brMstMap = new HashMap<String, List<BrMst>>();
		String hsql1 = "from TelBrConfig";//�������ļ��л�ȡ���������ò���Ա�Ļ���Ȩ��
		List<TelBrConfig> telBrConfigList = daoFactory.query(hsql1, null);
		if(telBrConfigList != null && telBrConfigList.size() > 0) {
			String hsql2 = "from BrMst order by brNo desc";//��ȡ���л���
			List<BrMst> brList = daoFactory.query(hsql2, null);
			for(TelBrConfig telBrConfig : telBrConfigList) {
				List<BrMst> brMstList = new ArrayList<BrMst>();
				String brNos = telBrConfig.getBrNos();//��@���
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
