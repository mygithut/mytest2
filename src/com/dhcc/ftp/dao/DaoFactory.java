package com.dhcc.ftp.dao;

import java.util.ArrayList;
import java.util.List;

import com.dhcc.ftp.entity.FtpEmpAccRel;
import com.dhcc.ftp.entity.FtpTjbbResult;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.formula.functions.T;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dhcc.ftp.common.HibernateFactory;
import com.dhcc.ftp.util.PageUtil;
import com.dhcc.ftp.util.QueryResult;



public class DaoFactory {
	private static final Logger logger=Logger.getLogger(DaoFactory.class);
	
	
	/**
	 * ��DaoFactory��Ψһ��insert
	 * @param entity
	 * @return
	 */
	public boolean insert(Object entity){
		try{
			//System.out.println("!!!!!!!!!!!!");
			logger.info("��ʼ��������");
			Session session=HibernateFactory.getSession();
			Transaction tran=session.beginTransaction();
			session.save(entity);
			tran.commit();
			//session.close();;
			logger.info("�������ݽ���");
			return true;
		}
		catch (Exception e) {
			logger.info("���������쳣��"+e.getMessage());	
		}
		return false;
	}
	
	/**
	 * ������������
	 * @param entity
	 * @return
	 */
	public boolean insert_s(List entityList){
		Session session=HibernateFactory.getSession();
		Transaction tran=session.beginTransaction();
		try{
			//System.out.println("!!!!!!!!!!!!");
			logger.info("��ʼ��������S...");
			int onceCommitNum=5000;//һ���ύ��¼��;
			for(int i=0;i<entityList.size();i++){
				/*if(entityList.get(i)==null){
					System.out.println("null������"+i);
					continue;
				}*/
				session.save(entityList.get(i));
				//entityList.set(i, null);//�ͷ�listѭ�����۹����У��ñʼ�¼����ռ�ڴ�(��list�иñʼ�¼��ʹ�ú�)��ע������ͬʱ�Ὣ���ô˷�����ԭList�����������(��ַ����)
				
                if((i+1)%1000==0 || i==entityList.size()-1){
                	System.out.println("--------------------------------------------------"+(i+1));
				}
				if((i+1)%onceCommitNum==0 || i==entityList.size()-1){
					System.out.println("-------------------------------------------------�����ύ...");
					tran.commit();
					session.clear();//�ύһ�κ����session�ͷ��ڴ�;
					System.out.println("-------------------------------------------------�����ύ����");
					if(i!=entityList.size()-1){
						tran=session.beginTransaction();
					}
				}			
			}			
			//session.close();
			logger.info("��������S����");
			return true;
		}
		catch (Exception e) {
			logger.info("��������S�쳣��"+e.getMessage());	
			tran.rollback();
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(Object entity){
		try{
			logger.info("��ʼ��������");
			Session session=HibernateFactory.getSession();
			Transaction tran=session.beginTransaction();
			session.clear();
			session.saveOrUpdate(entity);
			tran.commit();
			logger.info("�������ݽ���");
			//session.close();;
			return true;
		}
		catch (Exception e) {
			logger.info("���������쳣��"+e.getMessage());	
		}
		return false;
	}

	public boolean updateTjbbResult(FtpEmpAccRel ftpEmpAccRel, List<FtpTjbbResult> ftpTjbbResultList){
		try{
			logger.info("��ʼ��������");
			Session session=HibernateFactory.getSession();
			Transaction tran=session.beginTransaction();
			session.clear();
			session.saveOrUpdate(ftpEmpAccRel);
			for(FtpTjbbResult ftpTjbbResult:ftpTjbbResultList){
				session.saveOrUpdate(ftpTjbbResult);
			}
			tran.commit();
			logger.info("�������ݽ���");
			//session.close();;
			return true;
		}
		catch (Exception e) {
			logger.info("���������쳣��"+e.getMessage());
		}
		return false;
	}
	
	public boolean execute(String hsql,Object[] values){
		try{
			logger.info("��ʼִ�У�"+hsql);
			Session session=HibernateFactory.getSession();
			Transaction tran=session.beginTransaction();
			Query query=session.createQuery(hsql);
			
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}
			query.executeUpdate();
			
			tran.commit();
			//session.close();;
			return true;
		}
		catch (Exception e) {
			logger.info("ִ��HSQL�쳣��"+e.getMessage());	
		}
		return false;
	}


	public boolean executeEmpAccRel(String hsql,List<FtpEmpAccRel> ftpEmpAccRels){
		try{
			logger.info("��ʼִ�У�"+hsql);
			Session session=HibernateFactory.getSession();
			Transaction tran=session.beginTransaction();
			Query query=session.createQuery(hsql);
			query.executeUpdate();
			for(FtpEmpAccRel ftpEmpAccRel:ftpEmpAccRels){
				session.saveOrUpdate(ftpEmpAccRel);
			}
			tran.commit();
			//session.close();;
			return true;
		}
		catch (Exception e) {
			logger.info("ִ��HSQL�쳣��"+e.getMessage());
		}
		return false;
	}


	/**
	 * ִ����ԭʼ���κ�sql���
	 */
	public boolean execute1(String sql){
		try{
			logger.info("��ʼִ�У�"+sql);
			Session session=HibernateFactory.getSession();
			Transaction tran=session.beginTransaction();

			SQLQuery localSQLQuery = session.createSQLQuery(sql);

			localSQLQuery.executeUpdate();

			tran.commit();
			return true;
		}
		catch (Exception e) {
			logger.info("ִ��HSQL�쳣��"+e.getMessage());
		}
		return false;
	}

	/**
	 * ����ִ��
	 * @param hsql
	 * @param values
	 * @return
	 */
	public boolean execute_s(String[] hsqls,Object[] values){
		try{
			logger.info("��ʼִ����������"+hsqls[0]+"��...");
			System.out.println("��ʼִ����������"+hsqls[0]+"��...");
			Session session=HibernateFactory.getSession();
			Transaction tran=session.beginTransaction();
			
			for(int i=0;i<hsqls.length;i++){
				Query query=session.createQuery(hsqls[i]);
				/*if(values!=null){
					int size=values.length;
					for(int j=0;j<size;i++){
						query.setParameter(j,values[j]);
					}
				}*/
				query.executeUpdate();
				
                if((i+1)%100==0 || i==hsqls.length-1){
                	System.out.println("-------------------------------------------------"+(i+1));
				}
				if((i+1)%2000==0 || i==hsqls.length-1){
					System.out.println("-------------------------------------------------�����ύ...");
					tran.commit();
					System.out.println("-------------------------------------------------�����ύ����");
					if(i!=hsqls.length-1){
						tran=session.beginTransaction();
					}
				}			
			}
			//session.close();;
			System.out.println("ִ����������"+hsqls[0]+"�� ������");
			return true;
		}
		catch (Exception e) {
			logger.info("����ִ��HSQLʱ" +
					"�쳣��"+e.getMessage());	
		}
		return false;
	}
	
	/**
	 * ��DaoFactory��Ψһ��update
	 * @param hsql
	 * @param values
	 * @return
	 */
	public boolean update(String hsql,Object[] values){
		return this.execute(hsql, values);
	}
	
	/**
	 * ��DaoFactory��Ψһ��delete
	 * @param hsql
	 * @param values
	 * @return
	 */
	public boolean delete(String hsql,Object[] values){
		return this.execute(hsql, values);
	}

	/**
	 * ��DaoFactory��Ψһ��delete
	 * @param hsql
	 * @param ftpEmpAccRels
	 * @return
	 */
	public boolean deleteAndUpdate(String hsql,List<FtpEmpAccRel> ftpEmpAccRels){
		return this.executeEmpAccRel(hsql, ftpEmpAccRels);
	}


	/**
	 * ����ɾ��
	 * @param hsql
	 * @param values
	 * @return
	 */
	public boolean delete_s(String[] hsqls,Object[] values){
		return this.execute_s(hsqls, values);
	}
	
	/**
	 * ���õ�query
	 * @param hsql
	 * @param values
	 * @return
	 */
	public List query(String hsql,Object[] values){
		Session session=null;
		try{
			logger.info("��ʼִ�У�"+hsql);
		    session=HibernateFactory.getSession();
			Query query=session.createQuery(hsql);
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}
			session.flush();
			session.clear();
			return query.list();
		}
		catch (Exception e) {
			logger.info("ִ��HSQL�쳣��"+e.getMessage());	
		}finally{
			//session.close();
		}
		return new ArrayList();
	}
	
	
	
	public Object getBean(String hsql,Object[] values){
		Session session= null;
		try{
			logger.info("��ʼִ�У�"+hsql);
		    session=HibernateFactory.getSession();
		    
			Query query=session.createQuery(hsql);
			//query.setCacheable(false);//�����û���
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}

			session.flush();
			session.clear();
			List list=query.list();
			return list.size()>0?list.get(0):null;
		}
		catch (Exception e) {
			logger.info("ִ��HSQL�쳣��"+e.getMessage());	
		}finally{
			//////session.close();;
		}
		return null;
	}
	
	public List query(String hsql,Object[] values,int pageSize,int currentPage){
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		Session session=null;
		try{
			logger.info("��ʼִ�У�"+hsql);
		    session=HibernateFactory.getSession();
			Query query=session.createQuery(hsql);
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}
			query.setFirstResult((currentPage-1)*pageSize);
			query.setMaxResults(pageSize);
			//////session.close();;
			return query.list();
		}
		catch (Exception e) {
			logger.info("ִ��HSQL�쳣��"+e.getMessage());	
		}finally{
			////session.close();;
		}
		return new ArrayList();
	}
	
	public Object getUniqueResult(String hsql,Object[] values){
		Session session=null;
		try{
			logger.info("��ʼִ�У�"+hsql);
			session=HibernateFactory.getSession();
			Query query=session.createQuery(hsql);
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}
			return query.uniqueResult();
		}
		catch (Exception e) {
			logger.info("ִ��HSQL�쳣��"+e.getMessage());	
		}finally{
			////session.close();;
		}
		return null;
	}
	
	public PageUtil queryPage(String hsql,Object[] values, int pageSize, int currentPage, int rowsCount,String pageName){

		if(rowsCount<0){
			rowsCount=this.query(hsql, values).size();
		}
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		int pageCount=rowsCount/pageSize;
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		
		List list=this.query(hsql, values, pageSize, currentPage);
		
		String pageLine=this.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		return new PageUtil(list,pageLine);
	}
	
	/**
	 * �·�ҳ���캯��
	 * @param hsql
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public QueryResult queryPage1(String hsql, int firstResult, int maxResults){

		int totalRecords = HibernateFactory.getSession().createQuery(hsql).list().size();//
        List list = HibernateFactory.getSession().createQuery(hsql)//
												 .setFirstResult(1)//
												 .setMaxResults(2)//
												 .list();
        return new QueryResult(totalRecords, list);
	}
	
	/**
	 * ����PageUtil
	 * @param hsql
	 * @param pageNum �ڼ�ҳ
	 * @return
	 */
//	public PageUtil1 getPageUtil(String hsql,int pageNum) {
//		int pageSize = 10;
//
//		int firstResult = PageUtil1.clacFirstResult(pageNum, pageSize);
//		QueryResult qr = this.queryPage1(hsql, firstResult, pageSize);
//
//		return new PageUtil1(qr,pageNum,pageSize);
//	}
	
	/**  ��ҳ���캯������   **/
	
	
	
	
	public String formartPageLine(int pageSize, int currentPage, int rowsCount,String pageName){
		
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		StringBuffer buffer=new StringBuffer();
		int pageCount=rowsCount/pageSize;
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		
		
		if(currentPage==1){
			buffer.append("��ҳ&nbsp;");
			buffer.append("��һҳ&nbsp;");
		}
		else{
			buffer.append("<a href=\""+ pageName +"?page=1&rowsCount="+ rowsCount +"\">��ҳ</a>&nbsp;");
			buffer.append("<a href=\""+ pageName +"?page="+ (currentPage-1) +"&rowsCount="+ rowsCount +"\">��һҳ</a>&nbsp;");
		}
		
		if(currentPage==pageCount){
			buffer.append("��һҳ&nbsp;");
			buffer.append("ĩҳ");
		}
		else{
			buffer.append("<a href=\""+ pageName +"?page="+ (currentPage+1) +"&rowsCount="+ rowsCount +"\">��һҳ</a>&nbsp;");
			buffer.append("<a href=\""+ pageName +"?page="+ pageCount +"&rowsCount="+ rowsCount +"\">ĩҳ</a>&nbsp;");
		}
		buffer.append("&nbsp;&nbsp;��������"+ rowsCount +"�����ݣ�ÿҳ"+ pageSize +"�����ݣ�ҳ��<font color='red'>"+ currentPage+"</font>/"+ pageCount);
		
		//buffer.setLength(0);
		buffer.append("&nbsp;&nbsp;��ת����");
		buffer.append("\r\n<select onchange=\"window.location.replace('"+ pageName + "?page='+this.value+'&rowsCount='+"+ rowsCount +")\">\r\n");
		for(int i=0;i<pageCount;i++){
			String selected="";
			if(i==currentPage-1){
				selected="selected";
			}
			buffer.append("<option "+ selected +" value=\""+ (i+1) +"\">��"+ (i+1) +"ҳ</option>\r\n");
		}
		buffer.append("</select>");
		
		
		return buffer.toString();
	}
	
	
	public List sqlQuery(String sql,Class cls,Object[] values){
		Session session=null;
		try{
			logger.info("��ʼִ�У�"+sql);
		    session=HibernateFactory.getSession();
			SQLQuery query=session.createSQLQuery(sql);
			query.addEntity(cls);
			
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}
			return query.list();
		}
		catch (Exception e) {
			logger.info("ִ��SQL�쳣��"+e.getMessage());	
		}finally{
			////session.close();;
		}
		return new ArrayList();
	}
	
	public List query1(String hsql,Object[] values){
		Session session=null;
		try{
			logger.info("��ʼִ�У�"+hsql);
		    session=HibernateFactory.getSession();
			Query query=session.createSQLQuery(hsql);
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}
			session.flush();
			session.clear();
			List list=query.list();
			return list;
		}
		catch (Exception e) {
			e.printStackTrace();
			logger.info("ִ��HSQL�쳣��"+e.getMessage());	
		}finally{
			//session.close();;
		}
		return new ArrayList();
	}
	//��ҳsql��ѯ
	public List query1(String hsql,Object[] values,int pageSize,int currentPage){
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		Session session=null;
		try{
			logger.info("��ʼִ�У�"+hsql);
		    session=HibernateFactory.getSession();
			Query query=session.createSQLQuery(hsql);
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}
			query.setFirstResult((currentPage-1)*pageSize);
			query.setMaxResults(pageSize);
			session.flush();
			session.clear();
			return query.list();
		}
		catch (Exception e) {
			logger.info("ִ��HSQL�쳣��"+e.getMessage());	
		}finally{
			//session.close();;
		}
		return new ArrayList();
	}
	public Object sqlgetBean(String sql,Object[] values){
		Session session= null;
		try{
			logger.info("��ʼִ�У�"+sql);
		    session=HibernateFactory.getSession();
			Query query=session.createSQLQuery(sql);
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}
			List list=query.list();
			
			return list.size()>0?list.get(0):null;
		}
		catch (Exception e) {
			logger.info("ִ��SQL�쳣��"+e.getMessage());	
		}finally{
			//////session.close();;
		}
		return null;
	}
	
	public List sqlgetBeans(String sql,Object[] values){
		Session session= null;
		try{
			logger.info("��ʼִ�У�"+sql);
		    session=HibernateFactory.getSession();
			Query query=session.createSQLQuery(sql);
			if(values!=null){
				int size=values.length;
				for(int i=0;i<size;i++){
					query.setParameter(i,values[i]);
				}
			}
			List list=query.list();
			
			return list;
		}
		catch (Exception e) {
			logger.info("ִ��SQL�쳣��"+e.getMessage());	
		}finally{
			//////session.close();;
		}
		return null;
	}
	
	public Object loadById(Class clazz,Integer id){
		Session session= null;
		Object o = null;
		try{
		    session=HibernateFactory.getSession();
		    o = session.load(clazz, id);
		}
		catch (Exception e) {
			logger.info("ִ��Load�쳣��"+e.getMessage());	
		}finally{
		}
		return o;
	}
	public List getListBySQL(String hsql){
		Session session=null;
		try{
		    session=HibernateFactory.getSession();
			Query query=session.createSQLQuery(hsql);
			session.flush();
			session.clear();
			return query.list();
		}
		catch (Exception e) {
			logger.info("ִ��HSQL�쳣��"+e.getMessage());	
		}finally{
		}
		return new ArrayList();
	}

	
}
