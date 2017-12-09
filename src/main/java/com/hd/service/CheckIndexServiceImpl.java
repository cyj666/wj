package com.hd.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hd.mapper.CheckIndexMapper;
import com.hd.pojo.CheckIndex;

@Service
public class CheckIndexServiceImpl implements CheckIndexService {

	@Autowired
	private CheckIndexMapper checkIndexMapper;
	
	/**
	 * 通过批号和检测指标查询是否含有该条记录
	 * @param batchNo	批号
	 * @param checkIndex	检测指标
	 * @return
	 */
	@Override
	public int findCheckIndexCountByBatchNo(String batchNo, String checkIndex) {
		int count = checkIndexMapper.findCheckIndexCountByBatchNo(batchNo, checkIndex);
		return count;
	}

	/**
	 * 将指标对象存入数据库
	 * @param ci	指标对象
	 */
	@Override
	public void saveCheckIndex(CheckIndex ci) {
		checkIndexMapper.saveCheckIndex(ci);
	}
	
	/**
	 * 查询所有批号
	 * @return	含有所有批号的集合
	 */
	@Override
	public List<String> findBatchNo() {
		return checkIndexMapper.findBatchNo();
	}

	/**
	 * 通过批号和指标名查询
	 * @param batchNo 批号
	 * @param indexName 指标名
	 * @return
	 */
	@Override
	public CheckIndex findCheckIndexByBatchNoAndIndexName(String batchNo, String indexName) {
		return checkIndexMapper.findCheckIndexByBatchNoAndIndexName(batchNo, indexName);
	}

	@Override
	public List<CheckIndex> findCheckIndex(String batchNo, String indexName) {
		return checkIndexMapper.findCheckIndex(batchNo, indexName);
	}

	@Override
	public void updateCheckIndex(CheckIndex ci) {
		checkIndexMapper.updateCheckIndex(ci);
	}

	@Override
	public List<CheckIndex> findCheckIndexByBatchNo(String batchNo) {
		return checkIndexMapper.findCheckIndexByBatchNo(batchNo);
	}

}
