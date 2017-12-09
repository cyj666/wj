<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<link href="<%=ctx %>/static/css/jedate.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=ctx %>/static/js/jquery.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/jquery.jedate.js"></script>
<style type="text/css">
.od-header {
	width: auto;
	height: 60px;
}

.od-header {
	line-height: 60px;
}
.table {  
  border: 0px solid transparent;
}
</style>
</head>

<body>
	<div class="container-fluid">
		<div class="od-header">
			<a type="button" class="btn btn-sm btn-danger search">
				<span class="glyphicon glyphicon-search"></span> 查询
			</a>
 		<!-- <a type="button" class="btn btn-sm btn-danger hidden download">
				<span class="glyphicon glyphicon-download"></span> 导出
			</a> -->
			<!-- <input type="date" class="date" style="height:23px;width:170px;"> -->
			<input type="text" class="start" value="${start }" style="height:23px" hidden="hidden">
			<input type="text" class="end" value="${end }" style="height:23px" hidden="hidden">
		</div>
		<div>
			起始日期：<input type="text" id="txt_calendar1" class="jeinput" placeholder="YYYY-MM-DD" style="height:21px;width:173px"/>
			<!-- <input type="text" id="bn" style="height:23px;width:170px;font-family:'微软雅黑';font-size:5px"> -->
			结束日期：<input type="text" id="txt_calendar2" class="jeinput" placeholder="YYYY-MM-DD" style="height:21px;width:173px"/>
		</div>
		<div class="row">
			<table class="table">
				<c:forEach var="i" begin="0" end="${bnList.size() }" step="5">
					<tr>
						<c:if test="${bnList.size()>i }">
							<td class="bn_val">
								<a class="btn btn-lg btn-link getBatchNoData">${bnList.get(i) }</a>
							</td>
						</c:if>
						<c:if test="${bnList.size()>i+1 }">
							<td>
								<a class="btn btn-lg btn-link getBatchNoData">${bnList.get(i+1) }</a>
							</td>
						</c:if>
						<c:if test="${bnList.size()>i+2 }">
							<td>
								<a class="btn btn-lg btn-link getBatchNoData">${bnList.get(i+2) }</a>
							</td>
						</c:if>
						<c:if test="${bnList.size()>i+3 }">
							<td>
								<a class="btn btn-lg btn-link getBatchNoData">${bnList.get(i+3) }</a>
							</td>
						</c:if>
						<c:if test="${bnList.size()>i+4 }">
							<td>
								<a class="btn btn-lg btn-link getBatchNoData">${bnList.get(i+4) }</a>
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</table>
		</div>	
	</div>
</body>
<script type="text/javascript">

$("#txt_calendar1").jeDate({
  format: "YYYY-MM-DD"
});	
$("#txt_calendar2").jeDate({
  format: "YYYY-MM-DD"
});	

$(function() {
	//设置导出按钮的状态
/* 	if($('.bn_val a').text() != "") {
		$('.download').removeClass("hidden");
	} */
	
	//设置输入框的值，并设置颜色
	$('#bn').val("请输入检索批号前1~3个字符");
	$('#bn').css({"color":"#C0C0C0"});
	
	
	$('.search').click(function() {
		var startDate = $('#txt_calendar1').val();
		//console.log(startDate);
		var endDate = $('#txt_calendar2').val();
		//console.log(endDate);
/* 		var bn = $('#bn').val();
		var reg = /^[a-zA-Z]{1,3}$/;
		if(date!="" && !reg.test(bn)) {
			$(this).attr("href","selectByMonth?date="+date+"&bn=");
		}else if(date!="" && reg.test(bn)){
			$(this).attr("href","selectByMonth?date="+date+"&bn="+bn);
		}else {
			alert("请选择日期!");
		} */
		if (startDate != "" && endDate != "") {
			$(this).attr("href","selectByMonth?startDate="+startDate+"&endDate="+endDate);
		} else {
			alert("请选择日期!");
		}
	});
	
	
	$('.getBatchNoData').click(function() {
		var batchNo = $(this).text();
		//获取日期
		var start = $('.start').val();
		var end = $('.end').val();
		$(this).attr("href","getBatchNoData?batchNo="+batchNo+"&start="+start+"&end="+end)
			.attr("target", "_blank");
	});
	
	//取得页面加载时，输入框的值
	var bn_val = $('#bn').val();
	$('#bn').focus(function() {
		$('#bn').val("");
		$('#bn').css({"color":"#000"})
	});
	
	$('#bn').blur(function() {
		var reg = /^[a-zA-Z]{1,3}$/;
		if(!reg.test($('#bn').val())){
			alert("请输入1-3位字母");
			$('#bn').val(bn_val);
		}
	});
	
	$('.download').click(function() {
		var date = $('.month').val();
		$(this).attr("href","exportData?date="+date);
	});
	
});
</script>
</html>