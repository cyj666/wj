package com.hd.service;

import java.util.List;
import java.util.Map;

import com.hd.pojo.OliContent;

public interface OcService {
	//存入含油数据
	public void saveOc(List<OliContent> ocList);
	
	public Map<Integer, List<?>> readOc(String path, String date);
	
	public boolean findOc(String batchNo,String createDate);

	public List<OliContent> findSomeByCreateDate(String createDate);

	public void deleteOliContent(String batchNo, String lineNo, String createDate);

	public void saveDeleteOliContent(List<OliContent> ocDelList);

	public void updateOliContent(String batchNo, String lineNo, String createDate);
}
