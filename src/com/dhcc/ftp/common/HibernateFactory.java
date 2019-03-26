package com.dhcc.ftp.common;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateFactory {
	private static final Logger logger =Logger.getLogger(HibernateFactory.class);

	private static final Configuration configuration;
	private static final SessionFactory sessionFactory;

	static {
		configuration = new Configuration().configure("dhcc-framework-context/hibernate.cfg.xml");
		//configuration = new Configuration().configure();
		sessionFactory = configuration.buildSessionFactory();
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private static ThreadLocal<Session> data = new ThreadLocal<Session>();

	/**
	 * 返回当前线程所对应的session，如果当前线程没有对应的session，就创建一个。
	 * 
	 * @return
	 */
	public static Session getSession() {
		Session session = data.get();
		if (session == null) {
			logger.info("当前没有session，获取新的session.......");
			session = sessionFactory.openSession();
			data.set(session);
		}else{
			logger.info("当前有session，直接返回session.......");
		}
		return session;
	}

	/**
	 * 关闭当前线程关联Session，并取消他们的关联
	 */
	public static void closeSession() {
		Session session = data.get();
		if (session != null) {
			session.close();
			data.set(null);
		}
	}
	
	/*	
	private SessionFactory sessionFactory;
	public static HibernateFactory newIntance(){
		return HibernateFactory.instance==null?HibernateFactory.instance=new HibernateFactory():HibernateFactory.instance;
	}
	
	public HibernateFactory() {
		Configuration config=new Configuration().configure("/hibernate.cfg.xml");
		this.sessionFactory=config.buildSessionFactory();
	}
	
	public Session openSession(){
		if(this.sessionFactory==null){
			System.out.println("请通过静态方法newInstance()创建事例");
			return null;
		}
		return this.sessionFactory.openSession();
	}
	*/
}
