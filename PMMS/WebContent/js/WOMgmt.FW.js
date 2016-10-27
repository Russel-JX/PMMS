require([ 'jquery','pmms','datagrids','ge-bootstrap','datepicker'], function($,pmms) {
	var selectObj1 = $("#tab001 select:eq(0)");
	var selectObj2 = $("#tab002 select:eq(0)");
	pmms.getDeptInfo(selectObj1);
	pmms.getDeptInfo(selectObj2);
	//初始化-加载当前故障工单页面
	/**
	 * *1:orderType代表工单类型，1代表维修故障工单，2代表计划保养工单
	 * *2：orderFlag工单状态标识，1代表未关闭的工单(workOrderStatus=1,2),
	 * *  2代表关闭状态的工单(workOrderStatus=3) 
	 */
	$(function() {
		$('.datepicker').datepicker();	
		var opts={
			url:"../restful/Hitorical/getWorkOrders",
			data:{
				orderType:1,
				orderFlag:1,
				startDate:"",
				endDate:"",
				department:""
			}			
		};
		initTab001(opts);	
	});
	//点击当前故障工单tab,重新加载页面数据
	$("#tab001Nav").on('click',function(){
		var opts={
			url:"../restful/Hitorical/getWorkOrders",
			data:{
				orderType:1,
				orderFlag:1,
				startDate:"",
				endDate:"",
				department:""
			}
		}
		initTab001(opts);
	});
	//当前故障工单--条件查询
	$("#tab001 button").on('click',function(){
		var startDate=$("#tab001 .datepicker:eq(0)").val();
		var endDate=$("#tab001 .datepicker:eq(1)").val();
		var department=$("#tab001 select").val();
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
				orderType:1,
				orderFlag:1,
				startDate:startDate,
				endDate:endDate,
				department:department
			}
		}
		initTab001(opts);
	});
	//请求tab001的数据
	function initTab001(opts){
		$.ajax({
			type:"POST",
			url:opts.url,
			dataType:"json",
			data:opts.data,
			async:true,
			success:function(jsonData){
				if(jsonData.success){
				var orderhtml='';
					orderhtml+='<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable001">';
					orderhtml+='<thead>';
					orderhtml+='</thead>';
					orderhtml+='<tbody>';
					orderhtml+='</tbody>';
					orderhtml+='</table>';
					$("#wrappedWorkOrderTable001 table>tbody").empty();
					$("#wrappedWorkOrderTable001").html(orderhtml);
					buildWorkOrderTab001(jsonData.data);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("error"+textStatus);
			}
		});//ajax end
	}// initTab001 end
	function buildWorkOrderTab001(data){
//		var orderHeader=[{"sTitle": "工单号/WOSN","mData":"workOrderId"},{"sTitle": "设备编号/EQSN","mData":"equipId"},
//			 {"sTitle": "设备名称/EQNM","mData":"equipName"},{"sTitle": "设备型号/EQModel","mData":"equipModel"},
//			 {"sTitle": "固定资产号/AssetSN","mData":"assetId"},{"sTitle": "故障描述/FaultDesc","mData":"faultDescription"},
//			 {"sTitle": "申报人/Requestor","mData":"creator"},{"sTitle": "申报时间/ReqTime","mData":"strCreateDate"},
//			 {"sTitle": "涉及安全隐患/Safety","mData":"safetyInvolved"},{"sTitle":"是否需停机/Shutdown","mData":"shutdownFlag"},
//             {"sTitle": "维修工/Maintainer","mData":"mechianic"},{"sTitle": "维修开始时间/MaintStart","mData":"strMaintanceStartDate"},
//			 {"sTitle": "备注/Remark","mData":"remark"}];
			 
		var orderHeader = [{"sTitle": "工单号/WOSN","mData": "workOrderId"}, 
			{"sTitle": "设备编号/EQSN","mData": "equipId"}, {"sTitle": "设备名称/EQNM","mData": "equipName"}, {"sTitle": "设备型号/EQModel","mData": "equipModel"}, 
			{"sTitle": "固定资产号/AssetSN","mData": "assetId"}, {"sTitle": "故障描述/FaultDesc","mData": "faultDescription"}, 
			{"sTitle": "申报人/Requestor","mData": "creator"}, {"sTitle": "申报时间/RestTime","mData": "strCreateDate"}, {"sTitle": "涉及安全隐患/Safety","mData": "safetyInvolved"}, 
			{"sTitle": "是否需停机/Shutdown","mData": "shutdownFlag"}, {"sTitle": "维修工/Maintainer","mData": "mechianic"}, {"sTitle": "维修开始时间/MaintStart","mData": "strMaintanceStartDate"}, 
			{"sTitle": "是否等备件/WaitSP","mData": "sparePartInvolved"}, {"sTitle": "是否等外部服务/WaitService","mData": "externalServiceInvolved"}, 
			{"sTitle": "故障类型/FaultType","mData": "faultType"}, {"sTitle": "使用部门/Department","mData": "deptNM"},{"sTitle": "备注/Remark","mData": "remark"}];
		$("#wrappedWorkOrderTable001 table").iidsBasicDataGrid({
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
//	        if(aData.workOrderStatus==1){
//	        	$('td:eq(0)',nRow).css("background",'green');
//	        	$('td:eq(1)',nRow).css("background",'green');
//	        	$('td:eq(2)',nRow).css("background",'green');
//	        	$('td:eq(3)',nRow).css("background",'green');
//	        	$('td:eq(4)',nRow).css("background",'green');
//	        	$('td:eq(5)',nRow).css("background",'green');
//	        	$('td:eq(6)',nRow).css("background",'green');
//	        	$('td:eq(7)',nRow).css("background",'green');
//	        	$('td:eq(8)',nRow).css("background",'green');
//	        	$('td:eq(9)',nRow).css("background",'green');
//	        	$('td:eq(10)',nRow).css("background",'green');
//	        	$('td:eq(11)',nRow).css("background",'green');
//	        	$('td:eq(12)',nRow).css("background",'green');
//	        	$('td:eq(13)',nRow).css("background",'green');
//	        }else if(aData.workOrderStatus==2){
//	        	$('td:eq(0)',nRow).css("background",'orange');
//	        	$('td:eq(1)',nRow).css("background",'orange');
//	        	$('td:eq(2)',nRow).css("background",'orange');
//	        	$('td:eq(3)',nRow).css("background",'orange');
//	        	$('td:eq(4)',nRow).css("background",'orange');
//	        	$('td:eq(5)',nRow).css("background",'orange');
//	        	$('td:eq(6)',nRow).css("background",'orange');
//	        	$('td:eq(7)',nRow).css("background",'orange');
//	        	$('td:eq(8)',nRow).css("background",'orange');
//	        	$('td:eq(9)',nRow).css("background",'orange');
//	        	$('td:eq(10)',nRow).css("background",'orange');
//	        	$('td:eq(11)',nRow).css("background",'orange');
//	        	$('td:eq(12)',nRow).css("background",'orange');
//	        	$('td:eq(13)',nRow).css("background",'orange');
//	        }
//				if(aData.safetyInvolved==true){
//					$('td:eq(8)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
//				}else{
//					$('td:eq(8)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
//				}
//				if(aData.shutdownFlag==true){
//					$('td:eq(9)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
//				}else{
//					$('td:eq(9)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
//				}
                if (aData.workOrderStatus == 1) {
                    $('td:eq(0)', nRow).css("background", 'green');
                    $('td:eq(1)', nRow).css("background", 'green');
                    $('td:eq(2)', nRow).css("background", 'green');
                    $('td:eq(3)', nRow).css("background", 'green');
                    $('td:eq(4)', nRow).css("background", 'green');
                    $('td:eq(5)', nRow).css("background", 'green');
                    $('td:eq(6)', nRow).css("background", 'green');
                    $('td:eq(7)', nRow).css("background", 'green');
                    $('td:eq(8)', nRow).css("background", 'green');
                    $('td:eq(9)', nRow).css("background", 'green');
                    $('td:eq(10)', nRow).css("background", 'green');
                    $('td:eq(11)', nRow).css("background", 'green');
                    $('td:eq(12)', nRow).css("background", 'green').html("");
                    $('td:eq(13)', nRow).css("background", 'green').html("");
                    $('td:eq(14)', nRow).css("background", 'green');
					$('td:eq(15)', nRow).css("background", 'green');
					$('td:eq(16)', nRow).css("background", 'green');
                }else if (aData.workOrderStatus == 2) {
                        $('td:eq(0)', nRow).css("background", 'orange');
                        $('td:eq(1)', nRow).css("background", 'orange');
                        $('td:eq(2)', nRow).css("background", 'orange');
                        $('td:eq(3)', nRow).css("background", 'orange');
                        $('td:eq(4)', nRow).css("background", 'orange');
                        $('td:eq(5)', nRow).css("background", 'orange');
                        $('td:eq(6)', nRow).css("background", 'orange');
                        $('td:eq(7)', nRow).css("background", 'orange');
                        $('td:eq(8)', nRow).css("background", 'orange');
                        $('td:eq(9)', nRow).css("background", 'orange');
                        $('td:eq(10)', nRow).css("background", 'orange');
                        $('td:eq(11)', nRow).css("background", 'orange');
                        $('td:eq(12)', nRow).css("background", 'orange');
                        $('td:eq(13)', nRow).css("background", 'orange');
                        $('td:eq(14)', nRow).css("background", 'orange');
						$('td:eq(15)', nRow).css("background", 'orange');
						$('td:eq(16)', nRow).css("background", 'orange');
                        if (aData.sparePartInvolved == true) {
                            $('td:eq(12)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
                        }
                        else {
                            $('td:eq(12)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
                        }
                        if (aData.externalServiceInvolved == true) {
                            $('td:eq(13)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
                        }
                        else {
                            $('td:eq(13)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
                        }
                    }
                if (aData.safetyInvolved == true) {
                    $('td:eq(8)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
                }
                else {
                    $('td:eq(8)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
                }
                if (aData.shutdownFlag == true) {
                    $('td:eq(9)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
                }
                else {
                    $('td:eq(9)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
                }
				
				switch(aData.faultType){
					case '1':
						$('td:eq(14)', nRow).html('电气故障');
						break;
					case '2':
						$('td:eq(14)', nRow).html('机械故障');
						break;
					case '3':
						$('td:eq(14)', nRow).html('液压故障');
						break;
					case '4':
						$('td:eq(14)', nRow).html('软件故障');
						break;
					case '0':
						$('td:eq(14)', nRow).html('其他故障');
						break;
					default:$('td:eq(14)', nRow).html('');
				}
				
				if((aData.creatorLastName!="") || (aData.creatorFirtName!="")){
					$('td:eq(6)', nRow).html(aData.creator+"("+aData.creatorLastName+" "+aData.creatorFirtName+")");
				}else{
					$('td:eq(6)', nRow).html(aData.creator);
				}
				
				if((aData.mechianicLastName!="") || (aData.mechianicFirtName!="")){
					$('td:eq(10)', nRow).html(aData.mechianic+"("+aData.mechianicLastName+" "+aData.mechianicFirtName+")");
				}else{
					$('td:eq(10)', nRow).html(aData.mechianic);
				}
			} 
		});
	}//buildWorkOrderTab001 ended
	
	//********************************tab002 start*********************************
	//点击历史故障工单tab,加载数据
	$("#tab002Nav").on('click',function(){
		var opts={
			url:"../restful/Hitorical/getWorkOrders",
			data:{
				orderType:1,
				orderFlag:2,
				startDate:"",
				endDate:"",
				department:""
			}
		}
		initTab002(opts);
	});
	//历史故障工单条件查询
	$("#tab002 button:eq(0)").on('click',function(){
		var startDate=$("#tab002 .datepicker:eq(0)").val();
		var endDate=$("#tab002 .datepicker:eq(1)").val();
		var department=$("#tab002 select").val();
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
				orderType:1,
				orderFlag:2,
				startDate:startDate,
				endDate:endDate,
				department:department
			}
		}
		initTab002(opts);
	});
	function initTab002(opts){
		$.ajax({
			type:"POST",
			url:opts.url,
			dataType:"json",
			data:opts.data,
			success:function(jsonData){
				if(jsonData.success){
				var orderhtml='';
					orderhtml+='<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable002">';
					orderhtml+='<thead>';
					orderhtml+='</thead>';
					orderhtml+='<tbody>';
					orderhtml+='</tbody>';
					orderhtml+='</table>';
					$("#wrappedWorkOrderTable002 table>tbody").empty();
					$("#wrappedWorkOrderTable002").html(orderhtml);
					buildWorkOrderTab002(jsonData.data);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("error"+textStatus);
			}
		});//ajax end
	}// initTab002 end
	function buildWorkOrderTab002(data){
		var orderHeader=[{"sTitle": "选择/Select","mData":"selectItem"},{"sTitle": "工单号/WOSN","mData":"workOrderId"},{"sTitle": "设备编号/EQSN","mData":"equipId"},
			 {"sTitle": "设备名称/EQNM","mData":"equipName"},{"sTitle": "设备型号EQModel","mData":"equipModel"},
			 {"sTitle": "固定资产号/AssetSN","mData":"assetId"},{"sTitle": "故障描述/FaultDesc","mData":"faultDescription"},
			 {"sTitle": "申报人/Requestor","mData":"creator"},{"sTitle": "申报时间/ReqTime","mData":"strCreateDate"},
			 {"sTitle": "涉及安全隐患/Safety","mData":"safetyInvolved"},{"sTitle":"是否需停机/Shutdown","mData":"shutdownFlag"},
             {"sTitle": "维修工/Maintainer","mData":"mechianic"},{"sTitle": "维修开始时间/MaintStart","mData":"strMaintanceStartDate"},{"sTitle": "维修结束时间/MaintClose","mData":"strMaintanceEndDate"},
			 {"sTitle": "是否等备件/WaitSP","mData":"sparePartInvolved"},{"sTitle": "是否等外部服务/WaitService","mData":"externalServiceInvolved"},
			 {"sTitle": "故障类型/FaultType","mData": "faultType"},{"sTitle": "使用部门/Department","mData": "deptNM"},{"sTitle": "备注/Remark","mData":"remark"}];
		var oTable = $("#wrappedWorkOrderTable002 table").iidsBasicDataGrid({
		"isResponsive": true,
        "useFloater": false,
        "bAutoWidth": false,
        "bDestroy": true,
		'aaSorting': [],
		"aoColumns": orderHeader, 
        "aaData": data.workOrders,
		"oColReorder": {"iFixedColumns": 15},
        "fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull){  
			$('td:eq(0)', nRow).html('<input type="radio" name="radio" />');
            if (aData.sparePartInvolved == true) {
            	$('td:eq(14)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
            }else {
                $('td:eq(14)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
             }
            if (aData.externalServiceInvolved == true) {
            	$('td:eq(15)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
            }else {
                $('td:eq(15)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
           	}
                    
            if (aData.safetyInvolved == true) {
                $('td:eq(9)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
            }
            else {
                $('td:eq(9)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
            }
            if (aData.shutdownFlag == true) {
                $('td:eq(10)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
            }
            else {
                $('td:eq(10)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
            }
				
			switch(aData.faultType){
				case '1':
					$('td:eq(16)', nRow).html('电气故障');
					break;
				case '2':
					$('td:eq(16)', nRow).html('机械故障');
					break;
				case '3':
					$('td:eq(16)', nRow).html('液压故障');
					break;
				case '4':
					$('td:eq(16)', nRow).html('软件故障');
					break;
				case '0':
					$('td:eq(16)', nRow).html('其他故障');
					break;
				default:$('td:eq(16)', nRow).html('');
			}
			if((aData.creatorLastName!="") || (aData.creatorFirtName!="")){
				$('td:eq(7)', nRow).html(aData.creator+"("+aData.creatorLastName+" "+aData.creatorFirtName+")");
			}else{
				$('td:eq(7)', nRow).html(aData.creator);
			}
			
			if((aData.mechianicLastName!="") || (aData.mechianicFirtName!="")){
				$('td:eq(11)', nRow).html(aData.mechianic+"("+aData.mechianicLastName+" "+aData.mechianicFirtName+")");
			}else{
				$('td:eq(11)', nRow).html(aData.mechianic);
			}
		} 
	});
	//初始化--修改工单内容	
	var rowData = null;
	var editFlag = false;
	oTable = $("#wrappedWorkOrderTable002 table").dataTable();
	$("#woEditButton").unbind('click').on('click',function(){
		//console.log("click event");
		editFlag=false;
		var num = $("#tab002 :radio:checked").length;
		if(num==0){
			alert("请选择一条记录进行修改");
			//console.log("请选择一条记录进行修改");
			return false;
		}else{
			var pos = $("#tab002 :radio:checked").parent().parent()[0];
			rowData = oTable.fnGetData(pos);
			//console.log(rowData.workOrderId);
			$("#woUpdateModal").modal('show');  
			
			$("#woUpdateModal input:eq(0)").val(rowData.workOrderId);
			$("#woUpdateModal input:eq(1)").val(rowData.equipId);
			$("#woUpdateModal input:eq(2)").val(rowData.equipName);
			$("#woUpdateModal input:eq(3)").val(rowData.equipModel);
			$("#woUpdateModal input:eq(4)").val(rowData.assetId);
			$("#woUpdateModal input:eq(5)").val(rowData.creator);
			$("#woUpdateModal input:eq(6)").val(rowData.strCreateDate);
			$("#woUpdateModal input:eq(7)").val(rowData.mechianic);
			$("#woUpdateModal input:eq(8)").val(rowData.strMaintanceStartDate);
			$("#woUpdateModal input:eq(9)").val(rowData.strMaintanceEndDate);
			$("#woUpdateModal textarea:eq(0)").val(rowData.faultDescription);
			$("#woUpdateModal textarea:eq(1)").val(rowData.remark);
			$("#woUpdateModal select:eq(0)").val(rowData.deptId);
			$("#woUpdateModal select:eq(1)").val(rowData.faultType);
		}		
	});
	//保存-修改后的工单内容
	$("#woUpdateModal button:eq(1)").unbind('click').on('click',function(){
		var faultDescription = $("#woUpdateModal textarea:eq(0)").val();
		var faultType = $("#woUpdateModal select:eq(1)").val();
		var remark = $("#woUpdateModal textarea:eq(1)").val();
		
		rowData.faultDescription = faultDescription;
		rowData.faultType = faultType;
		rowData.remark = remark;
		if(!editFlag){
			$.ajax({
				type:"POST",
				url:"../restful/Hitorical/updateWorkOrder",
				dataType:"json",
				data:{
					workOrderInfo:JSON.stringify(rowData)  
				},
				beforeSend:function(XMLHttpRequest){
					editFlag = true;
				},
				success:function(jsondata){
					if(jsondata.success){
						$("#woUpdateModal").modal('hide');
						alert("update"+rowData.workOrderId+"successfully!");
						var opts={
							url:"../restful/Hitorical/getWorkOrders",
							data:{
								orderType:1,
								orderFlag:2,
								startDate:"",
								endDate:"",
								department:""
							}
						}
						initTab002(opts);
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert(textStatus);
				}
			});
		}else{
			//console.log("故障维修工单重复update");
		}
	});
	//初始化打印内容
		$("#woPrintButton").unbind('click').on('click',function(){
		//console.log("click event");
		var num = $("#tab002 :radio:checked").length;
		if(num==0){
			alert("请选择一条记录进行打印");
			return false;
		}else{			
			var pos = $("#tab002 :radio:checked").parent().parent()[0];
			var rowData = oTable.fnGetData(pos);
//			console.log(rowData.workOrderId);
//			$("#woPrintModal").modal('show'); 
			$("#orderInfoForm input").val(JSON.stringify(rowData));
			$("#orderInfoForm").submit();
 
			
//			$("#woPrintModal span:eq(0)").text(rowData.workOrderId);
//			$("#woPrintModal span:eq(1)").text('');
//			$("#woPrintModal span:eq(2)").text(rowData.equipId);
//			$("#woPrintModal span:eq(3)").text(rowData.equipName);
//			$("#woPrintModal span:eq(4)").text(rowData.equipModel);
//			$("#woPrintModal span:eq(5)").text(rowData.assetId);
//			
//			$("#woPrintModal textarea:eq(0)").val(rowData.faultDescription);
//			$("#woPrintModal textarea:eq(1)").val("");
//			
//			$("#woPrintModal :checkbox:eq(0)").prop("checked",rowData.shutdownFlag);
//			$("#woPrintModal :checkbox:eq(1)").prop("checked",rowData.safetyInvolved);
//			
//			$("#woPrintModal span:eq(6)").text(rowData.creator);
//			var dateStr = rowData.strCreateDate;
//			temp1 = dateStr.substring(0,10);
//			temp2 = dateStr.substring(11,19);
//			$("#woPrintModal span:eq(7)").text(temp1);
//			$("#woPrintModal span:eq(8)").text(temp2);
//			
//			var faultType = rowData.faultType;
//			console.log(faultType);
//			console.log(faultType==3);
//			console.log(faultType=='3');
//			switch(faultType){
//				case '1':
//					$("#woPrintModal textarea:eq(2)").val("故障类型1");
//					 break;
//				case '2':
//					$("#woPrintModal textarea:eq(2)").val("故障类型2");
//					 break;
//				case '3':
//					$("#woPrintModal textarea:eq(2)").val("故障类型3");
//					 break;
//				case '4':
//					$("#woPrintModal textarea:eq(2)").val("故障类型4");
//					 break;
//				case '5':
//					$("#woPrintModal textarea:eq(2)").val("故障类型5");
//					 break;
//				case '0':
//					$("#woPrintModal textarea:eq(2)").val("其他故障");
//					 break;
//				default:$("#woPrintModal textarea:eq(2)").val("");
//			}
//			$("#woPrintModal textarea:eq(3)").val("");
//			$.ajax({
//				type:"POST",
//				url:"../restful/sparePart/getSPTransByWOID",
//				dataType:"json",
//				data:{
//					workOrderId:rowData.workOrderId
//				},
//				success:function(jsonData){
//					if(jsonData.success){
//						console.log('success');
//						var html = '<table class="table table-striped table-bordered table-condensed" data-table-name="tab010">';
//						html += '</table>';
//						$("#wrappedTable010").empty();
//						$("#wrappedTable010").append(html);
//						buildTab010(jsonData.data);	
//					}
//				},
//				error:function(XMLHttpRequest, textStatus, errorThrown){
//					console.log(textStatus);
//				}
//			});
//			$("#woPrintModal :checkbox:eq(3)").prop("checked",rowData.externalServiceInvolved);
//			$("#woPrintModal span:eq(9)").text(rowData.strMaintanceStartDate);
//			$("#woPrintModal span:eq(10)").text(rowData.strMaintanceEndDate);
		}		
	});
	}//buildWorkOrderTab002 ended	
	
	//工单对应使用的备件,buildTab010 start
//	function buildTab010(data){
//		var header=[{"sTitle": "备件编号","mData":"sparePartId"},{"sTitle": "备件名称","mData":"sparePartName"},
//			{"sTitle": "规格型号","mData":"sparePartType"},{"sTitle": "数量","mData":"amount"},
//			{"sTitle": "入库价格","mData":"price"}];
//		var oTable = $("#wrappedTable010 table").iidsBasicDataGrid({
//		"isResponsive": true,
//        "useFloater": false,
//        "bAutoWidth": false,
//        "bDestroy": true,
//		'aaSorting': [],
//		"aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
//		"aoColumns": header, 
//        "aaData": data.spareParts,
//		"oColReorder": {"iFixedColumns": 15},
//		});
//	}// buildTab010 ended
	
//	$("#woPrintModal button:eq(1)").on('click',function(){
//		  $("#printArea").printArea();
//	});

	$("#tab001 a[class='btn btn-icon']").on('click',function(){
		window.location.href = "../restful/workOrder/curFMWOExportToExcel";
	});
	
	$("#tab002 a[class='btn btn-icon']").on('click',function(){
		window.location.href = "../restful/workOrder/hisFMWOExportToExcel";
	});
	// ****************common js**********************
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
