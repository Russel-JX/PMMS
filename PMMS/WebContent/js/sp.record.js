define(function(){
	$('.datepicker').datepicker();	
	//初始化出入库记录table
	var init = function(){
		var opts ={
			url:"../restful/sparePart/getSPTrans",
			data:{}
		}
		ajaxRequest(opts);
	}
	//ajax request
	function ajaxRequest(opts){
		$.ajax({
			type:"POST",
			url:opts.url,
			dataType:"json",
			async:true,
			data:opts.data,
			success:function(jsonData){
				if(jsonData.success){
					var html = '<table class="table table-striped table-bordered table-condensed" data-table-name="spinfoTable008">';
						html += '</table>';
					$("#wrappedspinfoTable008").empty();
					$("#wrappedspinfoTable008").append(html);
					buidTab009(jsonData.data);	
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(textStatus);
			}
		});
	}
	function buidTab009(data){
		var header=[{"sTitle": "流水号/SN","mData":"transId"},{"sTitle": "备件名称/SPNM","mData":"sparePartName"},
				 {"sTitle": "备件编号/SPSN","mData":"sparePartId"},{"sTitle": "规格型号/Type","mData":"sparePartType"},{"sTitle": "单位/Measurement","mData":"measurement"},
				 {"sTitle": "价格/Price(RMB)","mData":"price"},{"sTitle": "数量/Amount","mData":"amount"},{"sTitle": "出库Out/入库In","mData":"transType"},{"sTitle": "交付时间差/LeadTime","mData":"leadTime"},{"sTitle": "备注/Remark","mData":"remark"}];
		var oTable = $("#wrappedspinfoTable008 table").iidsBasicDataGrid({
			"isResponsive": true,
	        "useFloater": false,
	        "bAutoWidth": false,
	        "bDestroy": true,
			'aaSorting': [],
			"aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
			"aoColumns": header, 
	        "aaData": data.spTransList,
			"oColReorder": {"iFixedColumns": 15},
	        "fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
				if(aData['transType']=='1'){
					$('td:eq(7)',nRow).html("入库");
				}else{
					$('td:eq(7)',nRow).html("出库");
					$('td:eq(8)',nRow).html("");
				}		
			}
		});
	}
	$("#tab008 button:eq(0)").on('click',function(){
		var startDate = $("#tab008 .datepicker:eq(0)").val();
		var endDate = $("#tab008 .datepicker:eq(1)").val();
		if(startDate==""&&endDate!=""){
			alert("请输入查询开始时间");
			return false;
		}
		if(startDate!=""&&endDate==""){
			alert("请输入查询结束时间");
			return false;
		}
		if(!compareDate(startDate,endDate)){
			return false;
		}
		var opts ={
			url:"../restful/sparePart/getSPTrans",
			data:{
			startDate:startDate,
			endDate:endDate,
			transType:$("#tab008 select").val()	 
			}
		}
		ajaxRequest(opts);
	});
	
	//备件入库**********start************
	var spinfoData = null;
	
	$('#tab008 button:eq(1)').on('click',function(){
	    spinfoData = null;
		$("#inModal input:eq(0)").val("");
		$("#inModal p:eq(0)").text("");
		$("#inModal p:eq(1)").text("");
		$("#inModal p:eq(2)").text("");
		$("#inModal p:eq(3)").text("");
		$("#inModal input:eq(1)").val("");
		$("#inModal input:eq(2)").val("");
		$("#inModal input:eq(3)").val("");
		$("#inModal p:eq(4)").text("");
		$("#inModal textarea").val("");
		$("#inModal").modal('show');
	});
	
	$("#inModal input:eq(0)").on('change',function(){
		getSPInfo();
	});
	function getSPInfo(){
		var sparePartId = $("#inModal input:eq(0)").val().trim();
		if(sparePartId==""){
			alert("请输入备件编号");
			return false;
		}
		$.ajax({
			type:"POST",
			url:"../restful/sparePart/getSPInfoBySPId",
			dataType:"json",
			async:false,
			data:{
				sparePartId:sparePartId
			},
			success:function(jsonData){
				if (jsonData.success) {
				    spinfoData = jsonData.data.spInfo;
					if(spinfoData != null){
						$("#inModal p:eq(0)").text(spinfoData.sparePartName);
						$("#inModal p:eq(1)").text(spinfoData.sparePartType);
						$("#inModal p:eq(2)").text(spinfoData.source);
						$("#inModal p:eq(3)").text(spinfoData.measurement);
						$("#inModal input:eq(1)").val(spinfoData.price);
						$("#inModal p:eq(4)").text(spinfoData.stock);
						$("#inModal input:eq(2)").val("");
						$("#inModal textarea").val("");
						$("#inModal input:eq(2)").focus();
						
						
					}else{
						alert("查询的备件编码不存在，请添加备件信息后再入库");
					}
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				spinfoData = null;
				alert(textStatus);
			}
		});
	}
	//入库操作
	var inFlag = false;
	$("#inModal button:eq(1)").on('click',function(){
		var price = $("#inModal input:eq(1)").val().trim();
		var account = $("#inModal input:eq(2)").val().trim();
		var leadTime = $("#inModal input:eq(3)").val().trim();
		if(spinfoData == null){
			alert("请重新输入设备编号");
			return false;
		}
		
		var priceReg = /\d{1,10}(\.\d{1,2})?$/;
		var PriceRegFlag = priceReg.test(price);
		if(!PriceRegFlag){
			alert("价格格式不正确，正确格式为 0.00的形式");
			$("#inModal input:eq(1)").val("");
			return false;
		}
		//var reg = /^\d+(\.{0,1}\d+){0,1}$/;
		var reg = /^[1-9]*[1-9][0-9]*$/
		var regFlag = reg.test(account);
		if(!regFlag){
			alert("入库数量必须是正整数");
			$("#inModal input:eq(2)").val("");
			return false;
		}
		
		var lReg = /\d{1,10}(\.\d{1,2})?$/;
		var lRegFlag = lReg.test(leadTime);
		if(!lRegFlag){
			alert("请输入正确的交付时间差(day)");
			$("#inModal input:eq(3)").val("");
			return false;
		}
		if(!inFlag){
			//console.log("首次入库");
			$.ajax({
				type:"POST",
				url:"../restful/sparePart/spIn",
				dataType:"json",
				data:{
					price:price,
					account:account,
					leadTime:leadTime,
					remark:$("#inModal textarea").val(),
					spInfo:JSON.stringify(spinfoData)
				},
				beforeSend:function(XMLHttpRequest){
					inFlag = true;
				},
				complete:function(XMLHttpRequest, textStatus){
					inFlag = false;
				},
				success:function(jsonData){
					if(jsonData.success){
						getSPInfo();
						alert("入库成功");
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert(textStatus);
				}
			});
		}else{
			//console.log("重复入库操作");
		}
	})
	//关闭modal刷新数据
	$("#inModal button:eq(2)").on('click',function(){
		$("#inModal").modal('hide');
		init();
	});
	$("#inModal button:eq(0)").on('click',function(){
		init();
	});
	//备件入库**********end**************
	
	//备件出库**********shart*************
	var spInfoOut = null;
	$('#tab008 button:eq(2)').on('click',function(){
		spInfoOut = null;
		$("#outModal input").val("");
		$("#outModal p").text("");
		$("#outModal textarea").val("");
		$("#outModal").modal('show');
	})
	
	$("#outModal input:eq(0)").on('change',function(){
		getSPInfoBySPId();
	});
	
	function getSPInfoBySPId(){
		var sparePartId = $("#outModal input:eq(0)").val().trim();
		if(sparePartId==""){
			alert("请输入备件编号");
			return false;
		}
		$.ajax({
			type:"POST",
			url:"../restful/sparePart/getSPInfoBySPId",
			dataType:"json",
			data:{
				sparePartId:sparePartId
			},
			success:function(jsonDate){
				if(jsonDate.success){
					spInfoOut = jsonDate.data.spInfo;
					if(spInfoOut != null){
						//console.log("success");
						$("#outModal p:eq(0)").text(spInfoOut.sparePartName);
						$("#outModal p:eq(1)").text(spInfoOut.sparePartType);
						$("#outModal p:eq(2)").text(spInfoOut.source);
						$("#outModal p:eq(3)").text(spInfoOut.measurement);
						$("#outModal p:eq(4)").text(spInfoOut.stock);
						$("#outModal :checkbox").prop("checked",false);
						$("#outModal input[type='text']:eq(1)").val("");
						$("#outModal input[type='text']:eq(2)").val("");
						$("#outModal textarea").val("");
					}else{
						$("#outModal input[type='text']").val("");
						$("#outModal :checkbox").prop('checked',false);
						$("#outModal p").text("");
						$("#outModal textarea").val("");
						alert("查询的备件编码不存在，请确认后重新输入!");
						$("#outModal input:eq(0)").focus();
					}
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(textStatus);
			}
		});
	}
	//点击选择工单
	$("#outModal button:eq(1)").on('click',function(){
		$("#selectWOModal").modal('show')
		var opts = {
			url:"../restful/workOrder/getWOForSP",
			data:{}
		}
		ajaxRequestForWO(opts);
	});
	//多条件查询
	$("#selectWOModal button:eq(1)").on('click',function(){
		var startDate = $("#selectWOModal .datepicker:eq(0)").val();
		var endDate = $("#selectWOModal .datepicker:eq(1)").val();
		var deptId = $("#selectWOModal select:eq(0)").val();
		var equipType = $("#selectWOModal select:eq(1)").val();
		var workOrderType = $("#selectWOModal select:eq(2)").val();
		if(startDate==""&&endDate!=""){
			alert("请输入查询开始时间");
			return false;
		}
		if(startDate!=""&&endDate==""){
			alert("请输入查询结束时间");
			return false;
		}
		if(!compareDate(startDate,endDate)){
			return false;
		}
		var opts = {
			url:"../restful/workOrder/getWOForSP",
			data:{
				startDate:startDate,
				endDate:endDate,
				deptId:deptId,
				equipType:equipType,
				workOrderType:workOrderType
			}
		}
		ajaxRequestForWO(opts);
	});	
	function ajaxRequestForWO(opts){
		$.ajax({
			type:"POST",
			url:opts.url,
			dataType:"json",
			data:opts.data,
			success:function(jsonData){
				if(jsonData.success){
					var html = "";
					html+='<table class="table table-striped table-bordered table-condensed" data-table-name="WOTable009">';
					html+='</table>';
					$("#wrappedWOTable009").empty();
					$("#wrappedWOTable009").append(html);
					buildTab009(jsonData.data);
				}			
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(textStatus);
			}
		});
	}
	function buildTab009(data){
		var header = [{"sTitle": "选择","mData":"selectItem"},{"sTitle": "工单编号","mData":"workOrderId"},
					  {"sTitle": "设备名称","mData":"equipName"},{"sTitle": "设备编号","mData":"equipId"},
					  {"sTitle": "工单类型","mData":"workOrderType"},{"sTitle": "工单状态","mData":"workOrderStatus"},
					  {"sTitle": "备注","mData":"remark"}];
		var oTable = $("#wrappedWOTable009 table").iidsBasicDataGrid({
			"isResponsive": true,
	        "useFloater": false,
	        "bAutoWidth": false,
	        "bDestroy": true,
			'aaSorting': [],
			"aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
			"aoColumns":header, 
	        "aaData": data.orderList,
			"oColReorder": {"iFixedColumns": 15},
			"fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
				$('td:eq(0)',nRow).html('<input type="radio" name="radio" value="'+aData.workOrderId+'">');
			}
		});
	}
	
	//若备件与工单无关联
	$("#outModal :checkbox").on('click',function(){
		//console.log('checkbox click event')
		var checkedFalg = $(this).prop("checked");
		if(checkedFalg){
			$("#outModal input[type='text']:eq(1)").val("100000000000000000");
		}else{
			$("#outModal input[type='text']:eq(1)").val("");
		}
	});
	
	//选择关联对的工单
	$("#selectWOModal .btn-default:eq(0)").on('click',function(){
		var rdos = $("#selectWOModal :radio:checked");
		var num = rdos.size();
		if(num != 1){
			alert("请选择工单");
			return false;
		}
		$("#outModal :checkbox").prop('checked',false);
		$("#outModal input[type='text']:eq(1)").val(rdos.val());
		//console.log(rdos.val());
		$("#selectWOModal").modal("hide");
		$("#outModal input:eq(2)").focus();
	});
	
	//出库操作
	var outFalg = false;
	$("#outModal button:eq(2)").on('click',function(){
		if(spInfoOut == null){
			alert("请扫描备件编号...");
			return false;
		}
		var workOrderId = $("#outModal input[type='text']:eq(1)").val().trim();
		if(workOrderId == ""){
			alert("请选择关联的工单");
			return false;
		}
		var account = $("#outModal input[type='text']:eq(2)").val().trim();
		var reg = /^[1-9]*[1-9][0-9]*$/
		var regFlag = reg.test(account);
		if(!regFlag){
			alert("出库数量必须是正整数");
			$("#outModal input[type='text']:eq(2)").val("");
			return false;
		}
		var currentStock = spInfoOut.stock;
		if(parseInt(account) > parseInt(currentStock)){
			alert("库存不足");
			return false;
		}
		if(!outFalg){
			//console.log("首次点击");
			$.ajax({
				type:"POST",
				url:"../restful/sparePart/spOut",
				dataType:"json",
				data:{
					spInfo:JSON.stringify(spInfoOut),
					workOrderId:workOrderId,
					account:account,
					remark:$("#outModal textarea").val()
				},
				beforeSend:function(XMLHttpRequest){
					outFalg = true;
				},
				complete:function(XMLHttpRequest, textStatus){
					outFalg = false;
				},
				success:function(jsonData){
					if(jsonData.success){
						if(jsonData.data.feedback==null){
							getSPInfoBySPId();
							alert("出库成功");
							
						}else{
							getSPInfoBySPId();
							alert(jsonData.data.feedback);
						}					
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert(textStatus);
				}
			});
		}else{
			//console.log("重复点击");
		}
	});
	$("#outModal button:first,#outModal button:last").on('click',function(){
		init();
	});
	//备件出库**********end*************
	
	
	//export to excel
	$("#tab008 a[class='btn btn-icon']").on('click',function(){
		window.location.href="../restful/sparePart/spRecordsExportToExcel";
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
	return{
		init:init
	}
});
