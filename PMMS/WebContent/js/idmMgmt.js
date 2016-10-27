 require( ['jquery','ge-bootstrap', 'datagrids',
           'datatables', 'datatables-scroller','datepicker'], function($) {
	 		buildStockTable("all");
	 		//var stockNum=null;
	 	//	var isRefresh=false;
	 		$(function(){
				$('.datepicker').datepicker();
			});
	 		//buildScanInTable("idm1001");
	 		//点击库存间接物料tab
	 		$("#tab01Nav").on('click',function(){
	 			buildStockTable("all");
	 		});
	 		//点击出入库记录tab
	 		$("#tab02Nav").on('click',function(){
	 			buildRecordTable('','','');
	 		});
	 		//点击安全库存
	 		$("#tab03Nav").on('click',function(){
	 			buildSaveStockTb();
	 		});
	 		
	 		//间接物料类别改变事件
	 		$(".idm_type").on("change",function(){
	 			var idmTypeId = $(this).attr("id");
	 			
	 			if(idmTypeId=="idmType"){
	 				var idmTypeId = $(this).val(); //1,2
					buildStockTable(idmTypeId);
	 			}
	 			if(idmTypeId=="type_add"){
	 				var idmTypeId = $("#type_add").val();
	 				getSubType($("#subType_add"),idmTypeId);
	 			}
				
	 			if(idmTypeId=="type_Edit"){
	 				var idmTypeId = $("#type_Edit").val();
	 				getSubType($("#subType_Edit"),idmTypeId);
	 			}
	 			
	 			if(idmTypeId=="detail_add_type"){
	 				var idmTypeId = $("#detail_add_type").val();
	 				getSubType($("#detail_sub_type_add"),idmTypeId);
	 			}
			});
	 		
	 		//间接物料子类别改变事件
	 		$(".idm_subType").on("change",function(){
	 			var subTypeId = $(this).attr("id");
	 			if(subTypeId=="subType_add"){
	 				var idmTypeId = $("#type_add").val();
	 				getDetailType($("#type_detail_add"),idmTypeId, $("#subType_add").val());
	 			}
				
	 			if(subTypeId=="subType_Edit"){
	 				var idmTypeId = $("#type_Edit").val();
	 				getDetailType($("#type_detail_Edit"),idmTypeId,$("#subType_Edit").val());
	 			}
	 			if(subTypeId=="detail_sub_type_add"){
	 				var subTypeId = $("#detail_sub_type_add").val();
	 				var typeId=$("#detail_add_type").val();
	 					$.ajax({
	 			    		url:'/PMMS/restful/IdmMgmt/getAssignDetailTypeId',
	 						type:'post',
	 						dataType:'json',
	 						cache:false,
	 						data:{
	 							typeId:typeId,
	 							subTypeId:subTypeId
	 						},
	 						beforeSend:function(xhr){
	 							//$("#loading").show();
	 						},
	 						complete:function (XMLHttpRequest, textStatus) {
	 							//$("#loading").hide();
	 						},
	 						success:function(backData,status){
	 							var assignDetailId=backData.data.assignDetailTypeId;
	 							  $("#detail_add_detailId").val(assignDetailId);
	 						},
	 						error:function(XmlHttpRequest){
	 							alert('error!!!');
	 						}
	 			    		
	 			    	});
	 				
	 			}
			});
	 		//删除间接物料
	 		$("#deleStockIdm").click(function(){
				var selectIdmId=$('input:radio[name="radio"]:checked').val();
				 var check_boxes = $('input:radio[name="radio"]:checked');
				if(check_boxes.length > 1||check_boxes.length == 0){ alert('请选择一条记录，进行删除！');return;}
				if(!confirm('你确定要删除?')) return;
				$.ajax({
		    		url:'/PMMS/restful/IdmMgmt/deleIdmbyIdmId',
					type:'post',
					dataType:'json',
					cache:false,
					data:{
						idmId:selectIdmId
					},
					beforeSend:function(xhr){
						//$("#loading").show();
					},
					complete:function (XMLHttpRequest, textStatus) {
						//$("#loading").hide();
					},
					success:function(backData,status){
						alert('删除成功!!!');
					//	$("#equipmentInfo").modal("hide");
						buildStockTable("all");
					},
					error:function(XmlHttpRequest){
						alert('error!!!');
					}
		    		
		    	});
			});
	 			 		
	 		//扫描入库按钮
	 		$("#inBtn").click(function(){
	 			 $("#scanInTb").empty();
	 			$("#scanId").val("");
	 			$("#amount_in").val("");
	 			$("#LeadTime").val("");
	 			$("#poIn").val("");
	 			$("#idmInInfo").modal("show");
	 			buildScanInTable(0);
			});
	 		
	 		//扫描入库确定按钮
	 		$("#saveBtn_in").click(function(){
	 			var idmId=$.trim($("#scanId").val());
	 			//var transId=201502051829;
	 			var amount=$.trim($("#amount_in").val());
	 			var leadTime=$.trim($("#LeadTime").val());
	 			var poIn=$.trim($("#poIn").val());
	 			var flag=true;
	 			var reg = /\d{1,10}(\.\d{1,2})?$/;
	 			if(idmId==""){
	 				alert("请输入间接物料编号！");
	 				flag=false;
	 			}
	 			if(amount==""){
	 				alert("请输入数量！");
	 				flag=false;
	 			}else{
	 				var regFlag = reg.exec(amount);
	 				if(!regFlag){
						alert("您输入的数量格式不正确，请重新输入！");
						flag=false;
					}else if(isNaN(amount)){
						alert("您输入的数量格式不正确，请输入数字！");
						return false;
					}
	 			}
	 			if(leadTime!=""){
	 				var regFlag = reg.exec(leadTime);
	 				if(!regFlag){
						alert("您输入的时间差格式不正确，请重新输入！");
						flag=false;
					}else if(isNaN(leadTime)){
						alert("您输入的时间差格式不正确，请输入数字！");
						return false;
					}
	 			}
	 			if(poIn==""){
	 				alert("请输入批次号！");
	 				flag=false;
	 			}
	 			if(flag&&$("#saveBtn_in").attr("disabled")==undefined){
	 				$.ajax({
			    		url:'/PMMS/restful/IdmMgmt/scanInStock ',
						type:'post',
						dataType:'json',
						cache:false,
						data:{
							"idmId":idmId,
							//"transId":transId,
							"amount":amount,
							"leadTime":leadTime,
							"poIn":poIn
						},
						beforeSend:function(xhr){
							$("#saveBtn_in").attr("disabled",true);
						},
						complete:function (XMLHttpRequest, textStatus) {
							$("#saveBtn_in").removeAttr("disabled");
						},
						success:function(backData,status){
							if(backData.data.details!=null){
								alert("success");
								$("#idmInInfo input").val("");
								buildScanInTable(0);
							//	$("#idmInInfo").modal("hide");
							//	buildStockTable("all");
							}else{
								alert("该间接物料不存在！请重新输入物料编号！");
							}
							
						},
						error:function(XmlHttpRequest){
							alert('error!!!');
						}
			    	});
	 			}
	 			
			});
			
	 		//扫描入库窗口的条形码扫描输入框
	 		$("input[name='scanIn']").unbind().change(function(){
	 			var idmId = $(this).val(); 
	 			if(idmId!=""){
	 				buildScanInTable(idmId);
	 			}
	 		});
	 		//扫描出库按钮
			$("#outBtn").click(function(){
				$("#scanOutTb").empty();
				$("#scanId_out").val(""); 
				$("#amount_out").val("");
				$("#receiver").val("");
				$("#idmOutInfo").modal("show");
				buildScanOutTable(0);
				
			});
			//扫描出库窗口的输入框值改变事件
//			$('#scanId_out').bind('input propertychange', function(){
//				var idmId = $(this).val();
//				//alert(idmId);
//				if(idmId!=""){
//					buildScanOutTable(idmId);
//	 			}
//			}); 
	 		$("input[name='scanOut']").unbind().change(function(){
	 			var idmId = $(this).val(); 
	 			if(idmId!=""){
	 				buildScanOutTable(idmId);
	 			}
	 		});
	 		
	 		//验证SSO是否正确
	 		$("#receiver").on('blur',function(){
	 			var sso = $(this).val();
	 			//console.log("sso "+sso);
//	 			if(sso == "" || sso == null){
//	 				alert("SSO不能为空，请输入!");
//	 				return false;
//	 			}
	 			var ssoReg = /^[0-9]*$/;
	 			var ssoRegFlag = ssoReg.exec(sso);
	 			if(!ssoRegFlag){
	 				$("#receiver").val("");
	 				alert("SSO输入无效，请重新输入!!!");
	 				return false;
	 			}
	 			if(sso!=""){
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
		 							$("#receiver").val("");
		 							alert("SSO不正确，请重新输入");
		 						}
		 					}
		 				},
		 				error:function(XMLHttpRequest, textStatus, errorThrown){
		 					alert(textStatus);
		 				}
		 			});
	 			}
	 			
	 		});
	 		
			//扫描出库确认按钮
			$("#deleBtn_out").click(function(){
	 			var idmId=$.trim($("#scanId_out").val());
	 			//var transId=201502051829;
	 			var amount=$.trim($("#amount_out").val());
	 			var receiver=$.trim($("#receiver").val());
	 			var flag=true;
	 			var reg = /\d{1,10}(\.\d{1,2})?$/;
	 			if(idmId==""){
	 				alert("请输入间接物料编号！");
	 				flag=false;
	 			}
	 			if(amount==""){
	 				alert("请输入数量！");
	 				flag=false;
	 			}else{
	 				var regFlag = reg.exec(amount);
	 				if(!regFlag){
						alert("您输入的数量格式不正确，请重新输入！");
						flag=false;
					}else if(isNaN(amount)){
						alert("您输入的数量格式不正确，请输入数字！");
						return false;
					}
	 			}
	 			if(receiver==""){
	 				alert("请输入领料人sso！");
	 				flag=false;
	 			}
	 			if(flag&&$("#deleBtn_out").attr("disabled")==undefined){
	 				$.ajax({
			    		url:'/PMMS/restful/IdmMgmt/scanOutStock ',
						type:'post',
						dataType:'json',
						cache:false,
						data:{
							"idmId":idmId,
							//"transId":transId,
							"amount":amount,
							"receiver":receiver
						},
						beforeSend:function(xhr){
							$("#deleBtn_out").attr("disabled",true);
						},
						complete:function (XMLHttpRequest, textStatus) {
							$("#deleBtn_out").removeAttr("disabled");
						},
						success:function(backData,status){
								if(backData.data.details==null){
									alert("该间接物料不存在！请重新输入物料编号！");
									
									
								}else if(backData.data.details==0){
									alert("success");
									$("#idmOutInfo input").val("");
									buildScanOutTable(0);
									//$("#idmOutInfo").modal("hide");
									//buildStockTable("all");
								}else{
									alert("库存量不足");
								}
							
							
						},
						error:function(XmlHttpRequest){
							alert('error!!!');
						}
			    	});
	 			}
	 			
			});
			
			//扫描入出库窗口 关闭按钮 刷新页面
			$("#in_close,#out_close").click(function(){
				//buildStockTable("all");
				buildRecordTable('','','');
			});
			
			//新增间接物料
			$("#addStockIdm").click(function(){
				$("#addStockInfo input").val("");
				$("#addStockInfo select").val("");
				$("#addStockInfo").modal("show");
			});
			//新增间接物料确认按钮
			$("#add_idm").on("click",function(){
				var reg = /\d{1,10}(\.\d{1,2})?$/;
				var typeId=$("#type_add").val();
				if(typeId=="E"){
					var type="费用";
				}else{
					var type="固定资产";
				}
				var subTypeId=$("#subType_add").val();
				var detailTypeId=$("#type_detail_add").val();
				var idmNm=$.trim($("#idmNm_add").val());
				var source=$.trim($("#source_add").val());
				var size=$.trim($("#size_add").val());
				var measurement=$.trim($("#measurement_add").val());
				var price=$.trim($("#price_add").val());
				var amount=$.trim($("#amount_add").val());
				var remark=$.trim($("#remark_add").val());
				var position=$.trim($("#position_add").val());
				var flag=true;
				if(typeId==null||typeId=="0"){
					alert("请选择类别！");
					flag=false;
				}
				if(subTypeId==""||subTypeId==0){
					alert("请选择子类！");
					flag=false;
				}
				if(detailTypeId==""||detailTypeId=="0"){
					alert("请选择明细项目！");
					flag=false;
				}
				if(idmNm==""){
					alert("请输入间接物料名称！");
					flag=false;
				}
				if(price==""){
					
				}else{
					var regFlag = reg.exec(price);
					if(!regFlag){
						alert("单价格式不正确，请输入0.00的数字格式！");
						flag=false;
					}else if(isNaN(price)){
						alert("您输入的单价格式不正确，请输入数字！");
						return false;
					}
				}
				var requestJson=
				{
						type: type,
						typeId: typeId,
						subTypeId:subTypeId,
						detailTypeId:detailTypeId,
						idmNm: idmNm,
						source: source,
						size: size,
						measurement: measurement,
						price: price,
						amount: amount,
						remark:remark,
						position:position
					};
				if(flag&&$("#add_idm").attr("disabled")==undefined){
					$.ajax({
			    		url:'/PMMS/restful/IdmMgmt/saveIdm',
						type:'post',
						dataType:'json',
						cache:false,
						data:JSON.stringify(requestJson),
						beforeSend:function(xhr){
							$("#add_idm").attr("disabled",true);
						},
						complete:function (XMLHttpRequest, textStatus) {
							$("#add_idm").removeAttr("disabled");
						},
						success:function(backData,status){
							alert('添加成功!!!');
							$("#addStockInfo").modal("hide");
							buildStockTable("all");
							
						},
						error:function(XmlHttpRequest){
							alert('error!!!');
						}
			    		
			    	});
				}
				
			});
			
			//修改取消
			
			 $("#update_cancle").click(function(){
				 buildStockTable("all");
			 });
			//修改间接物料信息
			$("#editStockIdm").click(function(){
				 var check_boxes = $('input:radio[name="radio"]:checked');
					if(check_boxes.length > 1||check_boxes.length == 0){ alert('请选择一条记录，进行修改！');return;}
				$("#editStockInfo").modal("show");
			});
						
			//修改窗口确认按钮
			 $("#update_Stock").click(function(){
				//17
				 var reg = /\d{1,10}(\.\d{1,2})?$/;
					var idmId = $("#idmId_Edit").val();
					var idmNm =$.trim($("#idmNm_Edit").val().toString());
					var size = $.trim($("#size_Edit").val());
					var source = $.trim($("#source_Edit").val());
					var position =$.trim($("#position_Edit").val());
					var price =$.trim($("#price_Edit").val());
					var measurement=$.trim($("#measurement_Edit").val());
					//var stockNum = $("#stockNum_Edit").val();
					var safetyNm=$.trim($("#safetyNum_Edit").val());
					var type = $('#type_Edit  option:selected').text();
					var typeId=$('#type_Edit  option:selected').val();
					var subTypeId=$("#subType_Edit option:selected").val();
					var detailTypeId=$("#type_detail_Edit option:selected").val();
					var remark=$.trim($("#remark_Edit").val());
					var sugSafetyNum = $("#sugSafetyNum_Edit").val();
					var flag=true;
					if(idmNm==""){
						alert("请输入间接物料名称！");
						flag=false;
					}
					if(price==""){
						
					}else{
						var regFlag = reg.exec(price);
						if(!regFlag){
							alert("您输入单价格式不正确，请重新输入！");
							flag=false;
						} else if(isNaN(price)){
							alert("您输入的单价格式不正确，请输入数字！");
							return false;
						}
					}
					if(safetyNm==""){
						
					}else{
						var regFlag = reg.exec(safetyNm);
						if(!regFlag){
							alert("您输入的安全库存量格式不正确，请重新输入！");
							flag=false;
						} else if(isNaN(safetyNm)){
							alert("您输入的重量不正确，请输入数字！");
							return false;
						}
					}
					var requestJson=
					{
							idmId: idmId,
							idmNm:idmNm,
							source: source,
							size: size,
							price: price,
							measurement: measurement,
							safetyNm: safetyNm ,
							position:position,
							typeId:typeId,
							subTypeId:subTypeId,
							detailTypeId:detailTypeId,
							remark:remark,
							sugSaveStock:sugSafetyNum
						};
					if(flag){
						$.ajax({
				    		url:'/PMMS/restful/IdmMgmt/updateIdmInfo',
							type:'post',
							dataType:'json',
							cache:false,
							data:JSON.stringify(requestJson),
							beforeSend:function(xhr){
								//$("#loading").show();
							},
							complete:function (XMLHttpRequest, textStatus) {
								//$("#loading").hide();
							},
							success:function(backData,status){
								alert('修改成功!!!');
								$("#editStockInfo").modal("hide");
								buildStockTable("all");
							},
							error:function(XmlHttpRequest){
								alert('error!!!');
							}
				    		
				    	});
					}
					
			 });
//			//修改出入库记录
//			$("#editRecord").click(function(){
//				 var check_boxes = $('input:radio[name="radio"]:checked');
//					if(check_boxes.length > 1||check_boxes.length == 0){ alert('请选择一条记录，进行修改！');return;}
//				// alert("1");
//				 var oTable = $('#recordTable').dataTable();
//				 oTable.$('tr').click( function (event) {
//					 	var targetName = $(event.target).attr("targetName");
//					 	if(targetName=="editRecordTb"){
//					 		var data = oTable.fnGetData( this );
//					 		//alert("idmId_Edit "+data.idmId);
//					 		$("#trans_id").val(data.transId);
//					 		$("#id_record").val(data.idmId);
//							$("#idmNm_record").val(data.idmNm);
//							$("#price_record").val(data.price);
//							$("#amount_record").val(data.amount);
//							$("#type_record").val(data.idmType);
//							$("#inTime_record").val(data.inTime);
//							$("#outTime_record").val(data.outTime);
//					 	}
//					  } );
//				$("#editRecordInfo").modal("show");
//			}); 
//	 		
//	 		//修改出入库记录确认按钮
//			 $("#update_record").click(function(){
//				//17
//					var idmId = $("#id_record").val();
//					var transId = $("#trans_id").val().toString();
//					var idmNm = $("#idmNm_record").val();
//					var price = $("#price_record").val();
//					var amount = $("#amount_record").val();
//					var idmType = $("#type_record").val();
//					var inTime = $("#inTime_record").val();
//					var outTime = $("#outTime_record").val();
//					var requestJson=
//					{
//							idmId: idmId,
//							transId: transId,
//							idmNm: idmNm,
//							price:price,
//							amount: amount,
//							idmType: idmType,
//							inTime: inTime,
//							outTime: outTime       
//						};
//						$.ajax({
//				    		url:'/PMMS/restful/IdmMgmt/updateTransRecord',
//							type:'post',
//							dataType:'json',
//							cache:false,
//							data:JSON.stringify(requestJson),
//							beforeSend:function(xhr){
//								//$("#loading").show();
//							},
//							complete:function (XMLHttpRequest, textStatus) {
//								//$("#loading").hide();
//							},
//							success:function(backData,status){
//								alert('修改成功!!!');
//								$("#editRecordInfo").modal("hide");
//								buildRecordTable();
//							},
//							error:function(XmlHttpRequest){
//								alert('error!!!');
//							}
//				    		
//				    	});
//					
//					
//			 });
			
	 		function buildStockTable(idmTypeId){
	 			$('#search1').val("");
	 			$.ajax({
					type: "POST",
					url:  "/PMMS/restful/IdmMgmt/getIdmInfoByTypeId",
					dataType: "json",
					success: function(jsonData){
						if(jsonData.success){
							var dataList = jsonData.data.detail;
							var tb='<table id="stockTable" class="table table-striped table-bordered table-condensed" data-table-name="stockTable"> </table>';
							$("#stockTb").empty().html('<div class="input-append flR pull-right" style="margin-right:10px;">'+
									'<input type="text" class="input-medium search-query" data-filter-table="stockTable" id="seach1"><button class="btn btn-icon"><i class="icon-search"></i></button></div>'+
									tb);
							//$("#stockTb ").html(tb);
							$('#stockTable').iidsBasicDataGrid( {//iidsBasicDataGrid  dataTable
								"bProcessing": true,
						        "bPaginate":true,
						        "bAutoWidth": false,
						        "bSort": true,
						        "bDestroy":true,
								"useFloater": false, 
								"aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
								"aLengthMenu": [[10, 25, 2, -1], [10, 25, 2, "All"]],
								"aoColumns": [
								              	 {sTitle: '选择select', mData: 'select'},
							                  //   {sTitle: '序号NO.', mData: 'seriNum'},
							                     {sTitle: '编码号Encod', mData: 'idmId'},
							                     {sTitle: '名称Name', mData: 'idmNm'},
							                     {sTitle: '规格Size', mData: 'size'},
							                     {sTitle: '生产厂商Supplier', mData: 'source'},
							                     {sTitle: '单价(元)', mData: 'price'},
							                     {sTitle: '单位', mData: 'measurement'},
							                     {sTitle: '库存量StockNum', mData: 'stockNum'},//stockNum
							                     {sTitle: '安全库存量StockSaveNum', mData: 'safetyNm'},
							                     {sTitle: '建议安全库存', mData: 'sugSaveStock'},
							                     {sTitle: '存放地点position', mData: 'position'},
							                     {sTitle: '类别Class', mData: 'typeId'},
							                     {sTitle: '子类Subclass', mData: 'SUB_TYPE_NM'},
							                     {sTitle: '明细项目', mData: 'TYPE_DETAIL_NM'},
							                     {sTitle: '备注', mData: 'remark'},
							                 ],
							   "aaData":dataList,
								   "fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
								   	        $('td:eq(0)', nRow).html( '<input id="select" type="radio" name="radio" targetName="editStockIdm" value="'+aData.idmId+'"/>' );
								   	     $('td:eq(7)', nRow).html('<a href="#" class="btn linkHrf" dataValue="'+aData.idmId+'" targetName="viewPoDetail">'+aData.stockNum+'</a>');
								   	     
								   	     if(aData.typeId=='E'){
											  $('td:eq(11)', nRow).html('费用');
										  }else if(aData.typeId=='P'){
											  $('td:eq(11)', nRow).html('固定资产');
										  }
								   	     var oTable = $('#stockTable').dataTable();
										 oTable.$('tr').click( function (event) {
											 	var targetName = $(event.target).attr("targetName");
											 	if(targetName=="editStockIdm"){
											 		var data = oTable.fnGetData( this );
											 		//alert("idmId_Edit "+data.idmId);
											 		$("#idmId_Edit").val(data.idmId);
											 		$("#idmNm_Edit").val(data.idmNm);
													$("#size_Edit").val(data.size);
													$("#source_Edit").val(data.source);
													$("#position_Edit").val(data.position);
													$("#price_Edit").val(data.price); 
													$("#measurement_Edit").val(data.measurement);
													$("#stockNum_Edit").val(data.stockNum);
													$("#safetyNum_Edit").val(data.safetyNm);
													$("#remark_Edit").val(data.remark);
													$("#sugSafetyNum_Edit").val(data.sugSaveStock);
													var options=document.getElementById("type_Edit").options;
													for(var i = 0; i < options.length; i++) {
												        if(options[i].value == data.typeId) {
												                options[i].selected = true;
												        }
													}
													 $("#subType_Edit").empty();
													 $("#subType_Edit").append("<option value='"+data.subTypeId+"'>"+data.SUB_TYPE_NM+"</option>");
													// $("subType_Edit").val(data.SUB_TYPE_NM);
													 $("#type_detail_Edit").empty();
													 $("#type_detail_Edit").append("<option value='"+data.typeDetailId+"'>"+data.TYPE_DETAIL_NM+"</option>");
											 	}
											 	
											 	if(targetName=="viewPoDetail"){
											 		var idmId = $(event.target).attr("dataValue");
											 		buildPoDetailTb(idmId);
											 	}
											  } );
								   }

						    });
						}
					},
					data:{
						"idmTypeId":idmTypeId
					}
				});
			}
	 		//比较选择的日期
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
	 		
	 		//出入库表
	 		function buildRecordTable(startTime,endTime,transType){
	 			$('#search2').val("");
	 			if(compareDate(startTime,endTime)){
	 				$.ajax({
						type: "POST",
						url:  "/PMMS/restful/IdmMgmt/getIdmRecordByTimePeriod",
						dataType: "json",
						success: function(jsonData){
							if(jsonData.success){
								var dataList = jsonData.data.detail;
								var tb=' <table id="recordTable" class="table table-striped table-bordered table-condensed" data-table-name="recordTable"></table>';
								$("#recordTb").empty().html('<div class="input-append flR pull-right">'+
										'<input type="text" class="input-medium search-query" data-filter-table="recordTable" id="search2"><button class="btn btn-icon"><i class="icon-search"></i></button></div>'+
										tb);
								//$("#recordTb ").html(tb);
								item_table=$('#recordTable').iidsBasicDataGrid( {//iidsBasicDataGrid  dataTable
									"bProcessing": true,
							        "bPaginate":true,
							        "bAutoWidth": false,
							        "bSort": true,
							        "bDestroy":true,
									"useFloater": false, 
									"aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
									"aLengthMenu": [[10, 25, 2, -1], [10, 25, 2, "All"]],
									"aoColumns": [
									              	//  {sTitle: '选择select', mData: 'select'},
									              //	  {sTitle: '序号NO.', mData: 'seriNum'},
									              	  {sTitle: '流水号', mData: 'transId'},
									              	  {sTitle: '编码号', mData: 'idmId'},
									              	  {sTitle: '名称', mData: 'idmNm'},
									              	  {sTitle: '单价(元)', mData: 'price'},
									              	  {sTitle: '数量', mData: 'amount'},
									              	  {sTitle: '类别', mData: 'idmType'},
									              	  {sTitle: '入库/出库', mData: 'transType'},
									              	  {sTitle: '操作时间', mData: 'strCreateDate'},
									              	  {sTitle: '订货交付时间差（天）', mData: 'leadTime'},
									              	{sTitle: '仓库管理员', mData: 'creator'},
									              	{sTitle: '领料人', mData: 'receiver'},
									              	{sTitle: '批次号', mData: 'po'}
								                 ],
								   "aaData":dataList,
									   "fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
										   if(aData.transType==1){
											   $('td:eq(6)', nRow).html('入库');
										   }else{
											   $('td:eq(6)', nRow).html('出库');
										   }
										   if(aData.leadTime=="null"){
											   $('td:eq(8)', nRow).html('');
										   }
									   }

							    });
							}
						},
						data:{
							"startTime":startTime,
							"endTime":endTime,
							"transType":transType
						}
					});
	 			}
	 			
	 		}
	 		
	 		//创建安全库存表
	 		
	 		function buildSaveStockTb(){
	 			$('#search3').val("");
		 			$.ajax({
						type: "POST",
						url:  "/PMMS/restful/IdmMgmt/getUnSaftyIdmInfo",
						dataType: "json",
						success: function(jsonData){
							if(jsonData.success){
								var dataList = jsonData.data.detail;
								var tb=' <table id="saveStockTable" class="table table-striped table-bordered table-condensed" data-table-name="saveStockTable"></table>';
								$("#saveStockTb").empty().html('<div class="input-append flR pull-right">'+
										'<input type="text" class="input-medium search-query" data-filter-table="saveStockTable" id="search3"><button class="btn btn-icon"><i class="icon-search"></i></button></div>'+
										tb);
								//$("#saveStockTb ").html(tb);
								item_table=$('#saveStockTable').iidsBasicDataGrid( {//iidsBasicDataGrid  dataTable
									"bProcessing": true,
							        "bPaginate":true,
							        "bAutoWidth": false,
							        "bSort": true,
							        "bDestroy":true,
									"useFloater": false, 
									"aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
									"aLengthMenu": [[10, 25, 2, -1], [10, 25, 2, "All"]],
									"aoColumns": [
									         	// {sTitle: '选择select', mData: 'select'},
							                   //  {sTitle: '序号NO.', mData: 'seriNum'},
							                     {sTitle: '编码号Encod', mData: 'idmId'},
							                     {sTitle: '名称Name', mData: 'idmNm'},
							                     {sTitle: '规格Size', mData: 'size'},
							                     {sTitle: '生产厂商Supplier', mData: 'source'},
							                     {sTitle: '单价(元)', mData: 'price'},
							                     {sTitle: '库存量StockNum', mData: 'stockNum'},
							                     {sTitle: '安全库存量StockSaveNum', mData: 'safetyNm'},
							                   //  {sTitle: '建议安全库存', mData: 'SugSaveStock'},
							                     {sTitle: '存放地点position', mData: 'position'},
							                     {sTitle: '类别Class', mData: 'type'},
							                    // {sTitle: '子类Subclass', mData: 'subType'}
								                 ],
								   "aaData":dataList,
									   "fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
									   	    //    $('td:eq(0)', nRow).html( '<input id="select" type="radio" targetName="editRecordTb" name="radio" value="'+aData.idmId+'"/>' );
									  
									   }

							    });
							}
						},
						data:{
						}
					});
		 		}
	 		//创建扫描入库窗口的表格
	 		function buildScanInTable(idmId){
	 			$.ajax({
					type: "POST",
					url:  "/PMMS/restful/IdmMgmt/getIdmInfoByIdmId",
					dataType: "json",
					data:{
						"idmId":idmId
					},
					success: function(jsonData){
						if(jsonData.success){
							var dataList = jsonData.data.detail;
								var tb='<table id="scanInTable" class="table table-striped table-bordered table-condensed" data-table-name="scanInTable"></table>';
								 $("#scanInTb").empty();
								$("#scanInTb").append(tb);
								
								$('#scanInTable').iidsBasicDataGrid( {
									"bProcessing": true,
							        "bPaginate":true,
							        "bAutoWidth": false,
							        "bSort": true,
							        "bDestroy":true,
									"useFloater": false, 
									"aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
									"aLengthMenu": [[10, 25, 2, -1], [10, 25, 2, "All"]],
									"aaData":dataList,
									"aoColumns": [
									              	  {sTitle: '编码号', mData: 'idmId'},
									              	  {sTitle: '名称', mData: 'idmNm'},
									              	 {sTitle: '规格', mData: 'size'},
									              	 {sTitle: '单价(元)', mData: 'price'},
									              	 {sTitle: '单位', mData: 'measurement'},
										              	{sTitle: '库存量', mData: 'stockNum'},
									              	 {sTitle: '生产厂商', mData: 'source'},
									              	 {sTitle: '类别', mData: 'type'}
									              	// {sTitle: '子类', mData: 'subType'}
								                 ],
												  
							    });
							
							
						}
					}
				});
	 		}
	 	
	 		//创建扫描出库窗口的表格
	 		function buildScanOutTable(idmId){
	 			$.ajax({
					type: "POST",
					url:  "/PMMS/restful/IdmMgmt/getIdmInfoByIdmId",
					dataType: "json",
					data:{
						"idmId":idmId
					},
					success: function(jsonData){
						if(jsonData.success){
							var dataList = jsonData.data.detail;
							//stockNum=jsonData.data.detail.stockNum;
								var tb='<table id="scanOutTable" class="table table-striped table-bordered table-condensed" data-table-name="scanOutTable"></table>';
								 $("#scanOutTb").empty();
								$("#scanOutTb").append(tb);
								
								$('#scanOutTable').iidsBasicDataGrid( {
									"bProcessing": true,
							        "bPaginate":true,
							        "bAutoWidth": false,
							        "bSort": true,
							        "bDestroy":true,
									"useFloater": false, 
									"aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
									"aLengthMenu": [[10, 25, 2, -1], [10, 25, 2, "All"]],
									"aaData":dataList,
									"aoColumns": [
									              	  {sTitle: '编码号', mData: 'idmId'},
									              	  {sTitle: '名称', mData: 'idmNm'},
									              	 {sTitle: '规格', mData: 'size'},
									              	 {sTitle: '单价(元)', mData: 'price'},
									              	 {sTitle: '单位', mData: 'measurement'},
									              	{sTitle: '库存量', mData: 'stockNum'},
									              	 {sTitle: '生产厂商', mData: 'source'},
									              	 {sTitle: '类别', mData: 'type'}
									              	 
								                 ],
							    });
							
							
						}
					//	$("#idmOutInfo").modal("show");
					}
				});
	 		}
	 	//新增子类别按钮
	 		$("#addSubType").click(function(){
	 			$("#sub_type_add").val("");
	 			$("#sub_type_add_id").val("");
	 			$("#sub_type_add_detail").val("");
				$("#addTypeInfo select").val("");
				//$("#sub_type_add_detailId").val("01");
		 			$("#addTypeInfo").modal("show");
			});
	 	//确认按钮---新增子类别窗口
	 		 $("#type_add_idm").click(function(){
					//17
						var typeId = $("#sub_type_add_type").val();
						var subType =$.trim($("#sub_type_add").val());
						var subTypeId=$.trim($("#sub_type_add_id").val());
						var detailType=$.trim($("#sub_type_add_detail").val());
						var flag=true;
						//alert("typeId "+typeId);
						if(typeId==null||typeId=="0"){
							alert("请选择类别！");
							flag=false;
						}
						if(subType==""){
							alert("请输入子类名！");
							flag=false;
						}
						if(subTypeId==""){
							alert("请输入子类编号！");
							flag=false;
						}
						if(detailType==""){
							alert("请输入明细项目！");
							flag=false;
						}
						if(flag){
							$.ajax({
					    		url:'/PMMS/restful/IdmMgmt/insertSubType',
								type:'post',
								dataType:'json',
								cache:false,
								data:{
									typeId: typeId,
									subType: subType,
									subTypeId:subTypeId,
									detailType:detailType
								},
								beforeSend:function(xhr){
									//$("#loading").show();
								},
								complete:function (XMLHttpRequest, textStatus) {
									//$("#loading").hide();
								},
								success:function(backData,status){
									if(backData.data.detail!=null){
										alert("该子类编号已存在！请重新输入");
									}else{
										alert('新增子类成功!!!');
										$("#addTypeInfo").modal("hide");
									} 
								},
								error:function(XmlHttpRequest){
									alert('error!!!');
								}
					    		
					    	});
						}
							
						
				 });
	 		 
	 		//新增明细项按钮
		 		$("#addDetailType").click(function(){
		 			$("#addDetailTypeInfo input").val("");
					$("#addDetailTypeInfo select").val("");
			 			$("#addDetailTypeInfo").modal("show");
				});
		 	//确认按钮---新增明细项窗口
		 		 $("#detail_type_add").click(function(){
						//17
							var typeId = $("#detail_add_type").val();
							var subTypeId = $("#detail_sub_type_add").val();
						//	var subTypeNm= $("#detail_sub_type_add option:selected").text();
							var typeDetail = $.trim($("#detail_add_detail").val());
							var flag=true;
							if(typeId==null||typeId=="0"){
								alert("请选择类别！");
								flag=false;
							}
							if(subTypeId==""||subTypeId==0){
								alert("请选择子类！");
								flag=false;
							}
							if(typeDetail==""){
								alert("请输入明细项目！");
								flag=false;
							}
							if(flag){
								$.ajax({
						    		url:'/PMMS/restful/IdmMgmt/insertDetailType',
									type:'post',
									dataType:'json',
									cache:false,
									data:{
										typeId: typeId,
										subTypeId: subTypeId,
										//subTypeNm:subTypeNm,
										typeDetail: typeDetail 
									},
									beforeSend:function(xhr){
										//$("#loading").show();
									},
									complete:function (XMLHttpRequest, textStatus) {
										//$("#loading").hide();
									},
									success:function(backData,status){
										alert('新增明细项目成功!!!');
										$("#addDetailTypeInfo").modal("hide");
										//buildRecordTable();
									},
									error:function(XmlHttpRequest){
										alert('error!!!');
									}
						    		
						    	});
							}
								
					 });
		 		 
		 		function getSubType(ele,typeId){
					$.ajax({
			    		url:'/PMMS/restful/IdmMgmt/getSubTypeByTypeId',
						type:'post',
						dataType:'json',
						cache:false,
						data:{
							typeId:typeId
						},
						beforeSend:function(xhr){
							//$("#loading").show();
						},
						complete:function (XMLHttpRequest, textStatus) {
							//$("#loading").hide();
						},
						success:function(backData,status){
							buildDropDownSubType(backData.data.detail,ele);
						},
						error:function(XmlHttpRequest){
							alert('error!!!');
						}
			    	});
				}	
		 		
		 		function buildDropDownSubType(data,ele){
					for(var i=0;i<ele.length;i++){
						$(ele[i]).empty();
						if(data.length>0){
							$(ele[i]).append("<option value=0> --- 请选择 --- </option>");
							$.each(data,function(index,item){
								var selectOption = "<option value='"+item.SUB_TYPE_ID+"'>"+item.SUB_TYPE_ID+"---"+item.SUB_TYPE_NM+"</option>";
								$(ele[i]).append(selectOption);
							});
						}
					}
				}
		 		
		 		function getDetailType(ele,typeId,subTypeId){
					$.ajax({
			    		url:'/PMMS/restful/IdmMgmt/getdetailType',
						type:'post',
						dataType:'json',
						cache:false,
						data:{
							typeId:typeId,
							subTypeId:subTypeId
						},
						beforeSend:function(xhr){
							//$("#loading").show();
						},
						complete:function (XMLHttpRequest, textStatus) {
							//$("#loading").hide();
						},
						success:function(backData,status){
							buildDropDownDetailType(backData.data.detail,ele);
						},
						error:function(XmlHttpRequest){
							alert('error!!!');
						}
			    	});
				}
		 		
		 		function buildDropDownDetailType(data,ele){
					for(var i=0;i<ele.length;i++){
						$(ele[i]).empty();
						if(data.length>0){
							$(ele[i]).append("<option value=0> --- 请选择 --- </option>");
							$.each(data,function(index,item){
								var selectOption = "<option value='"+item.TYPE_DETAIL_ID+"'>"+item.TYPE_DETAIL_ID+"---"+item.TYPE_DETAIL_NM+"</option>";
								$(ele[i]).append(selectOption);
							});
						}
					}
				}
		 	//出入库记录查询按钮
		 		$("#searchBtn").click(function(){
		 			var startTime=$("#fromTime").val();
		 			var endTime=$("#toTime").val();
		 			var transType=$("#transType").val();
		 			buildRecordTable(startTime,endTime,transType);
		 			
				});
		 		
		 		//间接物料基本信息页面导出到excel按钮
				$("#idm_download").click(function(){
					window.location.href="/PMMS/restful/IdmMgmt/idmInfoExportToExcel";
				});
				
				//间接物料出入库页面导出到excel按钮
				$("#record_download").click(function(){
					window.location.href="/PMMS/restful/IdmMgmt/idmRecordExportToExcel";
				});
				
				//间接物料安全库存管理页面导出到excel按钮
				$("#safety_download").click(function(){
					window.location.href="/PMMS/restful/IdmMgmt/idmSafetyExportToExcel";
				});
				//计算安全库存量
				$("#calcSafetyNum").click(function(){
							$.ajax({
					    		url:'/PMMS/restful/IdmMgmt/calcSafetyNum',
								type:'post',
								dataType:'json',
								cache:false,
								data:{
								},
								beforeSend:function(xhr){
									//$("#loading").show();
								},
								complete:function (XMLHttpRequest, textStatus) {
									//$("#loading").hide();
								},
								success:function(backData,status){
									alert("calculate successfully!");
									buildStockTable("all");
								},
								error:function(XmlHttpRequest){
									alert('error!!!');
								}
					    		
					    	});
				 });
			
				//库存批次明细窗口
				function buildPoDetailTb(idmId){
		 			$("#poDetailWindow").modal('show');
		 			$.ajax({
						type: "POST",
						url:  "/PMMS/restful/IdmMgmt/getPoDetail",
						dataType: "json",
						success: function(jsonData){
							if(jsonData.success){
								var dataList = jsonData.data.detail;
								var tb='<table id="poDetailTb" class="table table-striped table-bordered table-condensed" data-table-name="equipDocDetails"></table>';
								$("#poDetailTbDiv").empty();
								$("#poDetailTbDiv ").html(tb);
								$('#poDetailTb').iidsBasicDataGrid( {//iidsBasicDataGrid  dataTable
									"bProcessing": true,
							        "bPaginate":true,
							        "bAutoWidth": false,
							        "bSort": true,
							        "bDestroy":true,
									"useFloater": false, 
									"aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
									"aLengthMenu": [[10, 25, 2, -1], [10, 25, 2, "All"]],
									 "aoColumns": [
													// {sTitle: '序号/ID', mData: 'id',"sClass":"center","filter":false,'sWidth':'50px'},
													 {sTitle: '编码号', mData: 'idmId',"filter":false,'sWidth':'20%'},
													 {sTitle: '批次号', mData: 'po',"sClass":"essential",'sWidth':'30%'},
													 {sTitle: '数量', mData: 'inAmount','sWidth':'10%'},
													 {sTitle: '剩余量', mData: 'remainAmount','sWidth':'10%'}
													 ],
								   "aaData":dataList
							    });
							}
						},
						data:{
							"idmId":idmId
						}
					});
				}
				
        });