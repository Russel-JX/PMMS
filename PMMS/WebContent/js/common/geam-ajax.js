
function performAjaxOperation(boolAsync,strUrl,boolCache,strData,strContentType,strMethodType,successCallback,errorCallback,process,map)
{

	var resp = '';
	$.ajax({
		async:boolAsync,
		url:strUrl,
		cache:boolCache,
		data:strData,
		contentType:strContentType,
		type:strMethodType,
		success: function(response)
				{
				resp = response;
				if(successCallback!=null && successCallback != undefined)
				{
					successCallback(response,process,map);
				}
				},
		error: errorCallback
	});

	return resp;
}
