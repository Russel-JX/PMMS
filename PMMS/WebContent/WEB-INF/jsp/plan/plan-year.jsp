<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String contextPath = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PMMS - 保养计划</title>
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
	
	<link href="<%=contextPath%>/css/app/custom.css" rel="stylesheet" type="text/css">
	
	<script src="<%=contextPath%>/js/components/requirejs/require.js"></script>
	<script src="<%=contextPath%>/js/iids/require.config.js" ></script>
	<script src="<%=contextPath%>/js/plan-year.js" ></script>
	<script src="<%=contextPath%>/js/ajaxReqLoading.js" ></script>
	
	<style type="text/css">
		.well{
			margin-bottom:10px;
			padding:5px;
		}
	</style>
	
  </head>
  <body>
     <div class="navbar navbar-static-top">
		<jsp:include page="../common/Header.jsp"></jsp:include>
	</div>   

	<div class="container">
		<div class="page-header">
			<div>
				<h1 class="voice voice-brand pull-left">保养计划</h1>
			</div>
		</div>
		<div class="row ui-sortable">
	        <div class="span12">            
				<section class="module">
					<div class="tabbable" id="workflowtab">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tab003" id="tab003Nav" data-toggle="tab">本年保养计划</a></li>
								<li><a href="#tab004" id="tab004Nav" data-toggle="tab">月度保养计划</a></li>
						  	</ul>
						  	<div class="tab-content">							
								<div class="tab-pane active" id="tab003">
									<div class="module-body">
										<div class="btn-group pull-left">
											<div class="pull-left">
												      <label class="col-sm-2 control-label pull-left" for="maintYear"> 年份/MaintYear： </label>
												      <select class="span1" id="maintYear">
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
												</div>
												<div class="span1"></div>
												<div class="pull-left">
													  <label class="col-sm-2 control-label pull-left" for="equipType"> 设备类型/EQType： </label>
												      <select class="span2" id="equipType">
												      		<option value=""> ---所有类型--- </option>
															<option value="1">生产设备</option>
															<option value="2">特种设备</option>
															<option value="3">设施</option>
														</select>
												</div>
												<div style="clear:both;"></div>
												<div class="btn-group">
										<%if(((String)session.getAttribute("modAccess2")).equalsIgnoreCase("RW")){ %>
												<button class="btn btn-primary pull-left " type="submit"  id="btn-add-plan">新增/Add</button>
												<button class="btn btn-primary pull-left" type="submit"  id="btn-modify-plan">编辑/Edit</button>
												<button class="btn btn-primary pull-left" type="submit"  id="btn-delete-plan">删除/Delete</button>
											<%} %>
											<a id="export" data-placement="bottom" title="导出到Excel" style="margin-left:100px;" data-title="Export to Excel" rel="tooltip" class="btn btn-icon" href="#" data-original-title=""><i class=" icon-download-alt"></i></a>
											<a id="planViewHelp" class="btn btn-primary" href="#"  style="margin-left:50px;" rel="popover" data-content="popover content" data-original-title="计划查看说明"><i class="icon-question-sign"></i></a>
										</div>
											
										</div>
										
										<div id="plan-year-div" class="table-responsive">
											<div class="input-append flR pull-right">
															<input type="text" class="input-medium search-query" data-filter-table="plan-year-table"><button class="btn btn-icon"><i class="icon-search"></i></button>
														</div>
											<table id="plan-year-table" class="table table-bordered table-striped tablebrdr mrgzero width100p" data-table-name="plan-year-table"></table>
										</div>
									</div>
								</div>
								<div class="tab-pane" id="tab004">
									<div class="module-body">
										<div class="btn-group pull-left">
											<a id="exportMonth" data-placement="bottom" title="导出到Excel" data-title="Export to Excel" rel="tooltip" class="btn btn-icon" href="#" data-original-title=""><i class=" icon-download-alt"></i></a>
												<!-- <button class="btn btn-primary pull-left " type="submit"  id="btn-add-role" data-toggle="modal"  data-target="#modal-add-plan">增加计划</button>
												<button class="btn btn-primary pull-left btn-warning" type="submit"  id="btn-edit-role" data-toggle="modal"  data-target="#modal-modify-plan">编辑计划</button>
												<button class="btn btn-primary pull-left  btn-danger" type="submit"  id="deleteButton" data-toggle="modal"  data-target="#modal-delete-plan">删除计划</button> -->
										</div>
										<div class="input-append flR pull-right">
														<input type="text" class="input-medium search-query" data-filter-table="plan-month-table"><button class="btn btn-icon"><i class="icon-search"></i></button>
													</div>
										<a id="monthPlanViewHelp" class="btn btn-primary pull-right" href="#"  rel="popover" data-content="popover content" data-original-title="计划查看说明"><i class="icon-question-sign"></i></a>
										<!-- 月度计划 -->
										<table id="plan-month-table" class="table table-bordered table-striped tablebrdr mrgzero width100p" data-table-name="plan-month-table"></table>
									</div>	
								</div>
							</div>
					</div>
				</section>
			</div>
		</div>
	</div>
	
	<!-- 增加|修改计划modal	开始 -->
	<div id="modal-add-plan" class="modal modal4 show fade" style="display: none;"><!-- hide -->
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
		<h3 id="modal-add-plan-title">增加计划</h3>
	</div>
	<div class="modal-body">
		<div class="module-body">
			<form style="margin-bottom:0;" class="form-horizontal">	
				<div class="well">	
					<div class="page-header">
						<div>
							<h3 class="voice voice-brand pull-left">第一步：选择设备</h3><h4 class="alert alert-error equipMsg">SP301设备不存在</h4><!-- label label-important -->
						</div>
					</div>			
					<fieldset>
					<!-- left -->
					<input type="hidden" id="hiddenId" value="" />
					<div class="sec-lft pull-left" style="width:50%">
						<div class="control-group">
									<label class="control-label span2" for="equipId" ><b>设备编号/EQNO.:</b></label>
									<div class="controls" style="margin-left:110px;">
										<input id="equipId" type="text" placeholder="请输入设备编号" class="span3">
										<input id="validEquipId" type="hidden" value="NO">
									</div>
								</div>
						<div class="control-group">
							<label class="control-label  span2" for="equipTypeModal"><b>设备类型/EQType:</b></label>
							<div class="controls" style="margin-left:110px;">
										<input id="equipTypeModal" type="text" placeholder="" class="span3" disabled ><!-- 设备有效！ -->
									</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="remark"><b>备注/Remark:</b></label>
							<div class="controls" style="margin-left:110px;">
								<input id="remark" type="text" placeholder="请输入备注（可选）" class="span3">
							</div>
						</div>
						
					</div>
					<!-- right -->
					<div class="sec-rht pull-right" style="width:50%">
						<div class="control-group">
							<label class="control-label span2" for="equipName"><b>设备名称/EQName:</b></label>
							<div class="controls" style="margin-left:110px;">
								<input id="equipName" type="text" placeholder="" class="span3" disabled>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label span2" for="equipModal"><b>设备型号/EQModel:</b></label>
							<div class="controls" style="margin-left:110px;">
								<input id="equipModal" type="text" placeholder="" class="span3" disabled>
							</div>
						</div>
						
					</div>
					</fieldset>
					</div>
					<!-- <div class="well">	
						<div class="page-header">
							<div>
								<h3 class="voice voice-brand pull-left">第二步：选择计划类型</h3>
							</div>
						</div>			
						<fieldset>
							<div class="sec-lft pull-left" style="width:50%">
								<div id="planTypeSelDiv" class="control-group">
									<label class="control-label" for="plan_type" style="width:100px;"><b>计划类型:</b></label>
									<div class="controls" style="margin-left:110px;">
										<select id="plan_type">
											<option value="Y">年度计划 - Y</option>
											<option value="Q">季度计划 - Q</option>
											<option value="M">月计划 - M</option>
										</select>
									</div>
								</div>
								<div id="planTypeInputDiv" class="control-group">
									<label class="control-label" for="plan_type_input" style="width:100px;"><b>计划类型:</b></label>
									<div class="controls" style="margin-left:110px;">
										<input id="plan_type_input" type="text" placeholder="" class="span3" disabled>
									</div>
								</div>
							</div>
					</fieldset>
				</div> -->
				
				<div class="well">	
					<div class="page-header">
						<div>
							<h3 class="voice voice-brand pull-left">第二步：选择保养月份</h3>
						</div>
					</div>
					<a id="planHelp" class="btn btn-primary pull-right" href="#" rel="popover" data-content="popover content" data-original-title="新增|修改计划说明"><i class="icon-question-sign"></i></a>			
					<fieldset>
							<div id="plan-table_wrapper"
								class="dataTables_wrapper form-inline" role="grid">
								<table id="maintItem-table"
									class="table table-bordered table-striped tablebrdr mrgzero width100p dataTable"
									data-table-name="plan-table" aria-describedby="plan-table_info">

									<thead>
										<tr role="row">
											<th class="sorting" tabindex="0" rowspan="1" colspan="1"
												aria-label="1: activate to sort column ascending"
												style="width: 30px;">1 月份</th>
											<th class="sorting" tabindex="0" rowspan="1" colspan="1"
												aria-label="2: activate to sort column ascending"
												style="width: 30px;">2 月份</th>
											<th class="sorting" tabindex="0" rowspan="1" colspan="1"
												aria-label="3: activate to sort column ascending"
												style="width: 30px;">3 月份</th>
											<th class="sorting" tabindex="0" rowspan="1" colspan="1"
												aria-label="4: activate to sort column ascending"
												style="width: 30px;">4 月份</th>
											<th class="sorting" tabindex="0" rowspan="1" colspan="1"
												aria-label="5: activate to sort column ascending"
												style="width: 30px;">5 月份</th>
											<th class="sorting" tabindex="0" rowspan="1" colspan="1"
												aria-label="6: activate to sort column ascending"
												style="width: 30px;">6 月份</th>
											<th class="sorting" tabindex="0" rowspan="1" colspan="1"
												aria-label="7: activate to sort column ascending"
												style="width: 30px;">7 月份</th>
											<th class="sorting" tabindex="0" rowspan="1" colspan="1"
												aria-label="8: activate to sort column ascending"
												style="width: 30px;">8 月份</th>
											<th class="sorting" tabindex="0" rowspan="1" colspan="1"
												aria-label="9: activate to sort column ascending"
												style="width: 30px;">9 月份</th>
											<th class="sorting" tabindex="0" rowspan="1" colspan="1"
												aria-label="10: activate to sort column ascending"
												style="width: 30px;">10 月份</th>
											<th class="sorting" tabindex="0" rowspan="1" colspan="1"
												aria-label="11: activate to sort column ascending"
												style="width: 30px;">11 月份</th>
											<th class="sorting" tabindex="0" rowspan="1" colspan="1"
												aria-label="12: activate to sort column ascending"
												style="width: 30px;">12 月份</th>
										</tr>
									</thead>
									<tbody role="alert" aria-live="polite" aria-relevant="all">
										<tr class="odd maint-month">
											<td>
												<div class="pull-left"><input type="checkbox" id="year-1"><br><label for="year-1">年</label></div>
												<div class="pull-left"><input type="checkbox" id="quarter-1" class="quarter1"><br><label for="quarter-1">季</label></div>
												<div class="pull-left"><input type="checkbox" id="month-1"><br><label for="month-1">月</label></div>
											</td>
											<td>
												<div class="pull-left"><input type="checkbox" name="year-1"><br><label for="year">年</label></div>
												<div class="pull-left"><input type="checkbox" name="quarter-1" class="quarter1"><br><label for="quarter">季</label></div>
												<div class="pull-left"><input type="checkbox" name="month-1"><br><label for="month">月</label></div>
											</td>
											<td>
												<div class="pull-left"><input type="checkbox" name="year-1"><br><label for="year">年</label></div>
												<div class="pull-left"><input type="checkbox" name="quarter-1" class="quarter1"><br><label for="quarter">季</label></div>
												<div class="pull-left"><input type="checkbox" name="month-1"><br><label for="month">月</label></div>
											</td>
											<td>
												<div class="pull-left"><input type="checkbox" name="year-1"><br><label for="year">年</label></div>
												<div class="pull-left"><input type="checkbox" name="quarter-1" class="quarter2"><br><label for="quarter">季</label></div>
												<div class="pull-left"><input type="checkbox" name="month-1"><br><label for="month">月</label></div>
											</td>
											<td>
												<div class="pull-left"><input type="checkbox" name="year-1"><br><label for="year">年</label></div>
												<div class="pull-left"><input type="checkbox" name="quarter-1" class="quarter2"><br><label for="quarter">季</label></div>
												<div class="pull-left"><input type="checkbox" name="month-1"><br><label for="month">月</label></div>
											</td>
											<td>
												<div class="pull-left"><input type="checkbox" name="year-1"><br><label for="year">年</label></div>
												<div class="pull-left"><input type="checkbox" name="quarter-1" class="quarter2"><br><label for="quarter">季</label></div>
												<div class="pull-left"><input type="checkbox" name="month-1"><br><label for="month">月</label></div>
											</td>
											<td>
												<div class="pull-left"><input type="checkbox" name="year-1"><br><label for="year">年</label></div>
												<div class="pull-left"><input type="checkbox" name="quarter-1" class="quarter3"><br><label for="quarter">季</label></div>
												<div class="pull-left"><input type="checkbox" name="month-1"><br><label for="month">月</label></div>
											</td>
											<td>
												<div class="pull-left"><input type="checkbox" name="year-1"><br><label for="year">年</label></div>
												<div class="pull-left"><input type="checkbox" name="quarter-1" class="quarter3"><br><label for="quarter">季</label></div>
												<div class="pull-left"><input type="checkbox" name="month-1"><br><label for="month">月</label></div>
											</td>
											<td>
												<div class="pull-left"><input type="checkbox" name="year-1"><br><label for="year">年</label></div>
												<div class="pull-left"><input type="checkbox" name="quarter-1" class="quarter3"><br><label for="quarter">季</label></div>
												<div class="pull-left"><input type="checkbox" name="month-1"><br><label for="month">月</label></div>
											</td>
											<td>
												<div class="pull-left"><input type="checkbox" name="year-1"><br><label for="year">年</label></div>
												<div class="pull-left"><input type="checkbox" name="quarter-1" class="quarter4"><br><label for="quarter">季</label></div>
												<div class="pull-left"><input type="checkbox" name="month-1"><br><label for="month">月</label></div>
											</td>
											<td>
												<div class="pull-left"><input type="checkbox" name="year-1"><br><label for="year">年</label></div>
												<div class="pull-left"><input type="checkbox" name="quarter-1" class="quarter4"><br><label for="quarter">季</label></div>
												<div class="pull-left"><input type="checkbox" name="month-1"><br><label for="month">月</label></div>
											</td>
											<td>
												<div class="pull-left"><input type="checkbox" name="year-1"><br><label for="year">年</label></div>
												<div class="pull-left"><input type="checkbox" name="quarter-1" class="quarter4"><br><label for="quarter">季</label></div>
												<div class="pull-left"><input type="checkbox" name="month-1"><br><label for="month">月</label></div>
											</td>
										</tr>
									</tbody>
								</table>
								<!--  
								<div class="btn-group btn-datatables-floater"
									style="display: block; top: 59px; left: 119px;">
									dddd
									<button class="btn dropdown-toggle btn-mini"
										data-toggle="dropdown">
										<i class="icon-chevron-down"></i>
									</button>
									<ul
										class="dropdown-menu dropdown-datatables-floater pull-right">
										<li><a href="#">Show Only</a></li>
										<li><a href="#">Hide</a></li>
										<li><a href="#">Highlight</a></li>
										<li class="divider"></li>
										<li><a href="#">Share this</a></li>
									</ul>
								</div>
								-->
							</div>
					</fieldset>
				</div>
			</form>
		</div>

	</div>
 	<div class="modal-footer">
 		<!-- <button href="#" class="btn btn-primary" id = "">上一步</button>
		<button href="#" class="btn btn-primary" id = "">下一步</button> -->
		<button href="#" class="btn btn-primary" id="plan-add-confirm">保存/Save</button>
		<button href="#" class="btn btn-primary" id="plan-modify-confirm">保存/Save</button>
		<a href="#" class="btn" data-dismiss="modal">取消/Cancel</a>
	</div>
	</div>
	<!-- 增加|修改计划modal	结束 -->
	
	
	<!-- 删除计划modal	开始 -->
	<div id="modal-delete-plan" class="modal modal3 hide fade" style="display: none;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
		<h3>删除计划</h3>
	</div>
	<div class="modal-body">
		<div class="module-body">
			<form style="margin-bottom:0;" class="form-horizontal">	
				<div class="well">	
				<div class="page-header">
					<div>
						<h3 id="deleteConfirmText" class="voice voice-brand pull-left">确定删除编号是 sp001 的设备吗？</h3>
					</div>
				</div>			
				</div>
			</form>
		</div>

	</div>
 	<div class="modal-footer">
		<button href="#" class="btn btn-primary" id="btn-remove-confirm">确定/OK</button>
		<a href="#" class="btn" data-dismiss="modal">取消/Cancel</a>
	</div>
	</div>
	<!-- 删除计划modal	结束 -->
	
	<!-- 删除计划失败modal	开始 -->
	<div id="modal-delete-plan2" class="modal modal3 hide fade" style="display: none;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
		<h3>删除计划失败提示</h3>
	</div>
	<div class="modal-body">
		<div class="module-body">
			<form style="margin-bottom:0;" class="form-horizontal">	
				<div class="well">	
				<div class="page-header">
					<div>
						<h3 class="voice voice-brand pull-left">此计划已经执行，不能删除！</h3>
					</div>
				</div>			
				</div>
			</form>
		</div>
	</div>
 	<div class="modal-footer">
		<button href="#" class="btn btn-primary" id = "">确定/OK</button>
		<a href="#" class="btn" data-dismiss="modal">取消/Cancel</a>
	</div>
	</div>
	<!-- 删除计划失败modal	结束 -->
	
	<!-- 删除计划成功modal	开始 -->
	<div id="modal-delete-plan3" class="modal modal3 hide fade" style="display: none;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
		<h3>删除计划成功提示</h3>
	</div>
	<div class="modal-body">
		<div class="module-body">
			<form style="margin-bottom:0;" class="form-horizontal">	
				<div class="well">	
				<div class="page-header">
					<div>
						<h3 class="voice voice-brand pull-left">成功删除编号为 SP001 的设备成功！</h3>
					</div>
				</div>			
				</div>
			</form>
		</div>
	</div>
 	<div class="modal-footer">
		<button href="#" class="btn btn-primary" id = "">确定/OK</button>
		<a href="#" class="btn" data-dismiss="modal">取消/Cancel</a>
	</div>
	</div>
	<!-- 删除计划成功modal	结束 -->
	
	<!-- 查看计划的保养内容modal	开始 -->
	<div id="modal-plan-maintItem-view" class="modal modal3 hide fade" style="display: none;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
		<h3>计划保养内容</h3>
	</div>
	<div class="modal-body">
		<div class="module-body">
			<form style="margin-bottom:0;" class="form-horizontal">	
				<div class="page-header">
					<div>
						<h3 class="voice voice-brand pull-left" id="modal-maint-item-title">SP301设备</h3>
					</div>
				</div>
				<a id="exportMaintItem" data-placement="bottom" title="导出到Excel" data-title="Export to Excel" rel="tooltip" class="btn btn-icon" href="#" data-original-title=""><i class=" icon-download-alt"></i></a>
					<div id="maint-item-table_wrapper"
						class="dataTables_wrapper form-inline" role="grid">
						<table id="maint-item-table"
							class="table table-bordered table-striped tablebrdr mrgzero width100p dataTable"
							data-table-name="plan-table"
							aria-describedby="maint-item-table_info">
							<thead>
								<tr role="row">
									<th class="sorting" tabindex="0" rowspan="1" colspan="1"
										aria-label="序号: activate to sort column ascending"
										style="width: 136px;">序号<br>/NO.</th>
									<th class="sorting" tabindex="0" rowspan="1" colspan="1"
										aria-label="保养内容: activate to sort column ascending"
										style="width: 211px;">保养内容<br>/MaintItem</th>
									<th class="sorting" tabindex="0" rowspan="1" colspan="1"
										aria-label="周期: activate to sort column ascending"
										style="width: 136px;">周期<br>/Cycle</th>
									<th class="sorting" tabindex="0" rowspan="1" colspan="1"
										aria-label="方法: activate to sort column ascending"
										style="width: 137px;">方法<br>/Way</th>
									<th class="sorting" tabindex="0" rowspan="1" colspan="1"
										aria-label="正常状态: activate to sort column ascending"
										style="width: 213px;">正常状态<br>/Standard</th>
								</tr>
							</thead>
							<tbody role="alert" aria-live="polite" aria-relevant="all" class="maint-item-view">
							</tbody>
						</table>
						<div style="clear:both;">方法代码: 1（清洁）, 2（检查）, 3（补充调整）, 4（修理）, 5（更换）, 6（润滑）</div>
					</div>
				</form>
		</div>
	</div>
 	<div class="modal-footer">
		<!--  
		<button href="#" class="btn btn-primary" id = "">确定/OK</button>
		-->
		<a href="#" class="btn btn-primary" data-dismiss="modal">关闭/Close</a>
	</div>
	</div>
	<!-- 查看计划的保养内容modal	结束 -->

</body>
</html>