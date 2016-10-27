<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>设备设施</title>
<link href="<%=contextPath%>/css/bootstrap/ie.css" rel="stylesheet" type="text/css"/>
	<link href="<%=contextPath%>/css/bootstrap/iids-docs.css" rel="stylesheet" type="text/css"/>
	<link href="<%=contextPath%>/css/bootstrap/bootstrap-duallistbox.css" rel="stylesheet" />
	<link href="<%=contextPath%>/css/common/geam-iids.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/app/custom.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/iids/responsive.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/common/geam-table.css" rel="stylesheet"/>
    <link href="<%=contextPath%>/css/common/appstyle.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/appCss/PMMS.css" rel="stylesheet"/>
 <link href="<%=contextPath%>/css/app/datepicker.css" rel="stylesheet" type="text/css">
<script src="<%=contextPath%>/js/components/requirejs/require.js" ></script>
<script src="<%=contextPath%>/js/iids/require.config.js" ></script>
<script src="<%=contextPath%>/js/common/loadHomePageScripts.js"></script>
<script src="<%=contextPath%>/js/equipInfo.js" ></script>
  <script src="<%=contextPath%>/js/ajaxReqLoading.js"></script>
 <style type="text/css">
    	.dropdown-menu{
    		z-index:2000;
    	}
      </style>
</head>
<body>
<div class="navbar navbar-static-top">
	<jsp:include page="../common/Header.jsp"></jsp:include>
</div>
<div class="container">

    <div class="page-header">
        <div>
            <h1 class="voice voice-brand pull-left">设备设施管理</h1>
        </div>
    </div>


    <div class="row ui-sortable">
        <div class="span12">
            <section class="module">
                <div class="tabbable" id="workflowtab">

                    <!--<div class="tab-content">-->
                    <div class="tab-content">

                        <div class="tab-pane active" id="tab01">


                            <div id="myWorkflow1" class="pull-left">

                                <div class="module-body">
                                    <form class="form-horizontal" style="margin-bottom:0;">
                                        <fieldset>
                                            <div class="sec-lft pull-left" style="width:50%">


                                                <div class="control-group multiselect">
                                                    <label style="width:130px;" class="control-label">选择类别Type:</label>
                                                    <div style="margin-left:140px;" class="controls">
                                                        <select class="span3 equipType" id="equipTypeMainPg">
                                                            <option value="1">生产设备</option>
                                                            <option value="2">特种设备</option>
                                                            <option value="3">设施</option>
                                                            <option value="all">全部</option>
                                                        </select>

                                                    </div>
                                                </div>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div>
                                <%if(((String)session.getAttribute("modAccess5")).equalsIgnoreCase("RW")){ %>
                                <div class="btn-group pull-left">
                                    <button class="btn btn-primary pull-left " type="button"  id="addButton">新增/Add</button>
                                    <button class="btn btn-primary pull-left" type="button"  id="editButton">修改/Edit</button>
                                    <button class="btn btn-primary pull-left" type="button"  id="deleteButton">删除/Delete</button>
                                    <a data-placement="bottom" data-title="Export to Excel" rel="tooltip" class="btn btn-icon pull-left" href="#" data-original-title="" id="download"><i class=" icon-download-alt"></i></a>
                                </div>
                                <%} %>
                                <br></br>
                                <div class="module-body pull-left" id="equipInfoTbl">
                                    <div class="input-append flR pull-right">
                                    	<input type="text" class="input-medium search-query" data-filter-table="myWorkflowTable1" id='searchEquip'><button class="btn btn-icon"><i class="icon-search"></i></button>
                                    </div>
                                </div>
                                <br/>
                            </div>

                            <br>
                        </div>

                    </div>

                    <!-- add window-->
                    <div id="equipmentInfo" class="modal modal3 hide fade" style="display: none;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
                            <h3>添加设备</h3>
                        </div>
                        <div class="modal-body">
                            <div class="module-body">

                                <form style="margin-bottom:0;" class="form-horizontal">
                                    <fieldset>
                                        <!-- left -->
                                        <div class="sec-lft pull-left" style="width:50%">
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>设备类别:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <select class="equipType" id=equipTypeAdd>
                                                       <option value="1">生产设备</option>
													   <option value="2">特种设备</option>
												       <option value="3">设施</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>设备编号:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="equipId1" name="equipId1" type="text" maxlength="30" class="span3" value="">
                                                </div>
                                                <div class="controls" style="margin-left:50px;display:none;" id="divE">
                                                    <input id="errorId" type="text" placeholder="该设备已存在" class="span3">
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>固定资产编号:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="assetId1" type="text" maxlength="30" class="span3">
                                                </div>
                                            </div>

                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>设备名称:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <select class="equipName" id=equipNmAdd>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>设备型号:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="equipModel1" type="text" maxlength="30" class="span3">
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>出厂日期:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <div class="span3 pull-left mrgzeroimp pic_advance">
                                                        <div class="input-append date datepicker-bootstrap datepicker-bootstrap-to datepicker-bootstrap-csa datePicker">
                                                            <input type="text" placeholder="" class="span1 date-input disabled csa-date-to datepickerinp datepicker"  id="factoryDate1">
                                                            <span class="add-on"><i class="icon-calendar"></i></span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>出厂编号:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="factoryNo1" type="text" placeholder="" class="span3" maxlength="30">
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>制造厂:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="source1" type="text" placeholder="" class="span3" maxlength="30">
                                                </div>
                                            </div>

                                        </div>
                                        <!-- right -->
                                        <div class="sec-rht pull-right" style="width:50%">
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>耗电总容量:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="powerConsum1" type="text" placeholder="" class="span3" maxlength="30">
                                             	</div>
                                             </div>
                                             <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>安装日期:</b></label>
                                            	 <div class="controls" style="margin-left:110px;">
                                                    <div class="span3 pull-left mrgzeroimp pic_advance">
                                                        <div class="input-append date datepicker-bootstrap datepicker-bootstrap-to datepicker-bootstrap-csa datePicker" >
                                                            <input type="text" placeholder="" class="span1 date-input disabled csa-date-to datepickerinp datepicker" id="installdate1">
                                                            <span class="add-on"><i class="icon-calendar"></i></span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                             <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>原值:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="oriPrice1" type="text" placeholder="" class="span3" maxlength="30">
                                                </div>
                                           </div>     
                                                <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>现值:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="currPrice1" type="text" placeholder="" class="span3" maxlength="30">
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>外形尺寸（MM):</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="size1" type="text" placeholder="" class="span3" maxlength="30">
                                                </div>
                                            </div>
                                             <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>重量（KG）:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="weight1" type="text" placeholder="" class="span3" maxlength="7">
                                                </div>
                                            </div>
                                             <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>是否使用:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="inuse1" type="checkbox" name="checkbox_add">
                                                </div>
                                            </div>
                                           
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>使用部门:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                     <select class="useDepart" id="useDepart1">
                                                       
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>备注:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="remark1" type="text" placeholder="" maxlength="40">
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" id = "saveBtn">保存Save</button>
                            <a href="#" class="btn btn-primary" data-dismiss="modal">取消Cancle</a>
                        </div>

                    </div>

                    <!--edit window-->
<div id="equipmentUpdate" class="modal modal3 hide fade" style="display: none;">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
        <h3>编辑设备信息</h3>
    </div>
    <div class="modal-body">
        <div class="module-body">

            <form style="margin-bottom:0;" class="form-horizontal">
                <fieldset>
                    <!-- left -->
                    <div class="sec-lft pull-left" style="width:50%">
                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>设备类别:</b></label>
                            <div class="controls" style="margin-left:110px;">
                               <select class="equipType" id="equipTypeEdit">
                                    <option value="1">生产设备</option>
									<option value="2">特种设备</option>
									<option value="3">设施</option>
                                </select>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>设备编号.:</b></label>
                            <div class="controls" style="margin-left:110px;">
                                <input id="equipId2" type="text" placeholder="" class="span3" disabled="disabled">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>固定资产编号:</b></label>
                            <div class="controls" style="margin-left:110px;">
                                <input id="assetId2" type="text" placeholder="" class="span3" maxlength="30">
                            </div>
                        </div>

                         <div class="control-group">
                              <label class="control-label"  style="width:100px;"><b>设备名称:</b></label>
                                   <div class="controls" style="margin-left:110px;">
                                         <select class="equipName" id="equipNmEdit">
                                          </select>
                                   </div>
                         </div>
                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>设备型号:</b></label>
                            <div class="controls" style="margin-left:110px;">
                                <input id="equipModel2" type="text" placeholder="" class="span3" maxlength="30">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>出厂日期:</b></label>
                            <div class="controls" style="margin-left:110px;">
                                <div class="span3 pull-left mrgzeroimp pic_advance">
                                    <div class="input-append date datepicker-bootstrap datepicker-bootstrap-to datepicker-bootstrap-csa datePicker" >
                                        <input type="text" placeholder="" class="span1 date-input disabled csa-date-to datepickerinp datepicker" data-value="96.7" id="factoryDate2">
                                        <span class="add-on"><i class="icon-calendar"></i></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>出厂编号:</b></label>
                            <div class="controls" style="margin-left:110px;">
                                <input id="factoryNo2" type="text" placeholder="" class="span3" maxlength="30">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>制造厂:</b></label>
                            <div class="controls" style="margin-left:110px;">
                                <input id="source2" type="text" placeholder="" class="span3" maxlength="30">
                            </div>
                        </div>

                    </div>
                    <!-- right -->
                    <div class="sec-rht pull-right" style="width:50%">
                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>耗电总容量:</b></label>
                            <div class="controls" style="margin-left:110px;">
                                <input id="powerConsum2" type="text" placeholder="" class="span3" maxlength="30">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>安装日期:</b></label>
                            <div class="controls" style="margin-left:110px;">
                                <div class="span3 pull-left mrgzeroimp pic_advance">
                                    <div class="input-append date datepicker-bootstrap datepicker-bootstrap-to datepicker-bootstrap-csa datePicker">
                                        <input type="text" placeholder="" class="span1 date-input disabled csa-date-to datepickerinp datepicker" data-value="96.7"  id="installDate2">
                                        <span class="add-on"><i class="icon-calendar"></i></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>原值:</b></label>
                            <div class="controls" style="margin-left:110px;">
                                <input id="oriPrice2" type="text" placeholder="" class="span3" maxlength="30">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>现值:</b></label>
                            <div class="controls" style="margin-left:110px;">
                                <input id="currPrice2" type="text" placeholder="" class="span3" maxlength="30">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>外形尺寸（MM):</b></label>
                            <div class="controls" style="margin-left:110px;">
                                <input id="size2" type="text" placeholder="" class="span3" maxlength="30">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>重量（KG）:</b></label>
                            <div class="controls" style="margin-left:110px;">
                                <input id="weight2" type="text" placeholder="" class="span3" maxlength="7">
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>是否使用:</b></label>
                            <div class="inuse2" style="margin-left:110px;">
                                <input id="inuse2" type="checkbox" name="checkbox_edit">
                            </div>
                        </div>

                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>使用部门:</b></label>
                            <div class="controls" style="margin-left:110px;">
                                 <select class="useDepart" id="useDepart2">                   
                                 </select>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label"  style="width:100px;"><b>备注:</b></label>
                            <div class="controls" style="margin-left:110px;">
                                <input id="remark2" type="text" placeholder="" class="span3" maxlength="40">
                            </div>
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>

    </div>
    <div class="modal-footer">
        <button href="#" class="btn btn-primary" id = "updateBtn">更新Update</button>
        <a href="#" class="btn btn-primary" data-dismiss="modal" id="cancle">取消Cancle</a>
    </div>

</div>  
    
   <!-- download file window --> 
   <div id="downloadFileWindow" class="modal modal3 hide fade" style="display: none;">
		<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
		<h3>文档明细列表</h3>
		</div>
		<div class="modal-body">
		<div class="modal-body">
		 <div id="epDocTb">
                 </div>
		</div>
		   
		</div>
	</div>
   
           
        </div>
    </div>
</div>

<!--alert info -->
<div class="alert alert-success alert-fixed-top fade" style="">
    <button type="button" class="close" data-dismiss="alert"><i class=" icon-remove icon-white"></i></button>
    <button class="btn btn-small pull-right">Action</button>
    <strong>Well done!</strong> You successfully read this important alert message.
</div>
</body>
</html>
