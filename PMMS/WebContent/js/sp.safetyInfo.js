define(function(){
	//初始库存小于安全库存tab007
	var init = function(){
		var opts ={
			url:"../restful/sparePart/getSPInfo",
			data:{
				safetyFlag:true
			}
		}
		ajaxRequest(opts);
	}
	//ajax request
	function ajaxRequest(opts){
		$.ajax({
			type:"POST",
			url:opts.url,
			dataType:"json",
			async:true,
			data:opts.data,
			success:function(jsonData){
				if(jsonData.success){
					var spHtml='';
					spHtml+='<table class="table table-striped table-bordered table-condensed" data-table-name="spinfoTable007">';
					spHtml+='<thead>';
					spHtml+='</thead>';
					spHtml+='<tbody>';
					spHtml+='</tbody>';
					spHtml+='</table>';
					$("#wrappedspinfoTable007").empty();
					$("#wrappedspinfoTable007").append(spHtml);
					buildTab007(jsonData.data);
				}
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				alert(textStatus);
			}
		});
	}
	//build tab007
	function buildTab007(data){
		var spHeader=[{"sTitle": "编号/SN","mData":"sparePartId"},
					  {"sTitle": "名称/Name","mData":"sparePartName"},{"sTitle": "规格型号/Type","mData":"sparePartType"},
					  {"sTitle": "生产厂商/Source","mData":"source"},{"sTitle": "单位/Measurement","mData":"measurement"},
					  {"sTitle": "单价/Price(RMB)","mData":"price"},{"sTitle": "库存/Stock","mData":"stock"},
					  {"sTitle": "安全库存/SafetyStock","mData":"safetyStock"},{"sTitle":"建议安全库存/SysSafetyStock","mData":"sysSafetyStock"},
					  {"sTitle": "备注/Remark","mData":"remark"}];
		var oTable = $("#wrappedspinfoTable007 table").iidsBasicDataGrid({
			"isResponsive": true,
	        "useFloater": false,
	        "bAutoWidth": true,
	        "bDestroy": true,
			'aaSorting': [],
			"aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
			"aoColumns": spHeader, 
	        "aaData": data.listSpareInfo,
			"oColReorder": {"iFixedColumns": 15},
			"fnRowCallback":function(nRow, aData, iDisplayIndex, iDisplayIndexFull){
			}
		});
	}
	
	//export to excel
	$("#tab007 a[class='btn btn-icon']").on('click',function(){
		window.location.href="../restful/sparePart/spInfoExportToExcel?type=2";
	});
	
	return{
		init:init
	}
});
