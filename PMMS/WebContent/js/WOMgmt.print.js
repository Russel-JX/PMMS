require([ 'jquery','datagrids','ge-bootstrap','datepicker'], function() {
	var strData = $("#orderInfo2").val();
	var rowData = JSON.parse(strData);
	console.log(rowData);
	$("#woPrintModal span:eq(0)").text(rowData.workOrderId);  //工单号
	$("#woPrintModal span:eq(1)").text('');                   //申报部门
	$("#woPrintModal span:eq(2)").text(rowData.equipId);      //设备编号
	$("#woPrintModal span:eq(3)").text(rowData.equipName);    //设备名称
	$("#woPrintModal span:eq(4)").text(rowData.equipModel);   //设备型号
	$("#woPrintModal span:eq(5)").text(rowData.assetId);      //固定资产号
	
	$("#woPrintModal textarea:eq(0)").val(rowData.faultDescription);  //故障描述
	
	$("#woPrintModal :checkbox:eq(0)").prop("checked",rowData.shutdownFlag);    //是否需停机
	$("#woPrintModal :checkbox:eq(1)").prop("checked",rowData.safetyInvolved);  //涉及安全隐患
	
	var reqSSO = rowData.creator;//申报人sso
	var reqName=rowData.creatorLastName+" "+rowData.creatorFirtName;
	$("#woPrintModal span:eq(6)").text(reqName+"("+reqSSO+")");  //申报人
//    $.ajax({
//		type:"POST",
//		url:"../restful/workOrder/isUser",
//		dataType:"json",
//		async:true,
//		data:{
//			sso:reqSSO
//		},
//		success:function(jsonData){
//			if(jsonData.success){
//				if(jsonData.data.user != null){
//					var user = jsonData.data.user;
//					userName = user.lastName+" "+user.firstName;
//					//alert(userName);
//					$("#woPrintModal span:eq(6)").text(userName+"("+reqSSO+")");  //申报人
//				}
//			}
//		},
//		error:function(XMLHttpRequest, textStatus, errorThrown){
//			alert(textStatus);
//		}
//	});
	var dateStr = rowData.strCreateDate;
	temp1 = dateStr.substring(0,10);
	temp2 = dateStr.substring(11,19);
	$("#woPrintModal span:eq(7)").text(temp1);   //申报日期
	$("#woPrintModal span:eq(8)").text(temp2);   //申报时间
	
	var faultType = rowData.faultType;
	//console.log(faultType);
	//console.log(faultType==3);
	//console.log(faultType=='3');
	switch(faultType){                         //故障原因判断
		case '1':
			$("#woPrintModal textarea:eq(1)").val("电气故障");
			 break;
		case '2':
			$("#woPrintModal textarea:eq(1)").val("机械故障");
			 break;
		case '3':
			$("#woPrintModal textarea:eq(1)").val("液压故障");
			 break;
		case '4':
			$("#woPrintModal textarea:eq(1)").val("软件故障");
			 break;
		case '0':
			$("#woPrintModal textarea:eq(1)").val("其他故障");
			 break;
		default:$("#woPrintModal textarea:eq(1)").val("");
	}
	$("#woPrintModal textarea:eq(2)").val("");    //维修维护措施
	$.ajax({                                      //备件使用
		type:"POST",
		url:"../restful/sparePart/getSPTransByWOID",
		dataType:"json",
		async:true,
		data:{
			workOrderId:rowData.workOrderId
		},
		success:function(jsonData){
			if(jsonData.success){
				//console.log('success');
				var html = '<table class="table table-striped table-bordered table-condensed" data-table-name="tab010">';
					html += '</table>';
				var html2 = '无备件使用/NO Spare Part Is Used' 
					$("#wrappedTable010").empty();
					//console.log(jsonData.data.spareParts.length == 0);
				if(jsonData.data.spareParts.length == 0){
					$("#wrappedTable010").append(html2);
				}else{
					$("#wrappedTable010").append(html);
					buildTab010(jsonData.data);	
				}
			}
		},
		error:function(XMLHttpRequest, textStatus, errorThrown){
			alert(textStatus);
			//console.log(textStatus);
		}
	});
	$("#woPrintModal :checkbox:eq(3)").prop("checked",rowData.externalServiceInvolved); //需要外部技术服务 
	var mechianic = rowData.mechianic;
	var mechianicNM = rowData.mechianicLastName+" "+rowData.mechianicFirtName;
	$("#woPrintModal span:eq(9)").text(mechianicNM+"("+mechianic+")"); //维修人员签名
//	$.ajax({
//		type:"POST",
//		url:"../restful/workOrder/isUser",
//		dataType:"json",
//		async:true,
//		data:{
//			sso:mechianic
//		},
//		success:function(jsonData){
//			if(jsonData.success){
//				if(jsonData.data.user != null){
//					var user = jsonData.data.user;
//					mechianicNM = user.lastName+" "+user.firstName;
//					//alert(mechianicNM);
//					$("#woPrintModal span:eq(9)").text(mechianicNM+"("+mechianic+")"); //维修人员签名
//				}
//			}
//		},
//		error:function(XMLHttpRequest, textStatus, errorThrown){
//			
//		}
//	});
	
	$("#woPrintModal span:eq(10)").text(rowData.strMaintanceStartDate);                 //维修开始时间
	$("#woPrintModal span:eq(11)").text(rowData.strMaintanceEndDate);                   //维修结束时间
	$("#woPrintModal input:last").val(rowData.timeGap);                                 //停机时间(小时)
	
	//工单对应使用的备件,buildTab010 start
	function buildTab010(data){
		var header=[{"sTitle": "备件编号","mData":"sparePartId"},{"sTitle": "备件名称","mData":"sparePartName"},
			{"sTitle": "规格型号","mData":"sparePartType"},{"sTitle": "数量","mData":"amount"},
			{"sTitle": "入库价格","mData":"price"}];
		var oTable = $("#wrappedTable010 table").iidsBasicDataGrid({
		"isResponsive": true,
        "useFloater": false,
        "bAutoWidth": false,
        "bDestroy": true,
		'aaSorting': [],
		"aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
		"aoColumns": header, 
        "aaData": data.spareParts,
		"oColReorder": {"iFixedColumns": 15},
		});
	}// buildTab010 ended
	$("#woPrintModal button:last").on('click',function(){
		window.print();
	});
});