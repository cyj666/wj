package com.hd.controller;

import java.io.File;
import java.util.ArrayList;
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

import com.hd.pojo.OliContent;
import com.hd.pojo.Page;
import com.hd.service.OcService;
import com.hd.service.PageService;
import com.hd.tool.PathUtil;

@Controller
public class OcController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(SeController.class);
	
	//将数据进度信息存储到map中
	private Map<String, Long> dataMap = new HashMap<String, Long>();
	
	//将要返回给页面的信息存到data中
	private Map<String, String> data = new HashMap<String, String>();
	
	//定义一个路径
	private String allPath;
	
	@Autowired
	private OcService ocService;
		
	private List<OliContent> ocList;
	
	@Autowired
	private PageService pageService;
	
	private Map<Integer, List<?>> map;
	
	/**
	 * 将刚刚上传的文件经过处理返回到页面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/ocRead",method=RequestMethod.GET)
	public String ocRead(Model model, @RequestParam(value="pages",required=false) Integer pages,
			HttpServletRequest request,@RequestParam(value="date",required=false) String date) {
		//判断page是否等于null，如果等于null则说明没有读取数据，不为null则需要读取数据
		List<String> errList = new ArrayList<String>();
 		if (pages==null) {
			//获取刚刚文件上传的路径
			String path = PathUtil.getPath("ocPath");
			request.getSession().setAttribute("saveStatus", 0);
			//读取文件
			map = ocService.readOc(path,date);
			
			model.addAttribute("status", "show");
			
			if (map != null) {
				//判断是否有读取的数据
				ocList = (List<OliContent>) map.get(1);
				if (map.size()>1) {
					errList = (List<String>) map.get(2);
				}
			} else {
				model.addAttribute("msg", 2);
				return "OC";
			}
			
			
			if (errList.size()>0) {
				model.addAttribute("errList", errList);
				//model.addAttribute("ocList", ocList);
				return "error/OcError";
			}
			
			if(ocList==null) {
				model.addAttribute("msg", 1);
			}else if(ocList.size()==0) {
				model.addAttribute("msg", 2);		//如果上传失败的话，文件夹中没有数据，在读的时候就提示没有文件可以读
			}else {
				model.addAttribute("msg", 3);
			}
		}
		
		//分页
		Page page = pageService.getpage(ocList);
		
		Integer begin = null;
		Integer end = null;
		Integer totalpages=0;
		Integer totalcount=0;
		if (ocList != null && !ocList.isEmpty()) {
			if (page.getTotalPage()!=null||page.getTotalCount()!=null) {
				totalpages = page.getTotalPage();
				totalcount = page.getTotalCount();
			}
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
			
			model.addAttribute("ocList", ocList);
			model.addAttribute("page", page);
			model.addAttribute("begin", begin);
			model.addAttribute("end", end);
			model.addAttribute("pages", pages);
			return "forward:OC";
		}else {
			model.addAttribute("msg", 4);
			//model.addAttribute("ocList", ocList);
			return "OC";
		}
		
	}
	
	/**
	 * 文件上传
	 * @param file
	 * @return 如果成功就跳转到成功页面，如果失败就跳转到失败页面
	 */
	@RequestMapping("/oc/ocUpFile")
	@ResponseBody
	public Map<String, String> upload(@RequestParam("file") MultipartFile[] files, 
			@RequestParam("filelSavePath") String path) {
		path = path + PathUtil.getTime();
		allPath = path;
		
		//将路径写入到文件中
		PathUtil.savePath(path,"ocPath");
		
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
		
		System.out.println("文件上传成功1");
		data.put("msg", "文件上传成功");
		return data;
	}
	
	/**
	 * 获取进度条信息的
	 * @return
	 */
	@RequestMapping("/oc/getPercent")
	@ResponseBody
	public Map<String, Long> getPercent() {
//		System.out.println(allPath);
		Long length = 0L;
		File file = new File(allPath);
		if(!file.isDirectory()) {
			return null;
		}
		String[] fileNames = file.list();
		for (String fn : fileNames) {
			file = new File(allPath + fn);
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
	@RequestMapping(value="ocSaveData",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String> ocSave() {
		OliContent oc = ocList.get(0);
		String date = oc.getCreateDate();

		if (ocService.findOc(oc.getBatchNo(),date)) {  //判断数据库中是否存在同天同批号的数据
			data.put("msg", "存在相同数据，保存失败");
			data.put("style", "layui-layer-lan");
			data.put("id", "2");
			return data;
		}
		
		ocService.saveOc(ocList);
		data.put("style", "layui-layer-molv");
		data.put("id", "1");
		data.put("msg", "保存数据成功");
		return data;
	}
		
}
