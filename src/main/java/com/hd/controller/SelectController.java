package com.hd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hd.pojo.CheckIndex;
import com.hd.pojo.NyLon;
import com.hd.pojo.NyLonSon;
import com.hd.service.CheckIndexService;
import com.hd.service.SelectService;
import com.hd.tool.ExcelAndChartUtils;

@Controller
public class SelectController extends BaseController{
	
	@Autowired
	private SelectService selectService;
	
	@Autowired
	private CheckIndexService checkIndexService;
	
	@RequestMapping("/selectData")
	public String selectOneDayData(@RequestParam("date") String date,Model model) {
		List<NyLon> nlList = selectService.findByDay(date);
		date = date.substring(date.indexOf("-")+1);
		
		model.addAttribute("nlList", nlList);
		model.addAttribute("date", date);
		return "select/oneDay";
	}
	
	@RequestMapping("/selectDataRange")
	public String selectDataRange(@RequestParam("date1") String date1,@RequestParam("date2") String date2,Model model) {
		List<NyLon> nlList = selectService.findByDateRange(date1, date2);
		date1 = date1.substring(date1.indexOf("-")+1);
		date2 = date2.substring(date2.indexOf("-")+1);
		model.addAttribute("nlList", nlList);
		
		model.addAttribute("date1", date1);
		model.addAttribute("date2", date2);
		return "select/dateRange";
	}
	
	@RequestMapping("/selectByMonth")
	public String selectByMonth(@RequestParam("startDate") String start, 
			@RequestParam("endDate") String end, Model model, HttpServletRequest request) {
		//String _batchNo = bn.toUpperCase() + "%";
		//String month = date.substring(0,date.lastIndexOf("-"));
		
		List<String> bnList = selectService.findByStartAndEnd(start, end);
		//设置session
		HttpSession session = request.getSession();
		session.setAttribute("_bnList", bnList);
		
		model.addAttribute("bnList", bnList);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		
		return "select/selectByMonth";
	}
	
	@RequestMapping("/getBatchNoData")
	public String getBatchNoData(@RequestParam("batchNo") String batchNo,
			@RequestParam("start") String start, @RequestParam("end") String end, Model model) {
		
		List<CheckIndex> ciList = checkIndexService.findCheckIndexByBatchNo(batchNo);
		List<NyLonSon> nlList = selectService.findBatchNoDataByStartAndEnd(batchNo, start, end);
		
		//生成图片
		/*String filename = nlList.get(0).getBatchNo()+"("+start+"至"+end+")";
		String[] rowKeys = {"含油率"}; 
	    String[] colKeys =ExcelAndChartUtils.autoX1(nlList);
	    String[] nylonItem = {"oliContent"};
	    String chartTitle = "测试用折线图"; 
	    String X = "线位号/日期";
        String Y = "含油";
        double[] autoY = ExcelAndChartUtils.autoY1(nlList, "oliContent");
        int Ystart = (int)autoY[1];
        int Yend = (int)autoY[0];
        int space = (int)autoY[2];
		String pic1 = ExcelAndChartUtils.createChart(ExcelAndChartUtils.createDataset1(nlList, rowKeys, colKeys, nylonItem),
				chartTitle, X, Y, Ystart, Yend, space);
		String[] PicName = {pic1};
 		ExcelAndChartUtils.creatExcel1(nlList, filename, PicName);*/
		
		String filename = nlList.get(0).getBatchNo()+"("+start+"至"+end+")"; //生成excel文件名	
		//8张图的名称
		String[] rowKeyLinearDensity = {"线密度dtex"}; 
		String[] rowKeyStrength = {"强度CN/dtex"};
		String[] rowKeyElongation = {"伸长率%"};
		String[] rowKeyCrimp = {"卷曲收缩率%","卷曲稳定度%"};
		String[] rowKeyYarnlevelness = {"条干"};
		String[] rowKeyBoilingWater= {"沸水"};
		String[] rowKeyOliContent = {"含油率"}; 
		String[] rowKeyGridLine = {"网络度"}; 
				
	    String[] colKeys =ExcelAndChartUtils.autoX1(nlList);   //自动生成X轴名称
	    
	    String[] nylonItem = {"oliContent"};
	    String[] nylonItemLinearDensity = {"linearDensity"};
	    
	    
	    String chartTitle = "测试用折线图"; 
	    String X = "线位号/日期";
        String Y = "含油";
        double[] autoY = ExcelAndChartUtils.autoY1(nlList, "oliContent");
        int Ystart = (int)autoY[1];
        int Yend = (int)autoY[0];
        int space = (int)autoY[2];
        String linearDensity = ExcelAndChartUtils.createChart(ExcelAndChartUtils.createDataset1(nlList, 
				rowKeyLinearDensity, colKeys, nylonItemLinearDensity),"线密度折线图", X, "线密度", Ystart, Yend, space);
		String oliContent = ExcelAndChartUtils.createChart(ExcelAndChartUtils.createDataset1(nlList, 
				rowKeyOliContent, colKeys, nylonItem),"含油率折线图", X, "含油率", Ystart, Yend, space);
		String[] PicName = {oliContent,linearDensity};
 		ExcelAndChartUtils.creatExcel1(nlList, filename, PicName);
 		
 		
 		
 		
		String format = nlList.get(0).getBatchNo().substring(3,8);
		if (format.matches("^0[0-9]{4}$")) {
			format = format.substring(1,3) + "/" + format.substring(3);
		} else {
			format = format.substring(0,3) + "/" + format.substring(3);
		}
		
		if (ciList.size() == 0) {
			model.addAttribute("msg", 1);
			model.addAttribute("nlList", nlList);
		} else {
			model.addAttribute("ciSize", ciList.size()-1);
			model.addAttribute("format", format);
			model.addAttribute("nlList", nlList);
			model.addAttribute("ciList", ciList);
		}
		
		return "select/batchNoData";
	}
	
	@RequestMapping("/getCheckAgainData")
	public String checkAgain(@RequestParam("date") String date, @RequestParam("bn") String bn,
			Model model) {
		String prefix = bn.substring(0,1);
		List<NyLon> nlList = null;
		if ("H".equals(prefix)) {
			nlList = selectService.findCheckAgainNyLonByDate(date,prefix);
		} else if ("F".equals(prefix)) {
			nlList = selectService.findCheckAgainNyLonByDate(date,prefix);
		} else if ("P".equals(prefix)) {
			nlList = selectService.findCheckAgainNyLonByDate(date, prefix);
		} else if ("D".equals(prefix)) {
			String firstPrefix = "P";
			String secondPrefix = "F";
			String thirdPrefix = "H";
			nlList = selectService.findCheckAgainNyLonByDateAndPrefix(date, firstPrefix, secondPrefix, thirdPrefix);
		}
		
		model.addAttribute("nlList", nlList);
		return "select/checkAgain";
	}
}
