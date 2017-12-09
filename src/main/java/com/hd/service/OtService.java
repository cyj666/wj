package com.hd.service;

import java.util.List;

import com.hd.pojo.Other;
import com.hd.pojo.StrElongation;

public interface OtService {

	public void saveOther(List<StrElongation> seList);
	
	public List<Other> getOther(String date);
	
	public List<Other> getOtherAll(String date);
	
	public void updateOther(List<Other> list);
	
	public void saveOtherAll(List<Other> otlist);
}
