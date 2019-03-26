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
 * @author �����
 * 
 * @date 2013-4-9 ����05:11:03
 * 
 * @Title: ���session�Ƿ�ʱ�Ĺ�����
 * 
 * @company ��������ɷݹ�˾���ڷ��ղ�Ʒ��
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
		// ÿ�η�����������session�Ƿ�Ϊ��
		String requestUrl = request.getServletPath();// ��������url
		//String url = "/index.jsp-/logoutSession.jsp-/htmlframe/menu.jsp-/login.jsp-/zuayLogin_.action";//���в���Ҫsession���˼���ҳ��(Ҳ����û��session�������ģ���session����ǰ��ҳ��)
		System.out.println("requestUrl"+requestUrl);
		String url = "/index.jsp-/logoutSession.jsp-/zuayLogin_removeSessionAndLogin.action-/login.jsp-/zuayLogin_login.action";//���session��ʱʱ��ҳ��ieˢ�±�������
		// ÿ�η����������������ȶ���õ�url�Ƚϣ��������ͬ������session�Ƿ�Ϊ��
		if (url.indexOf(requestUrl) == -1 && session.getAttribute("userBean") == null) {
			String CONTENT_TYPE = "text/html; charset=GBK";
			response.setContentType(CONTENT_TYPE);
			PrintWriter out = response.getWriter();		
			//ֻ����showModalDialog(��������ʽ)�򿪵ĵ���������Ҫ���λ���丸���������⴦��(������window.opener��ȡ�丸���ڣ�Ҳ������window.top��ȡ����ര��)����window.open�򿪵ĵ������ڲ���Ҫ���⴦��(������window.opener��ȡ�丸���ڣ�Ҳ������window.top��ȡ����ര��)
			//out.write("<script>if(!window.dialogArguments){alert('��ģʽ��������');window.top.open('" + request.getContextPath() + "/logoutSession.jsp', '_self');}else{alert('ģʽ��������');window.close();window.dialogArguments.top.open('" + request.getContextPath() + "/logoutSession.jsp', '_self');}</script>");
			out.write("<script>");
			out.write("alert('�Ự��ʱ�������µ�¼��');");
			out.write("var parentWin=window.dialogArguments;");
			out.write("var openerWin=window.opener;");
			out.write("if(openerWin!=undefined){");//�������window.open�ķ�ʽ�򿪣����ȹص��򿪵Ĵ���
			out.write("window.close();");
			out.write("openerWin.top.open('" + request.getContextPath() + "/logoutSession.jsp', '_self');");
			out.write("} else if(parentWin!=undefined){");//�������window.showModalDialog�ķ�ʽ�򿪣����ȹص��򿪵Ĵ���
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
