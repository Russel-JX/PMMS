define(['pmms'],function(pmms){
	var selectObj = $("#workOrderflow2 select:eq(0)");
	var faultTypeSeclect = $("#tab02 select:eq(0)");
	pmms.getDeptInfo(selectObj);
	pmms.getFaultType(faultTypeSeclect)
	/**
	 * *1:初始化tab02
	 * *2:orderType代表工单类型，1代表维修故障工单，2代表计划保养工单
	 * *3：orderFlag工单状态标识，1代表未关闭的工单(workOrderStatus=1,2),
	 * *  2代表关闭状态的工单(workOrderStatus=3) 
	 */
    var init = function(){
		clearData02();
		abledItem();
		var opts={
			url:"../restful/workOrder/getWorkOrders",
			data:{
				orderType:1,
				orderFlag:1,
				deptId:$("#workOrderflow2 select:eq(0)").val()		
			}
		};
		ajaxRequest(opts);
    }; //init end
	
	//请求数据tab02
	function ajaxRequest(opts){
		$.ajax({
            type: "POST",
            url: opts.url,
            dataType: "json",
			async:true,
			data: opts.data,
            success: function(jsonData){
                if (jsonData.success) {
                    var orderhtml = '';
                    orderhtml += '<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable2">';
                    orderhtml += '<thead>';
                    orderhtml += '</thead>';
                    orderhtml += '<tbody>';
                    orderhtml += '</tbody>';
                    orderhtml += '</table>';
					$("#wrappedWorkOrderTable2 table>tbody").empty();
                    $("#wrappedWorkOrderTable2").html(orderhtml);
                    buildWorkOrderTab2(jsonData.data);
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                alert("error" + textStatus);
            }
        });//ajax end
	}//ajaxRequest end
	
    function buildWorkOrderTab2(data){
        var orderHeader = [{
            "sTitle": "选择/Select",
            "mData": "selectItem"
        },{
            "sTitle": "工单号/WOSN",
            "mData": "workOrderId"
        }, {
            "sTitle": "设备编号/EQSN",
            "mData": "equipId"
        }, {
            "sTitle": "设备名称/EQNM",
            "mData": "equipName"
        }, {
            "sTitle": "设备型号/EQModel",
            "mData": "equipModel"
        }, {
            "sTitle": "固定资产号/AssetSN",
            "mData": "assetId"
        }, {
            "sTitle": "故障描述/FaultDesc",
            "mData": "faultDescription"
        }, {
            "sTitle": "申报人/Requestor",
            "mData": "creator"
        }, {
            "sTitle": "申报时间/RestTime",
            "mData": "strCreateDate"
        }, {
            "sTitle": "涉及安全隐患/Safety",
            "mData": "safetyInvolved"
        }, {
            "sTitle": "是否需停机/Shutdown",
            "mData": "shutdownFlag"
        }, {
            "sTitle": "维修工/Maintainer",
            "mData": "mechianic"
        }, {
            "sTitle": "维修开始时间/MaintStart",
            "mData": "strMaintanceStartDate"
        }, {
            "sTitle": "是否等备件/WaitSP",
            "mData": "sparePartInvolved"
        }, {
            "sTitle": "是否等外部服务/WaitService",
            "mData": "externalServiceInvolved"
        }, {
            "sTitle": "故障类型/FaultType",
            "mData": "faultType"
        }, {
            "sTitle": "使用部门/Department",
            "mData": "deptNM"
        }, {
            "sTitle": "备注/Remark",
            "mData": "remark"
        }];
        $("#wrappedWorkOrderTable2 table").iidsBasicDataGrid({
            "isResponsive": true,
            "useFloater": false,
            "bAutoWidth": true,
            "bDestroy": true,
            'aaSorting': [],
            "aoColumns": orderHeader,
            "aaData": data.workOrders,
            "oColReorder": {
                "iFixedColumns": 15
            },
            "fnRowCallback": function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
                //console.log(aData.workOrderStatus);
				 $('td:eq(0)', nRow).html('<input type="radio" name="radio" targetName="workOrderInfo"/>');
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
                    $('td:eq(12)', nRow).css("background", 'green')
                    $('td:eq(13)', nRow).css("background", 'green').html("");
                    $('td:eq(14)', nRow).css("background", 'green').html("");
					$('td:eq(15)', nRow).css("background", 'green');
					$('td:eq(16)', nRow).css("background", 'green');
					$('td:eq(17)', nRow).css("background", 'green');
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
						$('td:eq(17)', nRow).css("background", 'orange');
                        if (aData.sparePartInvolved) {
                            $('td:eq(13)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
                        }
                        else {
                            $('td:eq(13)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
                        }
                        if (aData.externalServiceInvolved) {
                            $('td:eq(14)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
                        }
                        else {
                            $('td:eq(14)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
                        }
                    }
                if (aData.safetyInvolved) {
                    $('td:eq(9)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
                }
                else {
                    $('td:eq(9)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
                }
                if (aData.shutdownFlag) {
                    $('td:eq(10)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
                }
                else {
                    $('td:eq(10)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
                }
				
				switch(aData.faultType){
					case '1':
						$('td:eq(15)', nRow).html('电气故障');
						break;
					case '2':
						$('td:eq(15)', nRow).html('机械故障');
						break;
					case '3':
						$('td:eq(15)', nRow).html('液压故障');
						break;
					case '4':
						$('td:eq(15)', nRow).html('软件故障');
						break;
					case '0':
						$('td:eq(15)', nRow).html('其他故障');
						break;
					default:$('td:eq(15)', nRow).html('');
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
	    var oTable = $("#wrappedWorkOrderTable2 table").dataTable();
	    oTable.$('tr').click(function(event){
	        //console.log(":::click TR event:::");
			var targetName = $(event.target).attr("targetName");
			//console.log(":::targetName:::"+targetName);
			if(targetName!="workOrderInfo"){
				return false;
			} 
	        var rowData = oTable.fnGetData(this);
	        var id = rowData.id;
			var woMaintId = rowData.woMaintId;
	        var equipId = rowData.equipId;
	        var workOrderId = rowData.workOrderId;
	        var workOrderStatus = rowData.workOrderStatus;
			var mechianic = rowData.mechianic;
			var mechianicNM = rowData.mechianicLastName+" "+rowData.mechianicFirtName
			var faultType = rowData.faultType;
			var remark = rowData.remark;
			var sparePartInvolved = rowData.sparePartInvolved;
			var externalServiceInvolved = rowData.externalServiceInvolved;
			var equipNM = rowData.equipName;
	        //console.log(":::workOrderStatus:::" + workOrderStatus);
			if(workOrderStatus=="1"){
				abledItem();
		        $("#tab02 input[type='text']:eq(0)").val(equipId);                 //设备代码
				$("#tab02 input[type='text']:eq(1)").val("");	                   //维修工编号
				$("#tab02 select:first").val("1");                                 //故障种类
				$("#tab02 input[type='text']:eq(2)").val("");                      //关闭工单人
				$("#tab02 textarea").val("");	                                   //备注
				$("#tab02 :checkbox").prop("checked",false);                       //备件和外部服务，清空勾选
				$("#tab02 span:eq(0)").text("("+equipNM+")");                      //设备名称            
				//验证维修人员信息
				$("#tab02 input[type='text']:eq(1)").unbind('blur').on('blur',function(){
					var sso = $(this).val();
					//console.log("sso "+sso);
					if(sso == "" || sso == null){
						alert("SSO不能为空，请输入!");
						return false;
					}
					var ssoReg = /^[0-9]*$/;
					var ssoRegFlag = ssoReg.test(sso);
					if(!ssoRegFlag){
						$("#tab02 input[type='text']:eq(1)").val("");
						alert("SSO输入无效，请重新输入!!!");
						return false;
					}
					$.ajax({
						type:"POST",
						url:"../restful/workOrder/isMaintUser",
						dataType:"json",
						async:false,
						data:{
							sso:sso
						},
						success:function(jsonData){
							if(jsonData.success){
								var maintUser = jsonData.data.maintUser;
								if(maintUser != null){
									$("#tab02 span:eq(1)").text("("+maintUser.lastName+" "+maintUser.firstName+")");
								}else{
									$("#tab02 input[type='text']:eq(1)").val("");
									$("#tab02 span:eq(1)").text("");
									alert("SSO不正确，请重新输入");
								}
							}
						},
						error:function(XMLHttpRequest, textStatus, errorThrown){
							alert(textStatus);
						}
					});
				});
			}
			if(workOrderStatus=="2"){
				disabledItem();
				$("#tab02 input[type='text']:eq(0)").val(equipId);                  //设备编号
				$("#tab02 input[type='text']:eq(1)").val(mechianic);			    //维修人编号
				$("#tab02 select:first").val(faultType);                            //故障种类
				$("#tab02 textarea").val(remark);                                   //备注
				$("#tab02 :checkbox:eq(0)").prop("checked",sparePartInvolved);      //等待备件
				$("#tab02 :checkbox:eq(1)").prop("checked",externalServiceInvolved);//等外部服务
				$("#tab02 span:eq(0)").text("("+equipNM+")");                        //维修人名字
				$("#tab02 span:eq(1)").text("("+mechianicNM+")");                   //维修人名字
				$("#tab02 input[type='text']:eq(2)").val("");		                 //关闭工单人
				//验证关闭工单人员信息
				$("#tab02 input[type='text']:eq(2)").unbind('blur').on('blur',function(){
					var sso = $(this).val();
					console.log("sso "+sso);
					if(sso == "" || sso == null){
						alert("SSO不能为空，请输入!");
						return false;
					}
					var ssoReg = /^[0-9]*$/;
					var ssoRegFlag = ssoReg.test(sso);
					if(!ssoRegFlag){
						$("#tab02 input[type='text']:eq(2)").val("");
						alert("SSO输入无效，请重新输入!!!");
						return false;
					}
					$.ajax({
						type:"POST",
						url:"../restful/workOrder/isMaintUser",
						dataType:"json",
						async:false,
						data:{
							sso:sso
						},
						success:function(jsonData){
							if(jsonData.success){
								var maintUser = jsonData.data.maintUser;
								if(maintUser != null){
									$("#tab02 span:eq(2)").text("("+maintUser.lastName+" "+maintUser.firstName+")");
								}else{
									$("#tab02 input[type='text']:eq(2)").val("");
									$("#tab02 span:eq(2)").text("");
									alert("SSO不正确，请重新输入");
								}
							}
						},
						error:function(XMLHttpRequest, textStatus, errorThrown){
							alert(textStatus);
						}
					});
				});
			}
			
			//维修开始
	        $("#tab02 button:eq(0)").unbind('click').click(function(){
				//console.log(":::click button event:::");
				var num = $("#tab02 :radio:checked").length;
				if(num != 1){
//			        $("#tab02 input[type='text']:eq(0)").val("");
//					$("#tab02 input[type='text']:eq(1)").val("");
//					$("#tab02 select:eq(0)").val("1");
//					$("#tab02 textarea").val("");
//					$("#tab02 :checkbox").prop("checked",false);
//					$("#tab02 input[type='text']:eq(2)").val("");
					alert("请选择一条工单进行处理");
					return false;
				}
	            if (workOrderStatus == "2") {
	                alert("工单状态已处于维修中");
					//console.log("工单状态已处于维修中");
	                return false;
	            }else if($("#tab02 input[type='text']:eq(1)").val()==""){
					alert("请输入您的sso");
					//console.log("请输入您的sso");
					return false;
				}
	            else {
	                var mechianic = $("#tab02 input[type='text']:eq(1)").val();
	                var faultType = $("#tab02 select").val();
	                var remark = $("#tab02 textarea").val();
	                var sparePartInvolved = $("#tab02 :checkbox:eq(0)").prop("checked");
	                var externalServiceInvolved = $("#tab02 :checkbox:eq(1)").prop("checked");
					
					var optFlag = confirm("确定对工单 "+workOrderId+" 进行处理吗?");
					if(optFlag){
						$("#tab02 button:eq(0)").unbind();
		                $.ajax({
		                    type: "POST",
		                    url: "../restful/workOrder/saveWOMaintInfo",
							async:true,
		                    dataType: "json",
		                    data: {
		                        id: id,
								orderType:1,
		                        workOrderId: workOrderId,
		                        workOrderStatus: workOrderStatus,
		                        mechianic: mechianic,
		                        faultType: faultType,
		                        remark: remark,
		                        sparePartInvolved: sparePartInvolved,
		                        externalServiceInvolved: externalServiceInvolved
		                    },
							beforeSend:function(XMLHttpRequest){
							},
							complete:function(XMLHttpRequest, textStatus){
								$("#tab02 button:eq(0)").bind('click',function(){
									alert("请选择一条工单进行处理...");
								});
							},
		                    success: function(jsonData){
		                        if (jsonData.success) {
									if(jsonData.data.addMaintWO=="success"){
			                            init();
										alert(workOrderId+"处理完成");
									}else{
										$("#tab02 input[type='text']:eq(1)").val("");
										alert("维修人员sso不正确");
									}
		                        }
		                    },
		                    error: function(XMLHttpRequest, textStatus, errorThrown){
		                        alert("error" + textStatus);
		                    }
		                });//ajax end					
					}
	            }
	        });
			
			//结束维修，关闭工单
			$("#tab02 button:eq(1)").unbind("click").click(function(){
				//console.log("点击结束维修按钮");
				var num = $("#tab02 :radio:checked").length;
				//关闭工单的人
				var operator = $("#tab02 input[type='text']:eq(2)").val();
				if(num != 1){
//			        $("#tab02 input[type='text']:eq(0)").val("");
//					$("#tab02 input[type='text']:eq(1)").val("");
//					$("#tab02 select:eq(0)").val("1");
//					$("#tab02 textarea").val("");
//					$("#tab02 :checkbox").prop("checked",false);
//					$("#tab02 input[type='text']:eq(2)").val("");
					alert("请选择一条工单进行处理");
					return false;
				}
				if(workOrderStatus == "1"){
					alert("工单未进入维修状态");
					return false;
				}else if(operator == ""){
					alert("请输入关闭工单人员SSO");
					return false;
				}else{		
					var optFlag = confirm("确定对工单 "+workOrderId+" 进行关闭吗?");
					if(optFlag){
						$("#tab02 button:eq(1)").unbind('click');
						$.ajax({
		                    type: "POST",
		                    url: "../restful/workOrder/closeWOMaintInfo",
		                    dataType: "json",
							async:true,
		                    data: {
		                        id: id,
								orderType:1,
								woMaintId:woMaintId,
								operator: operator,
							    remark:$("#tab02 textarea").val()
		                    },
							beforeSend:function(XMLHttpRequest){
							},
							complete:function(XMLHttpRequest, textStatus){
								$("#tab02 button:eq(1)").bind('click',function(){
									alert("请选择一条工单进行处理");
								});
							},
		                    success: function(jsonData){
		                        if (jsonData.success) {
									if(jsonData.data.closeMaint=="success"){
			                            init();
										alert(workOrderId+"进入关闭状态");
									}else{
										$("#tab02 input[type='text']:eq(2)").val("");
										alert("请输入正确的结束维修人员的sso!");
									}
		                        }
		                    },
		                    error: function(XMLHttpRequest, textStatus, errorThrown){
		                        alert("服务器连接" + textStatus);
		                    }
		                });					
					}
				}				
			});
	    });
    }
	//条件查询--区域
	$("#workOrderflow2 select:eq(0)").change(function(){
		clearDataForSel();
		var opts={
			url:"../restful/workOrder/getWorkOrders",
			data:{
				orderType:1,
				orderFlag:1,
				deptId:$("#workOrderflow2 select:eq(0)").val(),
				equipType:$("#workOrderflow2 select:eq(1)").val(),			
			}
		}
		ajaxRequest(opts);
		//console.log("change event1 ");
	});
	//条件查询--设备种类
	$("#workOrderflow2 select:eq(1)").change(function(){
		clearDataForSel();
		var opts={
			url:"../restful/workOrder/getWorkOrders",
			data:{
				orderType:1,
				orderFlag:1,
				deptId:$("#workOrderflow2 select:eq(0)").val(),
				equipType:$("#workOrderflow2 select:eq(1)").val(),			
			}
		}
		ajaxRequest(opts)
		//console.log("change event2 ");
	});
	
	function clearData02(){
        $("#tab02 input[type='text']:eq(0)").val("");
		$("#tab02 input[type='text']:eq(1)").val("");
		$("#tab02 select:eq(0)").val("1");
		$("#tab02 textarea").val("");
		$("#tab02 :checkbox").prop("checked",false);
		$("#workOrderflow2 select:eq(1)").val("");
		$("#tab02 input[type='text']:eq(2)").val("").attr("disabled","disabled");
//		$("#tab02 span:eq(0)").text("");
//		$("#tab02 span:eq(1)").text("");
	}
	
	function clearDataForSel(){
        $("#tab02 input[type='text']:eq(0)").val("");
		$("#tab02 input[type='text']:eq(1)").val("").attr("disabled",false);
		$("#tab02 select:eq(0)").val("1").attr("disabled",false);
		$("#tab02 textarea").val("");
		$("#tab02 :checkbox").prop("checked",false);
		$("#tab02 input[type='text']:eq(2)").val("").attr("disabled",true);
		$("#tab02 span:eq(0)").text("");
		$("#tab02 span:eq(1)").text("");
		$("#tab02 span:eq(2)").text("");
	}
	
	function disabledItem(){
		$("#tab02 input[type='text']:eq(1)").attr("disabled",true);
		$("#tab02 input[type='text']:eq(2)").attr("disabled",false);
		$("#tab02 select:eq(0)").attr("disabled",true);
		$("#tab02 :checkbox").attr("disabled",true);
		$("#tab02 span:eq(0)").text("");
		$("#tab02 span:eq(1)").text("");
		$("#tab02 span:eq(2)").text("");
	}
	function abledItem(){
		$("#tab02 input[type='text']:eq(1)").attr("disabled",false);
		$("#tab02 input[type='text']:eq(2)").attr("disabled",true);
		$("#tab02 select:eq(0)").attr("disabled",false);
		$("#tab02 :checkbox").attr("disabled",false);
		$("#tab02 span:eq(0)").text("");
		$("#tab02 span:eq(1)").text("");
		$("#tab02 span:eq(2)").text("");
	}
    return {
        init: init
    }
});
