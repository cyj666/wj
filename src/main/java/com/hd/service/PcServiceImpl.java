package com.hd.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.mapper.PcMapper;
import com.hd.pojo.PercentCrimp;
import com.hd.tool.ReadPercentCrimp;

@Service
public class PcServiceImpl implements PcService {

	@Autowired
	private PcMapper pcMapper;
	
	@Override
	public Map<Integer, Object> readPc(String path) {
		Map<Integer, Object> pcMap = null;
		try {
			pcMap = ReadPercentCrimp.readPCTxt(path);
			
			return pcMap;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean findOneByBatchNoLineNoAndCreateDate(String batchNo, String lineNo, String createDate) {
		int count = pcMapper.findOneByBatchNoLineNoAndCreateDate(batchNo, lineNo, createDate);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void savePc(List<PercentCrimp> pcList) {
		pcMapper.savePc(pcList);
	}

	@Override
	public List<PercentCrimp> findSomeByCreateDate(String createDate) {
		return pcMapper.findSomeByCreateDate(createDate);
	}

	@Override
	public void deletePercentCrimp(String batchNo, String lineNo, String createDate) {
		pcMapper.deletePercentCrimp(batchNo, lineNo, createDate);
	}

	@Override
	public void saveDeletePercentCrimp(List<PercentCrimp> pcDelList) {
		pcMapper.saveDeletePercentCrimp(pcDelList);
	}

	@Override
	public void updatePercentCrimp(String batchNo, String lineNo, String createDate) {
		pcMapper.updatePercentCrimp(batchNo, lineNo, createDate);
	}

}
