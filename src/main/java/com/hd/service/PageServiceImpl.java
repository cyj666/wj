package com.hd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hd.pojo.Page;

@Service
public class PageServiceImpl implements PageService{

	@Override
	public Page getpage(List<?> list) {
		Page page = new Page();
		Integer totalPage = null;
		/*
		    判断list是否为空*/
		if (list==null || list.size()==0) {
			System.out.println("error");
			return null;
		}
		
		//设置总记录数
		page.setTotalCount(list.size());
		
		//判断页数并设置页数
		if (list.size()%page.getPageSize()==0) {
			 totalPage = list.size()/page.getPageSize();
		}else {
			totalPage = list.size()/page.getPageSize()+1;
		}
		page.setTotalPage(totalPage);
		
		return page;
	}

}
