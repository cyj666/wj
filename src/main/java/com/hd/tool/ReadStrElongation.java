package com.hd.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.pojo.StrElongation;

/**
 * 读取强伸文件数据
 * @author Bob
 *
 */
public class ReadStrElongation {
	
	/**
	 * 根据文件夹的路径，读取文件夹下的所有文件
	 * @param path	路径
	 * @return	含有数据的文件的集合，以及含有错误文件的集合
	 * @throws Exception
	 */
	public static Map<Integer, Object> readSETxt(String path) throws Exception {
		
		File file = new File(path);
		// 判断文件是否存在
		if (!file.exists()) {
			return null;
		}
		// 判断文件是不是文件夹
		if (!file.isDirectory()) {
			return null;
		}
		
		String[] fileList = file.list();
		// 创建行数标记
		int index = 0;
		int i = 0;
		// 创建测量强度和伸长率的对象
		StrElongation se = null;
		// 创建存储该对象的集合
		List<StrElongation> seList = new ArrayList<StrElongation>();
		// 存储行数据
		List<Integer> dataList = new ArrayList<Integer>();
		//创建一个集合保存线位号
		List<String> lineNoList = new ArrayList<String>();
		//创建map集合用来存放返回的数据
		Map<Integer, Object> map = new HashMap<Integer, Object>(16);
		//创建一个map集合用来存放文件名
		Map<String, String> fileNameMap = new HashMap<String, String>(16);
		//List<String> fileNameList = new ArrayList<String>();
		
		// 遍历所有文件的名字
		for (String fileName : fileList) {
			BufferedReader br = null;
			try {
				file = new File(path + "/" + fileName);
				InputStream is = new FileInputStream(file);
				br = new BufferedReader(new InputStreamReader(is, "utf-8"));
				String line = null;
				// 定义有多少数据的行数
				int totalLine = 0;
				//机器检测的次数
				int testNum = 0;

				// 创建一个集合用来存储有效的行
				List<String> lineList = new ArrayList<String>();
				// 创建se对象
				se = new StrElongation();
				String dataTime = null;
				String batchNo = null;
				String format = null;
				//产品的状态
				int seStatus = 0;
				//创建存放StrElongation示例的中间集合
				List<StrElongation> seMiddleList = new ArrayList<StrElongation>();
				while ((line = br.readLine()) != null) {
					index++;
					if (index == 1) {
						//F201-11,12/F202-1-2
						//F201-11,12/F202-1,2
						//F201-11-12/F202-11-12
						//F201-11,12,13
						//F201-11-15
						String allLineNo = line.split("\"")[3].trim().toUpperCase();
						//输入的线位号不符合格式要求
						if (!checkLineNo(allLineNo)) {
							fileNameMap.put(fileName, "线位号输入有错误");
							continue;
						}
						//获得所有线位号的集合
						lineNoList = getLineNo(allLineNo);
					} else if (index == 2) {
						// 获得有用的数据的管数
						totalLine = Integer.parseInt(line.split(",")[4]);
						// 获取机器检测的次数
						testNum = Integer.parseInt(line.split(",")[3]);
					} else if (index == 3) {
						String[] dataLine = line.split(",");
						//获取时间
						dataTime = dataLine[0].substring(1, dataLine[0].lastIndexOf("#"));
						String batchNoLine = null;
						String formatLine = null;
						if("0".equals(dataLine[dataLine.length-2])) {
							batchNoLine = dataLine[dataLine.length-4].toUpperCase();
							formatLine = dataLine[dataLine.length-5];
						}else {
							batchNoLine = dataLine[dataLine.length-5].toUpperCase();
							formatLine  = dataLine[dataLine.length-6];
						}
						batchNoLine = batchNoLine.substring(1,batchNoLine.lastIndexOf("\"")).trim();
						//校验批号
						if (!checkBatchNo(batchNoLine)) {
							if (fileNameMap.containsKey(fileName)) {
								fileNameMap.put(fileName, "批号和线位号都有错误");
							} else {
								fileNameMap.put(fileName, "批号输入有错误");
							}
							lineNoList.clear();
							break;
						}
						if(batchNoLine.contains("-")) {
							//获取线位号
							batchNo = batchNoLine.substring(0, batchNoLine.indexOf("-"));
							//获取状态
							seStatus = Integer.parseInt(batchNoLine.substring(batchNoLine.indexOf("-")+1));
						}else {
							batchNo = batchNoLine;
						}
						format = formatLine.substring(1, formatLine.lastIndexOf("\"")).trim();
					} else if (index >= 4 + totalLine + totalLine * testNum) {
						break;
					}else {
						//文件的前4行不含数据
						int startLine = 4;
						//倍数
						//int multiple = 2;
						//加数
						//int addend = 2;
						for (int j = startLine + totalLine; j < startLine + totalLine + totalLine * testNum; j += testNum) {
							if (index >= j && index < j+testNum) {
								//System.out.println(index);
								i++;
								lineList.add(line);
								if (i == testNum) {
									//设置创建时间
									se.setCreateDate(dataTime);
									//设置产品批号
									se.setBatchNo(batchNo);
									//将有用的行数据进行处理
									dataList = getSe(lineList, testNum);
									//强度
									se.setStrength(dataList.get(0));
									//伸长率
									se.setElongation(dataList.get(1));
									//System.out.println(dataList);
									//设置产品状态
									se.setSeStatus(seStatus);
									//设置规格
									se.setSeFormat(format);
									//将测量强度和伸长率的对象存到集合中
									seMiddleList.add(se);
									//将集合清空
									lineList.clear();
									dataList.clear();
									se = new StrElongation();
									i = 0;
								}
							}
						}
						if (index == 3 + totalLine + totalLine * testNum) {
							if (lineNoList.size() == seMiddleList.size()) {
								for (String lineNo : lineNoList) {
									i++;
									seMiddleList.get(seMiddleList.size()-i).setLineNo(lineNo);
								}
								seList.addAll(seMiddleList);
								lineNoList.clear();
								//将变量i置0
								i = 0;
							} else if (lineNoList.size() != 0) {
								fileNameMap.put(fileName, "线位号、批号与数据不对应");
								break;
							}
						}
					}
				}
				//清空集合
				seMiddleList.clear();
				// 将下标重新置零
				index = 0;
				br.close();
			} finally {
				//将文件删除
				//file.delete();
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
//		for (StrElongation se1 : seList) {
//			System.out.println(se1);
//		}
		map.put(1, seList);
		map.put(2, fileNameMap);
		return map;
	}
	
	/**
	 * 用来处理有用的行数据
	 * @param 存储的行数据
	 * @return 处理之后的有用数据
	 */
	private static List<Integer> getSe(List<String> lineList, int testNum) {
		double strength = 0;double elongation = 0;
		boolean flag = true;
		String reg = "^[0-9]{1,2}(\\.[0-9]{1,6}){0,1}$";
		
		List<Integer> list = new ArrayList<Integer>();

		String[] lineArray = lineList.toArray(new String[lineList.size()]);
		//遍历数组中的数据
		for (int i = 0; i < lineArray.length; i++) {
			if(lineArray[i].split(",").length!=9 || !lineArray[i].split(",")[3].matches(reg) || 
					!lineArray[i].split(",")[2].matches(reg)) {
				flag = false;
			}else {
				//强度相加
				strength += Double.parseDouble(lineArray[i].split(",")[3])*100;
				//伸长率相加
				elongation += Double.parseDouble(lineArray[i].split(",")[2])*10;
			}
		}
		if (flag) {
			//将数据添加到集合中		取得所有整数部分，根据整数的大小来看是四舍五入还是五舍六入
			list.add(Integer.parseInt(new DecimalFormat("#").format(strength/testNum)));
			list.add(Integer.parseInt(new DecimalFormat("#").format(elongation/testNum)));
		} else {
			//不符合格式的数据置零
			list.add(0); list.add(0);
		}
		return list;
	}
	
	/**
	 * 处理输入的线位号
	 * @param allLineNo
	 * @return	包含有所有处理过的线位号的集合
	 */
	private static List<String> getLineNo(String allLineNo) {
		//创建一个含有线位号的集合
		List<String> lineNoList = new ArrayList<String>();
		//定义正斜线
		String slash = "/";
		if (allLineNo.contains(slash)) {
			//F106-3,4/F103-3,4/F107-3,4
			//F106-3-4/F103-3-4
			//F106-3-4/F103-3,4
			String[] lineNoArr = allLineNo.split(slash);
			for (String lineNos : lineNoArr) {
				analyseLineNo(lineNoList, lineNos);
			}
		} else if (!allLineNo.contains(slash)) {
			analyseLineNo(lineNoList, allLineNo);
		}
		
		return lineNoList;
	}
	
	/**
	 * 分析输入的线位号
	 * @param lineNoList	将线位号设置到集合中
	 * @param lineNos		输入的线位号
	 */
	private static void analyseLineNo(List<String> lineNoList, String lineNos) {
		String prefix = null;
		String[] lineNoArr = null;
		//定义连字符
		String hyphen = "-";
		//定义逗号
		String comma = ",";
		if (lineNos.contains(hyphen) && lineNos.contains(comma)) {
			// 线位号前缀	F106-3,4
			prefix = lineNos.substring(0, lineNos.indexOf(hyphen)+1);
			lineNoArr = lineNos.split(comma);
			int index = 0;
			
			for (String lineNo : lineNoArr) {
				index++;
				if (index == 1) {
					lineNoList.add(lineNo);
				}else {
					lineNoList.add(prefix+lineNo);
				}
			}
		}else if (lineNos.contains(hyphen) && !lineNos.contains(comma)) {
			// F106-3-4
			prefix = lineNos.substring(0, lineNos.indexOf(hyphen)+1);
			lineNoArr = lineNos.split(hyphen);
			
			int start = Integer.parseInt(lineNoArr[1]);
			int end = Integer.parseInt(lineNoArr[lineNoArr.length-1]);
			for (int i = start; i <= end; i++) {
				lineNoList.add(prefix+i);
			}
		}
	}
	
	/**
	 * 校验输入的线位号
	 * @param allLineNo	输入的线位号
	 * @return	输入的线位号是否符合格式
	 */
	private static boolean checkLineNo(String allLineNo) {
		// 将输入的线位号分隔
		String[] lineNoArr = allLineNo.split("/");
		// 正则表达式
		String reg1 = "^[A-Z]{1}[0-9]{3}-[0-9]{1,2}-[0-9]{1,2}$";
		String reg2 = "^[0-9]{1,2}[A-Z]{1}-[0-9]{1,2}-[0-9]{1,2}$";
		String reg3 = "^[A-Z]{1}[0-9]{3}-[0-9]{1,3}(,[0-9]{1,3}){0,12}$";
		String reg4 = "^[0-9]{1,2}[A-Z]{1}-[0-9]{1,3}(,[0-9]{1,3}){0,12}$";
		boolean flag = true;
		
		//遍历数组，判断是否符合格式
		for (String lineNos : lineNoArr) {
			if (lineNos.matches(reg1) || lineNos.matches(reg2) 
					|| lineNos.matches(reg3) || lineNos.matches(reg4)) {
				flag = true;
			} else {
				flag = false;
				return flag;
			}
		}
		return flag;
	}
	
	/**
	 * 校验输入的批号
	 * @param batchNoLine	含有状态的批号
	 * @return	boolean
	 */
	private static boolean checkBatchNo(String batchNoLine) {
		//正则表达式
		String reg = "^[A-Z]{3}[0-9A-Z]{8}$";
		if (batchNoLine.contains("-")) {
			String batchNo = batchNoLine.substring(0, batchNoLine.indexOf("-"));
			//System.out.println(batchNo);
			String status = batchNoLine.substring(batchNoLine.indexOf("-")+1);
			if (batchNo.matches(reg) && status.matches("[1-4]{1}[1-9]{1}")) {
				return true;
			} else {
				return false;
			}
		} else {
			if (batchNoLine.matches(reg)) {
				return true;
			} else {
				return false;
			}
		}
	}

	
//	public static void main(String[] args) throws Exception{
//		Map<Integer, Object> map = readSETxt("e:/project/xxx");
//		List<StrElongation> seList = (List)map.get(1);
//		for(StrElongation se : seList) {
//			System.out.println(se);
//		}
//		
//		Map<String, String> fileMap = (Map)map.get(2);
//		for(String key : fileMap.keySet()) {
//			System.out.println(key + " : " + fileMap.get(key));
//		}
//		
//		//System.out.println(checkBatchNo("DAS3284323"));
//	}
	
}
