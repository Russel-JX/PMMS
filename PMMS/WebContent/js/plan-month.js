require( ['jquery', 'ge/datatables','bootstrap','chosen','datepicker'], function($) {
			$("#plan-table").iidsBasicDataGrid({
				 "useFloater": true, //turn off cell filtering layer
				 "aLengthMenu": [[4, 25, 2, -1], [4, 25, 2, "All"]],
				 "aoColumns": [
				 {sTitle: '序号', mData: 'engine'},
				 {sTitle: '是否完成', mData: 'status'},
				 {sTitle: '设备名', mData: 'fulcrumVersion'},
				 {sTitle: '设备型号', mData: 'platform'},
				 {sTitle: '设备编号', mData: 'differentialVersion'},
				 {sTitle: '12月', mData: 'grade1'},
				 {sTitle: '负责人', mData: 'grade2'},
				 {sTitle: '持续时间', mData: 'grade3'},
				 {sTitle: '让出时间', mData: 'grade4'}
				 ],
				 "sAjaxSource": 'data/plan-month.json'
			});	
		});