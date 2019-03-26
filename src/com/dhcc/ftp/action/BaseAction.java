package com.dhcc.ftp.action;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * <p>
 * Title: 所有action的父类,变更模式
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Company: 东华软件股份公司金融事业部
 * </p>
 * 
 * @author 李瑞盛
 * 
 * @date Feb 22, 2011 4:29:33 PM
 * 
 * @version 1.0
 */
public abstract class BaseAction extends ActionSupport {
	private String result;
	private String encoding = "UTF-8";
	private static Map handlerMethodMap = new HashMap();
	private static boolean isInit = false;

	private void registerHandlerMethod(Method method) {
		BaseAction.handlerMethodMap.put(this.getClass().getName()
				+ method.getName(), method);
	}

	private Method getMethod(HttpServletRequest request) {
		try {
			String methodName = request.getParameter("method");
			if (methodName == null || "".equals(methodName))
				return null;
			Method method = (Method) handlerMethodMap.get(this.getClass()
					.getName()
					+ methodName);
			if (method == null) {
				method = this.getClass().getMethod(methodName,
						new Class[] { HttpServletRequest.class });
				if (method != null)
					registerHandlerMethod(method);
			}
			return method;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}

	// protected ServerLocator getLocator(HttpServletRequest request) {
	// // ServerLocator locator = (ServerLocator) request.getSession()
	// // .getAttribute(ServiceLocator.SERVICE_LOCATOR);
	// return locator;
	// }

	public void setPlainEncode() throws Exception {
		getResponse().setContentType("text/plain;charset="+encoding);
	}
	

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected HttpSession getSession() {
		return this.getRequest().getSession();
	}

	protected ActionContext getContext() {
		return ActionContext.getContext();
	}


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getRealPath(){
			return getRealPath(true);
	}
	
	/**
	 * Desc:根路径/绝对地址
	 * @param b
	 * @return 
	 */
	public String getRealPath(boolean b){
		if(!b)
			return getRequest().getContextPath();
		else 
			return getRequest().getSession().getServletContext().getRealPath(File.separator) ;
	}
	
}


