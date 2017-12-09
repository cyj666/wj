package com.hd.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController{
	
	/**
	 * 跳转到主页
	 * @return
	 */
	@RequestMapping("home")
	public String toIndex() {
		return "/home/fmain";
	}
	
	/**
	 * 页面转向作用
	 * @return 跳转到含油率页面
	 */
	@RequestMapping("OC")
	public String toOc() {
		return "OC";
	}
	/**
	 * 页面跳转到强度和伸长率页面
	 * @return
	 */
	@RequestMapping("SE")
	public String toSE() {
		return "SE";
	}
	
	/**
	 * 跳转到测量卷曲度和卷曲稳定度
	 * @return
	 */
	@RequestMapping("PC")
	public String toCB() {
		return "PC";
	}
	
	@RequestMapping("BW")
	public String toBW() {
		return "BW";
	}
	
	@RequestMapping("others")
	public String toOthers() {
		return "others";
	}
	
	@RequestMapping("title")
	public String toTitle() {
		return "/home/title";
	}
	
	@RequestMapping("left")
	public String toLeft() {
		return "/home/left";
	}
	
	@RequestMapping("main")
	public String toMain() {
		return "/home/main";
	}
	
	/**
	 * 转到处理数据主页面
	 * @return	页面的路径名
	 */
	@RequestMapping("dealMain")
	public String todealMain() {
		return "/home/dealMain";
	}
	
	/**
	 * 转到查询的主页面
	 * @return 页面的路径名
	 */
	@RequestMapping("searchMain")
	public String toSearchMain() {
		return "/home/searchMain";
	}
	
	/**
	 * 转到基础数据页面
	 * @return
	 */
	@RequestMapping("/checkMain")
	public String toCheckMain() {
		return "/home/checkMain";
	}
	
	/**
	 * 转到添加指标页面
	 * @return 页面的路径名
	 */
	@RequestMapping("addIndex")
	public String toAddIndex() {
		return "/checkIndex/addIndex";
	}
	
	@RequestMapping("od")
	public String toOD() {
		return "/select/oneDay";
	}
	
	@RequestMapping("dr")
	public String toDr() {
		return "/select/dateRange";
	}
	@RequestMapping("sm")
	public String toSelectByMonth() {
		return "/select/selectByMonth";
	}
	
	@RequestMapping("/ca")
	public String checkAgain() {
		return "select/checkAgain";
	}
	
	/**
	 * 查询指标数据
	 * @return
	 */
	@RequestMapping("/is")
	public String toSelectCheckIndex() {
		return "select/allIndex";
	}
	
	@RequestMapping("testAgain")
	public String toTestAgain() {
		return "/test/testAgain";
	}
	
}
