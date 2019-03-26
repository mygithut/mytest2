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
 * @author  孙红玉
 * 
 * 导出excel的公用方法
 * 
 * @date    2013-11-5 上午10:10:46
 * 
 * @company 东华软件股份公司金融风险产品部
 */
public class ExcelExport {
    private  HSSFWorkbook workbook;
    private  HSSFSheet sheet;
    private  HSSFRow row;
    public HSSFCellStyle centerBoldStyle;
    public HSSFCellStyle centerNormalStyle;
    public HSSFCellStyle headCenterNormalStyle;//加了背景色
    public HSSFCellStyle rightNormalStyle;
    public HSSFCellStyle rightBoldStyle;
    public HSSFCellStyle rightBoldMoneyStyle;
    public HSSFCellStyle rightNormalMoneyStyle;
    public HSSFCellStyle rightBoldDoubleStyle;
    public HSSFCellStyle rightNormalDoubleStyle;
    
    
    public ExcelExport(HSSFWorkbook workbook, HSSFSheet sheet) {
		this.workbook = workbook;
		this.sheet = sheet;
		this.sheet.setDefaultColumnWidth(16);//单元格的默认宽度
		
		// 居中加粗样式
		this.centerBoldStyle = getBodyStyle(HSSFCellStyle.ALIGN_CENTER,
				HSSFFont.BOLDWEIGHT_BOLD, null);
		// 居中不加粗样式
		this.centerNormalStyle = getBodyStyle(HSSFCellStyle.ALIGN_CENTER,
				HSSFFont.BOLDWEIGHT_NORMAL, null);
		// 居中不加粗样式，加了背景色
		this.headCenterNormalStyle = getHeadBckColorStyle(HSSFCellStyle.ALIGN_CENTER,
				HSSFFont.BOLDWEIGHT_BOLD, null);
		// 居右不加粗样式
		this.rightNormalStyle = getBodyStyle(HSSFCellStyle.ALIGN_RIGHT,
				HSSFFont.BOLDWEIGHT_NORMAL, null);
		// 居右加粗样式
		this.rightBoldStyle = getBodyStyle(HSSFCellStyle.ALIGN_RIGHT,
				HSSFFont.BOLDWEIGHT_BOLD, null);
		// 居右加粗货币样式
		this.rightBoldMoneyStyle = getBodyStyle(HSSFCellStyle.ALIGN_RIGHT,
				HSSFFont.BOLDWEIGHT_BOLD, "money");
		// 居右不加粗货币样式
		this.rightNormalMoneyStyle = getBodyStyle(HSSFCellStyle.ALIGN_RIGHT,
				HSSFFont.BOLDWEIGHT_NORMAL, "money");
		// 居右加粗浮点样式
		this.rightBoldDoubleStyle = getBodyStyle(HSSFCellStyle.ALIGN_RIGHT,
				HSSFFont.BOLDWEIGHT_BOLD, "double");
		// 居右不加粗浮点样式
		this.rightNormalDoubleStyle = getBodyStyle(HSSFCellStyle.ALIGN_RIGHT,
				HSSFFont.BOLDWEIGHT_NORMAL, "double");

	}

    /**
	 * 创建workbook,将workbook写入到InputStream
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
	 * 增加一行
	 * 
	 * @param index 行号
	 */
	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}

	/**
	 * 设置单元格
	 * 
	 * @param index 列号
	 * @param value 单元格填充值
	 * @param style 单元格样式
	 */
	public void setCell(int index, String value,HSSFCellStyle style) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
	/**
	 * 设置单元格
	 * 
	 * @param index 列号
	 * @param value 单元格填充值
	 * @param style 单元格样式
	 */
	public void setCell(int index, double value,HSSFCellStyle style) {
		HSSFCell cell = this.row.createCell(index);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}
	/**
	 * 设置excel第一行表头的样式
	 * @param workbook
	 * @return
	 */
	public HSSFCellStyle getHeaderStyle() {
		// 生成一个样式
		HSSFCellStyle style = this.workbook.createCellStyle();
		// 设置这些样式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		//==========
		//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		// 生成一个字体
		HSSFFont font = this.workbook.createFont();
		font.setFontHeightInPoints((short) 14);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontName("Times New Roman");
		// 把字体应用到当前的样式
		style.setFont(font);
		return style;
	}
	/**
	 * 设置excel第一行表头的样式
	 * @param workbook
	 * @return
	 */
	public HSSFCellStyle getHeaderLeftStyle() {
		// 生成一个样式
		HSSFCellStyle style = this.workbook.createCellStyle();
		//style.setFillBackgroundColor((short)200);
		// 设置这些样式
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);//水平居左
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		//==================
		//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		//====================
		// 生成一个字体
		HSSFFont font = this.workbook.createFont();
		font.setFontHeightInPoints((short) 14);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontName("Times New Roman");
		// 把字体应用到当前的样式
		style.setFont(font);
		
		return style;
	}
	/**
	 * 设置单元格第二行表头样式
	 * @param workbook
	 * @return
	 */
	public HSSFCellStyle getTitleStyle() {
		HSSFCellStyle titleStyle = this.workbook.createCellStyle();
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中/居右/居左
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		//======================
		/*titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);*/
		//==================
		//背景颜色
		//titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		//HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
		//palette.setColorAtIndex((short)9, (byte)216, (byte)216,(byte)216);//自定义颜色
		//titleStyle.setFillForegroundColor((short)9);//前景色
		//titleStyle.setFillBackgroundColor(HSSFCellStyle.THICK_FORWARD_DIAG);
		
		// 生成一个字体
		HSSFFont font = this.workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//是否加粗
		font.setFontName("宋体");
		font.setFontName("Times New Roman");
		// 把字体应用到当前的样式
		titleStyle.setFont(font);
		
		return titleStyle;
	}
	/**
	 * 设置表格内单元格样式
	 * @param workbook
	 * @param align 居中状态
	 * @param boldweight 字体加粗状态
	 * @param valueType 字段类型
	 * @return
	 */
	public HSSFCellStyle getBodyStyle(short align, short boldweight, String valueType) {
		HSSFCellStyle bodyStyle = this.workbook.createCellStyle();
		bodyStyle.setAlignment(align);//居中/居右/居左
		bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		
		//边框样式
		setBorderStyle(workbook, bodyStyle);
		
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setBoldweight(boldweight);//是否加粗
		font.setFontName("宋体");
		font.setFontName("Times New Roman");
		//单元格显示样式
		if(valueType != null) {
			if(valueType.equals("money")) {
				bodyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
			}else if(valueType.equals("double")) {
				bodyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));//只支持保留2位小数
			}
		}
		// 把字体应用到当前的样式
		bodyStyle.setFont(font);
		
		return bodyStyle;
	}
	/**
	 * 设置表头的样式，比普通的居中格式多加了背景颜色
	 */
	
	public HSSFCellStyle getHeadBckColorStyle(short align, short boldweight, String valueType) {
		HSSFCellStyle bodyStyle = this.workbook.createCellStyle();
		bodyStyle.setAlignment(align);//居中/居右/居左
		bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		bodyStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		HSSFPalette palette = ((HSSFWorkbook) workbook).getCustomPalette();
		palette.setColorAtIndex((short)10, (byte)217, (byte)217,(byte)217);//自定义颜色
		bodyStyle.setFillForegroundColor((short)10);//前景色
		//边框样式
		setBorderStyle(workbook, bodyStyle);
		
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setBoldweight(boldweight);//是否加粗
		font.setFontName("宋体");
		font.setFontName("Times New Roman");
		//单元格显示样式
		if(valueType != null) {
			if(valueType.equals("money")) {
				bodyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
			}else if(valueType.equals("double")) {
				bodyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.000"));
			}
		}
		// 把字体应用到当前的样式
		bodyStyle.setFont(font);
		
		return bodyStyle;
	}
	
	/**
	 * 设置单元格边框
	 * @param workbook
	 * @param style
	 */
	public static void setBorderStyle(HSSFWorkbook workbook, HSSFCellStyle style) {
		//边框宽度
		style.setBorderBottom((short)1);
		style.setBorderLeft((short)1);
		style.setBorderRight((short)1);
		style.setBorderTop((short)1);
		style.setWrapText(true);
		HSSFPalette cellPalette = ((HSSFWorkbook) workbook).getCustomPalette(); // 创建颜色 这里创建的是黑色边框
		cellPalette.setColorAtIndex(HSSFColor.BLACK.index, (byte)0, (byte)0,(byte)0); // 设置 RGB
		style.setLeftBorderColor(HSSFColor.BLACK.index); // 设置边框颜色
		style.setRightBorderColor(HSSFColor.BLACK.index);
		style.setTopBorderColor(HSSFColor.BLACK.index);
		style.setBottomBorderColor(HSSFColor.BLACK.index);
	}
}
