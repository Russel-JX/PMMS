//require( ['jquery', 'datagrids','datepicker','charts'], function($) {//'jquery', 'datagrids'   'jquery', 'datatables','bootstrap','chosen'    ,'highchart','highchart-3d','exporting'
require( ['jquery','pmms', 'datagrids','ge-bootstrap','datepicker','highchart','highchart-3d','exporting'], function($,pmms) {//'jquery', 'datagrids'   'jquery', 'datatables','bootstrap','chosen'    ,'highchart','highchart-3d','exporting'
	$("#equip-id").val("");
	$("#equip-name").val("");
	//DatePicker Initialization
	$('#startDate').datepicker({
		format: 'yyyy-mm-dd'
	});
	$('#endDate').datepicker({
		format: 'yyyy-mm-dd'
	});
	//图表类型（一年中五大区域或五年中五大区域或单个设备）
	var chartType="PM_ONE";
	//导出表格数据
	$("#export").on("click",function(){
		pmms.chartTableExport(chartType);
	});
	var aoColumns_dept = [
	 	                 {sTitle: 'hiddenColumn',"bVisible":false},
	 	                 {sTitle: '区域/Dept'},
	 					 {sTitle: '1月'},
	 					 {sTitle: '2月'},
	 					 {sTitle: '3月'},
	 					 {sTitle: '4月'},
	 					 {sTitle: '5月'},
	 					 {sTitle: '6月'},
	 					 {sTitle: '7月'},
	 					 {sTitle: '8月'},
	 					 {sTitle: '9月'},
	 					 {sTitle: '10月'},
	 					 {sTitle: '11月'},
	 					 {sTitle: '12月'}
	 	                 ];
	 	var aoColumns_single = [
	 	 	                 {sTitle: 'hiddenColumn',"bVisible":false},
	 	 	                 {sTitle: '设备编号/EQNO.'},
	 	 					 {sTitle: '1月'},
	 	 					 {sTitle: '2月'},
	 	 					 {sTitle: '3月'},
	 	 					 {sTitle: '4月'},
	 	 					 {sTitle: '5月'},
	 	 					 {sTitle: '6月'},
	 	 					 {sTitle: '7月'},
	 	 					 {sTitle: '8月'},
	 	 					 {sTitle: '9月'},
	 	 					 {sTitle: '10月'},
	 	 					 {sTitle: '11月'},
	 	 					 {sTitle: '12月'}
	 	 	                 ];	 
	//全局详细数据
	var chart = null;
	//全局设置highchart
	Highcharts.setOptions({
		lang:{
			printChart:'Print chart 打印图表',
			downloadJPEG: 'Download JPEG image下载JPEG格式图片',
			downloadPDF: 'Download PDF document 下载PDF格式文档',
			downloadPNG: 'Download PNG image 下载PNG格式图片',
			downloadSVG: 'Download SVG vector image 下载SVG格式矢量图片'
		}
	});
	
	var options = {
		credits:{
			enabled:false                  //版权信息
		},
		chart : {
			renderTo : 'pm-completion',
			type : 'column',
			margin : 75,
			options3d : {
				enabled : true,
				alpha : 0,
				beta : 0,
				depth : 50,
				viewDistance : 25
			}
		},
		title : {
			text : 'PM完成率统计<br>PM Fulfilment'
		},
		subtitle : {
			text : '无数据'
		},
		xAxis : {
			categories : []
		},
		yAxis : {
			title : {
				text : '计划保养完成率/月'
			}
		},
		plotOptions : {
			column : {
				depth : 50
			//柱子的Z轴宽度
			}
		},
		tooltip : {
			headerFormat : '<span style="font-size:15px">{point.key}计划保养完成率</span><table>',
			pointFormat : '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
					+ '<td style="padding:0"><b>{point.y:.2f} %</b></td></tr>',
			footerFormat : '</table>',
			shared : true,
			useHTML : true
		},
		series : [{"name":"无数据","data":[null,null,null,null,null,null,null,null,null,null,null,null]}]
	};
//	chart = $('#pm-completion').highcharts(options);
	
	//默认查看当前年的五大部门
	getChartData();
	
	//按年统计
	$("#btn-byYear").on("click",function(){
		$.ajax({
			url:'/PMMS/restful/chart/getPMWORateByYear',
			type:'post',
			dataType:'json',
			cache:false,
			data:{
			},
			beforeSend:function(xhr){
				//$("#loading").show();
			},
			complete:function (XMLHttpRequest, textStatus) {
				//$("#loading").hide();
			},
			success:function(backData,status){
//				alert('success!!!');
				chartType="PM_FIVE";
				var backPeriod = backData.data.backPeriod;
				options.subtitle.text = "五大部门";
				options.xAxis.categories = backPeriod;
				options.series = backData.data.list;
				chart = $('#pm-completion').highcharts(options);
				
				//表格数据填充
				var aoColumns = []; 
				var hiddenColumn = {sTitle: 'hiddenColumn',"bVisible":false};
				var deptNmColumn =  {sTitle: '区域'};
				aoColumns.push(hiddenColumn);
				aoColumns.push(deptNmColumn);
				for(var i=0;i<backPeriod.length;i++){
					var colTitle = {"sTitle":backPeriod[i]};
					aoColumns.push(colTitle);
				}
				var tableData = JSON.stringify(backData.data.list);
				buildTable(aoColumns,toTableData(JSON.parse(tableData)));
			},
			error:function(XmlHttpRequest){
				alert('error!!!');
			}
			
		});
	});
	
	var single_list_table = null;
	var updateingRowIndex = null;
	//根据设备名称查询设备编号列表
	$("#equip-name").on("blur",function(){
		var eqName = $("#equip-name").val();
		if(eqName==null||eqName==""){
			return false;
		}
		$("#modal-single-list").modal("show");
		$.ajax({
			url:'/PMMS/restful/EquipInfo/getEquipsByEQName',
			type:'post',
			dataType:'json',
			cache:false,
			data:{
				eqName:eqName
			},
			success:function(backData,status){
//				alert('success!!!');
				if(1==1){
//					alert(backData.data.eqNameList.length);
					$('#single-list-div').empty().append('<div class="input-append flR pull-right">'+
							'<input type="text" class="input-medium search-query" data-filter-table="single-list-table"><button class="btn btn-icon"><i class="icon-search"></i></button>'+
							'</div><table id="single-list-table" class="table table-bordered table-striped tablebrdr mrgzero width100p" data-table-name="single-list-table"></table>');
					var dataList = backData.data.eqNameList;
					single_list_table = $('#single-list-table').iidsBasicDataGrid( {
						"bProcessing": true,
				        "bPaginate":true,
				        "bAutoWidth": false,
				        "bSort": true,
				        "bDestroy":true,
						 "useFloater": false,
//						 "plugins":['G'],
						 "aLengthMenu": [[10, 25, 2, -1], [10, 25, 2, "All"]],
						 "oLanguage": {
					            "sSearch": "查找:",
					            "sZeroRecords": "没数据...",
					            "sLengthMenu": "每页 _MENU_ 条",
					            "sNext": "下页",
					            "sInfo": "_START_ - _END_ of _TOTAL_",
					            "sInfoEmpty": "_START_ - _END_ of _TOTAL_"
					          },
						 "aoColumns": [
						    {sTitle: "<label for='selectAll'>选择</label>", mData: 'equipId',"sDefaultContent":"","sClass":"center","bSortable":false,"bSearchable": true, "bVisible": true,"filter":false},
							{sTitle: '序号<br>/NO.', mData: 'equipId',"bSortable":false,"bSearchable": false},
							{sTitle: '是否完成<br>/Status', mData: 'equipId'},
							{sTitle: '设备名称<br>/EQName', mData: 'equipmentName'},
							{sTitle: '使用部门<br>//EQType', mData: 'deptNm'},
						 ],
						 "aaSorting":[[6,"asc"]],
						"aaData":dataList,
						"fnRowCallback":function(nRow,aData,iDisplayIndex,iDisplayIndexFull){
							$('td:eq(0)', nRow).html(
	                    			'<div class="radio">'+
	                    				'<label>'+
	                    					'<input type="radio" name="equip_id" class="radio" value="'+aData.equipId+'">'+
	                    				'</label>'+
	                    			'</div>'
	                    	);
						 },
						 //第一列为序号自增列
						 "fnDrawCallback": function ( oSettings ) {
					            /* Need to redo the counters if filtered or sorted */
					            if ( oSettings.bSorted || oSettings.bFiltered )
					            {
					                for ( var i=0, iLen=oSettings.aiDisplay.length ; i<iLen ; i++ )
					                {
					                    $('td:eq(1)', oSettings.aoData[ oSettings.aiDisplay[i] ].nTr ).html( i+1 );
					                }
					            }
					        }
				    });
					single_list_table = $('#single-list-table').dataTable();
				}
			},
			error:function(XmlHttpRequest){
				alert('error!!!');
			}
		});
	});
	
	//选中编号事件
	$("#single-select-confirm").on("click",function(){
		var check_boxes = $("input[name='equip_id']:checked");
		var thisEquip = $(check_boxes[0]);
		var id = $(check_boxes[0]).val();
		var thisRow = thisEquip.parent().parent().parent().parent()[0];
		updateingRowIndex = single_list_table.fnGetPosition(thisRow);
		var thisRowData = single_list_table.fnGetData(thisRow);
		$("#equip-id").val(thisRowData.equipId);
		
		getChartData();
	});
	
	$("#search").on("click",function(){
		getChartData();
	});
	
	//全局详细数据
	var data=null;
	//ajax加载一年的数据
	function getChartData(){
		var equipId = $("#equip-id").val();
		var year = $("#planYear").val();
		//判断是按单条设备还是五大部门查询
		if(equipId!=''){
//			var validFlag = verifySingle(equipId);
//			console.log(validFlag);
//			if(validFlag==false){
//				alert("设备编号不存在，请重新输入有效设备编号！");
//				return false;
//			}
			url = "/PMMS/restful/chart/getSinglePMWORate";
		}else{
			url = "/PMMS/restful/chart/getPMWORate";
		}
		$.ajax({
			url:url,
			type:'post',
			dataType:'json',
			cache:false,
			data:{
				equipId:equipId,
				year:year
			},
			beforeSend:function(xhr){
				//$("#loading").show();
			},
			complete:function (XMLHttpRequest, textStatus) {
				//$("#loading").hide();
			},
			success:function(backData,status){
//				alert('success!!!');
				var aoColumns = null;
				if(equipId!=''){
					//****检验编号是否存在****
					if(backData.data.list[0].name==null){
						alert("此设备编号不存在，请输入有效编号！");
						return false;
					}
					chartType="PM_SINGLE";
					options.subtitle.text = "单个设备";
					aoColumns = aoColumns_single;
				}else{
					chartType="PM_ONE";
					options.subtitle.text = "五大部门";
					aoColumns = aoColumns_dept;
				}
				options.xAxis.categories = [ '1月', '2月', '3月', '4月' , '5月' , '6月' , '7月' , '8月' , '9月' , '10月' , '11月' , '12月' ];
				if(backData.data.list.length==1){
					if(backData.data.list[0].data==null){
						backData.data.list[0].data = [null,null,null,null,null,null,null,null,null,null,null,null];
					}
				}
				options.series = backData.data.list;
				chart = $('#pm-completion').highcharts(options);
				
				//表格数据填充。把json转成字符串，否则抵用buildTable方法后，后台返回的json会被改变
				var tableData = JSON.stringify(backData.data.list);
				buildTable(aoColumns,toTableData(JSON.parse(tableData)));
			},
			error:function(XmlHttpRequest){
				alert('error!!!');
			}
			
		});
	}
	
	//json的chart数据转成datatable数据格式
	function toTableData(jsonData){
		var arr = [];
		for(var i=0;i<jsonData.length;i++){
			if(jsonData[i].data==null||jsonData[i].data.length==0){
				jsonData[i].data = [null,null,null,null,null,null,null,null,null,null,null,null];
			}
			jsonData[i].data.unshift(jsonData[i].name);
			jsonData[i].data.unshift(i);//第一列为隐藏列（共14列），用于排序
			arr.push(jsonData[i].data);
		}
		return arr;
		
	}
	
	//初始化datatable
	function buildTable(aoColumns,dataList){
		$('#pm-completion-div').empty().append('<div class="input-append flR pull-right">'+
				'<input type="text" class="input-medium search-query" data-filter-table="pm-completion-table"><button class="btn btn-icon"><i class="icon-search"></i></button>'+
				'</div><table id="pm-completion-table" class="table table-bordered table-striped tablebrdr mrgzero width100p" data-table-name="pm-completion-table"></table>');
		
		$('#pm-completion-table').iidsBasicDataGrid( {//iidsBasicDataGrid  dataTable
			"bProcessing": true,
	        "bPaginate":true,
	        "bAutoWidth": false,
	        "bSort": true,
	        "bDestroy":true,
	        "aaSorting" : [[0, "asc"]],
	        
			 "useFloater": false,
//			 "plugins":['G'],
			 
			 "aLengthMenu": [[10, 25, 2, -1], [10, 25, 2, "All"]],
			//"iDisplayLength":2,
			 //"iDisplayStart":0,
			 
			 "oLanguage": {
		            "sSearch": "查找:",
		            "sZeroRecords": "没数据...",
		            "sLengthMenu": "每页 _MENU_ 条",
		            "sNext": "下页",
		            "sInfo": "_START_ - _END_ of _TOTAL_",
		            "sInfoEmpty": "_START_ - _END_ of _TOTAL_"
		          },
			 
			 "aoColumns": aoColumns,
			 "aaData":dataList
			
	    });
	}

});