<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.ge.pmms.po.User"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <meta name="description" content=""/>
    <meta name="author" content=""/>
    <title>Users</title>
    	
	<link href="<%=contextPath%>/css/bootstrap/ie.css" rel="stylesheet" type="text/css"/>
	<link href="<%=contextPath%>/css/bootstrap/iids-docs.css" rel="stylesheet" type="text/css"/>
	<link href="<%=contextPath%>/css/bootstrap/bootstrap-duallistbox.css" rel="stylesheet" />
	
    <link href="<%=contextPath%>/css/iids/responsive.css" rel="stylesheet"/>
    <link href="<%=contextPath%>/css/common/geam-iids.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/common/geam-table.css" rel="stylesheet"/>
    <link href="<%=contextPath%>/css/common/appstyle.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/appCss/PMMS.css" rel="stylesheet"/>
	
	
	<script src="<%=contextPath%>/js/components/requirejs/require.js" ></script>
    <script src="<%=contextPath%>/js/iids/require.config.js" ></script>
	<script src="<%=contextPath%>/js/common/loadUsersScripts.js"></script>
	<script>
	function checkSSO(var_sso)
	{
		<%
			List userList = (ArrayList)request.getAttribute("userInfoList");
			List<String> ssoList = new ArrayList<String>();
			Iterator userListIterator = userList.iterator();
			while(userListIterator.hasNext())
			{
				User user = (User)userListIterator.next();
				String ssoUser = user.getSso();
				ssoList.add(ssoUser);
			}
		%>
		
		var list = "<%= ssoList %>";
		
		var a = list.substring(1, list.length-1);
		
		var SSOList = a.split(', ');
		
		for(i=0;i<SSOList.length;i++)
		{
			if(var_sso.toUpperCase() == SSOList[i].toUpperCase())
			{
				return true;
			}
		}
		return false;
	}
	</script>
</head>

<body>
<div class="navbar navbar-static-top">
	<jsp:include page="/WEB-INF/jsp/common/Header.jsp"></jsp:include>
</div>
<form action="userAction" method="post" name="userForm" id="userform">
<input type="hidden" id="mode" name="mode" value="UPDATE">
<div class="container-fluid">
	<div class="row-fluid content-row-fluid ">
		<div class="module span12 actionable page-content">
 			<div class="row-fluid div-margin-bottom-5">
 				<pageheader>
					<div class="span6 no-white-space">
						<h1>User Management</h1>
					</div>
				</pageheader>
			</div>
			<div class="input-append  pull-right" >
				     <input type="text" placeholder="Search" class="input-medium search-query" data-filter-table="dt_userRole"></input>
				 </div>
			<div class="row-fluid">
				<input type="hidden" id="groupListJSONID" value='<%=request.getAttribute("groupListJSON")%>'>
				<table class="table table-bordered dataTable table-responsive" data-table-name="dt_userRole" id='dt_userRole'></table>
			</div><br>
			<%if(((String)session.getAttribute("modAccess7")).equalsIgnoreCase("RW")){ %>
			<div class="row-fluid">
				<div class="well well-small pmms-module-header remove-margin-bottom">
					<div class="span6 no-white-space">
						<section-header>&nbsp;User Information&nbsp;</section-header>
					</div>
				</div>
				<div class="well well-small pmms-module-content">
					<section class = "module">
						<div class="row-fluid remove-margin-bottom">
							<div class="span3 remove-margin-bottom">
								<label class="text no-white-space">
									SSO :
								</label>
								<input type="text" id="sso" name="sso" readonly="true">		
							</div>
							<div class="span3 remove-margin-bottom">
								<label class="text no-white-space">
									Password :
								</label>
								<input type="hidden" id="hidPwd" name="hidPwd"/>
								<input type="password" class="form-control" id="password" name="password" placeholder="password">
							</div>
							<div class="span3 remove-margin-bottom">
								<label class="text no-white-space">
									First Name :
								</label>
								<!-- <html:text property="Fname" name="coeFormBean" maxlength="250"  style="width: 100%"/> -->	
								<input type="text" id="fname" name="fname" class="form-control" placeholder="First Name">	
							</div>
							<div class="span3 remove-margin-bottom">
								<label class="text no-white-space">
									Last Name :
								</label>
								<input type="text" id="lname" name="lname" class="form-control" placeholder="Last Name">		
							</div>
						</div>
						<div class="row-fluid remove-margin-bottom">
							<div class="span3 remove-margin-bottom">
								<label class="text no-white-space">
									Email ID :
								</label>
								<input type="text" id="emailID" name="emailID" class="form-control" placeholder="Email Id">		
							</div>
							<div class="span3 remove-margin-bottom">
								<label class="text no-white-space">
									Contact Number :
								</label>
								<input type="text" id="cnumber" name="cnumber" class="form-control" placeholder="Contact Number">	
							</div>
							<div class="span3 remove-margin-bottom">
								<label class="text no-white-space">
									Role :
								</label>
								<input type="hidden" id="roleNameList" value="${roleNameList}">
								<select class="form-control" id="role" name="role">
									<option value="" disabled selected>Select Role</option>
								    <c:forEach items="${roleNameList}" var="rolename">
								      <option value="${rolename}"><c:out value="${rolename}"></c:out></option>
								    </c:forEach>
      							</select>
							</div>
							<div class="span3 remove-margin-bottom">
								<input type="hidden" id="userId" name="userId" />
							</div>
						</div>
					</section>
				</div>
			</div>
			
			<div class="row-fluid center-align">
	        	<div class="span12" >
	               	<div id="AddNewButtons">
						<button id ="savebtnSubmit" class="btn btn-inverse btn-geam-custom" type="button" onclick="javascript:fnAddNewUser();">Add New User</button>
						<button id ="UpdatebtnSubmit" class="btn btn-inverse btn-geam-custom" type="button" onclick="javascript:fnSaveUser();">Save</button>
						<button id ="DeletebtnSubmit" class="btn btn-inverse btn-geam-custom" type="button" onclick="javascript:fnDeleteUser();">Delete</button>
						<button id ="clearbtnSubmit" class="btn btn-inverse btn-geam-custom" type="button" onclick="javascript:fnClearUser();">Clear</button>
					</div>
					<div class="no-show" id="nonAddNewButtons">
						<button id ="UpdatebtnSubmit" class="btn btn-inverse btn-geam-custom" type="button" onclick="javascript:fnSaveUser();">Save</button>
						<button id ="DeletebtnSubmit" class="btn btn-inverse btn-geam-custom" type="button" onclick="javascript:fnDeleteUser();">Delete</button>
						<button id ="clearbtnSubmit" class="btn btn-inverse btn-geam-custom" type="button" onclick="javascript:fnClearUser();">Clear</button>
					</div>
				</div>
			</div>
			<%} %>
		</div>
	</div>
</div>
</form>
<form id="dummy_userform"></form>
</body>
</html>
