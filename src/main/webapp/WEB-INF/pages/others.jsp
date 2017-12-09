<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  
<%
	String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
<script type="text/javascript">
	var list = "${list}";
</script>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title></title>
<link href="<%=ctx%>/static/img/life.ico" rel="shortcut icon">
<link href="<%=ctx%>/static/css/iconfont.css" rel="stylesheet" type="text/css" />
<link href="<%=ctx%>/static/css/bootstrap-select.min.css" rel="stylesheet" type="text/css">
<link href="<%=ctx%>/static/skin/default/layer.css" rel="stylesheet" type="text/css">
<link href="<%=ctx%>/static/css/bootstrap.min.css" rel="stylesheet" type="text/css">
<link href="<%=ctx%>/static/css/fileUpload.css" rel="stylesheet" type="text/css">
<link href="<%=ctx %>/static/css/jedate.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=ctx%>/static/js/map-util.js"></script>
<script type="text/javascript" src="<%=ctx%>/static/js/jquery.js"></script>
<script type="text/javascript" src="<%=ctx%>/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=ctx%>/static/js/fileUpload.js"></script>
<script type="text/javascript" src="<%=ctx%>/static/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="<%=ctx%>/static/layer.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/jquery.jedate.js"></script>
</head>
<body>
	<div class="container-fluid">
		<div class="text-info" style="height: 50px; line-height: 50px">
			<h4 style="text-align: center; margin: 0 auto">测 量 线 密 度、条 干 和 网
				络 线</h4>
		</div>

	<div class="ot_header">
			<a type="button" class="btn btn-md btn-success" id="readData">
				<span class="glyphicon glyphicon-floppy-open"></span> 读取文件
			</a>
			<a type="button" class="btn btn-md btn-success" id="checkData">
				<span class="glyphicon glyphicon-search"></span> 查询
			</a>
			<input type="text" id="txt_calendar" class="jeinput" placeholder="YYYY-MM-DD" style="height:21px;width:173px"/>
			
		</div>

		<!-- 显示表格 -->
		<div class="se_body">
			<table
				class="table table-responsive table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width: 3%">序 号</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width: 8%">测量时间</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width: 8%">生产时间</th>	
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width: 5%">批号</th>	
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width: 5%">线位号</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width: 5%">线密度</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width: 5%">D%</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width: 5%">CV%</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width: 5%">条干U%</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width: 5%">含水率</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width: 5%">状态</th>	
						<th colspan="2" class="text-center" style="vertical-align: middle !important;width: 8%">网络</th>														
						<tr class="info">										
							<th class="text-center">平均</th>
							<th class="text-center">范围</th>				
						</tr>
					</tr>
					
				</thead>
				<tbody class="addDate-show">
				
					<c:choose>
						<c:when test="${list.size()==0||list==null}">
							<h4 class="text-danger">无数据可读，请选择日期！</h4>
						</c:when>
						<c:otherwise>
							<c:forEach var="i" begin="0" end="${list.size()-1}">
								<tr class="success text-center operate">
									<!-- 序号 -->
									<td style="line-height: 34px"><span class="text-primary">${i+1}</span></td>	
									<!-- 测量时间 -->							
									<td style="line-height: 34px"><span class="hidden">${list.get(i).createDate}</span><span>${fn:substring(list.get(i).createDate,0, 10)}</span></td>
									
									<!-- 生产时间 -->										
									<td style="line-height: 34px">		
										<!-- 截取字符串使得时间显示到分钟，而不是到秒 -->	
										<input type="text"
										class="form-control input-sm productionDate"
										value="${fn:substring(list.get(i).productionDate, 0, 16)}">
									</td>
									
									
									<!-- 批号 -->	
									<td style="line-height: 34px">${list.get(i).batchNo}</td>
									<!-- 线位号 -->	
									<td style="line-height: 34px">${list.get(i).lineNo}</td>
								
									<!-- 线密度 -->	
									
									<td style="line-height: 34px"><input type="text"
										class="form-control input-sm linearDensity"
										value="${list.get(i).linearDensity}"></td>
									
									
									<!-- D% -->
									
									<td style="line-height: 34px"><input type="text"
										class="form-control input-sm d"
										value="${list.get(i).densityPercent}"></td>
									
									
									<!-- CV% -->	
									
									<td style="line-height: 34px"><input type="text"
										class="form-control input-sm cv"
										value="${list.get(i).ldCV}"></td>
									
									
									<!-- 条干U% -->
									
									<td style="line-height: 34px"><input type="text"
										class="form-control input-sm yarnlevelness"
										value="${list.get(i).yarnlevelness}"></td>
									
									
									<!-- 含水率 -->
									
									<td style="line-height: 34px"><input type="text"
										class="form-control input-sm waterRatio"
										value="${list.get(i).waterRatio}"></td>
									
											
									<!-- 状态 -->
									<td style="line-height: 34px">
									<c:if test="${fn:contains(fn:substring(list.get(i).otStatus, 0, 1),'0')}">
									<span class="text-success"><b>正常</b></span>
									</c:if>
									<c:if test="${fn:contains(fn:substring(list.get(i).otStatus, 0, 1),'1')}">
									<span class="text-primary"><b>复测</b></span>
									</c:if>
									<c:if test="${fn:contains(fn:substring(list.get(i).otStatus, 0, 1),'2')}">
									<span class="text-primary"><b>研究加测</b></span>
									</c:if>
									<c:if test="${fn:contains(fn:substring(list.get(i).otStatus, 0, 1),'3')}">
									<span class="text-warning"><b>车间加测</b></span>
									</c:if>
									<c:if test="${fn:contains(fn:substring(list.get(i).otStatus, 0, 1),'4')}">
									<span class="text-danger"><b>异常跟踪 </b></span>
									</c:if>
									</td>
									
									<!-- 状态 -->			
									<!--<td style="line-height: 34px">${list.get(i).otStatus}</td>-->
									
										
									<!-- 网络（平均） -->
									
									<td style="line-height: 34px"><input type="text"
										class="form-control input-sm gridLine"
										value="${list.get(i).gridLine}"></td>
									
									
									<!-- 网络（范围） -->
									<td style="line-height: 34px">
									<input type="text"
										class="form-control input-sm ranges"
										value="${list.get(i).ranges}"></td>
								</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>

				</tbody>
			</table>
			<!-- 暂时将手动添加和删除功能隐藏 -->
			<!--  
			<a type="button" class="btn btn-primary btn-sm addDate" href="#"
				style="margin-left: 10px;"> <span
				class="glyphicon glyphicon-plus"></span> 手动添加
			</a> <a type="button" class="btn btn-primary btn-sm deleteDate" href="#"
				style="margin-left: 10px;"> <span
				class="glyphicon glyphicon-minus"></span> 手动删除
			</a>
			-->
		</div>

		<div class="se_footer row">
			<a type="button" class="btn btn-primary btn-sm saveData" href="#"
				style="float: right; margin-left: 10px;"> <span
				class="glyphicon glyphicon-floppy-saved"></span> 保存
			</a>
			<button type="button" class="btn btn-success btn-sm confirmData "
				style="float: right">
				<span class="glyphicon glyphicon-check"></span> 确认
			</button>
		</div>

	</div>
</body>
<script type="text/javascript">
	/**
	 * 正则表达式
	 */
	function zhengze() {
		/**
		 *批号的正则表达及判断
		 */
		$(".batchNo").each(function(i) {
			var reg = /^[A-Z]{3}[0-9A-Z]{8}$/;
			var length = $(".batchNo").length;
			if (i <= length) {
				$(this).blur(function() {
					if (!reg.test($(this).val())) {

						layer.alert("批号格式不匹配，请重新输入！", {
							icon : 7,
							skin : 'layui-layer-lan',//样式类名
							closeBtn : 1
						});
						$(this).val(null);
					}
				});
			}
		});

		/**
		 *线位号的正则表达及判断
		 */
		$(".lineNo").each(
				function(i) {
					var reg1 = /^[A-Z]{1}[0-9]{3}$/;
					var reg2 = /^[0-9]{2}[A-Z]{1}$/;
					var length = $(".lineNo").length;
					if (i <= length) {
						$(this).blur(function() {
							if (!reg1.test($(this).val())
									&& !reg2.test($(this).val())) {

								layer.alert("线位号格式不匹配，请重新输入！", {
									icon : 7,
									skin : 'layui-layer-lan',//样式类名
									closeBtn : 1
								});
								$(this).val(null);
							}
						});
					}
				});

		/**
		 *线密度的正则表达及判断
		 *数字，不允许字符，一位小数
		 */
		$(".linearDensity").each(function(i) {
			var reg = /^[0-9]{1,2}(\.[0-9]{1}){0,1}$/;
			var length = $(".linearDensity").length;
			if (i <= length) {
				$(this).blur(function() {
					if (!reg.test($(this).val())) {

						layer.alert("线密度格式不匹配，请重新输入！", {
							icon : 7,
							skin : 'layui-layer-lan',//样式类名
							closeBtn : 1
						});
						$(this).val(null);
					}
				});
			}
		});

		/**
		 *条干的正则表达及判断
		 */
		$(".yarnlevelness").each(function(i) {
			var reg = /^[0-9]{1,2}(\.[0-9]){0,2}$/;
			var length = $(".yarnlevelness").length;
			if (i <= length) {
				$(this).blur(function() {
					if (!reg.test($(this).val())) {

						layer.alert("条干格式不匹配，请重新输入！", {
							icon : 7,
							skin : 'layui-layer-lan',//样式类名
							closeBtn : 1
						});
						$(this).val(null);
					}
				});
			}
		});

		/**
		 *网络线的正则表达及判断
		 */
		$(".gridLine").each(function(i) {
			var reg = /^[0-9]{1,2}$/;
			var length = $(".gridLine").length;
			if (i <= length) {
				$(this).blur(function() {
					if (!reg.test($(this).val())) {

						layer.alert("网络线格式不匹配，请重新输入！", {
							icon : 7,
							skin : 'layui-layer-lan',//样式类名
							closeBtn : 1
						});
						$(this).val(null);
					}
				});
			}
		});
		
		/**
		 *D%的正则表达及判断
		 *数字，可有小数字 ,2位小数
		 */
		$(".d").each(function(i) {
			var reg = /^[0-9]{1,2}(\.[0-9]){0,2}$/;
			var length = $(".d").length;
			if (i <= length) {
				$(this).blur(function() {
					if (!reg.test($(this).val())) {

						layer.alert("D%格式不匹配，请重新输入！", {
							icon : 7,
							skin : 'layui-layer-lan',//样式类名
							closeBtn : 1
						});
						$(this).val(null);
					}
				});
			}
		});
		
		/**
		 *CV%的正则表达及判断
		 *数字，可有小数字 ,2位小数
		 */
		$(".cv").each(function(i) {
			var reg = /^[0-9]{1,2}(\.[0-9]){0,2}$/;
			var length = $(".cv").length;
			if (i <= length) {
				$(this).blur(function() {
					if (!reg.test($(this).val())) {

						layer.alert("CV%格式不匹配，请重新输入！", {
							icon : 7,
							skin : 'layui-layer-lan',//样式类名
							closeBtn : 1
						});
						$(this).val(null);
					}
				});
			}
		});
		
		/**
		 *含水率的正则表达及判断
		 *数字，可有小数字 ,2位小数
		 */
		$(".waterRatio").each(function(i) {
			var reg = /^[0-9]{1,2}(\.[0-9]){0,2}$/;
			var length = $(".waterRatio").length;
			if (i <= length) {
				$(this).blur(function() {
					if (!reg.test($(this).val())) {

						layer.alert("waterRatio格式不匹配，请重新输入！", {
							icon : 7,
							skin : 'layui-layer-lan',//样式类名
							closeBtn : 1
						});
						$(this).val(null);
					}
				});
			}
		});
		
		/**
		 *网络范围的正则表达及判断
		 *字符
		 */
		$(".ranges").each(function(i) {
			var reg = /^[0-9]{1,3}~{1}[0-9]{1,3}$/;
			var length = $(".waterRatio").length;
			if (i <= length) {
				$(this).blur(function() {
					if (!reg.test($(this).val())) {

						layer.alert("网络(范围)格式不匹配，请重新输入！", {
							icon : 7,
							skin : 'layui-layer-lan',//样式类名
							closeBtn : 1
						});
						$(this).val(null);
					}
				});
			}
		});
		
	}

	$(function() {
		zhengze();
		/*暂时将手动添加删除隐藏
		$('.addDate').click(function() {
			var n = $(".operate").length + 1;
			var tr = "<tr class=\"success text-center operate\">"
			+ "<td style=\"line-height: 34px\"><span class=\"text-primary\">"+ n
			+ "</span><input type=\"checkbox\" value=\""+n+"\"></td>"
			+ "<td style=\"line-height: 34px\"><input type=\"text\" class=\"form-control input-sm batchNo\"></td>"
			+ "<td style=\"line-height: 34px\"><input type=\"text\" class=\"form-control input-sm lineNo\" ></td>"
			+ "<td style=\"line-height: 34px\"><input type=\"text\" class=\"form-control input-sm linearDensity\" ></td>"
			+ "<td style=\"line-height: 34px\"><input type=\"text\" class=\"form-control input-sm yarnlevelness\" ></td>"
			+ "<td style=\"line-height: 34px\"><input type=\"text\" class=\"form-control input-sm gridLine\" ></td>"
			+ "<td style=\"line-height: 34px\"><input type=\"text\" class=\"form-control input-sm otStatus\" ></td>"
			+ "<td style=\"line-height: 34px\"><input type=\"date\" class=\"form-control input-sm createDate\"></td>"
			+ "</tr>"
			$('.addDate-show').append(tr);

			zhengze();

			});

		$('.deleteDate').click(function() {
			if (!$("input[type='checkbox']").is(':checked')) {
				alert("请先选择");
			} else {
				var t = confirm("确定删除？");

				$("input[type='checkbox']").each(function() {
					if ($(this).is(':checked')) {
						if (t) {
							$(this).parent().parent().remove();
							layer.alert("删除成功", {
								icon : 1,
								skin : 'layui-layer-molv',//样式类名
								closeBtn : 0
							});
						}				
					}

				});
			}
		});*/
		$("#txt_calendar").jeDate({
			  format: "YYYY-MM-DD"
			});
		$('.productionDate').each(function(){
			$(this).jeDate({
				  format: "YYYY-MM-DD hh:mm"
				});
		})
		
	});

	$(function() {
		/**
		*根据日期查询出未曾设置过条干，线密度，网络线的数据
		*/
		$('#readData').click(function() { 			
			var date = $(this).next().next().val();
			if(date!=""){
				alert("输入日期为："+date);
				$(this).attr("href", "readData?date="+date);
			}else{
				alert("请输入要查询的日期");
			}
		});
		
		/**
		*根据日期查询出当天所有数据
		*/
		$('#checkData').click(function() { 			
			var date = $(this).next().val();
			if(date!=""){
				alert("输入日期为："+date);
				$(this).attr("href", "checkData?date="+date);
				
			}else{
				alert("请输入要查询的日期");
			}
		});
		
		
		$('.saveData').attr("disabled", true);
		$(".confirmData").click(function() {
			/**
			*确认功能
			*/
				if ($(this).text().trim() == "确认")
				{
					$('.operate').each(function(i)
						{
							$(this).children().each(function(j) {
								if (j == 2) {
									$(this).removeClass('input').text($(this).find('input').val());
											} else if (j == 5) {
												$(this).removeClass('input').text($(this).find('input').val());
											} else if (j == 6) {
												$(this).removeClass('input').text($(this).find('input').val());
											} else if (j == 7) {
												$(this).removeClass('input').text($(this).find('input').val());
											} else if (j == 8) {$(this).removeClass('input').text($(this).find('input').val());
											} else if (j == 9) {$(this).removeClass('input').text($(this).find('input').val());
											} else if (j == 11) {$(this).removeClass('input').text($(this).find('input').val());
											}else if (j == 12) {$(this).removeClass('input').text($(this).find('input').val());
											}
											});
						});

								$(this).attr("class", "btn btn-warning btn-sm");
								$(this).html('<span class="glyphicon glyphicon-pencil"></span> 修改');
								$('.saveData').attr("disabled", false);
								/**
								*修改功能
								*/
							} else if ($(this).text().trim() == "修改") {
								$('.operate').each(function() {
									$(this).children().each(function(i) {
										if (i==2) {
											$(this).addClass('input').html(
												'<input type="text" class="form-control input-sm productionDate" value="'
												+ $(this).text()
												+ '"/>');
											}
										if (i==5) {
											$(this).addClass('input').html(
												'<input type="text" class="form-control input-sm linearDensity" value="'
												+ $(this).text()
												+ '"/>');
											}
										if (i==6) {
											$(this).addClass('input').html(
												'<input type="text" class="form-control input-sm d" value="'
												+ $(this).text()
												+ '"/>');
											}
										if (i==7) {
											$(this).addClass('input').html(
												'<input type="text" class="form-control input-sm cv" value="'
												+ $(this).text()
												+ '"/>');
											}
										if (i==8) {
											$(this).addClass('input').html(
												'<input type="text" class="form-control input-sm yarnlevelness" value="'
												+ $(this).text()
												+ '"/>');
											}
										if (i==9) {
											$(this).addClass('input').html(
												'<input type="text" class="form-control input-sm waterRatio" value="'
												+ $(this).text()
												+ '"/>');
											}
										if (i==11) {
											$(this).addClass('input').html(
												'<input type="text" class="form-control input-sm gridLine" value="'
												+ $(this).text()
												+ '"/>');
											}
										if (i==12) {
											$(this).addClass('input').html(
												'<input type="text" class="form-control input-sm ranges" value="'
												+ $(this).text()
												+ '"/>');
											}
										zhengze();
																		
									});
							});

								$(this).html('<span class="glyphicon glyphicon-check"></span> 确认');
								$(this).attr("class", "btn btn-success btn-sm");
								$('.saveData').attr("disabled", true);
							}

						});

		$('.saveData').click(function() {
			var map = getSaveValue()
			/*var otList = map[1];
			var otList2 = map[2];*/
			$.ajax({
				type : "post",
				url : "OtherUpData",
				data : JSON.stringify(map),
				processData : false, //告诉jQuery不要处理发送的数据
				contentType : false, //告诉jQuery去设置Content-Type请求头
				success : function(data) {
					layer.alert(data.msg, {
						icon : 1,
						skin : 'layui-layer-molv',//样式类名
						closeBtn : 0
					});
					$('.pagination').show();
					/*
					*保存成功后将当前表格清空，防止再次保存
					*/
					$('tr').each(function(i){
						if(i!=0){
							$(this).remove();
						}		
					});
					
				},
				error : function(e) {
					layer.msg("数据保存失败,请检查是否有误", {
						icon : 2,
						time : 10000,
						closeBtn : 2
					}, function() {
					})
					
					
				}
			});

		});
		
		

		//获取页面需要保存到数据库的值
		function getSaveValue() {
			function Other(otCreateDate,otProductionDate,otBatchNo,otLineNo,otLinearDensity,otDensityPercent,otLdCV,
					otYarnlevelness,otWaterRatio,/*otStatus,*/otGridLine,otRanges) {
				this.batchNo = otBatchNo;
				this.lineNo = otLineNo;
				this.linearDensity = otLinearDensity;
				this.yarnlevelness = otYarnlevelness;
				this.gridLine = otGridLine;
				//this.otStatus = otStatus;
				this.createDate = otCreateDate;
				
				this.productionDate = otProductionDate;				
				this.densityPercent = otDensityPercent;
				this.ldCV = otLdCV;
				this.waterRatio = otWaterRatio;
				this.ranges = otRanges;
				
			}
			
			//定义一个数组来存放该对象，作用的保存那些读取的数据，
			var otList = new Array();
			
			//定义一个数组存放存放该对象，作用的保存那些手动添加的数据
			var otList2 = new Array();
			
			var map = {};
			
			//取得td中的值
			$('.operate').each(
					function() {
						$(this).children().each(
										function(i) {
											switch (i) {
											case 1:
												createDate = $(this).find('span').eq(0).text();
												break;
											case 2:
												productionDate = $(this).text();
												break;
											case 3:
												batchNo = $(this).text();
												break;
											case 4:
												lineNo = $(this).text();
												break;											
											case 5:
												//linearDensity = $(this).text();
												linearDensity = parseFloat($(this).text());
												break;
											case 6:
												//densityPercent = $(this).text();
												densityPercent = parseFloat($(this).text());
												break;
											case 7:
												/*ldCV = $(this).text();*/
												ldCV = parseFloat($(this).text());
												break;
											case 8:
												/*yarnlevelness = $(this).text();*/
												yarnlevelness = parseFloat($(this).text());
												break;
											case 9:
												//waterRatio = $(this).text();
												waterRatio = parseFloat($(this).text());
												break;
											/*case 10:
												otStatus = $(this).text();
												break;*/
											case 11:
												gridLine = $(this).text();												
												break;
											case 12:
												ranges = $(this).text();
												break;
											}
										});
						/*var ot = new Other(batchNo, lineNo,
								linearDensity, yarnlevelness, gridLine,otStatus,
								createDate);*/
						var ot = new Other(createDate,productionDate,batchNo,lineNo,linearDensity,
								densityPercent, ldCV, yarnlevelness,waterRatio,/*otStatus,*/
								gridLine,ranges);
								
								
					//	console.log(ot); //控制台输出
						
						//判断是否存在
						if($(this).children("td:first").children("input[type='checkbox']").length>0){
							otList2.push(ot);
						}else{
						otList.push(ot);
						}
						
						map[1] = otList;
						map[2] = otList2;
					});
			
			return map;
		}
		;
	})
</script>
</html>