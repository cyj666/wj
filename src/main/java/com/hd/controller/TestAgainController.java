package com.hd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd.pojo.BoilingWater;
import com.hd.pojo.OliContent;
import com.hd.pojo.PercentCrimp;
import com.hd.pojo.StrElongation;
import com.hd.service.BwService;
import com.hd.service.OcService;
import com.hd.service.PcService;
import com.hd.service.SeService;

import net.sf.json.JSONArray;

@Controller
public class TestAgainController {
	
	@Autowired
	private OcService ocService;

	@Autowired
	private SeService seService;
	
	@Autowired
	private PcService pcService;
	
	@Autowired
	private BwService bwService;
	
	//强伸
	private List<StrElongation> seList = new ArrayList<StrElongation>();
	//含油率
	private List<OliContent> ocList = new ArrayList<OliContent>();
	//卷曲
	private List<PercentCrimp> pcList = new ArrayList<PercentCrimp>();
	//沸水
	private List<BoilingWater> bwList = new ArrayList<BoilingWater>();
	
	
	@RequestMapping("testAgainSearch")
	public String searchTestAgain(Model model, 
			@RequestParam("createDate")String createDate, 
			@RequestParam("testType")String testType) {
		
		if ("p_se".equals(testType)) {
			//查询含有复测数据的集合
			seList = seService.findSomeByCreateDate(createDate);
			model.addAttribute("testType", 1);
			model.addAttribute("seList", seList);
			
			if (seList.size() > 0) {
				model.addAttribute("seSize", seList.size()-1);
			}
		} else if ("p_oc".equals(testType)) {
			ocList = ocService.findSomeByCreateDate(createDate);
			model.addAttribute("testType", 0);
			model.addAttribute("ocList", ocList);
			
			if (ocList.size() > 0) {
				model.addAttribute("ocSize", ocList.size()-1);
			}
		} else if("p_cc".equals(testType)) {
			//查询含有复测数据的集合
			pcList = pcService.findSomeByCreateDate(createDate);
			model.addAttribute("testType", 2);
			model.addAttribute("pcList", pcList);

			if (pcList.size() > 0) {
				model.addAttribute("pcSize", pcList.size()-1);
			}
		} else if ("p_bw".equals(testType)) {
			//查询含有复测数据的集合
			bwList = bwService.findSomeByCreateDate(createDate);
			for (BoilingWater bw : bwList) {
				System.out.println(bw);
			}
			model.addAttribute("testType", 3);
			model.addAttribute("bwList", bwList);
			
			if (bwList.size() > 0) {
				model.addAttribute("bwSize", bwList.size()-1);
			}
		}
		return "forward:testAgain";
	}
	
	@RequestMapping("deleteData")
	@ResponseBody
	public Map<String, String> deleteData(@RequestParam String indexList,@RequestParam Integer testValue) {
		JSONArray jsonArr = JSONArray.fromObject(indexList);
		//存放下标
		List<Integer> list = new ArrayList<Integer>();
		Map<String, String> data = new HashMap<String, String>(16);
		
		for (Object obj : jsonArr) {
			list.add(Integer.parseInt(obj.toString()));
		}
		//判断应该处理什么数据
		if (testValue == 0) {
			//存放要删除的元素
			List<OliContent> ocDelList = new ArrayList<OliContent>();
			for (int index : list) {
				ocDelList.add(ocList.get(index));
			}
			
			Iterator<OliContent> it = ocList.iterator();
			int i = 0;
			while (it.hasNext()) {
				it.next();
				if (list.contains(i)) {
					it.remove();
				}
				i++;
			}
			for (OliContent oc : ocDelList) {
				ocService.deleteOliContent(oc.getBatchNo(), oc.getLineNo(), oc.getCreateDate());
			}
			ocService.saveDeleteOliContent(ocDelList);

			for (OliContent oc : ocList) {
				ocService.updateOliContent(oc.getBatchNo(), oc.getLineNo(), oc.getCreateDate());
			}
			data.put("msg", "删除成功");
			return data;
		} else if (testValue == 1) {
			//存放要删除的元素
			List<StrElongation> seDelList = new ArrayList<StrElongation>();
			for (int index : list) {
				seDelList.add(seList.get(index));
			}
			
			Iterator<StrElongation> it = seList.iterator();
			int i = 0;
			while (it.hasNext()) {
				it.next();
				if (list.contains(i)) {
					it.remove();
				}
				i++;
			}
			for (StrElongation se : seDelList) {
				seService.deleteStrElongation(se.getBatchNo(), se.getLineNo(), se.getCreateDate());
			}
			seService.saveDeleteStrElongation(seDelList);
			for (StrElongation se : seList) {
				seService.updateStrElongation(se.getBatchNo(), se.getLineNo(), se.getCreateDate());
			}
			data.put("msg", "删除成功");
			return data;
		} else if (testValue == 2) {
			//存放要删除的元素
			List<PercentCrimp> pcDelList = new ArrayList<PercentCrimp>();
			for (int index : list) {
				pcDelList.add(pcList.get(index));
			}
			
			Iterator<PercentCrimp> it = pcList.iterator();
			int i = 0;
			while (it.hasNext()) {
				it.next();
				if (list.contains(i)) {
					it.remove();
				}
				i++;
			}
			
			for (PercentCrimp pc : pcDelList) {
				pcService.deletePercentCrimp(pc.getBatchNo(), pc.getLineNo(), pc.getCreateDate());
			}
			pcService.saveDeletePercentCrimp(pcDelList);
			for (PercentCrimp pc : pcList) {
				pcService.updatePercentCrimp(pc.getBatchNo(), pc.getLineNo(), pc.getCreateDate());
			}
			
			data.put("msg", "删除成功");
			return data;
		} else if (testValue == 3) {
			//存放要删除的元素
			List<BoilingWater> bwDelList = new ArrayList<BoilingWater>();
			for (int index : list) {
				bwDelList.add(bwList.get(index));
			}
			
			Iterator<BoilingWater> it = bwList.iterator();
			int i = 0;
			while (it.hasNext()) {
				it.next();
				if (list.contains(i)) {
					it.remove();
				}
				i++;
			}
			
			for (BoilingWater bw : bwDelList) {
				bwService.deleteBoilingWater(bw.getBatchNo(), bw.getLineNo(), bw.getCreateDate());
			}
			bwService.saveDeleteBoilingWater(bwDelList);
			for (BoilingWater bw : bwList) {
				bwService.updateBoilingWater(bw.getBatchNo(), bw.getLineNo(), bw.getCreateDate());
			}
			
			data.put("msg", "删除成功");
			return data;
		}
		data.put("msg", "删除失败");
		return data;
	}
}
