package com.hd.tool;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.RectangleInsets;

import com.hd.pojo.NyLon;
import com.hd.pojo.NyLonSon;

public class ExcelAndChartUtils {

	public static Logger logger = Logger.getLogger(ExcelAndChartUtils.class);
	
	/**
	 * 设置单元格的默认样式
	 */
	public static CellStyle setDefaultCellStyle(Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();// 创建单元格样式
		org.apache.poi.ss.usermodel.Font ztFont = wb.createFont();// 创建字体对象
		cellStyle.setFont(ztFont); // 将字体应用到样式上面
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		return cellStyle;
	}

	/**
	 * 设置单元格的其他样式（红色字体）
	 */
	public static CellStyle setCellStyle(Workbook wb) {

		CellStyle cellStyle = wb.createCellStyle();// 创建单元格样式
		org.apache.poi.ss.usermodel.Font ztFont = wb.createFont();// 创建字体对象
		ztFont.setColor(org.apache.poi.ss.usermodel.Font.COLOR_RED); // 将字体设置为“红色”
		cellStyle.setFont(ztFont); // 将字体应用到样式上面
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		return cellStyle;
	}
	/**
	 * 设置单元格的其他样式（红色字体）
	 */
	public static CellStyle setCellStyle2(Workbook wb) {

		CellStyle cellStyle = wb.createCellStyle();// 创建单元格样式
		org.apache.poi.ss.usermodel.Font ztFont = wb.createFont();// 创建字体对象
		ztFont.setColor(org.apache.poi.ss.usermodel.Font.COLOR_RED); // 将字体设置为“红色”
		cellStyle.setFont(ztFont); // 将字体应用到样式上面
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		return cellStyle;
	}

	
	/**
	 * 生成Excel
	 * @param nList     Nylon的结果集
	 * @param filename  生成Excel的文件名/路径（不用.xls）
	 * @param PicName   传入图片路径数组
	 */
	public static void creatExcel(List<NyLon> nList, String filename,String[] PicName) {
		try {
			int size = nList.size();// 判断有多少条数据

			// 1.创建Excel工作薄对象
			Workbook wb = new HSSFWorkbook();

			// 2.创建Excel工作表对象
			org.apache.poi.ss.usermodel.Sheet sheet = wb.createSheet("new Excel");
			// sheet.setDefaultColumnWidth(15);//修改所有列的默认宽度
			sheet.setColumnWidth(0, 15 * 256); // 设置第一列宽度
			sheet.setColumnWidth(1, 15 * 256);
			sheet.setColumnWidth(2, 15 * 256);

			// 3.创建Excel工作表的行
			// 设置表头（标题）
			HSSFRow row = (HSSFRow) sheet.createRow(0); // 第一行
			HSSFRow row2 = (HSSFRow) sheet.createRow(1);// 第二行

			// 4.创建单元格样式
			CellStyle cellStyle = setDefaultCellStyle(wb);

			HSSFCell cell = null;

			// 标题文字
			String[] tableTitle1 = new String[] { "生产时间", "测量时间", "批号", "线位号", "线密度", "D%", "CV%", "强度", "null", "伸长",
					"null", "条干U%", "卷曲收缩率", "卷曲稳定度", "沸水", "含油率", "含水率", "网络", "null", "状态" };
			String[] tableTitle2 = new String[] { "平均值", "CV%", "平均值", "CV%", "平均", "范围" };

			// 设置表格标题
			for (int i = 0; i < 20; i++) {
				cell = row.createCell(i);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(tableTitle1[i]);
			}
			for (int i = 0; i < 20; i++) {
				switch (i) {
				case 7:
					cell = row2.createCell(i);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(tableTitle2[0]);
					break;
				case 8:
					cell = row2.createCell(i);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(tableTitle2[1]);
					break;
				case 9:
					cell = row2.createCell(i);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(tableTitle2[2]);
					break;
				case 10:
					cell = row2.createCell(i);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(tableTitle2[3]);
					break;
				case 17:
					cell = row2.createCell(i);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(tableTitle2[4]);
					break;
				case 18:
					cell = row2.createCell(i);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(tableTitle2[5]);
					break;
				default:
					break;
				}

			}

			HSSFRow rowX;
			// 设置（内容）
			for (int i = 0; i < size; i++) {
				rowX = (HSSFRow) sheet.createRow(i + 2);// 表示从第三行开始，前两行已为表头
				for (int j = 0; j < 20; j++) {
					cell = rowX.createCell(j);
					switch (j) {
					case 0:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getProductionDate());
						break;
					case 1:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getCreateDate());
						break;
					case 2:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getBatchNo());
						break;
					case 3:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getLineNo());
						break;
					case 4:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getLinearDensity());
						break;
					case 5:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getDensityPercent());
						break;
					case 6:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getLdCV());
						break;
					case 7:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getStrength());
						break;
					case 8:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getStrengthCV());
						break;
					case 9:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getElongation());
						break;
					case 10:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getElongationCV());
						break;
					case 11:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getYarnlevelness());
						break;
					case 12:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getCrimpContraction());
						break;
					case 13:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getCrimpStability());
						break;
					case 14:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getBoilingWater());
						break;
					case 15:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getOliContent());
						break;
					case 16:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getWaterRatio());
						break;
					case 17:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getGridLine());
						break;
					case 18:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getRanges());
						break;
					case 19:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getNlStatus());
						break;
					default:
						break;
					}
				//}
				}

			}

			// 设置sheet名称
			wb.setSheetName(0, "第一张工作表");
			CellRangeAddress cra;
			// 上下合并
			for (int i = 0; i < 20; i++) {
				if ((i >= 7 && i <= 10) || (i >= 17 && i <= 18)) {
					continue;
				} else {
					cra = new CellRangeAddress(0, 1, i, i); // 起始行, 终止行, 起始列, 终止列
					sheet.addMergedRegion(cra);
				}

			}

			// 左右合并
			for (int i = 0; i < 20; i++) {
				if (i == 7 || i == 9 || i == 17) {
					cra = new CellRangeAddress(0, 0, i, i + 1); // 起始行, 终止行, 起始列, 终止列
					sheet.addMergedRegion(cra);
				}

			}

	        
	        insertPicToExcel(PicName, wb, sheet, nList);
	        
					
			// ## 保存Workbook ##//
			// 设置默认地址
			if (filename == null) {
				filename = "AutoNylon";
			}

			String fileExcel = filename + ".xls";
			FileOutputStream fileOut = new FileOutputStream(fileExcel);
			wb.write(fileOut);
			fileOut.close();
			System.out.println("创建成功");

			// fos.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 生成Excel
	 * @param nList     NylonSon的结果集
	 * @param filename  生成Excel的文件名/路径（不用.xls）
	 * @param PicName   传入图片路径数组
	 */
	public static void creatExcel1(List<NyLonSon> nList, String filename,String[] PicName) {
		BufferedOutputStream bos = null;
		
		try {
			//int size = nList.size();// 判断有多少条数据

			// 1.创建Excel工作薄对象
			Workbook wb = new HSSFWorkbook();

			// 2.创建Excel工作表对象
			org.apache.poi.ss.usermodel.Sheet sheet = wb.createSheet("new Excel");
			// sheet.setDefaultColumnWidth(15);//修改所有列的默认宽度
			sheet.setColumnWidth(0, 15 * 256); // 设置第一列宽度
			sheet.setColumnWidth(1, 15 * 256);
			sheet.setColumnWidth(2, 15 * 256);

			// 3.创建Excel工作表的行
			// 设置表头（标题）
			HSSFRow row = (HSSFRow) sheet.createRow(0); // 第一行
			HSSFRow row2 = (HSSFRow) sheet.createRow(1);// 第二行
			HSSFRow row3 = (HSSFRow) sheet.createRow(2);// 第三行
			
			// 4.创建单元格样式
			CellStyle cellStyle = setDefaultCellStyle(wb);

			HSSFCell cell = null;

			// 标题文字
			String batchTitle = nList.get(0).getBatchNo();
			String[] tableTitle1 = new String[] { "检测日期", "生产时间", "线位号", "线密度", "D%", "CV%", "强度", "null", "伸长",
					"null", "条干U%", "卷曲收缩率", "卷曲稳定度", "沸水", "含油率", "含水率", "网络", "null", "状态" };
			String[] tableTitle2 = new String[] { "平均值", "CV%", "平均值", "CV%", "平均", "范围" };

			// 设置表格标题
			cell = row.createCell(0);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(batchTitle);
			
			for (int i = 0; i < tableTitle1.length; i++) {
				cell = row2.createCell(i);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(tableTitle1[i]);
			}
			for (int i = 0; i < tableTitle1.length; i++) {
				switch (i) {
				case 6:
					cell = row3.createCell(i);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(tableTitle2[0]);
					break;
				case 7:
					cell = row3.createCell(i);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(tableTitle2[1]);
					break;
				case 8:
					cell = row3.createCell(i);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(tableTitle2[2]);
					break;
				case 9:
					cell = row3.createCell(i);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(tableTitle2[3]);
					break;
				case 16:
					cell = row3.createCell(i);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(tableTitle2[4]);
					break;
				case 17:
					cell = row3.createCell(i);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(tableTitle2[5]);
					break;
				default:
					break;
				}

			}

			HSSFRow rowX;
			// 设置（内容）
			for (int i = 0; i < nList.size(); i++) {
				rowX = (HSSFRow) sheet.createRow(i + 3);// 表示从第四行开始，前两行已为表头
				for (int j = 0; j < tableTitle1.length; j++) {
					cell = rowX.createCell(j);
					switch (j) {
					case 0:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getCreateDate());
						break;
					case 1:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getProductionDate());
						break;
					/*case 2:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getBatchNo());
						break;*/
					case 2:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getLineNo());
						break;
					case 3:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getLinearDensity());
						break;
					case 4:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getDensityPercent());
						break;
					case 5:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getLdCV());
						break;
					case 6:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getStrength());
						break;
					case 7:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getStrengthCV());
						break;
					case 8:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getElongation());
						break;
					case 9:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getElongationCV());
						break;
					case 10:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getYarnlevelness());
						break;
					case 11:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getCrimpContraction());
						break;
					case 12:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getCrimpStability());
						break;
					case 13:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getBoilingWater());
						break;
					case 14:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getOliContent());
						break;
					case 15:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getWaterRatio());
						break;
					case 16:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getGridLine());
						break;
					case 17:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getRanges());
						break;
					case 18:
						cell.setCellStyle(cellStyle);
						cell.setCellValue(nList.get(i).getNlStatus());
						break;
					default:
						break;
					}
				//}
				}

			}

			// 设置sheet名称
			wb.setSheetName(0, "第一张工作表");
			CellRangeAddress cra;
			cra = new CellRangeAddress(0, 0, 0, 18); // 起始行, 终止行, 起始列, 终止列
			sheet.addMergedRegion(cra);
			// 上下合并
			for (int i = 0; i < tableTitle1.length; i++) {
				if ((i >= 6 && i <= 9) || (i >= 16 && i <= 17)) {
					continue;
				} else {
					cra = new CellRangeAddress(1, 2, i, i); // 起始行, 终止行, 起始列, 终止列
					sheet.addMergedRegion(cra);
				}

			}

			// 左右合并
			for (int i = 0; i < 20; i++) {
				if (i == 6 || i == 8 || i == 16) {
					cra = new CellRangeAddress(1, 1, i, i + 1); // 起始行, 终止行, 起始列, 终止列
					sheet.addMergedRegion(cra);
				}

			}

	        insertPicToExcel1(PicName, wb, sheet, nList);
	        
			// ## 保存Workbook ##//
			// 设置默认地址
			if (filename == null) {
				filename = "AutoNylon";
			}

			String fileExcel = filename + ".xls";
			
            File file = new File(fileExcel);
            
            if(!file.getParentFile().exists()) {
            	file.getParentFile().mkdirs();
            }
            
            file.createNewFile();
            
            OutputStream os = new FileOutputStream(file);
            bos = new BufferedOutputStream(os);  
            wb.write(bos);  
			
			//System.out.println("创建成功");

		} catch (Exception e) {
			e.printStackTrace();
		}finally {  
            if (bos != null) {  
                try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}  
            }  
        } 
	}
	
	/**
	 * 创建CategoryDataset对象
	 * 
	 * @param nList
	 * @param rowKeys[]
	 *            //底部线的标识，表示有几条线和线的名字
	 * @param colKeys[]
	 *            //表示 x轴坐标的值
	 * @param nylonItem
	 *            表示要输入的项目
	 * @return
	 */
	public static CategoryDataset createDataset1(List<NyLonSon> nList, String[] rowKeys, String[] colKeys,
			String[] nylonItem) {

		// nylonItem = "linearDensity";

		double[][] data = new double[rowKeys.length][nList.size()];

		//for (String item : nylonItem) {
					
		for (int i = 0; i < rowKeys.length; i++) {
			
			String item = nylonItem[i];    //表示nylonItem数组应该与i变量同时进行，而不是单独循环nylonItem数组，否则会覆盖之前的数据
			
			for (int j = 0; j < nList.size(); j++) {
				switch (item) {
				case "linearDensity":
					data[i][j] = nList.get(j).getLinearDensity();
					break;
				case "yarnlevelness":
					break;
				case "strength":
					data[i][j] = nList.get(j).getStrength();
					break;
				case "elongation":
					data[i][j] = nList.get(j).getElongation();
					break;
				case "crimpContraction":
					data[i][j] = nList.get(j).getCrimpContraction();
					break;
				case "crimpStability":
					data[i][j] = nList.get(j).getCrimpStability();
					break;
				case "boilingWater":
					data[i][j] = nList.get(j).getBoilingWater();
					break;
				case "oliContent":
					data[i][j] = nList.get(j).getOliContent();
					break;
				case "gridLine":
					data[i][j] = nList.get(j).getGridLine();
					break;
				case "densityPercent":
					data[i][j] = nList.get(j).getDensityPercent();
					break;
				case "ldCV":
					data[i][j] = nList.get(j).getLdCV();
					break;
				case "strengthCV":
					data[i][j] = nList.get(j).getStrengthCV();
					break;
				case "elongationCV":
					data[i][j] = nList.get(j).getElongationCV();
					break;
				case "waterRatio":
					data[i][j] = nList.get(j).getWaterRatio();
					break;
				default:
					System.out.println("无此项目！");
					break;
				}
			
			}
		}
		//System.out.println("rs:"+data[0][0]);
		return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);
	}

	/**
	 * 创建CategoryDataset对象
	 * 
	 * @param nList
	 * @param rowKeys[]
	 *            //底部线的标识，表示有几条线和线的名字
	 * @param colKeys[]
	 *            //表示 x轴坐标的值
	 * @param nylonItem
	 *            表示要输入的项目
	 * @return
	 */
	public static CategoryDataset createDataset(List<NyLon> nList, String[] rowKeys, String[] colKeys,
			String[] nylonItem) {

		// nylonItem = "linearDensity";

		double[][] data = new double[rowKeys.length][nList.size()];

		//for (String item : nylonItem) {
					
		for (int i = 0; i < rowKeys.length; i++) {
			
			String item = nylonItem[i];    //表示nylonItem数组应该与i变量同时进行，而不是单独循环nylonItem数组，否则会覆盖之前的数据
			
			for (int j = 0; j < nList.size(); j++) {
				switch (item) {
				case "linearDensity":
					data[i][j] = nList.get(j).getLinearDensity();
					break;
				case "yarnlevelness":
					break;
				case "strength":
					data[i][j] = nList.get(j).getStrength();
					break;
				case "elongation":
					data[i][j] = nList.get(j).getElongation();
					break;
				case "crimpContraction":
					data[i][j] = nList.get(j).getCrimpContraction();
					break;
				case "crimpStability":
					data[i][j] = nList.get(j).getCrimpStability();
					break;
				case "boilingWater":
					data[i][j] = nList.get(j).getBoilingWater();
					break;
				case "oliContent":
					data[i][j] = nList.get(j).getOliContent();
					break;
				case "gridLine":
					data[i][j] = nList.get(j).getGridLine();
					break;
				case "densityPercent":
					data[i][j] = nList.get(j).getDensityPercent();
					break;
				case "ldCV":
					data[i][j] = nList.get(j).getLdCV();
					break;
				case "strengthCV":
					data[i][j] = nList.get(j).getStrengthCV();
					break;
				case "elongationCV":
					data[i][j] = nList.get(j).getElongationCV();
					break;
				case "waterRatio":
					data[i][j] = nList.get(j).getWaterRatio();
					break;
				default:
					System.out.println("无此项目！");
					break;
				}
			
			}
		}
		System.out.println("rs:"+data[0][0]);
		return DatasetUtilities.createCategoryDataset(rowKeys, colKeys, data);
	}
	/**
	 * 根据CategoryDataset创建JFreeChart对象
	 * @param categoryDataset
	 * @param chartTitle    折线图标题
	 * @param X				X轴标题
	 * @param Y             Y轴标题
	 * @param Ystart		Y轴起始点数值
	 * @param Yend			Y轴结束点数值
	 * @param space			Y轴两点之间的间隔
	 * @return
	 */
	public static String createChart(CategoryDataset categoryDataset,String chartTitle
			,String X,String Y,int Ystart,int Yend,int space) {
		// 设置中文主题，以防乱码
		StandardChartTheme mChartTheme = new StandardChartTheme("CN");
		mChartTheme.setLargeFont(new Font("黑体", Font.BOLD, 20));
		mChartTheme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 15));
		mChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15));
		ChartFactory.setChartTheme(mChartTheme);
		
		// 创建JFreeChart对象：ChartFactory.createLineChart
		JFreeChart jfreechart = ChartFactory.createLineChart(chartTitle, // 标题
				X, // categoryAxisLabel （category轴，横轴，X轴标签）
				Y, // valueAxisLabel（value轴，纵轴，Y轴的标签）
				categoryDataset, // dataset
				PlotOrientation.VERTICAL, true, // legend
				false, // tooltips
				false); // URLs

		
		// 使用CategoryPlot设置各种参数。以下设置可以省略。
		CategoryPlot plot = (CategoryPlot) jfreechart.getPlot();
		NumberAxis numberaxis = (NumberAxis) plot.getRangeAxis();
		numberaxis.setAutoTickUnitSelection(false);
		// 设置纵坐标值的间距为10
		numberaxis.setTickUnit(new NumberTickUnit(space));
		// 纵坐标值只能是0-100之间的值
		numberaxis.setRangeWithMargins(Ystart, Yend);
		// 背景色 透明度
		plot.setBackgroundAlpha(0.5f);
		// 前景色 透明度
		plot.setForegroundAlpha(0.5f);
		// 设置网格背景颜色
		plot.setBackgroundPaint(Color.WHITE);
		// 设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.pink);
		// 设置网格横线颜色
		plot.setRangeGridlinePaint(Color.pink);
		// 设置曲线图与xy轴的距离
		plot.setAxisOffset(new RectangleInsets(0D, 0D, 0D, 10D));
		// 其他设置 参考 CategoryPlot类
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setBaseShapesVisible(true); // series 点（即数据点）可见
		renderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见
		renderer.setUseSeriesOffset(true); // 设置偏移量
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());// 显示y轴数据 纵轴数据显示
		renderer.setBaseItemLabelsVisible(true);
		
		// 设置X轴
        CategoryAxis domainAxis = plot.getDomainAxis();   
        domainAxis.setLabelFont(new Font("宋书", Font.PLAIN, 15)); // 设置横轴字体
        domainAxis.setTickLabelFont(new Font("宋书", Font.PLAIN, 15));// 设置坐标轴标尺值字体
        domainAxis.setLowerMargin(0.01);// 左边距 边框距离
        domainAxis.setUpperMargin(0.06);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
        domainAxis.setMaximumCategoryLabelLines(10);
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);// 横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45
		
		
		Random random = new Random();
		String filename = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss")+random.nextInt() + ".png";// 用时间作为文件名防止重名的问题发生
		
		File file = new File(filename);
		
		
		
		try {
			Integer column = categoryDataset.getColumnCount();
			Integer row = categoryDataset.getRowCount();
			//System.out.println(column+"/"+row);
			if (space==0) {
				space=1;
			}
			if (column!=null&&row!=null) {
				//自定义大小
				ChartUtilities.saveChartAsPNG(file, jfreechart, 1000+column*20, 200+((Yend-Ystart)/space)*100);
			}else {
			ChartUtilities.saveChartAsPNG(file, jfreechart, 1300, 200);//默认大小
			}
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return filename;
	}
	
	//private static int Rows = 7;
	/**
	 * 
	 * @param PicName  图片的路径数组
	 * @param wb
	 * @param sheet
	 * @param nList
	 * @throws IOException
	 */
	public static void insertPicToExcel(String[] PicName,Workbook wb,org.apache.poi.ss.usermodel.Sheet sheet,
			List<NyLon> nList) throws IOException {
		int Rows = 7;
		for (String pic : PicName) {
			// 添加图片到该工作薄
			File file = new File(pic);
			InputStream is = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(is);
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			is.close();
			file.delete();

			CreationHelper helper = wb.getCreationHelper();

			// ## 创建一个DrawingPatriarch实例 ##//
			Drawing drawing = ((org.apache.poi.ss.usermodel.Sheet) sheet).createDrawingPatriarch();

			// ## 设置图片的形状，位置等 ##//
			ClientAnchor anchor = helper.createClientAnchor();
			// 设置图片起始点
			anchor.setCol1(0);
			anchor.setRow1(nList.size()+ Rows); // 表示从数据集下5行开始
			Picture pict = drawing.createPicture(anchor, pictureIdx);
			// ## 自动设置图片的大小 注意：只支持PNG，JPG，GIF//
			pict.resize();
			Rows+=30;      //图片自动向下平移30单位

		}
		
		
					
	}
	
	/**
	 * 
	 * @param PicName  图片的路径数组
	 * @param wb
	 * @param sheet
	 * @param nList
	 * @throws IOException
	 */
	public static void insertPicToExcel1(String[] PicName,Workbook wb,org.apache.poi.ss.usermodel.Sheet sheet,
			List<NyLonSon> nList) throws IOException {
		int Rows = 7;
		for (String pic : PicName) {
			// 添加图片到该工作薄
			File file = new File(pic);
			InputStream is = new FileInputStream(file);
			byte[] bytes = IOUtils.toByteArray(is);
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			is.close();
			file.delete();

			CreationHelper helper = wb.getCreationHelper();

			// ## 创建一个DrawingPatriarch实例 ##//
			Drawing drawing = ((org.apache.poi.ss.usermodel.Sheet) sheet).createDrawingPatriarch();

			// ## 设置图片的形状，位置等 ##//
			ClientAnchor anchor = helper.createClientAnchor();
			// 设置图片起始点
			anchor.setCol1(0);
			anchor.setRow1(nList.size()+ Rows); // 表示从数据集下5行开始
			Picture pict = drawing.createPicture(anchor, pictureIdx);
			// ## 自动设置图片的大小 注意：只支持PNG，JPG，GIF//
			pict.resize();
			Rows+=30;      //图片自动向下平移30单位

		}
		
		
					
	}
	
	public static final List<NyLon> createNylon() {
		NyLon nyLon = null;
		List<NyLon> nList = new ArrayList<>();
		for(int i=0;i<14;i++) {
			nyLon = new NyLon("ABC12345678", "2017-01-01", "40A", "10/20", 12.0, 13.0, 14.0, 15.0, 16.0, 
					17.0, 18.0, 19.0, 20+i, 21+2*i, "2017-01-02", 23.0, 24.0, 25.0, 26.0, 27.0,null);
			//System.out.println(nyLon.getBatchNo());
			nList.add(nyLon);
		}
		
	//	System.out.println(nList.size());
		return nList;
	}
	/**
	 * 自动生成X轴数据
	 * @param nList  Nylon类的list集合
	 * @return
	 */
	public static String[] autoX(List<NyLon> nList) {
		int size = nList.size();
		String[] X = new String[size];
		for(int i=0;i<size;i++) {
			X[i] = nList.get(i).getLineNo()+"/"+nList.get(i).getCreateDate().substring(5, 10)+"("+i+")";
		}
		return X;
		
	}
	/**
	 * 自动生成X轴数据
	 * @param nList  NylonSon类的list集合
	 * @return
	 */
	public static String[] autoX1(List<NyLonSon> nList) {
		int size = nList.size();
		String[] X = new String[size];
		for(int i=0;i<size;i++) {
			X[i] = nList.get(i).getLineNo()+"/"+nList.get(i).getCreateDate().substring(5, 10)+"("+i+")";
		}
		return X;
		
	}
	
	 /**
     * 取出数组中的最大值
     * @param arr
     * @return
     */
    public static double getMax(double[] arr){
    	double max=arr[0];
        for(int i=1;i<arr.length;i++){
            if(arr[i]>max){
                max=arr[i];
            }
        }
        return max;
    }
    
    /**
     * 取出数组中的最小值
     * @param arr
     * @return
     */
    public static double getMin(double[] arr){
    	double min=arr[0];
        for(int i=1;i<arr.length;i++){
            if(arr[i]<min){
            	min=arr[i];
            }
        }
        return min;
    }
	
	public static double[] autoY1(List<NyLonSon> nList,String item) {
		int size = nList.size();
		double[] data = new double[size];
		double[] Y = new double[3];
		//for(int i=0;i<size;i++) {
			switch (item) {
			case "linearDensity":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getLinearDensity();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "yarnlevelness":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getYarnlevelness();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "strength":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getStrength();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "elongation":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getElongation();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "crimpContraction":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getCrimpContraction();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "crimpStability":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getCrimpStability();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "boilingWater":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getBoilingWater();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "oliContent":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getOliContent();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "gridLine":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getGridLine();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "densityPercent":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getDensityPercent();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "ldCV":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getLdCV();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "strengthCV":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getStrengthCV();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "elongationCV":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getElongationCV();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "waterRatio":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getWaterRatio();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			default:
				System.out.println("无此项目！");
				break;
			}
			
		//}
		return Y;
	}
	
	public static double[] autoY(List<NyLon> nList,String item) {
		int size = nList.size();
		double[] data = new double[size];
		double[] Y = new double[3];
		//for(int i=0;i<size;i++) {
			switch (item) {
			case "linearDensity":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getLinearDensity();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "yarnlevelness":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getYarnlevelness();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "strength":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getStrength();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "elongation":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getElongation();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "crimpContraction":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getCrimpContraction();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "crimpStability":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getCrimpStability();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "boilingWater":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getBoilingWater();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "oliContent":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getOliContent();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "gridLine":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getGridLine();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "densityPercent":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getDensityPercent();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "ldCV":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getLdCV();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "strengthCV":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getStrengthCV();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "elongationCV":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getElongationCV();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			case "waterRatio":
				for (NyLon nyLon : nList) {
					data[nList.indexOf(nyLon)] = nyLon.getWaterRatio();
				}
				Y[0] = Math.ceil(getMax(data));
				Y[1] = Math.floor(getMin(data));
				Y[2] = Math.ceil((getMax(data)-getMin(data))/5);
				break;
			default:
				System.out.println("无此项目！");
				break;
			}
			
		//}
		return Y;
	}
	
//	public static void main(String[] args) {
//		String[] rowKeys = {"线密度dtex","含水率"};  //底部线的标识，表示有几条线	    
//		String[] rowKeys2 = {"伸长"};  //底部线的标识，表示有几条线
//		String[] rowKeys3 = {"含水"};
//        String[] colKeys = {"32A/8.1", "33B/8.2", "34A/8.3", "32A/8.4", "34A/8.5", "32A/8.6",  
//                "34B/8.7", "35A/8.8", "36B/8.9", "33B/8.10", "32B/8.11", "33A/8.12", "34A/8.13",  
//                "35A/8.14"};	        
//        String[] nylonItem = {"linearDensity","strength"};
//        String[] nylonItem2 = {"elongation"};
//        String[] nylonItem3 = {"waterRatio"};
//        String chartTitle = "测试用折线图"; 
//        String X = "线位号/日期";
//        String Y = "数字";
//        String[] autoX = autoX(createNylon());
//        double[] autoY = autoY(createNylon(), "waterRatio");
//        int Ystart = (int) autoY[0];
//        int Yend = (int) autoY[1];
//        int space = (int) autoY[2];
//        String pic1 = createChart(createDataset(createNylon(), rowKeys, colKeys, nylonItem), chartTitle, X, Y,10,15,1);
//        String pic2 = createChart(createDataset(createNylon(), rowKeys2, colKeys, nylonItem2), chartTitle, X, Y,10,20,2);
//        String pic3 = createChart(createDataset(createNylon(), rowKeys3, colKeys, nylonItem3), chartTitle, X, Y, Ystart, Yend, space);
//        String[] PicName = {pic1,pic2,pic3};
//
//		creatExcel(createNylon(), "test",PicName);
//		//System.out.println(Math.ceil(2.1));
//	}
}
