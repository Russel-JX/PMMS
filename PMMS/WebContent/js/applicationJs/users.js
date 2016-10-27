$(document).ready(function()
{	
	var objArray = JSON.parse($('#groupListJSONID').val());
	createTable('dt_userRole', getColModel().usersColModel, objArray, '130px', [[0,'asc']], true, true);
	selectRow('dt_userRole');
	autoCompleteOff();
	$('#dt_userRole').DataTable().fnAdjustColumnSizing();
	onload();
});

function validation(){
	var validate = true;
	if($('#roleNameList').val()==null || $('#roleNameList').val()=="")
	{
		alert("No Role Created. At least Create One Role");
		validate = false;
	}
	return validate;
}
function onload()
{
	document.userForm.sso.value = "";
	document.userForm.password.value = "";
	document.userForm.fname.value = "";
	document.userForm.lname.value = "";
	document.userForm.emailID.value = "";
	document.userForm.cnumber.value = "";
	document.userForm.role.value = "";
	document.userForm.userId.value = "";
	
	document.userForm.sso.disabled = true;
	document.userForm.password.disabled = true;
	document.userForm.fname.disabled = true;
	document.userForm.lname.disabled = true;
	document.userForm.emailID.disabled = true;
	document.userForm.cnumber.disabled = true;
	document.userForm.role.disabled = true;
}

function selectRow(tableName)
{     
       $('#'+ tableName+ ' tbody').on( 'click', 'tr', function ()
       {
              if ( $(this).hasClass('row_selected') )
              {
            	  $(this).removeClass('row_selected');
              }
              else {
            	  $('#' + tableName + ' tbody tr').removeClass('row_selected');
              }
        var rowvalue = [];
              var data = $(this);
              data.children('td').each(function(){
              rowvalue.push($(this).text());
           });
             fnPerformOnRowSelect(rowvalue);
    });
      
}

function fnPerformOnRowSelect(rowvalue){
	if(rowvalue.length > 0  && rowvalue[0]!="No records found.")
	{
		fnClearUser();
		
		document.userForm.sso.disabled = false;
		document.userForm.password.disabled = false;
		document.userForm.fname.disabled = false;
		document.userForm.lname.disabled = false;
		document.userForm.emailID.disabled = false;
		document.userForm.cnumber.disabled = false;
		document.userForm.role.disabled = false;
		
		if(document.userForm.mode.value != "ADD")
        {				
        	document.userForm.sso.value =  rowvalue[0];
        	document.userForm.fname.value =  rowvalue[1];
        	document.userForm.lname.value =  rowvalue[2];
        	document.userForm.emailID.value =  rowvalue[3];
        	document.userForm.cnumber.value =  rowvalue[4];
        	document.userForm.role.value =  rowvalue[5];
        	document.userForm.userId.value =  rowvalue[6];
        	//document.userForm.password.value =  rowvalue[7];
        	document.userForm.hidPwd.value =  rowvalue[7];
			document.userForm.mode.value= "UPDATE";
		}
		$('#alert').hide();
	}else{
    	$('#alert').hide();
    }
}

function fnAddNewUser(){
	$('#dt_userRole').clearSelectRow();
    $('#alert').hide();
    if(validation()){
    	document.userForm.mode.value="ADD";
    	document.userForm.sso.readOnly = false;
    	
    	onload();
    	
    	document.userForm.sso.disabled = false;
    	document.userForm.password.disabled = false;
    	document.userForm.fname.disabled = false;
    	document.userForm.lname.disabled = false;
    	document.userForm.emailID.disabled = false;
    	document.userForm.cnumber.disabled = false;
    	document.userForm.role.disabled = false;
    	
    	$("#nonAddNewButtons").removeClass();
    	$('#AddNewButtons').addClass('no-show');
    }
	
}
function isEmpty(str) {
    if (typeof (str) == "undefined" || str == '' || str== null) {
    	return true;
    }
    return false;
}

function fnSaveUser(){
	
	if(validation()){
		var var_userId = document.getElementById("userId").value;
		var var_sso = document.getElementById("sso").value;
		
		var var_password = document.getElementById("password").value;
		var var_hidPassword = document.getElementById("hidPwd").value;
		
		var var_fname = document.getElementById("fname").value;
		var var_lname = document.getElementById("lname").value;
		var var_role = document.getElementById("role").value;
		var var_emailID = document.getElementById("emailID").value;
		var var_cnumber = document.getElementById("cnumber").value;
		
		var ck_sso = /^[0-9]{1,20}$/;
		//var ck_password = /^[a-zA-Z0-9!@#$%^&*()_]{1,50}$/;
		//var ck_fname = /^[a-zA-Z ]{2,20}$/;
		var ck_email = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/;
		var ck_cnumber = /^[0-9]{11}$/;
		if(!ck_sso.test(var_sso))
		{
			alert('SSO must consist of 1 to 20 digits[0-9]');
		}
		else if(var_password.length > 12){
			alert('密码长度不能超过12个字符！');
		}
		else if(var_fname.length > 20){
			alert('姓名不能超过20个字符！');
		}
		else if(var_lname.length > 20){
			alert('姓名不能超过20个字符！');
		}
		/*else if(!ck_password.test(var_password))
		{
			alert('Password must consist of 1 to 12 characters[a-zA-Z0-9!@#$%^&*()_]');
		}
		else if(!ck_fname.test(var_fname))
		{
			alert('First Name must consist of 2 to 20 characters[a-zA-Z]');
		}
		else if(!ck_fname.test(var_lname))
		{
			alert('Last Name must consist of 2 to 20 characters[a-zA-Z]');
		}*/
		else if(!ck_email.test(var_emailID))
		{
			alert('Email ID is not in proper format');
		}
		else if(!ck_cnumber.test(var_cnumber))
		{
			alert('Contact Number must be of 11 digits[0-9]');
		}
		else if(var_role == null || var_role == '')
		{
			alert('Role is mandatory');
		}
		else
		{
			if(document.userForm.mode.value == "ADD")
		    {
				if(checkSSO(var_sso)){
					alert('This SSO already exists... Please Change SSO');
				}
				else
				{
					$.post("/PMMS/restful/addNewUser",{sso:var_sso, password:var_password, fname:var_fname, lname:var_lname, role:var_role, emailID:var_emailID, cnumber:var_cnumber},function(data){
						if(data == "1")
						{
							document.getElementById("dummy_userform").action = "/PMMS/restful/userMgmt";
							document.getElementById("dummy_userform").submit();
						}
						else if(data == "0")
						{
							alert("Some error, Please try again");
						}
						
					});
				}
		    }
			else if(document.userForm.mode.value == "UPDATE")
			{
		    	$.post("/PMMS/restful/updateUser", {userId:var_userId, sso:var_sso, password:var_password, oriPwd:var_hidPassword,fname:var_fname, lname:var_lname, role:var_role, emailID:var_emailID, cnumber:var_cnumber}, function(data){
					
					if(data == "1")
					{
						document.getElementById("dummy_userform").action = "/PMMS/restful/userMgmt";
						document.getElementById("dummy_userform").submit();
					}
					else if(data == "0")
					{
						alert("Some error, Please try again");
					}
					
				});
		    }
			else
			{
		    	//do nothing
		    }
		}	
	 }
	
}

function beforeUpdate(){
	
}

function afterUpdate(){
	alert("successful update");
}

function updateError(){
	//alert("update error");
}

function fnDeleteUser(){	
	
	var var_userId = document.getElementById("userId").value;
	
	if(var_userId == null || var_userId == '')
	{
		alert('Please select the row');
	}
	else
	{
		if(document.userForm.mode.value != "ADD")
	    {
			$.post("/PMMS/restful/deleteUser",{userId:var_userId},function(data){
				
				if(data == "1")
				{
					document.getElementById("dummy_userform").action = "/PMMS/restful/userMgmt";
					document.getElementById("dummy_userform").submit();
				}
				else if(data == "0")
				{
					alert("Some error, Please try again");
				}
				
			});
	    }
	}
}

function fnClearUser(){
	$('#dt_userRole').clearSelectRow();
    $('#alert').hide();
    
    document.userForm.sso.readOnly = true;
	document.userForm.mode.value="UPDATE";
	
	onload();
	
	$("#AddNewButtons").removeClass();
	$('#nonAddNewButtons').addClass('no-show');
	
}
