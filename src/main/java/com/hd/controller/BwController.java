package com.hd.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hd.pojo.BoilingWater;
import com.hd.pojo.Page;
import com.hd.service.BwService;
import com.hd.service.PageService;
import com.hd.tool.PathUtil;

@Controller
public class BwController {
	private static final Logger log = LoggerFactory.getLogger(BwController.class);
	
	@Autowired
	private BwService bwService;
	
	@Autowired
	private PageService pageService;
	
	//将数据进度信息存储到map中
	private Map<String, Long> dataMap = new HashMap<String, Long>();
	
	//将要返回给页面的信息存到data中
	private Map<String, String> data = new HashMap<String, String>();
	
	//定义一个集合用来存储伸长率和强度这个对象数据
	private List<BoilingWater> bwList;
	//定义一个map集合用来存放有错误的文件名
	private Map<String, String> fileNameMap;
	
	//定义一个存放文件的路径
	private String currentPath;
	
	/**
	 * 将刚刚上传的文件经过处理返回到页面
	 * @return
	 */
	@RequestMapping(value="/bwRead",method=RequestMethod.GET)
	@SuppressWarnings("all")
	public String bwRead(Model model, @RequestParam(value="pages",required=false) Integer pages,HttpServletRequest request) {
		//判断page是否等于null，如果等于null则说明没有读取数据，不为null则需要读取数据
		if(pages == null) {
			request.getSession().setAttribute("saveStatus", 0);
			//获取刚刚文件上传的路径
			String path = PathUtil.getPath("bwPath");
			
			//读取文件
			Map<Integer, Object> bwMap = bwService.readBw(path);
			
			model.addAttribute("status", "show");
			if (bwMap != null) {
				bwList = (List<BoilingWater>)bwMap.get(1);
				fileNameMap = (Map<String, String>) bwMap.get(2);
			} else {
				model.addAttribute("msg", 2);
				return "BW";
			}
			
			
		}
		
		if (fileNameMap.isEmpty()) {
			//判断是否有读取的数据
			if(bwList==null) {
				model.addAttribute("msg", 1);
				return "BW";
			}else if(bwList.size()==0) {
				model.addAttribute("msg", 2);		//如果上传失败的话，文件夹中没有数据，在读的时候就提示没有文件可以读
				return "BW";
			}else {
				model.addAttribute("msg", 3);
			}
			//分页
			Page page = pageService.getpage(bwList);
			
			Integer begin = null;
			Integer end = null;
			int totalpages = page.getTotalPage();
			int totalcount = page.getTotalCount();
			if (pages==null||pages<=0) {
				pages = 1;
			}
			if (pages>totalpages) {
					pages = totalpages;
			}		
			if (pages==totalpages) {
				int last = totalcount-(totalpages-1)*page.getPageSize();
				begin = (pages-1)*page.getPageSize();
				end = (pages-1)*page.getPageSize()+last-1;
			}else {
				begin = (pages-1)*page.getPageSize();
				end = (pages-1)*page.getPageSize()+page.getPageSize()-1;
			}
			
			model.addAttribute("bwList", bwList);
			model.addAttribute("page", page);
			model.addAttribute("begin", begin);
			model.addAttribute("end", end);
			model.addAttribute("pages", pages);
			
			return "BW";
		} else {
			model.addAttribute("fileNameMap", fileNameMap);
			return "error/otherError";
		}
	}
	
	/**
	 * 文件上传
	 * @param file
	 * @return 如果成功就跳转到成功页面，如果失败就跳转到失败页面
	 */
	@RequestMapping("/bw/bwUpFile")
	@ResponseBody
	public Map<String, String> upload(@RequestParam("file") MultipartFile[] files, 
			@RequestParam("filelSavePath") String path) {
		//设置文件存放的完整路径
		path = path + PathUtil.getTime();
		currentPath = path;
		
		//将路径写入到文件中
		PathUtil.savePath(path,"bwPath");
		
		File file1 = new File(path);
		if(!file1.exists()) {
			file1.mkdirs();
		}

		//将文件上传
		for (MultipartFile mf : files) {
			String fileName = mf.getOriginalFilename();
			
			file1 = new File(path + "/" + fileName);
			try {
				if(!file1.exists()) {
					file1.createNewFile();
				}
				mf.transferTo(file1);
			}catch(Exception e) {
				log.error("文件上传失败");
				e.printStackTrace();
				data.put("msg", "文件上传失败");
				return data;
			}
		}
		
		data.put("msg", "文件上传成功");
		return data;
	}
	
	/**
	 * 获取进度条信息的
	 * @return
	 */
	@RequestMapping("/bw/getPercent")
	@ResponseBody
	public Map<String, Long> getPercent() {
//		System.out.println(allPath);
		Long length = 0L;
		File file = new File(currentPath);
		if(!file.isDirectory()) {
			return null;
		}
		String[] fileNames = file.list();
		for (String fn : fileNames) {
			file = new File(currentPath + fn);
			length += file.length();
		}
		dataMap.put("bytesRead", length);
		dataMap.put("percent", 100L);
		return dataMap;
	}
	
	/**
	 * 保存数据到数据库
	 * @param request
	 * @return
	 */
	@RequestMapping(value="bwSaveData",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> bwSave(HttpServletRequest request) {
		//判断集合中有没有数据
		if(bwList == null || bwList.size() == 0) {
			data.put("msg", "没有数据");
			return data;
		}
		
		
		BoilingWater bw = bwList.get(0);
		String batchNo = bw.getBatchNo();
		String lineNo = bw.getLineNo();
		String createDate = bw.getCreateDate();
		
		if (bwService.findOneByBatchNoLineNoAndCreateDate(batchNo, lineNo, createDate)) {
			data.put("msg", "数据重复保存");
			data.put("style", "layui-layer-lan");
			data.put("id", "2");
			return data;
		}
		data.put("msg", "保存数据成功");
		data.put("style", "layui-layer-molv");
		data.put("id", "1");
		bwService.saveBw(bwList);
		return data;
	}
}
