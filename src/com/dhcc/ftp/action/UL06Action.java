package com.dhcc.ftp.action;
/**
 * ����ƥ��
 * @author Sunhongyu
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.Ftp1PrepayAdjust;
import com.dhcc.ftp.entity.FtpBusinessInfo;
import com.dhcc.ftp.entity.FtpPublicRate;
import com.dhcc.ftp.entity.FtpQxppResult;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;
import com.dhcc.ftp.util.SCYTCZlineF;

public class UL06Action  extends BoBuilder {

	private String brNo;
	private String curNo;
	private String businessNo;
	private String prdtNo;
	private String opnDate1;
	private String opnDate2;
	private String mtrDate1;
	private String mtrDate2;
	private String checkAll;
	private String currentPage;
	private String pageSize;
	private String no;
	private String isQuery;
	private String date;
	private int page = 1;
	private PageUtil UL06Util = null;
	private PageUtil ULFtp06Util = null;
	private PageUtil ULFtpSuccess06Util = null;
	private PageUtil ULFtpError06Util = null;
	HttpServletRequest request = getRequest();
	private String savePath = null;
	private String manageLvl;
	DaoFactory df = new DaoFactory();

	public String execute() throws Exception {
		return super.execute();
	}
	
	public String List() throws Exception {
		UL06Util = uL06BO.dofind(request, page, checkAll, isQuery, brNo,prdtNo, curNo, businessNo, opnDate1, opnDate2, mtrDate1, mtrDate2);
		System.out.println("��ѯ��ȡҵ���б������");
		request.setAttribute("checkAll", checkAll);
		request.setAttribute("UL06Util", UL06Util);
		request.setAttribute("opnDate2", opnDate2);
		return "list";
	}
	public String ftp_compute() throws Exception {
		//list javabean
		List<FtpBusinessInfo> ftpBusinessInfoList = (List<FtpBusinessInfo>)request.getSession().getAttribute("ftpBusinessInfoList");
		
		Map<String,SCYTCZlineF> curvesF_map=FtpUtil.getMap_AllCurves_N(date, brNo, manageLvl);//dateΪul06List.jspҳ�����ȡ�ĵ�ǰ���ݿ�ϵͳ����
		Map<String,String[]> ftpMethodMap = FtpUtil.getMap_FTPMethod_xls(brNo, manageLvl);
		List<FtpBusinessInfo> ftped_data_successList=new ArrayList<FtpBusinessInfo>();//���۳ɹ���ҵ���¼list
		List<FtpBusinessInfo> ftped_data_errorList=new ArrayList<FtpBusinessInfo>();//����ʧ�ܵ�ҵ���¼list
		long sys_date_long=CommonFunctions.GetDBSysDate(); 
		
		//������Ʒ�ĸ�����Ե���
		Map<Integer, Double> ckzbjAdjustMap = uL06BO.getCkzbjAdjustValue();//���׼�������
		Map<Integer, Double> ldxAdjustMap = uL06BO.getLdxAdjustValue();//�����Ե���
		Map<String, Double> irAdjustMap = uL06BO.getIrAdjustValue();//���÷��յ���
		List<Ftp1PrepayAdjust> prepayList = uL06BO.getPrepayAdjustValue();//��ǰ����/��ǰ֧ȡ����
		Map<String, Double> clAdjustMap = uL06BO.getClAdjustValueByxls(brNo,manageLvl);//���Ե���
		double[][] dkAdjustArr = uL06BO.getDkAdjustValue();//�����������
		Double[][] publicRate = FtpUtil.getFtpPublicRate();//���д�������
		//double[][] dkSfblAdjustArr=uL06BO.getDkSfblAdjustValue();//�����ϸ�������������
		
		int ftp_total_num=0;//��ѡ�����۵��ܱ���
		int ftp_success_num=0;//���۳ɹ��ܱ���
		int ftp_fault_num=0;//����ʧ���ܱ���
		try{
			List<FtpBusinessInfo> selectted_dataList=new ArrayList<FtpBusinessInfo>();
			if (checkAll.equals("false")) {
				//ѡ�е�ǰҳ����
				System.out.println("no:"+no);
				System.out.println("currentPage:"+currentPage);//��ǰҳ��
				System.out.println("pageSize:"+pageSize);//ÿҳ��ʾ����
				String[] noStr = no.split(",");//ѡ�е����ڵ�ǰҳlist��λ��0-99
				int currentPage_int=Integer.valueOf(currentPage);
				int pageSize_int=Integer.valueOf(pageSize);
				int start=(currentPage_int-1)*pageSize_int;
				int end=currentPage_int*pageSize_int-1;
				List<FtpBusinessInfo> dataList=ftpBusinessInfoList.subList(start, Math.min(end+1, ftpBusinessInfoList.size()-(currentPage_int-1)*100));//��ѡҳ��ȫ����ҵ������list				
				for(int i=0;i<noStr.length;i++){
					selectted_dataList.add(dataList.get(Integer.valueOf(noStr[i])));
				}
			}else{//����ҳ��������ȫѡ
				selectted_dataList=ftpBusinessInfoList;
			}
			
			//����ѡ��ҵ���� ���ζ��� ���������۽������ftped_data_successList ��  ftped_data_errorList
			for(int i=0;i<selectted_dataList.size();i++){
				FtpBusinessInfo ftp_business_info=selectted_dataList.get(i);
				String[] ftp_methodComb=ftpMethodMap.get(ftp_business_info.getPrdtNo());
				if(ftp_methodComb==null){//û�����ñ����ҵ���Ӧ���۷�����¼�У��򲻶Ըñ�ҵ�񶨼�
					ftp_business_info.setMethodName("��δ����");
					ftped_data_errorList.add(ftp_business_info);
					ftp_fault_num++;
					continue;
				}
				
				
				double adjust_rate=Double.valueOf(ftp_methodComb[2]);//��������
				String method_no=ftp_methodComb[0];//���嶨�۷������
				int term=(ftp_business_info.getTerm()==null || ftp_business_info.getTerm().equals(""))?0:Integer.valueOf(ftp_business_info.getTerm());
				if(method_no.equals("06")){//ֻ�С����ʴ����06����ʹ�á��ο����ޡ� 
					term=Integer.valueOf(ftp_methodComb[1]);//�ο�����
				}
				String curve_no=ftp_methodComb[3];//ʹ�õ����������߱��
				
				String curve_date="";//ѡ�����������ߵ���������,ʵ�֡�����ѡ���ϸ�ÿ�ա��¼ӡ�
				// ����ǻ��ڴ��YW201���ֽ�YW107�������ʲ�YW108��������ծYW209������ѡ�����������ߵ���������Ϊ���ݿ�ϵͳ����
				if(("YW201").equals(ftp_business_info.getBusinessNo()) || ("YW107").equals(ftp_business_info.getBusinessNo())){
					curve_date=String.valueOf(sys_date_long);
				
				//�������ݿ�ϵͳ����Ϊÿ��1��10�ţ��Ҷԡ��г��ڴ���ҵ��ʵ���ض��� ʱ����ѡ������1��1�� (�� �俪����  �����д���Ǹ�)
				}else if(sys_date_long%10000==110 && ftp_business_info.getPrdtNo().startsWith("P1") && (ftp_business_info.getPrdtName().indexOf("3��")>=0  || ftp_business_info.getPrdtName().indexOf("5��")>=0 )){
					curve_date=String.valueOf(Math.max(sys_date_long/10000+101, Long.valueOf(ftp_business_info.getOpnDate()==null?"19000101":ftp_business_info.getOpnDate())));
				}else{//����ҵ�� ,����ѡ�����������ߵ���������Ϊ��ҵ��������<��������>---��������Ϊ�գ���Ĭ��ȡ���ݿ�ϵͳ����
					curve_date=(ftp_business_info.getOpnDate()==null || ftp_business_info.getOpnDate().equals(""))?String.valueOf(sys_date_long):ftp_business_info.getOpnDate();
				}
				
				if(!"��".equals(curve_no) && curvesF_map.get(curve_date+"-"+curve_no)==null){
					//ftp_business_info.setCurveName("��δ����");
					ftped_data_errorList.add(ftp_business_info);
					ftp_fault_num++;
					continue;
				}
				double appoint_rate=Double.valueOf(ftp_methodComb[4]);//ָ������
				double appoint_delta_rate=Double.valueOf(ftp_methodComb[5]);//�̶�����
				
				double ftp_price=-1;
				if(method_no.equals("01")){//## ԭʼ����ƥ�䷨
					ftp_price=FtpUtil.getFTPPrice_ysqxppf(term, curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("02")){//## ָ�����ʷ�
					//ftp_price=FtpUtil.getFTPPrice_zdllf(term, curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
					ftp_price=appoint_rate+adjust_rate;
				}else if(method_no.equals("03")){//## �ض�������ƥ�䷨---��ʱ�����ض���ԭʼ����ƥ�䷨<���۴��������ڲ�ѯ�б����(����ѡ��������ǰ�浥������)��Ȼ������㷨�ͺ�ԭʼ����ƥ�䷨һ����>
					ftp_price=FtpUtil.getFTPPrice_ysqxppf(term, curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("04")){//## �ֽ�����,��������
					ftp_price=FtpUtil.getFTPPrice_xjlf(term,30,curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("05")){//## ���ڷ�
				/*	if(ftp_business_info.getPrdtNo().substring(0, 4).equals("P109")){//���Ҵ����������
						ftp_price=FtpUtil.getFTPPrice_jqf(term,30,0,curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
					}else{//�̶��ʲ�,Ĭ�����ò�ֵ��Ϊ0.4���۾�����
						ftp_price=FtpUtil.getFTPPrice_jqf(term,365,0.4,curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
					}*/
					ftp_price=FtpUtil.getFTPPrice_jqf(term,30,0,curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("06")){//## ���ʴ����
					ftp_price=FtpUtil.getFTPPrice_lldmcef(term, curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("07")){//## ��Ȩ���ʷ�
					ftp_price=FtpUtil.getFTPPrice_jqllf(curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("08")){//## �̶����
					if(ftp_business_info.getPrdtNo().startsWith("P1")){//�ʲ���Ʒ��FTP�۸�=�ͻ�������-�̶�����ֵ
						ftp_price=Double.parseDouble(ftp_business_info.getRate())-appoint_delta_rate+adjust_rate;
					}else{//��ծ��Ʒ��FTP�۸�=�ͻ��˳ɱ�+�̶�����ֵ
						ftp_price=Double.parseDouble(ftp_business_info.getRate())+appoint_delta_rate+adjust_rate;
					}
					//ftp_price=Double.parseDouble(ftp_business_info.getRate())+appoint_delta_rate+adjust_rate;
				}else{
					ftp_business_info.setMethodName("����"+method_no+"���ô���");
					ftped_data_errorList.add(ftp_business_info);
					ftp_fault_num++;
					continue;
				}
				//FTP����
				double adjustValue = 0;
				//�Ƿ������=���ǡ������Ǵ���
				if(ftp_methodComb[9].equals("1")){
					double amt = (ftp_business_info.getAmt()==null||ftp_business_info.getAmt().equals(""))?Double.NaN:Double.valueOf(ftp_business_info.getAmt());
					double rate = (ftp_business_info.getRate()==null||ftp_business_info.getRate().equals(""))?Double.NaN:Double.valueOf(ftp_business_info.getRate());
					adjustValue = FtpUtil.getDkAmtAdjust(ftp_business_info.getPrdtNo(), term, amt, dkAdjustArr, publicRate,rate);
				}else {//���򣬶���������ĵ���
					//ʹ�ô�������������ߵĲ�Ʒ
					if(curve_no.equals("0100")) {
						adjustValue = FtpUtil.getCdkFtpAdjustValue(ftp_business_info.getBusinessNo(), term, ckzbjAdjustMap, ldxAdjustMap, irAdjustMap, prepayList);
					}else if(curve_no.startsWith("02")) {//ʹ���г����������� +�����Է��ռӵ�+����ռ�üӵ�
						adjustValue += Double.valueOf(ftp_methodComb[7])+Double.valueOf(ftp_methodComb[8]);
					}else{//������ʱʲôҲ����
						
					}
					//���Ե��������ݲ�Ʒ��ȡ��Ӧ������
					adjustValue += clAdjustMap.get(ftp_business_info.getPrdtNo()) == null ? 0 : clAdjustMap.get(ftp_business_info.getPrdtNo());
				}
				ftp_price += adjustValue;
				ftp_business_info.setFtpPrice(ftp_price);
				ftp_business_info.setMethodNo(method_no);
				ftp_business_info.setMethodName(FtpUtil.getMethodName_byMethodNo(method_no));////
				ftp_business_info.setCurveNo(curve_no);
				ftp_business_info.setWrkDate(date);
				if(Double.isNaN(ftp_price)){
					ftped_data_errorList.add(ftp_business_info);
					ftp_fault_num++;
				}else{
					ftped_data_successList.add(ftp_business_info);
					ftp_success_num++;
				}
			}														
		}catch(Exception e){
			e.printStackTrace();
		}
		ftp_total_num=ftp_fault_num+ftp_success_num;
		String ftpResultDescribe="������ɣ��ܹ�ѡ��"+ftp_total_num+"�ʣ����гɹ�����"+ftp_success_num+"�ʣ�ʧ��"+ftp_fault_num+"�ʣ�";
		System.out.println(ftpResultDescribe);
		request.getSession().setAttribute("ftped_data_successList", ftped_data_successList);
		request.getSession().setAttribute("ftped_data_errorList", ftped_data_errorList);
		ULFtpSuccess06Util = uL06BO.doFY(request, page, date, ftped_data_successList, "UL06_ftpedSuccessListFy");
		ULFtpError06Util = uL06BO.doFY(request, page, date, ftped_data_errorList, "UL06_ftpedErrorListFy");
	    request.getSession().setAttribute("ULFtpSuccess06Util", ULFtpSuccess06Util);
	    request.getSession().setAttribute("ULFtpError06Util", ULFtpError06Util);
		request.getSession().setAttribute("date", date);
		request.setAttribute("ftpResultDescribe", ftpResultDescribe);
		return "compute_result";
	 }
	/**
	 * ���۳ɹ��б�ķ�ҳlist
	 * @return
	 * @throws Exception
	 */
	 public String ftpedSuccessListFy() throws Exception {
		 List<FtpBusinessInfo> ftped_data_successList = (List<FtpBusinessInfo>)request.getSession().getAttribute("ftped_data_successList");
		 ULFtpSuccess06Util = uL06BO.doFY(request, page, date, ftped_data_successList, "UL06_ftpedSuccessListFy");
		 request.getSession().setAttribute("ULFtpSuccess06Util", ULFtpSuccess06Util);
		 return "compute_success_list";
	 }
	 /**
	  * ����ʧ���б�ķ�ҳlist
      * @return
	  * @throws Exception
	  */
	 public String ftpedErrorListFy() throws Exception {
		 List<FtpBusinessInfo> ftped_data_errorList = (List<FtpBusinessInfo>)request.getSession().getAttribute("ftped_data_errorList");
		 ULFtpError06Util = uL06BO.doFY(request, page, date, ftped_data_errorList, "UL06_ftpedErrorListFy");
		 request.getSession().setAttribute("ULFtpError06Util", ULFtpError06Util);
		 return "compute_error_list";
	 }
	 /**
	  * ���۽������
	  * @return
	  * @throws Exception
	  */
	 public String Save() {
		 try{
			 //ftped_dataList��ȡ���Ѿ���ȫ���ɹ����۵�
			 List<FtpBusinessInfo> ftped_dataList = (List<FtpBusinessInfo>)request.getSession().getAttribute("ftped_data_successList");
			 date = (String)request.getSession().getAttribute("date");
			 int ftpSave_total_num=ftped_dataList.size();//��ѡ��������ܱ���
			 int ftpSave_success_num=0;//�ɹ������ܱ���
			 //int ftpSave_fault_num=0;//�򶨼�ʧ�ܶ�δ������ܱ���
			 String STime=CommonFunctions.GetCurrentTime();
			 //�����ʱ��
			 String wrkTime = CommonFunctions.GetCurrentDateInLong() + CommonFunctions.getCurrentTime();
			 String wrkSysDate=date;
			 List<FtpQxppResult> ftpQxppResult_saveList=new ArrayList<FtpQxppResult>();////�������������¼�
			 String[] delHsqlS=new String[ftped_dataList.size()];//��������ɾ���¼�
			 //String acIdSql="acId in(";//ɾ����ʱ̫�����--���ַ���ִ�в���ȥ
			 String isAll_price="0";//�Ƿ��Ǹû�����ȫ��ҵ�񡯡�����ҳȫѡ������-----already�޸�Ϊǰ̨����
			 if(businessNo.equals("")&&checkAll.equals("true")) {//��ҵ�����͡�Ϊ:��ѡ��(ȫ��ҵ��)��������ҳȫѡ��Ϊѡ��
				 isAll_price = "1";
			 }
			 String br_lvl=LrmUtil.getBr_lvlInfo(brNo)[0];//�������������缶��Ϊ2
			 if(isAll_price.equals("1") && br_lvl.equals("2")){//�������缶���ҡ�ȫ���ۡ�����ֱ��ɾ�����������µĵ�ǰϵͳ���ڵ��������ж��۽����¼
				 System.out.println("isAll_price="+isAll_price);
				 String delHsql = "delete from FtpQxppResult where wrkSysDate = '"+wrkSysDate+"'";
				 df.delete(delHsql, null); 
			 }
			 
			 for (int i = 0; i < ftped_dataList.size(); i++) {
				 FtpBusinessInfo ftp_business_info = ftped_dataList.get(i);
				 //δ�ɹ����۵Ĳ�����
	    	     /*if (ftp_business_info.getFtpPrice() == null || Double.isNaN(ftp_business_info.getFtpPrice())) {
	    	    	 ftpSave_fault_num++;
	        	     continue;
				 }*/
	    	     FtpQxppResult ftpQxppResult = new FtpQxppResult();
				 int wrkNum = 0;
				 //ͬһ����ֻ�ܶ���һ�Σ�����ac_id����������wrkSysDate+ --<��������Ŀ��>(������Ϊ���ڴ������������)--��ѯ�Ƿ��Ѿ����ۣ�����У�����ɾ���������
				 /*String hsql = "from FtpQxppResult where acId = '"+ftp_business_info.getAcId()+"' and wrkSysDate = '"+wrkSysDate+"'";
				 System.out.println("hsql:"+hsql);
				 FtpQxppResult ftpQxppResultOld = (FtpQxppResult)df.getBean(hsql, null);
				 if(ftpQxppResultOld != null) {
					 String delHsql = "delete from FtpQxppResult where acId = '"+ftp_business_info.getAcId()+"' and wrkSysDate = '"+wrkSysDate+"'";
					 df.delete(delHsql, null); 
				 }*/
				 //���ò飬ֱ��ɾ���������ڵĸñ���ǰ����<������û��>----ͬһ������ֻ�ܶ���һ��
				 if(!isAll_price.equals("1") || !br_lvl.equals("2")){//����������ȫ�����ۣ��Ű�ac_id�������ɾ��sql���
					 String delHsql = "delete from FtpQxppResult where acId = '"+ftp_business_info.getAcId()+"' and wrkSysDate = '"+wrkSysDate+"'";
					 delHsqlS[i]=delHsql;////��������ɾ���¼�
				 }
				 
				 //acIdSql+="'"+ftp_business_info.getAcId()+"',";
				 ////df.delete(delHsql, null); 
				 
				 //����ac_id��ftp_qxpp_result��ȡ�����Wrk_time�Ķ��۴�����----����ũ�ţ���ʱȫΪ1�ζ���
				 /*String sql = "select max(wrk_num) from ftp_qxpp_result where ac_id = '"+ftp_business_info.getAcId()+"'";
			     List wrkNumList = df.query1(sql, null);
			     if (wrkNumList != null && wrkNumList.size() > 0 && wrkNumList.get(0)!=null) {
			    	 wrkNum = Integer.valueOf(wrkNumList.get(0).toString());
			     }*/
			     ftpQxppResult.setAcId(ftp_business_info.getAcId());
			     //ftpQxppResult.setAcSqen(ftp_business_info.getAcSeqn());
			     ftpQxppResult.setWrkNum(wrkNum + 1);
			     ftpQxppResult.setAcId(ftp_business_info.getAcId());
			     ftpQxppResult.setBrNo(ftp_business_info.getBrNo());
			     ftpQxppResult.setCurNo(ftp_business_info.getCurNo());
			     ftpQxppResult.setBusinessNo(ftp_business_info.getBusinessNo());
			     ftpQxppResult.setBusinessName(ftp_business_info.getBusinessName());
			     ftpQxppResult.setProductNo(ftp_business_info.getPrdtNo());
			     ftpQxppResult.setProductName(ftp_business_info.getPrdtName());
			     ftpQxppResult.setFtpPrice(ftp_business_info.getFtpPrice());
			     ftpQxppResult.setMethodNo(ftp_business_info.getMethodNo());
			     ftpQxppResult.setCurveNo(ftp_business_info.getCurveNo());
			     ftpQxppResult.setTelNo(ftp_business_info.getTel());
			     ftpQxppResult.setFtpTelNo(((TelMst) request.getSession().getAttribute("userBean")).getTelNo());
				 ftpQxppResult.setWrkTime(wrkTime);
				 ftpQxppResult.setWrkSysDate(wrkSysDate);
				 //���� �¼�
				 ftpQxppResult.setIsZq(ftp_business_info.getIsZq());
				 ftpQxppResult.setCusName(ftp_business_info.getCustomName());
				 ftpQxppResult.setOpnDate(ftp_business_info.getOpnDate());
				 ftpQxppResult.setAmt(ftp_business_info.getAmt()==null?null:Double.valueOf(ftp_business_info.getAmt()));
				 ftpQxppResult.setBal(Double.valueOf(ftp_business_info.getBal()));
				 ftpQxppResult.setRate(Double.valueOf(ftp_business_info.getRate()));
				 ftpQxppResult.setTerm(ftp_business_info.getTerm()==null?null:Integer.valueOf(ftp_business_info.getTerm()));
				 ftpQxppResult.setMtrDate(ftp_business_info.getMtrDate());
				 
				 ftpQxppResult.setKmh(ftp_business_info.getKmh());
				 
				 ftpQxppResult.setKhjl(ftp_business_info.getKhjl());
				 ftpQxppResult.setSjsflx(ftp_business_info.getSjsflx()==null?null:Double.valueOf(ftp_business_info.getSjsflx()));
				 
				 ftpQxppResult.setCustNo(ftp_business_info.getCustNo());//�ͻ����
				 ftpQxppResult.setZhzt(ftp_business_info.getZhzt());//�˻�״̬
				 ftpQxppResult.setFivSts(ftp_business_info.getFivSts());//'�弶����״̬',����ʵʩ�¼�
				 ftpQxppResult.setZqrq(ftp_business_info.getZqrq());//չ������
				 ftpQxppResult.setZqAmt((ftp_business_info.getZqAmt()==null||ftp_business_info.getZqAmt().equals(""))?null:Double.valueOf(ftp_business_info.getZqAmt()));//չ�ڽ��
				 ftpQxppResult.setZqMtrDate(ftp_business_info.getZqMtrDate());//չ�ڵ�������
				 
				 ftpQxppResult.setResultId(Long.parseLong(IDUtil.getInstanse().getUID()));//����ʹ��hibernate sequence������UID--���Ч��
				 
				 ////df.insert(ftpQxppResult);
				 ftpQxppResult_saveList.add(ftpQxppResult);////���������¼�

				 ftpSave_success_num++;
			}
			 if(!isAll_price.equals("1") || !br_lvl.equals("2")){//����������ȫ�����ۣ��Ű�ac_id���ɾ��
				 df.delete_s(delHsqlS, null);
			 }
			
			//acIdSql=acIdSql.substring(0,acIdSql.length()-1)+")";
			//String delSql="delete from FtpQxppResult where "+acIdSql+" and wrkSysDate = '"+wrkSysDate+"'";
			//System.out.println("delSql���ַ�����="+delSql.length());
			//df.delete(delSql, null);//ִ�в���ȥ
			
		    boolean save_success=df.insert_s(ftpQxppResult_saveList);
			//String ftpSaveDescribe="�ܹ�ѡ��"+ftpSave_total_num+"�ʣ����гɹ�����"+ftpSave_success_num+"�ʣ��򶨼�ʧ�ܶ�δ����"+ftpSave_fault_num+"�ʣ�";
		    String ftpSaveDescribe="����ɹ�!��"+ftpSave_success_num+"��!";
		    if(!save_success){
		    	ftpSaveDescribe="�������! ����ϵϵͳ��������Ա!";
		    }
			String ETime=CommonFunctions.GetCurrentTime();
			int costTime=CommonFunctions.GetCostTimeInSecond(STime, ETime);
			int CostFen=costTime/60;
			int CostMiao=costTime%60;
			ftpSaveDescribe+="��ʱ"+CostFen+"��"+CostMiao+"��";
			System.out.println(ftpSaveDescribe);
			
			HttpServletResponse res = getResponse();
			res.setContentType("text/plain;charset=UTF-8");
			res.getWriter().print(ftpSaveDescribe);
		    return null;
		 }catch(Exception e){
			 e.printStackTrace();
			 return null;
		 }
		 
	 
	 }

	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getCurNo() {
		return curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getOpnDate1() {
		return opnDate1;
	}

	public void setOpnDate1(String opnDate1) {
		this.opnDate1 = opnDate1;
	}

	public String getOpnDate2() {
		return opnDate2;
	}

	public void setOpnDate2(String opnDate2) {
		this.opnDate2 = opnDate2;
	}

	public String getMtrDate1() {
		return mtrDate1;
	}

	public void setMtrDate1(String mtrDate1) {
		this.mtrDate1 = mtrDate1;
	}

	public String getMtrDate2() {
		return mtrDate2;
	}

	public void setMtrDate2(String mtrDate2) {
		this.mtrDate2 = mtrDate2;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PageUtil getUL06Util() {
		return UL06Util;
	}

	public void setUL06Util(PageUtil uL06Util) {
		UL06Util = uL06Util;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getCheckAll() {
		return checkAll;
	}

	public void setCheckAll(String checkAll) {
		this.checkAll = checkAll;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public PageUtil getULFtpSuccess06Util() {
		return ULFtpSuccess06Util;
	}

	public void setULFtpSuccess06Util(PageUtil uLFtpSuccess06Util) {
		ULFtpSuccess06Util = uLFtpSuccess06Util;
	}

	public PageUtil getULFtpError06Util() {
		return ULFtpError06Util;
	}

	public void setULFtpError06Util(PageUtil uLFtpError06Util) {
		ULFtpError06Util = uLFtpError06Util;
	}

	public String getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}

	public PageUtil getULFtp06Util() {
		return ULFtp06Util;
	}

	public void setULFtp06Util(PageUtil uLFtp06Util) {
		ULFtp06Util = uLFtp06Util;
	}

	public String getManageLvl() {
		return manageLvl;
	}

	public void setManageLvl(String manageLvl) {
		this.manageLvl = manageLvl;
	}

	public String getPrdtNo() {
		return prdtNo;
	}

	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}

}
