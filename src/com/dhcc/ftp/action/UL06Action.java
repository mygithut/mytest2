package com.dhcc.ftp.action;
/**
 * 期限匹配
 * @author Sunhongyu
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.Ftp1PrepayAdjust;
import com.dhcc.ftp.entity.FtpBusinessInfo;
import com.dhcc.ftp.entity.FtpPublicRate;
import com.dhcc.ftp.entity.FtpQxppResult;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;
import com.dhcc.ftp.util.SCYTCZlineF;

public class UL06Action  extends BoBuilder {

	private String brNo;
	private String curNo;
	private String businessNo;
	private String prdtNo;
	private String opnDate1;
	private String opnDate2;
	private String mtrDate1;
	private String mtrDate2;
	private String checkAll;
	private String currentPage;
	private String pageSize;
	private String no;
	private String isQuery;
	private String date;
	private int page = 1;
	private PageUtil UL06Util = null;
	private PageUtil ULFtp06Util = null;
	private PageUtil ULFtpSuccess06Util = null;
	private PageUtil ULFtpError06Util = null;
	HttpServletRequest request = getRequest();
	private String savePath = null;
	private String manageLvl;
	DaoFactory df = new DaoFactory();

	public String execute() throws Exception {
		return super.execute();
	}
	
	public String List() throws Exception {
		UL06Util = uL06BO.dofind(request, page, checkAll, isQuery, brNo,prdtNo, curNo, businessNo, opnDate1, opnDate2, mtrDate1, mtrDate2);
		System.out.println("查询获取业务列表结束！");
		request.setAttribute("checkAll", checkAll);
		request.setAttribute("UL06Util", UL06Util);
		request.setAttribute("opnDate2", opnDate2);
		return "list";
	}
	public String ftp_compute() throws Exception {
		//list javabean
		List<FtpBusinessInfo> ftpBusinessInfoList = (List<FtpBusinessInfo>)request.getSession().getAttribute("ftpBusinessInfoList");
		
		Map<String,SCYTCZlineF> curvesF_map=FtpUtil.getMap_AllCurves_N(date, brNo, manageLvl);//date为ul06List.jsp页面里获取的当前数据库系统日期
		Map<String,String[]> ftpMethodMap = FtpUtil.getMap_FTPMethod_xls(brNo, manageLvl);
		List<FtpBusinessInfo> ftped_data_successList=new ArrayList<FtpBusinessInfo>();//定价成功的业务记录list
		List<FtpBusinessInfo> ftped_data_errorList=new ArrayList<FtpBusinessInfo>();//定价失败的业务记录list
		long sys_date_long=CommonFunctions.GetDBSysDate(); 
		
		//存贷款产品的各项策略调整
		Map<Integer, Double> ckzbjAdjustMap = uL06BO.getCkzbjAdjustValue();//存款准备金调整
		Map<Integer, Double> ldxAdjustMap = uL06BO.getLdxAdjustValue();//流动性调整
		Map<String, Double> irAdjustMap = uL06BO.getIrAdjustValue();//信用风险调整
		List<Ftp1PrepayAdjust> prepayList = uL06BO.getPrepayAdjustValue();//提前还款/提前支取调整
		Map<String, Double> clAdjustMap = uL06BO.getClAdjustValueByxls(brNo,manageLvl);//策略调整
		double[][] dkAdjustArr = uL06BO.getDkAdjustValue();//贷款调整比率
		Double[][] publicRate = FtpUtil.getFtpPublicRate();//央行贷款利率
		//double[][] dkSfblAdjustArr=uL06BO.getDkSfblAdjustValue();//贷款上浮比例调整比率
		
		int ftp_total_num=0;//被选定定价的总笔数
		int ftp_success_num=0;//定价成功总笔数
		int ftp_fault_num=0;//定价失败总笔数
		try{
			List<FtpBusinessInfo> selectted_dataList=new ArrayList<FtpBusinessInfo>();
			if (checkAll.equals("false")) {
				//选中当前页数据
				System.out.println("no:"+no);
				System.out.println("currentPage:"+currentPage);//当前页码
				System.out.println("pageSize:"+pageSize);//每页显示多少
				String[] noStr = no.split(",");//选中的行在当前页list的位置0-99
				int currentPage_int=Integer.valueOf(currentPage);
				int pageSize_int=Integer.valueOf(pageSize);
				int start=(currentPage_int-1)*pageSize_int;
				int end=currentPage_int*pageSize_int-1;
				List<FtpBusinessInfo> dataList=ftpBusinessInfoList.subList(start, Math.min(end+1, ftpBusinessInfoList.size()-(currentPage_int-1)*100));//所选页的全部笔业务依序list				
				for(int i=0;i<noStr.length;i++){
					selectted_dataList.add(dataList.get(Integer.valueOf(noStr[i])));
				}
			}else{//所有页所有有行全选
				selectted_dataList=ftpBusinessInfoList;
			}
			
			//对所选的业务行 依次定价 ，并将定价结果存入ftped_data_successList 与  ftped_data_errorList
			for(int i=0;i<selectted_dataList.size();i++){
				FtpBusinessInfo ftp_business_info=selectted_dataList.get(i);
				String[] ftp_methodComb=ftpMethodMap.get(ftp_business_info.getPrdtNo());
				if(ftp_methodComb==null){//没在配置表中找到对应定价方法记录行，则不对该笔业务定价
					ftp_business_info.setMethodName("还未配置");
					ftped_data_errorList.add(ftp_business_info);
					ftp_fault_num++;
					continue;
				}
				
				
				double adjust_rate=Double.valueOf(ftp_methodComb[2]);//调整利率
				String method_no=ftp_methodComb[0];//具体定价方法编号
				int term=(ftp_business_info.getTerm()==null || ftp_business_info.getTerm().equals(""))?0:Integer.valueOf(ftp_business_info.getTerm());
				if(method_no.equals("06")){//只有‘利率代码差额法06’才使用‘参考期限’ 
					term=Integer.valueOf(ftp_methodComb[1]);//参考期限
				}
				String curve_no=ftp_methodComb[3];//使用的收益率曲线编号
				
				String curve_date="";//选用收益率曲线的日期条件,实现‘曲线选用严格到每日’新加。
				// 如果是活期存款YW201、现金YW107、其他资产YW108、其他负债YW209，则其选用收益率曲线的日期条件为数据库系统日期
				if(("YW201").equals(ftp_business_info.getBusinessNo()) || ("YW107").equals(ftp_business_info.getBusinessNo())){
					curve_date=String.valueOf(sys_date_long);
				
				//对于数据库系统日期为每年1月10号，且对‘中长期贷款业务’实行重定价 时，则选：本年1月1日 (与 其开户日  两者中大的那个)
				}else if(sys_date_long%10000==110 && ftp_business_info.getPrdtNo().startsWith("P1") && (ftp_business_info.getPrdtName().indexOf("3年")>=0  || ftp_business_info.getPrdtName().indexOf("5年")>=0 )){
					curve_date=String.valueOf(Math.max(sys_date_long/10000+101, Long.valueOf(ftp_business_info.getOpnDate()==null?"19000101":ftp_business_info.getOpnDate())));
				}else{//其他业务 ,则其选用收益率曲线的日期条件为该业务发生日期<开户日期>---若发生日为空，则默认取数据库系统日期
					curve_date=(ftp_business_info.getOpnDate()==null || ftp_business_info.getOpnDate().equals(""))?String.valueOf(sys_date_long):ftp_business_info.getOpnDate();
				}
				
				if(!"无".equals(curve_no) && curvesF_map.get(curve_date+"-"+curve_no)==null){
					//ftp_business_info.setCurveName("还未发布");
					ftped_data_errorList.add(ftp_business_info);
					ftp_fault_num++;
					continue;
				}
				double appoint_rate=Double.valueOf(ftp_methodComb[4]);//指定利率
				double appoint_delta_rate=Double.valueOf(ftp_methodComb[5]);//固定利差
				
				double ftp_price=-1;
				if(method_no.equals("01")){//## 原始期限匹配法
					ftp_price=FtpUtil.getFTPPrice_ysqxppf(term, curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("02")){//## 指定利率法
					//ftp_price=FtpUtil.getFTPPrice_zdllf(term, curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
					ftp_price=appoint_rate+adjust_rate;
				}else if(method_no.equals("03")){//## 重定价期限匹配法---暂时当做重定价原始期限匹配法<定价触发条件在查询列表控制(曲线选用日期在前面单独控制)，然后计算算法就和原始期限匹配法一样了>
					ftp_price=FtpUtil.getFTPPrice_ysqxppf(term, curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("04")){//## 现金流法,还款周期
					ftp_price=FtpUtil.getFTPPrice_xjlf(term,30,curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("05")){//## 久期法
				/*	if(ftp_business_info.getPrdtNo().substring(0, 4).equals("P109")){//按揭贷款，还款周期
						ftp_price=FtpUtil.getFTPPrice_jqf(term,30,0,curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
					}else{//固定资产,默认设置残值率为0.4，折旧周期
						ftp_price=FtpUtil.getFTPPrice_jqf(term,365,0.4,curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
					}*/
					ftp_price=FtpUtil.getFTPPrice_jqf(term,30,0,curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("06")){//## 利率代码差额法
					ftp_price=FtpUtil.getFTPPrice_lldmcef(term, curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("07")){//## 加权利率法
					ftp_price=FtpUtil.getFTPPrice_jqllf(curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("08")){//## 固定利差法
					if(ftp_business_info.getPrdtNo().startsWith("P1")){//资产产品：FTP价格=客户端收益-固定利差值
						ftp_price=Double.parseDouble(ftp_business_info.getRate())-appoint_delta_rate+adjust_rate;
					}else{//负债产品：FTP价格=客户端成本+固定利差值
						ftp_price=Double.parseDouble(ftp_business_info.getRate())+appoint_delta_rate+adjust_rate;
					}
					//ftp_price=Double.parseDouble(ftp_business_info.getRate())+appoint_delta_rate+adjust_rate;
				}else{
					ftp_business_info.setMethodName("方法"+method_no+"配置错误");
					ftped_data_errorList.add(ftp_business_info);
					ftp_fault_num++;
					continue;
				}
				//FTP调整
				double adjustValue = 0;
				//是否金额调整=‘是’，且是贷款
				if(ftp_methodComb[9].equals("1")){
					double amt = (ftp_business_info.getAmt()==null||ftp_business_info.getAmt().equals(""))?Double.NaN:Double.valueOf(ftp_business_info.getAmt());
					double rate = (ftp_business_info.getRate()==null||ftp_business_info.getRate().equals(""))?Double.NaN:Double.valueOf(ftp_business_info.getRate());
					adjustValue = FtpUtil.getDkAmtAdjust(ftp_business_info.getPrdtNo(), term, amt, dkAdjustArr, publicRate,rate);
				}else {//否则，都进行下面的调整
					//使用存贷款收益率曲线的产品
					if(curve_no.equals("0100")) {
						adjustValue = FtpUtil.getCdkFtpAdjustValue(ftp_business_info.getBusinessNo(), term, ckzbjAdjustMap, ldxAdjustMap, irAdjustMap, prepayList);
					}else if(curve_no.startsWith("02")) {//使用市场收益率曲线 +流动性风险加点+敞口占用加点
						adjustValue += Double.valueOf(ftp_methodComb[7])+Double.valueOf(ftp_methodComb[8]);
					}else{//其他暂时什么也不做
						
					}
					//策略调整，根据产品获取对应的数据
					adjustValue += clAdjustMap.get(ftp_business_info.getPrdtNo()) == null ? 0 : clAdjustMap.get(ftp_business_info.getPrdtNo());
				}
				ftp_price += adjustValue;
				ftp_business_info.setFtpPrice(ftp_price);
				ftp_business_info.setMethodNo(method_no);
				ftp_business_info.setMethodName(FtpUtil.getMethodName_byMethodNo(method_no));////
				ftp_business_info.setCurveNo(curve_no);
				ftp_business_info.setWrkDate(date);
				if(Double.isNaN(ftp_price)){
					ftped_data_errorList.add(ftp_business_info);
					ftp_fault_num++;
				}else{
					ftped_data_successList.add(ftp_business_info);
					ftp_success_num++;
				}
			}														
		}catch(Exception e){
			e.printStackTrace();
		}
		ftp_total_num=ftp_fault_num+ftp_success_num;
		String ftpResultDescribe="定价完成！总共选中"+ftp_total_num+"笔；其中成功定价"+ftp_success_num+"笔，失败"+ftp_fault_num+"笔！";
		System.out.println(ftpResultDescribe);
		request.getSession().setAttribute("ftped_data_successList", ftped_data_successList);
		request.getSession().setAttribute("ftped_data_errorList", ftped_data_errorList);
		ULFtpSuccess06Util = uL06BO.doFY(request, page, date, ftped_data_successList, "UL06_ftpedSuccessListFy");
		ULFtpError06Util = uL06BO.doFY(request, page, date, ftped_data_errorList, "UL06_ftpedErrorListFy");
	    request.getSession().setAttribute("ULFtpSuccess06Util", ULFtpSuccess06Util);
	    request.getSession().setAttribute("ULFtpError06Util", ULFtpError06Util);
		request.getSession().setAttribute("date", date);
		request.setAttribute("ftpResultDescribe", ftpResultDescribe);
		return "compute_result";
	 }
	/**
	 * 定价成功列表的分页list
	 * @return
	 * @throws Exception
	 */
	 public String ftpedSuccessListFy() throws Exception {
		 List<FtpBusinessInfo> ftped_data_successList = (List<FtpBusinessInfo>)request.getSession().getAttribute("ftped_data_successList");
		 ULFtpSuccess06Util = uL06BO.doFY(request, page, date, ftped_data_successList, "UL06_ftpedSuccessListFy");
		 request.getSession().setAttribute("ULFtpSuccess06Util", ULFtpSuccess06Util);
		 return "compute_success_list";
	 }
	 /**
	  * 定价失败列表的分页list
      * @return
	  * @throws Exception
	  */
	 public String ftpedErrorListFy() throws Exception {
		 List<FtpBusinessInfo> ftped_data_errorList = (List<FtpBusinessInfo>)request.getSession().getAttribute("ftped_data_errorList");
		 ULFtpError06Util = uL06BO.doFY(request, page, date, ftped_data_errorList, "UL06_ftpedErrorListFy");
		 request.getSession().setAttribute("ULFtpError06Util", ULFtpError06Util);
		 return "compute_error_list";
	 }
	 /**
	  * 定价结果保存
	  * @return
	  * @throws Exception
	  */
	 public String Save() {
		 try{
			 //ftped_dataList获取的已经是全部成功定价的
			 List<FtpBusinessInfo> ftped_dataList = (List<FtpBusinessInfo>)request.getSession().getAttribute("ftped_data_successList");
			 date = (String)request.getSession().getAttribute("date");
			 int ftpSave_total_num=ftped_dataList.size();//被选定保存的总笔数
			 int ftpSave_success_num=0;//成功保存总笔数
			 //int ftpSave_fault_num=0;//因定价失败而未保存的总笔数
			 String STime=CommonFunctions.GetCurrentTime();
			 //计算机时间
			 String wrkTime = CommonFunctions.GetCurrentDateInLong() + CommonFunctions.getCurrentTime();
			 String wrkSysDate=date;
			 List<FtpQxppResult> ftpQxppResult_saveList=new ArrayList<FtpQxppResult>();////事务批量保存新加
			 String[] delHsqlS=new String[ftped_dataList.size()];//事务批量删除新加
			 //String acIdSql="acId in(";//删除花时太长解决--此种方法执行不下去
			 String isAll_price="0";//是否是该机构‘全部业务’‘所有页全选’定价-----already修改为前台传递
			 if(businessNo.equals("")&&checkAll.equals("true")) {//‘业务类型’为:请选择(全部业务)，‘所有页全选’为选中
				 isAll_price = "1";
			 }
			 String br_lvl=LrmUtil.getBr_lvlInfo(brNo)[0];//机构级别；县联社级别为2
			 if(isAll_price.equals("1") && br_lvl.equals("2")){//‘县联社级别’且‘全定价’，则直接删除该县联社下的当前系统日期的所有已有定价结果记录
				 System.out.println("isAll_price="+isAll_price);
				 String delHsql = "delete from FtpQxppResult where wrkSysDate = '"+wrkSysDate+"'";
				 df.delete(delHsql, null); 
			 }
			 
			 for (int i = 0; i < ftped_dataList.size(); i++) {
				 FtpBusinessInfo ftp_business_info = ftped_dataList.get(i);
				 //未成功定价的不保存
	    	     /*if (ftp_business_info.getFtpPrice() == null || Double.isNaN(ftp_business_info.getFtpPrice())) {
	    	    	 ftpSave_fault_num++;
	        	     continue;
				 }*/
	    	     FtpQxppResult ftpQxppResult = new FtpQxppResult();
				 int wrkNum = 0;
				 //同一个月只能定价一次，根据ac_id、定价日期wrkSysDate+ --<机构、科目号>(后两者为活期存款打包定价新增)--查询是否已经定价，如果有，则先删除，后添加
				 /*String hsql = "from FtpQxppResult where acId = '"+ftp_business_info.getAcId()+"' and wrkSysDate = '"+wrkSysDate+"'";
				 System.out.println("hsql:"+hsql);
				 FtpQxppResult ftpQxppResultOld = (FtpQxppResult)df.getBean(hsql, null);
				 if(ftpQxppResultOld != null) {
					 String delHsql = "delete from FtpQxppResult where acId = '"+ftp_business_info.getAcId()+"' and wrkSysDate = '"+wrkSysDate+"'";
					 df.delete(delHsql, null); 
				 }*/
				 //不用查，直接删除本周期内的该笔以前定价<不管有没有>----同一个周期只能定价一次
				 if(!isAll_price.equals("1") || !br_lvl.equals("2")){//不是县联社全部定价，才按ac_id逐个构建删除sql语句
					 String delHsql = "delete from FtpQxppResult where acId = '"+ftp_business_info.getAcId()+"' and wrkSysDate = '"+wrkSysDate+"'";
					 delHsqlS[i]=delHsql;////事务批量删除新加
				 }
				 
				 //acIdSql+="'"+ftp_business_info.getAcId()+"',";
				 ////df.delete(delHsql, null); 
				 
				 //根据ac_id从ftp_qxpp_result获取最近的Wrk_time的定价次数。----湖南农信：暂时全为1次定价
				 /*String sql = "select max(wrk_num) from ftp_qxpp_result where ac_id = '"+ftp_business_info.getAcId()+"'";
			     List wrkNumList = df.query1(sql, null);
			     if (wrkNumList != null && wrkNumList.size() > 0 && wrkNumList.get(0)!=null) {
			    	 wrkNum = Integer.valueOf(wrkNumList.get(0).toString());
			     }*/
			     ftpQxppResult.setAcId(ftp_business_info.getAcId());
			     //ftpQxppResult.setAcSqen(ftp_business_info.getAcSeqn());
			     ftpQxppResult.setWrkNum(wrkNum + 1);
			     ftpQxppResult.setAcId(ftp_business_info.getAcId());
			     ftpQxppResult.setBrNo(ftp_business_info.getBrNo());
			     ftpQxppResult.setCurNo(ftp_business_info.getCurNo());
			     ftpQxppResult.setBusinessNo(ftp_business_info.getBusinessNo());
			     ftpQxppResult.setBusinessName(ftp_business_info.getBusinessName());
			     ftpQxppResult.setProductNo(ftp_business_info.getPrdtNo());
			     ftpQxppResult.setProductName(ftp_business_info.getPrdtName());
			     ftpQxppResult.setFtpPrice(ftp_business_info.getFtpPrice());
			     ftpQxppResult.setMethodNo(ftp_business_info.getMethodNo());
			     ftpQxppResult.setCurveNo(ftp_business_info.getCurveNo());
			     ftpQxppResult.setTelNo(ftp_business_info.getTel());
			     ftpQxppResult.setFtpTelNo(((TelMst) request.getSession().getAttribute("userBean")).getTelNo());
				 ftpQxppResult.setWrkTime(wrkTime);
				 ftpQxppResult.setWrkSysDate(wrkSysDate);
				 //湖南 新加
				 ftpQxppResult.setIsZq(ftp_business_info.getIsZq());
				 ftpQxppResult.setCusName(ftp_business_info.getCustomName());
				 ftpQxppResult.setOpnDate(ftp_business_info.getOpnDate());
				 ftpQxppResult.setAmt(ftp_business_info.getAmt()==null?null:Double.valueOf(ftp_business_info.getAmt()));
				 ftpQxppResult.setBal(Double.valueOf(ftp_business_info.getBal()));
				 ftpQxppResult.setRate(Double.valueOf(ftp_business_info.getRate()));
				 ftpQxppResult.setTerm(ftp_business_info.getTerm()==null?null:Integer.valueOf(ftp_business_info.getTerm()));
				 ftpQxppResult.setMtrDate(ftp_business_info.getMtrDate());
				 
				 ftpQxppResult.setKmh(ftp_business_info.getKmh());
				 
				 ftpQxppResult.setKhjl(ftp_business_info.getKhjl());
				 ftpQxppResult.setSjsflx(ftp_business_info.getSjsflx()==null?null:Double.valueOf(ftp_business_info.getSjsflx()));
				 
				 ftpQxppResult.setCustNo(ftp_business_info.getCustNo());//客户编号
				 ftpQxppResult.setZhzt(ftp_business_info.getZhzt());//账户状态
				 ftpQxppResult.setFivSts(ftp_business_info.getFivSts());//'五级分类状态',蚌埠实施新加
				 ftpQxppResult.setZqrq(ftp_business_info.getZqrq());//展期日期
				 ftpQxppResult.setZqAmt((ftp_business_info.getZqAmt()==null||ftp_business_info.getZqAmt().equals(""))?null:Double.valueOf(ftp_business_info.getZqAmt()));//展期金额
				 ftpQxppResult.setZqMtrDate(ftp_business_info.getZqMtrDate());//展期到期日期
				 
				 ftpQxppResult.setResultId(Long.parseLong(IDUtil.getInstanse().getUID()));//不再使用hibernate sequence；改用UID--提高效率
				 
				 ////df.insert(ftpQxppResult);
				 ftpQxppResult_saveList.add(ftpQxppResult);////批量保存新加

				 ftpSave_success_num++;
			}
			 if(!isAll_price.equals("1") || !br_lvl.equals("2")){//不是县联社全部定价，才按ac_id逐个删除
				 df.delete_s(delHsqlS, null);
			 }
			
			//acIdSql=acIdSql.substring(0,acIdSql.length()-1)+")";
			//String delSql="delete from FtpQxppResult where "+acIdSql+" and wrkSysDate = '"+wrkSysDate+"'";
			//System.out.println("delSql的字符长度="+delSql.length());
			//df.delete(delSql, null);//执行不下去
			
		    boolean save_success=df.insert_s(ftpQxppResult_saveList);
			//String ftpSaveDescribe="总共选中"+ftpSave_total_num+"笔；其中成功保存"+ftpSave_success_num+"笔，因定价失败而未保存"+ftpSave_fault_num+"笔！";
		    String ftpSaveDescribe="保存成功!共"+ftpSave_success_num+"笔!";
		    if(!save_success){
		    	ftpSaveDescribe="保存出错! 请联系系统技术管理员!";
		    }
			String ETime=CommonFunctions.GetCurrentTime();
			int costTime=CommonFunctions.GetCostTimeInSecond(STime, ETime);
			int CostFen=costTime/60;
			int CostMiao=costTime%60;
			ftpSaveDescribe+="耗时"+CostFen+"分"+CostMiao+"秒";
			System.out.println(ftpSaveDescribe);
			
			HttpServletResponse res = getResponse();
			res.setContentType("text/plain;charset=UTF-8");
			res.getWriter().print(ftpSaveDescribe);
		    return null;
		 }catch(Exception e){
			 e.printStackTrace();
			 return null;
		 }
		 
	 
	 }

	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getCurNo() {
		return curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getOpnDate1() {
		return opnDate1;
	}

	public void setOpnDate1(String opnDate1) {
		this.opnDate1 = opnDate1;
	}

	public String getOpnDate2() {
		return opnDate2;
	}

	public void setOpnDate2(String opnDate2) {
		this.opnDate2 = opnDate2;
	}

	public String getMtrDate1() {
		return mtrDate1;
	}

	public void setMtrDate1(String mtrDate1) {
		this.mtrDate1 = mtrDate1;
	}

	public String getMtrDate2() {
		return mtrDate2;
	}

	public void setMtrDate2(String mtrDate2) {
		this.mtrDate2 = mtrDate2;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PageUtil getUL06Util() {
		return UL06Util;
	}

	public void setUL06Util(PageUtil uL06Util) {
		UL06Util = uL06Util;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getCheckAll() {
		return checkAll;
	}

	public void setCheckAll(String checkAll) {
		this.checkAll = checkAll;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public PageUtil getULFtpSuccess06Util() {
		return ULFtpSuccess06Util;
	}

	public void setULFtpSuccess06Util(PageUtil uLFtpSuccess06Util) {
		ULFtpSuccess06Util = uLFtpSuccess06Util;
	}

	public PageUtil getULFtpError06Util() {
		return ULFtpError06Util;
	}

	public void setULFtpError06Util(PageUtil uLFtpError06Util) {
		ULFtpError06Util = uLFtpError06Util;
	}

	public String getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}

	public PageUtil getULFtp06Util() {
		return ULFtp06Util;
	}

	public void setULFtp06Util(PageUtil uLFtp06Util) {
		ULFtp06Util = uLFtp06Util;
	}

	public String getManageLvl() {
		return manageLvl;
	}

	public void setManageLvl(String manageLvl) {
		this.manageLvl = manageLvl;
	}

	public String getPrdtNo() {
		return prdtNo;
	}

	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}

}
