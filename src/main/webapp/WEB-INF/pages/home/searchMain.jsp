<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>

<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>
<link rel="stylesheet" href="${ctx }/static/css/bootstrap.min.css">
<link rel="stylesheet" href="${ctx }/static/css/matrix-style.css">
<link rel="stylesheet" href="${ctx }/static/font-awesome/css/font-awesome.css">
<link rel="shortcut icon" href="${ctx }/static/img/life.ico">

<script type="text/javascript" src="${ctx }/static/js/jquery.js"></script>
<script type="text/javascript" src="${ctx }/static/js/bootstrap.min.js"></script>
<style type="text/css">
body {
	background: #D1EEEE
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<div class="quick-actions_homepage">
					<ul class="quick-actions">
						<li class="bg_ly span3"><a href="od"> <i
								class="icon-edit"></i> 查询当天数据
						</a></li>
						<li class="bg_ls span3"><a href="dr"> <i
								class="icon-calendar"></i> 查询多天数据
						</a></li>
						<li class="bg_lo span3"><a href="sm"> <i
								class="icon-share"></i> 查询批号数据
						</a></li>
						<li class="bg_lr span3"><a href="ca"> <i
								class="icon-forward"></i> 查询加测数据
						</a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
</body>
</html>