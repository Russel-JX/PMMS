var objElementSelected = null;
function setTypeAhead(strElement,url,strMinLength,strItems,fnmatcher,fnupdater,ajaxCallable)
{
	
	var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      
    	strItems = 10;
	var map = {};
	$(strElement).typeahead({
		source : function (query,process) 
		{
			var strurl= url+query+"&"+$.now();
			performAjaxOperation(false,strurl,false,null,"text/html","GET",ajaxCallable,null,process,map);
			$('.typeahead').css({'min-width':$('.typeahead').siblings('input').width() + 10});
		},
		    minLength:strMinLength,
		    items:strItems,
		    matcher:function(item){
		    	
		    	var fn = window[fnmatcher];
				return fn(item,this.query);
		    	
		    },
		    updater: function(item)
			{
		    	var fn = window[fnupdater];
				return fn(map[item],item);
			},
			highlighter: function(item)
			{
				var query = this.query.replace(/[\-\[\]{}()*+?.,\\\^$|#\s]/g, '\\$&');
		        return item.replace(new RegExp('(' + query + ')', 'ig'), function ($1, match) {
		            return '<font class="highlight-match">' + match + '</font>';
		        });
			}
		
	});
}

$.fn.createDatePicker =  function (format,todayBtn,autoClose)
{
	var $dateTextFiled = $(this).find('input');
	$dateTextFiled.removeAttr('readonly');
	$dateTextFiled.on('keypress',function(){return false;});

	if($(this).find('.icon-calendar'))
	{
		$(this).find('.icon-calendar').parent('.btn').show();
	}
	$(this).datepicker({
		'format': format,
		'autoclose': autoClose,
		'todayBtn':todayBtn,
		'todayHighlight': true,
		 "orientation": "auto"

	});

};

$.fn.disableDatePicker = function()
{
	var $dateTextFiled = $(this).find('input');
	$dateTextFiled.attr('readonly',true);
	$(this).datepicker('remove');
	
	if($(this).find('.icon-calendar'))
	{
		$(this).find('.icon-calendar').parent('.btn').hide();
	}
};

function trimAll(sString) 
{
	while (sString.substring(0,1) == ' ')
	{
		sString = sString.substring(1, sString.length);
	}
	while (sString.substring(sString.length-1, sString.length) == ' ')
	{
		sString = sString.substring(0,sString.length-1);
	}
return sString;
}

function search(item, query) 
{
	
	var input =query;
	if (item.toLowerCase().contains(input.toLowerCase())) 
	{
		return true;
	}
	return false;
}

function autoCompleteOff()
{
	$('input').attr({"autocomplete":"off"});
}
String.prototype.contains = function(str) {
	return this.indexOf(str) > -1;
};
String.prototype.trim = function () 
{
	return this.replace(/^\s+|\s+$/, "");
};
$.fn.disableElement = function()
{
	$(this).attr("disabled","disabled");
};
$.fn.enableElement = function()
{
	$(this).removeAttr("disabled");
};
$.fn.clearSelectRow = function()
{
	$(this).find('tbody').find('tr').each(function()
			{
				$(this).removeClass('row_selected');
			});
};
function clearRowBGColor(tableName){
	
	$('#'+ tableName+ ' tbody').find('tr').each(function()
			{
				if ( $(this).hasClass('row_selected') ) 
				{
					  $(this).removeClass('row_selected');
				}
				else {
					  $('#' + tableName + ' tbody tr').removeClass('row_selected');
				}
				
			});
	
}

function adjusTypeaheadDropWidth()
{
	$('.typeahead').width($('.typeahead').siblings('input').width() + $('.typeahead').siblings('btn').width()).addClass('left-align');
 	$('.typeahead li').each( function() {$(this).attr('title',$(this).find('a').text());});
}
$.fn.setPlaceHolder = function(text)
{
	$(this).attr('placeholder',text);
};



function getDateinFormat(data,toFormat)
{
	data = new Date(data);
	if(toFormat == 'MM/DD/YYYY')
	{
		return (data.getMonth()+1+"/"+ data.getDate()+"/"+data.getFullYear());
	}
	else
	{
		return (data.getMonth()+1+"/"+ data.getDate()+"/"+data.getFullYear());
	}
}