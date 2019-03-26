package com.dhcc.ftp.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;

import com.dhcc.ftp.dao.DaoFactory;

public class MenuTreeUtil {

	public static String read(String roleno, String menu_parent) {

		String result = "";
		if ("".equals(menu_parent))
			menu_parent = "A";
		DaoFactory df = new DaoFactory();
		String sql = "select a.menu_no,menu_name,menu_parent,menu_lvl,menu_url from ftp.sys_menu a join ftp.sys_role_menu b on a.menu_no=b.menu_no  "
				+ " where menu_lvl=3 and a.menu_no like '"
				+ menu_parent
				+ "%' and b.role_no='" + roleno + "' order by a.menu_no";
		List list = df.query1(sql, null);
		for (int i = 0; i < list.size(); i++) {
			Object[] o = (Object[]) list.get(i);
			if (o != null && o.length > 4 && o[4] != null)
				result += "<li><a href='" + o[4] + "' target=\"rightFrame\">"
						+ o[1] + "</a></li>";
		}

		return result;
	}

	private void showdetail(String id) {

	}

	public static String creLecl(String roleno, String menu_parent) {

		String result = "";
		DaoFactory df = new DaoFactory();
		if (menu_parent == null) {
			String sql = "select a.menu_no from ftp.sys_menu a join ftp.sys_role_menu b on a.menu_no=b.menu_no  "
					+ " where menu_lvl=1 and menu_stats = '1' and b.role_no='"
					+ roleno
					+ "' order by a.menu_no";
			List list = df.query1(sql, null);
			if (list.size() == 0) {
				menu_parent = null;
			} else {
				menu_parent = list.get(0).toString();
			}

		}

		String sql = "select a.menu_no,menu_name,menu_parent,menu_lvl,menu_url from ftp.sys_menu a join ftp.sys_role_menu b on a.menu_no=b.menu_no  "
				+ " where menu_lvl=3 and menu_stats = '1' and a.menu_no like '"
				+ menu_parent
				+ "%' and b.role_no='" + roleno + "' order by a.menu_no";
		List list = df.query1(sql, null);
		result += "<link type=\"text/css\" href=\"css/styles.css\" rel=\"stylesheet\">";
		result += "</head><body>";
		result += "<div class=\"left_menu\" style=\"height:100%\"><ul>";

		if (list == null || list.size() == 0) {
			result += "</ul></div>";
			result += "</body></html>";
			return result;
		}
		for (int i = 0; i < list.size(); i++) {
			Object[] o = (Object[]) list.get(i);
			if (o != null && o.length > 4 && o[4] != null)
				result += "<li id=\"l" + i + "\"><a id=\"a" + i + "\" name=\"" + o[1] + "\" link=\"" + o[4] + "\" href=\"javascript:doClick('" + o[1] + "','" + o[4] + "','l" + i + "')\">" + o[1]
						+ "</a></li>";
		}

		result += "</ul></div>";
		result += "</body></html>";
		return result;
	}

	/**
	 * 
	 * @param roleno
	 * @return res
	 */
	@SuppressWarnings("unchecked")
	public static String creHeader(String roleno) {

		String res = "";
		DaoFactory df = new DaoFactory();
		String sql = "select a.menu_no,menu_name,menu_parent,menu_lvl,menu_url from ftp.sys_menu a join ftp.sys_role_menu b on a.menu_no=b.menu_no  "
				+ " where menu_lvl=1 and b.role_no='"
				+ roleno
				+ "' order by a.menu_no";
		List list = df.query1(sql, null);
		for (int i = 0; i < list.size(); i++) {
			Object[] o = (Object[]) list.get(i);
			res += "<li class=\"nav_n\" id=\"href" + (i + 1)
					+ "\"><a href=\"left_center.jsp?menu_parent=" + o[0]
					+ "\" target=\"centerFrame\" onclick=\"changed('" + (i + 1)
					+ "')\">" + o[1] + "</a></li>";

		}
		return res;
	}

	/**
	 * ��ȡһ���˵�
	 * 
	 * @param roleno
	 * @return
	 */
	public static List getFirstMenu(String roleno) {
		DaoFactory df = new DaoFactory();
		String sql = "select  substr(a.menu_no,1,1),a.menu_name  from ftp.sys_menu a join ftp.sys_role_menu b on a.menu_no=b.menu_no  "
				+ " where a.menu_lvl=1 and a.menu_stats='1' and b.role_no='" + roleno + "'"
				+ " and a.menu_seqc is not null "
				+ " order by a.menu_seqc";
		return df.query1(sql, null);
	}

	public static String creTwolevel(String roleno, String menu_parent) {

		String result = "";
		if ("".equals(menu_parent))
			menu_parent = "A";
		DaoFactory df = new DaoFactory();
		String sql = "select a.menu_no,menu_name,menu_parent,menu_lvl,menu_url from ftp.sys_menu a join ftp.sys_role_menu b on a.menu_no=b.menu_no  "
				+ " where menu_lvl=2 and a.menu_no like '"
				+ menu_parent
				+ "%' and b.role_no='" + roleno + "' order by a.menu_no";

		String sql2 = "select a.menu_no,menu_name,menu_parent,menu_lvl,menu_url from ftp.sys_menu a join ftp.sys_role_menu b on a.menu_no=b.menu_no  "
				+ " where menu_lvl=3 and a.menu_no like '"
				+ menu_parent
				+ "%' and b.role_no='" + roleno + "' order by a.menu_no";

		List list = df.query1(sql, null);
		for (int i = 0; i < list.size(); i++) {
			Object[] o = (Object[]) list.get(i);
			if (o != null && o.length > 4 && o[4] != null)
				result += "<li><a href='" + o[4] + "' target=\"rightFrame\">"
						+ o[1] + "</a></li>";
		}

		return result;
	}
	
	/**
	 * ��ȡ�����������˵�
	 * @param roleno
	 * @return
	 */
	public static Map<String, String[][]> getChildMenu(String menuParent, String roleNo) {
		DaoFactory df = new DaoFactory();   
		Map<String, String[][]> result = (Map<String, String[][]>)new ListOrderedMap();//hashmap��keyset����ȡ�������ǰ��Ž�ȥ��˳���ǻ��ҵ�
		//����ǵ�һ�ε�½���ҳ��
		if (menuParent == null) {
			// ��ȡ��һ��һ���˵�����ΪmenuParent
			String sql = "select  a.menu_no from ftp.sys_menu a join ftp.sys_role_menu b on a.menu_no=b.menu_no  "
					+ " where a.menu_lvl=1 and b.role_no='" + roleNo + "' and a.menu_stats='1' order by a.menu_no asc";
			List list = df.query1(sql, null);
			if (list == null || list.size() == 0) {
				return null;
			} else {
				menuParent = list.get(0).toString();
			}
		}
		//��ȡ�����˵�
		String sql = "select a.menu_no,menu_name from ftp.sys_menu a join ftp.sys_role_menu b on a.menu_no=b.menu_no  "
				+ " where a.menu_lvl=2 and a.menu_stats='1' and a.menu_parent = '" + menuParent
				+ "' and b.role_no='" + roleNo + "' order by a.menu_no";
		List list = df.query1(sql, null);
		if (list == null || list.size() == 0) {
			return null;
		}
		for (Object object : list) {
			Object[] o = (Object[])object;
			//��ȡ��Ӧ�������˵�
			String sql2 = "select a.menu_no,menu_name,menu_url from ftp.sys_menu a join ftp.sys_role_menu b on a.menu_no=b.menu_no  "
				+ " where a.menu_lvl=3 and a.menu_parent = '" + o[0]
				+ "' and b.role_no='" + roleNo + "' and a.menu_stats = '1' order by a.menu_no";

			List list2 = df.query1(sql2, null);
			if (list2 == null || list2.size() == 0) {
				result.put(o[1].toString(), null);
			}
			String[][] menu3 = new String[list2.size()][3];//�����˵���š� �˵�����url
			for (int i = 0; i < list2.size(); i++) {
				Object object2 = list2.get(i);
				Object[] o2 = (Object[])object2;
				menu3[i][0] = o2[0].toString();
				menu3[i][1] = o2[1].toString();
				menu3[i][2] = o2[2].toString();
			}
			result.put(o[1].toString(), menu3);
			
		}
		return result;
	}
}
