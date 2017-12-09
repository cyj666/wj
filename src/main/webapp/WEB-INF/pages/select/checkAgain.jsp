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
<title>加测数据</title>
<link href="<%=ctx %>/static/img/life.ico" rel="shortcut icon">
<link href="<%=ctx %>/static/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
<link href="<%=ctx %>/static/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" >
<link href="<%=ctx %>/static/css/jedate.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=ctx %>/static/js/jquery.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/jquery.table2excel.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/jquery.jedate.js"></script>
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
		<div class="row">
<!-- 		<div class="col-sm-1">
				<a class="btn btn-sm btn-link" href="searchMain"><b>返回首页</b></a>
			</div> -->
			<div class="col-sm-12 text-danger">
				<h3 style="text-align: center;margin:0 auto">加 测 数 据</h3>
			</div>
			
		</div>
		<div class="od-header">
		<a type="button" class="btn btn-sm btn-danger search">
				<span class="glyphicon glyphicon-search"></span> 查询
			</a>
			  <button type="button" id="download" class="btn btn-sm btn-success">
			 <span class="glyphicon glyphicon-download-alt"></span>导出</button> 	
			日期：<input type="text" id="txt_calendar" class="jeinput" placeholder="YYYY-MM-DD" style="height:21px;width:173px"/>
			项目: &nbsp;
			<select class="selectpicker" data-width="auto">
				<option value="FDY" data-content="<span class='label label-success'>FDY</span>">FDY</option>
				<option value="HOY" data-content="<span class='label label-info'>HOY</span>">HOY</option>
				<option value="POY" data-content="<span class='label label-primary'>POY</span>">POY</option>
				<option value="DTY" data-content="<span class='label label-danger'>DTY</span>">DTY</option>
			</select>
			
		</div>
		
		 <div class="row">
			<table  class="table table-responsive table-bordered table-condensed" id="tableExcel">
				<thead>
					 <tr class="warning">
						<th rowspan="2" class="text-center noExl hidden" style="vertical-align: middle !important;width:3%">序号</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:7%">生产时间</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:5%">测量时间</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:2%">批号</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">线位号</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">线密度</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:1%">D%</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:1%">CV%</th>
						<th colspan="2" class="text-center" style="vertical-align: middle !important;width:7%">强度</th>
						
						<th colspan="2" class="text-center" style="vertical-align: middle !important;width:7%">伸长</th>
						
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">条干U%</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">卷曲收缩率</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">卷曲稳定度</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">沸水</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">含油率</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">含水率</th>
						<th colspan="2" class="text-center" style="vertical-align: middle !important;width:7%">网络</th>
						
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:4%">状态</th>
						<tr class="info">										
							<th class="text-center" >平均值</th>
							<th class="text-center" >CV%</th>	
							<th class="text-center" >平均值</th>
							<th class="text-center" >CV%</th>	
							<th class="text-center" >平均</th>
							<th class="text-center" >范围</th>	
						</tr>
				</thead>
				<tbody>
					<c:forEach items="${nlList}" var="nl">
						<tr class="success text-center operate">
							<td class="hidden">${nlList.indexOf(nl)+1}</td>
							<td>${nl.productionDate}</td>
							<td class="date">${nl.createDate}</td>
							<td>${nl.batchNo }</td>
							<td>${nl.lineNo }</td>
							<td>
								<c:if test="${nl.linearDensity == null }"></c:if>
								<c:if test="${nl.linearDensity != null }">
									<fmt:formatNumber type="number" value="${nl.linearDensity }" pattern="0.0" maxFractionDigits="1" />							
								</c:if>
							</td>
							<td>
								<c:if test="${nl.densityPercent == null }"></c:if>
								<c:if test="${nl.densityPercent != null }">
									<fmt:formatNumber type="number" value="${nl.densityPercent }" pattern="0.00" maxFractionDigits="2" />
								</c:if>
							</td>
							<td>
								<c:if test="${nl.ldCV == null }"></c:if>
								<c:if test="${nl.ldCV != null }">
									<fmt:formatNumber type="number" value="${nl.ldCV }" pattern="0.00" maxFractionDigits="2" />
								</c:if>
							</td>
							<td>
								<c:if test="${nl.strength == null }"></c:if>
								<c:if test="${nl.strength != null }">
									<fmt:formatNumber type="number" value="${nl.strength/100.0 }" pattern="0.00" maxFractionDigits="2" />
								</c:if>
							</td>
							<td>
								<c:if test="${nl.strengthCV == null }"></c:if>
								<c:if test="${nl.strengthCV != null }">
									<fmt:formatNumber type="number" value="${nl.strengthCV }" pattern="0.00" maxFractionDigits="2" />
								</c:if>
							</td>
							<td>
								<c:if test="${nl.elongation == null }"></c:if>
								<c:if test="${nl.elongation != null }">
									<fmt:formatNumber type="number" value="${nl.elongation/10 }" pattern="0.0" maxFractionDigits="1" />
								</c:if>
							</td>
							<td>
								<c:if test="${nl.elongationCV == null }"></c:if>
								<c:if test="${nl.elongationCV != null }">
									<fmt:formatNumber type="number" value="${nl.elongationCV }" pattern="0.00" maxFractionDigits="2" />
								</c:if>
							</td>		
							<td>
								<c:if test="${nl.yarnlevelness == null }"></c:if>
								<c:if test="${nl.yarnlevelness != null }">
									<fmt:formatNumber type="number" value="${nl.yarnlevelness }" pattern="0.00" maxFractionDigits="2" />
								</c:if>
							</td>			
							<td>
								<c:if test="${nl.crimpContraction == null }"></c:if>
								<c:if test="${nl.crimpContraction != null }">
									<fmt:formatNumber type="number" value="${nl.crimpContraction/10 }" pattern="0.0" maxFractionDigits="1" />
								</c:if>
							</td>
							<td>
								<c:if test="${nl.crimpStability == null }"></c:if>
								<c:if test="${nl.crimpStability != null }">
									<fmt:formatNumber type="number" value="${nl.crimpStability/10 }" pattern="0.0" maxFractionDigits="1" />
								</c:if>
							</td>
							<td>
								<c:if test="${nl.boilingWater == null }"></c:if>
								<c:if test="${nl.boilingWater != null }">
									<fmt:formatNumber type="number" value="${nl.boilingWater/10 }" pattern="0.0" maxFractionDigits="1" />
								</c:if>
							</td>
							<td>
								<c:if test="${nl.oliContent == null }"></c:if>
								<c:if test="${nl.oliContent != null }">
									<fmt:formatNumber type="number" value="${nl.oliContent/100.0 }" pattern="0.00" maxFractionDigits="2" />
								</c:if>
							</td>
							<td>
								<c:if test="${nl.waterRatio == null }"></c:if>
								<c:if test="${nl.waterRatio != null }">
									<fmt:formatNumber type="number" value="${nl.waterRatio }" pattern="0.00" maxFractionDigits="2" />
								</c:if>
							</td>
							<td>
								<c:if test="${nl.gridLine == null }"></c:if>
								<c:if test="${nl.gridLine != null }">
									<fmt:formatNumber type="number" value="${nl.gridLine }" pattern="0" />
								</c:if>
							</td>
							<td>${nl.ranges }</td>
							 <td>
								<c:if test="${nl.nlStatus > 20 && nl.nlStatus < 30}"><span class="text-primary"><b>研究加测</b></span></c:if>
								<c:if test="${nl.nlStatus > 30 && nl.nlStatus < 40}"><span class="text-warning"><b>车间加测</b></span></c:if>
						    </td>
							<!--  <td>${fn:substring(nl.createDate,0, 10)}</td>-->
						</tr>
					</c:forEach>
				</tbody>
				<c:if test="${nlList.size()==0}">
				<h4 class="text-danger">没有当天数据!</h4>
				</c:if>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">

$("#txt_calendar").jeDate({
  format: "YYYY-MM-DD"
});	

$(function() {
	$('.search').click(function() {
		var date = $('#txt_calendar').val();
		console.log(date);
		var bn = $('.selectpicker').val();
		console.log(bn);
		if (date!="") {
			$(this).attr("href","getCheckAgainData?date="+date+"&bn="+bn).attr("target", "_blank");
		} else {
			alert("请选择日期!");
		}
	});
	
	var length = $('.operate').length;
    $("#download").click(function(){
    	//alert("导出");
    	if(length==0){
    		alert("没有数据，请先选择");
    	}else{
	        $("#tableExcel").table2excel({
	            // 不被导出的表格行的CSS class类
	            exclude: ".noExl",
	            // 导出的Excel文档的名称，（没看到作用）
	            name: "Excel Document Name",
	            // Excel文件的名称
	            filename: $(".date").first().text()+"号加测数据"+".xls"
	        });
	        alert("导出成功！");
    	}
    });
	
});
</script>
</html>