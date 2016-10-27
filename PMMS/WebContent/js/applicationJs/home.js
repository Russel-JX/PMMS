
$(document).ready(function()
{
	if($("#PendingWork").height > $("#DirectLinks").height()){
		$("#DirectLinks").height($("#PendingWork").height());
	}else{
		$("#PendingWork").height($("#DirectLinks").height());
	}
	var objArray = JSON.parse($('#workOrderListJSONID').val());
	createTable('dt_workOrder', getColModel().workOrderHomeColModel, objArray, '130px', [[0,'asc']], true, true);
	autoCompleteOff();
	$('#dt_workOrder').DataTable().fnAdjustColumnSizing();
});

