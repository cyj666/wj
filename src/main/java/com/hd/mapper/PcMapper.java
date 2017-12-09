package com.hd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pojo.PercentCrimp;

public interface PcMapper {

	int findOneByBatchNoLineNoAndCreateDate(@Param("batchNo") String batchNo, 
			@Param("lineNo") String lineNo, @Param("createDate") String createDate);

	void savePc(@Param("pcList") List<PercentCrimp> pcList);

	List<PercentCrimp> findSomeByCreateDate(String createDate);

	void deletePercentCrimp(@Param("batchNo") String batchNo, 
			@Param("lineNo") String lineNo, @Param("createDate") String createDate);

	void saveDeletePercentCrimp(@Param("pcDelList")List<PercentCrimp> pcDelList);

	void updatePercentCrimp(@Param("batchNo") String batchNo, 
			@Param("lineNo") String lineNo, @Param("createDate") String createDate);
	
}
