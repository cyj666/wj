package com.hd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pojo.BoilingWater;

public interface BwMapper {

	void saveBw(@Param("bwList")List<BoilingWater> bwList);

	int findOneByBatchNoLineNoAndCreateDate(@Param("batchNo")String batchNo, 
			@Param("lineNo")String lineNo, @Param("createDate")String createDate);

	List<BoilingWater> findSomeByCreateDate(String createDate);

	void deleteBoilingWater(@Param("batchNo")String batchNo, 
			@Param("lineNo")String lineNo, @Param("createDate")String createDate);

	void saveDeleteBoilingWater(@Param("bwDelList")List<BoilingWater> bwDelList);

	void updateBoilingWater(@Param("batchNo")String batchNo, 
			@Param("lineNo")String lineNo, @Param("createDate")String createDate);

}
