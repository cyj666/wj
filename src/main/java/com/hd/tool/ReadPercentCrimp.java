package com.hd.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.pojo.PercentCrimp;

/**
 * 读取卷曲稳定度、卷曲收缩率文件
 * @author Bob
 *
 */
public class ReadPercentCrimp {
	
	/**
	 * 根据文件夹的路径，读取文件夹下的所有文件
	 * @param path	路径
	 * @return	含有数据的文件的集合，以及含有错误文件的集合
	 * @throws Exception
	 */
	public static Map<Integer, Object> readPCTxt(String path) throws Exception { 
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
		List<PercentCrimp> pcList = new ArrayList<PercentCrimp>();
		//创建map集合对象用来存放数据集合和文件名集合
		Map<Integer, Object> map = new HashMap<Integer, Object>(16);
		//创建map集合存放文件名称
		Map<String, String> fileNameMap = new HashMap<String, String>(16);
		//List<String> fileNameList = new ArrayList<String>();
		
		//遍历文件夹下中所有的文件
		for (String fileName : fileList) {
			//定义一个中间线位号的集合
			List<String> middleLineNoList = new ArrayList<String>();
			//定义一个中间批号的集合
			List<String> middleBatchNoList = new ArrayList<String>();
			//定义一个中间集合
			List<PercentCrimp> middlePCList = new ArrayList<PercentCrimp>();
			
			String time = fileName.substring(fileName.indexOf("_")+1, fileName.indexOf("."));
			String[] times = time.split("_");
			dateTime = times[0]+"-"+times[1]+"-"+times[2]+" "+times[3]+":"+times[4];
			//创建对象
			PercentCrimp pc = null;
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
						middleBatchNoList = getBatchNoList(batchNos);
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
					}else if(index>=9 && index<10+endNum1-startNum1) {
						if(endNum1>0 && startNum1>0) {
							//判断是不是沸水文件
							if (line.split(",").length != 5) {
								middleLineNoList.removeAll(middleLineNoList);
								break;
							}

							//创建卷曲度对象
							pc = new PercentCrimp();
							//设置卷曲度的属性
							setPercentCrimp(pc, dateTime, line);
							
							middlePCList.add(pc);
							pc = null;
						}
					}else if(index>=10+endNum1-startNum1 && index<11+endNum1-startNum1+endNum2-startNum2) {
						if(endNum2>0 && startNum2>0) {
							//创建卷曲度对象
							pc = new PercentCrimp();
							
							//设置卷曲度的属性
							setPercentCrimp(pc, dateTime, line);
							
							middlePCList.add(pc);
							pc = null;
						}
					}else if(index>=11+endNum1-startNum1+endNum2-startNum2 && index<12+endNum1-startNum1+endNum2-startNum2+endNum3-startNum3) {
						if(endNum3>0 && startNum3>0) {
							//创建卷曲度对象
							pc = new PercentCrimp();
							
							//设置卷曲度的属性
							setPercentCrimp(pc, dateTime, line);
							
							middlePCList.add(pc);
							pc = null;
						}
					}
				}
				//判断线位号数量与对应的数据数量是否一致
				if (middlePCList.size() != middleLineNoList.size()) {
					fileNameMap.put(fileName, "线位号、批号与数据不对应");
					continue;
				}
				
				br.close();
			} finally {
				//file.delete();
				if(br!=null) {
					br = null;
				}
			}
			
			//三个中间集合都不能为空
			if (!middleBatchNoList.isEmpty() && !middleLineNoList.isEmpty() 
					&& !middlePCList.isEmpty()) {
				//定义线位号前缀不同数量
				int count = 0;
				//取得第一个线位号的前缀
				String prefixs = middleLineNoList.get(0).substring(0, middleLineNoList.get(0).indexOf("-"));
				
				//遍历中间线位号、中间批号、对象集合，设置对象的线位号与批号属性
				//遍历中中间线位号集合
				for (int i = 0; i < middleLineNoList.size(); i++) {
					//判断第二线位号和第一个线位号前缀是不是一样
					if (middleLineNoList.get(i).contains(prefixs)) {
						//取得第一个批号
						String batchNo = middleBatchNoList.get(count);
						//判断批号是不是有"-"，来区分日常丝和其他状态的丝
						if (batchNo.contains("-")) {
							//设置批号
							middlePCList.get(i).setBatchNo(batchNo.substring(0, batchNo.indexOf("-")));
							//设置状态
							middlePCList.get(i).setPcStatus(Integer.parseInt(batchNo.substring(batchNo.indexOf("-")+1)));
						} else {
							//直接设置批号
							middlePCList.get(i).setBatchNo(batchNo);
							middlePCList.get(i).setPcStatus(0);
						}
						//设置线位号
						middlePCList.get(i).setLineNo(middleLineNoList.get(i));
					} else {
						//后面的线位号前缀和第一个线位号前缀不一致
						prefixs = middleLineNoList.get(i).substring(0, middleLineNoList.get(i).indexOf("-"));
						//将批号的下标加1
						count++;
						//获取批号
						String batchNo = middleBatchNoList.get(count);
						if (batchNo.contains("-")) {
							//设置批号
							middlePCList.get(i).setBatchNo(batchNo.substring(0, batchNo.indexOf("-")));
							//设置状态
							middlePCList.get(i).setPcStatus(Integer.parseInt(batchNo.substring(batchNo.indexOf("-")+1)));
						} else {
							middlePCList.get(i).setBatchNo(batchNo);
							middlePCList.get(i).setPcStatus(0);
						}
						middlePCList.get(i).setLineNo(middleLineNoList.get(i));
					}
				}
				pcList.addAll(middlePCList);
			}
		}
		
//		for (PercentCrimp pc : pcList) {
//			System.out.println(pc);
//		}
		
		map.put(1, pcList);
		map.put(2, fileNameMap);
		
		return map;
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
		String reg3 = "^[A-Z]{1}[0-9]{3}-[0-9]{1,2}-[0-9]{1,2}$";
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
					// 线位号   40A-3,4/1A-4,5,2
					for (String lineNo : lineNoGroup.split("/")) {
						divisionLineNo(lineNo, lineNoList);
					}
				} else {
					// 线位号前缀	40A-3,4
					divisionLineNo(lineNoGroup, lineNoList);
				}
			}
		}
		return lineNoList;
	}

	/**
	 * 处理批号所在的那一行
	 * @param batchNos 含有许多无效数据以及批号的数组
	 * @return	存有批号的集合
	 */
	private static List<String> getBatchNoList(String[] batchNos) {
		//创建批号的集合
		List<String> batchNoList = new ArrayList<String>();
		
		//遍历
		for (String batchNoGroup : batchNos) {
			batchNoGroup = batchNoGroup.trim().toUpperCase();
			//匹配合格的批号
			if (!"".equals(batchNoGroup) && !batchNoGroup.matches(",")) {
				if (batchNoGroup.contains(",")) {
					batchNos = batchNoGroup.split(",");
					for (int i = 0; i < batchNos.length; i++) {
						batchNoList.add(batchNos[i]);
					}
				} else {
					//只有一个批号
					batchNoList.add(batchNoGroup);
				}
			}

		}
		return batchNoList;
	}
	
	/**
	 * 检测批号输入格式是否正确
	 * @param batchNos	含有许多无效数据以及批号的数组
	 * @return	true 格式正确; false 格式不正确
	 */
	private static boolean checkBatchNo(String[] batchNos) {
		//定义boolean中间参数
		boolean flag = false;
		for (String batchNoGroup : batchNos) {
			batchNoGroup = batchNoGroup.trim().toUpperCase();
			//匹配合格的批号
			if (!"".equals(batchNoGroup) && !batchNoGroup.matches(",")) {
				//System.out.println(batchNoGroup);
				if (batchNoGroup.contains(",")) {
					for (String batchNoLine : batchNoGroup.split(",")) {
						if (checkBatchNo(batchNoLine)) {
							flag = true;
						} else {
							return false;
						}
					}
				} else {
					if (checkBatchNo(batchNoGroup)) {
						flag = true;
					} else {
						return false;
					}
				}
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
			if (batchNo.matches(reg) && (status.matches("[0-1]{1}") 
					|| status.matches("[2-4]{1}[1-9]{1}"))) {
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
	
	/**
	 * 将输入的线位号按正确的格式获取出来
	 * @param lineNoGroup	输如的一组线位号
	 * @param lineNoList	存储线位号的集合
	 */
	private static void divisionLineNo(String lineNoGroup, List<String> lineNoList) {
		//线位号的前缀
		String prefix = lineNoGroup.substring(0, lineNoGroup.indexOf("-")+1);
		//线位号的分开组合
		String[] lineNoArr = lineNoGroup.split(",");
		int index = 0;
		
		for (String lineNo : lineNoArr) {
			index++;
			if (index == 1) {
				lineNoList.add(lineNo);
			}else {
				lineNoList.add(prefix+lineNo);
			}
		}
	}
	
	/**
	 * 设置卷曲度对象的属性
	 * @param pc	卷曲度对象
	 * @param dateTime	测量时间
	 * @param line	数据所在的行
	 */
	private static void setPercentCrimp(PercentCrimp pc, String dateTime, String line) {
		pc.setCreateDate(dateTime);
		String reg = "^-?\\d{1,2}(\\.[0-9]{1,8})?$";
		String ccStr = line.split(",")[3];
		String csStr = line.split(",")[4];
		if (ccStr.matches(reg) && csStr.matches(reg)) {
			//卷曲收缩率
			pc.setCrimpContraction(Integer.parseInt(new DecimalFormat("#").format(Double.parseDouble(ccStr)*10)));
			//卷曲稳定度
			pc.setCrimpStability(Integer.parseInt(new DecimalFormat("#").format(Double.parseDouble(csStr)*10)));
		} else {
			pc.setCrimpContraction(0);
			pc.setCrimpStability(0);
		}
	}
	
	
//	public static void main(String[] args) throws Exception{
//		String str1 = "\"\",\"\",\"\",\"DAS04436321-21,DAS04436311\",\"DAS04436311-23\",\"\"";
//		System.out.println(str1);
//		String[] batchNos = str1.split("\"");
//		System.out.println(getBatchNoList(batchNos).toString());
//		System.out.println(checkBatchNo(batchNos));
//		
//		str1 = "\"9B-1,2,4,5/5B-12\",\"5A-1,2,3,4,5/37B-1,2,3,4,5\",\"\",2500,2500,2500"; 
//		System.out.println(str1);
//		String[] lineNos = str1.split("\"");
//		for (String ss : batchNos) {
//			System.out.println(ss+";");
//		}
//		System.out.println(checkLineNo(lineNos));
//		System.out.println(getLineNoList(lineNos).toString());
//		Map<Integer, Object> map = readPCTxt("e:/project/y");
//		System.out.println("======================================================");
//		List<PercentCrimp> pcList = (List)map.get(1);
//		for (PercentCrimp pc : pcList) {
//			System.out.println(pc);
//		}
//		
//		Map<String, String> fileMap = (Map)map.get(2);
//		for(String key :fileMap.keySet()) {
//			System.out.println(key+" : "+fileMap.get(key));
//		}
//	}
}
