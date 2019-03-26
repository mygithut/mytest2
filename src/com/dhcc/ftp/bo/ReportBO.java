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
import com.dhcc.ftp.entity.FtpBusinessStaticDivide;
import com.dhcc.ftp.entity.FtpPoolInfo;
import com.dhcc.ftp.entity.FtpProductMethodRel;
import com.dhcc.ftp.entity.YlfxReport;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.LrmUtil;

public class ReportBO extends BaseBo {

	/**
	 * ������ӯ������
	 * @param request
	 * @param date
	 * @param brNo
	 * @param manageLvl ��������
	 * @param assessScope ͳ��ά�� ��-1��-3��-12
	 * @param isMx �Ƿ�鿴��ϸ
	 * @param tjType ͳ������ 1.���� 2.����
	 * @return
	 */
	public List<YlfxReport> brPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, 
			Integer assessScope, Integer isMx, String tjType) {
		CacheOperation co = CacheOperation.getInstance();//����
	    long intervalTime = Long.valueOf("2592000000");//������һ����
	    int maxVisitCount = 0;//�����Ʒ��ʴ���
	    
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String xlsBrNo = FtpUtil.getXlsBrNo(brNo, manageLvl);// ������
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
	    //��ȡ�����÷������ںͽ������ڵĲ�ѯ����
		
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
				//����ƥ�䣺��ȡӯ������ʱ�䷶Χ�ڵĶ������ڴ���
				List<String[]> ftpResultsList=(List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, date2, date, tjType}, intervalTime, maxVisitCount);
				if(ftpResultsList == null) return null;
				//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
				double[] qxppZcValue = this.getQxppValueByBrNo(ftpResultsList, prdtNoZc, brSql);
				double[] qxppFzValue =this.getQxppValueByBrNo(ftpResultsList, prdtNoFz, brSql);
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
				ylfxReport.setZczyzc(qxppZcValue[4]);//�ʲ�ת��֧��
				System.out.println("�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
				
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
				ylfxReport.setFzzysr(qxppFzValue[4]);//��ծת��֧��
				System.out.println("��ծת��֧��:" + ylfxReport.getZczyzc());
				
				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
				System.out.println("�ʲ�������:" + ylfxReport.getZcjlc());
				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
				System.out.println("�ʲ�������:" + ylfxReport.getZcjsr());
				
				
				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
				System.out.println("��ծ������:" + ylfxReport.getFzjlc());
				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
				System.out.println("��ծ������:" + ylfxReport.getZcjsr());
				
				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// ������
			}
			ylfxReportList.add(ylfxReport);
		} else {
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
				//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
				List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, date2, date, tjType}, intervalTime, maxVisitCount);
				
				if(ftpResultsList == null) return null;
				Map<String,Double[]> QxppValue_map = this.getQxppValueMap_jgzylfx(ftpResultsList, list);
				
				for (int i = 0; i < list.size(); i++) {
					BrMst brMst = list.get(i);
					System.out.println("��ʼ�������:"+brMst.getBrNo()+"...");
					YlfxReport ylfxReport = new YlfxReport();
					ylfxReport.setBrName(brMst.getBrName());
					ylfxReport.setBrNo(brMst.getBrNo());
					ylfxReport.setManageLvl(brMst.getManageLvl());
					Double[] qxppZcValue = QxppValue_map.get(brMst.getBrNo()+"-1");
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
					
					//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
					Double[] qxppFzValue = QxppValue_map.get(brMst.getBrNo()+"-2");
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
	public List<YlfxReport> busPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope, String tjType) {
		
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String xlsBrNo = FtpUtil.getXlsBrNo(brNo, manageLvl);// ������
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
		
		ylfxReportList = this.getYlfxReportList(brSql, xlsBrNo, date, prcMode, assessScope, businessNo, ftpPoolInfoList, tjType);

		return ylfxReportList;
	}
	
	/**
	 * ҵ������ӯ������---ĳһ��ҵ��(Ŀǰֻ֧�֣������Ͷ��ҵ��)�����в�Ʒӯ������ ���ݻ�ȡ
	 * @param request
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @param staticNo
	 * @return
	 */
	public List<YlfxReport> prdtPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope, String prdtType, String tjType) {
		
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String xlsBrNo = FtpUtil.getXlsBrNo(brNo, manageLvl);// ������
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
		Map<String, String> prdtCatgResult = this.getPrdtCatg();//����Ʒ���ղ�Ʒ������з���
		String[][] prdtNo = null;//��Ʒ���+��Ʒ����
		String businessNo = "";
		if(prdtType.equals("ck")) {//���
			businessNo = "'YW201','YW202','YW203','YW204'";
		}else if(prdtType.equals("dk")) {//����
			businessNo = "'YW101'";
		}else if(prdtType.equals("tzyw")) {//Ͷ��ҵ��
			businessNo = "'YW105'";
		}
		String[][] prdtCtgNos = this.getPrdtCtgNos(businessNo);
		prdtNo = new String[prdtCtgNos.length][2];
		for (int i = 0; i < prdtCtgNos.length; i++) {
			prdtNo[i][0] = prdtCatgResult.get(prdtCtgNos[i][0]);//��Ʒ�����Ӧ�����в�Ʒ���
			prdtNo[i][1] = prdtCtgNos[i][1];//��Ʒ��������
		}
		String brSql = LrmUtil.getBrSql(brNo);
		ylfxReportList = this.getYlfxReportListByPrdtNo(brSql, xlsBrNo, date, prcMode, assessScope, prdtNo, ftpPoolInfoList, tjType);
		
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
	public List<YlfxReport> brPayOffRanking(HttpServletRequest request, String date, String brNo, String manageLvl, String brCountLvl, Integer assessScope, String tjType) {
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
		}else {
			List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, date2, date, tjType}, intervalTime, maxVisitCount);
			if(ftpResultsList == null) return null;
			Map<String,Double[]> QxppValue_map=this.getQxppValueMap_jgzylfx(ftpResultsList, brMstList);

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
	public YlfxReport financialCenterLCProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope, String tjType) {
		CacheOperation co = CacheOperation.getInstance();//����
	    long intervalTime = Long.valueOf("2592000000");//������һ����
	    int maxVisitCount = 0;//�����Ʒ��ʴ���
		
	    String xlsBrNo = FtpUtil.getXlsBrNo(brNo, manageLvl);// ������
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
			//����ƥ�䣺��ȡӯ������ʱ�䷶Χ�ڵĶ������ڴ���
			List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, date2, date, tjType}, intervalTime, maxVisitCount);
			if(ftpResultsList == null) return null;
			
			double[] qxppZcValue = this.getQxppValue(ftpResultsList, date, daysSubtract, prdtNoZc);
			double[] qxppFzValue = this.getQxppValue(ftpResultsList, date, daysSubtract, prdtNoFz);
			
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
	 * ����ƥ���£� ���ݻ�����ȡ����Ӧ�ʽ�������õĲ�Ʒ
	 * 
	 * @param brNo �����������
	 * @return
	 */
	public String getPrdtNoQxpp(String brNo) {
		StringBuffer prdtNo = new StringBuffer();
		String hsql = "from FtpProductMethodRel t where t.brNo = '"+brNo+"'";
		
		List<FtpProductMethodRel> ftpProductMethodRelList = daoFactory.query(hsql, null);
		for(FtpProductMethodRel ftpProductMethodRel : ftpProductMethodRelList) {
			prdtNo.append("'" + ftpProductMethodRel.getProductNo() + "',");
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
			+ "row_number() over(partition by t.pool_no order by t.RES_DATE desc,T.RESULT_ID desc ) rn from ftp.Ftp_Result t "
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
				+ "row_number() over(partition by t.pool_no order by t.RES_DATE desc,T.RESULT_ID desc ) rn from ftp.Ftp_Result t "
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
	 * @param date
	 * @param prcMode �ʽ������
	 * @param assessScope ͳ��ά��
	 * @param businessNo ҵ����[]
	 * @param ftpPoolInfoList �ʽ������list
	 * @return
	 */
	public List<YlfxReport> getYlfxReportList(String brSql,String xlsBrNo, String date,String prcMode, Integer assessScope, String[] businessNo, List<FtpPoolInfo> ftpPoolInfoList, String tjType) {
		CacheOperation co = CacheOperation.getInstance();//����
	    long intervalTime = Long.valueOf("2592000000");//������һ����
	    int maxVisitCount = 0;//�����Ʒ��ʴ���
		
	    List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String date2 = CommonFunctions.dateModifyM(date, assessScope);
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//���������꣬��ֻȡ��������ݣ���1�·ݿ�ʼ
	    	date2 = date2.substring(0,4)+"1231";
		
		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//��ȡ��ͳ�Ƶ�����
		
		List<String[]> ftpResultsList = new ArrayList<String[]>();
		Map<String,Double[]> QxppValue_map = new HashMap<String, Double[]>();
		if(prcMode.equals("4")) {//���������ƥ��
			ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, date2, date, tjType}, intervalTime, maxVisitCount);
			if(ftpResultsList == null) return null;
			//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
			QxppValue_map = this.getQxppValueMap_ywtxylfx(ftpResultsList, xlsBrNo, brSql);
		}
		
		for(int i = 0; i < businessNo.length; i++) {
			//����ҵ���Ż�ȡ��Ӧ�Ĳ�Ʒ���
			String hsql = "from FtpProductMethodRel where businessNo in ("+businessNo[i]+") and brNo = '"+xlsBrNo+"'";
			List<FtpProductMethodRel> ftpProductMethodRelList = daoFactory.query(hsql, null);
			StringBuilder prdtNo = new StringBuilder();
			for (FtpProductMethodRel ftpProductMethodRel : ftpProductMethodRelList) {
				prdtNo.append("'" + ftpProductMethodRel.getProductNo() + "',");
			}
			prdtNo = prdtNo.deleteCharAt(prdtNo.length() - 1);
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
			YlfxReport ylfxReport = new YlfxReport();
			ylfxReport.setBusinessNo(businessNo[i]);// ҵ����
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
			}else {//���������ƥ�䷨
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
	 * @param prdtNo ��Ʒ���+��Ʒ��������[][]
	 * @param ftpPoolInfoList �ʽ������list
	 * @return
	 */
	public List<YlfxReport> getYlfxReportListByPrdtNo(String brSql,String xlsBrNo, String date,String prcMode, Integer assessScope, String[][] prdtNo, List<FtpPoolInfo> ftpPoolInfoList, String tjType) {
		CacheOperation co = CacheOperation.getInstance();//����
	    long intervalTime = Long.valueOf("2592000000");//������һ����
	    int maxVisitCount = 0;//�����Ʒ��ʴ���
	    
	    List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String date2 = CommonFunctions.dateModifyM(date, assessScope);
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//���������꣬��ֻȡ��������ݣ���1�·ݿ�ʼ
	    	date2 = date2.substring(0,4)+"1231";
		
		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//��ȡ��ͳ�Ƶ�����
		
		List<String[]> ftpResultsList = new ArrayList<String[]>();
		Map<String,Double[]> QxppValue_map = new HashMap<String, Double[]>();
		if(prcMode.equals("4")) {//���������ƥ��
			ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, date2, date, tjType}, intervalTime, maxVisitCount);
			if(ftpResultsList == null) return null;
			//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
			QxppValue_map = this.getQxppValueMap_ywtxylfx(ftpResultsList, xlsBrNo, brSql);
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
	  * ��ȡĳһ������ָ�������µ�����ƥ�䶨�� 
	  * @param xlsbrNo
	  * @param minDate������˵�
	  * @param maxDate�����Ҷ˵�
	  * @param tjTypeͳ������--1������2����
	  * @return
	  */
	public List<String[]> getQxppResultList(String xlsbrNo, String minDate, String maxDate, String tjType) {
		List<String[]> resultList=new ArrayList<String[]>();

		String prdtNo = this.getPrdtNoQxpp(xlsbrNo);//����ƥ�������õ����в�Ʒ
		//���ж�Ҫ����Ĳ�Ʒ���Ƿ����⼸���Ʒ
		String grhqPrdtNo = "'P2010','P2012','P2027','P2028'";//����Ĳ�Ʒ���<����>
		//������Ĳ�Ʒ���<�������ļ���ĸ��˻���+��λ����>
		String dwhqPrdtNo = "'P2001','P2002','P2003','P2004','P2011','P2025','P2026','P2051','P2053','P2055','P2057','P2059','P2061','P2063','P2065','P2067'";
        
		String lhqPrdtNo = "";//���˻���'YW201'�⣬��Ҫ��ζ��۵Ĳ�Ʒ
		lhqPrdtNo += "'P1001'";//�ֽ�
		
		//1.�ȴ���ÿ�ڶ��۵�����ڴ��<��Ҫ�ǻ��ڴ����������ֽ�>-------------Ŀǰ����������һ��
		String hq_sql = "select rate, count(*) n,nvl(sum(bal),0.0) bal," +
		" max(ftp_price),min(ftp_price),max(wrk_sys_date), min(wrk_sys_date),br_no,product_no,max(opn_date)" +
		" from ftp.ftp_qxpp_result " +
        " where br_no is not null " +//�����л���
                " and to_date(wrk_sys_date,'yyyymmdd')>to_date('"+minDate+"','yyyymmdd')" +
        		" and to_date(wrk_sys_date,'yyyymmdd')<=to_date('"+maxDate+"','yyyymmdd') " +
                " and business_no='YW201' and product_no in ("+grhqPrdtNo+")" +
                " group by br_no,product_no,kmh,rate" +//���˻�������
        " union all " +
        "select max(rate), count(*) n,nvl(sum(bal),0.0) bal," +
        " max(ftp_price),min(ftp_price),max(wrk_sys_date), min(wrk_sys_date),br_no,product_no,max(opn_date)" +
        " from ftp.ftp_qxpp_result " +
        " where br_no is not null " +
                " and to_date(wrk_sys_date,'yyyymmdd')>to_date('"+minDate+"','yyyymmdd')" +
		        " and to_date(wrk_sys_date,'yyyymmdd')<=to_date('"+maxDate+"','yyyymmdd') " +
                " and ((business_no='YW201' and product_no in ("+dwhqPrdtNo+")) or business_no='YW203' or product_no in ("+lhqPrdtNo+"))" +
                " group by br_no,product_no,ac_id ";//��λ��������
//        " union all " +
//        "select rate, count(*) n,nvl(sum(bal),0.0) bal," +
//		" max(ftp_price),min(ftp_price),max(wrk_sys_date), min(wrk_sys_date),br_no,product_no" +
//		" from ftp.ftp_qxpp_result_history " +
//        " where br_no is not null " +//�����л���
//                " and to_date(wrk_sys_date,'yyyymmdd')>to_date('"+minDate+"','yyyymmdd')" +
//        		" and to_date(wrk_sys_date,'yyyymmdd')<=to_date('"+maxDate+"','yyyymmdd') " +
//                " and business_no='YW201' and product_no in ("+grhqPrdtNo+")" +
//                " group by br_no,product_no,kmh,rate" +//���˻�������
//        " union all " +
//        "select max(rate), count(*) n,nvl(sum(bal),0.0) bal," +
//        " max(ftp_price),min(ftp_price),max(wrk_sys_date), min(wrk_sys_date),br_no,product_no" +
//        " from ftp.ftp_qxpp_result_history " +
//        " where br_no is not null " +
//                " and to_date(wrk_sys_date,'yyyymmdd')>to_date('"+minDate+"','yyyymmdd')" +
//		        " and to_date(wrk_sys_date,'yyyymmdd')<=to_date('"+maxDate+"','yyyymmdd') " +
//                " and ((business_no='YW201' and product_no in ("+dwhqPrdtNo+")) or product_no in ("+lhqPrdtNo+"))" +
//                " group by br_no,product_no,ac_id";//��λ��������
		
		List hqList = daoFactory.query1(hq_sql, null);
		if(hqList.size() > 0) {
			for(Object object : hqList) {
				Object[] obj = (Object[])object;
				String[] result = new String[6];
				result[0] = obj[0] == null ? "0.0" : obj[0].toString();//����
				//���ޣ���󶨼�ϵͳ����-��С����ϵͳ���ڵ���һϵͳ����
				int n = 0;
				if(obj[6].toString().substring(6,8).equals("28")) {
					n = 8;
				} else if (obj[6].toString().substring(6,8).equals("29")){
					n = 9;
				}else if(obj[6].toString().substring(6,8).equals("31")) {
					n = 11;
				}else {
					n = 10;
				}
				int opnDate = obj[9]==null?0:Integer.valueOf(obj[9].toString());
				int minFtpDate = Integer.valueOf(CommonFunctions.dateModifyD(obj[6].toString(),-n));
				//ȡ�����պ���С����������һ��ϵͳ�������������ĵ�������
				int term = CommonFunctions.daysSubtract(obj[5].toString(), opnDate>minFtpDate?""+opnDate:""+minFtpDate);
				
				result[1] = String.valueOf(term);//����
				Integer count = obj[1] == null ? 0 : Integer.valueOf(obj[1].toString());
				double amt1 = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());
				result[2] = String.valueOf(amt1/count);//���
				double max_ftpPrice = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());
				double min_ftpPrice = obj[4] == null ? 0.0 : Double.valueOf(obj[4].toString());
				result[3] = String.valueOf((max_ftpPrice+min_ftpPrice)/2);//���۽��
				result[4] = String.valueOf(obj[7]);//����
				result[5] = String.valueOf(obj[8]);//��Ʒ
				
				resultList.add(result);
			}
		}
		
		//2.�ٴ����ζ��۵ķǻ��ڴ��(!YW201)
		String dcfhq_sql = "";
		if(tjType.equals("1")) {//����
			dcfhq_sql = "select max(rate), count(*), nvl(sum(amt),0.0) bal," +
			       " max(ftp_price),(case when max(term) <0 then 0 else (days(least(to_date(case when max(mtr_date) is null then '20991231' else t.mtr_date end,'yyyymmdd'), to_date('"+maxDate+"','yyyymmdd')))-days(greatest(to_date(max(opn_date),'yyyymmdd'), to_date('"+minDate+"','yyyymmdd')))) end) term," +
			       " br_no,product_no " +
			       " from ftp.ftp_qxpp_result " +
			       " where business_no!='YW201' and business_no!='YW203' and product_no in ("+prdtNo+") and product_no  not in ("+lhqPrdtNo+")  " +
			       " and br_no is not null " +
			       " and to_date(mtr_date,'yyyymmdd') > to_date('"+minDate+"','yyyymmdd') " +
			       " and to_date(Opn_date,'yyyymmdd') <= to_date('"+maxDate+"','yyyymmdd') "+
	                " group by  br_no, product_no,ac_id ";
//	               " union all " +
//	               "select max(rate), count(*), nvl(sum(bal),0.0) bal," +
//			       " max(ftp_price),(case when max(term) <0 then 0 " +
//			       " when max(term) is null then 0 else (days(least(to_date(max(mtr_date),'yyyymmdd'), to_date('"+maxDate+"','yyyymmdd')))-days(greatest(to_date(max(opn_date),'yyyymmdd'), to_date('"+minDate+"','yyyymmdd')))) end) term," +
//			       " br_no,product_no " +
//			       " from ftp.ftp_qxpp_result_history " +
//			       " where business_no!='YW201' and product_no in ("+prdtNo+") and product_no not in ("+lhqPrdtNo+") " +
//			       " and br_no is not null " +
//			       " and to_date(Opn_date,'yyyymmdd') > to_date('"+minDate+"','yyyymmdd') " +
//			       " and to_date(Opn_date,'yyyymmdd') <= to_date('"+maxDate+"','yyyymmdd') "+
//	               " group by  br_no, product_no,ac_id ";
		}else {
			//Ŀǰȡ�����޲���ʵ�ʵ����ޣ����ǿ���ʱ����ڵ���Чʱ���(case when t.term < 0 then 0 when t.term is null then 0 else t.term end)
			dcfhq_sql = " select * from (select t.rate,1,(case when t.now_bal is null then t.bal else t.now_bal end) now_bal," +
					"(case when ((case when NOW_FIV_STS is null then fiv_sts else NOW_FIV_STS end) not in ('03','04','05') and t.business_no='YW101') OR t.business_no!='YW101' then t.ftp_price else t.rate end) as ftp_price, " +
					"(case when t.term <0 then 0 else (days(least(to_date(case when t.mtr_date is null then '20991231' else t.mtr_date end,'yyyymmdd'), to_date('"+maxDate+"','yyyymmdd')))-days(greatest(to_date(t.opn_date,'yyyymmdd'), to_date('"+minDate+"','yyyymmdd')))) end) term," +
			        " t.br_no,t.product_no, row_number() over(partition by ac_id order by wrk_sys_date desc ) rn " +
	                " from ftp.ftp_qxpp_result t" +
	                " where t.br_no is not null " +
				    " and to_date(t.mtr_date,'yyyymmdd') > to_date('"+minDate+"','yyyymmdd') " +
			        " and to_date(t.Opn_date,'yyyymmdd') <= to_date('"+maxDate+"','yyyymmdd')" +
			       	" and t.business_no!='YW201' and t.business_no!='YW203'" +
			       	" and t.product_no in ("+prdtNo+") and t.product_no not in ("+lhqPrdtNo+") )" +
	                " where rn=1 and now_bal!=0 ";
//	                "union all  select * from (select t.rate,1,t.now_bal,t.ftp_price,(case when t.term < 0 then 0 when t.term is null then 0 else t.term end)," +
//					" t.br_no,t.product_no, row_number() over(partition by ac_id order by wrk_time desc ) rn " +
//	                " from ftp.ftp_qxpp_result_history t" +
//	                " where t.br_no is not null " +
//			        " and to_date(t.Opn_date,'yyyymmdd') <= to_date('"+maxDate+"','yyyymmdd')" +
//			       	" and t.business_no!='YW201'" +
//			       	" and t.product_no in ("+prdtNo+") and product_no not in ("+lhqPrdtNo+") )" +
//	                " where rn=1 and now_bal!=0";
		}
		
		List dcfhqList = daoFactory.query1(dcfhq_sql, null);
		if(dcfhqList.size() > 0) {
			for(Object object : dcfhqList) {
				Object[] obj = (Object[])object;
				String[] result = new String[6];
				result[0] = obj[0] == null ? "0.0" : obj[0].toString();//����
				result[1] = Double.valueOf(obj[4].toString()) < 0 ? "0" : obj[4].toString();//����
				Integer count = obj[1] == null ? 0 : Integer.valueOf(obj[1].toString());
				double amt1 = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());
				result[2] = String.valueOf(amt1/count);//���
				result[3] = obj[3] == null ? "0.0" : obj[3].toString();
				result[4] = String.valueOf(obj[5]);//����
				result[5] = String.valueOf(obj[6]);//��Ʒ
				resultList.add(result);
			}
		}
		
			/*ʵʱ����
			 * 1.�ȴ����ζ��۵Ļ��ڴ��
			 * String hq_sql = "select t.rate,nvl(t.bal,0) bal,t.ftp_price,t.br_no,t.product_no from ftp.ftp_qxpp_result t" +
					" where t.br_no is not null and business_no='YW201' and t.product_no in ("+gehqPrdtNo+","+dwhqPrdtNo+")) " +
					" where rn=1 and bal!=0 " +
					" union all " +
					" select * from (select t.rate,nvl(t.bal,0) bal,t.ftp_price,t.br_no,t.product_no, row_number() over(partition by ac_id order by wrk_time desc ) rn " +
					" from ftp.ftp_qxpp_result t" +
					" where t.br_no is not null and business_no='YW201' and t.product_no in ("+dwhqPrdtNo+")) " +
					" where rn=1 and bal!=0";
			   2.�ٴ����ζ��۵ķǻ��ڴ��(!YW201)
			   String dcfhq_sql = " select * from (select t.rate,(case when term < 0 then 0 " +
					" when term is null then 0 else term end),nvl(t.bal,0) bal,t.ftp_price,t.br_no,t.product_no, row_number() over(partition by ac_id order by wrk_time desc ) rn " +
			        " from ftp.ftp_qxpp_result t" +
			        " where t.br_no is not null and business_no!='YW201' and t.product_no in ("+prdtNo+")) " +
			        " where rn=1 and bal!=0";
            */

		//3.������ζ��۵ķǻ��ڴ��(!YW201)����Ҫ�������ac_id����ѭ��ÿ��ac_id��ѯ�����дζ��۽���󵥶��ֶμ���
		//#### ��ʱ������һ����Ŵ���ȡ��ζ����еĽ��ƽ��ֵ��������ʡ����ftp�۸���ȫ��һ�μ��㡣
		////////####
		
		return resultList;
	}
	/**
	 * ��ȡĳ���Ʒ�ļ�Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢�����--����ƥ��
	 * @param datesql
	 * @param brSql
	 * @param prdtNo
	 * @param ye �����
	 * @param countTerm ���۴���
	 * @param date2 ʱ�����˵�
	 * @param dateʱ����Ҷ˵�
	 * @return ��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢�����
	 */
	public double[] getQxppValue(String brSql, String prdtNo, String date2, String date) {
		double[] returnValue = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		double amt = 0.0, rate_fz = 0.0, ftpPrice_fz = 0.0, term_fz = 0.0, lxAmt = 0.0, zyAmt = 0.0;

		//1.�ȴ����ζ��۵Ļ��ڴ��
		//���ж�Ҫ����Ĳ�Ʒ���Ƿ����⼸���Ʒ
		StringBuffer gehqPrdtNo = new StringBuffer();//���˻��������Ʒ���
		if(prdtNo.indexOf("P2010") != -1) gehqPrdtNo.append("'P2010'" + ",");
		if(prdtNo.indexOf("P2038") != -1) gehqPrdtNo.append("'P2038'" + ",");
		if(prdtNo.indexOf("P2056") != -1) gehqPrdtNo.append("'P2056'" + ",");
		StringBuffer dwhqPrdtNo = new StringBuffer();//��λ���������Ʒ���
		if(prdtNo.indexOf("P2001") != -1) dwhqPrdtNo.append("'P2001'" + ",");
		if(prdtNo.indexOf("P2055") != -1) dwhqPrdtNo.append("'P2055'" + ",");
		if(prdtNo.indexOf("P2031") != -1) dwhqPrdtNo.append("'P2031'" + ",");
		
		if(!gehqPrdtNo.toString().equals("") || !dwhqPrdtNo.toString().equals("")) {
			if(!gehqPrdtNo.toString().equals(""))gehqPrdtNo.deleteCharAt(gehqPrdtNo.length()-1);
			else gehqPrdtNo.append("''");
			if(!dwhqPrdtNo.toString().equals(""))dwhqPrdtNo.deleteCharAt(dwhqPrdtNo.length()-1);
			else dwhqPrdtNo.append("''");
			String hq_sql = "select * from ((select rate, count(*) n,nvl(sum(case when t.amt is null then t.bal else t.amt end),0.0) amt," +
					"max(t.ftp_price),min(t.ftp_price),max(t.wrk_sys_date), min(t.wrk_sys_date) from ftp.ftp_qxpp_result t " +
			   "where t.br_no "+brSql+" and t.wrk_sys_date>'"+date2+"' and t.wrk_sys_date<='"+date+"' " +
			   "and t.business_no='YW201' and t.product_no in ("+gehqPrdtNo.toString()+") group by t.br_no,t.kmh,t.rate)" +//���˻�������
			   "union all " +
			   "(select max(rate), count(*) n,nvl(sum(case when t.amt is null then t.bal else t.amt end),0.0) amt," +
			   "max(t.ftp_price),min(t.ftp_price),max(t.wrk_sys_date), min(t.wrk_sys_date) from ftp.ftp_qxpp_result t " +
			   "where t.br_no  "+brSql+" and t.wrk_sys_date>'"+date2+"' and t.wrk_sys_date<='"+date+"' " +
			   "and t.business_no='YW201' and t.product_no in ("+dwhqPrdtNo.toString()+") group by t.br_no,t.ac_id))";//��λ��������

			List hqList = daoFactory.query1(hq_sql, null);
			
			if(hqList.size() > 0) {
				for(Object object : hqList) {
					Object[] obj = (Object[])object;
					double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
					Integer count = obj[1] == null ? 0 : Integer.valueOf(obj[1].toString());
					double amt1 = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());
					double max_ftpPrice = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());
					double min_ftpPrice = obj[4] == null ? 0.0 : Double.valueOf(obj[4].toString());
					//���ޣ���󶨼�ϵͳ����-��С����ϵͳ���ڵ���һϵͳ����
					int term = CommonFunctions.daysSubtract(obj[5].toString(), CommonFunctions.dateModifyD(obj[6].toString(),-10));
					amt += amt1/count;//�����
					rate_fz += rate*amt1/count;//��Ȩƽ�����ʼ���ķ���
					term_fz += term*amt1/count;//��Ȩƽ�����޼���ķ���
					ftpPrice_fz += (max_ftpPrice+min_ftpPrice)/2*amt1/count;//��Ȩƽ��ת�Ƽ۸����ķ���
					lxAmt += amt1/count*rate*term/365;//��Ϣ����/��Ϣ֧��=���*����*����/365
					zyAmt += amt1/count*(max_ftpPrice+min_ftpPrice)/2*term/365;//�ʲ�ת��֧��/��ծת������=���*ת�Ƽ۸�*����/365
				}
			}
		}
		//2.�ٴ����ζ��۵ķǻ��ڴ��(!YW201)
		String dcfhq_sql = "select max(rate), count(*), nvl(sum(case when t.amt is null then t.bal else t.amt end),0.0) amt," +
				"max(t.ftp_price),(case when max(t.term) <0 then 0 " +
				   "when max(t.term) is null then 0 else (days(least(to_date(max(mtr_date),'yyyymmdd'), to_date('"+date+"','yyyymmdd')))-days(greatest(to_date(max(opn_date),'yyyymmdd'), to_date('"+date2+"','yyyymmdd')))) end) term from ftp.ftp_qxpp_result t " +
				"where t.business_no!='YW201' and t.product_no in ("+prdtNo+") and  br_no "+brSql+"" +
				" and (t.Opn_date <= '"+date+"' " +
                "and t.Opn_date > '"+date2+"') group by  br_no,ac_id ";
                //+" having count(*)=1";
		List dcfhqList = daoFactory.query1(dcfhq_sql, null);
		if(dcfhqList.size() > 0) {
			for(Object object : dcfhqList) {
				Object[] obj = (Object[])object;
				double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
				Integer count = obj[1] == null ? 0 : Integer.valueOf(obj[1].toString());
				double amt1 = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());
				double ftpPrice = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());
				double term = obj[4] == null ? 0.0 : Double.valueOf(obj[4].toString());
				amt += amt1/count;//�����
				rate_fz += rate*amt1/count;//��Ȩƽ�����ʼ���ķ���
				term_fz += term*amt1/count;//��Ȩƽ�����޼���ķ���
				ftpPrice_fz += ftpPrice*amt1/count;//��Ȩƽ��ת�Ƽ۸����ķ���
				lxAmt += amt1/count*rate*term/365;//��Ϣ����/��Ϣ֧��=���*����*����/365
				zyAmt += amt1/count*ftpPrice*term/365;//�ʲ�ת��֧��/��ծת������=���*ת�Ƽ۸�*����/365
			}
		}
		
		//3.������ζ��۵ķǻ��ڴ��(!YW201)����Ҫ�������ac_id����ѭ��ÿ��ac_id��ѯ�����дζ��۽���󵥶��ֶμ���
		//#### ��ʱ������һ����Ŵ���ȡ��ζ����еĽ��ƽ��ֵ��������ʡ����ftp�۸���ȫ��һ�μ��㡣
		////////####
		
		returnValue[0] = rate_fz/amt;//��Ȩƽ������
		returnValue[1] = CommonFunctions.doublecut(term_fz/amt, 1);//��Ȩƽ������
		returnValue[2] = ftpPrice_fz/amt;//��Ȩƽ��ת�Ƽ۸�
		returnValue[3] = lxAmt;//��Ϣ����/��Ϣ֧��
		returnValue[4] = zyAmt;//�ʲ�ת��֧��/��ծת������
		returnValue[5] = amt;//�����
		return returnValue;
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
			if(prdtNo.indexOf(result[5]) != -1) {
				double rate = Double.valueOf(result[0]);
				double term = Double.valueOf(result[1]);
				double amt1 = Double.valueOf(result[2]);
				double ftpPrice = Double.valueOf(result[3]);
				amt += amt1;//�����
				rate_fz += rate*amt1;//��Ȩƽ�����ʼ���ķ���
				ftpPrice_fz += ftpPrice*amt1;//��Ȩƽ��ת�Ƽ۸����ķ���
				if(term != -1) {//�������������޲�����
					term_fz += term*amt1;//��Ȩƽ�����޼���ķ���
					lxAmt += amt1*rate*term/365;//��Ϣ����/��Ϣ֧��=���*����*����/365
					zyAmt += amt1*ftpPrice*term/365;//�ʲ�ת��֧��/��ծת������=���*ת�Ƽ۸�*����/365
				}
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
	 * @param prdtNo
	 * @param brNosҪ���ܵĻ����ַ�������'1801031009','1801031008'
	 * @return ��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢�����
	 * @throws ParseException 
	 */
	public double[] getQxppValueByBrNo(List<String[]> ftpResultList, String prdtNo, String brNos) {
		double[] returnValue = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		double amt = 0.0, rate_fz = 0.0, ftpPrice_fz = 0.0, term_fz = 0.0, lxAmt = 0.0, zyAmt = 0.0;
		for (String[] result : ftpResultList) {
			if(prdtNo.indexOf(result[5]) != -1 && (brNos.indexOf(result[4])!= -1|| brNos.equals("is not null"))) {
				double rate = Double.valueOf(result[0]);
				double term = Double.valueOf(result[1]);
				double amt1 = Double.valueOf(result[2]);
				double ftpPrice = Double.valueOf(result[3]);
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
	 * ������ӯ������-----������ϸ��ѯ����ftpResultList�е����ݣ����ջ����ŵ���Ӧ��map��
	 * @param ftped_data_successList
	 * @param brMstList Ҫ��ȡ����ֵ������ֱ����ʾ�Ļ������ݱ�����б�(2��1��0��������֧��)
	 * @return map
	 */
	public Map<String,Double[]> getQxppValueMap_jgzylfx(List<String[]> ftpResultList, List<BrMst> brMstList) {
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> amt_map=new HashMap<String,Double>();
		Map<String,Double> rate_fz_map=new HashMap<String,Double>();
		Map<String,Double> ftpPrice_fz_map=new HashMap<String,Double>();
		Map<String,Double> term_fz_map=new HashMap<String,Double>();
		Map<String,Double> lxAmt_map=new HashMap<String,Double>();
		Map<String,Double> zyAmt_map=new HashMap<String,Double>();
	
		for (String[] result : ftpResultList) {
			
			double rate = Double.valueOf(result[0]);
			double term = Double.valueOf(result[1]);
			double amt1 = Double.valueOf(result[2]);
			double ftpPrice = Double.valueOf(result[3]);
			//�����
			if(amt_map.get(result[4]+"-"+result[5].substring(1,2))==null){
				amt_map.put(result[4]+"-"+result[5].substring(1,2), amt1);
			}else{
				amt_map.put(result[4]+"-"+result[5].substring(1,2), amt1+amt_map.get(result[4]+"-"+result[5].substring(1,2)));
			}
			//��Ȩƽ�����ʼ���ķ���
			if(rate_fz_map.get(result[4]+"-"+result[5].substring(1,2))==null){
				rate_fz_map.put(result[4]+"-"+result[5].substring(1,2), rate*amt1);
			}else{
				rate_fz_map.put(result[4]+"-"+result[5].substring(1,2), rate*amt1+rate_fz_map.get(result[4]+"-"+result[5].substring(1,2)));
			}
			
			//��Ȩƽ�����޼���ķ���
			if(term_fz_map.get(result[4]+"-"+result[5].substring(1,2))==null){
				term_fz_map.put(result[4]+"-"+result[5].substring(1,2), term*amt1);
			}else{
				term_fz_map.put(result[4]+"-"+result[5].substring(1,2), term*amt1+term_fz_map.get(result[4]+"-"+result[5].substring(1,2)));
			}
			//��Ȩƽ��ת�Ƽ۸����ķ���
			if(ftpPrice_fz_map.get(result[4]+"-"+result[5].substring(1,2))==null){
				ftpPrice_fz_map.put(result[4]+"-"+result[5].substring(1,2), ftpPrice*amt1);
			}else{
				ftpPrice_fz_map.put(result[4]+"-"+result[5].substring(1,2), ftpPrice*amt1+ftpPrice_fz_map.get(result[4]+"-"+result[5].substring(1,2)));
			}
			//��Ϣ����/��Ϣ֧��=���*����*����/365
			if(lxAmt_map.get(result[4]+"-"+result[5].substring(1,2))==null){
				lxAmt_map.put(result[4]+"-"+result[5].substring(1,2),amt1*rate*term/365);
			}else{
				lxAmt_map.put(result[4]+"-"+result[5].substring(1,2), amt1*rate*term/365+lxAmt_map.get(result[4]+"-"+result[5].substring(1,2)));
			}
			//�ʲ�ת��֧��/��ծת������=���*ת�Ƽ۸�*����/365
			if(zyAmt_map.get(result[4]+"-"+result[5].substring(1,2))==null){
				zyAmt_map.put(result[4]+"-"+result[5].substring(1,2),amt1*ftpPrice*term/365);
			}else{
				zyAmt_map.put(result[4]+"-"+result[5].substring(1,2), amt1*ftpPrice*term/365+zyAmt_map.get(result[4]+"-"+result[5].substring(1,2)));
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
	public Map<String,Double[]> getQxppValueMap_ywtxylfx(List<String[]> ftped_data_successList, String xlsBrNo, String brNos) {
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> amt_map=new HashMap<String,Double>();
		Map<String,Double> rate_fz_map=new HashMap<String,Double>();
		Map<String,Double> ftpPrice_fz_map=new HashMap<String,Double>();
		Map<String,Double> term_fz_map=new HashMap<String,Double>();
		Map<String,Double> lxAmt_map=new HashMap<String,Double>();
		Map<String,Double> zyAmt_map=new HashMap<String,Double>();
	
		//����Ʒ��ŷŵ�map��
		for (String[] result : ftped_data_successList) {
			if(brNos.indexOf(result[4])!= -1|| brNos.equals("is not null")) {//���Ҫ���ܵĻ����а���result[4]
				double rate = Double.valueOf(result[0]);
				double term = Double.valueOf(result[1]);
				double amt1 = Double.valueOf(result[2]);
				double ftpPrice = Double.valueOf(result[3]);
				//�����
				if(amt_map.get(result[5])==null){
					amt_map.put(result[5], amt1);
				}else{
					amt_map.put(result[5], amt1+amt_map.get(result[5]));
				}
				if(result[5].equals("P2029")||result[5].equals("P2030")) {
					System.out.println("amt1"+amt1);
				}
				//��Ȩƽ�����ʼ���ķ���
				if(rate_fz_map.get(result[5])==null){
					rate_fz_map.put(result[5], rate*amt1);
				}else{
					rate_fz_map.put(result[5], rate*amt1+rate_fz_map.get(result[5]));
				}
				
				//��Ȩƽ�����޼���ķ���
				if(term_fz_map.get(result[5])==null){
					term_fz_map.put(result[5], term*amt1);
				}else{
					term_fz_map.put(result[5], term*amt1+term_fz_map.get(result[5]));
				}
				//��Ȩƽ��ת�Ƽ۸����ķ���
				if(ftpPrice_fz_map.get(result[5])==null){
					ftpPrice_fz_map.put(result[5], ftpPrice*amt1);
				}else{
					ftpPrice_fz_map.put(result[5], ftpPrice*amt1+ftpPrice_fz_map.get(result[5]));
				}
				//��Ϣ����/��Ϣ֧��=���*����*����/365
				if(lxAmt_map.get(result[5])==null){
					lxAmt_map.put(result[5],amt1*rate*term/365);
				}else{
					lxAmt_map.put(result[5], amt1*rate*term/365+lxAmt_map.get(result[5]));
				}
				//�ʲ�ת��֧��/��ծת������=���*ת�Ƽ۸�*����/365
				if(zyAmt_map.get(result[5])==null){
					zyAmt_map.put(result[5],amt1*ftpPrice*term/365);
				}else{
					zyAmt_map.put(result[5], amt1*ftpPrice*term/365+zyAmt_map.get(result[5]));
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
	 * ����ҵ���Ż�ȡ��Ʒ����
	 * @param businessNo
	 * @return
	 */
	public String[][] getPrdtCtgNos(String businessNo) {
		String[][] prdtCtgNos = null;
		String sql = "select distinct Product_ctg_no, Product_ctg_name from ftp.FTP_business_static_divide where business_no in ("+businessNo+")";
		List list = daoFactory.query1(sql, null);
		if(list != null && list.size() > 0) {
			prdtCtgNos = new String[list.size()][2];
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				prdtCtgNos[i][0] = obj[0].toString();
				prdtCtgNos[i][1] = obj[1].toString();
			}
		}
		return prdtCtgNos;
	}
	
	/**
	 * ����Ʒ���ղ�Ʒ������з���
	 * @return Map <��Ʒ������,'��Ʒ���','��Ʒ���2'...>
	 */
	public Map<String, String> getPrdtCatg() {
		Map<String, String> result = new HashMap<String, String>();
		String sql = "from FtpBusinessStaticDivide order by productCtgNo, productNo";
		List<FtpBusinessStaticDivide> resultList = daoFactory.query(sql, null);
		String lastproductCtgNo = "";
		String prdtNos = "";
		for(int i = 0; i < resultList.size(); i++) {
			FtpBusinessStaticDivide ftpBusinessStaticDivide = (FtpBusinessStaticDivide)resultList.get(i);
			if(i == 0) {
				lastproductCtgNo = ftpBusinessStaticDivide.getProductCtgNo();
				prdtNos = "'"+ftpBusinessStaticDivide.getProductNo()+"'";
			}else {
				if(!lastproductCtgNo.equals(ftpBusinessStaticDivide.getProductCtgNo())){
					result.put(lastproductCtgNo, prdtNos);
				    lastproductCtgNo = ftpBusinessStaticDivide.getProductCtgNo();
					prdtNos = "'"+ftpBusinessStaticDivide.getProductNo()+"'";
				}else {
					prdtNos += ",'"+ftpBusinessStaticDivide.getProductNo()+"'";
				}
			}
		}
		return result;
	}
	public static void main(String[] args) {
		System.out.println();
	}
}
