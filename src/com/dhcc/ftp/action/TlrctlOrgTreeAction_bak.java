package com.dhcc.ftp.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.TelMst;
import com.opensymphony.xwork2.ActionSupport;

public class TlrctlOrgTreeAction_bak extends ActionSupport {
	private static final Logger logger=Logger.getLogger(DaoFactory.class);
	@Override
	public String execute() throws Exception {
		
		TelMst telMst=(TelMst)ServletActionContext.getRequest().getSession().getAttribute("userBean");
		
		String orgleve = telMst.getBrMst().getManageLvl();
		System.out.println("orgleve:"+orgleve);
		String orgno = telMst.getBrMst().getBrNo();
		System.out.println("orgno:"+orgno);
		String brname = telMst.getBrMst().getBrName();
		System.out.println("brname:"+brname);
		String id = ServletActionContext.getRequest().getParameter("node");
		// id�����Ϊ��"brno_ficode"������������Ҫȡ�����ţ�����ȥ��id����"_"+ficode by wangshanfang
		// 20081226
		System.out.println("id0:"+id);
		if (-1 != id.indexOf("_")) {
			id = id.split("_")[0];
		}
		logger.info("id="+id);
		String inserTrctlFlag = ServletActionContext.getRequest().getParameter("insertTlrctl");
		System.out.println(inserTrctlFlag);
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (id.equals("-1")) {
			// �����һ��Ŀ¼
			sb.append("{id:'" + orgno + "_" + orgleve + "',leaf:false,text:'"
			    + brname + "[" + orgno + "]',leaf:");
			sb.append(orgleve.equals("0") ? "true" : "false");
			sb.append("}]");
		} else {
			List<BrMst> databean = new DaoFactory().query("from BrMst b where b.superBrNo='"+id+"' order by b.brNo " ,null);
			int size = databean.size();
			int i = 0;
			for (BrMst one : databean) {
				i++;
				/* ����������idΪ:brno_ficode */
				sb.append("{id:'" + one.getBrNo() + "_" + one.getManageLvl() + "',text:'"
				    + one.getBrName() + "[" + one.getBrNo() + "]',leaf:");
				// �ж��Ƿ���Ҷ�ӽڵ�,�жϸû����Ƿ����¼����������û�У���ΪҶ�ӽڵ�
				List<BrMst> list = new DaoFactory().query("from BrMst b where b.superBrNo='"+one.getBrNo()+"' order by b.brNo " ,null);
				if (list == null || list.size() == 0) {
					sb.append("true");
				} else {
					sb.append("false");
				}
				sb.append("}");
				sb.append((i == size) ? "" : ",");
				one = null;
			}
			sb.append("]");
			databean = null;
			//logger.info("@@@"+sb.toString());
		}
		try {
			// System.out.println("-write-");
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");

			PrintWriter pw = ServletActionContext.getResponse().getWriter();
			logger.info(sb.toString());
			pw.write(sb.toString());
			pw.flush();
			pw.close();
			// response.getWriter().write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
