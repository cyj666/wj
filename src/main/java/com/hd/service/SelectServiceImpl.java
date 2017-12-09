package com.hd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.mapper.SelectMapper;
import com.hd.pojo.NyLon;
import com.hd.pojo.NyLonSon;

@Service
public class SelectServiceImpl implements SelectService {

	@Autowired
	private SelectMapper selectMapper;
	
	@Override
	public List<String> findByStartAndEnd(String start, String end) {
		return selectMapper.findByStartAndEnd(start, end);
	}

	@Override
	public List<NyLon> findBatchNoData(String month, String batchNo) {
		return selectMapper.findBatchNoData(month, batchNo);
	}

	@Override
	public List<NyLon> findByDay(String date) {
		return selectMapper.findByDay(date);
	}

	@Override
	public List<NyLon> findCheckAgainNyLonByDate(String date, String prefix) {
		return selectMapper.findCheckAgainNyLonByDate(date, prefix);
	}

	@Override
	public List<NyLon> findCheckAgainNyLonByDateAndPrefix(String date, String firstPrefix, String secondPrefix,
			String thirdPrefix) {
		return selectMapper.findCheckAgainNyLonByDateAndPrefix(date, firstPrefix, secondPrefix, thirdPrefix);
	}

	@Override
	public List<NyLonSon> findBatchNoDataByStartAndEnd(String batchNo, String start, String end) {
		return selectMapper.findBatchNoDataByStartAndEnd(batchNo, start, end);
	}
	
	@Override
	public List<NyLon> findByDateRange(String date1, String date2) {		
		return selectMapper.findByDateRange(date1, date2);
	}

}
