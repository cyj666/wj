<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>
<link href="<%=ctx%>/static/img/life.ico" rel="shortcut icon">
<link href="<%=ctx%>/static/css/iconfont.css" rel="stylesheet" type="text/css" />
<link href="<%=ctx%>/static/css/bootstrap.min.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=ctx%>/static/js/jquery.js"></script>
<script type="text/javascript" src="<%=ctx%>/static/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="text-center">
			<h2 class="text-danger">错误的文件名</h2>
		</div>
		<div>
			<table class="table talbe-responsive table-hover">
				<c:forEach items="${fileNameMap.keySet() }" var="key">
					<tr class="danger text-center">
						<td style="width:50%"><b>${key }</b></td>
						<td style="width:50%"><b>${fileNameMap.get(key) }</b></td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>