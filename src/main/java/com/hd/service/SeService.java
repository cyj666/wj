package com.hd.service;

import java.util.List;
import java.util.Map;

import com.hd.pojo.StrElongation;

/**
 * 强伸的服务层，处理逻辑
 * @author Bob
 *
 */
public interface SeService {
	public void saveSe(List<StrElongation> seList);
	
	/**
	 * 读取上传的强伸文件
	 * @param path	文件的路径
	 * @return	含有强伸数据的集合或者含有错误文件名的集合
	 */
	public Map<Integer, Object> readSe(String path);

	public boolean findOneByBatchNoLineNoAndCreateDate(String batchNo, String lineNo, String createDate);

	public List<StrElongation> findSomeByCreateDate(String createDate);

	public void updateStrElongation(String batchNo, String lineNo, String createDate);

	public void saveDeleteStrElongation(List<StrElongation> seDelList);

	public void deleteStrElongation(String batchNo, String lineNo, String createDate);
	
}
