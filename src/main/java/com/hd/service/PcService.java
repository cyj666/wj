package com.hd.service;

import java.util.List;
import java.util.Map;

import com.hd.pojo.PercentCrimp;

public interface PcService {

	Map<Integer, Object> readPc(String path);

	boolean findOneByBatchNoLineNoAndCreateDate(String batchNo, String lineNo, String createDate);

	void savePc(List<PercentCrimp> pcList);
	
	public List<PercentCrimp> findSomeByCreateDate(String createDate);

	void deletePercentCrimp(String batchNo, String lineNo, String createDate);

	void saveDeletePercentCrimp(List<PercentCrimp> pcDelList);

	void updatePercentCrimp(String batchNo, String lineNo, String createDate);
}
