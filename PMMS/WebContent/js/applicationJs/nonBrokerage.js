$(document).ready(function () {
	autoCompleteOff();
	var objArray1 = JSON.parse($('#nonBrokerageJSONID').val());	
	var table1 = createTable('dt_nonBrokerage', getColModel().nonBrokerageColModel, objArray1,"130px",[[2,'asc']]);
	selectRow('dt_nonBrokerage');
	fnClearAll('NONBROKER','loadmodule');
	doOnload('NB');
});


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
	if(rowvalue.length > 0  && rowvalue[0]!="No records found."){
		fnReadOnlyFalseNonBrokerage();
		document.getElementById("accountDropDownDiv").style.display = "none";
		document.getElementById("accountTestBoxDiv").style.display = "block";
		document.forms[0].account[0].value = rowvalue[2];
		document.forms[0].account[1].value = rowvalue[0];				
	    document.forms[0].tickerCode.value = rowvalue[3];
	    document.forms[0].noofShare.value = rowvalue[4];
	    //document.forms[0].activeFl.value = rowvalue[5];
	    var accStatus = trimAll(rowvalue[5].substring(0, 3).toLowerCase());
		if (accStatus == "act") {
			document.forms[0].activeFl.value = "Y";
			document.forms[0].activeFl[0].checked=true;
			document.forms[0].activeFl[1].checked=false;
		}
		if (accStatus == "ina") {
			document.forms[0].activeFl.value = "N";
			document.forms[0].activeFl[0].checked=false;
			document.forms[0].activeFl[1].checked=true;
		}
	    document.forms[0].accountId.value = rowvalue[0];
	    document.forms[0].yearNr.value = rowvalue[8];
	    document.forms[0].brokerCode.value = rowvalue[7];
	    document.forms[0].accountNumber.value = rowvalue[1];
	    //document.forms[0].securityActive.value = rowvalue[6];
	    var secActive = trimAll(rowvalue[6].substring(0, 2).toLowerCase());
		if (secActive == "ye") {
			document.forms[0].securityActive.value = "Y";
			document.forms[0].securityActive[0].checked=true;
			document.forms[0].securityActive[1].checked=false;
		}
		if (secActive == "no") {
			document.forms[0].securityActive.value = "N";
			document.forms[0].securityActive[0].checked=false;
			document.forms[0].securityActive[1].checked=true;
		}
	    document.forms[0].accountDateReported.value = rowvalue[11];
	    document.forms[0].personId.value = rowvalue[10];
	    document.forms[0].activeFl[0].disabled = false;
	    document.forms[0].activeFl[1].disabled = false;
	    document.forms[0].mode.value = "normalMode";
	    document.forms[0].lineId.value = rowvalue[9];
	    $('#alert').hide();
    }else{
    	$('#alert').hide();
    }
}

