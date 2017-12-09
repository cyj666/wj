$("#fileUploadContent").initUpload({
	"uploadUrl" : "se/seUpFile",// 上传文件信息地址
	"progressUrl" : "se/getPercent",// 获取进度信息地址，可选，注意需要返回的data格式如下（{bytesRead:
									// 102516060, contentLength: 102516060,
									// items: 1, percent: 100, startTime:
									// 1489223136317, useTime: 2767}）
	// "showSummerProgress":false,//总进度条，默认限制
	// "size":350,//文件大小限制，单位kb,默认不限制
	// "maxFileNumber":15,//文件个数限制，为整数
	"filelSavePath" : "E:/project/se/",// 文件上传地址，后台设置的根目录
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
		$(this).attr("href", "seRead");
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
			url : "seSaveData",
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
				layer.alert("数据重复", {
							icon : 2,
							skin : 'layui-layer-lan',// 样式类名
							closeBtn : 1
						});
			}
		});
	});
});
