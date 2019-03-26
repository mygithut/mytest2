package com.dhcc.ftp.bo;

import com.dhcc.ftp.util.IdCardUtil;
import com.dhcc.ftp.util.Idcardchecks;



/**
 * �����Ƿ�ֹ���˻������ͥ��Ա�ظ��Ǽǹ��ܵ�ҵ������
 * 
 * @author fuww 2007/12/29
 */
public class CheckIdNumBO extends BaseBo{
	/**
	 * ��������Ա���뱣֤����������ݿ�û�����õ��˺�
	 * 
	 * @param idNum
	 * @param otherIdNum
	 * @return
	 */
	public int checkTlrctlEixtData(String idNum, String otherIdNum) {
		int i = 0;
		try {
			i = checkIdNumDAO.checkTlrctlEixtData(idNum, otherIdNum);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return i;
	}

	/**
	 * ת��������ʾ��ǰ̨������Ӧ����ʾ
	 * 
	 * @param idNum
	 * @return
	 */
	public int checkIdNumForTlrctl(String idNum) {
		// �����֤��
		if (null == idNum || "".equals(idNum)) {
			return 4;
		}
		IdCardUtil u = new IdCardUtil();
		// �ȼ���Ƿ�Ϸ���
		if (!u.checkIDCard(idNum)) {
			return 3;
		}
		String othernum = u.getOtherIdNum(idNum);
		if (this.checkTlrctlEixtData(idNum, othernum) > 0) {
			// �Ѿ����ڸó�Ա��
			return 1;
		} else {
			return 2;
		}
	}
	
	/**
	 * ���֤15λת18λ
	 * 
	 * @param id
	 *            15λ���֤
	 * @return 18λ���֤
	 */
	public static String transIDNum(String id) {
		String _id = id.trim();
		String str = (String) Idcardchecks.checkIDCard2(_id);
		if ("5".equals(str)) {
			if (id == null || "".equals(id)) {
				return "incorrect";
			}
			if (_id.length() == 15) {
				IdCardUtil cu = new IdCardUtil();
				_id = cu.IdCard15to18(_id);
			}
		}else{
			return "incorrect";
		}
		return _id;
	}
	
}
