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
		<script src="<%=contextPath%>/js/WOMgmt.print.js"></script>
		<script src="<%=contextPath%>/js/ajaxReqLoading.js"></script>
		<style type="text/css"> 
			body{ font-size:6px; line-height:14px;} 
		</style> 
	</head>
	<body>
		<div class="container">
		    <div class="row ui-sortable">
		        <div class="span12">            
					<section class="module">		
						<div id="woPrintModal">			   
			        		<div class="module-body">
			        			<div><h5 align="right">上海通用电气开关/广电有限公司</h5></div>
			        			<hr />
				        		<table  style="width:80%" border="0">
				        			<tr>
				        				<td style="width:33%;"></td>
				        				<td align="center" style="width:33%;"><h4 align="center">内 部 维 修 服 务 单</h4></td>
				        				<td align="right" style="width:15%;"><h4>工单号:</h4></td>
				        				<td align="left" style="width:18%;"><span style="text-decoration:underline"></span></td>
				        			</tr>
				        		</table>
				        		<table  width="100%" height="80%" border="1">
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
												<%-- 
												<tr>
													<td align="center" style="width:10%">其<br>他<br>维<br>修</td>
													<td colspan="4" align="center">
														<textarea rows="3" value="" class="span3"></textarea>
													</td>
												</tr>
												--%>
												
												<tr>
													<td colspan="5">
														<div>
															<div　 class="pull-right" style="margin-left:50px;">是否需停机　　　　　<input type="checkbox"/>　　是</div>
															<div　 class="pull-left">涉及安全隐患　　　　<input type="checkbox"/>　　是</div> <br/>
															<div　 class="pull-left">如果是，具体为:　　　<input type="text" value=""/></div>					
														</div>	
													</td>
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
												<%-- 
												<tr>
													<td colspan="5"></td>
												</tr>
												--%>
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
													<td style="width:30%">维修人员签名：<span></span></td>
													<td style="width:70%">
														开始时间<span></span> <br/>
														结束时间<span></span>
													</td>
												</tr>
												<tr>
													<td style="width:60%">
													耗用时间：正常上班<input style="text" value="" class="span1" size="4"/>小时+
													加班<input style="text" value="" class="span1" size="4"/>小时
													</td>
													<td style="width:40%">
														停机时间<input style="text" value="" class="span1" size="4"/>小时
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
				      		<div class="modal-footer">
				      			<button type="button" class="btn btn-default">打印</button>
				      		</div> 	
						</div>
					</section>		
		        </div>
		    </div>
		</div>	
		<input id="orderInfo2" name="orderInfo2" type="hidden" value='${orderInfo2}'/>
	</body>
</html>