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
		<link href="<%=contextPath%>/css/app/datepicker.css" rel="stylesheet" type="text/css">
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
	    
		<link href="<%=contextPath%>/css/app/custom.css" rel="stylesheet" type="text/css">
		<script src="<%=contextPath%>/js/components/requirejs/require.js"></script>
		<script src="<%=contextPath%>/js/iids/require.config.js"></script>
		<script src="<%=contextPath%>/js/WOMgmt.FW.js"></script>
		<script src="<%=contextPath%>/js/ajaxReqLoading.js"></script>
    </head>
    <body>    
		<div class="navbar navbar-static-top">
			<jsp:include page="../common/Header.jsp"></jsp:include>
		</div>
        <div class="container">
		    <div class="page-header">
		    	<div>
		        	<h1 class="voice voice-brand pull-left">故障维修工单信息</h1>	
		        </div>
		    </div>	
		    <div class="row ui-sortable">
		        <div class="span12">            
					<section class="module">		
						<div class="tabbable" id="workflowtab">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#tab001" id="tab001Nav" data-toggle="tab">当前故障工单</a></li>
								<li><a href="#tab002" id="tab002Nav" data-toggle="tab">历史故障工单</a></li>
						  	</ul>	
							<!--<div class="tab-content">-->
							<div class="tab-content">
								<div class="tab-pane active" id="tab001">
									<div class="module-body">
										<form role="form" class="form-horizontal">
										    <fieldset>
											    <div class="sec-lft pull-left" style="width:400px;">
													<div class="control-group">
														<label class="control-label" style="width:140px;"><b>申报时间FROM:</b></label>
														<div class="controls" style="margin-left:150px;">
															<input type="text" class="datepicker" value=""/>
													    	<span class="add-on"><i class="icon-calendar"></i></span>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label" style="width:140px;"><b>申报时间TO:</b></label>
														<div class="controls" style="margin-left:150px;">
															<input type="text" class="datepicker" value=""/>
													    	<span class="add-on"><i class="icon-calendar"></i></span>
														</div>
													</div>													
											    </div>
											    <div class="sec-rht pull-right" style="margin-right:80px;width:300px;">
											    	<div class="control-group">
														<label class="control-label" style="width:130px;"><b>区域/Department:</b></label>
														<div class="controls" style="margin-left:130px;">
															<select class="form-control">
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
									</div>
									<div id="workOrderflow001" class="pull-left">
										<form class="form-search">
											<div class="legendmain mb10 pull-left">
												<span class="label" style="background-color:green;"><h5>未开始维修</h5></span>
												<span class="label" style="background-color:orange;"><h5>正在维修中</h5></span> 
											</div>
											<div class="input-append flR pull-right">
												<input type="text" class="input-medium search-query" data-filter-table="workOrderTable001"><button class="btn btn-icon"><i class="icon-search"></i></button>
											</div>
										</form>
										<br/>
										<div id="wrappedWorkOrderTable001" class="module-body pull-left width100p">
											<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable001">
												<thead>
												</thead>
												<tbody>
												</tbody>
											</table>
										</div>
									</div>
								</div>
								<div class="tab-pane" id="tab002">
									<div class="module-body">
										<form role="form" class="form-horizontal">
										     <fieldset>
											    <div class="sec-lft pull-left" style="width:400px;">
													<div class="control-group">
														<label class="control-label" style="width:140px;"><b>申报时间FROM:</b></label>
														<div class="controls" style="margin-left:150px;">
															<input type="text" class="datepicker" value=""/>
													    	<span class="add-on"><i class="icon-calendar"></i></span>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label" style="width:140px;"><b>申报时间TO:</b></label>
														<div class="controls" style="margin-left:150px;">
															<input type="text" class="datepicker" value=""/>
													    	<span class="add-on"><i class="icon-calendar"></i></span>
														</div>
													</div>													
											    </div>
											    <div class="sec-rht pull-right" style="margin-right:80px;width:300px;">
											    	<div class="control-group">
														<label class="control-label" style="width:140px;"><b>区域/Department:</b></label>
														<div class="controls" style="margin-left:140px;">
															<select class="form-control">
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
									</div>
									<div id="workOrderflow002" class="pull-left">
										<form class="form-search">
											<div class="pull-left" style="width:50%;">
												<%if(((String)session.getAttribute("modAccess1")).equalsIgnoreCase("RW")){ %>
												<button class="btn btn-primary" type="button" id="woEditButton">编辑/Edit</button>
                                                 <%} %>												
												<button class="btn btn-primary" type="button" id="woPrintButton">打印维修单/Print</button>
											</div>
											<div class="input-append flR pull-right">
												<input type="text" class="input-medium search-query" data-filter-table="workOrderTable002"><button class="btn btn-icon"><i class="icon-search"></i></button>
											</div>
										</form>
										<br/>
										<div id="wrappedWorkOrderTable002" class="module-body pull-left width100p">
											<table class="table table-striped table-bordered table-condensed" data-table-name="workOrderTable002">
												<thead>
												</thead>
												<tbody>
												</tbody>
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
		<!-- Modal -->
		
		<div id="woUpdateModal" class="modal modal3 hide fade" data-backdrop="static" data-keyboard="false" style="display: none;">
		  	<div class="modal-dialog">
		   		<div class="modal-content">
		     		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal"><i class="icon-remove icon-white font13"></i></button>
		        		<h3 class="modal-title">历史维修工单编辑</h3>
		      		</div>
		        	<div class="modal-body">
		        		<div class="module-body">
			        		<form style="margin-bottom:0;" class="form-horizontal">					
								<fieldset>
								<!-- left -->
								<div class="sec-lft pull-left" style="width:50%">
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
										<label class="control-label"><b>报修人:</b></label>
										<div class="controls">
											<input type="text" placeholder="" class="span3" value="" disabled="disabled">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><b>报修时间:</b></label>
										<div class="controls">
											<input type="text" class="datepicker" value="" disabled="disabled"/>
										    <span class="add-on"><i class="icon-calendar"></i></span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><b>故障描述:</b></label>
										<div class="controls">
											<textarea rows="3" class="span3" value=""></textarea>
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
								</div>
								<!-- right -->
								<div class="sec-rht pull-right" style="width:50%;">								
									<div class="control-group">
										<label class="control-label"><b>维修工:</b></label>
										<div class="controls">
											<input type="text" placeholder="" class="span3" value="" disabled="disabled">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label"><b>故障种类:</b></label>
										<div class="controls">
											<select class="span3">
												<option value="1">电气故障</option>
												<option value="2">机械故障</option>
												<option value="3">液压故障</option>
												<option value="4">软件故障</option>
												<option value="0">其他故障</option>
											</select>
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
		<%--
		<div id="woPrintModal" class="modal modal3 hide fade" data-backdrop="static" data-keyboard="false" style="display: none;">
		  	<div class="modal-dialog">
		   		<div class="modal-content">
		     		<div class="modal-header">
		        		<button type="button" class="close" data-dismiss="modal"><i class="icon-remove icon-white font13"></i></button>
		        		<h3 class="modal-title">历史维修工单打印预览</h3>
		      		</div>
		        	<div class="modal-body" id="printArea">
		        		<div class="module-body">
		        			<div><h5 align="right">上海通用电气开关/光电有限公司</h5></div>
		        			<hr />
			        		<table  style="width:80%" border="0">
			        			<tr>
			        				<td style="width:33%;"></td>
			        				<td align="center" style="width:33%;"><h2 align="center">内 部 维 修 服 务 单</h2></td>
			        				<td align="right" style="width:15%;"><h4>工单号:</h4></td>
			        				<td align="left" style="width:18%;"><span style="text-decoration:underline"></span></td>
			        			</tr>
			        		</table>
			        		<table  width="100%" border="1">
			        			<tr>
									<td style="width:10%;" align="center">申<br />报<br />人<br />填<br />写</td>
									<td>
										<table width="100%" border="1" frame=void>
											<tr>
												<td colspan="5" align="left">申报部门：<span></span></td>
											</tr>
											<tr>
												<td rowspan="3" align="center" style="width:10%">设<br>备<br>维<br>修</td>
												<td align="center">设备编号</td>
												<td align="center">设备名称</td>
												<td align="center">设备型号</td>
												<td align="center">固定资产号</td>
											</tr>
											<tr>
												<td align="center"><span></span></td>
												<td align="center"><span></span></td>
												<td align="center"><span></span></td>
												<td align="center"><span></span></td>
											</tr>
											<tr>
												<td colspan="4" align="center">
													<div><p>设备故障描述</p></div>
													<div><textarea rows="2" cols="10" value="" class="span3"></textarea></div>
												</td>
											</tr>
											<tr>
												<td align="center" style="width:10%">其<br>他<br>维<br>修</td>
												<td colspan="4" align="center">
													<textarea rows="3" value="" class="span3"></textarea>
												</td>
											</tr>
											</tr>
												<tr>
												<td colspan="5">
													<div>
														<div　 class="pull-right" style="margin-left:50px;">是否需停机　　　　　<input type="checkbox"/>　　是</div>
														<div　 class="pull-left">涉及安全隐患　　　　<input type="checkbox"/>　　是</div> <br/>
														<div　 class="pull-left">如果是，具体为:　　　<input type="text" value=""/></div>					
													</div>	
												</td>
											</tr>
											</tr>
												<tr>
												<td colspan="2">
													申报人:<span></span>
												</td>
												<td colspan="3">
													申报日期:<span></span>
													<br />
													申报时间:<span></span>
												</td>
											</tr>
											</tr>
											<tr>
												<td colspan="5"></td>
											</tr>
										</table>
									</td>
			        			</tr>
			        			<tr>
									<td style="width:10%;" align="center">设<br />备<br />部<br />门<br />填<br />写</td>
									<td align="left">
										<div><P>维修纪要</P></div>
										<div>1、故障原因判断<br /><textarea rows="3" value=""></textarea></div>
										<div>2、维修/维护措施<br /><textarea rows="3" value=""></textarea></div>
										<div>3、备件使用<br />
										<div id="wrappedTable010" class="module-body pull-left width100p">
											<table class="table table-striped table-bordered table-condensed" data-table-name="tab010">
											</table>
										</div>
										<div>4、备注/进一步工作<br />
											<input type="checkbox"/> 需要采购以下备件 <br/>
											<textarea rows="3" value=""></textarea> <br/>
											<input type="checkbox"/> 需要外部技术服务 <br/>
											<input type="checkbox"/> 需要修改《设备保养作业指导书》 <br/>
											<input type="checkbox"/> 需要修改《设备维护保养计划》 <br/>
											<input type="checkbox"/> 其他 <br/>									
										</div>	
										<table border="1" style="width: 100%;">
											<tr>
												<td style="width:30%">维修人员签名</td>
												<td style="width:70%">
													开始时间<span></span> <br/>
													结束时间<span></span>
												</td>
											</tr>
											<tr>
												<td style="width:60%">
												耗用时间：　　正常上班<input style="text" value="" class="span1"/>小时+
												加班<input style="text" value="" class="span1"/>小时
												</td>
												<td style="width:40%">
													停机时间<input style="text" value="" class="span1"/>小时
												</td>
											</tr>
										</table>							
									</td>
			        			</tr>
			        			<tr>
									<td style="width:10%;"></td>
									<td></td>
			        			</tr>
			        		</table>
						</div>
		       	    </div>
		      		<div class="modal-footer">
		      			<button type="button" class="btn btn-default">打印</button>
		        		<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		      		</div>
		    	</div>
		    </div>
		</div>
		 --%>
		<div class="alert alert-success alert-fixed-top fade" style="">
			<button type="button" class="close" data-dismiss="alert"><i class=" icon-remove icon-white"></i></button>
			<button class="btn btn-small pull-right">Action</button>
			<strong>Well done!</strong> You successfully read this important alert message.
		</div> 
		<form  id ="orderInfoForm" action="../orderMgmt/printPage.htm" method="post" target="_blank">
			<input name="orderInfo1" type="hidden" value=""/>
		</form>
  	</body>
</html>
