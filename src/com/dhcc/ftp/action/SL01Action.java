package com.dhcc.ftp.action;

import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.FtpGkh;
import com.dhcc.ftp.entity.FtpStockYield;
import com.dhcc.ftp.entity.FtpSylqx;
import com.dhcc.ftp.util.PageUtil;

public class SL01Action extends BoBuilder {
	private static final Logger logger = Logger.getLogger(DaoFactory.class);
	private String flag;
	private PageUtil FtpSylqxUtil = null;
	private int page = 1;
	private String curveId;
	private String curveNo;
   private String curNo;
    private String curveType;
    private String curveLlk;
    private String Remark;
    private String curveName;
     private Date curveDate;
     private String gjllmc;
     private Double gjllz;
     private String curveMode;
     private Double curveCkzbjl;
     private Double curveCkzbjll;
    
    
	public String execute() throws Exception {
		HttpServletRequest request = getRequest();
		DaoFactory df = new DaoFactory();
			if (flag.equals("list")) {
			FtpSylqxUtil = ftpSylqxBO.dofind(page,curveName,curveType,curNo,curveDate);
			request.setAttribute("FtpSylqxUtil", FtpSylqxUtil);
			return flag;
		}
			else if (flag.equals("query")) {
			FtpSylqx ftpSylqx = (FtpSylqx)df.getBean("from FtpSylqx where curveId='"+curveId+"'", null);
			request.setAttribute("ftpSylqx", ftpSylqx);
			request.setAttribute("curveId", curveId);
			return flag;
		} else if (flag.equals("add")) {
			FtpSylqx ftpSylqx =new FtpSylqx();
			ftpSylqx.setCurveNo(curveNo);
			ftpSylqx.setCurveName(curveName);
			ftpSylqx.setCurNo(curNo);
			ftpSylqx.setCurveType(curveType);
			ftpSylqx.setCurveLlk(curveLlk);
			ftpSylqx.setRemark(Remark);
			ftpSylqx.setCurveDate(curveDate);
			ftpSylqx.setGjllmc(gjllmc);
			ftpSylqx.setCurveCkzbjl(curveCkzbjl);
			ftpSylqx.setCurveCkzbjll(curveCkzbjll);
			//������ڲ����������ߣ���������ߵ���
			double VOF=gjllz;
			double COF=gjllz;
			double VOF1=0.0;double COF1=COF;
			double step1value=0.0;
			if(curveType.equals("2")){
				//���õȼ�����
				if(gjllmc.indexOf("��")!=-1){
					step1value=0;
				}else if(gjllmc.equals("һ��")){
					String hql = "from FtpGkh where gkhTerm=1 and gkhDate="+curveDate;
					FtpGkh ftpGkh = (FtpGkh)df.getBean(hql, null);	
					double gkhYield=ftpGkh.getGkhYield();
					String hql1 = "from FtpStock where stockTerm=1 and stockDate="+curveDate;
					FtpStockYield ftpStock = (FtpStockYield)df.getBean(hql1, null);	
					double stockYield=ftpStock.getStockYield();
					step1value=gkhYield-stockYield;
				}
				else if(gjllmc.equals("����")){
					String hql = "from FtpGkh where gkhTerm=2 and gkhDate="+curveDate;
					FtpGkh ftpGkh = (FtpGkh)df.getBean(hql, null);	
					double gkhYield=ftpGkh.getGkhYield();
					String hql1 = "from FtpStock where stockTerm=2 and stockDate="+curveDate;
					FtpStockYield ftpStock = (FtpStockYield)df.getBean(hql1, null);	
					double stockYield=ftpStock.getStockYield();
					step1value=gkhYield-stockYield;
				}
				else if(gjllmc.equals("����")){
					String hql = "from FtpGkh where gkhTerm=3 and gkhDate="+curveDate;
					FtpGkh ftpGkh = (FtpGkh)df.getBean(hql, null);	
					double gkhYield=ftpGkh.getGkhYield();
					String hql1 = "from FtpStock where stockTerm=3 and stockDate="+curveDate;
					FtpStockYield ftpStock = (FtpStockYield)df.getBean(hql1, null);	
					double stockYield=ftpStock.getStockYield();
					step1value=gkhYield-stockYield;
				}
				else if(gjllmc.equals("����")){
					String hql = "from FtpGkh where gkhTerm=5 and gkhDate="+curveDate;
					FtpGkh ftpGkh = (FtpGkh)df.getBean(hql, null);	
					double gkhYield=ftpGkh.getGkhYield();
					String hql1 = "from FtpStock where stockTerm=4 and stockDate="+curveDate;
					FtpStockYield ftpStock = (FtpStockYield)df.getBean(hql1, null);	
					double stockYield=ftpStock.getStockYield();
					step1value=gkhYield-stockYield;
				}
					VOF1=VOF+step1value;	
					System.out.println("VOF1:"+VOF1);
					System.out.println("COF1:"+COF1);
				//��������۵���
				double step2value=0.0;
				double VOF2=0.0;double COF2=0.0;
				if(gjllmc.indexOf("��")!=-1){
					//һ��������ծȯ�ع��г�ÿ����߳ɽ��ۺ���ͳɽ���֮��ֵ����
					String hql1 = "select max(zqhg_yield) from risk.ftp_zqhgsc where zqhg_date='"+curveDate+"'";
					List zqhgList = df.query1(hql1, null);	
					step2value=Double.parseDouble(zqhgList.get(0).toString())/100;	
				}else{//һ�������Թ�ծ�г�ÿ����߳ɽ��ۺ���ͳɽ���֮��ֵ��Ϊ������Χ
					String hql1 = "select max(gz_yield) from risk.ftp_gzsc where gz_date='"+curveDate+"'";
					List gzList = df.query1(hql1, null);	
					step2value=Double.parseDouble(gzList.get(0).toString())/100;				
				}
				VOF2=VOF1+step2value/2;
				COF2=COF1+step2value/2;
				System.out.println("VOF2:"+VOF2);
				System.out.println("COF2:"+COF2);
				//�������׼����ɱ�����
				double VOF3=0.0;double COF3=0.0;
				VOF3=VOF2*(1-curveCkzbjl)+curveCkzbjl*curveCkzbjll;
				COF3=(COF2-curveCkzbjl*curveCkzbjll)/(1-curveCkzbjl);
				System.out.println("VOF3:"+VOF3);
				System.out.println("COF3:"+COF3);
				//�����Ե���
				double VOF4=VOF3;double COF4=0.0;
				if(gjllmc.equals("����")){
					COF4=COF3-0.537767/100;	
				}
				if(gjllmc.equals("����")){
					COF4=COF3-1.077767/100;	
				}
				if(gjllmc.equals("����")){
					COF4=COF3-1.208302/100;	
				}
				if(gjllmc.equals("����")){
					COF4=COF3-1.338836/100;	
				}
				if(gjllmc.indexOf("��")>=1&&!gjllmc.equals("һ��")&&!gjllmc.equals("����")&&!gjllmc.equals("����")&&!gjllmc.equals("����")&&!gjllmc.equals("����")){
					COF4=COF3-1.338836/100;	
				}else{
					COF4=COF3;
				}
				System.out.println("VOF4:"+VOF4);
				System.out.println("COF4:"+COF4);
				if(curveMode.equals("0")){
					System.out.println("�����Ǵ��");
			ftpSylqx.setGjllz(COF4);	
				}else if(curveMode.equals("1")){
					System.out.println("�����Ǵ���");
					ftpSylqx.setGjllz(VOF4);	
				}
			}else{
			ftpSylqx.setGjllz(gjllz);
			}
			ftpSylqx.setCurveMode(curveMode);
			df.insert(ftpSylqx);
		}
		if (flag.equals("update")) {
			double gjllzvalue=0.0;
			//������ڲ����������ߣ���������ߵ���
			double VOF=gjllz;
			double COF=gjllz;
			double VOF1=0.0;double COF1=COF;
			double step1value=0.0;
			if(curveType.equals("2")){
				//���õȼ�����
				if(gjllmc.indexOf("��")!=-1){
					step1value=0;
				}else if(gjllmc.equals("һ��")){
					String hql = "from FtpGkh where gkhTerm=1 and gkhDate="+curveDate;
					FtpGkh ftpGkh = (FtpGkh)df.getBean(hql, null);	
					double gkhYield=ftpGkh.getGkhYield();
					String hql1 = "from FtpStock where stockTerm=1 and stockDate="+curveDate;
					FtpStockYield ftpStock = (FtpStockYield)df.getBean(hql1, null);	
					double stockYield=ftpStock.getStockYield();
					step1value=gkhYield-stockYield;
				}
				else if(gjllmc.equals("����")){
					String hql = "from FtpGkh where gkhTerm=2 and gkhDate="+curveDate;
					FtpGkh ftpGkh = (FtpGkh)df.getBean(hql, null);	
					double gkhYield=ftpGkh.getGkhYield();
					String hql1 = "from FtpStock where stockTerm=2 and stockDate="+curveDate;
					FtpStockYield ftpStock = (FtpStockYield)df.getBean(hql1, null);	
					double stockYield=ftpStock.getStockYield();
					step1value=gkhYield-stockYield;
				}
				else if(gjllmc.equals("����")){
					String hql = "from FtpGkh where gkhTerm=3 and gkhDate="+curveDate;
					FtpGkh ftpGkh = (FtpGkh)df.getBean(hql, null);	
					double gkhYield=ftpGkh.getGkhYield();
					String hql1 = "from FtpStock where stockTerm=3 and stockDate="+curveDate;
					FtpStockYield ftpStock = (FtpStockYield)df.getBean(hql1, null);	
					double stockYield=ftpStock.getStockYield();
					step1value=gkhYield-stockYield;
				}
				else if(gjllmc.equals("����")){
					String hql = "from FtpGkh where gkhTerm=5 and gkhDate="+curveDate;
					FtpGkh ftpGkh = (FtpGkh)df.getBean(hql, null);	
					double gkhYield=ftpGkh.getGkhYield();
					String hql1 = "from FtpStock where stockTerm=4 and stockDate="+curveDate;
					FtpStockYield ftpStock = (FtpStockYield)df.getBean(hql1, null);	
					double stockYield=ftpStock.getStockYield();
					step1value=gkhYield-stockYield;
				}
					VOF1=VOF+step1value;	
					System.out.println("VOF1:"+VOF1);
					System.out.println("COF1:"+COF1);
				//��������۵���
				double step2value=0.0;
				double VOF2=0.0;double COF2=0.0;
				if(gjllmc.indexOf("��")!=-1){
					//һ��������ծȯ�ع��г�ÿ����߳ɽ��ۺ���ͳɽ���֮��ֵ����
					String hql1 = "select max(zqhg_yield) from risk.ftp_zqhgsc where zqhg_date='"+curveDate+"'";
					List zqhgList = df.query1(hql1, null);	
					step2value=Double.parseDouble(zqhgList.get(0).toString())/100;	
				}else{//һ�������Թ�ծ�г�ÿ����߳ɽ��ۺ���ͳɽ���֮��ֵ��Ϊ������Χ
					String hql1 = "select max(gz_yield) from risk.ftp_gzsc where gz_date='"+curveDate+"'";
					List gzList = df.query1(hql1, null);	
					step2value=Double.parseDouble(gzList.get(0).toString())/100;				
				}
				VOF2=VOF1+step2value/2;
				COF2=COF1+step2value/2;
				System.out.println("VOF2:"+VOF2);
				System.out.println("COF2:"+COF2);
				//�������׼����ɱ�����
				double VOF3=0.0;double COF3=0.0;
				VOF3=VOF2*(1-curveCkzbjl)+curveCkzbjl*curveCkzbjll;
				COF3=(COF2-curveCkzbjl*curveCkzbjll)/(1-curveCkzbjl);
				System.out.println("VOF3:"+VOF3);
				System.out.println("COF3:"+COF3);
				//�����Ե���
				double VOF4=VOF3;double COF4=0.0;
				if(gjllmc.equals("����")){
					COF4=COF3-0.537767/100;	
				}
				if(gjllmc.equals("����")){
					COF4=COF3-1.077767/100;	
				}
				if(gjllmc.equals("����")){
					COF4=COF3-1.208302/100;	
				}
				if(gjllmc.equals("����")){
					COF4=COF3-1.338836/100;	
				}
				if(gjllmc.indexOf("��")>=1&&!gjllmc.equals("һ��")&&!gjllmc.equals("����")&&!gjllmc.equals("����")&&!gjllmc.equals("����")&&!gjllmc.equals("����")){
					COF4=COF3-1.338836/100;	
				}else{
					COF4=COF3;
				}
				System.out.println("VOF4:"+VOF4);
				System.out.println("COF4:"+COF4);
				
				if(curveMode.equals("0")){
					System.out.println("�����Ǵ��");
					gjllzvalue=COF4;	
				}else if(curveMode.equals("1")){
					System.out.println("�����Ǵ���");
					gjllzvalue=VOF4;	
				}
			}else{
				gjllzvalue=gjllz;
			}
			String hsql = "update FtpSylqx set curveNo='"+curveNo+"',curveName='" + curveName + "',curNo='"
					+ curNo + "',curveType='" + curveType + "',curveLlk='" + curveLlk + "',Remark='"
					+ Remark + "',gjllmc='"+gjllmc+"',gjllz='"+gjllzvalue+"',curveMode='"
					+curveMode+"',curveDate='"+curveDate+"',curveCkzbjl='"+curveCkzbjl+"',curveCkzbjll='"+curveCkzbjll+"' where curve_Id='" + curveId + "'";
			df.update(hsql, null);
			}
		if (flag.equals("del")) {
			String[] typeIdStr = curveId.split(",");
			for (int i = 0; i < typeIdStr.length; i++) {
				String hsql = "delete from FtpSylqx where curve_Id="+ typeIdStr[i] + "";
				df.delete(hsql, null);
			}
		}
		return null;

	}

	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public PageUtil getFtpSylqxUtil() {
		return FtpSylqxUtil;
	}


	public void setFtpSylqxUtil(PageUtil FtpSylqxUtil) {
		FtpSylqxUtil = FtpSylqxUtil;
	}


	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public String getCurveId() {
		return curveId;
	}


	public void setCurveId(String curveId) {
		this.curveId = curveId;
	}


	public String getCurveNo() {
		return curveNo;
	}


	public void setCurveNo(String curveNo) {
		this.curveNo = curveNo;
	}

	public String getCurNo() {
		return curNo;
	}


	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}


	public String getCurveType() {
		return curveType;
	}


	public void setCurveType(String curveType) {
		this.curveType = curveType;
	}


	public String getCurveLlk() {
		return curveLlk;
	}


	public void setCurveLlk(String curveLlk) {
		this.curveLlk = curveLlk;
	}


	public String getRemark() {
		return Remark;
	}


	public void setRemark(String remark) {
		Remark = remark;
	}

	public String getCurveName() {
		return curveName;
	}

	public void setCurveName(String curveName)  throws UnsupportedEncodingException {
		this.curveName = new String(curveName.getBytes("iso-8859-1"),"UTF-8");
	}

	public String getGjllmc() {
		return gjllmc;
	}

	public void setGjllmc(String gjllmc) {
		this.gjllmc = gjllmc;
	}

	public Double getGjllz() {
		return gjllz;
	}

	public void setGjllz(Double gjllz) {
		this.gjllz = gjllz;
	}

	public String getCurveMode() {
		return curveMode;
	}

	public void setCurveMode(String curveMode) {
		this.curveMode = curveMode;
	}

	public Date getCurveDate() {
		return curveDate;
	}

	public void setCurveDate(Date curveDate) {
		this.curveDate = curveDate;
	}

	public Double getCurveCkzbjl() {
		return curveCkzbjl;
	}

	public void setCurveCkzbjl(Double curveCkzbjl) {
		this.curveCkzbjl = curveCkzbjl;
	}

	public Double getCurveCkzbjll() {
		return curveCkzbjll;
	}

	public void setCurveCkzbjll(Double curveCkzbjll) {
		this.curveCkzbjll = curveCkzbjll;
	}
	
}
