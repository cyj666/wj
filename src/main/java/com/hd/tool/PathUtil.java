package com.hd.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PathUtil {
	
	private static final Logger log = LoggerFactory.getLogger(PathUtil.class);
	//新建一个集合，用来存储文件名
	private static Map<String, String> fileNames = new HashMap<String, String>(16);
	//创建Properties对象
	private static Properties prop = new Properties();
	//创建PathUtil对象
	private static PathUtil pathUtil = new PathUtil();
	

	/**
	 * 获取时间格式为年/月/日/时/分/秒
	 * @return 时间字符串
	 */
	public static String getTime() {
		Calendar cld = Calendar.getInstance();
		
		String year = cld.get(Calendar.YEAR) + "";
		String month = (cld.get(Calendar.MONTH)+1) + "";
		String day = cld.get(Calendar.DAY_OF_MONTH) + "";
		String hour = cld.get(Calendar.HOUR_OF_DAY) + "";
		String minute = cld.get(Calendar.MINUTE) + "";
		String s = cld.get(Calendar.SECOND) + "";
		return year+"/"+month+"/"+day+"/"+hour+"/"+minute+"/"+s+"/";
	}
	
	/**
	 * 获取月份
	 * @return 月份字符串
	 */
	public static String getMonth() {
		Calendar cld = Calendar.getInstance();
		
		String year = cld.get(Calendar.YEAR) + "";
		String month = (cld.get(Calendar.MONTH)+1) + "";
		
		return year+"/"+month+"/";
	}
	
	/**
	 * 获取所有文件名，返回文件名的集合
	 * @param 文件所在路径
	 * @return
	 */
	public static Map<String, String> getAllFileName(String path) {
//		fileNames.removeAll(fileNames);
		fileNames = new HashMap<String, String>();
		
		getFile(path);

		Set<String> keySet = fileNames.keySet();
		for (String key : keySet) {
			System.out.println(fileNames.get(key));
		}
		return fileNames;
	}

	//获取所有文件
	public static void getFile(String path) {
		File file = new File(path);
		if(file.exists()) {
			File[] files = file.listFiles();
			if(files.length!=0) {
				for (File file2 : files) {
					if(file2.isDirectory()) {
						getFile(file2.getAbsolutePath());
					}else {
						fileNames.put(file2.getName(),file2.getName());
					}
				}	
			}else {
				log.debug("文件夹是空的");
				return;
			}
		}else {
			System.out.println("文件不存在");
		}
	}
	
	/** 
	 * 随机指定范围内N个不重复的数 
	 * 最简单最基本的方法 
	 * @param min 指定范围最小值 
	 * @param max 指定范围最大值 
	 * @param n 随机数个数 
	 */  
	public static int[] randomCommon(int min, int max, int n){  
	    if (n > (max - min + 1) || max < min) {  
	           return null;  
	       }  
	    int[] result = new int[n];  
	    int count = 0;  
	    while(count < n) {  
	        int num = (int) (Math.random() * (max - min)) + min;  
	        boolean flag = true;  
	        for (int j = 0; j < n; j++) {  
	            if(num == result[j]){  
	                flag = false;  
	                break;  
	            }  
	        }  
	        if(flag){  
	            result[count] = num;  
	            count++;  
	        }  
	    }  
	    return result;  
	}  
	
	/**
	 * 将路径做为字符串写入到文件中
	 * @param path 数据文件的存储路径
	 */
	public static void savePath(String path, String name) {
		String filePath = pathUtil.getClass().getClassLoader().getResource("path.properties").getPath();
		
		BufferedWriter bw = null;
		try {
			//将路径写到文件中
			File file = new File(filePath);
			OutputStream os = new FileOutputStream(file);
			bw = new BufferedWriter(new OutputStreamWriter(os, "utf-8"));
			
			prop.setProperty(name, path);
			prop.store(bw, "新增路径");
			
			bw.flush();
			bw.close();
		} catch (Exception e) {
			log.debug("文件找不到");
			e.printStackTrace();
		} finally {
			if(bw != null) {
				bw = null;
			}
		}
	}
	
	/**
	 * 获取存储在properties中的文件路径
	 * @param name properties中文件key的名字，如sePath
	 * @return
	 */
	public static String getPath(String name) {
		String sePath = null;
		InputStreamReader isr = null;
		try {
			InputStream is = pathUtil.getClass().getClassLoader().getResourceAsStream("path.properties");
			isr = new InputStreamReader(is, "utf-8");
			Properties prop = new Properties();
			prop.load(is);
			sePath = prop.getProperty(name);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(isr != null) {
				isr = null;
			}
		}
		return sePath;
	}
	
}
