<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String contextPath = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PMMS - 保养内容</title>
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
	<script src="<%=contextPath%>/js/maint-item.js" ></script>
	<script src="<%=contextPath%>/js/ajaxReqLoading.js" ></script>
	
  </head>
  <body>
	 <div class="navbar navbar-static-top">
		<jsp:include page="../common/Header.jsp"></jsp:include>
	</div>   

	<div class="container">
		<div class="page-header">
			<div>
				<h1 class="voice voice-brand pull-left">设备保养内容</h1>
			</div>
		</div>
		<div class="row ui-sortable">
	        <div class="span12">            
					<div class="module-body">
						<!-- <form action='/PMMS/restful/maintItem/export' method="get"> -->
						<div class="btn-group pull-left">
							<div class="pull-left">
										  <label class="col-sm-2 control-label pull-left" for="equipType"> 设备类型/EQType： </label>
									      <select class="span2 equipType" id="equipType">
												<option value="1">生产设备</option>
												<option value="2">特种设备</option>
												<option value="3">设施</option>
											</select>
									</div>
							<div class="span1"></div>
							<div class="pull-left">
								      <label class="col-sm-2 control-label pull-left" for="equipName"> 设备名称/EQName： </label>
								      <select class="span3 equipName" id="equipName">
											<option>无数据...</option>
										</select>
								</div>
							<div style="clear:both;"></div>
							<button id="export" type="submit" data-placement="bottom" title="导出到Excel" data-title="Export to Excel" rel="tooltip" class="btn btn-icon" href="#" data-original-title=""><i class="icon-download-alt"></i></button>
							<%if(((String)session.getAttribute("modAccess2")).equalsIgnoreCase("RW")){ %>
								<button class="btn btn-primary pull-left " type="submit"  id="btn-add" data-toggle="modal"  data-target="#modal-add-maintItem">增加保养内容/Add</button>
								<button class="btn btn-primary pull-left" type="submit"  id="btn-modify">编辑保养内容/Edit</button>
								<button class="btn btn-primary pull-left" type="submit"  id="btn-delete" data-toggle="modal"  data-target="#modal-delete-maintItem">删除保养内容/Delete</button>
							<%} %>	
							<a id="cycleHelp" class="btn btn-primary pull-right" href="#"  rel="popover" data-content="popover content" data-original-title="保养周期说明"><i class="icon-question-sign"></i></a>
						</div>
						<!-- </form> -->
						
						<div id="maint-item-div" class="table-responsive">
							<div class="input-append flR pull-right">
										<input type="text" class="input-medium search-query" data-filter-table="plan-table"><button class="btn btn-icon"><i class="icon-search"></i></button>
									</div>
							<table id="maint-item-table" class="table table-bordered table-striped tablebrdr mrgzero width100p" data-table-name="plan-table">
							</table>
						</div>
						
						
						
						<br>
						<div style="clear:both;">方法代码: 1（清洁）, 2（检查）, 3（补充调整）, 4（修理）, 5（更换）, 6（润滑）</div>
					</div>
			</div>
		</div>
	</div>
	
	<!-- 增加保养内容modal	开始 -->
	<div id="modal-add-maintItem" class="modal modal3 hide fade" style="display: none;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
		<h3 id="modal-add-maintItem-title">增加保养内容</h3>
	</div>
	<div class="modal-body">
		<div class="module-body">
			<form style="margin-bottom:0;" class="form-horizontal">	
				<div class="well">	
				<div class="page-header">
					<div>
						<h3 class="voice voice-brand pull-left"></h3>
					</div>
				</div>			
				<fieldset>
				<!-- left -->
				<input type="hidden" id="hiddenId" value="" />
				<div class="sec-lft pull-left" style="width:50%">
					<div class="control-group">
						<label class="control-label" for="equipType-modal"><b>设备类型/EQType:</b></label>
						 <select class="span3 equipType equipType-modal" id="equipType-modal">
												<option value="1">生产设备</option>
												<option value="2">特种设备</option>
												<option value="3">设施</option>
											</select>
					</div>
					<div class="control-group">
						<label class="control-label" for="item" ><b>内容/MaintItem:</b></label>
						<div class="controls" >
							<input id="item" type="text" placeholder="请输入保养内容" class="span3">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="standard" ><b>正常状态/Standard:</b></label>
						<div class="controls" >
							<input id="standard" type="text" placeholder="请输入设备保养后的正常状态" class="span3">
						</div>
					</div>
				
					<!-- <div class="control-group">
						<label class="control-label" for="input01" ><b>方法:</b></label>
						<div class="controls" >
							<input id="contract-no" type="text" placeholder="请输入保养方法代码" class="span3">
						</div>
					</div> -->
					<div class="control-group">
						<!-- <label class="control-label" for="way" ><b>保养方法:</b></label>
						<select id="way" multiple="multiple">
							<option value="1"> 1. 清洁 </option>
							<option value="2"> 2. 检查 </option>
							<option value="3"> 3. 补充调整 </option>
							<option value="4"> 4. 修理 </option>
							<option value="5"> 5. 更换 </option>
							<option value="6"> 6. 润滑 </option>
						</select> -->
						<div class="control-label">
								<label>
							      	<b>保养方法/Way:</b>
							    </label>
						</div><br><div style="clear:both;"></div>
						<div class="span1 remove-margin-bottom control-label">
							<label>
								<input type="checkbox" id="item1" name="1" class="wayClass"> 1.清洁
							</label>
						</div>
						<div class="span1 remove-margin-bottom control-label">
							<label>
								<input type="checkbox" id="item2" name="2" class="wayClass"> 2.检查
							</label>
						</div>
						<div class="span1 remove-margin-bottom control-label">
							<label>
								<input type="checkbox" id="item3" name="3" class="wayClass"> 3.补充调整
							</label>
						</div>
						<div class="span1 remove-margin-bottom control-label">
							<label>
								<input type="checkbox" id="item4" name="4" class="wayClass"> 4.修理 
							</label>
						</div>
						<div class="span1 remove-margin-bottom control-label">
							<label>
								<input type="checkbox" id="item5" name="5" class="wayClass"> 5.更换
							</label>
						</div>
						<div class="span1 remove-margin-bottom control-label">
							<label>
								<input type="checkbox" id="item6" name="6" class="wayClass"> 6.润滑
							</label>
						</div>
					</div>
				</div>
				<!-- right -->
				<div class="sec-rht pull-right" style="width:50%">
					<div class="control-group">
						<label class="control-label" for="equipName-modal" ><b>设备名称/EQName:</b></label>
						 <select class="span3 equipName equipName-modal" id="equipName-modal">
											<option value="2">数控冲床</option>
											<option value="3">激光切割机</option>
											<option value="4">电瓶堆垛车</option>
											<option value="5">ATE</option>
											<option value="6">焊接机器人</option>
										</select>
					</div>
					<div class="control-group">
						<label class="control-label" for="cycle" ><b>保养周期/Cycle:</b></label>
						<div class="controls" >
							<div class="pull-left"><input type="checkbox" id="cycle-year" name="Y" class="cycle" value="Y"><label class="pull-left" for="cycle-year">年保养-Y</label></div>
							<div class="pull-left"><input type="checkbox" id="cycle-quarter" name="Q" class="cycle" value="Q"><label class="pull-left" for="cycle-quarter">季保养-Q</label></div>
							<div class="pull-left"><input type="checkbox" id="cycle-month" name="M" class="cycle" value="M"><label class="pull-left" for="cycle-month">月保养-M</label></div>
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="remark" ><b>备注/Remark:</b></label>
						<div class="controls" >
							<input id="remark" type="text" placeholder="请输入备注（可选）" class="span3">
						</div>
					</div>
				</div>
				</fieldset>
				</div>
			</form>
		</div>

	</div>
 	<div class="modal-footer">
		<button href="#" class="btn btn-primary" id="btn-add-confirm">确定/Add</button>
		<button href="#" class="btn btn-primary" id="btn-modify-confirm">确定/Modify</button>
		<a href="#" class="btn" data-dismiss="modal">取消/Cancel</a>
	</div>
	</div>
	<!-- 增加保养内容modal	结束 -->
	
	<!-- 删除保养内容modal	开始 -->
	<div id="modal-delete-maintItem" class="modal modal3 hide fade" style="display: none;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
			<h3>删除保养内容</h3>
		</div>
		<div class="modal-body">
			<div class="module-body">
				<form style="margin-bottom:0;" class="form-horizontal">	
					<div class="well">	
					<div class="page-header">
						<div>
							<h3 class="voice voice-brand pull-left text-danger">确定删除本条保养内容吗<br>/Are you sure to delete？</h3>
						</div>
					</div>			
					</div>
				</form>
			</div>
	
		</div>
	 	<div class="modal-footer">
			<button href="#" class="btn btn-primary" id = "btn-remove-confirm">确定/OK</button>
			<a href="#" class="btn" data-dismiss="modal">取消/Cancel</a>
		</div>
	</div>
	<!-- 删除保养内容modal	结束 -->
	
	<!-- 删除保养内容失败modal	开始 -->
	<div id="modal-delete-maintItem4" class="modal modal3 hide fade" style="display: none;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
		<h3>删除保养内容失败提示</h3>
	</div>
	<div class="modal-body">
		<div class="module-body">
			<form style="margin-bottom:0;" class="form-horizontal">	
				<div class="well">	
				<div class="page-header">
					<div>
						<h3 class="voice voice-brand pull-left">删除失败，请刷新重试！</h3>
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
	<!-- 删除保养内容失败modal	结束 -->
	
	<!-- 删除计划成功modal	开始 -->
	<div id="modal-delete-maintItem5" class="modal modal3 hide fade" style="display: none;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
		<h3>删除保养内容成功提示</h3>
	</div>
	<div class="modal-body">
		<div class="module-body">
			<form style="margin-bottom:0;" class="form-horizontal">	
				<div class="well">	
				<div class="page-header">
					<div>
						<h3 class="voice voice-brand pull-left">删除成功！</h3>
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

</body>
</html>