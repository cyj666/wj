package com.hd.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hd.pojo.CheckIndex;
import com.hd.pojo.NyLon;
import com.hd.pojo.NyLonSon;
import com.hd.service.CheckIndexService;
import com.hd.service.SelectService;
import com.hd.tool.DownloadUtils;
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
			@RequestParam("start") String start, @RequestParam("end") String end, 
			HttpServletRequest request, Model model) {
		
		List<CheckIndex> ciList = checkIndexService.findCheckIndexByBatchNo(batchNo);
		List<NyLonSon> nlList = selectService.findBatchNoDataByStartAndEnd(batchNo, start, end);
		
		HttpSession session = request.getSession();
 		session.setAttribute("nlList", nlList);
 		session.setAttribute("start", start);
 		session.setAttribute("end", end);
 		
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
	
	
	@RequestMapping("/download")
	@SuppressWarnings("unchecked")
	public void  download(HttpServletRequest request, HttpServletResponse response) {	
		HttpSession session = request.getSession();
		
		List<NyLonSon> nlList = (List<NyLonSon>)session.getAttribute("nlList");
		String start = (String)session.getAttribute("start");
		String end = (String)session.getAttribute("end");
		
		//生成图片
		String filename = ResultExcel(nlList, start, end);

		String filePath = filename + ".xls";
		DownloadUtils.download(response, filePath);
	}
	
	
	public static String ResultExcel(List<NyLonSon> nlList,String start,String end) {
		String filename = "E:\\project\\Excel\\" + nlList.get(0).getBatchNo()+"("+start+"To"+end+")"; //生成excel文件名	
		//8张图中折线图的名称
		String[] rowKeyLinearDensity = {"线密度dtex"}; 
		String[] rowKeyStrength = {"强度CN/dtex"};
		String[] rowKeyElongation = {"伸长率%"};
		String[] rowKeyCrimp = {"卷曲收缩率%","卷曲稳定度%"};
		String[] rowKeyYarnlevelness = {"条干"};
		String[] rowKeyBoilingWater= {"沸水"};
		String[] rowKeyOliContent = {"含油率"}; 
		String[] rowKeyGridLine = {"网络度"}; 
				
	    String[] colKeys =ExcelAndChartUtils.autoX1(nlList);   //自动生成X轴名称
	    
	    
	    //String[] nylonItem = {"oliContent"};
	    String[] nylonItemLinearDensity = {"linearDensity"};
	    String[] nylonItemStrength = {"strength"};
	    String[] nylonItemElongation = {"elongation"};
	    String[] nylonItemCrimp = {"crimpContraction","crimpStability"};
	    String[] nylonItemYarnlevelness = {"yarnlevelness"};
	    String[] nylonItemBoilingWater = {"boilingWater"};
	    String[] nylonItemOliContent = {"oliContent"};
	    String[] nylonItemGridLine = {"gridLine"};
	   
	    
	    
	   // String chartTitle = "测试用折线图"; 图片名称
	    String X = "线位号/日期";
        //String Y = "含油";
        
        //自动生成Y轴
        double[] autoYLinearDensity = ExcelAndChartUtils.autoY1(nlList, "linearDensity");
        double[] autoYStrength = ExcelAndChartUtils.autoY1(nlList, "strength");
        double[] autoYElongation = ExcelAndChartUtils.autoY1(nlList, "elongation");
        double[] autoYCrimp = ExcelAndChartUtils.autoY1(nlList, "crimpContraction"); //
        double[] autoYYarnlevelness = ExcelAndChartUtils.autoY1(nlList, "yarnlevelness");
        double[] autoYBoilingWater = ExcelAndChartUtils.autoY1(nlList, "boilingWater");
        double[] autoYOliContent = ExcelAndChartUtils.autoY1(nlList, "oliContent");
        double[] autoYGridLine = ExcelAndChartUtils.autoY1(nlList, "gridLine");
      
        
        
        
        String linearDensity = ExcelAndChartUtils.createChart(ExcelAndChartUtils.createDataset1(nlList, 
				rowKeyLinearDensity, colKeys, nylonItemLinearDensity),"线密度dtex折线图", X, "线密度dtex", (int)autoYLinearDensity[1], (int)autoYLinearDensity[0], (int)autoYLinearDensity[2]);
        String strength = ExcelAndChartUtils.createChart(ExcelAndChartUtils.createDataset1(nlList, 
        		rowKeyStrength, colKeys, nylonItemStrength),"强度CN/dtex折线图", X, "强度CN/dtex", (int)autoYStrength[1], (int)autoYStrength[0], (int)autoYStrength[2]);
        String elongation = ExcelAndChartUtils.createChart(ExcelAndChartUtils.createDataset1(nlList, 
        		rowKeyElongation, colKeys, nylonItemElongation),"伸长率%折线图", X, "伸长率%", (int)autoYElongation[1], (int)autoYElongation[0], (int)autoYElongation[2]);
        String crimp = ExcelAndChartUtils.createChart(ExcelAndChartUtils.createDataset1(nlList, 
        		rowKeyCrimp, colKeys, nylonItemCrimp),"卷曲折线图", X, "卷曲", (int)autoYCrimp[1], (int)autoYCrimp[0], (int)autoYCrimp[2]);
        String yarnlevelness = ExcelAndChartUtils.createChart(ExcelAndChartUtils.createDataset1(nlList, 
        		rowKeyYarnlevelness, colKeys, nylonItemYarnlevelness),"条干折线图", X, "条干", (int)autoYYarnlevelness[1], (int)autoYYarnlevelness[0], (int)autoYYarnlevelness[2]);
        String boilingWater = ExcelAndChartUtils.createChart(ExcelAndChartUtils.createDataset1(nlList, 
        		rowKeyBoilingWater, colKeys, nylonItemBoilingWater),"沸水折线图", X, "沸水", (int)autoYBoilingWater[1], (int)autoYBoilingWater[0], (int)autoYBoilingWater[2]);
        String oliContent = ExcelAndChartUtils.createChart(ExcelAndChartUtils.createDataset1(nlList, 
        		rowKeyOliContent, colKeys, nylonItemOliContent),"含油率折线图", X, "含油率", (int)autoYOliContent[1], (int)autoYOliContent[0], (int)autoYOliContent[2]);
        String gridLine = ExcelAndChartUtils.createChart(ExcelAndChartUtils.createDataset1(nlList, 
        		rowKeyGridLine, colKeys, nylonItemGridLine),"网络度折线图", X, "网络度", (int)autoYGridLine[1], (int)autoYGridLine[0], (int)autoYGridLine[2]);
		
		
		
		String[] PicName = {linearDensity,strength,elongation,crimp,yarnlevelness,boilingWater,oliContent,gridLine};
 		ExcelAndChartUtils.creatExcel1(nlList, filename, PicName);
 		
 		return filename;
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
