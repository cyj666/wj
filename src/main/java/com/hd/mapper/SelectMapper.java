package com.hd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pojo.NyLon;
import com.hd.pojo.NyLonSon;

public interface SelectMapper {

	List<NyLon> findByDay(String date);
	
	List<NyLon> findByDateRange(@Param("date1") String date1,@Param("date2") String date2);

	List<String> findByStartAndEnd(@Param("start") String start, @Param("end") String end);

	List<NyLon> findBatchNoData(@Param("month")String month, @Param("batchNo") String batchNo);

	List<NyLon> findCheckAgainNyLonByDate(@Param("date") String date, @Param("prefix") String prefix);

	List<NyLon> findCheckAgainNyLonByDateAndPrefix(@Param("date") String date, @Param("fp") String firstPrefix, 
			@Param("sp") String secondPrefix, @Param("tp") String thirdPrefix);

	List<NyLonSon> findBatchNoDataByStartAndEnd(@Param("batchNo") String batchNo, @Param("start") String start, 
			@Param("end") String end);

}
