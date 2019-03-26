package com.dhcc.ftp.bo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;

import com.dhcc.ftp.cache.CacheOperation;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.FtpBusinessStaticDivide;
import com.dhcc.ftp.entity.FtpEmpInfo;
import com.dhcc.ftp.entity.FtpProductMethodRel;
import com.dhcc.ftp.entity.TelBrConfig;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.entity.YlfxBbReport;
import com.dhcc.ftp.entity.YlfxReport;
import com.dhcc.ftp.util.CastUtil;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.ExcelExport;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;

public class ReportBbBO extends BaseBo {
	public Map<String, List<BrMst>> brMstMap = this.getBrByEmpNo();
	CacheOperation co = CacheOperation.getInstance();//����
    long intervalTime = Long.valueOf("2592000000");//������һ����
    int maxVisitCount = 0;//�����Ʒ��ʴ���
	/**
	 * ������ӯ������
	 * @param request
	 * @param minDate
	 * @param maxDate
	 * @param brNo
	 * @param manageLvl ��������
	 * @param assessScope ͳ��ά�� ��-1��-3��-12
	 * @param isMx �Ƿ�鿴��ϸ
	 * @return
	 */
	public List<YlfxBbReport> brPayOffProfile(HttpServletRequest request, String minDate, String maxDate, String brNo,String brCountLvl, String manageLvl, Integer assessScope, Integer isMx) {
	    
	    TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
	    List<YlfxBbReport> ylfxReportList = new ArrayList<YlfxBbReport>();
	    
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(brNo, manageLvl);// ������
		System.out.println("�����磺" + xlsBrNo);
		
		int days = CommonFunctions.daysSubtract(maxDate, minDate);//����

		//��Ȩƽ�����ʡ���Ȩƽ�����ޡ���Ȩƽ��ת�Ƽ۸���Ϣ����/��Ϣ֧�����ʲ�ת��֧��/��ծת�����롢���--����ƥ��
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultPrdtList",new Object[]{xlsBrNo, minDate, maxDate,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, maxDate,assessScope);
		if(ftpResultsList == null) return null;
		
		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = this. getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		//����Ĭ����ʾ�¼�����|֧��Ĭ����ʾ֧�У������ϸ����ʾ�¼�
		if (isMx == 0 && !manageLvl.equals("3")&&!manageLvl.equals("2")) {// ������ǲ鿴�ӻ�����ӯ����������ֱ�Ӳ鿴�û�����ӯ������
			YlfxBbReport ylfxBbReport = new YlfxBbReport();
			String brSql = LrmUtil.getBrSqlByTel(brNo, telMst.getTelNo());
			ylfxBbReport.setBrName(brInfoDAO.getInfo(brNo).getBrName());
			ylfxBbReport.setBrNo(brNo);
			ylfxBbReport.setManageLvl(manageLvl);
			ylfxBbReport.setLeaf(this.getIsLeaf(brNo));
			double[] resultValue = this.getQxppValueByBrNo(ftpResultsList, brSql, custType);
			ylfxBbReport.setZcbal(resultValue[4]);// �ʲ����
			ylfxBbReport.setZcrjye(resultValue[0]);// �ʲ��վ����
			ylfxBbReport.setZcftplr(resultValue[1]);//�ʲ�FTP����
			ylfxBbReport.setZclc(resultValue[0]==0?0.00:resultValue[1]/resultValue[0]*360/days);//�ʲ�����(������)

			ylfxBbReport.setFzbal(resultValue[5]);  //��ծ���
			ylfxBbReport.setGrhqbal(resultValue[6]);  //��ծ���
			ylfxBbReport.setGrdqbal(resultValue[7]);  //��ծ���
			ylfxBbReport.setDwhqbal(resultValue[8]);  //��ծ���
			ylfxBbReport.setDwdqbal(resultValue[9]);  //��ծ���
			ylfxBbReport.setCzxbal(resultValue[10]);  //��ծ���
			ylfxBbReport.setYhkbal(resultValue[11]);  //��ծ���
			ylfxBbReport.setYjhkbal(resultValue[12]);  //��ծ���
			ylfxBbReport.setBzjbal(resultValue[13]);  //��ծ���

			ylfxBbReport.setFzrjye(resultValue[2]);// ��ծ�վ����
			ylfxBbReport.setGrhqrjye(resultValue[14]);  //��ծ�վ����
			ylfxBbReport.setGrdqrjye(resultValue[15]);  //��ծ�վ����
			ylfxBbReport.setDwhqrjye(resultValue[16]);  //��ծ�վ����
			ylfxBbReport.setDwdqrjye(resultValue[17]);  //��ծ�վ����
			ylfxBbReport.setCzxrjye(resultValue[18]);  //��ծ�վ����
			ylfxBbReport.setYhkrjye(resultValue[19]);  //��ծ�վ����
			ylfxBbReport.setYjhkrjye(resultValue[20]);  //��ծ�վ����
			ylfxBbReport.setBzjrjye(resultValue[21]);  //��ծ�վ����
			ylfxBbReport.setFzftplr(resultValue[3]);// ��ծFTP����
			ylfxBbReport.setFzlc(resultValue[2]==0?0.00:resultValue[3]/resultValue[2]*360/days);//��ծ����(������)
			ylfxBbReport.setFtplrhj(ylfxBbReport.getZcftplr()+ylfxBbReport.getFzftplr());// FTP����ϼ�
			
			ylfxReportList.add(ylfxBbReport);
		}else if(isMx == 0&&brCountLvl.equals("0")){
			List<BrMst> brMstList = getBrMstList(brNo, brCountLvl);
			Map<String,Double[]> QxppValue_map = this.getQxppValueMap_qhsyjgftphzb(ftpResultsList, brMstList, telMst.getTelNo(), custType);
			for (int i = 0; i < brMstList.size(); i++) {
				BrMst brMst = brMstList.get(i);
				System.out.println("��ʼ�������:"+brMst.getBrNo()+"...");
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setBrName(brMst.getBrName());
				ylfxBbReport.setBrNo(brMst.getBrNo());
				ylfxBbReport.setManageLvl(brMst.getManageLvl());
				ylfxBbReport.setLeaf(this.getIsLeaf(brMst.getBrNo()));

				Double[] qxppZcValue = QxppValue_map.get(brMst.getBrNo()+"-1");
				ylfxBbReport.setZcbal(qxppZcValue[2]);// �ʲ����
				ylfxBbReport.setZcrjye(qxppZcValue[0]);// �ʲ��վ����
				ylfxBbReport.setZcftplr(qxppZcValue[1]);//�ʲ�FTP����
				ylfxBbReport.setZclc(qxppZcValue[0]==0?0.00:qxppZcValue[1]/qxppZcValue[0]*360/days);//�ʲ�����(������)

				Double[] qxppFzValue = QxppValue_map.get(brMst.getBrNo()+"-2");
				ylfxBbReport.setFzbal(qxppFzValue[2]);// ��ծ���
				ylfxBbReport.setGrhqbal(qxppFzValue[3]);// ��ծ���
				ylfxBbReport.setGrdqbal(qxppFzValue[4]);// ��ծ���
				ylfxBbReport.setDwhqbal(qxppFzValue[5]);// ��ծ���
				ylfxBbReport.setDwdqbal(qxppFzValue[6]);// ��ծ���
				ylfxBbReport.setCzxbal(qxppFzValue[7]);// ��ծ���
				ylfxBbReport.setYhkbal(qxppFzValue[8]);// ��ծ���
				ylfxBbReport.setYjhkbal(qxppFzValue[9]);// ��ծ���
				ylfxBbReport.setBzjbal(qxppFzValue[10]);// ��ծ���

				ylfxBbReport.setFzrjye(qxppFzValue[0]);// ��ծ�վ����
				ylfxBbReport.setFzftplr(qxppFzValue[1]);// ��ծFTP����
				ylfxBbReport.setGrhqrjye(qxppFzValue[11]);// ��ծ�վ����
				ylfxBbReport.setGrdqrjye(qxppFzValue[12]);// ��ծ�վ����
				ylfxBbReport.setDwhqrjye(qxppFzValue[13]);// ��ծ�վ����
				ylfxBbReport.setDwdqrjye(qxppFzValue[14]);// ��ծ�վ����
				ylfxBbReport.setCzxrjye(qxppFzValue[15]);// ��ծ�վ����
				ylfxBbReport.setYhkrjye(qxppFzValue[16]);// ��ծ�վ����
				ylfxBbReport.setYjhkrjye(qxppFzValue[17]);// ��ծ�վ����
				ylfxBbReport.setBzjrjye(qxppFzValue[18]);// ��ծ�վ����
				ylfxBbReport.setFzlc(qxppFzValue[0]==0?0.00:qxppFzValue[1]/qxppFzValue[0]*360/days);//��ծ����(������)
				ylfxBbReport.setFtplrhj(ylfxBbReport.getZcftplr()+ylfxBbReport.getFzftplr());// FTP����ϼ�
				ylfxReportList.add(ylfxBbReport);
			}
			//��ylfxBbReportList��FTP�����С���н�������
			Collections.sort(ylfxReportList, new Comparator<YlfxBbReport>() {
						public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
							return arg1.getFtplrhj().compareTo(arg0.getFtplrhj());
						}
					}
			);
		}
		else {
			// ��ȡ�û������¼�����
			List<BrMst> brMstList = getChildBrListByTel(brNo, telMst.getTelNo());
			Map<String,Double[]> QxppValue_map = this.getQxppValueMap_qhsyjgftphzb(ftpResultsList, brMstList, telMst.getTelNo(), custType);
			for (int i = 0; i < brMstList.size(); i++) {
				BrMst brMst = brMstList.get(i);
				System.out.println("��ʼ�������:"+brMst.getBrNo()+"...");
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setBrName(brMst.getBrName());
				ylfxBbReport.setBrNo(brMst.getBrNo());
				ylfxBbReport.setManageLvl(brMst.getManageLvl());
				ylfxBbReport.setLeaf(this.getIsLeaf(brMst.getBrNo()));
				
				Double[] qxppZcValue = QxppValue_map.get(brMst.getBrNo()+"-1");
				ylfxBbReport.setZcbal(qxppZcValue[2]);// �ʲ����
				ylfxBbReport.setZcrjye(qxppZcValue[0]);// �ʲ��վ����
				ylfxBbReport.setZcftplr(qxppZcValue[1]);//�ʲ�FTP����
				ylfxBbReport.setZclc(qxppZcValue[0]==0?0.00:qxppZcValue[1]/qxppZcValue[0]*360/days);//�ʲ�����(������)
				
				Double[] qxppFzValue = QxppValue_map.get(brMst.getBrNo()+"-2");
				ylfxBbReport.setFzbal(qxppFzValue[2]);// ��ծ���
				ylfxBbReport.setGrhqbal(qxppFzValue[3]);// ��ծ���
				ylfxBbReport.setGrdqbal(qxppFzValue[4]);// ��ծ���
				ylfxBbReport.setDwhqbal(qxppFzValue[5]);// ��ծ���
				ylfxBbReport.setDwdqbal(qxppFzValue[6]);// ��ծ���
				ylfxBbReport.setCzxbal(qxppFzValue[7]);// ��ծ���
				ylfxBbReport.setYhkbal(qxppFzValue[8]);// ��ծ���
				ylfxBbReport.setYjhkbal(qxppFzValue[9]);// ��ծ���
				ylfxBbReport.setBzjbal(qxppFzValue[10]);// ��ծ���

				ylfxBbReport.setFzrjye(qxppFzValue[0]);// ��ծ�վ����
				ylfxBbReport.setFzftplr(qxppFzValue[1]);// ��ծFTP����
				ylfxBbReport.setGrhqrjye(qxppFzValue[11]);// ��ծ�վ����
				ylfxBbReport.setGrdqrjye(qxppFzValue[12]);// ��ծ�վ����
				ylfxBbReport.setDwhqrjye(qxppFzValue[13]);// ��ծ�վ����
				ylfxBbReport.setDwdqrjye(qxppFzValue[14]);// ��ծ�վ����
				ylfxBbReport.setCzxrjye(qxppFzValue[15]);// ��ծ�վ����
				ylfxBbReport.setYhkrjye(qxppFzValue[16]);// ��ծ�վ����
				ylfxBbReport.setYjhkrjye(qxppFzValue[17]);// ��ծ�վ����
				ylfxBbReport.setBzjrjye(qxppFzValue[18]);// ��ծ�վ����
				ylfxBbReport.setFzlc(qxppFzValue[0]==0?0.00:qxppFzValue[1]/qxppFzValue[0]*360/days);//��ծ����(������)
				ylfxBbReport.setFtplrhj(ylfxBbReport.getZcftplr()+ylfxBbReport.getFzftplr());// FTP����ϼ�
				ylfxReportList.add(ylfxBbReport);
			}
			//��ylfxBbReportList��FTP�����С���н�������
			Collections.sort(ylfxReportList, new Comparator<YlfxBbReport>() {
				 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
					 return arg1.getFtplrhj().compareTo(arg0.getFtplrhj());
					 }
				 }
			);
		}



		return ylfxReportList;
	}
	
	/**
	 * ȫ�����л���FTP��������
	 * @param request
	 * @param minDate
	 * @param maxDate
	 * @param brCountLvl ����ͳ�Ƽ���
	 * @param assessScope ͳ��ά��
	 * @return
	 */
	public List<YlfxBbReport> brPayOffRanking(HttpServletRequest request, String minDate, String maxDate, String brCountLvl, Integer assessScope) {
	    
	    List<YlfxBbReport> ylfxReportList = new ArrayList<YlfxBbReport>();
	    TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
	    String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// ������
		System.out.println("�����磺" + xlsBrNo);
		
		// ��ȡ�û���������1������
		//List<BrMst> brMstList = getBrLvl1List(brNo, telMst.getTelNo());//����Ȩ���жϲ鿴��Щ����
       // List<BrMst> brMstList = getBrLvl1List(xlsBrNo, "");//Ŀǰ�������Բ鿴���л���
		int days = CommonFunctions.daysSubtract(maxDate, minDate);//����
		//���ݻ��������ȡ���������¸ü����µ����л���
		List<BrMst> brMstList = getBrMstList(xlsBrNo, brCountLvl);
		
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, maxDate,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, maxDate,assessScope);
		if(ftpResultsList == null) return null;
		
		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		//String custType = "0";//Ŀǰ�����������Բ鿴���е�ҵ�����ߣ�û��Ȩ������
		Map<String,Double[]> QxppValue_map=this.getQxppValueMap_qhsyjgftphzb(ftpResultsList, brMstList, telMst.getTelNo(), custType);

		for(BrMst brMst : brMstList) {
			System.out.println("��ʼ�������:"+brMst.getBrNo()+"...");
			YlfxBbReport ylfxBbReport = new YlfxBbReport();
			ylfxBbReport.setBrName(brMst.getBrName());
			ylfxBbReport.setBrNo(brMst.getBrNo());
			ylfxBbReport.setManageLvl(brMst.getManageLvl());
			ylfxBbReport.setLeaf(this.getIsLeaf(brMst.getBrNo()));

			Double[] qxppZcValue = QxppValue_map.get(brMst.getBrNo()+"-1");
			ylfxBbReport.setZcbal(qxppZcValue[2]);// �ʲ����
			ylfxBbReport.setZcrjye(qxppZcValue[0]);// �ʲ��վ����
			ylfxBbReport.setZcftplr(qxppZcValue[1]);//�ʲ�FTP����
			ylfxBbReport.setGrdkftplr(qxppZcValue[3]);//���˴���FTP����
			ylfxBbReport.setGsdkftplr(qxppZcValue[4]);//��˾����FTP����
			ylfxBbReport.setZclc(qxppZcValue[0]==0?0.00:qxppZcValue[1]/qxppZcValue[0]*360/days);//�ʲ�����(������)

			Double[] qxppFzValue = QxppValue_map.get(brMst.getBrNo()+"-2");
			ylfxBbReport.setFzbal(qxppFzValue[2]);// ��ծ���
			ylfxBbReport.setGrhqbal(qxppFzValue[3]);// ��ծ���
			ylfxBbReport.setGrdqbal(qxppFzValue[4]);// ��ծ���
			ylfxBbReport.setDwhqbal(qxppFzValue[5]);// ��ծ���
			ylfxBbReport.setDwdqbal(qxppFzValue[6]);// ��ծ���
			ylfxBbReport.setCzxbal(qxppFzValue[7]);// ��ծ���
			ylfxBbReport.setYhkbal(qxppFzValue[8]);// ��ծ���
			ylfxBbReport.setYjhkbal(qxppFzValue[9]);// ��ծ���
			ylfxBbReport.setBzjbal(qxppFzValue[10]);// ��ծ���

			ylfxBbReport.setFzrjye(qxppFzValue[0]);// ��ծ�վ����
			ylfxBbReport.setFzftplr(qxppFzValue[1]);// ��ծFTP����
			ylfxBbReport.setGrhqrjye(qxppFzValue[11]);// ��ծ�վ����
			ylfxBbReport.setGrdqrjye(qxppFzValue[12]);// ��ծ�վ����
			ylfxBbReport.setDwhqrjye(qxppFzValue[13]);// ��ծ�վ����
			ylfxBbReport.setDwdqrjye(qxppFzValue[14]);// ��ծ�վ����
			ylfxBbReport.setCzxrjye(qxppFzValue[15]);// ��ծ�վ����
			ylfxBbReport.setYhkrjye(qxppFzValue[16]);// ��ծ�վ����
			ylfxBbReport.setYjhkrjye(qxppFzValue[17]);// ��ծ�վ����
			ylfxBbReport.setBzjrjye(qxppFzValue[18]);// ��ծ�վ����

            ylfxBbReport.setDsftplr(qxppFzValue[19]);// ��˽����
            ylfxBbReport.setDgftplr(qxppFzValue[20]);// �Թ�����

			ylfxBbReport.setGrhqftplr(qxppFzValue[21]);
			ylfxBbReport.setGrdqftplr(qxppFzValue[22]);
			ylfxBbReport.setDwhqftplr(qxppFzValue[23]);
			ylfxBbReport.setDwdqftplr(qxppFzValue[24]);
			ylfxBbReport.setCzxftplr(qxppFzValue[25]);
			ylfxBbReport.setYhkftplr(qxppFzValue[26]);
			ylfxBbReport.setYjhkftplr(qxppFzValue[27]);
			ylfxBbReport.setBzjftplr(qxppFzValue[28]);

			ylfxBbReport.setFzlc(qxppFzValue[0]==0?0.00:qxppFzValue[1]/qxppFzValue[0]*360/days);//��ծ����(������)
			ylfxBbReport.setFtplrhj(ylfxBbReport.getZcftplr()+ylfxBbReport.getFzftplr());// FTP����ϼ�
			ylfxReportList.add(ylfxBbReport);
		}
		//��ylfxBbReportList��FTP����ϼƴ�С���н�������
		Collections.sort(ylfxReportList, new Comparator<YlfxBbReport>() {
			 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				 return arg1.getFtplrhj().compareTo(arg0.getFtplrhj());
				 }
			 }
		);
		return ylfxReportList;
	}

	/**
	 * ����ҵ������FTP������ܱ�-- ����ҵ�����͵����ݻ�ȡ
	 * @param request
	 * @param minDate
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @return
	 */
	public List<YlfxBbReport> busPayOffProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, Integer assessScope) {
		
	    TelMst telMst=(TelMst)request.getSession().getAttribute("userBean");
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(brNo, manageLvl);// ������

		int days = CommonFunctions.daysSubtract(date, minDate);//����
		//�ӻ����л�ȡ����
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, date,assessScope);
		if(ftpResultsList == null) return null;
		
		String brNos = this.getAllChildBrAllbrno(brNo, manageLvl, telMst.getTelNo());//��ȡ���е��Ӽ������������Լ�
		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());

		//�����Բ�Ʒ���ΪKEY�Ľ����
		Map<String,Double[]> QxppValue_map = this.getQxppValueMap_ywtxylfx(ftpResultsList, xlsBrNo, brNos, custType);
		
		//��ȡͳ�ƴ���
		String sql1 = "select distinct business_no, business_name,substr(business_no,3,1) as zctype " +
		" from ftp.FTP_business_static_divide" +
		//" where br_no = '"+xlsBrNo+"'" +
		" order by zctype,business_no";//�Ȱ��ʲ���ծ���ͣ��ٰ��������
        List tjdlList = daoFactory.query1(sql1, null);
		
		for(int i = 0; i < tjdlList.size(); i++) {
			Object[] tjdl = (Object[])tjdlList.get(i);
			
			if(tjdl[0]!=null) {
				//����ͳ�ƴ����Ż�ȡ��Ӧ�Ĳ�Ʒ���
				List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = this.getFtpBSDivideList(xlsBrNo, tjdl[0].toString(), null, null);

				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setBusinessNo(tjdl[0].toString());// ҵ�����߱��
				ylfxBbReport.setBusinessName(tjdl[1].toString());// ҵ����������
				double rjye = 0.0,ftplr = 0.0,bal=0.0;
				for(FtpBusinessStaticDivide ftpBusinessStaticDivide : ftpBusinessStaticDivideList) {
					Double[] values=QxppValue_map.get(ftpBusinessStaticDivide.getProductNo());
					rjye += (values==null||values[0]==null)?0.0:values[0];
					ftplr += (values==null||values[1]==null)?0.0:values[1];
					bal += (values==null||values[2]==null)?0.0:values[2];
				}

				ylfxBbReport.setBal(bal);//���
				ylfxBbReport.setRjye(rjye);//�վ����
				ylfxBbReport.setFtplr(ftplr);//FTP����
				ylfxBbReport.setLc(rjye==0?0.00:ftplr/rjye*360/days);//����(������)
				
				ylfxBbReportList.add(ylfxBbReport);
			}
			
		}
	   return ylfxBbReportList;
	}
	
	/**
	 * �����������·ֲ�ƷFTP������ϸ��---ĳһͳ�ƴ��������в�Ʒӯ������ ���ݻ�ȡ
	 * @param request
	 * @param minDate
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @param businessNo
	 * @return
	 */
	public List<YlfxBbReport> prdtPayOffProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, Integer assessScope, String businessNo) {
	    
		List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		
		TelMst telMst=(TelMst)request.getSession().getAttribute("userBean");
	    String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(brNo, manageLvl);// ������
		System.out.println("�����磺" + xlsBrNo);
		
		//�ӻ����л�ȡ����
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, date,assessScope);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//����
		
		String brNos = this.getAllChildBrAllbrno(brNo, manageLvl, telMst.getTelNo());//��ȡ���е��Ӽ������������Լ�
		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		//�����Բ�Ʒ���ΪKEY�Ľ����
		Map<String,Double[]> QxppValue_map = this.getQxppValueMap_ywtxylfx(ftpResultsList, xlsBrNo, brNos, custType);
		
		//��ȡ��Ʒ���
		String hsql = "select distinct product_no, product_name" +
				" from ftp.Ftp_Business_Static_Divide" +
				" where  business_no = '"+businessNo+"' order by product_no";
        List prdtList = daoFactory.query1(hsql, null);
        
        //���ղ�Ʒ����ѭ��
        for (int i = 0; i < prdtList.size(); i++) {
			Object[] obj = (Object[])prdtList.get(i);
			double rjye = 0.0,ftplr = 0.0,bal= 0.0;
			//Ͷ��ҵ������һ������ĸ�ծͶ��ҵ��ͳ�ƴ��࣬��Ӧ��ҵ��Ͳ�Ʒ����null
			if(obj[0] == null) {
				continue;
			}
			Double[] values=QxppValue_map.get(obj[0]);
			rjye = (values==null||values[0]==null)?0.0:values[0];
			ftplr = (values==null||values[1]==null)?0.0:values[1];
			bal = (values==null||values[2]==null)?0.0:values[2];
			//ֻ��ʾ��ֵ��
			if(rjye==0&&ftplr==0) {
			}else {
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setPrdtNo(obj[0].toString());
				ylfxBbReport.setPrdtName(obj[1].toString());
				ylfxBbReport.setBal(bal);//���
				ylfxBbReport.setRjye(rjye);//�վ����
				ylfxBbReport.setFtplr(ftplr);//FTP����
				ylfxBbReport.setLc(rjye==0?0.00:ftplr/rjye*360/days);//����(������)
				
				ylfxBbReportList.add(ylfxBbReport);
			}
			
		}
      //��ylfxBbReportList��FTP�����С���н�������
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
			 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				 return arg1.getFtplr().compareTo(arg0.getFtplr());
				 }
			 }
		);
		return ylfxBbReportList;
	}
	
	/**
	 * ����ҵ�����߻��߲�Ʒ���߲�Ʒ������в�Ʒ�ֻ���FTP�������
	 * @param request
	 * @param minDate
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @param businessNo
	 * @param prdtCtgNo
	 * @param prdtNo
	 * @param isMx
	 * @return
	 */
    public List<YlfxBbReport> prdtBrProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, Integer assessScope, String businessNo, String prdtCtgNo,String prdtNo, Integer isMx) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(brNo, manageLvl);// ������
		TelMst telMst=(TelMst)request.getSession().getAttribute("userBean");
	    
		//�ӻ����л�ȡ����
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, date,assessScope);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//����
		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		
		//����ͳ�ƴ����Ż��߲�Ʒ�����Ż��߲�Ʒ��Ż�ȡ��Ӧ�Ĳ�Ʒ���
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = this.getFtpBSDivideList(xlsBrNo, businessNo, prdtCtgNo, prdtNo);
		
		//����Ĭ����ʾ�¼�����|֧��Ĭ����ʾ֧�У������ϸ����ʾ�¼�
		if (isMx == 0 && !manageLvl.equals("3")&&!manageLvl.equals("2")) {// ������ǲ鿴�ӻ����ģ���ֱ�Ӳ鿴�û�����
			YlfxBbReport ylfxBbReport = new YlfxBbReport();
			String brSql = LrmUtil.getBrSqlByTel(brNo, telMst.getTelNo());
			String prdtNos = "";
			for(FtpBusinessStaticDivide ftpBusinessStaticDivide : ftpBusinessStaticDivideList) {
				prdtNos += ftpBusinessStaticDivide.getProductNo() + ",";
			}
			ylfxBbReport.setBrName(brInfoDAO.getInfo(brNo).getBrName());
			ylfxBbReport.setBrNo(brNo);
			ylfxBbReport.setManageLvl(manageLvl);
			ylfxBbReport.setLeaf(this.getIsLeaf(brNo));
			Double[] resultValue = this.getQxppValue_cpfjglrb(ftpResultsList, brSql, custType, prdtNos);

			ylfxBbReport.setRjye(resultValue[0]);//�վ����
			ylfxBbReport.setFtplr(resultValue[1]);//FTP����
			ylfxBbReport.setBal(resultValue[2]);//FTP����
			ylfxBbReport.setLc(resultValue[0]==0?0.00:resultValue[1]/resultValue[0]*360/days);//����(������)
			
			ylfxBbReportList.add(ylfxBbReport);
		}else {
			// ��ȡ�û������¼�����
			List<BrMst> brMstList = getChildBrListByTel(brNo, telMst.getTelNo());
			Map<String,Double[]> QxppValue_map = this.getQxppValueMap_cpfjglrb(ftpResultsList, xlsBrNo, brMstList, custType, telMst.getTelNo());
			
			//ѭ����������ȡ��Ӧ��Ҫͳ�ƵĲ�Ʒ��ֵ
			for(BrMst brMst : brMstList) {
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setBrNo(brMst.getBrNo());
				ylfxBbReport.setBrName(brMst.getBrName());
				ylfxBbReport.setManageLvl(brMst.getManageLvl());
				ylfxBbReport.setLeaf(this.getIsLeaf(brMst.getBrNo()));
				double rjye = 0.0,ftplr = 0.0,bal=0.0;
				for(FtpBusinessStaticDivide ftpBusinessStaticDivide : ftpBusinessStaticDivideList) {
					Double[] values=QxppValue_map.get(ftpBusinessStaticDivide.getProductNo()+"-"+brMst.getBrNo());
					rjye += (values==null||values[0]==null)?0.0:values[0];
					ftplr += (values==null||values[1]==null)?0.0:values[1];
					bal += (values==null||values[2]==null)?0.0:values[2];
				}
				
				ylfxBbReport.setRjye(rjye);//�վ����
				ylfxBbReport.setBal(bal);//���
				ylfxBbReport.setFtplr(ftplr);//FTP����
				ylfxBbReport.setLc(rjye==0?0.00:ftplr/rjye*360/days);//����(������)
				
				ylfxBbReportList.add(ylfxBbReport);
			}
			
			//��ylfxBbReportList��FTP�����С���н�������
			Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
				 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
					 return arg1.getFtplr().compareTo(arg0.getFtplr());
					 }
				 }
			);
		}
		
	   return ylfxBbReportList;
	}
    
    /**
	 * ����ҵ�����߻��߲�Ʒ������߲�Ʒ���в�Ʒ�ֿͻ�����FTP�������
	 * @param request
	 * @param minDate
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @param businessNo
	 * @param prdtCtgNo
	 * @param prdtNo
	 * @return
	 */
    public List<YlfxBbReport> prdtEmpProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, Integer assessScope, String businessNo, String prdtCtgNo, String prdtNo) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
	    TelMst telMst=(TelMst)request.getSession().getAttribute("userBean");
	    
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(brNo, manageLvl);// ������
		//�ӻ����л�ȡ����
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, date,assessScope);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//����
		
		//����ҵ�����߱�Ż��߲�Ʒ������߲�Ʒ��Ż�ȡ��Ӧ�Ĳ�Ʒ���
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = this.getFtpBSDivideList(xlsBrNo, businessNo, prdtCtgNo, prdtNo);
		String prdtNos = "";
		for(FtpBusinessStaticDivide ftpBusinessStaticDivide : ftpBusinessStaticDivideList) {
			prdtNos += ftpBusinessStaticDivide.getProductNo()+",";
		}
		
		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
        
		Map<String,Double> QxppValue_map = this.getQxppValueMap_khjl(ftpResultsList, prdtNos, custType);
		
		//��ȡ�û����µ����пͻ�����
		String hsql1 = "from FtpEmpInfo where brMst.brNo "+this.getAllChildBr(brNo, manageLvl, telMst.getTelNo())+"";
		List<FtpEmpInfo> empList = daoFactory.query(hsql1, null);
		
		//ѭ���ͻ�������ȡ��Ӧ��Ҫͳ�ƵĲ�Ʒ��ֵ
		for(FtpEmpInfo ftpEmpInfo : empList) {
			double rjye = QxppValue_map.get(ftpEmpInfo.getEmpNo()+"-ye")==null?0:QxppValue_map.get(ftpEmpInfo.getEmpNo()+"-ye");
			double ftplr = QxppValue_map.get(ftpEmpInfo.getEmpNo()+"-lr")==null?0:QxppValue_map.get(ftpEmpInfo.getEmpNo()+"-lr");
			double bal = QxppValue_map.get(ftpEmpInfo.getEmpNo()+"-bal")==null?0:QxppValue_map.get(ftpEmpInfo.getEmpNo()+"-bal");

			//ֻ��ʾ��ֵ��
			if(rjye==0&&ftplr==0) {
			}else {
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(ftpEmpInfo.getEmpNo());
				ylfxBbReport.setEmpName(ftpEmpInfo.getEmpName());
				ylfxBbReport.setBrNo(ftpEmpInfo.getBrMst().getBrNo());
				ylfxBbReport.setBrName(ftpEmpInfo.getBrMst().getBrName());
				ylfxBbReport.setRjye(rjye);//�վ����
				ylfxBbReport.setBal(bal);//���
				ylfxBbReport.setFtplr(ftplr);//FTP����
				ylfxBbReport.setLc(rjye==0?0.00:ftplr/rjye*360/days);//����(������)
				
				ylfxBbReportList.add(ylfxBbReport);
			}
		}
		
		//��ylfxBbReportList��FTP�����С���н�������
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
			 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				 return arg1.getFtplr().compareTo(arg0.getFtplr());
				 }
			 }
		);
	   return ylfxBbReportList;
	}
    
    /**
	 * �ͻ�����ֲ�ƷFTP����������
	 * @param request
	 * @param minDate
	 * @param date
	 * @param empNo
	 * @param assessScope
	 * @return
	 */
    public List<YlfxBbReport> empPrdtProfile(HttpServletRequest request, String minDate, String date, String empNo, Integer assessScope) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// ������
		//�ӻ����л�ȡ����
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, date,assessScope);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//����
		
		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		
		Map<String,Double> QxppValue_map = this.getQxppValueMap_khjlfcp(ftpResultsList, empNo, custType);
		
		//��ȡ��Ʒ���
		String hsql = "select distinct product_no, product_name" +
				" from ftp.Ftp_Business_Static_Divide";
				//" where br_No = '"+xlsBrNo+"'";
        List prdtList = daoFactory.query1(hsql, null);
		
        //���ղ�Ʒ����ѭ��
        for (int i = 0; i < prdtList.size(); i++) {
			Object[] obj = (Object[])prdtList.get(i);
			double rjye = 0.0,ftplr = 0.0,bal=0;
			//Ͷ��ҵ������һ������ĸ�ծͶ��ҵ��ͳ�ƴ��࣬��Ӧ��ҵ��Ͳ�Ʒ����null
			if(obj[0] == null) {
				continue;
			}
			rjye = QxppValue_map.get(obj[0]+"-ye")==null?0:QxppValue_map.get(obj[0]+"-ye");
			ftplr = QxppValue_map.get(obj[0]+"-lr")==null?0:QxppValue_map.get(obj[0]+"-lr");
			bal = QxppValue_map.get(obj[0]+"-bal")==null?0:QxppValue_map.get(obj[0]+"-bal");
		
			//ֻ��ʾ��ֵ��
			if(rjye==0&&ftplr==0) {
			}else {
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setPrdtNo(obj[0].toString());
				ylfxBbReport.setPrdtName(obj[1].toString());
				ylfxBbReport.setBal(bal);//���
				ylfxBbReport.setRjye(rjye);//�վ����
				ylfxBbReport.setFtplr(ftplr);//FTP����
				ylfxBbReport.setLc(rjye==0?0.00:ftplr/rjye*360/days);//����(������)
				
				ylfxBbReportList.add(ylfxBbReport);
			}
		}
		//��ylfxBbReportList��FTP�����С���н�������
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
			 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				 return arg1.getFtplr().compareTo(arg0.getFtplr());
				 }
			 }
		);
	   return ylfxBbReportList;
	}
    
    /**
	 * �ͻ�����ֿͻ�FTP�����
	 * @param request
	 * @param minDate
	 * @param date
	 * @param empNo
	 * @param assessScope
	 * @param pageName
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
    public PageUtil empCusProfile(HttpServletRequest request, String minDate, String date, String empNo, Integer assessScope,
    		String pageName, int pageSize, int currentPage,boolean isAll) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		
		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// ������
		//�ӻ����л�ȡ����
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultListEmp(xlsBrNo, minDate, date,assessScope,empNo);
		if(ftpResultsList == null) return null;
		int days = CommonFunctions.daysSubtract(date, minDate);//����
		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		StringBuffer accIds = new StringBuffer();
		//�ӽ�����л�ȡĳ���ͻ���������ݣ������˺Ż��ܵ�map��
		Map<String,String>  brNameMap= FtpUtil.getBrNameMap();
		//�ӽ�����л�ȡ�����ͻ���������ݣ����ݿͻ���-�˺Ż��ܵ�map��
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[16]);//��¼�Ƿ�����ͳ��,�ֶԹ��Ͷ�˽

			if(!result[2].equals("")&&isStatic) {
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[8]);

				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(result[2]);
				ylfxBbReport.setBrName(brNameMap.get(result[0]));
				ylfxBbReport.setAcId(String.valueOf(result[5]));
				ylfxBbReport.setPrdtName(String.valueOf(result[9]));
				ylfxBbReport.setCustNo(String.valueOf(result[2]));
				ylfxBbReport.setCustName(String.valueOf(result[10]));
				ylfxBbReport.setOpnDate(String.valueOf(result[11]));
				ylfxBbReport.setMtrDate(String.valueOf(result[12]));
				ylfxBbReport.setRate(result[15] == null ? 0 : Double.valueOf(String.valueOf(result[15])));
				ylfxBbReport.setFtp(result[14] == null ? 0 : Double.valueOf(String.valueOf(result[14])));//���Ϊnull��ֵΪ-999
				ylfxBbReport.setRjye(rjye);//�վ����
				ylfxBbReport.setFtplr(ftplr);//FTP����
				ylfxBbReport.setBal(bal);//���
				ylfxBbReport.setLc(rjye == 0 ? 0.00 : ftplr / rjye * 360 / days);//����(������)
				ylfxBbReport.setBusinessName(result[13]);
				ylfxBbReportList.add(ylfxBbReport);
			}
		}

		//��ylfxBbReportList��FTP�����С���н�������
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
			 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				 return arg1.getFtplr().compareTo(arg0.getFtplr());
				 }
			 }
		);
		int rowsCount = ylfxBbReportList.size();
		QueryPageBO queryPageBo = new QueryPageBO();
		String pageLine=queryPageBo.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		if(isAll){
			return new PageUtil(ylfxBbReportList,pageLine);
		}else{
			if(currentPage*pageSize<rowsCount){
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,currentPage*pageSize),pageLine);
			}else {
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,rowsCount),pageLine);
			}
		}
		
	}
    
    /**
     * �ͻ�����FTP������ϸ��
     * @param request
     * @param minDate
     * @param date
     * @param empNo
     * @param assessScope
     * @param businessNo
     * @param prdtCtgNo
     * @param prdtNo
     * @param pageName
     * @param pageSize
     * @param currentPage
     * @return
     */
    public PageUtil empDetailProfile(HttpServletRequest request, String minDate, String date, String empNo, Integer assessScope,String businessNo,String prdtCtgNo,String prdtNo,
    		String pageName, int pageSize, int currentPage, boolean isAll) {

	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();

		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// ������
		//�ӻ����л�ȡ����
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultListEmp(xlsBrNo, minDate, date,assessScope,empNo);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//����

		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());

		//����ҵ�����߱�Ż��߲�Ʒ������߲�Ʒ��Ż�ȡ��Ӧ�Ĳ�Ʒ���
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = this.getFtpBSDivideList(xlsBrNo, businessNo, prdtCtgNo, prdtNo);
		String prdtNos = "";
		for(FtpBusinessStaticDivide ftpBusinessStaticDivide : ftpBusinessStaticDivideList) {
			prdtNos += ftpBusinessStaticDivide.getProductNo()+",";
		}

		Map<String,String>  brNameMap= FtpUtil.getBrNameMap();
		//�ӽ�����л�ȡ�����ͻ���������ݣ����ݿͻ���-�˺Ż��ܵ�map��
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[16]);//��¼�Ƿ�����ͳ��,�ֶԹ��Ͷ�˽

			if(!result[2].equals("")&&isStatic && prdtNos.indexOf(result[1])!=-1) {
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[8]);

				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(result[2]);
				ylfxBbReport.setBrName(brNameMap.get(result[0]));
				ylfxBbReport.setAcId(String.valueOf(result[5]));
				ylfxBbReport.setPrdtName(String.valueOf(result[9]));
				ylfxBbReport.setCustNo(String.valueOf(result[2]));
				ylfxBbReport.setCustName(String.valueOf(result[10]));
				ylfxBbReport.setOpnDate(String.valueOf(result[11]));
				ylfxBbReport.setMtrDate(String.valueOf(result[12]));
				ylfxBbReport.setRate(result[15] == null ? 0 : Double.valueOf(String.valueOf(result[15])));
				ylfxBbReport.setFtp(result[14] == null ? 0 : Double.valueOf(String.valueOf(result[14])));//���Ϊnull��ֵΪ-999
				ylfxBbReport.setRjye(rjye);//�վ����
				ylfxBbReport.setFtplr(ftplr);//FTP����
				ylfxBbReport.setBal(bal);//���
				ylfxBbReport.setLc(rjye == 0 ? 0.00 : ftplr / rjye * 360 / days);//����(������)
				ylfxBbReport.setBusinessName(result[13]);
				ylfxBbReportList.add(ylfxBbReport);
			}
		}
		
		//��ylfxBbReportList��FTP�����С���н�������
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
			 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				 return arg1.getFtplr().compareTo(arg0.getFtplr());
				 }
			 }
		);
		int rowsCount=ylfxBbReportList.size();
		QueryPageBO queryPageBo = new QueryPageBO();
		String pageLine=queryPageBo.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		if(isAll){
			return new PageUtil(ylfxBbReportList,pageLine);
		}else{
			if(currentPage*pageSize<rowsCount){
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,currentPage*pageSize),pageLine);
			}else {
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,rowsCount),pageLine);
			}
		}
		
	}
    
    /**
     * ���пͻ�����ͳ�Ʊ���
     * @param request
     * @param minDate
     * @param date
     * @param brNo
     * @param manageLvl
     * @param assessScope
     * @param businessNo
     * @param prdtCtgNo
     * @param prdtNo
     * @param pageName
     * @param pageSize
     * @param currentPage
     * @param isAll
     * @return
     */
    public PageUtil allEmpDetailProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, Integer assessScope,String businessNo,String prdtCtgNo,String prdtNo,
    		String pageName, int pageSize, int currentPage, boolean isAll) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		
		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");

		String brNos = this.getAllChildBrAllbrno(brNo, manageLvl,telMst.getTelNo());//��ȡ���е��Ӽ������������Լ�

		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// ������
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultListEmpAll(xlsBrNo, minDate, date,assessScope,brNos);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//����
		
		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		
		//����ҵ�����߱�Ż��߲�Ʒ������߲�Ʒ��Ż�ȡ��Ӧ�Ĳ�Ʒ���
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = this.getFtpBSDivideList(xlsBrNo, businessNo, prdtCtgNo, prdtNo);
		String prdtNos = "";
		for(FtpBusinessStaticDivide ftpBusinessStaticDivide : ftpBusinessStaticDivideList) {
			prdtNos += ftpBusinessStaticDivide.getProductNo()+",";
		}

		Map<String,String>  brNameMap= FtpUtil.getBrNameMap();
		//��ȡ�û����µ����пͻ�����
		String hsql1 = "from FtpEmpInfo where brMst.brNo "+this.getAllChildBrAllbrno(brNo, manageLvl, telMst.getTelNo())+"";
		List<FtpEmpInfo> empList = daoFactory.query(hsql1, null);
		Map<String, String> ftpEmpMap = new HashMap<String, String>();
		for(FtpEmpInfo ftpEmpInfo : empList) {
			ftpEmpMap.put(ftpEmpInfo.getEmpNo(), ftpEmpInfo.getPostNo()+"-"+ftpEmpInfo.getEmpName());
		}
		//�ӽ�����л�ȡ�����ͻ���������ݣ����ݿͻ���-�˺Ż��ܵ�map��
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[16]);//��¼�Ƿ�����ͳ��,�ֶԹ��Ͷ�˽
			
			String empInfo = ftpEmpMap.get(result[2]) == null ? "":ftpEmpMap.get(result[2]);
			String[] empInfos = empInfo.split("-");
			//�ͻ������EMP_NO��Ϊ����POST_NO=4(���������пͻ�����) �� ��Ҫͳ�ƵĲ�Ʒ
			if(!result[2].equals("")&&empInfos[0].equals("4")&&isStatic && prdtNos.indexOf(result[1])!=-1) {
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[8]);

				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(result[2]);
				ylfxBbReport.setEmpName(empInfos[1]);
				ylfxBbReport.setBrNo(result[0]);
				ylfxBbReport.setBrName(brNameMap.get(result[0]));
				ylfxBbReport.setAcId(String.valueOf(result[5]));
				ylfxBbReport.setPrdtName(String.valueOf(result[9]));
				ylfxBbReport.setCustNo(String.valueOf(result[2]));
				ylfxBbReport.setCustName(String.valueOf(result[10]));
				ylfxBbReport.setOpnDate(String.valueOf(result[11]));
				ylfxBbReport.setMtrDate(String.valueOf(result[12]));
				ylfxBbReport.setRate(result[15] == null ? 0 : Double.valueOf(String.valueOf(result[15])));
				ylfxBbReport.setFtp(result[14] == null ? 0 : Double.valueOf(String.valueOf(result[14])));//���Ϊnull��ֵΪ-999
				ylfxBbReport.setRjye(rjye);//�վ����
				ylfxBbReport.setFtplr(ftplr);//FTP����
				ylfxBbReport.setBal(bal);//���
				ylfxBbReport.setLc(rjye == 0 ? 0.00 : ftplr / rjye * 360 / days);//����(������)
				ylfxBbReport.setBusinessName(result[13]);
				ylfxBbReportList.add(ylfxBbReport);
			}
		}
		//��ftpEmpLrList���ͻ������������ٰ�FTP��������
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
			public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				if (arg0.getEmpNo().compareTo(arg1.getEmpNo()) == 0) {
					return Double.valueOf(arg0.getFtplr()).compareTo(
							Double.valueOf(arg1.getFtplr()));
				} else {
					return arg0.getEmpNo().compareTo(arg1.getEmpNo());
				}
			}
		});

		int rowsCount = ylfxBbReportList.size();
		QueryPageBO queryPageBo = new QueryPageBO();
		String pageLine=queryPageBo.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		if(isAll){
			return new PageUtil(ylfxBbReportList,pageLine);
		}else{
			if(currentPage*pageSize<rowsCount){
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,currentPage*pageSize),pageLine);
			}else {
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,rowsCount),pageLine);
			}
		}
	}
    /**
	 * ���пͻ�����ͳ�Ʊ���-����
	 * @param reportList
	 * @param empName
	 * @param empNo
	 * @param minDate
	 * @param maxDate
	 * @param title
	 * @return
	 */
	public HSSFWorkbook getYhkhjltjbbWorkbook(List<YlfxBbReport> reportList, String brName , String minDate, String maxDate, String title) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheet = workbook.createSheet(title);//����һ��sheet
		try {
	        // ��ͷ�ϲ�
			sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 14 ));// �ϲ���һ�е�һ��15��
			sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 14 ));// �ϲ��ڶ��е�һ��15��
			//���õ�һ�л�����Ԫ��Ŀ��
	        sheet.setColumnWidth(0, 20*2*256);//���ȳ���2��Ϊ�˽���������п�Ȳ������ʾ��ѧ���������⣬����256�õ������ݲ���excel��ʵ�п�
			
			ExcelExport excelExport = new ExcelExport(workbook, sheet);
			excelExport.createRow(0); // ������һ��
			excelExport.setCell(0, title, excelExport.getHeaderStyle());
			
			excelExport.createRow(1); // �����ڶ���
			excelExport.setCell(0, "������"+brName+"        ����ʱ�Σ�"+CommonFunctions.dateModifyD(minDate,1)+"-"+maxDate+"         ��λ��Ԫ��%(������)", excelExport.getTitleStyle());
			
			excelExport.createRow(2);//�����У���ͷ��
			sheet.setColumnWidth(0, 7*256);//���õ�һ�еĿ��   
			sheet.setColumnWidth(1, 25*256);//���õ�2�еĿ��   
			sheet.setColumnWidth(3, 15*256);//���õ�4�еĿ��   
			sheet.setColumnWidth(4, 30*256);//���õ�5�еĿ��  
			sheet.setColumnWidth(5, 25*256);//���õ�6�еĿ��   
			sheet.setColumnWidth(6, 12*256);//���õ�7�еĿ��  
			sheet.setColumnWidth(8, 20*256);//���õھ��еĿ��   
			sheet.setColumnWidth(9, 20*256);//���õ�10�еĿ��  
			sheet.setColumnWidth(10, 20*256);//���õ�11�еĿ��  
			excelExport.setCell(0, "���", excelExport.headCenterNormalStyle);
			excelExport.setCell(1, "��������", excelExport.headCenterNormalStyle);
			excelExport.setCell(2, "�ͻ���������", excelExport.headCenterNormalStyle);
			excelExport.setCell(3, "�˺�", excelExport.headCenterNormalStyle);
			excelExport.setCell(4, "��Ʒ����", excelExport.headCenterNormalStyle);
			excelExport.setCell(5, "�ͻ�����", excelExport.headCenterNormalStyle);
			excelExport.setCell(6, "��������", excelExport.headCenterNormalStyle);
			excelExport.setCell(7, "��������", excelExport.headCenterNormalStyle);
			excelExport.setCell(8, "���", excelExport.headCenterNormalStyle);
			excelExport.setCell(9, "�վ����", excelExport.headCenterNormalStyle);
			excelExport.setCell(10, "�վ�������", excelExport.headCenterNormalStyle);
			excelExport.setCell(11, "���ʣ�%��", excelExport.headCenterNormalStyle);
			excelExport.setCell(12, "FTP�۸�%��", excelExport.headCenterNormalStyle);
			excelExport.setCell(13, "���%��", excelExport.headCenterNormalStyle);
			excelExport.setCell(14, "FTP����", excelExport.headCenterNormalStyle);
			double zftplr = 0,rjye=0, ftplr=0,ratefz=0,ftpfz=0,bal=0;
			int days = CommonFunctions.daysSubtract(maxDate, minDate);//����
			if(reportList != null) {
				for(YlfxBbReport ylfxBbReport : reportList) {
					zftplr += ylfxBbReport.getFtplr();
				}
				for (int j = 0; j < reportList.size(); j++) {
					YlfxBbReport entity = reportList.get(j);
					rjye += entity.getRjye();
		        	ftplr += entity.getFtplr();
		        	ratefz += entity.getRjye()*entity.getRate();
		        	ftpfz += entity.getRjye()*(entity.getFtp()==-999?0:entity.getFtp());
		        	bal+=entity.getBal();
					//ѭ������������
					excelExport.createRow(j+3);
					excelExport.setCell(0, j+1, excelExport.centerNormalStyle);
					excelExport.setCell(1, entity.getBrName()+"["+entity.getBrNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(2, entity.getEmpName()+"["+entity.getEmpNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(3, entity.getAcId(), excelExport.centerNormalStyle);
					excelExport.setCell(4, entity.getPrdtName(), excelExport.centerNormalStyle);
					excelExport.setCell(5, entity.getCustName(), excelExport.centerNormalStyle);
					excelExport.setCell(6,(entity.getOpnDate().equals("")||entity.getOpnDate()==null||entity.getOpnDate().equals("null"))?"-":entity.getOpnDate(), excelExport.centerNormalStyle);
					excelExport.setCell(7, (entity.getMtrDate().equals("")||entity.getMtrDate()==null||entity.getMtrDate().equals("null"))?"-":entity.getMtrDate(), excelExport.centerNormalStyle);
					excelExport.setCell(8, entity.getBal(), excelExport.rightNormalMoneyStyle);
					excelExport.setCell(9, entity.getRjye(), excelExport.rightNormalMoneyStyle);
					excelExport.setCell(10, entity.getRjye()*days, excelExport.rightNormalMoneyStyle);
					excelExport.setCell(11, CommonFunctions.doublecut(entity.getRate()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(12, entity.getFtp()==-999?0:CommonFunctions.doublecut(entity.getFtp()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(13, CommonFunctions.doublecut(entity.getLc()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(14, entity.getFtplr(), excelExport.rightNormalMoneyStyle);
					
					
				}
				//�ϼ���
				//int days = CommonFunctions.daysSubtract(maxDate, minDate);//����
				excelExport.createRow(reportList.size()+3);
				excelExport.setCell(0, "�ϼ�", excelExport.centerBoldStyle);
				excelExport.setCell(1, "-", excelExport.centerBoldStyle);
				excelExport.setCell(2, "-", excelExport.centerBoldStyle);
				excelExport.setCell(3, "-", excelExport.centerBoldStyle);
				excelExport.setCell(4, "-", excelExport.centerBoldStyle);
				excelExport.setCell(5, "-", excelExport.centerBoldStyle);
				excelExport.setCell(6, "-", excelExport.centerBoldStyle);
				excelExport.setCell(7, "-", excelExport.centerBoldStyle);
				excelExport.setCell(8, bal, excelExport.rightBoldMoneyStyle);
				excelExport.setCell(9, rjye, excelExport.rightBoldMoneyStyle);
				excelExport.setCell(10, CommonFunctions.doublecut(rjye*days,3), excelExport.rightBoldMoneyStyle);
				excelExport.setCell(11, CommonFunctions.doublecut(ratefz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(12, CommonFunctions.doublecut(ftpfz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(13, CommonFunctions.doublecut(ftplr/rjye*360/days*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(14, ftplr, excelExport.rightBoldMoneyStyle);
				
			}
			
			
			sheet.getRow(0).setHeight((short)500);//���õ�һ�б��߶�
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
	}
    /**
	 * �ͻ���������������
	 * @param request
	 * @param minDate
	 * @param date
	 * @param brNo
	 * @param assessScope
	 * @return
	 */
    public List<YlfxBbReport> brEmpOrderProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, Integer assessScope) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
	    TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(brNo, manageLvl);// ������
		//�ӻ����л�ȡ����
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, date,assessScope);
		if(ftpResultsList == null) return null;
		
		int days = CommonFunctions.daysSubtract(date, minDate);//����
		String brNos = this.getAllChildBrAllbrno(brNo, manageLvl, telMst.getTelNo());
		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		
		Map<String, Double> rjyejlMap = new HashMap<String, Double>();//key:�ͻ�������
		Map<String, Double> ftplrjlMap = new HashMap<String, Double>();//key:�ͻ�������
		Map<String, Double> balMap = new HashMap<String, Double>();//key:�ͻ�������

		Map<String,Double> ftplr_dg_map=new HashMap<String,Double>();//�Թ�
		Map<String,Double> ftplr_ds_map=new HashMap<String,Double>();//��˽

		Map<String,List<String>> prdtCtgMap = getPrdtCtgMap();

		Map<String,Double> grhqrjye_map=new HashMap<String,Double>();//���˻����վ����map
		Map<String,Double> grdqrjye_map=new HashMap<String,Double>();//���˶����վ����map
		Map<String,Double> dwhqrjye_map=new HashMap<String,Double>();//��λ�����վ����map
		Map<String,Double> dwdqrjye_map=new HashMap<String,Double>();//��λ�����վ����map
		Map<String,Double> yhkrjye_map=new HashMap<String,Double>();//���п��վ����map
		Map<String,Double> czxrjye_map=new HashMap<String,Double>();//�����Դ���վ����map
		Map<String,Double> yjhkrjye_map=new HashMap<String,Double>();//Ӧ�����վ����map
		Map<String,Double> bzjrjye_map=new HashMap<String,Double>();//��֤���վ����map

		Map<String,Double> grhqbal_map=new HashMap<String,Double>();//���˻������map
		Map<String,Double> grdqbal_map=new HashMap<String,Double>();//���˶������map
		Map<String,Double> dwhqbal_map=new HashMap<String,Double>();//��λ�������map
		Map<String,Double> dwdqbal_map=new HashMap<String,Double>();//��λ�������map
		Map<String,Double> yhkbal_map=new HashMap<String,Double>();//���п����map
		Map<String,Double> czxbal_map=new HashMap<String,Double>();//�����Դ�����map
		Map<String,Double> yjhkbal_map=new HashMap<String,Double>();//Ӧ�������map
		Map<String,Double> bzjbal_map=new HashMap<String,Double>();//��֤�����map

		Map<String,Double> grdkftplr_map=new HashMap<String,Double>();//���˴���ftp����map
		Map<String,Double> gsdkftplr_map=new HashMap<String,Double>();//��˾����ftp����map
		Map<String,Double> grhqftplr_map=new HashMap<String,Double>();//���˻���ftp����map
		Map<String,Double> grdqftplr_map=new HashMap<String,Double>();//���˶���ftp����map
		Map<String,Double> dwhqftplr_map=new HashMap<String,Double>();//��λ����ftp����map
		Map<String,Double> dwdqftplr_map=new HashMap<String,Double>();//��λ����ftp����map
		Map<String,Double> yhkftplr_map=new HashMap<String,Double>();//���п�ftp����map
		Map<String,Double> czxftplr_map=new HashMap<String,Double>();//�����Դ��ftp����map
		Map<String,Double> yjhkftplr_map=new HashMap<String,Double>();//Ӧ����ftp����map
		Map<String,Double> bzjftplr_map=new HashMap<String,Double>();//��֤��ftp����map


		//�ӽ�����л�ȡĳЩ�ͻ���������ݣ����ݿͻ������Ż��ܵ�map��
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//��¼�Ƿ�����ͳ��,�ֶԹ��Ͷ�˽
			if (brNos.indexOf(result[0]) != -1 && isStatic) {
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[6]);
				//�վ����
				if (rjyejlMap.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
					rjyejlMap.put(result[2] + "-" + result[1].substring(1, 2), rjye);
				} else {
					rjyejlMap.put(result[2] + "-" + result[1].substring(1, 2), rjye + rjyejlMap.get(result[2] + "-" + result[1].substring(1, 2)));
				}
				//FTP����
				if (ftplrjlMap.get(result[2]) == null) {
					ftplrjlMap.put(result[2], ftplr);
				} else {
					ftplrjlMap.put(result[2], ftplr + ftplrjlMap.get(result[2]));
				}
				//FTP���
				if (balMap.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
					balMap.put(result[2] + "-" + result[1].substring(1, 2), bal);
				} else {
					balMap.put(result[2] + "-" + result[1].substring(1, 2), bal + balMap.get(result[2] + "-" + result[1].substring(1, 2)));
				}

				if (prdtCtgMap.get("grhq").contains(result[1])) {
					if (grhqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						grhqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						grhqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + grhqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (grhqbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						grhqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						grhqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + grhqbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (grhqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						grhqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						grhqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + grhqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("grdq").contains(result[1])) {
					if (grdqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						grdqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						grdqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + grdqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (grdqbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						grdqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						grdqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + grdqbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (grdqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						grdqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						grdqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + grdqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("dwhq").contains(result[1])) {
					if (dwhqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						dwhqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						dwhqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + dwhqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (dwhqbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						dwhqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						dwhqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + dwhqbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (dwhqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						dwhqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						dwhqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + dwhqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("dwdq").contains(result[1])) {
					if (dwdqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						dwdqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						dwdqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + dwdqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (dwdqbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						dwdqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						dwdqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + dwdqbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (dwdqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						dwdqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						dwdqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + dwdqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}


				if (prdtCtgMap.get("yhk").contains(result[1])) {
					if (yhkrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						yhkrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						yhkrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + yhkrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (yhkbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						yhkbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						yhkbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + yhkbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (yhkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						yhkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						yhkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + yhkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

				}

				if (prdtCtgMap.get("czx").contains(result[1])) {
					if (czxrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						czxrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						czxrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + czxrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (czxbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						czxbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						czxbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + czxbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (czxftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						czxftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						czxftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + czxftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("yjhk").contains(result[1])) {
					if (yjhkrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						yjhkrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						yjhkrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + yjhkrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (yjhkbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						yjhkbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						yjhkbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + yjhkbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (yjhkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						yjhkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						yjhkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + yjhkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("bzj").contains(result[1])) {
					if (bzjrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						bzjrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						bzjrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + bzjrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (bzjbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						bzjbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						bzjbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + bzjbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (bzjftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						bzjftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						bzjftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + bzjftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}

				boolean isds = this.getIsStatic("1", result[5], result[7]);//��˽ͳ��
				if (isds) {
					if (ftplr_ds_map.get(result[2]) == null) {
						ftplr_ds_map.put(result[2], ftplr);
					} else {
						ftplr_ds_map.put(result[2], ftplr + ftplr_ds_map.get(result[2]));
					}
				}
				boolean isdg = this.getIsStatic("2", result[5], result[7]);//�Թ�ͳ��
				if (isdg) {
					//FTP����
					if (ftplr_dg_map.get(result[2]) == null) {
						ftplr_dg_map.put(result[2], ftplr);
					} else {
						ftplr_dg_map.put(result[2], ftplr + ftplr_dg_map.get(result[2]));
					}
				}


				boolean isdsdk = this.getIsStaticDk("1", result[5], result[7]);//��˽ͳ��
				if (isdsdk) {
					if (grdkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						grdkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						grdkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + grdkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}
				boolean isdgdk = this.getIsStaticDk("2", result[5], result[7]);//�Թ�ͳ��
				if (isdgdk) {
					//FTP����
					if (gsdkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						gsdkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						gsdkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + gsdkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}
			}

		}
		//��ȡ�û����µ����пͻ�����
		String hsql1 = "from FtpEmpInfo where brMst.brNo  "+brNos+"";
		List<FtpEmpInfo> empList = daoFactory.query(hsql1, null);
        //ѭ���ͻ�������ȡҪͳ�Ƶ�ֵ
		for(FtpEmpInfo ftpEmpInfo : empList) {
			double zcrjye = rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-1")==null?0:rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-1");
			double fzrjye = rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-2");

			double zcye = balMap.get(ftpEmpInfo.getEmpNo()+"-1")==null?0:balMap.get(ftpEmpInfo.getEmpNo()+"-1");
			double fzye = balMap.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:balMap.get(ftpEmpInfo.getEmpNo()+"-2");

			double grhqbal = grhqbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:grhqbal_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double grdqbal = grdqbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:grdqbal_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double dwhqbal = dwhqbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:dwhqbal_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double dwdqbal = dwdqbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:dwdqbal_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double czxbal = czxbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:czxbal_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double yhkbal = yhkbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:yhkbal_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double yjhkbal = yjhkbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:yjhkbal_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double bzjbal = bzjbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:bzjbal_map.get(ftpEmpInfo.getEmpNo()+"-2");

			double grhqrjye = grhqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:grhqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double grdqrjye = grdqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:grdqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double dwhqrjye = dwhqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:dwhqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double dwdqrjye = dwdqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:dwdqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double czxrjye = czxrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:czxrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double yhkrjye = yhkrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:yhkrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double yjhkrjye = yjhkrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:yjhkrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double bzjrjye = bzjrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:bzjrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double ftplr = ftplrjlMap.get(ftpEmpInfo.getEmpNo())==null?0:ftplrjlMap.get(ftpEmpInfo.getEmpNo());
			double dsftplr = ftplr_ds_map.get(ftpEmpInfo.getEmpNo())==null?0:ftplr_ds_map.get(ftpEmpInfo.getEmpNo());
			double dgftplr = ftplr_dg_map.get(ftpEmpInfo.getEmpNo())==null?0:ftplr_dg_map.get(ftpEmpInfo.getEmpNo());
			double grhqftplr = grhqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:grhqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double grdqftplr = grdqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:grdqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double dwhqftplr = dwhqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:dwhqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double dwdqftplr = dwdqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:dwdqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double czxftplr = czxftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:czxftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double yhkftplr = yhkftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:yhkftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double yjhkftplr = yjhkftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:yjhkftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double bzjftplr = bzjftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:bzjftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double grdkftplr = grdkftplr_map.get(ftpEmpInfo.getEmpNo()+"-1")==null?0:grdkftplr_map.get(ftpEmpInfo.getEmpNo()+"-1");
			double gsdkftplr = gsdkftplr_map.get(ftpEmpInfo.getEmpNo()+"-1")==null?0:gsdkftplr_map.get(ftpEmpInfo.getEmpNo()+"-1");



			//ֻ��ʾ��ֵ��
			if((zcrjye!=0||fzrjye!=0)) {
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(ftpEmpInfo.getEmpNo());
				ylfxBbReport.setEmpName(ftpEmpInfo.getEmpName());
				ylfxBbReport.setBrNo(ftpEmpInfo.getBrMst().getBrNo());
				ylfxBbReport.setBrName(ftpEmpInfo.getBrMst().getBrName());
				ylfxBbReport.setZcrjye(zcrjye);//�ʲ��վ����
				ylfxBbReport.setFzrjye(fzrjye);//��ծ�վ����
				ylfxBbReport.setGrhqrjye(grhqrjye);
				ylfxBbReport.setGrdqrjye(grdqrjye);
				ylfxBbReport.setDwhqrjye(dwhqrjye);
				ylfxBbReport.setDwdqrjye(dwdqrjye);
				ylfxBbReport.setCzxrjye(czxrjye);
				ylfxBbReport.setYhkrjye(yhkrjye);
				ylfxBbReport.setYjhkrjye(yjhkrjye);
				ylfxBbReport.setBzjrjye(bzjrjye);

				ylfxBbReport.setZcbal(zcye);//�ʲ����
				ylfxBbReport.setFzbal(fzye);//��ծ���
				ylfxBbReport.setGrhqbal(grhqbal);
				ylfxBbReport.setGrdqbal(grdqbal);
				ylfxBbReport.setDwhqbal(dwhqbal);
				ylfxBbReport.setDwdqbal(dwdqbal);
				ylfxBbReport.setCzxbal(czxbal);
				ylfxBbReport.setYhkbal(yhkbal);
				ylfxBbReport.setYjhkbal(yjhkbal);
				ylfxBbReport.setBzjbal(bzjbal);


				ylfxBbReport.setGrhqftplr(grhqftplr);
				ylfxBbReport.setGrdqftplr(grdqftplr);
				ylfxBbReport.setDwhqftplr(dwhqftplr);
				ylfxBbReport.setDwdqftplr(dwdqftplr);
				ylfxBbReport.setCzxftplr(czxftplr);
				ylfxBbReport.setYhkftplr(yhkftplr);
				ylfxBbReport.setYjhkftplr(yjhkftplr);
				ylfxBbReport.setBzjftplr(bzjftplr);

				ylfxBbReport.setGrdkftplr(grdkftplr);
				ylfxBbReport.setGsdkftplr(gsdkftplr);

				ylfxBbReport.setDsftplr(dsftplr);
				ylfxBbReport.setDgftplr(dgftplr);

				ylfxBbReport.setFtplr(ftplr);//FTP����
				ylfxBbReport.setLc((zcrjye+fzrjye)==0?0.00:ftplr/(zcrjye+fzrjye)*360/days);//����(������)
				
				ylfxBbReportList.add(ylfxBbReport);
			}
		}
		//��ylfxBbReportList��FTP�����С���н�������
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
			 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				 return arg1.getFtplr().compareTo(arg0.getFtplr());
				 }
			 }
		);
	   return ylfxBbReportList;
	}
    /**
	 * ���˰��һ���FTP�������
	 * @param request
	 * @param minDate
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @param businessNo
	 * @param prdtCtgNo
	 * @param prdtNo
	 * @param isMx
	 * @return
	 */
    public List<YlfxBbReport> grajBrProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, Integer assessScope, Integer isMx) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();

		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(brNo, manageLvl);// ������
		TelMst telMst=(TelMst)request.getSession().getAttribute("userBean");
		String brNos = this.getAllChildBrAllbrno(brNo, manageLvl,telMst.getTelNo());//��ȡ���е��Ӽ������������Լ�
	    System.out.println(manageLvl+"!!!"+brNo);
		System.out.println(","+xlsBrNo+","+minDate+","+date);
		//�ӻ����л�ȡ����
		List<String[]> ftpResultsList =getQxppResultListMortgage(xlsBrNo, minDate, date,assessScope,brNos,null);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//����
		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		
		//����Ĭ����ʾ�¼�����|֧��Ĭ����ʾ֧�У������ϸ����ʾ�¼�
		if (isMx == 0 && !manageLvl.equals("3")&&!manageLvl.equals("2")) {// ������ǲ鿴�ӻ�����ӯ����������ֱ�Ӳ鿴�û�����ӯ������
			YlfxBbReport ylfxBbReport = new YlfxBbReport();
			String brSql = LrmUtil.getBrSqlByTel(brNo, telMst.getTelNo());
			ylfxBbReport.setBrName(brInfoDAO.getInfo(brNo).getBrName());
			ylfxBbReport.setBrNo(brNo);
			ylfxBbReport.setManageLvl(manageLvl);
			ylfxBbReport.setLeaf(this.getIsLeaf(brNo));
			double[] resultValue = this.getQxppValueMap_grajjglrbhz(ftpResultsList, brSql, custType);
			ylfxBbReport.setRjye(resultValue[0]);//�վ����
			ylfxBbReport.setFtplr(resultValue[1]);//FTP����
			ylfxBbReport.setBal(resultValue[2]);//FTP���
			ylfxBbReport.setLc(resultValue[0]==0?0.00:resultValue[1]/resultValue[0]*360/days);//����(������)
			ylfxBbReportList.add(ylfxBbReport);
		}else {
			// ��ȡ�û������¼�����
			List<BrMst> brMstList = getChildBrListByTel(brNo, telMst.getTelNo());
			Map<String,Double[]> QxppValue_map = this.getQxppValueMap_grajjglrb(ftpResultsList, xlsBrNo, brMstList, custType, telMst.getTelNo());
			//ѭ����������ȡ��Ӧ��Ҫͳ�ƵĲ�Ʒ��ֵ
			for(BrMst brMst : brMstList) {
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setBrNo(brMst.getBrNo());
				ylfxBbReport.setBrName(brMst.getBrName());
				ylfxBbReport.setManageLvl(brMst.getManageLvl());
				ylfxBbReport.setLeaf(this.getIsLeaf(brMst.getBrNo()));
				double rjye = 0.0,ftplr = 0.0,bal=0;
				Double[] values=QxppValue_map.get(brMst.getBrNo());
				rjye += (values==null||values[0]==null)?0.0:values[0];
				ftplr += (values==null||values[1]==null)?0.0:values[1];
				bal += (values==null||values[2]==null)?0.0:values[2];

				ylfxBbReport.setBal(bal);//���
				ylfxBbReport.setRjye(rjye);//�վ����
				ylfxBbReport.setFtplr(ftplr);//FTP����
				ylfxBbReport.setLc(rjye==0?0.00:ftplr/rjye*360/days);//����(������)
				
				ylfxBbReportList.add(ylfxBbReport);
			}
			//��ylfxBbReportList��FTP�����С���н�������
			Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
				 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
					 return arg1.getFtplr().compareTo(arg0.getFtplr());
					 }
				 }
			);
		}
		
	   return ylfxBbReportList;
	}
    
    /**
     * ���˰��ҿͻ�����FTP������ϸ��
     * @param request
     * @param minDate
     * @param date
	 * @param brNo
	 * @param manageLvl
     * @param empNo
     * @param assessScope
     * @param pageName
     * @param pageSize
     * @param currentPage
     * @param isAll
     * @return
     */
    public PageUtil grajEmpDetailProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, String empNo, Integer assessScope,
    		String pageName, int pageSize, int currentPage, boolean isAll) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		
		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// ������
		String brNos = this.getAllChildBr(brNo, manageLvl,telMst.getTelNo());//��ȡ���е��Ӽ������������Լ�
		//�ӻ����л�ȡ����
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList =getQxppResultListMortgage(xlsBrNo, minDate, date,assessScope,brNos,empNo);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//����

		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());

		Map<String,String>  brNameMap= FtpUtil.getBrNameMap();
		Map<String,String>  empNameMap= FtpUtil.getempNameMap();
		//�ӽ�����л�ȡ�����ͻ���������ݣ����ݿͻ���-�˺Ż��ܵ�map��
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[16]);//��¼�Ƿ�����ͳ��,�ֶԹ��Ͷ�˽
			if(!result[2].equals("")&&isStatic) {
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[8]);

				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(result[2]);
				ylfxBbReport.setEmpName(empNameMap.get(result[2]));
				ylfxBbReport.setBrNo(result[0]);
				ylfxBbReport.setBrName(brNameMap.get(result[0]));
				ylfxBbReport.setAcId(String.valueOf(result[5]));
				ylfxBbReport.setPrdtName(String.valueOf(result[9]));
				ylfxBbReport.setCustNo(String.valueOf(result[2]));
				ylfxBbReport.setCustName(String.valueOf(result[10]));
				ylfxBbReport.setOpnDate(String.valueOf(result[11]));
				ylfxBbReport.setMtrDate(String.valueOf(result[12]));
				ylfxBbReport.setRate(result[15] == null ? 0 : Double.valueOf(String.valueOf(result[15])));
				ylfxBbReport.setFtp(result[14] == null ? 0 : Double.valueOf(String.valueOf(result[14])));//���Ϊnull��ֵΪ-999
				ylfxBbReport.setRjye(rjye);//�վ����
				ylfxBbReport.setFtplr(ftplr);//FTP����
				ylfxBbReport.setBal(bal);//���
				ylfxBbReport.setLc(rjye == 0 ? 0.00 : ftplr / rjye * 360 / days);//����(������)
				ylfxBbReport.setBusinessName(result[13]);
				ylfxBbReportList.add(ylfxBbReport);
			}
		}

		//��ylfxBbReportList��FTP�����С���н�������
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
					public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
						return arg1.getFtplr().compareTo(arg0.getFtplr());
					}
				}
		);
		int rowsCount=ylfxBbReportList.size();
		QueryPageBO queryPageBo = new QueryPageBO();
		String pageLine=queryPageBo.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		if(isAll){
			return new PageUtil(ylfxBbReportList,pageLine);
		}else{
			if(currentPage*pageSize<rowsCount){
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,currentPage*pageSize),pageLine);
			}else {
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,rowsCount),pageLine);
			}
		}
	}
    /**
	 * ���˰��ҿͻ�����FTP������ϸ��-����
	 * @param reportList
	 * @param brName
	 * @param empName
	 * @param empNo
	 * @param minDate
	 * @param maxDate
	 * @param title
	 * @return
	 */
	public HSSFWorkbook getGrajkhjllrbWorkbook(List<YlfxBbReport> reportList, String brName, String empName, String empNo, String minDate, String maxDate, String title) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheet = workbook.createSheet(title);//����һ��sheet
		try {
	        // ��ͷ�ϲ�
			sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 13 ));// �ϲ���һ�е�һ��16��
			sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 13 ));// �ϲ��ڶ��е�һ��16��
			//���õ�һ�л�����Ԫ��Ŀ��
	        sheet.setColumnWidth(0, 20*2*256);//���ȳ���2��Ϊ�˽���������п�Ȳ������ʾ��ѧ���������⣬����256�õ������ݲ���excel��ʵ�п�
			
			ExcelExport excelExport = new ExcelExport(workbook, sheet);
			excelExport.createRow(0); // ������һ��
			excelExport.setCell(0, title, excelExport.getHeaderStyle());
			
			excelExport.createRow(1); // �����ڶ���
			excelExport.setCell(0, "������"+brName+" "+(empNo!=null&&!empNo.equals("")?"       �ͻ�����"+empName+"["+ empNo+"]":"")+"       ����ʱ�Σ�"+CommonFunctions.dateModifyD(minDate,1)+"-"+maxDate+"         ��λ��Ԫ��%(������)", excelExport.getTitleStyle());
			
			excelExport.createRow(2);//�����У���ͷ��
			sheet.setColumnWidth(0, 7*256);//���õ�һ�еĿ��
			sheet.setColumnWidth(1, 50*256);//���õ�2�еĿ��  
			sheet.setColumnWidth(2, 40*256);//���õ�3�еĿ��     
			sheet.setColumnWidth(5, 35*256);//���õ�6�еĿ��  
			sheet.setColumnWidth(6, 25*256);//���õ�7�еĿ��   
			sheet.setColumnWidth(9, 20*256);//���õ�10�еĿ��  
			sheet.setColumnWidth(11, 20*256);//���õ�12�еĿ��   
			sheet.setColumnWidth(13, 20*256);//���õ�14�еĿ��  
			excelExport.setCell(0, "���", excelExport.headCenterNormalStyle);
			excelExport.setCell(1, "��������", excelExport.headCenterNormalStyle);
			excelExport.setCell(2, "�ͻ���������", excelExport.headCenterNormalStyle);
			excelExport.setCell(3, "�˺�", excelExport.headCenterNormalStyle);
			excelExport.setCell(4, "�ͻ�����", excelExport.headCenterNormalStyle);
			excelExport.setCell(5, "��Ʒ����", excelExport.headCenterNormalStyle);
			excelExport.setCell(6, "��������", excelExport.headCenterNormalStyle);
			excelExport.setCell(7, "��������", excelExport.headCenterNormalStyle);
			excelExport.setCell(8, "���", excelExport.headCenterNormalStyle);
			excelExport.setCell(9, "�վ����", excelExport.headCenterNormalStyle);
//			excelExport.setCell(10, "�վ�������", excelExport.headCenterNormalStyle);
			excelExport.setCell(10, "����(%)", excelExport.headCenterNormalStyle);
			excelExport.setCell(11, "FTP�۸�(%)", excelExport.headCenterNormalStyle);
			excelExport.setCell(12, "����(%)", excelExport.headCenterNormalStyle);
			excelExport.setCell(13, "FTP����", excelExport.headCenterNormalStyle);
			double zftplr = 0,rjye=0, ftplr=0,ratefz=0,ftpfz=0,bal=0;
			//�ϼ���
			int days = CommonFunctions.daysSubtract(maxDate, minDate);//����
			if(reportList != null) {
				for(YlfxBbReport ylfxBbReport : reportList) {
					zftplr += ylfxBbReport.getFtplr();
				}
				for (int j = 0; j < reportList.size(); j++) {
					YlfxBbReport entity = reportList.get(j);
					rjye += entity.getRjye();
		        	ftplr += entity.getFtplr();
		        	ratefz += entity.getRjye()*entity.getRate();
		        	ftpfz += entity.getRjye()*(entity.getFtp()==-999?0:entity.getFtp());
		        	bal +=entity.getBal();
					//ѭ������������
					excelExport.createRow(j+3);
					excelExport.setCell(0, j+1, excelExport.centerNormalStyle);
					excelExport.setCell(1, entity.getBrName()+"["+entity.getBrNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(2, entity.getEmpName()+"["+entity.getEmpNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(3, entity.getAcId(), excelExport.centerNormalStyle);
					excelExport.setCell(4, entity.getPrdtName(), excelExport.centerNormalStyle);
					excelExport.setCell(5, entity.getCustName(), excelExport.centerNormalStyle);
					excelExport.setCell(6,(entity.getOpnDate().equals("")||entity.getOpnDate()==null||entity.getOpnDate().equals("null"))?"-":entity.getOpnDate(), excelExport.centerNormalStyle);
					excelExport.setCell(7, (entity.getMtrDate().equals("")||entity.getMtrDate()==null||entity.getMtrDate().equals("null"))?"-":entity.getMtrDate(), excelExport.centerNormalStyle);
					excelExport.setCell(8, entity.getBal(), excelExport.rightNormalMoneyStyle);
					excelExport.setCell(9, entity.getRjye(), excelExport.rightNormalMoneyStyle);
//					excelExport.setCell(10, entity.getRjye()*days, excelExport.rightNormalMoneyStyle);
					excelExport.setCell(10, CommonFunctions.doublecut(entity.getRate(),3), excelExport.rightNormalStyle);
					excelExport.setCell(11, entity.getFtp()==-999?0:CommonFunctions.doublecut(entity.getFtp()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(12, CommonFunctions.doublecut(entity.getLc()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(13, entity.getFtplr(), excelExport.rightNormalMoneyStyle);
					
				}
				excelExport.createRow(reportList.size()+3);
				excelExport.setCell(0, "�ϼ�", excelExport.centerBoldStyle);
				excelExport.setCell(1, "-", excelExport.centerBoldStyle);
				excelExport.setCell(2, "-", excelExport.centerBoldStyle);
				excelExport.setCell(3, "-", excelExport.centerBoldStyle);
				excelExport.setCell(4, "-", excelExport.centerBoldStyle);
				excelExport.setCell(5, "-", excelExport.centerBoldStyle);
				excelExport.setCell(6, "-", excelExport.centerBoldStyle);
				excelExport.setCell(7, "-", excelExport.centerBoldStyle);
				excelExport.setCell(8, bal, excelExport.rightBoldMoneyStyle);
				excelExport.setCell(9, rjye, excelExport.rightBoldMoneyStyle);
//				excelExport.setCell(10, CommonFunctions.doublecut(rjye*days,3), excelExport.rightBoldStyle);
				excelExport.setCell(10, CommonFunctions.doublecut(ratefz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(11, CommonFunctions.doublecut(ftpfz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(12, CommonFunctions.doublecut(ftplr/rjye*360/days*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(13, ftplr, excelExport.rightBoldMoneyStyle);
				
			}
			
			
			sheet.getRow(0).setHeight((short)500);//���õ�һ�б��߶�
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
	}
    /**
     * ��֤����ͻ�����FTP������ϸ��
     * @param request
     * @param minDate
     * @param date
	 * @param brNo
	 * @param manageLvl
     * @param empNo
     * @param assessScope
     * @param pageName
     * @param pageSize
     * @param currentPage
     * @param isAll
     * @return
     */
    public PageUtil bzjckEmpDetailProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, String empNo, Integer assessScope,
    		String pageName, int pageSize, int currentPage,boolean isAll) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();

		Map<String,String>  brNameMap= FtpUtil.getBrNameMap();

		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");

		Map<String,String>  empNameMap= FtpUtil.getempNameMap();

		String brNos = this.getAllChildBr(brNo, manageLvl,telMst.getTelNo());//��ȡ���е��Ӽ������������Լ�
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// ������
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultListEmpBzj(brNos, minDate, date,assessScope,empNo);
		if(ftpResultsList == null) return null;
		int days = CommonFunctions.daysSubtract(date, minDate);//����

		//��ȡ�û����µ����пͻ�����
		String hsql1 = "from FtpEmpInfo where brMst.brNo "+this.getAllChildBr(brNo, manageLvl, telMst.getTelNo())+"";
		List<FtpEmpInfo> empList = daoFactory.query(hsql1, null);
		Map<String, FtpEmpInfo> ftpEmpMap = new HashMap<String, FtpEmpInfo>();
		for(FtpEmpInfo ftpEmpInfo : empList) {
			ftpEmpMap.put(ftpEmpInfo.getEmpNo(),ftpEmpInfo);
		}

		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		
		//�ӽ�����л�ȡĳ���ͻ���������ݣ������˺Ż��ܵ�map��
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[16]);//��¼�Ƿ�����ͳ��,�ֶԹ��Ͷ�˽
			FtpEmpInfo empInfo = ftpEmpMap.get(result[2]) == null ? null:ftpEmpMap.get(result[2]);
			if(isStatic&&empInfo!=null) {//��Ҫͳ�ƵĿͻ��������ͬ����Ҫͳ�ƵĲ�Ʒ
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[8]);
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(result[2]);
//				ylfxBbReport.setEmpNo(empNo);
				ylfxBbReport.setEmpName(empInfo.getEmpName());
				ylfxBbReport.setBrNo(result[0]);
				ylfxBbReport.setBrName(brNameMap.get(result[0]));
				ylfxBbReport.setAcId(String.valueOf(result[5]));
				ylfxBbReport.setPrdtName(String.valueOf(result[9]));
				ylfxBbReport.setCustNo(String.valueOf(result[2]));
				ylfxBbReport.setCustName(String.valueOf(result[10]));
				ylfxBbReport.setOpnDate(String.valueOf(result[11]));
				ylfxBbReport.setMtrDate(String.valueOf(result[12]));
				ylfxBbReport.setRate(result[15] == null ? 0 : Double.valueOf(String.valueOf(result[15])));
				ylfxBbReport.setFtp(result[14] == null ? 0 : Double.valueOf(String.valueOf(result[14])));//���Ϊnull��ֵΪ-999
				ylfxBbReport.setRjye(rjye);//�վ����
				ylfxBbReport.setFtplr(ftplr);//FTP����
				ylfxBbReport.setBal(bal);//���
				ylfxBbReport.setLc(rjye == 0 ? 0.00 : ftplr / rjye * 360 / days);//����(������)
				ylfxBbReport.setBusinessName(result[13]);

				ylfxBbReportList.add(ylfxBbReport);
			}
		}
		int rowsCount = ylfxBbReportList.size();
		//��ylfxBbReportList��FTP�����С���н�������
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
					public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
						return arg1.getFtplr().compareTo(arg0.getFtplr());
					}
				}
		);
		QueryPageBO queryPageBo = new QueryPageBO();
		String pageLine=queryPageBo.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		if(isAll){
			return new PageUtil(ylfxBbReportList,pageLine);
		}else{
			if(currentPage*pageSize<rowsCount){
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,currentPage*pageSize),pageLine);
			}else {
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,rowsCount),pageLine);
			}
		}
	}
    /**
	 * ��֤����ͻ�����FTP�����-����
	 * @param reportList
	 * @param brName
	 * @param empName
	 * @param empNo
	 * @param minDate
	 * @param maxDate
	 * @param title
	 * @return
	 */

	/**
	 * �Թ�ҵ��FTP������ϸ��
	 * @param request
	 * @param minDate
	 * @param date
	 * @param empNo
	 * @param assessScope
	 * @param businessNo
	 * @param prdtCtgNo
	 * @param prdtNo
	 * @param pageName
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public PageUtil gsywbDetailProfile(HttpServletRequest request, String minDate, String date, String brNo, Integer assessScope,String businessNo,String prdtCtgNo,String prdtNo,
									 String pageName, int pageSize, int currentPage,String manageLvl,boolean isAll) {

		List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();

		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");

		String brNos = this.getAllChildBr(brNo, manageLvl,telMst.getTelNo());//��ȡ���е��Ӽ������������Լ�

		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// ������
		//�ӻ����л�ȡ����
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultListGsyw(xlsBrNo, minDate, date,assessScope,brNos);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//����

		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());

		//����ҵ�����߱�Ż��߲�Ʒ������߲�Ʒ��Ż�ȡ��Ӧ�Ĳ�Ʒ���
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = this.getFtpBSDivideList(xlsBrNo, businessNo, prdtCtgNo, prdtNo);
		String prdtNos = "";
		for(FtpBusinessStaticDivide ftpBusinessStaticDivide : ftpBusinessStaticDivideList) {
			prdtNos += ftpBusinessStaticDivide.getProductNo()+",";
		}
		Map<String,String>  brNameMap= FtpUtil.getBrNameMap();
		Map<String, Double> ftplrjlMap = new HashMap<String, Double>();//key:�˺�
		Map<String,String>  empNameMap= FtpUtil.getempNameMap();
		StringBuffer accIds = new StringBuffer();
		//�ӽ�����л�ȡĳ���ͻ���������ݣ������˺Ż��ܵ�map��
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[16]);//��¼�Ƿ�����ͳ��,�ֶԹ��Ͷ�˽
			if(isStatic && prdtNos.indexOf(result[1])!=-1) {//��Ҫͳ�ƵĿͻ��������ͬ����Ҫͳ�ƵĲ�Ʒ
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[8]);

				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setBrNo(result[0]);
				ylfxBbReport.setBrName(brNameMap.get(result[0]));
				ylfxBbReport.setEmpNo(result[2]);
				ylfxBbReport.setEmpName(empNameMap.get(result[2]));
				ylfxBbReport.setEmpName(empNameMap.get(result[2]));
				ylfxBbReport.setAcId(String.valueOf(result[5]));
				ylfxBbReport.setPrdtName(String.valueOf(result[9]));
				ylfxBbReport.setCustNo(String.valueOf(result[2]));
				ylfxBbReport.setCustName(String.valueOf(result[10]));
				ylfxBbReport.setOpnDate(String.valueOf(result[11]));
				ylfxBbReport.setMtrDate(String.valueOf(result[12]));
				ylfxBbReport.setRate(result[15] == null ? 0 : Double.valueOf(String.valueOf(result[15])));
				ylfxBbReport.setFtp(result[14] == null ? 0 : Double.valueOf(String.valueOf(result[14])));//���Ϊnull��ֵΪ-999
				ylfxBbReport.setRjye(rjye);//�վ����
				ylfxBbReport.setFtplr(ftplr);//FTP����
				ylfxBbReport.setBal(bal);//���
				ylfxBbReport.setLc(rjye == 0 ? 0.00 : ftplr / rjye * 360 / days);//����(������)
				ylfxBbReport.setBusinessName(result[13]);

				ylfxBbReportList.add(ylfxBbReport);


			}
		}
		int rowsCount = ylfxBbReportList.size();
		//��ylfxBbReportList��FTP�����С���н�������
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
					public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
						return arg1.getFtplr().compareTo(arg0.getFtplr());
					}
				}
		);
		QueryPageBO queryPageBo = new QueryPageBO();
		String pageLine=queryPageBo.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		if(isAll){
			return new PageUtil(ylfxBbReportList,pageLine);
		}else{
			if(currentPage*pageSize<rowsCount){
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,currentPage*pageSize),pageLine);
			}else {
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,rowsCount),pageLine);
			}
		}
		
	}

	/**
	 * ��˾ҵ��FTP������ϸ��-����
	 * @param reportList
	 * @param brName
	 * @param empName
	 * @param empNo
	 * @param minDate
	 * @param maxDate
	 * @param title
	 * @return
	 */
	public HSSFWorkbook getGsywblrmxbWorkbook(List<YlfxBbReport> reportList, String brName , String minDate, String maxDate, String title) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheet = workbook.createSheet(title);//����һ��sheet
		try {
	        // ��ͷ�ϲ�
			sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 15 ));// �ϲ���һ�е�һ��15��
			sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 15 ));// �ϲ��ڶ��е�һ��15��
			//���õ�һ�л�����Ԫ��Ŀ��
	        sheet.setColumnWidth(0, 20*2*256);//���ȳ���2��Ϊ�˽���������п�Ȳ������ʾ��ѧ���������⣬����256�õ������ݲ���excel��ʵ�п�
			
			ExcelExport excelExport = new ExcelExport(workbook, sheet);
			excelExport.createRow(0); // ������һ��
			excelExport.setCell(0, title, excelExport.getHeaderStyle());
			
			excelExport.createRow(1); // �����ڶ���
			excelExport.setCell(0, "������"+brName+"        ����ʱ�Σ�"+CommonFunctions.dateModifyD(minDate,1)+"-"+maxDate+"         ��λ��Ԫ��%(������)", excelExport.getTitleStyle());
			
			excelExport.createRow(2);//�����У���ͷ��
			sheet.setColumnWidth(0, 7*256);//���õ�һ�еĿ��   
			sheet.setColumnWidth(1, 35*256);//���õ�2�еĿ��  
			sheet.setColumnWidth(3, 35*256);//���õ�4�еĿ��   
			sheet.setColumnWidth(5, 35*256);//���õ�6�еĿ��   
			sheet.setColumnWidth(6, 45*256);//���õ�7�еĿ��  
			sheet.setColumnWidth(9, 20*256);//���õ�10�еĿ��   
			sheet.setColumnWidth(10, 20*256);//���õ�11�еĿ��  
			sheet.setColumnWidth(11, 20*256);//���õ�12�еĿ��  
			excelExport.setCell(0, "���", excelExport.headCenterNormalStyle);
			excelExport.setCell(1, "��������", excelExport.headCenterNormalStyle);
			excelExport.setCell(2, "�ͻ���������", excelExport.headCenterNormalStyle);
			excelExport.setCell(3, "�˺�", excelExport.headCenterNormalStyle);
			excelExport.setCell(4, "ҵ������", excelExport.headCenterNormalStyle);
			excelExport.setCell(5, "��Ʒ����", excelExport.headCenterNormalStyle);
			excelExport.setCell(6, "�ͻ�����", excelExport.headCenterNormalStyle);
			excelExport.setCell(7, "��������", excelExport.headCenterNormalStyle);
			excelExport.setCell(8, "��������", excelExport.headCenterNormalStyle);
			excelExport.setCell(9, "���", excelExport.headCenterNormalStyle);
			excelExport.setCell(10, "�վ����", excelExport.headCenterNormalStyle);
			excelExport.setCell(11, "�վ�������", excelExport.headCenterNormalStyle);
			excelExport.setCell(12, "���ʣ�%��", excelExport.headCenterNormalStyle);
			excelExport.setCell(13, "FTP�۸�%��", excelExport.headCenterNormalStyle);
			excelExport.setCell(14, "���%��", excelExport.headCenterNormalStyle);
			excelExport.setCell(15, "FTP����", excelExport.headCenterNormalStyle);
			double zftplr = 0,rjye=0, ftplr=0,ratefz=0,ftpfz=0,bal=0;
			int days = CommonFunctions.daysSubtract(maxDate, minDate);//����
			if(reportList != null) {
				for(YlfxBbReport ylfxBbReport : reportList) {
					zftplr += ylfxBbReport.getFtplr();
				}
				for (int j = 0; j < reportList.size(); j++) {
					YlfxBbReport entity = reportList.get(j);
					rjye += entity.getRjye();
		        	ftplr += entity.getFtplr();
		        	ratefz += entity.getRjye()*entity.getRate();
		        	ftpfz += entity.getRjye()*(entity.getFtp()==-999?0:entity.getFtp());
		        	bal+=entity.getBal();
					//ѭ������������
					excelExport.createRow(j+3);
					excelExport.setCell(0, j+1, excelExport.centerNormalStyle);
					excelExport.setCell(1, entity.getBrName()+"["+entity.getBrNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(2, entity.getEmpName()+"["+entity.getEmpNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(3, entity.getAcId(), excelExport.centerNormalStyle);
					excelExport.setCell(4, entity.getBusinessName(), excelExport.centerNormalStyle);
					excelExport.setCell(5, entity.getPrdtName(), excelExport.centerNormalStyle);
					excelExport.setCell(6, entity.getCustName(), excelExport.centerNormalStyle);
					excelExport.setCell(7,(entity.getOpnDate().equals("")||entity.getOpnDate()==null||entity.getOpnDate().equals("null"))?"-":entity.getOpnDate(), excelExport.centerNormalStyle);
					excelExport.setCell(8, (entity.getMtrDate().equals("")||entity.getMtrDate()==null||entity.getMtrDate().equals("null"))?"-":entity.getMtrDate(), excelExport.centerNormalStyle);
					excelExport.setCell(9, entity.getBal(), excelExport.rightNormalMoneyStyle);
					excelExport.setCell(10, entity.getRjye(), excelExport.rightNormalMoneyStyle);
					excelExport.setCell(11, entity.getRjye()*days, excelExport.rightNormalMoneyStyle);
					excelExport.setCell(12, CommonFunctions.doublecut(entity.getRate()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(13, entity.getFtp()==-999?0:CommonFunctions.doublecut(entity.getFtp()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(14, CommonFunctions.doublecut(entity.getLc()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(15, entity.getFtplr(), excelExport.rightNormalMoneyStyle);
				}
				//�ϼ���
				//int days = CommonFunctions.daysSubtract(maxDate, minDate);//����
				excelExport.createRow(reportList.size()+3);
				excelExport.setCell(0, "�ϼ�", excelExport.centerBoldStyle);
				excelExport.setCell(1, "-", excelExport.centerBoldStyle);
				excelExport.setCell(2, "-", excelExport.centerBoldStyle);
				excelExport.setCell(3, "-", excelExport.centerBoldStyle);
				excelExport.setCell(4, "-", excelExport.centerBoldStyle);
				excelExport.setCell(5, "-", excelExport.centerBoldStyle);
				excelExport.setCell(6, "-", excelExport.centerBoldStyle);
				excelExport.setCell(7, "-", excelExport.centerBoldStyle);
				excelExport.setCell(8, "-", excelExport.centerBoldStyle);
				excelExport.setCell(9, bal, excelExport.rightBoldMoneyStyle);
				excelExport.setCell(10, rjye, excelExport.rightBoldMoneyStyle);
				excelExport.setCell(11, CommonFunctions.doublecut(rjye*days,3), excelExport.rightBoldMoneyStyle);
				excelExport.setCell(12, CommonFunctions.doublecut(ratefz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(13, CommonFunctions.doublecut(ftpfz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(14, CommonFunctions.doublecut(ftplr/rjye*360/days*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(15, ftplr, excelExport.rightBoldMoneyStyle);
			}
			sheet.getRow(0).setHeight((short)500);//���õ�һ�б��߶�
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
	}

	public HSSFWorkbook getBzjckkhjllrbWorkbook(List<YlfxBbReport> reportList, String brName, String empName, String empNo, String minDate, String maxDate, String title) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheet = workbook.createSheet(title);//����һ��sheet
		try {
	        // ��ͷ�ϲ�
			sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 14 ));// �ϲ���һ�е�һ��15��
			sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 14 ));// �ϲ��ڶ��е�һ��15��
			//���õ�һ�л�����Ԫ��Ŀ��
	        sheet.setColumnWidth(0, 20*2*256);//���ȳ���2��Ϊ�˽���������п�Ȳ������ʾ��ѧ���������⣬����256�õ������ݲ���excel��ʵ�п�
			
			ExcelExport excelExport = new ExcelExport(workbook, sheet);
			excelExport.createRow(0); // ������һ��
			excelExport.setCell(0, title, excelExport.getHeaderStyle());
			
			excelExport.createRow(1); // �����ڶ���
			excelExport.setCell(0, "������"+brName+" "+(empNo!=null&&!empNo.equals("")?"       �ͻ�����"+empName+"["+ empNo+"]":"")+"       ����ʱ�Σ�"+CommonFunctions.dateModifyD(minDate,1)+"-"+maxDate+"         ��λ��Ԫ��%(������)", excelExport.getTitleStyle());
			
			excelExport.createRow(2);//�����У���ͷ��
			sheet.setColumnWidth(0, 7*256);//���õ�һ�еĿ��
			sheet.setColumnWidth(1, 30*256);//���õ�2�еĿ��  
			sheet.setColumnWidth(2, 30*256);//���õ�3�еĿ��     
			sheet.setColumnWidth(3, 30*256);//���õ�6�еĿ��  
			sheet.setColumnWidth(4, 30*256);//���õ�6�еĿ��  
			sheet.setColumnWidth(5, 40*256);//���õ�3�еĿ��     
			sheet.setColumnWidth(6, 25*256);//���õ�7�еĿ��   
			sheet.setColumnWidth(9, 12*256);//���õ�10�еĿ��  
			sheet.setColumnWidth(11, 10*256);//���õ�12�еĿ��   
			sheet.setColumnWidth(13, 10*256);//���õ�13�еĿ��  
			excelExport.setCell(0, "���", excelExport.headCenterNormalStyle);
			excelExport.setCell(1, "��������", excelExport.headCenterNormalStyle);
			excelExport.setCell(2, "�ͻ���������", excelExport.headCenterNormalStyle);
			excelExport.setCell(3, "�˺�", excelExport.headCenterNormalStyle);
			excelExport.setCell(4, "��Ʒ����", excelExport.headCenterNormalStyle);
			excelExport.setCell(5, "�ͻ�����", excelExport.headCenterNormalStyle);
			excelExport.setCell(6, "��������", excelExport.headCenterNormalStyle);
			excelExport.setCell(7, "��������", excelExport.headCenterNormalStyle);
			excelExport.setCell(8, "����", excelExport.headCenterNormalStyle);
			excelExport.setCell(9, "���", excelExport.headCenterNormalStyle);
			excelExport.setCell(10, "�վ����", excelExport.headCenterNormalStyle);
			excelExport.setCell(11, "����(%)", excelExport.headCenterNormalStyle);
			excelExport.setCell(12, "FTP�۸�(%)", excelExport.headCenterNormalStyle);
			excelExport.setCell(13, "����(%)", excelExport.headCenterNormalStyle);
			excelExport.setCell(14, "FTP����", excelExport.headCenterNormalStyle);
			double zftplr = 0,rjye=0, ftplr=0,ratefz=0,ftpfz=0,bal=0;
			if(reportList != null) {
				for(YlfxBbReport ylfxBbReport : reportList) {
					zftplr += ylfxBbReport.getFtplr();
				}
				for (int j = 0; j < reportList.size(); j++) {
					YlfxBbReport entity = reportList.get(j);
					rjye += entity.getRjye();
		        	ftplr += entity.getFtplr();
		        	ratefz += entity.getRjye()*entity.getRate();
		        	ftpfz += entity.getRjye()*(entity.getFtp()==-999?0:entity.getFtp());
		        	bal +=entity.getBal();
					//ѭ������������
					excelExport.createRow(j+3);
					excelExport.setCell(0, j+1, excelExport.centerNormalStyle);
					excelExport.setCell(1, entity.getBrName()+"["+entity.getBrNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(2, entity.getEmpName()+"["+entity.getEmpNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(3, entity.getAcId(), excelExport.centerNormalStyle);
					excelExport.setCell(4, entity.getPrdtName(), excelExport.centerNormalStyle);
					excelExport.setCell(5, entity.getCustName(), excelExport.centerNormalStyle);
					excelExport.setCell(6, (entity.getOpnDate().equals("")||entity.getOpnDate()==null||entity.getOpnDate().equals("null"))?"-":entity.getOpnDate(), excelExport.centerNormalStyle);
					excelExport.setCell(7, (entity.getMtrDate().equals("")||entity.getMtrDate()==null||entity.getMtrDate().equals("null"))?"-":entity.getMtrDate(), excelExport.centerNormalStyle);
					excelExport.setCell(8, ((entity.getOpnDate().equals("")||entity.getOpnDate()==null||entity.getOpnDate().equals("null"))||(entity.getMtrDate().equals("")||entity.getMtrDate()==null||entity.getMtrDate().equals("null")))?"-":String.valueOf(CommonFunctions.daysSubtract(entity.getMtrDate(), entity.getOpnDate())), excelExport.centerNormalStyle);
					excelExport.setCell(9, entity.getBal(), excelExport.centerNormalStyle);
					excelExport.setCell(10, entity.getRjye(), excelExport.rightNormalMoneyStyle);
					excelExport.setCell(11, CommonFunctions.doublecut(entity.getRate()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(12, entity.getFtp()==-999?0:CommonFunctions.doublecut(entity.getFtp()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(13, CommonFunctions.doublecut(entity.getLc()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(14, entity.getFtplr(), excelExport.rightNormalMoneyStyle);
					
				}
				//�ϼ���
				int days = CommonFunctions.daysSubtract(maxDate, minDate);//����
				excelExport.createRow(reportList.size()+3);
				excelExport.setCell(0, "�ϼ�", excelExport.centerBoldStyle);
				excelExport.setCell(1, "-", excelExport.centerBoldStyle);
				excelExport.setCell(2, "-", excelExport.centerBoldStyle);
				excelExport.setCell(3, "-", excelExport.centerBoldStyle);
				excelExport.setCell(4, "-", excelExport.centerBoldStyle);
				excelExport.setCell(5, "-", excelExport.centerBoldStyle);
				excelExport.setCell(6, "-", excelExport.centerBoldStyle);
				excelExport.setCell(7, "-", excelExport.centerBoldStyle);
				excelExport.setCell(8, "-", excelExport.centerBoldStyle);
				excelExport.setCell(9, bal, excelExport.centerBoldStyle);
				excelExport.setCell(10, rjye, excelExport.rightBoldMoneyStyle);
				excelExport.setCell(11, CommonFunctions.doublecut(ratefz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(12, CommonFunctions.doublecut(ftpfz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(13, CommonFunctions.doublecut(ftplr/rjye*360/days*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(14, ftplr, excelExport.rightBoldMoneyStyle);
				
			}
			
			
			sheet.getRow(0).setHeight((short)500);//���õ�һ�б��߶�
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
	}
	/**
	  * ��Ա���˻�������Ϊ��׼����ȡ���ڷ�Χ�ڵ��˻��Ļ�������Ʒ���ͻ������վ��������˺š��ͻ���
	  * @param xlsbrNo
	  * @param minDate������˵�
	  * @param maxDate�����Ҷ˵�
	  * @return
	  */
	public List<String[]> getQxppResultList(String xlsbrNo, String minDate, String maxDate,Integer assessScope) {
		List<String[]> resultList=new ArrayList<String[]>();

    	Map<String, String> ftpValuesMap = new HashMap<String, String>();
    	
    	Integer days = CommonFunctions.daysSubtract(maxDate, minDate);
    	//1.��ȡԱ����Ӧ�Ļ�����Ϣ
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
    	
    	//2.��ȡ�վ�����FTP�۸�
		int num_errorInfo=0;
		double rjye_whole_mx=0;
		
		String avgbalStr = "";//�վ�����ѯ���ֶ�
		if(assessScope == -1) {
			avgbalStr = "AVERATE_BAL_M";//�¶��վ����
		} else if(assessScope == -3) {
			avgbalStr = "AVERATE_BAL_Q";//�����վ����
		} else if(assessScope == -12) {
			avgbalStr = "AVERATE_BAL_Y";//����վ����
		}
		//��TJBB_RESULT����ֱ�ӻ�ȡ�վ������۽����Ϣ
		String sql = "select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
				" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
						""+avgbalStr+" !=0 and "+avgbalStr+" is not null";
		List list = daoFactory.query1(sql, null);
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				double avebal = obj[1] == null ? 0.0 : Double.valueOf(obj[1].toString());//�վ����
				double ftp = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());//FTP
				double rate = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());//����
				double bal = obj[7] == null ? 0.0 : Double.valueOf(obj[7].toString());//FTP
				String custNo = String.valueOf(obj[8]);
				String[] result = new String[10];
				result[0] = empInfoMap.get(custNo);//�ͻ�������������
				if(result[0] == null) {
					num_errorInfo++;
					continue;
				}
				if(obj[6]==null||"".equals(obj[6])){
					continue;
				}
				result[1] = String.valueOf(obj[6]);//��Ʒ
				result[2] = String.valueOf(custNo);//�ͻ�������
				result[3] = String.valueOf(avebal);//�վ����
				if(obj[6].toString().startsWith("P1")){
					result[4] = String.valueOf(avebal*(rate-ftp)*days/360);//FTP����
				}else{
					result[4] = String.valueOf(avebal*(ftp-rate)*days/360);//FTP����
				}
				result[5] = String.valueOf(obj[0]);//�˺�
				result[6] = custNo;//�ͻ���		
				result[7] = String.valueOf(obj[5]);//չ��״̬�����='2'Ϊ���˰���			
				if(avebal!=0){
					resultList.add(result);
				}
			}
		}
		list.clear(); System.gc();//�ͷ�list��ռ���ڴ�
		ftpValuesMap.clear(); System.gc();
		return resultList;
	}

	/**
	 * ��Ա���˻�������Ϊ��׼����ȡ���ڷ�Χ�ڵ��˻��Ļ�������Ʒ���ͻ������վ��������˺š��ͻ���
	 * @param xlsbrNo
	 * @param minDate������˵�
	 * @param maxDate�����Ҷ˵�
	 * @return
	 */
	public List<String[]> getQxppResultListMortgage(String xlsbrNo, String minDate, String maxDate,Integer assessScope,String brNos,String empNo) {
		List<String[]> resultList=new ArrayList<String[]>();
		Map<String, String> ftpValuesMap = new HashMap<String, String>();

		Integer days = CommonFunctions.daysSubtract(maxDate, minDate);
		//1.��ȡԱ����Ӧ�Ļ�����Ϣ
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


		//2.��ȡ�վ�����FTP�۸�
		String avgbalStr = "";//�վ�����ѯ���ֶ�
		if(assessScope == -1) {
			avgbalStr = "AVERATE_BAL_M";//�¶��վ����
		} else if(assessScope == -3) {
			avgbalStr = "AVERATE_BAL_Q";//�����վ����
		} else if(assessScope == -12) {
			avgbalStr = "AVERATE_BAL_Y";//����վ����
		}

		String sql="";

		if(empNo!=null&&!"".equals(empNo)){
			//��TJBB_RESULT����ֱ�ӻ�ȡ�վ������۽����Ϣ
			sql = "select t.*,t1.product_name,t1.cus_name,t1.Opn_date,t1.Mtr_date,t1.BUSINESS_NAME,t1.kmh from (select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
					" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
					""+avgbalStr+" !=0 and "+avgbalStr+" is not null and br_no "+brNos+" and  substr(kmh,1,6)='130405' and cum_no = '"+empNo+"') t left join";
			String sql_2 = " (select a.ac_id,c.product_name,a.cust_no,a.cus_name,a.Opn_date,a.Mtr_date,a.rate,c.BUSINESS_NAME,a.kmh" +
					" from ftp.fzh_history a " +
					" left join ftp.ftp_business_static_divide c on a.prdt_no = c.product_no " +
					"  ) t1 on t.ac_id=t1.ac_id" ;
			sql+=sql_2;
		}else {
			//��TJBB_RESULT����ֱ�ӻ�ȡ�վ������۽����Ϣ
			   sql = "select t.*,t1.product_name,t1.cus_name,t1.Opn_date,t1.Mtr_date,t1.BUSINESS_NAME,t1.kmh from (select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
					" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
					""+avgbalStr+" !=0 and "+avgbalStr+" is not null and br_no "+brNos+" and  substr(kmh,1,6)='130405') t left join";
			String sql_2 = " (select a.ac_id,c.product_name,a.cust_no,a.cus_name,a.Opn_date,a.Mtr_date,a.rate,c.BUSINESS_NAME,a.kmh" +
					" from ftp.fzh_history a " +
					" left join ftp.ftp_business_static_divide c on a.prdt_no = c.product_no " +
					"  ) t1 on t.ac_id=t1.ac_id" ;
			sql+=sql_2;
		}

		List list = daoFactory.query1(sql, null);
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				double avebal = obj[1] == null ? 0.0 : Double.valueOf(obj[1].toString());//�վ����
				double ftp = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());//FTP
				double rate = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());//����
				double bal = obj[7] == null ? 0.0 : Double.valueOf(obj[7].toString());//FTP
				String custNo = String.valueOf(obj[8]);
				String product_name = String.valueOf(obj[9]==null?"":obj[9].toString());
				String cus_name = String.valueOf(obj[10]==null?"":obj[10].toString());
				String Opn_date = String.valueOf(obj[11]==null?"":obj[11].toString());
				String Mtr_date = String.valueOf(obj[12]==null?"":obj[12].toString());
				String business_name = String.valueOf(obj[13]==null?"":obj[13].toString());
				String kmh = String.valueOf(obj[14]==null?"":obj[14].toString());
				String[] result = new String[17];
				result[0] = empInfoMap.get(custNo);//�ͻ�������������
				if(result[0] == null) {
					continue;
				}
				if(obj[6]==null||"".equals(obj[6])){
					continue;
				}
				result[1] = String.valueOf(obj[6]);//��Ʒ
				result[2] = String.valueOf(custNo);//�ͻ�������
				result[3] = String.valueOf(avebal);//�վ����
				if(obj[6].toString().startsWith("P1")){
					result[4] = String.valueOf(avebal*(rate-ftp)*days/360);//FTP����
				}else{
					result[4] = String.valueOf(avebal*(ftp-rate)*days/360);//FTP����
				}
				result[5] = String.valueOf(obj[0]);//�˺�
				result[6] = custNo;//�ͻ���
				result[7] = String.valueOf(obj[5]);//չ��״̬�����='2'Ϊ���˰���
				result[8] = String.valueOf(bal);//���

				result[9] = String.valueOf(product_name);//��Ʒ����
				result[10] = String.valueOf(cus_name);//�ͻ�����
				result[11] = String.valueOf(Opn_date);//��������
				result[12] = String.valueOf(Mtr_date);//��������
				result[13] = String.valueOf(business_name);//ҵ��

				result[14] = String.valueOf(ftp);//ftp�۸�
				result[15] = String.valueOf(rate);//ftp����
				result[16] = String.valueOf(kmh);//��Ŀ��
				if(avebal!=0){
					resultList.add(result);
				}
			}
		}
		list.clear(); System.gc();//�ͷ�list��ռ���ڴ�
		ftpValuesMap.clear(); System.gc();
		return resultList;
	}


	/**
	  * ��Ա���˻�������Ϊ��׼����ȡ���ڷ�Χ�ڵ��˻��Ļ�������Ʒ���ͻ������վ��������˺š��ͻ���
	  * @param xlsbrNo
	  * @param minDate������˵�
	  * @param maxDate�����Ҷ˵�
	  * @return
	  */
	public List<String[]> getQxppResultListEmp(String xlsbrNo, String minDate, String maxDate,Integer assessScope,String empNo) {
		List<String[]> resultList=new ArrayList<String[]>();
		Map<String, String> ftpValuesMap = new HashMap<String, String>();

		Integer days = CommonFunctions.daysSubtract(maxDate, minDate);
		//1.��ȡԱ����Ӧ�Ļ�����Ϣ
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


		//2.��ȡ�վ�����FTP�۸�
		String avgbalStr = "";//�վ�����ѯ���ֶ�
		if(assessScope == -1) {
			avgbalStr = "AVERATE_BAL_M";//�¶��վ����
		} else if(assessScope == -3) {
			avgbalStr = "AVERATE_BAL_Q";//�����վ����
		} else if(assessScope == -12) {
			avgbalStr = "AVERATE_BAL_Y";//����վ����
		}
		//��TJBB_RESULT����ֱ�ӻ�ȡ�վ������۽����Ϣ
		String sql = "select t.*,t1.product_name,t1.cus_name,t1.Opn_date,t1.Mtr_date,t1.BUSINESS_NAME,t1.kmh,t1.CRDNO from (select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
				" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
				""+avgbalStr+" !=0 and "+avgbalStr+" is not null and cum_no = '"+empNo+"') t left join";
		String sql_2 = " (select a.ac_id,c.product_name,a.cust_no,a.cus_name,a.Opn_date,a.Mtr_date,a.rate,c.BUSINESS_NAME,a.kmh,trim(d.CRDNO) as CRDNO" +
				" from ftp.fzh_history a " +
				" left join ftp.ftp_business_static_divide c on a.prdt_no = c.product_no " +
				" left join bips.NSOP_MIR_CDFM23 d on a.SOP_CODE = d.ACCNO " +
				"  ) t1 on t.ac_id=t1.ac_id" ;
		sql+=sql_2;
		List list = daoFactory.query1(sql, null);
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				double avebal = obj[1] == null ? 0.0 : Double.valueOf(obj[1].toString());//�վ����
				double ftp = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());//FTP
				double rate = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());//����
				double bal = obj[7] == null ? 0.0 : Double.valueOf(obj[7].toString());//FTP
				String custNo = String.valueOf(obj[8]);
				String product_name = String.valueOf(obj[9]==null?"":obj[9].toString());
				String cus_name = String.valueOf(obj[10]==null?"":obj[10].toString());
				String Opn_date = String.valueOf(obj[11]==null?"":obj[11].toString());
				String Mtr_date = String.valueOf(obj[12]==null?"":obj[12].toString());
				String business_name = String.valueOf(obj[13]==null?"":obj[13].toString());
				String kmh = String.valueOf(obj[14]==null?"":obj[14].toString());
				String[] result = new String[17];
				result[0] = empInfoMap.get(custNo);//�ͻ�������������
				if(result[0] == null) {
					continue;
				}
				if(obj[6]==null||"".equals(obj[6])){
					continue;
				}

				String ac_id = String.valueOf(obj[15]==null?obj[0].toString():obj[15].toString());
				result[1] = String.valueOf(obj[6]);//��Ʒ
				result[2] = String.valueOf(custNo);//�ͻ�������
				result[3] = String.valueOf(avebal);//�վ����
				if(obj[6].toString().startsWith("P1")){
					result[4] = String.valueOf(avebal*(rate-ftp)*days/360);//FTP����
				}else{
					result[4] = String.valueOf(avebal*(ftp-rate)*days/360);//FTP����
				}

				result[5] = String.valueOf(ac_id);//�˺�
				result[6] = custNo;//�ͻ���
				result[7] = String.valueOf(obj[5]);//չ��״̬�����='2'Ϊ���˰���
				result[8] = String.valueOf(bal);//���

				result[9] = String.valueOf(product_name);//��Ʒ����
				result[10] = String.valueOf(cus_name);//�ͻ�����
				result[11] = String.valueOf(Opn_date);//��������
				result[12] = String.valueOf(Mtr_date);//��������
				result[13] = String.valueOf(business_name);//ҵ��

				result[14] = String.valueOf(ftp);//ftp�۸�
				result[15] = String.valueOf(rate);//ftp����
				result[16] = String.valueOf(kmh);//��Ŀ��
				if(avebal!=0){
					resultList.add(result);
				}
			}
		}
		list.clear(); System.gc();//�ͷ�list��ռ���ڴ�
		ftpValuesMap.clear(); System.gc();
		return resultList;
	}


	/**
	 * ��Ա���˻�������Ϊ��׼����ȡ���ڷ�Χ�ڵ��˻��Ļ�������Ʒ���ͻ������վ��������˺š��ͻ��š���֤��
	 * @param xlsbrNo
	 * @return
	 */
	public List<String[]> getQxppResultListEmpBzj(String xlsbrNo, String minDate, String maxDate,Integer assessScope,String empNo) {
		List<String[]> resultList=new ArrayList<String[]>();

		Map<String, String> ftpValuesMap = new HashMap<String, String>();

		Integer days = CommonFunctions.daysSubtract(maxDate, minDate);
		//1.��ȡԱ����Ӧ�Ļ�����Ϣ
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


		//2.��ȡ�վ�����FTP�۸�
		String avgbalStr = "";//�վ�����ѯ���ֶ�
		if(assessScope == -1) {
			avgbalStr = "AVERATE_BAL_M";//�¶��վ����
		} else if(assessScope == -3) {
			avgbalStr = "AVERATE_BAL_Q";//�����վ����
		} else if(assessScope == -12) {
			avgbalStr = "AVERATE_BAL_Y";//����վ����
		}

		String sql="";
		if(empNo!=null&&!"".equals(empNo)){
			//��TJBB_RESULT����ֱ�ӻ�ȡ�վ������۽����Ϣ
			 sql = "select t.*,t1.product_name,t1.cus_name,t1.Opn_date,t1.Mtr_date,t1.BUSINESS_NAME,t1.kmh from (select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
					" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
					""+avgbalStr+" !=0 and "+avgbalStr+" is not null and br_no "+xlsbrNo+" and cum_no = '"+empNo+"') t left join";
			String sql_2 = " (select a.ac_id,c.product_name,a.cust_no,a.cus_name,a.Opn_date,a.Mtr_date,a.rate,c.BUSINESS_NAME,a.kmh" +
					" from ftp.fzh_history a " +
					" left join ftp.ftp_business_static_divide c on a.prdt_no = c.product_no where c.PRODUCT_CTG_NO = 'D2011'" +
					"  ) t1 on t.ac_id=t1.ac_id where t1.ac_id is not null" ;
			sql+=sql_2;
		}else {
			//��TJBB_RESULT����ֱ�ӻ�ȡ�վ������۽����Ϣ
			 sql = "select t.*,t1.product_name,t1.cus_name,t1.Opn_date,t1.Mtr_date,t1.BUSINESS_NAME,t1.kmh from (select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
					" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
					""+avgbalStr+" !=0 and "+avgbalStr+" is not null and br_no "+xlsbrNo+" ) t left join";
			String sql_2 = " (select a.ac_id,c.product_name,a.cust_no,a.cus_name,a.Opn_date,a.Mtr_date,a.rate,c.BUSINESS_NAME,a.kmh" +
					" from ftp.fzh_history a " +
					" left join ftp.ftp_business_static_divide c on a.prdt_no = c.product_no where c.PRODUCT_CTG_NO = 'D2011'" +
					"  ) t1 on t.ac_id=t1.ac_id where t1.ac_id is not null" ;
			sql+=sql_2;
		}
		List list = daoFactory.query1(sql, null);
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				double avebal = obj[1] == null ? 0.0 : Double.valueOf(obj[1].toString());//�վ����
				double ftp = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());//FTP
				double rate = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());//����
				double bal = obj[7] == null ? 0.0 : Double.valueOf(obj[7].toString());//FTP
				String custNo = String.valueOf(obj[8]);
				String product_name = String.valueOf(obj[9]==null?"":obj[9].toString());
				String cus_name = String.valueOf(obj[10]==null?"":obj[10].toString());
				String Opn_date = String.valueOf(obj[11]==null?"":obj[11].toString());
				String Mtr_date = String.valueOf(obj[12]==null?"":obj[12].toString());
				String business_name = String.valueOf(obj[13]==null?"":obj[13].toString());
				String kmh = String.valueOf(obj[14]==null?"":obj[14].toString());
				String[] result = new String[17];
				result[0] = empInfoMap.get(custNo);//�ͻ�������������
				if(result[0] == null) {
					continue;
				}
				if(obj[6]==null||"".equals(obj[6])){
					continue;
				}
				result[1] = String.valueOf(obj[6]);//��Ʒ
				result[2] = String.valueOf(custNo);//�ͻ�������
				result[3] = String.valueOf(avebal);//�վ����
				if(obj[6].toString().startsWith("P1")){
					result[4] = String.valueOf(avebal*(rate-ftp)*days/360);//FTP����
				}else{
					result[4] = String.valueOf(avebal*(ftp-rate)*days/360);//FTP����
				}
				result[5] = String.valueOf(obj[0]);//�˺�
				result[6] = custNo;//�ͻ���
				result[7] = String.valueOf(obj[5]);//չ��״̬�����='2'Ϊ���˰���
				result[8] = String.valueOf(bal);//���

				result[9] = String.valueOf(product_name);//��Ʒ����
				result[10] = String.valueOf(cus_name);//�ͻ�����
				result[11] = String.valueOf(Opn_date);//��������
				result[12] = String.valueOf(Mtr_date);//��������
				result[13] = String.valueOf(business_name);//ҵ��

				result[14] = String.valueOf(ftp);//ftp�۸�
				result[15] = String.valueOf(rate);//ftp����
				result[16] = String.valueOf(rate);//��Ŀ��
				if(avebal!=0){
					resultList.add(result);
				}
			}
		}
		list.clear(); System.gc();//�ͷ�list��ռ���ڴ�
		ftpValuesMap.clear(); System.gc();
		return resultList;
	}


	/**
	 * ��Ա���˻�������Ϊ��׼����ȡ���ڷ�Χ�ڵ��˻��Ļ�������Ʒ���ͻ������վ��������˺š��ͻ��š����пͻ�����
	 * @param xlsbrNo
	 * @return
	 */
	public List<String[]> getQxppResultListEmpAll(String xlsbrNo, String minDate, String maxDate,Integer assessScope,String brNos) {
		List<String[]> resultList=new ArrayList<String[]>();

		Map<String, String> ftpValuesMap = new HashMap<String, String>();

		Integer days = CommonFunctions.daysSubtract(maxDate, minDate);
		//1.��ȡԱ����Ӧ�Ļ�����Ϣ
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


		//2.��ȡ�վ�����FTP�۸�
		String avgbalStr = "";//�վ�����ѯ���ֶ�
		if(assessScope == -1) {
			avgbalStr = "AVERATE_BAL_M";//�¶��վ����
		} else if(assessScope == -3) {
			avgbalStr = "AVERATE_BAL_Q";//�����վ����
		} else if(assessScope == -12) {
			avgbalStr = "AVERATE_BAL_Y";//����վ����
		}
		//��TJBB_RESULT����ֱ�ӻ�ȡ�վ������۽����Ϣ
		String sql = "select t.*,t1.product_name,t1.cus_name,t1.Opn_date,t1.Mtr_date,t1.BUSINESS_NAME,t1.kmh,t1.CRDNO from (select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
				" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
				""+avgbalStr+" !=0 and "+avgbalStr+" is not null and br_no "+brNos+" and substr(cum_no,1,2) != '99' and cum_no is not null and cum_no !='') t left join";
		String sql_2 = " (select a.ac_id,c.product_name,a.cust_no,a.cus_name,a.Opn_date,a.Mtr_date,a.rate,c.BUSINESS_NAME,a.kmh,trim(d.CRDNO) as CRDNO" +
				" from ftp.fzh_history a " +
				" left join ftp.ftp_business_static_divide c on a.prdt_no = c.product_no " +
				" left join bips.NSOP_MIR_CDFM23 d on a.SOP_CODE = d.ACCNO " +
				"  ) t1 on t.ac_id=t1.ac_id" ;
		sql+=sql_2;
		List list = daoFactory.query1(sql, null);
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				double avebal = obj[1] == null ? 0.0 : Double.valueOf(obj[1].toString());//�վ����
				double ftp = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());//FTP
				double rate = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());//����
				double bal = obj[7] == null ? 0.0 : Double.valueOf(obj[7].toString());//FTP
				String custNo = String.valueOf(obj[8]);
				String product_name = String.valueOf(obj[9]==null?"":obj[9].toString());
				String cus_name = String.valueOf(obj[10]==null?"":obj[10].toString());
				String Opn_date = String.valueOf(obj[11]==null?"":obj[11].toString());
				String Mtr_date = String.valueOf(obj[12]==null?"":obj[12].toString());
				String business_name = String.valueOf(obj[13]==null?"":obj[13].toString());
				String kmh = String.valueOf(obj[14]==null?"":obj[14].toString());
				String[] result = new String[17];
				result[0] = empInfoMap.get(custNo);//�ͻ�������������
				if(result[0] == null) {
					continue;
				}
				if(obj[6]==null||"".equals(obj[6])){
					continue;
				}

				String ac_id = String.valueOf(obj[15]==null?obj[0].toString():obj[15].toString());
				result[1] = String.valueOf(obj[6]);//��Ʒ
				result[2] = String.valueOf(custNo);//�ͻ�������
				result[3] = String.valueOf(avebal);//�վ����
				if(obj[6].toString().startsWith("P1")){
					result[4] = String.valueOf(avebal*(rate-ftp)*days/360);//FTP����
				}else{
					result[4] = String.valueOf(avebal*(ftp-rate)*days/360);//FTP����
				}
				result[5] = String.valueOf(ac_id);//�˺�
				result[6] = custNo;//�ͻ���
				result[7] = String.valueOf(obj[5]);//չ��״̬�����='2'Ϊ���˰���
				result[8] = String.valueOf(bal);//���

				result[9] = String.valueOf(product_name);//��Ʒ����
				result[10] = String.valueOf(cus_name);//�ͻ�����
				result[11] = String.valueOf(Opn_date);//��������
				result[12] = String.valueOf(Mtr_date);//��������
				result[13] = String.valueOf(business_name);//ҵ��

				result[14] = String.valueOf(ftp);//ftp�۸�
				result[15] = String.valueOf(rate);//ftp����
				result[16] = String.valueOf(kmh);//��Ŀ��
				if(avebal!=0){
					resultList.add(result);
				}
			}
		}
		list.clear(); System.gc();//�ͷ�list��ռ���ڴ�
		ftpValuesMap.clear(); System.gc();
		return resultList;
	}


	/**
	 * ��˾ҵ����ϸ����
	 * @param xlsbrNo
	 * @param minDate������˵�
	 * @param maxDate�����Ҷ˵�
	 * @return
	 */
	public List<String[]> getQxppResultListGsyw(String xlsbrNo, String minDate, String maxDate,Integer assessScope,String brNos) {
		List<String[]> resultList=new ArrayList<String[]>();

		Map<String, String> ftpValuesMap = new HashMap<String, String>();

		Integer days = CommonFunctions.daysSubtract(maxDate, minDate);
		//1.��ȡԱ����Ӧ�Ļ�����Ϣ
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


		//2.��ȡ�վ�����FTP�۸�
		String avgbalStr = "";//�վ�����ѯ���ֶ�
		if(assessScope == -1) {
			avgbalStr = "AVERATE_BAL_M";//�¶��վ����
		} else if(assessScope == -3) {
			avgbalStr = "AVERATE_BAL_Q";//�����վ����
		} else if(assessScope == -12) {
			avgbalStr = "AVERATE_BAL_Y";//����վ����
		}
		//��TJBB_RESULT����ֱ�ӻ�ȡ�վ������۽����Ϣ
		String sql = "select t.*,t1.product_name,t1.cus_name,t1.Opn_date,t1.Mtr_date,t1.BUSINESS_NAME,t1.kmh from (select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
				" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
				""+avgbalStr+" !=0 and "+avgbalStr+" is not null and br_no "+brNos+" and (substr(kmh,1,4) in ('2001','2002','2006','2011','2014','1306') or (substr(kmh,1,4) in  ('1301','1302','1303','1304','1305','1307','1308') and substr(cust_no,1,1)='2'))) t left join";
		String sql_2 = " (select * from ( select a.ac_id,a.product_name,a.cust_no,a.cus_name,a.Opn_date,a.Mtr_date,a.rate,a.BUSINESS_NAME,a.kmh, row_number() over(partition by ac_id order by WRK_SYS_DATE desc ) as rn " +
				"  from ftp.FTP_QXPP_RESULT a ) where rn=1 " +
				"  ) t1 on t.ac_id=t1.ac_id" ;
		sql+=sql_2;
		List list = daoFactory.query1(sql, null);
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				double avebal = obj[1] == null ? 0.0 : Double.valueOf(obj[1].toString());//�վ����
				double ftp = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());//FTP
				double rate = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());//����
				double bal = obj[7] == null ? 0.0 : Double.valueOf(obj[7].toString());//FTP
				String custNo = String.valueOf(obj[8]);
				String product_name = String.valueOf(obj[9]==null?"":obj[9].toString());
				String cus_name = String.valueOf(obj[10]==null?"":obj[10].toString());
				String Opn_date = String.valueOf(obj[11]==null?"":obj[11].toString());
				String Mtr_date = String.valueOf(obj[12]==null?"":obj[12].toString());
				String business_name = String.valueOf(obj[13]==null?"":obj[13].toString());
				String kmh = String.valueOf(obj[14]==null?"":obj[14].toString());
				String[] result = new String[17];
				result[0] = empInfoMap.get(custNo);//�ͻ�������������
				if(result[0] == null) {
					continue;
				}
				if(obj[6]==null||"".equals(obj[6])){
					continue;
				}
				result[1] = String.valueOf(obj[6]);//��Ʒ
				result[2] = String.valueOf(custNo);//�ͻ�������
				result[3] = String.valueOf(avebal);//�վ����
				if(obj[6].toString().startsWith("P1")){
					result[4] = String.valueOf(avebal*(rate-ftp)*days/360);//FTP����
				}else{
					result[4] = String.valueOf(avebal*(ftp-rate)*days/360);//FTP����
				}
				result[5] = String.valueOf(obj[0]);//�˺�
				result[6] = custNo;//�ͻ���
				result[7] = String.valueOf(obj[5]);//չ��״̬�����='2'Ϊ���˰���
				result[8] = String.valueOf(bal);//���

				result[9] = String.valueOf(product_name);//��Ʒ����
				result[10] = String.valueOf(cus_name);//�ͻ�����
				result[11] = String.valueOf(Opn_date);//��������
				result[12] = String.valueOf(Mtr_date);//��������
				result[13] = String.valueOf(business_name);//ҵ��

				result[14] = String.valueOf(ftp);//ftp�۸�
				result[15] = String.valueOf(rate);//ftp����
				result[16] = String.valueOf(kmh);//��Ŀ��
				if(avebal!=0){
					resultList.add(result);
				}
			}
		}
		list.clear(); System.gc();//�ͷ�list��ռ���ڴ�
		ftpValuesMap.clear(); System.gc();
		return resultList;
	}



	/**
	  * ���վ����Ϊ��׼����ȡָ��ʱ�䷶Χ���˻��Ŀ�����������Ʒ���ͻ������վ���FTP�������
	  * @param xlsbrNo
	  * @param minDateͳ��������˵�
	  * @param maxDateͳ�������Ҷ˵�
	  * @param assessScopeͳ��ά��-1��-3��-12
	  * @return
	  */
	public List<String[]> getQxppResultPrdtList(String xlsbrNo, String minDate, String maxDate, Integer assessScope) {
		List<String[]> resultList=new ArrayList<String[]>();
		String STime0=CommonFunctions.GetCurrentTime();
		List list = new ArrayList();
		Integer days = CommonFunctions.daysSubtract(maxDate, minDate);

		//1.��TJBB_RESULT�л�ȡ����
	    String balStr = "BAL";//����ѯ���ֶ�
		String avgbalStr = "AVERATE_BAL_M";//�վ�����ѯ���ֶ�
		if(assessScope == -0) {//Ѯ��
			int leftDay = Integer.valueOf(String.valueOf(minDate).substring(6, 8));//��˵�����
			//ͨ����˵�����ڽ����ж�������Ѯ
			if(leftDay == 1) {//��Ѯ
				avgbalStr = "AVERATE_BAL_SX";//��Ѯ�վ����
			}else if(leftDay == 11) {//��Ѯ
				avgbalStr = "AVERATE_BAL_ZX";//��Ѯ�վ����
			}else {//��Ѯ
				avgbalStr = "AVERATE_BAL_XX";//��Ѯ�վ����
			}
		} else if(assessScope == -1) {
			avgbalStr = "AVERATE_BAL_M";//�¶��վ����
		} else if(assessScope == -3) {
			avgbalStr = "AVERATE_BAL_Q";//�����վ����
		} else if(assessScope == -12) {
			avgbalStr = "AVERATE_BAL_Y";//����վ����
		}
		//��TJBB_RESULT����ֱ�ӻ�ȡ�վ������۽����Ϣ ## ls���������
		String sql = "select t.cum_no,t."+avgbalStr+",t.FTP_RATE_M,t.PRDT_NO,t.BR_NO,t."+balStr+",t.CUST_TYPE,t1.ACC_NO"
	            +" from ftp.TJBB_RESULT_PRDT  t  left join ftp.FTP_BUSINESS_PRDT t1 on t.prdt_no = t1.PRDT_NO"
	            +" where trim(t.CYC_DATE) = '"+maxDate+"'"
				//+" and CUR_NO"+curNoSql
				+" and t."+avgbalStr+" !=0 and t."+avgbalStr+" is not null";
		
		list = daoFactory.query1(sql, null);
		String ETime0=CommonFunctions.GetCurrentTime();
		int costTime0=CommonFunctions.GetCostTimeInSecond(STime0, ETime0);
		int CostFen=costTime0/60;
		int CostMiao=costTime0%60;
		System.out.println("TJBB_RESULT�еĲ�ѯ���"+list.size()+"�м�¼,��ʱ"+CostFen+"��"+CostMiao+"��");


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

		//3.ѭ������list��
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				list.set(i, null);//�ͷ�listѭ�������У��ñʼ�¼����ռ�ڴ�(��list�иñʼ�¼��ʹ�ú�)
				if(i%50000==0){
					System.out.println("------------>i="+i);
				}
				
				//����Ʒ���Ϊ�յĹ��˵�
				if (obj[3] == null || "".equals(obj[3])) {
					continue;
				}
				
				double avebal = obj[1] == null ? 0.0 : Double.valueOf(obj[1].toString());//�վ����
				double cz = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());//�վ����
				double bal = obj[5] == null ? 0.0 : Double.valueOf(obj[5].toString());//FTP
				String[] result = new String[8];
				//result[0] = String.valueOf(obj[4]);//�����������Ӷ��۽�����л�ȡ
				result[0] = String.valueOf(empInfoMap.get(obj[0])==null?obj[4]:empInfoMap.get(obj[0]));
				result[1] = String.valueOf(obj[3]);//��Ʒ
				result[2] = String.valueOf(obj[0]);//�ͻ�������
				result[3] = String.valueOf(avebal);//�վ����
				result[4] = String.valueOf(avebal*cz*days/360);//FTP����
				result[5] = obj[6] == null ? "" : String.valueOf(obj[6]);//�ͻ�����
				result[6] = String.valueOf(bal);//���
				result[7] =obj[7] == null ? "" : String.valueOf(obj[7]);//��Ŀ��
				resultList.add(result);
			}
		}
		list = new ArrayList();list.clear(); System.gc();//�ͷ�list��ռ���ڴ�
		return resultList;
	}
	
	
	/**
	 * ��ȡĳ��������վ���FTP����
	 * @param ftped_data_successList
	 * @param brNosҪ���ܵĻ����ַ�������'1801031009','1801031008'
	 * @return �վ���FTP����
	 * @paramm custType
	 */
	public double[] getQxppValueByBrNo(List<String[]> ftpResultList, String brNos, String custType) {
		double[] returnValue = {0.0, 0.0, 0.0, 0.0,0.0,0.0,0.0, 0.0, 0.0, 0.0,0.0,0.0,0.0, 0.0, 0.0, 0.0,0.0,0.0,0.0, 0.0, 0.0, 0.0};
		Map<String,List<String>> prdtCtgMap = getPrdtCtgMap();
		double rjye_zc = 0.0, rjye_fz = 0.0, ftplr_zc = 0.0, ftplr_fz = 0.0,zc_bal= 0.0,fz_bal=0.0,
				rjye_grhq = 0.0, rjye_grdq = 0.0, rjye_dwhq = 0.0, rjye_dwdq = 0.0,rjye_czx= 0.0,rjye_yhk=0.0,rjye_bzj= 0.0,rjye_yjhk=0.0,
				bal_grhq = 0.0, bal_grdq = 0.0, bal_dwhq = 0.0, bal_dwdq = 0.0,bal_czx= 0.0,bal_yhk=0.0,bal_bzj= 0.0,bal_yjhk=0.0;
		for (String[] result : ftpResultList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//��¼�Ƿ�����ͳ�ƣ��ֶԹ��Ͷ�˽
			if(isStatic) {
				if(result[1].indexOf("P1") != -1 && (brNos.indexOf(result[0])!= -1|| brNos.equals("is not null"))) {
					rjye_zc += Double.valueOf(result[3]);
					ftplr_zc += Double.valueOf(result[4]);
					zc_bal+=Double.valueOf(result[6]);
				}
				if(result[1].indexOf("P2") != -1 && (brNos.indexOf(result[0])!= -1|| brNos.equals("is not null"))) {
					rjye_fz += Double.valueOf(result[3]);
					ftplr_fz += Double.valueOf(result[4]);
					fz_bal+=Double.valueOf(result[6]);
				}

				if((brNos.indexOf(result[0])!= -1|| brNos.equals("is not null"))){
					if(prdtCtgMap.get("grhq").contains(result[1])){
						bal_grhq+=Double.valueOf(result[6]);
						rjye_grhq+=Double.valueOf(result[3]);
					}

					if(prdtCtgMap.get("grdq").contains(result[1])){
						bal_grdq+=Double.valueOf(result[6]);
						rjye_grdq+=Double.valueOf(result[3]);
					}

					if(prdtCtgMap.get("dwhq").contains(result[1])){
						bal_dwhq+=Double.valueOf(result[6]);
						rjye_dwhq+=Double.valueOf(result[3]);
					}

					if(prdtCtgMap.get("dwdq").contains(result[1])){
						bal_dwdq+=Double.valueOf(result[6]);
						rjye_dwdq+=Double.valueOf(result[3]);
					}


					if(prdtCtgMap.get("yhk").contains(result[1])){
						bal_yhk+=Double.valueOf(result[6]);
						rjye_yhk+=Double.valueOf(result[3]);
					}

					if(prdtCtgMap.get("czx").contains(result[1])){
						bal_czx+=Double.valueOf(result[6]);
						rjye_czx+=Double.valueOf(result[3]);
					}

					if(prdtCtgMap.get("yjhk").contains(result[1])){
						bal_yjhk+=Double.valueOf(result[6]);
						rjye_yjhk+=Double.valueOf(result[3]);
					}

					if(prdtCtgMap.get("bzj").contains(result[1])){
						bal_bzj+=Double.valueOf(result[6]);
						rjye_bzj+=Double.valueOf(result[3]);

					}
				}
			}
		}
		returnValue[0] = rjye_zc;
		returnValue[1] = ftplr_zc;
		returnValue[2] = rjye_fz;
		returnValue[3] = ftplr_fz;
		returnValue[4] = zc_bal;
		returnValue[5] = fz_bal;

		returnValue[6] = bal_grhq;
		returnValue[7] = bal_grdq;
		returnValue[8] = bal_dwhq;
		returnValue[9] = bal_dwdq;
		returnValue[10] = bal_czx;
		returnValue[11] = bal_yhk;
		returnValue[12] = bal_yjhk;
		returnValue[13] = bal_bzj;

		returnValue[14] = rjye_grhq;
		returnValue[15] = rjye_grdq;
		returnValue[16] = rjye_dwhq;
		returnValue[17] = rjye_dwdq;
		returnValue[18] = rjye_czx;
		returnValue[19] = rjye_yhk;
		returnValue[20] = rjye_yjhk;
		returnValue[21] = rjye_bzj;
		return returnValue;
	}
	
	
	
	/**
	 * ȫ�����л���FTP������ܱ���ftpResultList�е����ݣ����ջ����ŵ���Ӧ��map��
	 * @param ftped_data_successList
	 * @param brMstList Ҫ��ȡ����ֵ������ֱ����ʾ�Ļ������ݱ�����б�(2��1��0��������֧��)
	 * @paramm telNo
	 * @paramm custType
	 * @return map
	 */
	public Map<String,Double[]> getQxppValueMap_qhsyjgftphzb(List<String[]> ftpResultList, List<BrMst> brMstList, String telNo, String custType) {
		//key<������+�ʲ���ծ����(1��ծ2�ʲ�)>
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> rjye_map=new HashMap<String,Double>();
		Map<String,Double> bal_map=new HashMap<String,Double>();//���map
		Map<String,Double> ftplr_map=new HashMap<String,Double>();

        Map<String,Double> ftplr_dg_map=new HashMap<String,Double>();//�Թ�
        Map<String,Double> ftplr_ds_map=new HashMap<String,Double>();//��˽

		Map<String,List<String>> prdtCtgMap = getPrdtCtgMap();

		Map<String,Double> grhqrjye_map=new HashMap<String,Double>();//���˻����վ����map
		Map<String,Double> grdqrjye_map=new HashMap<String,Double>();//���˶����վ����map
		Map<String,Double> dwhqrjye_map=new HashMap<String,Double>();//��λ�����վ����map
		Map<String,Double> dwdqrjye_map=new HashMap<String,Double>();//��λ�����վ����map
		Map<String,Double> yhkrjye_map=new HashMap<String,Double>();//���п��վ����map
		Map<String,Double> czxrjye_map=new HashMap<String,Double>();//�����Դ���վ����map
		Map<String,Double> yjhkrjye_map=new HashMap<String,Double>();//Ӧ�����վ����map
		Map<String,Double> bzjrjye_map=new HashMap<String,Double>();//��֤���վ����map

		Map<String,Double> grhqbal_map=new HashMap<String,Double>();//���˻������map
		Map<String,Double> grdqbal_map=new HashMap<String,Double>();//���˶������map
		Map<String,Double> dwhqbal_map=new HashMap<String,Double>();//��λ�������map
		Map<String,Double> dwdqbal_map=new HashMap<String,Double>();//��λ�������map
		Map<String,Double> yhkbal_map=new HashMap<String,Double>();//���п����map
		Map<String,Double> czxbal_map=new HashMap<String,Double>();//�����Դ�����map
		Map<String,Double> yjhkbal_map=new HashMap<String,Double>();//Ӧ�������map
		Map<String,Double> bzjbal_map=new HashMap<String,Double>();//��֤�����map

		Map<String,Double> grdkftplr_map=new HashMap<String,Double>();//���˴���ftp����map
		Map<String,Double> gsdkftplr_map=new HashMap<String,Double>();//��˾����ftp����map
		Map<String,Double> grhqftplr_map=new HashMap<String,Double>();//���˻���ftp����map
		Map<String,Double> grdqftplr_map=new HashMap<String,Double>();//���˶���ftp����map
		Map<String,Double> dwhqftplr_map=new HashMap<String,Double>();//��λ����ftp����map
		Map<String,Double> dwdqftplr_map=new HashMap<String,Double>();//��λ����ftp����map
		Map<String,Double> yhkftplr_map=new HashMap<String,Double>();//���п�ftp����map
		Map<String,Double> czxftplr_map=new HashMap<String,Double>();//�����Դ��ftp����map
		Map<String,Double> yjhkftplr_map=new HashMap<String,Double>();//Ӧ����ftp����map
		Map<String,Double> bzjftplr_map=new HashMap<String,Double>();//��֤��ftp����map

		for (String[] result : ftpResultList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//��¼�Ƿ�����ͳ�ƣ��ֶԹ��Ͷ�˽
			if (isStatic) {
				double rjye = Double.valueOf(result[3]);
				double bal = Double.valueOf(result[6]);
				double ftplr = Double.valueOf(result[4]);
				//�վ����
				if (rjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
					rjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
				} else {
					rjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + rjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
				}
				//FTP����
				if (ftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
					ftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
				} else {
					ftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + ftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
				}
				//���
				if (bal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
					bal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
				} else {
					bal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + bal_map.get(result[0] + "-" + result[1].substring(1, 2)));
				}

				if (prdtCtgMap.get("grhq").contains(result[1])) {
					if (grhqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						grhqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						grhqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + grhqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (grhqbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						grhqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						grhqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + grhqbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (grhqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						grhqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						grhqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + grhqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("grdq").contains(result[1])) {
					if (grdqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						grdqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						grdqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + grdqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (grdqbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						grdqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						grdqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + grdqbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}


					if (grdqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						grdqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						grdqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + grdqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("dwhq").contains(result[1])) {
					if (dwhqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						dwhqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						dwhqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + dwhqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (dwhqbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						dwhqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						dwhqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + dwhqbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (dwhqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						dwhqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						dwhqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + dwhqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("dwdq").contains(result[1])) {
					if (dwdqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						dwdqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						dwdqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + dwdqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (dwdqbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						dwdqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						dwdqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + dwdqbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (dwdqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						dwdqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						dwdqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + dwdqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}


				if (prdtCtgMap.get("yhk").contains(result[1])) {
					if (yhkrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						yhkrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						yhkrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + yhkrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (yhkbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						yhkbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						yhkbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + yhkbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (yhkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						yhkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						yhkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + yhkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

				}

				if (prdtCtgMap.get("czx").contains(result[1])) {
					if (czxrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						czxrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						czxrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + czxrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (czxbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						czxbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						czxbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + czxbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (czxftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						czxftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						czxftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + czxftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("yjhk").contains(result[1])) {
					if (yjhkrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						yjhkrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						yjhkrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + yjhkrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (yjhkbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						yjhkbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						yjhkbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + yjhkbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}


					if (yjhkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						yjhkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						yjhkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + yjhkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("bzj").contains(result[1])) {
					if (bzjrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						bzjrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						bzjrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + bzjrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (bzjbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						bzjbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						bzjbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + bzjbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (bzjftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						bzjftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						bzjftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + bzjftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}

				boolean isds = this.getIsStatic("1", result[5], result[7]);//��˽ͳ��
				if (isds) {
					if (ftplr_ds_map.get(result[0]) == null) {
						ftplr_ds_map.put(result[0], ftplr);
					} else {
						ftplr_ds_map.put(result[0], ftplr + ftplr_ds_map.get(result[0]));
					}
				}

				boolean isdg = this.getIsStatic("2", result[5], result[7]);//�Թ�ͳ��
				if (isdg) {
					//FTP����
					if (ftplr_dg_map.get(result[0]) == null) {
						ftplr_dg_map.put(result[0], ftplr);
					} else {
						ftplr_dg_map.put(result[0], ftplr + ftplr_dg_map.get(result[0]));
					}
				}

				boolean isdsdk = this.getIsStaticDk("1", result[5], result[7]);//��˽ͳ��
				if (isdsdk) {
					if (grdkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						grdkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						grdkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + grdkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}
				boolean isdgdk = this.getIsStaticDk("2", result[5], result[7]);//�Թ�ͳ��
				if (isdgdk) {
					//FTP����
					if (gsdkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						gsdkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						gsdkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + gsdkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}
			}
		}

            for(BrMst br_mst:brMstList){
			String brNos=this.getAllChildBrByNotIn(br_mst.getBrNo(), br_mst.getManageLvl(), telNo);
			String[] br_no_s=null;
			br_no_s = brNos.split(",");
			double[][] returnValue = new double[29][2];//[i][0]Ϊ�ʲ��ĸ����������[i][1]Ϊ��ծ�ĸ�����������
			for(String br_no:br_no_s){
				//���ɵ�brNos�ַ�����br_no��������
				br_no = br_no.substring(1, br_no.length() -1);
				returnValue[0][0]+=rjye_map.get(br_no+"-1")==null?0.0:rjye_map.get(br_no+"-1");
				returnValue[1][0]+=ftplr_map.get(br_no+"-1")==null?0.0:ftplr_map.get(br_no+"-1");
				returnValue[2][0]+=bal_map.get(br_no+"-1")==null?0.0:bal_map.get(br_no+"-1");
				returnValue[3][0]+=grdkftplr_map.get(br_no+"-1")==null?0.0:grdkftplr_map.get(br_no+"-1");
				returnValue[4][0]+=gsdkftplr_map.get(br_no+"-1")==null?0.0:gsdkftplr_map.get(br_no+"-1");

				returnValue[0][1]+=rjye_map.get(br_no+"-2")==null?0.0:rjye_map.get(br_no+"-2");
				returnValue[1][1]+=ftplr_map.get(br_no+"-2")==null?0.0:ftplr_map.get(br_no+"-2");
				returnValue[2][1]+=bal_map.get(br_no+"-2")==null?0.0:bal_map.get(br_no+"-2");
				returnValue[3][1]+=grhqbal_map.get(br_no+"-2")==null?0.0:grhqbal_map.get(br_no+"-2");
				returnValue[4][1]+=grdqbal_map.get(br_no+"-2")==null?0.0:grdqbal_map.get(br_no+"-2");
				returnValue[5][1]+=dwhqbal_map.get(br_no+"-2")==null?0.0:dwhqbal_map.get(br_no+"-2");
				returnValue[6][1]+=dwdqbal_map.get(br_no+"-2")==null?0.0:dwdqbal_map.get(br_no+"-2");
				returnValue[7][1]+=czxbal_map.get(br_no+"-2")==null?0.0:czxbal_map.get(br_no+"-2");
				returnValue[8][1]+=yhkbal_map.get(br_no+"-2")==null?0.0:yhkbal_map.get(br_no+"-2");
				returnValue[9][1]+=yjhkbal_map.get(br_no+"-2")==null?0.0:yjhkbal_map.get(br_no+"-2");
				returnValue[10][1]+=bzjbal_map.get(br_no+"-2")==null?0.0:bzjbal_map.get(br_no+"-2");

				returnValue[11][1]+=grhqrjye_map.get(br_no+"-2")==null?0.0:grhqrjye_map.get(br_no+"-2");
				returnValue[12][1]+=grdqrjye_map.get(br_no+"-2")==null?0.0:grdqrjye_map.get(br_no+"-2");
				returnValue[13][1]+=dwhqrjye_map.get(br_no+"-2")==null?0.0:dwhqrjye_map.get(br_no+"-2");
				returnValue[14][1]+=dwdqrjye_map.get(br_no+"-2")==null?0.0:dwdqrjye_map.get(br_no+"-2");
				returnValue[15][1]+=czxrjye_map.get(br_no+"-2")==null?0.0:czxrjye_map.get(br_no+"-2");
				returnValue[16][1]+=yhkrjye_map.get(br_no+"-2")==null?0.0:yhkrjye_map.get(br_no+"-2");
				returnValue[17][1]+=yjhkrjye_map.get(br_no+"-2")==null?0.0:yjhkrjye_map.get(br_no+"-2");
				returnValue[18][1]+=bzjrjye_map.get(br_no+"-2")==null?0.0:bzjrjye_map.get(br_no+"-2");
                returnValue[19][1]+=ftplr_ds_map.get(br_no)==null?0.0:ftplr_ds_map.get(br_no);
                returnValue[20][1]+=ftplr_dg_map.get(br_no)==null?0.0:ftplr_dg_map.get(br_no);

				returnValue[21][1]+=grhqftplr_map.get(br_no+"-2")==null?0.0:grhqftplr_map.get(br_no+"-2");
				returnValue[22][1]+=grdqftplr_map.get(br_no+"-2")==null?0.0:grdqftplr_map.get(br_no+"-2");
				returnValue[23][1]+=dwhqftplr_map.get(br_no+"-2")==null?0.0:dwhqftplr_map.get(br_no+"-2");
				returnValue[24][1]+=dwdqftplr_map.get(br_no+"-2")==null?0.0:dwdqftplr_map.get(br_no+"-2");
				returnValue[25][1]+=czxftplr_map.get(br_no+"-2")==null?0.0:czxftplr_map.get(br_no+"-2");
				returnValue[26][1]+=yhkftplr_map.get(br_no+"-2")==null?0.0:yhkftplr_map.get(br_no+"-2");
				returnValue[27][1]+=yjhkftplr_map.get(br_no+"-2")==null?0.0:yjhkftplr_map.get(br_no+"-2");
				returnValue[28][1]+=bzjftplr_map.get(br_no+"-2")==null?0.0:bzjftplr_map.get(br_no+"-2");
            }
			QxppValue_map.put(br_mst.getBrNo()+"-1", new Double[]{returnValue[0][0],returnValue[1][0],returnValue[2][0],
					returnValue[3][0],returnValue[4][0]
			});
			QxppValue_map.put(br_mst.getBrNo()+"-2", new Double[]{returnValue[0][1],returnValue[1][1],returnValue[2][1]
			        ,returnValue[3][1],returnValue[4][1],returnValue[5][1]
					,returnValue[6][1],returnValue[7][1],returnValue[8][1]
					,returnValue[9][1],returnValue[10][1],returnValue[11][1]
					,returnValue[12][1],returnValue[13][1],returnValue[14][1]
					,returnValue[15][1],returnValue[16][1],returnValue[17][1]
					,returnValue[18][1],returnValue[19][1],returnValue[20][1]
					,returnValue[21][1],returnValue[22][1],returnValue[23][1]
					,returnValue[24][1],returnValue[25][1],returnValue[26][1]
					,returnValue[27][1],returnValue[28][1]
			});
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
	 * @param custType�ͻ����ͣ�0���У�2�Թ���1��˽
	 * @return map
	 */
	public Map<String,Double[]> getQxppValueMap_ywtxylfx(List<String[]> ftped_data_successList, String xlsBrNo, String brNos, String custType) {
		//key<��Ʒ��>
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> rjye_map=new HashMap<String,Double>();
		Map<String,Double> ftplr_map=new HashMap<String,Double>();
		Map<String,Double> bal_map=new HashMap<String,Double>(); //���map
		
		//����Ʒ��ŷŵ�map��
		for (String[] result : ftped_data_successList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//��¼�Ƿ�����ͳ�ƣ��ֶԹ��Ͷ�˽
			if(brNos.indexOf(result[0])!= -1 && isStatic) {//���Ҫ���ܵĻ����а���result[0]
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[6]); //���
				//�վ����
				if(rjye_map.get(result[1])==null){
					rjye_map.put(result[1], rjye);
				}else{
					rjye_map.put(result[1], rjye+rjye_map.get(result[1]));
				}
				//FTP����
				if(ftplr_map.get(result[1])==null){
					ftplr_map.put(result[1], ftplr);
				}else{
					ftplr_map.put(result[1], ftplr+ftplr_map.get(result[1]));
				}
				//���
				if(bal_map.get(result[1])==null){
					bal_map.put(result[1], bal);
				}else{
					bal_map.put(result[1], bal+bal_map.get(result[1]));
				}
			}
		}
		//���������Ӧ�����в�Ʒ
		String hsql = "from FtpBusinessStaticDivide t ";
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = daoFactory.query(hsql, null);
		double[] returnValue = new double[3];
		for(FtpBusinessStaticDivide ftpBusinessStaticDivide:ftpBusinessStaticDivideList){
			returnValue[0]=rjye_map.get(ftpBusinessStaticDivide.getProductNo())==null?0.0:rjye_map.get(ftpBusinessStaticDivide.getProductNo());
			returnValue[1]=ftplr_map.get(ftpBusinessStaticDivide.getProductNo())==null?0.0:ftplr_map.get(ftpBusinessStaticDivide.getProductNo());
			returnValue[2]=bal_map.get(ftpBusinessStaticDivide.getProductNo())==null?0.0:bal_map.get(ftpBusinessStaticDivide.getProductNo());
			QxppValue_map.put(ftpBusinessStaticDivide.getProductNo(), new Double[]{returnValue[0],returnValue[1],returnValue[2]});
		}
		return QxppValue_map;
	}
	
	/**
	 * ��Ʒ�ֻ����������ftpResultList�е����ݣ����ղ�Ʒ-�����ŵ���Ӧ��map��
	 * @param ftped_data_successList
	 * @param xlsBrNo������
	 * @param brMstList Ҫ��ȡ����ֵ������ֱ����ʾ�Ļ������ݱ�����б�(2��1��0��������֧��)
	 * @param custType�ͻ����ͣ�0���У�2�Թ���1��˽
	 * @param telNo����Ա���
	 * @return map
	 */
	public Map<String,Double[]> getQxppValueMap_cpfjglrb(List<String[]> ftpResultList, String xlsBrNo, List<BrMst> brMstList, String custType, String telNo) {
		//key<��Ʒ��-������>
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> rjye_map=new HashMap<String,Double>();
		Map<String,Double> ftplr_map=new HashMap<String,Double>();
		Map<String,Double> bal_map=new HashMap<String,Double>();
		
		for (String[] result : ftpResultList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//��¼�Ƿ�����ͳ�ƣ��ֶԹ��Ͷ�˽
			if(isStatic) {
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[6]);
				//�վ����
				if(rjye_map.get(result[1]+"-"+result[0])==null){
					rjye_map.put(result[1]+"-"+result[0], rjye);
				}else{
					rjye_map.put(result[1]+"-"+result[0], rjye+rjye_map.get(result[1]+"-"+result[0]));
				}
				//FTP����
				if(ftplr_map.get(result[1]+"-"+result[0])==null){
					ftplr_map.put(result[1]+"-"+result[0], ftplr);
				}else{
					ftplr_map.put(result[1]+"-"+result[0], ftplr+ftplr_map.get(result[1]+"-"+result[0]));
				}

				//FTP���
				if(bal_map.get(result[1]+"-"+result[0])==null){
					bal_map.put(result[1]+"-"+result[0], bal);
				}else{
					bal_map.put(result[1]+"-"+result[0], bal+bal_map.get(result[1]+"-"+result[0]));
				}
			}
		}

		//���������Ӧ�����в�Ʒ
		String hsql = "from FtpBusinessStaticDivide t ";
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = daoFactory.query(hsql, null);
		
		//ѭ������
		for(BrMst br_mst:brMstList){
			String brNos=this.getAllChildBrByNotIn(br_mst.getBrNo(), br_mst.getManageLvl(), telNo);
			String[] br_no_s=null;
			br_no_s = brNos.split(",");
			for(FtpBusinessStaticDivide ftpProductMethodRel:ftpBusinessStaticDivideList){
				double[] returnValue = new double[3];
				for(String br_no:br_no_s){
					//���ɵ�brNos�ַ�����br_no��������
					br_no = br_no.substring(1, br_no.length() -1);
					returnValue[0]+=rjye_map.get(ftpProductMethodRel.getProductNo()+"-"+br_no)==null?0.0:rjye_map.get(ftpProductMethodRel.getProductNo()+"-"+br_no);
					returnValue[1]+=ftplr_map.get(ftpProductMethodRel.getProductNo()+"-"+br_no)==null?0.0:ftplr_map.get(ftpProductMethodRel.getProductNo()+"-"+br_no);
					returnValue[2]+=bal_map.get(ftpProductMethodRel.getProductNo()+"-"+br_no)==null?0.0:bal_map.get(ftpProductMethodRel.getProductNo()+"-"+br_no);
				}
				QxppValue_map.put(ftpProductMethodRel.getProductNo()+"-"+br_mst.getBrNo(), new Double[]{returnValue[0],returnValue[1],returnValue[2]});
			}
		}
		return QxppValue_map;
	}
	/**
	 * ��Ʒ�ֻ��������-����ĳһ�����������ݣ���ftpResultList�е����ݣ����ղ�Ʒ-�����ŵ���Ӧ��map��
	 * @param ftped_data_successList
	 * @param xlsBrNo������
	 * @param brNos
	 * @param custType�ͻ����ͣ�0���У�2�Թ���1��˽
	 * @param prdtNos
	 * @return map
	 */
	public Double[] getQxppValue_cpfjglrb(List<String[]> ftpResultList, String brNos, String custType, String prdtNos) {
		Double[] resultValue = new Double[3];
		double rjye = 0;
		double ftplr = 0;
		double bal = 0;
		
		for (String[] result : ftpResultList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//��¼�Ƿ�����ͳ�ƣ��ֶԹ��Ͷ�˽
			if(isStatic) {
				if(prdtNos.indexOf(result[1]) != -1 && (brNos.indexOf(result[0])!= -1|| brNos.equals("is not null"))) {
					rjye += Double.valueOf(result[3]);
				    ftplr += Double.valueOf(result[4]);
					bal += Double.valueOf(result[6]);
				}
			}
		}
		resultValue[0] = rjye;
		resultValue[1] = ftplr;
		resultValue[2] = bal;
		return resultValue;
	}
	/**
	 * ���˰��һ����������ftpResultList�е����ݣ����ջ����ŵ���Ӧ��map�У��Ƿ�չ��Ϊ2��
	 * @param ftped_data_successList
	 * @param xlsBrNo������
	 * @param brMstList Ҫ��ȡ����ֵ������ֱ����ʾ�Ļ������ݱ�����б�(2��1��0��������֧��)
	 * @param custType�ͻ����ͣ�0���У�2�Թ���1��˽
	 * @param telNo����Ա���
	 * @return map
	 */
	public Map<String,Double[]> getQxppValueMap_grajjglrb(List<String[]> ftpResultList, String xlsBrNo, List<BrMst> brMstList, String custType, String telNo) {
		//key<��Ʒ��-������>
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> rjye_map=new HashMap<String,Double>();
		Map<String,Double> ftplr_map=new HashMap<String,Double>();
		Map<String,Double> bal_map=new HashMap<String,Double>();

		for (String[] result : ftpResultList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[9]);//��¼�Ƿ�����ͳ�ƣ��ֶԹ��Ͷ�˽
			if(isStatic && result[7].equals("2")) {//�Ƿ�չ��Ϊ2��
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[8]);
				//�վ����
				if(rjye_map.get(result[0])==null){
					rjye_map.put(result[0], rjye);
				}else{
					rjye_map.put(result[0], rjye+rjye_map.get(result[0]));
				}
				//FTP����
				if(ftplr_map.get(result[0])==null){
					ftplr_map.put(result[0], ftplr);
				}else{
					ftplr_map.put(result[0], ftplr+ftplr_map.get(result[0]));
				}

				//FTP���
				if(bal_map.get(result[0])==null){
					bal_map.put(result[0], bal);
				}else{
					bal_map.put(result[0], bal+bal_map.get(result[0]));
				}
			}
		}
		for(BrMst br_mst:brMstList){
			String brNos=this.getAllChildBrByNotIn(br_mst.getBrNo(), br_mst.getManageLvl(), telNo);
			String[] br_no_s=null;
			br_no_s = brNos.split(",");
			double[] returnValue = new double[3];
			for(String br_no:br_no_s){
				//���ɵ�brNos�ַ�����br_no��������
				br_no = br_no.substring(1, br_no.length() -1);
				returnValue[0]+=rjye_map.get(br_no)==null?0.0:rjye_map.get(br_no);
				returnValue[1]+=ftplr_map.get(br_no)==null?0.0:ftplr_map.get(br_no);
				returnValue[2]+=bal_map.get(br_no)==null?0.0:bal_map.get(br_no);
			}
			QxppValue_map.put(br_mst.getBrNo(), new Double[]{returnValue[0],returnValue[1],returnValue[2]});
		}
		return QxppValue_map;
	}
	
	/**
	 * ���˰��һ��������-ĳ�������Ļ��ܣ��Ƿ�չ��Ϊ2��
	 * @param ftped_data_successList
	 * @param brNosҪ���ܵĻ����ַ�������'1801031009','1801031008'
	 * @return �վ���FTP����
	 * @paramm custType
	 */
	public double[] getQxppValueMap_grajjglrbhz(List<String[]> ftpResultList, String brNos, String custType) {
		double[] returnValue = {0.0, 0.0,0.0};
		double rjye = 0.0, ftplr = 0.0,bal=0;
		for (String[] result : ftpResultList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[9]);//��¼�Ƿ�����ͳ�ƣ��ֶԹ��Ͷ�˽
			if(isStatic) {
				if(result[7].equals("2") && (brNos.indexOf(result[0])!= -1|| brNos.equals("is not null"))) {
					rjye += Double.valueOf(result[3]);
					ftplr += Double.valueOf(result[4]);
					bal += Double.valueOf(result[8]);
				}
			}
		}
		returnValue[0] = rjye;
		returnValue[1] = ftplr;
		returnValue[2] = bal;
		return returnValue;
	}
	/**
	 * ��ftpResultList�е����ݣ����տͻ�����-����(ye/lr����������)�ŵ���Ӧ��map��
	 * @param ftped_data_successList
	 * @param xlsBrNo������
	 * @param brMstList Ҫ��ȡ����ֵ������ֱ����ʾ�Ļ������ݱ�����б�(2��1��0��������֧��)
	 * @param custType�ͻ����ͣ�0���У�2�Թ���1��˽
	 * @param telNo����Ա���
	 * @return map
	 */
	public Map<String, Double> getQxppValueMap_khjl(List<String[]> ftpResultList, String prdtNos, String custType) {
		Map<String, Double> QxppValue_map = new HashMap<String, Double>();//key:�ͻ�������-����(ye/lr����������)
		//�ӽ�����л�ȡĳЩ�ͻ���������ݣ����ݿͻ������Ż��ܵ�map��
		for (String[] result : ftpResultList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//��¼�Ƿ�����ͳ�ƣ��ֶԹ��Ͷ�˽
			if(prdtNos.indexOf(result[1])!=-1 && isStatic) {//ͳ�ƶ�Ӧ�Ĳ�Ʒ��
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[6]);

				//�վ����
				if(QxppValue_map.get(result[2]+"-ye")==null){
					QxppValue_map.put(result[2]+"-ye", rjye);
				}else{
					QxppValue_map.put(result[2]+"-ye", rjye+QxppValue_map.get(result[2]+"-ye"));
				}
				//FTP����
				if(QxppValue_map.get(result[2]+"-lr")==null){
					QxppValue_map.put(result[2]+"-lr", ftplr);
				}else{
					QxppValue_map.put(result[2]+"-lr", ftplr+QxppValue_map.get(result[2]+"-lr"));
				}

				//FTP���
				if(QxppValue_map.get(result[2]+"-bal")==null){
					QxppValue_map.put(result[2]+"-bal", bal);
				}else{
					QxppValue_map.put(result[2]+"-bal", bal+QxppValue_map.get(result[2]+"-bal"));
				}
			}
		}
		return QxppValue_map;
	}
	/**
	 * ��ftpResultList�е����ݣ���ĳ���ͻ������ղ�Ʒ�ŷŵ���Ӧ��map��
	 * @param ftped_data_successList
	 * @param xlsBrNo������
	 * @param brMstList Ҫ��ȡ����ֵ������ֱ����ʾ�Ļ������ݱ�����б�(2��1��0��������֧��)
	 * @param custType�ͻ����ͣ�0���У�2�Թ���1��˽
	 * @param telNo����Ա���
	 * @return map
	 */
	public Map<String, Double> getQxppValueMap_khjlfcp(List<String[]> ftpResultsList, String empNo, String custType) {
		Map<String, Double> QxppValue_map = new HashMap<String, Double>();//key:��Ʒ��
		//�ӽ�����л�ȡĳ���ͻ���������ݣ����ݲ�Ʒ�Ż��ܵ�map��
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//��¼�Ƿ�����ͳ�ƣ��ֶԹ��Ͷ�˽
			if(empNo.equals(result[2]) && isStatic) {//��Ҫͳ�ƵĿͻ��������ͬ
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[6]);

				//�վ����
				if(QxppValue_map.get(result[1]+"-ye")==null){
					QxppValue_map.put(result[1]+"-ye", rjye);
				}else{
					QxppValue_map.put(result[1]+"-ye", rjye+QxppValue_map.get(result[1]+"-ye"));
				}
				//FTP����
				if(QxppValue_map.get(result[1]+"-lr")==null){
					QxppValue_map.put(result[1]+"-lr", ftplr);
				}else{
					QxppValue_map.put(result[1]+"-lr", ftplr+QxppValue_map.get(result[1]+"-lr"));
				}

				if(QxppValue_map.get(result[1]+"-bal")==null){
					QxppValue_map.put(result[1]+"-bal", bal);
				}else{
					QxppValue_map.put(result[1]+"-bal", bal+QxppValue_map.get(result[1]+"-bal"));
				}
			}
		}
		return QxppValue_map;
	}
	/**
	 * ����ͳ�ƴ����ȡ��Ʒ���࣬��ȡͳ�ƴ����������ʲ���ծ��Ĳ�Ʒ
	 * @param staticNo
	 * @return
	 */
	public String[][] getPrdtCtgNos(String businessNo, String brNo) {
		String[][] prdtCtgNos = null;
		String sql = "select distinct Product_ctg_no, Product_ctg_name" +
		" from ftp.FTP_business_static_divide" +
		" where business_no='"+businessNo+"'"+
		" order by Product_ctg_no";
		List list = daoFactory.query1(sql, null);
		if(list != null && list.size() > 0) {
			prdtCtgNos = new String[list.size()][2];
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				if(obj[0] == null) {
					continue;
				}
				prdtCtgNos[i][0] = obj[0].toString();
				prdtCtgNos[i][1] = obj[1].toString();
			}
		}
		return prdtCtgNos;
	}
	
	/**
	 * ��ȡ�û����µ�����1������+����(�����1������)
	 * @param brNo
	 * @return
	 */
	public List<BrMst> getBrLvl1List(String brNo, String telNo) {
		List<BrMst> brMstList = brMstMap.get(telNo);//��ȡ�ò���Ա��Ͻ�Ļ���

		if(brMstList == null || brMstList.size() == 0) {
			String hsql = "from BrMst where (brNo ='"+brNo+"' or superBrNo = '"+brNo+"') and manageLvl ='1' order by brNo";
			brMstList = daoFactory.query(hsql, null);
		}
		
		return brMstList;
	}
	
	/**
	 * ���ݻ�����ţ���ȡ���е��¼�����+����(��������߼���2�Ļ���)����,�ָ�
	 * @param br_no
	 * @return
	 */
	public String getAllChildBr(String br_no, String br_level, String tel_no) {
		String br_nos="";
		if("3".equals(br_level)){
			br_nos="is not null";//likeЧ��̫������������ ������ÿ����Ŀȡ�������崦��:��like�죬�Ͳ����� �� is not null��ࡣ
		}else if("2".equals(br_level)){
			br_nos+="'"+br_no+"',";
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList0=daoFactory.query1(sql, null);
			for(Object obj0:br_noList0){
				br_nos+="'"+obj0.toString()+"',";
				String sql1="select br_no from ftp.br_mst where super_br_no='"+obj0+"'";
				List br_noList01=daoFactory.query1(sql1, null);
				for(Object obj01:br_noList01){
					br_nos+="'"+obj01.toString()+"',";
				}
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
			br_nos="in ("+br_nos+")";
		}else if("1".equals(br_level)){
			br_nos="'"+br_no+"',";//�Ȱ��Լ�����ӽ�ȥ
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList=daoFactory.query1(sql, null);
			for(Object obj:br_noList){				
				br_nos+="'"+obj.toString()+"',";		
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
			br_nos="in ("+br_nos+")";
		}else{
			br_nos="'"+br_no+"'";
			br_nos="in ("+br_nos+")";
		}

		return br_nos;
	}


	/**
	 * ���ݻ�����ţ���ȡ���е��¼�����+����(��������߼���2�Ļ���)����,�ָ�
	 * @param br_no
	 * @return
	 */
	public String getAllChildBrByNotIn(String br_no, String br_level, String tel_no) {
		String br_nos="";
		if("3".equals(br_level)){
			br_nos="is not null";//likeЧ��̫������������ ������ÿ����Ŀȡ�������崦��:��like�죬�Ͳ����� �� is not null��ࡣ
		}else if("2".equals(br_level)){
			br_nos+="'"+br_no+"',";
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList0=daoFactory.query1(sql, null);
			for(Object obj0:br_noList0){
				br_nos+="'"+obj0.toString()+"',";
				String sql1="select br_no from ftp.br_mst where super_br_no='"+obj0+"'";
				List br_noList01=daoFactory.query1(sql1, null);
				for(Object obj01:br_noList01){
					br_nos+="'"+obj01.toString()+"',";
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
	 * ���ݻ�����ţ���ȡ���е��¼�����+����(��������߼���2�Ļ���)����,�ָ�
	 * @param br_no
	 * @return
	 */
	public String getAllChildBrAllbrno(String br_no, String br_level, String tel_no) {
		String br_nos="";
		if("3".equals(br_level)){
			br_nos+="'"+br_no+"',";
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList0=daoFactory.query1(sql, null);
			for(Object obj0:br_noList0){
				String sql1="select br_no from ftp.br_mst where super_br_no='"+obj0+"'";
				List br_noList01=daoFactory.query1(sql1, null);
				for(Object obj01:br_noList01){
					br_nos+="'"+obj01+"',";
					String sql2="select br_no from ftp.br_mst where super_br_no='"+obj01+"'";
					List br_noList02=daoFactory.query1(sql2, null);
					for(Object obj02:br_noList02){
						br_nos+="'"+obj02.toString()+"',";
					}
				}
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
			br_nos="in ("+br_nos+")";
		}else if("2".equals(br_level)){
			br_nos+="'"+br_no+"',";
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList0=daoFactory.query1(sql, null);
			for(Object obj0:br_noList0){
				br_nos+="'"+obj0+"',";
				String sql1="select br_no from ftp.br_mst where super_br_no='"+obj0+"'";
				List br_noList01=daoFactory.query1(sql1, null);
				for(Object obj01:br_noList01){
					br_nos+="'"+obj01.toString()+"',";
				}
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
			br_nos="in ("+br_nos+")";
		}else if("1".equals(br_level)){
			br_nos="'"+br_no+"',";//�Ȱ��Լ�����ӽ�ȥ
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList=daoFactory.query1(sql, null);
			for(Object obj:br_noList){
				br_nos+="'"+obj.toString()+"',";
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
			br_nos="in ("+br_nos+")";
		}else{
			br_nos="'"+br_no+"'";
			br_nos="in ("+br_nos+")";
		}

		return br_nos;
	}

	
	/**
	 * ����������ں�ͳ��ά�Ȼ�ȡ��С����
	 * @param maxDate
	 * @param assessScope-1�¶�-3����-12���
	 * @return
	 */
	public String getMinDate(String maxDate,Integer assessScope) {
		int nowMonth = Integer.valueOf(String.valueOf(maxDate).substring(4, 6));//��ǰ��
		if(assessScope == -3) {//����
			if (nowMonth >= 1 && nowMonth <= 3)
				assessScope = 0 - nowMonth;
	        else if (nowMonth >= 4 && nowMonth <= 6)
	        	assessScope =  3 - nowMonth;
	        else if (nowMonth >= 7 && nowMonth <= 9)
	        	assessScope = 6 - nowMonth;
	        else if (nowMonth >= 10 && nowMonth <= 12)
	        	assessScope = 9 - nowMonth;
		}else if(assessScope == -12) {//���
			assessScope = -nowMonth;
		}
		String minDate = CommonFunctions.dateModifyM(maxDate, assessScope);
		return minDate;
	}
	/**
	 * ���ݲ���Ա��Ӧ��Ա�����������źͶ�Ӧ�Ľ�ɫ���𣬻�ȡ�����Բ鿴�Ŀͻ�����
	 * @param brNo
	 * @param roleLvl
	 * @return
	 */
	public String getCustTypeByBrNoAndRoleLvl(String brNo, String roleLvl) {
		String custType = "0";//��ѯ��������
		if(brNo.equals("3403112004") && roleLvl.equals("2")) {
			custType = "2";//�������Ա��Ӧ����Ϊ��˾���в����Ҷ�Ӧ��ɫ����Ϊ-�м�(�ܾ���),�鿴�Թ�ҵ������
		}else if(brNo.equals("3403111105") && roleLvl.equals("2")) {
			custType = "1";//�������Ա��Ӧ����Ϊ�������в����Ҷ�Ӧ��ɫ����Ϊ-�м�(�ܾ���),�鿴��˽ҵ������
		}
		
		return custType;
	}
	
	/**
	 * ����ͳ�ƿͻ����ͺͿͻ��ţ����жϵ�ǰ��¼�Ƿ�����ͳ��
	 * @param custType
	 * @param custNo
	 * @return
	 */
	public boolean getIsStatic(String custType, String custNo, String kmh) {
		boolean isStatic = false;
		boolean isdg=false;
		boolean isds=false;

		String dkkmh ="1301,1302,1303,1304,1305,1307,1308";//�����Ŀ(��ȥ����)
		String dgkmh = "2001,2002,2006,2011,2014,1306";//�Թ���Ŀ��
		String dskmh = "2003,2004,2005";//��˽��Ŀ��
		if(kmh!=null&&kmh.length()>=4){
			isdg= dgkmh.indexOf(kmh.substring(0,4)) == -1 ? false : true;
			isds = dskmh.indexOf(kmh.substring(0,4)) == -1 ? false : true;

			if(dkkmh.indexOf(kmh.substring(0,4)) != -1){
				if(custNo!=null&&!"".equals(custNo)){
					if(custNo.startsWith("1")){
						isds=true;
					}
				}
			}

			if(dkkmh.indexOf(kmh.substring(0,4)) != -1){
				if(custNo!=null&&!"".equals(custNo)){
					if(custNo.startsWith("2")){
						isdg=true;
					}
				}
			}
		}

		if(custType.equals("2")){
			if(isdg) {//ͳ�ƶԹ��ͻ����ǽ���̨�˲�¼�Ļ򣨿ͻ�����2��ͷ�Ļ���Ϊ�գ�����=*��
				isStatic = true;
			}
		}else if(custType.equals("1")){
			if(isds) {//ͳ�ƶ�˽�ͻ������ǽ���̨�˲�¼���ҿͻ�����1,3��ͷ��
				isStatic = true;
			}
		}
		else {//�������ж�����ͳ��
			isStatic = true;
		}
		return isStatic;
	}

	/**
	 * ����ͳ�ƿͻ����ͺͿͻ��ţ����жϵ�ǰ��¼�Ƿ�����ͳ��
	 * @param custType
	 * @param custNo
	 * @return
	 */
	public boolean getIsStaticDk(String custType, String custNo, String kmh) {
		boolean isStatic = false;
		boolean isdg=false;
		boolean isds=false;

		String dkkmh ="1301,1302,1303,1304,1305,1307,1308";//�����Ŀ(��ȥ����)
		if(kmh!=null&&kmh.length()>=4){
			if(dkkmh.indexOf(kmh.substring(0,4)) != -1){
				if(custNo!=null&&!"".equals(custNo)){
					if(custNo.startsWith("1")){
						isds=true;
					}
				}
			}

			if(dkkmh.indexOf(kmh.substring(0,4)) != -1){
				if(custNo!=null&&!"".equals(custNo)){
					if(custNo.startsWith("2")){
						isdg=true;
					}
				}
			}
		}

		if(custType.equals("2")){
			if(isdg) {//ͳ�ƶԹ��ͻ����ǽ���̨�˲�¼�Ļ򣨿ͻ�����2��ͷ�Ļ���Ϊ�գ�����=*��
				isStatic = true;
			}
		}else if(custType.equals("1")){
			if(isds) {//ͳ�ƶ�˽�ͻ������ǽ���̨�˲�¼���ҿͻ�����1,3��ͷ��
				isStatic = true;
			}
		}
		else {//�������ж�����ͳ��
			isStatic = true;
		}
		return isStatic;
	}

	
	/**
	 * ��ȡTelBrConfig���У�����Ա�����õĻ���
	 * @param telNo
	 * @return MAP(KEY:TelNo��VALUE:List<BrMst>)
	 */
	public Map<String, List<BrMst>> getBrByEmpNo() {
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
	/**
	 * ��FtpBusinessStaticDivide���л�ȡ��Ҫ�Ĳ�Ʒ
	 * @param xlsBrNo
	 * @param businessNo
	 * @param prdtCtgNo
	 * @param prdtNo
	 * @return
	 */
	public List<FtpBusinessStaticDivide> getFtpBSDivideList(String xlsBrNo, String businessNo, String prdtCtgNo, String prdtNo) {
		//����ҵ�����߱�Ż��߲�Ʒ�����Ż�ȡ��Ӧ�Ĳ�Ʒ���
		String hsql = "from FtpBusinessStaticDivide where 1=1";
		
		//�ж�˳�򣺰���Ʒ��Ȼ���ǲ�Ʒ���࣬�����ҵ������
		if(prdtNo != null && !prdtNo.equals("")){//����Ʒ���в�ѯ
			hsql += " and productNo = '"+prdtNo+"'";
		}else if(prdtCtgNo != null && !prdtCtgNo.equals("")) {//����Ʒ������в�ѯ
			hsql += " and productCtgNo = '"+prdtCtgNo+"'";
		}else if(businessNo != null && !businessNo.equals("")) {//��ҵ�����߽��в�ѯ
			hsql += " and businessNo = '"+businessNo+"'";
		}
		hsql += " order by businessNo,productCtgNo,productNo";
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = daoFactory.query(hsql, null);
		return ftpBusinessStaticDivideList;
	}
	
	/**
	 * ��map����value��������
	 * @param map
	 * @return
	 */
	public static Map sortByValue(Map map) {
		// ����ֵ��Ӯ��entryset�ŵ�������
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {

			// ��������ֵ�ôӴ�С��������
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue())
						.compareTo(((Map.Entry) (o1)).getValue());
			}
		});

		Map result = new LinkedHashMap();

		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/**
	 * ��ȡҪ��ȡaccIds�Ŀ�ʼ�ͽ���λ��
	 * @param rowsCount
	 * @param pageSize
	 * @param currentPage
	 * @param accIds
	 * @return
	 */
	public Integer[] getStartAndEnd(int rowsCount, int pageSize, int currentPage, String accIds) {
		Integer[] result = new Integer[2];
		pageSize=pageSize<1?100:pageSize;//ÿҳ��ʾ����
		currentPage=currentPage<1?1:currentPage;//�ڼ�ҳ
		int pageCount=rowsCount/pageSize;//��ҳ��
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		int start=0;
		if(currentPage != 1) {//���ǵ�һҳ
			start = this.getCharacterPosition(accIds.toString(), ",", (currentPage-1)*pageSize)+1;//��ǰ��ʾ�ӵ�start��,���ֵ�λ��+1
		}
		int end = 0;
		if(currentPage==rowsCount/pageSize+1){//���һҳ
			end=accIds.length();
		}else {
			end=this.getCharacterPosition(accIds.toString(), ",", currentPage*pageSize);//����end��,���ֵ�λ��
		}
		result[0] = start;
		result[1] = end;
		return result;
	}
	/**
	 * ��ȡĳ���ַ�s���ַ���string�г��ֵ�n�ε�λ��--n��֧��0
	 * @param string
	 * @param s
	 * @param n
	 * @return
	 */
	public static int getCharacterPosition(String string, String s, int n){
	    //�����ǻ�ȡs���ŵ�λ��
	    Matcher slashMatcher = Pattern.compile(",").matcher(string);
	    int mIdx = 0;
	    while(slashMatcher.find()) {
	       mIdx++;
	       //��s���ŵ�n�γ��ֵ�λ��
	       if(mIdx == n){
	          break;
	       }
	    }
	    return slashMatcher.start();
	 }
	/**
	 * ����������brNo��Ҫ��ȡ�Ļ������𣬻�ȡ�������¸ü�������л���,���漰��Ȩ��
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
		}else if(manageLvl.equals("2")) {
			hsql = "from BrMst where superBrNo = '"+xlsBrNo+"'";
			List<BrMst> brMstList1 = daoFactory.query(hsql, null);
			for(BrMst brMst : brMstList1) {
				hsql = "from BrMst where superBrNo = '"+brMst.getBrNo()+"'";
				List<BrMst> brMstList2 = daoFactory.query(hsql, null);
				for(BrMst brMst2 : brMstList2) {
					hsql = "from BrMst where superBrNo = '"+brMst2.getBrNo()+"'";
					List<BrMst> brMstList3 = daoFactory.query(hsql, null);
					brMstList.addAll(brMstList3);
				}
			}
		}
		return brMstList;
	}
	
	/**
	 * ��ȡ�������¼�����(ĳЩ����Ա�л���Ȩ��)
	 * @param br_no
	 * @param tel_no
	 * @return
	 */
	public List<BrMst> getChildBrListByTel(String br_no, String tel_no) {
		List<BrMst> brMstList = new ArrayList<BrMst>();
		List<BrMst> brMstListN = new ArrayList<BrMst>();
		String[] brInfos = LrmUtil.getBr_lvlInfo(br_no);
		if("3".equals(brInfos[0])){
			ReportBbBO reportBbBO = new ReportBbBO();
			List<BrMst> brList = reportBbBO.brMstMap.get(tel_no);//��ȡ�ò���Ա��Ͻ�Ļ���
			if(brList == null || brList.size() == 0) {
				String sql="from BrMst where superBrNo='"+br_no+"'";
				brMstList=daoFactory.query(sql, null);
				for(BrMst brMst:brMstList){
					 sql="from BrMst where superBrNo='"+brMst.getBrNo()+"'";
					 brMstList=daoFactory.query(sql, null);
					 brMstListN.addAll(brMstList);
				}
			}else {//�м��³��������в�����
				brMstList=brList;
			}
		}if("2".equals(brInfos[0])){
			ReportBbBO reportBbBO = new ReportBbBO();
			List<BrMst> brList = reportBbBO.brMstMap.get(tel_no);//��ȡ�ò���Ա��Ͻ�Ļ���
			if(brList == null || brList.size() == 0) {
				String sql="from BrMst where superBrNo='"+br_no+"'";
				brMstList=daoFactory.query(sql, null);
			}else {//�м��³��������в�����
				brMstList=brList;
			}
		}else {
			String sql="from BrMst where superBrNo='"+br_no+"'";
			brMstList=daoFactory.query(sql, null);
		}

		return brMstList;
	}
	
	/**
	 * �жϸû����Ƿ�ΪҶ�ӽڵ�
	 * @param brNo
	 * @return
	 */
	public boolean getIsLeaf(String brNo) {
		boolean isLeaf = true;
		List<BrMst> brMstList = new ArrayList<BrMst>();
		String sql="from BrMst where superBrNo='"+brNo+"'";//�ж��Ƿ����Ӽ�����
		brMstList=daoFactory.query(sql, null);
		if(brMstList != null && brMstList.size() > 0) {
			isLeaf = false;
		}
		return isLeaf;
	}
	/**
	 * ��ȡ����Ա����Ա����š�Ա�������������������Լ�����������Ӧ���ϼ�����
	 * @return
	 */
	public Map<String, String[]> getEmpBrInfoMap() {
		Map<String, String[]> resultMap = new HashMap<String, String[]>();
		String sql = "select a.emp_no, a.emp_name, a.br_no, b.br_name, c.br_no as super_br_no, c.br_name as super_br_name " +
				" from ftp.ftp_emp_info a " +
				" left join ftp.br_mst b on a.br_no = b.br_no " +
				" left join ftp.br_mst c on b.super_br_no = c.br_no";
		List brInfoList = daoFactory.query1(sql, null);
		for(Object object : brInfoList) {
			Object[] o = (Object[])object;
			String[] brInfo = new String[5];
			brInfo[0] = String.valueOf(o[1]);
			brInfo[1] = String.valueOf(o[2]);
			brInfo[2] = String.valueOf(o[3]);
			brInfo[3] = String.valueOf(o[4]);
			brInfo[4] = String.valueOf(o[5]);
			resultMap.put(String.valueOf(o[0]), brInfo);
		}
		return resultMap;
	}

	/**
	 * ��ȡ��Ʒ����
	 * @param brNo
	 * @return
	 */
	public Map<String,List<String>> getPrdtCtgMap() {
		Map<String,List<String>> listHashMap =new HashMap<String,List<String>>();
		String sql="from FtpBusinessStaticDivide";//�ж��Ƿ����Ӽ�����
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivides=daoFactory.query(sql, null);
		if(ftpBusinessStaticDivides != null && ftpBusinessStaticDivides.size() > 0) {
			List<String> grhqList = new ArrayList<String>();
			List<String> grdqList = new ArrayList<String>();
			List<String> dwhqList = new ArrayList<String>();
			List<String> dwdqList = new ArrayList<String>();

			List<String> yhkList = new ArrayList<String>();
			List<String> czxList = new ArrayList<String>();
			List<String> yjhkList = new ArrayList<String>();
			List<String> bzjList = new ArrayList<String>();
             for(FtpBusinessStaticDivide ftpBusinessStaticDivide:ftpBusinessStaticDivides){
				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2003")){
					 grhqList.add(ftpBusinessStaticDivide.getProductNo());
 				 }

				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2004")){
					 grdqList.add(ftpBusinessStaticDivide.getProductNo());
				 }

				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2002")){
					 dwdqList.add(ftpBusinessStaticDivide.getProductNo());
				 }

				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2001")){
					 dwhqList.add(ftpBusinessStaticDivide.getProductNo());
				 }

				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2005")){
					 yhkList.add(ftpBusinessStaticDivide.getProductNo());
				 }

				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2006")){
					 czxList.add(ftpBusinessStaticDivide.getProductNo());
				 }

				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2008")){
					 yjhkList.add(ftpBusinessStaticDivide.getProductNo());
				 }

				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2011")){
					 bzjList.add(ftpBusinessStaticDivide.getProductNo());
				 }
			}

			listHashMap.put("grhq",grhqList);
			listHashMap.put("grdq",grdqList);
			listHashMap.put("dwhq",dwhqList);
			listHashMap.put("dwdq",dwdqList);
			listHashMap.put("yhk",yhkList);
			listHashMap.put("bzj",bzjList);
			listHashMap.put("yjhk", yjhkList);
			listHashMap.put("czx",czxList);
		}
		return listHashMap;
	}

	public static void main(String[] args) {
		String a = "1@ @";
		String[] as = a.split("@");
		for(int i = 0; i < as.length ; i++) {

			System.out.println("??????????"+as[i]);
		}
		
	}
}
