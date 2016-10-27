require(['jquery','pmms','ge-bootstrap'],function($,pmms){
	$("#editpwd").click(function(){
		$("#updSSO").val('');
		$("#updNewPwd").val('');
		$("#updOldPwd").val('');
		$("#updConNwePwd").val('');
		$("#msg").html('');
		$("#msg").hide();
		$("#modal-editPwd").modal('show');
	});
	
	$("#submitBtn").click(function(){
		$("#msg").html("");
		$("#msg").hide();
		var sso = $("#updSSO").val();
		var updNewPwd = $("#updNewPwd").val();
		var updOldPwd = $("#updOldPwd").val();
		var updConNwePwd = $("#updConNwePwd").val();
		if(updNewPwd.length > 12 
				|| updOldPwd.length > 12 
				|| updConNwePwd.length > 12){
			alert("密码长度不能超过12位!");
			return;
		}
		if(updNewPwd != updConNwePwd){
			alert("新密码输入的不一致！");
			$("#updOldPwd").empty();
			$("#updConNwePwd").empty();
			return;
		}
		
		$.ajax({
			type: "GET",
			url:  "/PMMS/restful/verifyUser",
			async:false,
			dataType: "json",
			success: function(jsonData){
				if(!jsonData.successFlag){
					$("#msg").html("账号或密码不正确！");
					$("#msg").show();
				}else{
					updatepwd(sso,updConNwePwd);
				}
			},
			data:{sso:sso,password:updOldPwd}
		});
	});
	
	function updatepwd(sso,pwd){
		$.ajax({
			type: "POST",
			url:  "/PMMS/restful/updatePwd",
			dataType: "json",
			success: function(jsonData){
				if(jsonData.successFlag){
					$("#msg").html("修改密码成功！");
					$("#msg").show();
				}else{
					
				}
			},
			data:{sso:sso,password:pwd}
		});
	}
	
});