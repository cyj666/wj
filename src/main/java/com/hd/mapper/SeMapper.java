package com.hd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pojo.StrElongation;

public interface SeMapper {
	
	public void saveSe(@Param("seList") List<StrElongation> seList);
	
	public int findOneByBatchNoLineNoAndCreateDate(@Param("batchNo")String batchNo, 
			@Param("lineNo")String lineNo, @Param("createDate")String createDate);

	public List<StrElongation> findSomeByCreateDate(String createDate);

	public void updateStrElongation(@Param("batchNo")String batchNo, 
			@Param("lineNo")String lineNo, @Param("createDate")String createDate);

	public void saveDeleteStrElongation(@Param("seDelList") List<StrElongation> seDelList);

	public void deleteStrElongation(@Param("batchNo")String batchNo, 
			@Param("lineNo")String lineNo, @Param("createDate")String createDate);
}
