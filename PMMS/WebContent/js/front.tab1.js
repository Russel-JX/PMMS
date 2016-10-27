define(['pmms'],function(pmms){
	var selectObj = $("#workOrderflow1 select:eq(0)");
	pmms.getDeptInfo(selectObj);
	/**
	 * *1:初始化tab01
	 * *2:orderType代表工单类型，1代表维修故障工单，2代表计划保养工单
	 * *3：orderFlag工单状态标识，1代表未关闭的工单(workOrderStatus=1,2),
	 * *  2代表关闭状态的工单(workOrderStatus=3) 
	 */
	var init = function(){
		$("#tab01 input:eq(0)").val("");
		$("#tab01 input:eq(1)").val("");
		$("#tab01 textarea").val("");
		$("#tab01 :checkbox:eq(0)").prop("checked",false);
		$("#tab01 :checkbox:eq(1)").prop("checked",true);
		$("#tab01 select:eq(1)").val("");
		$("#tab01 span:eq(0)").text("");
		$("#tab01 span:eq(1)").text("");
		var opts={
			url:"../restful/workOrder/getWorkOrders",
			data:{
				orderType:1,
				orderFlag:1,
				deptId:$("#workOrderflow1 select:eq(0)").val()		
			}
		};
		ajaxRequest(opts);
	};
	//数据请求tab01
	function ajaxRequest(opts){
		$.ajax({
			type:"POST",
			url:opts.url,
			dataType:"json",
			async:true,
			data:opts.data,
			success: function(jsonData){
				if(jsonData.success){
					var orderhtml='';
						orderhtml+='<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable1">';
						orderhtml+='<thead>';
						orderhtml+='</thead>';
						orderhtml+='<tbody>';
						orderhtml+='</tbody>';
						orderhtml+='</table>';
						$("#wrappedWorkOrderTable1 table>tbody").empty();
						$("#wrappedWorkOrderTable1").html(orderhtml);
						buildWorkOrderTab1(jsonData.data);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert("error"+textStatus);
			}
		});//ajax end
	}//init end
	function buildWorkOrderTab1(data){
		var orderHeader=[{"sTitle": "工单号/WOSN","mData":"workOrderId"},{"sTitle": "设备编号/EQSN","mData":"equipId"},
						 {"sTitle": "设备名称/EQNM","mData":"equipName"},{"sTitle": "设备型号/EQModel","mData":"equipModel"},
						 {"sTitle": "固定资产号/AssetSN","mData":"assetId"},{"sTitle": "故障描述/FaultDesc","mData":"faultDescription"},
						 {"sTitle": "申报人/Requestor","mData":"creator"},{"sTitle": "申报时间/ReqTime","mData":"strCreateDate"},
						 {"sTitle": "涉及安全隐患/Safety","mData":"safetyInvolved"},{"sTitle":"是否需停机/Shutdown","mData":"shutdownFlag"},
		                 {"sTitle": "维修工/Maintainer","mData":"mechianic"},{"sTitle": "维修开始时间/MaintStart","mData":"strMaintanceStartDate"},
						 {"sTitle": "使用部门/Department","mData":"deptNM"},{"sTitle": "备注/Remark","mData":"remark"}];
		$("#wrappedWorkOrderTable1 table").iidsBasicDataGrid({
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
		        	$('td:eq(11)',nRow).css("background",'green');
		        	$('td:eq(12)',nRow).css("background",'green');
		        	$('td:eq(13)',nRow).css("background",'green');
					$('td:eq(14)',nRow).css("background",'green');
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
		        	$('td:eq(11)',nRow).css("background",'orange');
		        	$('td:eq(12)',nRow).css("background",'orange');
		        	$('td:eq(13)',nRow).css("background",'orange');
					$('td:eq(14)',nRow).css("background",'orange');
		        }
				if(aData.safetyInvolved){
					$('td:eq(8)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
				}else{
					$('td:eq(8)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
				}
				if(aData.shutdownFlag){
					$('td:eq(9)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
				}else{
					$('td:eq(9)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
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
	}
	//提交工单
	//提交标志，防止用户重复提交
	var submitFlag = false;
	$("#saveWorkOrderInfo").click(function(){
		var sso = $("#tab01 input:eq(0)").val();
		var equipId = $("#tab01 input:eq(1)").val();
		var faultDescription = $("#tab01 textarea").val();
		var safetyInvolved = $("#tab01 :checkbox:eq(0)").prop("checked");
		var shutdownFlag = $("#tab01 :checkbox:eq(1)").prop("checked");
		if(sso=="" || equipId==""){
			alert("SSO和设备代码不能为空");
			//console.log("sso和equipID 不能为空");
			return false;
		}
		var data = {
			sso: sso,
			equipId: equipId,
			faultDescription: faultDescription,
			safetyInvolved: safetyInvolved,
			shutdownFlag: shutdownFlag
		}
		if(!submitFlag){
			//console.log("首次提交");
			$.ajax({
				type:"POST",
				url:"../restful/workOrder/saveWorkOrderInfo",
				dataType:"json",
				async:true,
				data:data,
				beforeSend:function(XMLHttpRequest){
					submitFlag = true;
				},
				complete:function (XMLHttpRequest, textStatus){
					submitFlag = false;
				},
				success: function(jsonData){
					if(jsonData.success){
						if(jsonData.data.addWO=="success"){
							init();
							alert("提交工单成功！！！");	
						}else{
							$("#tab01 input:eq(0)").val("");
							$("#tab01 input:eq(1)").val("");
							$("#tab01 textarea").val("");
							$("#tab01 :checkbox:eq(0)").prop("checked",false);
							$("#tab01 :checkbox:eq(1)").prop("checked",true);
							$("#tab01 select:eq(1)").val("");
							$("#tab01 span:eq(0)").text("");
							$("#tab01 span:eq(1)").text("");
							alert("员工编号 或 设备编号不正确!");
						}
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert("服务器连接"+textStatus);
				}
			});//ajax end
		}else{
			//console.log("重复提交");
		}
	});	
	//验证SSO是否正确
	$("#tab01 input:eq(0)").on('blur',function(){
		var sso = $(this).val();
		//console.log("sso "+sso);
		if(sso == "" || sso == null){
			alert("SSO不能为空，请输入!");
			return false;
		}
		var ssoReg = /^[0-9]*$/;
		var ssoRegFlag = ssoReg.test(sso);
		if(!ssoRegFlag){
			$("#tab01 input:eq(0)").val("");
			alert("SSO输入无效，请重新输入!!!");
			return false;
		}
		$.ajax({
			type:"POST",
			url:"../restful/workOrder/isUser",
			dataType:"json",
			async:false,
			data:{
				sso:sso
			},
			success:function(jsonData){
				if(jsonData.success){
					var user = jsonData.data.user;
					if(user != null){
						$("#tab01 span:eq(0)").text("("+user.lastName+" "+user.firstName+")");
					}else{
						$("#tab01 input:eq(0)").val("");
						$("#tab01 span:eq(0)").text("");
						alert("SSO不正确，请重新输入");
					}
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				$("#tab01 span:eq(0)").text("");
				alert(textStatus);
			}
		});
	});
	//判断设备编号是否正确
	$("#tab01 input:eq(1)").on('blur',function(){
		var equipId = $(this).val();
		if(equipId == ""){
			alert("设备编号不能为空，请输入");
			return false;
		}
		$.ajax({
			type:"POST",
			url:"../restful/workOrder/isEquip",
			dataType:"json",
			async:false,
			data:{
				equipId:equipId
			},
			success: function(jsonData){
				if(jsonData.success){
					var equip = jsonData.data.equip;
					if(equip != null){
						//console.log(jsonData.data.equip==null);
						$("#tab01 span:eq(1)").text("("+equip.equipmentName+" "+")");
						
					}else{
						$("#tab01 input:eq(1)").val("");
						$("#tab01 span:eq(1)").text("");
						alert("设备代码不正确，请重新输入");
					}
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				$("#tab01 span:eq(1)").text("");
				alert("error"+textStatus);
			}
		});//ajax end
	});
	//条件查询--区域
	$("#workOrderflow1 select:eq(0)").change(function(){
//		$("#tab01 input:eq(0)").val("");
//		$("#tab01 input:eq(1)").val("");
//		$("#tab01 textarea").val("");
//		$("#tab01 :checkbox:eq(0)").prop("checked",false);
//		$("#tab01 :checkbox:eq(1)").prop("checked",true);
		var opts={
			url:"../restful/workOrder/getWorkOrders",
			data:{
				orderType:1,
				orderFlag:1,
				deptId:$("#workOrderflow1 select:eq(0)").val(),
				equipType:$("#workOrderflow1 select:eq(1)").val()			
			}
		}
		ajaxRequest(opts)
		//console.log("change event1 ");
	});
	//条件查询--设备种类
	$("#workOrderflow1 select:eq(1)").change(function(){
//		$("#tab01 input:eq(0)").val("");
//		$("#tab01 input:eq(1)").val("");
//		$("#tab01 textarea").val("");
//		$("#tab01 :checkbox:eq(0)").prop("checked",false);
//		$("#tab01 :checkbox:eq(1)").prop("checked",true);
		var opts={
			url:"../restful/workOrder/getWorkOrders",
			data:{
				orderType:1,
				orderFlag:1,
				deptId:$("#workOrderflow1 select:eq(0)").val(),
				equipType:$("#workOrderflow1 select:eq(1)").val()		
			}
		}
		ajaxRequest(opts)
		//console.log("change event2 ");
	});
	return {
		init: init
	}
});