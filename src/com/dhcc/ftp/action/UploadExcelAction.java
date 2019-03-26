package com.dhcc.ftp.action;
/**
 * <p>
 * Title: Excel文件上传
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Company: 东华软件股份公司金融事业部
 * </p>
 * 
 * @author 孙红玉
 * 
 * @date july 22, 2011 3:29:33 PM
 * 
 * @version 1.0
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.dhcc.ftp.util.CommonFunctions;
import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.util.DateUtil;

public class UploadExcelAction  extends BaseAction {
	private File ClientExcelFile;           //与jsp表单中的名称对应   
	private String ClientExcelFileFileName;   //文件名称
    private String ClientExcelFileContentType;//文件路径
    private String CurUri;


	public String getCurUri() {
		return CurUri;
	}
	public void setCurUri(String curUri) {
		CurUri = curUri;
	}
	public String getClientExcelFileFileName(){
        return ClientExcelFileFileName;
    }
    public void setClientExcelFileFileName(String ClientExcelFileFileName){
        this.ClientExcelFileFileName = ClientExcelFileFileName;
    }

    public File getClientExcelFile(){
        return ClientExcelFile;
    }

    public void setClientExcelFile(File ClientExcelFile){
        this.ClientExcelFile = ClientExcelFile;
    }

    public void setClientExcelFileContentType(String ClientExcelFileContentType){
        this.ClientExcelFileContentType=ClientExcelFileContentType;
    }
    
    public String getClientExcelFileContentType(){
    	return ClientExcelFileContentType;
    }



	@Override
	public String execute() throws Exception {
		HttpServletResponse response  = ServletActionContext.getResponse();
		//request.setCharacterEncoding("GBK"); 
        //response.setContentType("text/html;chaset=GBK");
        PrintWriter out = response.getWriter();
		//取得web应用在服务区上的物理路径
		String docBasePath =ServletActionContext.getServletContext().getRealPath("/");
		String UploadedExcelPath = docBasePath + "ExcelFile";
		System.out.println("UploadedExcelPath:" + UploadedExcelPath);
		
		//检查上载文件的目录是否存在
        File fileDir = new File(UploadedExcelPath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();//在指定路径下创建目录
        }
		
        //String NowStr = String.valueOf(DateUtil.GetDBSysDate());//获取当前时间
		String NowStr = "";
		ClientExcelFileFileName=ClientExcelFile.getName();
		String name = ClientExcelFileFileName.substring(0,ClientExcelFileFileName.indexOf("."));
		String savePath = UploadedExcelPath + "\\" +name+".xls";
		File savefile = new File(savePath);
		//检查上载文件是否存在
        if (savefile.exists()) {
            savefile.delete();
        }
        //保存文件
        try{
			FileOutputStream fos = new FileOutputStream(savefile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			
			FileInputStream fis = new FileInputStream(ClientExcelFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			byte[] buffer = new byte[1024];
			int length = -1;
			while((length = fis.read(buffer))!=-1){
				bos.write(buffer, 0, length);
			}
			bos.close();
			fos.close();
			bis.close();
			fis.close();
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
        try {
			// System.out.println("-write-");
			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");

			PrintWriter pw = ServletActionContext.getResponse().getWriter();
			pw.write(savePath);
			pw.flush();
			pw.close();
			// response.getWriter().write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	   // return null;
	}
	
}
