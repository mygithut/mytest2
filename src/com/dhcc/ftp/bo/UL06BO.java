package com.dhcc.ftp.bo;
/**
 * @desc:����ƥ��
 * @author :�����
 * @date ��2012-04-17
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.entity.Ftp1PrdtCkzbjAdjust;
import com.dhcc.ftp.entity.Ftp1PrdtClAdjust;
import com.dhcc.ftp.entity.Ftp1PrdtIrAdjust;
import com.dhcc.ftp.entity.Ftp1PrdtLdxAdjust;
import com.dhcc.ftp.entity.Ftp1PrepayAdjust;
import com.dhcc.ftp.entity.FtpBusinessInfo;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.PageUtil;

public class UL06BO extends BaseBo{

	/**
	 * ��ҳ��ѯ
	 * @param request
	 * @param page
	 * @param checkAll
	 * @param isQuery
	 * @param brNo
	 * @param curNo
	 * @param businessNo
	 * @param opnDate1
	 * @param opnDate2
	 * @param mtrDate1
	 * @param mtrDate2
	 * @return
	 */
	public PageUtil dofind(HttpServletRequest request, int page, String checkAll, String isQuery, String brNo, String prdtNo, String curNo, String businessNo, String opnDate1, String opnDate2, String mtrDate1, String mtrDate2) {
		return uL06DAO.dofind(request, page, checkAll, isQuery, brNo, prdtNo, curNo, businessNo, opnDate1, opnDate2, mtrDate1, mtrDate2);
	}
	/**
	 * ����list���з�ҳ
	 * @param request
	 * @param page
	 * @param date
	 * @param ftped_dataList
	 * @param url
	 * @return
	 */
	public PageUtil doFY(HttpServletRequest request, int page, String date, List<FtpBusinessInfo> ftped_dataList, String url) {
		return uL06DAO.doFY(request, page, date, ftped_dataList, url);
	}
	
	/**
	 * �����Ʒ�Ĵ��׼�������
	 * @return
	 */
	public Map<Integer, Double> getCkzbjAdjustValue() {
		Map<Integer, Double> ckzbjAdjustMap = new HashMap<Integer, Double>();
		String hsql = "from Ftp1PrdtCkzbjAdjust order by termType";
		List<Ftp1PrdtCkzbjAdjust> list = daoFactory.query(hsql, null);
		for(Ftp1PrdtCkzbjAdjust ftp1PrdtCkzbjAdjust : list) {
			ckzbjAdjustMap.put(ftp1PrdtCkzbjAdjust.getTermType(), ftp1PrdtCkzbjAdjust.getAdjustValue());
		}
		return ckzbjAdjustMap;
	}
	
	/**
	 * �����Ʒ�������Ե���
	 * @return
	 */
	public Map<Integer, Double> getLdxAdjustValue() {
		Map<Integer, Double> ldxAdjustMap = new HashMap<Integer, Double>();
		String hsql = "from Ftp1PrdtLdxAdjust order by termType";
		List<Ftp1PrdtLdxAdjust> list = daoFactory.query(hsql, null);
		for(Ftp1PrdtLdxAdjust ftp1PrdtLdxAdjust : list) {
			ldxAdjustMap.put(ftp1PrdtLdxAdjust.getTermType(), ftp1PrdtLdxAdjust.getAdjustValue());
		}
		return ldxAdjustMap;
	}
	
	/**
	 * �����Ʒ�����÷��յ���
	 * @return
	 */
	public Map<String, Double> getIrAdjustValue() {
		Map<String, Double> irAdjustMap = new HashMap<String, Double>();
		String hsql = "from Ftp1PrdtIrAdjust order by custCreditLvl";
		List<Ftp1PrdtIrAdjust> list = daoFactory.query(hsql, null);
		for(Ftp1PrdtIrAdjust ftp1PrdtIrAdjust : list) {
			irAdjustMap.put(ftp1PrdtIrAdjust.getCustCreditLvl(), ftp1PrdtIrAdjust.getAdjustValue());
		}
		return irAdjustMap;
	}
	/**
	 * ��ȡ�ͻ������õȼ���MAP<KEY(CUST_NO),�ȼ�>
	 * @return
	 */
	public Map<String, Double> getCust() {
		Map<String, Double> irAdjustMap = new HashMap<String, Double>();
		String hsql = "from Ftp1PrdtIrAdjust order by custCreditLvl";
		List<Ftp1PrdtIrAdjust> list = daoFactory.query(hsql, null);
		for(Ftp1PrdtIrAdjust ftp1PrdtIrAdjust : list) {
			irAdjustMap.put(ftp1PrdtIrAdjust.getCustCreditLvl(), ftp1PrdtIrAdjust.getAdjustValue());
		}
		return irAdjustMap;
	}
	/**
	 * ������Ʒ����ǰ����/��ǰ֧ȡ����
	 * @return
	 */
	public List<Ftp1PrepayAdjust> getPrepayAdjustValue() {
		String hsql = "from Ftp1PrepayAdjust order by assetsType,maxTermType desc";
		List<Ftp1PrepayAdjust> list = daoFactory.query(hsql, null);
		return list;
	}
	
	/**
	 * ������Ʒ�Ĳ��Ե���
	 * @return
	 */
	public Map<String, Double> getClAdjustValue() {
		Map<String, Double> clAdjustMap = new HashMap<String, Double>();
		String hsql = "from Ftp1PrdtClAdjust order by productNo";
		List<Ftp1PrdtClAdjust> list = daoFactory.query(hsql, null);
		for(Ftp1PrdtClAdjust ftp1PrdtClAdjust : list) {
			clAdjustMap.put(ftp1PrdtClAdjust.getProductNo(), ftp1PrdtClAdjust.getAdjustValue());
		}
		return clAdjustMap;
	}

	/**
	 * ������Ʒ�Ĳ��Ե���
	 * @return
	 */
	public Map<String, Double> getClAdjustValueByxls(String xlsBrno,String manageLvl) {
		Map<String, Double> clAdjustMap = new HashMap<String, Double>();
		xlsBrno = FtpUtil.getXlsBrNo_sylqx(xlsBrno, manageLvl);//��ȡ��Ӧ��������
		String hsql = "from Ftp1PrdtClAdjust where brNo='"+xlsBrno+"' order by productNo";
		List<Ftp1PrdtClAdjust> list = daoFactory.query(hsql, null);
		for(Ftp1PrdtClAdjust ftp1PrdtClAdjust : list) {
			clAdjustMap.put(ftp1PrdtClAdjust.getProductNo(), ftp1PrdtClAdjust.getAdjustValue());
		}
		return clAdjustMap;
	}

	/**
	 * �����Ʒ�Ĵ����������-���ñ�Ļ�ȡ:����������� ��  ���ϸ�������
	 * @return ��ά���飬0��С��1����2��С�ϸ�������3����ϸ�������4��������
	 */
	public double[][] getDkAdjustValue() {
		double[][] dkAdjust = null;
		String sql = "select min_amt, max_amt,min_percent,max_percent,adjust_value from ftp.Ftp1_dk_Adjust";
		List list = daoFactory.query1(sql, null);
		if(list != null && list.size() > 0) {
			dkAdjust = new double[list.size()][5];
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				dkAdjust[i][0] = obj[0]==null?0:Double.valueOf(obj[0].toString());
				dkAdjust[i][1] = obj[1]==null?0:Double.valueOf(obj[1].toString());
				dkAdjust[i][2] = obj[2]==null?0:Double.valueOf(obj[2].toString());
				dkAdjust[i][3] = obj[3]==null?0:Double.valueOf(obj[3].toString());
				dkAdjust[i][4] = obj[4]==null?0:Double.valueOf(obj[4].toString());
			}
		}
		return dkAdjust;
	}
	
	/**
	 * (XXXX-��ʱû��)�����Ʒ�Ĵ����ϸ�������������-���ñ�Ļ�ȡ
	 * @return ��ά���飬0��С����(�ٷ���) 1������(��) 2��������(����С��ֵ)
	 */
	/*public double[][] getDkSfblAdjustValue() {
		double[][] dkSfblAdjust = null;
		String sql = "select min_percent, max_percent, adjust_value from ftp.Ftp1_dk_sfbl_Adjust";
		List list = daoFactory.query1(sql, null);
		if(list != null && list.size() > 0) {
			dkSfblAdjust = new double[list.size()][3];
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				dkSfblAdjust[i][0] = obj[0]==null?0:Double.valueOf(obj[0].toString());
				dkSfblAdjust[i][1] = obj[0]==null?0:Double.valueOf(obj[1].toString());
				dkSfblAdjust[i][2] = obj[0]==null?0:Double.valueOf(obj[2].toString());
			}
		}
		return dkSfblAdjust;
	}*/
	
	/**
	 * ��ȡ�ͻ���������
	 * @return
	 */
	public String getCustCreditLvl() {
		//ȥ�ͻ�����������ر��л�ȡ��Ŀǰδ֪
		String lvl = "δ����";
		return lvl;
	}
}
