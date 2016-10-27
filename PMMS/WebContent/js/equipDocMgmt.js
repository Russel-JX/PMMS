require(['jquery','pmms','ge-bootstrap', 'datagrids',
         'datatables', 'datatables-scroller'],function($,pmms){
	
	initEquipDoc();
	window.localStorage["hrefUrl"] = "http://"+window.location.host+"/PMMS/equipDocMgmt/index.htm";
	$("#equipCategory").on("change",function(){
		var docType = $("#docType").val();
		if(2 == docType){
			pmms.getEquipModels($(this).val(),$("#selEquipType"));
		}
		if(3 == docType){
			pmms.getEquipNo($(this).val(),$("#selEquipNo"));
		}
	});
	
	$("#addFile").on("click",function(){
		var len = $(".input-file").length;
		if(len > 5){
			alert("最多只能增加6个");
			return;
		}
		
		$(box.getInputFile(len)).appendTo("#fileGroups");
		$("#delBtn"+len).click(function(){
			box.getClick(this);
		});
	});
	
	var docLstTbl = null;
	var box = {};
	//修改文档上传后点击返回按钮后，是否需要刷新table
	var isNeedUpdateDocTbl = false;
	var curUrl = '';
	
	box.getInputFile = function(len){
	return '<div class="span8" ><input class="input-file dyncGenFile" type="file" name="file'+len+'"/><button class="btn btn-primary" type="button" id="delBtn'+len+'">删除</button></div>';
	//返回任何你需要生成的新元素
	};
	
	box.getClick = function(selfObj){
		$(selfObj).parent().remove();
	};
	
	function initEquipDoc(){
		buildeEquipDocTable();
	};
	
	function buildeEquipDocTable(){
		$.ajax({
			type: "POST",
			url:  "/PMMS/restful/equipDocMgmt/getAll",
			dataType: "json",
			success: function(jsonData){
				//console.log(jsonData);
				if(jsonData.success){
					//console.log("get provinces success");
					var dataList = jsonData.data.details;
					//var tbl='<table id="EquipDocListTable" class="table table-bordered dataTable table-responsive" data-table-name="EquipDocListTable"></table>';
					//$("#equipDocTb").empty();
					//$("#equipDocTb ").html(tbl);
					$('#EquipDocListTable').iidsBasicDataGrid( {//iidsBasicDataGrid  dataTable
						"bProcessing": true,
						"isResponsive": true,
				        "bAutoWidth": true,
						'aaSorting': [],
						"oColReorder": {"iFixedColumns": 15},
				        "bPaginate":true,
				        "aoColumnDefs": [
							{
							 sDefaultContent: '',
							 aTargets: [ '_all' ]
							  }
							],
				        "bSort": true,
				        "bDestroy":true,
						 "useFloater": false,
						 "aoColumns": [
						 {sTitle: "<input type='checkbox' name='selectAll' targetName='selectAll' id='selectAll' ><label for='selectAll'>全选</label></input>", mData: 'id',"sDefaultContent":"","sClass":"center","bSortable":false,"bSearchable": true, "bVisible": true,"filter":false},
						 {sTitle: '设备编号/EQID', mData: 'equipId',"sClass":"center","filter":false},
						 {sTitle: '设备型号/EQModel', mData: 'equipModel',"filter":false},
						 {sTitle: '设备名称/EQName', mData: 'equipName',"filter":false},
						 {sTitle: '文档分类/DocType', mData: 'docType',"filter":false},
						 {sTitle: '文档名称/DocName', mData: 'fileName',"sClass":"essential"},
						 {sTitle: '文档大小/Size', mData: 'fileSize',"sClass":"essential"},
						 {sTitle: '备注/Desc', mData: 'description'},
						 {sTitle: '下载/Download', mData: ''}
						 ],
						"aaData":dataList,
						"fnRowCallback":function(nRow,aData,iDisplayIndex,iDisplayIndexFull){
							var docType = aData.docType;
							var name = "";
							if(1 == docType){
								name = '全厂';
							}else if (2 == docType){
								name = '设备类型';
							}else if (3 == docType){
								name = '设备编号';
							}
							
							$('td:eq(0)', nRow).html(
	                    			'<div class="checkbox">'+
	                    				'<label>'+
	                    					'<input type="hidden" value="'+aData.filePath+'"/><input type="hidden" value="'+aData.id+'"/><input type="checkbox" name="id" class="checkbox case" value="'+aData.docId+'">'+
	                    				'</label>'+
	                    			'</div>'
	                    	);
							$('td:eq(4)', nRow).html('<span>'+name+'</span>');
							
							var size = (parseInt(aData.fileSize/1000) <=0)? 1: parseInt(aData.fileSize/1000);
							$('td:eq(6)', nRow).html('<span >'+size+'KB</span>');
							$('td:eq(8)', nRow).html('<a href="#" class="btn linkHrf" dataValue="'+aData.id+'" targetName="download"><i class="icon-download-alt"></i>下载</a>');
							
						 },
						 "fnPageChange":function(){
							 //alert(8);
						 }
				    });
					//***get datatables object***
					docLstTbl = $('#EquipDocListTable').dataTable();
					docLstTbl.on('click','tr',function(event){
						var targetName=$(event.target).attr("targetName");
						if(targetName=="download"){
							//alert(this.innerHTML);
							var id = $(event.target).attr("dataValue");
							$(event.target).attr("href","downloadDoc.htm?id="+id);
						}
						if(targetName=="selectAll"){
							var selectAllObj = $(event.target)[0];
							//var chkObj = $(":checkbox[name='id']:checked");
							 $('.case').each(function() {
								$(this).prop('checked',selectAllObj.checked);
							 });
							
						}
					});
				}
				
			},
			data:{
				equipName:$('#equipName').val()
			}
		});

	};
	
	
	$("#viewUploadlist").on("click",function(){
		$.ajax({
			type: "POST",
			url:  "/PMMS/restful/equipDocMgmt/getDocDetail",
			dataType: "json",
			success: function(jsonData){
				//console.log(jsonData);
				if(jsonData.success){
					//console.log("get provinces success");
					var dataList = jsonData.data.docDetails;
					$('#equipDocDetails').iidsBasicDataGrid( {//iidsBasicDataGrid  dataTable
						"bProcessing": true,
						"isResponsive": true,
				        "bAutoWidth": true,
						'aaSorting': [],
						"oColReorder": {"iFixedColumns": 15},
				        "bPaginate":true,
				        "aoColumnDefs": [
							{
							 sDefaultContent: '',
							 aTargets: [ '_all' ]
							  }
							],
				        "bSort": true,
				        "bDestroy":true,
						 "useFloater": false,
						 "aoColumns": [
						 {sTitle: '文件名称/DocName', mData: 'fileName',"filter":false,'sWidth':'20%'},
						 {sTitle: '文档大小/DocName', mData: 'fileSize',"sClass":"essential",'sWidth':'5%'},
						 {sTitle: '上传时间/UploadDate', mData: 'createDate','sWidth':'10%'},
						 {sTitle: '操作/Action', mData: '','sWidth':'10%'}
						 ],
						"aaData":dataList,
						"fnRowCallback":function(nRow,aData,iDisplayIndex,iDisplayIndexFull){
							$('td:eq(3)', nRow).html('<a href="#" class="btn hrfBtn" targetName="hrefBtn" dataValue="'+aData.id+'"><i targetName="iconDel" class="icon-remove" dataValue="'+aData.id+'"></i></a>');
							
							var size = (parseInt(aData.fileSize/1000) <=0)? 1: parseInt(aData.fileSize/1000);
							$('td:eq(1)', nRow).html('<span>'+size+'KB</span>');
						 },
						 "fnPageChange":function(){
						 }
				    });
					//***get datatables object***
					item_table = $('#equipDocDetails').dataTable();
					
					item_table.$('td').click(function(event){
						//alert($(this).parent());
						var targetName=$(event.target).attr("targetName");
						if(targetName == "hrefBtn" || targetName == "iconDel"){
							var detailId = $(event.target).attr("dataValue");
							var pos = item_table.fnGetPosition(this);
							if(!confirm('Are you sure to delete?')) return;
							$.ajax({
								type: "POST",
								url:  "/PMMS/restful/equipDocMgmt/delOneDoc.htm",
								dataType: "json",
								success: function(jsonData){
									if(jsonData.success){
										alert("delete successful!!");
										item_table.fnDeleteRow(pos[0]);
										isNeedUpdateDocTbl = true;
									}
								},
								data:{id:detailId}
							});
						}
					});
				}
			},
			data:{
				equipDocId:$('#equpDocId').val()
			}
		});
	});
	
	$("#saveBtn").on("click",function(){
		var flag = 0;
		var files = $(":file");
		for(var i = 0; i < files.length;i++){
			if(pmms.isEmpty($(files[i]).val())){
				flag++;
				alert("请选择上传文件！");
				return;
			}
		}
		
		if(divM.style.display == "block"){
			var type = $("#selEquipType").val(); 
			if(pmms.isEmpty(type) ){
				alert("请选择设备型号!");
				return;
			}
		}
		
		if(divE.style.display == "block"){
			var seriaNo = $("#selEquipNo").val();
			if(pmms.isEmpty(seriaNo)){
				alert("请选择设备编号!");
				return;
			}
		}
		if($("#memo").val().length > 500){
			alert("描述信息不能超过500个字符!");
			return;
		}
		if(0 == flag){
			$(".docsForm").submit();
		}
		
	});
	
	
	$(".cancelBtn,.closeBtn").on("click",function(){
		if(isNeedUpdateDocTbl){
			window.location=window.localStorage["hrefUrl"];
		}
	});
	
//新增按钮
	$("#uploadBtn").on("click",function(){
		$("#fileGroups").html('<div class="span8"><input class="input-file" type="file" name="file" id="file"/></div>');
		
		document.all.docType.value = 1;
		$("#docType").change();
		
		$("#docType").attr("disabled",false);
		$("#equipCategory").attr("disabled",false);
		$("#selEquipType").attr("disabled",false);
		$("#selEquipType").attr("disabled",false);
		$("#uploadForm").attr("action","addUpload.htm");
		$("#viewUploadlist").hide();
		$("#uploadWindow").modal('show');
		
	});
	
	
	$("#editBtn").on("click",function(){
		var chkObj = $(":checkbox[name='id']:checked");
		if(2 <= chkObj.length || 0 >= chkObj.length){
			alert('请选择一条数据修改!');
			return;
		}
		var equipDocKeyId = chkObj.val();
		$("#fileGroups").html('<div class="span8"><input class="input-file" type="file" name="file" id="file"/></div>');
		
		$.ajax({
			type: "POST",
			url:  "/PMMS/restful/equipDocMgmt/getEquipDocInfoById",
			dataType: "json",
			success: function(jsonData){
				var docType = jsonData.data.details.docType;
				var id = jsonData.data.details.id;
				$("#uploadWindow").modal('show');
				$("#docType").val($.trim(docType));
				$("#docType").attr("disabled",true);
				$("#equipCategory").attr("disabled",true);
				$("#selEquipType").attr("disabled",true);
				divT.style.display = "none";
				divM.style.display = "none";
				divE.style.display = "none";
				$(".dyncGenFile").each(function(index, item){
					$(this).parent().remove();
				});
				$("#uploadForm").attr("action","editUploadDocs.htm?id="+id);
				$("#equpDocId").val(jsonData.data.details.equipDocId);
				$("#viewUploadlist").show();
			},
			data:{id:equipDocKeyId}
		});
	});
	
	$("#delBtn").on("click",function(){
		var chkObj = $(":checkbox[name='id']:checked");
		if( 0 >= chkObj.length){
			alert('请至少选择一条数据修改!');
			return;
		}
		
		if(!confirm('Are your sure to delete?')) return;
		var equipDetailKeyIds = '';
		var paths = '';
		chkObj.each(function(index,item){
			equipDetailKeyIds += $(this).prev().val()+",";
			paths += $(this).prev().prev().val()+",";
        });
		
		$.ajax({
			type: "POST",
			url:  "/PMMS/restful/equipDocMgmt/delBulk",
			dataType: "json",
			success: function(jsonData){
				alert("delete successful!!");
				
				chkObj.each(function(index,item){
					var pos = docLstTbl.fnGetPosition($(this).parent().parent().parent()[0]);
					docLstTbl.fnDeleteRow(pos[0],null, false);
		        });
				docLstTbl.fnDraw();
			},
			data:{ids:equipDetailKeyIds,paths:paths}
		});
	});
	
	$("#docType").on("change",function(){
		var currSelect=document.all.docType.value;
			if(currSelect=="2"){
				divT.style.display = "block";
				divM.style.display = "block";
				divE.style.display ="none";
				pmms.getEquipModels("1",$("#selEquipType"));
			}else if(currSelect=="3"){
				divT.style.display = "block";
				divM.style.display = "none";
				divE.style.display ="block";
				pmms.getEquipNo("1",$("#selEquipNo"));
			}else{
				divT.style.display = "none";
				divM.style.display = "none";
				divE.style.display ="none";
			}
	});
}
);