<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>
<link href="<%=ctx %>/static/img/life.ico" rel="shortcut icon">
<link href="<%=ctx %>/static/css/bootstrap.min.css" rel="stylesheet" type="text/css" >

<script type="text/javascript" src="<%=ctx %>/static/js/jquery.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/jquery.table2excel.js"></script>
<style type="text/css">
.od-header {
	width: auto;
	height: 60px;
}

.od-header {
	line-height: 60px;
}
</style>
</head>

<body>
	<div class="container-fluid">
		<div class="od-header">
			<a type="button" class="btn btn-sm btn-danger search">
				<span class="glyphicon glyphicon-search"></span> 查询
			</a>
			<!-- <a type="button" class="btn btn-sm btn-success">
				<span class="glyphicon glyphicon-download"></span> 导出
			</a> -->
			<input type="text" class="input-sm" value="输入批号的开头部分"/>
			<select class="checkIndexName">
				<option value="all">所有指标</option>
				<option value="linear_density">纤度</option>
				<option value="density_percent">D%</option>
				<option value="ld_CV">纤度CV%</option>
				<option value="strength">强度</option>
				<option value="strength_CV">强度CV%</option>
				<option value="elongation">伸长</option>
				<option value="elongation_CV">伸长CV%</option>
				<option value="yarnlevelness_U">条干U%</option>
				<option value="boiling_water">沸水%</option>
				<option value="crimp_contraction">卷曲收缩率%</option>
				<option value="crimp_stability">卷曲稳定度%</option>
				<option value="water_ratio">含水率%</option>
				<option value="oli_content">含油率%</option>
				<option value="grid_line">网络度</option>
			</select>
		</div>
		<div class="od-body">
			<table class="table table-responsive table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="text-center" style="width: 25%">批号</th>
						<th class="text-center" style="width: 25%">指标</th>
						<th class="text-center" style="width: 25%">标准值</th>
						<th class="text-center" style="width: 25%">偏差值</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${checkIndexList }" var="cil">
						<tr class="text-center danger">
							<td>${cil.batchNo }</td>
							<td>
								<c:choose>
									<c:when test="${cil.checkIndex == 'linear_density' }">纤度</c:when>
									<c:when test="${cil.checkIndex == 'density_percent' }">D%</c:when>
									<c:when test="${cil.checkIndex == 'ld_CV' }">纤度CV%</c:when>
									<c:when test="${cil.checkIndex == 'strength' }">强度</c:when>
									<c:when test="${cil.checkIndex == 'strength_CV' }">强度CV%</c:when>
									<c:when test="${cil.checkIndex == 'elongation' }">伸长</c:when>
									<c:when test="${cil.checkIndex == 'elongation_CV' }">伸长CV%</c:when>
									<c:when test="${cil.checkIndex == 'yarnlevelness_U' }">条干U%</c:when>
									<c:when test="${cil.checkIndex == 'boiling_water' }">沸水%</c:when>
									<c:when test="${cil.checkIndex == 'crimp_contraction' }">卷曲收缩率%</c:when>
									<c:when test="${cil.checkIndex == 'crimp_stability' }">卷曲稳定度%</c:when>
									<c:when test="${cil.checkIndex == 'water_ratio' }">含水率%</c:when>
									<c:when test="${cil.checkIndex == 'oli_content' }">含油率%</c:when>
									<c:when test="${cil.checkIndex == 'grid_line' }">网络度</c:when>
								</c:choose>
							</td>
							<td>${cil.standardValue }</td>
							<td>${cil.deviationValue }</td>
						</tr>
					</c:forEach>
				</tbody>
				<c:if test="${checkIndexList.size()==0}">
					<h4 class="text-danger"><b>没有数据!</b></h4>
				</c:if>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function() {
	$('.input-sm').focus(function() {
		$(this).val("");
	});
	
/* 	var indexName = "";
	var i = 0;
	$('.checkIndexName').click(function() {
		i++;
		if (i % 2 == 0) {
			indexName = $(this).val(); 
		}
	}); */
	
	$('.search').click(function() {
		var batchNo = $('.input-sm').val();
		var indexName = $('.checkIndexName').val();
		//console.log(batchNo+","+indexName);
		/*var reg = /^[A-Za-z]{1,3}([A-Za-z0-9]{8})?$/;
		if (!reg.test(batchNo)) {
			batchNo = "";
		} */
		$(this).attr("href","selectCheckIndex?batchNo="+batchNo+"&indexName="+indexName);
	});
});
</script>
</html>
