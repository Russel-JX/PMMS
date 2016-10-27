require( ['jquery', 'ge-bootstrap'], function($) {//'datagrids',
	//验证计划
	$("#btn-copy-plan").on("click",function(){
		var copyFrom = $("#copyFrom").val();
		var copyTo = $("#copyTo").val();
		$.ajax({
			url:'/PMMS/restful/plan/validatePlans',
			type:'post',
			dataType:'json',
			cache:false,
			data:{
				copyFrom:copyFrom,
				copyTo:copyTo
			},
			beforeSend:function(xhr){
				//$("#loading").show();
			},
			complete:function (XMLHttpRequest, textStatus) {
				//$("#loading").hide();
			},
			success:function(backData,status){
				var fromFlag = backData.data.fromFlag;
				var toFlag = backData.data.toFlag;
				if(fromFlag=="1"){
					$("#no-plan-msg").html(copyFrom+" 年份没有计划可复制！");
					$("#modal-copy-alert").modal("show");
					return false;
				}
				if(toFlag=="2"){
					var currYear = new Date().getFullYear(); 
					if(copyTo<=currYear){
						$("#not-cover-msg").html(copyTo+" 年小于等于当前年份，不能覆盖以往年份的计划！");
						$("#modal-not-cover").modal("show");
						return false;
					}
					$("#confirm-msg").html(copyTo+" 年的保养计划已存在，确定要覆盖此计划吗？");
					$("#modal-copy-confirm").modal("show");
					return false;
				}
				$("#confirm-msg").html("将<span class='alert alert-info'>"+$("#copyFrom").val()+"</span> 年的计划复制到 <span class='alert alert-info'>"+$("#copyTo").val()+"</span> 年？");
				$("#modal-copy-confirm").modal("show");
				
			},
			error:function(XmlHttpRequest){
				alert('error!!!');
			}
    	});
		
	});
	
	//复制计划
	$(".btn-copy-confirm").on("click",function(){
		var copyFrom = $("#copyFrom").val();
		var copyTo = $("#copyTo").val();
		$.ajax({
			url:'/PMMS/restful/plan/generatePlans',
			type:'post',
			dataType:'json',
			cache:false,
			data:{
				copyFrom:copyFrom,
				copyTo:copyTo
			},
			beforeSend:function(xhr){
				//$("#loading").show();
			},
			complete:function (XMLHttpRequest, textStatus) {
				//$("#loading").hide();
			},
			success:function(backData,status){
				alert("success!!");
				$("#modal-copy-confirm").modal("hide");
			},
			error:function(XmlHttpRequest){
				alert('error!!!');
			}
    	});
	});
	
});