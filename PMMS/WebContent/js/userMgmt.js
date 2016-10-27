 require( ['jquery','ge-bootstrap', 'datagrids','datatables', 'datatables-scroller'], function($) {
	 buildUserInfoTb();
	 getRole($("#role"));
	 
	 function buildUserInfoTb(){
		 $.ajax({
				type: "POST",
				url:  "/PMMS/restful/UserMgmt/getAllUser",
				dataType: "json",
				success: function(jsonData){
					if(jsonData.success){
						var dataList = jsonData.data.details;
						var tb=' <table id="userInfoTable" class="table table-striped table-bordered table-condensed" data-table-name="userInfoTable"></table>';
						$("#userInfoTb").empty();
						$("#userInfoTb ").html(tb);
						item_table=$('#userInfoTable').iidsBasicDataGrid( {//iidsBasicDataGrid  dataTable
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
							              //	  {sTitle: '序号NO.', mData: 'seriNum'},
							              	  {sTitle: 'sso', mData: 'sso'},
							              	  {sTitle: '姓名', mData: 'name'},
							              	  {sTitle: '角色', mData: 'roleNm'},
							              	  {sTitle: '联系方式', mData: 'contractNo'},
							              	  {sTitle: '邮箱地址', mData: 'emailId'},
						                 ],
						   "aaData":dataList,
							   "fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
							   	        $('td:eq(0)', nRow).html( '<input id="select" type="radio" targetName="userInfoTable" name="radio" value="'+aData.id+'"/>' );
							
							   }

					    });
						
						 var oTable = $('#userInfoTable').dataTable();
						 oTable.$('tr').click( function (event) {
							 var targetName = $(event.target).attr("targetName");
							 	if(targetName=="userInfoTable"){
							 		var data = oTable.fnGetData( this );
							 		$("#sso").val(data.sso);
							 		$("#pwd").val(data.pwd);
									$("#name").val(data.name);
									//$("#firstName").val(data.firstName);
									$("#contractNo").val(data.contractNo);
									$("#emailId").val(data.emailId);
									$("#role").val(data.role);
									$("#sso").attr("disabled","true");
									hideBtn();
									$("#updateBtn").css("display","block");
									$("#addNewUser").css("display","block");
									var options=document.getElementById("role").options;
									for(var i = 0; i < options.length; i++) {
								        if(options[i].value == data.role_id) {
								                options[i].selected = true;
								        }
									}
							 	}
							 		
									
							  } );
					}
				},
				data:{
				}
			});
		 
		 
	 }
	 
	 //点击新增用户按钮
	 $("#addNewUser").click(function(){
		 $('#addNewUser').css("display","none");
		 clearUser();
		 hideBtn();
		$("#saveBtn").css("display","block");
	 });
	 
	 //点击保存按钮
	 $("#saveBtn").click(function(){
		 var sso=$("#sso").val();
		 var pwd=$("#pwd").val();
		// var lastName=$("#lastName").val();
		 var name=$("#name").val();
		 var contractNo=$("#contractNo").val();
		 var emailId=$("#emailId").val();
		 var role=$("#role").val();
		 //alert("role "+role);
		 

			 $.ajax({
		    		url:'/PMMS/restful/UserMgmt/getUserBySso',
					type:'post',
					dataType:'json',
					cache:false,
					data:{
						"sso":sso
					},
					beforeSend:function(xhr){
						
					},
					complete:function (XMLHttpRequest, textStatus) {
						//$("#loading").hide();
					},
					success:function(backData,status){
						var flag=true;
						flag=checkInput(sso,pwd,role);
						 if(backData.data.detail==null&&flag==true){
							 $.ajax({
						    		url:'/PMMS/restful/UserMgmt/saveUser',
									type:'post',
									dataType:'json',
									cache:false,
									data:{
										"sso":sso,
										"pwd":pwd,
										"name":name,
										"contractNo":contractNo,
										"emailId":emailId,
										"role":role
									},
									beforeSend:function(xhr){
										
									},
									complete:function (XMLHttpRequest, textStatus) {
										//$("#loading").hide();
									},
									success:function(backData,status){
										alert('添加成功!!!');
										buildUserInfoTb();
										clearUser();
									},
									error:function(XmlHttpRequest){
										alert('error!!!');
									}
						    		
						    	});
						}else if(backData.data.detail.isActive=="Y"){
							alert("该sso已存在");
							}else if(backData.data.detail.isActive=="N"){
								alert("该sso已存在，请联系管理员激活");
							}
								
							
					},
					error:function(XmlHttpRequest){
						alert('error!!!');
					}
		    		
		    	});
		 
		
	 });
	 
	//点击修改按钮
	 $("#updateBtn").click(function(){
		 var check_boxes = $('input:radio[name="radio"]:checked');
			if(check_boxes.length > 1||check_boxes.length == 0){ alert('请选择一条记录，进行修改！');return;}
		 var selectId=$('input:radio[name="radio"]:checked').val();
			//alert("selectId "+selectId);
			 var sso=$("#sso").val();
			 var pwd=$("#pwd").val();
			// var lastName=$("#lastName").val();
			 var name=$("#name").val();
			 var contractNo=$("#contractNo").val();
			 var emailId=$("#emailId").val();
			 var role=$("#role").val();
			 var flag=true;
			 flag=checkInput(sso,pwd,role);
			 if(flag){
				 $.ajax({
			    		url:'/PMMS/restful/UserMgmt/updateUser',
						type:'post',
						dataType:'json',
						cache:false,
						data:{
							"sso":sso,
							"pwd":pwd,
							"name":name,
							"contractNo":contractNo,
							"emailId":emailId,
							"role":role
						},
						beforeSend:function(xhr){
							//$("#loading").show();
						},
						complete:function (XMLHttpRequest, textStatus) {
							//$("#loading").hide();
						},
						success:function(backData,status){
							alert('修改成功!!!');
						//	$("#equipmentInfo").modal("hide");
							buildUserInfoTb();
						},
						error:function(XmlHttpRequest){
							alert('error!!!');
						}
			    		
			    	});
			 }
			
	 });
	 //点击删除按钮
	 $("#delBtn").click(function(){
		 var check_boxes = $('input:radio[name="radio"]:checked');
			if(check_boxes.length > 1||check_boxes.length == 0){ alert('请选择一条记录，进行删除！');return;}
		 var selectId=$('input:radio[name="radio"]:checked').val();
			//alert("selectId "+selectId);
		 if(!confirm('你确定要删除?')) return;
			$.ajax({
	    		url:'/PMMS/restful/UserMgmt/deleteUser',
				type:'post',
				dataType:'json',
				cache:false,
				data:{
					id:selectId
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
					buildUserInfoTb();
				},
				error:function(XmlHttpRequest){
					alert('error!!!');
				}
	    		
	    	});
	 });
	 
	 //点击重置按钮
	 $("#reset").click(function(){
		 clearUser();
		 hideBtn();
		 $("#saveBtn").css("display","block");
	 });
	 function clearUser(){
		// document.getElementById("sso")="";
		 $("#sso").val("");
		 $('#pwd').val("");
		 $('#name').val("");
		 $("#role").val("");
		// $("#firstName").val("");
		 $("#contractNo").val("");
		 $("#emailId").val("");
		 $("#sso").attr("disabled",false);
	}
	 
	 function hideBtn(){
		 $("#updateBtn").css("display","none");
		$("#saveBtn").css("display","none");
	 }
	 
	 function getRole(ele){
			$.ajax({
	    		url:'/PMMS/restful/role/getAllRoles',
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
					buildDropDownRole(backData.data.list,ele);
				},
				error:function(XmlHttpRequest){
					alert('error!!!');
				}
	    	});
		}
	 
	 function buildDropDownRole(data,ele){
			for(var i=0;i<ele.length;i++){
				$(ele[i]).empty();
				if(data.length>0){
					$(ele[i]).append("<option value=0> --- 请选择 --- </option>");
					$.each(data,function(index,item){
						var selectOption = "<option value='"+item.role_id+"'>"+item.role_name+"</option>";
						$(ele[i]).append(selectOption);
					});
				}
			}
		}
	 
	 function checkInput(sso,pwd,role){
		 if(sso==""){
				alert("请输入sso！");
				return false;
		}
		 if(pwd==""){
				alert("密码不能为空！");
				return false;
		}
		 if(role==0){
				alert("请选择角色！");
				return false;
		}
		 return true;
	 }
 });



