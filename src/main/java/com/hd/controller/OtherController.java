package com.hd.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hd.pojo.Other;
import com.hd.service.OtService;

import net.sf.json.JSONObject;

@Controller
public class OtherController {

	@Autowired
	private OtService otService;
	
	private List<Other> list;
	
	@RequestMapping(value="/readData",method=RequestMethod.GET)
	public String readData(Model model,@RequestParam(value="date",required=true)String date) {		
		list = otService.getOther(date);
		model.addAttribute("list",list);		
		return "others";
	}
	
	
	@RequestMapping(value="/checkData",method=RequestMethod.GET)
	public String checkData(Model model,@RequestParam(value="date",required=true)String date) {		
		list = otService.getOtherAll(date);
		model.addAttribute("list",list);		
		return "others";
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/OtherUpData",method=RequestMethod.POST)
	public Map<String, String> upDate(@RequestBody String map) {
		JSONObject jsonObject = JSONObject.fromObject(map);
		Map<String, String> data = new HashMap<String,String>();
		List<Other> List1 = new ArrayList<Other>();
		List<Other> otList1 = new ArrayList<Other>();
		//注释部分为手动添加的数据，因页面暂时隐藏该功能，所以暂时注释，待到以后再说
		/*List<Other> List2 = new ArrayList<Other>();
		List<Other> otList2 = new ArrayList<Other>();*/
		List1 =  (List<Other>) jsonObject.get("1");
		//List2 =  (List<Other>) jsonObject.get("2");
		 for (Object obj : List1) {
			   Other ot = new Other();	  
				ot = com.alibaba.fastjson.JSONObject.parseObject(obj.toString(),Other.class);
				//System.out.println(ot);
				if (ot.getOtStatus() >= 11 && ot.getOtStatus() <= 19) {   //表示为复测数据，选择跳过不保存
					continue;
				}else {
					otList1.add(ot);
				}
				
			}
		 /*for (Object obj : List2) {     //表示手动添加的数据保存，不过暂时不用
			   Other ot = new Other();	  
				ot = com.alibaba.fastjson.JSONObject.parseObject(obj.toString(),Other.class);				
				otList2.add(ot);
			}*/
		 
		otService.updateOther(otList1);
		/*if (otList2!=null&&otList2.size()!=0) {
			otService.saveOtherAll(otList2);
		}	*/	
		data.put("msg", "成功");
		return data;
	}
}
