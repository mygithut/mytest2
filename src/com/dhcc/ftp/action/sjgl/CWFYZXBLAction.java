package com.dhcc.ftp.action.sjgl;
/**
 * @desc:财务费用杂项补录Action
 * @author :孙红玉
 * @date ：2012-11-05
 */
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.action.BoBuilder;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.FtpMidItem;
import com.dhcc.ftp.util.CommonFunctions;

public class CWFYZXBLAction extends BoBuilder {

    private String itemNo;
    private String itemAmount;
    private List<FtpMidItem> list = null;
    HttpServletRequest request = getRequest();
	DaoFactory df = new DaoFactory();
	
    
    public String execute() throws Exception {
		return super.execute();
	}
    public String List() throws Exception {
    	String hsql = "select t.item_no, t.item_name, t.item_amount, t.item_date from ftp.Ftp_Mid_Item t " +
    			"left join ftp.Ftp_Item_To_Acc f on t.item_No = f.item_No where f.to_Acc= '补录' " +
    			"order by t.item_No";
    	list = df.query1(hsql, null);
		request.setAttribute("list", list);
		return "List";
    }
    public String Query() throws Exception {
		String hsql = "from FtpMidItem where itemNo = '"+itemNo+"'";
		FtpMidItem ftpMidItem = (FtpMidItem)df.getBean(hsql, null);
		request.setAttribute("ftpMidItem", ftpMidItem);
		return "Edit";
    }
    public String save() throws Exception {
    	Double amount = (itemAmount.equals("") || itemAmount == null) ? 0.0 : Double.valueOf(itemAmount)/100;
    	String sql = "update FtpMidItem set itemAmount = "+amount+"" +
    			", itemDate = '"+CommonFunctions.GetDBSysDate()+"' where itemNo = '"+itemNo+"'";
		df.update(sql, null);
    	return null;
    }
	public List<FtpMidItem> getList() {
		return list;
	}
	public void setList(List<FtpMidItem> list) {
		this.list = list;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getItemAmount() {
		return itemAmount;
	}
	public void setItemAmount(String itemAmount) {
		this.itemAmount = itemAmount;
	}

}
