package com.dhcc.ftp.bo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.cache.CacheOperation;
import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.Ftp1PrepayAdjust;
import com.dhcc.ftp.entity.FtpBusinessInfo;
import com.dhcc.ftp.entity.FtpPoolInfo;
import com.dhcc.ftp.entity.FtpProductMethodRel;
import com.dhcc.ftp.entity.FtpSystemInitial;
import com.dhcc.ftp.entity.YlfxReport;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.SCYTCZlineF;

public class ReportSfbBO extends BaseBo {

	/**
	 * ������ӯ������
	 * @param request
	 * @param date
	 * @param brNo
	 * @param manageLvl ��������
	 * @param assessScope ͳ��ά�� ��-1��-3��-12
	 * @param isMx �Ƿ�鿴��ϸ
	 * @return
	 */
	public List<YlfxReport> brPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, 
			Integer assessScope, Integer isMx) {
		CacheOperation co = CacheOperation.getInstance();//����
	    long intervalTime = Long.valueOf("2592000000");//������һ����
	    int maxVisitCount = 0;//�����Ʒ��ʴ���
		
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// ������
		System.out.println("�����磺" + xlsBrNo);
		String prcMode = FtpUtil.getPrcModeByBrNo(xlsBrNo);// �����õ��ʽ��
		if (prcMode == null)
			return null;
		request.getSession().setAttribute("prcMode", prcMode);
		System.out.println("�����õĶ��۲��ԣ�" + prcMode);
		String prdtNoZc = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "1"); // ��Ӧ�ʽ�ػ�����ƥ�������õ��ʲ����Ʒ
		String prdtNoFz = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "2"); // ��Ӧ�ʽ�ػ�����ƥ�������õĸ�ծ���Ʒ
		System.out.println("�ʲ��ʽ�ػ�����ƥ���Ʒ��" + prdtNoZc);
		System.out.println("��ծ�ʽ�ػ�����ƥ���Ʒ��" + prdtNoFz);
		if (prdtNoZc == null || prdtNoFz == null)
			return null;
		List ftpResultList = null;
		if(!prcMode.equals("4")) {//�����������ƥ�䷨
			ftpResultList = this.getFtpResult(date, xlsBrNo, prcMode);//��ȡ��Ӧ������Ķ��۽��list
		}
		String date2 = CommonFunctions.dateModifyM(date, assessScope);
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//���������꣬��ֻȡ��������ݣ���1�·ݿ�ʼ
	    	date2 = date2.substring(0,4)+"1231";
		
		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//��ȡ��ͳ�Ƶ�����
		
		if (isMx == 0) {// ������ǲ鿴�ӻ�����ӯ����������ֱ�Ӳ鿴�û�����ӯ������
			YlfxReport ylfxReport = new YlfxReport();
			String brSql = LrmUtil.getBrSql(brNo);
			ylfxReport.setBrName(brInfoDAO.getInfo(brNo).getBrName());
			ylfxReport.setBrNo(brNo);
			ylfxReport.setManageLvl(manageLvl);
			if(!prcMode.equals("4")) {//��������ƥ��
				ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// �ʲ����
				System.out.println("�ʲ����:" + ylfxReport.getZcye());
				ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoZc, "3", assessScope, true));// ��Ϣ�ʣ���Ȩƽ������
				System.out.println("��Ϣ��:" + ylfxReport.getSxl());
				ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//�ʲ�ƽ������
		        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
		        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//��ծƽ������
		        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
		        ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// ��ծ���
				System.out.println("��ծ���:" + ylfxReport.getFzye());
				ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoFz, "3", assessScope, true));// ��Ϣ�ʣ���Ȩƽ������
				System.out.println("��Ϣ��:" + ylfxReport.getFxl());
				Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
				ylfxReport.setZczyjg(resultPrice[0]);// �ʲ�ת�Ƽ۸�
				System.out.println("�ʲ�ת�Ƽ۸�:" + ylfxReport.getZczyjg());
				ylfxReport.setFzzyjg(resultPrice[1]);// ��ծת�Ƽ۸�
				System.out.println("��ծת�Ƽ۸�:" + ylfxReport.getFzzyjg());
				ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);// ��Ϣ����
				System.out.println("��Ϣ����:" + ylfxReport.getLxsr());
				ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);// ��Ϣ֧��
				System.out.println("��Ϣ֧��:" + ylfxReport.getLxzc());
				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
				System.out.println("�ʲ�������:" + ylfxReport.getZcjlc());
				ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// �ʲ�ת��֧��
				System.out.println("�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
				System.out.println("�ʲ�������:" + ylfxReport.getZcjsr());
				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
				System.out.println("��ծ������:" + ylfxReport.getFzjlc());
				ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// ��ծת������
				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// ������
			}else {
			       
				List<String[]> ftped_data_successList=(List<String[]>)co.getCacheData(this, "get_ftped_data_successList",new Object[]{xlsBrNo, "2", date}, intervalTime, maxVisitCount);
				if(ftped_data_successList == null) return null;
				double[] qxppZcValue = this.getQxppValueByBrNo(ftped_data_successList, date, daysSubtract, prdtNoZc, brSql);
				ylfxReport.setZcye(qxppZcValue[5]);// �ʲ����
				System.out.println("�ʲ����:"+ylfxReport.getZcye());
				ylfxReport.setSxl(qxppZcValue[0]);// ��Ϣ��
				System.out.println("��Ϣ��:"+ylfxReport.getSxl());
		        ylfxReport.setZcpjqx(qxppZcValue[1]);//�ʲ�ƽ������
		        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
		        ylfxReport.setZczyjg(qxppZcValue[2]);// �ʲ�ת�Ƽ۸�
				System.out.println("�ʲ�ת�Ƽ۸�:"+ylfxReport.getZczyjg());
				ylfxReport.setLxsr(qxppZcValue[3]);// ��Ϣ����
				System.out.println("��Ϣ����:"+ylfxReport.getLxsr());
				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
				System.out.println("�ʲ�������:" + ylfxReport.getZcjlc());
				ylfxReport.setZczyzc(qxppZcValue[4]);//�ʲ�ת��֧��
				System.out.println("�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
				System.out.println("�ʲ�������:" + ylfxReport.getZcjsr());
				
				double[] qxppFzValue = this.getQxppValueByBrNo(ftped_data_successList, date, daysSubtract, prdtNoFz, brSql);
				ylfxReport.setFzye(qxppFzValue[5]);// ��ծ���
				System.out.println("��ծ���:"+ylfxReport.getFzye());
				ylfxReport.setFxl(qxppFzValue[0]);// ��Ϣ��
				System.out.println("��Ϣ��:"+ylfxReport.getFxl());
		        ylfxReport.setFzpjqx(qxppFzValue[1]);//��ծƽ������
		        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
		        ylfxReport.setFzzyjg(qxppFzValue[2]);// ��ծת�Ƽ۸�
				System.out.println("��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
				ylfxReport.setLxzc(qxppFzValue[3]);// ��Ϣ֧��
				System.out.println("��Ϣ֧��:"+ylfxReport.getLxzc());
				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
				System.out.println("��ծ������:" + ylfxReport.getFzjlc());
				ylfxReport.setFzzysr(qxppFzValue[4]);//��ծת��֧��
				System.out.println("��ծת������:" + ylfxReport.getZczyzc());
				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
				System.out.println("��ծ������:" + ylfxReport.getZcjsr());
				
				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// ������
			}
			ylfxReportList.add(ylfxReport);
		} else {//�ǻ�����ϸ
			String hsql = "from BrMst where superBrNo = '" + brNo + "' order by brNo";// ��ȡ�û����������¼�����
			List<BrMst> list = daoFactory.query(hsql, null);
			if(!prcMode.equals("4")) {//��������ƥ��
				for (int i = 0; i < list.size(); i++) {
					BrMst brMst = list.get(i);
					System.out.println("��ʼ�������:"+brMst.getBrNo()+"...");
					String brSql = LrmUtil.getBrSql(brMst.getBrNo());
					YlfxReport ylfxReport = new YlfxReport();
					ylfxReport.setBrName(brMst.getBrName());
					ylfxReport.setBrNo(brMst.getBrNo());
					ylfxReport.setManageLvl(brMst.getManageLvl());
					ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// �ʲ����
					System.out.println("�ʲ����:" + ylfxReport.getZcye());
					ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoZc, "3", assessScope, true));// ��Ϣ�ʣ���Ȩƽ������
					System.out.println("��Ϣ��:" + ylfxReport.getSxl());
					ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// ��ծ���
					System.out.println("��ծ���:" + ylfxReport.getFzye());
					ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoFz, "3", assessScope, true));// ��Ϣ�ʣ���Ȩƽ������
					System.out.println("��Ϣ��:" + ylfxReport.getFxl());
					ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//�ʲ�ƽ������
			        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
			        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//��ծƽ������
			        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
			        Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
					ylfxReport.setZczyjg(resultPrice[0]);// �ʲ�ת�Ƽ۸�
					System.out.println("�ʲ�ת�Ƽ۸�:" + ylfxReport.getZczyjg());
					ylfxReport.setFzzyjg(resultPrice[1]);// ��ծת�Ƽ۸�
					System.out.println("��ծת�Ƽ۸�:" + ylfxReport.getFzzyjg());
					ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);// ��Ϣ����
					System.out.println("��Ϣ����:" + ylfxReport.getLxsr());
					ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);// ��Ϣ֧��
					System.out.println("��Ϣ֧��:" + ylfxReport.getLxzc());
					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
					ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// �ʲ�ת��֧��
					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
					ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// ��ծת������
					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
					ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// ������
					ylfxReportList.add(ylfxReport);
				}
			}else{//������ƥ��
				List<String[]> ftped_data_successList=(List<String[]>)co.getCacheData(this, "get_ftped_data_successList",new Object[]{xlsBrNo, "2", date}, intervalTime, maxVisitCount);
				if(ftped_data_successList == null) return null;
				Map<String,Double[]> QxppValue_map=this.getQxppValueMap_jgzylfx(ftped_data_successList, date, daysSubtract, list);
				for (int i = 0; i < list.size(); i++) {
					BrMst brMst = list.get(i);
					System.out.println("��ʼ�������:"+brMst.getBrNo()+"...");
					YlfxReport ylfxReport = new YlfxReport();
					ylfxReport.setBrName(brMst.getBrName());
					ylfxReport.setBrNo(brMst.getBrNo());
					ylfxReport.setManageLvl(brMst.getManageLvl());
					
					Double[] values_zc=QxppValue_map.get(brMst.getBrNo()+"-1");
					Double[] values_fz=QxppValue_map.get(brMst.getBrNo()+"-2");
					ylfxReport.setZcye(values_zc[5]);// �ʲ����
					System.out.println("�ʲ����:"+ylfxReport.getZcye());
					ylfxReport.setSxl(values_zc[0]);// ��Ϣ��
					System.out.println("��Ϣ��:"+ylfxReport.getSxl());
			        ylfxReport.setZcpjqx(values_zc[1]);//�ʲ�ƽ������
			        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
			        ylfxReport.setZczyjg(values_zc[2]);// �ʲ�ת�Ƽ۸�
					System.out.println("�ʲ�ת�Ƽ۸�:"+ylfxReport.getZczyjg());
					ylfxReport.setLxsr(values_zc[3]);// ��Ϣ����
					System.out.println("��Ϣ����:"+ylfxReport.getLxsr());
					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
					System.out.println("�ʲ�������:" + ylfxReport.getZcjlc());
					ylfxReport.setZczyzc(values_zc[4]);//�ʲ�ת��֧��
					System.out.println("�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
					System.out.println("�ʲ�������:" + ylfxReport.getZcjsr());
					
					ylfxReport.setFzye(values_fz[5]);// ��ծ���
					System.out.println("��ծ���:"+ylfxReport.getFzye());
					ylfxReport.setFxl(values_fz[0]);// ��Ϣ��
					System.out.println("��Ϣ��:"+ylfxReport.getFxl());
			        ylfxReport.setFzpjqx(values_fz[1]);//��ծƽ������
			        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
			        ylfxReport.setFzzyjg(values_fz[2]);// ��ծת�Ƽ۸�
					System.out.println("��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
					ylfxReport.setLxzc(values_fz[3]);// ��Ϣ֧��
					System.out.println("��Ϣ֧��:"+ylfxReport.getLxzc());
					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
					System.out.println("��ծ������:" + ylfxReport.getFzjlc());
					ylfxReport.setFzzysr(values_fz[4]);//��ծת��֧��
					System.out.println("��ծת��֧��:" + ylfxReport.getZczyzc());
					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
					System.out.println("��ծ������:" + ylfxReport.getZcjsr());
					
					ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// ������
					ylfxReportList.add(ylfxReport);
				}
			}
		}
		return ylfxReportList;
	}

	/**
	 * ҵ������ӯ������--- ����ҵ�����͵����ݻ�ȡ
	 * @param request
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @return
	 */
	public List<YlfxReport> busPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope) {
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// ������
		System.out.println("�����磺" + xlsBrNo);
		String prcMode = FtpUtil.getPrcModeByBrNo(xlsBrNo);// �����õ��ʽ��
		if (prcMode == null) return null;
		System.out.println("�����õ��ʽ�أ�" + prcMode);
		request.getSession().setAttribute("prcMode", prcMode);
		List<FtpPoolInfo> ftpPoolInfoList = new ArrayList<FtpPoolInfo>();
		if(!prcMode.equals("4")) {//�����������ƥ�䷨�����ȡ��Ӧ�Ķ��۲��������õ��ʽ��
			ftpPoolInfoList = this.getFtpPoolInfoList(xlsBrNo, prcMode);// ��Ӧ�Ķ��۲����������ʽ��
			if (ftpPoolInfoList == null || ftpPoolInfoList.size() < 1) return null;
		}
		String[] businessNo = new String[14];//��Ʒ����Ӧ��ҵ����ftp_product_method_rel��
		// ����
		businessNo[0]  = "'YW101'";
		// ����������п���
		businessNo[1] = "'YW102'";
		// ���ͬҵ
		businessNo[2] = "'YW103'";
		// ���ͬҵ
		businessNo[3] = "'YW104'";
		// Ͷ��ҵ��
		businessNo[4] = "'YW105'";
		// ���뷷��
		businessNo[5] = "'YW106'";
		// �ֽ�
		businessNo[6] = "'YW107'";
		// �����ʲ�
		businessNo[7] = "'YW108'";
		// ���
		businessNo[8]  = "'YW201','YW202','YW203','YW204'";
		// ͬҵ����
		businessNo[9] = "'YW205'";
		// ���������н��
		businessNo[10] = "'YW206'";
		// ���ָ�ծ
		businessNo[11] = "'YW207'";
		// �����ع�
		businessNo[12] = "'YW208'";
		// ������ծ
		businessNo[13] = "'YW209'";
		
		String brSql = LrmUtil.getBrSql(brNo);
		
		ylfxReportList = this.getYlfxReportList(request, brSql, xlsBrNo, brNo, manageLvl, date, prcMode, assessScope, businessNo, ftpPoolInfoList);
		
		return ylfxReportList;
	}
	
	/**
	 * ҵ������ӯ������---ĳһ��ҵ��(Ŀǰֻ֧�֣������Ͷ��ҵ��)�����в�Ʒӯ������ ���ݻ�ȡ
	 * @param request
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @param prdtType
	 * @return
	 */
	public List<YlfxReport> prdtPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope, String prdtType) {
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// ������
		System.out.println("�����磺" + xlsBrNo);
		String prcMode = FtpUtil.getPrcModeByBrNo(xlsBrNo);// �����õ��ʽ��
		if (prcMode == null) return null;
		System.out.println("�����õ��ʽ�أ�" + prcMode);
		request.getSession().setAttribute("prcMode", prcMode);
		List<FtpPoolInfo> ftpPoolInfoList = new ArrayList<FtpPoolInfo>();
		if(!prcMode.equals("4")) {//�����������ƥ�䷨�����ȡ��Ӧ�Ķ��۲��������õ��ʽ��
			ftpPoolInfoList = this.getFtpPoolInfoList(xlsBrNo, prcMode);// ��Ӧ�Ķ��۲����������ʽ��
			if (ftpPoolInfoList == null || ftpPoolInfoList.size() < 1) return null;
		}
		String[][] prdtNo = null;//��Ʒ���+��Ʒ����
		if(prdtType.equals("ck")) {//���
			prdtNo = new String[19][2];
			prdtNo[0][0] = "'P2010','P2011,'P2012','P2027'";
			prdtNo[0][1] = "���˻��ڴ��";
			prdtNo[1][0] = "'P2011','P2012','P2039','P2040'";
			prdtNo[1][1] = "���˶��ڴ��1��������(����1��)";
			prdtNo[2][0] = "'P2013','P2014','P2017','P2020','P2023','P2028','P2041','P2043','P2046','P2048','P2051'";
			prdtNo[2][1] = "���˶��ڴ��1-2����";
			prdtNo[3][0] = "'P2015','P2016','P2018','P2019','P2021','P2022','P2024','P2025','P2029','P2030','P2042','P2044','P2045','P2047','P2049','P2050','P2052','P2053'";
			prdtNo[3][1] = "���˶��ڴ��3-5����";
			prdtNo[4][0] = "'P2028'";
			prdtNo[4][1] = "�������ÿ����";
			prdtNo[5][0] = "'P2026','P2054'";
			prdtNo[5][1] = "���˶���������";
			prdtNo[6][0] = "'P2027'";
			prdtNo[6][1] = "����֪ͨ���";
			prdtNo[7][0] = "'P2001','P2031'";
			prdtNo[7][1] = "��λ���ڴ��";
			prdtNo[8][0] = "'P2002','P2003','P2032','P2033'";
			prdtNo[8][1] = "��λ���ڴ��1������(����1��)";
			prdtNo[9][0] = "'P2004','P2005','P2034','P2035'";
			prdtNo[9][1] = "��λ���ڴ��1-2����";
			prdtNo[10][0] = "'P2006','P2007','P2036','P2037'";
			prdtNo[10][1] = "��λ���ڴ��3-5����";
			prdtNo[11][0] = "'P2057'";
			prdtNo[11][1] = "�����Դ��";
			prdtNo[12][0] = "'P2059','P2060','P2061'";
			prdtNo[12][1] = "Ӧ��������������Ʊ";
			prdtNo[13][0] = "'P2062'";
			prdtNo[13][1] = "��֤����";
			prdtNo[14][0] = "'P2055'";
			prdtNo[14][1] = "��λ���ÿ����";
			prdtNo[15][0] = "'P2008'";
			prdtNo[15][1] = "��λЭ����";
			prdtNo[16][0] = "'P2009'";
			prdtNo[16][1] = "��λ֪ͨ���";
			prdtNo[17][0] = "'P2065'";
			prdtNo[17][1] = "ͬҵ��ſ���";
			prdtNo[18][0] = "'P2066'";
			prdtNo[18][1] = "ϵͳ�ڴ�ſ���";
		}else if(prdtType.equals("dk")) {//���
			prdtNo = new String[23][2];
			prdtNo[0][0] = "'P1018','P1023','P1028','P1033','P1038','P1043'";
			prdtNo[0][1] = "ũ������6����(��)����";
			prdtNo[1][0] = "'P1019','P1024','P1029','P1034','P1039','P1044'";
			prdtNo[1][1] = "ũ������6����-1��(��1��)";
			prdtNo[2][0] = "'P1020','P1025','P1030','P1035','P1040','P1045'";
			prdtNo[2][1] = "ũ������1��-3��(��3��)";
			prdtNo[3][0] = "'P1021','P1026','P1031','P1036','P1041','P1046'";
			prdtNo[3][1] = "ũ������3��-5��(��5��)";
			prdtNo[4][0] = "'P1022','P1027','P1032','P1037','P1042','P1047'";
			prdtNo[4][1] = "ũ������5������(����5��)";
			prdtNo[5][0] = "'P1048','P1053'";
			prdtNo[5][1] = "ũ�徭����֯����6����(��)����";
			prdtNo[6][0] = "'P1049','P1054'";
			prdtNo[6][1] = "ũ�徭����֯����6����-1��(��1��)";
			prdtNo[7][0] = "'P1050','P1055'";
			prdtNo[7][1] = "ũ�徭����֯����1��-3��(��3��)";
			prdtNo[8][0] = "'P1051','P1056'";
			prdtNo[8][1] = "ũ�徭����֯����3��-5��(��5��)";
			prdtNo[9][0] = "'P1052','P1057'";
			prdtNo[9][1] = "ũ�徭����֯����5������(����5��)";
			prdtNo[10][0] = "'P1058','P1063','P1068'";
			prdtNo[10][1] = "ũ����ҵ����6����(��)����";
			prdtNo[11][0] = "'P1059','P1064','P1069'";
			prdtNo[11][1] = "ũ����ҵ����6����-1��(��1��)";
			prdtNo[12][0] = "'P1060','P1065','P1070'";
			prdtNo[12][1] = "ũ����ҵ����1��-3��(��3��)";
			prdtNo[13][0] = "'P1061','P1066','P1071'";
			prdtNo[13][1] = "ũ����ҵ����3��-5��(��5��)";
			prdtNo[14][0] = "'P1062','P1067','P1072'";
			prdtNo[14][1] = "ũ����ҵ����5������(����5��)";
			prdtNo[15][0] = "'P1073','P1078','P1083','P1088','P1093','P1098'";
			prdtNo[15][1] = "��ũ����6����(��)����";
			prdtNo[16][0] = "'P1074','P1079','P1084','P1089','P1094','P1099'";
			prdtNo[16][1] = "��ũ����6����-1��(��1��)";
			prdtNo[17][0] = "'P1075','P1080','P1085','P1090','P1095','P1100'";
			prdtNo[17][1] = "��ũ����1��-3��(��3��)";
			prdtNo[18][0] = "'P1076','P1081','P1086','P1091','P1096','P1101'";
			prdtNo[18][1] = "��ũ����3��-5��(��5��)";
			prdtNo[19][0] = "'P1077','P1082','P1087','P1092','P1097','P1102'";
			prdtNo[19][1] = "��ũ����5������(����5��)";
			prdtNo[20][0] = "'P1103'";
			prdtNo[20][1] = "���ÿ�͸֧";
			prdtNo[21][0] = "'P1104'";
			prdtNo[21][1] = "�����ʲ�";
			prdtNo[22][0] = "'P1106'";
			prdtNo[22][1] = "���";
		}else if(prdtType.equals("tzyw")) {//Ͷ��ҵ��
			prdtNo = new String[4][2]; 
			prdtNo[0][0] = "'P1011'";
			prdtNo[0][1] = "�����Խ����ʲ�";
			prdtNo[1][0] = "'P1114'";
			prdtNo[1][1] = "����������Ͷ��";
			prdtNo[2][0] = "'P1116'";
			prdtNo[2][1] = "�ɹ����۽����ʲ�";
			prdtNo[3][0] = "'P1123'";
			prdtNo[3][1] = "Ӧ�տ�����Ͷ��";
		}
		String brSql = LrmUtil.getBrSql(brNo);
		ylfxReportList = this.getYlfxReportListByPrdtNo(request, brSql, xlsBrNo, date, prcMode, assessScope, prdtNo, ftpPoolInfoList);
		return ylfxReportList;
	}
	
	/**
	 * ����ӯ������
	 * @param request
	 * @param date
	 * @param brNo ����Ա��������
	 * @param manageLvl �����ļ���
	 * @param brCountLvl ����ͳ�Ƽ���
	 * @param assessScope ͳ��ά��
	 * @return
	 */
	public List<YlfxReport> brPayOffRanking(HttpServletRequest request, String date, String brNo, String manageLvl, String brCountLvl, Integer assessScope) {
		CacheOperation co = CacheOperation.getInstance();//����
	    long intervalTime = Long.valueOf("2592000000");//������һ����
	    int maxVisitCount = 0;//�����Ʒ��ʴ���
	    
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String xlsBrNo = brNo;// ������
		System.out.println("�����磺" + xlsBrNo);
		String prcMode = FtpUtil.getPrcModeByBrNo(xlsBrNo);// �����õ��ʽ��
		if (prcMode == null)
			return null;
		System.out.println("�����õ��ʽ�أ�" + prcMode);
		request.getSession().setAttribute("prcMode", prcMode);
		String prdtNoZc = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "1"); // ��Ӧ�ʽ�������õ��ʲ����Ʒ
		String prdtNoFz = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "2"); // ��Ӧ�ʽ�������õĸ�ծ���Ʒ
		System.out.println("�ʲ��ʽ�ز�Ʒ��" + prdtNoZc);
		System.out.println("��ծ�ʽ�ز�Ʒ��" + prdtNoFz);
		if (prdtNoZc == null || prdtNoFz == null)
			return null;
		List ftpResultList = null;
		if(!prcMode.equals("4")) {//�����������ƥ�䷨
			ftpResultList = this.getFtpResult(date, xlsBrNo, prcMode);//��ȡ��Ӧ������Ķ��۽��list
		}
		//���ݻ��������ȡ���������¸ü����µ����л���
		List<BrMst> brMstList = getBrMstList(brNo, brCountLvl);
		String date2 = CommonFunctions.dateModifyM(date, assessScope);
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//���������꣬��ֻȡ��������ݣ���1�·ݿ�ʼ
	    	date2 = date2.substring(0,4)+"1231";
		
		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//��ȡ��ͳ�Ƶ�����
		if(!prcMode.equals("4")) {
			for(BrMst brMst : brMstList) {
				String brSql = LrmUtil.getBrSql(brMst.getBrNo());
				YlfxReport ylfxReport = new YlfxReport();
				ylfxReport.setBrName(brMst.getBrName());
				ylfxReport.setBrNo(brMst.getBrNo());
				ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// �ʲ����
				System.out.println("�ʲ����:" + ylfxReport.getZcye());
				ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoZc, "3", assessScope, true));// ��Ϣ�ʣ���Ȩƽ������
				System.out.println("��Ϣ��:" + ylfxReport.getSxl());
				ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// ��ծ���
				System.out.println("��ծ���:" + ylfxReport.getFzye());
				ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoFz, "3", assessScope, true));// ��Ϣ�ʣ���Ȩƽ������
				System.out.println("��Ϣ��:" + ylfxReport.getFxl());
				ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//�ʲ�ƽ������
		        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
		        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//��ծƽ������
		        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
		        Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
				ylfxReport.setZczyjg(resultPrice[0]);// �ʲ�ת�Ƽ۸�
				System.out.println("�ʲ�ת�Ƽ۸�:" + ylfxReport.getZczyjg());
				ylfxReport.setFzzyjg(resultPrice[1]);// ��ծת�Ƽ۸�
				System.out.println("��ծת�Ƽ۸�:" + ylfxReport.getFzzyjg());
				ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);// ��Ϣ����
				System.out.println("��Ϣ����:" + ylfxReport.getLxsr());
				ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);// ��Ϣ֧��
				System.out.println("��Ϣ֧��:" + ylfxReport.getLxzc());
				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
				ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// �ʲ�ת��֧��
				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
				ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// ��ծת������
				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// ������
				ylfxReportList.add(ylfxReport);
			}
		}else{
			List<String[]> ftped_data_successList=(List<String[]>)co.getCacheData(this, "get_ftped_data_successList",new Object[]{xlsBrNo, "2", date}, intervalTime, maxVisitCount);
			if(ftped_data_successList == null) return null;
			Map<String,Double[]> QxppValue_map=this.getQxppValueMap_jgzylfx(ftped_data_successList, date, daysSubtract, brMstList);
			for(BrMst brMst : brMstList) {
				YlfxReport ylfxReport = new YlfxReport();
				ylfxReport.setBrName(brMst.getBrName());
				ylfxReport.setBrNo(brMst.getBrNo());
				Double[] values_zc=QxppValue_map.get(brMst.getBrNo()+"-1");
				Double[] values_fz=QxppValue_map.get(brMst.getBrNo()+"-2");
			    
				ylfxReport.setZcye(values_zc[5]);// �ʲ����
				System.out.println("�ʲ����:"+ylfxReport.getZcye());
				ylfxReport.setSxl(values_zc[0]);// ��Ϣ��
				System.out.println("��Ϣ��:"+ylfxReport.getSxl());
		        ylfxReport.setZcpjqx(values_zc[1]);//�ʲ�ƽ������
		        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
		        ylfxReport.setZczyjg(values_zc[2]);// �ʲ�ת�Ƽ۸�
				System.out.println("�ʲ�ת�Ƽ۸�:"+ylfxReport.getZczyjg());
				ylfxReport.setLxsr(values_zc[3]);// ��Ϣ����
				System.out.println("��Ϣ����:"+ylfxReport.getLxsr());
				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
				System.out.println("�ʲ�������:" + ylfxReport.getZcjlc());
				ylfxReport.setZczyzc(values_zc[4]);//�ʲ�ת��֧��
				System.out.println("�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
				System.out.println("�ʲ�������:" + ylfxReport.getZcjsr());
				
				ylfxReport.setFzye(values_fz[5]);// ��ծ���
				System.out.println("��ծ���:"+ylfxReport.getFzye());
				ylfxReport.setFxl(values_fz[0]);// ��Ϣ��
				System.out.println("��Ϣ��:"+ylfxReport.getFxl());
		        ylfxReport.setFzpjqx(values_fz[1]);//��ծƽ������
		        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
		        ylfxReport.setFzzyjg(values_fz[2]);// ��ծת�Ƽ۸�
				System.out.println("��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
				ylfxReport.setLxzc(values_fz[3]);// ��Ϣ֧��
				System.out.println("��Ϣ֧��:"+ylfxReport.getLxzc());
				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
				System.out.println("��ծ������:" + ylfxReport.getFzjlc());
				ylfxReport.setFzzysr(values_fz[4]);//��ծת��֧��
				System.out.println("��ծת��֧��:" + ylfxReport.getZczyzc());
				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
				System.out.println("��ծ������:" + ylfxReport.getZcjsr());
				
				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// ������
				ylfxReportList.add(ylfxReport);
		}
		
		
		}
		//��ylfxReportList���������С���н�������
		Collections.sort(ylfxReportList, new Comparator<YlfxReport>() {
			 public int compare(YlfxReport arg0, YlfxReport arg1) {
				 return arg1.getJsr().compareTo(arg0.getJsr());
				 }
			 }
		);
		return ylfxReportList;
	}
	/**
	 * �ʽ�����ӯ������
	 * @param request
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @return
	 */
	public YlfxReport financialCenterLCProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope) {

		CacheOperation co = CacheOperation.getInstance();//����
	    long intervalTime = Long.valueOf("2592000000");//������һ����
	    int maxVisitCount = 0;//�����Ʒ��ʴ���
		
		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// ������
		System.out.println("�����磺" + xlsBrNo);
		String prcMode = FtpUtil.getPrcModeByBrNo(xlsBrNo);// �����õ��ʽ��
		if (prcMode == null)
			return null;
		System.out.println("�����õ��ʽ�أ�" + prcMode);
		request.getSession().setAttribute("prcMode", prcMode);
		String prdtNoZc = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "1"); // ��Ӧ�ʽ�������õ��ʲ����Ʒ
		String prdtNoFz = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "2"); // ��Ӧ�ʽ�������õĸ�ծ���Ʒ
		System.out.println("�ʲ��ʽ�ز�Ʒ��" + prdtNoZc);
		System.out.println("��ծ�ʽ�ز�Ʒ��" + prdtNoFz);
		if (prdtNoZc == null || prdtNoFz == null)
			return null;
		List ftpResultList = null;
		if(!prcMode.equals("4")) {//�����������ƥ�䷨
			ftpResultList = this.getFtpResult(date, xlsBrNo, prcMode);//��ȡ��Ӧ������Ķ��۽��list
		}
		String date2 = CommonFunctions.dateModifyM(date, assessScope);
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//���������꣬��ֻȡ��������ݣ���1�·ݿ�ʼ
	    	date2 = date2.substring(0,4)+"1231";
		
		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//��ȡ��ͳ�Ƶ�����
	    YlfxReport ylfxReport = new YlfxReport();
		String brSql = LrmUtil.getBrSql(brNo);
		ylfxReport.setBrName(brInfoDAO.getInfo(brNo).getBrName());
		ylfxReport.setBrNo(brNo);
		ylfxReport.setManageLvl(manageLvl);
		if(!prcMode.equals("4")) {
			ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// �ʲ����
			System.out.println("�ʲ����:" + ylfxReport.getZcye());
			ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// ��ծ���
			System.out.println("��ծ���:" + ylfxReport.getFzye());
			Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
			ylfxReport.setZczyjg(resultPrice[0]);// �ʲ�ת�Ƽ۸�
			System.out.println("�ʲ�ת�Ƽ۸�:" + ylfxReport.getZczyjg());
			ylfxReport.setFzzyjg(resultPrice[1]);// ��ծת�Ƽ۸�
			System.out.println("��ծת�Ƽ۸�:" + ylfxReport.getFzzyjg());
			ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//�ʲ�ƽ������
	        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
	        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//��ծƽ������
	        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
	        ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// �ʽ����ĵ��ʲ�ת������=�������ʲ�ת��֧��
			System.out.println("�ʲ�ת������:" + ylfxReport.getZczyzc());
			ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// �ʽ����ĵĸ�ծת��֧��=�����ĸ�ծת������
			System.out.println("��ծת��֧��:" + ylfxReport.getFzzysr());
		}else {//���������ƥ��
			List<String[]> ftped_data_successList=(List<String[]>)co.getCacheData(this, "get_ftped_data_successList",new Object[]{xlsBrNo, "2", date}, intervalTime, maxVisitCount);
			if(ftped_data_successList == null) return null;
			double[] qxppZcValue = this.getQxppValue(ftped_data_successList, date, daysSubtract, prdtNoZc);
			double[] qxppFzValue = this.getQxppValue(ftped_data_successList, date, daysSubtract, prdtNoFz);
			ylfxReport.setZcye(qxppZcValue[5]);// �ʲ����
			System.out.println("�ʲ����:"+ylfxReport.getZcye());
			ylfxReport.setZczyjg(qxppZcValue[2]);// �ʲ�ת�Ƽ۸�
			System.out.println("�ʲ�ת�Ƽ۸�:"+ylfxReport.getZczyjg());
			ylfxReport.setFzye(qxppFzValue[5]);// ��ծ���
			System.out.println("��ծ���:"+ylfxReport.getFzye());
			ylfxReport.setFzzyjg(qxppFzValue[2]);// ��ծת�Ƽ۸�
			System.out.println("��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
			ylfxReport.setZcpjqx(qxppZcValue[1]);//�ʲ�ƽ������
	        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
	        ylfxReport.setFzpjqx(qxppFzValue[1]);//��ծƽ������
	        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
	        ylfxReport.setZczyzc(qxppZcValue[4]);// �ʽ����ĵ��ʲ�ת������=�������ʲ�ת��֧��
			System.out.println("�ʲ�ת������:" + ylfxReport.getZczyzc());
			ylfxReport.setFzzysr(qxppFzValue[4]);// �ʽ����ĵĸ�ծת��֧��=�����ĸ�ծת������
			System.out.println("��ծת��֧��:" + ylfxReport.getFzzysr());
		}
		
		return ylfxReport;
	}
	/**
	 * ���ݻ�����ȡ����Ӧ�ʽ�������õĲ�Ʒ
	 * 
	 * @param brNo
	 *            �����������
	 * @param prcMode
	 *            �ʽ��
	 * @param poolType
	 *            �ʽ���ʲ�����:�ʲ�/��ծ
	 * @return
	 */
	public String getPrdtNoByPrcMode(String brNo, String prcMode, String poolType) {
		StringBuffer prdtNo = new StringBuffer();
		if(!prcMode.equals("4")) {//��˫���ʽ�أ�����ƥ��͵�˫�ඨ�۲�ѯ������ͬ���鲻ͬ�����ݱ�
			String hsql = "from FtpPoolInfo where prcMode = '" + prcMode + "' and brNo = '" + brNo + "'";
			if (!prcMode.equals("1")) {// ����ǵ��ʽ�أ��޷�����poolType�������ʲ����Ǹ�ծ
				hsql += " and poolType = '" + poolType + "'";
			}
			List<FtpPoolInfo> poolList = daoFactory.query(hsql, null);
			if (poolList == null || poolList.size() < 1) {
				return null;// ��Ӧ�Ķ��۲���δ�����ʽ��
			} else {
				if (prcMode.equals("1")) {// ����ǵ��ʽ�أ�����ContentObject��prdtNo���������ʲ����Ǹ�ծ��P1��ͷΪ�ʲ���P2��ͷΪ��ծ
					FtpPoolInfo ftpPoolInfo = poolList.get(0);
					String contentObject = ftpPoolInfo.getContentObject();
					String[] contentObjects = contentObject.split("\\+");
					for (int i = 0; i < contentObjects.length; i++) {
						if (contentObjects[i].indexOf("P" + poolType) != -1)
							prdtNo.append(contentObjects[i] + ",");
					}
				} else {
					for (FtpPoolInfo ftpPoolInfo : poolList) {
						prdtNo.append(ftpPoolInfo.getContentObject() + ",");
					}
				}
			}
		}else {//����ƥ�䷨
			String hsql = "from FtpProductMethodRel t where t.productNo like 'P"+poolType+"%' " +
					"and t.brNo = '"+brNo+"'";
			List<FtpProductMethodRel> ftpProductMethodRelList = daoFactory.query(hsql, null);
			for(FtpProductMethodRel ftpProductMethodRel : ftpProductMethodRelList) {
				prdtNo.append("'" + ftpProductMethodRel.getProductNo() + "',");
			}
		}
		if (prdtNo.lastIndexOf(",") == prdtNo.length() - 1)
			prdtNo = prdtNo.deleteCharAt(prdtNo.length() - 1);
		return prdtNo.toString().replace("+", ",");
	}
	
	/**
	 * ��ȡ������Ӧ���ʽ�ת�Ƽ۸��ʲ�+��ծ
	 * 
	 * @param list ���۽��list
	 * @param date
	 * @param brSql
	 * @param prcMode
	 *            �ʽ��
	 * @param assessScope
	 *            ͳ��ά��
	 * @return
	 */
	public Double[] getFtpResultPrice(List list, String date, String brSql, String prcMode, Integer assessScope) {
		Double[] resultPrice = {0.0, 0.0};// �ʲ�+��ծ
		if(list == null || list.size() < 1) {
			return resultPrice;
		}
		if (prcMode.equals("1")) {// ����ǵ��ʽ�أ���ֱ�ӻ�ȡ�ʽ�ת�Ƽ۸�,�ʲ�ת�Ƽ۸�͸�ծת�Ƽ۸�Ϊͬһ��
			Object obj = list.get(0);
			Object[] o = (Object[]) obj;
			resultPrice[0] = Double.valueOf(o[0].toString());
			resultPrice[1] = Double.valueOf(o[0].toString());
		} else if (prcMode.equals("2")) {// �����˫�ʽ�أ�����pool_no��ȡת�Ƽ۸�pool_no=201Ϊ�ʲ���pool_noΪ202Ϊ��ծ
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				Object[] o = (Object[]) obj;
				if (o[1].equals("201")) {// pool_no=201Ϊ�ʲ�
					resultPrice[0] = Double.valueOf(o[0].toString());
				} else if (o[1].equals("202")) {// pool_noΪ202Ϊ��ծ
					resultPrice[1] = Double.valueOf(o[0].toString());
				}
			}
		} else if (prcMode.equals("3")) {// ����Ƕ��ʽ�أ���Զ�����Ӽ�Ȩƽ����ȡת�Ƽ۸�
			double zc = 0.0, zcye = 0.0, fz = 0.0, fzye = 0.0;
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				Object[] o = (Object[]) obj;
				String prdtNo = o[3].toString().replace("+", ",");
				System.out.println("prdtNo"+prdtNo);
				double ye = FtpUtil.getAverageAmount(brSql, date, prdtNo, "3", assessScope, true);
				if (o[2].equals("1")) {// �ʲ�
					zc += ye * Double.valueOf(o[0].toString());
					zcye += ye;
				} else if (o[2].equals("2")) {// ��ծ
					fz += ye * Double.valueOf(o[0].toString());
					fzye += ye;
				}
			}
			resultPrice[0] = zc / zcye;
			resultPrice[1] = fz / fzye;
		}
		resultPrice[0] = (resultPrice[0].isInfinite() || resultPrice[0].isNaN()) ? 0.0 : resultPrice[0];
		resultPrice[1] = (resultPrice[1].isInfinite() || resultPrice[1].isNaN()) ? 0.0 : resultPrice[1];

		return resultPrice;

	}
	
	/**
	 * ��ȡ�����綨�۽���������
	 * @param date
	 * @param xlsBrNo
	 * @param prcMode
	 * @return
	 */
	public List getFtpResult(String date, String xlsBrNo, String prcMode) {
		String sql = "select * from (select t.Result_price, t.pool_no, f.pool_type, f.Content_Object, f.prc_mode, "
			+ "row_number() over(partition by t.pool_no order by t.RES_DATE desc,T.RESULT_ID desc ) rn from ftp.Ftp_Result_sfb t "
			+ "left join ftp.ftp_pool_info f on t.prc_mode = f.prc_mode and t.br_no = f.br_no and t.pool_no = f.pool_no "
			+ "where t.prc_Mode ='" + prcMode
			+ "' and t.cur_no = '01' and t.br_no = '" + xlsBrNo + "' and to_date(t.res_date,'yyyyMMdd') <= to_date('"+date+"','yyyyMMdd') ";
	    if (prcMode.equals("3")) {
		     sql += " and t.pool_no in (select pool_no from ftp.ftp_pool_info where prc_mode='3' and br_no=t.br_no)";
	    }
	    sql += ") where rn=1 order by pool_type, pool_no";
	    List list = daoFactory.query1(sql, null);
	    return list;
	}

	/**
	 * ��ȡ������Ӧĳ��ҵ����ʽ�ת�Ƽ۸�
	 * 
	 * @return
	 */
	public Double getFtpResultPrice(String date, String brSql, String xlsBrNo,
			String prcMode, String prdtNo, Integer assessScope) {
		Double resultPrice = null;
		String sql = "select * from (select t.Result_price, t.pool_no, f.pool_type, f.Content_Object, "
				+ "row_number() over(partition by t.pool_no order by t.RES_DATE desc,T.RESULT_ID desc ) rn from ftp.Ftp_Result_sfb t "
				+ "left join ftp.ftp_pool_info f on t.prc_mode = f.prc_mode and t.br_no = f.br_no and t.pool_no = f.pool_no "
				+ "where t.prc_Mode ='" + prcMode + "' and t.cur_no = '01' and t.br_no = '" + xlsBrNo + "' and to_date(t.res_date,'yyyyMMdd') <= to_date('"+date+"','yyyyMMdd') ";
		if (prcMode.equals("3")) {
			sql += " and t.pool_no in (select pool_no from ftp.ftp_pool_info where prc_mode='3' and br_no=t.br_no)";
		}
		sql += ") where rn=1 order by pool_type, pool_no";
		List list = daoFactory.query1(sql, null);
		if (prcMode.equals("1")) {// ����ǵ��ʽ�أ���ֱ�ӻ�ȡ�ʽ�ת�Ƽ۸�
			Object obj = list.get(0);
			Object[] o = (Object[]) obj;
			resultPrice = Double.valueOf(o[0].toString());
		} else if (prcMode.equals("2") || prcMode.equals("3")) {// �����˫/���ʽ�أ���ÿ����Ʒͨ�������ʽ�ת�Ƽ۸���м�Ȩƽ������ȡĳ��ҵ����ʽ�ת�Ƽ۸�
			double fz = 0.0;// ��Ȩƽ����ʽ�ķ���
			double fm = 0.0;// ��Ȩƽ����ʽ�ķ�ĸ
			System.out.println("prdtNo:"+prdtNo);
			for (int i = 0; i < list.size(); i++) {// ѭ��ÿ������
				Object obj = list.get(i);
				Object[] o = (Object[]) obj;
				String[] contentObjects = o[3].toString().split("\\+");
				
				for (int j = 0; j < contentObjects.length; j++) {// ѭ��ÿ�����Ӷ�Ӧ�Ĳ�Ʒ���жϸò�Ʒ�Ƿ���prdtNo�д��ڣ�������ڣ����м�Ȩ
					if (prdtNo.indexOf(contentObjects[j]) != -1) {
						// ��ȡ�ò�Ʒ���¾����
						double ye = FtpUtil.getAverageAmount(brSql, date, contentObjects[j], "3", assessScope, true);
						System.out.println("ye"+ye);
						fz += ye * Double.valueOf(o[0].toString());
						fm += ye;
					}
				}
			}
			resultPrice = fz / fm;
		}

		return (resultPrice.isNaN() || resultPrice.isInfinite()) ? 0.0 : resultPrice;

	}
	
	/**
	 * ����ҵ�����ͱ������ ��ȡ �����ڸ���ҵ�����͵�ӯ��������������
	 * @param brSql ������ѯsql
	 * @param xlsBrNo ������
	 * @param brNo
	 * @param brNo
	 * @param date
	 * @param prcMode �ʽ������
	 * @param assessScope ͳ��ά��
	 * @param businessNo ҵ����[]
	 * @param ftpPoolInfoList �ʽ������list
	 * @return
	 */
	public List<YlfxReport> getYlfxReportList(HttpServletRequest request,String brSql,String xlsBrNo, String brNo, String manageLvl, String date,String prcMode, Integer assessScope, String[] businessNo, List<FtpPoolInfo> ftpPoolInfoList) {

		CacheOperation co = CacheOperation.getInstance();//����
	    long intervalTime = Long.valueOf("2592000000");//������һ����
	    int maxVisitCount = 0;//�����Ʒ��ʴ���
		
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String date2 = CommonFunctions.dateModifyM(date, assessScope);
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//���������꣬��ֻȡ��������ݣ���1�·ݿ�ʼ
	    	date2 = date2.substring(0,4)+"1231";
		
		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//��ȡ��ͳ�Ƶ�����
		List<String[]> ftped_data_successList = new ArrayList<String[]>();
		Map<String,Double[]> QxppValue_map = new HashMap<String, Double[]>();
		if(prcMode.equals("4")) {//���������ƥ��
			ftped_data_successList=(List<String[]>)co.getCacheData(this, "get_ftped_data_successList",new Object[]{xlsBrNo, "2", date}, intervalTime, maxVisitCount);
			if(ftped_data_successList == null) return null;
			//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
			QxppValue_map = this.getQxppValueMap_ywtxylfx(ftped_data_successList, date, daysSubtract, xlsBrNo, brSql);
		}
		for(int i = 0; i < businessNo.length; i++) {
			YlfxReport ylfxReport = new YlfxReport();
			ylfxReport.setBusinessNo(businessNo[i]);// ҵ����
			//����ҵ���Ż�ȡ��Ӧ�Ĳ�Ʒ���
			String hsql = "from FtpProductMethodRel where businessNo in ("+businessNo[i]+") and brNo = '"+xlsBrNo+"'";
			List<FtpProductMethodRel> ftpProductMethodRelList = daoFactory.query(hsql, null);
			if(ftpProductMethodRelList == null || ftpProductMethodRelList.size() ==0) {
				ylfxReport.setBusinessName("-");// ҵ������
				ylfxReportList.add(ylfxReport);
				continue;
			}
			StringBuilder prdtNo = new StringBuilder();
			for (FtpProductMethodRel ftpProductMethodRel : ftpProductMethodRelList) {
				prdtNo.append("'" + ftpProductMethodRel.getProductNo() + "',");
			}
			if(prdtNo.length() > 0)prdtNo = prdtNo.deleteCharAt(prdtNo.length() - 1);
			// ��ȡ��Ӧ�ʽ�������õĲ�Ʒ
			String settedPrdtNo = "";
			//�����˫���ʽ�أ�Ҫ���ݵ�˫���ʽ�ص����ý��л�ȡ
			if(!prcMode.equals("4"))settedPrdtNo = this.getPrdtNoByPrcModeAndPrdtNo(ftpPoolInfoList, prdtNo.toString());
			else settedPrdtNo = prdtNo.toString();
			String businessName = ftpProductMethodRelList.get(0).getBusinessName();//�ǡ�����ҵ���ҵ��ֻ��һ������ֱ��ȡ���list�ĵ�һ����ҵ������
			if(businessNo[i].indexOf("YW201") != -1) {//������ҵ�����ڰ���4��ҵ������ֱ���ж��������"���ڴ��"����ҵ����Ϊ�����Ӳ����ݿ����ñ��ȡ
				businessName = "���";
			}
			System.out.println(businessName+"["+businessNo[i]+"]"+"����Ӧ�Ĳ�Ʒ"+settedPrdtNo);
			ylfxReport.setBusinessName(businessName);// ҵ������
			if(settedPrdtNo.equals("")) {
				ylfxReportList.add(ylfxReport);
				continue;
			}
			if(!prcMode.equals("4")) {
				if(businessNo[i].indexOf("YW1") != -1) {//�ʲ���
					ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//�ʲ����
					System.out.println(businessName+"-�ʲ����:" + ylfxReport.getZcye());
					ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//��Ϣ�ʣ���Ȩƽ������
					System.out.println(businessName+"-��Ϣ��:" + ylfxReport.getSxl());
					ylfxReport.setZczyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//�ʲ�ת�Ƽ۸�
					ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//�ʲ�ƽ������
			        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
			        System.out.println(businessName+"-�ʲ�ת�Ƽ۸�:" + ylfxReport.getZczyjg());
					ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);//��Ϣ����
					System.out.println(businessName+"-��Ϣ����:" + ylfxReport.getLxsr());
					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());//�ʲ�������
					System.out.println(businessName+"-�ʲ�������:" + ylfxReport.getZcjlc());
					ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);//�ʲ�ת��֧��
					System.out.println(businessName+"-�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());//�ʲ�������
					System.out.println(businessName+"-�ʲ�������:" + ylfxReport.getZcjsr());
				}else if(businessNo[i].indexOf("YW2") != -1) {//��ծ��
					ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//��ծ���
					System.out.println(businessName+"-��ծ���:"+ylfxReport.getFzye());
					ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//��Ϣ�ʣ���Ȩƽ������
					System.out.println(businessName+"-��Ϣ��:"+ylfxReport.getFxl());
					ylfxReport.setFzzyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//�ʲ�ת�Ƽ۸�
					ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//��ծƽ������
			        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
			        System.out.println(businessName+"-��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
					ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);//��Ϣ֧��
					System.out.println(businessName+"-��Ϣ֧��:"+ylfxReport.getLxzc());
					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());//��ծ������
					System.out.println(businessName+"-��ծ������:"+ylfxReport.getFzjlc());
					ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);//��ծת������
					System.out.println(businessName+"-��ծת������:"+ylfxReport.getFzzysr());
					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());//��ծ������
					System.out.println(businessName+"-��ծ������:"+ylfxReport.getFzjsr());
				}
			}else{//����ƥ��
				double rate = 0.0,term = 0.0,ftpPrice = 0.0,lx = 0.0,zyjg = 0.0,amt = 0.0;
				String[] prdtNos = settedPrdtNo.replace("'", "").split(",");
				for(String prdt_no : prdtNos) {//��QxppValue_map��ȡ��Ҫ��ֵ
					Double[] values=QxppValue_map.get(prdt_no);
					rate += values[0];
					term += values[1];
					ftpPrice += values[2];
					lx += values[3];
					zyjg += values[4];
					amt += values[5];
				}
				if(businessNo[i].indexOf("YW1") != -1) {//�ʲ���
					ylfxReport.setZcye(amt);// �ʲ����
					System.out.println(businessName+"-�ʲ����:"+ylfxReport.getZcye());
					ylfxReport.setSxl(rate/amt);// ��Ϣ��
					System.out.println(businessName+"-��Ϣ��:"+ylfxReport.getSxl());
			        ylfxReport.setZcpjqx(CommonFunctions.doublecut(term/amt, 1));//�ʲ�ƽ������
			        System.out.println(businessName+"-�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
					ylfxReport.setZczyjg(ftpPrice/amt);// �ʲ�ת�Ƽ۸�
					System.out.println(businessName+"-�ʲ�ת�Ƽ۸�:"+ylfxReport.getZczyjg());
					ylfxReport.setLxsr(lx);// ��Ϣ����
					System.out.println(businessName+"-��Ϣ����:"+ylfxReport.getLxsr());
					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
					System.out.println(businessName+"-�ʲ�������:" + ylfxReport.getZcjlc());
					ylfxReport.setZczyzc(zyjg);//�ʲ�ת��֧��
					System.out.println(businessName+"-�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
					System.out.println(businessName+"-�ʲ�������:" + ylfxReport.getZcjsr());
				}else if(businessNo[i].indexOf("YW2") != -1) {//��ծ��
					ylfxReport.setFzye(amt);// ��ծ���
					System.out.println(businessName+"-��ծ���:"+ylfxReport.getFzye());
					ylfxReport.setFxl(rate/amt);// ��Ϣ��
					System.out.println(businessName+"-��Ϣ��:"+ylfxReport.getFxl());
			        ylfxReport.setFzpjqx(CommonFunctions.doublecut(term/amt, 1));//��ծƽ������
			        System.out.println(businessName+"-��ծƽ������:"+ylfxReport.getFzpjqx());
			        ylfxReport.setFzzyjg(ftpPrice/amt);// ��ծת�Ƽ۸�
					System.out.println(businessName+"-��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
					ylfxReport.setLxzc(lx);// ��Ϣ֧��
					System.out.println(businessName+"-��Ϣ֧��:"+ylfxReport.getLxzc());
					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
					System.out.println(businessName+"-��ծ������:" + ylfxReport.getFzjlc());
					ylfxReport.setFzzysr(zyjg);//��ծת��֧��
					System.out.println(businessName+"-��ծת��֧��:" + ylfxReport.getZczyzc());
					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
					System.out.println(businessName+"-��ծ������:" + ylfxReport.getZcjsr());
				}
			}
			ylfxReportList.add(ylfxReport);
		}
			
		
	 return ylfxReportList;
	}
	
	/**
	 * ��ȡ��ͬ���Ʒ��ӯ����������
	 * @param brSql ������ѯsql
	 * @param xlsBrNo ������
	 * @param date
	 * @param prcMode �ʽ������
	 * @param assessScope ͳ��ά��
	 * @param prdtNo ��Ʒ���+��Ʒ����[][]
	 * @param ftpPoolInfoList �ʽ������list
	 * @return
	 */
	public List<YlfxReport> getYlfxReportListByPrdtNo(HttpServletRequest request, String brSql, String xlsBrNo, String date,String prcMode, Integer assessScope, String[][] prdtNo, List<FtpPoolInfo> ftpPoolInfoList) {

		CacheOperation co = CacheOperation.getInstance();//����
	    long intervalTime = Long.valueOf("2592000000");//������һ����
	    int maxVisitCount = 0;//�����Ʒ��ʴ���
	    
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String date2 = CommonFunctions.dateModifyM(date, assessScope);
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//���������꣬��ֻȡ��������ݣ���1�·ݿ�ʼ
	    	date2 = date2.substring(0,4)+"1231";
		
		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//��ȡ��ͳ�Ƶ�����
		List<String[]> ftped_data_successList = new ArrayList<String[]>();
		Map<String,Double[]> QxppValue_map = new HashMap<String, Double[]>();
		if(prcMode.equals("4")) {//���������ƥ��
			ftped_data_successList=(List<String[]>)co.getCacheData(this, "get_ftped_data_successList",new Object[]{xlsBrNo, "2", date}, intervalTime, maxVisitCount);
			if(ftped_data_successList == null) return null;
			//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
			QxppValue_map = this.getQxppValueMap_ywtxylfx(ftped_data_successList, date, daysSubtract, xlsBrNo, brSql);
		}
		for(int i = 0; i < prdtNo.length; i++) {
			YlfxReport ylfxReport = new YlfxReport();
			// ��ȡ��Ӧ�ʽ�������õĲ�Ʒ
			String settedPrdtNo = "";
			//�����˫���ʽ�أ�Ҫ���ݵ�˫���ʽ�ص����ý��л�ȡ
			if(!prcMode.equals("4"))settedPrdtNo = this.getPrdtNoByPrcModeAndPrdtNo(ftpPoolInfoList, prdtNo[i][0]);
			else settedPrdtNo = prdtNo[i][0];
			ylfxReport.setPrdtName(prdtNo[i][1]);
			if(settedPrdtNo.equals("")) {
				ylfxReportList.add(ylfxReport);
				continue;
			}
			if(!prcMode.equals("4")) {
				if(prdtNo[i][0].indexOf("P1") != -1) {//�ʲ���
					ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//�ʲ����
					System.out.println(prdtNo[i][1]+"-�ʲ����:" + ylfxReport.getZcye());
					ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//��Ϣ�ʣ���Ȩƽ������
					System.out.println(prdtNo[i][1]+"-��Ϣ��:" + ylfxReport.getSxl());
					ylfxReport.setZczyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//�ʲ�ת�Ƽ۸�
					ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//�ʲ�ƽ������
			        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
			        System.out.println(prdtNo[i][1]+"-�ʲ�ת�Ƽ۸�:" + ylfxReport.getZczyjg());
					ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);//��Ϣ����
					System.out.println(prdtNo[i][1]+"-��Ϣ����:" + ylfxReport.getLxsr());
					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());//�ʲ�������
					System.out.println(prdtNo[i][1]+"-�ʲ�������:" + ylfxReport.getZcjlc());
					ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);//�ʲ�ת��֧��
					System.out.println(prdtNo[i][1]+"-�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());//�ʲ�������
					System.out.println(prdtNo[i][1]+"-�ʲ�������:" + ylfxReport.getZcjsr());
				}else if(settedPrdtNo.indexOf("P2") != -1) {//��ծ��
					ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//��ծ���
					System.out.println(prdtNo[i][1]+"-��ծ���:"+ylfxReport.getFzye());
					ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//��Ϣ�ʣ���Ȩƽ������
					System.out.println(prdtNo[i][1]+"-��Ϣ��:"+ylfxReport.getFxl());
					ylfxReport.setFzzyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//�ʲ�ת�Ƽ۸�
					ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//��ծƽ������
			        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
			        System.out.println(prdtNo[i][1]+"-��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
					ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);//��Ϣ֧��
					System.out.println(prdtNo[i][1]+"-��Ϣ֧��:"+ylfxReport.getLxzc());
					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());//��ծ������
					System.out.println(prdtNo[i][1]+"-��ծ������:"+ylfxReport.getFzjlc());
					ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);//��ծת������
					System.out.println(prdtNo[i][1]+"-��ծת������:"+ylfxReport.getFzzysr());
					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());//��ծ������
					System.out.println(prdtNo[i][1]+"-��ծ������:"+ylfxReport.getFzjsr());
				}
			}else {//���������ƥ�䷨
				double rate = 0.0,term = 0.0,ftpPrice = 0.0,lx = 0.0,zyjg = 0.0,amt = 0.0;
				String[] prdtNos = settedPrdtNo.replace("'", "").split(",");
				for(String prdt_no : prdtNos) {//��QxppValue_map��ȡ��Ҫ��ֵ
					Double[] values=QxppValue_map.get(prdt_no);
					if(values == null){
						continue;
					}
					rate += values[0];
					term += values[1];
					ftpPrice += values[2];
					lx += values[3];
					zyjg += values[4];
					amt += values[5];
				}
				if(prdtNo[i][0].indexOf("P1") != -1) {//�ʲ���
					ylfxReport.setZcye(amt);// �ʲ����
					System.out.println(prdtNo[i][1]+"-�ʲ����:"+ylfxReport.getZcye());
					ylfxReport.setSxl(rate/amt);// ��Ϣ��
					System.out.println(prdtNo[i][1]+"-��Ϣ��:"+ylfxReport.getSxl());
			        ylfxReport.setZcpjqx(CommonFunctions.doublecut(term/amt, 1));//�ʲ�ƽ������
			        System.out.println(prdtNo[i][1]+"-�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
			        ylfxReport.setZczyjg(ftpPrice/amt);// �ʲ�ת�Ƽ۸�
					System.out.println(prdtNo[i][1]+"-�ʲ�ת�Ƽ۸�:"+ylfxReport.getZczyjg());
					ylfxReport.setLxsr(lx);// ��Ϣ����
					System.out.println(prdtNo[i][1]+"-��Ϣ����:"+ylfxReport.getLxsr());
					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
					System.out.println(prdtNo[i][1]+"-�ʲ�������:" + ylfxReport.getZcjlc());
					ylfxReport.setZczyzc(zyjg);//�ʲ�ת��֧��
					System.out.println(prdtNo[i][1]+"-�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
					System.out.println(prdtNo[i][1]+"-�ʲ�������:" + ylfxReport.getZcjsr());
				}else if(settedPrdtNo.indexOf("P2") != -1) {//��ծ��
					ylfxReport.setFzye(amt);// ��ծ���
					System.out.println(prdtNo[i][1]+"-��ծ���:"+ylfxReport.getFzye());
					ylfxReport.setFxl(rate/amt);// ��Ϣ��
					System.out.println(prdtNo[i][1]+"-��Ϣ��:"+ylfxReport.getFxl());
			        ylfxReport.setFzpjqx(CommonFunctions.doublecut(term/amt, 1));//��ծƽ������
			        System.out.println(prdtNo[i][1]+"-��ծƽ������:"+ylfxReport.getFzpjqx());
			        ylfxReport.setFzzyjg(ftpPrice/amt);// ��ծת�Ƽ۸�
					System.out.println(prdtNo[i][1]+"-��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
					ylfxReport.setLxzc(lx);// ��Ϣ֧��
					System.out.println(prdtNo[i][1]+"-��Ϣ֧��:"+ylfxReport.getLxzc());
					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
					System.out.println(prdtNo[i][1]+"-��ծ������:" + ylfxReport.getFzjlc());
					ylfxReport.setFzzysr(zyjg);//��ծת��֧��
					System.out.println(prdtNo[i][1]+"-��ծת��֧��:" + ylfxReport.getZczyzc());
					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
					System.out.println(prdtNo[i][1]+"-��ծ������:" + ylfxReport.getZcjsr());
				}
			}
			ylfxReportList.add(ylfxReport);
		}
	 return ylfxReportList;
	}

	/**
	 * ���ݻ�����ȡ����Ӧ�ʽ�������õ�ĳ��ҵ���¶�Ӧ�Ĳ�Ʒ
	 * 
	 * @param poolList
	 *            �ʽ��list
	 * @param prdtNo
	 *            ĳ��ҵ������Ӧ�����в�Ʒ
	 * @return
	 */
	public String getPrdtNoByPrcModeAndPrdtNo(List<FtpPoolInfo> poolList,
			String prdtNo) {
		StringBuffer settedPrdtNo = new StringBuffer();
		for (FtpPoolInfo ftpPoolInfo : poolList) {
			String contentObject = ftpPoolInfo.getContentObject();
			String[] contentObjects = contentObject.split("\\+");
			for (int i = 0; i < contentObjects.length; i++) {
				// ѭ���ж������õĲ�Ʒ�Ƿ���prdtNo��
				if (prdtNo.indexOf(contentObjects[i]) != -1)
					settedPrdtNo.append(contentObjects[i] + ",");
			}
		}
		System.out.println("settedPrdtNo:"+settedPrdtNo);
		if(settedPrdtNo.length() > 0) {
			if (settedPrdtNo.lastIndexOf(",") == settedPrdtNo.length() - 1)
				settedPrdtNo = settedPrdtNo.deleteCharAt(settedPrdtNo.length() - 1);
		}
		
		return settedPrdtNo.toString();
	}

	/**
	 * ����prcMode��brNo����ȡ��Ӧ���ʽ������
	 * 
	 * @param brNo
	 *            �����������
	 * @param prcMode
	 *            �ʽ��
	 * @return
	 */
	public List<FtpPoolInfo> getFtpPoolInfoList(String brNo, String prcMode) {
		String hsql = "from FtpPoolInfo where prcMode = '" + prcMode
				+ "' and brNo = '" + brNo + "'";
		List<FtpPoolInfo> poolList = daoFactory.query(hsql, null);
		return poolList;
	}

	/**
	 * ��ȡĳ���Ʒ�ļ�Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢�����--����ƥ��
	 * @param ftped_data_successList
	 * @param dateʱ����Ҷ˵�
	 * @param daysSubtract�ܵ�������
	 * @param prdtNo
	 * @return ��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢�����
	 */
	public double[] getQxppValue(List<String[]> ftped_data_successList, String date, Integer daysSubtract, String prdtNo) {
		double[] returnValue = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		double amt = 0.0, rate_fz = 0.0, ftpPrice_fz = 0.0, term_fz = 0.0, lxAmt = 0.0, zyAmt = 0.0;
		for (String[] result : ftped_data_successList) {
			if(prdtNo.indexOf(result[0]) != -1) {
				double rate = Double.valueOf(result[3]);
				double amt1 = Double.valueOf(result[2]==null?"0":result[2]);
				double ftpPrice = Double.valueOf(result[4]);
				double term = daysSubtract;//��ǰ���������µ�������
				amt += amt1;//�����
				rate_fz += rate*amt1;//��Ȩƽ�����ʼ���ķ���
				term_fz += term*amt1;//��Ȩƽ�����޼���ķ���
				ftpPrice_fz += ftpPrice*amt1;//��Ȩƽ��ת�Ƽ۸����ķ���
				lxAmt += amt1*rate*term/365;//��Ϣ����/��Ϣ֧��=���*����*����/365
				zyAmt += amt1*ftpPrice*term/365;//�ʲ�ת��֧��/��ծת������=���*ת�Ƽ۸�*����/365
			}
		}
		returnValue[0] = rate_fz/amt;//��Ȩƽ������
		returnValue[1] = CommonFunctions.doublecut(term_fz/amt, 1);//��Ȩƽ������
		returnValue[2] = ftpPrice_fz/amt;//��Ȩƽ��ת�Ƽ۸�
		returnValue[3] = lxAmt;//��Ϣ����/��Ϣ֧��
		returnValue[4] = zyAmt;//�ʲ�ת��֧��/��ծת������
		returnValue[5] = amt;//�����
		return returnValue;
	}
	/**
	 * ��ȡĳ�������ĳ���Ʒ�ļ�Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢�����--����ƥ��
	 * @param ftped_data_successList
	 * @param dateʱ����Ҷ˵�
	 * @param daysSubtract�ܵ�������
	 * @param prdtNo
	 * @param brNosҪ���ܵĻ����ַ�������'1801031009','1801031008'
	 * @return ��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢�����
	 * @throws ParseException 
	 */
	public double[] getQxppValueByBrNo(List<String[]> ftped_data_successList, String date, Integer daysSubtract, String prdtNo, String brNos) {
		double[] returnValue = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		double amt = 0.0, rate_fz = 0.0, ftpPrice_fz = 0.0, term_fz = 0.0, lxAmt = 0.0, zyAmt = 0.0;
		for (String[] result : ftped_data_successList) {
			if(prdtNo.indexOf(result[0]) != -1 && (brNos.indexOf(result[5])!= -1 || brNos.equals("is not null"))) {
				double rate = Double.valueOf(result[3]);
				double amt1 = Double.valueOf(result[2]==null?"0":result[2]);
				double ftpPrice = Double.valueOf(result[4]);
				double term = daysSubtract;
				amt += amt1;//�����
				rate_fz += rate*amt1;//��Ȩƽ�����ʼ���ķ���
				term_fz += term*amt1;//��Ȩƽ�����޼���ķ���
				ftpPrice_fz += ftpPrice*amt1;//��Ȩƽ��ת�Ƽ۸����ķ���
				lxAmt += amt1*rate*term/365;//��Ϣ����/��Ϣ֧��=���*����*����/365
				zyAmt += amt1*ftpPrice*term/365;//�ʲ�ת��֧��/��ծת������=���*ת�Ƽ۸�*����/365
			}
		}
		returnValue[0] = rate_fz/amt;//��Ȩƽ������
		returnValue[1] = CommonFunctions.doublecut(term_fz/amt, 1);//��Ȩƽ������
		returnValue[2] = ftpPrice_fz/amt;//��Ȩƽ��ת�Ƽ۸�
		returnValue[3] = lxAmt;//��Ϣ����/��Ϣ֧��
		returnValue[4] = zyAmt;//�ʲ�ת��֧��/��ծת������
		returnValue[5] = amt;//�����
		return returnValue;
	}
	
	
	/**
	 * ������ӯ������-----������ϸ��ѯ����ftped_data_successList�е����ݣ����ջ����ŵ���Ӧ��map��
	 * @param ftped_data_successList
	 * @param dateʱ����Ҷ˵�
	 * @param daysSubtract�ܵ�������
	 * @param brMstList Ҫ��ȡ����ֵ������ֱ����ʾ�Ļ������ݱ�����б�(2��1��0��������֧��)
	 * @return map
	 */
	public Map<String,Double[]> getQxppValueMap_jgzylfx(List<String[]> ftped_data_successList, String date, Integer daysSubtract, List<BrMst> brMstList) {
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> amt_map=new HashMap<String,Double>();
		Map<String,Double> rate_fz_map=new HashMap<String,Double>();
		Map<String,Double> ftpPrice_fz_map=new HashMap<String,Double>();
		Map<String,Double> term_fz_map=new HashMap<String,Double>();
		Map<String,Double> lxAmt_map=new HashMap<String,Double>();
		Map<String,Double> zyAmt_map=new HashMap<String,Double>();
	
		for (String[] result : ftped_data_successList) {
			
			double rate = Double.valueOf(result[3]);
			double amt1 = Double.valueOf(result[2]==null?"0":result[2]);
			double ftpPrice = Double.valueOf(result[4]);
			double term = daysSubtract;//�ܵ�������
			//�����
			if(amt_map.get(result[5]+"-"+result[0].substring(1,2))==null){
				amt_map.put(result[5]+"-"+result[0].substring(1,2), amt1);
			}else{
				amt_map.put(result[5]+"-"+result[0].substring(1,2), amt1+amt_map.get(result[5]+"-"+result[0].substring(1,2)));
			}
			//��Ȩƽ�����ʼ���ķ���
			if(rate_fz_map.get(result[5]+"-"+result[0].substring(1,2))==null){
				rate_fz_map.put(result[5]+"-"+result[0].substring(1,2), rate*amt1);
			}else{
				rate_fz_map.put(result[5]+"-"+result[0].substring(1,2), rate*amt1+rate_fz_map.get(result[5]+"-"+result[0].substring(1,2)));
			}
			
			//��Ȩƽ�����޼���ķ���
			if(term_fz_map.get(result[5]+"-"+result[0].substring(1,2))==null){
				term_fz_map.put(result[5]+"-"+result[0].substring(1,2), term*amt1);
			}else{
				term_fz_map.put(result[5]+"-"+result[0].substring(1,2), term*amt1+term_fz_map.get(result[5]+"-"+result[0].substring(1,2)));
			}
			//��Ȩƽ��ת�Ƽ۸����ķ���
			if(ftpPrice_fz_map.get(result[5]+"-"+result[0].substring(1,2))==null){
				ftpPrice_fz_map.put(result[5]+"-"+result[0].substring(1,2), ftpPrice*amt1);
			}else{
				ftpPrice_fz_map.put(result[5]+"-"+result[0].substring(1,2), ftpPrice*amt1+ftpPrice_fz_map.get(result[5]+"-"+result[0].substring(1,2)));
			}
			//��Ϣ����/��Ϣ֧��=���*����*����/365
			if(lxAmt_map.get(result[5]+"-"+result[0].substring(1,2))==null){
				lxAmt_map.put(result[5]+"-"+result[0].substring(1,2),amt1*rate*term/365);
			}else{
				lxAmt_map.put(result[5]+"-"+result[0].substring(1,2), amt1*rate*term/365+lxAmt_map.get(result[5]+"-"+result[0].substring(1,2)));
			}
			//�ʲ�ת��֧��/��ծת������=���*ת�Ƽ۸�*����/365
			if(zyAmt_map.get(result[5]+"-"+result[0].substring(1,2))==null){
				zyAmt_map.put(result[5]+"-"+result[0].substring(1,2),amt1*ftpPrice*term/365);
			}else{
				zyAmt_map.put(result[5]+"-"+result[0].substring(1,2), amt1*ftpPrice*term/365+zyAmt_map.get(result[5]+"-"+result[0].substring(1,2)));
			}			
		}
		for(BrMst br_mst:brMstList){
			String brNos=LrmUtil.getBrSql(br_mst.getBrNo());
			String[] br_no_s=null;
			if(brNos.indexOf("in") != -1) {
				br_no_s = brNos.substring(4,brNos.length()-1).replaceAll("'", "").split(",");
			}else {
				br_no_s = brNos.substring(1).replaceAll("'", "").split(",");
			}
			double[][] returnValue = new double[6][2];//[i][0]Ϊ�ʲ��ĸ����������[i][1]Ϊ��ծ�ĸ�����������
			for(String br_no:br_no_s){
				returnValue[0][0]=returnValue[0][0]+=rate_fz_map.get(br_no+"-1")==null?0.0:rate_fz_map.get(br_no+"-1");
				returnValue[0][1]=returnValue[0][1]+=rate_fz_map.get(br_no+"-2")==null?0.0:rate_fz_map.get(br_no+"-2");
				returnValue[1][0]=returnValue[1][0]+=term_fz_map.get(br_no+"-1")==null?0.0:term_fz_map.get(br_no+"-1");
				returnValue[1][1]=returnValue[1][1]+=term_fz_map.get(br_no+"-2")==null?0.0:term_fz_map.get(br_no+"-2");
				returnValue[2][0]=returnValue[2][0]+=ftpPrice_fz_map.get(br_no+"-1")==null?0.0:ftpPrice_fz_map.get(br_no+"-1");
				returnValue[2][1]=returnValue[2][1]+=ftpPrice_fz_map.get(br_no+"-2")==null?0.0:ftpPrice_fz_map.get(br_no+"-2");
				returnValue[3][0]=returnValue[3][0]+=lxAmt_map.get(br_no+"-1")==null?0.0:lxAmt_map.get(br_no+"-1");
				returnValue[3][1]=returnValue[3][1]+=lxAmt_map.get(br_no+"-2")==null?0.0:lxAmt_map.get(br_no+"-2");
				returnValue[4][0]=returnValue[4][0]+=zyAmt_map.get(br_no+"-1")==null?0.0:zyAmt_map.get(br_no+"-1");
				returnValue[4][1]=returnValue[4][1]+=zyAmt_map.get(br_no+"-2")==null?0.0:zyAmt_map.get(br_no+"-2");
				returnValue[5][0]=returnValue[5][0]+=amt_map.get(br_no+"-1")==null?0.0:amt_map.get(br_no+"-1");
				returnValue[5][1]=returnValue[5][1]+=amt_map.get(br_no+"-2")==null?0.0:amt_map.get(br_no+"-2");				
			}
			returnValue[0][0]=returnValue[0][0]/returnValue[5][0];
			returnValue[0][1]=returnValue[0][1]/returnValue[5][1];
			returnValue[1][0]=CommonFunctions.doublecut(returnValue[1][0]/returnValue[5][0],1);
			returnValue[1][1]=CommonFunctions.doublecut(returnValue[1][1]/returnValue[5][1],1);
			returnValue[2][0]=returnValue[2][0]/returnValue[5][0];
			returnValue[2][1]=returnValue[2][1]/returnValue[5][1];
			QxppValue_map.put(br_mst.getBrNo()+"-1", new Double[]{returnValue[0][0],returnValue[1][0],returnValue[2][0],returnValue[3][0],returnValue[4][0],returnValue[5][0]});
			QxppValue_map.put(br_mst.getBrNo()+"-2", new Double[]{returnValue[0][1],returnValue[1][1],returnValue[2][1],returnValue[3][1],returnValue[4][1],returnValue[5][1]});
		}
		return QxppValue_map;
	}
	
	/**
	 * ҵ������ӯ������---����Ʒ����ftped_data_successList�е����ݣ�����Ҫ���ܵĻ������ղ�Ʒ�ŵ���Ӧ��map��
	 * @param ftped_data_successList
	 * @param dateʱ����Ҷ˵�
	 * @param daysSubtract�ܵ�������
	 * @param xlsBrNo������
	 * @param brNosҪ���ܵĻ����ַ�������'1801031009','1801031008'
	 * @return map
	 */
	public Map<String,Double[]> getQxppValueMap_ywtxylfx(List<String[]> ftped_data_successList, String date, Integer daysSubtract, String xlsBrNo, String brNos) {
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> amt_map=new HashMap<String,Double>();
		Map<String,Double> rate_fz_map=new HashMap<String,Double>();
		Map<String,Double> ftpPrice_fz_map=new HashMap<String,Double>();
		Map<String,Double> term_fz_map=new HashMap<String,Double>();
		Map<String,Double> lxAmt_map=new HashMap<String,Double>();
		Map<String,Double> zyAmt_map=new HashMap<String,Double>();
	
		//����Ʒ��ŷŵ�map��
		for (String[] result : ftped_data_successList) {
			if(brNos.indexOf(result[5]) != -1 || brNos.equals("is not null")) {//���Ҫ���ܵĻ����а���result[5]
				double rate = Double.valueOf(result[3]);
				double amt1 = Double.valueOf(result[1]==null?result[2]:result[1]);
				double ftpPrice = Double.valueOf(result[4]);
				double term = daysSubtract;//������
				//�����
				if(amt_map.get(result[0])==null){
					amt_map.put(result[0], amt1);
				}else{
					amt_map.put(result[0], amt1+amt_map.get(result[0]));
				}
				//��Ȩƽ�����ʼ���ķ���
				if(rate_fz_map.get(result[0])==null){
					rate_fz_map.put(result[0], rate*amt1);
				}else{
					rate_fz_map.put(result[0], rate*amt1+rate_fz_map.get(result[0]));
				}
				
				//��Ȩƽ�����޼���ķ���
				if(term_fz_map.get(result[0])==null){
					term_fz_map.put(result[0], term*amt1);
				}else{
					term_fz_map.put(result[0], term*amt1+term_fz_map.get(result[0]));
				}
				//��Ȩƽ��ת�Ƽ۸����ķ���
				if(ftpPrice_fz_map.get(result[0])==null){
					ftpPrice_fz_map.put(result[0], ftpPrice*amt1);
				}else{
					ftpPrice_fz_map.put(result[0], ftpPrice*amt1+ftpPrice_fz_map.get(result[0]));
				}
				//��Ϣ����/��Ϣ֧��=���*����*����/365
				if(lxAmt_map.get(result[0])==null){
					lxAmt_map.put(result[0],amt1*rate*term/365);
				}else{
					lxAmt_map.put(result[0], amt1*rate*term/365+lxAmt_map.get(result[0]));
				}
				//�ʲ�ת��֧��/��ծת������=���*ת�Ƽ۸�*����/365
				if(zyAmt_map.get(result[0])==null){
					zyAmt_map.put(result[0],amt1*ftpPrice*term/365);
				}else{
					zyAmt_map.put(result[0], amt1*ftpPrice*term/365+zyAmt_map.get(result[0]));
				}	
			}
		}
		//���������Ӧ�����в�Ʒ
		String hsql = "from FtpProductMethodRel t where t.brNo = '"+xlsBrNo+"'";
		List<FtpProductMethodRel> ftpProductMethodRelList = daoFactory.query(hsql, null);
		double[] returnValue = new double[6];
		for(FtpProductMethodRel ftpProductMethodRel:ftpProductMethodRelList){
			returnValue[0]=rate_fz_map.get(ftpProductMethodRel.getProductNo())==null?0.0:rate_fz_map.get(ftpProductMethodRel.getProductNo());
			returnValue[1]=term_fz_map.get(ftpProductMethodRel.getProductNo())==null?0.0:term_fz_map.get(ftpProductMethodRel.getProductNo());
			returnValue[2]=ftpPrice_fz_map.get(ftpProductMethodRel.getProductNo())==null?0.0:ftpPrice_fz_map.get(ftpProductMethodRel.getProductNo());
			returnValue[3]=lxAmt_map.get(ftpProductMethodRel.getProductNo())==null?0.0:lxAmt_map.get(ftpProductMethodRel.getProductNo());
			returnValue[4]=zyAmt_map.get(ftpProductMethodRel.getProductNo())==null?0.0:zyAmt_map.get(ftpProductMethodRel.getProductNo());
			returnValue[5]=amt_map.get(ftpProductMethodRel.getProductNo())==null?0.0:amt_map.get(ftpProductMethodRel.getProductNo());
			QxppValue_map.put(ftpProductMethodRel.getProductNo(), new Double[]{returnValue[0],returnValue[1],returnValue[2],returnValue[3],returnValue[4],returnValue[5]});
		}
		return QxppValue_map;
	}
	
	 /**
	  * ��ȡĳһ������ָ�������µ����б�ҵ���һ��ȫ������ƥ�䶨�� 
	  * @param brNo
	  * @param manageLvl
	  * @param date
	  * @return
	  */
	public List<String[]> get_ftped_data_successList(String brNo, String manageLvl, String date) {
		String STime=CommonFunctions.GetCurrentTime();
		//��ѯ��øû����µ�����ҵ��������
		String sql="";
		if("2".equals(manageLvl)){
			sql="select br_no from ftp.br_mst where is_business='1' order by br_no";
		}else{
			String brSql=LrmUtil.getBrSql(brNo);
			sql="select br_no from ftp.br_mst where is_business='1' and br_no "+brSql+" order by br_no";
		}
		List brNoList = daoFactory.query1(sql, null);
		
		//������Ʒ�ĸ�����Ե���
		UL06BO uL06BO = new UL06BO();
		Map<Integer, Double> ckzbjAdjustMap = uL06BO.getCkzbjAdjustValue();//���׼�������
		Map<Integer, Double> ldxAdjustMap = uL06BO.getLdxAdjustValue();//�����Ե���
		Map<String, Double> irAdjustMap = uL06BO.getIrAdjustValue();//���÷��յ���
		List<Ftp1PrepayAdjust> prepayList = uL06BO.getPrepayAdjustValue();//��ǰ����/��ǰ֧ȡ����
		Map<String, Double> clAdjustMap = uL06BO.getClAdjustValue();//���Ե���
		double[][] dkAdjustArr = uL06BO.getDkAdjustValue();//�����������
		Double[][] publicRate = FtpUtil.getFtpPublicRate();//���д�������
		//double[][] dkSfblAdjustArr=uL06BO.getDkSfblAdjustValue();//�����ϸ�������������
		
		//��ȡ�û�����Ӧ������ָ�����������ڡ��µ�����������map<curve_no,SCYTCZlineF>
		//Map<String,SCYTCZlineF> curvesF_map=FtpUtil.getMap_AllCurves_sfb(computeDate, xian_brNo, "2");//���Է����������������Լ���
		Map<String,SCYTCZlineF> curvesF_map=FtpUtil.getMap_AllCurves_sfb(CommonFunctions.GetCurrentTime().substring(0,10).replaceAll("-", ""), brNo, manageLvl);//
		if(curvesF_map == null || curvesF_map.size() == 0) return null;
		//��ȡ�û�����Ӧ�������Ʒ���۷�����ϵ�map
		Map<String,String[]> ftp_methodComb_map=FtpUtil.getMap_FTPMethod(brNo);
		
 		int all_ftp_total_num=0;//��ѡ�����۵��ܱ���
//     	int all_ftp_success_num=0;//���۳ɹ��ܱ���
 		int all_ftp_fault_num=0;//����ʧ���ܱ���
 		double sbbal=0;
 		double zcbal=0;
 		double fzbal=0;
        double cgzcbal=0;
        double cgfzbal=0;
		//##############################################################################################
		//                  ���еײ��������ѭ��
		//##############################################################################################
		//���еײ��������ѭ�����ۣ���Ϊһ����ȫ��ѯ��������̫�࣬�����ڴ��޷����� --- onceBrNum������Ϊһ��ѭ��һ��
		//��û��UIDʱ��6��һ��ʱ��Ż�ʱ25���ӣ�12��һ��ʱ��Լ20���ӡ�
		//����UIDʱ��34������һ�飬��ʱ7��30�룬����ڴ�400M��12��һ�飬��ʱ9��20�룬����ڴ�190M��
		int onceBrNum=12;//һ����ִ��(һ��)��ѯҵ���¼�����ۼ��㡢�����涨�۽����¼�Ļ���������
		List<String[]> ftped_data_successList=new ArrayList<String[]>();//���۳ɹ���ҵ���¼list
		for(int m=0;m<( brNoList.size()%onceBrNum==0 ? (brNoList.size()/onceBrNum) : (brNoList.size()/onceBrNum+1) );m++){
//			List<FtpBusinessInfo> ftped_data_errorList=new ArrayList<FtpBusinessInfo>();//����ʧ�ܵ�ҵ���¼list
			
			//����һ���Բ�ѯ��onceBrNum��������sql
			String brNos_sql="(";
			for(int a=0;a<onceBrNum;a++ ){
				if((m*onceBrNum+a)<brNoList.size()){
					brNos_sql+="'"+brNoList.get(m*onceBrNum+a).toString()+"',";
				}				
			}
			brNos_sql=brNos_sql.substring(0, brNos_sql.length()-1)+")";//ȥ�����һ�����ţ���ΪbrNos_sql�ﲻ����û�������Բ����ж��޶���
			
			System.out.println("############################################");
			System.out.println("* ��ʼִ�е� "+(m+1)+ "�����"+brNos_sql);
			System.out.println("############################################");
			
			/*if(brNos_sql.indexOf("1801033610")<0){//����
				break;
			}*/
			
			System.out.println("* ���˻�ҵ�����ݼ�¼��ѯ��ȡ��...");
			sql="select * from ftp.Ftp_Qxpp_Business t where t.BAL!=0 and t.BR_NO in "+brNos_sql;			
			List listdata = daoFactory.query1(sql, null);
			System.out.println("����ѯ��ȡ "+listdata.size()+" ��ҵ������");
					
			/*if(true){//����
				System.out.println("listdata.size()="+listdata.size());
				continue;
			}*/
			
			int ftp_once_num=listdata.size();		
			all_ftp_total_num+=ftp_once_num;
			List<FtpBusinessInfo> ftpBusinessInfoList = new ArrayList<FtpBusinessInfo>();
			if (listdata != null && listdata.size() > 0) {
				for (int i = 0; i < listdata.size(); i++) {
					//System.out.println("######  "+i+"  ##########");
					Object[] o = (Object[])listdata.get(i);
					listdata.set(i, null);//�ͷ�listѭ�����۹����У��ñʼ�¼����ռ�ڴ�(��list�иñʼ�¼��ʹ�ú�)
					
					FtpBusinessInfo ftpBusinessInfo = new FtpBusinessInfo();
					ftpBusinessInfo.setBrNo(o[0] == null? null : o[0].toString().trim());
					ftpBusinessInfo.setBusinessNo(o[5] == null? null : o[5].toString().trim());
					ftpBusinessInfo.setPrdtNo(o[7] == null? null : o[7].toString().trim());
					ftpBusinessInfo.setAmt(o[10] == null? null : o[10].toString().trim());
					ftpBusinessInfo.setBal(o[11] == null? null : o[11].toString().trim());
					ftpBusinessInfo.setRate(o[12] == null? null : String.valueOf(Double.valueOf(o[12].toString().trim())/100));
					ftpBusinessInfo.setTerm(o[13] == null? null : o[13].toString().trim());
					ftpBusinessInfo.setFivSts(o[20] == null? null : o[20].toString().trim());
					ftpBusinessInfoList.add(ftpBusinessInfo);
				}
			}
			
			listdata.clear(); System.gc();//�ͷ�����list��ռ�ڴ�(������list��ʹ�ú�)
			//###########################################################################
			//         ��ʼ����
			//###########################################################################
			System.out.println("* "+ftp_once_num+" ��ҵ�񶨼ۼ�����...");
			//����ѡ��ҵ���� ���ζ��� ���������۽������ftped_data_successList ��  all_ftped_data_errorList
			for(int i=0;i<ftpBusinessInfoList.size();i++){
				FtpBusinessInfo ftp_business_info=ftpBusinessInfoList.get(i);
				ftpBusinessInfoList.set(i, null);//�ͷ�listѭ�����۹����У��ñʼ�¼����ռ�ڴ�(��list�иñʼ�¼��ʹ�ú�)
				
				//String[] ftp_methodComb=FtpUtil.getFTPMethod_byPrdtNo(ftp_business_info.getPrdtNo(),ftp_business_info.getBrNo());
				
				
				
				String[] ftp_methodComb=ftp_methodComb_map.get(ftp_business_info.getPrdtNo());
				if(ftp_methodComb==null){//û�����ñ����ҵ���Ӧ���۷�����¼�У��򲻶Ըñ�ҵ�񶨼�
//					ftp_business_info.setMethodName("��δ����");
//					if(all_ftped_data_errorList.size()<20000){
//						all_ftped_data_errorList.add(ftp_business_info);
//					}				
					sbbal+=Double.valueOf(ftp_business_info.getBal());
					if(ftp_business_info.getPrdtNo().indexOf("P1")!=-1) {
						zcbal+=Double.valueOf(ftp_business_info.getBal());
					}else {
						fzbal+=Double.valueOf(ftp_business_info.getBal());
					}
					all_ftp_fault_num++;
					continue;
				}
				
				
				double adjust_rate=Double.valueOf(ftp_methodComb[2]);//��������
				String method_no=ftp_methodComb[0];//���嶨�۷������
				int term=(ftp_business_info.getTerm()==null || ftp_business_info.getTerm().equals(""))?0:Integer.valueOf(ftp_business_info.getTerm());
				if(method_no.equals("06")){//ֻ�С����ʴ����06����ʹ�á��ο����ޡ� 
					term=Integer.valueOf(ftp_methodComb[1]);//�ο�����
				}
				String curve_no=ftp_methodComb[3];//ʹ�õ����������߱��
				
				/*
				 * String curve_date="";//ѡ�����������ߵ���������,ʵ�֡�����ѡ���ϸ�ÿ�ա��¼ӡ�
				//������ڴ��ҵ������ѡ�����������ߵ���������Ϊ���ݿ�ϵͳ����
				if(("YW201").equals(ftp_business_info.getBusinessNo())){
					curve_date=String.valueOf(CommonFunctions.GetDBSysDate());
				}else{//����ҵ�� ,����ѡ�����������ߵ���������Ϊ��ҵ��������<��������>---��������Ϊ�գ���Ĭ��ȡ���ݿ�ϵͳ����
					curve_date=(ftp_business_info.getOpnDate()==null || ftp_business_info.getOpnDate().equals(""))?String.valueOf(CommonFunctions.GetDBSysDate()):ftp_business_info.getOpnDate();
				}*/
				if(!"��".equals(curve_no) && curvesF_map.get(curve_no)==null){
					//ftp_business_info.setCurveName("��δ����");
//					if(all_ftped_data_errorList.size()<20000){
//						all_ftped_data_errorList.add(ftp_business_info);
//					}
					sbbal+=Double.valueOf(ftp_business_info.getBal());
					if(ftp_business_info.getPrdtNo().indexOf("P1")!=-1) {
						zcbal+=Double.valueOf(ftp_business_info.getBal());
					}else {
						fzbal+=Double.valueOf(ftp_business_info.getBal());
					}
					all_ftp_fault_num++;
					continue;
				}
				double appoint_rate=Double.valueOf(ftp_methodComb[4]);//ָ������
				double appoint_delta_rate=Double.valueOf(ftp_methodComb[5]);//�̶�����
				
				double ftp_price=-1;
				if(method_no.equals("01")){//## ԭʼ����ƥ�䷨
					ftp_price=FtpUtil.getFTPPrice_ysqxppf(term, curvesF_map.get(curve_no), adjust_rate);
				}else if(method_no.equals("02")){//## ָ�����ʷ�
					//ftp_price=FtpUtil.getFTPPrice_zdllf(term, curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
					ftp_price=appoint_rate+adjust_rate;
				}else if(method_no.equals("03")){//## �ض�������ƥ�䷨
					/////
				}else if(method_no.equals("04")){//## �ֽ�����,��������
					ftp_price=FtpUtil.getFTPPrice_xjlf(term,30,curvesF_map.get(curve_no), adjust_rate);
				}else if(method_no.equals("05")){//## ���ڷ�
					if(ftp_business_info.getPrdtNo().substring(0, 4).equals("P109")){//���Ҵ����������
						ftp_price=FtpUtil.getFTPPrice_jqf(term,30,0,curvesF_map.get(curve_no), adjust_rate);
					}else{//�̶��ʲ�,Ĭ�����ò�ֵ��Ϊ0.4���۾�����
						ftp_price=FtpUtil.getFTPPrice_jqf(term,365,0.4,curvesF_map.get(curve_no), adjust_rate);
					}		
				}else if(method_no.equals("06")){//## ���ʴ����
					ftp_price=FtpUtil.getFTPPrice_lldmcef(term, curvesF_map.get(curve_no), adjust_rate);
				}else if(method_no.equals("07")){//## ��Ȩ���ʷ�
					ftp_price=FtpUtil.getFTPPrice_jqllf(curvesF_map.get(curve_no), adjust_rate);
				}else if(method_no.equals("08")){//## �̶����
					ftp_price=Double.parseDouble(ftp_business_info.getRate())+appoint_delta_rate+adjust_rate;
				}else{
//					ftp_business_info.setMethodName("����"+method_no+"���ô���");
//					if(all_ftped_data_errorList.size()<20000){
//						all_ftped_data_errorList.add(ftp_business_info);
//					}
					sbbal+=Double.valueOf(ftp_business_info.getBal());
					if(ftp_business_info.getPrdtNo().indexOf("P1")!=-1) {
						zcbal+=Double.valueOf(ftp_business_info.getBal());
					}else {
						fzbal+=Double.valueOf(ftp_business_info.getBal());
					}
					all_ftp_fault_num++;
					continue;
				}
				//FTP����
				double adjustValue = 0;
				if(ftp_methodComb[9].equals("0")) {//�Ƿ���е���
					if(curve_no.equals("0100")) {//ʹ�ô�������������ߵĲ�Ʒ��Ҫ���и������
						double amt = (ftp_business_info.getAmt()==null||ftp_business_info.getAmt().equals(""))?Double.NaN:Double.valueOf(ftp_business_info.getAmt());
						adjustValue = FtpUtil.getCdkFtpAdjustValue(ftp_business_info.getBusinessNo(), term, ckzbjAdjustMap, ldxAdjustMap, irAdjustMap, prepayList);
					}else if(curve_no.startsWith("02")) {//ʹ���г����������� +�����Է��ռӵ�+����ռ�üӵ�
						adjustValue += Double.valueOf(ftp_methodComb[7])+Double.valueOf(ftp_methodComb[8]);
					}else{//������ʱʲôҲ����
						
					}
					
					//���Ե��������ݲ�Ʒ��ȡ��Ӧ������
					adjustValue += clAdjustMap.get(ftp_business_info.getPrdtNo()) == null ? 0 : clAdjustMap.get(ftp_business_info.getPrdtNo());
					
				}else {
					double amt = (ftp_business_info.getAmt()==null||ftp_business_info.getAmt().equals(""))?Double.NaN:Double.valueOf(ftp_business_info.getAmt());
					double rate = (ftp_business_info.getRate()==null||ftp_business_info.getRate().equals(""))?Double.NaN:Double.valueOf(ftp_business_info.getRate());
					adjustValue = FtpUtil.getDkAmtAdjust(ftp_business_info.getPrdtNo(), term, amt, dkAdjustArr, publicRate,rate);
				}
				ftp_price += adjustValue;
				
				//�鿴�弶����״̬������ǲ�������(03��04��05)����۸�=����			
				if(ftp_business_info.getBusinessNo().equals("YW101") && (ftp_business_info.getFivSts().equals("03")||ftp_business_info.getFivSts().equals("04")||ftp_business_info.getFivSts().equals("05"))) {
					ftp_price = Double.valueOf(ftp_business_info.getRate());
				}
				ftp_business_info.setFtpPrice(ftp_price);
				if(Double.isNaN(ftp_price)){
//					if(all_ftped_data_errorList.size()<20000){
//						all_ftped_data_errorList.add(ftp_business_info);
//					}
					if(ftp_business_info.getPrdtNo().indexOf("P1")!=-1) {
						zcbal+=Double.valueOf(ftp_business_info.getBal());
					}else {
						fzbal+=Double.valueOf(ftp_business_info.getBal());
					}
					sbbal+=Double.valueOf(ftp_business_info.getBal());
					all_ftp_fault_num++;
				}else{
					if(ftp_business_info.getPrdtNo().indexOf("P1")!=-1) {
						cgzcbal+=Double.valueOf(ftp_business_info.getBal());
					}else {
						cgfzbal+=Double.valueOf(ftp_business_info.getBal());
					}
					String[] result = new String[6];
					result[0] = ftp_business_info.getPrdtNo();
					result[1] = ftp_business_info.getAmt();
					result[2] = ftp_business_info.getBal();
					result[3] = ftp_business_info.getRate();
					result[4] = String.valueOf(ftp_business_info.getFtpPrice());
					result[5] = ftp_business_info.getBrNo();
					ftped_data_successList.add(result);
				}
				
			}
			
			ftpBusinessInfoList.clear();
			System.gc();//�ͷ�����list��ռ�ڴ�(������list��ʹ�ú�)
		}
		String ETime=CommonFunctions.GetCurrentTime();
		int costTime=CommonFunctions.GetCostTimeInSecond(STime, ETime);
		int CostFen=costTime/60;
		int CostMiao=costTime%60;
		System.out.println("��ʱ"+CostFen+"��"+CostMiao+"��");		
		String ftpResultDescribe="������ɣ��ܹ�ѡ��"+all_ftp_total_num+"�ʣ�����ʧ��"+all_ftp_fault_num+"�ʣ�ʧ���ʲ����"+zcbal+"��ʧ�ܸ�ծ���"+fzbal+"���ɹ��ʲ����"+cgzcbal+"���ɹ���ծ���"+cgfzbal;
		System.out.println(ftpResultDescribe);
		return ftped_data_successList;
	}
	
	/**
	 * ���ݻ����źͻ������𣬻�ȡ���Ӧ��������brno
	 * 
	 * @param brNo
	 * @param manageLvl
	 * @return
	 */
	public String getXlsBrNo(String brNo, String manageLvl) {
		if (manageLvl.equals("2")) {
			return brNo;
		} else if (manageLvl.equals("1")) {
			String hsql = "from BrMst where brNo = '" + brNo + "'";
			BrMst brMst = (BrMst) daoFactory.getBean(hsql, null);
			return brMst.getSuperBrNo();// �����1������ȡ���ĸ�����Ϊ����Ӧ��������
		} else if (manageLvl.equals("0")) {
			String hsql = "from BrMst where brNo = '" + brNo + "'";
			BrMst brMst = (BrMst) daoFactory.getBean(hsql, null);
			String hsql2 = "from BrMst where brNo = '" + brMst.getSuperBrNo()
					+ "'";
			brMst = (BrMst) daoFactory.getBean(hsql2, null);
			return brMst.getSuperBrNo();// �����0����Ҫѭ�����λ�ȡ���ĸ����ĸ�����Ϊ����Ӧ��������
		}
		return "";
	}
	
	/**
	 * ����������brNo��Ҫ��ȡ�Ļ������𣬻�ȡ�������¸ü�������л���
	 * @param brNo ������brNo
	 * @param manageLvl
	 * @return
	 */
	public List<BrMst> getBrMstList(String xlsBrNo, String manageLvl) {
		String hsql = "";
		List<BrMst> brMstList = new ArrayList<BrMst>();
		if(manageLvl.equals("1")) {
			hsql = "from BrMst where superBrNo = '"+xlsBrNo+"'";
			brMstList = daoFactory.query(hsql, null);
		}else if(manageLvl.equals("0")) {
			hsql = "from BrMst where superBrNo = '"+xlsBrNo+"'";
			List<BrMst> brMstList1 = daoFactory.query(hsql, null);
			for(BrMst brMst : brMstList1) {
				hsql = "from BrMst where superBrNo = '"+brMst.getBrNo()+"'";
				List<BrMst> brMstList2 = daoFactory.query(hsql, null);
				for(BrMst brMst2 : brMstList2) {
					brMstList.add(brMst2);
				}
			}
		}
		return brMstList;
	}
}
