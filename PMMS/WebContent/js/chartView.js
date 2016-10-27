//require( ['jquery', 'datagrids','datepicker','charts'], function($) {//'jquery', 'datagrids'   'jquery', 'datatables','bootstrap','chosen'    ,'highchart','highchart-3d','exporting'
require( ['jquery', 'datagrids','ge-bootstrap','datepicker','highchart','highchart-3d','exporting'], function($) {//'jquery', 'datagrids'   'jquery', 'datatables','bootstrap','chosen'    ,'highchart','highchart-3d','exporting'
	//DatePicker Initialization
	$('#startDate').datepicker({
		format: 'yyyy-mm-dd'
	});
	$('#endDate').datepicker({
		format: 'yyyy-mm-dd'
	});
	
	//PM 完成率表格
	$("#pm-table").iidsBasicDataGrid({
		 "useFloater": true, //turn off cell filtering layer
		 "aLengthMenu": [[4, 25, 2, -1], [4, 25, 2, "All"]],
		 "aoColumns": [
		 {sTitle: '区域', mData: 'area'},
		 {sTitle: '1月', mData: 'grade1'},
		 {sTitle: '2月', mData: 'grade2'},
		 {sTitle: '3月', mData: 'grade3'},
		 {sTitle: '4月', mData: 'grade4'},
		 {sTitle: '5月', mData: 'grade5'},
		 {sTitle: '6月', mData: 'grade6'},
		 {sTitle: '7月', mData: 'grade7'},
		 {sTitle: '8月', mData: 'grade8'},
		 {sTitle: '9月', mData: 'grade9'},
		 {sTitle: '10月', mData: 'grade10'},
		 {sTitle: '11月', mData: 'grade11'},
		 {sTitle: '12月', mData: 'grade12'}
		 ],
		 "sAjaxSource": '../../data/pm.json'
	});	
	
	//PM 完成率表格(单个)
	$("#pm-table-single").iidsBasicDataGrid({
		 "useFloater": true, //turn off cell filtering layer
		 "aLengthMenu": [[4, 25, 2, -1], [4, 25, 2, "All"]],
		 "aoColumns": [
		 {sTitle: '设备编号', mData: 'area'},
		 {sTitle: '1月', mData: 'grade1'},
		 {sTitle: '2月', mData: 'grade2'},
		 {sTitle: '3月', mData: 'grade3'},
		 {sTitle: '4月', mData: 'grade4'},
		 {sTitle: '5月', mData: 'grade5'},
		 {sTitle: '6月', mData: 'grade6'},
		 {sTitle: '7月', mData: 'grade7'},
		 {sTitle: '8月', mData: 'grade8'},
		 {sTitle: '9月', mData: 'grade9'},
		 {sTitle: '10月', mData: 'grade10'},
		 {sTitle: '11月', mData: 'grade11'},
		 {sTitle: '12月', mData: 'grade12'}
		 ],
		 "sAjaxSource": '../../data/pm-single.json'
	});	
	
	//PM 完成率图表
	var pm = new Highcharts.Chart(
			{
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
					text : '五大区域'//SJV-EQP-744-069
				},
				xAxis : {
					/* title: {
					    text: '设备名称'
					}, */
					categories : [ '1月', '2月', '3月', '4月' , '5月' , '6月' , '7月' , '8月' , '9月' , '10月' , '11月' , '12月' ]
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
					headerFormat : '<span style="font-size:15px">{point.key}</span><table>',
					pointFormat : '<tr><td style="color:{series.color};padding:0">PM完成率: </td>'
							+ '<td style="padding:0"><b>{point.y:.2f} %</b></td></tr>',
					footerFormat : '</table>',
					shared : true,
					useHTML : true
				},
				series : [ 
				           /*{name : 'SJV-EQP-744-069',data : [ 0.55, 0.43, 0.7, 0 , 0.48, 0.47, 0.89,0.44, 0.23, 0.56,0.56,0.89]}*/
				           {name : '开关制造部',data : [ 0.78, 0.48, 0.47, 0.89,0.23,0.58,0.49,0.95,0.97,0.68,0.75,0.81 ]},
				           {name : '变压器制造部',data : [ 0.44, 0.23, 0.56, 0.10 ,0.86,0.79,0.61,0.57,0.13,0.86,0.48,0.84]},
				           {name : '成套设备制造部',data : [ 0.22, 0.49, 0.6, 0.20 ,0.47, 0.89,0.23,0.58,0.49,0.95,0.44, 0.23]},
				           {name : '其他',data : [ 0.7, 0 , 0.48, 0.47, 0.89,0.44, 0.23, 0.56,0.44, 0.23, 0.56, 0.10]},
				           {name : '总计',data : [ 0.22, 0.49, 0.6, 0.20 ,0.47, 0.89,0.23,0.58,0.49,0.95,0.44, 0.23]}
				]
			});
	
	//设备可利用率
	var equip_avali = $('#equip-avali').highcharts(
			{
				title : {
					text : '设备可利用率统计<br>Equipment availability',
					x : -20
				//center
				},
				subtitle : {
					text : '五大区域',
					x : -20
				},
				xAxis : {
					categories : [ '1', '2', '3', '4', '5', '6', '7', '8', '9',
							'10', '11', '12']
				},
				yAxis : {
					title : {
						text : '可利用率 (%)/月'
					},
					plotLines : [ {
						value : 0,
						width : 1,
						color : '#808080'
					} ]
				},
				tooltip : {
					valueSuffix : ''
				},
				tooltip: {
				    headerFormat: '<span style="font-size:15px">{point.key}</span><table>',
				    pointFormat: '<tr><td style="color:{series.color};padding:0">可利用率: </td>' +
				        '<td style="padding:0"><b>{point.y:.2f} %</b></td></tr>',
				    footerFormat: '</table>',
				    shared: true,
				    useHTML: true
				},
				legend : {//图示说明 配置
					layout : 'vertical',
					align : 'right',
					verticalAlign : 'middle',
					borderWidth : 0
				},
				series : [
						{
							name : '机械加工部 - 2014',
							data : [ 96, 98, 88, 86, 78, 99, 95,
									93, 94, 99, 96, 98
									]
						},
						{
							name : '开关制造部 - 2014',
							data : [  78, 99, 95, 93,
									94, 96, 95, 93, 94, 96, 98,
									88 ]
						},
						{
							name : '变压器制造部 - 2014',
							data : [  94, 99, 95, 93,
									94, 96, 98, 88, 86, 78, 99,
									99]
						},
						{
							name : '成套设备制造部 - 2014',
							data : [  94, 99, 96, 98, 88, 86,
									78, 99, 95, 93, 94, 99]
						},
						{
							name : '其他 - 2014',
							data : [  98, 88,
									86, 78, 96, 98, 88, 86, 78,
									99, 95, 93]
						},
						{
							name : '总计 - 2014',
							data : [  94, 99, 95, 93,
									94, 96, 98, 88, 86, 78, 99,
									99]
						}]
			});
	
	//维修间隔时间
	var maint_gap = $('#maint-gap').highcharts(
			{
				title : {
					text : '平均维修间隔时间统计<br>MTBF',
					x : -20
				//center
				},
				subtitle : {
					text : '五大区域',
					x : -20
				},
				xAxis : {
					categories : [ '1', '2', '3', '4', '5', '6', '7', '8', '9',
							'10', '11', '12']
				},
				yAxis : {
					title : {
						text : '维修间隔时间 （月）/次'
					},
					plotLines : [ {
						value : 0,
						width : 1,
						color : '#808080'
					} ]
				},
				tooltip : {
					valueSuffix : ''
				},
				tooltip: {
				    headerFormat: '<span style="font-size:15px">{point.key}</span><table>',
				    pointFormat: '<tr><td style="color:{series.color};padding:0">维修时间（月）: </td>' +
				        '<td style="padding:0"><b>{point.y:.2f} 次</b></td></tr>',
				    footerFormat: '</table>',
				    shared: true,
				    useHTML: true
				},
				legend : {//图示说明 配置
					layout : 'vertical',
					align : 'right',
					verticalAlign : 'middle',
					borderWidth : 0
				},
				series : [
						{
							name : '机械加工部 - 2014',
							data : [ 0.8,2,5,1,4,5,6,5,4,6,4,2
									]
						},
						{
							name : '开关制造部 - 2014',
							data : [  0.5,0.6,0.7,0.8,0.4,0.2,0.6,2,4,4,1,2]
						},
						{
							name : '变压器制造部 - 2014',
							data : [  0.7,8,4,6,5,4,9,1,5,4,4,3]
						},
						{
							name : '成套设备制造部 - 2014',
							data : [  5,6,4,2,0.3,0.8,4,1,6,2,1,4]
						},
						{
							name : '其他 - 2014',
							data : [  6,0.5,0.4,0.9,2,0,4,1,6,3,2,1]
						},
						{
							name : '总计 - 2014',
							data : [  0.5,0.6,0.7,0.8,0.4,0.2,0.6,2,4,4,1,2]
						},]
			});
	
	//平均维修时间
	var maint_consuming = new Highcharts.Chart(
			{
				chart : {
					renderTo : 'maint-consuming',
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
					text : '平均维修时间统计<br>MTTR'
				},
				subtitle : {
					text : '五大区域'
				},
				xAxis : {
					/* title: {
					    text: '设备名称'
					}, */
					categories : [ '1月', '2月', '3月', '4月' , '5月' , '6月' , '7月' , '8月' , '9月' , '10月' , '11月' , '12月' ]
				},
				yAxis : {
					title : {
						text : '平均维修时间（小时）/月'
					}
				},
				plotOptions : {
					column : {
						depth : 50
					//柱子的Z轴宽度
					}
				},
				tooltip : {
					headerFormat : '<span style="font-size:15px">{point.key}</span><table>',
					pointFormat : '<tr><td style="color:{series.color};padding:0">维修时间: </td>'
							+ '<td style="padding:0"><b>{point.y:.2f} 小时</b></td></tr>',
					footerFormat : '</table>',
					shared : true,
					useHTML : true
				},
				series : [ 
				           {name : '机械加工部',data : [ 0.55, 0.43, 0.7, 3,5,4,7, 0.23, 0.56,0.56,0.89,9]},
				           {name : '开关制造部',data : [ 1,2,3,4,0.23,0.58,0.49,0.95,0.97,0.68,0.75,0.81 ]},
				           {name : '变压器制造部',data : [ 0.44, 0.23, 0.56, 0.10 ,0.86,0.79,0.61,0.57,0.13,0.86,0.48,0.84]},
				           {name : '成套设备制造部',data : [ 0.22, 0.49, 0.6, 0.20 ,0.47, 0.89,0.23,0.58,0.49,0.95,0.44, 0.23]},
				           {name : '其他',data : [ 0.7, 0 , 0.48, 0.47, 0.89,0.44, 0.23, 0.56,0.44, 0.23, 0.56, 0.10]},
				           {name : '总计',data : [ 0.55, 0.43, 0.7, 3,5,4,7, 0.23, 0.56,0.56,0.89,9]}
				]
			});
	
	//维修配件费用
	var maint_cost = new Highcharts.Chart(
			{
				chart : {
					renderTo : 'maint-cost',
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
					text : '维修配件费用统计<br>Maintenance Spare-Part Cost'
				},
				subtitle : {
					text : '五大区域'
				},
				xAxis : {
					/* title: {
					    text: '设备名称'
					}, */
					categories : [ '1月', '2月', '3月', '4月' , '5月' , '6月' , '7月' , '8月' , '9月' , '10月' , '11月' , '12月' ]
				},
				yAxis : {
					title : {
						text : '维修配件费用（RMB）/月'
					}
				},
				plotOptions : {
					column : {
						depth : 50
					//柱子的Z轴宽度
					}
				},
				tooltip : {
					headerFormat : '<span style="font-size:15px">{point.key}</span><table>',
					pointFormat : '<tr><td style="color:{series.color};padding:0">配件费用: </td>'
							+ '<td style="padding:0"><b>{point.y:.2f} RMB</b></td></tr>',
					footerFormat : '</table>',
					shared : true,
					useHTML : true
				},
				series : [ 
				           {name : '机械加工部',data : [ 55, 43, 7, 3,5,4,7, 23, 56,56,89,9]},
				           {name : '开关制造部',data : [ 1,2,3,4,23,58,49,95,97,68,75,81 ]},
				           {name : '变压器制造部',data : [ 44, 23, 56, 10 ,86,79,0.61,0.57,0.13,0.86,48,84]},
				           {name : '成套设备制造部',data : [ 22, 0.49, 6, 20 ,47, 89,0.23,0.58,0.49,0.95,44, 23]},
				           {name : '其他',data : [ 7, 0 , 48, 47, 89,44, 0.23, 56,0.44, 23, 0.56, 0.10]},
				           {name : '总计',data : [ 44, 23, 56, 10 ,86,79,0.61,0.57,0.13,0.86,48,84]}
				]
			});
	
	//维修配件使用数量
	var maint_quantity = new Highcharts.Chart(
			{
				chart : {
					renderTo : 'maint-quantity',
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
					text : '维修配件使用数量统计<br>Maintenance Spare-Part Quantity Used'
				},
				subtitle : {
					text : '五大区域'
				},
				xAxis : {
					/* title: {
					    text: '设备名称'
					}, */
					categories : [ '1月', '2月', '3月', '4月' , '5月' , '6月' , '7月' , '8月' , '9月' , '10月' , '11月' , '12月' ]
				},
				yAxis : {
					title : {
						text : '维修配件使用数量（个）/月'
					}
				},
				plotOptions : {
					column : {
						depth : 50
					//柱子的Z轴宽度
					}
				},
				tooltip : {
					headerFormat : '<span style="font-size:15px">{point.key}</span><table>',
					pointFormat : '<tr><td style="color:{series.color};padding:0">配件使用数量: </td>'
							+ '<td style="padding:0"><b>{point.y:.2f} 个</b></td></tr>',
					footerFormat : '</table>',
					shared : true,
					useHTML : true
				},
				series : [ 
				           {name : '机械加工部',data : [ 55, 43, 7, 3,5,4,7, 23, 56,56,89,9]},
				           {name : '开关制造部',data : [ 1,2,3,4,23,58,49,95,97,68,75,81 ]},
				           {name : '变压器制造部',data : [ 44, 23, 56, 10 ,86,79,0.61,0.57,0.13,0.86,48,84]},
				           {name : '成套设备制造部',data : [ 22, 0.49, 6, 20 ,47, 89,0.23,0.58,0.49,0.95,44, 23]},
				           {name : '其他',data : [ 7, 0 , 48, 47, 89,44, 0.23, 56,0.44, 23, 0.56, 0.10]},
				           {name : '总计',data : [ 22, 0.49, 6, 20 ,47, 89,0.23,0.58,0.49,0.95,44, 23]}
				]
			});
	
	//按年份统计
	var maint_byYear = new Highcharts.Chart(
			{
				chart : {
					renderTo : 'maint-byYear',
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
					text : '五大区域'
				},
				xAxis : {
					/* title: {
					    text: '设备名称'
					}, */
					categories : [ '2013', '2014', '2015', '2016' , '2017' , '2018' , '2019' , '2020' , '2021' , '2022' , '2023' , '2024' ]
				},
				yAxis : {
					title : {
						text : '计划保养完成率/年'
					}
				},
				plotOptions : {
					column : {
						depth : 50
					//柱子的Z轴宽度
					}
				},
				tooltip : {
					headerFormat : '<span style="font-size:15px">{point.key}</span><table>',
					pointFormat : '<tr><td style="color:{series.color};padding:0">配件使用数量: </td>'
							+ '<td style="padding:0"><b>{point.y:.2f} 个</b></td></tr>',
					footerFormat : '</table>',
					shared : true,
					useHTML : true
				},
				series : [ 
				           {name : '机械加工部',data : [ 55, 43, 7, 3,5,4,7, 23, 56,56,89,9]},
				           {name : '开关制造部',data : [ 1,2,3,4,23,58,49,95,97,68,75,81 ]},
				           {name : '变压器制造部',data : [ 44, 23, 56, 10 ,86,79,0.61,0.57,0.13,0.86,48,84]},
				           {name : '成套设备制造部',data : [ 22, 0.49, 6, 20 ,47, 89,0.23,0.58,0.49,0.95,44, 23]},
				           {name : '其他',data : [ 7, 0 , 48, 47, 89,44, 0.23, 56,0.44, 23, 0.56, 0.10]},
				           {name : '总计',data : [ 7, 0 , 48, 47, 89,44, 0.23, 56,0.44, 23, 0.56, 0.10]}
				]
			});
	
	//2d 柱状图
	$('#container')
			.highcharts(
					{ //图表展示容器，与div的id保持一致
						chart : {
							type : 'column' //指定图表的类型，默认是折线图（line）
						},
						title : {
							text : '每周设备故障停机时间统计'
						},
						xAxis : {
							/* title: {
							    text: '设备名称'
							}, */
							categories : [ 'SP302', 'MV086', 'MV085', 'FPI' ]
						},
						yAxis : {
							title : {
								text : '停机时间/小时'
							}
						},
						tooltip : {
							headerFormat : '<span style="font-size:15px">{point.key}</span><table>',
							pointFormat : '<tr><td style="color:{series.color};padding:0">停机时间: </td>'
									+ '<td style="padding:0"><b>{point.y:.2f} 小时</b></td></tr>',
							footerFormat : '</table>',
							shared : true,
							useHTML : true
						},
						series : [ 
						           {name : '第3周',data : [ 0.55, 0.43, 0.7, 0 ]},
						           {name : '第4周',data : [ 0.78, 0.48, 0.47, 0.89 ]},
						           {name : '第12周',data : [ 0.44, 0.23, 0.56, 0.10 ]},
						           {name : '第18周',data : [ 0.22, 0.49, 0.6, 0.20 ]}
						]
					});

	//2d 饼图
	$("#pie")
			.highcharts(
					{
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : 1,//null,
							plotShadow : false
						},
						title : {
							text : '停机故障时间百分比, 2014'
						},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled : true,
									format : '<b>{point.name}</b>: {point.percentage:.1f} %',
									style : {
										color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
												|| 'black'
									}
								}
							}
						},
						series : [ {
							type : 'pie',
							name : '停机时间百分比',
							data : [ [ '费停机故障', 72.00 ], [ '停机故障（质量）', 9.00 ],
									{
										name : '停机故障（机台测试）',
										y : 12.00,
										sliced : true,
										selected : true
									}, [ '停机故障（备件）', 7.00 ] ]
						} ]
					});

});