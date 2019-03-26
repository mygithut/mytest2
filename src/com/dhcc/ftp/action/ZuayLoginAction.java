package com.dhcc.ftp.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.filter.SessionUserListener;
import com.dhcc.ftp.util.FtpUtil;


public class ZuayLoginAction extends BoBuilder {
	HttpServletRequest request = ServletActionContext.getRequest();
	
	public String execute() throws Exception {
		return super.execute();
	}
	//��¼
	public String login() throws Exception {
		ServletActionContext.getResponse().setContentType("text/plain;charset=UTF-8");
		Object object=userBo.getUserBean(this.username);
		TelMst telMst=(TelMst)object;
		if(telMst==null){//�û�������
			ServletActionContext.getResponse().getWriter().print("error1");
		}
		else if(telMst!=null){
			String telNo=telMst.getTelNo();
			if(telNo==null||telNo.trim().length()==0||!telMst.getFlag().equalsIgnoreCase("1")){
			//if(telNo==null||telNo.trim().length()==0){
				ServletActionContext.getResponse().getWriter().print("error1");
			}
			else if(telNo!=null&&telNo.trim().length()!=0&&telMst.getFlag().equalsIgnoreCase("1")){
			//else if(telNo!=null&&telNo.trim().length()!=0){
				String pwd=telMst.getPasswd().trim();
				if(!this.password.equalsIgnoreCase(pwd)){//�������
					ServletActionContext.getResponse().getWriter().print("error2");
				}else{	
					//��֤���û�ID���Ƿ��Ѿ���¼����ǰ�û��Ƚ��ѵ�¼��ϵͳ�ľ�̬�����е�ֵ���Ƿ���ڡ�  
			        Boolean hasLogin = SessionUserListener.checkIfHasLogin(telMst);  
			        // ����ظ���¼�����ƶˣ����أ���������<�Ƿ��ߵ���ǰ�û�>
			        if (hasLogin) {  
						ServletActionContext.getResponse().getWriter().print("error3");
			        } else {  
			    		setSession(telMst);//����session
					}
					return null;
				}
				
			}
		}
		
		return null;
		
	}
	
	/**
	 * �Ƴ���ǰ�û���session�������е�¼
	 * @return
	 * @throws Exception
	 */
	public String removeSessionAndLogin() throws Exception {
		SessionUserListener.removeUserSession(username); //�Ƴ���ǰ�û���session
		
		TelMst telMst=(TelMst)userBo.getUserBean(username);
		
		setSession(telMst);//�������û���session
        return null;
	}
	
	/**
	 * ��������ݷ��õ�session��
	 * @param telMst
	 */
	public void setSession(TelMst telMst) {
		HttpSession session = ServletActionContext.getRequest().getSession();
		//����session�����������
		session.setMaxInactiveInterval(28800);//��λs��28800s=8Сʱ����Ȼ�˴�����Ϊ8Сʱ����ʵ������tomcatҲ��session���ƣ���Ĭ��Ϊ��Сʱ���������������sessionʱ��Ϊ��Сʱ��
        
    	session.setAttribute("userBean", telMst); 

		//initMenu(telMst,session);
		session.setAttribute("roleno", telMst.getRoleMst().getRoleNo());
		session.setAttribute("userid", username);
		//Integer ftpSetNum = ftpSystemInitialBO.getFtpSetNum(request);//��ȡ�ж��ٸ�������δ���ж��۲�������
		//session.setAttribute("ftpSetNum", ftpSetNum);
        SessionUserListener.addUserSession(ServletActionContext.getRequest().getSession());//�����û���session������ 
	}
	
	public Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
		
	public String getImage() throws Exception{
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		int width = 60, height = 20;
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sRand += rand;
			g.setColor(new Color(20 + random.nextInt(110), 20 + random
					.nextInt(110), 20 + random.nextInt(110)));
			g.drawString(rand, 13 * i + 6, 16);
		}
		session.setAttribute("rand", sRand);
		g.dispose();
		ImageIO.write(image, "JPEG", response.getOutputStream());
		
		return null;
	}
	public String valid() throws Exception{
		
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpSession session = ServletActionContext.getRequest().getSession();
		
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control","no-cache");
		String rand = (String)session.getAttribute("rand"); 
		String input = ServletActionContext.getRequest().getParameter("rand"); 
		String res="";
		
		PrintWriter pw = ServletActionContext.getResponse().getWriter();
		
		if(rand.equals(input)){ 
			res="1";
		}else{ 
			res="0";
		} 
		pw.write(res);
		pw.flush();
		pw.close();
		
		return null;
	}

	private String username;
	private String password;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
