package com.dhcc.ftp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dhcc.ftp.bo.ReportBbBO;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.FtpEmpInfo;


/**
 * <p>
 * Title: LrmUtil
 * </p>
 * 
 * <p>
 * Description: 
 * </p>
 * 
 * <p>
 * Company: ��������ɷݹ�˾������ҵ��
 * </p>
 * 
 * @author ����ʢ
 * 
 * @date Jun 16, 2011 1:28:06 PM
 * 
 * @version 1.0
 */
public class LrmUtil {
	
	/**
	 * @author ������
	 * ͨ�����ֱ����ѯ��������
	 */
	public static String getAlmCur(String curno) {	
		String result="";
		/*String hsql =" from AlmCur1 where curno ="+curno;
		DaoFactory df = new DaoFactory();
		List<AlmCur1> list =(List<AlmCur1>) df.query(hsql, null);
		
		if(list!=null && list.size()>0){			
			for(AlmCur1 entity:list){
				result =entity.getCurname();
			}
		}*/
		if(curno.equals("01")){
			result="�����";
		}else if(curno.equals("02")){
			result="����������";
		}else if(curno.equals("00")){
			result="����Һϼ�";
		}else{
			result="����";
		}
		return result;
	}

	/**
	 * @author ������
	 * ͨ�����������ѯ��������
	 */
	public static String getBrName(String brNo) {
		String hsql="select br_name from ftp.br_mst where br_no='"+brNo+"'";
		DaoFactory df = new DaoFactory();
		List list = df.query1(hsql, null);
		String result="";
		if(list!=null && list.size()>0){
			result =list.get(0).toString();
		}
		return result;
	}
	
	/**
	 * �������л�����ţ�������Ӧ������sql���(ֻ������ײ����)
	 * @param br_no
	 * @return
	 */
	public static String getBrSql(String br_no) {
		DaoFactory df = new DaoFactory();
		String[] br_info=getBr_lvlInfo(br_no);
		String br_level=br_info[0];
		String is_business=br_info[1];
		String brSql = "";
		System.out.println("br_level"+br_level);
		if(is_business.equals("1")){
			brSql="='"+br_no+"'";
		}else{
			if("3".equals(br_level)){
				brSql="is not null";//likeЧ��̫������������ ������ÿ����Ŀȡ�������崦��:��like�죬�Ͳ����� �� is not null��ࡣ
			}else if("2".equals(br_level)){
				brSql+="'"+br_no+"',";
				String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
				List br_noList0=df.query1(sql, null);
				for(Object obj0:br_noList0){
					brSql+="'"+obj0.toString()+"',";
					String sql1="select br_no from ftp.br_mst where super_br_no='"+obj0+"'";
					List br_noList01=df.query1(sql1, null);
					for(Object obj01:br_noList01){
						brSql+="'"+obj01.toString()+"',";
					}
				}
				brSql=brSql.substring(0,brSql.length()-1);
				brSql="in ("+brSql+")";
			}else if("1".equals(br_level)){
				String br_nos="";
				String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
				List br_noList0=df.query1(sql, null);
				if(br_noList0==null || br_noList0.size()==0){
					br_nos+="'"+br_no+"',";
				}else{
					for(Object obj0:br_noList0){
						br_nos+="'"+obj0.toString()+"',";
					}
				}
				br_nos=br_nos.substring(0,br_nos.length()-1);
				brSql="in ("+br_nos+")";
			}
			else{
				brSql="='"+br_no+"'";
			}
		}
		
		return brSql;
	}
	
	/**
	 * ���ݻ�����ţ�������Ӧ������sql���-��ȡ�ò���Ա��Ͻ�Ļ�������ײ㣬1��0����
	 * @param br_no
	 * @param tel_no
	 * @return
	 */
	public static String getBrSqlByTel(String br_no, String tel_no) {
		DaoFactory df = new DaoFactory();
		String[] brInfos = getBr_lvlInfo(br_no);
		String brSql = "";
		String br_nos="";
		if("3".equals(brInfos[0])){
			ReportBbBO reportBbBO = new ReportBbBO();
			List<BrMst> brMstList = reportBbBO.brMstMap.get(tel_no);//��ȡ�ò���Ա��Ͻ�Ļ���
			if(brMstList == null || brMstList.size() == 0) {
				brSql="is not null";
			}else {//�м��³��������в�����
				for (BrMst brMst : brMstList) {
					br_nos+="'"+brMst.getBrNo()+"',";
					String sql="select br_no from ftp.br_mst where super_br_no='"+brMst.getBrNo()+"'";
					List br_noList=df.query1(sql, null);
					for(Object obj1:br_noList){
						br_nos+="'"+obj1.toString()+"',";
					}
				}
				br_nos=br_nos.substring(0,br_nos.length()-1);
				brSql="in ("+br_nos+")";
			}
		}else if("2".equals(brInfos[0])){
			br_nos+="'"+br_no+"',";
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList0=df.query1(sql, null);
			for(Object obj0:br_noList0){
				br_nos+="'"+obj0.toString()+"',";
				 String sql1="select br_no from ftp.br_mst where super_br_no='"+obj0+"'";
				 List br_noList01=df.query1(sql1, null);
				 for(Object obj01:br_noList01){
					 br_nos+="'"+obj01.toString()+"',";
				 }
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
			brSql="in ("+br_nos+")";
		}else if("1".equals(brInfos[0])){
			br_nos+="'"+br_no+"',";
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList0=df.query1(sql, null);
			for(Object obj0:br_noList0){
				br_nos+="'"+obj0.toString()+"',";
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
			brSql="in ("+br_nos+")";
		}
		else{
			brSql="='"+br_no+"'";
		}
		
		return brSql;
	}
	
	/**
	 * ���ݻ�����ţ�������Ӧ������sql���-���е��¼�������ֻ��ȡ1��
	 * @param br_no
	 * @return
	 */
	public static String getBrLvl1Sql(String br_no, String tel_no) {
		DaoFactory df = new DaoFactory();
		String[] brInfos = getBr_lvlInfo(br_no);
		String brSql = "";
		String br_nos="";
		if("2".equals(brInfos[0])){
			ReportBbBO reportBbBO = new ReportBbBO();
			List<BrMst> brMstList = reportBbBO.brMstMap.get(tel_no);//��ȡ�ò���Ա��Ͻ�Ļ���
			if(brMstList == null || brMstList.size() == 0) {
				String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
				List br_noList=df.query1(sql, null);
				for(Object obj1:br_noList){
					br_nos+="'"+obj1.toString()+"',";
				}
			}else {//�м��³��������в�����
				for (BrMst brMst : brMstList) {
					br_nos+="'"+brMst.getBrNo()+"',";
				}
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
			brSql="in ("+br_nos+")";
		}else{
			brSql="='"+br_no+"'";
		}
		
		return brSql;
	}
	/**
	 * ��ȡ�����Ĺ�����+�Ƿ�ҵ������
	 * @param br_no
	 * @return String[]{br_level,is_business}
	 */
	public static String[] getBr_lvlInfo(String br_no){
		String br_level="";
		String is_business="0";
		String sql0="select manage_lvl,is_business from ftp.br_mst where br_no='"+br_no+"'";
		List list=CommonFunctions.mydao.query1(sql0, null);
		Object obj0=list.get(0);
		Object[] obj0s=(Object[])obj0;
		br_level=obj0s[0].toString();
		is_business=obj0s[1].toString();
		return new String[]{br_level,is_business};
	}


	/**
	 * ��ȡ�ϼ�����
	 * @param br_no
	 * @return String
	 */
	public static String getSuperBrNo(String br_no){
		String br_level="";
		String is_business="0";
		String sql0=" from BrMst where brNo='"+br_no+"'";
		List<BrMst> list=CommonFunctions.mydao.query(sql0, null);
		return ((BrMst)list.get(0)).getSuperBrNo();
	}


	/**
	 * ��ȡȫ���¼��ṹ
	 * @param br_no
	 * @return String[]
	 */
	public static String getSubBrNo(String br_no){

		String brNos = "'"+br_no+"',";
		String sql0=" from BrMst where superBrNo='"+br_no+"'";
		List<BrMst> list=CommonFunctions.mydao.query(sql0, null);
		for(BrMst brMst:list){
			 String brNo = brMst.getBrNo();
			 brNos+="'"+brNo+"',";
		}

		return brNos;
	}

	/**
	 * ��ȡ  ����Ա�������Ļ��� �Ƿ�Ϊ  �������������ڵ�1��֧�С��������򷵻ش�Ա����ţ����򷵻�null
	 * @param br_no
	 * @param empNO
	 * @return String ������  �������������ڵ�1��֧�С���Ա���򷵻ش�Ա����ţ����򷵻�null
	 */
	public static String checkEmpno(String br_no, String empNO) {

	    String super_br_no = getSuperBrNo(br_no);
        String brNos = getSubBrNo(super_br_no);
		String hsql = "select EMP_NO,BR_NO from ftp.Ftp_Emp_Info where br_No in (" + brNos.substring(0,brNos.length()-1) + ")";
		Map<String, String> empMap = new HashMap<String, String>();
		List list = CommonFunctions.mydao.query1(hsql, null);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] objects = (Object[])list.get(i);
				empMap.put(objects[0].toString(), objects[1].toString());
			}

			if (empMap.containsKey(empNO)) {
				return null;
			} else {
				return empNO;
			}
		} else {
			return	empNO ;
		}
	}
	//��������main����
	public static void main(String[] args) {
//		System.out.println(getAccSum("10100","01002","01","20110825","5"));
		//System.out.println(getItemSum("1101001","320223800","01","20111225","2"));
		//System.out.println(getBrLevel("320223800"));
		//Object[] itemSum_final =new  Object[2] ;
		//String[] chg_item_nos = {"1101001"};
		//Map<String,Double> chg_amt_map = new HashMap();
		//chg_amt_map.put("1101001", 100000.0);
		//itemSum_final[1]= getItemSum_final("01",chg_item_nos,chg_amt_map,"320223000","01","20111201");
		
		//itemSum_final[i]=getItemSum_final(chg_item_nos[0].substring(2,4),chg_item_nos,chg_amt_map,"320223000","01","20111201");
		
		//�����sql��䱨��
		/*ReportBo.java 740�� sql������ ��- SQL Error: 17410, SQLState: null
		- �޷����׽��ֶ�ȡ��������ݡ�------ֻ�Ǳ������ݿ������ֻ������1801040009������ɳ�����粻���������Ͽ������ݿⲻ����� */
		String sql="select * from (select t.Result_price, t.pool_no, f.pool_type, f.Content_Object, f.prc_mode, row_number() over(partition by t.pool_no order by t.RES_DATE desc,T.RESULT_ID desc ) rn from ftp.Ftp_Result t left join ftp.ftp_pool_info f on t.prc_mode = f.prc_mode and t.br_no = f.br_no and t.pool_no = f.pool_no where t.prc_Mode ='3' and t.cur_no = '01' and t.br_no = '1801040009' and to_date(t.res_date,'yyyyMMdd') <= to_date('20121231','yyyyMMdd')  and t.pool_no in (select pool_no from ftp.ftp_pool_info where prc_mode='3' and br_no=t.br_no)) where rn=1 order by pool_type, pool_no";
		List list = CommonFunctions.mydao.query1(sql, null);
		System.out.println(list.size());


	}
}
