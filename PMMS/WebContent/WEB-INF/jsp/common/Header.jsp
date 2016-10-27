<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.ge.pmms.po.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath();%>
<!DOCTYPE html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link href="<%=contextPath%>/css/common/geam-header.css" rel="stylesheet">
	<link href="<%=contextPath%>/css/common/geam-menu.css" rel="stylesheet">
	<link href="<%=contextPath%>/css/bootstrap/responsive.css" rel="stylesheet">
	<script src="<%=contextPath%>/js/applicationJs/header.js" ></script>

</head>
<div class="navbar navbar-static-top">
	<div class="navbar-inner">
    	<div class="container-fluid">
    		<div class="span1 navbar-header pull-left headerLogo"></div>
    		<div class="span2 header-appname"><a class="ancher-nounderlineColour" href="#asdf" onclick="javascript:fnHome();">PMMS</a></div>
   			<div class="span9 pull-right">
       			<div class="btn-toolbar pull-right">
         				<button class="btn userInfoBtn"> 
							<i class="icon-user"></i>&nbsp;Welcome ${user.firstName}(${user.role}) 
 						</button>
						<button class="btn" value onclick="javascript:fnLogout();">
							<i class="icon-signout"></i>&nbsp;Logout
						</button> 
						<button class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				          <i class="icon-bar"></i>
				          <i class="icon-bar"></i>
				          <i class="icon-bar"></i>
						</button>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="primary-navbar nav-collapse">
	<div class="container-fluid">
		<ul class="nav">
		<%if(((String)session.getAttribute("modAccess1")).equalsIgnoreCase("RW") || ((String)session.getAttribute("modAccess1")).equalsIgnoreCase("RO")){ %>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">工单管理<span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					
					<%if(((String)session.getAttribute("subModAccess1")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/orderMgmt/fwMgmt.htm">故障维修工单</a></li>
					<%} %>
					
					<%if(((String)session.getAttribute("subModAccess2")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/orderMgmt/pwMgmt.htm">计划保养工单</a></li>
					<%} %>
					
				</ul>
			</li>
			<%} %>
			<%if(((String)session.getAttribute("modAccess2")).equalsIgnoreCase("RW") || ((String)session.getAttribute("modAccess2")).equalsIgnoreCase("RO")){ %>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">保养计划管理<span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					
					<%if(((String)session.getAttribute("subModAccess3")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/plan/index.htm">年度保养计划</a></li>
					<%} %>
					
					<%if(((String)session.getAttribute("subModAccess4")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/plan/copy.htm">产生整年保养计划</a></li>
					<%} %>
					
					<%if(((String)session.getAttribute("subModAccess5")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/maintItem/index.htm">保养内容管理</a></li>
					<%} %>
					
				</ul>
			</li>
			<%} %>
			<%if(((String)session.getAttribute("modAccess3")).equalsIgnoreCase("RW") || ((String)session.getAttribute("modAccess3")).equalsIgnoreCase("RO")){ %>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">备件管理<span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					
					<%if(((String)session.getAttribute("subModAccess6")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/sparePart/spMgmt.htm">备件管理</a></li>
					<%} %>
					
				</ul>
			</li>
			<%} %>
			<%if(((String)session.getAttribute("modAccess4")).equalsIgnoreCase("RW") || ((String)session.getAttribute("modAccess4")).equalsIgnoreCase("RO")){ %>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">分析与报表<span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					
					<%if(((String)session.getAttribute("subModAccess7")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/chart/pm.htm">计划保养完成率统计</a></li>
					<%} %>
					
					<%if(((String)session.getAttribute("subModAccess8")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/chart/ea.htm">设备可利用率统计</a></li>
					<%} %>
					
					<%if(((String)session.getAttribute("subModAccess9")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/chart/mtbf.htm">平均维修间隔时间统计 </a></li>
					<%} %>
					
					<%if(((String)session.getAttribute("subModAccess10")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/chart/mttr.htm">平均维修时间统计</a></li>
					<%} %>
					
					<%if(((String)session.getAttribute("subModAccess11")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/chart/mspc.htm">维修备件费用统计</a></li>
					<%} %>
					
					<%if(((String)session.getAttribute("subModAccess17")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/chart/mspqu.htm">维修备件使用数量统计</a></li>
					<%} %>
					
				</ul>
			</li>
			<%} %>
			<%if(((String)session.getAttribute("modAccess5")).equalsIgnoreCase("RW") || ((String)session.getAttribute("modAccess5")).equalsIgnoreCase("RO")){ %>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">设备设施及文档管理<span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					
					<%if(((String)session.getAttribute("subModAccess12")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/equipMgmt/equipInfo.htm">设备设施管理</a></li>
					<%} %>
					
					<%if(((String)session.getAttribute("subModAccess13")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/equipDocMgmt/index.htm">设备文档管理</a></li>
					<%} %>
					
				</ul>
			</li>
			<%} %>
			
			<%if(((String)session.getAttribute("modAccess6")).equalsIgnoreCase("RW") || ((String)session.getAttribute("modAccess6")).equalsIgnoreCase("RO")){ %>
			<li class="dropdown">
			<a href="#" class="dropdown-toggle" data-toggle="dropdown">间接物料管理<span class="caret"></span></a>
			<ul class="dropdown-menu" role="menu">
				
				<%if(((String)session.getAttribute("subModAccess14")).equalsIgnoreCase("Checked")){ %>
				<li><a href="<%=contextPath%>/idmMgmt/idmMgmt.htm">间接物料管理</a></li>
				<%} %>
				
			</ul>
			</li>
			<%} %>
			
			<%if(((String)session.getAttribute("modAccess7")).equalsIgnoreCase("RW") || ((String)session.getAttribute("modAccess7")).equalsIgnoreCase("RO")){ %>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">用户和角色管理<span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
				
					<%if(((String)session.getAttribute("subModAccess15")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/userMgmt.htm">用户管理</a></li>
					<%} %>
					
					<%if(((String)session.getAttribute("subModAccess16")).equalsIgnoreCase("Checked")){ %>
					<li><a href="<%=contextPath%>/roleMgmt.htm">角色管理</a></li>
					<%} %>
					
				</ul>
			</li>
			<%} %>
		</ul>	
	</div>
</div>

<form id="homepageform">
</form>
