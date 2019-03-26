package com.dhcc.ftp.action.xtgl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.action.BoBuilder;
import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.FtpEmpInfo;
import com.dhcc.ftp.entity.RoleMst;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;

public class TelMstAction extends BoBuilder {
	private int page = 1;
	private int pageSize = 10;
	private int rowsCount = -1;
	private String telNo;
	private String telName;
	private String brNo;
	private String remark;
	private String tlrno;
	private String passwd;
	private String name;
	private String type;
    private String rightflag;
    private String roleNo;
    private String empNo;
    
	@Override
	public String execute() throws Exception {
		return super.execute();
	}
	
	public String list () throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			brNo = brNo.trim();			
			String br_lvl=LrmUtil.getBr_lvlInfo(brNo)[0];
			String brSql=LrmUtil.getBrSql(brNo);//只包含最底层的机构
			String hsql="";
			if(Integer.valueOf(br_lvl)<3){//县联社以下级别机构
				hsql = "from TelMst t where t.brMst.brNo='"+brNo+"' or t.brMst.brNo "+brSql;
			}else if(br_lvl.equals("3")){//县联社
				hsql = "from TelMst t where t.brMst.brNo is not null";
			}		
			String pageName = "telmst_list.action?pageSize="+pageSize+"&brNo="+brNo;
			if (telNo != null && !"".equals(telNo.trim()) && !"null".equals(telNo.trim())) {
				telNo = telNo.trim();
				hsql +=" and t.telNo like '%" + telNo + "%'";
				pageName +="&telNo="+telNo+"";
			}
			if (telName != null && !"".equals(telName.trim()) && !"null".equals(telName.trim())) {
				telName = telName.trim();
				telName = CommonFunctions.Chinese_CodeChange(telName);
				hsql +=" and t.telName like '%" + telName + "%'";
				pageName +="&telName="+URLEncoder.encode(telName,"UTF-8");
			}
			
			hsql += " order by t.telNo";

			PageUtil pageUtil = queryPageBO.queryPage(hsql, pageSize, page, rowsCount, pageName);
			request.setAttribute("pageUtil", pageUtil);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("error.unexpected");
		}
		return "list";
	}
	//查找对应的操作员的详细信息
	public String search() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			TelMst telMst = telMstBO.searchTelMst(tlrno);
			request.setAttribute("telMst", telMst);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return "search";
	}

	public String noSearch() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			TelMst telMst = telMstBO.searchTelMst(tlrno);
			request.setAttribute("telMst", telMst);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return "noSearch";
	}
	
//	public String modfiyRole() throws Exception {
//		HttpServletRequest request = ServletActionContext.getRequest();
//		try {			
//			TelMst telMst = telMstBO.searchTelMst(tlrno);
//			// 次角色
//			// String lessRole = tlrctl.getEdate();
//			request.setAttribute("telMst", telMst);
//
//			Map<String,String> rolebean = new HashMap<String,String>();
//			List<RoleMst> roleList = telMstBO.findAllRole();
//			if (roleList != null && roleList.size() != 0) {
//				int len = roleList.size();
//				String[] roleNoList = new String[len];
//				String[] roleNameList = new String[len];
//				for (int i = 0; i < roleList.size(); i++) {
//					RoleMst role = roleList.get(i);
//					roleNoList[i] = role.getRoleNo();
//					roleNameList[i] = role.getRoleName();
//					rolebean.put(roleNoList[i], roleNameList[i]);
//				}
//				request.setAttribute("roleNoList", roleNoList);
//				request.setAttribute("roleNameList", roleNameList);
//			}
//			request.setAttribute("rolebean", rolebean);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//		}
//		return "modfiyRole";
//	}
	
	public String insertStart() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			/* gouqifeng 修改 把用名字like 改成 ficode ==ficode */
			List<RoleMst> roleList = telMstBO.findAllRole();
			if (roleList != null && roleList.size() != 0) {
				int len = roleList.size();
				String[] roleNoList = new String[len];
				String[] roleNameList = new String[len];
				for (int i = 0; i < len; i++) {
					RoleMst role = (RoleMst) roleList.get(i);
					roleNoList[i] = role.getRoleNo();
					roleNameList[i] = role.getRoleName();
				}
				request.setAttribute("roleNoList", roleNoList);
				request.setAttribute("roleNameList", roleNameList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return "insertStart";
	}
	
	public String insert() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse resp = ServletActionContext.getResponse();
		resp.setContentType("text/plain;charset=UTF-8");

		try {
			TelMst telMst_exist =telMstBO.searchTelMst(tlrno);
			if(telMst_exist!=null){
				resp.getWriter().print("0");
				return null;
			}
			TelMst user = (TelMst) request.getSession().getAttribute("userBean");
			TelMst telMst = new TelMst();
			telMst.setBrMst(new BrMst(brNo));
			telMst.setFtpEmpInfo(new FtpEmpInfo(empNo));
			telMst.setPasswd(passwd);
			telMst.setTelNo(tlrno);
			telMst.setTelName(name);
			//telMst.setIdNo(type);
			telMst.setRoleMst(new RoleMst(roleNo));
			telMst.setTxTelNo(user.getTelNo());
			telMst.setTxBrNo(user.getBrMst().getBrNo());
			//SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMdd");
			//Date date = new Date(0);
			//telMst.setTxDate(bartDateFormat.format(date));
			telMst.setTxDate(String.valueOf(CommonFunctions.GetCurrentDateInLong()));
			telMst.setFlag("1");
			telMstBO.save(telMst);
			resp.getWriter().print("1");
			request.setAttribute("telMst", telMst);
		} catch (Exception e) {
			resp.getWriter().print("2");
			e.printStackTrace();
		} finally {
		}
		return null;
	}
	public String update() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			TelMst telMst = telMstBO.searchTelMst(tlrno);
			telMst.setTelName(name);
			telMst.setIdNo(type);
			telMst.setBrMst(new BrMst(brNo));
			telMst.setRoleMst(new RoleMst(roleNo));
			telMst.setFtpEmpInfo(new FtpEmpInfo(empNo));
			
			SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date(0);
			telMst.setLastModDate(bartDateFormat.format(date));
			telMstBO.update(telMst);
			request.setAttribute("telMst", telMst);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
		}
		return null;
	}
	
	public String delete() throws Exception {
		try {
			telMstBO.delete(tlrno);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}
	
	public String pwdreset() {
		telMstBO.pwdreset(tlrno);
		return null;
	}
	public String pwd() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			TelMst telMst = telMstBO.searchTelMst(tlrno);
			request.setAttribute("telMst", telMst);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return "pwd";
	}
	public String pwdUpdate() {
		telMstBO.updatePWD(tlrno,passwd);
		return null;
	}
	public String updateRole() throws Exception {
		telMstBO.updateRole(tlrno, roleNo);
		return null;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTlrno() {
		return tlrno;
	}

	public void setTlrno(String tlrno) {
		this.tlrno = tlrno;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRightflag() {
		return rightflag;
	}

	public void setRightflag(String rightflag) {
		this.rightflag = rightflag;
	}

	public String getRoleNo() {
		return roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
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

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getTelName() {
		return telName;
	}

	public void setTelName(String telName) throws UnsupportedEncodingException {
		this.telName = new String(telName.getBytes("iso-8859-1"),"UTF-8");
	}

	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getEmpNo() {
		return empNo;
	}

	public void setEmpNo(String empNo) {
		this.empNo = empNo;
	}
	
	
}
