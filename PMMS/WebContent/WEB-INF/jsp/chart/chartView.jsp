<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <% String contextPath = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PMMS - 分析与报表</title>
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
	<script src="<%=contextPath%>/js/chartView.js" ></script>
	
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
				<a href="/PMMS/html/MaintItem/maintItem.html"><button class="btn btn-large btn-primary pull-right"  id="btn-show-maintItem">按年份统计</button></a>
				<div> </div><div> </div><div> </div>
			</div>
			<!-- <div>
				<a href="/PMMS/spChart.html"><button class="btn btn-large btn-primary pull-right"  id="btn-show-maintItem">维修配件费用统计</button></a>
			</div> -->
		</div>
		<form class="" role="form">
		<div class="well">
			<div class="form-group">
				<div>
			   		<label class="col-sm-2 control-label pull-left" for="startDate">请输入起始日期：</label>
			   		<div class="col-sm-3 pull-left"  id="datepicker1">
			     		<input type="text" class="form-control" id="startDate" placeholder="请输入起始日期" >
			      	</div>
			      	<label class="col-sm-2 control-label pull-left" for="endDate"> 请输入结束日期： </label>
			      	<div class="col-sm-3 pull-left"  id="datepicker1">
		      			<input type="text" class="form-control" id="endDate" placeholder="请输入结束日期" >
		      		</div>
		      		<label class="col-sm-2 control-label pull-left" for="endDate"> 请输入设备编号： </label>
			      	<div class="col-sm-3 pull-left"  id="equip-id">
		      			<input type="text" class="form-control"  placeholder="要查询单台设备，请输入设备编号" >
		      		</div>
		   		</div>
		   </div>
		   <button type="submit" class="btn btn-primary"> 查询</button><br>
		</div>
		</form>
		
	</div>


	<!-- 日期选择区域			结束 -->

	<!-- 图表展示区域		开始 -->
	<div class="container">
		<div class="row ui-sortable">
	        <div class="span12">            
				<section class="module">
					<div class="module-body">
						<!-- PM 完成率图表 -->
						<div id="pm-completion"></div>
						<!-- <div id="sliders">
							<table>
								<tr><td>垂直旋转 角度</td><td><input id="R0" type="range" min="0" max="100" value="0"/> <span id="R0-value" class="value"></span></td></tr>
							    <tr><td>水平旋转 角度</td><td><input id="R1" type="range" min="0" max="100" value="0"/> <span id="R1-value" class="value"></span></td></tr>
							</table>
						</div> -->
						<!-- PM 完成率表格 -->
						<div class="input-append flR pull-right">
							<input type="text" class="input-medium search-query" data-filter-table="plan-table"><button class="btn btn-icon"><i class="icon-search"></i></button>
						</div>
						<a data-placement="bottom" title="导出到Excel" data-title="Export to Excel" rel="tooltip" class="btn btn-icon" href="#" data-original-title=""><i class=" icon-download-alt"></i></a>
						<table id="pm-table" class="table table-bordered table-striped tablebrdr mrgzero width100p" data-table-name="plan-table"></table>
						<br><br>
						
						<!-- PM 完成率表格（单台设备） -->
						<div class="input-append flR pull-right">
							<input type="text" class="input-medium search-query" data-filter-table="pm-table-single"><button class="btn btn-icon"><i class="icon-search"></i></button>
						</div>
						<a data-placement="bottom" title="导出到Excel" data-title="Export to Excel" rel="tooltip" class="btn btn-icon" href="#" data-original-title=""><i class=" icon-download-alt"></i></a>
						<table id="pm-table-single" class="table table-bordered table-striped tablebrdr mrgzero width100p" data-table-name="pm-table-single"></table>
					</div>
				</section>
			</div>
		</div>
		
		
		<br>
		<!-- 可利用率 -->
		<div id="equip-avali" style="min-width:800px;height:400px; margin: 0 auto;"></div>
		<br><br>
		<!-- 维修间隔时间 -->
		<div id="maint-gap" style="min-width:800px;height:400px; margin: 0 auto;"></div>
		<br><br>
		<!-- 平均维修时间 -->
		<div id="maint-consuming"></div>
		<br><br>
		<!-- 维修配件费用 -->
		<div id="maint-cost"></div>
		<br><br>
		<!-- 维修配件使用数量 -->
		<div id="maint-quantity"></div>
		<br><br>
		<!-- 按年份统计 -->
		<div id="maint-byYear"></div>
		<br><br>
		<!-- <h3>3D 图表</h3>
		3D 柱状图
		<div id="3d-column"></div>
		<div id="sliders">
			<table>
				<tr><td>垂直旋转 角度</td><td><input id="R0" type="range" min="0" max="100" value="0"/> <span id="R0-value" class="value"></span></td></tr>
			    <tr><td>水平旋转 角度</td><td><input id="R1" type="range" min="0" max="100" value="0"/> <span id="R1-value" class="value"></span></td></tr>
			</table>
		</div>
		<br><br> -->
		<!-- 3D 饼图 -->
		<!-- <div id="3d-pie" style="min-width:800px;height: 400px;"></div> -->
	</div>
	<!-- 图表展示区域		结束 -->
	
	<!-- <div class="container">
		<div class="row ui-sortable">
	        <div class="span12">            
				<section class="module">
					<div class="module-body">
						可利用率
						<div id="equip-avali" style="min-width:800px;height:400px; margin: 0 auto;"></div>
					</div>
				</section>
			</div>
		</div>
	</div> -->

</body>
</html>