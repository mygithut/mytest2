package com.dhcc.ftp.action;
/**
 * 期限匹配-历史价格查询
 * @author Sunhongyu
 * @date 20120426
 */
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.FtpQxppResult;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.PageUtil;

public class UL07Action  extends BoBuilder {

	private String brNo;
	private String curNo;
	private String businessNo;
	private String prdtNo;
	private String wrkTime1;
	private String wrkTime2;
	private String currentPage;
	private String pageSize;
	private String no;
	private String isQuery;
	private int page = 1;
	private int rowsCount = -1;
	private PageUtil UL07Util = null;
	HttpServletRequest request = getRequest();
	DaoFactory df = new DaoFactory();

	public String execute() throws Exception {
		return super.execute();
	}
	
	
	public String List() throws Exception {
		UL07Util = uL07BO.dofind(request, page, rowsCount, brNo, curNo, businessNo, prdtNo, wrkTime1, wrkTime2);
		request.setAttribute("UL07Util", UL07Util);
		return "list";
	}

	public String ftpHistory() throws Exception {
		System.out.println("no"+no);
		int m = 0;
		//将x、y赋值到curve_x和curve_y中
		System.out.println("no:"+no);
		System.out.println("currentPage:"+currentPage);//当前页码
		System.out.println("pageSize:"+pageSize);//每页显示多少
		String[] noStr = no.split(",");//选中的行在当前页list的位置0-99
		int currentPage_int=Integer.valueOf(currentPage);
		int pageSize_int=Integer.valueOf(pageSize);
		int start=(currentPage_int-1)*pageSize_int;
		int end=currentPage_int*pageSize_int-1;
		List<FtpQxppResult> ftpQxppResultList = (List<FtpQxppResult>)request.getSession().getAttribute("ftpQxppResultList");
		List<FtpQxppResult> dataList=ftpQxppResultList.subList(start, end+1);//所选页的全部笔业务依序list
		List<FtpQxppResult> selectted_dataList=new ArrayList<FtpQxppResult>();
		for(int i=0;i<noStr.length;i++){
			selectted_dataList.add(dataList.get(Integer.valueOf(noStr[i])));
		}
		String[][] curve_x = new String[noStr.length][];
		String[][] curve_y = new String[noStr.length][];
		String[] curve_name = new String[noStr.length];
		for (int i = 0; i < noStr.length; i++) {
			FtpQxppResult ftpQxppResult=selectted_dataList.get(i);
			curve_name[i] = "业务账号:"+ftpQxppResult.getAcId()+" 业务类型:"+ftpQxppResult.getBusinessName()+" 产品名称:"+ftpQxppResult.getProductName();
			String hsql = "from FtpQxppResult where acId = '"+ftpQxppResult.getAcId()+"' order by wrkNum,wrkTime asc";
			List<FtpQxppResult> ftpQxppList = (List<FtpQxppResult>)df.query(hsql, null);
			curve_x[i] = new String[ftpQxppList.size()];
			curve_y[i] = new String[ftpQxppList.size()];
			for (int j = 0; j < ftpQxppList.size(); j++) {
				curve_x[i][j] = ftpQxppList.get(j).getWrkTime();
				curve_y[i][j] = String.valueOf(ftpQxppList.get(j).getFtpPrice());
				m++;
			}
		}
		long[][] curve2 = new long[m][3];//第几条曲线；某点在该曲线上的第几个点；该点的定价时间
		int n = 0;
		for (int k = 0; k < curve_x.length; k++) {
			for (int j = 0; j < curve_x[k].length; j++) {
				curve2[n][0] = k;
				curve2[n][1] = j;
				curve2[n][2] = Long.valueOf(curve_x[k][j]);
				n++;
			}
		}
		FtpUtil.sort(curve2, new int[] {2}); //对二维数组按第三列升序排序
		List x = new ArrayList();//最终的x轴list
		//对数组每两行的时间进行比较，如果时间差<600(10分钟)【湖南农信 修改为 30秒】，则将前面的时间赋给后面
		int delta_time=30;//时间差<600(10分钟)【湖南农信 修改为 30秒】，则将前面的时间赋给后面
		x.add(curve2[0][2]);
		for (int o = 0; o < curve2.length - 1; o++) {
			long times = CommonFunctions.GetTimeInSecond(String.valueOf(curve2[o][2]), String.valueOf(curve2[o+1][2]));
			if (times < delta_time) {
		    	curve2[o+1][2] = curve2[o][2]; //前面的时间赋给后面
		    }else {//湖南农信修改：只要两次定价时差大于delta_time秒，就在曲线中以不同x轴点展示
		    	x.add(curve2[o+1][2]);
		    }
		}
		Double[][] y = new Double[curve_y.length][x.size()];//曲线对应的y轴二维数组
		for (int u = 0; u < x.size(); u++) {
			for (int v = 0; v < curve_y.length; v++) {//第几条线
				for (int w = 0; w < curve_y[v].length; w++) {//实际第几个点
					//循环匹配x轴的点，如果没有该点，则默认不管--保存为null
					if (FtpUtil.getValueFrom(curve2, v, w) == Long.valueOf(x.get(u).toString())){
					    y[v][u] = Double.valueOf(curve_y[v][w]);
					    break;
				    }
				}
			}
		}
		System.out.println("x------------------------------------");
		for (int i = 0; i < x.size(); i++) {    
                System.out.print(x.get(i));    
                System.out.print("\t");    
        } 
		System.out.println();    
		System.out.println("y------------------------------------");
		for (int i = 0; i < y.length; i++) {    
            for (int j = 0; j < y[i].length; j++) {    
                System.out.print(y[i][j]);    
                System.out.print("\t");    
            }    
            System.out.println();    
        }
        request.setAttribute("curve_name", curve_name);
        request.setAttribute("xPointNum", x.size());
		request.setAttribute("x", x);
		request.setAttribute("y", y);
		return "curve_history";
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


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
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

	public void setNo(String no) throws UnsupportedEncodingException {
		this.no = new String(no.getBytes("iso-8859-1"),"UTF-8");
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public PageUtil getUL07Util() {
		return UL07Util;
	}

	public void setUL07Util(PageUtil uL07Util) {
		UL07Util = uL07Util;
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

	public String getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}
	
}
