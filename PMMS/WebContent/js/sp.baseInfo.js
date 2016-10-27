define(['pmms'],function(pmms){
	//初始化备件信息
	$('.datepicker').datepicker();
	//$("select").chosen();
	var selectObj = $("#selectWOModal select:eq(0)");
	pmms.getDeptInfo(selectObj);
	
	var init = function(){
		var opts ={
			url:"../restful/sparePart/getSPInfo",
			data:{
				
			}
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
					var spHtml='';
						spHtml+='<table class="table table-striped table-bordered table-condensed" data-table-name="spinfoTable006">';
						spHtml+='<thead>';
						spHtml+='</thead>';
						spHtml+='<tbody>';
						spHtml+='</tbody>';
						spHtml+='</table>';
						$("#wrappedspinfoTable006").empty();
						$("#wrappedspinfoTable006").append(spHtml);
						buildTab006(jsonData.data);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(textStatus);
			}
		});
	}
	//build tab006
	function buildTab006(data){
		var spHeader=[{"sTitle": "选择/Select","mData":"selectItem"},{"sTitle": "编号/SN","mData":"sparePartId"},
					  {"sTitle": "名称/Name","mData":"sparePartName"},{"sTitle": "规格型号/Type","mData":"sparePartType"},
					  {"sTitle": "生产厂商/Source","mData":"source"},{"sTitle": "单位/Measurement","mData":"measurement"},
					  {"sTitle": "单价/Price(RMB)","mData":"price"},{"sTitle": "库存/Stock","mData":"stock"},
					  {"sTitle": "安全库存/SafetyStock","mData":"safetyStock"},{"sTitle":"建议安全库存/SysSafetyStock","mData":"sysSafetyStock"},
					  {"sTitle":"存放地点/Location","mData":"location"},{"sTitle": "备注/Remark","mData":"remark"}];   
		var oTable = $("#wrappedspinfoTable006 table").iidsBasicDataGrid({
			"isResponsive": true,
	        "useFloater": false,
	        "bAutoWidth": false,
	        "bDestroy": true,
			'aaSorting': [],
			"aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
			"aoColumns": spHeader, 
	        "aaData": data.listSpareInfo,
			"oColReorder": {"iFixedColumns": 15},
			"fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
				$('td:eq(0)',nRow).html('<input type="checkbox" targetName="spInfo" value="'+aData.id+'">');
			}
		});
		oTable = $("#wrappedspinfoTable006 table").dataTable();
		
		//init edit
		$("#tab006 button:eq(1)").unbind('click').on('click',function(){
			$("#editSPInfo .btn:eq(0)").attr("disabled",false);
			var chs = $("#wrappedspinfoTable006 :checkbox[targetName='spInfo']:checked").size();
			if(chs == 0){
				alert('请选择一条备件信息进行编辑');
				return false;
			}
			if(chs>1){
				alert('只能选择一条备件信息进行编辑');
				return false;
			}			
			var pos = $("#wrappedspinfoTable006 :checkbox[targetName='spInfo']:checked").parent().parent()[0];
			var rowData = oTable.fnGetData(pos);
			$("#editSPInfo").modal('show');
			$("#editSPInfo input:eq(0)").val(rowData.sparePartId).attr('disabled','true');
			$("#editSPInfo input:eq(1)").val(rowData.sparePartName).attr('disabled','true');
			$("#editSPInfo input:eq(2)").val(rowData.sparePartType); 
			$("#editSPInfo input:eq(3)").val(rowData.source); 
			$("#editSPInfo input:eq(4)").val(rowData.measurement); 
			$("#editSPInfo input:eq(5)").val(rowData.price); 
			$("#editSPInfo input:eq(6)").val(rowData.safetyStock);
			$("#editSPInfo input:eq(7)").val(rowData.location); 
			$("#editSPInfo textarea").val(rowData.remark);
			$("#sparePartId").val(rowData.id);
		});		
	}
	//init save
	$("#tab006 button:eq(0)").on('click',function(){
		$("#addSPInfo input:eq(0)").val("");
		$("#addSPInfo input:eq(1)").val("");
		$("#addSPInfo input:eq(2)").val(""); 
		$("#addSPInfo input:eq(3)").val(""); 
		$("#addSPInfo input:eq(4)").val(""); 
		$("#addSPInfo input:eq(5)").val(""); 
		$("#addSPInfo textarea").val("");
		$("#addSPInfo .btn:eq(0)").attr("disabled",false);
		$("#addSPInfo").modal('show');		
	});
	//save spare part info
	$("#addSPInfo .btn:eq(0)").on('click',function(){
		var sparePartName = $("#addSPInfo input:eq(0)").val().trim();
		if(sparePartName==""){
			alert("请输入备件的名称");
			return false;
		}
		var price = $("#addSPInfo input:eq(4)").val().trim();
		var reg = /\d{1,10}(\.\d{1,2})?$/;
		var regFlag = reg.test(price);
		if(!regFlag){
			alert("价格格式不正确，正确格式为 0.00的形式");
			return false;
		}
		var data={
			sparePartName:sparePartName,
			sparePartType:$("#addSPInfo input:eq(1)").val().trim(),
			source:$("#addSPInfo input:eq(2)").val().trim(),
			measurement:$("#addSPInfo input:eq(3)").val().trim(),
			price:price,
			location:$("#addSPInfo input:eq(5)").val().trim(),
			remark:$("#addSPInfo textarea").val().trim()
		}
		if($("#addSPInfo .btn:eq(0)").attr("disabled")==undefined){
			$.ajax({
				type:"POST",
				url:"../restful/sparePart/addSPInfo",
				dataType:"json",
				async:true,
				data:data,
				beforeSend:function(XMLHttpRequest){
					$("#addSPInfo .btn:eq(0)").attr("disabled",true);
				},
				success:function(jsonData){
					if(jsonData.success){
						//console.log("添加备件成功");
						$("#addSPInfo").modal('hide');
						init();
						alert('添加备件成功');					
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					$("#addSPInfo").modal('hide');
					alert(textStatus);
				}
			});
		}else{
			alert("正在保存备件信息，请不要重复操作");
		}
	});
	//edit spInfo
	$("#editSPInfo .btn:eq(0)").on('click',function(){
		var sparePartId = $("#editSPInfo input:eq(0)").val().trim();
		var price = $("#editSPInfo input:eq(5)").val().trim();
		var ss = $("#editSPInfo input:eq(6)").val().trim();      //安全库存
		var reg = /\d{1,10}(\.\d{1,2})?$/;
		var regFlag = reg.test(price);
		if(!regFlag){
			alert("价格格式不正确，正确格式为 0.00的形式");
			return false;
		}	
		var ssRegFlag = reg.test(ss);
		if(!ssRegFlag){
			alert("安全库存输入不正确，请输入一个非负数");
			return false;
		}
		$("#addSPInfo .btn:eq(0)").unbind();
		var data={
			id:$("#sparePartId").val(),
			sparePartId:sparePartId,
			sparePartName:$("#editSPInfo input:eq(1)").val().trim(),
			sparePartType:$("#editSPInfo input:eq(2)").val().trim(),
			source:$("#editSPInfo input:eq(3)").val().trim(),
			measurement:$("#editSPInfo input:eq(4)").val().trim(),
			price:price,
			safetyStock:ss,
			location:$("#editSPInfo input:eq(7)").val().trim(),
			remark:$("#editSPInfo textarea").val().trim()			
		}
		if($("#editSPInfo .btn:eq(0)").attr("disabled")==undefined){
			//alert("第一次保存， 发送请求");
			//console.log("第一次保存发送请求");
			$.ajax({
				type:"POST",
				url:"../restful/sparePart/updateSPInfo",
				dataType:"json",
				data:data,
				beforeSend:function(XMLHttpRequest){
					$("#editSPInfo .btn:eq(0)").attr("disabled",true);
				},
				success:function(jsonData){
					if(jsonData.success){
						$("#editSPInfo").modal('hide');
						init();
						alert(sparePartId+" "+"修改成功");
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert(textStatus);
				}
			});
		}else{
			alert("正在修改，请不要重复更新");
			//console.log("重复保存，不能执行");
		}
	});
	//delete spInfo record
	var delFlag = false;
	$("#tab006 button:eq(2)").on('click',function(){
		var chs = $("#wrappedspinfoTable006 :checkbox[targetName='spInfo']:checked");
		var len = chs.size();
		if(len == 0){
			alert('请选择一条备件信息进行删除');
			return false;
		}
		var values = "";
		$.each(chs,function(index,item){
			values+=item.value+",";
		});
		//console.log(values);
		if(!delFlag){
			//console.log("首次删除操作");
			var flag = confirm("确定要删除这些备件信息吗?");
			if(flag){
				delFlag = true;
				$.ajax({
					type:"POST",
					url:"../restful/sparePart/deleteSPInfo",
					dataType:"json",
					data:{
						ids:values
					},
					beforeSend:function(XMLHttpRequest){
						
					},
					complete:function(XMLHttpRequest, textStatus){
						delFlag = false;
					},
					success:function(JsonData){
						if(JsonData.success){
							init();
							alert('删除成功!');
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						alert(textStatus);
					}
				});
			}
		}else{
			//console.log("重复删除操作");
		}
	});
	
	//export to excel
	$("#tab006 a[class='btn btn-icon']").on('click',function(){
		window.location.href="../restful/sparePart/spInfoExportToExcel?type=1";
	});
	
	//计算备件系统建议安全库存
	$('#tab006 button:eq(3)').on('click',function(){
		$.ajax({
			type:"POST",
			url:"../restful/sparePart/calculateSysSafetyStock",
			dataType:"json",
			success:function(jsonData){
				if(jsonData.success){
					init();
					alert("calculate successfully!");
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(textStatus);
			}
		});
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
