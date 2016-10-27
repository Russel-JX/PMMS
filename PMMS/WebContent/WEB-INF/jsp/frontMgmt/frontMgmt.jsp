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
    <title>PMMS</title>
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
	<script src="<%=contextPath%>/js/front.main.js"></script>
	<script src="<%=contextPath%>/js/ajaxReqLoading.js"></script>
</head>
<body>   
	<div class="navbar">
    	<div class="masthead navbar-inner">
        	<div class="container">
            	<a class="brand" href="../front/index.htm"><span class="ge-logo">General Electric</span> Welcome to PMMS(预防性维护系统)<small><i>powered by</i> GE Aviation </small></a>    
    		</div>
  		</div>
	</div>
	<div class="container">
    	<div class="row ui-sortable">
        	<div class="span12">            
				<section class="module">		
					<div class="tabbable" id="workflowtab">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#tab01" id="tab01Nav" data-toggle="tab">生产人员报修</a>
							</li>
							<li><a href="#tab02" id="tab02Nav" data-toggle="tab">故障维修工单处理</a>
							</li>
							<li><a href="#tab03" id="tab03Nav" data-toggle="tab">计划保养工单处理</a>
							</li>
						</ul>
						<!--<div class="tab-content">-->
						<div class="tab-content">
							<div class="tab-pane active" id="tab01">
                            	<div class="module-body">
			                		<form style="margin-bottom:0;" class="form-horizontal">					
				           	    		<fieldset>
				           					<!-- left -->
				                			<div class="sec-lft pull-left" style="width:100%">
					                   	    	<div class="control-group">
						                    		<label class="control-label" style="width:200px"><b>员工编号/SSO*:</b>
						                    		</label>
						                    		<div class="controls" style="margin-left:110px;">
							                			<input type="text" placeholder="" class="span3" value="">
							                			<span></span>
						                    		</div>
					                    		</div>
					                    		<div class="control-group">
						                    		<label class="control-label" style="width:200px;"><b>设备代码/EQSN.*:</b></label>
						                    		<div class="controls" style="margin-left:110px;">
							                			<input type="text" placeholder="" class="span3" value="" >
							                			<span></span>
						                    		</div>
					                    		</div>
						                    	<div class="control-group">
							                		<label class="control-label" style="width:200px;"><b>故障描述/FaultDesc:</b>
							                		</label>
							                    	<div class="controls" style="margin-left:110px;">
								                		<textarea rows="3" class="span3"></textarea>
							                   	    </div>
						                   		</div>
						                   		<div class="control-group">	
						                   			<label class="control-label" style="width:200px;"><b>涉及安全隐患/Safety:</b>
						                   			</label>				    
							                    	<div class="controls" style="margin-left:110px;">
	                                                   	<input type="checkbox">
							                   	    </div>
						                   		</div>
						                   		<div class="control-group">	
						                   			<label class="control-label" style="width:200px;"><b>是否需停机/Shutdown:</b>
						                   			</label>				    
							                    	<div class="controls" style="margin-left:110px;">
	                                                   	<input type="checkbox" checked="checked">
							                   	    </div>
						                   		</div>							                   				
						                   		<div class="control-group">						        
							                    	<div class="controls" style="margin-left:210px;">
								                		<button type="button" id="saveWorkOrderInfo" class="btn btn-large btn-primary">提交/Submit</button>
							                   	    </div>
						                   		</div>					
				                    		</div>
										</fieldset>
			                    	</form>
		                    	</div>
								<div id="workOrderflow1" class="pull-left">
									<form class="form-search form-horizontal" style="margin-top:10px;">
										<div class="pull-left" style="width:900px">
											<div class="control-group pull-left" style="width:290px;" >
												<label style="width:130px;margin-left:0px;" class="control-label">区域/Department:
												</label>
												<div style="margin-left:130px;" class="controls">
													<select class="span2">										
													</select>															
												</div>
											</div>
											<div class="control-group pull-left" style="width:300px;margin-left:40px;" >
												<label style="width:130px;margin-left:0px;" class="control-label">设备种类/EQType:
												</label>
												<div style="margin-left:130px;" class="controls">
													<select class="span2">
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
											<input type="text" class="input-medium search-query" data-filter-table="workOrderTable1"><button class="btn btn-icon"><i class="icon-search"></i></button>
										</div>
									</form>
									<br/>
									<div id="wrappedWorkOrderTable1" class="module-body pull-left width100p">
										<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable1">
										<thead>
										</thead>
										<tbody>
										</tbody>
										</table>
									</div>
								</div>
							</div>
							<div class="tab-pane" id="tab02">
								<div class="module-body">
				                	<form style="margin-bottom:0;" class="form-horizontal">					
					           	    	<fieldset>
					           		<!-- left -->
					                		<div class="sec-lft pull-left" style="width:100%">
						                   	    <div class="control-group">
							                    	<label class="control-label" style="width:200px"><b>设备代码/EQSN*:</b></label>
							                    	<!-- <div class="controls" style="margin-left:110px;">
								                		<input id="devNO" type="text" placeholder="" class="span3" value="" readonly="readonly">
							                    	</div> -->
							                    	<div class="input-append flR">
														<input type="text" class="input-medium search-query span3" data-filter-table="workOrderTable2">
													</div>
													<span></span>
						                    	</div>
						                    	<div class="control-group">
							                    	<label class="control-label" style="width:200px;"><b>维修工编号/SSO*:</b></label>
							                    	<div class="controls" style="margin-left:110px;">
								                		<input type="text" placeholder="" class="span3" value="" >
								                		<span></span>
							                    	</div>
						                    	</div>
						                   		<div class="control-group">
							                		<label class="control-label" style="width:200px;">
							                			<b>故障种类/FaultType*:</b>
							                		</label>
							                    	<div class="controls" style="margin-left:110px;">
							                    		<select class="span3">
							                    		</select>								     
							                   	    </div>
						                   		</div>
						                    	<div class="control-group">
							                    	<label class="control-label" style="width:200px;"><b>关闭工单人SSO*:</b></label>
							                    	<div class="controls" style="margin-left:110px;">
								                		<input type="text" placeholder="结束维修时填写" class="span3" value="" disabled="disabled">
								                		<span></span>
							                    	</div>
						                    	</div>
												<div class="control-group">	
						                   			<label class="control-label" style="width:200px;"><b>备注/Remark:</b>
						                   			</label>				    
							                    	<div class="controls" style="margin-left:110px;">
	                                                   	<textarea rows="3" class="span3"></textarea>
							                   	    </div>
						                   		</div>	
						                   		<div class="control-group">	
						                   			<label class="control-label" style="width:200px;"><b>等备件/WaitSP:</b>
						                   			</label>				    
							                    	<div class="controls" style="margin-left:110px;">
	                                                   	<input type="checkbox">
							                   	    </div>
						                   		</div>
						                   		<div class="control-group">	
						                   			<label class="control-label" style="width:200px;"><b>等外部服务/WaitService:</b>
						                   			</label>				    
							                    	<div class="controls" style="margin-left:110px;">
	                                                   	<input type="checkbox">
							                   	    </div>
						                   		</div>
						                   		<div class="control-group">						        
							                    	<div class="controls" style="margin-left:110px;">
								                		<button type="button" class="btn btn-large btn-primary">开始维修/Start</button>
								                		<button type="button" class="btn btn-large btn-primary">结束维修/Close</button>
							                   	    </div>
						                   		</div>					
					                    	</div>
					                    </fieldset>
				                    </form>		                    
									<div id="workOrderflow2" class="module-body pull-left" style="margin-top:20px;">
										<form class="form-search form-horizontal" >
											<div class="pull-left" style="width:900px">
												<div class="control-group pull-left" style="width:250px;" >
													<label style="width:130px;" class="control-label">区域/Department:
													</label>
													<div style="margin-left:130px;" class="controls">
														<select class="span2">										
														</select>															
													</div>
												</div>
												<div class="control-group pull-left" style="width:300px;margin-left:60px;" >
													<label style="width:130px;margin-left:10px;" class="control-label">设备种类/EQType:
													</label>
													<div style="margin-left:140px;" class="controls">
														<select class="span2">
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
												<input type="text" class="input-medium search-query" data-filter-table="workOrderTable2"><button class="btn btn-icon"><i class="icon-search"></i></button>
											</div>
										</form>
										<br />
										<div id="wrappedWorkOrderTable2" class="module-body pull-left">		
											<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable2">
											<thead>
											</thead>
											<tbody>
											</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
							<div class="tab-pane" id="tab03">
								<div class="module-body">
				                	<form style="margin-bottom:0;" class="form-horizontal">					
					           	    	<fieldset>
					           		<!-- left -->
					                		<div class="sec-lft pull-left" style="width:100%">
					                			<%-- 
						                   	    <div class="control-group">
							                    	<label class="control-label" style="width:200px"><b>设备代码/EQSN*:</b></label>
							                    	<div class="controls" style="margin-left:110px;" value="">
								                		<input id="devNO2" type="text" placeholder="" class="span3" value="" readonly="readonly">
							                    	</div>
						                    	</div>
						                    	--%>
						                    	 <div class="control-group">
							                    	<label class="control-label" style="width:200px"><b>设备代码/EQSN*:</b></label>
							                    	<div class="input-append flR">
														<input type="text" class="input-medium search-query span3" data-filter-table="workOrderTable3">
													</div>
													<span></span>
						                    	</div>
						                    	<div class="control-group">
							                    	<label class="control-label" style="width:200px;"><b>维修工编号/SSO*:</b></label>
							                    	<div class="controls" style="margin-left:110px;">
								                		<input id="opSSO2" type="text" placeholder="" class="span3" value="" >
								                		<span></span>
							                    	</div>
						                    	</div>
						                   		<div class="control-group">
							                    	<label class="control-label" style="width:200px;"><b>关闭工单人SSO*:</b></label>
							                    	<div class="controls" style="margin-left:110px;">
								                		<input type="text" placeholder="结束维修时填写" class="span3" value="" disabled="disabled">
								                		<span></span>
							                    	</div>
						                    	</div>
						                   		<div class="control-group">
							                		<label class="control-label" style="width:200px;">
							                			<b>备注/Remark:</b>
							                		</label>
							                    	<div class="controls" style="margin-left:110px;">
								                		<textarea id="desc3" rows="3" class="span3" value=""></textarea>
							                   	    </div>
						                   		</div>	
						                   		<div class="control-group">
							                		<label class="control-label" style="width:200px;">
							                			<b>是否需停机/Shutdown:</b>
							                		</label>
							                    	<div class="controls" style="margin-left:110px;">
								                		<input type="checkbox" checked="checked">
							                   	    </div>
						                   		</div>
						                   		<div class="control-group" style="margin-top:10px;">						        
							                    	<div class="controls" style="margin-left:110px;">
								                		<button type="button" class="btn btn-large btn-primary">开始维修/Start</button>
								                		<button type="button" class="btn btn-large btn-primary">结束维修/Close</button>
							                   	    </div>
						                   		</div>					
					                    	</div>
					                    </fieldset>
				                    </form>
			                    </div>
								<div id="workOrderflow3" class="pull-left">
									<form class="form-search form-horizontal" style="margin-top:10px;">
										<div class="pull-left" style="width:800px">
											<div class="control-group pull-left" style="width:250px;" >
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
													<select class="span2">
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
											<input type="text" class="input-medium search-query" data-filter-table="workOrderTable3"><button class="btn btn-icon"><i class="icon-search"></i></button>
										</div>
									</form>
									<br />
									<div id="wrappedWorkOrderTable3" class="module-body pull-left width100p">								
										<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable3">
										</table>
									</div>
								</div>
							</div>
					    </div><!-- tab content ends -->
			    	</div><!-- tabbable ends -->
				</section>		
        	</div>
    	</div>
	</div>
	<!-- 查看计划的保养内容modal	开始 -->
	<div id="modal-plan-maintItem-view" class="modal modal3 hide fade" style="display: none;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
		<h3>计划保养内容/Maint Content</h3>
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
 	    <!--  
		<button href="#" class="btn btn-primary" data-dismiss="modal">确定</button>
		-->
		<a href="#" class="btn btn-primary" data-dismiss="modal">关闭</a>
	</div>
	</div>
	<!-- 查看计划的保养内容modal	结束 -->
</body>
</html>
