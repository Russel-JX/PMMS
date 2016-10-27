<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.ge.pmms.po.RoleVo"%>
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
    <title>Roles</title>
    	
	<link href="<%=contextPath%>/css/bootstrap/ie.css" rel="stylesheet" type="text/css"/>
	<link href="<%=contextPath%>/css/bootstrap/iids-docs.css" rel="stylesheet" type="text/css"/>
	<link href="<%=contextPath%>/css/bootstrap/bootstrap-duallistbox.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/common/geam-iids.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/iids/responsive.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/common/geam-table.css" rel="stylesheet"/>
    <link href="<%=contextPath%>/css/common/appstyle.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/appCss/PMMS.css" rel="stylesheet"/>
	
	<script src="<%=contextPath%>/js/components/requirejs/require.js"></script>
    <script src="<%=contextPath%>/js/iids/require.config.js"></script>
	<script src="<%=contextPath%>/js/common/loadRolesScripts.js"></script>
	<script>
	function checkRoleExists(var_fname)
	{
		<%
			List roleList = (ArrayList)request.getAttribute("roleInfoList");
			List<String> roleNameList = new ArrayList<String>();
			Iterator roleNameListIterator = roleList.iterator();
			while(roleNameListIterator.hasNext())
			{
				RoleVo role = (RoleVo)roleNameListIterator.next();
				String roleName = role.getName();
				roleNameList.add(roleName);
			}
		%>
		
		var list = "<%= roleNameList %>";
		
		var a = list.substring(1, list.length-1);
		
		var ROLEList = a.split(', ');
		
		for(i=0;i<ROLEList.length;i++)
		{
			if(var_fname.toUpperCase() == ROLEList[i].toUpperCase())
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
<form action="userAction" method="post" name="roleForm">
<input type="hidden" id="mode" name="mode" value="UPDATE">
<div class="container-fluid">
	<div class="row-fluid content-row-fluid ">
		<div class="module span12 actionable page-content">
 			<div class="row-fluid div-margin-bottom-5">
 				<pageheader>
					<div class="span6 no-white-space">
						<h1>Role Management</h1>
					</div>
				</pageheader>
			</div>
			<div class="input-append  pull-right" >
				     <input type="text" placeholder="Search" class="input-medium search-query" data-filter-table="dt_roleManagement"></input>
				 </div>
			<div class="row-fluid">
				<input type="hidden" id="roleListJSONID" value='<%=request.getAttribute("roleListJSON")%>'> 
				<table class="table table-bordered dataTable table-responsive" data-table-name="dt_roleManagement" id='dt_roleManagement'></table>
			</div><br>
			<div class="row-fluid">
				<div class="well well-small pmms-module-header remove-margin-bottom">
					<div class="span6 no-white-space">
						<section-header>&nbsp;角色 信息&nbsp;</section-header>
					</div>
				</div>
				<div class="well well-small pmms-module-content">
					<section class = "module">
						<div class="row-fluid remove-margin-bottom">
							<div class="span4 remove-margin-bottom">
								<label class="text no-white-space">
									Role ID :
								</label>
								<input type="text" id="sso" name="sso" class="form-control">		
							</div>
							<div class="span4 remove-margin-bottom">
								<label class="text no-white-space">
									Role Name :
								</label>
								<!-- <html:text property="Fname" name="coeFormBean" maxlength="250"  style="width: 100%"/> -->	
								<input type="text" id="fname" name="fname" class="form-control">	
							</div>
							<div class="span4 remove-margin-bottom">
								<label class="text no-white-space">
									Role Description :
								</label>
								<input type="text" id="lname" name="lname" class="form-control">		
							</div>
						</div>
					</section>
				</div>
				<div class="well well-small pmms-module-header remove-margin-bottom">
					<div class="span6 no-white-space">
						<section-header>&nbsp;角色 权限信息&nbsp;</section-header>
					</div>
				</div>
				<div class="well well-small pmms-module-content">
					<section class = "module">
						<div class="row-fluid remove-margin-bottom">
							<div class="span2 remove-margin-bottom">
								<label>
							      	<b>工单管理:</b>
							    </label>
							</div>
							<div class="span2 remove-margin-bottom form-align">
								<select id="moduleAccess_1" name="moduleAccess_1" class="selectBox-width-120" onChange="fnEnableModule_1();">
									<option value="">--选择--</option>
								    <option value="NS">不显示</option>
								    <option value="RO">只读</option>
								    <option value="RW">可编辑</option>
								</select>
							</div>
							<div class="span2 remove-margin-bottom checkbox">
								<label>
							      	<input type="checkbox" id="subModule_1" name="subModule_1"> 故障维修工单
							    </label>
							</div>
							<div class="span2 remove-margin-bottom checkbox">
								<label>
							      	<input type="checkbox" id="subModule_2" name="subModule_2"> 计划保养工单
							    </label>
							</div>
							<div class="span2 remove-margin-bottom"></div>
							<div class="span2 remove-margin-bottom"></div>
						</div>
						<div class="row-fluid remove-margin-bottom">
							<div class="span2 remove-margin-bottom">
								<label>
							      	<b>保养计划管理:</b>
							    </label>
							</div>
							<div class="span2 remove-margin-bottom form-align">
								<select id="moduleAccess_2" name="moduleAccess_2" class="selectBox-width-120" onChange="fnEnableModule_2();">
									<option value="">--选择--</option>
								    <option value="NS">不显示</option>
								    <option value="RO">只读</option>
								    <option value="RW">可编辑</option>
								</select>
							</div>
							<div class="span2 remove-margin-bottom checkbox">
								<label>
							      	<input type="checkbox" id="subModule_3" name="subModule_3"> 年度保养计划
							    </label>
							</div>
							<div class="span2 remove-margin-bottom checkbox">
								<label>
							      	<input type="checkbox" id="subModule_4" name="subModule_4"> 产生整年保养计划
							    </label>
							</div>
							<div class="span2 remove-margin-bottom checkbox">
								<label>
							      	<input type="checkbox" id="subModule_5" name="subModule_5"> 保养内容管理
							    </label>
							</div>
							<div class="span2 remove-margin-bottom"></div>
						</div>
						<div class="row-fluid remove-margin-bottom">
							<div class="span2 remove-margin-bottom">
								<label>
							      	<b>备件管理 :</b>
							    </label>
							</div>
							<div class="span2 remove-margin-bottom form-align">
								<select id="moduleAccess_3" name="moduleAccess_3" class="selectBox-width-120" onChange="fnEnableModule_3();">
									<option value="">--选择--</option>
								    <option value="NS">不显示</option>
								    <option value="RO">只读</option>
								    <option value="RW">可编辑</option>
								</select>
							</div>
							<div class="span2 remove-margin-bottom checkbox">
								<label>
							      	<input type="checkbox" id="subModule_6" name="subModule_6"> 备件管理
							    </label>
							</div>
							<!--  
							<div class="span2 remove-margin-bottom checkbox">
								<label>
							      	<input type="checkbox" id="subModule_18" name="subModule_18"> 出入库记录管理
							    </label>
							</div>
							<div class="span2 remove-margin-bottom checkbox">
								<label>
							      	<input type="checkbox" id="subModule_19" name="subModule_19"> 安全库存
							    </label>
							</div>
							-->
							<div class="span2 remove-margin-bottom"></div>
							<div class="span2 remove-margin-bottom"></div>
							<div class="span2 remove-margin-bottom"></div>
						</div>
						<div class="row-fluid remove-margin-bottom">
							<div class="span2 remove-margin-bottom">
								<label>
							      	<b>分析与报表 :</b>
							    </label>
							</div>
							<div class="span2 remove-margin-bottom form-align">
								<select id="moduleAccess_4" name="moduleAccess_4" class="selectBox-width-120" onChange="fnEnableModule_4();">
									<option value="">--选择--</option>
								    <option value="NS">不显示</option>
								    <option value="RO">只读</option>
								    <option value="RW">可编辑</option>
								</select>
							</div>
							<div class="span8">
								<div class="span2 remove-margin-bottom checkbox" >
									<label>
								      	<input type="checkbox" id="subModule_7" name="subModule_7"> 计划保养完成率统计
								    </label>
								</div>
								<div class="span2 remove-margin-bottom checkbox">
									<label>
								      	<input type="checkbox" id="subModule_8" name="subModule_8"> 设备可利用率统计
								    </label>
								</div>
								<div class="span2 remove-margin-bottom checkbox">
									<label>
								      	<input type="checkbox" id="subModule_9" name="subModule_9"> 平均维修间隔时间统计
								    </label>
								</div>
								<div class="span2 remove-margin-bottom checkbox">
									<label>
								      	<input type="checkbox" id="subModule_10" name="subModule_10"> 平均维修时间统计
								    </label>
								</div>
								<div class="span2 remove-margin-bottom checkbox">
									<label>
								      	<input type="checkbox" id="subModule_11" name="subModule_11" > 维修备件费用统计
								    </label>
								</div>
								<div class="span2 remove-margin-bottom checkbox" >
									<label>
								      	<input type="checkbox" id="subModule_17" name="subModule_17"> 维修备件使用数量统计
								    </label>
								</div>
						</div>
						</div>
						<div class="row-fluid remove-margin-bottom">
							<div class="span2 remove-margin-bottom">
								<label>
							      	<b>设备设施及文档管理 :</b>
							    </label>
							</div>
							<div class="span2 remove-margin-bottom form-align">
								<select id="moduleAccess_5" name="moduleAccess_5" class="selectBox-width-120" onChange="fnEnableModule_5();">
									<option value="">--选择--</option>
								    <option value="NS">不显示</option>
								    <option value="RO">只读</option>
								    <option value="RW">可编辑</option>
								</select>
							</div>
							<div class="span2 remove-margin-bottom checkbox">
								<label>
							      	<input type="checkbox" id="subModule_12" name="subModule_12"> 设备设施管理
							    </label>
							</div>
							<div class="span2 remove-margin-bottom checkbox">
								<label>
							      	<input type="checkbox" id="subModule_13" name="subModule_13"> 设备文档上传
							    </label>
							</div>
							<div class="span2 remove-margin-bottom"></div>
							<div class="span2 remove-margin-bottom"></div>
						</div>
						<div class="row-fluid remove-margin-bottom">
							<div class="span2 remove-margin-bottom">
								<label>
							      	<b>间接物料管理 :</b>
							    </label>
							</div>
							<div class="span2 remove-margin-bottom form-align">
								<select id="moduleAccess_6" name="moduleAccess_6" class="selectBox-width-120" onChange="fnEnableModule_6();">
									<option value="">--选择--</option>
								    <option value="NS">不显示</option>
								    <option value="RO">只读</option>
								    <option value="RW">可编辑</option>
								</select>
							</div>
							<div class="span2 remove-margin-bottom checkbox">
								<label>
							      	<input type="checkbox" id="subModule_14" name="subModule_14"> 间接物料管理
							    </label>
							</div>
							<!--  
							<div class="span2 remove-margin-bottom checkbox">
								<label>
							      	<input type="checkbox" id="subModule_21" name="subModule_21"> 安全库存
							    </label>
							</div>
							<div class="span2 remove-margin-bottom checkbox">
								<label>
							      	<input type="checkbox" id="subModule_20" name="subModule_20"> 出入库记录管理
							    </label>
							</div>
							-->
							<div class="span2 remove-margin-bottom"></div>
							<div class="span2 remove-margin-bottom"></div>
							<div class="span2 remove-margin-bottom"></div>
						</div>
						<div class="row-fluid remove-margin-bottom">
							<div class="span2 remove-margin-bottom">
								<label>
							      	<b>用户和权限管理 :</b>
							    </label>
							</div>
							<div class="span2 remove-margin-bottom form-align">
								<select id="moduleAccess_7" name="moduleAccess_7" class="selectBox-width-120" onChange="fnEnableModule_7();">
									<option value="">--选择--</option>
								    <option value="NS">不显示</option>
								    <option value="RO">只读</option>
								    <option value="RW">可编辑</option>
								</select>
							</div>
							<div class="span2 remove-margin-bottom checkbox">
								<label>
							      	<input type="checkbox" id="subModule_15" name="subModule_15"> 用户管理
							    </label>
							</div>
							<div class="span2 remove-margin-bottom checkbox">
								<label>
							      	<input type="checkbox" id="subModule_16" name="subModule_16"> 角色管理
							    </label>
							</div>
							<div class="span2 remove-margin-bottom"></div>
							<div class="span2 remove-margin-bottom"></div>
						</div>
					</section>
				</div>
			</div>
			<%if(((String)session.getAttribute("modAccess7")).equalsIgnoreCase("RW")){ %>
			<div class="row-fluid center-align">
	        	<div class="span12" >
	               	<div id="AddNewButtons">
						<button id ="savebtnSubmit" class="btn btn-inverse btn-geam-custom" type="button" onclick="javascript:fnAddNewRole();">新增</button>
						<button id ="UpdatebtnSubmit" class="btn btn-inverse btn-geam-custom" type="button" onclick="javascript:fnSaveRole();">保存</button>
						<button id ="deletebtnSubmit" class="btn btn-inverse btn-geam-custom" type="button" onclick="javascript:fnDeleteRole();">删除</button>
						<button id ="clearbtnSubmit" class="btn btn-inverse btn-geam-custom" type="button" onclick="javascript:fnClearRole();">重置</button>
					</div>
					<div class="no-show" id="nonAddNewButtons">
						<button id ="UpdatebtnSubmit" class="btn btn-inverse btn-geam-custom" type="button" onclick="javascript:fnSaveRole();">保存</button>
						<button id ="deletebtnSubmit" class="btn btn-inverse btn-geam-custom" type="button" onclick="javascript:fnDeleteRole();">删除</button>
						<button id ="clearbtnSubmit" class="btn btn-inverse btn-geam-custom" type="button" onclick="javascript:fnClearRole();">重置</button>
					</div>
				</div>
			</div>
			<%} %>
		</div>
	</div>
</div>
</form>
<form id="dummy_roleform"></form>
</body>
</html>