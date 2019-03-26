package com.dhcc.ftp.action.sjgl;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.action.BoBuilder;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.FtpEmpAccRel;
import com.dhcc.ftp.entity.FtpEmpInfo;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.DateUtil;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @author yl
 */
public class YGXXWHAction extends BoBuilder implements ModelDriven<FtpEmpInfo>{
	private int pageSize = 10;
	private int rowsCount = -1;
	private int page = 1;
	HttpServletRequest request = getRequest();
	DaoFactory df = new DaoFactory();

	private PageUtil frpEmpInfoUtil = null;
	private FtpEmpInfo po ;
	/**
	 * 查询列表
	 * @return
	 * @throws Exception
	 */
	public String List() throws Exception {
	    TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
		String empName="";
		String hql = "from FtpEmpInfo h  where 1=1 " ;
			//	"and brMst is not null ";//substr(shiborTerm,2,1),substr(shiborTerm,1,1)";
		String ve=this.getRequest().getParameter("ve");
		if(po.getEmpNo()!=null&&!"null".equals(po.getEmpNo())&&!"".equals(po.getEmpNo())){
			hql+=" and  h.empNo like  '%"+po.getEmpNo()+"%'  ";
		}
		if(po.getEmpName()!=null&&!"null".equals(po.getEmpName())&&!"".equals(po.getEmpName())){
			System.out.println("编码转换前EmpName="+po.getEmpName()); 
			empName= CommonFunctions.Chinese_CodeChange(po.getEmpName());
/*			empName=new String(po.getEmpName().getBytes("iso8859-1"));*/
			System.out.println("编码转换后EmpName="+empName);
			
			hql+=" and h.empName like '%"+	empName		+"%' ";
		}
		System.out.println("po.getBrNo()"+po.getBrNo());
		if(po.getBrNo()!=null&&!"null".equals(po.getBrNo())&&!"".equals(po.getBrNo())){
			hql+=" and  h.brMst.brNo  "+LrmUtil.getBrSqlByTel(po.getBrNo(), telMst.getTelNo());
		}
		if(po.getEmpStatus()!=null&&!"null".equals(po.getEmpStatus())&&!"".equals(po.getEmpStatus())){
			hql+=" and   h.empStatus='"+po.getEmpStatus()+"'";
		}
		hql+=" order by brMst.brNo desc,empNo ";
		String pageName = "YGXXWH_List.action?brNo="+this.po.getBrNo()+"&empName="+empName+"&empNo="+this.po.getEmpNo()+"&empStatus="+po.getEmpStatus()+"&ve="+ve;
		frpEmpInfoUtil = queryPageBO.queryPage(hql, pageSize, page, rowsCount, pageName);
		if("1".equals(ve)){
			return "sel";
		}
			return "List";
		
	}
	
	/**
	 * 查询detail
	 * @return
	 * @throws Exception
	 */
	public String Query() throws Exception {
		String hsql = "from FtpEmpInfo where empNo = '"+po.getEmpNo()+"'";
		FtpEmpInfo empInfo = (FtpEmpInfo)df.getBean(hsql, null);
		request.setAttribute("ftpEmpInfo", empInfo);
		return "detail";
	}
	
	/**
	 * 检验员工编号是否重复
	 * @return
	 * @throws Exception
	 */
	public String checkEmp() throws Exception{
		String hsql = "from FtpEmpInfo where empNo = '"+po.getEmpNo()+"'";
		FtpEmpInfo empInfo = (FtpEmpInfo)df.getBean(hsql, null);
		if(empInfo!=null){
			this.getResponse().getWriter().print("no");
		}else{
			this.getResponse().getWriter().print("ok");
		}
		return NONE;
	}
	
	 /**
	  * 保存
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
			TelMst telmst = (TelMst) this.getRequest().getSession().getAttribute("userBean"); 
			BrMst brMst=new BrMst(this.po.getBrNo());
			String now =DateUtil.getCurrentDay()+DateUtil.getCurrentTime();
			this.po.setBrMst(brMst);
			this.po.setLastModityTime(now);
			this.po.setLastModifyTelNo(telmst.getTelNo());
			/*//岗位
			FtpBrPostRel brPost=new FtpBrPostRel();
			brPost.setPostNo(this.po.getPostNo());
			this.po.setBrPost(brPost);
		*/
//	    	if (po.getEmpId() != null && !po.getEmpId().equals("")){
				df.update(this.po);
//	    	}else {
//	    		//添加
//	    		//this.po.setEmpId(this.po.getEmpNo());
//				df.insert(this.po);
//	    	}
	    	return null;
	    }
	
	 /**
	  * 删除方法
	 * @return
	 * @throws Exception
	 */
	public String del() throws Exception {
	    	String[] id = this.po.getEmpNo().split(",");
	    	String message="";
			for(int i = 0; i < id.length; i++){

				List<FtpEmpAccRel> empAccRelList = new ArrayList<FtpEmpAccRel>();
				String hsql = "select t.*,t1.br_no from ( select * from ftp.Ftp_Emp_Acc_Rel where emp_No like '%"+id[i]+"%') t left join ftp.fzh_history t1 on t.acc_id = t1.ac_id where t1.ac_id is not null";
				List list =  df.query1(hsql, null);
				for(Object object:list){
					Object[] objects= (Object[]) object;
					String brNo = objects[8].toString();
					String newEmpNo = objects[2].toString().replace(id[i],"99"+brNo.substring(6,10));
					FtpEmpAccRel ftpEmpAccRel = new FtpEmpAccRel();
					ftpEmpAccRel.setId(objects[0].toString());
					ftpEmpAccRel.setAccId(objects[1].toString());
					ftpEmpAccRel.setEmpNo(newEmpNo);
					ftpEmpAccRel.setRate(objects[3].toString());
					ftpEmpAccRel.setRelTime(objects[4].toString());
					ftpEmpAccRel.setRelTelNo(objects[5].toString());
					ftpEmpAccRel.setRelType(objects[6].toString());
					ftpEmpAccRel.setPrdtNo(objects[7].toString());
					empAccRelList.add(ftpEmpAccRel);
				}

				if(this.checkDel(id[i])>0){
					message+="["+id[i]+"] ";
				}else{
					 hsql="delete from FtpEmpInfo where empNo='"+id[i]+"'";
					System.out.println(hsql);
					df.deleteAndUpdate(hsql, empAccRelList);
				}
			}
			if(message!=null&&!"".equals(message)){
				this.getResponse().getWriter().print(message);
			}else{
				this.getResponse().getWriter().print("ok");
			}
			return NONE;
	}
	
	public int checkDel(String empNo) {
			return 0;
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
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public PageUtil getFrpEmpInfoUtil() {
		return frpEmpInfoUtil;
	}
	public void setFrpEmpInfoUtil(PageUtil frpEmpInfoUtil) {
		this.frpEmpInfoUtil = frpEmpInfoUtil;
	}
	public FtpEmpInfo getPo() {
		return po;
	}
	public void setPo(FtpEmpInfo po) {
		this.po = po;
	}
	public FtpEmpInfo getModel() {
		// TODO Auto-generated method stub
		if(this.po==null){
			this.po=new FtpEmpInfo();
		}
		return  this.po;
	}
}
