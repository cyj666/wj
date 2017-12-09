<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String ctx = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<%=ctx %>/static/img/life.ico" rel="shortcut icon">
<link href="<%=ctx %>/static/css/iconfont.css" rel="stylesheet" type="text/css"/>
<link href="<%=ctx %>/static/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
<link href="<%=ctx %>/static/css/bootstrap.min.css" rel="stylesheet" type="text/css" >

<script type="text/javascript" src="<%=ctx %>/static/js/jquery.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/bootstrap-select.min.js"></script>
<title>含油错误界面</title>
</head>
<body>
<h2 class="text-center">错误信息</h2>
共计<span class="text-danger" >${errList.size()}</span>条错误 
<br/>
<c:forEach var="i" begin="0" end="${errList.size()-1}">
<small class="">${errList.get(i)}</small>
<br/>
</c:forEach>
</body>
</html>