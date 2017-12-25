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
<title>
	<c:if test="${nlList.size() != 0 }">
		${nlList.get(0).createDate}数据
	</c:if>
</title>
<link href="<%=ctx %>/static/img/life.ico" rel="shortcut icon">
<link href="<%=ctx %>/static/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
<link href="<%=ctx %>/static/css/jedate.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=ctx %>/static/js/jquery.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/static/js/jquery.jedate.js"></script>
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
			  <a type="button" id="btn" class="btn btn-sm btn-success" href="downloadOne">
			 <span class="glyphicon glyphicon-download-alt"></span>导出</a> 
		</div>
		<div>	
			日期：<input type="text" id="control_date" class="jeinput" placeholder="YYYY-MM-DD" style="height:21px;width:173px"/>
			
			 <span class="hidden FileName">${date}(日报表)</span>
		</div>
		
		<div class="od-body">
			<table class="table table-responsive table-bordered table-condensed" id="tableExcel">
				<thead>
					 <tr class="warning">
						<th rowspan="2" class="text-center noExl" hidden="hidden" style="vertical-align: middle !important;width:3%">序号</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:7%">生产时间</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:5%">测量时间</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">批号</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">线位号</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">线密度</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">D%</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">CV%</th>
						<th colspan="2" class="text-center" style="vertical-align: middle !important;width:7%">强度</th>
						
						<th colspan="2" class="text-center" style="vertical-align: middle !important;width:7%">伸长</th>
						
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:4%">条干U%</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">卷曲收缩率</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">卷曲稳定度</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">沸水</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">含油率</th>
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">含水率</th>
						<th colspan="2" class="text-center" style="vertical-align: middle !important;width:7%">网络</th>
						
						<th rowspan="2" class="text-center" style="vertical-align: middle !important;width:3%">状态</th>
						<tr class="info">										
							<th class="text-center" >平均值</th>
							<th class="text-center" >CV%</th>	
							<th class="text-center" >平均值</th>
							<th class="text-center" >CV%</th>	
							<th class="text-center" >平均</th>
							<th class="text-center" >范围</th>	
						</tr>
					<tr>
				</thead>
				<tbody>
						<c:forEach items="${nlList}" var="nl">
							<tr class="success text-center operate hidden">
								<td class="noExl" hidden="hidden">${nlList.indexOf(nl)+1}</td>
								<td>${nl.productionDate}</td>
								<td>${fn:substring(nl.createDate,0, 10)}</td>
								<td>${nl.batchNo }</td>
								<td>${nl.lineNo }</td>
								<td>
								<c:if test="${nl.linearDensity==null }"></c:if>
								<c:if test="${nl.linearDensity!=null }"><fmt:formatNumber type="number" value="${nl.linearDensity }" pattern="0.0" maxFractionDigits="1" />							
								</c:if></td>
								<td>
								<c:if test="${nl.densityPercent==null }"></c:if>
								<c:if test="${nl.densityPercent!=null }"><fmt:formatNumber type="number" value="${nl.densityPercent }" pattern="0.00" maxFractionDigits="2" />
								</c:if>
								</td>
								<td>
								<c:if test="${nl.ldCV==null }"></c:if>
								<c:if test="${nl.ldCV!=null }"><fmt:formatNumber type="number" value="${nl.ldCV }" pattern="0.00" maxFractionDigits="2" />
								</c:if>
								</td>
								<td>
								<c:if test="${nl.strength==null }"></c:if>
								<c:if test="${nl.strength!=null }"><fmt:formatNumber type="number" value="${nl.strength/100.0 }" pattern="0.00" maxFractionDigits="2" />
								</c:if>
								</td>
								<td>
								<c:if test="${nl.strengthCV==null }"></c:if>
								<c:if test="${nl.strengthCV!=null }"><fmt:formatNumber type="number" value="${nl.strengthCV }" pattern="0.00" maxFractionDigits="2" />
								</c:if>
								</td>
								<td>
								<c:if test="${nl.elongation==null }"></c:if>
								<c:if test="${nl.elongation!=null }"><fmt:formatNumber type="number" value="${nl.elongation/10 }" pattern="0.0" maxFractionDigits="1" />
								</c:if>
								</td>
								<td>
								<c:if test="${nl.elongationCV==null }"></c:if>
								<c:if test="${nl.elongationCV!=null }"><fmt:formatNumber type="number" value="${nl.elongationCV }" pattern="0.00" maxFractionDigits="2" />
								</c:if>
								</td>		
								<td>
								<c:if test="${nl.yarnlevelness==null }"></c:if>
								<c:if test="${nl.yarnlevelness!=null }"><fmt:formatNumber type="number" value="${nl.yarnlevelness }" pattern="0.00" maxFractionDigits="2" />
								</c:if>
								</td>			
								<td>
								<c:if test="${nl.crimpContraction==null }"></c:if>
								<c:if test="${nl.crimpContraction!=null }"><fmt:formatNumber type="number" value="${nl.crimpContraction/10 }" pattern="0.0" maxFractionDigits="1" />
								</c:if>
								</td>
								<td>
									<c:if test="${nl.crimpStability==null }"></c:if>
									<c:if test="${nl.crimpStability!=null }"><fmt:formatNumber type="number" value="${nl.crimpStability/10 }" pattern="0.0" maxFractionDigits="1" /></c:if>
									</td>
								<td>
									<c:if test="${nl.boilingWater==null }"></c:if>
									<c:if test="${nl.boilingWater!=null }"><fmt:formatNumber type="number" value="${nl.boilingWater/10 }" pattern="0.0" maxFractionDigits="1" /></c:if>
								</td>
								<td>
								<c:if test="${nl.oliContent==null }"></c:if>
								<c:if test="${nl.oliContent!=null }"><fmt:formatNumber type="number" value="${nl.oliContent/100.0 }" pattern="0.00" maxFractionDigits="2" /></c:if></td>
								<td>
								<c:if test="${nl.waterRatio==null }"></c:if>
								<c:if test="${nl.waterRatio!=null }">
								<fmt:formatNumber type="number" value="${nl.waterRatio }" pattern="0.00" maxFractionDigits="2" /></c:if>
								</td>
								<td>
									${nl.gridLine }
								</td>
								<td>${nl.ranges }</td>
								 <td>
									<c:if test="${nl.nlStatus==0}"><span class="text-success"><b>正常</b></span></c:if>
									<c:if test="${nl.nlStatus > 20 && nl.nlStatus < 30}"><span class="text-primary"><b>研究加测</b></span></c:if>
									<c:if test="${nl.nlStatus > 30 && nl.nlStatus < 40}"><span class="text-warning"><b>车间加测</b></span></c:if>
									<c:if test="${nl.nlStatus > 40 && nl.nlStatus < 50}"><span class="text-danger"><b>异常跟踪</b></span></c:if>
							    </td>
								<!--  <td>${fn:substring(nl.createDate,0, 10)}</td>-->
							</tr>
						</c:forEach>
				</tbody>
				<c:if test="${nlList.size()==0}">
					<h4 class="text-danger">没有当天数据!</h4>
				</c:if>
			</table>
			
			<div class="oneday_footer row">
			<c:if test="${nlList.size() != 0 && nlList.size() != null}">
			<div class="col-md-3">
				当前第<b class="page"></b>页，总共<b class="pages">
				</b>页，总共<b>${nlList.size()}</b>条记录
			</div>
			<!-- 分页 -->
			<ul class="pagination">
			<li><a href="javascript:void(0)" class="prev">&laquo;</a></li>
			<c:forEach var="i" begin="0" 
			end="${nlList.size()/20}">
			<li><a href="javascript:void(0)" class="fenye">${i+1}</a></li>
			</c:forEach>
			<li><a href="javascript:void(0)" class="next">&raquo;</a></li>
			</ul>
				</c:if>
			</div>
			
		</div>
	</div>
</body>
<script type="text/javascript">
$("#control_date").jeDate({
  format: "YYYY-MM-DD"
});	
	
$(function() {
	$('.search').click(function() {
		var date = $('#control_date').val();
		if(date!="") {
			$(".FileName").text(date);
			$(this).attr("href","selectData?date="+date).attr("target","_blank");
		}else {
			alert("请选择日期!");
		}
	});
	
});

$(function(){
	var length = $('.operate').length;
	
  /*  $("#btn").click(function(){
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
            filename: $(".FileName").text()+".xls"
        });
        alert("导出成功！");
    	}
    });*/
    
    
    fenye(20,1,length);//默认显示第一页
    //分页   
    $(".fenye").click(function(){ 
   fenye(20,$(this).text(),length);
    });
    $(".prev").click(function(){
    	if(parseInt($(".page").text())==1){
    		alert("已经是第一页了");
    	}else{
    	fenye(5,parseInt($(".page").text())-1,length);
    	}
    })
     $(".next").click(function(){
    	 if(parseInt($(".page").text())==parseInt($(this).parent().prev().children().text())){
     		alert("已经是最后一页了");
     	}else{
     		//alert($(this).parent().prev().children().text());
    	fenye(5,parseInt($(".page").text())+1,length);
     	
     	}
    })
    
   //输入显示
   function fenye(size, page,allSize){  //分别输入每页最大数，当前页，总数
    	$(".page").text(page);
    	$(".pages").text(parseInt(allSize/size)+1);
    	yincang(allSize);
    	if(size>=allSize){    //当总数小于每页的数量
    		for(var i=0;i<allSize;i++){
    			
        		$('.operate').eq(i).removeClass("hidden");
        	}
    	}else{
    		if(size*page>allSize){   //当指定页的最后一项大于总数
    			for(var i=size*(page-1);i<allSize;i++){
    				
            		$('.operate').eq(i).removeClass("hidden");
            	}
    		}else{                   //当指定页的最后一项小于总数
    			for(var i=size*(page-1);i<size*page;i++){
            		$('.operate').eq(i).removeClass("hidden");
            	}
    		}
		
    	}
    }
   
   function yincang(length){
	       for(var i=0;i<length;i++){
	    	   $('.operate').eq(i).addClass("hidden");
	       }
	       
   }
   
    
});
</script>
</html>
