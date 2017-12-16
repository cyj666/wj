<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath }"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>
<link href="${ctx }/static/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" >
<link rel="stylesheet" href="${ctx }/static/css/bootstrap.min.css">
<link href="${ctx }/static/css/jedate.css" rel="stylesheet" type="text/css">
<link rel="shortcut icon" href="${ctx }/static/img/life.ico">

<script type="text/javascript" src="${ctx }/static/js/jquery.js"></script>
<script type="text/javascript" src="${ctx }/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${ctx }/static/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${ctx }/static/layer.js"></script>
<script type="text/javascript" src="${ctx }/static/js/jquery.jedate.js"></script>
<style type="text/css">
body{
	background: #D1EEEE;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-1">
				<a class="btn btn-sm btn-link" href="dealMain"><b>首页</b></a>
			</div>
			<div class="col-sm-11 text-danger">
				<h3 style="text-align: center;margin:0 auto">复  测  数  据</h3>
			</div>
		</div>
		
		<div class="ta_header">
			<a type="button" class="btn btn-xs btn-danger" id="search">
				<span class="glyphicon glyphicon-search"></span> 查 询
			</a>
			&nbsp;&nbsp;&nbsp;&nbsp;
			项目: &nbsp;
			<select class="selectpicker" data-width="100px">
				<option value="p_oc" <c:if test="${testType==0}">selected</c:if> data-content="<span class='label label-success'>含油率</span>">含油率</option>
				<option value="p_se" <c:if test="${testType==1}">selected</c:if> data-content="<span class='label label-info'>强&nbsp;&nbsp;&nbsp;&nbsp;伸</span>">强伸</option>
				<option value="p_cc" <c:if test="${testType==2}">selected</c:if> data-content="<span class='label label-primary'>卷&nbsp;&nbsp;&nbsp;&nbsp;曲</span>">卷曲稳定度和卷曲收缩率</option>
				<option value="p_bw" <c:if test="${testType==3}">selected</c:if> data-content="<span class='label label-danger'>沸&nbsp;&nbsp;&nbsp;&nbsp;水</span>">沸水</option>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;
			日期:
			<input type="text" id="txt_calendar" class="jeinput" placeholder="YYYY-MM-DD" style="height:21px;width:173px"/>
			<input type="text" hidden="hidden" id="testType" value="${testType }"/>
		</div>
		
		<div class="ta_body">
			<!-- 含油率 -->
			<table class="table table-responsive table-bordered table-condensed table-hover" hidden="hidden">
				<thead>
					<tr class="success">
						<th class="text-center" style="width:10%">勾 选</th>
						<th class="text-center" style="width:15%">批号</th>
						<th class="text-center" style="width:15%">线位号</th>
						<th class="text-center" style="width:10%">含油率</th>
						<th class="text-center" style="width:15%">状态</th>
						<th class="text-center" style="width:25%">测量时间</th>
					</tr>
				</thead>
				<c:choose>
					<c:when test="${ocList.size() == 0 }">
						<h3 class="text-danger">当天没有复测数据</h3>
					</c:when>
					<c:when test="${ocList.size() > 0 }">
						<tbody>
							<c:forEach var="i" begin="0" end="${ocSize }" step="1">
								<tr class="warning text-center ocOperate">
									<td><input type="checkbox" value="${i+1 }" class="checked" disabled/></td>
									<td>${ocList.get(i).batchNo}</td>
									<td>${ocList.get(i).lineNo }</td>
									<td><fmt:formatNumber type="number" value="${ocList.get(i).oliContent/100.0 }" pattern="0.00" maxFractionDigits="2" /></td>
									<td>
										<c:choose>
											<c:when test="${ocList.get(i).ocStatus==0 }"><p class="text-success" style="margin: 0"><b>日常取样丝</b></p></c:when>
										</c:choose>
										<c:choose>
											<c:when test="${ocList.get(i).ocStatus>10 && ocList.get(i).ocStatus<20}"><p class="text-danger" style="margin: 0"><b>复&nbsp;测&nbsp;丝</b></p></c:when>
										</c:choose>
									</td>
									<td>${ocList.get(i).createDate }</td>
								</tr>
							</c:forEach>
						</tbody>
					</c:when>
				</c:choose>
			</table>
		
			<!-- 强伸 -->
			<table class="table table-responsive table-bordered table-condensed table-hover" hidden="hidden">
				<thead>
					<tr class="warning">
						<th class="text-center" style="width:5%">勾 选</th>
						<th class="text-center" style="width:15%">批号</th>
						<th class="text-center" style="width:15%">线位号</th>
						<th class="text-center" style="width:10%">规格</th>
						<th class="text-center" style="width:10%">强度</th>
						<th class="text-center" style="width:10%">伸长率</th>
						<th class="text-center" style="width:15%">状态</th>
						<th class="text-center" style="width:20%">测量时间</th>
					</tr>
				</thead>
				<c:choose>
					<c:when test="${seList.size() == 0}">
						<h3 class="text-danger">当天没有复测数据</h3>
					</c:when>
					<c:when test="${seList.size() > 0}">
						<tbody>
							<c:forEach var="i" begin="0" end="${seSize }" step="1">
								<tr class="success text-center seOperate">
									<td><input type="checkbox" value="${i+1 }" class="checked" disabled/></td>
									<td>${seList.get(i).batchNo}</td>
									<td>${seList.get(i).lineNo }</td>
									<td>${seList.get(i).seFormat }</td>
									<td><fmt:formatNumber type="number" value="${seList.get(i).strength/100.0 }" pattern="0.00" maxFractionDigits="2" /></td>
									<td><fmt:formatNumber type="number" value="${seList.get(i).elongation/10.0 }" pattern="0.0" maxFractionDigits="1" /></td>
									<td>
										<c:choose>
											<c:when test="${seList.get(i).seStatus==0 }"><p class="text-success" style="margin: 0"><b>日常取样丝</b></p></c:when>
										</c:choose>
										<c:choose>
											<c:when test="${seList.get(i).seStatus>10 && seList.get(i).seStatus<20 }"><p class="text-danger" style="margin: 0"><b>复&nbsp;测&nbsp;丝</b></p></c:when>
										</c:choose>
									</td>
									<td>${seList.get(i).createDate }</td>
								</tr>
							</c:forEach>
						</tbody>
					</c:when>
				</c:choose>
			</table>
			
			<!-- 卷曲度 -->
			<table class="table table-responsive table-bordered table-condensed table-hover" hidden="hidden">
				<thead>
					<tr class="warning">
						<th class="text-center" style="width:5%">勾 选</th>
						<th class="text-center" style="width:15%">批号</th>
						<th class="text-center" style="width:15%">线位号</th>
						<th class="text-center" style="width:10%">卷曲收缩率</th>
						<th class="text-center" style="width:10%">卷曲稳定度</th>
						<th class="text-center" style="width:15%">状态</th>
						<th class="text-center" style="width:25%">测量时间</th>
					</tr>
				</thead>
				<c:choose>
					<c:when test="${pcList.size() == 0}">
						<h3 class="text-danger">当天没有复测数据</h3>
					</c:when>
					<c:when test="${pcList.size() > 0}">
						<tbody>
							<c:forEach var="i" begin="0" end="${pcSize }" step="1">
								<tr class="info text-center pcOperate">
									<td><input type="checkbox" value="${i+1 }" class="checked" disabled/></td>
									<td>${pcList.get(i).batchNo}</td>
									<td>${pcList.get(i).lineNo }</td>
									<td><fmt:formatNumber type="number" value="${pcList.get(i).crimpContraction/10.0 }" pattern="0.0" maxFractionDigits="2" /></td>
									<td><fmt:formatNumber type="number" value="${pcList.get(i).crimpStability/10.0 }" pattern="0.0" maxFractionDigits="2" /></td>
									<td>
										<c:choose>
											<c:when test="${pcList.get(i).pcStatus==0 }"><p class="text-success" style="margin: 0"><b>日常取样丝</b></p></c:when>
										</c:choose>
										<c:choose>
											<c:when test="${pcList.get(i).pcStatus>10 && pcList.get(i).pcStatus<20 }"><p class="text-danger" style="margin: 0"><b>复&nbsp;测&nbsp;丝</b></p></c:when>
										</c:choose>
									</td>
									<td>${pcList.get(i).createDate }</td>
								</tr>
							</c:forEach>
						</tbody>
					</c:when>
				</c:choose>
			</table>
			
			<!-- 沸水 -->
			<table class="table table-responsive table-bordered table-condensed table-hover" hidden="hidden">
				<thead>
					<tr class="info">
						<th class="text-center" style="width:5%">勾 选</th>
						<th class="text-center" style="width:15%">批号</th>
						<th class="text-center" style="width:15%">线位号</th>
						<th class="text-center" style="width:10%">沸水</th>
						<th class="text-center" style="width:15%">状态</th>
						<th class="text-center" style="width:20%">测量时间</th>
					</tr>
				</thead>
				<c:choose>
					<c:when test="${bwList.size() == 0}">
						<h3 class="text-danger">当天没有复测数据</h3>
					</c:when>
					<c:when test="${bwList.size() > 0}">
						<tbody>
							<c:forEach var="i" begin="0" end="${bwSize }" step="1">
								<tr class="danger text-center bwOperate">
									<td><input type="checkbox" value="${i+1 }" class="checked" disabled/></td>
									<td>${bwList.get(i).batchNo}</td>
									<td>${bwList.get(i).lineNo }</td>
									<td><fmt:formatNumber type="number" value="${bwList.get(i).boilingWater/10.0 }" pattern="0.0" maxFractionDigits="1" /></td>
									<td>
										<c:choose>
											<c:when test="${bwList.get(i).bwStatus==0 }"><p class="text-success" style="margin: 0"><b>日常取样丝</b></p></c:when>
										</c:choose>
										<c:choose>
											<c:when test="${bwList.get(i).bwStatus>10 && bwList.get(i).bwStatus<20}"><p class="text-danger" style="margin: 0"><b>复&nbsp;测&nbsp;丝</b></p></c:when>
										</c:choose>
									</td>
									<td>${bwList.get(i).createDate }</td>
								</tr>
							</c:forEach>
						</tbody>
					</c:when>
				</c:choose>
			</table>
		</div>
		
		<div class="ta-foot">
			<a class="btn btn-xs btn-danger delete">
				<span class="glyphicon glyphicon-trash"></span> 删 除
			</a>
		</div>
		
	</div>
</body>
<script type="text/javascript">

$("#txt_calendar").jeDate({
  format: "YYYY-MM-DD"
});	

$(function(){
	//设置选中的测试项目
	var testValue = $('#testType').val();
	//将选择的测试类别显示起来
	$('.ta_body table').each(function(j){
		if (testValue == j) {
			$(this).removeAttr("hidden");
		}
	});
	
	//如果数据只有一行，则说明是没有数据
	if ($('.ocOperate').length == 1) {
		$('.ocOperate').attr("hidden", true);
	}
	if ($('.seOperate').length == 1) {
		$('.seOperate').attr("hidden", true);
	}
	if ($('.pcOperate').length == 1) {
		$('.pcOperate').attr("hidden", true);
	}
	if ($('.bwOperate').length == 1) {
		$('.bwOperate').attr("hidden", true);
	}
	
	$('.ocOperate').click(function() {
		var currentObj = $(this);
		operateTr("warning", currentObj);
	});
	$('.seOperate').click(function() {
		var currentObj = $(this);
		operateTr("success", currentObj);
	});
	$('.pcOperate').click(function() {
		var currentObj = $(this);
		operateTr("info", currentObj);
	});
	$('.bwOperate').click(function() {
		var currentObj = $(this);
		operateTr("danger", currentObj);
	});
	
	
	//页面中日常取样丝的个数
	var totalLine = 0;
	
	if (testValue == 0) {
		$('.ocOperate').each(function(i) {
			if ($(this).find('p').text().trim() === "日常取样丝") {
				console.log($(this).find('p').text().trim());
				totalLine++;
				console.log(totalLine);
			};
		});
	} else if (testValue == 1) {
		$('.seOperate').each(function(i) {
			if ($(this).find('p').text().trim() === "日常取样丝") {
				console.log($(this).find('p').text().trim());
				totalLine++;
				console.log(totalLine);
			};
		});
	} else if (testValue == 2) {
		$('.pcOperate').each(function(i) {
			if ($(this).find('p').text().trim() === "日常取样丝") {
				console.log($(this).find('p').text().trim());
				totalLine++;
				console.log(totalLine);
			};
		});
	} else if (testValue == 3) {
		$('.bwOperate').each(function(i) {
			if ($(this).find('p').text().trim() === "日常取样丝") {
				console.log($(this).find('p').text().trim());
				totalLine++;
				console.log(totalLine);
			};
		});
	}
	
	//删除请求
	$('.delete').click(function() {
		var indexArr = new Array();
		var t = confirm('确定要删除吗？');
		$('.checked:checked').each(function(i) {
			indexArr.push($(this).val()-1);
		});

		if ($('.checked:not(:checked)').length != totalLine) {
			layer.alert("请选全要删除的数据", {
				icon: 2,
				skin: 'layui-layer-lan',//样式类名
				closeBtn: 1
				}
			);
		} else {
			$(this).addClass("hidden");
			if (t) {
				$.ajax({
					type : "get",
					url : "deleteData?testValue="+testValue,
					data: {"indexList" : JSON.stringify(indexArr)},
					dataType: "json",
					success : function(data) {
						layer.alert("删除数据成功", {
									icon : 1,
									skin : 'layui-layer-molv',// 样式类名
									closeBtn : 0
								});
					},
					error : function(e) {
						layer.alert("删除数据失败", {
									icon : 2,
									skin : 'layui-layer-lan',// 样式类名
									closeBtn : 1
								});
					}
				});
			}
		}
	});
	
	//查询请求
	$('#search').click(function(){
		var createDate = $('#txt_calendar').val();
		var testType = $('.selectpicker').val();
		if (createDate != "") {
			$(this).attr('href', 'testAgainSearch?createDate='+createDate+'&testType='+testType);
		} else if(createDate == ""){
			layer.alert("请选择日期", {
				icon: 5,
				skin: 'layui-layer-lan',//样式类名
				closeBtn: 1
				}
			);
		}
	});
	
	//设置页面表格状态
	function operateTr(color, currentObj) {
		var first = currentObj.children().first().children().first();
		var prev = currentObj.prev().children().first().children().first();
		var next = currentObj.next().children().first().children().first();
		
		//console.log(first.val());
		if (first.is(":checked")) {
			currentObj.addClass(color);
			currentObj.removeClass("active");
			first.removeAttr("checked");
		} else {
			currentObj.addClass("active");
			currentObj.removeClass(color);
			first.attr("checked", true);
		}
	}
});
</script>
</html>