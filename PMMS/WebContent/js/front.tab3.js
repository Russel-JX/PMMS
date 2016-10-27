define(['pmms'],function(pmms){
	var selectObj = $("#workOrderflow3 select:eq(0)");
	pmms.getDeptInfo(selectObj);
	/**
	 * *1:初始化tab03
	 * *2:orderType代表工单类型，1代表维修故障工单，2代表计划保养工单
	 * *3：orderFlag工单状态标识，1代表未关闭的工单(workOrderStatus=1,2),
	 * *  2代表关闭状态的工单(workOrderStatus=3) 
	 */
	var init = function(){
		clearData03();
		abledItem();
		var opts={
			url:"../restful/workOrder/getWorkOrders",
			data:{
				orderType:2,
				orderFlag:1,
				deptId:$("#workOrderflow3 select:eq(0)").val()	
			}
		};
		ajaxRequest(opts);
	};
	//请求数据
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
                    orderhtml += '<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable3">';
                    orderhtml += '<thead>';
                    orderhtml += '</thead>';
                    orderhtml += '<tbody>';
                    orderhtml += '</tbody>';
                    orderhtml += '</table>';
					$("#wrappedWorkOrderTable3 table>tbody").empty();
                    $("#wrappedWorkOrderTable3").html(orderhtml);
                    buildWorkOrderTab3(jsonData.data);
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                alert("error" + textStatus);
            }
        });//ajax end
	}//ajaxRequest end	
	function buildWorkOrderTab3(data){
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
            "sTitle": "保养内容/MaintCnt",
            "mData": "maintContent"
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
            "sTitle": "计划开始月/PlanMonth",
            "mData": "strPlanStartMonth"
        }, {
            "sTitle": "使用部门/Department",
            "mData": "deptNM"
        }, {
            "sTitle": "备注/Remark",
            "mData": "remark"
        }];
        $("#wrappedWorkOrderTable3 table").iidsBasicDataGrid({
            "isResponsive": true,
            "useFloater": false,
            "bAutoWidth": false,
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
				 $('td:eq(6)',nRow).html(
				 			"<button class='btn btn-primary' targetName='maintItem' title='查看此计划的保养内容'>" +
									"<div class='icon-search'></div>" +
							"</button>"
				 );
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
					$('td:eq(12)', nRow).css("background", 'green');
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
                    }
                if (aData.shutdownFlag) {
                    $('td:eq(7)', nRow).html('<i class="icon icon-large icon-ok mrgzero color-green"></i>');
                }
                else {
                    $('td:eq(7)', nRow).html('<i class="icon icon-large icon-remove mrgzero color-red" style="color:#ff0000;">');
                }
				
				if((aData.mechianicLastName!="") || (aData.mechianicFirtName!="")){
					$('td:eq(8)', nRow).html(aData.mechianic+"("+aData.mechianicLastName+" "+aData.mechianicFirtName+")");
				}else{
					$('td:eq(8)', nRow).html(aData.mechianic);
				}
            }
        });		
	    var oTable = $("#wrappedWorkOrderTable3 table").dataTable();
	    oTable.$('tr').click(function(event){
			var targetName = $(event.target).attr("targetName");
			//console.log(":::targetName:::"+targetName);
			if(targetName=="workOrderInfo"){
	        var rowData = oTable.fnGetData(this);
	        var id = rowData.id;
			var woMaintId = rowData.woMaintId;
	        var equipId = rowData.equipId;
	        var workOrderId = rowData.workOrderId;
	        var workOrderStatus = rowData.workOrderStatus;
			var mechianic = rowData.mechianic;
			var mechianicNM = rowData.mechianicLastName+" "+rowData.mechianicFirtName;
			var remark = rowData.remark;
			var equipNM = rowData.equipName;
	        //console.log(":::workOrderStatus:::" + workOrderStatus);
			if(workOrderStatus=="1"){
				abledItem();
				$("#tab03 input[type='text']:eq(0)").val(equipId); //设备代码
				$("#tab03 input[type='text']:eq(1)").val("");      //维修工编号
				$("#tab03 input[type='text']:eq(2)").val("");      //关闭工单人编号
				$("#tab03 textarea").val("");	                   //备注
				$("#tab03 ：checkbox").prop("checked",true);	       //还原默认勾选
				$("#tab03 span:eq(0)").text("("+equipNM+")");      //设备名称
				var jqueryObj1 = $("#tab03 input[type='text']:eq(1)");
				var jquerySpan1 = $("#tab03 span:eq(1)");
				pmms.verifySSO(jqueryObj1,jquerySpan1);
				/*
				$("#tab03 input[type='text']:eq(1)").unbind('blur').on('blur',function(){
					var sso = $(this).val();
					//console.log("sso "+sso);
					if(sso == "" || sso == null){
						alert("SSO不能为空，请输入!");
						return false;
					}
					var ssoReg = /^[0-9]*$/;
					var ssoRegFlag = ssoReg.test(sso);
					if(!ssoRegFlag){
						$("#tab03 input[type='text']:eq(1)").val("");
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
								if(jsonData.data.validate == null){
									$("#tab03 input[type='text']:eq(1)").val("");
									alert("SSO不正确，请重新输入");
								}
							}
						},
						error:function(XMLHttpRequest, textStatus, errorThrown){
							alert(textStatus);
						}
					});
				});	*/	
			}
			if(workOrderStatus=="2"){
				disabedItem();
				$("#tab03 input[type='text']:eq(0)").val(equipId);          //设备编号
				$("#tab03 input[type='text']:eq(1)").val(mechianic);        //维修人SSO
				$("#tab03 input[type='text']:eq(2)").val("");               //关闭工单人
				$("#tab03 textarea").val(remark);	                        //备注
				$("#tab03 :checkbox").prop("checked",rowData.shutdownFlag); //是否需停机
				$("#tab03 span:eq(0)").text("("+equipNM+")");               //设备名称
				$("#tab03 span:eq(1)").text("("+mechianicNM+")");           //维修工姓名
				var jqueryObj2 = $("#tab03 input[type='text']:eq(2)");
				var jquerySpan2 = $("#tab03 span:eq(2)");
				pmms.verifySSO(jqueryObj2,jquerySpan2);
				/*
				$("#tab03 input[type='text']:eq(2)").unbind('blur').on('blur',function(){
					var sso = $(this).val();
					//console.log("sso "+sso);
					if(sso == "" || sso == null){
						alert("SSO不能为空，请输入!");
						return false;
					}
					var ssoReg = /^[0-9]*$/;
					var ssoRegFlag = ssoReg.exec(sso);
					if(!ssoRegFlag){
						$("#tab03 input[type='text']:eq(2)").val("");
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
								if(jsonData.data.user == null){
									$("#tab03 input[type='text']:eq(2)").val("");
									alert("SSO不正确，请重新输入");
								}
							}
						},
						error:function(XMLHttpRequest, textStatus, errorThrown){
							alert(textStatus);
						}
					});
				});*/
			}
			//计划保养工单开始维修
	        $("#tab03 button:eq(0)").unbind('click').click(function(){
				//console.log(":::click button event:::");
				var num = $("#tab03 :radio:checked").length;
				if(num != 1){
//					$("#tab03 .controls input:eq(0)").val(equipId);
//					$("#tab03 .controls input:eq(1)").val("");
//					$("#tab03 textarea").val("");	
//					$("#tab03 :checkbox").prop("checked",false);	
					alert("请选择一条工单进行处理");
					return false;
				}
	            if (workOrderStatus == "2") {
	                alert("工单状态已处于维修中");
					//console.log("工单状态已处于维修中");
	                return false;
	            }else if($("#tab03 input[type='text']:eq(1)").val()==""){
					alert("请输入您的sso");
					//console.log("请输入您的sso");
					return false;
				}
	            else {
	                var mechianic = $("#tab03 input[type='text']:eq(1)").val();
	                var remark = $("#tab03 textarea").val();
					var shutdownFlag = $("#tab03 :checkbox").prop("checked");
					var optFlag = confirm("确定对工单 "+workOrderId+" 进行处理吗?");
					if(optFlag){
						$("#tab03 button:eq(0)").unbind();
		                $.ajax({
		                    type: "POST",
		                    url: "../restful/workOrder/saveWOMaintInfo",
		                    dataType: "json",
							async:true,
		                    data: {
		                        id: id,
								orderType:2,
		                        workOrderId: workOrderId,
		                        workOrderStatus: workOrderStatus,
		                        mechianic: mechianic,
								shutdownFlag:shutdownFlag,
		                        remark: remark
		                    },
							beforeSend:function(XMLHttpRequest){
							},
							complete:function(XMLHttpRequest, textStatus){
								$("#tab03 button:eq(0)").bind('click',function(){
									alert("请选择一条工单进行处理");
								});
							},
		                    success: function(jsonData){
		                        if (jsonData.success) {
									if(jsonData.data.addMaintWO=="success"){
										init();
										alert(workOrderId+"处理完成");
									}else{
										$("#tab03 input[type='text']:eq(1)").val("");
										alert("请输入正确的维修人员sso");
									}
		                           
		                        }
		                    },
		                    error: function(XMLHttpRequest, textStatus, errorThrown){
		                        alert("error" + textStatus);
		                    }
		                });					
					}
	            }
	        });
			//计划保养工单结束维修
			$("#tab03 button:eq(1)").unbind("click").click(function(){
				//console.log("点击结束维修按钮");
				var num = $("#tab03 :radio:checked").length;
				var operator = $("#tab03 input[type='text']:eq(2)").val();
				if(num != 1){
					alert("请选择一条工单进行处理");
					return false;
				}
				if(workOrderStatus == "1"){
					alert("工单未进入维修状态");
					return false;
				}else if(operator == ""){
					alert("请输入操作员sso");
					return false;
				}else{
					var optFlag = confirm("确定对工单 "+workOrderId+" 进行关闭吗?");
					if(optFlag){
						$("#tab03 button:eq(1)").unbind();
						$.ajax({
		                    type: "POST",
		                    url: "../restful/workOrder/closeWOMaintInfo",
		                    dataType: "json",
							async:true,
		                    data: {
		                        id: id,
								woMaintId:woMaintId,
								operator:operator,
							    remark:$("#tab03 textarea").val()
		                    },
		                    success: function(jsonData){
		                        if (jsonData.success) {
									if(jsonData.data.closeMaint=="success"){
										init();
										alert(workOrderId+"处理完成");
									}else{
										$("#tab03 input[type='text']:eq(2)").val("");
										alert("关闭工单人员sso输入不正确!");
									}
		                        }
		                    },
							beforeSend:function(XMLHttpRequest){
							},
							complete:function(){
								$("#tab03 button:eq(1)").bind('click',function(){
									alert("请选择一条工单进行处理");
								});
							},
		                    error: function(XMLHttpRequest, textStatus, errorThrown){
		                        alert("error" + textStatus);
		                    }
		                });					
					}
				}				
			});
			} 
			//查看保养内容
			if(targetName=="maintItem"){
				var rowData = oTable.fnGetData(this);
				var equipNameId = rowData.equipNameId;
				$.ajax({
					type:'POST',
					url:'../restful/maintItem/getMaintItems',
					dataType:'json',
					data:{
						equipName:equipNameId
					},
					success:function(jsonData){
						if(jsonData.success){
							var items = jsonData.data.list;
							var itemContainer = $(".maint-item-view").empty();
							$("#modal-maint-item-title").html(rowData.equipId+"设备");
							$.each(items,function(index,item){
								$(itemContainer).append(
									'<tr class="odd">'+
										'<td class="">'+(index+1)+'</td>'+
										'<td class="">'+item.maint_item+'</td>'+
										'<td class="">'+item.cycle+'</td>'+
										'<td class="">'+item.maint_way+'</td>'+
										'<td class="">'+item.standard+'</td>'+
									'</tr>'
								);
							});
							$("#modal-plan-maintItem-view").modal("show");
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						alert(textStatus);
					}
				});
			}
	    });
    }
	//条件查询--区域
	$("#workOrderflow3 select:eq(0)").change(function(){
		clearDataForSel();
		var opts={
			url:"../restful/workOrder/getWorkOrders",
			data:{
				orderType:2,
				orderFlag:1,
				deptId:$("#workOrderflow3 select:eq(0)").val(),
				equipType:$("#workOrderflow3 select:eq(1)").val()		
			}
		}
		ajaxRequest(opts);
		//console.log("change event1 ");
	});
	//条件查询--设备种类
	$("#workOrderflow3 select:eq(1)").change(function(){
		clearDataForSel();
		var opts={
			url:"../restful/workOrder/getWorkOrders",
			data:{
				orderType:2,
				orderFlag:1,
				deptId:$("#workOrderflow3 select:eq(0)").val(),
				equipType:$("#workOrderflow3 select:eq(1)").val()		
			}
		}
		ajaxRequest(opts)
		//console.log("change event2 ");
	});
	
	function clearData03(){
		$("#tab03 input[type='text']:eq(0)").val("");
		$("#tab03 input[type='text']:eq(1)").val("");
		$("#tab03 textarea").val("");
		$("#tab03 :checkbox").prop("checked",true);
		$("#tab03 input[type='text']:eq(2)").val("").attr("disabled",true);
		$("#workOrderflow3 select:eq(1)").val("");
//		$("#tab03 span:eq(0)").text("");
//		$("#tab03 span:eq(1)").text("");
	}
	
	function clearDataForSel(){
		$("#tab03 input[type='text']:eq(0)").val("");
		$("#tab03 input[type='text']:eq(1)").val("").attr("disabled",false);
		$("#tab03 input[type='text']:eq(2)").val("").attr("disabled",true);
		$("#tab03 textarea").val("");
		$("#tab03 :checkbox").prop("checked",true);
		$("#tab03 span:eq(0)").text("");
		$("#tab03 span:eq(1)").text("");
		$("#tab03 span:eq(2)").text("");
	}
	function disabedItem(){
		$("#tab03 input[type='text']:eq(1)").attr("disabled",true);
		$("#tab03 input[type='text']:eq(2)").attr("disabled",false);
		$("#tab03 :checkbox").attr("disabled",true);
		$("#tab03 span:eq(0)").text("");
		$("#tab03 span:eq(1)").text("");
		$("#tab03 span:eq(2)").text("");
	}
	function abledItem(){
		$("#tab03 input[type='text']:eq(1)").attr("disabled",false);
		$("#tab03 input[type='text']:eq(2)").attr("disabled",true);
		$("#tab03 :checkbox").attr("disabled",false);
		$("#tab03 span:eq(0)").text("");
		$("#tab03 span:eq(1)").text("");
		$("#tab03 span:eq(2)").text("");
	}
    return {
        init: init
    }
});
