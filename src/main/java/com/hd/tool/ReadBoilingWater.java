package com.hd.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.pojo.BoilingWater;

/**
 * 读取沸水的文件
 * @author Bob
 *
 */
public class ReadBoilingWater {
	
	/**
	 * 根据文件夹的路径，读取文件夹下的所有文件
	 * @param path	路径
	 * @return	含有数据的文件的集合，以及含有错误文件名的集合
	 * @throws Exception
	 */
	public static Map<Integer, Object> readBWTxt(String path) throws Exception{
		
		//判断路径存不存在
		if (StringUtils.isEmpty(path)) {
			return null;
		}
		
		File file = new File(path);
		//判断文件是否存在
		if(!file.exists()) {
			return null;
		}
		//判断文件是不是文件夹
		if(!file.isDirectory()) {
			return null;
		}
		String[] fileList = file.list();
		
		//定义表单的日期
		String dateTime = null;
		
		//定义集合存放对象
		List<BoilingWater> bwList = new ArrayList<BoilingWater>();
		//创建map集合对象用来存放数据集合和文件名集合
		Map<Integer, Object> map = new HashMap<Integer, Object>(16);
		//创建map集合存放文件名称
		Map<String, String> fileNameMap = new HashMap<String, String>(16);
		//List<String> fileNameList = new ArrayList<String>();
		
		//遍历文件夹下中所有的文件
		for (String fileName : fileList) {
			//定义一个中间线位号的集合,用来判断线位号数量和数据数量是否一致
			List<String> middleLineNoList = new ArrayList<String>();
			//将三组线位号分别存放
			Map<Integer, String> middleLineNoMap = new LinkedHashMap<Integer, String>(16);
			
			//定义一个中间批号的集合
			Map<Integer, String> middleBatchNoMap = new LinkedHashMap<Integer, String>(16);
			//定义一个中间集合
			List<BoilingWater> middleBWList = new ArrayList<BoilingWater>();
			
			String time = fileName.substring(fileName.indexOf("_")+1, fileName.indexOf("."));
			String[] times = time.split("_");
			dateTime = times[0]+"-"+times[1]+"-"+times[2]+" "+times[3]+":"+times[4];
			//创建对象
			BoilingWater bw = null;
			file = new File(path + "/" + fileName);
			//设置io流变量
			InputStream is = null;
			BufferedReader br = null;
			try {
				is = new FileInputStream(file);
				br = new BufferedReader(new InputStreamReader(is, "utf-8"));
				String line = null;
				
				int startNum1 = 0;int endNum1 = 0;
				int startNum2 = 0;int endNum2 = 0;
				int startNum3 = 0;int endNum3 = 0;
				//定义读取到哪一行
				int index = 0;
				//遍历文件的行
				while((line = br.readLine()) != null) {
					index++;
					if(index == 6) {
						String[] lines = line.split(",");
						//遍历获得行数
						for (int i=0; i<lines.length; i++) {
							if(i==0) {
								startNum1 = Integer.parseInt(lines[i]);
								endNum1 = Integer.parseInt(lines[i+1]);
							}else if(i==2) {
								startNum2 = Integer.parseInt(lines[i]);
								endNum2 = Integer.parseInt(lines[i+1]);
							}else if(i==4){
								startNum3 = Integer.parseInt(lines[i]);
								endNum3 = Integer.parseInt(lines[i+1]);
							}
						}
					}else if(index == 7) {
						String[] batchNos = line.split("\"");
						//判断输入的批号是否符合格式
						if (!checkBatchNo(batchNos)) {
							fileNameMap.put(fileName, "批号输入有错误");
							continue;
						}
						//处理数据
						middleBatchNoMap = getBatchNoList(batchNos);
					}else if(index == 8) {
						String[] lineNos = line.split("\"");
						//判断输入的线位号是不是符合要求
						if (!checkLineNo(lineNos)) {
							if (fileNameMap.containsKey(fileName)) {
								fileNameMap.put(fileName, "线位号、批号输入都有错误");
							} else {
								fileNameMap.put(fileName, "线位号输入有错误");
							}
							break;
						}
						//处理数据,获取含油线位号的集合
						middleLineNoList = getLineNoList(lineNos);
						middleLineNoMap = getLineNoMap(lineNos);
					}else if(index>=9 && index<10+endNum1-startNum1) {
						if(endNum1>0 && startNum1>0) {
							//判断是不是卷曲文件
							if (line.split(",").length != 3) {
								middleLineNoList.removeAll(middleLineNoList);
								break;
							}
							
							//创建对象
							bw = new BoilingWater();
							bw.setCreateDate(dateTime);
							String bwStr = line.split(",")[2];
							if (bwStr.matches("^[0-9]{1,2}(\\.[0-9]{1,7})?$")) {
								bw.setBoilingWater(Integer.parseInt(new DecimalFormat("#")
										.format(Double.parseDouble(bwStr)*10)));
							} else {
								bw.setBoilingWater(0);
							}
							//将对象添加到中间集合中
							middleBWList.add(bw);
							bw = null;
						}
					}else if(index>=10+endNum1-startNum1 && index<11+endNum1-startNum1+endNum2-startNum2) {
						if(endNum2>0 && startNum2>0) {
							//创建对象
							bw = new BoilingWater();
							bw.setCreateDate(dateTime);
							String bwStr = line.split(",")[2];
							if (bwStr.matches("^[0-9]{1,2}(\\.[0-9]{1,7})?$")) {
								bw.setBoilingWater(Integer.parseInt(new DecimalFormat("#")
										.format(Double.parseDouble(bwStr)*10)));
							} else {
								bw.setBoilingWater(0);
							}
							//将对象添加到中间集合中
							middleBWList.add(bw);
							bw = null;
						}
					}else if(index>=11+endNum1-startNum1+endNum2-startNum2 
							&& index<12+endNum1-startNum1+endNum2-startNum2+endNum3-startNum3) {
						if(endNum3>0 && startNum3>0) {
							//创建对象
							bw = new BoilingWater();
							bw.setCreateDate(dateTime);
							if (line.split(",")[2].matches("^[0-9]{1,2}(\\.[0-9]{1,7})?$")) {
								bw.setBoilingWater(Integer.parseInt(new DecimalFormat("#")
										.format(Double.parseDouble(line.split(",")[2])*10)));
							} else {
								bw.setBoilingWater(0);
							}
							//将对象添加到中间集合中
							middleBWList.add(bw);
							bw = null;
						}
					}
				}
				//判断线位号数量与对应的数据数量是否一致
				if (middleBWList.size() != middleLineNoList.size()) {
					fileNameMap.put(fileName, "线位号与数据不对应");
					continue;
				}
				
				br.close();
			} finally {
				//file.delete();
				if(br!=null) {
					br = null;
				}
			}
			
			//创建一个集合用来存放批号+线位号+状态
			List<String> allList = new ArrayList<String>();
			
			if (!middleBatchNoMap.isEmpty() && !middleLineNoList.isEmpty()) {
				for (Integer key : middleBatchNoMap.keySet()) {
					//获取的一组批号和线位号
					String batchNoGroup = middleBatchNoMap.get(key);
					String lineNoGroup = middleLineNoMap.get(key);
					
					if (batchNoGroup.contains(",")) {
						//含有多个批号
						String[] batchNoArr = batchNoGroup.split(",");
						List<String> lineNoList = getLineNoList(lineNoGroup);
						
						//批号的数量
						int batchNoNum = batchNoArr.length;
						//不同线的数量
						int lineNoNum = lineNoGroup.split("/").length;
						if (batchNoNum != lineNoNum) {
							fileNameMap.put(fileName, "线位号与批号不对应");
							continue;
						}
						
						List<String> pxsList = getAllList(batchNoArr, lineNoList);
						allList.addAll(pxsList);
					}
					
					if (!batchNoGroup.contains(",")) {
						//只含有一个批号
						List<String> lineNoList = getLineNoList(lineNoGroup);
						String batchNo = null;
						int status = 0;
						if (batchNoGroup.contains("-")) {
							status = Integer.parseInt(batchNoGroup.substring(
									batchNoGroup.indexOf("-")+1));
							batchNo = batchNoGroup.substring(0, batchNoGroup.indexOf("-"));
						} else {
							batchNo = batchNoGroup;
						}
						for (String lineNo : lineNoList) {
							allList.add(batchNo + "," + lineNo + "," + status);
						}
					}
				}
			}
			
			//遍历存放BoilingWater对象的集合，并且给每个对象设置三个属性批号、线位号和状态
			if (fileNameMap.isEmpty() && !middleBWList.isEmpty() && !allList.isEmpty()) {
				for (int i = 0; i < middleBWList.size(); i++) {
					BoilingWater bw1 = middleBWList.get(i);
					//包含批号，线位号和状态这三种数据
					String[] threeData = allList.get(i).split(",");
					bw1.setBatchNo(threeData[0]);
					bw1.setLineNo(threeData[1]);
					bw1.setBwStatus(Integer.parseInt(threeData[2]));
				}
			}
			bwList.addAll(middleBWList);
		}
		
//		for (BoilingWater bw : bwList) {
//			System.out.println(bw);
//		}
		
//		for (String fileName : fileNameMap.keySet()) {
//			System.out.println(fileName + " : " + fileNameMap.get(fileName));
//		}
		map.put(1, bwList);
		map.put(2, fileNameMap);
		
		return map;
	}
	
	/**
	 * 将含有批号的数组和含有线位号的集合中数据一一对应起来
	 * @param batchNoArr	批号数组
	 * @param lineNoList	线位号集合
	 * @return
	 */
	private static List<String> getAllList(String[] batchNoArr, List<String> lineNoList) {
		int index = 0;
		String lineNo1 = lineNoList.get(0);
		String prefix = lineNo1.substring(0, lineNo1.indexOf("-"));
		//存放批号，线位号，状态的集合
		List<String> allList = new ArrayList<String>();
		for (String lineNo : lineNoList) {
			//跟集合前一个线位号的前缀比较看是否一致
			if (prefix.equals(lineNo.substring(0, lineNo.indexOf("-")))) {
				//一致
//				System.out.println(prefix + ":" + "ONE");
				String batchNos = batchNoArr[index];
				
				//获取批号，线位号和状态，并添加到集合中
				groupThreeFile(batchNos, allList, lineNo);
			} else {
				index++;
				//不一致
				prefix = lineNo.substring(0, lineNo.indexOf("-"));
				
				String batchNos = batchNoArr[index];
				groupThreeFile(batchNos, allList, lineNo);
//				System.out.println(prefix + ":" +"TWO");
				
			}
		}
		
		return allList;
	}

	/**
	 * 将批号，线位号和状态组合起来添加到集合中
	 * @param batchNos	一个包含状态的批号
	 * @param allList	
	 */
	private static void groupThreeFile(String batchNos, List<String> allList, String lineNo) {
		String batchNo = null;
		String status = null;
		if (batchNos.contains("-")) {
			//除了日常丝的其他状态
			batchNo = batchNos.substring(0, batchNos.indexOf("-"));
			status = batchNos.substring(batchNos.indexOf("-")+1);
			allList.add(batchNo + "," + lineNo + "," + status);
		} else {
			//日常丝
			allList.add(batchNos + "," + lineNo + "," + 0);
		}
	}

	/**
	 * 核对输入的线位号是不是符合格式
	 * @param lineNos	含有无效数据以及线位号的数组
	 * @return	true 格式正确; false 格式错误
	 */
	private static boolean checkLineNo(String[] lineNos) {
		//定义正则表达式
		//匹配后纺线位号
		String reg1 = "^[A-Z]{1}[0-9]{3}-[0-9]{1,3}(,[0-9]{1,3}){0,12}$";
		String reg3 = "^[A-Z]{1}[0-9]{3}-[0-9]{1,2}\\-[0-9]{1,2}$";
		//匹配前纺线位号
		String reg2 = "^[0-9]{1,2}[A-Z]{1}-[0-9]{1,3}(,[0-9]{1,3}){0,12}$";
		String reg4 = "^[0-9]{1,2}[A-Z]{1}-[0-9]{1,2}-[0-9]{1,2}$";
		//定义中间布尔值
		boolean flag = false;
		//遍历
		for (String lineNoGroup : lineNos) {
			lineNoGroup = lineNoGroup.trim().toUpperCase();
			//舍去没有线位号的数据
			if (lineNoGroup.contains("-")) {
				//取得两种以上的机台号数据
				if (lineNoGroup.contains("/")) {
					lineNos = lineNoGroup.split("/");
					for (String lineNo : lineNos) {
						if (lineNo.matches(reg1) || lineNo.matches(reg2)
								|| lineNo.matches(reg3) || lineNo.matches(reg4)) {
							//格式正确
							flag = true;
						} else {
							//格式不正确
							flag = false;
							return flag;
						}
					}
				} else {
					if (lineNoGroup.matches(reg1) || lineNoGroup.matches(reg2)
							|| lineNoGroup.matches(reg3) || lineNoGroup.matches(reg4)) {
						flag = true;
					} else {
						flag = false;
						return flag;
					}
				}
			}
		}
		return flag;
	}
	
	/**
	 * 处理线位号所在的那一行
	 * @param lineNos 含油许多无效数据以及线位号的数组
	 * @return	只有线位号的集合
	 */
	private static List<String> getLineNoList(String[] lineNos) {
		//创建存入线位号的集合
		List<String> lineNoList = new ArrayList<String>();
		//遍历
		for (String lineNoGroup : lineNos) {
			lineNoGroup = lineNoGroup.trim().toUpperCase();
			//舍去没有线位号的数据
			if (lineNoGroup.contains("-")) {
				//取得两种以上的机台号数据
				if (lineNoGroup.contains("/")) {
					// 线位号   40A-3,4/1A-4,5,2/F102-1,2,3/F103-1-12
					for (String lineNo : lineNoGroup.split("/")) {
						analyseLineNo(lineNoList, lineNo);
					}
				} else {
					// 线位号前缀	40A-3,4  F102-1-12
					analyseLineNo(lineNoList, lineNoGroup);
				}
			}
		}
		return lineNoList;
	}
	
	/**
	 * 处理一组线位号
	 * @param lineNoGroup	含有一组线位号的字符串
	 * @return	分析完成的线位号集合
	 */
	private static List<String> getLineNoList(String lineNoGroup) {
		//创建存入线位号的集合
		List<String> lineNoList = new ArrayList<String>();

		if (lineNoGroup.contains("/")) {
			//遍历
			for (String lineNo : lineNoGroup.split("/")) {
				analyseLineNo(lineNoList, lineNo);
			}
			
		} else {
			analyseLineNo(lineNoList, lineNoGroup);
		}
		return lineNoList;
	}
	
	/**
	 * 将三组线位号分别取出
	 * @param lineNos 含油许多无效数据以及线位号的数组
	 * @return	三组线位号
	 */
	private static Map<Integer, String> getLineNoMap(String[] lineNos) {
		//创建一个map集合
		Map<Integer, String> lineNoMap = new LinkedHashMap<Integer, String>(16);
		
		int i = 0;
		for (String lineNoGroup : lineNos) {
			lineNoGroup = lineNoGroup.trim().toUpperCase();
			//舍去没有线位号的数据
			if (lineNoGroup.contains("-")) {
				i++;
				lineNoMap.put(i, lineNoGroup);
			}
		}
		return lineNoMap;
	}
	
	/**
	 * 处理批号所在的那一行
	 * @param batchNos 含有许多无效数据以及批号的数组
	 * @return	存有批号的集合
	 */
	private static Map<Integer, String> getBatchNoList(String[] batchNos) {
		//创建批号的集合
		//List<String> batchNoList = new ArrayList<String>();
		Map<Integer, String> batchNoMap = new LinkedHashMap<Integer, String>(16);
		int j = 0;
		//遍历
		for (String batchNoGroup : batchNos) {
			batchNoGroup = batchNoGroup.trim().toUpperCase();
			//匹配合格的批号
			if (!"".equals(batchNoGroup) && !batchNoGroup.matches(",")) {
				j++;
				batchNoMap.put(j, batchNoGroup);
			}
		}
		return batchNoMap;
	}
	
	/**
	 * 检测批号输入格式是否正确
	 * @param batchNos	含有许多无效数据以及批号的数组
	 * @return	true 格式正确; false 格式不正确
	 */
	private static boolean checkBatchNo(String[] batchNos) {
		//定义正则表达式   最多能输8个批号
		String reg = "^[A-Z]{3}[A-Z0-9]{8}(-[1-4]{1}[1-9])?"
				+ "(,[A-Z]{3}[A-Z0-9]{8}(-[1-4]{1}[1-9])?){0,7}$";
		
		//定义boolean中间参数
		boolean flag = false;
		for (String batchNoGroup : batchNos) {
			batchNoGroup = batchNoGroup.trim().toUpperCase();
			//匹配合格的批号,去掉空字符串，以及只有逗号
			if (!"".equals(batchNoGroup) && !batchNoGroup.matches(",")) {
				//System.out.println(batchNoGroup);
				if (batchNoGroup.matches(reg)) {
					flag = true;
				} else {
					flag = false;
					return flag;
				}
			}
		}
		return flag;
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
	
//	public static void main(String[] args) throws Exception{
//		String str1 = "\"G301-1-6\",\"F301-1-12/F302-1,4/F303-1,5\",\"F301-1,3\",2500,2500,2500";
//		System.out.println(str1);
//		String[] lineNos = str1.split("\"");
//		System.out.println(checkLineNo(lineNos));
//		Map<Integer, String> lineNoMap = getLineNoMap(lineNos);
//		for (Integer i : lineNoMap.keySet()) {
//			System.out.println(i + " : " +lineNoMap.get(i));
//		}
//		
//		System.out.println("===========================================");
//		String str2 = "\"\",\"\",\"\",\"FMT05012211,FMT05012211,FMT05012211\",\"FMT02207211,FMT02207211\",\"XYZ09909099\"";
//		System.out.println(str2);
//		String[] batchNos = str2.split("\"");
//		Map<Integer, String> batchNoMap = getBatchNoList(batchNos);
//		for (Integer i : batchNoMap.keySet()) {
//			System.out.println(i + " : " + batchNoMap.get(i));
//		}
//		
//		List<String> lineNoList = new ArrayList<String>();
//		for (int x = 1; x < 5; x++) {
//			for (int y = 0; y < 5; y++) {
//				lineNoList.add("F10" + x + "-" + y);
//			}
//		}
//		
//		String[] batchNoArr = {"FXXX", "FYYY", "FUUU", "FZZZ"};
//		System.out.println("====================================================");
//		getAllList(batchNoArr, lineNoList);
//		
//		System.out.println("=======================读取文件=============================");
//		readBWTxt("e:/project/z");
//		
//		System.out.println("100".matches("^\\d{1,2}(\\.[0-9]{0,6})?$"));
//	}
	
	
}
