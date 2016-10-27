<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
    <title>PMMS Home</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
    	
	<link href="<%=contextPath%>/css/bootstrap/ie.css" rel="stylesheet" type="text/css">
	<link href="<%=contextPath%>/css/bootstrap/iids-docs.css" rel="stylesheet" type="text/css">
	<link href="<%=contextPath%>/css/common/geam-iids.css" rel="stylesheet">
	<link href="<%=contextPath%>/css/iids/responsive.css" rel="stylesheet">
	<link href="<%=contextPath%>/css/common/geam-table.css" rel="stylesheet">
    <link href="<%=contextPath%>/css/common/appstyle.css" rel="stylesheet">
	<link href="<%=contextPath%>/css/appCss/PMMS.css" rel="stylesheet">
	
	<script src="<%=contextPath%>/js/components/requirejs/require.js" ></script>
    <script src="<%=contextPath%>/js/iids/require.config.js" ></script>
	<script src="<%=contextPath%>/js/common/loadHomePageScripts.js"></script>
</head>

<body>
<div class="navbar navbar-static-top">
	<jsp:include page="Header.jsp"></jsp:include>
</div>
<div class="container-fluid">
	<div class="row-fluid content-row-fluid ">
		<section class="module actionable main-content-section">
			<div class="row-fluid">
				<div class="span6 remove-margin-bottom">
					<div class = "row-fluid well well-small pmms-module-header remove-margin-bottom" >
						<section-header><center><b>Direct Links</b></center></section-header>								
					</div>
					<div id="DirectLinks" class="row-fluid well well-small pmms-module-content remove-margin-left">
						<div class="remove-margin-bottom"><center>
							<%if(((String)session.getAttribute("modAccess1")).equalsIgnoreCase("RW") || ((String)session.getAttribute("modAccess1")).equalsIgnoreCase("RO")){ %>
								<%if(((String)session.getAttribute("subModAccess1")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid">
											<a id="" href="<%=contextPath%>/orderMgmt/fwMgmt.htm" onclick=""><b>故障维修工单</b></a>
								</div>
								<%} %>
								
								<%if(((String)session.getAttribute("subModAccess2")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid">
								    <a id="" href="<%=contextPath%>/orderMgmt/pwMgmt.htm" onclick=""><b>计划保养工单</b></a>
								</div>
								<%} %>
							<%} %>
							
							
							<%if(((String)session.getAttribute("modAccess2")).equalsIgnoreCase("RW") || ((String)session.getAttribute("modAccess2")).equalsIgnoreCase("RO")){ %>
								<%if(((String)session.getAttribute("subModAccess3")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid padding-top-3">
									<a id="" href="<%=contextPath%>/plan/index.htm" onclick=""><b>年度保养计划</b></a>
								</div>
								<%} %>
								
								<%if(((String)session.getAttribute("subModAccess4")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid padding-top-3">
												<a id="" href="<%=contextPath%>/plan/copy.htm" onclick=""><b>产生整年保养计划</b></a>
								</div>
								<%} %>
								
								<%if(((String)session.getAttribute("subModAccess5")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid padding-top-3">
												<a id="" href="<%=contextPath%>/maintItem/index.htm" onclick=""><b>保养内容管理</b></a>
								</div>
								<%} %>
							<%} %>
							
							
							<%if(((String)session.getAttribute("modAccess3")).equalsIgnoreCase("RW") || ((String)session.getAttribute("modAccess3")).equalsIgnoreCase("RO")){ %>
								<%if(((String)session.getAttribute("subModAccess6")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid padding-top-3">
									<a id="" href="<%=contextPath%>/sparePart/spMgmt.htm" onclick=""><b>备件管理</b></a>
								</div>
								<%} %>
							<%} %>
							
							
							<%if(((String)session.getAttribute("modAccess4")).equalsIgnoreCase("RW") || ((String)session.getAttribute("modAccess4")).equalsIgnoreCase("RO")){ %>
								<%if(((String)session.getAttribute("subModAccess7")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid padding-top-3">
									<a id="" href="<%=contextPath%>/chart/pm.htm" onclick=""><b>计划保养完成率统计</b></a>
								</div>
								<%} %>
								
								<%if(((String)session.getAttribute("subModAccess8")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid padding-top-3">
											<a id="" href="<%=contextPath%>/chart/ea.htm" onclick=""><b>设备可利用率统计</b></a>
								</div>
								<%} %>
								
								<%if(((String)session.getAttribute("subModAccess9")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid padding-top-3">
											<a id="" href="<%=contextPath%>/chart/mtbf.htm" onclick=""><b>平均维修间隔时间统计</b></a>
								</div>
								<%} %>
								
								<%if(((String)session.getAttribute("subModAccess10")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid padding-top-3">
											<a id="" href="<%=contextPath%>/chart/mttr.htm" onclick=""><b>平均维修时间统计</b></a>
								</div>
								<%} %>
								
								<%if(((String)session.getAttribute("subModAccess11")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid padding-top-3">
											<a id="" href="<%=contextPath%>/chart/mspc.htm" onclick=""><b>维修备件费用统计</b></a>
								</div>
								<%} %>
								
								<%if(((String)session.getAttribute("subModAccess17")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid padding-top-3">
											<a id="" href="<%=contextPath%>/chart/mspqu.htm" onclick=""><b>维修备件使用数量统计</b></a>
								</div>
								<%} %>
							<%} %>
							
							
							<%if(((String)session.getAttribute("modAccess5")).equalsIgnoreCase("RW") || ((String)session.getAttribute("modAccess5")).equalsIgnoreCase("RO")){ %>
								<%if(((String)session.getAttribute("subModAccess12")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid padding-top-3">
									<a id="" href="<%=contextPath%>/equipMgmt/equipInfo.htm" onclick=""><b>设备设施管理</b></a>
								</div>
								<%} %>
								
								<%if(((String)session.getAttribute("subModAccess13")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid padding-top-3">
											<a id="" href="<%=contextPath%>/equipDocMgmt/index.htm" onclick=""><b>设备文档上传</b></a>
								</div>
								<%} %>
							<%} %>
							
							
							<%if(((String)session.getAttribute("modAccess6")).equalsIgnoreCase("RW") || ((String)session.getAttribute("modAccess6")).equalsIgnoreCase("RO")){ %>
								<%if(((String)session.getAttribute("subModAccess14")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid padding-top-3">
									<a id="" href="<%=contextPath%>/idmMgmt/idmMgmt.htm" onclick=""><b>间接物料管理</b></a>
								</div>
								<%} %>
							<%} %>
							
							
							<%if(((String)session.getAttribute("modAccess7")).equalsIgnoreCase("RW") || ((String)session.getAttribute("modAccess7")).equalsIgnoreCase("RO")){ %>
								<%if(((String)session.getAttribute("subModAccess15")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid padding-top-3">
									<a id="" href="<%=contextPath%>/userMgmt.htm" onclick=""><b>用户管理</b></a>
								</div>
								<%} %>
								
								<%if(((String)session.getAttribute("subModAccess16")).equalsIgnoreCase("Checked")){ %>
								<div class="row-fluid padding-top-3">
											<a id="" href="<%=contextPath%>/roleMgmt.htm" onclick=""><b>角色管理</b></a>
										</div>
								<%} %>
							<%} %>
							</center>
						</div>
					</div>
				</div>
				<div class="span6">
					<div class = "row-fluid well well-small pmms-module-header remove-margin-bottom" >
						<section-header><center><b>Pending Work Orders</b></center></section-header>
					</div>
					<div id="PendingWork" class="row-fluid well well-small pmms-module-content remove-margin-left" id="">
						<div class="remove-margin-bottom">
						<center>
							<%if(request.getAttribute("isEmpty") == "false"){ %>
								<div class="row-fluid">
									<a id="" href="#" onclick=""><%= request.getAttribute("pending1") %></a>
								</div>
								<div class="row-fluid padding-top-3">
									<a id="" href="#" onclick=""><%= request.getAttribute("pending2") %></a>
								</div>
								<div class="row-fluid padding-top-3">
									<a id="" href="#" onclick=""><%= request.getAttribute("pending3") %></a>
								</div>
								<div class="row-fluid padding-top-3">
									<a id="" href="#" onclick=""><%= request.getAttribute("pending4") %></a>
								</div>
								<div class="row-fluid padding-top-3">
									<a id="" href="#" onclick=""><%= request.getAttribute("pending5") %></a>
								</div>
								<div class="row-fluid padding-top-3">
									<a id="" href="#" onclick=""><%= request.getAttribute("pending6") %></a>
								</div>
								<div class="row-fluid padding-top-3">
									<a id="" href="#" onclick=""><%= request.getAttribute("pending7") %></a>
								</div>
							<%} else{ %>
								<div class="alligne-center">
									<label class="text no-white-space">No Pending Work Orders !!</label>
								</div>
							<%}%>
							</center>
						</div>
					</div>
				</div>
			</div>
			<div class="row-fluid">
				<div class = "row-fluid well well-small pmms-module-header remove-margin-bottom" >
					<section-header><b><center>任务工单列表</center></b></section-header>
				</div>
 				<input type="hidden" id="workOrderListJSONID" value='<%=request.getAttribute("workOrderListJSON")%>'>
				<table class="table table-bordered dataTable table-responsive" data-table-name="dt_workOrder" id='dt_workOrder'></table>
			</div>
		</section>
	</div>
</div>
</body>

</html>
