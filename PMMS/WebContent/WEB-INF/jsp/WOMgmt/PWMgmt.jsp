<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath();%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> 
<html class="no-js"> <!--<![endif]-->
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	    <title>WO.Manage 工单管理</title>
	    <meta name="viewport" content="width=device-width">

	    <!-- For third-generation iPad with high-resolution Retina display: -->
	    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="<%=contextPath%>/favicon/favicon-144px.png">

	    <!-- For iPhone with high-resolution Retina display: -->
	    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="<%=contextPath%>/favicon/favicon-114px.png">

	    <!-- For first- and second-generation iPad: -->
	    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="<%=contextPath%>/favicon/favicon-72px.png">

	    <!-- For non-Retina iPhone, iPod Touch, and Android 2.1+ devices: -->
	    <link rel="apple-touch-icon-precomposed" href="<%=contextPath%>/favicon/favicon.png">	
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
	    
		<link href="<%=contextPath%>/css/app/custom.css" rel="stylesheet" type="text/css">
	    <link href="<%=contextPath%>/css/app/datepicker.css" rel="stylesheet" type="text/css">
			
		<script src="<%=contextPath%>/js/components/requirejs/require.js"></script>
		<script src="<%=contextPath%>/js/iids/require.config.js"></script>
		<script src="<%=contextPath%>/js/WOMgmt.PW.js"></script>  
		<script src="<%=contextPath%>/js/ajaxReqLoading.js"></script>
    </head>
    <body>    
    	<div class="navbar navbar-static-top">
			<jsp:include page="../common/Header.jsp"></jsp:include>
		</div>
        <div class="container">
		    <div class="page-header">
		    	<div>
		        	<h1 class="voice voice-brand pull-left">计划维护工单信息</h1>	
		        </div>
		    </div>	
		    <div class="row ui-sortable">
		        <div class="span12">            
					<section class="module">		
						<div class="tabbable" id="workflowtab">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tab003" id="tab003Nav" data-toggle="tab">当前计划保养工单</a></li>
								<li><a href="#tab004" id="tab004Nav" data-toggle="tab">历史计划保养工单</a></li>
								<li><a href="#tab005" id="tab005Nav" data-toggle="tab">计划保养工单处理</a></li>
						  	</ul>	
							<!--<div class="tab-content">-->
							<div class="tab-content">							
								<div class="tab-pane active" id="tab003">
									<div class="module-body">
										<form role="form" class="form-horizontal">
										    <fieldset>
											    <div class="sec-lft pull-left" style="width:50%">
													<div class="control-group">
														<label class="control-label" style="width:140px;"><b>产生时间FROM:</b></label>
														<div class="controls" style="margin-left:150px;">
															<input type="text" class="datepicker" value=""/>
													    	<span class="add-on"><i class="icon-calendar"></i></span>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label" style="width:140px;"><b>产生时间TO:</b></label>
														<div class="controls" style="margin-left:150px;">
															<input type="text" class="datepicker" value=""/>
													    	<span class="add-on"><i class="icon-calendar"></i></span>
														</div>
													</div>										　　　　　							
											    </div>
											    <div class="sec-rht pull-right" style="width:50%">
											    	<div class="control-group">
														<label class="control-label" style="width:130px;"><b>区域/Department:</b></label>
														<div class="controls" style="margin-left:130px;">
															<select class="form-control">
																<option value="">---全部---</option>
																<option value="1001">机械加工部</option>
															    <option value="1002">开关制造部</option>
															    <option value="1003">变压器制造部</option>
															    <option value="1004">成套设备制造部和其他</option>
															    <option value="1005">其他</option>
															</select>
														</div>
													</div>	
													<div class="control-group" style="width:500px">
														<div class="controls pull-left" style="margin-left:110px;">
															<button type="button" class="btn btn-primary btn-lg btn-block">查询/Search</button>
														</div>
														<div class="btn-group controls pull-left" style="margin-left:120px;">
															<a data-placement="bottom" data-title="Export to Excel" rel="tooltip" class="btn btn-icon" href="#" data-original-title=""><i class=" icon-download-alt"></i></a>
														</div>
													</div>	
												</div>
									    	</fieldset>
										</form>
										<div id="workOrderflow003" class="pull-left">
											<form class="form-search">
												<div class="legendmain mb10 pull-left">
													<span class="label" style="background-color:green;"><h5>未开始维修</h5></span>
													<span class="label" style="background-color:orange;"><h5>正在维修中</h5></span> 
												</div>
												<div class="input-append flR pull-right">
													<input type="text" class="input-medium search-query" data-filter-table="workOrderTable003"><button class="btn btn-icon"><i class="icon-search"></i></button>
												</div>
											</form>
											<br/>
											<div id="wrappedWorkOrderTable003" class="module-body pull-left width100p">
												<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable003">
												</table>
											</div>
										</div>
									</div>
								</div>
								<div class="tab-pane" id="tab004">
									<div class="module-body">
										<form role="form" class="form-horizontal">
										    <fieldset>
											    <div class="sec-lft pull-left" style="width:50%">
													<div class="control-group">
														<label class="control-label" style="width:140px;"><b>产生时间FROM:</b></label>
														<div class="controls" style="margin-left:150px;">
															<input type="text" class="datepicker" value=""/>
													    	<span class="add-on"><i class="icon-calendar"></i></span>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label" style="width:140px;"><b>产生时间TO:</b></label>
														<div class="controls" style="margin-left:150px;">
															<input type="text" class="datepicker" value=""/>
													    	<span class="add-on"><i class="icon-calendar"></i></span>
														</div>
													</div>										　　　　　							
											    </div>
											    <div class="sec-rht pull-right" style="width:50%">
											    	<div class="control-group">
														<label class="control-label" style="width:130px;"><b>区域/Department:</b></label>
														<div class="controls" style="margin-left:130px;">
															<select class="form-control">
																<option value="">---全部---</option>
																<option value="1001">机械加工部</option>
															    <option value="1002">开关制造部</option>
															    <option value="1003">变压器制造部</option>
															    <option value="1004">成套设备制造部和其他</option>
															    <option value="1005">其他</option>
															</select>
														</div>
													</div>	
													<div class="control-group" style="width:500px">
														<div class="controls pull-left" style="margin-left:110px;">
															<button type="button" class="btn btn-primary btn-lg btn-block">查询/Search</button>
														</div>
														<div class="btn-group controls pull-left" style="margin-left:120px;">
															<a data-placement="bottom" data-title="Export to Excel" rel="tooltip" class="btn btn-icon" href="#" data-original-title=""><i class=" icon-download-alt"></i></a>
														</div>
													</div>	
												</div>
									    	</fieldset>
										</form>
										<div id="workOrderflow004" class="pull-left">
											<form class="form-search">
												<div class="pull-left" style="width:50%;">
													<div class="legendmain mb10 pull-left">
														<span class="label" style="background-color:green;"><h5>未开始维修</h5></span>
														<span class="label" style="background-color:orange;"><h5>正在维修中</h5></span> 
													</div>
												<%if(((String)session.getAttribute("modAccess1")).equalsIgnoreCase("RW")){ %>
													<button class="btn btn-primary" type="button" id="woEditButton" style="margin-left:20px;">编辑/Edit</button>
                                                 <%} %>
												</div>
												<div class="input-append flR pull-right">
													<input type="text" class="input-medium search-query" data-filter-table="workOrderTable004"><button class="btn btn-icon"><i class="icon-search"></i></button>
												</div>
											</form>
											<br/>
											<div id="wrappedWorkOrderTable004" class="module-body pull-left width100p">
												<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable004">
												</table>
											</div>
										</div>
									</div>	
								</div><!--tab004 ends-->
								<div class="tab-pane" id="tab005">
									<div class="module-body">
					                	<form style="margin-bottom:0;" class="form-horizontal">					
						           	    	<fieldset>
						           		<!-- left -->
						                		<div class="sec-lft pull-left" style="width:100%">
						                			<%-- 
							                   	    <div class="control-group">
								                    	<label class="control-label" style="width:200px"><b>设备代码/EQSN*：</b></label>
								                    	<div class="controls" style="margin-left:110px;">
									                		<input id="devNO2" type="text" placeholder="" class="span3" value="" readonly="readonly">
								                    	</div>
							                    	</div>
							                    	--%>
						                    	 	 <div class="control-group">
								                    	<label class="control-label" style="width:200px"><b>设备代码/EQSN*:</b></label>
								                    	<div class="input-append flR">
															<input type="text" class="input-medium search-query span3" data-filter-table="workOrderTable005">
														</div>
														<span></span>
						                    		</div>
							                    	<div class="control-group">
								                    	<label class="control-label" style="width:200px;"><b>维修工编号/SSO*：</b></label>
								                    	<div class="controls" style="margin-left:110px;">
									                		<input id="opSSO2" type="text" placeholder="" class="span3" value="" >
									                		<span></span>
								                    	</div>
							                    	</div>
							       					<div class="control-group">
								                    	<label class="control-label" style="width:200px;"><b>关闭工单人SSO*：</b></label>
								                    	<div class="controls" style="margin-left:110px;">
									                		<input type="text" placeholder="结束维修时填写" class="span3" value="" >
									                		<span></span>
								                    	</div>
							                    	</div>
							                   		<div class="control-group">
								                		<label class="control-label" style="width:200px;">
								                			<b>备注/Remark：</b>
								                		</label>
								                    	<div class="controls" style="margin-left:110px;">
									                		<textarea id="desc3" rows="3" class="span3"></textarea>
								                   	    </div>
							                   		</div>	
							                   		<div class="control-group">
								                		<label class="control-label" style="width:200px;">
								                			<b>是否需停机/Shutdown：</b>
								                		</label>
								                    	<div class="controls" style="margin-left:110px;">
									                		<input type="checkbox" checked="checked">
								                   	    </div>
							                   		</div>
							                   		<div class="control-group" style="margin-top:10px;">						        
								                    	<div class="controls" style="margin-left:110px;">
								                    	<%if(((String)session.getAttribute("modAccess1")).equalsIgnoreCase("RW")){ %>
															<button type="button" class="btn btn-large btn-primary">开始维修/Start</button>
									                		<button type="button" class="btn btn-large btn-primary">维修结束/Close</button>
                                                         <%} %>		
								                   	    </div>
							                   		</div>					
						                    	</div>
						                    </fieldset>
					                    </form>
				                    </div>
									<div id="workOrderflow005" class="pull-left">
										<form class="form-search form-horizontal" style="margin-top:10px;">
											<div class="pull-left" style="width:800px;">
												<div class="control-group pull-left" style="width:250px" >
													<label style="width:130px;" class="control-label">区域/Department:
													</label>
													<div style="margin-left:130px;" class="controls">
														<select class="span2">										
														</select>															
													</div>
												</div>
												<div class="control-group pull-left" style="width:300px;margin-left:60px;" >
													<label style="width:130px;margin-left:0px;" class="control-label">设备种类/EQType:
													</label>
													<div style="margin-left:130px;" class="controls">
														<select class="span2" id="select4">
															<option value="">------全部------</option>
															<option value="1">生产设备</option>
															<option value="2">特种设备</option>
															<option value="3">设施</option>										
														</select>															
													</div>
												</div>
												<div class="legendmain mb10 pull-right">
													<span class="label" style="background-color:green;"><h5>未开始维修</h5></span>
													<span class="label" style="background-color:orange;"><h5>正在维修中</h5></span> 
												</div>
											</div>
											<div class="input-append flR pull-right">
												<input type="text" class="input-medium search-query" data-filter-table="workOrderTable005"><button class="btn btn-icon"><i class="icon-search"></i></button>
											</div>
										</form>
										<br />
										<div id="wrappedWorkOrderTable005" class="module-body pull-left width100p">								
											<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable005">
											</table>
										</div>
									</div>
								</div><!--tab005 ends-->
							</div><!-- tab content ends -->
						</div><!-- tabbable ends -->
					</section>		
		        </div>
		    </div>
		</div>
		<!-- Modal -->
		<div id="pwoUpdateModal" class="modal hide fade" data-backdrop="static" data-keyboard="false" style="display: none;">
		  	<div class="modal-dialog">
		   		<div class="modal-content">
		     		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal"><i class="icon-remove icon-white font13"></i></button>
		        		<h3 class="modal-title">历史计划保养工单编辑</h3>
		      		</div>
		        	<div class="modal-body">
		        		<div class="module-body">
			        		<form style="margin-bottom:0;" class="form-horizontal">					
								<fieldset>
								<div>
									<div class="control-group">
										<label class="control-label"><b>工单号:</b></label>
										<div class="controls">
											<input type="text" placeholder="" class="span3" value="" disabled="disabled">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><b>设备编号:</b></label>
										<div class="controls">
											<input type="text" placeholder="" class="span3" value="" disabled="disabled">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><b>设备名称:</b></label>
										<div class="controls">
											<input type="text" placeholder="" class="span3" value="" disabled="disabled">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><b>设备型号:</b></label>
										<div class="controls">
											<input type="text" placeholder="" class="span3" value="" disabled="disabled">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><b>固定资产号:</b></label>
										<div class="controls">
											<input type="text" placeholder="" class="span3" value="" disabled="disabled">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><b>计划开始月:</b></label>
										<div class="controls">
											<input type="text" class="datepicker" value="" disabled="disabled"/>
										    <span class="add-on"><i class="icon-calendar"></i></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><b>区域:</b></label>
										<div class="controls">
											<select class="span3" disabled="disabled">
												<option value="">---全部---</option>
												<option value="dept0001">变压器制造部</option>
											    <option value="dept0002">成套设备制造部</option>
											    <option value="dept0004">机械加工部</option>
											    <option value="dept0005">开关制造部</option>
											    <option value="dept0006">其他</option>
											</select>
										</div>
									</div>	
									<div class="control-group">
										<label class="control-label"><b>维修工:</b></label>
										<div class="controls">
											<input type="text" placeholder="" class="span3" value="" disabled="disabled">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><b>维修开始时间:</b></label>
										<div class="controls">
											<input type="text" class="datepicker" value="" disabled="disabled"/>
										    <span class="add-on"><i class="icon-calendar"></i></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><b>维修结束时间:</b></label>
										<div class="controls">
											<input type="text" class="datepicker" value="" disabled="disabled"/>
										    <span class="add-on"><i class="icon-calendar"></i></span>
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
		       	    </div>
		      		<div class="modal-footer">
		      			<button type="button" class="btn btn-default">保存</button>
		        		<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		      		</div>
		    	</div>
		    </div>
		</div>
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
					<!--  
					<a data-placement="bottom" title="导出到Excel" data-title="Export to Excel" rel="tooltip" class="btn btn-icon" href="#" data-original-title=""><i class=" icon-download-alt"></i></a>
					-->
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
											style="width: 136px;">序号</th>
										<th class="sorting" tabindex="0" rowspan="1" colspan="1"
											aria-label="保养内容: activate to sort column ascending"
											style="width: 211px;">保养内容</th>
										<th class="sorting" tabindex="0" rowspan="1" colspan="1"
											aria-label="周期: activate to sort column ascending"
											style="width: 136px;">周期</th>
										<th class="sorting" tabindex="0" rowspan="1" colspan="1"
											aria-label="方法: activate to sort column ascending"
											style="width: 137px;">方法</th>
										<th class="sorting" tabindex="0" rowspan="1" colspan="1"
											aria-label="正常状态: activate to sort column ascending"
											style="width: 213px;">正常状态</th>
									</tr>
								</thead>
								<tbody role="alert" aria-live="polite" aria-relevant="all" class="maint-item-view">
								</tbody>
							</table>
							<div style="clear:both;">方法代码: （清洁）1, （检查）2, （补充调整）3, （修理）4, （更换）5, （润滑）6</div>
						</div>
					</form>
			</div>
		</div>
	 	<div class="modal-footer">
			<button href="#" class="btn btn-primary" data-dismiss="modal">确定</button>
			<a href="#" class="btn" data-dismiss="modal">取消</a>
		</div>
		</div>
		<!-- 查看计划的保养内容modal	结束 -->
		<div class="alert alert-success alert-fixed-top fade" style="">
			<button type="button" class="close" data-dismiss="alert"><i class=" icon-remove icon-white"></i></button>
			<button class="btn btn-small pull-right">Action</button>
			<strong>Well done!</strong> You successfully read this important alert message.
		</div>  
  	</body>
</html>
