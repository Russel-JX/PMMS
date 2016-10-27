<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath();%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>设备文档上传</title>

    <link href="<%=contextPath%>/css/bootstrap/ie.css" rel="stylesheet" type="text/css"/>
	<link href="<%=contextPath%>/css/bootstrap/bootstrap-duallistbox.css" rel="stylesheet" />
	<link href="<%=contextPath%>/css/common/geam-iids.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/common/geam-table.css" rel="stylesheet">
	<link href="<%=contextPath%>/css/app/custom.css" rel="stylesheet"/>
    <link href="<%=contextPath%>/css/common/appstyle.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/appCss/PMMS.css" rel="stylesheet"/>


	<script src="<%=contextPath%>/js/components/requirejs/require.js" ></script>
	<script src="<%=contextPath%>/js/iids/require.config.js" ></script>
	<script src="<%=contextPath%>/js/equipDocMgmt.js" ></script>
	<script src="<%=contextPath%>/js/common/loadHomePageScripts.js"></script>

</head>
<body>
<div class="navbar navbar-static-top">
	<jsp:include page="../common/Header.jsp"></jsp:include>
</div>
<div class="container-fluid">
    
    <div class="page-header">
        <div>
            <h1 class="voice voice-brand pull-left">设备文档列表</h1>
        </div>
    </div>
    
    <div class="module-group">
	    <section class="module">
			<div class="module-body">
			 <div class="span11" style="margin-bottom:5px;">
				 <div class="input-append  pull-right" >
				     <input type="text" class="input-medium search-query" data-filter-table="EquipDocListTable"></input>
				 </div>
				 <%if(((String)session.getAttribute("modAccess5")).equalsIgnoreCase("RW")){ %>
				    <div class="pull-left">
				        <button class="btn btn-primary " type="button"   id="uploadBtn">新增/Add</button>
				        <button class="btn btn-primary " type="button" id="editBtn">修改/Edit</button>
				        <button class="btn btn-primary " type="button"   id="delBtn">删除/Delete</button>
				    </div>
				  <%} %>  
			  </div>
			    <div class="span11" >
                       <table id="EquipDocListTable" class="table table-bordered dataTable table-responsive" data-table-name="EquipDocListTable"></table>
		       </div>
			</div>
		</section>
    </div>
    
    
    <!-- upload window start -->
    <div id="uploadWindow" class="modal modal3 hide fade" style="height:500px;">
    <div class="modal-header">
        <button type="button" class="close closeBtn" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
        <h3>文档上传</h3>
    </div>
    <form class="form-horizontal docsForm" action="" enctype="multipart/form-data" id="uploadForm" method="post">
        <div class="module-body">
            <!-- left -->
            <div class="row">
            <div class="span4">
            <fieldset>
                        <div class="row">
                         <div class="control-group">
                            <label class="control-label pull-left"  >附件数:</label>
                            <div class="span4 pull-right">
                            <button class="btn btn-primary" type="button"  id="addFile">增加/Add</button>
                            <button class="btn btn-primary" type="button" data-toggle="modal"  data-target="#modal-lstDoc" id="viewUploadlist" >已上传文档列表/Uploaded Items</button>
                            </div>
                           </div>
                        </div>

                        <div class="row">
                        <div class="span2 control-group" id="fileGroups">
                            <div class="span8">
                            <input class="input-file" type="file" name="file" id="file"/>
                            </div>
                        </div>
                        </div>
             </fieldset>
            </div>
            
            <!-- right -->
            <div class="span4">
            <div class="sec-rht pull-right">
                    <fieldset>
                <div class="control-group">
                    <label class="control-label"><b>选择文档类型:</label>
                    <div class="controls">
                        <select class="span2" id="docType" name="docType">
                            <option value="1">全厂</option>
                            <option value="2">设备型号</option>
                            <option value="3">设备编号</option>
                        </select>
                    </div>
                </div>
                <div id="divT" class="control-group" style="display:none;">
                    <label class="control-label"  ><b>选择设备类别/Equip Category:</b></label>
                    <div class="controls">
                        <select id="equipCategory">
                        	<option value="1">生产设备</option>
							<option value="2">特种设备</option>
							<option value="3">设施</option>
                        </select>
                    </div>                  
                </div>
                   
                    <div id="divM" class="control-group" style="display:none;">
                         <label class="control-label" ><b>选择设备型号/Equip Model:</b></label>
                         <div class="controls">
                        <select id="selEquipType" name="equipModelId">
                        </select>
                        </div>
                    </div>
                   
                     <div id="divE" class="control-group" style="display:none;">
                        <label class="control-label"  ><b>选择设备编号/Equip No:</b></label>
                         <div class="controls">
                        <select id="selEquipNo" name="equipNo">
                        </select>
                        </div>
                    </div>
                    
               
                <div class="control-group">
                    <label class="control-label"><b>备注/Memo:</b></label>
                    <div class="controls">
                        <textarea rows="4" cols="" class="span2" name="desc" id="memo"></textarea>
                    </div>
                </div>
           
            </fieldset>

            
        </div>
   </div>
   </div>
   </div>
</form>
    <div class="modal-footer pull-left" >
        <button type="button" class="btn btn-primary " id = "saveBtn">开始上传/Upload</button>
        <button class="btn btn-primary cancelBtn" data-dismiss="modal">取消/Cancel</button>
    </div>
    
</div>
<!-- upload window end -->


	<div id="modal-lstDoc" class="modal modal3 hide fade" style="display: none;">
		<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
		<h3>文档明细列表</h3>
		</div>
		<div class="modal-body">
		    <div class="span11" >
		    <input type="hidden" id="equpDocId"></input>
                     <table id="equipDocDetails" class="table table-striped table-bordered table-condensed" data-table-name="equipDocDetails">
                     </table>
		    </div>
		</div>
	</div>
</div>
</body>
</html>