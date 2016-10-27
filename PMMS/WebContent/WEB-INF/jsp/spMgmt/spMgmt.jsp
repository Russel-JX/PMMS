<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath();%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>SP.Info</title>
		<meta name="viewport" content="width=device-width">

		<!-- For third-generation iPad with high-resolution Retina display: -->
		<link rel="apple-touch-icon-precomposed" sizes="144x144" href="<%=contextPath%>/favicon/favicon-144px.png">

		<!-- For iPhone with high-resolution Retina display: -->
		<link rel="apple-touch-icon-precomposed" sizes="114x114" href="<%=contextPath%>/favicon/favicon-114px.png">

		<!-- For first- and second-generation iPad: -->
		<link rel="apple-touch-icon-precomposed" sizes="72x72" href="<%=contextPath%>/favicon/favicon-72px.png">

		<!-- For non-Retina iPhone, iPod Touch, and Android 2.1+ devices: -->
		<link rel="apple-touch-icon-precomposed" href="<%=contextPath%>/favicon/favicon.png">
		<link href="<%=contextPath%>/css/app/chosen.css" rel="stylesheet" type="text/css">

		<!--[if lt IE 9]>
		<link id="theme-ie" href="css/themes/iids/ie.min.css" rel="stylesheet" type="text/css">
		<![endif]-->
		
		
		<link href="<%=contextPath%>/css/bootstrap/ie.css" rel="stylesheet" type="text/css"/>
		<link href="<%=contextPath%>/css/bootstrap/iids-docs.css" rel="stylesheet" type="text/css"/>
		<link href="<%=contextPath%>/css/bootstrap/bootstrap-duallistbox.css" rel="stylesheet" />
		<link href="<%=contextPath%>/css/common/geam-iids.css" rel="stylesheet"/>
		<link href="<%=contextPath%>/css/iids/responsive.css" rel="stylesheet"/>
		<link href="<%=contextPath%>/css/common/geam-table.css" rel="stylesheet"/>
	    <link href="<%=contextPath%>/css/common/appstyle.css" rel="stylesheet"/>
		<link href="<%=contextPath%>/css/appCss/PMMS.css" rel="stylesheet"/>
		
		<link href="<%=contextPath%>/css/app/chosen.css" rel="stylesheet" type="text/css">
		<link href="<%=contextPath%>/css/app/datepicker.css" rel="stylesheet" type="text/css">
		<link href="<%=contextPath%>/css/app/custom.css" rel="stylesheet" type="text/css">
		<script src="<%=contextPath%>/js/components/requirejs/require.js"></script>
		<script src="<%=contextPath%>/js/iids/require.config.js"></script>
		<script src="<%=contextPath%>/js/sp.main.js"></script>
		<script src="<%=contextPath%>/js/ajaxReqLoading.js"></script>
	</head>
	<body>
		<div class="navbar navbar-static-top">
			<jsp:include page="../common/Header.jsp"></jsp:include>
		</div>
		<div class="container">
			<div class="page-header">
				<div>
					<h1 class="voice voice-brand pull-left">备件信息查询</h1>
				</div>
			</div>
			<div class="row ui-sortable">
				<div class="span12">
					<section class="module">
						<div class="tabbable" id="workflowtab">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tab006" id="tab006Nav" data-toggle="tab">备件库存</a></li>
								<li><a href="#tab007" id="tab007Nav" data-toggle="tab">少于安全库存量的备件</a></li>
								<li><a href="#tab008" id="tab008Nav" data-toggle="tab">出入库记录</a></li>
						  	</ul>
							<!--<div class="tab-content">-->
							<div class="tab-content">
								<div class="tab-pane active" id="tab006">
									<div id="spInfoflow006" class="pull-left">									
										<div class="module-body">
											<form class="form-horizontal" style="margin-bottom:0;">					
												<fieldset>
													<div class="sec-lft pull-left" style="width:100%;">
														<div class="pull-left" style="width:480px;">
															<div class="btn-group pull-left" style="width:220px">
																<%if(((String)session.getAttribute("modAccess3")).equalsIgnoreCase("RW")){ %>						
																<button class="btn btn-primary pull-left" type="button">增加/Add</button>
																<button class="btn btn-primary pull-left" type="button">编辑/Edit</button>
																<button class="btn btn-primary pull-left" type="button">删除/Del</button>
																<%} %>
																<a data-placement="bottom" data-title="Export to Excel" rel="tooltip" class="btn btn-icon" href="#" data-original-title=""><i class=" icon-download-alt"></i></a>
															</div>
															<div class="control-group pull-right">
																<button type="button" class="btn btn-primary btn-lg">计算建议安全库存/CalSafetyStock</button>
															</div>
														</div>
														<div class="pull-right">
															<form class="form-search">								
																<div class="input-append flR pull-right">
																	<input type="text" class="input-medium search-query" data-filter-table="spinfoTable006"><button class="btn btn-icon"><i class="icon-search"></i></button>
																</div>
															</form>
														</div>
													</div>
												</fieldset>
											</form>
										</div>									
										<div id="wrappedspinfoTable006" class="module-body pull-left width:100%">
											<table class="table table-striped table-bordered table-condensed" data-table-name="spinfoTable006">
											</table>
										</div>								
									</div>	
								</div>
								<div class="tab-pane" id="tab007">
									<div id="spInfoflow007" class="pull-left">	
										<div class="btn-group pull-right">
											<a data-placement="bottom" data-title="Export to Excel" rel="tooltip" class="btn btn-icon" href="#" data-original-title=""><i class=" icon-download-alt"></i></a>
										</div>
										<br><br>
										<form class="form-search">								
											<div class="input-append flR pull-right">
												<input type="text" class="input-medium search-query" data-filter-table="spinfoTable007"><button class="btn btn-icon"><i class="icon-search"></i></button>
											</div>
										</form>
										<br/>
										<div id="wrappedspinfoTable007" class="module-body pull-left width:100%">
											<table class="table table-striped table-bordered table-condensed" data-table-name="spinfoTable007">
											</table>
										</div>
									</div>		
								</div>
								<div class="tab-pane" id="tab008">
									<div>
										<form role="form" class="form-horizontal">
										    <fieldset>
											    <div class="pull-left" style="width:400px; margin-left:0px">
													<div class="control-group">
														<label class="control-label"><b>时间FROM:</b></label>
														<div class="controls">
															<input type="text" class="datepicker" value=""/>
													    	<span class="add-on"><i class="icon-calendar"></i></span>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><b>时间TO:</b></label>
														<div class="controls">
															<input type="text" class="datepicker" value=""/>
													    	<span class="add-on"><i class="icon-calendar"></i></span>
														</div>
													</div>			　　　　　							
											    </div>
											    <div class="control-group pull-right" style="width:400px;margin-right:300px;" >
											    	<label class="control-label"><b>出库Out/入库In:</b></label>
											    	<select class="span2">
														<option value="">---全部---</option>
														<option value="1">入库</option>
													    <option value="2">出库</option>
													</select>	
											    </div>
										    </fieldset>
										</form>
									</div>
									<div id="spInfoflow008" class="pull-left">	
										<form class="form-search">	
											<div class="control-group" style="width:500px">
												<div class="controls pull-left" style="margin-left:10px;">
													<button type="button" class="btn btn-primary btn-lg btn-block">查询/Search</button>
												</div>
												<div class="btn-group controls pull-left" style="margin-left:40px;">
													<a data-placement="bottom" data-title="Export to Excel" rel="tooltip" class="btn btn-icon" href="#" data-original-title=""><i class=" icon-download-alt"></i></a>
									                <button type="button" class="btn btn-primary btn-lg">扫描入库/ScanIn</button>
									                <button type="button" class="btn btn-primary btn-lg">扫描出库/ScanOut</button>
												</div>
											</div>							
											<div class="input-append flR pull-right">
												<input type="text" class="input-medium search-query" data-filter-table="spinfoTable008"><button class="btn btn-icon"><i class="icon-search"></i></button>
											</div>
										</form>
										<br/>
										<div id="wrappedspinfoTable008" class="module-body pull-left width100p">
											<table class="table table-striped table-bordered table-condensed" data-table-name="spinfoTable008">
											</table>
										</div>
									</div>		
								</div>
							</div><!-- tab-content -->
						</div> <!-- tabbable -->
					</section>
				</div>
			</div>
		</div>	
		<div id="inModal" class="modal hide fade" style="display: none;" data-backdrop="static"  data-keyboard="false">
		  	<div class="modal-dialog">
		   		<div class="modal-content">
		     		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal"><i class="icon-remove icon-white font13"></i></button>
		        		<h3 class="modal-title">备件扫描入库</h3>
		      		</div>
		        	<div class="modal-body">
		        		<div class="module-body">
			        		<form style="margin-bottom:0;" class="form-horizontal">					
								<fieldset>
									<div class="sec-lft pull-left" style="width:50%">
										<div class="control-group">
											<label class="control-label"><b>备件代码:</b></label>
											<div class="controls">
												<input type="text" placeholder="请扫描...." class="span3" value="">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><b>备件名称:</b></label>					
											<div class="controls">
												<p></p>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><b>规格型号:</b></label>					
											<div class="controls">
												<p></p>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><b>生产厂商:</b></label>					
											<div class="controls">
												<p></p>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><b>单位:</b></label>					
											<div class="controls">
												<p></p>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><b>单价(RMB):</b></label>					
											<div class="controls">
												<input type="text" placeholder="" class="span3" value="">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><b>现有库存:</b></label>					
											<div class="controls">
												<p></p>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" ><b>入库数量:</b></label>
											<div class="controls">
												<input type="text" placeholder="请输入数量" class="span3" value="">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" ><b>交付时间差(Day):</b></label>
											<div class="controls">
												<input type="text" placeholder="请输入天数" class="span3" value="">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" ><b>备注:</b></label>
											<div class="controls">
												<textarea rows="3" class="span3" value=""></textarea>
											</div>
										</div>			
									</div> 
								</fieldset>
							</form>
						</div>
		       	    </div>
		      		<div class="modal-footer">
		      			<button type="button" class="btn btn-default">入库</button>
		        		<button type="button" class="btn btn-default">关闭</button>
		      		</div>
		    	</div>
		    </div>
		</div>
		<div id="outModal" class="modal hide fade" data-backdrop="static" data-keyboard="false" style="display: none;">
		  	<div class="modal-dialog">
		   		<div class="modal-content">
		     		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal"><i class="icon-remove icon-white font13"></i></button>
		        		<h3 class="modal-title">备件扫描出库</h3>
		      		</div>
		        	<div class="modal-body" >
		        		<div class="module-body">
			        		<form style="margin-bottom:0;" class="form-horizontal">					
									<fieldset>
									<div class="sec-lft pull-left">
										<div class="control-group">
											<label class="control-label"><b>备件代码:</b></label>
											<div class="controls">
												<input type="text" placeholder="请扫描...." class="span3" value="">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><b>备件名称:</b></label>					
											<div class="controls">
												<p></p>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><b>规格型号:</b></label>					
											<div class="controls">
												<p></p>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><b>生产厂商:</b></label>					
											<div class="controls">
												<p></p>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><b>单位:</b></label>					
											<div class="controls">
												<p></p>
											</div>
										</div>
										<div class="control-group">
											<label class="control-label"><b>当前库存:</b></label>					
											<div class="controls">
												<p></p>
											</div>
										</div>
										
										<div class="control-group">
											<label class="control-label"><b>无工单关联:</b></label>					
											<div class="controls">
												<input type="checkbox" value="100000000000000000"></input>
											</div>
										</div>
								
										<div class="control-group">
											<label class="control-label"><b>关联工单:</b></label>
											<div class="controls">
												<input id="" type="text" placeholder="请选择工单...." class="span3" value="" readonly="readonly">
												<button type="button" class="btn btn-default">选择工单</button>
											</div>
										</div>	
										<div class="control-group">
											<label class="control-label"><b>出库数量:</b></label>
											<div class="controls">
												<input id="" type="text" placeholder="请输入出库数量" class="span3" value="">
											</div>
										</div>
										<div class="control-group">
											<label class="control-label" ><b>备注:</b></label>
											<div class="controls">
												<textarea rows="3" class="span3" value=""></textarea>
											</div>
										</div>					
									</div> 
								</fieldset>
							</form>
						</div>
		       	    </div>
		      		<div class="modal-footer">
		      			<button type="button" class="btn btn-default">出库</button>
		        		<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		      		</div>
		    	</div>
		    </div>
		</div>
		<!-- 选择工单 -->
		<div id="selectWOModal" class="modal modal3 hide fade" data-backdrop="static" data-keyboard="false" style="display: none;">
		  	<div class="modal-dialog">
		   		<div class="modal-content">
		     		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal"><i class="icon-remove icon-white font13"></i></button>
		        		<h3 class="modal-title">选择关联的工单</h3>
		      		</div>
		        	<div class="modal-body" >
		        		<div class="module-body">
			        		<form style="margin-bottom:0; width:800px;" class="form-horizontal">					
								<fieldset>
									<div>
									<!-- left -->
										<div class="sec-lft pull-left" style="width:400px;">
											<div class="control-group">
												<label class="control-label"><b>时间FROM:</b></label>
												<div class="controls">
													<input type="text" class="datepicker"  value="" placeholder="查询开始时间">
													<span class="add-on"><i class="icon-calendar"></i></span>
												</div>
											</div>
											<div class="control-group">
												<label class="control-label"><b>时间TO:</b></label>					
												<div class="controls">
													<input type="text" class="datepicker" value="" placeholder="查询结束时间">
													<span class="add-on"><i class="icon-calendar"></i></span>
												</div>			
											</div> 
										</div>
										<!-- right -->
										<div class="pull-right" style="width:300px;">
											<div class="control-group pull-right">
												<label class="control-label">选择区域:
												</label>
												<div class="controls">
													<select class="span2">										
													</select>															
												</div>
											</div>
											<div class="control-group pull-right">
												<label class="control-label">设备种类:
												</label>
												<div class="controls">
													<select class="span2">
														<option value="">------全部------</option>
														<option value="1">生产设备</option>
														<option value="2">特种设备</option>
														<option value="3">设施</option>											
													</select>															
												</div>
											</div>
											<div class="control-group pull-right">
												<label class="control-label">工单类型:
												</label>
												<div class="controls">
													<select class="span2">
														<option value="">------全部------</option>
														<option value="1">故障维修工单</option>
														<option value="2">计划保养工单</option>									
													</select>															
												</div>
											</div>
										</div>
									</div>
								</fieldset>
							</form>
						</div>
						<fieldset>
							<div id="workFlow009" style="margin-top:20px;">
								<div class="control-group pull-left" style="margin-left:20px;">
									<button type="button" class="btn btn-primary">查询/Search</button>
								</div>
								<div class="input-append flR pull-right" style="margin-right:50px;">
									<input type="text" class="input-medium search-query" data-filter-table="WOTable009"><button class="btn btn-icon"><i class="icon-search"></i></button>
								</div>
								<div id="wrappedWOTable009">
									<table class="table table-striped table-bordered table-condensed" data-table-name="WOTable009">
									</table>
								</div>
							</div>
						</fieldset>
		       	    </div>
		      		<div class="modal-footer">
		      			<button type="button" class="btn btn-default">确定</button>
		        		<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		      		</div>
		    	</div>
		    </div>
		</div>
		<!-- 编辑一条备件信息  -->
        <div id="editSPInfo" class="modal hide fade" style="display: none;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h3>编辑备件基本信息</h3>
            </div>
            	<div class="modal-body ">
            		<form class="form-horizontal">
	                <fieldset>
						<div class="sec-lft pull-left">
							<div class="control-group">
								<label class="control-label"><b>备件编号:</b></label>
								<div class="controls">
									<input type="text" placeholder="" class="span3" value="">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>备件名称:</b></label>
								<div class="controls">
									<input type="text" placeholder="" class="span3" value="">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>型号规格:</b></label>
								<div class="controls">
									<input type="text" placeholder="" class="span3" value="">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>生产厂商:</b></label>
								<div class="controls">
									<input type="text" placeholder="" class="span3" value="">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>单位:</b></label>
								<div class="controls">
									<input type="text" placeholder="" class="span3" value="">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>单价:</b></label>
								<div class="controls">
									<input type="text" placeholder="" class="span3" value="">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>安全库存:</b></label>
								<div class="controls">
									<input type="text" placeholder="" class="span3" value="">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>存放地点:</b></label>
								<div class="controls">
									<input type="text" placeholder="" class="span3" value="">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>备注:</b></label>
								<div class="controls">
									<textarea rows="3" class="span3" value=""></textarea>
								</div>
							</div>									
						</div> 
					</fieldset>
					</form>
            	</div>
            <div class="modal-footer">
                <a class="btn btn-primary">更新/Update</a>
                <a class="btn" data-dismiss="modal">取消/Cancel</a>
            </div>
        </div>
		<!-- 添加一条备件信息  -->
		<div id="addSPInfo" class="modal hide fade" style="display: none;">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">×</button>
                <h3>添加备件基本信息</h3>
            </div>
            	<div class="modal-body ">
            		<form class="form-horizontal">
	                <fieldset>
						<div class="sec-lft pull-left">
							<div class="control-group">
								<label class="control-label"><b>备件名称:</b></label>
								<div class="controls">
									<input type="text" placeholder="" class="span3" value="">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>型号规格:</b></label>
								<div class="controls">
									<input type="text" placeholder="" class="span3" value="">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>生产厂商:</b></label>
								<div class="controls">
									<input type="text" placeholder="" class="span3" value="">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>单位:</b></label>
								<div class="controls">
									<input type="text" placeholder="" class="span3" value="">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>单价:</b></label>
								<div class="controls">
									<input type="text" placeholder="" class="span3" value="">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>存放地点:</b></label>
								<div class="controls">
									<input type="text" placeholder="" class="span3" value="">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label"><b>备注:</b></label>
								<div class="controls">
									<textarea rows="3" class="span3" value=""></textarea>
								</div>
							</div>
							<input type="hidden" id="sparePartId" value="" >									
						</div> 
					</fieldset>
					</form>
            	</div>
            <div class="modal-footer">
                <a class="btn btn-primary">保存/Save</a>
                <a class="btn" data-dismiss="modal">取消/Cancel</a>
            </div>
        </div>
		<div class="alert alert-success alert-fixed-top fade" style="">
			<button type="button" class="close" data-dismiss="alert">
				<i class=" icon-remove icon-white"></i>
			</button>
			<button class="btn btn-small pull-right">Action</button>
			<strong>Well done!</strong> You successfully read this important
			alert message.
		</div>
	</body>
</html>
