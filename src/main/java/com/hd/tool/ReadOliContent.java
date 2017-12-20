package com.hd.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.hd.pojo.OliContent;

/**
 * 读取固定路径下的文件
 * 
 * @author cyj
 *
 */
public class ReadOliContent {

	/**
	 * 读取含油率文件数据
	 * 
	 * @param 含油文件所在路径
	 * @return 含油率数据对象集合 1：List<OliContent> OC类 2:List<String> 出错的信息（在xx中xx行可能出现错误）
	 */
	public static Map<Integer, List<?>> readOcTxt(String path, String date1) throws Exception {
		// //解压后文件的存饭路径以及即将要读取文件的路径
		// String filePath = "E:/project/out/";
		//判断路径存不存在
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		
		File file = new File(path);
		// 判断文件是否存在
		if (!file.exists()) {
			return null;
		}
		// 判断文件是不是文件夹
		if (!file.isDirectory()) {
			return null;
		}

		int lineCounts = 1; // 确定出错的行数
		String[] fileList = file.list(); // 文件名集合
		OliContent oc = new OliContent(); // OC类
		List<OliContent> ocList = new ArrayList<OliContent>(); // 创建存储该对象的集合
		Map<Integer, List<?>> map = new HashMap<Integer, List<?>>(); // 创建返回的数据
		List<String> errList = new ArrayList<String>();// 存放错误的数据的文件名
		
		String err = null;
		String chinese = "^.*[\\u4e00-\\u9fa5]{1,}.*$";  //中文的正则表达式

		// 遍历文件夹，得到该文件夹下所有的文件
		for (String fileName : fileList) {
			lineCounts = 1;
			String suffix = fileName.substring(fileName.lastIndexOf("."));
			if (suffix.equals(".RILog")) {
				file = new File(path + "/" + fileName);

				// 设置io流变量
				InputStream is = null;
				BufferedReader br = null;
				try {
					is = new FileInputStream(file);
					InputStream is1 = new FileInputStream(file);
					br = new BufferedReader(new InputStreamReader(is, "gbk"));
					
					byte[] b = new byte[3];
					is1.read(b);
					
					if (b[0] == -17 && b[1] == -69 && b[2] == -65)  {
//						System.out.println("编码为UTF-8");  
						br = new BufferedReader(new InputStreamReader(is, "utf-8"));
					} else if(b[0] == -1 && b[1] == -2) {
						br = new BufferedReader(new InputStreamReader(is, "utf-16"));
					}
					
					is1.close();
				
					String line = null;

					String date3 = checkDate(date1); // 日期去0化

					while ((line = br.readLine()) != null) {
						String[] data = line.split(",");
						for (int i = 0; i < data.length; i++) {
							// data.length==9为称重
							if (data.length == 9) {
								if (i == 8) {
									String date = data[1].replace("/", "-");
									String time = data[2];
									date = date + " " + time;

									String batchNoLine = data[4].toUpperCase(); // 将批号进行大写转换

									int oilContent = 0;
									if (isNumeric(data[8])) { // 判断oilContent是否为小数
										oilContent = (int) (Double.parseDouble(data[8]) * 100);
									}

									String LineNo = data[7];

									if (batchNoLine.equals("") || LineNo.equals("") || date.equals("")
											|| data[7].equals("")||Pattern.matches(chinese, batchNoLine)||!Pattern.matches(".*" + date3 + ".*", date)) {
										lineCounts++;
										continue;
									} else {

										Map<String, String> batchMap = checkBatchNo(batchNoLine); // 调用方法进行验证
										boolean isLineNo = checkLineNo(LineNo);
										if (!isLineNo) {
											err =  "在" + fileName + "中的第" + lineCounts + "行可能出现错误" +"(线位号："+LineNo+")";
											errList.add(err);
											map.put(2, errList);
										}
										String msg1 = batchMap.get("msg1");
										String msg2 = batchMap.get("msg2");
										String msg3 = batchMap.get("msg3");
										boolean isDate = Pattern.matches(".*" + date3 + ".*", date); // 判断日期匹配
										String batchNo = null;
										Integer ocStatus = 0;
										if (batchNoLine.contains("-")) {
											String[] batchNos = batchNoLine.split("-");
											batchNo = batchNos[0];
											ocStatus = Integer.parseInt(batchNos[1]);
										} else {
											batchNo = batchNoLine;
										}
										
										boolean istrue = (msg1 == null || msg1.equals("ok"))
												&& (msg2 == null || msg2.equals("ok"))
												&& (msg3 == null || msg3.equals("ok"));// 判断批号匹配
										/**
										 * 先判断批号是否匹配，在判断日期
										 */
										if (istrue) {
											lineCounts++;
											if (isDate) {																		
												oc.setBatchNo(batchNo);
												oc.setOcStatus(ocStatus);
												oc.setLineNo(LineNo);
												oc.setOliContent(oilContent);
												oc.setCreateDate(date);
												ocList.add(oc);
												map.put(1, ocList);
												continue;
												}

										} else {
											err = "在" + fileName + "中的第" + lineCounts + "行可能出现错误" +"(批号："+batchNoLine+")"+ msg1 + "/"
													+ msg2 + "/" + msg3 ;
//											System.out.println(err);
											errList.add(err);
											map.put(2, errList);
											lineCounts++;
											// continue;
										}

									}

								}
								// data.length==8为非称重
							} else if (data.length == 8) {
								if ( i == 7) {
									String date = data[1].replace("/", "-");
									String time = data[2];
									date = date + " " + time;
									String LineNo = data[5].replace(" ", "");
									String batchNoLine = data[3].toUpperCase().replace(" ", ""); // 将批号进行大写转换
									int oilContent = 0;

									if (isNumeric(data[7])) { // 判断oilContent是否为小数
										oilContent = (int) (Double.parseDouble(data[7]) * 100);
									}

									/**
									 * 当类似3,2017/7/23,15:17,,,,,1.50689这种没有批号，线位号这种空的数据/中文时，判断没有报错的价值，选择跳过。
									 * 对于不为空的数据进行报错，返回相应的行数。
									 */
									
									
									if (batchNoLine.equals("") || LineNo.equals("") || date.equals("")
											|| data[7].equals("")||Pattern.matches(chinese, batchNoLine)||
											!Pattern.matches(".*" + date3 + ".*", date)) {
										lineCounts++;
										//continue;
									} else {

										Map<String, String> batchMap = checkBatchNo(batchNoLine); // 调用方法进行批号验证
										boolean isLineNo = checkLineNo(LineNo);
										if (!isLineNo) {
											err =  "在" + fileName + "中的第" + lineCounts + "行可能出现错误" +"(线位号："+LineNo+")";
											errList.add(err);
											map.put(2, errList);
										}
										
										String batchNo = null;
										Integer ocStatus = 0;
										if (batchNoLine.contains("-")) {
											String[] batchNos = batchNoLine.split("-");
											batchNo = batchNos[0];
											ocStatus = Integer.parseInt(batchNos[1]);
										} else {
											batchNo = batchNoLine;
										}
										String msg1 = batchMap.get("msg1");
										String msg2 = batchMap.get("msg2");
										String msg3 = batchMap.get("msg3");

										boolean isDate = Pattern.matches(".*" + date3 + ".*", date); // 判断日期匹配

										boolean istrue = (msg1 == null || msg1.equals("ok"))
												&& (msg2 == null || msg2.equals("ok"))
												&& (msg3 == null || msg3.equals("ok"));// 判断批号匹配
										/**
										 * 先判断批号是否匹配，在判断日期
										 */
										if (istrue) {
											lineCounts++;
											if (isDate) {												
												oc.setBatchNo(batchNo);
												oc.setOcStatus(ocStatus);
												oc.setLineNo(LineNo);
												oc.setOliContent(oilContent);
												oc.setCreateDate(date);
												ocList.add(oc);
												map.put(1, ocList);
												continue;
											}

										} else  {
											err = "在" + fileName + "中的第" + lineCounts + "行可能出现错误" +"(批号："+batchNo+")"+ msg1 + "/"
													+ msg2 + "/" + msg3 ;

//											System.out.println(err);
											errList.add(err);
											map.put(2, errList);
											lineCounts++;
											// continue;
										}

									}

								}
							} else  {
								System.out.println("格式错误"+data.length);
								err = "在" + fileName + "中的第" + lineCounts + "行可能出现错误"+"";
								System.out.println(err);
								errList.add(err);
								map.put(2, errList);
								lineCounts++;
							}

						}

					
						// 创建含油对象
						oc = new OliContent();
					}
					br.close();
				} finally {
					if (br != null) {
						try {
							//file.delete(); // 删除该文件，下次可以传相同的文件
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return map;
	}

	/**
	 * 批号处理
	 * 
	 * @param str
	 * @return
	 */
	public static Map<String, String> checkBatchNo(String str) {
		// FMT02207211
		// FMT02207211-1 1:表
		// FMT02207211-21
		// FMT02207211-31
		// FMT02207211-41
		// -后面的数字只能的-0,-1,-2(0-9),-3(0-9),-4(0-9)

		str = str.toUpperCase();
		Map<String, String> batchMap = new HashMap<String, String>();

		boolean is1 = false;
		boolean is2 = false;
		boolean is3 = false;
		String[] result = str.split("-"); // 根据“—”拆分

		String pattern1 = "^[A-Z]{3}[0-9A-Z]{8}$*"; // 表示含油率批号的前八位数字的正则表达式
		String pattern2 = "^[1-4]{1}$*"; // 表示含油率批号‘—’后的第一位数字
		String pattern4 = "^[0]$*"; // 特殊情况
		String pattern3 = "^[1-9]{1}$*"; // 表示含油率批号‘—’后的第二位数字

		is1 = Pattern.matches(pattern1, result[0]);

		if (is1) {
			batchMap.put("batchNo", result[0]);
			batchMap.put("msg1", "ok");
		} else {
			batchMap.put("batchNo", null);
			batchMap.put("msg1", "含油率批号的前八位数字不匹配");
		}

		if (result.length > 1) {
			String firstNum = new String();
			String secondNum = new String();
			if (result[1].length() > 1) {
				firstNum = result[1].substring(0, 1); // 截取含油率批号‘—’后的第一位数字
				secondNum = result[1].substring(1, 2); // 截取含油率批号‘—’后的第二位数字
			} else {
				firstNum = result[1].substring(0, 1);
			}

			if (secondNum == null || secondNum.equals("")) {
				is2 = Pattern.matches(pattern4, firstNum);
				is3 = true;
			} else {
				is2 = Pattern.matches(pattern2, firstNum);
				is3 = Pattern.matches(pattern3, secondNum);
			}
			if (!is2) {
				batchMap.put("firstNum", null);
				// System.out.println("含油率批号‘—’后的第一位数字不匹配");
				batchMap.put("msg2", "含油率批号‘—’后的第一位数字不匹配");

			} else {
				batchMap.put("firstNum", firstNum);
				batchMap.put("msg2", "ok");
			}
			if (!is3) {
				batchMap.put("secondNum", null);
				// System.out.println("含油率批号‘—’后的第二位数字不匹配");
				batchMap.put("msg3", "含油率批号‘—’后的第二位数字不匹配");

			} else {
				batchMap.put("secondNum", secondNum);
				batchMap.put("msg3", "ok");

			}
		}

		return batchMap;

	}
	
	/**
	 * 线位号处理
	 */
	public static boolean checkLineNo(String lineNo) {
		//40A-23,F102-1,F102-2...F102-12
		lineNo = lineNo.toUpperCase();
		String pattern1 = "^[0-9]{1,2}[A-Z]{1}-[0-9]{1,3}$"; // 表示线位号
		String pattern2 = "^[A-Z]{1}[0-9]{3}-[0-9]{1,3}$"; // 表示线位号
		
		//System.out.println(checkLineNo("A234");+"/"+Pattern.matches(pattern2, lineNo));
		if (Pattern.matches(pattern1, lineNo) || Pattern.matches(pattern2, lineNo)) {
			return true;
		}else {
			return false;
		}
		
	}
	
	/*public static void main(String[] args) {
		System.out.println(checkLineNo("DTY-1"));
	}*/
	
	/**
	 * 将前台传入的日期值进行去0处理，便于后台与数据库中的值进行匹配（2017-01-01 => 2017-1-1)
	 * 
	 * @param date1
	 * @return
	 */
	private static String checkDate(String date1) {
		String[] date2 = date1.split("-");
		String date3 = null;
		if (date2.length > 0) {
			if (date2[1].substring(0, 1).equals("0")) {
				date2[1] = date2[1].replace("0", "");

			}
			if (date2[2].substring(0, 1).equals("0")) {
				date2[2] = date2[2].replace("0", "");
			}
			date3 = date2[0] + "-" + date2[1] + "-" + date2[2];
		}
		return date3;
	}

	/**
	 * 判断是不是数字
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public static boolean isUTF8(String key){
        try {
            key.getBytes("utf-8");
            return true;
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

//	public static void main(String[] args) throws Exception {
//		String path = "E:/project/oc/";
//		String date1 = "2017-07-23";
//		Map<Integer, List<?>> map = readOcTxt(path, date1);
//		System.out.println("list:"+map.size());
//		System.out.println(map);
//		// System.out.println(checkBatchNo("FMR00000d00"));
//		//checkLineNo("45t");
//	}
}
