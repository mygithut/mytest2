package com.dhcc.ftp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dhcc.ftp.common.HibernateFactory;
import com.dhcc.ftp.dao.DaoFactory;



public class CloseSessionFilter implements Filter {

	private static final Logger logger=Logger.getLogger(DaoFactory.class);
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("utf-8");

		Session session = HibernateFactory.getSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			chain.doFilter(request, response);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw new ServletException(e);
		} finally {
			logger.info("filter中关闭session.......");
			HibernateFactory.closeSession();
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("关闭session过滤器初始化.......");
	}

}
