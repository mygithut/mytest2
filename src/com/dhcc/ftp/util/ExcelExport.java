package com.dhcc.ftp.util;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * @author  �����
 * 
 * ����excel�Ĺ��÷���
 * 
 * @date    2013-11-5 ����10:10:46
 * 
 * @company ��������ɷݹ�˾���ڷ��ղ�Ʒ��
 */
public class ExcelExport {
    private  HSSFWorkbook workbook;
    private  HSSFSheet sheet;
    private  HSSFRow row;
    public HSSFCellStyle centerBoldStyle;
    public HSSFCellStyle centerNormalStyle;
    public HSSFCellStyle headCenterNormalStyle;//���˱���ɫ
    public HSSFCellStyle rightNormalStyle;
    public HSSFCellStyle rightBoldStyle;
    public HSSFCellStyle rightBoldMoneyStyle;
    public HSSFCellStyle rightNormalMoneyStyle;
    public HSSFCellStyle rightBoldDoubleStyle;
    public HSSFCellStyle rightNormalDoubleStyle;
    
    
    public ExcelExport(HSSFWorkbook workbook, HSSFSheet sheet) {
		this.workbook = workbook;
		this.sheet = sheet;
		this.sheet.setDefaultColumnWidth(16);//��Ԫ���Ĭ�Ͽ��
		
		// ���мӴ���ʽ
		this.centerBoldStyle = getBodyStyle(HSSFCellStyle.ALIGN_CENTER,
				HSSFFont.BOLDWEIGHT_BOLD, null);
		// ���в��Ӵ���ʽ
		this.centerNormalStyle = getBodyStyle(HSSFCellStyle.ALIGN_CENTER,
				HSSFFont.BOLDWEIGHT_NORMAL, null);
		// ���в��Ӵ���ʽ�����˱���ɫ
		this.headCenterNormalStyle = getHeadBckColorStyle(HSSFCellStyle.ALIGN_CENTER,
				HSSFFont.BOLDWEIGHT_BOLD, null);
		// ���Ҳ��Ӵ���ʽ
		this.rightNormalStyle = getBodyStyle(HSSFCellStyle.ALIGN_RIGHT,
				HSSFFont.BOLDWEIGHT_NORMAL, null);
		// ���ҼӴ���ʽ
		this.rightBoldStyle = getBodyStyle(HSSFCellStyle.ALIGN_RIGHT,
				HSSFFont.BOLDWEIGHT_BOLD, null);
		// ���ҼӴֻ�����ʽ
		this.rightBoldMoneyStyle = getBodyStyle(HSSFCellStyle.ALIGN_RIGHT,
				HSSFFont.BOLDWEIGHT_BOLD, "money");
		// ���Ҳ��Ӵֻ�����ʽ
		this.rightNormalMoneyStyle = getBodyStyle(HSSFCellStyle.ALIGN_RIGHT,
				HSSFFont.BOLDWEIGHT_NORMAL, "money");
		// ���ҼӴָ�����ʽ
		this.rightBoldDoubleStyle = getBodyStyle(HSSFCellStyle.ALIGN_RIGHT,
				HSSFFont.BOLDWEIGHT_BOLD, "double");
		// ���Ҳ��Ӵָ�����ʽ
		this.rightNormalDoubleStyle = getBodyStyle(HSSFCellStyle.ALIGN_RIGHT,
				HSSFFont.BOLDWEIGHT_NORMAL, "double");

	}

    /**
	 * ����workbook,��workbookд�뵽InputStream
	 * @param workbook
	 * @param fileName
	 */
	public static void workbookInputStream(HttpServletResponse response, HSSFWorkbook workbook,String fileName) {     
		 try {
	            byte[] fileNameByte = (fileName + ".xls").getBytes("GBK");
	            String filename = new String(fileNameByte, "ISO8859-1");
	          
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    workbook.write(baos);
			    baos.flush();       
			    byte[] bytes = baos.toByteArray();   
	            response.setContentType("application/x-msdownload");
	            response.setContentLength(bytes.length);
	            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
	            response.getOutputStream().write(bytes);
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	}

	/**
	 * ����һ��
	 * 
	 * @param index �к�
	 */
	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}

	/**
	 * ���õ�Ԫ��
	 * 
	 * @param index �к�
	 * @param value ��Ԫ�����ֵ
	 * @param style ��Ԫ����ʽ
	 */
	public void setCell(int index, String value,HSSFCellStyle style) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
	/**
	 * ���õ�Ԫ��
	 * 
	 * @param index �к�
	 * @param value ��Ԫ�����ֵ
	 * @param style ��Ԫ����ʽ
	 */
	public void setCell(int index, double value,HSSFCellStyle style) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
	/**
	 * ����excel��һ�б�ͷ����ʽ
	 * @param workbook
	 * @return
	 */
	public HSSFCellStyle getHeaderStyle() {
		// ����һ����ʽ
		HSSFCellStyle style = this.workbook.createCellStyle();
		// ������Щ��ʽ
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//ˮƽ����
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ��ֱ����
		//==========
		//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		// ����һ������
		HSSFFont font = this.workbook.createFont();
		font.setFontHeightInPoints((short) 14);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("����");
		font.setFontName("Times New Roman");
		// ������Ӧ�õ���ǰ����ʽ
		style.setFont(font);
		return style;
	}
	/**
	 * ����excel��һ�б�ͷ����ʽ
	 * @param workbook
	 * @return
	 */
	public HSSFCellStyle getHeaderLeftStyle() {
		// ����һ����ʽ
		HSSFCellStyle style = this.workbook.createCellStyle();
		//style.setFillBackgroundColor((short)200);
		// ������Щ��ʽ
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);//ˮƽ����
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ��ֱ����
		//==================
		//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		//====================
		// ����һ������
		HSSFFont font = this.workbook.createFont();
		font.setFontHeightInPoints((short) 14);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("����");
		font.setFontName("Times New Roman");
		// ������Ӧ�õ���ǰ����ʽ
		style.setFont(font);
		
		return style;
	}
	/**
	 * ���õ�Ԫ��ڶ��б�ͷ��ʽ
	 * @param workbook
	 * @return
	 */
	public HSSFCellStyle getTitleStyle() {
		HSSFCellStyle titleStyle = this.workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//����/����/����
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ��ֱ����
		//======================
		/*titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);*/
		//==================
		//������ɫ
		//titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
		//palette.setColorAtIndex((short)9, (byte)216, (byte)216,(byte)216);//�Զ�����ɫ
		//titleStyle.setFillForegroundColor((short)9);//ǰ��ɫ
		//titleStyle.setFillBackgroundColor(HSSFCellStyle.THICK_FORWARD_DIAG);
		
		// ����һ������
		HSSFFont font = this.workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//�Ƿ�Ӵ�
		font.setFontName("����");
		font.setFontName("Times New Roman");
		// ������Ӧ�õ���ǰ����ʽ
		titleStyle.setFont(font);
		
		return titleStyle;
	}
	/**
	 * ���ñ���ڵ�Ԫ����ʽ
	 * @param workbook
	 * @param align ����״̬
	 * @param boldweight ����Ӵ�״̬
	 * @param valueType �ֶ�����
	 * @return
	 */
	public HSSFCellStyle getBodyStyle(short align, short boldweight, String valueType) {
		HSSFCellStyle bodyStyle = this.workbook.createCellStyle();
		bodyStyle.setAlignment(align);//����/����/����
		bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ��ֱ����
		
		//�߿���ʽ
		setBorderStyle(workbook, bodyStyle);
		
		// ����һ������
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setBoldweight(boldweight);//�Ƿ�Ӵ�
		font.setFontName("����");
		font.setFontName("Times New Roman");
		//��Ԫ����ʾ��ʽ
		if(valueType != null) {
			if(valueType.equals("money")) {
				bodyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
			}else if(valueType.equals("double")) {
				bodyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));//ֻ֧�ֱ���2λС��
			}
		}
		// ������Ӧ�õ���ǰ����ʽ
		bodyStyle.setFont(font);
		
		return bodyStyle;
	}
	/**
	 * ���ñ�ͷ����ʽ������ͨ�ľ��и�ʽ����˱�����ɫ
	 */
	
	public HSSFCellStyle getHeadBckColorStyle(short align, short boldweight, String valueType) {
		HSSFCellStyle bodyStyle = this.workbook.createCellStyle();
		bodyStyle.setAlignment(align);//����/����/����
		bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ��ֱ����
		bodyStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
		palette.setColorAtIndex((short)10, (byte)217, (byte)217,(byte)217);//�Զ�����ɫ
		bodyStyle.setFillForegroundColor((short)10);//ǰ��ɫ
		//�߿���ʽ
		setBorderStyle(workbook, bodyStyle);
		
		// ����һ������
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setBoldweight(boldweight);//�Ƿ�Ӵ�
		font.setFontName("����");
		font.setFontName("Times New Roman");
		//��Ԫ����ʾ��ʽ
		if(valueType != null) {
			if(valueType.equals("money")) {
				bodyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
			}else if(valueType.equals("double")) {
				bodyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.000"));
			}
		}
		// ������Ӧ�õ���ǰ����ʽ
		bodyStyle.setFont(font);
		
		return bodyStyle;
	}
	
	/**
	 * ���õ�Ԫ��߿�
	 * @param workbook
	 * @param style
	 */
	public static void setBorderStyle(HSSFWorkbook workbook, HSSFCellStyle style) {
		//�߿���
		style.setBorderBottom((short)1);
		style.setBorderLeft((short)1);
		style.setBorderRight((short)1);
		style.setBorderTop((short)1);
		style.setWrapText(true);
		HSSFPalette cellPalette = ((HSSFWorkbook) workbook).getCustomPalette(); // ������ɫ ���ﴴ�����Ǻ�ɫ�߿�
		cellPalette.setColorAtIndex(HSSFColor.BLACK.index, (byte)0, (byte)0,(byte)0); // ���� RGB
		style.setLeftBorderColor(HSSFColor.BLACK.index); // ���ñ߿���ɫ
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
	}
}
