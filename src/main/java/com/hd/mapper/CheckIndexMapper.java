package com.hd.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.pojo.CheckIndex;

public interface CheckIndexMapper {
	
	/**
	 * 通过批号和检测指标查询是否含有该条记录
	 * @param batchNo	批号
	 * @param checkIndex	检测指标
	 * @return
	 */
	int findCheckIndexCountByBatchNo(@Param("batchNo") String batchNo, @Param("checkIndex") String checkIndex);

	/**
	 * 将指标对象存入数据库
	 * @param ci	指标对象
	 */
	void saveCheckIndex(@Param("ci") CheckIndex ci);
	
	/**
	 * 查询所有批号
	 * @return	含有所有批号的集合
	 */
	List<String> findBatchNo();

	/**
	 * 通过批号和指标名查询
	 * @param batchNo 批号
	 * @param indexName 指标名
	 * @return
	 */
	CheckIndex findCheckIndexByBatchNoAndIndexName(@Param("batchNo") String batchNo, 
			@Param("indexName") String indexName);

	/**
	 * 查询所有数据
	 * @param indexName 
	 * @param batchNo 
	 * @return
	 */
	List<CheckIndex> findCheckIndex(@Param("batchNo") String batchNo, 
			@Param("indexName") String indexName);

	/**
	 * 修改数据
	 * @param ci
	 */
	void updateCheckIndex(@Param("ci") CheckIndex ci);

	/**
	 * 通过批号查询shuju
	 * @param batchNo
	 * @return
	 */
	List<CheckIndex> findCheckIndexByBatchNo(@Param("batchNo") String batchNo);

}
