require( ['jquery', 'datagrids','ge-bootstrap'], function($) {
			var plan_table = null;
			var month_table = null;
			initial();
			//popover config
			$("#planViewHelp").popover({
				html:true,
				delay:{show:1,hide:500},
				placement:"bottom",
				trigger:"hover",
				title:"计划查看说明",
				content:'<div id="popover3-content-wrapper">'+
				'<p><strong>保养计划的类型</strong><br/>'+
				  '用Y，Q，M表示</p>'+
				  '<div class="divider"></div>'+
				  '<p><strong>Y</strong>——'+
				  '年度保养</p>'+
				  '<div class="divider"></div>'+
				  '<p><strong>Q</strong>——季度保养</p>'+
				  '<div class="divider"></div>'+
				  '<p><strong>M</strong>——月度保养</p>'+
				'</div>'
			}).on('click', function(e) {
				  e.preventDefault();
			});
			$("#monthPlanViewHelp").popover({
				html:true,
				delay:{show:1,hide:500},
				placement:"bottom",
				trigger:"hover",
				title:"本月计划查看说明",
				content:'<div id="popover4-content-wrapper">'+
				'<p><strong>绿色字体</strong><br/>'+
				  '本月保养计划</p>'+
				  '<div class="divider"></div>'+
				  '<p><strong>灰色字体</strong>——'+
				  '本年度，往月未完成保养计划</p>'+
				'</div>'
			}).on('click', function(e) {
				  e.preventDefault();
			});
			$("#planHelp").popover({
				html:true,
				delay:{show:1,hide:500},
				placement:"left",
				trigger:"hover",
				title:"新增|修改计划说明",
//				content:"每个季度只能勾选一个季度保养checkbox；年度保养和月度保养checkbox，请根据需要勾选"
//				content: function() {
//					return $('#popover1-content-wrapper').html();
//				}
				content:'<div id="popover5-content-wrapper">'+
				'<p>季度计划checkbox<br/>'+
				  '每个季度只能勾选一次</p>'+
				  '<div class="divider"></div>'+
				  '<p>月度计划checkbox<br/>'+
				  '可以任意勾选</p>'+
				  '<div class="divider"></div>'+
				  '<p>年度计划checkbox可以任意勾选</p>'+
				  '<div class="divider"></div>'+
				  '<p>修改计划checkbox 已完成的或正在维修的计划无法修改</p>'+
				  '<p>新增|修改计划checkbox 过往月份和当前月份没有勾上的checkbox，不能勾选</p>'+
				'</div>'
//				content:'<h3 class="voice voice-brand pull-left">第二步：选择保养月份</h3><button class="btn btn-primary">fff</button>'
			}).on('click', function(e) {
				  e.preventDefault();
			});
	
			//初始化表格数据 initalize datatables
			function initial(equipType){
				buildTable((new Date()).getFullYear(),"");
			}
			
			//导出Excel
			$("#export").on("click",function(){
				var maintYear = $("#maintYear").val();
				var equipType = $("#equipType").val();
				window.location.href="/PMMS/restful/plan/exportYearPlan?maintYear="+maintYear+"&equipType="+equipType;
			});
			$("#exportMonth").on("click",function(){
				window.location.href="/PMMS/restful/plan/exportCurrMonthPlan";
			});
			$("#exportMaintItem").on("click",function(){
				var equipNameId = $("#exportMaintItem").data("equipNameId");
				window.location.href="/PMMS/restful/maintItem/export?equipNameId="+equipNameId;
			});
			
			//按年份查询计划 query plan by year
			$("#maintYear").on("change",function(){
				buildTable($("#maintYear").val(),$("#equipType").val());
			});
			//按设备类型查询计划 query plan by equip type
			$("#equipType").on("change",function(){
				
				if(!$("#maintYear").val()){
					alert("请先选择年份！");
					return false;
				}
				buildTable($("#maintYear").val(),$("#equipType").val());
			});
			
			//验证设备编号有效性
			$("#equipId").on('blur',function(){
				$("#equipName").val('');
				$("#equipType").val('');
				$("#equipTypeModal").val('');
				$("#equipModal").val('');
				
				var equipId = $("#equipId").val();
				$.ajax({
					url:'/PMMS/restful/plan/validateEquip',
					type:'post',
					dataType:'json',
					cache:false,
					data:{
						equipId:equipId
					},
					beforeSend:function(xhr){
						//$("#loading").show();
					},
					complete:function (XMLHttpRequest, textStatus) {
						//$("#loading").hide();
					},
					success:function(backData,status){
						var detail = backData.data.detail;
						//将设备信息放在html元素(设备编号输入框：equipId)中
						$("#equipId").data("equipInfo",detail);
						if(detail!=null){//设备存在
							$("#validEquipId").val("YES");
							$(".equipMsg").hide();
							$("#equipName").val(detail.equipmentName);
							var type = detail.equipType;
							switch (type){
								case "1":type = "生产设备";break;
								case "2":type = "特种设备";break;
								case "3":type = "设施";break;
								default :type = "";
							}
							$("#equipTypeModal").val(type);
							$("#equipModal").val(detail.equipModel);
						}else{
							$("#validEquipId").val("NO");
							$(".equipMsg").html(equipId+" 设备不存在");
							$(".equipMsg").show();
						}
					},
					error:function(XmlHttpRequest){
						alert('error!!!');
					}
		    	});
			});
			
			//选择季度保养事件
			$("input[class*=quarter]").on("change",function(){
				var cls = $(this).attr("class");
				var checkedLength = $("input[class*='"+cls+"']:checked").length;
				if(checkedLength>1){
					alert("每季度最多有一个季度保养！");
					$(this).prop("checked",false);
				}
			});
			
			$("#modal-add-plan").on("show",function(){
				$(".equipMsg").hide();
				$("#validEquipId").val("NO");
			});
			
			$("#modal-add-plan").on("hide",function(e){
				//不是popover使modal隐藏时。
				if($(e.target).attr("id")!="planHelp"){
					$("#equipId").val("");
					$("#equipName").val("");
					$("#equipType").val("");
					$("#equipModal").val("");
					$("#remark").val("");
					$(".maint-month td").find("input").attr("disabled",false);
					$(".maint-month td").find("input[type='checkbox']").prop("checked",false);
				}
			});
			
			//增加或修改计划验证
			function planSubmitValidation(){
				//设备编号存在且有效
				if($("#validEquipId").val()!="YES"){
					alert("请填写有效的设备编号！");
					return false;
				}
				//至少选择了一个保养月份
				if($(".maint-month td div input:checkbox:checked").length<1){
					alert("请至少勾选一个保养月份checkbox！");
					return false;
				}
				return true;
			}
			
			//增加计划
			$("#btn-add-plan").on("click",function(){
//				alert(6);
				$("#equipId").removeAttr("disabled");
				$("#modal-add-plan-title").html("增加计划");
				$("#plan-add-confirm").show();
				$("#plan-modify-confirm").hide();
				$("#planTypeSelDiv").show();
				$("#planTypeInputDiv").hide();
				
				//前台设置过往或当前月份没有计划工单的不能勾选
				var currMonth = new Date().getMonth()+1;
				$(".maint-month td:lt('"+(currMonth)+"')").find("input").not(":checked").attr("disabled","disabled");
				$("#modal-add-plan").modal("show");
			});
			
			//增加计划 - 确认
			$("#plan-add-confirm").on("click",function(){
				if(planSubmitValidation()==false){
					return false;
				}
				var equipId = $("#equipId").val();
				var plan_type = $("#plan_type").val();
				var remark = $("#remark").val();
				if(remark.length > 395){
					alert("备注不能超过400个字符长度!");
					return;
				}
				//获取所选的保养月份
				var tds = $(".maint-month td");
				var arr = [];
				for(var i=0;i<tds.length;i++){
					var divs = $(tds[i]).find("div");
					var checkBox0 = $(divs[0]).find("input[type='checkbox']").is(":checked")?'Y':'';
					var checkBox1 = $(divs[1]).find("input[type='checkbox']").is(":checked")?'Q':'';
					var checkBox2 = $(divs[2]).find("input[type='checkbox']").is(":checked")?'M':'';
//					console.log(checkBox0+","+checkBox1+","+checkBox2);
					arr[i] = checkBox0+","+checkBox1+","+checkBox2;
				}
				
				$.ajax({
		    		url:'/PMMS/restful/plan/savePlanInfo',
					type:'post',
					dataType:'json',
					cache:false,
					data:{
						equipId:equipId,
						plan_type:plan_type,
						maint_jan:arr[0],
						maint_feb:arr[1],
						maint_mar:arr[2],
						maint_apr:arr[3],
						maint_may:arr[4],
						maint_jun:arr[5],
						maint_jul:arr[6],
						maint_agu:arr[7],
						maint_sep:arr[8],
						maint_oct:arr[9],
						maint_nov:arr[10],
						maint_dec:arr[11],
						remark:remark
					},
					beforeSend:function(xhr){
						//$("#loading").show();
					},
					complete:function (XMLHttpRequest, textStatus) {
						//$("#loading").hide();
						
					},
					success:function(backData,status){
						alert('success!!!');
						var detail = backData.data.detail;
						var equipInfo = $("#equipId").data("equipInfo");
						detail.equipModel = equipInfo.equipModel;
						detail.equipmentName = equipInfo.equipmentName;
						detail.equipmentNameId = equipInfo.equipNmId;
						detail.equipType = equipInfo.equipType;
						
						plan_table.fnAddData(backData.data.detail);
						returnToOldPage(plan_table,"add",null);
//						$("#modal-add-plan").modal("hide");
					},
					error:function(XmlHttpRequest){
						alert('error!!!');
					}
		    	});
			});
			
			//正在做修改的行索引
			var updateingRowIndex = null;
			//修改计划 modify plan
			$("#btn-modify-plan").on("click",function(){
				$("#equipId").attr("disabled","disabled");
				$("#modal-add-plan-title").html("修改计划");
				$("#plan-add-confirm").hide();
				$("#plan-modify-confirm").show();
				$("#planTypeSelDiv").hide();
				$("#planTypeInputDiv").show();
				$(".maint-month td").find("input[type='checkbox']").prop("checked",false);
				
				var check_boxes = $("input[name='plan_id']:checked");
				if(check_boxes.length > 1||check_boxes.length == 0){ alert('请选择一条记录，进行修改！');return;}
				
				//前端获取要修改的日记
				var thisPlan = $(check_boxes[0]);
				var id = $(check_boxes[0]).val();
				var thisRow = thisPlan.parent().parent().parent().parent()[0];
				updateingRowIndex = plan_table.fnGetPosition(thisRow);
				var thisRowData = plan_table.fnGetData(thisRow);
				$("#hiddenId").val(id);
				
				$("#equipId").val(thisRowData.equip_id);
				$("#equipName").val(thisRowData.equipmentName);
				var type = "";
				switch (thisRowData.equipType){
					case "1":type = "生产设备";break;
					case "2":type = "特种设备";break;
					case "3":type = "设施";break;
					default :type = "";
				}
//				alert(thisRowData.equipType+"=="+type+"=="+$("#equipTypeModal").val());
				$("#equipTypeModal").val(type);
				$('#equipModal').val(thisRowData.equipModel);
				$('#remark').val(thisRowData.remark);
				$('#plan_type_input').val(thisRowData.plan_type);
				
				//获取所选的保养月份
				var maint_jan = thisRowData.maint_jan;
				var maint_feb = thisRowData.maint_feb;
				var maint_mar = thisRowData.maint_mar;
				var maint_apr = thisRowData.maint_apr;
				var maint_may = thisRowData.maint_may;
				var maint_jun = thisRowData.maint_jun;
				var maint_jul = thisRowData.maint_jul;
				var maint_agu = thisRowData.maint_agu;
				var maint_sep = thisRowData.maint_sep;
				var maint_oct = thisRowData.maint_oct;
				var maint_nov = thisRowData.maint_nov;
				var maint_dec = thisRowData.maint_dec;
				
				var planArr0 = maint_jan.split(",");
				var planArr1 = maint_feb.split(",");
				var planArr2 = maint_mar.split(",");
				var planArr3 = maint_apr.split(",");
				var planArr4 = maint_may.split(",");
				var planArr5 = maint_jun.split(",");
				var planArr6 = maint_jul.split(",");
				var planArr7 = maint_agu.split(",");
				var planArr8 = maint_sep.split(",");
				var planArr9 = maint_oct.split(",");
				var planArr10 = maint_nov.split(",");
				var planArr11 = maint_dec.split(",");
				
				var tds = $(".maint-month td");
				setCheckBox(planArr0,$(tds[0]).find("div"));
				setCheckBox(planArr1,$(tds[1]).find("div"));
				setCheckBox(planArr2,$(tds[2]).find("div"));
				setCheckBox(planArr3,$(tds[3]).find("div"));
				setCheckBox(planArr4,$(tds[4]).find("div"));
				setCheckBox(planArr5,$(tds[5]).find("div"));
				setCheckBox(planArr6,$(tds[6]).find("div"));
				setCheckBox(planArr7,$(tds[7]).find("div"));
				setCheckBox(planArr8,$(tds[8]).find("div"));
				setCheckBox(planArr9,$(tds[9]).find("div"));
				setCheckBox(planArr10,$(tds[10]).find("div"));
				setCheckBox(planArr11,$(tds[11]).find("div"));
				
				//查询不能修改计划工单月份的月份列表
				$.ajax({
//		    		url:'/PMMS/restful/plan/getFinishedPWOMonth',
					url:'/PMMS/restful/plan/getUnchangeablePWOMonth',
					type:'post',
					dataType:'json',
					cache:false,
					data:{
						plan_id:$("#hiddenId").val()
					},
					success:function(backData,status){
						//disable保养完成的月份checkbox
						var finishedMonth = backData.data.list;
						//后台验证。已完成的和正在维修的
						for(var i=0;i<finishedMonth.length;i++){
							$(".maint-month td:eq('"+(finishedMonth[i]-1)+"')").find("input").attr("disabled","disabled");
						}
						//前台设置过往或当前月份没有计划工单的不能勾选
						var currMonth = new Date().getMonth()+1;
//						console.log(currMonth);
						$(".maint-month td:lt('"+(currMonth)+"')").find("input").not(":checked").attr("disabled","disabled");
						$("#modal-add-plan").modal("show");
					},
					error:function(XmlHttpRequest){
						alert('error!!!');
					}
		    	});
				
				
			});
			
			//给选择修改的checkbox设置
			function setCheckBox(planValue,divs){
				if(planValue[0]=="Y"){
					$(divs[0]).find("input[type='checkbox']").prop("checked",true);
				}
				if(planValue[1]=="Q"){
					$(divs[1]).find("input[type='checkbox']").prop("checked",true);
				}
				if(planValue[2]=="M"){
					$(divs[2]).find("input[type='checkbox']").prop("checked",true);
				}
			}
			
			//修改计划 - 确认
			$("#plan-modify-confirm").on("click",function(){
				if($(".maint-month td div input:checkbox:checked").length<1){
					alert("请至少勾选一个保养月份checkbox！");
					return false;
				}
				var hiddenId = $("#hiddenId").val();
				var remark = $("#remark").val();
				//获取所选的保养月份
				var tds = $(".maint-month td");
				var arr = [];
				for(var i=0;i<tds.length;i++){
					var divs = $(tds[i]).find("div");
					var checkBox0 = $(divs[0]).find("input[type='checkbox']").is(":checked")?'Y':'';
					var checkBox1 = $(divs[1]).find("input[type='checkbox']").is(":checked")?'Q':'';
					var checkBox2 = $(divs[2]).find("input[type='checkbox']").is(":checked")?'M':'';
					arr[i] = checkBox0+","+checkBox1+","+checkBox2;
				}
				
				$.ajax({
		    		url:'/PMMS/restful/plan/modifyPlanInfo',
					type:'post',
					dataType:'json',
					cache:false,
					data:{
						id:hiddenId,
						maint_jan:arr[0],
						maint_feb:arr[1],
						maint_mar:arr[2],
						maint_apr:arr[3],
						maint_may:arr[4],
						maint_jun:arr[5],
						maint_jul:arr[6],
						maint_agu:arr[7],
						maint_sep:arr[8],
						maint_oct:arr[9],
						maint_nov:arr[10],
						maint_dec:arr[11],
						remark:remark
					},
					beforeSend:function(xhr){
						//$("#loading").show();
					},
					complete:function (XMLHttpRequest, textStatus) {
						//$("#loading").hide();
					},
					success:function(backData,status){
						alert('success!!!');
						var thisRowData = plan_table.fnGetData(updateingRowIndex);
						var updatedRowData = backData.data.detail;
						thisRowData.maint_jan = updatedRowData.maint_jan;
						thisRowData.maint_feb = updatedRowData.maint_feb;
						thisRowData.maint_mar = updatedRowData.maint_mar;
						thisRowData.maint_apr = updatedRowData.maint_apr;
						thisRowData.maint_may = updatedRowData.maint_may;
						thisRowData.maint_jun = updatedRowData.maint_jun;
						thisRowData.maint_jul = updatedRowData.maint_jul;
						thisRowData.maint_agu = updatedRowData.maint_agu;
						thisRowData.maint_sep = updatedRowData.maint_sep;
						thisRowData.maint_oct = updatedRowData.maint_oct;
						thisRowData.maint_nov = updatedRowData.maint_nov;
						thisRowData.maint_dec = updatedRowData.maint_dec;
						
						plan_table.fnUpdate(thisRowData,updateingRowIndex,undefined,true,true);//数据改变后，不draw表格，否则会回到第一页。
						returnToOldPage(plan_table,"update",null);
						$("#modal-add-plan").modal("hide");
					},
					error:function(XmlHttpRequest){
						alert('error!!!');
					}
		    	});
			});
			
			//点击删除计划，验证是否可删除 
			$("#btn-delete-plan").on("click",function(){
				var check_boxes = $("input[name='plan_id']:checked");
				
				if(check_boxes.length<=0){ alert('请至少选择一条记录！');return;}
				
				var thisPlan = $(check_boxes[0]);
				var thisRow = thisPlan.parent().parent().parent().parent()[0];
				var thisRowData = plan_table.fnGetData(thisRow);
				var text = "确定删除编号是 "+thisRowData.equip_id+ " 的设备吗？";
				$("#deleteConfirmText").html(text);
				$.ajax({
					url:'/PMMS/restful/plan/validateSinglePlan',
					type:'post',
					dataType:'json',
					cache:false,
					data:{
						plan_id:$(check_boxes).val()
					},
					beforeSend:function(xhr){
						//$("#loading").show();
					},
					complete:function (XMLHttpRequest, textStatus) {
						//$("#loading").hide();
					},
					success:function(backData,status){
						var finishedWONum = backData.data.finishedWONum;
						if(finishedWONum!=0){
							if(confirm("本计划有部分计划工单已完成或正在维修。选择“OK”，您将强制删除本条计划，以及相应的计划工单！")){
								$("#modal-delete-plan").modal("show");
							}else{
								return false;
							}
						}else{
							$("#modal-delete-plan").modal("show");
						}
					},
					error:function(XmlHttpRequest){
						alert('error!!!');
					}
		    	});
				
			});
			
			//删除计划确认
			$("#btn-remove-confirm").on("click",function(){
				var check_box = $("input[name='plan_id']:checked");
	            $.ajax({
	            	type: "POST",
	            	url: "/PMMS/restful/plan/removePlanInfo",
	    			dataType: "json",
	                data:{'planId':$(check_box).val()},
	                success: function(jsonData){
	    				if(jsonData.data.effectedRowNum!=0){
	    					alert("删除成功！");
	    					$("#modal-delete-plan").modal("hide");
				            var deletedRow = null;
				            deletedRow = $(check_box).parent().parent().parent().parent()[0];
    						plan_table.fnDeleteRow(deletedRow,null,true);
	    				}else{
	    					alert("删除失败!");
	    				}
	    			},
	    			error: function(){
	    				alert("操作失败！");
	    			}
	            });
		        return false;
			});
			
			//返回到之前操作的页码
			function returnToOldPage(dataTables,actionType,effectedNumber){
				var settings = dataTables.fnSettings();
				//_iDisplayStart:当前页的第一条记录的index;_iDisplayLength:每页显示几条
				var displayStart = updateingRowIndex;//settings._iDisplayStart     updateingRowIndex
				var displayLength = settings._iDisplayLength;
				var totalNumber = settings.aoData.length;
				var oldPageIndex = displayStart/displayLength;
				switch(actionType){
					case "add":oldPageIndex = Math.ceil(totalNumber/displayLength)-1;break;
					case "update":oldPageIndex = Math.floor(displayStart/displayLength);break;
					case "remove":
						if(displayStart+displayLength>=totalNumber){
							var lastPageRecordNumber = totalNumber%displayLength;
							if(lastPageRecordNumber==effectedNumber){
								oldPageIndex = oldPageIndex-1;
							}
						}
						break;
					default:break;
				}
//				alert("行索引（开始）是： "+updateingRowIndex+"每页大小是： "+displayLength+"运来页数是："+oldPageIndex);
				dataTables.fnPageChange(oldPageIndex);
			}
			
			//加载月度计划
			$.ajax({
				cache:false,
				type: "POST",
				url:  "/PMMS/restful/plan/getCurrMonthPlans",
				dataType: "json",
				success: function(jsonData){
					if(jsonData.success){
						var dataList = jsonData.data.list_month;
						month_table = $('#plan-month-table').iidsBasicDataGrid( {
							"bProcessing": true,
					        "bPaginate":true,
					        "bAutoWidth": false,
					        "bSort": true,
					        "bDestroy":true,
					        
							 "useFloater": false, //turn off cell filtering layer  true这一行影响fnRowCallback  fnPageChange的效用
//							 "plugins":['G'],
							 
							 "aLengthMenu": [[10, 25, 2, -1], [10, 25, 2, "All"]],
							//"iDisplayLength":2,
							 //"iDisplayStart":0,
							 
							 "oLanguage": {
						            "sSearch": "查找:",
						            "sZeroRecords": "没数据...",
						            "sLengthMenu": "每页 _MENU_ 条",
						            "sNext": "下页",
						            "sInfo": "_START_ - _END_ of _TOTAL_",
						            "sInfoEmpty": "_START_ - _END_ of _TOTAL_"
						          },
							 
							 "aoColumns": [
								{sTitle: '序号<br>/NO.', mData: 'equipId',"bSortable":false,"bSearchable": false},
								{sTitle: '是否完成<br>/Status', mData: 'woStatus'},
								{sTitle: '设备名<br>/EQName', mData: 'equipName'},
								{sTitle: '设备型号<br>//EQType', mData: 'equipModel'},
								{sTitle: '设备编号<br>/EQNO.', mData: 'equipId'},
								//{sTitle: '12月', mData: 'planMonth'},
								{sTitle: '负责人<br>/Maintainer', mData: 'mechanicSSO'},
								{sTitle: '计划保养月份<br>/PlanMaintMonth', mData: 'plan_start_month'},
								{sTitle: '保养开始时间<br>/StartDate', mData: 'maint_start_date'},
								{sTitle: '保养结束时间<br>/EndDate', mData: 'maint_end_date'}
							 ],
							 "aaSorting":[[6,"asc"]],
							"aaData":dataList,
							"fnRowCallback":function(nRow,aData,iDisplayIndex,iDisplayIndexFull){
								var status = "";
								switch (aData.woStatus){
									case "1":status = "未开始";break;
									case "2":status = "维修中";break;
									case "3":status = "关闭";break;
									default :status = "";
								}
								$("td:eq(1)",nRow).html(status);
								$("td:eq(5)",nRow).html(
										aData.lastname+" "+aData.firstname
								);
								var color = "";
								var maintMonthText = "";
								var currMonth = new Date().getMonth()+1;
								if(currMonth==aData.planMonth){
									color = "green";
								}else{
									color = "gray";
								}
								$("td:eq(6)",nRow).html(
										"<font color='"+color+"'>"+aData.plan_start_month+"</font>"
								);
							 },
							 //第一列为序号自增列
							 "fnDrawCallback": function ( oSettings ) {
						            /* Need to redo the counters if filtered or sorted */
						            if ( oSettings.bSorted || oSettings.bFiltered )
						            {
						                for ( var i=0, iLen=oSettings.aiDisplay.length ; i<iLen ; i++ )
						                {
						                    $('td:eq(0)', oSettings.aoData[ oSettings.aiDisplay[i] ].nTr ).html( i+1 );
						                }
						            }
						        }
					    });
						
					}
				}
			});

			
			//初始化datatable
			function buildTable(maintYear,equipType){
				$('#plan-year-div').empty().append('<div class="input-append flR pull-right">'+
						'<input type="text" class="input-medium search-query" data-filter-table="plan-year-table"><button class="btn btn-icon"><i class="icon-search"></i></button>'+
						'</div><table id="plan-year-table" class="table table-bordered table-striped tablebrdr mrgzero width100p" data-table-name="plan-year-table"></table>');
				$.ajax({
					cache:false,
					type: "POST",
					url:  "/PMMS/restful/plan/getAllPlans",
					dataType: "json",
					success: function(jsonData){
						if(jsonData.success){
							var dataList = jsonData.data.list;
							$('#plan-year-table').iidsBasicDataGrid( {//iidsBasicDataGrid  dataTable
								"bProcessing": true,
						        "bPaginate":true,
//						        "sPaginationType": "full_numbers",//" scrolling",
						        "bAutoWidth": false,
						        "bSort": true,
						        "bDestroy":true,
						        
								 "useFloater": false, //turn off cell filtering layer  true这一行影响fnRowCallback  fnPageChange的效用
//								 "plugins":['G'],
								 
								 "aLengthMenu": [[10, 25, 2, -1], [10, 25, 2, "All"]],
								//"iDisplayLength":2,
								 //"iDisplayStart":0,
								 
								 "oLanguage": {
							            "sSearch": "查找:",
							            "sZeroRecords": "没数据...",
							            "sLengthMenu": "每页 _MENU_ 条",
							            "sNext": "下页",
							            "sInfo": "_START_ - _END_ of _TOTAL_",
							            "sInfoEmpty": "_START_ - _END_ of _TOTAL_"
							          },
								 
								 "aoColumns": [
									 {sTitle: "<label for='selectAll'>选择</label>", mData: 'id',"sDefaultContent":"","sClass":"center","bSortable":false,"bSearchable": true, "bVisible": true,"filter":false},
									 {sTitle: '序号<br>/NO.', mData: 'plan_id',"sClass":"center","filter":false,"bSortable":false,"bSearchable": false},
									 {sTitle: '计划编号<br>/PlanNO.', mData: 'plan_id',"sClass":"center","filter":false},
									 {sTitle: '保养内容<br>/MaintItem', mData: 'creator',"filter":false},//maint_item
									 {sTitle: '设备编号<br>/EQNO.', mData: 'equip_id'},
									 {sTitle: '设备名<br>/EQName', mData: 'equipmentName'},
									 {sTitle: '设备型号<br>/EQModel', mData: 'equipModel'},
//									 {sTitle: '保养类型', mData: 'plan_type'},
									 {sTitle: '使用部门<br>/Dept', mData: 'deptNm'},
									 {sTitle: '1', mData: 'maint_jan'},
									 {sTitle: '2', mData: 'maint_feb'},
									 {sTitle: '3', mData: 'maint_mar'},
									 {sTitle: '4', mData: 'maint_apr'},
									 {sTitle: '5', mData: 'maint_may'},
									 {sTitle: '6', mData: 'maint_jun'},
									 {sTitle: '7', mData: 'maint_jul'},
									 {sTitle: '8', mData: 'maint_agu'},
									 {sTitle: '9', mData: 'maint_sep'},
									 {sTitle: '10', mData: 'maint_oct'},
									 {sTitle: '11', mData: 'maint_nov'},
									 {sTitle: '12', mData: 'maint_dec'}
								 ],
								 
								"aaData":dataList,
								"fnRowCallback":function(nRow,aData,iDisplayIndex,iDisplayIndexFull){
									$('td:eq(0)', nRow).html(
			                    			'<div class="radio">'+
			                    				'<label>'+
			                    					'<input type="radio" name="plan_id" class="radio" value="'+aData.plan_id+'">'+
			                    				'</label>'+
			                    			'</div>'
			                    	);
									$("td:eq(3)",nRow).html(
											"<button class='btn btn-primary maint-item' title='查看此计划的保养内容'>" +
												"<div class='icon-search'></div>" +
											"</button>"
									);
								 },
								 //第二列为序号自增列
								 "fnDrawCallback": function ( oSettings ) {
							            /* Need to redo the counters if filtered or sorted */
							            if ( oSettings.bSorted || oSettings.bFiltered )
							            {
							                for ( var i=0, iLen=oSettings.aiDisplay.length ; i<iLen ; i++ )
							                {
							                    $('td:eq(1)', oSettings.aoData[ oSettings.aiDisplay[i] ].nTr ).html( i+1 );
							                }
							            }
							        }
						    });
							//***get datatables object***
							plan_table = $('#plan-year-table').dataTable();
							//查询保养计划按钮绑定事件  bind row click event to query maint item
							$("#plan-year-table tr").on("click",".maint-item",function(){
								
								var currRow = $(this).parent().parent();
								var currRowData = $("#plan-year-table").dataTable().fnGetData(currRow[0]);
								var equipName = currRowData.equipmentNameId;
								$("#exportMaintItem").data("equipNameId",equipName);
//								alert(currRowData);
								$.ajax({
									url:'/PMMS/restful/maintItem/getMaintItems',
									type:'post',
									dataType:'json',
									cache:false,
									data:{
										equipName:equipName
									},
									beforeSend:function(xhr){
										//$("#loading").show();
									},
									complete:function (XMLHttpRequest, textStatus) {
										//$("#loading").hide();
									},
									success:function(backData,status){
										var items = backData.data.list;
										var itemContainer = $(".maint-item-view").empty();
										$("#modal-maint-item-title").html(currRowData.equip_id+"设备");
										
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
									},
									error:function(XmlHttpRequest){
										alert('error!!!');
									}
						    	});
							});
						}
					},
					data:{
						maintYear:maintYear,
						equipType:equipType
					}
				});
			}
		});