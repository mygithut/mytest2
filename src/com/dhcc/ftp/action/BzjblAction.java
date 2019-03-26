package com.dhcc.ftp.action;

/**
 * 数据管理/金融市场业务_同业导入
 * @author lijin
 */
import java.io.File;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.JrBzj;
import com.dhcc.ftp.entity.JrTy;
import com.dhcc.ftp.util.DateUtil;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;
import com.opensymphony.xwork2.ModelDriven;

public class BzjblAction extends BoBuilder implements ModelDriven<JrBzj> {
	private JrBzj po;

	private int page = 1;
	private int pageSize = 10;
	private int rowsCount = -1;
	private PageUtil JrTyUtil = null;
	HttpServletRequest request = ServletActionContext.getRequest();
	private String savePath = null;
	DaoFactory df = new DaoFactory();
	private File ClientExcelFile;

	public String execute() throws Exception {
		return super.execute();
	}
	
	public String List() throws Exception {

		// String custNameTest=custName==null?null:(new
		// String(custName.trim().getBytes("iso-8859-1"),"UTF-8"));
		String pageName = "Bzjbl_List.action?pageSize=" + pageSize;
		String hsql = "from JrBzj where 1=1 ";

		// 机构名称
		if (po.getBrNo() != null && !po.getBrNo().equals("")  && !po.getBrNo() .equals("null")) {
			hsql += " and brNo " + LrmUtil.getBrSql(po.getBrNo() );
		}

		//客户名称
		String cusName = "";
		if (po.getCusName() != null && !po.getCusName().equals("") && !po.getCusName().equals("null")) {
			cusName =  new String(po.getCusName().getBytes("ISO-8859-1"),"UTF-8");
			hsql += " and cusName like '%" + cusName + "%'";
			pageName += "&cusName=" + URLEncoder.encode(cusName, "UTF-8");
		}
		hsql += " order by acId";
		JrTyUtil = queryPageBO.queryPage(hsql, pageSize, page, rowsCount,
				pageName);
		request.setAttribute("JrBzjUtil", JrTyUtil);
		return "List";
	}

	public String edit() throws Exception {

		String hsql = "from JrBzj where acId ='" +po.getAcId() + "'";
		JrBzj jrbzj = (JrBzj) df.getBean(hsql, null);
		request.setAttribute("JrBzj", jrbzj);

		return "Edit";
	}

	public String update() throws Exception {
		System.out.println("acId===========" + po.getAcId());
		String[] params=po.getPrdtNo().split("@@");
		po.setPrdtNo(params[0]);
		po.setKmh(params[1]);
		po.setBrName(po.getBrName().substring(0,	po.getBrName().lastIndexOf("[")));
		int days=this.getDays(po.getOpnDate(), po.getMtrDate());
		po.setDays(Double.valueOf(String.valueOf(days)));
		po.setRate(po.getRate()/100);
		df.update(po);
		return null;
	}

	public String del() throws Exception {
		String acIds[] = po.getAcId().split("@@");
		for (int i = 0; i < acIds.length; i++) {
			String hsql = "delete from JrBzj where acId='" + acIds[i]+"'";
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
			List<Object> list = FtpUtil.importExcel(file, JrBzj.class);
			for (Object obj : list) {
				JrBzj jrty = (JrBzj) obj;
				if (jrty == null) {
					// 待修改
					continue;
				}
				// 判断excel是否填写ID
				if (null != jrty.getAcId()	&&  !"".equals(jrty.getAcId())&&null!=jrty.getPrdtName()&&!jrty.getPrdtName().equals("")) {
					total++;
					if(!jrty.getAcId().matches("[\\w]+")){
					    contents+="{'ac_id':'"+jrty.getAcId()+"','reason':'序号格式有误'},";
						continue;
					}
					if(jrty.getPrdtName()!=null&&!jrty.getPrdtName().equals("")&&!jrty.getPrdtName().equalsIgnoreCase("null")){
						if(jrty.getPrdtName()==null||jrty.getPrdtName().indexOf("[")==-1){
							contents+="{'ac_id':'"+jrty.getAcId()+"','reason':'产品名称格式有误'},";
							break;
						}else{
							int start1 = jrty.getPrdtName().indexOf("[");
							int end2 = jrty.getPrdtName().lastIndexOf("]");
							int end1 = jrty.getPrdtName().indexOf("]", start1);
							int start2 = end1+1;
						    jrty.setPrdtNo(jrty.getPrdtName().substring(start1+1, end1));
						    jrty.setKmh(jrty.getPrdtName().substring(start2+2, end2));
						    jrty.setPrdtName(jrty.getPrdtName().substring(0, start1));
						}
					}
				if(null!=jrty.getOpnDate()&&!jrty.getOpnDate().equals("")&&jrty.getMtrDate()!=null&&!jrty.getMtrDate().equals("")){
					SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
					sdFormat.format(new Date(Integer.parseInt(jrty.getOpnDate())));
					Integer wholeDays = Integer.parseInt(jrty.getOpnDate());
					Calendar calendar = new GregorianCalendar(); // using default time-zone
					setCalendar(calendar, wholeDays, 0, false);
					jrty.setOpnDate(sdFormat.format(calendar.getTime()));
					wholeDays = Integer.parseInt(jrty.getMtrDate());
					setCalendar(calendar, wholeDays, 0, false);
					jrty.setMtrDate(sdFormat.format(calendar.getTime()));
				}else{
					contents+="{'ac_id':'"+jrty.getAcId()+"','reason':'开始日或结束日为空'},";
				    continue;
				}
//			        if(jrty.getStartDate()==null||jrty.getStartDate().equals("")||jrty.getEndDate()==null||jrty.getEndDate().equals("")){
//			        	contents+="{'ac_id':'"+jrty.getAcId()+"','reason':'开始日或结束日为空'},";
//					    continue;
//			        }else if(jrty.getStartDate().lastIndexOf("/")-jrty.getStartDate().indexOf("/")>0&&jrty.getEndDate().lastIndexOf("/")-jrty.getEndDate().indexOf("/")>0){
//			        	
//			        	String start = jrty.getStartDate().substring(5, 6).equals("1")?jrty.getStartDate().replace("/", ""):(jrty.getStartDate().substring(0, 4)+"0"+jrty.getStartDate().substring(5, 6)+jrty.getStartDate().substring(7,9));
//			        	String end = jrty.getEndDate().substring(5, 6).equals("1")?jrty.getEndDate().replace("/", ""):jrty.getEndDate().substring(0, 4)+"0"+jrty.getEndDate().substring(5, 6)+jrty.getStartDate().substring(7,9);
//			        	if(getDateFstr(start)&&getDateFstr(end)){
//			        		jrty.setStartDate(start);
//			        		jrty.setEndDate(end);
//			        	}else{
//			        		contents+="{'ac_id':'"+jrty.getAcId()+"','reason':'开始日或结束日格式有误'},";
//						    continue;
//			        	}
//			        }else{
//			        		contents+="{'ac_id':'"+jrty.getAcId()+"','reason':'开始日或结束日格式有误'},";
//						    continue;
//			        }
			        	
//					if(!getDateFstr(jrty.getStartDate())||!getDateFstr(jrty.getEndDate())){
//						contents+="{'ac_id':'"+jrty.getAcId()+"','reason':'开始日或到期日格式有误'},";
//					    continue;
//					}
				jrty.setRate(jrty.getRate()/100);
				jrty.setAcId(jrty.getAcId());
					df.update(jrty);
					successNum++;
				} else {
					//break;
				}
			}
		} catch (Exception e) {
			if(contents.lastIndexOf(",")!=-1){
				contents = contents.substring(0, contents.length()-1)+"],";
			}
			msg = "{'total:'"+total+",'successNum':" + successNum +",'failNum':"+(total-successNum)+","+contents+ ",'success':false}";
			pw.write(msg);/*javascript:artDialog*/;
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
		String sqlfind = "select nvl(max(int(substr(ac_id,1,locate('_',ac_id)-1))),0)+1 from ftp.Jr_ty";
		List list = df.query1(sqlfind, null);
		String acId = list.get(0).toString();
		this.getResponse().getWriter().print(acId);
		return NONE;
	}
	/**
	 * 保存手工录入的数据
	 * 
	 * @return
	 */
	public String saveTy() throws Exception{
		String[] params=po.getPrdtNo().split("@@");
		po.setPrdtNo(params[0]);
		po.setKmh(params[1]);
		po.setBrName(po.getBrName().substring(0,	po.getBrName().lastIndexOf("[")));
		int days=this.getDays(po.getOpnDate(), po.getMtrDate());
		po.setDays(Double.valueOf(String.valueOf(days)));
		po.setRate(po.getRate()/100);
		po.setAcId(po.getAcId());
		df.insert(po);
		return null;
	}
	/**
	 * 检验重复
	 * @return
	 * @throws Exception
	 */
	public String checkId() throws Exception{
		String hqlfind = "from JrBzj where acId='" + po.getAcId()+ "'";
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
   


	public PageUtil getJrTyUtil() {
		return JrTyUtil;
	}

	public void setJrTyUtil(PageUtil jrTyUtil) {
		JrTyUtil = jrTyUtil;
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


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRowsCount() {
		return rowsCount;
	}

	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	public File getClientExcelFile() {
		return ClientExcelFile;
	}

	public void setClientExcelFile(File clientExcelFile) {
		ClientExcelFile = clientExcelFile;
	}


	public JrBzj getModel() {
		if(po==null){
			po=new JrBzj();
		}
		return po;
	}

}
