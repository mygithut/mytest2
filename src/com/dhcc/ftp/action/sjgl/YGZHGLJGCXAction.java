package com.dhcc.ftp.action.sjgl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;
import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.action.BoBuilder;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.FtpEmpInfo;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.CastUtil;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.ExcelExport;
import com.dhcc.ftp.util.PageUtil;

public class YGZHGLJGCXAction  extends BoBuilder {

	private int pageSize = 100;
	private int rowsCount = -1;
	private String brNo;
	private String manageLvl;
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
		" from (select distinct trim(t5.CRDNO) as MMCARD, t.br_no,t4.charge_person_name," +
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
		" t.ac_id,t2.br_no as br_no2 "+
		" from ftp.fzh_history  t " +
		"LEFT JOIN  ftp.FTP_EMP_ACC_REL t1  on t.AC_ID = t1. ACC_ID " +
		"left join  ftp.FTP_EMP_INFO t2 on locate(t2.emp_no,t1.emp_no)>0 "+
        "left join  ftp.br_mst t4 on t.br_no = t4.br_no "+
        "left join bips.NSOP_MIR_CDFM23  t5 on t.SOP_CODE = t5.ACCNO ";//�ų���������
		//��ȥ�����г���صģ������г�����̨�˲�¼���Ѿ���¼��
		hsql+="where t.business_no not in('YW103','YW104','YW105','YW106','YW204','YW205','YW206','YW208')" +
				" and t1.acc_id is not null ";//��ѯ�����˵�
		
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
			hsql +=" and ( t2.br_no "+reportBbBO.getAllChildBr(brNo, manageLvl, telMst.getTelNo())+"";//����Ա�������������в�ѯ

			hsql +=" or t.br_no "+reportBbBO.getAllChildBr(brNo, manageLvl, telMst.getTelNo())+")";//���ݿ����������в�ѯ
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
		
		if(cusName!=null && !cusName.equals("")&&!cusName.equals("null")){
			cusName = CommonFunctions.Chinese_CodeChange(cusName);
			hsql +=" and t.cus_name like '%"+cusName+"%'";
		}
		if (empNo != null && !empNo.equals("")&& !empNo.equals("null")) {
			hsql +=" and t2.emp_no = '"+empNo+"'";
		}
		if(mmCard!=null&&!mmCard.equals("")&&!mmCard.equals("null")){
			hsql +=" and t5.CRDNO = '"+mmCard.trim()+"'";
		}else{
		    if (accId != null && !accId.equals("")&& !accId.equals("null")) {
			   hsql +=" and t.ac_id like '%"+accId.trim()+"%'";
		    }
		}
		hsql += " ) a order by a.br_no desc, a.business_no, a.prdt_no, a.ac_id ";
		String pageName = "YGZHGLJGCX_List.action?pageSize="+pageSize+"&brNo="+brNo+"&manageLvl="+manageLvl+"&businessNo="+businessNo+"&prdtNo="+prdtNo+"&wrkTime1="+wrkTime1+"&wrkTime2="+wrkTime2+"&empNo="+empNo+"&accId="+accId;
		YGZHGLUtil = queryPageBO.sqlQueryPage(hsql, pageSize, page, rowsCount, pageName);
		Map map = getKHJLMap();//��ȡac_id��Ӧ�Ŀͻ������ַ���
		request.setAttribute("map", map);
		Map brMap = getBrMap();//��ȡ����Map(brNo,brName)
		request.setAttribute("brMap", brMap);
		return "List";
	}
	/**
	 * ������ֻ����Ա���Ϊ200001/200002/000000���Խ��е����������������г���֧�С�������ʣ�ಿ�ŵ�����
	 * @return
	 * @throws Exception
	 */
	public String Export() throws Exception {

		String mmCard ="";
		if(accId.startsWith("62")){  //�ж��Ƿ�Ϊ����
			mmCard = accId;
		}
	    
		String brNos = "'3403112019','3403112001','3403112002','3403112003','3403112004','3403112005','3403112010','3403112011','3403112012','3403112013','3403112014','3403112015','3403112016','3403112017','3403112018','3403111033','3403112020','3403112006','3403111031','3403112007','3403112008','3403112009','3403112021','3403112022'";
	    TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
	    String hsql = "select '',a.* "+
		" from (select distinct trim(t5.CRDNO) as MMCARD, t.br_no,t4.charge_person_name," +
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
		" t.ac_id,t2.br_no as br_no2 "+
		" from ftp.fzh_history  t " +
		"LEFT JOIN  ftp.FTP_EMP_ACC_REL t1  on t.AC_ID = t1. ACC_ID " +
		"left join  ftp.FTP_EMP_INFO t2 on locate(t2.emp_no,t1.emp_no)>0 "+
        "left join  ftp.br_mst t4 on t.br_no = t4.br_no "+
		"left join bips.NSOP_MIR_CDFM23  t5 on t.SOP_CODE = t5.ACCNO ";
		//��ȥ�����г���صģ������г�����̨�˲�¼���Ѿ���¼��
/*		hsql+="where t.business_no not in('YW103','YW104','YW105','YW106','YW204','YW205','YW206','YW208') and t.prdt_no!='P2096' and t.prdt_no!='P2097'" +*/
		hsql+="where t.business_no not in('YW103','YW104','YW105','YW106','YW204','YW205','YW206','YW208') " +
				" and t1.acc_id is not null ";
				//" and (locate('99',t1.emp_no)!=1 or (length(t1.emp_no)!=6  and locate('99',t1.emp_no)=1))";
				//" and t2.br_no in ("+brNos+") ";//��ѯ�����˵�
		
		//��ȡ�ò���Ա���Բ鿴�Ŀͻ�����
		String custType = reportBbBO.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
/*	    if(custType.equals("2")) {//ͳ�ƶԹ��ͻ����ͻ�����2��ͷ,��Ϊ�յ�
			hsql +=" and (substr(t.cust_no,1,1)='2' or t.cust_no='' or t.cust_no is null)";
		}else if(custType.equals("1")) {//ͳ�ƶ�˽�ͻ����ͻ�����1,3��ͷ��
			hsql +=" and (substr(t.cust_no,1,1)='1' or substr(t.cust_no,1,1)='3')";
		}*/

		if(custType.equals("2")) {//ͳ�ƶԹ��ͻ�
			hsql +=" yg";
		}else if(custType.equals("1")) {//ͳ�ƶ�˽�ͻ�
			hsql +=" and ((substr(kmh,1,4) in ('2003','2004','2005'))"+
					" or ( substr(kmh,1,4) in ('1301','1302','1303','1304','1305','1307','1308') and substr(cust_no,1,1)='1'))";
		}
		
		if (brNo != null && !brNo.equals("") && !brNo.equals("null") && !manageLvl.equals("2")) {//������ƣ�brNo���벻Ϊ�գ�������ѡ�񣬷���ִ�в�ѯ
			hsql +=" and ( t2.br_no "+reportBbBO.getAllChildBr(brNo, manageLvl, telMst.getTelNo())+"";//����Ա�������������в�ѯ

			hsql +=" or t.br_no "+reportBbBO.getAllChildBr(brNo, manageLvl, telMst.getTelNo())+")";//���ݿ����������в�ѯ
		}
		//�������ݿ�ϵͳ����
		if (wrkTime1 != null && !wrkTime1.equals("")&& !wrkTime1.equals("null")) {
			hsql +=" and to_date(t.opn_date,'yyyymmdd') >= to_date('"+wrkTime1+"','yyyymmdd')";
		}
		if (wrkTime2 != null && !wrkTime2.equals("")&& !wrkTime2.equals("null")) {
			hsql +=" and to_date(t.opn_date,'yyyymmdd') <= to_date('"+wrkTime2+"','yyyymmdd')";
		}
		
		if (businessNo != null && !businessNo.equals("")&& !businessNo.equals("null")) {
			hsql +=" and t.business_no = '"+businessNo+"'";
		}
		if (prdtNo != null && !prdtNo.equals("")&& !prdtNo.equals("null")) {
			hsql +=" and t.prdt_no = '"+prdtNo+"'";
		}
		if(cusName!=null && !cusName.equals("")&&!cusName.equals("null")){
			cusName = new String(cusName.getBytes("iso-8859-1"),"UTF-8");//����ת��
			hsql +=" and t.cus_name like '%"+cusName+"%'";
		}
		if (empNo != null && !empNo.equals("")&& !empNo.equals("null")) {
			hsql +=" and t2.emp_no = '"+empNo+"'";
		}
		if(mmCard!=null&&!mmCard.equals("")&&!mmCard.equals("null")){
			hsql +=" and t5.CRDNO = '"+mmCard.trim()+"'";
		}else{
			if (accId != null && !accId.equals("")&& !accId.equals("null")) {
				hsql +=" and t.ac_id like '%"+accId.trim()+"%'";
			}
		}
		
		hsql += " ) a order by a.br_no desc, a.business_no, a.prdt_no, a.ac_id ";
		List list = df.query1(hsql, null);
		Map empMap = getKHJLMap();//��ȡac_id��Ӧ�Ŀͻ������ַ���
		Map brMap = getBrMap();//��ȡ����Map(brNo,brName)
		
		String title = "����Ա��-�˻��������";
		//����������
		HSSFWorkbook workbook = this.getYGZHGLJGWorkbook(list, wrkTime1, wrkTime2, empMap, brMap, title);

		if (workbook != null) {
			HttpServletResponse response = ServletActionContext.getResponse();
			//���EXCEL
			ExcelExport.workbookInputStream(response, workbook, title);
		}
		return null;
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
	 * ����������
	 * @param list
	 * @param wrkTime1
	 * @param wrkTime2
	 * @param empMap
	 * @param brMap
	 * @param title
	 * @return
	 */
	public HSSFWorkbook getYGZHGLJGWorkbook(List list, String wrkTime1, String wrkTime2, Map empMap, Map brMap, String title) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheet = workbook.createSheet(title);//����һ��sheet
		try {
	        // ��ͷ�ϲ�
			sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 12 ));// �ϲ���һ�е�һ��13��
			sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 12 ));// �ϲ��ڶ��е�һ��13��
			//���õ�һ�л�����Ԫ��Ŀ��
			
			ExcelExport excelExport = new ExcelExport(workbook, sheet);
			excelExport.createRow(0); // ������һ��
			excelExport.setCell(0, title, excelExport.getHeaderStyle());
			
			excelExport.createRow(1); // �����ڶ���
			excelExport.setCell(0, "�������ڶΣ�"+wrkTime1+"-"+wrkTime2, excelExport.getTitleStyle());
			
			excelExport.createRow(2);//�����У���ͷ��
			sheet.setColumnWidth(0, 7*256);
			sheet.setColumnWidth(1, 30*256);
			sheet.setColumnWidth(2, 30*256);
			sheet.setColumnWidth(3, 30*256);
			sheet.setColumnWidth(4, 50*256);
			sheet.setColumnWidth(6, 30*256); 
			excelExport.setCell(0, "���", excelExport.headCenterNormalStyle);
			excelExport.setCell(1, "�ͻ�����", excelExport.headCenterNormalStyle);
			excelExport.setCell(2, "��������", excelExport.headCenterNormalStyle);
			excelExport.setCell(3, "��������", excelExport.headCenterNormalStyle);
			excelExport.setCell(4, "ҵ���˺�", excelExport.headCenterNormalStyle);
			excelExport.setCell(5, "�ͻ���", excelExport.headCenterNormalStyle);
			excelExport.setCell(6, "��Ʒ����", excelExport.headCenterNormalStyle);
			excelExport.setCell(7, "��������", excelExport.headCenterNormalStyle);
			excelExport.setCell(8, "���", excelExport.headCenterNormalStyle);
			excelExport.setCell(9, "���", excelExport.headCenterNormalStyle);
			excelExport.setCell(10, "����(%)", excelExport.headCenterNormalStyle);
			excelExport.setCell(11, "����(��)", excelExport.headCenterNormalStyle);
			excelExport.setCell(12, "��������", excelExport.headCenterNormalStyle);
			if(list != null) {
				for (int j = 0; j < list.size(); j++) {
					Object[] obj = (Object[])list.get(j);
					
					//ѭ������������
					excelExport.createRow(j+3);
					excelExport.setCell(0, j+1, excelExport.centerNormalStyle);
					String emps = "";
					if(obj[13]!=null&&!obj[13].equals("")&&!obj[13].equals("null")){
			     		String[] tempno = obj[13].toString().split("@");
			     		String[] temprate = obj[14].toString().split("@");
 			     		for(int k =0;k<tempno.length;k++){
			     			emps += String.valueOf(empMap.get(tempno[k])==null?"-":empMap.get(tempno[k]));
			     			emps +="(";
			     			if(empMap.get(tempno[k])!=null&&k<tempno.length){
			     				if(Double.parseDouble(temprate[k])<=1.0&&obj[17].toString().equals("1")){
			     					emps += Double.parseDouble(temprate[k])*100.0+"%";
			     				}else{ 
			     					emps += Double.parseDouble(temprate[k]);
			     				}
		     					emps += ")";
		     				}
			     			if(k<tempno.length-1){
			     				emps += "|";
			     			}
			     		}
			     	}else{emps += "-";}
			     	
					excelExport.setCell(1, emps, excelExport.centerNormalStyle);
					excelExport.setCell(3, brMap.get(obj[19])+"["+obj[19]+"]", excelExport.centerNormalStyle);
					excelExport.setCell(2, brMap.get(obj[2])+"["+obj[2]+"]", excelExport.centerNormalStyle);
					excelExport.setCell(4,  obj[1]==null ?CastUtil.trimNull(obj[18]):CastUtil.trimNull(obj[1]), excelExport.centerNormalStyle);
					excelExport.setCell(5, CastUtil.trimNull(obj[4]), excelExport.centerNormalStyle);
					excelExport.setCell(6,CastUtil.trimNull(obj[5]), excelExport.centerNormalStyle);
					excelExport.setCell(7, CastUtil.trimNull(obj[6]), excelExport.centerNormalStyle);
					excelExport.setCell(8, CastUtil.trimNull(obj[7]), excelExport.rightNormalMoneyStyle);
					excelExport.setCell(9, CastUtil.trimNull(obj[8]), excelExport.rightNormalMoneyStyle);
					excelExport.setCell(10, CommonFunctions.doublecut(Double.valueOf(CastUtil.trimNullIsNumber(obj[9],"0")),3), excelExport.rightNormalStyle);
					excelExport.setCell(11, CastUtil.trimNull(obj[10]), excelExport.rightNormalStyle);
					excelExport.setCell(12, CastUtil.trimNull(obj[11]), excelExport.centerNormalStyle);
					
					
				}
			}
			
			
			sheet.getRow(0).setHeight((short)500);//���õ�һ�б��߶�
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
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
	public String getManageLvl() {
		return manageLvl;
	}
	public void setManageLvl(String manageLvl) {
		this.manageLvl = manageLvl;
	}
	
	
}
