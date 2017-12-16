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
.se_header {
	width: auto;
	height: 60px;
}

.se_header {
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
			<div class="col-sm-11 text-warning">
				<h3 style="text-align: center;margin:0 auto">测 量 强 度 和 伸 长 率</h3>
			</div>
		</div>
	
	
		<div class="se_header">
			<button class="btn btn-sm btn-warning" data-target="#ajax"
				data-toggle="modal">
				<span class="glyphicon glyphicon-upload"></span> 导入
			</button>
			<a type="button" class="btn btn-sm btn-warning" id="readData">
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
								<span class="glyphicon glyphicon-remove danger"></span>没有上传文件或者读取文件过程中有错</c:when>
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
					<tr class="success">
						<th class="text-center" style="width:5%">序 号</th>
						<th class="text-center" style="width:15%">批号</th>
						<th class="text-center" style="width:15%">线位号</th>
						<th class="text-center" style="width:10%">规格</th>
						<th class="text-center" style="width:10%">强度</th>
						<th class="text-center" style="width:10%">伸长率</th>
						<th class="text-center" style="width:15%">状态</th>
						<th class="text-center" style="width:20%">测量时间</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="i" begin="${begin}" end="${end}">
						<c:if test="${pages!=null}">
							<tr class="warning text-center operate">
								<td><span class="text-primary">${i+1}</span></td>
								<td>${seList.get(i).batchNo}</td>
								<td>${seList.get(i).lineNo }</td>
								<td>${seList.get(i).seFormat }</td>
								<td><fmt:formatNumber type="number" value="${seList.get(i).strength/100.0 }" pattern="0.00" maxFractionDigits="2" /></td>
								<!-- <fmt:formatNumber type="number" value="${seList.get(i).strength/100.0 }" pattern="0.00" maxFractionDigits="2" /> -->
								<td><fmt:formatNumber type="number" value="${seList.get(i).elongation/10.0 }" pattern="0.0" maxFractionDigits="1" /></td>
								<!-- <fmt:formatNumber type="number" value="${seList.get(i).elongation/100.0 }" pattern="0.0" maxFractionDigits="1" /> -->
								<td>
									<c:choose>
										<c:when test="${seList.get(i).seStatus==0 }"><p class="text-success" style="margin: 0"><b>日常取样丝</b></p></c:when>
									</c:choose>
									<c:choose>
										<c:when test="${seList.get(i).seStatus>10 && seList.get(i).seStatus<20 }"><p class="text-info" style="margin: 0"><b>复&nbsp;测&nbsp;丝</b></p></c:when>
									</c:choose>
									<c:choose>
										<c:when test="${seList.get(i).seStatus>20 && seList.get(i).seStatus<30 }"><p class="text-primary" style="margin: 0"><b>开纺&nbsp;/&nbsp;改纺</b></p></c:when>
									</c:choose>
									<c:choose>
										<c:when test="${seList.get(i).seStatus>30 && seList.get(i).seStatus<40 }"><p class="text-warning" style="margin: 0"><b>车&nbsp;间&nbsp;加&nbsp;测</b></p></c:when>
									</c:choose>
									<c:choose>
										<c:when test="${seList.get(i).seStatus>40 && seList.get(i).seStatus<50 }"><p class="text-danger" style="margin: 0"><b>异&nbsp;常&nbsp;丝</b></p></c:when>
									</c:choose>
								</td>
								<td>${seList.get(i).createDate }</td>
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
					<li><a href="/seRead?pages=${pages-1}" title="上一页">&laquo;</a></li>
				</c:if>
					<c:choose>
						<c:when test="${page.getTotalPage()>10}">
							<c:if test="${pages>5&&pages<=page.getTotalPage()-5}">
								<c:forEach var="i" begin="${pages-4}" end="${pages+5}">
								<c:if test="${i==pages }">
								<li class="page active"><a href="/seRead?pages=${i}">${i}</a></li>
								</c:if>
								<c:if test="${i!=pages }">
									<li class="page"><a href="/seRead?pages=${i}">${i}</a></li>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${pages<=5}">
								<c:forEach var="i" begin="1" end="10">
									<c:if test="${i==pages }">
								<li class="page active"><a href="/seRead?pages=${i}">${i}</a></li>
								</c:if>
								<c:if test="${i!=pages }">
									<li class="page"><a href="/seRead?pages=${i}">${i}</a></li>
									</c:if>
								</c:forEach>
							</c:if>
							<c:if test="${pages>page.getTotalPage()-5}">
								<c:forEach var="i" begin="${page.getTotalPage()-9}"
									end="${page.getTotalPage()}">
									<c:if test="${i==pages }">
								<li class="page active"><a href="/seRead?pages=${i}">${i}</a></li>
								</c:if>
								<c:if test="${i!=pages }">
									<li class="page"><a href="/seRead?pages=${i}">${i}</a></li>
									</c:if>
								</c:forEach>
							</c:if>
						</c:when>
						<c:when test="${page.getTotalPage()<20&&page.getTotalPage()>0}">
							<c:forEach var="i" begin="1" end="${page.getTotalPage()}">
								<c:if test="${i==pages }">
								<li class="page active"><a href="/seRead?pages=${i}">${i}</a></li>
								</c:if>
								<c:if test="${i!=pages }">
									<li class="page"><a href="/seRead?pages=${i}">${i}</a></li>
									</c:if>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<span class="text-danger">暂无数据，请导入</span>
						</c:otherwise>
					</c:choose>
					<c:if test="${pages!=null}">
					<li class="pages"><a href="/seRead?pages=${pages+1}" title="下一页">&raquo;</a></li>
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
<script type="text/javascript" src="<%=ctx %>/static/js/seData.js"></script>
</html>