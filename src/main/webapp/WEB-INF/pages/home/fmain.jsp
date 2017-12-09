<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<title>物 化 检 测</title>
<style type="text/css">
frameset{
	padding:0px;
	margin:0px;
	border:none" 
} 
frame{
	width:100%;
	padding:0px;
	margin:0px;
	border:none;
}
</style>
<link rel="shortcut icon" href="${ctx }/static/img/life.ico">
</head>
<frameset rows="100,*" name="topFrameset" frameborder="no" border="0" framespacing="0">
	<frame name="top_frame" scrolling="no"  target="middleFrameSet" src="title" frameborder="no" marginwidth="0" marginheight="0">	
	<frameset cols="140,*" height="100%" name="middle" frameborder="no" border="0" framespacing="0">
		<frame name="leftFrame" class="leftFrame" target="main" scrolling="no" 
		src="left" />
		<frame name="main" class="rightFrame" src="main" frameborder="no" marginwidth="0" marginheight="0"/>
	</frameset>
	<noframes>
	<body>
	    <p>此网页使用了框架，但您的浏览器不支持框架。</p>
	</body>
	</noframes>
</frameset>
</html>