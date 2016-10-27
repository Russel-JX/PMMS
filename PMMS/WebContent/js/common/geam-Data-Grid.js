
function createTable(tableName, colArrayJSON, dataArray, height, sortType,isPaginate,isInfoEnabled) {
	
		if(null==isPaginate || undefined == isPaginate)
		{
			isPaginate = true;
		}
		if(null==isInfoEnabled || undefined == isInfoEnabled)
		{
			isInfoEnabled = true;
		}
		 var table = $('#'+tableName).iidsBasicDataGrid({			   
			   useFloater: false,
			   "sScrollY": height,
			   'aoColumns': colArrayJSON,
			   'aaData': dataArray,
			   'responsive' : true,
				"aaSorting": sortType,
				"bPaginate": isPaginate,
				"aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
				"bInfo":isInfoEnabled,
				"oLanguage": {
				       "sEmptyTable": "No records found."
				     }
		  });
		 
		 $(table).find('tr').each(
				 function(){
					 $(this).find('td:not(".no-show")').each(
							 function(i){
								 if(!$(this).hasClass('no-show'))
									{
										 $(this).css({'border-left':'0px'});
										 return false;
									}
								 
							 }
					 );
					 
				 }
				 
		 );
		 
		 return table;
}


jQuery.fn.dataTableExt.oSort['alphaNum-asc']  = function(x,y) {
    return alphaNumericSort(x,y);
};
 
jQuery.fn.dataTableExt.oSort['alphaNum-desc'] = function(x,y) {
    return alphaNumericSort(y,x);
};


function alphaNumericSort(a,b)
{
	
	var regExNumericFirst = new RegExp('^[0-9]*');
	var regExAlphabetFirst = new RegExp('^[a-z]*');
	var regEx = new RegExp('^[0-9]*');
	if(a.match(regExNumericFirst)[0] != '')
	{
		regEx = regExNumericFirst;
	}
	else if (a.match(regExAlphabetFirst)[0] != '')
	{
		regEx = regExAlphabetFirst;
	}
	
	var numPart1 = parseInt(a.match(regEx)[0]);
	var numPart2 = parseInt(b.match(regEx)[0]);
    if(numPart1 < numPart2)
    {
        return -1;
    }
    else if(numPart1 > numPart2)
    {
        return 1;
    }
    else if(numPart1 = numPart2)
    {
    	if(a[1] < b[1])
        {
            return -1;
        }
        if(a[1] > b[1])
        {
            return 1;
        }
    }
    return 0;
}