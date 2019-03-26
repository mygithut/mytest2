package com.dhcc.ftp.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.AlmCur;

public class QueryCurrencyAction extends BaseAction {
	private static final Logger logger=Logger.getLogger(DaoFactory.class);
	private static final long serialVersionUID = 1L;

	public String execute() throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		
		String  resultCurrenyListStr = null;
		//查询币种
		if(null==getRequest().getSession().getAttribute("resultCurrenyListStr")||"".equals(getRequest().getSession().getAttribute("resultCurrenyListStr")))
		{
			DaoFactory df = new DaoFactory();
			List currentList = df.query("from AlmCur", null);
			//拆分币种数据
			StringBuilder currenyListStr = new StringBuilder();
			for (Object cur : currentList) {
				AlmCur concur = (AlmCur)cur;
				currenyListStr.append(concur.getCurno()+"|"+concur.getCurname()+",");
			}
			currenyListStr = currenyListStr.deleteCharAt(currenyListStr.length()-1);
			logger.info(currenyListStr.toString());
			resultCurrenyListStr = currenyListStr.toString();
			getRequest().getSession().setAttribute("resultCurrenyListStr", resultCurrenyListStr);
		}else{
			resultCurrenyListStr = (String)getRequest().getSession().getAttribute("resultCurrenyListStr");
		}
		//查询币种结束
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(resultCurrenyListStr);
		return null;
	}

}
