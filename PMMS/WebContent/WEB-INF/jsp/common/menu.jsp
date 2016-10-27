<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="com.ge.pmms.po.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath();%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="<%=contextPath%>/css/common/geam-menu.css" rel="stylesheet">
<link href="<%=contextPath%>/css/bootstrap/responsive.css" rel="stylesheet">
</head>
<div class="navbar navbar-static-top">
	<div class="navbar-inner">
    	<div class="container">
			<button class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
			          <i class="icon-bar"></i>
			          <i class="icon-bar"></i>
			          <i class="icon-bar"></i>
			</button>
		</div>
	</div>
</div>
<div class="primary-navbar nav-collapse">
	<div class="container">
		<ul class="nav">
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">Work Order<span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">Outage Work Order</a></li>
					<li><a href="#">PM Work Order</a></li>
				</ul>
			</li>
			<li>
				<a href="#">Preventive Maintenance</a>
			</li>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">Spare-Part warehouse<span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">Spare Part stock-out</a></li>
					<li><a href="#">Spare Part stock-in</a></li>
				</ul>
			</li>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">Analytics and Reports<span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">PM Fulfilment Statistics And Report</a></li>
					<li><a href="#">Equipment availability statistics  And Report</a></li>
					<li><a href="#">Equipment MTBF statistics  And Report </a></li>
					<li><a href="#">Equipment MTTR statistics  And Report</a></li>
					<li><a href="#">Maintaince Spare-Part Cost Statistics And Report</a></li>
				</ul>
			</li>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">Equipment/facility<span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">Equipment Mgmt</a></li>
					<li><a href="#">Facility Mgmt</a></li>
					<li><a href="#">Document Mgmt</a></li>
				</ul>
			</li>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">Indirect material stock<span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="#">Stock-In</a></li>
					<li><a href="#">Stock-Out</a></li>
				</ul>
			</li>
			<%-- <%if(request.getAttribute("role")!="Admin"){ %> --%>
			<%if(((User)session.getAttribute("user")).getRole().equalsIgnoreCase("Admin")){ %>
			<li class="dropdown">
				<a href="#" class="dropdown-toggle" data-toggle="dropdown">User/Role<span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
					<li><a href="/PMMS/restful/userMgmt">User management</a></li>
					<li><a href="/PMMS/restful/roleMgmt">Role Management</a></li>
				</ul>
			</li>
			<%} %>
		</ul>	
	</div>
</div>