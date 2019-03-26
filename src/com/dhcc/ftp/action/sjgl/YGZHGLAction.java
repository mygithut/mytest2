package com.dhcc.ftp.action.sjgl;
/**
 * Ա���˻�����ά��
 * @author sss
 */
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.action.BoBuilder;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.*;
import com.dhcc.ftp.util.*;

public class YGZHGLAction  extends BoBuilder {

	private int pageSize = 100;
	private int rowsCount = -1;
	private String brNo;
	private String businessNo;
	private String prdtNo;
	private String wrkTime1;
	private String wrkTime2;
	private String isRelated;
	private String empId;
	private String empNo;
	private String empName;
	private String accId;
	private String ac_id;
	private String rate;
	private String ac_ids;
	private String empno1;//Ա��ҳ��ѯר��
    private String relType;
    private String value;
    private String cusName;
	private FtpEmpInfo ftpEmpInfo;
	private String relatedEmpNos;
	private PageUtil frpEmpInfoUtil = null;
	private FtpEmpInfo po ;
	private int page = 1;
	private PageUtil YGZHGLUtil = null;
	
	HttpServletRequest request = getRequest();
	DaoFactory df = new DaoFactory();

	public String execute() throws Exception {
		return super.execute();
	}
	/**
	 * ��ѯ�б�
	 * @return
	 * @throws Exception
	 */
	public String List() throws Exception {
		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
	    String mmCard ="";
		if(accId.startsWith("62")){
			mmCard = accId;
		}
		
		String hsql = "select a.* "+
		" from (select distinct trim(t5.CRDNO) as MMCARD , t.br_no,t4.charge_person_name," +
		" t.cus_name," +
		" t.prdt_name," +
		" t.opn_date," +
		" t.amt," +
		" t.bal," +
		" t.rate," +
		" t.term," +
		" t.mtr_date," +
		" case when t.AC_ID = t1.ACC_ID then '1' else '0' end isRelated, " +
		" t1.emp_no, "+
		" t1.rate relrate, " +
		" t.BUSINESS_NO, " +
		" t.PRDT_NO, " +
		" t1.rel_type, " +
		" t.ac_id "+
		" from ftp.fzh_history  t " +
		"LEFT JOIN  ftp.FTP_EMP_ACC_REL t1  on t.AC_ID = t1. ACC_ID " +
		"left join  ftp.FTP_EMP_INFO t2 on locate(t2.emp_no,t1.emp_no)>0 "+
        "left join  ftp.br_mst t4 on t.br_no = t4.br_no "+
        "left join bips.NSOP_MIR_CDFM23 t5 on t.SOP_CODE = t5.ACCNO ";//�ų���������
		hsql+="where t.business_no not in('YW103','YW104','YW105','YW106','YW204','YW205','YW206','YW208') ";//��ȥ�����г���صģ������г�����̨�˲�¼���Ѿ���¼��
		
		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = reportBbBO.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		if(custType.equals("2")) {//ͳ�ƶԹ��ͻ�
			hsql +=" and (( substr(kmh,1,4) in ('2001','2002','2006','2011','2014','1306'))"+
					" or ( substr(kmh,1,4) in ('1301','1302','1303','1304','1305','1307','1308') and substr(cust_no,1,1)='2'))";
		}else if(custType.equals("1")) {//ͳ�ƶ�˽�ͻ�
			hsql +=" and ((substr(kmh,1,4) in ('2003','2004','2005'))"+
					" or ( substr(kmh,1,4) in ('1301','1302','1303','1304','1305','1307','1308') and substr(cust_no,1,1)='1'))";
		}
		if (brNo != null && !brNo.equals("") && !brNo.equals("null")) {//������ƣ�brNo���벻Ϊ�գ�������ѡ�񣬷���ִ�в�ѯ
			hsql +=" and t.br_no "+LrmUtil.getBrSql(brNo);//getBrAllChildSql(brNo);
		}
		if (businessNo != null && !businessNo.equals("")&& !businessNo.equals("null")) {
			hsql +=" and t.business_no = '"+businessNo+"'";
		}
		if (prdtNo != null && !prdtNo.equals("")&& !prdtNo.equals("null")) {
			hsql +=" and t.prdt_no = '"+prdtNo+"'";
		}
		//�������ݿ�ϵͳ����
		if (wrkTime1 != null && !wrkTime1.equals("")&& !wrkTime1.equals("null")) {
			hsql +=" and to_date(t.opn_date,'yyyymmdd') >= to_date('"+wrkTime1+"','yyyymmdd')";
		}
		if (wrkTime2 != null && !wrkTime2.equals("")&& !wrkTime2.equals("null")) {
			hsql +=" and to_date(t.opn_date,'yyyymmdd') <= to_date('"+wrkTime2+"','yyyymmdd')";
		}
		
		if(mmCard!=null&&!mmCard.equals("")&&!mmCard.equals("null")){
			hsql +=" and t5.CRDNO = '"+mmCard.trim()+"'";
		}else{
		    if (accId != null && !accId.equals("")&& !accId.equals("null")) {
			   hsql +=" and t.ac_id like '%"+accId.trim()+"%'";
		    }
		}
		if(isRelated!= null && isRelated.equals("1")){
			hsql +=" and t1.acc_id is not null ";
		}
		if(isRelated!= null && isRelated.equals("2")){
			hsql +=" and t1.acc_id is null ";
		}
		if(cusName!=null && !cusName.equals("")&&!cusName.equals("null")){
			cusName = CommonFunctions.Chinese_CodeChange(cusName);
			hsql +=" and t.cus_name like '%"+cusName.trim()+"%'";
		}
		if (empName != null && !empName.equals("")&& !empName.equals("null")) {
			empName = CommonFunctions.Chinese_CodeChange(empName);
			hsql +=" and t2.emp_name like '%"+empName.trim()+"%' ";
		}
		hsql += " ) a order by a.isRelated desc, a.br_no desc, a.business_no, a.prdt_no, a.MMCARD ";
		String pageName = "YGZHGL_List.action?brNo="+brNo+"&businessNo="+businessNo+"&prdtNo="+prdtNo+"&wrkTime1="+wrkTime1+"&wrkTime2="+wrkTime2+"&isRelated="+isRelated+"&empNo="+empNo+"&accId="+accId;
		YGZHGLUtil = queryPageBO.sqlQueryPage(hsql, pageSize, page, rowsCount, pageName);
		Map map = getKHJLMap();//��ȡac_id��Ӧ�Ŀͻ������ַ���
		request.setAttribute("map", map);
		Map brMap = getBrMap();//��ȡ����Map(brNo,brName)
		request.setAttribute("brMap", brMap);
		return "List";
	}
	
	/**
	 * ��ȡ�ͻ�����map(empno,empname)
	 */
	public Map getKHJLMap(){
		String sql = "select t1.emp_no,t1.emp_name from ftp.ftp_emp_info t1 ";
		sql+=" where t1.emp_status = '1' ";
        List list = df.query1(sql, null);
        Map map = new HashMap();
       for (int i = 0; i < list.size();i++ ) {
          Object[] obj = (Object[])list.get(i);
          map.put(obj[0], obj[1]);
       }
       return map;
	}
	
	/*
	 * ��ȡ����Map(brNo,brName)
	 */
	public Map getBrMap(){
		String sql = "select t1.br_no,t1.charge_person_name from ftp.br_mst t1 ";
        List list = df.query1(sql, null);
        Map brmap = new HashMap();
       for (int i = 0; i < list.size();i++ ) {
          Object[] obj = (Object[])list.get(i);
          brmap.put(obj[0], obj[1]);
       }
       return brmap;
	}
	
	/**
	 * ��ȡ��������Ա����Ϣ
	 * @return
	 */
	public String getRelatedEmp() {
		String[][] relatedEmps = null;
		if(null==value||relType.equals(value)){
		if(empNo != null && !empNo.equals("")) {
			String[] empNos = empNo.split("@");
			String[] rates = rate.split("@");
			relatedEmps = new String[empNos.length][5];
			for (int i = 0; i < empNos.length; i++) {
				String sql = "select  t.emp_no,t.emp_name, t.br_no,t2.br_name from ftp.ftp_emp_info t " +
				" left join ftp.br_mst t2 on t.br_no = t2.br_no "+
				" where t.emp_status = '1' "+
				"and t.emp_no = '"+empNos[i]+"'";
				List list = df.query1(sql, null);
				Object[] obj = (Object[])list.get(0);
				relatedEmps[i][0] = obj[0].toString();
				relatedEmps[i][1] = obj[1].toString();
				relatedEmps[i][2] = obj[2].toString();
				//����Ϊ�յ�ת��
			    if(null==obj[3]){
				    relatedEmps[i][3] = "";
			    }else{
				    relatedEmps[i][3] = obj[3].toString();
			    }
				relatedEmps[i][4] = rates[i];
			}
			request.setAttribute("pcount", empNos.length);
		}
		}
		request.setAttribute("relatedEmps", relatedEmps);
		if(null!=value&&!relType.equals(value)){
		   request.setAttribute("relType", value);
		}else{
		   request.setAttribute("relType", relType);
		}
		request.setAttribute("prdtNo", prdtNo);
//		if(relType.equals("1")){
//		   return "relatedEmp";
//		}else{
//		   return "relatedEmp1";
//		}
		if(null!=value&&!relType.equals(value)&&value.equals("1")){
			return "relatedEmp";
		}else if(null!=value&&!relType.equals(value)&&value.equals("2")){
			return "relatedEmp1";
		}else if(null==value&&relType!=null&&relType.equals("1")){
			return "relatedEmp";
		}else if(null==value&&relType!=null&&relType.equals("2")){
			return "relatedEmp1";
		}else if(null!=value&&relType.equals(value)&&relType.equals("1")){
			return "relatedEmp";
		}else if(null!=value&&relType.equals(value)&&relType.equals("2")){
			return "relatedEmp1";
		}else{
			return null;
		}
		
	}
	
	/**
	 * ��ȡԱ���б�
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String getEmps() throws UnsupportedEncodingException {
		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
		String hql = "from FtpEmpInfo where empStatus = '1' ";
		String pageName = "YGZHGL_getEmps.action?brNo="+brNo;
		if(relatedEmpNos != null && !relatedEmpNos.equals("") && !relatedEmpNos.equals("null")) {
			pageName+="&relatedEmpNos="+relatedEmpNos;
			relatedEmpNos = relatedEmpNos.substring(0,relatedEmpNos.length()-1);
			relatedEmpNos = relatedEmpNos.replaceAll(",", "','");
			hql+=" and empNo not in ('"+relatedEmpNos+"') ";
		}
		if(empNo!=null&&!"".equals(empNo)&&!empNo.equals("null")){
			pageName+="&empNo="+empNo;
			hql+=" and empNo like  '%"+empNo+"%'  ";
		}
		if(empName!=null&&!"".equals(empName)&&!"null".equals(empName)){
			String empname = new String(empName.getBytes("iso-8859-1"),"UTF-8");
			pageName+="&empName="+empname;
			hql+=" and empName like '%"+empname+"%' ";
		}
		if(brNo!=null&&!"".equals(brNo)){
			hql+=" and brMst.brNo  "+LrmUtil.getBrSqlByTel(brNo, telMst.getTelNo());
		}
		hql += " order by brMst.brNo desc, empNo";
		PageUtil empInfoUtil = queryPageBO.queryPage(hql, 10, page, rowsCount, pageName);
		request.setAttribute("empInfoUtil", empInfoUtil);
		return "empList";
	}
	/**
	 * ��ѯԱ���б�����ʾ����
	 * @return
	 * @throws Exception
	 */
	public String Query() throws Exception {
//		String hql = "from FtpEmpInfo h  where 1=1 ";//substr(shiborTerm,2,1),substr(shiborTerm,1,1)";
		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
		String empNos[] = null;
		String rates[] = null; 
		String hsql = "";
		if(empNo!=null&&empNo.indexOf("@")!=-1&&!empNo.equals("")&&!empNo.equals("null")){
			empNos=empNo.split("@");
			empNo="";
			for(int i=0;i<empNos.length;i++){
				empNo+="\'"+empNos[i]+"',";
			}
		}
		if(empNo!=null&&empNo.indexOf(",")!=-1&&empNo.lastIndexOf(",")==empNo.length()-1){
			empNo=empNo.substring(0, empNo.length()-1);
		}
		if(rate!=null&&rate.indexOf("@")!=-1&&!rate.equals("")&&!rate.equals("null")){
			rates = rate.split("@");
		}
		
		if(empNo==null||empNo.equals("")){
			hsql =" select * from (select  t.emp_no,t.emp_name," +
					" t.br_no,t2.br_name ,'0' type1 " +
					" from ftp.ftp_emp_info t " +
					" left join ftp.br_mst t2 on t.br_no = t2.br_no) m where 1=1 ";
		}else{
		
		  hsql ="select * from ((select t.emp_no,t.emp_name,t.br_no,t2.br_name ," +
				" '1' type1 from ftp.ftp_emp_info t "+
		        " left join ftp.br_mst t2 on t.br_no = t2.br_no " +
			    " where t.emp_no in ("+empNo+") "+
			    " order by t.emp_no ) ";
		    	hsql+=" union all" +
			    "( select t.emp_no,t.emp_name,t.br_no,t2.br_name ,"+
		        "'0' type1 " +
		        "from ftp.ftp_emp_info t " +
		        "left join ftp.br_mst t2 on t.br_no = t2.br_no " +
		        "where  t.emp_no not in ( " +empNo+" )"+
		        " order by t.emp_no ) ) m  where 1=1 ";
		}
		if(empno1!=null&&!"".equals(empno1)&&empno1.indexOf(",")==-1&&!empno1.equals("1")){
			hsql+=" and  m.emp_no like  '%"+empno1+"%'  ";
		}
		if(empName!=null&&!"".equals(empName)){
			String tempName = new String(empName.getBytes("iso-8859-1"),"UTF-8");
			hsql+=" and m.emp_name like '%"+tempName+"%'   ";
		}
		if(brNo!=null&&!"".equals(brNo)){
			hsql+=" and  m.br_no  "+LrmUtil.getBrSqlByTel(brNo, telMst.getTelNo());
		}
		hsql+=" order by m.type1 desc,m.br_no desc,m.emp_no ";
		List list = df.query1(hsql, null);
		Map map = new HashMap();
		if(rate!=null&&rate.indexOf("@")!=-1){
		for(int i=0;i<rates.length;i++){
			if(i<list.size()){
			  Object[] obj = (Object[])list.get(i);
			  map.put(obj[0], rates[i]);
			}
		  }
		}
		request.setAttribute("ac_id", ac_id);
		request.setAttribute("list", list);
		if(map!=null&&map.size()!=0){
		  request.setAttribute("map", map);
		}
		  
		return "empList";
	} 
	/**
	  * ����
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		String acids[] = null;
		String prdtNos[] = null;

		if(FtpUtil.isDone){
			//ɾ������������ǰ�������ص���
			System.out.println("ac_id"+ac_id);
			if(ac_id!=null&&!ac_id.equals("null")&&!"".equals(ac_id)){
				acids = ac_id.split(",");
			}
			TelMst telmst = (TelMst) request.getSession().getAttribute("userBean");
			if(empNo.lastIndexOf("@")==empNo.length()-1){
				empNo=empNo.substring(0, empNo.length()-1);
			}
			if(rate.lastIndexOf("@")==rate.length()-1){
				rate=rate.substring(0, rate.length()-1);
			}
			if(prdtNo.lastIndexOf(",")==prdtNo.length()-1){
				prdtNos = prdtNo.split(",");
			}

			//ͬʱ���¹������ͳ�Ʊ��������
/*		String sql="select max(cyc_date) from ftp.TJBB_RESULT";
		List dateList =df.query1(sql,null);
		final String cyc_date= (String) dateList.get(0);*/

			for(int i=0;i<acids.length;i++ ){
				FtpEmpAccRel po =new FtpEmpAccRel();
				po.setId(IDUtil.getInstanse().getUID());
				po.setAccId(acids[i]);
				po.setEmpNo(empNo);
				po.setRate(rate);
				po.setRelTime(DateUtil.getCurrentDay()+DateUtil.getCurrentTime());
				po.setRelTelNo(telmst.getTelNo());
				po.setRelType(relType);
				if(prdtNo.indexOf(",")!=-1&&prdtNos.length!=0){
					po.setPrdtNo(prdtNos[i]);
				}else{
					po.setPrdtNo(prdtNo);
				}

		/*	List<FtpTjbbResult> ftpTjbbResultList = new ArrayList<FtpTjbbResult>();
			//map,key<�˺�>,<ͳ�Ʊ���ʵ��>
			Map<String, FtpTjbbResultTemp> ftpTjbbResultMap = new HashMap<String, FtpTjbbResultTemp>();
			String sql1 = " delete from FtpTjbbResult where ftpTjbbResultComposite.cycDate='"+cyc_date+"' and ftpTjbbResultComposite.acId='"+acids[i]+"'";
			boolean f=df.execute(sql1,null);*/
				//if(f){
				//1.ȡ��ͳ�Ʊ�����ʱ��
			/*	sql1 = " select * from ftp.TJBB_RESULT_TEMP where cyc_date='"+cyc_date+"' and ac_id='"+acids[i]+"'";
				List dcfhqList = df.query1(sql1,null);
				if(dcfhqList.size() > 0) {
					for(Object object : dcfhqList) {
						Object[] obj = (Object[])object;
						FtpTjbbResultTemp ftpTjbbResult = new FtpTjbbResultTemp();
						ftpTjbbResult.setID(obj[0]==null?"":obj[0].toString());
						ftpTjbbResult.setCycDate(obj[1]==null?"":obj[1].toString());
						ftpTjbbResult.setAcId(obj[2]==null?"":obj[2].toString().trim());
						ftpTjbbResult.setBrNo(obj[3]==null?"":obj[3].toString());
						ftpTjbbResult.setPrdtNo(obj[4]==null?"":obj[4].toString());
						ftpTjbbResult.setRate(obj[5]==null?0.00:Double.valueOf(obj[5].toString()));
						ftpTjbbResult.setFtpPrice(obj[6]==null?0.00:Double.valueOf(obj[6].toString()));
						ftpTjbbResult.setAverateBalM(obj[7]==null?0.00:Double.valueOf(obj[7].toString()));
						ftpTjbbResult.setAverateBalQ(obj[8]==null?0.00:Double.valueOf(obj[8].toString()));
						ftpTjbbResult.setAverateBalY(obj[9]==null?0.00:Double.valueOf(obj[9].toString()));
						ftpTjbbResult.setBal(obj[10]==null?0.00:Double.valueOf(obj[10].toString()));
						ftpTjbbResult.setBusinessNo(obj[11]==null?"":obj[11].toString());
						ftpTjbbResult.setKmh(obj[12]==null?"":obj[12].toString());
						ftpTjbbResult.setCustNo(obj[14]==null?"":obj[14].toString());
						ftpTjbbResult.setIsZq(obj[13]==null?"":obj[13].toString());
						ftpTjbbResultMap.put(obj[2].toString().trim(), ftpTjbbResult);
					}
				}


				String accId = String.valueOf(acids[i]).trim();
				FtpTjbbResultTemp ftpTjbbResultTemp = ftpTjbbResultMap.get(accId.trim());//�վ����
				if(ftpTjbbResultTemp==null){
					continue;
				}
				Double	rjye=ftpTjbbResultTemp.getAverateBalM();
				String[] empNos = String.valueOf(empNo).split("@");
				String[] rates = String.valueOf(rate).split("@");
				//������䷽ʽΪ�̶����ķ������
				double[] ratios = new double[rates.length];//�ָ����
				for(int g=0;g<rates.length;g++){
					ratios[g]=Double.valueOf(rates[g]);
				}

				if(String.valueOf(relType).equals("2")) {//���䷽ʽΪ"�̶����"ʱ
					double sumAmt = 0;//sum���䷽ʽΪ�̶����ʱ�ķָ����ܺ�
					for (int h = 0; h < ratios.length; i++) {
						sumAmt += ratios[h];
					}
					if(sumAmt < rjye) {//����ܺ�<�վ������һ���ͻ�������䵽���=�վ����-��������������
						ratios[0] = rjye - (sumAmt-ratios[0]);
						sumAmt = rjye;
					}
					//�������ܺ�>=�վ�������Ҫ������ֱ�Ӱ������ռ�������м��㣬
					for (int h = 0; h < ratios.length; h++) {
						ratios[h] = ratios[h]/sumAmt;//�������
					}
				}

				double r_whole=0;
				for(int j = 0; j < empNos.length; j++){
					r_whole+=ratios[j];
				}

				//�������б�
				for (int k = 0; k < empNos.length; k++) {
					FtpTjbbResultComposite ftpTjbbResultComposite = new FtpTjbbResultComposite();
					FtpTjbbResult ftpTjbbResult = new FtpTjbbResult();
					ftpTjbbResultComposite.setID(ftpTjbbResultTemp.getID());
					ftpTjbbResultComposite.setCycDate(ftpTjbbResultTemp.getCycDate());
					ftpTjbbResultComposite.setAcId(ftpTjbbResultTemp.getAcId());
					ftpTjbbResult.setBrNo(ftpTjbbResultTemp.getBrNo());
					ftpTjbbResultComposite.setCumNo(empNos[k]);
					ftpTjbbResult.setFtpTjbbResultComposite(ftpTjbbResultComposite);
					ftpTjbbResult.setPrdtNo(ftpTjbbResultTemp.getPrdtNo());
					ftpTjbbResult.setFtpPrice(ftpTjbbResultTemp.getFtpPrice());
					ftpTjbbResult.setRate(ftpTjbbResultTemp.getRate());
					ftpTjbbResult.setAverateBalM(ftpTjbbResultTemp.getAverateBalM()*ratios[k]);
					ftpTjbbResult.setAverateBalQ(ftpTjbbResultTemp.getAverateBalQ()*ratios[k]);
					ftpTjbbResult.setAverateBalY(ftpTjbbResultTemp.getAverateBalY()*ratios[k]);
					ftpTjbbResult.setBal(ftpTjbbResultTemp.getBal()*ratios[k]);
					ftpTjbbResult.setCustNo(ftpTjbbResultTemp.getCustNo());
					ftpTjbbResult.setBusinessNo(ftpTjbbResultTemp.getBusinessNo());
					ftpTjbbResult.setKmh(ftpTjbbResultTemp.getKmh());
					ftpTjbbResult.setIsZq(ftpTjbbResultTemp.getIsZq());
					ftpTjbbResultList.add(ftpTjbbResult);
				}*/
				df.update(po);
				//	}
			}
	/*	executorService.execute(new Runnable() {
			@Override
			public void run() {
				String sql_update = " MERGE INTO ftp.TJBB_RESULT_PRDT l"+
						" using ( SELECT br_no,prdt_no,cum_no, substr(cust_no,1,1) as cust_type,SUM(AVERATE_BAL_M) AS AVERATE_BAL_M,SUM(AVERATE_BAL_Q) AS AVERATE_BAL_Q,SUM(AVERATE_BAL_Y) AS AVERATE_BAL_Y,SUM(bal) as bal,"+
						"  MAX(BUSINESS_NO) as BUSINESS_NO,(case when substr(prdt_no,2,1)='1'then (case when sum(AVERATE_BAL_M)!=0 then sum((rate-FTP_PRICE)*AVERATE_BAL_M)/sum(AVERATE_BAL_M)"+
						"  else 0 end)  else (case when sum(AVERATE_BAL_M)!=0   then sum((FTP_PRICE-rate)*AVERATE_BAL_M)/sum(AVERATE_BAL_M) else 0 end)  end ) as ftp_Rate_M,"+
						"   (case when substr(prdt_no,2,1)='1' then (case when sum(AVERATE_BAL_Q)!=0 then sum((rate-FTP_PRICE)*AVERATE_BAL_Q)/sum(AVERATE_BAL_Q) else 0 end)  "+
						"  else  (case when sum(AVERATE_BAL_Q)!=0 then sum((FTP_PRICE-rate)*AVERATE_BAL_Q)/sum(AVERATE_BAL_Q) else 0 end)  end ) as ftp_Rate_Q,"+
						" (case when substr(prdt_no,2,1)='1' then (case when sum(AVERATE_BAL_Y)!=0  then sum((rate-FTP_PRICE)*AVERATE_BAL_Y)/sum(AVERATE_BAL_Y)"+
						"   else 0 end)   else (case when sum(AVERATE_BAL_Y)!=0  then sum((FTP_PRICE-rate)*AVERATE_BAL_Y)/sum(AVERATE_BAL_Y) else 0 end)   end ) as ftp_Rate_Y"+
						"  FROM ftp.TJBB_RESULT WHERE cyc_date='"+cyc_date+"' GROUP BY br_no,prdt_no,cum_no, substr(cust_no,1,1) ) lv"+
						" ON  l.br_no=lv.br_no and l.cyc_date='"+cyc_date+"' and l.prdt_no=lv.prdt_no and l.cum_no=lv.cum_no and l.cust_type=lv.cust_type"+
						" WHEN  MATCHED THEN update set l.CUM_NO=lv.CUM_NO,l.FTP_RATE_M=lv.FTP_RATE_M,l.FTP_RATE_Q=lv.FTP_RATE_Q,l.FTP_RATE_Y=lv.FTP_RATE_Y,l.AVERATE_BAL_M=lv.AVERATE_BAL_M,l.AVERATE_BAL_Q=lv.AVERATE_BAL_Q,l.AVERATE_BAL_Y=lv.AVERATE_BAL_Y,l.bal=lv.bal";
				df.execute1(sql_update);
			}
		});*/
		}

		if(FtpUtil.isDone){
			getResponse().getWriter().write("1");
		}else {
			getResponse().getWriter().write("0");
		}

		return null;
	}
	
	/**
	 * ɾ��
	 * @return
	 * @throws Exception
	 */
	public String del() throws Exception {
    	String[] id = ac_id.split(",");
    	int count=0;
		for(int i = 0; i < id.length; i++){
			String hsql="delete from FtpEmpAccRel where accId='"+id[i]+"'";
			System.out.println(hsql);
			df.delete(hsql, null);
			count++;
		}
		if(count==id.length){
			this.getResponse().getWriter().print("ok");
		}else{
			this.getResponse().getWriter().print("false");
		}
		return null;
}
	
	
	
	/**
	 * ��֤Ա�����������ǲ����˻���������
	 * @return
	 * @throws Exception
	 */
	public String checkEmpno() throws Exception {

		Map<String,String> empBrNoMap =new HashMap<String, String>();
		Map<String,String> empNameMap =new HashMap<String, String>();
		String[] empNos = empNo.split("@");
		String sql = "select t.emp_no,t.emp_name,t1.br_name from ftp.FTP_EMP_INFO t left join  ftp.BR_MST t1 on t.br_no = t1.br_no ";
		List empNoList = df.query1(sql,null);
		if(empNoList!=null&&empNoList.size()>0){
			for(int i=0;i<empNoList.size();i++){
                   Object[] objs = (Object[])empNoList.get(i);
				empBrNoMap.put(objs[0]==null?"":objs[0].toString(),objs[2]==null?"":objs[2].toString());
				empNameMap.put(objs[0]==null?"":objs[0].toString(),objs[1]==null?"":objs[1].toString());
			}
		}
		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
		if(telMst.getRoleMst().getRoleNo().equals("201")){
			sql = "select br_no from ftp.fzh_history where ac_id='"+ac_id+"'";
			List list = df.query1(sql,null);
			if(list!=null&&list.size()>0){
				String br_no = list.get(0).toString();
				for(String empNo:empNos){
					String empno =  LrmUtil.checkEmpno(br_no,empNo);
					if(empno!=null){//��Ա�� ����   ��1��֧�е�Ա������ֱ�ӷ���jsp��ʾ�����޸�
						
						this.getResponse().setContentType("text/xml; charset=gbk");
						this.getResponse().setCharacterEncoding("gbk");
						this.getResponse().getWriter().print("�˱�ҵ���Ѿ���������:<br>"+empBrNoMap.get(empno)+":<br>"+empNameMap.get(empno)+"�������޸�");
						return  null;
					}
				}
			}
		}
		return null;
	}




	/**
	 * ��֤Ա�����������ǲ����˻���������[����]
	 * @return
	 * @throws Exception
	 */
	public String checkEmpno_batch() throws Exception {

		Map<String,String> empBrNoMap =new HashMap<String, String>();
		Map<String,String> empNameMap =new HashMap<String, String>();
		String[] ids = ac_id.split(",");
		String sql = "select t.emp_no,t.emp_name,t1.br_name from ftp.FTP_EMP_INFO t left join  ftp.BR_MST t1 on t.br_no = t1.br_no ";
		List empNoList = df.query1(sql,null);
		if(empNoList!=null&&empNoList.size()>0){
			for(int i=0;i<empNoList.size();i++){
				Object[] objs = (Object[])empNoList.get(i);
				empBrNoMap.put(objs[0]==null?"":objs[0].toString(),objs[2]==null?"":objs[2].toString());
				empNameMap.put(objs[0]==null?"":objs[0].toString(),objs[1]==null?"":objs[1].toString());
			}
		}

		for(String id:ids){
			TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
			if(telMst.getRoleMst().getRoleNo().equals("201")){
				sql = "select br_no from ftp.fzh_history where ac_id='"+id.trim()+"'";
				List acIdList = df.query1(sql,null);
				sql = "select EMP_NO from ftp.FTP_EMP_ACC_REL where acc_id='"+id.trim()+"'";
				List empNOList = df.query1(sql,null);

				if(acIdList!=null&&acIdList.size()>0){
					String br_no = acIdList.get(0).toString();
					if(empNOList!=null&&empNOList.size()>0){
						String[] empNos = empNOList.get(0).toString().split("@");
						for(String empNo:empNos){
							String empno =  LrmUtil.checkEmpno(br_no,empNo);
							if(empno!=null){

								this.getResponse().setContentType("text/xml; charset=gbk");
								this.getResponse().setCharacterEncoding("gbk");
								this.getResponse().getWriter().print("�˱�ҵ���Ѿ���������:<br>"+empBrNoMap.get(empno)+":<br>"+empNameMap.get(empno)+"�������޸�");
								return  null;
							}
						}
					}
				}
			}
		}
		return null;
	}

	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getRowsCount() {
		return rowsCount;
	}
	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}
	public String getPrdtNo() {
		return prdtNo;
	}
	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}
	public String getWrkTime1() {
		return wrkTime1;
	}
	public void setWrkTime1(String wrkTime1) {
		this.wrkTime1 = wrkTime1;
	}
	public String getWrkTime2() {
		return wrkTime2;
	}
	public void setWrkTime2(String wrkTime2) {
		this.wrkTime2 = wrkTime2;
	}
	public String getIsRelated() {
		return isRelated;
	}
	public void setIsRelated(String isRelated) {
		this.isRelated = isRelated;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}

	public PageUtil getYGZHGLUtil() {
		return YGZHGLUtil;
	}
	public void setYGZHGLUtil(PageUtil yGZHGLUtil) {
		YGZHGLUtil = yGZHGLUtil;
	}
	public DaoFactory getDf() {
		return df;
	}
	public void setDf(DaoFactory df) {
		this.df = df;
	}
	public String getEmpNo() {
		System.out.println("dsd");
		return empNo;
	}
	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) throws UnsupportedEncodingException {
		this.empName = empName;
	}
	public FtpEmpInfo getFtpEmpInfo() {
		return ftpEmpInfo;
	}
	public void setFtpEmpInfo(FtpEmpInfo ftpEmpInfo) {
		this.ftpEmpInfo = ftpEmpInfo;
	}
	public FtpEmpInfo getPo() {
		return po;
	}
	public void setPo(FtpEmpInfo po) {
		this.po = po;
	}
	public PageUtil getFrpEmpInfoUtil() {
		return frpEmpInfoUtil;
	}
	public void setFrpEmpInfoUtil(PageUtil frpEmpInfoUtil) {
		this.frpEmpInfoUtil = frpEmpInfoUtil;
	}
	public String getAc_id() {
		return ac_id;
	}
	public void setAc_id(String acId) {
		ac_id = acId;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getAc_ids() {
		return ac_ids;
	}
	public void setAc_ids(String acIds) {
		ac_ids = acIds;
	}
	public String getEmpno1() {
		return empno1;
	}
	public void setEmpno1(String empno1) {
		this.empno1 = empno1;
	}
	public String getRelatedEmpNos() {
		return relatedEmpNos;
	}
	public void setRelatedEmpNos(String relatedEmpNos) {
		this.relatedEmpNos = relatedEmpNos;
	}
	public String getAccId() {
		return accId;
	}
	public void setAccId(String accId) {
		this.accId = accId;
	}
	public String getRelType() {
		return relType;
	}
	public void setRelType(String relType) {
		this.relType = relType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCusName() {
		return cusName;
	}
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}
	
	
}
