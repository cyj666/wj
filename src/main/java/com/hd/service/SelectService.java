package com.hd.service;

import java.util.List;

import com.hd.pojo.NyLon;
import com.hd.pojo.NyLonSon;

public interface SelectService {
	
	List<NyLon> findByDay(String date);

	List<String> findByStartAndEnd(String start, String end);

	List<NyLon> findBatchNoData(String month, String batchNo);
	
	List<NyLon> findByDateRange(String date1,String date2);

	List<NyLon> findCheckAgainNyLonByDate(String date, String prefix);

	List<NyLon> findCheckAgainNyLonByDateAndPrefix(String date, String firstPrefix, String secondPrefix, String thirdPrefix);

	List<NyLonSon> findBatchNoDataByStartAndEnd(String batchNo, String start, String end);

}
