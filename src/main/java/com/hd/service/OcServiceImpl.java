package com.hd.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.mapper.OcMapper;
import com.hd.pojo.OliContent;
import com.hd.tool.ReadOliContent;

@Service
public class OcServiceImpl implements OcService {
	
	@Autowired
	private OcMapper ocMapper;
	
	@Override
	public void saveOc(List<OliContent> ocList) {
		ocMapper.saveOc(ocList);
	}

	@Override
	public Map<Integer, List<?>> readOc(String path,String date) {
		Map<Integer, List<?>> map = new HashMap<Integer, List<?>>(16);
		try {
			map = ReadOliContent.readOcTxt(path, date);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return map;
	}

	@Override
	public boolean findOc(String batchNo,String createDate) {
		 int count =  ocMapper.findOc(batchNo, createDate);
		 if(count>0) {
			 return true;
		 }
		return false;
	}

	@Override
	public List<OliContent> findSomeByCreateDate(String createDate) {
		return ocMapper.findSomeByCreateDate(createDate);
	}

	@Override
	public void deleteOliContent(String batchNo, String lineNo, String createDate) {
		ocMapper.deleteOliContent(batchNo, lineNo, createDate);
	}

	@Override
	public void saveDeleteOliContent(List<OliContent> ocDelList) {
		ocMapper.saveDeleteOliContent(ocDelList);
	}

	@Override
	public void updateOliContent(String batchNo, String lineNo, String createDate) {
		ocMapper.updateOliContent(batchNo, lineNo, createDate);
	}
}
