package com.dhcc.ftp.bo;

import com.dhcc.ftp.util.IdCardUtil;
import com.dhcc.ftp.util.Idcardchecks;



/**
 * 此类是防止个人或者其家庭成员重复登记功能的业务层设计
 * 
 * @author fuww 2007/12/29
 */
public class CheckIdNumBO extends BaseBo{
	/**
	 * 新增操作员必须保证这个人在数据库没有启用的账号
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
	 * 转化各种提示到前台给出相应的提示
	 * 
	 * @param idNum
	 * @return
	 */
	public int checkIdNumForTlrctl(String idNum) {
		// 输入空证件
		if (null == idNum || "".equals(idNum)) {
			return 4;
		}
		IdCardUtil u = new IdCardUtil();
		// 先检查是否合法的
		if (!u.checkIDCard(idNum)) {
			return 3;
		}
		String othernum = u.getOtherIdNum(idNum);
		if (this.checkTlrctlEixtData(idNum, othernum) > 0) {
			// 已经存在该成员了
			return 1;
		} else {
			return 2;
		}
	}
	
	/**
	 * 身份证15位转18位
	 * 
	 * @param id
	 *            15位身份证
	 * @return 18位身份证
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
