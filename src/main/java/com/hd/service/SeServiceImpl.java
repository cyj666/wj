package com.hd.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.mapper.SeMapper;
import com.hd.pojo.StrElongation;
import com.hd.tool.ReadStrElongation;

@Service
public class SeServiceImpl implements SeService {

	@Autowired
	private SeMapper seMapper;
	
	@Override
	@SuppressWarnings("all")
	public Map<Integer, Object> readSe(String path) {
		Map<Integer, Object> seMap = null;
		
		try {
			seMap = ReadStrElongation.readSETxt(path);
			
			return seMap;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void saveSe(List<StrElongation> seList) {
		seMapper.saveSe(seList);
	}

	@Override
	public boolean findOneByBatchNoLineNoAndCreateDate(String batchNo, String lineNo, String createDate) {
		int count = seMapper.findOneByBatchNoLineNoAndCreateDate(batchNo, lineNo, createDate);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<StrElongation> findSomeByCreateDate(String createDate) {
		return seMapper.findSomeByCreateDate(createDate);
	}

	@Override
	public void updateStrElongation(String batchNo, String lineNo, String createDate) {
		seMapper.updateStrElongation(batchNo, lineNo, createDate);
	}

	@Override
	public void saveDeleteStrElongation(List<StrElongation> seDelList) {
		seMapper.saveDeleteStrElongation(seDelList);
	}

	@Override
	public void deleteStrElongation(String batchNo, String lineNo, String createDate) {
		seMapper.deleteStrElongation(batchNo, lineNo, createDate);
	}

}
