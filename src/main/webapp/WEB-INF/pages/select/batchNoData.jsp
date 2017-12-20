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
<title>
	<c:if test="${nlList.size() != 0 }">
		${nlList.get(0).batchNo }数据
	</c:if>
</title>
<link href="<%=ctx %>/static/img/life.ico" rel="shortcut icon">
<link href="<%=ctx %>/static/css/bootstrap.min.css" rel="stylesheet" type="text/css" >

<script type="text/javascript" src="<%=ctx %>/static/js/jquery.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/jquery.table2excel.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/echarts-all-3.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/ecStat.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/dataTool.min.js"></script>
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
	<span class="hidden filename">${filename}</span>
	
		<div class="row">
			<div class="col-sm-1">
				<a type="button" id="btn" class="btn btn-link btn-sm" href="download?filename=${filename}">
			 		<span class="glyphicon glyphicon-download"></span>导出
			 	</a> 
			</div>
			<div class="col-sm-11 text-danger headName">
				<h3 style="text-align: center;margin:0 auto">${nlList.get(0).batchNo }</h3>
			</div>
		</div>

		<div class="od-head">
			<table class="table table-responsive table-bordered table-condensed table-hover"  style="width:50%">
				<thead>
					<tr class="warning">
						<th class="text-center" style="width:3%">序号</th>
						<th class="text-center" style="width:2%">批号</th>
						<th class="text-center" style="width:3%">规格</th>
						<th class="text-center" style="width:5%">指标</th>
						<th class="text-center" style="width:3%">标准值</th>
						<th class="text-center" style="width:3%">偏差值</th>
					</tr>
				</thead>
				<c:if test="${msg!=1 }">
					<tbody>
						<c:forEach var="i" begin="0" end="${ciSize }" step="1">
							<tr class="success text-center ">
								<td>${i+1 }</td>
								<td>${ciList.get(i).batchNo }</td>
								<td>${format }</td>
								<td>
									<c:choose>
										<c:when test="${ciList.get(i).checkIndex == 'linear_density' }">纤度</c:when>
										<c:when test="${ciList.get(i).checkIndex == 'density_percent' }">D%</c:when>
										<c:when test="${ciList.get(i).checkIndex == 'ld_CV' }">纤度CV%</c:when>
										<c:when test="${ciList.get(i).checkIndex == 'strength' }">强度</c:when>
										<c:when test="${ciList.get(i).checkIndex == 'strength_CV' }">强度CV%</c:when>
										<c:when test="${ciList.get(i).checkIndex == 'elongation' }">伸长</c:when>
										<c:when test="${ciList.get(i).checkIndex == 'elongation_CV' }">伸长CV%</c:when>
										<c:when test="${ciList.get(i).checkIndex == 'yarnlevelness_U' }">条干U%</c:when>
										<c:when test="${ciList.get(i).checkIndex == 'boiling_water' }">沸水%</c:when>
										<c:when test="${ciList.get(i).checkIndex == 'crimp_contraction' }">卷曲收缩率%</c:when>
										<c:when test="${ciList.get(i).checkIndex == 'crimp_stability' }">卷曲稳定度%</c:when>
										<c:when test="${ciList.get(i).checkIndex == 'water_ratio' }">含水率%</c:when>
										<c:when test="${ciList.get(i).checkIndex == 'oli_content' }">含油率%</c:when>
										<c:when test="${ciList.get(i).checkIndex == 'grid_line' }">网络度</c:when>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${ciList.get(i).checkIndex == 'grid_line' }">
											<fmt:formatNumber type="number" value="${ciList.get(i).standardValue }" pattern="#" />
										</c:when>
										<c:otherwise>${ciList.get(i).standardValue }</c:otherwise>
									</c:choose>
								</td>
								<td>
									<c:choose>
										<c:when test="${ciList.get(i).checkIndex == 'grid_line' }">
											<fmt:formatNumber type="number" value="${ciList.get(i).deviationValue }" pattern="#" />
										</c:when>
										<c:otherwise>${ciList.get(i).deviationValue }</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<span class="text-danger"><h4><c:if test="${msg==1 }">没有数据!</c:if></h4></span>
		</div>
	
		<div class="od-body" style="width:100%">
			<table class="table table-responsive table-bordered table-condensed table-hover MyExcel">
				<thead>
					<tr class="warning">
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:4%">检测日期</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:7%">生产时间</th>	
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">线位号</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">线密度dtex</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">线密度D%</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">线密度CV%</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:2%">强度cn/dtex</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:2%">强度CV%</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:2%">伸长%</th> 
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">伸长CV%</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">条干U%</th>						
						<th colspan="2" class="text-center" style="vertical-align: middle !important;width:7%">卷曲</th>
						
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">沸水</th>
						
						<th colspan="2" class="text-center" style="vertical-align: middle !important;width:7%">网络（个）</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">含油率</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">含水率</th>
						<tr class="info">										
							<th class="text-center" >收缩率%</th>
							<th class="text-center" >稳定度%</th>
							<th class="text-center" >网络平均</th>
							<th class="text-center" >范围</th>
						<tr>
				</thead>
				<tbody>
					<c:forEach items="${nlList }" var="nl">
						<tr class="success text-center data">
							<td class="cd">${nl.createDate }</td>
							<td>${nl.productionDate }</td>
							<td>${nl.lineNo }</td>
							<td class="ln flagStatus"><fmt:formatNumber type="number" value="${nl.linearDensity }" pattern="0.0" maxFractionDigits="1" /></td>
							<td class="flagValue noExl" hidden="hidden">${nl.inLiFlag }</td>
							<td class="flagStatus"><fmt:formatNumber type="number" value="${nl.densityPercent }" pattern="0.00" maxFractionDigits="2" /></td>
							<td class="flagValue noExl" hidden="hidden">${nl.inDeFlag }</td>
							<td class="flagStatus"><fmt:formatNumber type="number" value="${nl.ldCV }" pattern="0.00" maxFractionDigits="2" /></td>
							<td class="flagValue noExl" hidden="hidden">${nl.inLdFlag }</td>
							<td class="flagStatus"><fmt:formatNumber type="number" value="${nl.strength }" pattern="0.00" maxFractionDigits="2" /></td>
							<td class="flagValue noExl" hidden="hidden">${nl.inStFlag }</td>
							<td class="flagStatus"><fmt:formatNumber type="number" value="${nl.strengthCV }" pattern="0.00" maxFractionDigits="2" /></td>
							<td class="flagValue noExl" hidden="hidden">${nl.inStvFlag }</td>
							<td class="flagStatus"><fmt:formatNumber type="number" value="${nl.elongation }" pattern="0.0" maxFractionDigits="1" /></td>
							<td class="flagValue noExl" hidden="hidden">${nl.inElFlag }</td>
							<td class="flagStatus"><fmt:formatNumber type="number" value="${nl.elongationCV }" pattern="0.00" maxFractionDigits="2" /></td>
							<td class="flagValue noExl" hidden="hidden">${nl.inElvFalg }</td>
							<td class="flagStatus"><fmt:formatNumber type="number" value="${nl.yarnlevelness }" pattern="0.00" maxFractionDigits="2" /></td>
							<td class="flagValue noExl" hidden="hidden">${nl.inYeFlag }</td>
							<td class="flagStatus"><fmt:formatNumber type="number" value="${nl.crimpContraction }" pattern="0.0" maxFractionDigits="1" /></td>
							<td class="flagValue noExl" hidden="hidden">${nl.inCcFlag }</td>
							<td class="flagStatus"><fmt:formatNumber type="number" value="${nl.crimpStability }" pattern="0.0" maxFractionDigits="1" /></td>
							<td class="flagValue noExl" hidden="hidden">${nl.inCsFlag }</td>
							<td class="flagStatus"><fmt:formatNumber type="number" value="${nl.boilingWater }" pattern="0.0" maxFractionDigits="1" /></td>
							<td class="flagValue noExl" hidden="hidden">${nl.inBoFlag }</td>
							<td class="flagStatus">${nl.gridLine }</td>
							<td class="flagValue noExl" hidden="hidden">${nl.inGliFalg }</td>
							<td>${nl.ranges }</td>
							<td class="oc flagStatus"><fmt:formatNumber type="number" value="${nl.oliContent }" pattern="0.00" maxFractionDigits="2" /></td>
							<td class="flagValue noExl" hidden="hidden">${nl.inOlFlag }</td>
							<td class="flagStatus"><fmt:formatNumber type="number" value="${nl.waterRatio }" pattern="0.00" maxFractionDigits="2" /></td>
							<td class="flagValue noExl" hidden="hidden">${nl.inWrFlag}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
 		<div id="linearDensity" style="height: 250px"></div>
		<p style="height:20px"></p>
		<div id="strength" style="height:250px"></div>
		<p style="height:20px"></p>
		<div id="elongation" style="height:250px"></div>
		<p style="height:20px"></p>
		<div id="ccb" style="height: 250px"></div>
		<p style="height:20px"></p>
		<div id="lyn" style="height: 250px"></div>
		<p style="height:20px"></p>
		<div id="bw" style="height: 250px"></div>
		<p style="height:20px"></p>
		<div id="oliContent" style="height: 250px"></div>
		<p style="height:20px"></p>
		<div id="gridLine" style="height: 250px"></div>	 	
	</div>
</body>
<script type="text/javascript">
	$(function() {
		//导出excel表格
		$('.btn').click(function() {
			/* $(".MyExcel").table2excel({
		            // 不被导出的表格行的CSS class类
		            exclude: ".noExl",
		            // 导出的Excel文档的名称，（没看到作用）
		            name: "Excel Document Name",
		            // Excel文件的名称
		            filename: $(".headName").find("h3").text()+"数据"+".xls"
		     });*/
		     /*$.ajax({
		    	 type: "GET",
	             url: "download?filename="+$('.filename').text(),
	            //data: "filename="+$('.filename'),
	             //dataType: "json",
	             success: function(){
	            	 alert("下载成功！");
	             }
	             
		     })*/
		     //alert($('.filename').text());
		});
		
		//判断测量值与标准值比较是不是在偏差值范围内
		$('.flagValue').each(function(i) {
			if ($(this).text() == 1) {
				$(this).prev().addClass("text-danger").css("font-weight","bold");
			}
		});
		
		//定义一个数组,用来存放含油率数据
		var ocArr = new Array();
		//定义一个用来存放日期和线位号组成的数组
		var ldArr = new Array();
		//定义一个用来存放线密度的数组
		var ltArr = new Array();
		//定义一个用来存放强度的数组
		var stArr = new Array();
		var elArr = new Array();
		var ccArr = new Array();
		var csArr = new Array();
		var ylnArr = new Array();
		var glArr = new Array();
		var bwArr = new Array();
		$('.oc').each(function(i){
			var oc = parseFloat($(this).text());
			ocArr.push(oc);
			//console.log(ocArr);
		});
		
		$('.data').each(function() {
			//ln 线位号, cd 测量日期, ld 线密度, st 强度, el 强伸, cc 卷曲收缩率, cs 卷曲收缩率, bw 沸水
			//pd 生产日期 , yln 条干 
			var ln;var cd;var ld;var st;var el;var cc;var cs;var gl;var bw;
			$(this).children().each(function(i) {
				//定义线位号和日期
				if (i == 0) {
					cd = $(this).text();
				} else if (i == 1) {
					pd = $(this).text();
				} else if(i == 2) {
					ln = $(this).text();
					//截取测量日期的月日
					cd = cd.substring(5, cd.length);
					//和线位号关联
					var nd = cd + "(" + ln +")";
					//生产日期不为空
					if (pd != "") {
						nd = cd + ", " + pd.substring(pd.indexOf("-")+1, pd.length) + "(" + ln +")";
					}
					ldArr.push(nd);
				} else if(i == 3) {
					ld = parseFloat($(this).text());
					ltArr.push(ld);
				} else if(i == 9){
					st = parseFloat($(this).text());
					stArr.push(st);
				} else if(i == 13) {
					el = parseFloat($(this).text());
					elArr.push(el);
				} else if (i == 17) {
					yln = parseFloat($(this).text());
					ylnArr.push(yln);
				} else if(i == 19) {
					cc = parseFloat($(this).text());
					ccArr.push(cc);
				} else if(i == 21) {
					cs = parseFloat($(this).text());
					csArr.push(cs);
				} else if (i == 23) {
					bw = parseFloat($(this).text());
					//console.log(typeof(mid));
					/* if(isNaN(bw)) {
						bwArr.push(NaN);
					}else {
						bwArr.push(bw);
					} */
					bwArr.push(bw);
				} else if (i == 25) {
					gl = parseInt($(this).text());
					/* if (isNaN(gl)) {
						glArr.push(NaN);
					} else {
						glArr.push(gl);
					} */
					glArr.push(gl);
				}
			});
		});
		console.log(stArr);
		
		doData("linearDensity", "线密度dtex", ltArr, ldArr, "line");
		doData("strength", "强度CN/detx", stArr, ldArr, "line");
		doData("elongation", "伸长率%", elArr, ldArr, "line");
		doTwoData("ccb", "卷曲收缩率%", "卷曲稳定度%", ccArr, csArr, ldArr);
		doData("lyn", "条干U%", ylnArr, ldArr, "line");
		doData("bw", "沸水%", bwArr, ldArr, "line");
		doData("oliContent", "含油率%", ocArr, ldArr, "line");
		doData("gridLine", "网络度", glArr, ldArr, "line");

		
		function doData(Did,Dname,dataArr,nameArr,theType) {
			var dom = document.getElementById(Did);
			var myChart = echarts.init(dom);
			var app = {};
			option = null;
			option = {
				title : {
					text : Dname
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [ Dname ]
				},
				calculable: false,
				grid : {
					left : '3%',
					right : '4%',
					bottom : '3%',
					containLabel : true
				},
				/* toolbox : {
					feature : {
						saveAsImage : {}
					}
				}, */
				xAxis : {
					type : 'category',
					boundaryGap : false,
					data : nameArr
				},
				yAxis : {
					type : 'value'
				},
				series : [ {
					name : Dname,
					type : theType,
					stack : '总量',
					data : dataArr
				}]
			};
			if (option && typeof option === "object") {
				myChart.setOption(option, true);
			}
		};
		
		function doTwoData(Did,Dname1,Dname2,dataArr1,dataArr2,nameArr) {
			var dom = document.getElementById(Did);
			var myChart = echarts.init(dom);
			var app = {};
			option = null;
			option = {
				title : {
					text : Dname1+'、'+Dname2
				},
				tooltip : {
					trigger : 'axis'
				},
				legend : {
					data : [ Dname1,Dname2 ]
				},
				grid : {
					left : '3%',
					right : '4%',
					bottom : '3%',
					containLabel : true
				},
				/* toolbox : {
					feature : {
						saveAsImage : {}
					}
				}, */
				xAxis : {
					type : 'category',
					boundaryGap : false,
					data : nameArr
				},
				yAxis : {
					type : 'value'
				},
				series : [ {
					name : Dname1,
					type : 'line',
					stack : '总量',
					data : dataArr1
				},{
					name : Dname2,
					type : 'line',
					stack : '总量',
					data : dataArr2
				}]
			};
			if (option && typeof option === "object") {
				myChart.setOption(option, true);
			}
		};
	});
</script>
</html>