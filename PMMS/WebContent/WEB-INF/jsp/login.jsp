<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath();%>
<!DOCTYPE html>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Login</title>
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
	<script src="<%=contextPath%>/js/login.js" ></script>
	
	
	<style type="text/css">
		body {
            	/*background-color:rgba(0,0,0,0.5);*/
            	background: url("../css/common/images/SJV.png") repeat;
            	background-size:contain;
            	background-attachment:fixed;
            }
	</style>
</head>
<body>
<form action="/PMMS/restful/validate" method="post" name="myForm" id="myForm">
<div class="container-fluid">

	<div class="row-fluid content-row-fluid">
		<div class="span12 actionable page-content full_height_div">
		
				<section class="span4 module offset4 element">
				<div class="row-fluid loginLogo">
		</div>
					<span style="color: red;"><center >${message}</center></span>
					<div class="remove-margin-bottom">
						<label class="control-label">SSO :</label>
						<div class="control-group">
						<input type="text" id="sso" name="sso" class="span12">
						</div>
					</div>
					<div class="row-fluid remove-margin-bottom">
					   <label class="control-label">Password :</label>
						<div class="control-group">
							<input type="password" name="password" class="span12" id="Password">
						</div>
					</div>
					<div class="row-fluid remove-margin-bottom ">
					<div class="pull-left" >
							<button id ="editpwd" class="btn btn-inverse btn-geam-custom" type="button" >修改密码</button>
						</div>
		               	<div class="pull-right" >
							<button id ="savebtnSubmit" class="btn btn-inverse btn-geam-custom" type="submit">登录</button>
						</div>
					</div>
					<!--  <div class="row-fluid remove-margin-bottom center-align">
						<br>Contact <b>Admin</b> for any inconvenience
					</div>
					-->
				</section>
		</div>
	</div>
</div>
</form>


	<div id="modal-editPwd" class="modal hide fade" data-backdrop="static" data-keyboard="false">
		<div class="modal-dialog">
	  		<div class="modal-content">
		  		<div class="modal-header">
				<h3>修改密码</h3>
				</div>
				
					<div class="modal-body">
				    <label >SSO:</label>
				    <input type="text" id="updSSO"></input>
				    <label >旧密码</label>
				    <input type="password" id="updOldPwd"></input>
				    <label >新密码</label>
				    <input type="password" id="updNewPwd"></input>
				    <label >确认新密码</label>
				    <input type="password" id="updConNwePwd"></input>
				</div>
			<div class="alert alert-success hide" role="alert" id="msg"></div>
				<div class="modal-footer">
			      			<button type="button" class="btn btn-default" id="submitBtn">确定</button>
			        		<button type="button" class="btn btn-default" data-dismiss="modal">返回</button>
			   </div>
	  		</div>
	    </div>
			
	</div>
</body>
</html>



