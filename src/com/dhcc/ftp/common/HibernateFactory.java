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
	 * ���ص�ǰ�߳�����Ӧ��session�������ǰ�߳�û�ж�Ӧ��session���ʹ���һ����
	 * 
	 * @return
	 */
	public static Session getSession() {
		Session session = data.get();
		if (session == null) {
			logger.info("��ǰû��session����ȡ�µ�session.......");
			session = sessionFactory.openSession();
			data.set(session);
		}else{
			logger.info("��ǰ��session��ֱ�ӷ���session.......");
		}
		return session;
	}

	/**
	 * �رյ�ǰ�̹߳���Session����ȡ�����ǵĹ���
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
			System.out.println("��ͨ����̬����newInstance()��������");
			return null;
		}
		return this.sessionFactory.openSession();
	}
	*/
}
