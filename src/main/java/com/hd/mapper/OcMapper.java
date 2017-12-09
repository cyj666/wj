package com.hd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pojo.OliContent;


/**
 * 含油率mapper
 * @author wzhd
 *
 */
public interface OcMapper {
	//将含油数据存入数据库
	public void saveOc(@Param("ocList")List<OliContent> ocList);

	public int findOc(@Param("batchNo")String batchNo,@Param("createDate")String createDate);

	public List<OliContent> findSomeByCreateDate(String createDate);

	public void deleteOliContent(@Param("batchNo")String batchNo, 
			@Param("lineNo")String lineNo, @Param("createDate")String createDate);

	public void saveDeleteOliContent(@Param("ocDelList")List<OliContent> ocDelList);

	public void updateOliContent(@Param("batchNo")String batchNo, 
			@Param("lineNo")String lineNo, @Param("createDate")String createDate);
}
