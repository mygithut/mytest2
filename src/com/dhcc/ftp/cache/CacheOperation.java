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
 * 缓存处理类
 * @author Sunhongyu
 *
 */
public class CacheOperation {
    private static final Log log = LogFactory.getLog(CacheOperation.class);
    private static CacheOperation singleton = null;
    
    private Hashtable<String, CacheData> cacheMap;//存放缓存数据
    
    private ArrayList<String> threadKeys;//处于线程更新中的key值列表
    
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
     * 添加数据缓存
     * 与方法getCacheData(String key, long intervalTime, int maxVisitCount)配合使用
     * @param key
     * @param data
     */
    public void addCacheData(String key, Object data) {
        addCacheData(key, data, true);
    }
    
    private void addCacheData(String key, Object data, boolean check) {
    	Runtime myRun = Runtime.getRuntime();
    	//当所用内存总量达到最大内存量时，就会报“内存溢出”错误；而空闲内存量似乎不太准(还剩300多M，一样会内存溢出)
    	System.out.println("------------ 添加缓存数据前：---------------");
        System.out.println("Java 虚拟机目前占用的内存总量="+myRun.totalMemory()+"字节");
    	System.out.println("Java 虚拟机试图使用的最大内存量="+myRun.maxMemory()+"字节");
    	System.out.println("Java 虚拟机中的空闲内存量="+myRun.freeMemory()+"字节");
        if (Runtime.getRuntime().freeMemory() < 500L*1024L*1024L) {//虚拟机内存小于500兆，则清除缓存
            log.warn("WEB缓存：内存不足，开始移除除了当前月季年三个key之外其他的所有缓存数据！");
            removeCacheDataByDate();//移除除了当前月季年三个key之外其他的所有缓存数据
            //return;
        } else if(check && cacheMap.containsKey(key)) {
            log.warn("WEB缓存：key值= " + key + " 在缓存中重复, 本次不缓存！");
            return;
        }
        System.out.println("缓存key为:"+key);
        cacheMap.put(key, new CacheData(data));
        System.out.println("------------ 添加缓存数据后：---------------");
        System.out.println("Java 虚拟机目前占用的内存总量="+myRun.totalMemory()+"字节");
    	System.out.println("Java 虚拟机试图使用的最大内存量="+myRun.maxMemory()+"字节");
    	System.out.println("Java 虚拟机中的空闲内存量="+myRun.freeMemory()+"字节");
    	
        data=null;System.gc();//似乎没用
        System.out.println("------------ 清除中间数据后：：---------------");
        System.out.println("Java 虚拟机目前占用的内存总量="+myRun.totalMemory()+"字节");
    	System.out.println("Java 虚拟机试图使用的最大内存量="+myRun.maxMemory()+"字节");
    	System.out.println("Java 虚拟机中的空闲内存量="+myRun.freeMemory()+"字节");
    }
    
    /** *//**
     * 取得缓存中的数据
     * 与方法addCacheData(String key, Object data)配合使用
     * @param key 
     * @param intervalTime 缓存的时间周期，小于等于0时不限制
     * @param maxVisitCount 访问累积次数，小于等于0时不限制
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
     * 当缓存中数据失效时，用不给定的方法线程更新数据
     * @param o 取得数据的对像(该方法是静态方法是不用实例，则传Class实列)
     * @param methodName 该对像中的方法
     * @param parameters 该方法的参数列表(参数列表中对像都要实现toString方法,若列表中某一参数为空则传它所属类的Class)
     * @param intervalTime 缓存的时间周期，小于等于0时不限制
     * @param maxVisitCount 访问累积次数，小于等于0时不限制
     * @return
     */
    public Object getCacheData(Object o, String methodName,Object[] parameters, 
            long intervalTime, int maxVisitCount) {
        Class oc = o instanceof Class ? (Class)o : o.getClass();
        String key = getKey(o, methodName, parameters);//生成缓存key值
        CacheData cacheData = (CacheData)cacheMap.get(key);
        if (cacheData == null) {//等待加载并返回
        	//如果threadKeys没有key，说明还未启动要执行的方法的线程
        	if (!threadKeys.contains(key)) {
        		System.out.println("add key="+key);
                threadKeys.add(key);
                Object returnValue = invoke(o, methodName, parameters, key);
                threadKeys.remove(key);
                return returnValue instanceof Class ? null : returnValue;
        	}else{//如果threadKeys中包含key，说明之前已经有对应方法的线程了，就等待，直到之前方法执行完毕
        		System.out.println("--- 之前已经有相同查询，不再重复查询，等待之前此相同查询结果...");
        		int waitMinutes=0;//等待的分钟数
	        	while(threadKeys.contains(key)) {
	        		try {
	        			System.out.println("--- 等待第"+(waitMinutes+1)+"分钟开始...");
	        			Thread.sleep(60000);	
	        			System.out.println("--- 等待第"+(waitMinutes+1)+"分钟结束!");
	        			waitMinutes++;
	        		} catch (Exception e) {
	        			e.printStackTrace();
	        		}
	        	}
	        	System.out.println("--- 等待相同查询结果全部结束，并获得之前相同查询结果缓存数据；一共等待"+waitMinutes+"分钟!!");
	        	CacheData cacheData_new = (CacheData)cacheMap.get(key);//重新从cacheMap中获取数据
	        	return cacheData_new.getData();
        	}
        }
        if (intervalTime > 0 && (System.currentTimeMillis() - cacheData.getTime()) > intervalTime) {
            daemonInvoke(o, methodName, parameters, key);//缓存时间超时,启动线程更新数据
        } else if (maxVisitCount > 0 && (maxVisitCount - cacheData.getCount()) <= 0) {//访问次数超出,启动线程更新数据
            daemonInvoke(o, methodName, parameters, key);
        } else {
            cacheData.addCount();
        }
        return cacheData.getData();
    }
         /** *//**
     * 递归调用给定方法更新缓存中数据据
     * @param o
     * @param methodName
     * @param parameters
     * @param key
     * @return 若反射调用方法返回值为空则返回该值的类型
     */
    private Object invoke(Object o, String methodName,Object[] parameters, String key) {
        Object returnValue = null;
        try {
           Class[] pcs = null;
            if (parameters != null) {
                pcs = new Class[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    if (parameters[i] instanceof MethodInfo) {//参数类型是MethodInfo则调用该方法的返回值做这参数
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
        	System.out.println("Java 虚拟机中的空闲内存量="+myRun.freeMemory()+"字节");
            if (Runtime.getRuntime().freeMemory() < 500L*1024L*1024L) {//虚拟机内存小于500兆，则清除缓存
                log.warn("WEB缓存：内存不足，开始移除除了当前月季年三个key之外其他的所有缓存数据！");
                removeCacheDataByDate();//移除除了当前月季年三个key之外其他的所有缓存数据
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
            log.error("调用方法失败,methodName=" + methodName);
            log.warn("----移除除了当前月季年三个key之外其他的所有缓存数据----");
            removeCacheDataByDate();//移除除了当前月季年三个key之外其他的所有缓存数据
            log.warn("重新执行方法,methodName=" + methodName);
            invoke(o, methodName, parameters, key);//重新执行
//            if (key != null) {
//                removeCacheData(key);
//                log.error("更新缓存失败，缓存key=" + key);
//            }
//            e.printStackTrace();
        }
        return returnValue;
    }
    
    /** 
     * 找不到完全匹配的方法时,对参数进行向父类匹配
     * 因为方法aa(java.util.List) 与 aa(java.util.ArrayList)不能自动匹配到
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
     * 新启线程后台调用给定方法更新缓存中数据据
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
     * 些类存放方法的主调对像,名称及参数数组
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
     * 线程调用方法
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
     * 移除缓存中的数据
     * @param key
     */
    public void removeCacheData(String key) {
        cacheMap.remove(key);
        //cacheMap.clear();
        System.gc();
    }
    
    
    /**
     * 移除缓存中的数据
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
     * 移除所有缓存中的数据
     *
     */
    public void removeAllCacheData() {
    	System.out.println("移除所有缓存中的数据!");
        cacheMap.clear();
        System.gc();
    }
    /**
     * 移除 除了当前默认月季年的三个key的其他缓存的数据
     */
    public void removeCacheDataByDate() {
    	String[] dates = this.getTjDate();
    	List<String> removeList = new ArrayList<String>();//用来装需要删除的元素
    	for(String key : cacheMap.keySet()) {//不能遍历的时候删除元素
			if (key.indexOf(dates[0]) == -1 && key.indexOf(dates[1]) == -1 && key.indexOf(dates[2]) == -1) {
				removeList.add(key);
			}
    	}
    	for(String key : removeList) {
    		System.out.println("清除缓存，key="+key);
			this.removeCacheData(key);
    	}
    	
    }
    public String toString() {
        StringBuffer sb = new StringBuffer("************************ ");
        sb.append("正在更新的缓存数据： ");
        for (int i = 0; i < threadKeys.size(); i++) {
            sb.append(threadKeys.get(i)).append(" ");
        }
        sb.append("当前缓存大小：").append(cacheMap.size()).append(" ");
        sb.append("************************");
        return sb.toString();
    }
    /**
     * 生成key值
     * @param o
     * @param methodName
     * @param parameters
     * @return
     */
    public String getKey(Object o, String methodName, Object[] parameters) {
    	Class oc = o instanceof Class ? (Class)o : o.getClass();
        StringBuffer key = new StringBuffer(oc.getName());//生成缓存key值
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
     * //获取统计报表月度、季度或年度最近的左、右端点日期，如果不为月底，则处理为上月月底
     * @param todayDate
     * @return 数组    0月 1季  2年     数据   左端点-右端点
     */
    public String[] getTjDate() {
    	ReportBbBO reportBbBO = new ReportBbBO();
    	String[] dates = new String[3];
    	String nowDate = String.valueOf(CommonFunctions.GetDBTodayDate());
    	//日期 如果不为月底，则处理为上月月底
    	if(!CommonFunctions.dateModifyD(nowDate, 1).substring(6,8).equals("01")) {//+1天后看是否为下月月初，如果不是则获取上月月底日期
    		nowDate = CommonFunctions.dateModifyM(nowDate, -1);//往前-1个月并处理到月底
		}
    	dates[0] = reportBbBO.getMinDate(nowDate, -1)+"-"+nowDate;//月度
    	dates[2] = reportBbBO.getMinDate(nowDate, -12)+"-"+nowDate;//年度
    	//计算季度  最近的完整季度
    	int nowMonth = Integer.valueOf(String.valueOf(nowDate).substring(4, 6));//当前月
		int scope = nowMonth%3;//超出完整季度的月份数，例:如果是20130731，scope=1
		nowDate = CommonFunctions.dateModifyM(nowDate, -scope);
    	dates[1] = reportBbBO.getMinDate(nowDate, -3)+"-"+nowDate;//季度
    	return dates;
    }
    public static void main(String[] args) {
	}
}


