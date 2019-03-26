package com.dhcc.ftp.bo;
/**
 * @desc:期限匹配
 * @author :孙红玉
 * @date ：2012-04-17
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
	 * 分页查询
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
	 * 根据list进行分页
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
	 * 贷款产品的存款准备金调整
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
	 * 贷款产品的流动性调整
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
	 * 贷款产品的信用风险调整
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
	 * 获取客户的信用等级，MAP<KEY(CUST_NO),等级>
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
	 * 存贷款产品的提前还款/提前支取调整
	 * @return
	 */
	public List<Ftp1PrepayAdjust> getPrepayAdjustValue() {
		String hsql = "from Ftp1PrepayAdjust order by assetsType,maxTermType desc";
		List<Ftp1PrepayAdjust> list = daoFactory.query(hsql, null);
		return list;
	}
	
	/**
	 * 存贷款产品的策略调整
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
	 * 存贷款产品的策略调整
	 * @return
	 */
	public Map<String, Double> getClAdjustValueByxls(String xlsBrno,String manageLvl) {
		Map<String, Double> clAdjustMap = new HashMap<String, Double>();
		xlsBrno = FtpUtil.getXlsBrNo_sylqx(xlsBrno, manageLvl);//获取对应的县联社
		String hsql = "from Ftp1PrdtClAdjust where brNo='"+xlsBrno+"' order by productNo";
		List<Ftp1PrdtClAdjust> list = daoFactory.query(hsql, null);
		for(Ftp1PrdtClAdjust ftp1PrdtClAdjust : list) {
			clAdjustMap.put(ftp1PrdtClAdjust.getProductNo(), ftp1PrdtClAdjust.getAdjustValue());
		}
		return clAdjustMap;
	}

	/**
	 * 贷款产品的贷款调整比率-配置表的获取:包括‘贷款金额’ 与  ‘上浮比例’
	 * @return 二维数组，0最小金额、1最大金额、2最小上浮比例、3最大上浮比例、4调整比率
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
	 * (XXXX-暂时没用)贷款产品的贷款上浮比例调整比率-配置表的获取
	 * @return 二维数组，0最小比例(百分数) 1最大比例(含) 2调整比率(绝对小数值)
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
	 * 获取客户信用评级
	 * @return
	 */
	public String getCustCreditLvl() {
		//去客户信用评级相关表中获取，目前未知
		String lvl = "未评级";
		return lvl;
	}
}
