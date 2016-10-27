<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <% String contextPath = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PMMS - EA设备可利用率统计</title>
    <meta name="viewport" content="width=device-width">
     <!-- For third-generation iPad with high-resolution Retina display: -->
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="<%=contextPath%>/favicon/favicon-144px.png">
    <!-- For iPhone with high-resolution Retina display: -->
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="<%=contextPath%>/favicon/favicon-114px.png">
    <!-- For first- and second-generation iPad: -->
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="<%=contextPath%>/favicon/favicon-72px.png">
    <!-- For non-Retina iPhone, iPod Touch, and Android 2.1+ devices: -->
    <link rel="apple-touch-icon-precomposed" href="<%=contextPath%>/favicon/favicon.png">
    <link rel="icon" href="<%=contextPath%>/favicon/favicon.ico">
    
    <link href="<%=contextPath%>/css/bootstrap/ie.css" rel="stylesheet" type="text/css"/>
	<link href="<%=contextPath%>/css/bootstrap/iids-docs.css" rel="stylesheet" type="text/css"/>
	<link href="<%=contextPath%>/css/bootstrap/bootstrap-duallistbox.css" rel="stylesheet" />
	<link href="<%=contextPath%>/css/common/geam-iids.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/iids/responsive.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/common/geam-table.css" rel="stylesheet"/>
    <link href="<%=contextPath%>/css/common/appstyle.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/appCss/PMMS.css" rel="stylesheet"/>
	
	<script src="<%=contextPath%>/js/components/requirejs/require.js"></script>
	<script src="<%=contextPath%>/js/iids/require.config.js" ></script>
	<script src="<%=contextPath%>/js/chart-ea.js" ></script>
	<script src="<%=contextPath%>/js/ajaxReqLoading.js"></script>
	
  </head>
  <body>
  
  	<div class="navbar navbar-static-top">
		<jsp:include page="../common/Header.jsp"></jsp:include>
	</div>  
  
	<!-- 日期选择区域		开始 -->
	<div class="container">
		<div class="page-header">
			<div>
				<h1 class="voice voice-brand pull-left">分析报表统计</h1>
			</div>
			<div>
				<div> </div><div> </div><div> </div>
				<button class="btn btn-large btn-primary pull-right"  id="btn-byYear">按年份统计/View Five Years</button>
				<div> </div><div> </div><div> </div>
			</div>
			<!-- <div>
				<a href="/PMMS/spChart.html"><button class="btn btn-large btn-primary pull-right"  id="btn-show-maintItem">维修配件费用统计</button></a>
			</div> -->
		</div>
		<div class="well">
			<div class="form-group">
				<div>
					<label class="col-sm-2 control-label pull-left" for="planYear"> 年份/Select Year： </label>
								      <select class="col-sm-3 pull-left" id="planYear">
								      		<option value="">-请选择-</option>
											<option value="2014">2014</option>
											<option value="2015">2015</option>
											<option value="2016">2016</option>
											<option value="2017">2017</option>
											<option value="2018">2018</option>
											<option value="2019">2019</option>
											<option value="2020">2020</option>
											<option value="2021">2021</option>
											<option value="2022">2022</option>
											<option value="2023">2023</option>
											<option value="2024">2024</option>
											<option value="2025"> 2025</option>
											<option value="2026">2026</option>
											<option value="2027">2027</option>
											<option value="2028">2028</option>
											<option value="2029">2029</option>
											<option value="2030">2030</option>
										</select>
			   		<!-- <label class="col-sm-2 control-label pull-left" for="startDate">请输入起始日期：</label>
			   		<div class="col-sm-3 pull-left"  id="datepicker1">
			     		<input type="text" class="form-control" id="startDate" placeholder="请输入起始日期" >
			      	</div>
			      	<label class="col-sm-2 control-label pull-left" for="endDate"> 请输入结束日期： </label>
			      	<div class="col-sm-3 pull-left"  id="datepicker1">
		      			<input type="text" class="form-control" id="endDate" placeholder="请输入结束日期" >
		      		</div> -->
		      		<label class="col-sm-2 control-label pull-left" for="equip-name"> 设备名称/EQNAME： </label>
			      	<div class="col-sm-3 pull-left" >
		      			<input id="equip-name" type="text" class="form-control"  placeholder="请输入设备名称" >
		      		</div>
		      		<label class="col-sm-2 control-label pull-left" for="equip-id"> 设备编号/EQNO.： </label>
			      	<div class="col-sm-3 pull-left" >
		      			<input id="equip-id" type="text" class="form-control"  placeholder="要查询单台设备，请输入设备编号" >
		      		</div>
		   		</div>
		   </div>
		   <button id="search" type="submit" class="btn btn-primary"> 查询/Search</button><br>
		</div>
		
	</div>
	<!-- 日期选择区域			结束 -->
	
	<!-- 设备或备件编号列表Modal区域			开始 -->
	<div id="modal-single-list" class="modal modal4 show fade" ><!-- style="display: none;" -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
			<h3>设备编号列表</h3>
		</div>
		<div class="modal-body">
			<div class="module-body">
				<div id="single-list-div" class="table-responsive">
					<div class="input-append flR pull-right">
						<input type="text" class="input-medium search-query" data-filter-table="single-list-table"><button class="btn btn-icon"><i class="icon-search"></i></button>
					</div>
					<table id="single-list-table" class="table table-bordered table-striped tablebrdr mrgzero width100p" data-table-name="single-list-table"></table>
				</div>
			</div>
		</div>
	 	<div class="modal-footer">
			<button href="#" class="btn btn-primary" id = "single-select-confirm" data-dismiss="modal">确定/OK</button>
			<a href="#" class="btn" data-dismiss="modal">取消/Cancel</a>
		</div>
	</div>
	<!-- 设备或备件编号列表Modal区域			结束 -->

	<!-- 图表展示区域		开始 -->
	<div class="container">
		<div class="row ui-sortable">
	        <div class="span12">            
				<section class="module">
					<div class="module-body">
						<!-- PM 完成率图表 -->
						<div id="equip-avali"></div>
						<!-- <div id="sliders">
							<table>
								<tr><td>垂直旋转 角度</td><td><input id="R0" type="range" min="0" max="100" value="0"/> <span id="R0-value" class="value"></span></td></tr>
							    <tr><td>水平旋转 角度</td><td><input id="R1" type="range" min="0" max="100" value="0"/> <span id="R1-value" class="value"></span></td></tr>
							</table>
						</div> -->
						<!-- EA 完成率表格 -->
						<button id="export" data-placement="bottom" title="导出到Excel" data-title="Export to Excel" rel="tooltip" class="btn btn-icon" data-original-title=""><i class=" icon-download-alt"></i></button>
						<div id="equip-avali-div" class="table-responsive">
							<div class="input-append flR pull-right">
											<input type="text" class="input-medium search-query" data-filter-table="equip-avali-table"><button class="btn btn-icon"><i class="icon-search"></i></button>
										</div>
							<table id="equip-avali-table" class="table table-bordered table-striped tablebrdr mrgzero width100p" data-table-name="equip-avali-table"></table>
						</div>
						<br><br>
					</div>
				</section>
			</div>
		</div>

</body>
</html>