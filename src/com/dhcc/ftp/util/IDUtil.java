package com.dhcc.ftp.util;

/**
 * ��ȡ����ID��ʹ��ʱ��+��������ʽʵ��
 * 
 * @author �����
 * 
 * @date 2012-11-21 ����03:43:09
 * 
 * @company ��������ɷݹ�˾���ڷ��ղ�Ʒ��
 */
public class IDUtil {

	public static IDUtil instance = null;

	private static final long ONE_STEP = 100;
	private static long lastTime = System.currentTimeMillis();//��ǰʱ��ĺ�����������һ����ǰ�ĺ���
	private static short count = 0;

	/**
	 * ��ȡ��������
	 * 
	 * @return
	 */
	public synchronized static IDUtil getInstanse() {
		if (instance == null) {
			instance = new IDUtil();
		}
		return instance;
	}

	private IDUtil() {
	}

	/**
	 * ���ݶ����ȡ������
	 * 
	 * @param clazz
	 * @return
	 */
	public synchronized String getUID() {
		try {
			if (count == ONE_STEP) {
				boolean done = false;
				while (!done) {
					long now = System.currentTimeMillis();
					if (now == lastTime) {
						try {
							Thread.sleep(1);
						} catch (java.lang.InterruptedException e) {
						}
						continue;
					} else {
						lastTime = now;
						count = 0;
						done = true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = lastTime + "" + (count++);
		System.out.println("����ID="+result);
		return result;
	}

}
