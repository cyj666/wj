package com.hd.tool;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hd.pojo.NyLon;

public class ExcelUtils {
	
	/**
	 * 设置头标题的格式
	 * @param cellStyle		单元格的格式
	 * @param fontStyle		单元格字体的格式
	 */
	public static void setHeaderCellStyle(XSSFCellStyle cellStyle, XSSFFont fontStyle) {
		//先设置字体样式，在设置单元格样式
		fontStyle.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		fontStyle.setFontName("Times New Roman");
		fontStyle.setFontHeightInPoints((short)16);
		
		cellStyle.setFont(fontStyle);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	}
	
	/**
	 * 设置第二行头标题
	 * @param cellStyle		单元格的格式
	 * @param fontStyle		单元格字体的格式
	 */
	public static void setCellStyle(XSSFCellStyle cellStyle, XSSFFont fontStyle) {
		fontStyle.setFontName("宋体");
		fontStyle.setFontHeightInPoints((short)10);
		
		cellStyle.setFont(fontStyle);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	}
	
	public static void setDoubleCellStyle1(XSSFCellStyle cellStyle, XSSFFont fontStyle,XSSFWorkbook book) {
		XSSFDataFormat xdf = book.createDataFormat();
		fontStyle.setFontName("宋体");
		fontStyle.setFontHeightInPoints((short)12 );
		
		cellStyle.setFont(fontStyle);
		cellStyle.setDataFormat(xdf.getFormat("0.00"));
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	}
	
	public static void setDoubleCellStyle2(XSSFCellStyle cellStyle, XSSFFont fontStyle,XSSFWorkbook book) {
		XSSFDataFormat xdf = book.createDataFormat();
		fontStyle.setFontName("宋体");
		fontStyle.setFontHeightInPoints((short)12 );
		
		cellStyle.setFont(fontStyle);
		cellStyle.setDataFormat(xdf.getFormat("0.0"));
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
	}
	
	/**
	 * 将单元格合并
	 * @param sheet		一张表格
	 */
	public static void setRangeCell(XSSFSheet sheet,int col) {
		CellRangeAddress cra = new CellRangeAddress(0, 0, 0, col);
		sheet.addMergedRegion(cra);
		for(int i=0; i<col-5; i++) {
			cra = new CellRangeAddress(1, 2, i, i);
			sheet.addMergedRegion(cra);
		}
		cra = new CellRangeAddress(1, 1, col-5, col-3);
		sheet.addMergedRegion(cra);
	}
	
	/**
	 * 设置表格第二行的头标签
	 * @param sheet			一张表格
	 * @param headers		头标签需要的字段名称
	 * @param cellStyle		单元格的格式
	 * @param fontStyle		单元格字体的格式
	 */
	public static void setSecondRowStyle(XSSFSheet sheet, String[] headers, XSSFCellStyle cellStyle, XSSFFont fontStyle) {
		XSSFRow row = sheet.createRow(1);
		for(int i=0; i<headers.length; i++) {
			XSSFCell cell = row.createCell(i);
			XSSFRichTextString text1 = new XSSFRichTextString(headers[i]);
			cell.setCellValue(text1);
			ExcelUtils.setCellStyle(cellStyle, fontStyle);
			cell.setCellStyle(cellStyle);
		}
	}
	
	/**
	 * 设置表格第三行的头标签
	 * @param sheet			一张表格
	 * @param headers		头标签需要的字段名称
	 * @param cellStyle		单元格的格式
	 * @param fontStyle		单元格字体的格式
	 */
	public static void setThirdRowStyle(XSSFSheet sheet, String[] headers, XSSFCellStyle cellStyle, XSSFFont fontStyle,int col) {
		XSSFRow row = sheet.createRow(2);
		int index = 0;
		for(int i=col-5; i<col-2; i++) {
			XSSFCell cell = row.createCell(i);
			XSSFRichTextString text1 = new XSSFRichTextString(headers[index]);
			index++;
			cell.setCellValue(text1);	//9 10 11	11 12 13
			ExcelUtils.setCellStyle(cellStyle, fontStyle);
			cell.setCellStyle(cellStyle);
		}
	}
	
	/**
	 * 将数据添加到表格中
	 * @param nlList		数据的集合
	 * @param sheet			一张表格
	 * @param cellStyle		单元格的格式
	 * @param fontStyle		单元格字体的格式
	 */
	public static void setExcel(List<NyLon> nlList, XSSFWorkbook book,XSSFSheet sheet, XSSFCellStyle cellStyle, XSSFFont fontStyle,String oneChar) {
		int index = 2;
		
		try {
			for (NyLon nl : nlList) {
				index++;
				//跳过的次数
				int step = 0;
				XSSFRow row = sheet.createRow(index);
				Field[] fields = nl.getClass().getDeclaredFields();
				for(int i=0; i<fields.length; i++) {
					if(i==0) {
						step++;
						continue;
					}
					if(("F".equals(oneChar) || "P".equals(oneChar) || "H".equals(oneChar)) && (i==8 || i==9)) {
						step++;
						continue;
					}
					XSSFCell cell = row.createCell(i-step);
					
					Field field = fields[i];
					String fieldName = field.getName();
					String methodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
					Class<?> nlClass = nl.getClass();
					
					Method getMethod = nlClass.getMethod(methodName, new Class[] {});
					
					Object value = getMethod.invoke(nl, new Object[] {});
					// 判断值的类型后进行强制类型转换  
                    String textValue = null;  
                    double d = 0;
                    boolean flag = true;
                    String reg = "^[0-9]+$";
                    //System.out.println(value.toString().matches(reg));
                    if(value.toString().matches(reg)) {
                    	if(i==5 || i==6 || i==11) {
                    		d = Double.parseDouble(value.toString())/100;
                    		if(d!=0) {
                    			setDoubleCellStyle1(cellStyle, fontStyle, book);
                        		cell.setCellStyle(cellStyle);
                        		cell.setCellValue(d);
                    		}else {
                    			cell.setCellValue("");
                    			cell.setCellStyle(cellStyle);
                    		}
                    	}else if(i==12) {
                    		int val = Integer.parseInt(value.toString());
                    		if(val!=0) {
                    			XSSFCellStyle cellStyle1 = book.createCellStyle();
                    			setCellStyle(cellStyle1, fontStyle);
                    			cell.setCellStyle(cellStyle1);
                    			cell.setCellValue(val);
                    		}else {
                    			cell.setCellValue("");
                    			cell.setCellStyle(cellStyle);
                    		}
                    	}else if(i == 4 || i == 7 || i == 8 || i == 9 || i == 10) {
                    		d = Double.parseDouble(value.toString())/10;
                    		if(d!=0) {
                    			XSSFCellStyle cellStyle1 = book.createCellStyle();
                    			setDoubleCellStyle2(cellStyle1, fontStyle, book);
                        		cell.setCellStyle(cellStyle1);
                        		cell.setCellValue(d);
                    		}else {
                    			cell.setCellStyle(cellStyle);
                    			cell.setCellValue("");
                    		}
                    	}
                    	flag = false;
                    }
                    
                    // 其它数据类型都当作字符串简单处理  
                    if(value != null && value != ""){  
                        textValue = value.toString();  
                    }
                    if(flag) {
                        XSSFRichTextString richString = new XSSFRichTextString(textValue);  
                        cell.setCellValue(richString);
                        setCellStyle(cellStyle, fontStyle);
                        cell.setCellStyle(cellStyle);
                    }
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将工作溥作为文件输出
	 * @param book			工作溥
	 * @param fileName		存入文件的名称
	 */
	public static void getOutExcel(XSSFWorkbook book, String fileName) {
		BufferedOutputStream fos = null;  
        try {  
            File file = new File(fileName);
            if(!file.getParentFile().exists()) {
            	file.getParentFile().mkdirs();
            }
            file.createNewFile();
            
            OutputStream os = new FileOutputStream(file);
            fos = new BufferedOutputStream(os);  
            book.write(fos);  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            if (fos != null) {  
                try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
            }  
        } 
	}
	
	public static void setAllExcel(XSSFSheet sheet,XSSFWorkbook book, String bn,String[] headers1, String[] headers2,int col) {
		//合并单元格
		ExcelUtils.setRangeCell(sheet,col);
		//设置表格的标题
		XSSFRow row = sheet.createRow(0);
		row.setHeight((short)350);
		//创建一个单元格
		XSSFCell cell = row.createCell(0);
		XSSFRichTextString text = new XSSFRichTextString(bn);
		cell.setCellValue(text);
		
		XSSFCellStyle cellStyle = book.createCellStyle();
		//创建字体样式
		XSSFFont fontStyle = book.createFont();
		//设置头标题样式
		ExcelUtils.setHeaderCellStyle(cellStyle, fontStyle);
		cell.setCellStyle(cellStyle);
		
		//获取第二行
		row = sheet.createRow(1);
		cellStyle = book.createCellStyle();
		fontStyle = book.createFont();
		//设置表的标题
		//String[] headers = {"日期","机台号","规格dtex/f","线密度dtex","条干%","强度CN/dtex","伸长%","卷曲率%","稳定度%","沸水%","含油率%","网络"};
		ExcelUtils.setSecondRowStyle(sheet, headers1, cellStyle, fontStyle);
		
		//获取第三行
		row = sheet.createRow(2);
		//headers = new String[] {"平均","范围","均匀度"};
		ExcelUtils.setThirdRowStyle(sheet, headers2, cellStyle, fontStyle, col);
	}
}





