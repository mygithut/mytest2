package com.dhcc.ftp.action;
/**
 * ����ƥ��ҵ��ģ�ⶨ��
 * @author Sunhongyu
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.Ftp1PrepayAdjust;
import com.dhcc.ftp.entity.FtpBusinessInfo;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.SCYTCZlineF;

public class QxppywmndjAction  extends BoBuilder {

	private String prdtNo;
	private String brNo;
	private String brName;
	private String manageLvl;
	private String curNo;
	private String curName;
	private String businessNo;
	private String businessName;
	private String productNo;
	private String productName;
	private String amt;
	private String rate;
	private String opnDate;
	private String mtrDate;
	private String hkTerm;
	private String zjTerm;
	private String scrapValueRate;
	HttpServletRequest request = getRequest();
	HttpServletResponse resp = getResponse();
	DaoFactory df = new DaoFactory();

	public String execute() throws Exception {
		return super.execute();
	}
	
	public String getMethodNo() throws Exception {
		String[] ftp_methodComb=FtpUtil.getFTPMethod_byPrdtNo(prdtNo,brNo);
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(ftp_methodComb[0]);
		return null;
	}
	public String ftp_compute_mn() throws Exception {

		String[] brNos = brNo.split(",");
		String[] brNames = CommonFunctions.Chinese_CodeChange(brName).split(",");
		String[] manageLvls = manageLvl.split(",");
		String[] curNos = curNo.split(",");
		String[] curNames =  CommonFunctions.Chinese_CodeChange(curName).split(",");
		String[] businessNos = businessNo.split(",");
		String[] businessNames = CommonFunctions.Chinese_CodeChange(businessName).split(",");
		String[] productNos = productNo.split(",");
		String[] productNames =  CommonFunctions.Chinese_CodeChange(productName).split(",");
		String[] amts = amt.split(",");
		String[] rates = rate.split(",");
		String[] opnDates = opnDate.split(",");
		String[] mtrDates = mtrDate.split(",");
		String[] hkTerms = hkTerm.split(",");
		String[] zjTerms = zjTerm.split(",");
		String[] scrapValueRates = scrapValueRate.split(",");
		String date = String.valueOf(CommonFunctions.GetDBSysDate());
		String sysdate = String.valueOf(CommonFunctions.GetCurrentDateInLong());//���������
		int ftp_total_num=0;//��ѡ�����۵��ܱ���
		int ftp_success_num=0;//���۳ɹ��ܱ���
		int ftp_fault_num=0;//����ʧ���ܱ���
		List<FtpBusinessInfo> ftped_data_successList=new ArrayList<FtpBusinessInfo>();//���۳ɹ���ҵ���¼list
		List<FtpBusinessInfo> ftped_data_errorList=new ArrayList<FtpBusinessInfo>();//����ʧ�ܵ�ҵ���¼list
		
		//������Ʒ�ĸ�����Ե���
		Map<Integer, Double> ckzbjAdjustMap = uL06BO.getCkzbjAdjustValue();//���׼�������
		Map<Integer, Double> ldxAdjustMap = uL06BO.getLdxAdjustValue();//�����Ե���
		Map<String, Double> irAdjustMap = uL06BO.getIrAdjustValue();//���÷��յ���
		List<Ftp1PrepayAdjust> prepayList = uL06BO.getPrepayAdjustValue();//��ǰ����/��ǰ֧ȡ����
		Map<String, Double> clAdjustMap = uL06BO.getClAdjustValueByxls(brNos[0], manageLvls[0]);//���Ե���
		double[][] dkAdjustArr = uL06BO.getDkAdjustValue();//�����������
		Double[][] publicRate = FtpUtil.getFtpPublicRate();//���д�������
		//double[][] dkSfblAdjustArr=uL06BO.getDkSfblAdjustValue();//�����ϸ�������������
		
		for(int i = 0; i < brNos.length; i++) {
			int term = 0;
			if(!mtrDates[i].equals("/")){
			    term = CommonFunctions.daysSubtract(mtrDates[i], opnDates[i]);
			}
			try{
				//��������ҵ���� ���ζ��� ���������۽������ftped_data_successList ��  ftped_data_errorList
				FtpBusinessInfo ftp_business_info = new FtpBusinessInfo();
				ftp_business_info.setBrNo(brNos[i]);
				ftp_business_info.setBrName(brNames[i]);
				ftp_business_info.setBusinessNo(businessNos[i]);
				ftp_business_info.setBusinessName(businessNames[i]);
				ftp_business_info.setCurNo(curNos[i]);
				ftp_business_info.setCurName(curNames[i]);
				ftp_business_info.setOpnDate(opnDates[i]);
				ftp_business_info.setMtrDate(mtrDates[i]);
				ftp_business_info.setTerm(String.valueOf(term));
				ftp_business_info.setPrdtNo(productNos[i]);
				ftp_business_info.setPrdtName(productNames[i]);
				ftp_business_info.setAmt(amts[i]);
				ftp_business_info.setRate(rates[i]);
				ftp_business_info.setHkTerm(hkTerms[i]);
				ftp_business_info.setZjTerm(zjTerms[i]);
				ftp_business_info.setScrapValueRate(scrapValueRates[i]);
				String[] ftp_methodComb=FtpUtil.getFTPMethod_byPrdtNo(productNos[i],brNos[i]);
				if(ftp_methodComb==null){//û�����ñ����ҵ���Ӧ���۷�����¼�У��򲻶Ըñ�ҵ�񶨼�
					ftp_business_info.setMethodName("��δ����");
					ftped_data_errorList.add(ftp_business_info);
					ftp_fault_num++;
					continue;
				}
				
				double adjust_rate=Double.valueOf(ftp_methodComb[2]);//��������
				String method_no=ftp_methodComb[0];//���嶨�۷������
				String curve_no=ftp_methodComb[3];//ʹ�õ����������߱��
				
				String br_no = FtpUtil.getXlsBrNo_sylqx(brNos[i], manageLvls[i]);
				SCYTCZlineF f=new SCYTCZlineF();
				if(!"��".equals(curve_no) ){
					 f= FtpUtil.getSCYTCZlineF_fromDB(curve_no,opnDates[i], br_no);
					 if(f==null){
						 //ftp_business_info.setCurveName("δ����");//ҳ����ֱ�Ӹ������߱������ȡ��ҳ�������߱��Ϊnull����ȡ��������:��δ��������
						 ftp_business_info.setMethodNo(method_no);
						 ftp_business_info.setMethodName(FtpUtil.getMethodName_byMethodNo(method_no));////
						 ftped_data_errorList.add(ftp_business_info);
						 ftp_fault_num++;
						 continue;
					 }
				}						
				
				if(method_no.equals("06")){//ֻ�С����ʴ����06����ʹ�á��ο����ޡ� 
					term=Integer.valueOf(ftp_methodComb[1]);//�ο�����
				}

				
				double appoint_rate=Double.valueOf(ftp_methodComb[4]);//ָ������
				double appoint_delta_rate=Double.valueOf(ftp_methodComb[5]);//�̶�����
				
				double ftp_price=-1;
				if(method_no.equals("01")){//## ԭʼ����ƥ�䷨
					ftp_price=FtpUtil.getFTPPrice_ysqxppf(term, f, adjust_rate);
				}else if(method_no.equals("02")){//## ָ�����ʷ�
					//ftp_price=FtpUtil.getFTPPrice_zdllf(term, curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
					ftp_price=appoint_rate+adjust_rate;
				}else if(method_no.equals("03")){//## �ض�������ƥ�䷨
					/////
				}else if(method_no.equals("04")){//## �ֽ�����,��������
					ftp_price=FtpUtil.getFTPPrice_xjlf(term,Integer.valueOf(hkTerms[i]),f, adjust_rate);
				}else if(method_no.equals("05")){//## ���ڷ�
					if(ftp_business_info.getPrdtNo().substring(0, 4).equals("P109")){//���Ҵ����������
						ftp_price=FtpUtil.getFTPPrice_jqf(term,Integer.valueOf(hkTerms[i]),0,f, adjust_rate);
					}else{//�̶��ʲ�,Ĭ�����ò�ֵ��Ϊ0.4���۾�����
						ftp_price=FtpUtil.getFTPPrice_jqf(term,Integer.valueOf(zjTerms[i]),Double.valueOf(scrapValueRates[i])/100,f, adjust_rate);
					}		
				}else if(method_no.equals("06")){//## ���ʴ����
					ftp_price=FtpUtil.getFTPPrice_lldmcef(term, f, adjust_rate);
				}else if(method_no.equals("07")){//## ��Ȩ���ʷ�
					ftp_price=FtpUtil.getFTPPrice_jqllf(f, adjust_rate);
				}else if(method_no.equals("08")){//## �̶����
					if(ftp_business_info.getPrdtNo().startsWith("P1")){//�ʲ���Ʒ��FTP�۸�=�ͻ�������-�̶�����ֵ
						ftp_price=Double.parseDouble(ftp_business_info.getRate())/100-appoint_delta_rate+adjust_rate;
					}else{//��ծ��Ʒ��FTP�۸�=�ͻ��˳ɱ�+�̶�����ֵ
						ftp_price=Double.parseDouble(ftp_business_info.getRate())/100+appoint_delta_rate+adjust_rate;
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
				if(ftp_methodComb[8].equals("1")){
					double amt = (ftp_business_info.getAmt()==null||ftp_business_info.getAmt().equals(""))?Double.NaN:Double.valueOf(ftp_business_info.getAmt());
					double rate = (ftp_business_info.getRate()==null||ftp_business_info.getRate().equals(""))?Double.NaN:Double.valueOf(ftp_business_info.getRate())/100;
					adjustValue = FtpUtil.getDkAmtAdjust(ftp_business_info.getPrdtNo(), term, amt, dkAdjustArr, publicRate,rate);
				}else {//�����н�����
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
					double jc = ftp_business_info.getPrdtNo().substring(0, 2).equals("P2")?(ftp_price-Double.valueOf(rates[i])/100):(Double.valueOf(rates[i])/100-ftp_price);
					if(!mtrDates[i].equals("/"))//���ں��ֽ�û���ܾ�������
						ftp_business_info.setTotalProfit(Double.valueOf(amts[i])*term*jc/365);
					String endDate = sysdate.substring(0,4)+"1231";//��ĩ����
					if(mtrDates[i].equals("/")) mtrDates[i] = endDate;//���ں��ֽ𣬲����ǵ������ڣ�ֱ�Ӱ���ĩ-��ʼ���ڼ���
					if(CommonFunctions.daysSubtract(endDate, mtrDates[i]) > 0) {//������������ڽ������ڣ����꾭������=�ܾ�������
						ftp_business_info.setCurYearProfit(ftp_business_info.getTotalProfit());
					}else {//����������ڳ������꣬������=��ĩ����-��ʼ���ڣ����¼���
						int term1 = CommonFunctions.daysSubtract(endDate, opnDates[i]);
						ftp_business_info.setCurYearProfit(Double.valueOf(amts[i])*term1*jc/365);
					}
					ftped_data_successList.add(ftp_business_info);
					ftp_success_num++;
				}														
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		ftp_total_num=ftp_fault_num+ftp_success_num;
		String ftpResultDescribe="������ɣ��ܹ�ѡ��"+ftp_total_num+"�ʣ����гɹ�����"+ftp_success_num+"�ʣ�ʧ��"+ftp_fault_num+"�ʣ�";
		request.getSession().setAttribute("ftped_data_successList", ftped_data_successList);
		request.getSession().setAttribute("ftped_data_errorList", ftped_data_errorList);
		request.getSession().setAttribute("date", date);
		request.setAttribute("ftpResultDescribe", ftpResultDescribe);
		return "compute_result";
	 }

	public String getPrdtNo() {
		return prdtNo;
	}

	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}

	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getBrName() {
		return brName;
	}

	public void setBrName(String brName) {
		this.brName = brName;
	}

	public String getCurNo() {
		return curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public String getCurName() {
		return curName;
	}

	public void setCurName(String curName) {
		this.curName = curName;
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

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getOpnDate() {
		return opnDate;
	}

	public void setOpnDate(String opnDate) {
		this.opnDate = opnDate;
	}


	public String getMtrDate() {
		return mtrDate;
	}

	public void setMtrDate(String mtrDate) {
		this.mtrDate = mtrDate;
	}

	public String getHkTerm() {
		return hkTerm;
	}

	public void setHkTerm(String hkTerm) {
		this.hkTerm = hkTerm;
	}

	public String getZjTerm() {
		return zjTerm;
	}

	public void setZjTerm(String zjTerm) {
		this.zjTerm = zjTerm;
	}

	public String getManageLvl() {
		return manageLvl;
	}

	public void setManageLvl(String manageLvl) {
		this.manageLvl = manageLvl;
	}

	public String getScrapValueRate() {
		return scrapValueRate;
	}

	public void setScrapValueRate(String scrapValueRate) {
		this.scrapValueRate = scrapValueRate;
	}


}
