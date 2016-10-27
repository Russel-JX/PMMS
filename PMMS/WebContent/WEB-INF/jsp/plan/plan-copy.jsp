<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <% String contextPath = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PMMS - 复制保养计划</title>
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
    
    <link href="<%=contextPath%>/css/bootstrap/ie.css" rel="stylesheet" type="text/css"/>
	<link href="<%=contextPath%>/css/bootstrap/iids-docs.css" rel="stylesheet" type="text/css"/>
	<link href="<%=contextPath%>/css/bootstrap/bootstrap-duallistbox.css" rel="stylesheet" />
	<link href="<%=contextPath%>/css/common/geam-iids.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/iids/responsive.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/common/geam-table.css" rel="stylesheet"/>
    <link href="<%=contextPath%>/css/common/appstyle.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/appCss/PMMS.css" rel="stylesheet"/>

	<script src="<%=contextPath%>/js/components/requirejs/require.js"></script>
	<script src="<%=contextPath%>/js/iids/require.config.js" ></script>
	<script src="<%=contextPath%>/js/plan-copy.js" ></script>
	<script src="<%=contextPath%>/js/ajaxReqLoading.js" ></script>

	<style type="text/css">
		.well{
			margin-bottom:10px;
			padding:5px;
		}
	</style>
	
  </head>
  <body>
     <div class="navbar navbar-static-top">
		<jsp:include page="../common/Header.jsp"></jsp:include>
	</div>   

	<!-- 创建角色表单	开始 -->
	<div class="container">
		<div class="page-header">
			<div>
				<h1 class="voice voice-brand pull-left">制定全年保养计划</h1>
			</div>
		</div>
		<div class="row ui-sortable">
	        <div class="span12">            
				<section class="module">
					<div class="module-body">
						<div class="btn-group pull-left">
								<div class="pull-left">
								      <label class="col-sm-2 control-label pull-left icon-file" for="copyFrom">复制此年份的计划/Copy From： </label>
								      <select class="span1" id="copyFrom">
											<option>2014</option>
											<option>2015</option>
											<option>2016</option>
											<option>2017</option>
											<option>2018</option>
											<option>2019</option>
											<option>2020</option>
											<option>2021</option>
											<option>2022</option>
											<option>2023</option>
											<option>2024</option>
											<option>2025</option>
											<option>2026</option>
											<option>2027</option>
											<option>2028</option>
											<option>2029</option>
											<option>2030</option>
										</select>
								</div>
								<div class="pull-left span2">
									 <label class="icon-arrow-right" style="margin:12px;"></label>
									 <label class="icon-arrow-right" style="margin:12px;"></label>
									 <label class="icon-arrow-right" style="margin:12px;"></label>
									 <label class="icon-arrow-right" style="margin:12px;"></label>
								</div>
								<div class="pull-left">
								      <label class="col-sm-2 control-label pull-left  icon-paste" for="copyTo"> 到此年份的计划/To： </label>
								      <select class="span1" id="copyTo">
											<option>2014</option>
											<option>2015</option>
											<option>2016</option>
											<option>2017</option>
											<option>2018</option>
											<option>2019</option>
											<option>2020</option>
											<option>2021</option>
											<option>2022</option>
											<option>2023</option>
											<option>2024</option>
											<option>2025</option>
											<option>2026</option>
											<option>2027</option>
											<option>2028</option>
											<option>2029</option>
											<option>2030</option>
										</select>
								</div>
								<%if(((String)session.getAttribute("modAccess2")).equalsIgnoreCase("RW")){ %>
								    <button class="btn btn-primary pull-left" style="margin-left:20px;" type="submit"  id="btn-copy-plan">复制计划/Copy</button>
								<%} %>
						</div>
					</div>
					
				</section>
			</div>
		</div>
	</div>
	
	<!-- 复制计划确认modal	开始 -->
	<div id="modal-copy-confirm" class="modal modal3 hide fade" style="display: none;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
			<h3>复制计划</h3>
		</div>
		<div class="modal-body">
			<div class="module-body">
				<form style="margin-bottom:0;" class="form-horizontal">	
					<div class="well">	
					<div class="page-header">
						<div>
							<h3 id="confirm-msg" class="voice voice-brand pull-left">复制确认</h3>
						</div>
					</div>			
					</div>
				</form>
			</div>
	
		</div>
	 	<div class="modal-footer">
			<button href="#" class="btn btn-primary btn-copy-confirm">确定/Copy</button>
			<a href="#" class="btn" data-dismiss="modal">取消/Cancel</a>
		</div>
	</div>
	<!-- 复制计划确认modal	结束 -->
	
	<!-- 复制计划往年不能覆盖modal	开始 -->
	<div id="modal-not-cover" class="modal modal3 hide fade" style="display: none;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
			<h3>复制计划</h3>
		</div>
		<div class="modal-body">
			<div class="module-body">
				<form style="margin-bottom:0;" class="form-horizontal">	
					<div class="well">	
					<div class="page-header">
						<div>
							<h3 id="not-cover-msg" class="voice voice-brand pull-left">往年计划已实施，不能覆盖！</h3>
						</div>
					</div>			
					</div>
				</form>
			</div>
	
		</div>
	 	<div class="modal-footer">
			<button href="#" class="btn btn-primary"  data-dismiss="modal">确定/OK</button>
			<a href="#" class="btn" data-dismiss="modal">取消/Cancel</a>
		</div>
	</div>
	<!-- 复制计划往年不能覆盖modal	结束 -->
	
	<!-- 源计划不存在提示modal	开始 -->
	<div id="modal-copy-alert" class="modal modal3 hide fade" style="display: none;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
			<h3>复制计划提示</h3>
		</div>
		<div class="modal-body">
			<div class="module-body">
				<form style="margin-bottom:0;" class="form-horizontal">	
					<div class="well">	
					<div class="page-header">
						<div>
							<h3 id="no-plan-msg" class="voice voice-brand pull-left">没有计划/No Plans in this Year!</h3>
						</div>
					</div>			
					</div>
				</form>
			</div>
	
		</div>
	 	<div class="modal-footer">
			<button href="#" class="btn btn-primary" data-dismiss="modal">确定/OK</button>
			<a href="#" class="btn" data-dismiss="modal">取消/Cancel</a>
		</div>
	</div>
	<!-- 源计划不存在提示modal	结束 -->
	

</body>
</html>