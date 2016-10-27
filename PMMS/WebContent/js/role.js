require( ['jquery', 'datagrids','ge-bootstrap'], function($) {
			var role_table = null;
			initial();
			
			//新增角色
			$("#btn-role-add").on("click",function(){
				var data = getSubmitData();
				$.ajax({
					url:'/PMMS/restful/role/saveRoleInfo',
					type:'post',
					dataType:'json',
					cache:false,
					data:data,
					beforeSend:function(xhr){
						//$("#loading").show();
					},
					complete:function (XMLHttpRequest, textStatus) {
						//$("#loading").hide();
					},
					success:function(backData,status){
						alert('success!!!');
						var detail = backData.data.detail;
						role_table.fnAddData(backData.data.detail);
					},
					error:function(XmlHttpRequest){
						alert('error!!!');
					}
		    	});
			});
			
			//修改角色
			$("#btn-role-modify").on("click",function(){
				var check_box = $("input[name='role_id']:checked");
				var data = getSubmitData();
				data.role_id = $(check_box).val();
				$.ajax({
					url:'/PMMS/restful/role/modifyRoleInfo',
					type:'post',
					dataType:'json',
					cache:false,
					data:data,
					beforeSend:function(xhr){
						//$("#loading").show();
					},
					complete:function (XMLHttpRequest, textStatus) {
						//$("#loading").hide();
					},
					success:function(backData,status){
						alert('success!!!');
						var detail = backData.data.detail;
						var deletedRow = null;
				        deletedRow = $(check_box).parent().parent().parent().parent()[0];
 						role_table.fnDeleteRow(deletedRow,null,true);
 						role_table.fnAddData(backData.data.detail);
					},
					error:function(XmlHttpRequest){
						alert('error!!!');
					}
		    	});
			});
			
			//删除角色
			$("#btn-role-delete").on("click",function(){
				var check_box = $("input[name='role_id']:checked");
				$.ajax({
					url:'/PMMS/restful/role/deleteRoleInfo',
					type:'post',
					dataType:'json',
					cache:false,
					data:{
						role_id:$(check_box).val()
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
						var deletedRow = null;
				        deletedRow = $(check_box).parent().parent().parent().parent()[0];
 						role_table.fnDeleteRow(deletedRow,null,true);
					},
					error:function(XmlHttpRequest){
						alert('error!!!');
					}
		    	});
			});
			
			function getSubmitData(){
				var roleName = $("#roleName").val();
				var remark = $("#remark").val();
				var owo = $("#chk-owo").is(":checked")?'Y':'N';
				var pwo = $("#chk-pwo").is(":checked")?'Y':'N';
				var pm = $("#pm").is(":checked")?'Y':'N';
				var spIn = $("#chk-spIn").is(":checked")?'Y':'N';
				var spOut = $("#chk-spOut").is(":checked")?'Y':'N';
				var pmff = $("#chk-pmff").is(":checked")?'Y':'N';
				var ea = $("#chk-ea").is(":checked")?'Y':'N';
				var mtbf = $("#chk-mtbf").is(":checked")?'Y':'N';
				var mttr = $("#chk-mttr").is(":checked")?'Y':'N';
				var mspc = $("#chk-mspc").is(":checked")?'Y':'N';
				var mspn = $("#chk-mspn").is(":checked")?'Y':'N';
				var em = $("#chk-em").is(":checked")?'Y':'N';
				var dm = $("#chk-dm").is(":checked")?'Y':'N';
				var idmIn = $("#chk-idmIn").is(":checked")?'Y':'N';
				var idmOut = $("#chk-idmOut").is(":checked")?'Y':'N';
				var um = $("#chk-um").is(":checked")?'Y':'N';
				var rm = $("#chk-rm").is(":checked")?'Y':'N';
				
				var data = {
						roleName:roleName,
						remark:remark,
						owo:owo,
						pwo:pwo,
						pm:pm,
						spIn:spIn,
						spOut:spOut,
						pmff:pmff,
						ea:ea,
						mtbf:mtbf,
						mttr:mttr,
						mspc:mspc,
						mspn:mspn,
						em:em,
						dm:dm,
						idmIn:idmIn,
						idmOut:idmOut,
						um:um,
						rm:rm
				}
				return data;
			}
	
			//初始化表格数据 initalize datatables
			function initial(equipType){
				buildTable();
			}
			
			//初始化datatable
			function buildTable(){
				$('#role-table-div').empty().append('<div class="input-append flR pull-right">'+
						'<input type="text" class="input-medium search-query" data-filter-table="dt_roleManagement"><button class="btn btn-icon"><i class="icon-search"></i></button>'+
						'</div>'+
						'<table class="table table-bordered dataTable table-responsive" data-table-name="dt_roleManagement" id="dt_roleManagement"></table>');
				$.ajax({
					cache:false,
					type: "POST",
					url:  "/PMMS/restful/role/getAllRoles",
					dataType: "json",
					success: function(jsonData){
						if(jsonData.success){
							var dataList = jsonData.data.list;
							$('#dt_roleManagement').iidsBasicDataGrid( {//iidsBasicDataGrid  dataTable
								"bProcessing": true,
						        "bPaginate":true,
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
									 {sTitle: "<label for='selectAll'>选择</label>", mData: 'role_id',"sDefaultContent":"","sClass":"center","bSortable":false,"bSearchable": true, "bVisible": true,"filter":false},
									 {sTitle: '角色编号', mData: 'role_id',"filter":false},
									 {sTitle: '角色名称', mData: 'role_name'},
									 {sTitle: '角色描述', mData: 'remark'}
								 ],
								 
								"aaData":dataList,
								"fnRowCallback":function(nRow,aData,iDisplayIndex,iDisplayIndexFull){
									 //$("td:eq(0)",nRow).html("<input type='checkbox'>");
									 //return nRow;
									$('td:eq(0)', nRow).html(
			                    			'<div class="radio">'+
			                    				'<label>'+
			                    					'<input type="radio" name="role_id" class="radio" value="'+aData.role_id+'">'+
			                    				'</label>'+
			                    			'</div>'
			                    	);
								 }
						    });
							//***get datatables object***
							role_table = $('#dt_roleManagement').dataTable();
						}
					}
				});
			}
		});