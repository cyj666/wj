<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link href="<%=ctx %>/static/css/iconfont.css" rel="stylesheet" type="text/css"/>
<link href="<%=ctx %>/static/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
<link href="<%=ctx %>/static/skin/default/layer.css" rel="stylesheet" type="text/css">
<link href="<%=ctx %>/static/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
<link href="<%=ctx %>/static/css/fileUpload.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=ctx %>/static/js/map-util.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/jquery.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/fileUpload.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/layer.js"></script>
<style type="text/css">
body {
	background-color: #D1EEEE
}
.bw_header {
	width: auto;
	height: 60px;
}

.bw_header {
	line-height: 60px;
}

div.content {
	background-color: #CAE1FF;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-1">
				<a class="btn btn-sm btn-link" href="main"><b>首页</b></a>
			</div>
			<div class="col-sm-11 text-danger">
				<h3 style="text-align: center;margin:0 auto"><b>测 量 沸 水</b></h3>
			</div>
		</div>
	
	
		<div class="bw_header">
			<button class="btn btn-sm btn-danger" data-target="#ajax"
				data-toggle="modal">
				<span class="glyphicon glyphicon-upload"></span> 导入
			</button>
			<a type="button" class="btn btn-sm btn-danger" id="readData">
				<span class="glyphicon glyphicon-floppy-open"></span> 读取文件
			</a>
		</div>


		<!-- 导入数据的一个窗口 -->
		<div class="modal fade" id="ajax" role="basic" aria-hidden="true" data-backdrop="static">
			<div class="modal-dialog modal-lg">
				<div class="content">
					<div class="modal-header">
						<!-- <button type="button" class="close" data-dismiss="modal"
							aria-hidden="true"></button> -->
						<h4 class="modal-title">数据处理</h4>
					</div>

					<div class="modal-body">

						<!-- 文件上传 -->
						<div id="fileUploadContent" class="fileUploadContent"></div>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal" id="close">关闭</button>
					</div>
				</div>
			</div>
		</div>

		<!-- 反馈信息的窗口 -->
		<div class="modal fade" id="backMsg" role="basic" aria-hidden="true" <c:if test="${status=='show' }">data-backdrop="static"</c:if> 
			>
			<div class="modal-dialog modal-md" style="width:20%">
				<div class="content bm">
					<div class="modal-header" style="padding:0">
						<h4 class="">提示</h4>
					</div>

					<div class="modal-body text-center">
						<h4 class='<c:choose>
									<c:when test="${msg==3}">text-success</c:when>
									<c:otherwise>text-danger</c:otherwise>
							</c:choose>' style="display:inline">
							<c:choose>
								<c:when test="${msg==1}">
								<span class="glyphicon glyphicon-remove danger"></span>上传文件的格式不对</c:when>
								<c:when test="${msg==2}">
								<span class="glyphicon glyphicon-remove danger"></span>没有文件可读</c:when>
								<c:when test="${msg==3}">
								<span class="glyphicon glyphicon-ok success"></span>数据读取成功</c:when>
							</c:choose>
						</h4>
					</div>
					<div class="modal-footer" style="padding:0">
						<button type="button" class="btn btn-warning btn-sm" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>

		<!-- 显示表格 -->
		<div class="se_body">
			<table class="table table-responsive table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="text-center" style="width:5%">序 号</th>
						<th class="text-center" style="width:15%">批号</th>
						<th class="text-center" style="width:15%">线位号</th>
						<th class="text-center" style="width:10%">沸水</th>
						<th class="text-center" style="width:15%">状态</th>
						<th class="text-center" style="width:20%">测量时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="i" begin="${begin}" end="${end}">
						<c:if test="${pages!=null}">
							<tr class="danger text-center operate">
								<td><span class="text-primary">${i+1}</span></td>
								<td>${bwList.get(i).batchNo}</td>
								<td>${bwList.get(i).lineNo }</td>
								<td><fmt:formatNumber type="number" value="${bwList.get(i).boilingWater/10.0 }" pattern="0.0" maxFractionDigits="1" /></td>
								<td>
									<c:choose>
										<c:when test="${bwList.get(i).bwStatus==0 }"><p class="text-success" style="margin: 0"><b>日常取样丝</b></p></c:when>
									</c:choose>
									<c:choose>
										<c:when test="${bwList.get(i).bwStatus>10 && bwList.get(i).bwStatus<20 }"><p class="text-info" style="margin: 0"><b>复&nbsp;测&nbsp;丝</b></p></c:when>
									</c:choose>
									<c:choose>
										<c:when test="${bwList.get(i).bwStatus>20 && bwList.get(i).bwStatus<30 }"><p class="text-primary" style="margin: 0"><b>开纺&nbsp;/&nbsp;改纺</b></p></c:when>
									</c:choose>
									<c:choose>
										<c:when test="${bwList.get(i).bwStatus>30 && bwList.get(i).bwStatus<40 }"><p class="text-warning" style="margin: 0"><b>车&nbsp;间&nbsp;加&nbsp;测</b></p></c:when>
									</c:choose>
									<c:choose>
										<c:when test="${bwList.get(i).bwStatus>40 && bwList.get(i).bwStatus<50 }"><p class="text-danger" style="margin: 0"><b>异&nbsp;常&nbsp;丝</b></p></c:when>
									</c:choose>
								</td>
								<td>${bwList.get(i).createDate }</td>
							</tr>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<div class="se_footer row">
			<div class="col-md-3">
				当前第<b>${pages}</b>页，总共<b>${page.getTotalPage()}</b>页，总共<b>${page.getTotalCount()}</b>条记录
			</div>
			<!-- 分页 -->
			<div class="col-md-6 text-center">
		        <ul class="pagination pagination-sm">
		        <c:if test="${pages!=null}">
					<li><a href="/bwRead?pages=${pages-1}" title="上一页">&laquo;</a></li>
				</c:if>
					<c:choose>
						<c:when test="${page.getTotalPage()>10}">
							<c:if test="${pages>5&&pages<=page.getTotalPage()-5}">
								<c:forEach var="i" begin="${pages-4}" end="${pages+5}">
								<c:if test="${i==pages }">
								<li class="page active"><a href="/bwRead?pages=${i}">${i}</a></li>
								</c:if>
								<c:if test="${i!=pages }">
									<li class="page"><a href="/bwRead?pages=${i}">${i}</a></li>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${pages<=5}">
								<c:forEach var="i" begin="1" end="10">
									<c:if test="${i==pages }">
								<li class="page active"><a href="/bwRead?pages=${i}">${i}</a></li>
								</c:if>
								<c:if test="${i!=pages }">
									<li class="page"><a href="/bwRead?pages=${i}">${i}</a></li>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${pages>page.getTotalPage()-5}">
								<c:forEach var="i" begin="${page.getTotalPage()-9}"
									end="${page.getTotalPage()}">
									<c:if test="${i==pages }">
								<li class="page active"><a href="/bwRead?pages=${i}">${i}</a></li>
								</c:if>
								<c:if test="${i!=pages }">
									<li class="page"><a href="/bwRead?pages=${i}">${i}</a></li>
									</c:if>
								</c:forEach>
							</c:if>
						</c:when>
						<c:when test="${page.getTotalPage()<20&&page.getTotalPage()>0}">
							<c:forEach var="i" begin="1" end="${page.getTotalPage()}">
								<c:if test="${i==pages }">
								<li class="page active"><a href="/bwRead?pages=${i}">${i}</a></li>
								</c:if>
								<c:if test="${i!=pages }">
									<li class="page"><a href="/bwRead?pages=${i}">${i}</a></li>
									</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<span class="text-danger">暂无数据，请导入</span>
						</c:otherwise>
					</c:choose>
					<c:if test="${pages!=null}">
					<li class="pages"><a href="/bwRead?pages=${pages+1}" title="下一页">&raquo;</a></li>
					</c:if>
				</ul>
			</div>
			<div class="col-md-3">
				<button type="button" class="btn btn-primary btn-sm hidden saveData" style="float:right;margin-left:10px;">
					<span class="glyphicon glyphicon-floppy-saved"></span> 保存
				</button>
			</div>
		</div>
		
	</div>	
</body>
<script type="text/javascript">
$("#fileUploadContent").initUpload({
	"uploadUrl" : "bw/bwUpFile",// 上传文件信息地址
	"progressUrl" : "bw/getPercent",// 获取进度信息地址，可选，注意需要返回的data格式如下（{bytesRead:
									// 102516060, contentLength: 102516060,
									// items: 1, percent: 100, startTime:
									// 1489223136317, useTime: 2767}）
	// "showSummerProgress":false,//总进度条，默认限制
	// "size":350,//文件大小限制，单位kb,默认不限制
	// "maxFileNumber":15,//文件个数限制，为整数
	"filelSavePath" : "E:/project/bw/",// 文件上传地址，后台设置的根目录
	// "beforeUpload":beforeUploadFun,//在上传前执行的函数
	"onUpload" : onUploadFun,// 在上传后执行的函数
	// autoCommit:true,//文件是否自动上传
	"fileType" : ['dat']
		// 文件类型限制，默认不限制，注意写的是文件后缀
	});
	
/*
 * function beforeUploadFun(opt) { opt.otherData = [ { "name" : "你要上传的参数",
 * "value" : "你要上传的值" } ]; };
 */
function onUploadFun(opt, data) {
	if ("文件已存在" == data.msg) {
		uploadTools.uploadError(opt);// 显示上传错误
		// alert(data.e);
		layer.alert(data.e, {
					icon : 2,
					skin : 'layui-layer-lan',// 样式类名
					closeBtn : 1
				});
	} else if ("文件上传成功" == data.msg) {
		// alert("文件上传成功");
		layer.alert(data.msg, {
					icon : 1,
					skin : 'layui-layer-molv',// 样式类名
					closeBtn : 1
				});
	};
};


$(function(){
	// 读取数据
	$('#readData').click(function() {
		$(this).attr("href", "bwRead");
	});
	
	// 弹出成功modal
	if ($('#backMsg').attr("data-backdrop") == "static") {
		$('#backMsg').modal("show");
	};
	
//	if ($(".operate").length == 0) {
//		$(".saveData").attr("disabled", true);
//	};
	
	//设置保存按钮只有在页码是1时才显示
	if ($('.page.active a').text() == 1) {
		$('.saveData').removeClass("hidden");
	}
	
	// 点击保存按钮
	$('.saveData').click(function() {
		$.ajax({
			type : "get",
			url : "bwSaveData",
			processData : false, // 告诉jQuery不要处理发送的数据
			contentType : false, // 告诉jQuery去设置Content-Type请求头
			success : function(data) {
				layer.alert(data.msg, {
							icon : parseInt(data.id),
							skin : data.style,// 样式类名
							closeBtn : 0
						});
			},
			error : function(e) {
				layer.alert("数据重复保存", {
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