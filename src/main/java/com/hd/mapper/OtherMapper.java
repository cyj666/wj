package com.hd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pojo.Other;
import com.hd.pojo.StrElongation;

public interface OtherMapper {

	/**
	 * 保存的是数据库中已存在的批号等
	 * @param seList
	 */
	public void saveOther(@Param("seList") List<StrElongation> seList);  
	
	public List<Other> getOther(@Param("date") String date);
	
	public List<Other> getOtherAll(@Param("date") String date);
	
	public void updateOther(Other other);
	
	/**
	 * 保存的是自己添加的数据
	 * @param seList
	 */
	public void saveOtherAll(@Param("otList") List<Other> otList);
}
