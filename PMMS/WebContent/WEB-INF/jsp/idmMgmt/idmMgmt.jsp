<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>间接物料</title>

    <link href="<%=contextPath%>/css/bootstrap/ie.css" rel="stylesheet" type="text/css"/>
	<link href="<%=contextPath%>/css/bootstrap/iids-docs.css" rel="stylesheet" type="text/css"/>
	<link href="<%=contextPath%>/css/bootstrap/bootstrap-duallistbox.css" rel="stylesheet" />
	<link href="<%=contextPath%>/css/common/geam-iids.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/app/custom.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/iids/responsive.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/common/geam-table.css" rel="stylesheet"/>
    <link href="<%=contextPath%>/css/common/appstyle.css" rel="stylesheet"/>
	<link href="<%=contextPath%>/css/appCss/PMMS.css" rel="stylesheet"/>
<script src="<%=contextPath%>/js/components/requirejs/require.js" ></script>
<script src="<%=contextPath%>/js/iids/require.config.js" ></script>
<script src="<%=contextPath%>/js/idmMgmt.js" ></script>
<script src="<%=contextPath%>/js/ajaxReqLoading.js"></script>
</head>
<body>
<div class="navbar navbar-static-top">
	<jsp:include page="../common/Header.jsp"></jsp:include>
</div>
<div class="container">

    <div class="page-header">
        <div>
            <h1 class="voice voice-brand pull-left">间接物料管理</h1>
        </div>
    </div>


    <div class="row ui-sortable">
        <div class="span12">
            <section class="module">
                <div class="tabbable" id="workflowtab">
                    <ul class="nav nav-tabs">
                        <li class="active"><a href="#tab01" id="tab01Nav" data-toggle="tab">库存间接物料</a></li>
                        <li><a href="#tab02" id="tab02Nav" data-toggle="tab">出入库记录</a></li>
                        <li><a href="#tab03" id="tab03Nav" data-toggle="tab">安全库存</a></li>
                    </ul>
                    <!--<div class="tab-content">-->
                    <div class="tab-content">

                        <div class="tab-pane active" id="tab01">


                            <div id="myWorkflow1" class="pull-left">

                                 <div class="module-body">
                                    <form class="form-horizontal" style="margin-bottom:0;">
                                        <fieldset>
                                            <div class="sec-lft pull-left" style="width:50%">
                                                <div class="control-group multiselect">
                                                    <label style="width:200px;" class="control-label">选择类别:</label>
                                                    <div style="margin-left:140px;" class="controls">
                                                        <select class="idm_type" id="idmType">
                                                            <option value="E">费用</option>
                                                            <option value="P">固定资产</option>
                                                            <option selected value="all">全部</option>
                                                        </select>

                                                    </div>
                                                </div>
                                            </div>
                                           
                                        </fieldset>
                                    </form>
                                     
                                </div>
                               <%if(((String)session.getAttribute("modAccess6")).equalsIgnoreCase("RW")){ %>
                                <div class="btn-group pull-left">
                                    <button class="btn btn-primary pull-left" type="button"  id="addStockIdm">新增/Add</button>
                                    <button class="btn btn-primary pull-left" type="button"  id="editStockIdm">修改/Edit</button>
                                    <button class="btn btn-primary pull-left" type="button"  id="deleStockIdm">删除/Delete</button>
                                   <div class="pull-left" style="algin-left:100px;">
                                    	<button class="btn btn-primary" id = "calcSafetyNum">计算安全库存量</button>
                                   </div>
                                </div>
                                 <%} %>
                                  <div class="btn-group pull-left">
                                  <a data-placement="bottom" data-title="Export to Excel" rel="tooltip" class="btn btn-icon pull-left" href="#" data-original-title="" id="idm_download"><i class=" icon-download-alt"></i></a>
                                  </div>
                                
                                <div class="module-body pull-left" id="stockTb">
                                  
                                </div>
                                <br/>

                            </div>

                            <br>



                        </div>


                        <div class="tab-pane" id="tab02">
									<div>
										<form role="form" class="form-horizontal">
										    <fieldset>
											    <div class="pull-left" style="width:400px;">
													<div class="control-group">
														<label class="control-label"><b>时间FROM:</b></label>
														<div class="controls">
															<input type="text" class="datepicker" id="fromTime"/>
													    	<span class="add-on"><i class="icon-calendar"></i></span>
														</div>
													</div>
													<div class="control-group">
														<label class="control-label"><b>时间TO:</b></label>
														<div class="controls">
															<input type="text" class="datepicker" id="toTime"/>
													    	<span class="add-on"><i class="icon-calendar"></i></span>
														</div>
													</div>			　　　　　							
											    </div>
											    <div class="control-group pull-right" style="width:400px;margin-right:300px;" >
											    	<label class="control-label"><b>出库/入库:</b></label>
											    	<select class="span2" id="transType">
														<option value="">---全部---</option>
														<option value="1">入库</option>
													    <option value="2">出库</option>
													</select>	
											    </div>
										    </fieldset>
										</form>
									</div>
									<div id="myWorkflow2" class="pull-left">	
										<form class="form-search">	
											<div class="control-group" style="width:400px">
												<div class="controls pull-left" style="margin-left:10px;">
													<button type="button" class="btn btn-primary btn-lg btn-block" id="searchBtn">查询</button>
												</div>
												<div class="btn-group controls pull-left" style="margin-left:50px;">
													 <a data-placement="bottom" data-title="Export to Excel" rel="tooltip" class="btn btn-icon pull-left" href="#" data-original-title="" id="record_download"><i class=" icon-download-alt"></i></a>
												</div>
												<div class="btn-group pull-right">
												<button class="btn btn-primary pull-left" type="button" id="inBtn">入库/Stock-in</button>
                                    			<button class="btn btn-primary pull-left" type="button" id="outBtn">出库/Stock-out</button>
                                    			</div>
											</div>							
											
										</form>
										<br/>
										 <div class="module-body pull-left width100p" id="recordTb">
                                   
										</div>
									</div>	
                        
                        </div>

					<div class="tab-pane" id="tab03">
					
							<div id="myWorkflow3" class="pull-left">

								<div class="btn-group pull-left">
                                     <a data-placement="bottom" data-title="Export to Excel" rel="tooltip" class="btn btn-icon pull-left" href="#" data-original-title="" id="safety_download"><i class=" icon-download-alt"></i></a>
                                </div>
                                
                                <br/>
                                <div class="module-body pull-left width100p" id="saveStockTb">
                                    
                                </div>
                                <br/>

                            </div>


                        </div>

                    </div>
                </div>
                
                 <!-- In window-->
                    <div id="idmInInfo" class="modal modal3 hide fade" style="display: none;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
                            <h3>间接物料入库</h3>
                        </div>
                        <div class="modal-body">
                            <div class="module-body">
                            <form style="margin-bottom:0;" class="form-horizontal">
                           
                                    <div class="sec-lft pull-left" style="width:35%">
                         				<div class="control-group">
                                			 <label class="control-label"  style="width:100px;"><b>编码号:</b></label>
                                			 <div class="controls" style="margin-left:110px;width:50px">
                                     		 <input type="text" id="scanId" name="scanIn" maxlength="10"></input>
                                			</div>
                          				</div>
                          				
                          				<div class="control-group">
                          			 		<label class="control-label"  style="width:100px;"><b>数量:</b></label>
                          			 		 <div class="controls" style="margin-left:110px;">
                                     		 <input type="text" id="amount_in" maxlength="6"></input>
                                			</div>
                          			 	</div>

                          			</div>
                          			 <div class="sec-lft pull-left" style="width:65%">
                          			 	<div class="control-group">
                          			 		<label class="control-label"  style="width:160px;"><b>批次号:</b></label>
                          			 		 <div class="controls" style="margin-left:80px;">
                                     		 <input type="text" id="poIn" maxlength="30"></input>
                                			</div>
                          			 	</div>
                          			 	
                          			 	<div class="control-group">
                          			 		<label class="control-label"  style="width:160px;"><b>订货交付时间差（天）:</b></label>
                          			 		 <div class="controls" style="margin-left:80px;">
                                     		 <input type="text" id="LeadTime" maxlength="5"></input>
                                			</div>
                          			 	</div>
                          			 </div>
                          			 
                          </form>
						 <div id="scanInTb"></div> 		
                              
                           </div>

                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-primary" id = "saveBtn_in">入库</button>
                            <button class="btn btn-primary" id = "in_close" data-dismiss="modal">关闭</button>
                        </div>

                    </div>
                    
                     <!-- out window-->
                    <div id="idmOutInfo" class="modal modal3 hide fade" style="display: none;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
                            <h3>间接物料出库</h3>
                        </div>
                        <div class="modal-body">
                            <div class="module-body">

								<form style="margin-bottom:0;" class="form-horizontal">
                                    <fieldset>
                                    <div class="sec-lft pull-left" style="width:30%">
                         				<div class="control-group">
                                			 <label class="control-label"  style="width:60px;"><b>编码号:</b></label>
                                			 <div class="controls" style="margin-left:70px;">
                                     		 <input type="text" id="scanId_out" name="scanOut" maxlength="10"></input>
                                			</div>
                          				</div>
                          			</div>
                          			 <div class="sec-lft pull-left" style="width:32%">
                          			 	<div class="control-group">
                          			 		<label class="control-label"  style="width:80px;"><b>出库数量:</b></label>
                          			 		 <div class="controls" style="margin-left:90px;">
                                     		 <input type="text" id="amount_out" maxlength="6"></input>
                                			</div>
                          			 	</div>
                          			 </div>
                          			 <div class="sec-lft pull-right" style="width:38%">
                          			 	<div class="control-group">
                          			 		<label class="control-label"  style="width:100px;"><b>领料人(sso):</b></label>
                          			 		 <div class="controls" style="margin-left:100px;">
                                     		 <input type="text" id="receiver" maxlength="30"></input>
                                			</div>
                          			 	</div>
                          			 </div>
                          			</fieldset>
                         		 </form>
                         		 <br>
						 	<div id="scanOutTb"></div>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-primary" id = "deleBtn_out" >出库</button>
                            <button class="btn btn-primary" data-dismiss="modal" id="out_close">关闭</button>
                        </div>

                    </div>
                    
                    <!-- add idm window-->
                     <div id="addStockInfo" class="modal modal3 hide fade" style="display: none;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
                            <h3>新增间接物料</h3>
                        </div>
                        <div class="modal-body">
                            <div class="module-body">

                                <form style="margin-bottom:0;" class="form-horizontal">
                                    <fieldset>
                                        <!-- left -->
                                        <div class="sec-lft pull-left" style="width:50%">
                                            <div class="control-group">
                                                  <label class="control-label"  style="width:100px;"><b>类别:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <select class="idm_type" id=type_add>
                                                       <option value="0">---请选择---</option>
                                                       <option value="E">E---费用</option>
													   <option value="P">P---固定资产</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>子类:*</b></label>
                                                <div class="idm_sub_type" style="margin-left:110px;">
                                                    <select class="idm_subType" id=subType_add>
                                                    </select>
                                                     <button class="btn btn-primary pull-right" type="button"  id="addSubType">新增子类</button>
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>明细项目:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <select class="type_detail" id=type_detail_add>
                                                    </select>
                                                    <button class="btn btn-primary pull-right" type="button"  id="addDetailType">新增明细项</button>
                                                </div>
                                            </div>
                                            
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>名称:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="idmNm_add" type="text" placeholder="" class="span3" maxlength="30">
                                                </div>
                                            </div>

                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>规格:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="size_add" type="text" placeholder="" class="span3" maxlength="30">
                                                </div>
                                            </div>
                                           
                                        </div>
                                       
                                        
                                        <!-- right -->
                                        <div class="sec-rht pull-right" style="width:50%">
                                        	
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>单价（元）:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="price_add" type="text" placeholder="" class="span3" maxlength="10">
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>单位:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="measurement_add" type="text" placeholder="" class="span3" maxlength="5">
                                                </div>
                                            </div>
                                            <!-- 
                                             <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>数量:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="amount_add" type="text" placeholder="" class="span3" value="">
                                                </div>
                                            </div>
                                             -->
                                             <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>生产厂商:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="source_add" type="text" placeholder="" class="span3" maxlength="30">
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>存放地点:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="position_add" type="text" placeholder="" class="span3" maxlength="30">
                                                </div>
                                       		 </div>
                                             <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>备注:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="remark_add" type="text" placeholder="" class="span3" maxlength="40">
                                                </div>
                                       		 </div>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button href="#" class="btn btn-primary" id = "add_idm">确定</button>
                            <a href="#" class="btn btn-primary" data-dismiss="modal">取消</a>
                        </div>

                    </div>
                    
                     <!-- edit idm window-->
                    <div id="editStockInfo" class="modal modal3 hide fade" style="display: none;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
                            <h3>更新间接物料信息</h3>
                        </div>
                        <div class="modal-body">
                            <div class="module-body">

                                <form style="margin-bottom:0;" class="form-horizontal">
                                    <fieldset>
                                        <!-- left -->
                                        <div class="sec-lft pull-left" style="width:50%">
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>编码号:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="idmId_Edit" type="text" placeholder="" class="span3" disabled="true">
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>名称:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="idmNm_Edit" type="text" placeholder="" class="span3" maxlength="30">
                                                </div>
                                            </div>

                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>规格:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="size_Edit" type="text" placeholder="" class="span3" maxlength="30">
                                                </div>
                                            </div>
                                            
                                             <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>单价（元）:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="price_Edit" type="text" placeholder="" class="span3" maxlength="10">
                                                </div>
                                            </div>
                                            
                                             <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>单位:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="measurement_Edit" type="text" placeholder="" class="span3" maxlength="10">
                                                </div>
                                            </div>
                                            
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>生产厂商:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="source_Edit" type="text" placeholder="" class="span3" maxlength="30">
                                                </div>
                                            </div>
                                             <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>存放地点:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="position_Edit" type="text" placeholder="" class="span3" maxlength="30">
                                                </div>
                                       		 </div>
                                       		
                                        </div>
                                       
                                        
                                        <!-- right -->
                                        <div class="sec-rht pull-right" style="width:50%">
                                         <div class="control-group">
                                                  <label class="control-label"  style="width:100px;"><b>类别:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <select class="idm_type" id=type_Edit disabled>
                                                       <option value="0">---请选择---</option>
                                                       <option value="E">费用</option>
													   <option value="P">固定资产</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>子类:</b></label>
                                                 <div class="controls" style="margin-left:110px;">
                                                 <select class="idm_subType" id="subType_Edit"disabled>
                                                   </select>
                                                   </div>
                                            </div>
                                             <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>明细项目:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <select class="type_detail" id="type_detail_Edit"disabled>
                                                    </select>
                                                </div>
                                            </div>
                                             
                                             <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>建议安全库存量:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="sugSafetyNum_Edit" type="text" placeholder="" class="span3" value="200" disabled>
                                                </div>
                                            </div>
                                            
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>安全库存量:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="safetyNum_Edit" type="text" placeholder="" class="span3" maxlength="15">
                                                </div>
                                            </div>
                                             
                                              <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>备注:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="remark_Edit" type="text" placeholder="" class="span3" maxlength="30">
                                                </div>
                                       		 </div>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>

                        </div>
                        <div class="modal-footer">
                            <button href="#" class="btn btn-primary" id = "update_Stock">更新Update</button>
                            <a href="#" class="btn btn-primary" data-dismiss="modal" id="update_cancle">取消Cancle</a>
                        </div>

                    </div>
                       
                    
                    <!-- add idm type window-->
                     <div id="addTypeInfo" class="modal modal3 hide fade" style="display: none;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
                            <h3>新增物料子类</h3>
                        </div>
                        <div class="modal-body">
                            <div class="module-body">

                                <form style="margin-bottom:0;" class="form-horizontal">
                                    <fieldset>
                                        <div class="sec-rht" style="width:100%">
                                        	<div class="control-group">
                                                  <label class="control-label"  style="width:100px;"><b>类别:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <select class="idm_type" id="sub_type_add_type">
                                                       <option value="0">---请选择---</option>
                                                       <option value="E">E---费用</option>
													   <option value="P">P---固定资产</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>子类名:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="sub_type_add" type="text" placeholder="" class="span3" maxlength="30">
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>子类编号:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="sub_type_add_id" name="sub_type_add_id" type="text" placeholder="" class="span3" maxlength="10">
                                                </div>
                                            </div>
                                             <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>明细项目:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="sub_type_add_detail" type="text" placeholder="" class="span3" maxlength="100">
                                                </div>
                                            </div>
                                             <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>明细编号:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="sub_type_add_detailId" type="text" class="span3" value="01" disabled>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>

                        </div>
                        <div class="modal-footer pull-left">
                            <button href="#" class="btn btn-primary" id = "type_add_idm">确定</button>
                            <a href="#" class="btn btn-primary" data-dismiss="modal">取消</a>
                        </div>
                    </div>
                    
                      <!-- add idm detail type window-->
                     <div id="addDetailTypeInfo" class="modal modal3 hide fade" style="display: none;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
                            <h3>新增物料明细项目</h3>
                        </div>
                        <div class="modal-body">
                            <div class="module-body">

                                <form style="margin-bottom:0;" class="form-horizontal">
                                    <fieldset>
                                        <div class="sec-rht" style="width:100%">
                                        	<div class="control-group">
                                                  <label class="control-label"  style="width:100px;"><b>类别:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <select class="idm_type" id="detail_add_type">
                                                       <option value="0">---请选择---</option>
                                                       <option value="E">E---费用</option>
													   <option value="P">P---固定资产</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>子类:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                 	<select class="idm_subType" id=detail_sub_type_add>
                                                   </select>
                                                </div>
                                            </div>
                                            <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>明细项目:*</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="detail_add_detail" type="text" placeholder="" class="span3" maxlength="100">
                                                </div>
                                            </div>
                                             <div class="control-group">
                                                <label class="control-label"  style="width:100px;"><b>明细项目编号:</b></label>
                                                <div class="controls" style="margin-left:110px;">
                                                    <input id="detail_add_detailId" type="text" placeholder="" class="span3"disabled>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                </form>
                            </div>

                        </div>
                        <div class="modal-footer pull-left">
                            <button href="#" class="btn btn-primary" id = "detail_type_add">确定</button>
                            <a href="#" class="btn btn-primary" data-dismiss="modal">取消</a>
                        </div>
                    </div>
                    
   <!--po record  window --> 
   <div id="poDetailWindow" class="modal modal3 hide fade" style="display: none;">
		<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><i class=" icon-remove icon-white font13"></i></button>
		<h3>库存批次明细</h3>
		</div>
		<div class="modal-body">
		<div class="modal-body">
		 	<div id="poDetailTbDiv">
             </div>
		</div>
		   
		</div>
	</div>
                    
                    
<!--alert info -->
                <div class="alert alert-success alert-fixed-top fade" style="">
                    <button type="button" class="close" data-dismiss="alert">
                        <i class=" icon-remove icon-white"></i>
                    </button>
                    <button class="btn btn-small pull-right">Action</button>
                    <strong>Well done!</strong> You successfully read this important
                    alert message.
                </div>
</body>
</html>
