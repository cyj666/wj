package com.hd.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd.pojo.CheckIndex;
import com.hd.service.CheckIndexService;

@Controller
public class CheckIndexController {
	
	@Autowired
	private CheckIndexService checkIndexService;
	
	private Map<String, Integer> data = new HashMap<String, Integer>(16);
	
	/**
	 * 添加指标数据
	 * @param indexValueList 指标数据的集合
	 * @return
	 */
	@RequestMapping("addIndexValue")
	@ResponseBody
	public Map<String, Integer> addIndexValue(@RequestParam String indexValueList) {
		String[] indexArr = indexValueList.split(",");
		String batchNo = indexArr[0];
		batchNo = batchNo.substring(2, batchNo.lastIndexOf("\""));
		String checkIndex = indexArr[3];
		checkIndex = checkIndex.substring(1, checkIndex.lastIndexOf("\""));
		String standardValue = indexArr[1];
		String deviationValue = indexArr[2];
		if (checkIndexService.findCheckIndexCountByBatchNo(batchNo, checkIndex) != 0) {
			data.put("addStatus", 0);
			return data;
		} else {
			CheckIndex ci = new CheckIndex();
			ci.setBatchNo(batchNo);
			ci.setStandardValue(Double.parseDouble(standardValue.substring(1, standardValue.lastIndexOf("\""))));
			ci.setDeviationValue(Double.parseDouble(deviationValue.substring(1, deviationValue.lastIndexOf("\""))));
			ci.setCheckIndex(checkIndex);
			checkIndexService.saveCheckIndex(ci);
			data.put("addStatus", 1);
			return data;
		}
	}
	
	/**
	 * 查询所有批号
	 * @return	所有批号名的集合
	 */
	@RequestMapping("findBatchNo")
	@ResponseBody
	public List<String> findBatchNo() {
		List<String> batchNoList = checkIndexService.findBatchNo();
		return batchNoList;
	}
	
	/**
	 * 通过批号和指标名查询数据，并将数据返回给页面
	 * @param batchNo
	 * @param indexName
	 * @return
	 */
	@RequestMapping("findCheckIndex")
	@ResponseBody
	public CheckIndex findCheckIndexByBatchNoAndIndexName(@RequestParam String batchNo, 
			@RequestParam String indexName) {
		CheckIndex checkIndex = checkIndexService.findCheckIndexByBatchNoAndIndexName(batchNo, indexName);
		return checkIndex;
	}
	
	/**
	 * 查询所有指标数据
	 * @param model
	 * @return
	 */
	@RequestMapping("selectCheckIndex")
	public String findCheckIndex(@RequestParam("batchNo") String batchNo,
			@RequestParam("indexName") String indexName, Model model) {
		batchNo = batchNo.toUpperCase() + "%" ;
		//查询所有指标
		if ("all".equals(indexName)) {
			indexName = "%";
		}
		List<CheckIndex> checkIndexList = checkIndexService.findCheckIndex(batchNo, indexName);
		model.addAttribute("checkIndexList", checkIndexList);
		
		return "/select/allIndex";
	}
	
	@RequestMapping("changeIndexData")
	@ResponseBody
	public Map<String, Integer> changeIndexData(@RequestParam String dataList) {
		String[] indexArr = dataList.split(",");
		String batchNo = indexArr[0];
		batchNo = batchNo.substring(2, batchNo.lastIndexOf("\""));
		String checkIndex = indexArr[3];
		checkIndex = checkIndex.substring(1, checkIndex.lastIndexOf("\""));
		String standardValue = indexArr[1];
		String deviationValue = indexArr[2];
		
		CheckIndex ci = new CheckIndex();
		ci.setBatchNo(batchNo);
		ci.setStandardValue(Double.parseDouble(standardValue.substring(1, standardValue.lastIndexOf("\""))));
		ci.setDeviationValue(Double.parseDouble(deviationValue.substring(1, deviationValue.lastIndexOf("\""))));
		ci.setCheckIndex(checkIndex);
		
		System.out.println(ci);
		checkIndexService.updateCheckIndex(ci);
		return data;
	}
}
