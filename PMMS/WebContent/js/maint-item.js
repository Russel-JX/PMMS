/*require.config({
    baseUrl: '../../js/'
});*/

require( ['jquery', 'pmms','datagrids','ge-bootstrap'], function($,pmms) {//"table[data-table-name='plan-table']"  maint-item-table  //,'bootstrap','chosen','d3'
			var item_table = null;
			initial();
			//popover config
			$("#cycleHelp").popover({
				html:true,
				delay:{show:1,hide:500},
				placement:"bottom",
				trigger:"hover",
				title:"保养周期说明",
				content:'<div id="popover2-content-wrapper">'+
				'<p><strong>保养周期类型</strong><br/>'+
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
			//设备类型change事件
			$("body").on("change",".equipType",function(){
				var equipType = $(this).val();
				var equipTypeId = $(this).attr("id");
				var targetId = null;
				//alert("id是："+equipTypeId);
				if(equipTypeId=="equipType"){
					targetId = "equipName";
				}else if(equipTypeId=="equipType-modal"){
					targetId = "equipName-modal";
				}
				//alert("in on change..."+targetId);
				pmms.getEquipNames(equipType,$("#"+targetId));
				
			});
			//增加或修改保养内容验证
			function maintItemSubmitValidation(){
				if($("#equipName-modal").val()=='--- 请选择 ---'||$("#equipName-modal").val()==null||$("#equipName-modal").val()==""){
					alert("请选择设备名称！");
					return false;
				}
				if($("#item").val()==''||$("#item").val()==null){
					alert("请填写保养内容！");
					return false;
				}
				//字符长度限制
//				alert($("#item").val().length+"kk"+$("#standard").val().length);
				if($("#item").val().length>100){
					alert("保养内容长度不能超过100个字符！");
					return false;
				}
				if($("#standard").val().length>50){
					alert("正常状态长度不能超过50个字符！");
					return false;
				}
				return true;
			}
			//设备名称change事件
			$("#equipName").on("change",function(){
				var equipName = $(this).val();
				var equipTypeId = $(this).attr("id");
				buildTable();
			});
			//增加保养内容
			$("#btn-add").on("click",function(){
				$("#modal-add-maintItem-title").html("增加保养内容");
				$("#btn-add-confirm").show();
				$("#btn-modify-confirm").hide();
				
				$("#equipType-modal").replaceWith(
					'<select class="span3 equipType equipType-modal" id="equipType-modal">'+
						'<option value="1">生产设备</option>'+
						'<option value="2">特种设备</option>'+
						'<option value="3">设施</option>'+
					'</select>'	
				);
				$("#equipName-modal").empty();
				$("#equipName-modal").append($("#equipName").find("option").clone());
				
			});
			
			//增加保养内容确认
			$("#btn-add-confirm").on("click",function(){
				if(maintItemSubmitValidation()==false){
					return false;
				}
				var equipName = $("#equipName-modal").val();
				var item = $("#item").val();
				var cycle = getCycleValue();
				var standard = $("#standard").val();
				var way = getWayValue();
				var remark = $("#remark").val();
				if(remark.length > 395){
					alert("备注不能超过400个字符长度!");
					return;
				}
				
				$.ajax({
		    		url:'/PMMS/restful/maintItem/saveMaintItem',
					type:'post',
					dataType:'json',
					cache:false,
					data:{
						equipName:equipName,
						item:item,
						cycle:cycle,
						standard:standard,
						way:way,
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
					},
					error:function(XmlHttpRequest){
						alert('error!!!');
					}
		    		
		    	});
			});
			
			//获取周期的值
			function getCycleValue(){
				var yearCycle = $("#cycle-year").is(":checked")?'Y':'';
				var quarCycle = $("#cycle-quarter").is(":checked")?'Q':'';
				var monthCycle = $("#cycle-month").is(":checked")?'M':'';
				var cycle = yearCycle+","+quarCycle+","+monthCycle;
				return cycle;
			}
			//获取保养方法的值
			function getWayValue(){
				var item1 = $("#item1").is(":checked")?'1,':'';
				var item2 = $("#item2").is(":checked")?'2,':'';
				var item3 = $("#item3").is(":checked")?'3,':'';
				var item4 = $("#item4").is(":checked")?'4,':'';
				var item5 = $("#item5").is(":checked")?'5,':'';
				var item6 = $("#item6").is(":checked")?'6,':'';
				var way = item1+item2+item3+item4+item5+item6;
				return way;
			}
			
			//保养方法多选选中事件，高亮选中的option
			$("#way option").on("click",function(){
				$(this).toggleClass("bg-bluecolor");
			});
			//正在做修改的行索引
			var updateingRowIndex = null;
			//修改保养内容
			$("#btn-modify").on("click",function(){
				$("#modal-add-maintItem-title").html("修改保养内容");
				$("#btn-add-confirm").hide();
				$("#btn-modify-confirm").show();
				$(".cycle").prop("checked",false);
				$(".wayClass").prop("checked",false);
				
				var check_boxes = $("input[name='id']:checked");
				if(check_boxes.length > 1||check_boxes.length == 0){ alert('请选择一条记录，进行修改！');return;}
				
				//前端获取要修改
				var thisMaintItem = $(check_boxes[0]);
				var id = $(check_boxes[0]).val();
				var thisRow = thisMaintItem.parent().parent().parent().parent()[0];
				updateingRowIndex = item_table.fnGetPosition(thisRow);
				var thisRowData = item_table.fnGetData(thisRow);
				$("#hiddenId").val(id);
				//$("#equipType-modal").val($("#equipType").text());
				
				
				pmms.buildDropDownOne($("#equipType"),$("#equipType-modal"));
				pmms.buildDropDownOne($("#equipName"),$("#equipName-modal"));
				var arrCycle = thisRowData.cycle.split(",");
				$("input[name='"+arrCycle[0]+"']").prop("checked",true);
				$("input[name='"+arrCycle[1]+"']").prop("checked",true);
				$("input[name='"+arrCycle[2]+"']").prop("checked",true);
				
				
				//way
				var waysArr = (thisRowData.maint_way).split(",");
				$("input[name='"+waysArr[0]+"']").prop("checked",true);
				$("input[name='"+waysArr[1]+"']").prop("checked",true);
				$("input[name='"+waysArr[2]+"']").prop("checked",true);
				$("input[name='"+waysArr[3]+"']").prop("checked",true);
				$("input[name='"+waysArr[4]+"']").prop("checked",true);
				$("input[name='"+waysArr[5]+"']").prop("checked",true);
//				alert($("#way").val());
				
				$("#item").val(thisRowData.maint_item);
				$("#standard").val(thisRowData.standard);
				$('#remark').val(thisRowData.remark);
				$("#modal-add-maintItem").modal("show");
			});
			
			//修改保养内容确认
			$("#btn-modify-confirm").on("click",function(){
				if(maintItemSubmitValidation()==false){
					return false;
				}
				var id = $("#hiddenId").val();
				var equipName = $("#equipName-modal").val();
				var item = $("#item").val();
				var cycle = getCycleValue();
				var standard = $("#standard").val();
				var way = getWayValue();
				var remark = $("#remark").val();
				if(remark.length > 395){
					alert("备注不能超过400个字符长度!");
					return;
				}
				
				$.ajax({
		    		url:'/PMMS/restful/maintItem/modifyMaintItem',
					type:'post',
					dataType:'json',
					cache:false,
					data:{
						id:id,
						equipName:equipName,
						item:item,
						cycle:cycle,
						standard:standard,
						way:way,
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
						var thisRowData = item_table.fnGetData(updateingRowIndex);
						var updatedRowData = backData.data.detail;
						thisRowData.id = updatedRowData.id;
						thisRowData.equip_name_id = updatedRowData.equip_name_id;
						thisRowData.maint_item = updatedRowData.maint_item;
						thisRowData.cycle = updatedRowData.cycle;
						thisRowData.maint_way = updatedRowData.maint_way;
						thisRowData.standard = updatedRowData.standard;
						thisRowData.creator = updatedRowData.creator;
						thisRowData.created_date = updatedRowData.created_date;
						thisRowData.remark = updatedRowData.remark;
						item_table.fnUpdate(thisRowData,updateingRowIndex,undefined,true,true);
						returnToOldPage(item_table,"update",null);
					},
					error:function(XmlHttpRequest){
						alert('error!!!');
					}
		    		
		    	});
			});
			
			//删除确认
			$("#btn-remove-confirm").on("click",function(){
				var check_boxes = $("input[name='id']:checked");
				if(check_boxes.length<=0){ alert('请至少选择一条记录！');return;}
				var dropIds = '';
	            check_boxes.each(function(index,item){
	                dropIds += $(this).val()+","; 
	            });
	            $.ajax({
	            	type: "POST",
	            	url: "/PMMS/restful/maintItem/removeMaintItem",
	    			dataType: "json",
	                data:{'ids':dropIds},
	                success: function(jsonData){
	    				if(jsonData.success){
	    					alert("删除成功！");
	    					$("#modal-delete-maintItem").modal("hide");
				            var deletedRow = null;
	    					for(var i=0;i<check_boxes.length;i++){
	    						deletedRow = $(check_boxes[i]).parent().parent().parent().parent()[0];
	    						if(i==(check_boxes.length-1)){
	    							item_table.fnDeleteRow(deletedRow,null,true);
	    						}else{
	    							item_table.fnDeleteRow(deletedRow,null,false);
	    						}
	    					}
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
			
			//导出Excel
			$("#export").on("click",function(){
				var equipNameId = $("#equipName").val();
				if(equipNameId==""||equipNameId==null){
					alert("请先选择设备名称！");
					return false;
				}
				window.location.href="/PMMS/restful/maintItem/export?equipNameId="+equipNameId;
			});
			
			//全选框
			$("body").on("change","#selectAll",function(){
				if($(this).is(":checked")){
					$("input[name='id']").prop("checked",true);
				}else{
					$("input[name='id']").prop("checked",false);
				}
			});	 
			
			//初始数据方法
			function initial(equipType){
				pmms.getEquipNames("1",$(".equipName"));
			}
			
			//build datatables
			function buildTable(){
				
				$('#maint-item-div').empty().append('<div class="input-append flR pull-right">'+
						'<input type="text" class="input-medium search-query" data-filter-table="plan-table"><button class="btn btn-icon"><i class="icon-search"></i></button>'+
						'</div><table id="maint-item-table" class="table table-bordered table-striped tablebrdr mrgzero width100p" data-table-name="plan-table"></table>');
				
				$.ajax({
					type: "POST",
					url:  "/PMMS/restful/maintItem/getMaintItems",
					dataType: "json",
					success: function(jsonData){
//						console.log(jsonData);
						if(jsonData.success){
//							console.log("get provinces success");
							var dataList = jsonData.data.list;
							//**************************************************************
							//***************************????初始化表格的不同方式，iidsBasicDataGrid方式不能拿到datatables对象；而dataTable方式可以????***********************************
							$('#maint-item-table').iidsBasicDataGrid( {//iidsBasicDataGrid  dataTable
								"bProcessing": true,
						        "bPaginate":true,
						        "bAutoWidth": false,
						        "bSort": true,
						        "bDestroy":true,
						        
								 "useFloater": false, //turn off cell filtering layer  true这一行影响fnRowCallback  fnPageChange的效用
								 //"plugins":['R'],
								 
								 "aLengthMenu": [[10, 25, 2, -1], [10, 25, 2, "All"]],
								//"iDisplayLength":2,
								 //"iDisplayStart":0,
								 
								 "aoColumns": [
								 {sTitle: "<input type='checkbox' name='selectAll' id='selectAll' ><label for='selectAll'>全选<br>/All</label>", mData: 'id',"sDefaultContent":"","sClass":"center","bSortable":false,"bSearchable": true, "bVisible": true,"filter":false},
								 {sTitle: '序号<br>/NO.', mData: 'equip_name_id',"sClass":"center","filter":false,"bSortable":false,"bSearchable": false},
								 {sTitle: '设备名称编号<br>/EQNameNO.', mData: 'equip_name_id',"sClass":"center","filter":false},
								 {sTitle: '保养内容<br>/MaintItem', mData: 'maint_item',"filter":false},
								 {sTitle: '周期<br>/Cycle', mData: 'cycle',filter:false},
								 {sTitle: '方法<br>/Way', mData: 'maint_way',"sClass":"essential"},
								 {sTitle: '正常状态<br>/Standard', mData: 'standard'},
								 {sTitle: '备注<br>/Remark', mData: 'remark'},
								 ],
								 //"sAjaxSource": '../../data/maint-item.json',
								 //"bServerSide": true,
								 //"sAjaxSource": '/PMMS/restful/maintItem/getMaintItems',
								 //"sAjaxDataProp":"data.list",
								 //"iDeferLoading": 0,
								 
								"aaData":dataList,
								"fnRowCallback":function(nRow,aData,iDisplayIndex,iDisplayIndexFull){
									 //$("td:eq(0)",nRow).html("<input type='checkbox'>");
									 //return nRow;
									$('td:eq(0)', nRow).html(
			                    			'<div class="checkbox">'+
			                    				'<label>'+
			                    					'<input type="checkbox" name="id"  class="checkbox" value="'+aData.id+'">'+
			                    				'</label>'+
			                    			'</div>'
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
							        },
								 "fnPageChange":function(){
									 alert(8);
								 }
						    });
							//***get datatables object***
							item_table = $('#maint-item-table').dataTable();
						}
					},
					data:{
						equipName:$('#equipName').val()
					}
				});
			}
		});