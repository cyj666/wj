<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${ctx }/static/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx }/static/css/matrix-style.css">
<link rel="stylesheet" href="${ctx }/static/css/sidebar-menu.css">
<link rel="stylesheet" href="${ctx }/static/font-awesome/css/font-awesome.css">

<script type="text/javascript" src="${ctx }/static/js/jquery.js"></script>
<script type="text/javascript" src="${ctx }/static/js/sidebar-menu.js"></script>
<style type="text/css">
.main-sidebar{
	position: absolute;
	top: 0;
	left: 0;
	height: 100%;
	min-height: 100%;
	width: 400px;
	z-index: 810;
	background-color: #222d32;
 }
</style>

</head>
<body>
<aside class="main-sidebar">
<section  class="sidebar">
	<ul class="sidebar-menu">
	  <li class="treeview">
	  	<a href="checkMain" target="main">
		  <i class="icon-th-large"></i> 基础数据
		</a>
		<ul class="treeview-menu">
		  <li><a href="addIndex" target="main"><i class="icon-coffee"></i> 指标数据</a></li>
		  <li><a href="is" target="main"><i class="icon-signin"></i> 查询指标数据</a></li>
		</ul>
	  </li>
	  <li class="treeview">
	  	<a href="main" target="main">
		  <i class="icon-th-list"></i> 检测数据
		</a>
		<ul class="treeview-menu">
		  <li><a href="OC" target="main"><i class="icon-tint"></i> 含油率</a></li>
		  <li><a href="SE" target="main"><i class="icon-link"></i> 强伸</a></li>
		  <li><a href="PC" target="main"><i class="icon-leaf"></i> 卷曲度</a></li>
		  <li><a href="BW" target="main"><i class="icon-adjust"></i> 沸水</a></li>
		  <li><a href="others" target="main"><i class="icon-ok-sign"></i> 录入项</a></li>
		</ul>
	  </li>
	  <li class="treeview">
		<a href="dealMain" target="main">
		  <i class="icon-desktop"></i> <span>数据处理</span>
		</a>
		<ul class="treeview-menu">
		  <li><a href="testAgain" target="main"><i class="icon-exchange"></i> 复测数据</a></li>
		</ul>
	  </li>
	  <li class="treeview">
		<a href="searchMain" target="main">
		  <i class="icon-search"></i>
		  <span>查询</span>
		</a>
		<ul class="treeview-menu" style="display: none;">
		  <li><a href="od" target="main"><i class="icon-edit"></i> 查询当天数据</a></li>
		  <li><a href="dr" target="main"><i class="icon-calendar"></i> 查询多天数据</a></li>
		  <li><a href="sm" target="main"><i class="icon-share"></i> 查询批号数据</a></li>
		  <li><a href="ca" target="main"><i class="icon-forward"></i> 查询加测数据</a></li>
		</ul>
	  </li>
	</ul>
  </section>
 </aside>
<script type="text/javascript">
sidebarMenu($('.sidebar-menu'));
$(function() {
	/* $('.main').click(function() {
		//console.log(111);
		$.get("dealMain",function(data){
			console.log(data);
		});
	}); */
});
</script>

</body>
</html>