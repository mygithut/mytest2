package com.dhcc.ftp.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * @author 孙红玉
 * 
 * @date 2013-4-9 下午05:11:03
 * 
 * @Title: 检查session是否超时的过滤器
 * 
 * @company 东华软件股份公司金融风险产品部
 */
public class MainFilter implements Filter {

	public void destroy() {
	}

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		// 每次发送请求，则检查session是否为空
		String requestUrl = request.getServletPath();// 获得请求的url
		//String url = "/index.jsp-/logoutSession.jsp-/htmlframe/menu.jsp-/login.jsp-/zuayLogin_.action";//所有不需要session过滤检查的页面(也就是没有session是正常的，即session产生前的页面)
		System.out.println("requestUrl"+requestUrl);
		String url = "/index.jsp-/logoutSession.jsp-/zuayLogin_removeSessionAndLogin.action-/login.jsp-/zuayLogin_login.action";//解决session超时时，页面ie刷新报错问题
		// 每次发送请求，首先与事先定义好的url比较，如果不相同，则检查session是否为空
		if (url.indexOf(requestUrl) == -1 && session.getAttribute("userBean") == null) {
			String CONTENT_TYPE = "text/html; charset=GBK";
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();		
			//只有用showModalDialog(或类似形式)打开的弹出窗口需要传参获得其父窗口来特殊处理(不能用window.opener获取其父窗口，也不能用window.top获取其最父类窗口)；而window.open打开的弹出窗口不需要特殊处理(可以用window.opener获取其父窗口，也可以用window.top获取其最父类窗口)
			//out.write("<script>if(!window.dialogArguments){alert('非模式弹出窗口');window.top.open('" + request.getContextPath() + "/logoutSession.jsp', '_self');}else{alert('模式弹出窗口');window.close();window.dialogArguments.top.open('" + request.getContextPath() + "/logoutSession.jsp', '_self');}</script>");
			out.write("<script>");
			out.write("alert('会话超时，请重新登录！');");
			out.write("var parentWin=window.dialogArguments;");
			out.write("var openerWin=window.opener;");
			out.write("if(openerWin!=undefined){");//如果是以window.open的方式打开，则先关掉打开的窗口
			out.write("window.close();");
			out.write("openerWin.top.open('" + request.getContextPath() + "/logoutSession.jsp', '_self');");
			out.write("} else if(parentWin!=undefined){");//如果是以window.showModalDialog的方式打开，则先关掉打开的窗口
			out.write("window.close();");
			out.write("parentWin.top.open('" + request.getContextPath() + "/logoutSession.jsp', '_self');");
			out.write("}else{");
			out.write("window.top.open('" + request.getContextPath() + "/logoutSession.jsp', '_self');");
			out.write("}");
			out.write("</script>");
			return;
		}
		arg2.doFilter(arg0, arg1);
	}
}
