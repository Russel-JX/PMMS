function getColModel()
{
	var tableTitleJSON = 
	{
		'usersColModel':
		[
		 	{'sTitle': 'SSO','mData': 'sso','sDefaultContent':'', 'sWidth':'10%'},
			{'sTitle': 'First Name', 'mData': 'firstName','sDefaultContent':'', 'sWidth':'15%'},
			{'sTitle': 'Last Name', 'mData': 'lastName','sDefaultContent':'', 'sWidth':'15%'},
			{'sTitle': 'Email ID','mData': 'emailId','sDefaultContent':'', 'sWidth':'25%'},
			{'sTitle': 'Contact Number','mData': 'contactNo','sDefaultContent':'', 'sWidth':'15%'},
			{'sTitle': 'Role','mData': 'role','sDefaultContent':'','sWidth':'15%'},
			{'sTitle': 'ID', 'mData': 'id', 'sDefaultContent':'','sWidth':'5%'},
			{'mData': 'password', 'sDefaultContent':'','sClass':'no-show'}
		],
		'rolesColModel':
		[
		 	{'sTitle': 'Role ID','mData': 'id', 'sWidth':'15%'},
			{'sTitle': 'Role Name', 'mData': 'name', 'sWidth':'35%'},
			{'sTitle': 'Role Description', 'mData': 'description', 'sWidth':'50%'}
		],
		'workOrderHomeColModel':
		[
			{'sTitle': '工单编号/WO ID', 'mData': 'workOrderId', 'sWidth':'15%'},
			{'sTitle': '设备编号/Equip ID', 'mData': 'equipId', 'sWidth':'12%'},
			{'sTitle': '工单类型/WO Type','mData': 'WOType', 'sWidth':'13%'},
			{'sTitle': '创建者/Creator','mData': 'name', 'sWidth':'11%'},
			{'sTitle': 'SSO','mData': 'SSO', 'sWidth':'11%'},
			{'sTitle': '工单状态/WO Status','mData': 'WOStatus', 'sWidth':'14%'},
			{'sTitle': '创建时间/Created Date','mData': 'strCreateDate','sWidth':'15%'},
			{'sTitle': '故障描述/Fault Desc','mData': 'faultDescription', 'sWidth':'20%'}
		]
	};
		return tableTitleJSON;
}