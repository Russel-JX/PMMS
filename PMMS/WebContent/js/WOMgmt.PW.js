require([ 'jquery','pmms','datagrids','ge-bootstrap','datepicker'], function($,pmms) {
	var selectObj3 = $("#tab003 select");
	var selectObj4 = $("#tab004 select");
	pmms.getDeptInfo(selectObj3);
	pmms.getDeptInfo(selectObj4);
	/*
	 * 当前计划保养工单
	 */
	//初始化--加载当前计划工单页面
	$(function(){
		$('.datepicker').datepicker();
		var opts={
			url:"../restful/Hitorical/getWorkOrders",
			data:{
				orderType:2,
				orderFlag:1,
				startDate:"",
				endDate:"",
				department:""
			}
		};
		initTab003(opts);
	});
	//点击当前计划保养工单tab,重新载入数据
	$("#tab003Nav").on("click",function(){
		clearDataTab003();
		var opts={
			url:"../restful/Hitorical/getWorkOrders",
			data:{
				orderType:2,
				orderFlag:1,
				startDate:"",
				endDate:"",
				department:""
			}
		}
		initTab003(opts);
	});
	//当前计划保养工单--多条件查询
	$("#tab003 button").on("click",function(){
		var startDate=$("#tab003 .datepicker:eq(0)").val();
		var endDate=$("#tab003 .datepicker:eq(1)").val();
		var department=$("#tab003 select").val();
		if(startDate==""&&endDate!=""){
			alert("请输入开始时间");
			return false;
		}
		if(startDate!=""&&endDate==""){
			alert("请输入结束时间");
			return false;
		}
		if(!compareDate(startDate,endDate)){
			return false;
		}
		var opts={
			url:"../restful/Hitorical/getWorkOrders",
			data:{
				orderType:2,
				orderFlag:1,
				startDate:startDate,
				endDate:endDate,
				department:department
			}
		}
		initTab003(opts);
	});
	//初始化tab003
	function initTab003(opts){
	$.ajax({
		type:"POST",
			url:opts.url,
			dataType:"json",
			data:opts.data,
			async:true,
			success:function(jsonData){
				if(jsonData.success){
				var orderhtml='';
					orderhtml+='<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable003">';
					orderhtml+='<thead>';
					orderhtml+='</thead>';
					orderhtml+='<tbody>';
					orderhtml+='</tbody>';
					orderhtml+='</table>';
					$("#wrappedWorkOrderTable003 table>tbody").empty();
					$("#wrappedWorkOrderTable003").html(orderhtml);
					buildWorkOrderTab003(jsonData.data);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("error"+textStatus);
			}
		});//ajax end
	}//initTab003 end
	function buildWorkOrderTab003(data){
		var orderHeader=[{"sTitle": "工单号/WOSN","mData":"workOrderId"},{"sTitle": "设备编号/EQSN","mData":"equipId"},
			 {"sTitle": "设备名称/EQNM","mData":"equipName"},{"sTitle": "设备型号/EQModel","mData":"equipModel"},
			 {"sTitle": "固定资产号/AssetSN","mData":"assetId"},	{"sTitle":"是否需停机/Shutdown","mData":"shutdownFlag"},
             {"sTitle": "维修工/Maintainer","mData":"mechianic"},{"sTitle": "维修开始时间/MaintStart","mData":"strMaintanceStartDate"},
			 {"sTitle": "计划开始月/PlanMonth","mData":"strPlanStartMonth"},{"sTitle": "使用部门/Department","mData": "deptNM"},{"sTitle": "备注/Remark","mData":"remark"}];
		$("#wrappedWorkOrderTable003 table").iidsBasicDataGrid({
			"isResponsive": true,
	        "useFloater": false,
	        "bAutoWidth": false,
	        "bDestroy": true,
			'aaSorting': [],
			"aoColumns": orderHeader, 
	        "aaData": data.workOrders,
			"oColReorder": {"iFixedColumns": 15},
	        "fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
        	//console.log(aData.workOrderStatus);
		        if(aData.workOrderStatus==1){
		        	$('td:eq(0)',nRow).css("background",'green');
		        	$('td:eq(1)',nRow).css("background",'green');
		        	$('td:eq(2)',nRow).css("background",'green');
		        	$('td:eq(3)',nRow).css("background",'green');
		        	$('td:eq(4)',nRow).css("background",'green');
		        	$('td:eq(5)',nRow).css("background",'green');
		        	$('td:eq(6)',nRow).css("background",'green');
		        	$('td:eq(7)',nRow).css("background",'green');
		        	$('td:eq(8)',nRow).css("background",'green');
		        	$('td:eq(9)',nRow).css("background",'green');
					$('td:eq(10)',nRow).css("background",'green');
		        }else if(aData.workOrderStatus==2){
		        	$('td:eq(0)',nRow).css("background",'orange');
		        	$('td:eq(1)',nRow).css("background",'orange');
		        	$('td:eq(2)',nRow).css("background",'orange');
		        	$('td:eq(3)',nRow).css("background",'orange');
		        	$('td:eq(4)',nRow).css("background",'orange');
		        	$('td:eq(5)',nRow).css("background",'orange');
		        	$('td:eq(6)',nRow).css("background",'orange');
		        	$('td:eq(7)',nRow).css("background",'orange');
		        	$('td:eq(8)',nRow).css("background",'orange');
		        	$('td:eq(9)',nRow).css("background",'orange');
					$('td:eq(10)',nRow).css("background",'orange');
		        }
				if(aData.shutdownFlag){
					$('td:eq(5)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
				}else{
					$('td:eq(5)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
				}
				if((aData.mechianicLastName!="") || (aData.mechianicFirtName!="")){
					$('td:eq(6)', nRow).html(aData.mechianic+"("+aData.mechianicLastName+" "+aData.mechianicFirtName+")");
				}else{
					$('td:eq(6)', nRow).html(aData.mechianic);
				}
			} 
		});
	}//buildWorkOrderTab003 ended
	
	/*
	 * 历史计划保养工单
	 */
	//点击历史计划保养工单tab
	$("#tab004Nav").on('click',function(){
		clearDataTab004();
		var opts={
			url:"../restful/Hitorical/getWorkOrders",
			data:{
				orderType:2,
				orderFlag:2,
				startDate:"",
				endDate:"",
				department:""
			}
		}
		initTab004(opts);
	});
	//历史计划保养工单--条件查询
	$("#tab004 button:eq(0)").on('click',function(){
		var startDate=$("#tab004 .datepicker:eq(0)").val();
		var endDate=$("#tab004 .datepicker:eq(1)").val();
		var department=$("#tab004 select").val();
		if(startDate==""&&endDate!=""){
			alert("请输入开始时间");
			return false;
		}
		if(startDate!=""&&endDate==""){
			alert("请输入结束时间");
			return false;
		}
		if(!compareDate(startDate,endDate)){
			return false;
		}
		var opts={
			url:"../restful/Hitorical/getWorkOrders",
			data:{
				orderType:2,
				orderFlag:2,
				startDate:startDate,
				endDate:endDate,
				department:department
			}
		}
		initTab004(opts);
	});
	//初始化tab004
	function initTab004(opts){
	$.ajax({
		type:"POST",
			url:opts.url,
			dataType:"json",
			data:opts.data,
			success:function(jsonData){
				if(jsonData.success){
				var orderhtml='';
					orderhtml+='<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable004">';
					orderhtml+='<thead>';
					orderhtml+='</thead>';
					orderhtml+='<tbody>';
					orderhtml+='</tbody>';
					orderhtml+='</table>';
					$("#wrappedWorkOrderTable004 table>tbody").empty();
					$("#wrappedWorkOrderTable004").html(orderhtml);
					buildWorkOrderTab004(jsonData.data);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("error"+textStatus);
			}
		});//ajax end
	}//initTab004 end
	function buildWorkOrderTab004(data){
		var orderHeader=[{"sTitle": "选择/Select","mData":"selectItem"},{"sTitle": "工单号/WOSN","mData":"workOrderId"},{"sTitle": "设备编号/EQSN","mData":"equipId"},
			 {"sTitle": "设备名称/EQNM","mData":"equipName"},{"sTitle": "设备型号/EQModel","mData":"equipModel"},
			 {"sTitle": "固定资产号/AssetSN","mData":"assetId"},	{"sTitle":"是否需停机/Shutdown","mData":"shutdownFlag"},
             {"sTitle": "维修工/Maintainer","mData":"mechianic"},{"sTitle": "维修开始时间/MaintStart","mData":"strMaintanceStartDate"},{"sTitle": "维修结束时间/MaintClose","mData":"strMaintanceEndDate"},
			 {"sTitle": "计划开始月/PlanMonth","mData":"strPlanStartMonth"},{"sTitle": "使用部门/Department","mData": "deptNM"},{"sTitle": "备注/Remark","mData":"remark"}];
		var oTable = $("#wrappedWorkOrderTable004 table").iidsBasicDataGrid({
		"isResponsive": true,
        "useFloater": false,
        "bAutoWidth": false,
        "bDestroy": true,
		'aaSorting': [],
		"aoColumns": orderHeader, 
        "aaData": data.workOrders,
		"oColReorder": {"iFixedColumns": 15},
        "fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
			$('td:eq(0)',nRow).html('<input type="radio" name="radio">');
				if(aData.shutdownFlag){
					$('td:eq(6)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
				}else{
					$('td:eq(6)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
				}
				if((aData.mechianicLastName!="") || (aData.mechianicFirtName!="")){
					$('td:eq(7)', nRow).html(aData.mechianic+"("+aData.mechianicLastName+" "+aData.mechianicFirtName+")");
				}else{
					$('td:eq(7)', nRow).html(aData.mechianic);
				}
			} 
		});
		oTable = $("#wrappedWorkOrderTable004 table").dataTable();
		
		var rowData = null;
		var peditFlag = false;
	    //历史计划保养工单的编辑
		$("#tab004 button:eq(1)").unbind('click').on("click",function(){
			peditFlag = false;
			var num = $("#wrappedWorkOrderTable004 :radio:checked").length;
			if(num==0){
				alert("请选择编辑的工单");
				//console.log("请选择编辑的工单");
				return false;
			}else{
				$("#pwoUpdateModal input:eq(0)").val("");
				$("#pwoUpdateModal select").val("");
				$("#pwoUpdateModal textarea").val("");
				var pos = $("#wrappedWorkOrderTable004 :radio:checked").parent().parent()[0];
			    rowData = oTable.fnGetData(pos);
				//console.log(rowData.workOrderId);
				$("#pwoUpdateModal input:eq(0)").val(rowData.workOrderId);
				$("#pwoUpdateModal input:eq(1)").val(rowData.equipId);
				$("#pwoUpdateModal input:eq(2)").val(rowData.equipName);
				$("#pwoUpdateModal input:eq(3)").val(rowData.equipModel);
				$("#pwoUpdateModal input:eq(4)").val(rowData.assetId);
				$("#pwoUpdateModal input:eq(5)").val(rowData.strPlanStartMonth);
				$("#pwoUpdateModal select").val(rowData.deptId);
				$("#pwoUpdateModal input:eq(6)").val(rowData.mechianic);
				$("#pwoUpdateModal input:eq(7)").val(rowData.strMaintanceStartDate);
				$("#pwoUpdateModal input:eq(8)").val(rowData.strMaintanceEndDate);
				$("#pwoUpdateModal textarea").val(rowData.remark);
				$("#pwoUpdateModal").modal('show');
			}
		});
		
	   //保存修改后的历史计划保养工单
	   $("#pwoUpdateModal button:eq(1)").unbind('click').on('click',function(){
		var remark = $("#pwoUpdateModal textarea").val();
		rowData.remark = remark;
		if(!peditFlag){
			$.ajax({
				type:"POST",
				url:"../restful/Hitorical/updatePlanWorkOrder",
				dataType:"json",
				data:{
					workOrderInfo:JSON.stringify(rowData)
				},
				beforeSend:function(XMLHttpRequest){
					peditFlag = true;
				},
				success:function(jsonData){
					if(jsonData.success){
						$("#pwoUpdateModal").modal('hide');
						alert("update"+rowData.workOrderId+"successfully!");
						var opts={
						url:"../restful/Hitorical/getWorkOrders",
						data:{
							orderType:2,
							orderFlag:2,
							startDate:"",
							endDate:"",
							department:""
						}
					}
					initTab004(opts);
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert(textStatus);
				}
			});
		}else{
			//console.log("计划保养工单重复update");
		}
	});
		
	}//buildWorkOrderTab004 ended
	
	/*
	 * 计划保养工单处理
	 */
	//点击计划保养工单处理
	/*
	$("#tab005Nav").on("click",function(){
		var opts={
		url:"../restful/Hitorical/getWorkOrders",
			data:{
				orderType:2,
				orderFlag:1,
				startDate:"",
				endDate:"",
				department:""
			}
		}
	initTab005(opts);
	});	
	//初始化tab005
	function initTab005(opts){
		$.ajax({
			type:"POST",
				url:opts.url,
				dataType:"json",
				data:opts.data,
				success:function(jsonData){
					if(jsonData.success){
						var orderhtml='';
						orderhtml+='<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable005">';
						orderhtml+='<thead>';
						orderhtml+='</thead>';
						orderhtml+='<tbody>';
						orderhtml+='</tbody>';
						orderhtml+='</table>';
						$("#wrappedWorkOrderTable005 table>tbody").empty();
						$("#wrappedWorkOrderTable005").html(orderhtml);
						buildWorkOrderTab005(jsonData.data);
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert("error"+textStatus);
				}
		});//ajax end
	}//initTab005 end
	function buildWorkOrderTab005(data){
		var orderHeader=[{"sTitle": "选择","mData":"selectItem"},{"sTitle": "工单号","mData":"workOrderId"},{"sTitle": "设备编号","mData":"equipId"},
			 {"sTitle": "保养内容","mData":"maintContent"},{"sTitle": "设备名称","mData":"equipName"},{"sTitle": "设备型号","mData":"equipModel"},
			 {"sTitle": "固定资产号","mData":"assetId"},	{"sTitle":"是否需停机","mData":"shutdownFlag"},
             {"sTitle": "维修工","mData":"mechianic"},{"sTitle": "维修开始时间","mData":"strMaintanceStartDate"},
			 {"sTitle": "计划开始月","mData":"strCreateDate"},{"sTitle": "备注","mData":"remark"}];
		var oTable = $("#wrappedWorkOrderTable005 table").iidsBasicDataGrid({
		"isResponsive": true,
        "useFloater": false,
        "bAutoWidth": false,
        "bDestroy": true,
		'aaSorting': [],
		"aoColumns": orderHeader, 
        "aaData": data.HisWorkOrders,
		"oColReorder": {"iFixedColumns": 15},
        "fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
			$('td:eq(0)',nRow).html('<input targetName="workOrderInfo" type="radio" name="radio" />');
			$('td:eq(3)',nRow).html('<a><i targetName = "showContentInfo" class="icon-search"></i></a>');
			} 
		});
	}//buildWorkOrderTab005 ended
	*/
	//计划工单的处理
	$("#tab005Nav").bind('click',function(){
		require(['WOMgmt.PWTab3'],function(pwTab3){
			pwTab3.init();
		});
	});
	
	//export to excel
	$("#tab003 a[class='btn btn-icon']").on('click',function(){
		window.location.href = "../restful/workOrder/curPMWOExportToExcel";
	});
	
	$("#tab004 a[class='btn btn-icon']").on('click',function(){
		window.location.href = "../restful/workOrder/hisPMWOExportToExcel";
	});
	
	//*******************************************************//
		//tab003 清理时间和下拉列选
		function clearDataTab003(){
			$("#tab003 .datepicker:eq(0)").val("");
			$("#tab003 .datepicker:eq(1)").val("");
			$("#tab003 select").val("");
		}
		//tab004 清理时间和下拉列选
		function clearDataTab004(){
			$("#tab004 .datepicker:eq(0)").val("");
			$("#tab004 .datepicker:eq(1)").val("");
			$("#tab004 select").val("");
		}
		//tab005 清理下拉列选
//		function clearDataTab005(){
//			$("#workOrderflow005 select").val("");
//		}
		
		//时间对比
		function compareDate(startDate,endDate){
		if (startDate != "" && endDate != "") {
			var arrStart = new Array();
			arrStart = startDate.split("/");
			var arrEnd = new Array();
			arrEnd = endDate.split("/");
			var fromDate = new Date();
			fromDate.setFullYear(arrStart[2], arrStart[0] - 1, arrStart[1]);
			var toDate = new Date();
			toDate.setFullYear(arrEnd[2], arrEnd[0] - 1, arrEnd[1]);
			if (toDate >= fromDate) {
				return true;
			}
			else {
				alert("开始时间应该小于结束时间.");
				return false;
			}
		}else{
			return true;
		}
	}
});

