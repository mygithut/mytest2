package com.dhcc.ftp.action;
/**
 * 数据管理/金融市场业务_回购导入
 * @author lijin
 */
import java.io.File;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.JrHg;
import com.dhcc.ftp.util.DateUtil;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;
import com.opensymphony.xwork2.ModelDriven;

public class JRSCYWDRHGAction  extends BoBuilder implements ModelDriven<JrHg> {
	private JrHg po;
	
	private int page = 1;
	private int pageSize = 10;
	private int rowsCount = -1;
	private PageUtil JrHgUtil = null;
	HttpServletRequest request = ServletActionContext.getRequest();
	private String savePath = null;
	DaoFactory df = new DaoFactory();
	private File ClientExcelFile;

	
	public String List() throws Exception {
		String pageName = "JRSCYWDRHG_List.action?pageSize=" + pageSize;
		String hsql = "from JrHg where 1=1 ";

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
		JrHgUtil = queryPageBO.queryPage(hsql, pageSize, page, rowsCount,
				pageName);
		request.setAttribute("JrHgUtil", JrHgUtil);
		return "List";
	}
	
	public String edit() throws Exception {
		String hsql = "from JrHg where acId ='" + po.getAcId() + "'";
		JrHg jrhg = (JrHg) df.getBean(hsql, null);
		request.setAttribute("jrhg", jrhg);
		return "Edit";
	}
	public String execute() throws Exception {
		HttpServletRequest request = getRequest();
		return super.execute();
	}
	public String update() throws Exception {
		System.out.println("acId===========" + po.getAcId());
		String[] params=po.getPrdtNo().split("@@");
		po.setPrdtNo(params[0]);
		po.setKmh(params[1]);
		po.setBrName(po.getBrName().substring(0,	po.getBrName().lastIndexOf("[")));
		int days=this.getDays(po.getSqjgr(), po.getDqjgr());
		po.setJyts(days);
		po.setHglv(po.getHglv()/100);
		df.update(po);
		return null;
	}
	 public String del() throws Exception {
			String acIds[] = po.getAcId().split("@@");
			for (int i = 0; i < acIds.length; i++) {
				String hsql = "delete from JrHg where acId='" + acIds[i]+"'";
				df.delete(hsql, null);
			}
			return null;
	    }
	public String doImport() throws Exception {
		getResponse().setContentType("text/html; charset=gb2312");  
		int successNum = 0;
		int total =0;
		File file = new File(savePath);
		PrintWriter pw = getResponse().getWriter();
		String msg = "";//msg结构为：total总共的数据量；successNum成功数；failNum失败数；ac_id失败条主键；reason失败原因
		String contents = "'contents':[";
		try {
			List<Object> list = FtpUtil.importExcel(file, JrHg.class);
			for (Object obj : list) {
				JrHg jrHg = (JrHg) obj;
				if (jrHg == null) {
					// 待修改
					continue;
				}
				// 判断excel是否填写ID
				if (null != jrHg.getAcId()&&  !"".equals(jrHg.getAcId())&&null!=jrHg.getPrdtName()&&!jrHg.getPrdtName().equals("")) {
					total++;
					if(!jrHg.getAcId().matches("[\\w]+")){
					    contents+="{'ac_id':'"+jrHg.getAcId()+"','reason':'序号格式有误'},";
						continue;
					}
					if(jrHg.getBrName()!=null&&!jrHg.getBrName().equals("")&&!jrHg.getBrName().equalsIgnoreCase("null")){
						if(jrHg.getPrdtName()==null||jrHg.getPrdtName().indexOf("[")==-1){
							contents+="{'ac_id':'"+jrHg.getAcId()+"','reason':'产品名称格式有误'},";
							break;
						}else{
							int start1 = jrHg.getPrdtName().indexOf("[");
							int end2 = jrHg.getPrdtName().lastIndexOf("]");
							int end1 = jrHg.getPrdtName().indexOf("]", start1);
							int start2 = end1+1;
						    jrHg.setPrdtNo(jrHg.getPrdtName().substring(start1+1, end1));
						    jrHg.setKmh(jrHg.getPrdtName().substring(start2+1, end2));
						    jrHg.setPrdtName(jrHg.getPrdtName().substring(0, start1));
						}
					}
				if(null!=jrHg.getSqjgr()&&!jrHg.getSqjgr().equals("")&&jrHg.getDqjgr()!=null&&!jrHg.getDqjgr().equals("")){
					SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
					sdFormat.format(new Date(Integer.parseInt(jrHg.getDqjgr())));
					Integer wholeDays = Integer.parseInt(jrHg.getDqjgr());
					Calendar calendar = new GregorianCalendar(); // using default time-zone
					setCalendar(calendar, wholeDays, 0, false);
					jrHg.setDqjgr(sdFormat.format(calendar.getTime()));
					wholeDays = Integer.parseInt(jrHg.getSqjgr());
					setCalendar(calendar, wholeDays, 0, false);
					jrHg.setSqjgr(sdFormat.format(calendar.getTime()));
				}else{
					contents+="{'ac_id':'"+jrHg.getAcId()+"','reason':'首期交割日或到到期交割日为空'},";
				    continue;
				}
//					if(!getDateFstr(jrHg.getDqjgr())||!getDateFstr(jrHg.getSqjgr())){
//						contents+="{'ac_id':'"+jrHg.getAcId()+"','reason':'首期交割日或到到期交割日格式有误'},";
//					    continue;
//					}
				jrHg.setHglv(jrHg.getHglv()/100);
				jrHg.setAcId(jrHg.getAcId()+"_JRHG");
					df.update(jrHg);
					successNum++;
				}else{
//					break;
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
		String sqlfind = "select nvl(max(int(substr(ac_id,1,locate('_',ac_id)-1))),0)+1 from ftp.Jr_HG";
		List list = df.query1(sqlfind, null);
		String acId = list.get(0).toString();
		this.getResponse().getWriter().print(acId);
		return NONE;
	}
	/**
	 * 保存手工录入的数据
	 * @return
	 */
	public String  saveHg() throws Exception{
		String[] params=po.getPrdtNo().split("@@");
		po.setPrdtNo(params[0]);
		po.setKmh(params[1]);
		po.setBrName(po.getBrName().substring(0,	po.getBrName().lastIndexOf("[")));
		int days=this.getDays(po.getSqjgr(), po.getDqjgr());
		po.setJyts(days);
		po.setHglv(po.getHglv()/100);
		po.setAcId(po.getAcId()+"_JRHG");
		df.insert(po);
		return null;
	}
	/**
	 * 检验重复
	 * @return
	 * @throws Exception
	 */
	public String checkId() throws Exception{
		String hqlfind = "from JrHg where acId='" + po.getAcId()+"_JRHG" + "'";
		List list = df.query(hqlfind, null);
		if(list.size()==0){
			this.getResponse().getWriter().print("ok");
		}else{
			this.getResponse().getWriter().print("no");
		}
		return NONE;
	}
	
    /**
	 * 计算日期之差
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private int getDays(String startDate,String endDate){
		if(startDate.length()!=8||endDate.length()!=8){
			return 0;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		Date d1=null,d2=null;
		try {
			d1 = sdf.parse(startDate);
			d2= sdf.parse(endDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		int count=DateUtil.getDaysInterval(d1, d2);
		return count;
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
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
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
	public PageUtil getJrHgUtil() {
		return JrHgUtil;
	}
	public void setJrHgUtil(PageUtil jrHgUtil) {
		JrHgUtil = jrHgUtil;
	}
	public File getClientExcelFile() {
		return ClientExcelFile;
	}
	public void setClientExcelFile(File clientExcelFile) {
		ClientExcelFile = clientExcelFile;
	}
	public JrHg getModel() {
		if(po==null){
			po=new JrHg();
		}
		return po;
	}
	public JrHg getPo() {
		return po;
	}
	public void setPo(JrHg po) {
		this.po = po;
	}
	
}
