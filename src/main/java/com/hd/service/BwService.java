package com.hd.service;

import java.util.List;
import java.util.Map;

import com.hd.pojo.BoilingWater;

public interface BwService {

	Map<Integer, Object> readBw(String path);

	boolean findOneByBatchNoLineNoAndCreateDate(String batchNo, String lineNo, String createDate);

	void saveBw(List<BoilingWater> bwList);

	public List<BoilingWater> findSomeByCreateDate(String createDate);

	void deleteBoilingWater(String batchNo, String lineNo, String createDate);

	void saveDeleteBoilingWater(List<BoilingWater> bwDelList);

	void updateBoilingWater(String batchNo, String lineNo, String createDate);
}
