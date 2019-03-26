package com.dhcc.ftp.filter;
/**
 * @author  �����
 * 
 * @date    2013-12-31 ����06:22:28
 * 
 * ���������û�session
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.entity.TelMst;
  
public class SessionUserListener implements HttpSessionListener {  
  
    // keyΪsessionId��valueΪHttpSession��ʹ��static�����徲̬������ʹ֮��������ʱ��һֱ�����ڴ��С�  
    private static java.util.Map<String, HttpSession> sessionMap = new java.util.concurrent.ConcurrentHashMap<String, HttpSession>(500);  
  
    /** 
     * HttpSessionListener�еķ������ڴ���session 
     */  
    public void sessionCreated(HttpSessionEvent event) {  
        // TODO Auto-generated method stub  
    }  
  
    /** 
     * HttpSessionListener�еķ���������sessionʱ,ɾ��sessionMap�ж�Ӧ��session 
     */  
    public void sessionDestroyed(HttpSessionEvent event) {  
        getSessionMap().remove(event.getSession().getId());  
    }  
  
    /** 
     * �õ������û��Ự���� 
     */  
    public static List<HttpSession> getUserSessions() {  
        List<HttpSession> list = new ArrayList<HttpSession>();  
        Iterator<String> iterator = getSessionMapKeySetIt();  
        while (iterator.hasNext()) {  
            String key = iterator.next();  
            HttpSession session = getSessionMap().get(key);  
            list.add(session);  
        }  
        return list;  
    }  
  
    /** 
     * �õ��û���Ӧ�Ựmap��keyΪ�û�ID,valueΪ�ỰID 
     */  
    public static Map<String, String> getUserSessionMap() {  
        Map<String, String> map = new HashMap<String, String>();  
        Iterator<String> iter = getSessionMapKeySetIt();  
        while (iter.hasNext()) {  
            String sessionId = iter.next();  
            HttpSession session = getSessionMap().get(sessionId); 
            TelMst user = null;
            try{
            	user = (TelMst) session.getAttribute("userBean"); 
            }catch (Exception e){
            	getSessionMap().remove(sessionId);
            	System.out.println("sessionIdΪ"+sessionId+"��session��ʧЧ���Ѵ�map���Ƴ�");
            } 
            if (user != null) {  
                map.put(user.getTelNo(), sessionId);  
            }  
        }  
        return map;  
    }  
  
    /** 
     * �Ƴ��û�Session 
     */  
    public synchronized static void removeUserSession(String userId) {  
        Map<String, String> userSessionMap = getUserSessionMap();  
        if (userSessionMap.containsKey(userId)) {  
            String sessionId = userSessionMap.get(userId);  
            getSessionMap().get(sessionId).invalidate();  
            getSessionMap().remove(sessionId);  
        }  
    }  
  
    /** 
     * �����û���session������ 
     */  
    public static void addUserSession(HttpSession session) {  
        getSessionMap().put(session.getId(), session);  
    }  
  
    /** 
     * �Ƴ�һ��session 
     */  
    public static void removeSession(String sessionID) {  
        getSessionMap().remove(sessionID);  
    }  
  
    public static boolean containsKey(String key) {  
        return getSessionMap().containsKey(key);  
    }  
  
    /** 
     * �жϸ��û��Ƿ����ظ���¼��ʹ�� 
     * ͬ��������ֻ����һ���߳̽��룬�ź���֤�Ƿ��ظ���¼ 
     * @param user 
     * @return 
     */  
    public synchronized static boolean checkIfHasLogin(TelMst user) {  
        Iterator<String> iter = getSessionMapKeySetIt();  
        while (iter.hasNext()) {  
            String sessionId = iter.next(); 
            HttpSession session = getSessionMap().get(sessionId); 
    		TelMst sessionuser = null;
    		try{
                sessionuser = (TelMst) session.getAttribute("userBean"); 
    		} catch(Exception e) {
            	getSessionMap().remove(sessionId);
            	System.out.println("sessionIdΪ"+sessionId+"��session��ʧЧ���Ѵ�map���Ƴ�");
            }
            if (sessionuser != null) {  
                if (sessionuser.getTelNo().equals(user.getTelNo())){  
                    return true;  
                }  
            }  
        }  
        return false;  
    }  
  
    /** 
     * ��ȡ���ߵ�sessionMap 
     */  
    public static Map<String, HttpSession> getSessionMap() {  
        return sessionMap;  
    }  
  
    /** 
     * ��ȡ����sessionMap�е�SessionId 
     */  
    public static Iterator<String> getSessionMapKeySetIt() {  
        return getSessionMap().keySet().iterator();  
    }  
} 