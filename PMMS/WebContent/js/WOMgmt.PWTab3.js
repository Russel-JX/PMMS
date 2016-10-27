define(['pmms'],function(pmms){
	var selectObj = $("#workOrderflow005 select:eq(0)");
	pmms.getDeptInfo(selectObj);
	/**
	 * *1:初始化tab03
	 * *2:orderType代表工单类型，1代表维修故障工单，2代表计划保养工单
	 * *3：orderFlag工单状态标识，1代表未关闭的工单(workOrderStatus=1,2),
	 * *  2代表关闭状态的工单(workOrderStatus=3) 
	 */
	var init = function(){
		clearDataTab005();
		clearSelectTab005();
		abledItem();
		var opts={
			url:"../restful/workOrder/getWorkOrders",
			data:{
				deptId:$("#workOrderflow005 select:eq(0)").val(),
				orderType:2,
				orderFlag:1,		
			}
		}
		ajaxRequest(opts);
	}
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
                    orderhtml += '<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable005">';
                    orderhtml += '<thead>';
                    orderhtml += '</thead>';
                    orderhtml += '<tbody>';
                    orderhtml += '</tbody>';
                    orderhtml += '</table>';
					$("#wrappedWorkOrderTable005 table>tbody").empty();
                    $("#wrappedWorkOrderTable005").html(orderhtml);
                    buildWorkOrderTab005(jsonData.data);
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                alert("error" + textStatus);
            }
        });//ajax end
	}//ajaxRequest end	
	function buildWorkOrderTab005(data){
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
            "sTitle": "是否需停机/ShutDown",
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
        var oTable= $("#wrappedWorkOrderTable005 table").iidsBasicDataGrid({
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
	    oTable = $("#wrappedWorkOrderTable005 table").dataTable();
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
			var equipNM = rowData.equipName;
			var remark = rowData.remark;
	        //console.log(":::workOrderStatus:::" + workOrderStatus);
			if(workOrderStatus=="1"){
				abledItem();
				$("#tab005 input[type='text']:eq(0)").val(equipId); //设备代码
				$("#tab005 span:eq(0)").text("("+equipNM+")");      //维修工姓名
				$("#tab005 input[type='text']:eq(1)").val("");      //维修工编号
				$("#tab005 input[type='text']:eq(2)").val("");      //关闭工单人编号
				$("#tab005 textarea").val("");	                    //备注
				$("#tab005 :checkbox").prop("checked",true);	    //默认勾选
				var jqueryObj1 = $("#tab005 input[type='text']:eq(1)");
				var jquerySpan1 = $("#tab005 span:eq(1)");
				pmms.verifySSO(jqueryObj1,jquerySpan1);
			}
			if(workOrderStatus=="2"){
				disabledItem();
				$("#tab005 input[type='text']:eq(0)").val(equipId);    //设备代码
				$("#tab005 span:eq(0)").text("("+equipNM+")");         //维修工姓名
				$("#tab005 input[type='text']:eq(1)").val(mechianic);  //维修工编号
				$("#tab005 span:eq(1)").text("("+mechianicNM+")");     //维修工姓名
				$("#tab005 input[type='text']:eq(2)").val("");         //关闭工单人编号
				$("#tab005 textarea").val(remark);	                   //备注
				$("#tab005 :checkbox").prop("checked",rowData.shutdownFlag);
				var jqueryObj2 = $("#tab005 input[type='text']:eq(2)");
				var jquerySpan2 = $("#tab005 span:eq(2)");
				pmms.verifySSO(jqueryObj2,jquerySpan2);
			}
			
			//开始维修
	        $("#tab005 button:eq(0)").unbind('click').click(function(){
				//console.log(":::click button event:::");
				var num = $("#tab005 :radio:checked").length
				if(num != 1){
					clearDataTab005();
					alert("请选择一条工单进行处理");
					return false;
				}
	            if (workOrderStatus == "2") {
	                alert("工单状态已处于维修中");
					//console.log("工单状态已处于维修中");
	                return false;
	            }else if($("#tab005 input[type='text']:eq(1)").val()==""){
					alert("请输入您的sso");
					//console.log("请输入您的sso");
					return false;
				}
	            else {
	                var mechianic = $("#tab005 input[type='text']:eq(1)").val();
	                var remark = $("#tab005 textarea").val();
					var shutdownFlag = $("#tab005 :checkbox").prop("checked");
					var optFlag = confirm("确定对工单 "+workOrderId+" 进行处理吗?");
					if(optFlag){
						$("#tab005 button:eq(0)").unbind();
		                $.ajax({
		                    type: "POST",
		                    url: "../restful/workOrder/saveWOMaintInfo",
		                    dataType: "json",
							async:false,
		                    data: {
		                        id: id,
								orderType:2,
		                        workOrderId: workOrderId,
		                        workOrderStatus: workOrderStatus,
		                        mechianic: mechianic,
								shutdownFlag:shutdownFlag,
		                        remark: remark
		                    },
							complete:function(XMLHttpRequest, textStatus){
								$("#tab005 button:eq(0)").bind('click',function(){
									alert("请选择一条工单进行处理")
								});
							},
		                    success: function(jsonData){
		                        if (jsonData.success) {
		                            //console.log(workOrderId+"处理完成");
								    clearSelectTab005();
		                            init();
									alert(workOrderId+"处理完成");
		                        }
		                    },
		                    error: function(XMLHttpRequest, textStatus, errorThrown){
		                        alert("error" + textStatus);
		                    }
		                });					
					}
	                
	            }
	        });
			//结束维修， 关闭工单
			$("#tab005 button:eq(1)").unbind("click").click(function(){
				//console.log("点击结束维修按钮");
				var num = $("#tab005 :radio:checked").length;
				var operator = $("#tab005 input[type='text']:eq(2)").val();
				if(num != 1){
					clearDataTab005();
					alert("请选择一条工单进行处理");
					return false;
				}
				if(workOrderStatus == "1"){
					alert("工单未进入维修状态");
					return false
				}else if(pmms.isEmpty(operator)){
					alert("请输入操作员sso");
					return false;
				}{
					var optFlag = confirm("确定对工单 "+workOrderId+" 进行关闭吗?");
					if(optFlag){
						$("#tab005 button:eq(1)").unbind();
						$.ajax({
		                    type: "POST",
		                    url: "../restful/workOrder/closeWOMaintInfo",
		                    dataType: "json",
							async:false,
		                    data: {
		                        id: id,
								woMaintId:woMaintId,
								operator:operator,
								remark:$("#tab005 textarea").val()
		                    },
							complete:function(XMLHttpRequest, textStatus){
								$("#tab005 button:eq(1)").bind('click',function(){
									alert("请选择一条工单进行处理");
								});
							},
		                    success: function(jsonData){
		                        if (jsonData.success) {
		                            //console.log(workOrderId+"处理完成");
									clearSelectTab005();
		                            init();
									alert(workOrderId+"处理完成");
		                        }
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
			if (targetName == "maintItem") {
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
	$("#workOrderflow005 select:eq(0)").change(function(){
		clearData005ForSel();
		var opts={
			url:"../restful/workOrder/getWorkOrders",
			data:{
				orderType:2,
				orderFlag:1,
				deptId:$("#workOrderflow005 select:eq(0)").val(),
				equipType:$("#workOrderflow005 select:eq(1)").val(),			
			}
		}
		ajaxRequest(opts)
		//console.log("change event1 ");
	});
	//条件查询--设备种类
	$("#workOrderflow005 select:eq(1)").change(function(){
		clearData005ForSel();
		var opts={
			url:"../restful/workOrder/getWorkOrders",
			data:{
				orderType:2,
				orderFlag:1,
				deptId:$("#workOrderflow005 select:eq(0)").val(),
				equipType:$("#workOrderflow005 select:eq(1)").val(),			
			}
		}
		ajaxRequest(opts)
		//console.log("change event2 ");
	});
	//clear data
	function clearDataTab005(){
		$("#tab005 input[type='text']:eq(0)").val("");
		$("#tab005 input[type='text']:eq(1)").val("");
		$("#tab005 input[type='text']:eq(2)").val("").attr("disabled",true);
		$("#tab005 textarea").val("");
		$("#tab005 :checkbox").prop("checked",true);
		$("#tab005 span:eq(0)").text("");
		$("#tab005 span:eq(1)").text("");
	}
	
	function clearData005ForSel(){
		$("#tab005 input[type='text']:eq(0)").val("");
		$("#tab005 input[type='text']:eq(1)").val("").attr("disabled",false);
		$("#tab005 input[type='text']:eq(2)").val("").attr("disabled",true);
		$("#tab005 textarea").val("");
		$("#tab005 :checkbox").prop("checked",true);
		$("#tab005 span:eq(0)").text("");
		$("#tab005 span:eq(1)").text("");
		$("#tab005 span:eq(2)").text("");
	}
	
	//清除下拉列选
	function clearSelectTab005(){
		$("#workOrderflow005 select:eq(1)").val("");
	}
	function disabledItem(){
		$("#tab005 input[type='text']:eq(1)").attr("disabled",true);
		$("#tab005 input[type='text']:eq(2)").attr("disabled",false);
		$("#tab005 :checkbox").attr("disabled",true);
		$("#tab005 span:eq(0)").text("");
		$("#tab005 span:eq(1)").text("");
		$("#tab005 span:eq(2)").text("");
	}
	function abledItem(){
		$("#tab005 input[type='text']:eq(1)").attr("disabled",false);
		$("#tab005 input[type='text']:eq(2)").attr("disabled",true);
		$("#tab005 :checkbox").attr("disabled",false);
		$("#tab005 span:eq(0)").text("");
		$("#tab005 span:eq(1)").text("");
		$("#tab005 span:eq(2)").text("");
	}
    return {
        init: init
    }
});
