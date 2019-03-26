//package com.dhcc.ftp.bo;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.dhcc.ftp.entity.BrMst;
//import com.dhcc.ftp.entity.FtpPoolInfo;
//import com.dhcc.ftp.entity.FtpProductMethodRel;
//import com.dhcc.ftp.entity.FtpSystemInitial;
//import com.dhcc.ftp.entity.YlfxReport;
//import com.dhcc.ftp.util.CommonFunctions;
//import com.dhcc.ftp.util.FtpUtil;
//import com.dhcc.ftp.util.LrmUtil;
//
//public class Copy_2_of_ReportBO extends BaseBo {
//
//	/**
//	 * ������ӯ������
//	 * @param request
//	 * @param date
//	 * @param brNo
//	 * @param manageLvl ��������
//	 * @param assessScope ͳ��ά�� ��-1��-3��-12
//	 * @param isMx �Ƿ�鿴��ϸ
//	 * @return
//	 */
//	public List<YlfxReport> brPayOffProfile(HttpServletRequest request, String date, String superBrNo, String brNo, String manageLvl, 
//			Integer assessScope, Integer isMx) {
//		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
//		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// ������
//		System.out.println("�����磺" + xlsBrNo);
//		String prcMode = getPrcModeByBrNo(xlsBrNo, manageLvl);// �����õ��ʽ��
//		if (prcMode == null)
//			return null;
//		request.getSession().setAttribute("prcMode", prcMode);
//		System.out.println("�����õĶ��۲��ԣ�" + prcMode);
//		String prdtNoZc = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "1"); // ��Ӧ�ʽ�ػ�����ƥ�������õ��ʲ����Ʒ
//		String prdtNoFz = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "2"); // ��Ӧ�ʽ�ػ�����ƥ�������õĸ�ծ���Ʒ
//		System.out.println("�ʲ��ʽ�ػ�����ƥ���Ʒ��" + prdtNoZc);
//		System.out.println("��ծ�ʽ�ػ�����ƥ���Ʒ��" + prdtNoFz);
//		if (prdtNoZc == null || prdtNoFz == null)
//			return null;
//		List ftpResultList = null;
//		if(!prcMode.equals("4")) {//�����������ƥ�䷨
//			ftpResultList = this.getFtpResult(date, xlsBrNo, prcMode);//��ȡ��Ӧ������Ķ��۽��list
//		}
//		String date2 = CommonFunctions.dateModifyM(date, assessScope);
//		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//���������꣬��ֻȡ��������ݣ���1�·ݿ�ʼ
//	    	date2 = date2.substring(0,4)+"1231";
//		
//		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//��ȡ��ͳ�Ƶ�����
//	    //��ȡ�����÷������ںͽ������ڵĲ�ѯ����
//		
//		if (isMx == 0) {// ������ǲ鿴�ӻ�����ӯ����������ֱ�Ӳ鿴�û�����ӯ������
//			YlfxReport ylfxReport = new YlfxReport();
//			String brSql = LrmUtil.getBrSql(brNo);
//			ylfxReport.setBrName(brInfoDAO.getInfo(brNo).getBrName());
//			ylfxReport.setBrNo(brNo);
//			ylfxReport.setManageLvl(manageLvl);
//			if(!prcMode.equals("4")) {//��������ƥ��
//				ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// �ʲ����
//				System.out.println("�ʲ����:" + ylfxReport.getZcye());
//				ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoZc, "3", assessScope, true));// ��Ϣ�ʣ���Ȩƽ������
//				System.out.println("��Ϣ��:" + ylfxReport.getSxl());
//				ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//�ʲ�ƽ������
//		        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
//		        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//��ծƽ������
//		        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
//		        ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// ��ծ���
//				System.out.println("��ծ���:" + ylfxReport.getFzye());
//				ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoFz, "3", assessScope, true));// ��Ϣ�ʣ���Ȩƽ������
//				System.out.println("��Ϣ��:" + ylfxReport.getFxl());
//				Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
//				ylfxReport.setZczyjg(resultPrice[0]);// �ʲ�ת�Ƽ۸�
//				System.out.println("�ʲ�ת�Ƽ۸�:" + ylfxReport.getZczyjg());
//				ylfxReport.setFzzyjg(resultPrice[1]);// ��ծת�Ƽ۸�
//				System.out.println("��ծת�Ƽ۸�:" + ylfxReport.getFzzyjg());
//				ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);// ��Ϣ����
//				System.out.println("��Ϣ����:" + ylfxReport.getLxsr());
//				ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);// ��Ϣ֧��
//				System.out.println("��Ϣ֧��:" + ylfxReport.getLxzc());
//				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
//				System.out.println("�ʲ�������:" + ylfxReport.getZcjlc());
//				ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// �ʲ�ת��֧��
//				System.out.println("�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
//				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
//				System.out.println("�ʲ�������:" + ylfxReport.getZcjsr());
//				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
//				System.out.println("��ծ������:" + ylfxReport.getFzjlc());
//				ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// ��ծת������
//				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
//				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// ������
//			}else {
//				//����ƥ�䣺��ȡӯ������ʱ�䷶Χ�ڵĶ������ڴ���
//				//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
//				double[] qxppZcValue = this.getQxppValue(brSql, prdtNoZc, date2, date);
//				ylfxReport.setZcye(qxppZcValue[5]);// �ʲ����
//				System.out.println("�ʲ����:"+ylfxReport.getZcye());
//				ylfxReport.setSxl(qxppZcValue[0]);// ��Ϣ��
//				System.out.println("��Ϣ��:"+ylfxReport.getSxl());
//		        ylfxReport.setZcpjqx(qxppZcValue[1]);//�ʲ�ƽ������
//		        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
//		        ylfxReport.setZczyjg(qxppZcValue[2]);// �ʲ�ת�Ƽ۸�
//				System.out.println("�ʲ�ת�Ƽ۸�:"+ylfxReport.getZczyjg());
//				ylfxReport.setLxsr(qxppZcValue[3]);// ��Ϣ����
//				System.out.println("��Ϣ����:"+ylfxReport.getLxsr());
//				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
//				System.out.println("�ʲ�������:" + ylfxReport.getZcjlc());
//				ylfxReport.setZczyzc(qxppZcValue[4]);//�ʲ�ת��֧��
//				System.out.println("�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
//				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
//				System.out.println("�ʲ�������:" + ylfxReport.getZcjsr());
//				
//				System.out.println("��ծ���:"+ylfxReport.getFzye());
//				double[] qxppFzValue = this.getQxppValue(brSql, prdtNoFz, date2, date);
//				ylfxReport.setFzye(qxppFzValue[5]);// ��ծ���
//				System.out.println("��ծ���:"+ylfxReport.getFzye());
//				ylfxReport.setFxl(qxppFzValue[0]);// ��Ϣ��
//				System.out.println("��Ϣ��:"+ylfxReport.getFxl());
//		        ylfxReport.setFzpjqx(qxppFzValue[1]);//��ծƽ������
//		        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
//		        ylfxReport.setFzzyjg(qxppFzValue[2]);// ��ծת�Ƽ۸�
//				System.out.println("��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
//				ylfxReport.setLxzc(qxppFzValue[3]);// ��Ϣ֧��
//				System.out.println("��Ϣ֧��:"+ylfxReport.getLxzc());
//				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
//				System.out.println("��ծ������:" + ylfxReport.getFzjlc());
//				ylfxReport.setFzzysr(qxppFzValue[4]);//��ծת��֧��
//				System.out.println("��ծת��֧��:" + ylfxReport.getZczyzc());
//				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
//				System.out.println("��ծ������:" + ylfxReport.getZcjsr());
//				
//				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// ������
//			}
//			ylfxReportList.add(ylfxReport);
//		} else {
//			String hsql = "from BrMst where superBrNo = '" + brNo + "' order by brNo";// ��ȡ�û����������¼�����
//			List<BrMst> list = daoFactory.query(hsql, null);
//			for (int i = 0; i < list.size(); i++) {
//				BrMst brMst = list.get(i);
//				String brSql = LrmUtil.getBrSql(brMst.getBrNo());
//				YlfxReport ylfxReport = new YlfxReport();
//				ylfxReport.setBrName(brMst.getBrName());
//				ylfxReport.setBrNo(brMst.getBrNo());
//				ylfxReport.setManageLvl(brMst.getManageLvl());
//				if(!prcMode.equals("4")) {//��������ƥ��
//					ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// �ʲ����
//					System.out.println("�ʲ����:" + ylfxReport.getZcye());
//					ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoZc, "3", assessScope, true));// ��Ϣ�ʣ���Ȩƽ������
//					System.out.println("��Ϣ��:" + ylfxReport.getSxl());
//					ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// ��ծ���
//					System.out.println("��ծ���:" + ylfxReport.getFzye());
//					ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoFz, "3", assessScope, true));// ��Ϣ�ʣ���Ȩƽ������
//					System.out.println("��Ϣ��:" + ylfxReport.getFxl());
//					ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//�ʲ�ƽ������
//			        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
//			        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//��ծƽ������
//			        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
//			        Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
//					ylfxReport.setZczyjg(resultPrice[0]);// �ʲ�ת�Ƽ۸�
//					System.out.println("�ʲ�ת�Ƽ۸�:" + ylfxReport.getZczyjg());
//					ylfxReport.setFzzyjg(resultPrice[1]);// ��ծת�Ƽ۸�
//					System.out.println("��ծת�Ƽ۸�:" + ylfxReport.getFzzyjg());
//					ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);// ��Ϣ����
//					System.out.println("��Ϣ����:" + ylfxReport.getLxsr());
//					ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);// ��Ϣ֧��
//					System.out.println("��Ϣ֧��:" + ylfxReport.getLxzc());
//					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
//					ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// �ʲ�ת��֧��
//					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
//					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
//					ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// ��ծת������
//					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
//					ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// ������
//				}else {					
//					//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
//					double[] qxppZcValue = this.getQxppValue(brSql, prdtNoZc, date2, date);
//					ylfxReport.setZcye(qxppZcValue[5]);// �ʲ����
//					System.out.println("�ʲ����:"+ylfxReport.getZcye());
//					ylfxReport.setSxl(qxppZcValue[0]);// ��Ϣ��
//					System.out.println("��Ϣ��:"+ylfxReport.getSxl());
//			        ylfxReport.setZcpjqx(qxppZcValue[1]);//�ʲ�ƽ������
//			        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
//			        ylfxReport.setZczyjg(qxppZcValue[2]);// �ʲ�ת�Ƽ۸�
//					System.out.println("�ʲ�ת�Ƽ۸�:"+ylfxReport.getZczyjg());
//					ylfxReport.setLxsr(qxppZcValue[3]);// ��Ϣ����
//					System.out.println("��Ϣ����:"+ylfxReport.getLxsr());
//					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
//					System.out.println("�ʲ�������:" + ylfxReport.getZcjlc());
//					ylfxReport.setZczyzc(qxppZcValue[4]);//�ʲ�ת��֧��
//					System.out.println("�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
//					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
//					System.out.println("�ʲ�������:" + ylfxReport.getZcjsr());
//					
//					//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
//					double[] qxppFzValue = this.getQxppValue(brSql, prdtNoFz, date2, date);
//					ylfxReport.setFzye(qxppFzValue[5]);// ��ծ���
//					System.out.println("��ծ���:"+ylfxReport.getFzye());
//					ylfxReport.setFxl(qxppFzValue[0]);// ��Ϣ��
//					System.out.println("��Ϣ��:"+ylfxReport.getFxl());
//			        ylfxReport.setFzpjqx(qxppFzValue[1]);//��ծƽ������
//			        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
//			        ylfxReport.setFzzyjg(qxppFzValue[2]);// ��ծת�Ƽ۸�
//					System.out.println("��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
//					ylfxReport.setLxzc(qxppFzValue[3]);// ��Ϣ֧��
//					System.out.println("��Ϣ֧��:"+ylfxReport.getLxzc());
//					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
//					System.out.println("��ծ������:" + ylfxReport.getFzjlc());
//					ylfxReport.setFzzysr(qxppFzValue[4]);//��ծת��֧��
//					System.out.println("��ծת��֧��:" + ylfxReport.getZczyzc());
//					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
//					System.out.println("��ծ������:" + ylfxReport.getZcjsr());
//					
//					ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// ������
//				}
//				
//				ylfxReportList.add(ylfxReport);
//			}
//		}
//		System.out.println("????????????????????");
//		return ylfxReportList;
//	}
//
//	/**
//	 * ҵ������ӯ������--- ����ҵ�����͵����ݻ�ȡ
//	 * @param request
//	 * @param date
//	 * @param brNo
//	 * @param manageLvl
//	 * @param assessScope
//	 * @return
//	 */
//	public List<YlfxReport> busPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope) {
//		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
//		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// ������
//		System.out.println("�����磺" + xlsBrNo);
//		String prcMode = getPrcModeByBrNo(xlsBrNo, manageLvl);// �����õ��ʽ��
//		if (prcMode == null) return null;
//		System.out.println("�����õ��ʽ�أ�" + prcMode);
//		request.getSession().setAttribute("prcMode", prcMode);
//		List<FtpPoolInfo> ftpPoolInfoList = new ArrayList<FtpPoolInfo>();
//		if(!prcMode.equals("4")) {//�����������ƥ�䷨�����ȡ��Ӧ�Ķ��۲��������õ��ʽ��
//			ftpPoolInfoList = this.getFtpPoolInfoList(xlsBrNo, prcMode);// ��Ӧ�Ķ��۲����������ʽ��
//			if (ftpPoolInfoList == null || ftpPoolInfoList.size() < 1) return null;
//		}
//		String[] businessNo = new String[14];//��Ʒ����Ӧ��ҵ����ftp_product_method_rel��
//		// ����
//		businessNo[0]  = "'YW101'";
//		// ����������п���
//		businessNo[1] = "'YW102'";
//		// ���ͬҵ
//		businessNo[2] = "'YW103'";
//		// ���ͬҵ
//		businessNo[3] = "'YW104'";
//		// Ͷ��ҵ��
//		businessNo[4] = "'YW105'";
//		// ���뷷��
//		businessNo[5] = "'YW106'";
//		// �ֽ�
//		businessNo[6] = "'YW107'";
//		// �����ʲ�
//		businessNo[7] = "'YW108'";
//		// ���
//		businessNo[8]  = "'YW201','YW202','YW203','YW204'";
//		// ͬҵ����
//		businessNo[9] = "'YW205'";
//		// ���������н��
//		businessNo[10] = "'YW206'";
//		// ���ָ�ծ
//		businessNo[11] = "'YW207'";
//		// �����ع�
//		businessNo[12] = "'YW208'";
//		// ������ծ
//		businessNo[13] = "'YW209'";
//		
//		String brSql = LrmUtil.getBrSql(brNo);
//		
//		ylfxReportList = this.getYlfxReportList(brSql, xlsBrNo, date, prcMode, assessScope, businessNo, ftpPoolInfoList);
//		
//		return ylfxReportList;
//	}
//	
//	/**
//	 * ҵ������ӯ������---ĳһ��ҵ��(Ŀǰֻ֧�֣������Ͷ��ҵ��)�����в�Ʒӯ������ ���ݻ�ȡ
//	 * @param request
//	 * @param date
//	 * @param brNo
//	 * @param manageLvl
//	 * @param assessScope
//	 * @param prdtType
//	 * @return
//	 */
//	public List<YlfxReport> prdtPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope, String prdtType) {
//		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
//		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// ������
//		System.out.println("�����磺" + xlsBrNo);
//		String prcMode = getPrcModeByBrNo(xlsBrNo, manageLvl);// �����õ��ʽ��
//		if (prcMode == null) return null;
//		System.out.println("�����õ��ʽ�أ�" + prcMode);
//		request.getSession().setAttribute("prcMode", prcMode);
//		List<FtpPoolInfo> ftpPoolInfoList = new ArrayList<FtpPoolInfo>();
//		if(!prcMode.equals("4")) {//�����������ƥ�䷨�����ȡ��Ӧ�Ķ��۲��������õ��ʽ��
//			ftpPoolInfoList = this.getFtpPoolInfoList(xlsBrNo, prcMode);// ��Ӧ�Ķ��۲����������ʽ��
//			if (ftpPoolInfoList == null || ftpPoolInfoList.size() < 1) return null;
//		}
//		String[][] prdtNo = null;//��Ʒ���+��Ʒ����
//		if(prdtType.equals("ck")) {//���
//			prdtNo = new String[19][2];
//			prdtNo[0][0] = "'P2010','P2038'";
//			prdtNo[0][1] = "���˻��ڴ��";
//			prdtNo[1][0] = "'P2011','P2012','P2039','P2040'";
//			prdtNo[1][1] = "���˶��ڴ��1��������(����1��)";
//			prdtNo[2][0] = "'P2013','P2014','P2017','P2020','P2023','P2028','P2041','P2043','P2046','P2048','P2051'";
//			prdtNo[2][1] = "���˶��ڴ��1-2����";
//			prdtNo[3][0] = "'P2015','P2016','P2018','P2019','P2021','P2022','P2024','P2025','P2029','P2030','P2042','P2044','P2045','P2047','P2049','P2050','P2052','P2053'";
//			prdtNo[3][1] = "���˶��ڴ��3-5����";
//			prdtNo[4][0] = "'P2056'";
//			prdtNo[4][1] = "�������ÿ����";
//			prdtNo[5][0] = "'P2026','P2054'";
//			prdtNo[5][1] = "���˶���������";
//			prdtNo[6][0] = "'P2027'";
//			prdtNo[6][1] = "����֪ͨ���";
//			prdtNo[7][0] = "'P2001','P2031'";
//			prdtNo[7][1] = "��λ���ڴ��";
//			prdtNo[8][0] = "'P2002','P2003','P2032','P2033'";
//			prdtNo[8][1] = "��λ���ڴ��1������(����1��)";
//			prdtNo[9][0] = "'P2004','P2005','P2034','P2035'";
//			prdtNo[9][1] = "��λ���ڴ��1-2����";
//			prdtNo[10][0] = "'P2006','P2007','P2036','P2037'";
//			prdtNo[10][1] = "��λ���ڴ��3-5����";
//			prdtNo[11][0] = "'P2057'";
//			prdtNo[11][1] = "�����Դ��";
//			prdtNo[12][0] = "'P2059','P2060','P2061'";
//			prdtNo[12][1] = "Ӧ��������������Ʊ";
//			prdtNo[13][0] = "'P2062'";
//			prdtNo[13][1] = "��֤����";
//			prdtNo[14][0] = "'P2055'";
//			prdtNo[14][1] = "��λ���ÿ����";
//			prdtNo[15][0] = "'P2008'";
//			prdtNo[15][1] = "��λЭ����";
//			prdtNo[16][0] = "'P2009'";
//			prdtNo[16][1] = "��λ֪ͨ���";
//			prdtNo[17][0] = "'P2065'";
//			prdtNo[17][1] = "ͬҵ��ſ���";
//			prdtNo[18][0] = "'P2066'";
//			prdtNo[18][1] = "ϵͳ�ڴ�ſ���";
//		}else if(prdtType.equals("dk")) {//���
//			prdtNo = new String[23][2];
//			prdtNo[0][0] = "'P1018','P1023','P1028','P1033','P1038','P1043'";
//			prdtNo[0][1] = "ũ������6����(��)����";
//			prdtNo[1][0] = "'P1019','P1024','P1029','P1034','P1039','P1044'";
//			prdtNo[1][1] = "ũ������6����-1��(��1��)";
//			prdtNo[2][0] = "'P1020','P1025','P1030','P1035','P1040','P1045'";
//			prdtNo[2][1] = "ũ������1��-3��(��3��)";
//			prdtNo[3][0] = "'P1021','P1026','P1031','P1036','P1041','P1046'";
//			prdtNo[3][1] = "ũ������3��-5��(��5��)";
//			prdtNo[4][0] = "'P1022','P1027','P1032','P1037','P1042','P1047'";
//			prdtNo[4][1] = "ũ������5������(����5��)";
//			prdtNo[5][0] = "'P1048','P1053'";
//			prdtNo[5][1] = "ũ�徭����֯����6����(��)����";
//			prdtNo[6][0] = "'P1049','P1054'";
//			prdtNo[6][1] = "ũ�徭����֯����6����-1��(��1��)";
//			prdtNo[7][0] = "'P1050','P1055'";
//			prdtNo[7][1] = "ũ�徭����֯����1��-3��(��3��)";
//			prdtNo[8][0] = "'P1051','P1056'";
//			prdtNo[8][1] = "ũ�徭����֯����3��-5��(��5��)";
//			prdtNo[9][0] = "'P1052','P1057'";
//			prdtNo[9][1] = "ũ�徭����֯����5������(����5��)";
//			prdtNo[10][0] = "'P1058','P1063','P1068'";
//			prdtNo[10][1] = "ũ����ҵ����6����(��)����";
//			prdtNo[11][0] = "'P1059','P1064','P1069'";
//			prdtNo[11][1] = "ũ����ҵ����6����-1��(��1��)";
//			prdtNo[12][0] = "'P1060','P1065','P1070'";
//			prdtNo[12][1] = "ũ����ҵ����1��-3��(��3��)";
//			prdtNo[13][0] = "'P1061','P1066','P1071'";
//			prdtNo[13][1] = "ũ����ҵ����3��-5��(��5��)";
//			prdtNo[14][0] = "'P1062','P1067','P1072'";
//			prdtNo[14][1] = "ũ����ҵ����5������(����5��)";
//			prdtNo[15][0] = "'P1073','P1078','P1083','P1088','P1093','P1098'";
//			prdtNo[15][1] = "��ũ����6����(��)����";
//			prdtNo[16][0] = "'P1074','P1079','P1084','P1089','P1094','P1099'";
//			prdtNo[16][1] = "��ũ����6����-1��(��1��)";
//			prdtNo[17][0] = "'P1075','P1080','P1085','P1090','P1095','P1100'";
//			prdtNo[17][1] = "��ũ����1��-3��(��3��)";
//			prdtNo[18][0] = "'P1076','P1081','P1086','P1091','P1096','P1101'";
//			prdtNo[18][1] = "��ũ����3��-5��(��5��)";
//			prdtNo[19][0] = "'P1077','P1082','P1087','P1092','P1097','P1102'";
//			prdtNo[19][1] = "��ũ����5������(����5��)";
//			prdtNo[20][0] = "'P1103'";
//			prdtNo[20][1] = "���ÿ�͸֧";
//			prdtNo[21][0] = "'P1104'";
//			prdtNo[21][1] = "�����ʲ�";
//			prdtNo[22][0] = "'P1106'";
//			prdtNo[22][1] = "���";
//		}else if(prdtType.equals("tzyw")) {//Ͷ��ҵ��
//			prdtNo = new String[4][2]; 
//			prdtNo[0][0] = "'P1011'";
//			prdtNo[0][1] = "�����Խ����ʲ�";
//			prdtNo[1][0] = "'P1114'";
//			prdtNo[1][1] = "����������Ͷ��";
//			prdtNo[2][0] = "'P1116'";
//			prdtNo[2][1] = "�ɹ����۽����ʲ�";
//			prdtNo[3][0] = "'P1123'";
//			prdtNo[3][1] = "Ӧ�տ�����Ͷ��";
//		}
//		String brSql = LrmUtil.getBrSql(brNo);
//		ylfxReportList = this.getYlfxReportListByPrdtNo(brSql, xlsBrNo, date, prcMode, assessScope, prdtNo, ftpPoolInfoList);
//		
//		return ylfxReportList;
//	}
//	
//	/**
//	 * ����ӯ������
//	 * @param request
//	 * @param date
//	 * @param brNo ����Ա��������
//	 * @param manageLvl �����ļ���
//	 * @param brCountLvl ����ͳ�Ƽ���
//	 * @param assessScope ͳ��ά��
//	 * @return
//	 */
//	public List<YlfxReport> brPayOffRanking(HttpServletRequest request, String date, String brNo, String manageLvl, String brCountLvl, Integer assessScope) {
//		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
//		String xlsBrNo = brNo;// ������
//		System.out.println("�����磺" + xlsBrNo);
//		String prcMode = getPrcModeByBrNo(xlsBrNo, manageLvl);// �����õ��ʽ��
//		if (prcMode == null)
//			return null;
//		System.out.println("�����õ��ʽ�أ�" + prcMode);
//		request.getSession().setAttribute("prcMode", prcMode);
//		String prdtNoZc = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "1"); // ��Ӧ�ʽ�������õ��ʲ����Ʒ
//		String prdtNoFz = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "2"); // ��Ӧ�ʽ�������õĸ�ծ���Ʒ
//		System.out.println("�ʲ��ʽ�ز�Ʒ��" + prdtNoZc);
//		System.out.println("��ծ�ʽ�ز�Ʒ��" + prdtNoFz);
//		if (prdtNoZc == null || prdtNoFz == null)
//			return null;
//		List ftpResultList = null;
//		if(!prcMode.equals("4")) {//�����������ƥ�䷨
//			ftpResultList = this.getFtpResult(date, xlsBrNo, prcMode);//��ȡ��Ӧ������Ķ��۽��list
//		}
//		//���ݻ��������ȡ���������¸ü����µ����л���
//		List<BrMst> brMstList = getBrMstList(brNo, brCountLvl);
//		String date2 = CommonFunctions.dateModifyM(date, assessScope);
//		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//���������꣬��ֻȡ��������ݣ���1�·ݿ�ʼ
//	    	date2 = date2.substring(0,4)+"1231";
//		
//		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//��ȡ��ͳ�Ƶ�����
//		for(BrMst brMst : brMstList) {
//			String brSql = LrmUtil.getBrSql(brMst.getBrNo());
//			YlfxReport ylfxReport = new YlfxReport();
//			ylfxReport.setBrName(brMst.getBrName());
//			ylfxReport.setBrNo(brMst.getBrNo());
//			if(!prcMode.equals("4")) {
//				ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// �ʲ����
//				System.out.println("�ʲ����:" + ylfxReport.getZcye());
//				ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoZc, "3", assessScope, true));// ��Ϣ�ʣ���Ȩƽ������
//				System.out.println("��Ϣ��:" + ylfxReport.getSxl());
//				ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// ��ծ���
//				System.out.println("��ծ���:" + ylfxReport.getFzye());
//				ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoFz, "3", assessScope, true));// ��Ϣ�ʣ���Ȩƽ������
//				System.out.println("��Ϣ��:" + ylfxReport.getFxl());
//				ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//�ʲ�ƽ������
//		        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
//		        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//��ծƽ������
//		        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
//		        Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
//				ylfxReport.setZczyjg(resultPrice[0]);// �ʲ�ת�Ƽ۸�
//				System.out.println("�ʲ�ת�Ƽ۸�:" + ylfxReport.getZczyjg());
//				ylfxReport.setFzzyjg(resultPrice[1]);// ��ծת�Ƽ۸�
//				System.out.println("��ծת�Ƽ۸�:" + ylfxReport.getFzzyjg());
//				ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);// ��Ϣ����
//				System.out.println("��Ϣ����:" + ylfxReport.getLxsr());
//				ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);// ��Ϣ֧��
//				System.out.println("��Ϣ֧��:" + ylfxReport.getLxzc());
//				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
//				ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// �ʲ�ת��֧��
//				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
//				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
//				ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// ��ծת������
//				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
//				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// ������
//			}else {
//				//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢��� --����ƥ��
//				double[] qxppZcValue = this.getQxppValue(brSql, prdtNoZc, date2, date);
//				ylfxReport.setZcye(qxppZcValue[5]);// �ʲ����
//				System.out.println("�ʲ����:"+ylfxReport.getZcye());
//				ylfxReport.setSxl(qxppZcValue[0]);// ��Ϣ��
//				System.out.println("��Ϣ��:"+ylfxReport.getSxl());
//		        ylfxReport.setZcpjqx(qxppZcValue[1]);//�ʲ�ƽ������
//		        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
//		        ylfxReport.setZczyjg(qxppZcValue[2]);// �ʲ�ת�Ƽ۸�
//				System.out.println("�ʲ�ת�Ƽ۸�:"+ylfxReport.getZczyjg());
//				ylfxReport.setLxsr(qxppZcValue[3]);// ��Ϣ����
//				System.out.println("��Ϣ����:"+ylfxReport.getLxsr());
//				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
//				System.out.println("�ʲ�������:" + ylfxReport.getZcjlc());
//				ylfxReport.setZczyzc(qxppZcValue[4]);//�ʲ�ת��֧��
//				System.out.println("�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
//				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
//				System.out.println("�ʲ�������:" + ylfxReport.getZcjsr());
//				
//				//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
//				double[] qxppFzValue = this.getQxppValue(brSql, prdtNoFz, date2, date);
//				ylfxReport.setFzye(qxppFzValue[5]);// ��ծ���
//				System.out.println("��ծ���:"+ylfxReport.getFzye());
//				ylfxReport.setFxl(qxppFzValue[0]);// ��Ϣ��
//				System.out.println("��Ϣ��:"+ylfxReport.getFxl());
//		        ylfxReport.setFzpjqx(qxppFzValue[1]);//��ծƽ������
//		        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
//		        ylfxReport.setFzzyjg(qxppFzValue[2]);// ��ծת�Ƽ۸�
//				System.out.println("��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
//				ylfxReport.setLxzc(qxppFzValue[3]);// ��Ϣ֧��
//				System.out.println("��Ϣ֧��:"+ylfxReport.getLxzc());
//				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
//				System.out.println("��ծ������:" + ylfxReport.getFzjlc());
//				ylfxReport.setFzzysr(qxppFzValue[4]);//��ծת��֧��
//				System.out.println("��ծת��֧��:" + ylfxReport.getZczyzc());
//				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
//				System.out.println("��ծ������:" + ylfxReport.getZcjsr());
//				
//				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// ������
//			}
//			ylfxReportList.add(ylfxReport);
//		}
//		//��ylfxReportList���������С���н�������
//		Collections.sort(ylfxReportList, new Comparator<YlfxReport>() {
//			 public int compare(YlfxReport arg0, YlfxReport arg1) {
//				 return arg1.getJsr().compareTo(arg0.getJsr());
//				 }
//			 }
//		);
//		return ylfxReportList;
//	}
//	/**
//	 * �ʽ�����ӯ������
//	 * @param request
//	 * @param date
//	 * @param brNo
//	 * @param manageLvl
//	 * @param assessScope
//	 * @return
//	 */
//	public YlfxReport financialCenterLCProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope) {
//		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// ������
//		System.out.println("�����磺" + xlsBrNo);
//		String prcMode = getPrcModeByBrNo(xlsBrNo, manageLvl);// �����õ��ʽ��
//		if (prcMode == null)
//			return null;
//		System.out.println("�����õ��ʽ�أ�" + prcMode);
//		request.getSession().setAttribute("prcMode", prcMode);
//		String prdtNoZc = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "1"); // ��Ӧ�ʽ�������õ��ʲ����Ʒ
//		String prdtNoFz = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "2"); // ��Ӧ�ʽ�������õĸ�ծ���Ʒ
//		System.out.println("�ʲ��ʽ�ز�Ʒ��" + prdtNoZc);
//		System.out.println("��ծ�ʽ�ز�Ʒ��" + prdtNoFz);
//		if (prdtNoZc == null || prdtNoFz == null)
//			return null;
//		List ftpResultList = null;
//		if(!prcMode.equals("4")) {//�����������ƥ�䷨
//			ftpResultList = this.getFtpResult(date, xlsBrNo, prcMode);//��ȡ��Ӧ������Ķ��۽��list
//		}
//		String date2 = CommonFunctions.dateModifyM(date, assessScope);
//		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//���������꣬��ֻȡ��������ݣ���1�·ݿ�ʼ
//	    	date2 = date2.substring(0,4)+"1231";
//		
//		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//��ȡ��ͳ�Ƶ�����
//	    YlfxReport ylfxReport = new YlfxReport();
//		String brSql = LrmUtil.getBrSql(brNo);
//		ylfxReport.setBrName(brInfoDAO.getInfo(brNo).getBrName());
//		ylfxReport.setBrNo(brNo);
//		ylfxReport.setManageLvl(manageLvl);
//		if(!prcMode.equals("4")) {
//			ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// �ʲ����
//			System.out.println("�ʲ����:" + ylfxReport.getZcye());
//			ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// ��ծ���
//			System.out.println("��ծ���:" + ylfxReport.getFzye());
//			Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
//			ylfxReport.setZczyjg(resultPrice[0]);// �ʲ�ת�Ƽ۸�
//			System.out.println("�ʲ�ת�Ƽ۸�:" + ylfxReport.getZczyjg());
//			ylfxReport.setFzzyjg(resultPrice[1]);// ��ծת�Ƽ۸�
//			System.out.println("��ծת�Ƽ۸�:" + ylfxReport.getFzzyjg());
//			ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//�ʲ�ƽ������
//	        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
//	        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//��ծƽ������
//	        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
//	        ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// �ʽ����ĵ��ʲ�ת������=�������ʲ�ת��֧��
//			System.out.println("�ʲ�ת������:" + ylfxReport.getZczyzc());
//			ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// �ʽ����ĵĸ�ծת��֧��=�����ĸ�ծת������
//			System.out.println("��ծת��֧��:" + ylfxReport.getFzzysr());
//		}else {//���������ƥ��
//			//����ƥ�䣺��ȡӯ������ʱ�䷶Χ�ڵĶ������ڴ���
//			//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
//			double[] qxppZcValue = this.getQxppValue(brSql, prdtNoZc, date2, date);
//			//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת������--����ƥ��
//			double[] qxppFzValue = this.getQxppValue(brSql, prdtNoFz, date2, date);
//			ylfxReport.setZcye(qxppZcValue[5]);// �ʲ����
//			System.out.println("�ʲ����:"+ylfxReport.getZcye());
//			ylfxReport.setZczyjg(qxppZcValue[2]);// �ʲ�ת�Ƽ۸�
//			System.out.println("�ʲ�ת�Ƽ۸�:"+ylfxReport.getZczyjg());
//			ylfxReport.setFzye(qxppFzValue[5]);// ��ծ���
//			System.out.println("��ծ���:"+ylfxReport.getFzye());
//			ylfxReport.setFzzyjg(qxppFzValue[2]);// ��ծת�Ƽ۸�
//			System.out.println("��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
//			ylfxReport.setZcpjqx(qxppZcValue[1]);//�ʲ�ƽ������
//	        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
//	        ylfxReport.setFzpjqx(qxppFzValue[1]);//��ծƽ������
//	        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
//	        ylfxReport.setZczyzc(qxppZcValue[4]);// �ʽ����ĵ��ʲ�ת������=�������ʲ�ת��֧��
//			System.out.println("�ʲ�ת������:" + ylfxReport.getZczyzc());
//			ylfxReport.setFzzysr(qxppFzValue[4]);// �ʽ����ĵĸ�ծת��֧��=�����ĸ�ծת������
//			System.out.println("��ծת��֧��:" + ylfxReport.getFzzysr());
//		}
//		
//		return ylfxReport;
//	}
//	/**
//	 * ���ݻ�����ȡ����Ӧ�ʽ�������õĲ�Ʒ
//	 * 
//	 * @param brNo
//	 *            �����������
//	 * @param prcMode
//	 *            �ʽ��
//	 * @param poolType
//	 *            �ʽ���ʲ�����:�ʲ�/��ծ
//	 * @return
//	 */
//	public String getPrdtNoByPrcMode(String brNo, String prcMode, String poolType) {
//		StringBuffer prdtNo = new StringBuffer();
//		if(!prcMode.equals("4")) {//��˫���ʽ�أ�����ƥ��͵�˫�ඨ�۲�ѯ������ͬ���鲻ͬ�����ݱ�
//			String hsql = "from FtpPoolInfo where prcMode = '" + prcMode + "' and brNo = '" + brNo + "'";
//			if (!prcMode.equals("1")) {// ����ǵ��ʽ�أ��޷�����poolType�������ʲ����Ǹ�ծ
//				hsql += " and poolType = '" + poolType + "'";
//			}
//			List<FtpPoolInfo> poolList = daoFactory.query(hsql, null);
//			if (poolList == null || poolList.size() < 1) {
//				return null;// ��Ӧ�Ķ��۲���δ�����ʽ��
//			} else {
//				if (prcMode.equals("1")) {// ����ǵ��ʽ�أ�����ContentObject��prdtNo���������ʲ����Ǹ�ծ��P1��ͷΪ�ʲ���P2��ͷΪ��ծ
//					FtpPoolInfo ftpPoolInfo = poolList.get(0);
//					String contentObject = ftpPoolInfo.getContentObject();
//					String[] contentObjects = contentObject.split("\\+");
//					for (int i = 0; i < contentObjects.length; i++) {
//						if (contentObjects[i].indexOf("P" + poolType) != -1)
//							prdtNo.append(contentObjects[i] + ",");
//					}
//				} else {
//					for (FtpPoolInfo ftpPoolInfo : poolList) {
//						prdtNo.append(ftpPoolInfo.getContentObject() + ",");
//					}
//				}
//			}
//		}else {//����ƥ�䷨
//			String hsql = "from FtpProductMethodRel t where t.productNo like 'P"+poolType+"%' " +
//					"and t.brNo = '"+brNo+"'";
//			List<FtpProductMethodRel> ftpProductMethodRelList = daoFactory.query(hsql, null);
//			for(FtpProductMethodRel ftpProductMethodRel : ftpProductMethodRelList) {
//				prdtNo.append("'" + ftpProductMethodRel.getProductNo() + "',");
//			}
//		}
//		if (prdtNo.lastIndexOf(",") == prdtNo.length() - 1)
//			prdtNo = prdtNo.deleteCharAt(prdtNo.length() - 1);
//		return prdtNo.toString().replace("+", ",");
//	}
//
//	/**
//	 * ��ȡĳ�����������õ��ʽ��
//	 * 
//	 * @param brNo
//	 * @param manageLvl
//	 * @return
//	 */
//	public String getPrcModeByBrNo(String brNo, String manageLvl) {
//		String hsql = "from FtpSystemInitial where brNo = '" + brNo
//				+ "' and setValidMark = '1'";
//		FtpSystemInitial ftpSystemInitial = (FtpSystemInitial) daoFactory.getBean(hsql, null);
//		return ftpSystemInitial == null ? null : ftpSystemInitial.getSetResult();
//	}
//
//	/**
//	 * ��ȡ������Ӧ���ʽ�ת�Ƽ۸��ʲ�+��ծ
//	 * 
//	 * @param list ���۽��list
//	 * @param date
//	 * @param brSql
//	 * @param prcMode
//	 *            �ʽ��
//	 * @param assessScope
//	 *            ͳ��ά��
//	 * @return
//	 */
//	public Double[] getFtpResultPrice(List list, String date, String brSql, String prcMode, Integer assessScope) {
//		Double[] resultPrice = {0.0, 0.0};// �ʲ�+��ծ
//		if(list == null || list.size() < 1) {
//			return resultPrice;
//		}
//		if (prcMode.equals("1")) {// ����ǵ��ʽ�أ���ֱ�ӻ�ȡ�ʽ�ת�Ƽ۸�,�ʲ�ת�Ƽ۸�͸�ծת�Ƽ۸�Ϊͬһ��
//			Object obj = list.get(0);
//			Object[] o = (Object[]) obj;
//			resultPrice[0] = Double.valueOf(o[0].toString());
//			resultPrice[1] = Double.valueOf(o[0].toString());
//		} else if (prcMode.equals("2")) {// �����˫�ʽ�أ�����pool_no��ȡת�Ƽ۸�pool_no=201Ϊ�ʲ���pool_noΪ202Ϊ��ծ
//			for (int i = 0; i < list.size(); i++) {
//				Object obj = list.get(i);
//				Object[] o = (Object[]) obj;
//				if (o[1].equals("201")) {// pool_no=201Ϊ�ʲ�
//					resultPrice[0] = Double.valueOf(o[0].toString());
//				} else if (o[1].equals("202")) {// pool_noΪ202Ϊ��ծ
//					resultPrice[1] = Double.valueOf(o[0].toString());
//				}
//			}
//		} else if (prcMode.equals("3")) {// ����Ƕ��ʽ�أ���Զ�����Ӽ�Ȩƽ����ȡת�Ƽ۸�
//			double zc = 0.0, zcye = 0.0, fz = 0.0, fzye = 0.0;
//			for (int i = 0; i < list.size(); i++) {
//				Object obj = list.get(i);
//				Object[] o = (Object[]) obj;
//				String prdtNo = o[3].toString().replace("+", ",");
//				System.out.println("prdtNo"+prdtNo);
//				double ye = FtpUtil.getAverageAmount(brSql, date, prdtNo, "3", assessScope, true);
//				if (o[2].equals("1")) {// �ʲ�
//					zc += ye * Double.valueOf(o[0].toString());
//					zcye += ye;
//				} else if (o[2].equals("2")) {// ��ծ
//					fz += ye * Double.valueOf(o[0].toString());
//					fzye += ye;
//				}
//			}
//			resultPrice[0] = zc / zcye;
//			resultPrice[1] = fz / fzye;
//		}
//		resultPrice[0] = (resultPrice[0].isInfinite() || resultPrice[0].isNaN()) ? 0.0 : resultPrice[0];
//		resultPrice[1] = (resultPrice[1].isInfinite() || resultPrice[1].isNaN()) ? 0.0 : resultPrice[1];
//
//		return resultPrice;
//
//	}
//	
//	/**
//	 * ��ȡ�����綨�۽���������
//	 * @param date
//	 * @param xlsBrNo
//	 * @param prcMode
//	 * @return
//	 */
//	public List getFtpResult(String date, String xlsBrNo, String prcMode) {
//		String sql = "select * from (select t.Result_price, t.pool_no, f.pool_type, f.Content_Object, f.prc_mode, "
//			+ "row_number() over(partition by t.pool_no order by t.RES_DATE desc,T.RESULT_ID desc ) rn from ftp.Ftp_Result t "
//			+ "left join ftp.ftp_pool_info f on t.prc_mode = f.prc_mode and t.br_no = f.br_no and t.pool_no = f.pool_no "
//			+ "where t.prc_Mode ='" + prcMode
//			+ "' and t.cur_no = '01' and t.br_no = '" + xlsBrNo + "' and to_date(t.res_date,'yyyyMMdd') <= to_date('"+date+"','yyyyMMdd') ";
//	    if (prcMode.equals("3")) {
//		     sql += " and t.pool_no in (select pool_no from ftp.ftp_pool_info where prc_mode='3' and br_no=t.br_no)";
//	    }
//	    sql += ") where rn=1 order by pool_type, pool_no";
//	    List list = daoFactory.query1(sql, null);
//	    return list;
//	}
//
//	/**
//	 * ��ȡ������Ӧĳ��ҵ����ʽ�ת�Ƽ۸�
//	 * 
//	 * @return
//	 */
//	public Double getFtpResultPrice(String date, String brSql, String xlsBrNo,
//			String prcMode, String prdtNo, Integer assessScope) {
//		Double resultPrice = null;
//		String sql = "select * from (select t.Result_price, t.pool_no, f.pool_type, f.Content_Object, "
//				+ "row_number() over(partition by t.pool_no order by t.RES_DATE desc,T.RESULT_ID desc ) rn from ftp.Ftp_Result t "
//				+ "left join ftp.ftp_pool_info f on t.prc_mode = f.prc_mode and t.br_no = f.br_no and t.pool_no = f.pool_no "
//				+ "where t.prc_Mode ='" + prcMode + "' and t.cur_no = '01' and t.br_no = '" + xlsBrNo + "' and to_date(t.res_date,'yyyyMMdd') <= to_date('"+date+"','yyyyMMdd') ";
//		if (prcMode.equals("3")) {
//			sql += " and t.pool_no in (select pool_no from ftp.ftp_pool_info where prc_mode='3' and br_no=t.br_no)";
//		}
//		sql += ") where rn=1 order by pool_type, pool_no";
//		List list = daoFactory.query1(sql, null);
//		if (prcMode.equals("1")) {// ����ǵ��ʽ�أ���ֱ�ӻ�ȡ�ʽ�ת�Ƽ۸�
//			Object obj = list.get(0);
//			Object[] o = (Object[]) obj;
//			resultPrice = Double.valueOf(o[0].toString());
//		} else if (prcMode.equals("2") || prcMode.equals("3")) {// �����˫/���ʽ�أ���ÿ����Ʒͨ�������ʽ�ת�Ƽ۸���м�Ȩƽ������ȡĳ��ҵ����ʽ�ת�Ƽ۸�
//			double fz = 0.0;// ��Ȩƽ����ʽ�ķ���
//			double fm = 0.0;// ��Ȩƽ����ʽ�ķ�ĸ
//			System.out.println("prdtNo:"+prdtNo);
//			for (int i = 0; i < list.size(); i++) {// ѭ��ÿ������
//				Object obj = list.get(i);
//				Object[] o = (Object[]) obj;
//				String[] contentObjects = o[3].toString().split("\\+");
//				
//				for (int j = 0; j < contentObjects.length; j++) {// ѭ��ÿ�����Ӷ�Ӧ�Ĳ�Ʒ���жϸò�Ʒ�Ƿ���prdtNo�д��ڣ�������ڣ����м�Ȩ
//					if (prdtNo.indexOf(contentObjects[j]) != -1) {
//						// ��ȡ�ò�Ʒ���¾����
//						double ye = FtpUtil.getAverageAmount(brSql, date, contentObjects[j], "3", assessScope, true);
//						System.out.println("ye"+ye);
//						fz += ye * Double.valueOf(o[0].toString());
//						fm += ye;
//					}
//				}
//			}
//			resultPrice = fz / fm;
//		}
//
//		return (resultPrice.isNaN() || resultPrice.isInfinite()) ? 0.0 : resultPrice;
//
//	}
//	
//	/**
//	 * ����ҵ�����ͱ������ ��ȡ �����ڸ���ҵ�����͵�ӯ��������������
//	 * @param brSql ������ѯsql
//	 * @param xlsBrNo ������
//	 * @param date
//	 * @param prcMode �ʽ������
//	 * @param assessScope ͳ��ά��
//	 * @param businessNo ҵ����[]
//	 * @param ftpPoolInfoList �ʽ������list
//	 * @return
//	 */
//	public List<YlfxReport> getYlfxReportList(String brSql,String xlsBrNo, String date,String prcMode, Integer assessScope, String[] businessNo, List<FtpPoolInfo> ftpPoolInfoList) {
//		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
//		String date2 = CommonFunctions.dateModifyM(date, assessScope);
//		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//���������꣬��ֻȡ��������ݣ���1�·ݿ�ʼ
//	    	date2 = date2.substring(0,4)+"1231";
//		
//		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//��ȡ��ͳ�Ƶ�����
//		for(int i = 0; i < businessNo.length; i++) {
//			//����ҵ���Ż�ȡ��Ӧ�Ĳ�Ʒ���
//			String hsql = "from FtpProductMethodRel where businessNo in ("+businessNo[i]+") and brNo = '"+xlsBrNo+"'";
//			List<FtpProductMethodRel> ftpProductMethodRelList = daoFactory.query(hsql, null);
//			StringBuilder prdtNo = new StringBuilder();
//			for (FtpProductMethodRel ftpProductMethodRel : ftpProductMethodRelList) {
//				prdtNo.append("'" + ftpProductMethodRel.getProductNo() + "',");
//			}
//			prdtNo = prdtNo.deleteCharAt(prdtNo.length() - 1);
//			// ��ȡ��Ӧ�ʽ�������õĲ�Ʒ
//			String settedPrdtNo = "";
//			//�����˫���ʽ�أ�Ҫ���ݵ�˫���ʽ�ص����ý��л�ȡ
//			if(!prcMode.equals("4"))settedPrdtNo = this.getPrdtNoByPrcModeAndPrdtNo(ftpPoolInfoList, prdtNo.toString());
//			else settedPrdtNo = prdtNo.toString();
//			String businessName = ftpProductMethodRelList.get(0).getBusinessName();//�ǡ�����ҵ���ҵ��ֻ��һ������ֱ��ȡ���list�ĵ�һ����ҵ������
//			if(businessNo[i].indexOf("YW201") != -1) {//������ҵ�����ڰ���4��ҵ������ֱ���ж��������"���ڴ��"����ҵ����Ϊ�����Ӳ����ݿ����ñ��ȡ
//				businessName = "���";
//			}
//			System.out.println(businessName+"["+businessNo[i]+"]"+"����Ӧ�Ĳ�Ʒ"+settedPrdtNo);
//			YlfxReport ylfxReport = new YlfxReport();
//			ylfxReport.setBusinessNo(businessNo[i]);// ҵ����
//			ylfxReport.setBusinessName(businessName);// ҵ������
//			if(settedPrdtNo.equals("")) {
//				ylfxReportList.add(ylfxReport);
//				continue;
//			}
//			if(businessNo[i].indexOf("YW1") != -1) {//�ʲ���
//				if(!prcMode.equals("4")) {
//					ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//�ʲ����
//					System.out.println(businessName+"-�ʲ����:" + ylfxReport.getZcye());
//					ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//��Ϣ�ʣ���Ȩƽ������
//					System.out.println(businessName+"-��Ϣ��:" + ylfxReport.getSxl());
//					ylfxReport.setZczyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//�ʲ�ת�Ƽ۸�
//					ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//�ʲ�ƽ������
//			        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
//			        System.out.println(businessName+"-�ʲ�ת�Ƽ۸�:" + ylfxReport.getZczyjg());
//					ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);//��Ϣ����
//					System.out.println(businessName+"-��Ϣ����:" + ylfxReport.getLxsr());
//					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());//�ʲ�������
//					System.out.println(businessName+"-�ʲ�������:" + ylfxReport.getZcjlc());
//					ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);//�ʲ�ת��֧��
//					System.out.println(businessName+"-�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
//					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());//�ʲ�������
//					System.out.println(businessName+"-�ʲ�������:" + ylfxReport.getZcjsr());
//				}else {//���������ƥ�䷨
//					//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
//					double[] qxppZcValue = this.getQxppValue(brSql, settedPrdtNo, date2, date);
//					ylfxReport.setZcye(qxppZcValue[5]);// �ʲ����
//					System.out.println(businessName+"-�ʲ����:"+ylfxReport.getZcye());
//					ylfxReport.setSxl(qxppZcValue[0]);// ��Ϣ��
//					System.out.println(businessName+"-��Ϣ��:"+ylfxReport.getSxl());
//			        ylfxReport.setZcpjqx(qxppZcValue[1]);//�ʲ�ƽ������
//			        System.out.println(businessName+"-�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
//					ylfxReport.setZczyjg(qxppZcValue[2]);// �ʲ�ת�Ƽ۸�
//					System.out.println(businessName+"-�ʲ�ת�Ƽ۸�:"+ylfxReport.getZczyjg());
//					ylfxReport.setLxsr(qxppZcValue[3]);// ��Ϣ����
//					System.out.println(businessName+"-��Ϣ����:"+ylfxReport.getLxsr());
//					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
//					System.out.println(businessName+"-�ʲ�������:" + ylfxReport.getZcjlc());
//					ylfxReport.setZczyzc(qxppZcValue[4]);//�ʲ�ת��֧��
//					System.out.println(businessName+"-�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
//					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
//					System.out.println(businessName+"-�ʲ�������:" + ylfxReport.getZcjsr());
//				}
//			}else if(businessNo[i].indexOf("YW2") != -1) {//��ծ��
//				if(!prcMode.equals("4")) {
//					ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//��ծ���
//					System.out.println(businessName+"-��ծ���:"+ylfxReport.getFzye());
//					ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//��Ϣ�ʣ���Ȩƽ������
//					System.out.println(businessName+"-��Ϣ��:"+ylfxReport.getFxl());
//					ylfxReport.setFzzyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//�ʲ�ת�Ƽ۸�
//					ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//��ծƽ������
//			        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
//			        System.out.println(businessName+"-��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
//					ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);//��Ϣ֧��
//					System.out.println(businessName+"-��Ϣ֧��:"+ylfxReport.getLxzc());
//					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());//��ծ������
//					System.out.println(businessName+"-��ծ������:"+ylfxReport.getFzjlc());
//					ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);//��ծת������
//					System.out.println(businessName+"-��ծת������:"+ylfxReport.getFzzysr());
//					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());//��ծ������
//					System.out.println(businessName+"-��ծ������:"+ylfxReport.getFzjsr());
//				}else {//���������ƥ�䷨
//					//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
//					double[] qxppFzValue = this.getQxppValue(brSql, settedPrdtNo, date2, date);
//					ylfxReport.setFzye(qxppFzValue[5]);// ��ծ���
//					System.out.println(businessName+"-��ծ���:"+ylfxReport.getFzye());
//					ylfxReport.setFxl(qxppFzValue[0]);// ��Ϣ��
//					System.out.println(businessName+"-��Ϣ��:"+ylfxReport.getFxl());
//			        ylfxReport.setFzpjqx(qxppFzValue[1]);//��ծƽ������
//			        System.out.println(businessName+"-��ծƽ������:"+ylfxReport.getFzpjqx());
//			        ylfxReport.setFzzyjg(qxppFzValue[2]);// ��ծת�Ƽ۸�
//					System.out.println(businessName+"-��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
//					ylfxReport.setLxzc(qxppFzValue[3]);// ��Ϣ֧��
//					System.out.println(businessName+"-��Ϣ֧��:"+ylfxReport.getLxzc());
//					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
//					System.out.println(businessName+"-��ծ������:" + ylfxReport.getFzjlc());
//					ylfxReport.setFzzysr(qxppFzValue[4]);//��ծת��֧��
//					System.out.println(businessName+"-��ծת��֧��:" + ylfxReport.getZczyzc());
//					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
//					System.out.println(businessName+"-��ծ������:" + ylfxReport.getZcjsr());
//				}
//			}
//			ylfxReportList.add(ylfxReport);
//		}
//	 return ylfxReportList;
//	}
//	
//	/**
//	 * ��ȡ��ͬ���Ʒ��ӯ����������
//	 * @param brSql ������ѯsql
//	 * @param xlsBrNo ������
//	 * @param date
//	 * @param prcMode �ʽ������
//	 * @param assessScope ͳ��ά��
//	 * @param prdtNo ��Ʒ���+��Ʒ����[][]
//	 * @param ftpPoolInfoList �ʽ������list
//	 * @return
//	 */
//	public List<YlfxReport> getYlfxReportListByPrdtNo(String brSql,String xlsBrNo, String date,String prcMode, Integer assessScope, String[][] prdtNo, List<FtpPoolInfo> ftpPoolInfoList) {
//		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
//		String date2 = CommonFunctions.dateModifyM(date, assessScope);
//		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//���������꣬��ֻȡ��������ݣ���1�·ݿ�ʼ
//	    	date2 = date2.substring(0,4)+"1231";
//		
//		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//��ȡ��ͳ�Ƶ�����
//		for(int i = 0; i < prdtNo.length; i++) {
//			YlfxReport ylfxReport = new YlfxReport();
//			// ��ȡ��Ӧ�ʽ�������õĲ�Ʒ
//			String settedPrdtNo = "";
//			//�����˫���ʽ�أ�Ҫ���ݵ�˫���ʽ�ص����ý��л�ȡ
//			if(!prcMode.equals("4"))settedPrdtNo = this.getPrdtNoByPrcModeAndPrdtNo(ftpPoolInfoList, prdtNo[i][0]);
//			else settedPrdtNo = prdtNo[i][0];
//			ylfxReport.setPrdtName(prdtNo[i][1]);
//			if(settedPrdtNo.equals("")) {
//				ylfxReportList.add(ylfxReport);
//				continue;
//			}
//			if(prdtNo[i][0].indexOf("P1") != -1) {//�ʲ���
//				if(!prcMode.equals("4")) {
//					ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//�ʲ����
//					System.out.println(prdtNo[i][1]+"-�ʲ����:" + ylfxReport.getZcye());
//					ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//��Ϣ�ʣ���Ȩƽ������
//					System.out.println(prdtNo[i][1]+"-��Ϣ��:" + ylfxReport.getSxl());
//					ylfxReport.setZczyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//�ʲ�ת�Ƽ۸�
//					ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//�ʲ�ƽ������
//			        System.out.println("�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
//			        System.out.println(prdtNo[i][1]+"-�ʲ�ת�Ƽ۸�:" + ylfxReport.getZczyjg());
//					ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);//��Ϣ����
//					System.out.println(prdtNo[i][1]+"-��Ϣ����:" + ylfxReport.getLxsr());
//					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());//�ʲ�������
//					System.out.println(prdtNo[i][1]+"-�ʲ�������:" + ylfxReport.getZcjlc());
//					ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);//�ʲ�ת��֧��
//					System.out.println(prdtNo[i][1]+"-�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
//					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());//�ʲ�������
//					System.out.println(prdtNo[i][1]+"-�ʲ�������:" + ylfxReport.getZcjsr());
//				}else {//���������ƥ�䷨
//					//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
//					double[] qxppZcValue = this.getQxppValue(brSql, settedPrdtNo, date2, date);
//					ylfxReport.setZcye(qxppZcValue[5]);// �ʲ����
//					System.out.println(prdtNo[i][1]+"-�ʲ����:"+ylfxReport.getZcye());
//					ylfxReport.setSxl(qxppZcValue[0]);// ��Ϣ��
//					System.out.println(prdtNo[i][1]+"-��Ϣ��:"+ylfxReport.getSxl());
//			        ylfxReport.setZcpjqx(qxppZcValue[1]);//�ʲ�ƽ������
//			        System.out.println(prdtNo[i][1]+"-�ʲ�ƽ������:"+ylfxReport.getZcpjqx());
//			        ylfxReport.setZczyjg(qxppZcValue[2]);// �ʲ�ת�Ƽ۸�
//					System.out.println(prdtNo[i][1]+"-�ʲ�ת�Ƽ۸�:"+ylfxReport.getZczyjg());
//					ylfxReport.setLxsr(qxppZcValue[3]);// ��Ϣ����
//					System.out.println(prdtNo[i][1]+"-��Ϣ����:"+ylfxReport.getLxsr());
//					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// �ʲ�������
//					System.out.println(prdtNo[i][1]+"-�ʲ�������:" + ylfxReport.getZcjlc());
//					ylfxReport.setZczyzc(qxppZcValue[4]);//�ʲ�ת��֧��
//					System.out.println(prdtNo[i][1]+"-�ʲ�ת��֧��:" + ylfxReport.getZczyzc());
//					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// �ʲ�������
//					System.out.println(prdtNo[i][1]+"-�ʲ�������:" + ylfxReport.getZcjsr());
//				}
//			}else if(settedPrdtNo.indexOf("P2") != -1) {//��ծ��
//				if(!prcMode.equals("4")) {
//					ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//��ծ���
//					System.out.println(prdtNo[i][1]+"-��ծ���:"+ylfxReport.getFzye());
//					ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//��Ϣ�ʣ���Ȩƽ������
//					System.out.println(prdtNo[i][1]+"-��Ϣ��:"+ylfxReport.getFxl());
//					ylfxReport.setFzzyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//�ʲ�ת�Ƽ۸�
//					ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//��ծƽ������
//			        System.out.println("��ծƽ������:"+ylfxReport.getFzpjqx());
//			        System.out.println(prdtNo[i][1]+"-��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
//					ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);//��Ϣ֧��
//					System.out.println(prdtNo[i][1]+"-��Ϣ֧��:"+ylfxReport.getLxzc());
//					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());//��ծ������
//					System.out.println(prdtNo[i][1]+"-��ծ������:"+ylfxReport.getFzjlc());
//					ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);//��ծת������
//					System.out.println(prdtNo[i][1]+"-��ծת������:"+ylfxReport.getFzzysr());
//					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());//��ծ������
//					System.out.println(prdtNo[i][1]+"-��ծ������:"+ylfxReport.getFzjsr());
//				}else {//���������ƥ�䷨
//					//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
//					double[] qxppFzValue = this.getQxppValue(brSql, settedPrdtNo, date2, date);
//					ylfxReport.setFzye(qxppFzValue[5]);// ��ծ���
//					System.out.println(prdtNo[i][1]+"-��ծ���:"+ylfxReport.getFzye());
//					ylfxReport.setFxl(qxppFzValue[0]);// ��Ϣ��
//					System.out.println(prdtNo[i][1]+"-��Ϣ��:"+ylfxReport.getFxl());
//			        ylfxReport.setFzpjqx(qxppFzValue[1]);//��ծƽ������
//			        System.out.println(prdtNo[i][1]+"-��ծƽ������:"+ylfxReport.getFzpjqx());
//			        ylfxReport.setFzzyjg(qxppFzValue[2]);// ��ծת�Ƽ۸�
//					System.out.println(prdtNo[i][1]+"-��ծת�Ƽ۸�:"+ylfxReport.getFzzyjg());
//					ylfxReport.setLxzc(qxppFzValue[3]);// ��Ϣ֧��
//					System.out.println(prdtNo[i][1]+"-��Ϣ֧��:"+ylfxReport.getLxzc());
//					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// ��ծ������
//					System.out.println(prdtNo[i][1]+"-��ծ������:" + ylfxReport.getFzjlc());
//					ylfxReport.setFzzysr(qxppFzValue[4]);//��ծת��֧��
//					System.out.println(prdtNo[i][1]+"-��ծת��֧��:" + ylfxReport.getZczyzc());
//					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// ��ծ������
//					System.out.println(prdtNo[i][1]+"-��ծ������:" + ylfxReport.getZcjsr());
//				}
//				
//			}
//			ylfxReportList.add(ylfxReport);
//		}
//	 return ylfxReportList;
//	}
//
//	/**
//	 * ���ݻ�����ȡ����Ӧ�ʽ�������õ�ĳ��ҵ���¶�Ӧ�Ĳ�Ʒ
//	 * 
//	 * @param poolList
//	 *            �ʽ��list
//	 * @param prdtNo
//	 *            ĳ��ҵ������Ӧ�����в�Ʒ
//	 * @return
//	 */
//	public String getPrdtNoByPrcModeAndPrdtNo(List<FtpPoolInfo> poolList,
//			String prdtNo) {
//		StringBuffer settedPrdtNo = new StringBuffer();
//		for (FtpPoolInfo ftpPoolInfo : poolList) {
//			String contentObject = ftpPoolInfo.getContentObject();
//			String[] contentObjects = contentObject.split("\\+");
//			for (int i = 0; i < contentObjects.length; i++) {
//				// ѭ���ж������õĲ�Ʒ�Ƿ���prdtNo��
//				if (prdtNo.indexOf(contentObjects[i]) != -1)
//					settedPrdtNo.append(contentObjects[i] + ",");
//			}
//		}
//		System.out.println("settedPrdtNo:"+settedPrdtNo);
//		if(settedPrdtNo.length() > 0) {
//			if (settedPrdtNo.lastIndexOf(",") == settedPrdtNo.length() - 1)
//				settedPrdtNo = settedPrdtNo.deleteCharAt(settedPrdtNo.length() - 1);
//		}
//		
//		return settedPrdtNo.toString();
//	}
//
//	/**
//	 * ����prcMode��brNo����ȡ��Ӧ���ʽ������
//	 * 
//	 * @param brNo
//	 *            �����������
//	 * @param prcMode
//	 *            �ʽ��
//	 * @return
//	 */
//	public List<FtpPoolInfo> getFtpPoolInfoList(String brNo, String prcMode) {
//		String hsql = "from FtpPoolInfo where prcMode = '" + prcMode
//				+ "' and brNo = '" + brNo + "'";
//		List<FtpPoolInfo> poolList = daoFactory.query(hsql, null);
//		return poolList;
//	}
//
//	/**
//	 * ��ȡĳ���Ʒ�ļ�Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢�����--����ƥ��
//	 * @param datesql
//	 * @param brSql
//	 * @param prdtNo
//	 * @param ye �����
//	 * @param countTerm ���۴���
//	 * @param date2 ʱ�����˵�
//	 * @param dateʱ����Ҷ˵�
//	 * @return ��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢�����
//	 */
//	public double[] getQxppValue(String brSql, String prdtNo, String date2, String date) {
//		double[] returnValue = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
//		double amt = 0.0, rate_fz = 0.0, ftpPrice_fz = 0.0, term_fz = 0.0, lxAmt = 0.0, zyAmt = 0.0;
//
//		//1.�ȴ����ζ��۵Ļ��ڴ��
//		//���ж�Ҫ����Ĳ�Ʒ���Ƿ����⼸���Ʒ
//		StringBuffer gehqPrdtNo = new StringBuffer();//���˻��������Ʒ���
//		if(prdtNo.indexOf("P2010") != -1) gehqPrdtNo.append("'P2010'" + ",");
//		if(prdtNo.indexOf("P2038") != -1) gehqPrdtNo.append("'P2038'" + ",");
//		if(prdtNo.indexOf("P2056") != -1) gehqPrdtNo.append("'P2056'" + ",");
//		StringBuffer dwhqPrdtNo = new StringBuffer();//��λ���������Ʒ���
//		if(prdtNo.indexOf("P2001") != -1) dwhqPrdtNo.append("'P2001'" + ",");
//		if(prdtNo.indexOf("P2055") != -1) dwhqPrdtNo.append("'P2055'" + ",");
//		if(prdtNo.indexOf("P2031") != -1) dwhqPrdtNo.append("'P2031'" + ",");
//		
//		if(!gehqPrdtNo.toString().equals("") || !dwhqPrdtNo.toString().equals("")) {
//			if(!gehqPrdtNo.toString().equals(""))gehqPrdtNo.deleteCharAt(gehqPrdtNo.length()-1);
//			else gehqPrdtNo.append("''");
//			if(!dwhqPrdtNo.toString().equals(""))dwhqPrdtNo.deleteCharAt(dwhqPrdtNo.length()-1);
//			else dwhqPrdtNo.append("''");
//			String hq_sql = "select * from ((select rate, count(*) count,nvl(sum(case when t.amt is null then t.bal else t.amt end),0.0) amt," +
//					"max(t.ftp_price),min(t.ftp_price),max(t.wrk_sys_date), min(t.wrk_sys_date) from ftp.ftp_qxpp_result t " +
//			   "where t.br_no "+brSql+" and t.wrk_sys_date>'"+date2+"' and t.wrk_sys_date<='"+date+"' " +
//			   "and t.business_no='YW201' and t.product_no in ("+gehqPrdtNo.toString()+") group by t.br_no,t.kmh,t.rate)" +//���˻�������
//			   "union " +
//			   "(select max(rate), count(*) count,nvl(sum(case when t.amt is null then t.bal else t.amt end),0.0) amt," +
//			   "max(t.ftp_price),min(t.ftp_price),max(t.wrk_sys_date), min(t.wrk_sys_date) from ftp.ftp_qxpp_result t " +
//			   "where t.br_no  "+brSql+" and t.wrk_sys_date>'"+date2+"' and t.wrk_sys_date<='"+date+"' " +
//			   "and t.business_no='YW201' and t.product_no in ("+dwhqPrdtNo.toString()+") group by t.br_no,t.ac_id))";//��λ��������
//
//			List hqList = daoFactory.query1(hq_sql, null);
//			if(hqList.size() > 0) {
//				for(Object object : hqList) {
//					Object[] obj = (Object[])object;
//					double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
//					Integer count = obj[1] == null ? 0 : Integer.valueOf(obj[1].toString());
//					double amt1 = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());
//					double max_ftpPrice = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());
//					double min_ftpPrice = obj[4] == null ? 0.0 : Double.valueOf(obj[4].toString());
//					//���ޣ���󶨼�ϵͳ����-��С����ϵͳ���ڵ���һϵͳ����
//					int term = CommonFunctions.daysSubtract(obj[5].toString(), CommonFunctions.dateModifyD(obj[6].toString(),-10));
//					amt += amt1/count;//�����
//					rate_fz += rate*amt1/count;//��Ȩƽ�����ʼ���ķ���
//					term_fz += term*amt1/count;//��Ȩƽ�����޼���ķ���
//					ftpPrice_fz += (max_ftpPrice+min_ftpPrice)/2*amt1/count;//��Ȩƽ��ת�Ƽ۸����ķ���
//					lxAmt += amt1/count*rate*term/365;//��Ϣ����/��Ϣ֧��=���*����*����/365
//					zyAmt += amt1/count*(max_ftpPrice+min_ftpPrice)/2*term/365;//�ʲ�ת��֧��/��ծת������=���*ת�Ƽ۸�*����/365
//				}
//			}
//		}
//		
//		//2.�ٴ����ζ��۵ķǻ��ڴ��(!YW201)
//		String dcfhq_sql = "select max(rate), count(*), nvl(sum(case when t.amt is null then t.bal else t.amt end),0.0) amt," +
//				"max(t.ftp_price),(case when max(t.term) <0 then 0 " +
//				   "when max(t.term) is null then 0 else (days(least(to_date(max(mtr_date),'yyyymmdd'), to_date('"+date+"','yyyymmdd')))-days(greatest(to_date(max(opn_date),'yyyymmdd'), to_date('"+date2+"','yyyymmdd')))) end) term from ftp.ftp_qxpp_result t " +
//				"where t.business_no!='YW201' and t.product_no in ("+prdtNo+") and  br_no "+brSql+" and (t.Opn_date <= '"+date+"' " +
//                "and t.Mtr_date > '"+date2+"') group by  br_no,ac_id ";
//                //+" having count(*)=1";
//		List dcfhqList = daoFactory.query1(dcfhq_sql, null);
//		if(dcfhqList.size() > 0) {
//			for(Object object : dcfhqList) {
//				Object[] obj = (Object[])object;
//				double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
//				Integer count = obj[1] == null ? 0 : Integer.valueOf(obj[1].toString());
//				double amt1 = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());
//				double ftpPrice = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());
//				double term = obj[4] == null ? 0.0 : Double.valueOf(obj[4].toString());
//				amt += amt1/count;//�����
//				rate_fz += rate*amt1/count;//��Ȩƽ�����ʼ���ķ���
//				term_fz += term*amt1/count;//��Ȩƽ�����޼���ķ���
//				ftpPrice_fz += ftpPrice*amt1/count;//��Ȩƽ��ת�Ƽ۸����ķ���
//				lxAmt += amt1/count*rate*term/365;//��Ϣ����/��Ϣ֧��=���*����*����/365
//				zyAmt += amt1/count*ftpPrice*term/365;//�ʲ�ת��֧��/��ծת������=���*ת�Ƽ۸�*����/365
//			}
//		}
//		
//		//3.������ζ��۵ķǻ��ڴ��(!YW201)����Ҫ�������ac_id����ѭ��ÿ��ac_id��ѯ�����дζ��۽���󵥶��ֶμ���
//		//#### ��ʱ������һ����Ŵ���ȡ��ζ����еĽ��ƽ��ֵ��������ʡ����ftp�۸���ȫ��һ�μ��㡣
//		////////####
//		
//		returnValue[0] = rate_fz/amt;//��Ȩƽ������
//		returnValue[1] = CommonFunctions.doublecut(term_fz/amt, 1);//��Ȩƽ������
//		returnValue[2] = ftpPrice_fz/amt;//��Ȩƽ��ת�Ƽ۸�
//		returnValue[3] = lxAmt;//��Ϣ����/��Ϣ֧��
//		returnValue[4] = zyAmt;//�ʲ�ת��֧��/��ծת������
//		returnValue[5] = amt;//�����
//		return returnValue;
//	}
//	
//	/**
//	 * ���ݻ����źͻ������𣬻�ȡ���Ӧ��������brno
//	 * 
//	 * @param brNo
//	 * @param manageLvl
//	 * @return
//	 */
//	public String getXlsBrNo(String brNo, String manageLvl) {
//		if (manageLvl.equals("2")) {
//			return brNo;
//		} else if (manageLvl.equals("1")) {
//			String hsql = "from BrMst where brNo = '" + brNo + "'";
//			BrMst brMst = (BrMst) daoFactory.getBean(hsql, null);
//			return brMst.getSuperBrNo();// �����1������ȡ���ĸ�����Ϊ����Ӧ��������
//		} else if (manageLvl.equals("0")) {
//			String hsql = "from BrMst where brNo = '" + brNo + "'";
//			BrMst brMst = (BrMst) daoFactory.getBean(hsql, null);
//			String hsql2 = "from BrMst where brNo = '" + brMst.getSuperBrNo()
//					+ "'";
//			brMst = (BrMst) daoFactory.getBean(hsql2, null);
//			return brMst.getSuperBrNo();// �����0����Ҫѭ�����λ�ȡ���ĸ����ĸ�����Ϊ����Ӧ��������
//		}
//		return "";
//	}
//	
//	/**
//	 * ����������brNo��Ҫ��ȡ�Ļ������𣬻�ȡ�������¸ü�������л���
//	 * @param brNo ������brNo
//	 * @param manageLvl
//	 * @return
//	 */
//	public List<BrMst> getBrMstList(String xlsBrNo, String manageLvl) {
//		String hsql = "";
//		List<BrMst> brMstList = new ArrayList<BrMst>();
//		if(manageLvl.equals("1")) {
//			hsql = "from BrMst where superBrNo = '"+xlsBrNo+"'";
//			brMstList = daoFactory.query(hsql, null);
//		}else if(manageLvl.equals("0")) {
//			hsql = "from BrMst where superBrNo = '"+xlsBrNo+"'";
//			List<BrMst> brMstList1 = daoFactory.query(hsql, null);
//			for(BrMst brMst : brMstList1) {
//				hsql = "from BrMst where superBrNo = '"+brMst.getBrNo()+"'";
//				List<BrMst> brMstList2 = daoFactory.query(hsql, null);
//				for(BrMst brMst2 : brMstList2) {
//					brMstList.add(brMst2);
//				}
//			}
//		}
//		return brMstList;
//	}
//}
