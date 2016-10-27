require( ['jquery','ge-bootstrap', 'datagrids',
          'datatables', 'datatables-scroller','datepicker'], function($) {//"table[data-table-name='plan-table']"  maint-item-table
			var item_table = null;
			$(function(){
				$('.datepicker').datepicker();
			});
		
			buildTable("all");
			getDeptInfo($("#useDepart2"));
			
			//设备类型change事件
			$(".equipType").on("change",function(){
				var equipType = $(this).val(); //1,2,3
				var equipTypeId = $(this).attr("id");
				var targetId = null;
				
			//	alert("id是："+equipTypeId);
				if(equipTypeId=="equipTypeMainPg"){
					buildTable(equipType);
				//	alert("equipType is" +equipType);
				}
				else if(equipTypeId=="equipTypeAdd"){
					targetId = "equipNmAdd";
				}else if(equipTypeId=="equipTypeEdit"){
					targetId = "equipNmEdit";
				}
				getEquipNames(equipType,$("#"+targetId),'');
			});
			//验证新增的设备是否已经存在

//			$("input[name='equipId1']").unbind().change(function(){
//				if($("#equipId1").val()!=null){
//					$.ajax({
//			    		url:'/PMMS/restful//EquipInfo/getEquipInfoByEpId',
//						type:'post',
//						dataType:'json',
//						cache:false,
//						data:{
//							equipId:$("#equipId1").val()
//						},
//						beforeSend:function(xhr){
//							//$("#loading").show();
//						},
//						complete:function (XMLHttpRequest, textStatus) {
//							//$("#loading").hide();
//						},
//						success:function(backData,status){
//							if(backData.data.detail!=null){
//								alert("该设备已存在！请重新输入");
//							}
//						},
//						error:function(XmlHttpRequest){
//							alert('error!!!');
//						}
//			    	});
//				}
//				
//			});
			function getEquipNames(equipType,ele,tag){
				$.ajax({
		    		url:'/PMMS/restful/maintItem/getEquipNames',
					type:'post',
					dataType:'json',
					cache:false,
					data:{
						equipType:equipType
					},
					beforeSend:function(xhr){
						//$("#loading").show();
					},
					complete:function (XMLHttpRequest, textStatus) {
						//$("#loading").hide();
					},
					success:function(backData,status){
						buildDropDown(backData.data.list,ele);
						if(tag!=''){
							$("#equipNmEdit").val(tag);	
						}
					},
					error:function(XmlHttpRequest){
						alert('error!!!');
					}
		    	});
			} 
			
			function getDeptInfo(ele){
				$.ajax({
		    		url:'/PMMS/restful/EquipInfo/getDeptInfo',
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
						buildDropDownDept(backData.data.detail,ele);
					},
					error:function(XmlHttpRequest){
						alert('error!!!');
					}
		    	});
			}
			
			function buildTable(equipTypeId){
				$('#searchEquip').val("");
				$.ajax({
					type: "POST",
					url:  "/PMMS/restful/EquipInfo/getEquipInfo",
					dataType: "json",
					success: function(jsonData){
						if(jsonData.success){
							var dataList = jsonData.data.detail;
							//alert("dataList length "+dataList.length+"rtetr");
							var tbl='<table id="myWorkflowTable1" class="table table-striped table-bordered table-condensed" data-table-name="myWorkflowTable1"> </table>';
							$("#equipInfoTbl").empty().append('<div class="input-append flR pull-left" style="margin-right:10px;">'+
									'<input type="text" class="input-medium search-query" data-filter-table="myWorkflowTable1" id="searchEquip"><button class="btn btn-icon"><i class="icon-search"></i></button></div>'+
									tbl);
							//$("#equipInfoTbl ").html(tbl);
							$('#myWorkflowTable1').iidsBasicDataGrid( {//iidsBasicDataGrid  dataTable
								"bProcessing": true,
						        "bPaginate":true,
						        "bAutoWidth": true,
						        "bSort": true,
						        "bDestroy":true,
						        "aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
								"useFloater": false, 
								"aLengthMenu": [[10, 25, 2, -1], [10, 25, 2, "All"]],
								"aoColumns": [
							                     {sTitle: '选择'},
							                 //    {sTitle: '序号NO.', mData: 'id'},
							                     {sTitle: '类别', mData: 'equipType'},
							                     {sTitle: '设备编号', mData: 'equipId'},
							                     {sTitle: '固定资产编号AssetNo', mData: 'assetId'},
							                     {sTitle: '设备名称DeviceName', mData: 'equipmentName'},
							                     {sTitle: '设备型号ModelNum', mData: 'equipModel'},
							                     {sTitle: '制造厂Supplier', mData: 'source'},
							                     {sTitle: '耗电总容量', mData: 'powerConsum'},
							                     {sTitle: '安装日期', mData: 'installDate'},
							                     {sTitle: '原值', mData: 'originPrice'},
							                     {sTitle: '现值', mData: 'currPrice'},
							                     {sTitle: '外形尺寸（MM)', mData: 'size'},
							                     {sTitle: '重量（KG）', mData: 'weight'},
							                      {sTitle: '是否使用', mData: 'inUse'},
							                      {sTitle: '出厂日期', mData: 'releaseDate'},
							                      {sTitle: '出厂编号', mData: 'factoryNo'},
							                     {sTitle: '使用部门', mData: 'deptNm'},
							                     {sTitle: '备注', mData: 'remark'},
							                     {sTitle: '文档查看（下载）', mData: ''} //equipDocId
							                 ],
							   "aaData":dataList,
								   "fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
								   	        $('td:eq(0)', nRow).html( '<input id="select" type="radio" name="radio" targetName="editEquipInfo" value="'+aData.equipId+'"/>' );
								   	     $('td:eq(18)', nRow).html('<a href="#" class="btn linkHrf" dataValue="'+aData.equipDocId+'" targetName="viewAndDownload"><i class="icon-download-alt"></i>下载</a>');
								  if(aData.equipType==2){
									  $('td:eq(1)', nRow).html('特种设备');
								  }else if(aData.equipType==3){
									  $('td:eq(1)', nRow).html('设施');
								  }else{
									  $('td:eq(1)', nRow).html('生产设备');
								  }
								  
								  if(aData.inUse==0){
									  $('td:eq(13)', nRow).html('否');
								  }else{
									  $('td:eq(13)', nRow).html('是');
								  }
								  
								  if(aData.installDate=="null"){
									  $('td:eq(8)', nRow).html('');
								  }
								  if(aData.releaseDate=="null"){
									  $('td:eq(14)', nRow).html('');
								  }
								  var oTable = $('#myWorkflowTable1').dataTable();
									 oTable.$('tr').click( function (event) {
										 	var targetName = $(event.target).attr("targetName");
										 	if(targetName=="editEquipInfo"){
										 		var data = oTable.fnGetData( this );
										 		getEquipNames(data.equipType,$("#equipNmEdit"),data.equipNmId);
										 		$("#equipTypeEdit").val(data.equipType);
										 		$("#equipId2").val(data.equipId);
												$("#assetId2").val(data.assetId);
												$("#equipModel2").val(data.equipModel);
												
												$("#factoryDate2").val(data.releaseDate);
												$("#factoryNo2").val(data.factoryNo);
												
												$("#source2").val(data.source);
												$("#powerConsum2").val(data.powerConsum);
												$("#installDate2").val(data.installDate);
												$("#oriPrice2").val(data.originPrice);
												$("#currPrice2").val(data.currPrice);
												$("#size2").val(data.size);
												$("#weight2").val(data.weight);
												if(data.inUse==1){
													  $("[name='checkbox_edit']").prop("checked",'true');
												}else{
													 $("[name='checkbox_edit']").removeAttr("checked");
												}
												$("#useDepart2").val(data.deptId);
												$("#remark2").val(data.remark);
											
										 	}
										 	
										  } ); 
									
								 
							}

						    });
							item_table = $('#myWorkflowTable1').dataTable();
							item_table.$('tr').click(function(event){
								var targetName=$(event.target).attr("targetName");
								if(targetName=="viewAndDownload"){
									var epDocId = $(event.target).attr("dataValue");
									//alert("epDocId "+epDocId);
									buildFileDownLoadTb(epDocId);
								}
							});
							
						}
					},
					data:{
						"equipType":equipTypeId
					}
				});
			}
		//文档下载窗口
			function buildFileDownLoadTb(epDocId){
	 			$("#downloadFileWindow").modal('show');
	 			$.ajax({
					type: "POST",
					url:  "/PMMS/restful/equipDocMgmt/getDocDetail",
					dataType: "json",
					success: function(jsonData){
						if(jsonData.success){
							var dataList = jsonData.data.docDetails;
							var tb='<table id="equipDocDetails" class="table table-striped table-bordered table-condensed" data-table-name="equipDocDetails"></table>';
							$("#epDocTb").empty();
							$("#epDocTb ").html(tb);
							$('#equipDocDetails').iidsBasicDataGrid( {//iidsBasicDataGrid  dataTable
								"bProcessing": true,
						        "bPaginate":true,
						        "bAutoWidth": false,
						        "bSort": true,
						        "bDestroy":true,
								"useFloater": false, 
								"aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
								"aLengthMenu": [[10, 25, 2, -1], [10, 25, 2, "All"]],
								 "aoColumns": [
												 {sTitle: '序号/ID', mData: 'id',"sClass":"center","filter":false,'sWidth':'50px'},
												 {sTitle: '文件名称/DocName', mData: 'fileName',"filter":false,'sWidth':'20%'},
												 {sTitle: '文档大小/DocName', mData: 'fileSize',"sClass":"essential",'sWidth':'5%'},
												 {sTitle: '上传时间/UploadDate', mData: 'createDate','sWidth':'10%'},
												 {sTitle: '操作/Action', mData: '','sWidth':'10%'}
												 ],
							   "aaData":dataList,
								   "fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
									   $('td:eq(4)', nRow).html('<a href="#" class="btn linkHrf" dataValue="'+aData.id+'" targetName="download"><i class="icon-download-alt"></i>下载</a>');
								  
								   }

						    });
							
							item_table = $('#equipDocDetails').dataTable();
							item_table.$('tr').click(function(event){
								var targetName=$(event.target).attr("targetName");
								if(targetName=="download"){
									var id = $(event.target).attr("dataValue");
									$(event.target).attr("href","../equipDocMgmt/downloadDoc.htm?id="+id);
									//$("#downloadFileWindow").modal('hide');
								}
								
							});
						}
						
					},
					data:{
						"equipDocId":epDocId
					}
				});

			}	
			
			
		//添加设备
			$("#addButton").click(function(){
				getDeptInfo($("#useDepart1"));
				$("#equipTypeAdd").val("");
				$("#equipId1").val("");
				$("#assetId1").val("");
				$("#equipNmAdd").val("");
				$("#equipModel1").val("");
				$("#factoryDate1").val("");
				$("#factoryNo1").val("");
				$("#source1").val("");
				$("#powerConsum1").val("");
				$("#installdate1").val("");
				$("#oriPrice1").val("");
				$("#currPrice1").val("");
				$("#size1").val("");
				$("#weight1").val("");
				$("#useDepart1").val("");
				$("[name='checkbox_add']").removeAttr("checked");
				$("#remark1").val("");
				$("#equipmentInfo").modal("show");
				
			});
			$("#saveBtn").on("click",function(){
				//17
				var equipType = $("#equipTypeAdd").val();
				var equipId =$.trim($("#equipId1").val().toString());
				var assetId = $.trim($("#assetId1").val());
				var equipNm =$.trim($("#equipNmAdd").val());
				var equipModel = $.trim($("#equipModel1").val());
				var releaseDate = $.trim($("#factoryDate1").val());
				var factoryNo =$.trim($("#factoryNo1").val());
				var source = $.trim($("#source1").val());
				var powerConsum =$.trim($("#powerConsum1").val());
				var installdate = $.trim($("#installdate1").val());
				var oriPrice =$.trim($("#oriPrice1").val());
				var currPrice = $.trim($("#currPrice1").val());
				var size =$.trim($("#size1").val());
				var weight =$.trim($("#weight1").val());
				var inuse;
			//	$("[name='checkbox_add']").attr("checked",'true');
				//'input:checkbox[name="checkbox_add"]:checked'
				if($("#inuse1").attr("checked")){
					inuse=1;
				}else{
					inuse=0;
				}
				var useDepart = $("#useDepart1").val();
				//alert("useDepart1 "+$("#useDepart1").val());
				var remark = $.trim($("#remark1").val());
				
				var currDate = new Date();
				var requestJson=
				{
					                equipId: equipId,
					                assetId: assetId,
					                equipNmId: equipNm,
					                equipModel:equipModel,
					                source: source,
					                factoryNo: factoryNo,
					                releaseDate: releaseDate,
					                powerConsum: powerConsum,
					                installDate: installdate,
					                originPrice: oriPrice,
					                currPrice:currPrice,
					                size: size,
					                weight: weight,
					                inUse: inuse,
					                deptId:useDepart,
					                equipType:equipType,
					                creator: "test",
					                createDate:currDate.toLocaleDateString() ,
					                updater: "test",
					                lastUpdateDate: currDate.toLocaleDateString(),
					                remark: remark
					};
				var flag=true;
				//alert(equipType);
				flag=checkInput(equipType,equipId,equipNm,useDepart,currPrice,oriPrice,weight);
				if(flag&&$("#saveBtn").attr("disabled")==undefined){
					$.ajax({
			    		url:'/PMMS/restful/EquipInfo/saveEquip',
						type:'post',
						dataType:'json',
						cache:false,
						data:JSON.stringify(requestJson),
						beforeSend:function(xhr){
							$("#saveBtn").attr("disabled",true);
						},
						complete:function (XMLHttpRequest, textStatus) {
							$("#saveBtn").removeAttr("disabled");
						},
						success:function(backData,status){
							if(backData.data.detail!=null){
								alert("该设备编号已存在！请重新输入");
							}else{
								alert('添加成功!!!');
								$("#equipmentInfo").modal("hide");
								buildTable("all");
							}
							
						},
						error:function(XmlHttpRequest){
							alert('error!!!');
						}
			    		
			    	});
				}
				
			});
			
			//验证必填输入框是否输入
			function checkInput(equipType,equipId,equipNm,useDepart,currPrice,oriPrice,weight){
				var reg = /\d{1,10}(\.\d{1,2})?$/;
				if(equipId==""){
					alert("请输入设备编号！");
					return false;
				}
				if(equipType==null){
					alert("请选择设备类别！");
					return false;
				}
				if(equipNm==0||equipNm==""){
					alert("请选择设备名称！");
					return false;
				}
				if(useDepart==0||useDepart==""){
					alert("请选择部门！");
					return false;
				}
				if(currPrice==""){
					
				}else{
					var regFlag = reg.exec(currPrice);
					if(!regFlag){
						alert("现值格式不正确，正确格式为 0.00的形式");
						return false;
					}else if(isNaN(currPrice)){
						alert("您输入的现值格式不正确，请输入数字！");
						return false;
					}
				}
				if(weight==""){
					
				}else{
					var regFlag = reg.exec(weight);
					if(!regFlag){
						alert("重量格式不正确，正确格式为 0.00的形式");
						return false;
					}else if(isNaN(weight)){
						alert("您输入的重量不正确，请输入数字！");
						return false;
					}
				}	
				return true;
			}
			//修改设备
			 $("#editButton").click(function(){
				 var check_boxes = $('input:radio[name="radio"]:checked');
					if(check_boxes.length > 1||check_boxes.length == 0){ alert('请选择一条记录，进行修改！');return;}
					$("#equipmentUpdate").modal("show");
				});
			 
			 
			 function buildDropDownDept(data,ele){
					for(var i=0;i<ele.length;i++){
						$(ele[i]).empty();
						if(data.length>0){
							$(ele[i]).append("<option value=0> --- 请选择 --- </option>");
							$.each(data,function(index,item){
								var selectOption = "<option value='"+item.deptId+"'>"+item.deptNm+"</option>";
								$(ele[i]).append(selectOption);
							});
						}
					}
				}
			 
			 function buildDropDown(data,ele){
					//alert("in buildDropDown..."+targetId);
					for(var i=0;i<ele.length;i++){
						$(ele[i]).empty();
						if(data.length>0){
							$(ele[i]).append("<option value=0> --- 请选择 --- </option>");
							$.each(data,function(index,item){
								var selectOption = "<option value='"+item.equipmentNameId+"'>"+item.equipmentName+"</option>";
								$(ele[i]).append(selectOption);
							});
						}
					}
				}
			 
			 function buildDropDownOne(data,ele){
				 $(ele).empty();
					//	$(ele).append("<option> --- 请选择 --- </option>");
							var selectOption = "<option value='"+data.equipNmId+"'>"+data.equipmentName+"</option>";
							$(ele).append(selectOption);			
				}
			 
			//修改设备确认按钮
			 $("#updateBtn").click(function(){
				//17
					var equipType = $("#equipTypeEdit").val();//equipTypeEdit
					var equipId =$.trim($("#equipId2").val().toString());
					var assetId = $.trim($("#assetId2").val());
					var equipNm = $.trim($("#equipNmEdit").val());
					//alert("equipNmEdit value is "+equipNm);
					var equipModel = $.trim($("#equipModel2").val());
					var releaseDate = $.trim($("#factoryDate2").val());
					if(releaseDate=='null'){
						releaseDate="";
					}
					var factoryNo = $.trim($("#factoryNo2").val());
					var source = $.trim($("#source2").val());
					var powerConsum = $.trim($("#powerConsum2").val());
					var installdate = $.trim($("#installDate2").val());
					if(installdate=='null'){
						installdate="";
					}
					var oriPrice = $.trim($("#oriPrice2").val());
					var currPrice = $.trim($("#currPrice2").val());
					var size = $.trim($("#size2").val());
					var weight = $.trim($("#weight2").val());
					var inuse;
					//alert($("[name='checkbox_edit']").attr("checked"));
						 if($("[name='checkbox_edit']").prop("checked"))
						   {
							 inuse=1;
						    
						   }else{
							   inuse=0;
						   }
					
					var useDepart = $("#useDepart2").val();
					var remark = $.trim($("#remark2").val());
					
					
				//	var currDate = new Date();
					var requestJson=
					{
						                equipId: equipId,
						                assetId: assetId,
						                equipNmId: equipNm,
						                equipModel:equipModel,
						                source: source,
						                factoryNo: factoryNo,
						                releaseDate: releaseDate,
						                powerConsum: powerConsum,
						                installDate: installdate,
						                originPrice: oriPrice,
						                currPrice:currPrice,
						                size: size,
						                weight: weight,
						                inUse: inuse,
						                deptId:useDepart,
						                equipType:equipType,
						                //creator: "test",
						                //createDate:currDate.toLocaleDateString() ,
						            //    updater: "test",
						            //    lastUpdateDate: currDate.toLocaleDateString(),
						                remark: remark
						};
					var flag=true;
					flag=checkInput(equipType,equipId,equipNm,useDepart,currPrice,oriPrice,weight);
					if(flag){
						$.ajax({
				    		url:'/PMMS/restful/EquipInfo/updateEquip',
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
								$("#equipmentUpdate").modal("hide");
								buildTable("all");
								
							},
							error:function(XmlHttpRequest){
								alert('error!!!');
							}
				    		
				    	});
					}
					
			 });
			 //修改取消按钮
			 
			 $("#cancle").click(function(){
				 buildTable("all");
			 });
				//删除设备
			$("#deleteButton").click(function(){
				var selectEpId=$('input:radio[name="radio"]:checked').val();
				 var check_boxes = $('input:radio[name="radio"]:checked');
					if(check_boxes.length > 1||check_boxes.length == 0){ alert('请选择一条记录，进行删除！');return;}
				if(!confirm('你确定要删除?')) return;
				$.ajax({
		    		url:'/PMMS/restful/EquipInfo/deleteEquipByEpId',
					type:'post',
					dataType:'json',
					cache:false,
				//	data:JSON.stringify(generateJson()),
					data:{
						equipId:selectEpId
					},
					beforeSend:function(xhr){
						//$("#loading").show();
					},
					complete:function (XMLHttpRequest, textStatus) {
						//$("#loading").hide();
					},
					success:function(backData,status){
						alert('删除成功!!!');
						$("#equipmentInfo").modal("hide");
						buildTable("all");
					},
					error:function(XmlHttpRequest){
						alert('error!!!');
					}
		    		
		    	});
			});
//下载按钮
			$("#download").click(function(){
				window.location.href="/PMMS/restful/EquipInfo/exportToExcel";
			});
});
		