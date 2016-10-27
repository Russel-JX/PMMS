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

	<script src="<%=contextPath%>/js/components/requirejs/require.js"></script>
	<script src="<%=contextPath%>/js/iids/require.config.js" ></script>
	<script src="<%=contextPath%>/js/maint-item.js" ></script>
	
	
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
				<section class="module">
					<div class="module-body">
						<form action='/PMMS/restful/maintItem/export' method="get">
						<div class="btn-group pull-left">
							<div class="pull-left">
										  <label class="col-sm-2 control-label pull-left" for="equipType"> 请选择设备类型： </label>
									      <select class="span2 equipType" id="equipType">
												<option value="1">生产设备</option>
												<option value="2">特种设备</option>
												<option value="3">设施</option>
											</select>
									</div>
							<div class="span1"></div>
							<div class="pull-left">
								      <label class="col-sm-2 control-label pull-left" for="equipName"> 请选择设备名称： </label>
								      <select class="span3 equipName" id="equipName">
											<option>xx数控冲床</option>
											<option>激光切割机</option>
											<option>电瓶堆垛车</option>
											<option>ATE</option>
											<option>焊接机器人</option>
										</select>
								</div>
									
							<div style="clear:both;"></div>
							<button type="submit" data-placement="bottom" title="导出到Excel" data-title="Export to Excel" rel="tooltip" class="btn btn-icon" href="#" data-original-title=""><i class="icon-download-alt"></i></button>
								<button class="btn btn-primary pull-left " type="submit"  id="btn-add" data-toggle="modal"  data-target="#modal-add-maintItem">增加保养内容</button>
								<button class="btn btn-primary pull-left" type="submit"  id="btn-modify">编辑保养内容</button>
								<button class="btn btn-primary pull-left" type="submit"  id="btn-delete" data-toggle="modal"  data-target="#modal-delete-maintItem">删除保养内容</button>
								
						</div>
						</form>
						
						<div id="maint-item-div" class="table-responsive">
							<div class="input-append flR pull-right">
										<input type="text" class="input-medium search-query" data-filter-table="plan-table"><button class="btn btn-icon"><i class="icon-search"></i></button>
									</div>
							<table id="maint-item-table" class="table table-bordered table-striped tablebrdr mrgzero width100p" data-table-name="plan-table">
							</table>
						</div>
						
						
						
						<br>
						<div style="clear:both;">方法代码: （清洁）1, （检查）2, （补充调整）3, （修理）4, （更换）5, （润滑）6</div>
					</div>
				</section>
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
						<label class="control-label" for="equipType-modal" style="width:110px;"><b>请选择设备类型:</b></label>
						 <select class="span3 equipType equipType-modal" id="equipType-modal">
												<option value="1">生产设备</option>
												<option value="2">特种设备</option>
												<option value="3">设施</option>
											</select>
					</div>
					<div class="control-group">
						<label class="control-label" for="item" style="width:100px;"><b>内容:</b></label>
						<div class="controls" style="margin-left:110px;">
							<input id="item" type="text" placeholder="请输入保养内容" class="span3">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="standard" style="width:100px;"><b>正常状态:</b></label>
						<div class="controls" style="margin-left:110px;">
							<input id="standard" type="text" placeholder="请输入设备保养后的正常状态" class="span3">
						</div>
					</div>
				
					<!-- <div class="control-group">
						<label class="control-label" for="input01" style="width:100px;"><b>方法:</b></label>
						<div class="controls" style="margin-left:110px;">
							<input id="contract-no" type="text" placeholder="请输入保养方法代码" class="span3">
						</div>
					</div> -->
					<div class="control-group">
						<label class="control-label" for="way" style="width:100px;"><b>保养方法:</b></label>
						<select id="way" multiple>
							<option value="a"> 1. 清洁 </option>
							<option value="b"> 2. 检查 </option>
							<option value="c"> 3. 补充调整 </option>
							<option value="d"> 4. 修理 </option>
							<option value="e"> 5. 更换 </option>
							<option value="f"> 6. 润滑 </option>
						</select>
					</div>
				</div>
				<!-- right -->
				<div class="sec-rht pull-right" style="width:50%">
					<div class="control-group">
						<label class="control-label" for="equipName-modal" style="width:110px;"><b>请选择设备名称:</b></label>
						 <select class="span3 equipName equipName-modal" id="equipName-modal">
											<option value="2">数控冲床</option>
											<option value="3">激光切割机</option>
											<option value="4">电瓶堆垛车</option>
											<option value="5">ATE</option>
											<option value="6">焊接机器人</option>
										</select>
					</div>
					<div class="control-group">
						<label class="control-label" for="cycle" style="width:100px;"><b>选择保养周期:</b></label>
						<div class="controls" style="margin-left:110px;">
							<select id="cycle">
								<option value="Y">年度保养 - Y</option>
								<option value="Q">季度保养 - Q</option>
								<option value="M">与保养 - M</option>
							</select>
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label" for="remark" style="width:100px;"><b>备注:</b></label>
						<div class="controls" style="margin-left:110px;">
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
		<button href="#" class="btn btn-primary" id="btn-add-confirm">确定</button>
		<button href="#" class="btn btn-primary" id="btn-modify-confirm">确定</button>
		<a href="#" class="btn" data-dismiss="modal">取消</a>
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
							<h3 class="voice voice-brand pull-left text-danger">确定删除本条保养内容吗？</h3>
						</div>
					</div>			
					</div>
				</form>
			</div>
	
		</div>
	 	<div class="modal-footer">
			<button href="#" class="btn btn-primary" id = "btn-remove-confirm">确定</button>
			<a href="#" class="btn" data-dismiss="modal">取消</a>
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
		<button href="#" class="btn btn-primary" id = "">确定</button>
		<a href="#" class="btn" data-dismiss="modal">取消</a>
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
		<button href="#" class="btn btn-primary" id = "">确定</button>
		<a href="#" class="btn" data-dismiss="modal">取消</a>
	</div>
	</div>
	<!-- 删除计划成功modal	结束 -->

</body>
</html>