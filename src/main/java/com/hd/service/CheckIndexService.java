package com.hd.service;

import java.util.List;

import com.hd.pojo.CheckIndex;

public interface CheckIndexService {
	/**
	 * 通过批号和检测指标查询是否含有该条记录
	 * @param batchNo	批号
	 * @param checkIndex	检测指标
	 * @return
	 */
	int findCheckIndexCountByBatchNo(String batchNo, String checkIndex);

	/**
	 * 将指标对象存入数据库
	 * @param ci	指标对象
	 */
	void saveCheckIndex(CheckIndex ci);
	
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
	CheckIndex findCheckIndexByBatchNoAndIndexName(String batchNo, String indexName);

	/**
	 * 查询所有数据
	 * @param indexName 
	 * @param batchNo 
	 * @return
	 */
	List<CheckIndex> findCheckIndex(String batchNo, String indexName);

	/**
	 * 修改数据
	 * @param ci
	 */
	void updateCheckIndex(CheckIndex ci);

	/**
	 * 通过批号查询数据
	 * @param batchNo
	 * @return
	 */
	List<CheckIndex> findCheckIndexByBatchNo(String batchNo);
	
}
