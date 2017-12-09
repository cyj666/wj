<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link href="<%=ctx %>/static/css/iconfont.css" rel="stylesheet" type="text/css"/>
<link href="<%=ctx %>/static/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
<link href="<%=ctx %>/static/skin/default/layer.css" rel="stylesheet" type="text/css">
<link href="<%=ctx %>/static/css/bootstrap.min.css" rel="stylesheet" type="text/css" >

<script type="text/javascript" src="<%=ctx %>/static/js/jquery.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/layer.js"></script>
<style type="text/css">
body {
	background-color: #D1EEEE
}
.index_header {
	width: auto;
	height: 60px;
}

.index_header {
	line-height: 60px;
}

.content {
	background-color: #98FB98;
}

div.modal-body {
	padding-bottom: 0;
}
div.modal-footer {
	padding-top: 5px;
	padding-bottom: 5px;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-sm-1">
				<a class="btn btn-sm btn-link" href="checkMain"><b>返回首页</b></a>
			</div>
			<div class="col-sm-11 text-success">
				<h3 style="text-align: center;margin:0 auto">检 测 指 标</h3>
			</div>
		</div>
		<div class="index_head">
			<div>
				<button type="button" class="btn btn-sm btn-success" data-target="#ajax"
					data-toggle="modal">
					<span class="glyphicon glyphicon-plus"></span> 添加
				</button>
				<button type="button" class="btn btn-sm btn-success" data-target="#change"
					data-toggle="modal">
					<span class="glyphicon glyphicon-wrench"></span> 修改
				</button>
				<input type="text" value="1" hidden="hidden"/>
			</div>
		</div>
		
		<!-- 添加指标的弹出框 -->
		<div class="modal fade" id="ajax" role="basic" aria-hidden="true" data-backdrop="static">
			<div class="modal-dialog modal-md">
				<div class="content">
					<div class="modal-header">
						<h4 class="modal-title"><b>添加指标</b></h4>
					</div>

					<div class="modal-body">
						<form action="#" role="form">
							<div class="form-group form-group-sm">
								<label for="batchNo">批&nbsp;&nbsp;&nbsp;&nbsp;号:</label>&nbsp;&nbsp;
								<input type="text" class="indexValue" id="batchNo"/>
								<span id="batchNoErr" class="text-danger"></span>
							</div>
							<div class="form-group form-group-sm">
								<label for="indexName">指标名:</label>&nbsp;&nbsp;
								<select id="indexName">
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
							<div class="form-group form-group-sm">
								<label for="standardValue">标准值:</label>&nbsp;&nbsp;
								<input type="text" class="indexValue" id="standardValue"/>
								<span id="standardValueErr" class="text-danger"></span>
							</div>
							<div class="form-group form-group-sm">
								<label for="deviationValue">偏差值:</label>&nbsp;&nbsp;
								<input type="text" class="indexValue" id="deviationValue"/>
								<span id="deviationValueErr" class="text-danger"></span>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<span id="msg" class="pull-left"></span>
						<button type="button" class="btn btn-sm btn-success" id="submit">添加</button>
						<button type="button" class="btn btn-sm btn-success" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 修改指标的弹出框 -->
		<div class="modal fade" id="change" role="basic" aria-hidden="true" data-backdrop="static">
			<div class="modal-dialog modal-md">
				<div class="content">
					<div class="modal-header">
						<h4 class="modal-title"><b>修改指标</b></h4>
					</div>

					<div class="modal-body">
						<div id="deleteOption">
							<button type="button" class="btn btn-sm btn-success" id="searchByBatchNo">
								<span class="glyphicon glyphicon-search"></span> 查询
							</button>
							<select class="allIndexName">
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
							<select class="allBatchNo">
								<%-- <option value="" data-content="<span class='label label-warning'>DAS12345678</span>"></option> --%>
							</select>
						</div>
						<table class="table table-responsive table-bordered table-condensed">
							<thead>
								<tr>
									<th class="text-center" style="width: 25%">批号</th>
									<th class="text-center" style="width: 25%">指标</th>
									<th class="text-center" style="width: 25%">标准值</th>
									<th class="text-center" style="width: 25%">偏差值</th>
								</tr>
							</thead>
							<tbody class="changeIndex">
								<tr class="text-center changeData" hidden="hidden">
									<td></td>
									<td></td>
									<td><input type="text" class="form-control input-sm"/></td>
									<td><input type="text" class="form-control input-sm"/></td>
									<td class="hidden"></td>
								</tr>
							</tbody>
						</table>
						<span class="backData"></span>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-sm btn-success" id="update">修改</button>
						<button type="button" class="btn btn-sm btn-success" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
$(function(){
	$('#batchNo').blur(function(){
		var reg = /^[A-Z]{3}[A-Z0-9]{8}$/;;
		var batchNo = $(this).val();
		if (!reg.test(batchNo)) {
			$('#batchNoErr').text("批号格式不对!");
		}else {
			$('#batchNoErr').text("");
		}
	});
	/*验证标准值数据*/
	$('#standardValue').blur(function(){
		var reg = /^[0-9]{1,3}(\.[0-9]{1,2}){0,1}$/;
		var standardValue = $(this).val();
		if (!reg.test(standardValue)) {
			$('#standardValueErr').text("标准值格式不对!");
		} else {
			$('#standardValueErr').text("");
		}
	});
	/*修改时的验证*/
	$('#changedStandardValue').blur(function() {
		var reg = /^[0-9]{1,3}(\.[0-9]{1,2}){0,1}$/;
		var standardValue = $(this).val();
		if (!reg.test(standardValue)) {
			alert("标准值格式不对!");
		}
	});
	/*验证偏差值数据*/
	$('#deviationValue').blur(function(){
		var reg = /^[0-9]{1,3}(\.[0-9]{1,2})?$/;
		var deviationValue = $(this).val();
		console.log(deviationValue);
		
		if (!reg.test(deviationValue)) {
			$('#deviationValueErr').text("偏差值格式不对!");
		} else {
			$('#deviationValueErr').text("");
		}
	});
	/*修改时偏差值验证*/
	$('.changeData td:eq(3)').find('input').blur(function() {
		var reg = /^[0-9]{1,3}(\.[0-9]{1,2}){0,1}$/;
		var standardValue = $(this).val();
		//console.log(1111);
		if (!reg.test(standardValue)) {
			alert("偏差值格式不对!");
		}
	});
	$('#submit').click(function(){
		var indexArr = new Array();
		$('.indexValue').each(function(i){
			indexArr.push($(this).val());
		});
		indexArr.push($('#indexName').val());
		
		switch("") {
		case indexArr[0]:
			$('#batchNoErr').text("批号不能为空!");
		case indexArr[1]:
			$('#standardValueErr').text("标准值不能为空!");
		case indexArr[2]:
			$('#deviationValueErr').text("偏差值不能为空!");
			break;
		default:
			$.get("addIndexValue", {"indexValueList" : JSON.stringify(indexArr)}, function(data){
				if (data.addStatus == 1) {
					layer.alert("添加成功", {
						icon: 1,
						skin: 'layui-layer-molv',//样式类名
						closeBtn: 1
						}
					);
				} else if (data.addStatus == 0) {
					layer.alert("已存在，添加失败", {
						icon: 2,
						skin: 'layui-layer-lan',//样式类名
						closeBtn: 1
						}
					);
				}
			});
			break;
		}
	});
	
	$('[data-target="#change"]').click(function(){
		//$('.changeIndex').empty();
		$('.backData').html("");
		var i = $(this).next().val();
		console.log(i);
		if (i == 1) {
			$.get('findBatchNo',function(batchNoList){
				for (var i = 0; i < batchNoList.length; i++) {
					$('.allBatchNo').append('<option value="'+batchNoList[i]+'">'+batchNoList[i]+'</option>');
					//将多添加的元素删除掉， ？不知道为什么会出现这种情况
					//$('.allBatchNo').next().remove();
				};
				//$('.allBatchNo').selectpicker('refresh');
			});
		} 
		i++;
		$(this).next().val(i);
	});
	
	$('#searchByBatchNo').click(function() {
		var batchNo = $('.allBatchNo :selected').val();
		//console.log(batchNo);
		var indexName = $('.allIndexName :selected').val();
		//console.log(indexName);
		$.get('findCheckIndex?&batchNo='+batchNo+'&indexName='+indexName, function(checkIndex){
			//console.log(typeof(checkIndex));
			$('.backData').html("");
			$('.changeData').hide();
			if (checkIndex == "") {
				$('.backData').addClass('text-danger').html('<h4><b>没有查询的数据</b></h4>');
			} else {
				var indexName;
				switch(checkIndex.checkIndex) {
				case "linear_density":
					indexName = "纤度";
					break;
				case "density_percent":
					indexName = "D%";
					break;
				case "ld_CV":
					indexName = "纤度CV%";
					break;
				case "strength":
					indexName = "强度";
					break;
				case "strength_CV":
					indexName = "强度CV%";
					break;
				case "elongation":
					indexName = "伸长";
					break;
				case "elongation_CV":
					indexName = "伸长CV%";
					break;
				case "yarnlevelness_U":
					indexName = "条干%";
					break;
				case "boiling_water":
					indexName = "沸水%";
					break;
				case "crimp_contraction":
					indexName = "卷曲收缩率%";
					break;
				case "crimp_stability":
					indexName = "卷曲稳定度%";
					break;
				case "water_ratio":
					indexName = "含水率%";
					break;
				case "oli_content":
					indexName = "含油率%";
					break;
				case "grid_line":
					indexName = "网络度";
					break;
				default:
					indexName = "无对应指标名";
					break;
				}
				$('.changeData').show();
				$('.changeData td:eq(0)').text(checkIndex.batchNo);
				$('.changeData td:eq(1)').text(indexName);
				$('.changeData td:eq(2) input').val(checkIndex.standardValue);
				$('.changeData td:eq(3) input').val(checkIndex.deviationValue);
				$('.changeData td:eq(4)').text(checkIndex.checkIndex);
				
				$('.changeIndex tr td').css("line-height","31px");
			}
		});
	});
	
	//console.log($('.changeData td:eq(3)').html());

	$('#update').click(function() {
		var batchNo; var indexName;
		var standardValue; var deviationValue;
		var dataArr = new Array();
		$('.changeData').children().each(function(i){
			switch(i) {
				case 0 :
					batchNo = $(this).text();
					dataArr.push(batchNo);
					break;
				case 4:
					indexName = $(this).text();
					dataArr.push(indexName);
					break;
				case 2:
					standardValue = $(this).find('input').val();
					dataArr.push(standardValue);
					break;
				case 3:
					deviationValue = $(this).find('input').val();
					dataArr.push(deviationValue);
					break;
				default:
					break;
			}
		});
		//console.log(batchNo+","+indexName+","+standardValue+","+deviationValue);
		$.ajax({
			type : "get",
			url : "changeIndexData",
			data: {"dataList" : JSON.stringify(dataArr)},
			dataType: "json",
			success : function(data) {
				$('.changeData').hide();
				layer.alert("修改数据成功", {
							icon : 1,
							skin : 'layui-layer-molv',// 样式类名
							closeBtn : 0
						});
			},
			error : function(e) {
				$('.changeData').hide();
				layer.alert("修改数据失败", {
							icon : 2,
							skin : 'layui-layer-lan',// 样式类名
							closeBtn : 1
						});
			}
		});
	});
});
</script>
</html>