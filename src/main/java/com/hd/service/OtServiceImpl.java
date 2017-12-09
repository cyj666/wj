package com.hd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.mapper.OtherMapper;
import com.hd.pojo.Other;
import com.hd.pojo.StrElongation;

@Service
public  class OtServiceImpl implements OtService {

	@Autowired
	private OtherMapper othermapper;
	
	@Override
	public void saveOther(List<StrElongation> seList) {
		String batchNo = null;
		String lineNO = null;
		String lineNOLine = null;
		String createDate = null;
		String[] line = null;
		Integer seStatus = null;
		Integer seStatus2 = null;
		List<StrElongation> seListChange = new ArrayList<StrElongation>();
 		for (StrElongation se : seList) {
 			
			 batchNo = se.getBatchNo();
			lineNOLine = se.getLineNo();
			seStatus = se.getSeStatus();
			createDate = se.getCreateDate();
			if (lineNOLine.contains("-")) {
				line = lineNOLine.split("-");
			}
			if ((line[0].equals(lineNO)&&seStatus.equals(seStatus2))||seStatus==1) { //判断两者是否相等，相等就跳过不保存
				//System.out.println(line[0]+"/"+lineNO);
				continue;
			}else if (line!=null&&line.length>1) {
				lineNO = line[0];		
				seStatus2 = seStatus;
				StrElongation se2= new StrElongation();
				se2.setBatchNo(batchNo);
				se2.setLineNo(lineNO);
				se2.setCreateDate(createDate);
				se2.setSeStatus(seStatus2);
				seListChange.add(se2);
			}
			
			
		}
		  seList = seListChange;
		othermapper.saveOther(seList);
	}

	@Override
	public List<Other> getOther(String date) {
		return othermapper.getOther(date);
	}

	@Override
	public List<Other> getOtherAll(String date) {
		// TODO Auto-generated method stub
		return othermapper.getOtherAll(date);
	}
	
	@Override
	public void updateOther(List<Other> list) {
		for (Other other : list) {
			othermapper.updateOther(other);
		}
	}

	@Override
	public void saveOtherAll(List<Other> otlist) {
		othermapper.saveOtherAll(otlist);
	}

	
}
