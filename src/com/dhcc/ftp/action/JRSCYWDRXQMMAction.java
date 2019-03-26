package com.dhcc.ftp.action;
/**
 * 数据管理/金融市场业务_现券买卖导入
 * @author lijin
 */
import java.io.File;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.JrXqmm;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;
import com.opensymphony.xwork2.ModelDriven;

public class JRSCYWDRXQMMAction  extends BoBuilder implements ModelDriven<JrXqmm>{
	private JrXqmm po;
	private int page = 1;
	private int pageSize = 10;
	private int rowsCount = -1;
	private PageUtil JrXqmmUtil = null;
	HttpServletRequest request = ServletActionContext.getRequest();
	private String savePath = null;
	DaoFactory df = new DaoFactory();
	private File ClientExcelFile;

	
	public String List() throws Exception {
		
		String pageName = "JRSCYWDRXQMM_List.action?pageSize=" + pageSize;
		String hsql = "from JrXqmm where 1=1 ";

		// 机构名称
		if (po.getBrNo() != null && !po.getBrNo().equals("") && !po.getBrNo().equals("null")) {
			hsql += " and brNo " + LrmUtil.getBrSql(po.getBrNo());
		}

		// 交易对手
		String cusName = "";
		if (po.getCusName() != null && !po.getCusName().equals("") && !po.getCusName().equals("null")) {
			cusName =  new String(po.getCusName().getBytes("ISO-8859-1"),"UTF-8");
			hsql += " and cusName like '%" + cusName + "%'";
			pageName += "&cusName=" + URLEncoder.encode(cusName, "UTF-8");
		}
		hsql += " order by acId";
		JrXqmmUtil = queryPageBO.queryPage(hsql, pageSize, page, rowsCount,
				pageName);
		request.setAttribute("JrXqmmUtil", JrXqmmUtil);
		return "List";
	}

	public String execute() throws Exception {
		HttpServletRequest request = getRequest();
		return super.execute();
	}
	public String edit() throws Exception {

		String hsql = "from JrXqmm where acId ='" + po.getAcId() + "'";
		JrXqmm jrXqmm = (JrXqmm) df.getBean(hsql, null);
		request.setAttribute("jrXqmm", jrXqmm);

		return "Edit";
	}

	public String update() throws Exception {
		String[] params=po.getPrdtNo().split("@@");
		po.setPrdtNo(params[0]);
		po.setKmh(params[1]);
		po.setBrName(po.getBrName().substring(0,	po.getBrName().lastIndexOf("[")));
		po.setRate(po.getRate()/100);
		df.update(po);
		return null;
	}
	 public String del() throws Exception {
		 String acIds[] = po.getAcId().split("@@");
			for (int i = 0; i < acIds.length; i++) {
				String hsql = "delete from JrXqmm where acId='" + acIds[i]+"'";
				df.delete(hsql, null);
			}
			return null;
	 }
	public String doImport() throws Exception  {
		getResponse().setContentType("text/html; charset=gb2312");  
		int successNum = 0;
		int total =0;
		File file = new File(savePath);
		PrintWriter pw = getResponse().getWriter();
		String msg = "";//msg结构为：total总共的数据量；successNum成功数；failNum失败数；ac_id失败条主键；reason失败原因
		String contents = "'contents':[";
		try {
			List<Object> list = FtpUtil.importExcel(file, JrXqmm.class);
			for (Object obj : list) {
				JrXqmm jrxqmm = (JrXqmm) obj;
				if (jrxqmm == null) {
					//待修改
					continue;
				}
				// 判断excel是否填写ID
				if (null != jrxqmm.getAcId()&&  !"".equals(jrxqmm.getAcId())&&null!=jrxqmm.getPrdtName()&&!jrxqmm.getPrdtName().equals("")) {
				     total++;
				    if(!jrxqmm.getAcId().matches("[\\w]+")){
					    contents+="{'ac_id':'"+jrxqmm.getAcId()+"','reason':'序号格式有误'},";
						continue;
					}
					if(jrxqmm.getBrName()!=null&&!jrxqmm.getBrName().equals("")&&!jrxqmm.getBrName().equalsIgnoreCase("null")){
					if(jrxqmm.getPrdtName()==null||jrxqmm.getPrdtName().indexOf("[")==-1){
						contents+="{'ac_id':'"+jrxqmm.getAcId()+"','reason':'产品名称格式有误'},";
						break;
					}else{
						int start1 = jrxqmm.getPrdtName().indexOf("[");
						int end2 = jrxqmm.getPrdtName().lastIndexOf("]");
						int end1 = jrxqmm.getPrdtName().indexOf("]", start1);
						int start2 = end1+1;
					    jrxqmm.setPrdtNo(jrxqmm.getPrdtName().substring(start1+1, end1));
					    jrxqmm.setKmh(jrxqmm.getPrdtName().substring(start2+1, end2));
					    jrxqmm.setPrdtName(jrxqmm.getPrdtName().substring(0, start1));
					}
				}
				if(null!=jrxqmm.getBuyDate()&&!jrxqmm.getBuyDate().equals("")){

					SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
					sdFormat.format(new Date(Integer.parseInt(jrxqmm.getBuyDate())));
					Integer wholeDays = Integer.parseInt(jrxqmm.getBuyDate());
					Calendar calendar = new GregorianCalendar(); // using default time-zone
					setCalendar(calendar, wholeDays, 0, false);
					jrxqmm.setBuyDate(sdFormat.format(calendar.getTime()));
				}else{
					contents+="{'ac_id':'"+jrxqmm.getAcId()+"','reason':'买入日期为空'},";
					continue;
				}
//				if(!getDateFstr(jrxqmm.getBuyDate())){
//					contents+="{'ac_id':'"+jrxqmm.getAcId()+"','reason':'买入日期格式有误'},";
//					continue;
//				}
				jrxqmm.setRate(jrxqmm.getRate()/100);
				jrxqmm.setAcId(jrxqmm.getAcId()+"_JRXQMM");
				df.update(jrxqmm);
				successNum++;
			}else{
				//break;
			}
			}
	} catch (Exception e) {
		if(contents.lastIndexOf(",")!=-1){
			contents = contents.substring(0, contents.length()-1)+"],";
		}
		msg = "{'total:'"+total+",'successNum':" + successNum +",'failNum':"+(total-successNum)+","+contents+ ",'success':false}";
		pw.write(msg);
		pw.flush();
		pw.close();
		return null;
	}
	if(total==successNum){
		msg = "{'successNum':" + successNum + ",'success':true}";
	}else{
		if(contents.lastIndexOf(",")!=-1){
			contents = contents.substring(0, contents.length()-1)+"]";
		}
		msg = "{'total':'"+total+"','successNum':'" + successNum +"','failNum':'"+(total-successNum)+"',"+contents+ ",'success':false}";
	}
	pw.write(msg);
	pw.flush();
	pw.close();
		return null;
	}
	/**
	 * 获取下一个序号
	 * @return
	 * @throws Exception 
	 */
	public String getNextAcId() throws Exception {
		String sqlfind = "select nvl(max(int(substr(ac_id,1,locate('_',ac_id)-1))),0)+1 from ftp.Jr_XQMM";
		List list = df.query1(sqlfind, null);
		String acId = list.get(0).toString();
		this.getResponse().getWriter().print(acId);
		return NONE;
	}
	/**
	 * 保存手工录入的数据
	 * @return
	 */
	public String  saveXqmm() throws Exception{
		String[] params=po.getPrdtNo().split("@@");
		po.setPrdtNo(params[0]);
		po.setKmh(params[1]);
		po.setBrName(po.getBrName().substring(0,	po.getBrName().lastIndexOf("[")));
		po.setRate(po.getRate()/100);
		po.setAcId(po.getAcId()+"_JRXQMM");
		df.insert(po);
		return NONE;
	}
	/**
	 * 检验重复
	 * @return
	 * @throws Exception
	 */
	public String checkId() throws Exception{
		String hqlfind = "from JrXqmm where acId='" + po.getAcId()+"_JRXQMM" + "'";
		List list = df.query(hqlfind, null);
		if(list.size()==0){
			this.getResponse().getWriter().print("ok");
		}else{
			this.getResponse().getWriter().print("no");
		}
		return NONE;
	}
	
	public static void setCalendar(Calendar calendar, int wholeDays,
			   int millisecondsInDay, boolean use1904windowing) {
			   int startYear = 1900;
			   int dayAdjust = -1; // Excel thinks 2/29/1900 is a valid date, which it isn't
			   if (use1904windowing) {
			       startYear = 1904;
			       dayAdjust = 1; // 1904 date windowing uses 1/2/1904 as the first day
			   }
			   else if (wholeDays < 61) {
			       // Date is prior to 3/1/1900, so adjust because Excel thinks 2/29/1900 exists
			       // If Excel date == 2/29/1900, will become 3/1/1900 in Java representation
			       dayAdjust = 0;
			  }
			  calendar.set(startYear,0, wholeDays + dayAdjust, 0, 0, 0);
			  calendar.set(GregorianCalendar.MILLISECOND, millisecondsInDay);
		      }

	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public DaoFactory getDf() {
		return df;
	}
	public void setDf(DaoFactory df) {
		this.df = df;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
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
	public PageUtil getJrXqmmUtil() {
		return JrXqmmUtil;
	}
	public void setJrXqmmUtil(PageUtil jrXqmmUtil) {
		JrXqmmUtil = jrXqmmUtil;
	}
	public File getClientExcelFile() {
		return ClientExcelFile;
	}
	public void setClientExcelFile(File clientExcelFile) {
		ClientExcelFile = clientExcelFile;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}

	public JrXqmm getModel() {
		if(po==null){
			po=new JrXqmm();
		}
		return po;
	}
	
}
