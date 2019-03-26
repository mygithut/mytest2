package com.dhcc.ftp.cache;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.bo.ReportBbBO;
import com.dhcc.ftp.util.CommonFunctions;

/**
 * ���洦����
 * @author Sunhongyu
 *
 */
public class CacheOperation {
    private static final Log log = LogFactory.getLog(CacheOperation.class);
    private static CacheOperation singleton = null;
    
    private Hashtable<String, CacheData> cacheMap;//��Ż�������
    
    private ArrayList<String> threadKeys;//�����̸߳����е�keyֵ�б�
    
    public static CacheOperation getInstance() {
        if (singleton == null) {
            singleton = new CacheOperation();
        }
        return singleton;
    }
    
    private CacheOperation() {
        cacheMap = new Hashtable<String, CacheData>();
        threadKeys = new ArrayList<String>();
    }
    
    /** *//**
     * ������ݻ���
     * �뷽��getCacheData(String key, long intervalTime, int maxVisitCount)���ʹ��
     * @param key
     * @param data
     */
    public void addCacheData(String key, Object data) {
        addCacheData(key, data, true);
    }
    
    private void addCacheData(String key, Object data, boolean check) {
    	Runtime myRun = Runtime.getRuntime();
    	//�������ڴ������ﵽ����ڴ���ʱ���ͻᱨ���ڴ���������󣻶������ڴ����ƺ���̫׼(��ʣ300��M��һ�����ڴ����)
    	System.out.println("------------ ��ӻ�������ǰ��---------------");
        System.out.println("Java �����Ŀǰռ�õ��ڴ�����="+myRun.totalMemory()+"�ֽ�");
    	System.out.println("Java �������ͼʹ�õ�����ڴ���="+myRun.maxMemory()+"�ֽ�");
    	System.out.println("Java ������еĿ����ڴ���="+myRun.freeMemory()+"�ֽ�");
        if (Runtime.getRuntime().freeMemory() < 500L*1024L*1024L) {//������ڴ�С��500�ף����������
            log.warn("WEB���棺�ڴ治�㣬��ʼ�Ƴ����˵�ǰ�¼�������key֮�����������л������ݣ�");
            removeCacheDataByDate();//�Ƴ����˵�ǰ�¼�������key֮�����������л�������
            //return;
        } else if(check && cacheMap.containsKey(key)) {
            log.warn("WEB���棺keyֵ= " + key + " �ڻ������ظ�, ���β����棡");
            return;
        }
        System.out.println("����keyΪ:"+key);
        cacheMap.put(key, new CacheData(data));
        System.out.println("------------ ��ӻ������ݺ�---------------");
        System.out.println("Java �����Ŀǰռ�õ��ڴ�����="+myRun.totalMemory()+"�ֽ�");
    	System.out.println("Java �������ͼʹ�õ�����ڴ���="+myRun.maxMemory()+"�ֽ�");
    	System.out.println("Java ������еĿ����ڴ���="+myRun.freeMemory()+"�ֽ�");
    	
        data=null;System.gc();//�ƺ�û��
        System.out.println("------------ ����м����ݺ󣺣�---------------");
        System.out.println("Java �����Ŀǰռ�õ��ڴ�����="+myRun.totalMemory()+"�ֽ�");
    	System.out.println("Java �������ͼʹ�õ�����ڴ���="+myRun.maxMemory()+"�ֽ�");
    	System.out.println("Java ������еĿ����ڴ���="+myRun.freeMemory()+"�ֽ�");
    }
    
    /** *//**
     * ȡ�û����е�����
     * �뷽��addCacheData(String key, Object data)���ʹ��
     * @param key 
     * @param intervalTime �����ʱ�����ڣ�С�ڵ���0ʱ������
     * @param maxVisitCount �����ۻ�������С�ڵ���0ʱ������
     * @return
     */
    public Object getCacheData(String key, long intervalTime, int maxVisitCount) {
        CacheData cacheData = (CacheData)cacheMap.get(key);
        if (cacheData == null) {
            return null;
        }
        if (intervalTime > 0 && (System.currentTimeMillis() - cacheData.getTime()) > intervalTime) {
            removeCacheData(key);
            return null;
        }
        if (maxVisitCount > 0 && (maxVisitCount - cacheData.getCount()) <= 0) {
            removeCacheData(key);
            return null;
        } else {
            cacheData.addCount();
        }
        return cacheData.getData();
    }
    
    /** *//**
     * ������������ʧЧʱ���ò������ķ����̸߳�������
     * @param o ȡ�����ݵĶ���(�÷����Ǿ�̬�����ǲ���ʵ������Classʵ��)
     * @param methodName �ö����еķ���
     * @param parameters �÷����Ĳ����б�(�����б��ж���Ҫʵ��toString����,���б���ĳһ����Ϊ�������������Class)
     * @param intervalTime �����ʱ�����ڣ�С�ڵ���0ʱ������
     * @param maxVisitCount �����ۻ�������С�ڵ���0ʱ������
     * @return
     */
    public Object getCacheData(Object o, String methodName,Object[] parameters, 
            long intervalTime, int maxVisitCount) {
        Class oc = o instanceof Class ? (Class)o : o.getClass();
        String key = getKey(o, methodName, parameters);//���ɻ���keyֵ
        CacheData cacheData = (CacheData)cacheMap.get(key);
        if (cacheData == null) {//�ȴ����ز�����
        	//���threadKeysû��key��˵����δ����Ҫִ�еķ������߳�
        	if (!threadKeys.contains(key)) {
        		System.out.println("add key="+key);
                threadKeys.add(key);
                Object returnValue = invoke(o, methodName, parameters, key);
                threadKeys.remove(key);
                return returnValue instanceof Class ? null : returnValue;
        	}else{//���threadKeys�а���key��˵��֮ǰ�Ѿ��ж�Ӧ�������߳��ˣ��͵ȴ���ֱ��֮ǰ����ִ�����
        		System.out.println("--- ֮ǰ�Ѿ�����ͬ��ѯ�������ظ���ѯ���ȴ�֮ǰ����ͬ��ѯ���...");
        		int waitMinutes=0;//�ȴ��ķ�����
	        	while(threadKeys.contains(key)) {
	        		try {
	        			System.out.println("--- �ȴ���"+(waitMinutes+1)+"���ӿ�ʼ...");
	        			Thread.sleep(60000);	
	        			System.out.println("--- �ȴ���"+(waitMinutes+1)+"���ӽ���!");
	        			waitMinutes++;
	        		} catch (Exception e) {
	        			e.printStackTrace();
	        		}
	        	}
	        	System.out.println("--- �ȴ���ͬ��ѯ���ȫ�������������֮ǰ��ͬ��ѯ����������ݣ�һ���ȴ�"+waitMinutes+"����!!");
	        	CacheData cacheData_new = (CacheData)cacheMap.get(key);//���´�cacheMap�л�ȡ����
	        	return cacheData_new.getData();
        	}
        }
        if (intervalTime > 0 && (System.currentTimeMillis() - cacheData.getTime()) > intervalTime) {
            daemonInvoke(o, methodName, parameters, key);//����ʱ�䳬ʱ,�����̸߳�������
        } else if (maxVisitCount > 0 && (maxVisitCount - cacheData.getCount()) <= 0) {//���ʴ�������,�����̸߳�������
            daemonInvoke(o, methodName, parameters, key);
        } else {
            cacheData.addCount();
        }
        return cacheData.getData();
    }
         /** *//**
     * �ݹ���ø����������»��������ݾ�
     * @param o
     * @param methodName
     * @param parameters
     * @param key
     * @return ��������÷�������ֵΪ���򷵻ظ�ֵ������
     */
    private Object invoke(Object o, String methodName,Object[] parameters, String key) {
        Object returnValue = null;
        try {
           Class[] pcs = null;
            if (parameters != null) {
                pcs = new Class[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    if (parameters[i] instanceof MethodInfo) {//����������MethodInfo����ø÷����ķ���ֵ�������
                        MethodInfo pmi = (MethodInfo)parameters[i];
                        Object pre = invoke(pmi.getO(), pmi.getMethodName(), pmi.getParameters(), null);
                        parameters[i] = pre;
                    }
                    if (parameters[i] instanceof Class) {
                        pcs[i] = (Class)parameters[i];
                        parameters[i] = null;
                    } else {
                        pcs[i] = parameters[i].getClass();
                    }
                }
            }
            Class oc = o instanceof Class ? (Class)o : o.getClass();
        //    Method m = oc.getDeclaredMethod(methodName, pcs);
            Runtime myRun = Runtime.getRuntime();
            myRun.gc();
            Thread.yield();
        	System.out.println("Java ������еĿ����ڴ���="+myRun.freeMemory()+"�ֽ�");
            if (Runtime.getRuntime().freeMemory() < 500L*1024L*1024L) {//������ڴ�С��500�ף����������
                log.warn("WEB���棺�ڴ治�㣬��ʼ�Ƴ����˵�ǰ�¼�������key֮�����������л������ݣ�");
                removeCacheDataByDate();//�Ƴ����˵�ǰ�¼�������key֮�����������л�������
                //return;
            }
            Method m = matchMethod(oc, methodName, pcs);
            returnValue = m.invoke(o, parameters);
            if (key != null && returnValue != null) {
                addCacheData(key, returnValue, false);
            }
            if (returnValue == null) {
                returnValue = m.getReturnType();
            }
        } catch(Exception e) {
            log.error("���÷���ʧ��,methodName=" + methodName);
            log.warn("----�Ƴ����˵�ǰ�¼�������key֮�����������л�������----");
            removeCacheDataByDate();//�Ƴ����˵�ǰ�¼�������key֮�����������л�������
            log.warn("����ִ�з���,methodName=" + methodName);
            invoke(o, methodName, parameters, key);//����ִ��
//            if (key != null) {
//                removeCacheData(key);
//                log.error("���»���ʧ�ܣ�����key=" + key);
//            }
//            e.printStackTrace();
        }
        return returnValue;
    }
    
    /** 
     * �Ҳ�����ȫƥ��ķ���ʱ,�Բ�����������ƥ��
     * ��Ϊ����aa(java.util.List) �� aa(java.util.ArrayList)�����Զ�ƥ�䵽
     * 
     * @param oc
     * @param methodName
     * @param pcs
     * @return
     * @throws NoSuchMethodException 
     * @throws NoSuchMethodException
     */
    private Method matchMethod(Class oc, String methodName, Class[] pcs) throws NoSuchMethodException, SecurityException {
        try {
            Method method = oc.getDeclaredMethod(methodName, pcs);
            return method;
        } catch (NoSuchMethodException e) {
            Method[] ms = oc.getDeclaredMethods();
            aa:for (int i = 0; i < ms.length; i++) {
                if (ms[i].getName().equals(methodName)) {
                    Class[] pts = ms[i].getParameterTypes();
                    if (pts.length == pcs.length) {
                        for (int j = 0; j < pts.length; j++) {
                            if (!pts[j].isAssignableFrom(pcs[j])) {
                                break aa;
                            }
                        }
                        return ms[i];
                    }
                }
            }
            throw new NoSuchMethodException();
        }
    }
    
    /** *//**
     * �����̺߳�̨���ø����������»��������ݾ�
     * @param o
     * @param methodName
     * @param parameters
     * @param key
     */
    private void daemonInvoke(Object o, String methodName,Object[] parameters, String key) {
        if (!threadKeys.contains(key)) {
        	InvokeThread t = new InvokeThread(o, methodName, parameters, key);
            t.start();
        }
    }
    
    /** *//**
     * Щ���ŷ�������������,���Ƽ���������
     * @author zsy
     *
     */
    public class MethodInfo {
        private Object o;
        private String methodName;
        private Object[] parameters;
        public MethodInfo(Object o, String methodName,Object[] parameters) {
            this.o = o;
            this.methodName = methodName;
            this.parameters = parameters;
        }
        public String getMethodName() {
            return methodName;
        }
        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }
        public Object getO() {
            return o;
        }
        public void setO(Object o) {
            this.o = o;
        }
        public Object[] getParameters() {
            return parameters;
        }
        public void setParameters(Object[] parameters) {
            this.parameters = parameters;
        }
        
        public String toString() {
            StringBuffer str = new StringBuffer(methodName);
            if (parameters != null) {
                str.append("(");
                for (int i = 0; i < parameters.length; i++) {
                    if (parameters[i] instanceof Object[]) {
                        str.append(Arrays.toString((Object[])parameters[i])).append(",");
                    } else {
                        str.append(parameters[i]).append(",");
                    }
                }
                str.append(")");
            }
            return str.toString();
        }
    }
    
    /** *//**
     * �̵߳��÷���
     * @author zsy
     *
     */
    private class InvokeThread extends Thread {
        private Object o;
        private String methodName;
        private Object[] parameters;
        private String key;
        public InvokeThread(Object o, String methodName,Object[] parameters, String key) {
            this.o = o;
            this.methodName = methodName;
            this.parameters = parameters;
            this.key = key;
        }
        public void run() {
            threadKeys.add(key);
            invoke(o, methodName, parameters, key);
            threadKeys.remove(key);
        }
    }
    /**  
     * �Ƴ������е�����
     * @param key
     */
    public void removeCacheData(String key) {
        cacheMap.remove(key);
        //cacheMap.clear();
        System.gc();
    }
    
    
    /**
     * �Ƴ������е�����
     * @param o
     * @param methodName
     * @param parameters
     */
    public void removeCacheData(Object o, String methodName, Object[] parameters) {
    	String key = this.getKey(o, methodName, parameters);
    	System.out.println("remove key="+key);
        cacheMap.remove(key);
        System.gc();
    }
    
    /**  
     * �Ƴ����л����е�����
     *
     */
    public void removeAllCacheData() {
    	System.out.println("�Ƴ����л����е�����!");
        cacheMap.clear();
        System.gc();
    }
    /**
     * �Ƴ� ���˵�ǰĬ���¼��������key���������������
     */
    public void removeCacheDataByDate() {
    	String[] dates = this.getTjDate();
    	List<String> removeList = new ArrayList<String>();//����װ��Ҫɾ����Ԫ��
    	for(String key : cacheMap.keySet()) {//���ܱ�����ʱ��ɾ��Ԫ��
			if (key.indexOf(dates[0]) == -1 && key.indexOf(dates[1]) == -1 && key.indexOf(dates[2]) == -1) {
				removeList.add(key);
			}
    	}
    	for(String key : removeList) {
    		System.out.println("������棬key="+key);
			this.removeCacheData(key);
    	}
    	
    }
    public String toString() {
        StringBuffer sb = new StringBuffer("************************ ");
        sb.append("���ڸ��µĻ������ݣ� ");
        for (int i = 0; i < threadKeys.size(); i++) {
            sb.append(threadKeys.get(i)).append(" ");
        }
        sb.append("��ǰ�����С��").append(cacheMap.size()).append(" ");
        sb.append("************************");
        return sb.toString();
    }
    /**
     * ����keyֵ
     * @param o
     * @param methodName
     * @param parameters
     * @return
     */
    public String getKey(Object o, String methodName, Object[] parameters) {
    	Class oc = o instanceof Class ? (Class)o : o.getClass();
        StringBuffer key = new StringBuffer(oc.getName());//���ɻ���keyֵ
        key.append("-").append(methodName);
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] instanceof Object[]) {
                    key.append("-").append(Arrays.toString((Object[])parameters[i]));
                } else {
                    key.append("-").append(parameters[i]);
                }
            }
        }
        return key.toString();
    }
    /**
     * //��ȡͳ�Ʊ����¶ȡ����Ȼ������������Ҷ˵����ڣ������Ϊ�µף�����Ϊ�����µ�
     * @param todayDate
     * @return ����    0�� 1��  2��     ����   ��˵�-�Ҷ˵�
     */
    public String[] getTjDate() {
    	ReportBbBO reportBbBO = new ReportBbBO();
    	String[] dates = new String[3];
    	String nowDate = String.valueOf(CommonFunctions.GetDBTodayDate());
    	//���� �����Ϊ�µף�����Ϊ�����µ�
    	if(!CommonFunctions.dateModifyD(nowDate, 1).substring(6,8).equals("01")) {//+1����Ƿ�Ϊ�����³�������������ȡ�����µ�����
    		nowDate = CommonFunctions.dateModifyM(nowDate, -1);//��ǰ-1���²������µ�
		}
    	dates[0] = reportBbBO.getMinDate(nowDate, -1)+"-"+nowDate;//�¶�
    	dates[2] = reportBbBO.getMinDate(nowDate, -12)+"-"+nowDate;//���
    	//���㼾��  �������������
    	int nowMonth = Integer.valueOf(String.valueOf(nowDate).substring(4, 6));//��ǰ��
		int scope = nowMonth%3;//�����������ȵ��·�������:�����20130731��scope=1
		nowDate = CommonFunctions.dateModifyM(nowDate, -scope);
    	dates[1] = reportBbBO.getMinDate(nowDate, -3)+"-"+nowDate;//����
    	return dates;
    }
    public static void main(String[] args) {
	}
}


