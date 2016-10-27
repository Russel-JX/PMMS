
$(document).ready(function()
{	
	var objArray = JSON.parse($('#roleListJSONID').val());
	createTable('dt_roleManagement', getColModel().rolesColModel, objArray, '130px', [[0,'asc']], true, true);
	selectRow('dt_roleManagement');
	autoCompleteOff();
	$('#dt_roleManagement').DataTable().fnAdjustColumnSizing();
	onload();
});


function onload(){
	
	document.roleForm.sso.value = "";
	document.roleForm.fname.value = "";
	document.roleForm.lname.value = "";
	
	document.roleForm.sso.disabled = true;
	document.roleForm.fname.disabled = true;
	document.roleForm.lname.disabled = true;
	
	document.getElementById('moduleAccess_1').value = "NS";
	document.getElementById('moduleAccess_2').value = "NS";
	document.getElementById('moduleAccess_3').value = "NS";
	document.getElementById('moduleAccess_4').value = "NS";
	document.getElementById('moduleAccess_5').value = "NS";
	document.getElementById('moduleAccess_6').value = "NS";
	document.getElementById('moduleAccess_7').value = "NS";
		
	document.getElementById('subModule_1').checked = false;
	document.getElementById('subModule_2').checked = false;
	document.getElementById('subModule_3').checked = false;
	document.getElementById('subModule_4').checked = false;
	document.getElementById('subModule_5').checked = false;
	document.getElementById('subModule_6').checked = false;
	document.getElementById('subModule_7').checked = false;
	document.getElementById('subModule_8').checked = false;
	document.getElementById('subModule_9').checked = false;
	document.getElementById('subModule_10').checked = false;
	document.getElementById('subModule_11').checked = false;
	document.getElementById('subModule_12').checked = false;
	document.getElementById('subModule_13').checked = false;
	document.getElementById('subModule_14').checked = false;
	document.getElementById('subModule_15').checked = false;
	document.getElementById('subModule_16').checked = false;
	document.getElementById('subModule_17').checked = false;
	
	document.roleForm.moduleAccess_1.disabled=true;
	document.roleForm.moduleAccess_2.disabled=true;
	document.roleForm.moduleAccess_3.disabled=true;
	document.roleForm.moduleAccess_4.disabled=true;
	document.roleForm.moduleAccess_5.disabled=true;
	document.roleForm.moduleAccess_6.disabled=true;
	document.roleForm.moduleAccess_7.disabled=true;
	
	document.roleForm.subModule_1.disabled=true;
	document.roleForm.subModule_2.disabled=true;
	document.roleForm.subModule_3.disabled=true;
	document.roleForm.subModule_4.disabled=true;
	document.roleForm.subModule_5.disabled=true;
	document.roleForm.subModule_6.disabled=true;
	document.roleForm.subModule_7.disabled=true;
	document.roleForm.subModule_8.disabled=true;
	document.roleForm.subModule_9.disabled=true;
	document.roleForm.subModule_10.disabled=true;
	document.roleForm.subModule_11.disabled=true;
	document.roleForm.subModule_12.disabled=true;
	document.roleForm.subModule_13.disabled=true;
	document.roleForm.subModule_14.disabled=true;
	document.roleForm.subModule_15.disabled=true;
	document.roleForm.subModule_16.disabled=true;
	document.roleForm.subModule_17.disabled=true;
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
	
	fnClearRole();
	
	document.roleForm.fname.disabled = false;
	document.roleForm.lname.disabled = false;
	
	document.roleForm.moduleAccess_1.disabled=false;
	document.roleForm.moduleAccess_2.disabled=false;
	document.roleForm.moduleAccess_3.disabled=false;
	document.roleForm.moduleAccess_4.disabled=false;
	document.roleForm.moduleAccess_5.disabled=false;
	document.roleForm.moduleAccess_6.disabled=false;
	document.roleForm.moduleAccess_7.disabled=false;
	
	if(rowvalue.length > 0  && rowvalue[0]!="No records found."){
		if(document.roleForm.mode.value != "ADD")
        {	
			
        	document.roleForm.sso.value =  rowvalue[0];
        	document.roleForm.fname.value =  rowvalue[1];
        	document.roleForm.lname.value =  rowvalue[2];
        	
        	var var_roleId = rowvalue[0];
        	        	
        	$.get("/PMMS/restful/accessDetails",{roleId:var_roleId},function(status)
        	{
        		var statusArr = status.split('====');
        	
        		if(statusArr[0] != '')
        		{
        			var val1 = statusArr[0].split('||');
        			
        			for(j=0; j<val1.length; j++)
        			{
        				if(val1[j] != '')
        				{
        					var val11 = val1[j].split('==');
        					        					
        					document.getElementById('moduleAccess_'+val11[0]).value = val11[1];
        				}
        			}
        					
        		}
        		
        		if(statusArr[1] != '')
        		{
        			var val2 = statusArr[1].split(',');
        			
        			for(k=0; k<val2.length; k++)
        			{
        				if((val2[k] != '') && (val2[k] != null))
        				{
        					document.getElementById('subModule_'+val2[k]).checked = true;
        				}	
        			}	
        		}
        		
        		if(document.getElementById('moduleAccess_1').value=='RO' || document.getElementById('moduleAccess_1').value=='RW')
    			{
        			document.roleForm.subModule_1.disabled=false;
        			document.roleForm.subModule_2.disabled=false;
    			}
    		
	    		if(document.getElementById('moduleAccess_2').value=='RO' || document.getElementById('moduleAccess_2').value=='RW')
				{
	    			document.roleForm.subModule_3.disabled=false;
	    			document.roleForm.subModule_4.disabled=false;
	    			document.roleForm.subModule_5.disabled=false;
				}
	    		
	    		if(document.getElementById('moduleAccess_3').value=='RO' || document.getElementById('moduleAccess_3').value=='RW')
				{
	    			document.roleForm.subModule_6.disabled=false;
				}
	    		
	    		if(document.getElementById('moduleAccess_4').value=='RO' || document.getElementById('moduleAccess_4').value=='RW')
				{
	    			document.roleForm.subModule_7.disabled=false;
	    			document.roleForm.subModule_8.disabled=false;
	    			document.roleForm.subModule_9.disabled=false;
	    			document.roleForm.subModule_10.disabled=false;
	    			document.roleForm.subModule_11.disabled=false;
	    			document.roleForm.subModule_17.disabled=false;
				}
	    		
	    		if(document.getElementById('moduleAccess_5').value=='RO' || document.getElementById('moduleAccess_5').value=='RW')
				{
	    			document.roleForm.subModule_12.disabled=false;
	    			document.roleForm.subModule_13.disabled=false;
				}
	    		
	    		if(document.getElementById('moduleAccess_6').value=='RO' || document.getElementById('moduleAccess_6').value=='RW')
				{
	    			document.roleForm.subModule_14.disabled=false;
				}
	    		
	    		if(document.getElementById('moduleAccess_7').value=='RO' || document.getElementById('moduleAccess_7').value=='RW')
				{
	    			document.roleForm.subModule_15.disabled=false;
	    			document.roleForm.subModule_16.disabled=false;
				}
        	});
        	
			document.roleForm.mode.value= "UPDATE";
		}
		$('#alert').hide();
	}else{
    	$('#alert').hide();
    }
}


function fnAddNewRole(){
	$('#dt_roleManagement').clearSelectRow();
    $('#alert').hide();
	document.roleForm.mode.value="ADD";
	document.roleForm.fname.readOnly = false;
	
	onload();
	
	document.roleForm.fname.disabled = false;
	document.roleForm.lname.disabled = false;
	
	document.roleForm.moduleAccess_1.disabled=false;
	document.roleForm.moduleAccess_2.disabled=false;
	document.roleForm.moduleAccess_3.disabled=false;
	document.roleForm.moduleAccess_4.disabled=false;
	document.roleForm.moduleAccess_5.disabled=false;
	document.roleForm.moduleAccess_6.disabled=false;
	document.roleForm.moduleAccess_7.disabled=false;
	
	$("#nonAddNewButtons").removeClass();
	$('#AddNewButtons').addClass('no-show');
}

function isEmpty(str) {
    if (typeof (str) == "undefined" || str == '' || str== null) {
    	return true;
    }
    return false;
}

function fnSaveRole(){
	if(document.roleForm.mode.value == "ADD")
    {
		var var_fname = document.getElementById("fname").value;
		var var_lname = document.getElementById("lname").value;
		
		//var ck_fname = /^[w+]{2,20}$/;
		//var ck_lname = /^[w+]{2,50}$/;
		if(isEmpty(var_fname)){
			alert('角色名称不能为空！');
		}
		else if(var_fname.length > 20)
		{
			alert('角色名称不能超过20个字符!');
		}
		//else if(!ck_lname.test(var_lname))
		else if(var_lname.length > 50)
		{
			//alert('Role Description must consist of 2 to 50 characters[a-zA-Z]');
			alert('描述不能超过50个字符!');
		}
		else
		{
			var mod_access_1 = document.getElementById('moduleAccess_1').value;
			var mod_access_2 = document.getElementById('moduleAccess_2').value;
			var mod_access_3 = document.getElementById('moduleAccess_3').value;
			var mod_access_4 = document.getElementById('moduleAccess_4').value;
			var mod_access_5 = document.getElementById('moduleAccess_5').value;
			var mod_access_6 = document.getElementById('moduleAccess_6').value;
			var mod_access_7 = document.getElementById('moduleAccess_7').value;
			
			var sub_mod_access_1 = 0;
			var sub_mod_access_2 = 0;
			var sub_mod_access_3 = 0;
			var sub_mod_access_4 = 0;
			var sub_mod_access_5 = 0;
			var sub_mod_access_6 = 0;
			var sub_mod_access_7 = 0;
			var sub_mod_access_8 = 0;
			var sub_mod_access_9 = 0;
			var sub_mod_access_10 = 0;
			var sub_mod_access_11 = 0;
			var sub_mod_access_12 = 0;
			var sub_mod_access_13 = 0;
			var sub_mod_access_14 = 0;
			var sub_mod_access_15 = 0;
			var sub_mod_access_16 = 0;
			var sub_mod_access_17 = 0;		
			
			if(document.getElementById("subModule_1").checked == true)
			{
				sub_mod_access_1 = 1;
			}
		
			if(document.getElementById("subModule_2").checked == true)
			{
				sub_mod_access_2 = 2;
			}
			
			if(document.getElementById("subModule_3").checked == true)
			{
				sub_mod_access_3 = 3;
			}
			
			if(document.getElementById("subModule_4").checked == true)
			{
				sub_mod_access_4 = 4;
			}
			
			if(document.getElementById("subModule_5").checked == true)
			{
				sub_mod_access_5 = 5;
			}
			
			if(document.getElementById("subModule_6").checked == true)
			{
				sub_mod_access_6 = 6;
			}
			
			if(document.getElementById("subModule_7").checked == true)
			{
				sub_mod_access_7 = 7;
			}
			
			if(document.getElementById("subModule_8").checked == true)
			{
				sub_mod_access_8 = 8;
			}
			
			if(document.getElementById("subModule_9").checked == true)
			{
				sub_mod_access_9 = 9;
			}
			
			if(document.getElementById("subModule_10").checked == true)
			{
				sub_mod_access_10 = 10;
			}
			
			if(document.getElementById("subModule_11").checked == true)
			{
				sub_mod_access_11 = 11;
			}
			
			if(document.getElementById("subModule_12").checked == true)
			{
				sub_mod_access_12 = 12;
			}
			
			if(document.getElementById("subModule_13").checked == true)
			{
				sub_mod_access_13 = 13;
			}
			
			if(document.getElementById("subModule_14").checked == true)
			{
				sub_mod_access_14 = 14;
			}
			
			if(document.getElementById("subModule_15").checked == true)
			{
				sub_mod_access_15 = 15;
			}
			
			if(document.getElementById("subModule_16").checked == true)
			{
				sub_mod_access_16 = 16;
			}
			
			if(document.getElementById("subModule_17").checked == true)
			{
				sub_mod_access_17 = 17;
			}
			
			if((sub_mod_access_1 == "0" && sub_mod_access_2 == "0") && (mod_access_1 == "RW" || mod_access_1 == "RO"))
			{
				alert('Please mark atleast one checkbox for Work Order or make it as No Show');
			}
			else if((sub_mod_access_3 == "0" && sub_mod_access_4 == "0" && sub_mod_access_5 == "0") && (mod_access_2 == "RW" || mod_access_2 == "RO"))
			{
				alert('Please mark atleast one checkbox for Preventive Maintenance or make it as No Show');
			}
			else if((sub_mod_access_6 == "0") && (mod_access_3 == "RW" || mod_access_3 == "RO"))
			{
				alert('Please mark atleast one checkbox for Spare-Part Warehouse or make it as No Show');
			}
			else if((sub_mod_access_7 == "0" && sub_mod_access_8 == "0" && sub_mod_access_9 == "0" && sub_mod_access_10 == "0" && sub_mod_access_11 == "0" && sub_mod_access_17 == "0") && (mod_access_4 == "RW" || mod_access_4 == "RO"))
			{
				alert('Please mark atleast one checkbox for Analytics & Reports or make it as No Show');
			}
			else if((sub_mod_access_12 == "0" && sub_mod_access_13 == "0") && (mod_access_5 == "RW" || mod_access_5 == "RO"))
			{
				alert('Please mark atleast one checkbox for Equipment/Facility Mgmt or make it as No Show');
			}
			else if((sub_mod_access_14 == "0") && (mod_access_6 == "RW" || mod_access_6 == "RO"))
			{
				alert('Please mark atleast one checkbox for Indirect Material Stock or make it as No Show');
			}
			else if((sub_mod_access_15 == "0" && sub_mod_access_16 == "0") && (mod_access_7 == "RW" || mod_access_7 == "RO"))
			{
				alert('Please mark atleast one checkbox for User/Role Mgmt or make it as No Show');
			}
			else
			{
				if(checkRoleExists(var_fname))
				{
					alert('This Role already exists... Please Change Role Name');				
				}
				else
				{
					$.post("/PMMS/restful/addNewRole",{roleName:var_fname,roleDesc:var_lname,moduleAccess_1:mod_access_1,moduleAccess_2:mod_access_2,moduleAccess_3:mod_access_3,moduleAccess_4:mod_access_4,moduleAccess_5:mod_access_5,moduleAccess_6:mod_access_6,moduleAccess_7:mod_access_7,sub1:sub_mod_access_1,sub2:sub_mod_access_2,sub3:sub_mod_access_3,sub4:sub_mod_access_4,sub5:sub_mod_access_5,sub6:sub_mod_access_6,sub7:sub_mod_access_7,sub8:sub_mod_access_8,sub9:sub_mod_access_9,sub10:sub_mod_access_10,sub11:sub_mod_access_11,sub12:sub_mod_access_12,sub13:sub_mod_access_13,sub14:sub_mod_access_14,sub15:sub_mod_access_15,sub16:sub_mod_access_16,sub17:sub_mod_access_17},function(status)
							{
								if(status == "1")
								{
									document.getElementById("dummy_roleform").action = "/PMMS/restful/roleMgmt";
									document.getElementById("dummy_roleform").submit();
								}
								else if(status == "0")
								{
									alert("Some error, Please try again");
								}
							}
					);
				}
			}
		}
    }
	else if(document.roleForm.mode.value == "UPDATE")
	{
		var var_sso = document.getElementById("sso").value;
		var var_fname = document.getElementById("fname").value;
		var var_lname = document.getElementById("lname").value;
		
		
		//var ck_fname = /^[w+]{2,20}$/;
		//var ck_lname = /^[w+]{2,50}$/;
		
		if(var_fname.length > 20)
		{
			alert('角色名称不能超过20个字符!');
		}
		//else if(!ck_lname.test(var_lname))
		else if(var_lname.length > 50)
		{
			//alert('Role Description must consist of 2 to 50 characters[a-zA-Z]');
			alert('描述不能超过50个字符!');
		}
		else
		{
			var mod_access_1 = document.getElementById('moduleAccess_1').value;
			var mod_access_2 = document.getElementById('moduleAccess_2').value;
			var mod_access_3 = document.getElementById('moduleAccess_3').value;
			var mod_access_4 = document.getElementById('moduleAccess_4').value;
			var mod_access_5 = document.getElementById('moduleAccess_5').value;
			var mod_access_6 = document.getElementById('moduleAccess_6').value;
			var mod_access_7 = document.getElementById('moduleAccess_7').value;
			
			var sub_mod_access_1 = 0;
			var sub_mod_access_2 = 0;
			var sub_mod_access_3 = 0;
			var sub_mod_access_4 = 0;
			var sub_mod_access_5 = 0;
			var sub_mod_access_6 = 0;
			var sub_mod_access_7 = 0;
			var sub_mod_access_8 = 0;
			var sub_mod_access_9 = 0;
			var sub_mod_access_10 = 0;
			var sub_mod_access_11 = 0;
			var sub_mod_access_12 = 0;
			var sub_mod_access_13 = 0;
			var sub_mod_access_14 = 0;
			var sub_mod_access_15 = 0;
			var sub_mod_access_16 = 0;
			var sub_mod_access_17 = 0;
			
			if(document.getElementById("subModule_1").checked == true)
			{
				var sub_mod_access_1 = 1;
			}
		
			if(document.getElementById("subModule_2").checked == true)
			{
				var sub_mod_access_2 = 2;
			}
			
			if(document.getElementById("subModule_3").checked == true)
			{
				var sub_mod_access_3 = 3;
			}
			
			if(document.getElementById("subModule_4").checked == true)
			{
				var sub_mod_access_4 = 4;
			}
			
			if(document.getElementById("subModule_5").checked == true)
			{
				var sub_mod_access_5 = 5;
			}
			
			if(document.getElementById("subModule_6").checked == true)
			{
				var sub_mod_access_6 = 6;
			}
			
			if(document.getElementById("subModule_7").checked == true)
			{
				var sub_mod_access_7 = 7;
			}
			
			if(document.getElementById("subModule_8").checked == true)
			{
				var sub_mod_access_8 = 8;
			}
			
			if(document.getElementById("subModule_9").checked == true)
			{
				var sub_mod_access_9 = 9;
			}
			
			if(document.getElementById("subModule_10").checked == true)
			{
				var sub_mod_access_10 = 10;
			}
			
			if(document.getElementById("subModule_11").checked == true)
			{
				var sub_mod_access_11 = 11;
			}
			
			if(document.getElementById("subModule_12").checked == true)
			{
				var sub_mod_access_12 = 12;
			}
			
			if(document.getElementById("subModule_13").checked == true)
			{
				var sub_mod_access_13 = 13;
			}
			
			if(document.getElementById("subModule_14").checked == true)
			{
				var sub_mod_access_14 = 14;
			}
			
			if(document.getElementById("subModule_15").checked == true)
			{
				var sub_mod_access_15 = 15;
			}
			
			if(document.getElementById("subModule_16").checked == true)
			{
				var sub_mod_access_16 = 16;
			}
			
			if(document.getElementById("subModule_17").checked == true)
			{
				var sub_mod_access_17 = 17;
			}
			
			if((sub_mod_access_1 == "0" && sub_mod_access_2 == "0") && (mod_access_1 == "RW" || mod_access_1 == "RO"))
			{
				alert('Please mark atleast one checkbox for Work Order or make it as No Show');
			}
			else if((sub_mod_access_3 == "0" && sub_mod_access_4 == "0" && sub_mod_access_5 == "0") && (mod_access_2 == "RW" || mod_access_2 == "RO"))
			{
				alert('Please mark atleast one checkbox for Preventive Maintenance or make it as No Show');
			}
			else if((sub_mod_access_6 == "0") && (mod_access_3 == "RW" || mod_access_3 == "RO"))
			{
				alert('Please mark atleast one checkbox for Spare-Part Warehouse or make it as No Show');
			}
			else if((sub_mod_access_7 == "0" && sub_mod_access_8 == "0" && sub_mod_access_9 == "0" && sub_mod_access_10 == "0" && sub_mod_access_11 == "0" && sub_mod_access_17 == "0") && (mod_access_4 == "RW" || mod_access_4 == "RO"))
			{
				alert('Please mark atleast one checkbox for Analytics & Reports or make it as No Show');
			}
			else if((sub_mod_access_12 == "0" && sub_mod_access_13 == "0") && (mod_access_5 == "RW" || mod_access_5 == "RO"))
			{
				alert('Please mark atleast one checkbox for Equipment/Facility Mgmt or make it as No Show');
			}
			else if((sub_mod_access_14 == "0") && (mod_access_6 == "RW" || mod_access_6 == "RO"))
			{
				alert('Please mark atleast one checkbox for Indirect Material Stock or make it as No Show');
			}
			else if((sub_mod_access_15 == "0" && sub_mod_access_16 == "0") && (mod_access_7 == "RW" || mod_access_7 == "RO"))
			{
				alert('Please mark atleast one checkbox for User/Role Mgmt or make it as No Show');
			}
			else
			{
				$.post("/PMMS/restful/updateRole",{roleId:var_sso,roleName:var_fname,roleDesc:var_lname,moduleAccess_1:mod_access_1,moduleAccess_2:mod_access_2,moduleAccess_3:mod_access_3,moduleAccess_4:mod_access_4,moduleAccess_5:mod_access_5,moduleAccess_6:mod_access_6,moduleAccess_7:mod_access_7,sub1:sub_mod_access_1,sub2:sub_mod_access_2,sub3:sub_mod_access_3,sub4:sub_mod_access_4,sub5:sub_mod_access_5,sub6:sub_mod_access_6,sub7:sub_mod_access_7,sub8:sub_mod_access_8,sub9:sub_mod_access_9,sub10:sub_mod_access_10,sub11:sub_mod_access_11,sub12:sub_mod_access_12,sub13:sub_mod_access_13,sub14:sub_mod_access_14,sub15:sub_mod_access_15,sub16:sub_mod_access_16,sub17:sub_mod_access_17},function(data){
					
					if(data == "1")
					{
						document.getElementById("dummy_roleform").action = "/PMMS/restful/roleMgmt";
						document.getElementById("dummy_roleform").submit();
					}
					else if(data == "0")
					{
						alert("Some error, Please try again");
					}
				});
			}
		}
		
    }
	else
	{
    	//do nothing
    }
	
}

function fnDeleteRole(){	
	
	if(document.roleForm.mode.value != "ADD")
    {
		var var_sso = document.getElementById("sso").value;
		if(var_sso=="" || var_sso== null )
		{
			alert("Please select the Role");
		}
		else
		{		
			$.post("/PMMS/restful/deleteRole",{roleId:var_sso},function(status){
				
				if(status == "1")
				{
					document.getElementById("dummy_roleform").action = "/PMMS/restful/roleMgmt";
					document.getElementById("dummy_roleform").submit();
				}
				else if(status == "0")
				{
					alert("Some error, Please try again");
				}
			});
		}
    }
	else
    {
		//do nothing
    }
	
}

function fnClearRole(){
	$('#dt_roleManagement').clearSelectRow();
    $('#alert').hide();
	document.roleForm.mode.value="UPDATE";
	document.roleForm.fname.readOnly = true;
	
	onload();
			
	$("#AddNewButtons").removeClass();
	$('#nonAddNewButtons').addClass('no-show');
	
}

function fnEnableModule_1()
{
	if(document.getElementById('moduleAccess_1').value=="RO" || document.getElementById('moduleAccess_1').value=="RW")
	{
		document.roleForm.subModule_1.disabled=false;
		document.roleForm.subModule_2.disabled=false;
	}
	else
	{
		document.getElementById('subModule_1').checked = false;
		document.roleForm.subModule_1.disabled=true;
		
		document.getElementById('subModule_2').checked = false;
		document.roleForm.subModule_2.disabled=true;
	}
}

function fnEnableModule_2()
{
	if(document.getElementById('moduleAccess_2').value=="RO" || document.getElementById('moduleAccess_2').value=="RW")
	{
		document.roleForm.subModule_3.disabled=false;
		document.roleForm.subModule_4.disabled=false;
		document.roleForm.subModule_5.disabled=false;
	}
	else
	{
		document.getElementById('subModule_3').checked = false;
		document.roleForm.subModule_3.disabled=true;
		
		document.getElementById('subModule_4').checked = false;
		document.roleForm.subModule_4.disabled=true;
		
		document.getElementById('subModule_5').checked = false;
		document.roleForm.subModule_5.disabled=true;
	}
}

function fnEnableModule_3()
{
	if(document.getElementById('moduleAccess_3').value=="RO" || document.getElementById('moduleAccess_3').value=="RW")
	{
		document.roleForm.subModule_6.disabled=false;
	}
	else
	{
		document.getElementById('subModule_6').checked = false;
		document.roleForm.subModule_6.disabled=true;
	}
}

function fnEnableModule_4()
{
	if(document.getElementById('moduleAccess_4').value=="RO" || document.getElementById('moduleAccess_4').value=="RW")
	{
		document.roleForm.subModule_7.disabled=false;
		document.roleForm.subModule_8.disabled=false;
		document.roleForm.subModule_9.disabled=false;
		document.roleForm.subModule_10.disabled=false;
		document.roleForm.subModule_11.disabled=false;
		document.roleForm.subModule_17.disabled=false;
	}
	else
	{
		document.getElementById('subModule_7').checked = false;
		document.roleForm.subModule_7.disabled=true;
		
		document.getElementById('subModule_8').checked = false;
		document.roleForm.subModule_8.disabled=true;
		
		document.getElementById('subModule_9').checked = false;
		document.roleForm.subModule_9.disabled=true;
		
		document.getElementById('subModule_10').checked = false;
		document.roleForm.subModule_10.disabled=true;
		
		document.getElementById('subModule_11').checked = false;
		document.roleForm.subModule_11.disabled=true;
		
		document.getElementById('subModule_17').checked = false;
		document.roleForm.subModule_17.disabled=true;
	}
}

function fnEnableModule_5()
{
	if(document.getElementById('moduleAccess_5').value=="RO" || document.getElementById('moduleAccess_5').value=="RW")
	{
		document.roleForm.subModule_12.disabled=false;
		document.roleForm.subModule_13.disabled=false;
	}
	else
	{
		document.getElementById('subModule_12').checked = false;
		document.roleForm.subModule_12.disabled=true;
		
		document.getElementById('subModule_13').checked = false;
		document.roleForm.subModule_13.disabled=true;
	}
}

function fnEnableModule_6()
{
	if(document.getElementById('moduleAccess_6').value=="RO" || document.getElementById('moduleAccess_6').value=="RW")
	{
		document.roleForm.subModule_14.disabled=false;
	}
	else
	{
		document.getElementById('subModule_14').checked = false;
		document.roleForm.subModule_14.disabled=true;
	}
}

function fnEnableModule_7()
{
	if(document.getElementById('moduleAccess_7').value=="RO" || document.getElementById('moduleAccess_7').value=="RW")
	{
		document.roleForm.subModule_15.disabled=false;
		document.roleForm.subModule_16.disabled=false;
	}
	else
	{
		document.getElementById('subModule_15').checked = false;
		document.roleForm.subModule_15.disabled=true;
		
		document.getElementById('subModule_16').checked = false;
		document.roleForm.subModule_16.disabled=true;
	}
}
