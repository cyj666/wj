package com.hd.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.mapper.BwMapper;
import com.hd.pojo.BoilingWater;
import com.hd.tool.ReadBoilingWater;

@Service
public class BwServiceImpl implements BwService {
	
	@Autowired
	private BwMapper bwMapper;
	
	@Override
	public Map<Integer, Object> readBw(String path) {
		Map<Integer, Object> bwMap = null;
		//读取文件
		try {
			bwMap = ReadBoilingWater.readBWTxt(path);
			return bwMap;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean findOneByBatchNoLineNoAndCreateDate(String batchNo, 
			String lineNo, String createDate) {
		int count = bwMapper.findOneByBatchNoLineNoAndCreateDate(batchNo, lineNo, createDate);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void saveBw(List<BoilingWater> bwList) {
		bwMapper.saveBw(bwList);
	}

	@Override
	public List<BoilingWater> findSomeByCreateDate(String createDate) {
		return bwMapper.findSomeByCreateDate(createDate);
	}

	@Override
	public void deleteBoilingWater(String batchNo, String lineNo, String createDate) {
		bwMapper.deleteBoilingWater(batchNo, lineNo, createDate);
	}

	@Override
	public void saveDeleteBoilingWater(List<BoilingWater> bwDelList) {
		bwMapper.saveDeleteBoilingWater(bwDelList);
	}

	@Override
	public void updateBoilingWater(String batchNo, String lineNo, String createDate) {
		bwMapper.updateBoilingWater(batchNo, lineNo, createDate);
	}
}
