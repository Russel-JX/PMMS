//PMMS项目中，公用逻辑的js方法定义
define(['jquery'],function(){
	
	pmms = function(){
		var pmms = {
			    version: "1.0.0"
		};
		/*
		 * 根据设备类型加载设备名称列表
		 * @param equipType
		 * 		变量类型：字符串 
		 * 		说明：设备类型 如生产设备：'1',特殊设备：'2',设施：'3'。
		 * @param ele
		 * 		变量类型：js对象 
		 * 		说明：供用户选择设备名称的select元素  
		 */
		//加载设备名称
		pmms.getEquipNames = function getEquipNames(equipType,ele){
			$.ajax({
	    		url:'/PMMS/restful/maintItem/getEquipNames',
				type:'post',
				dataType:'json',
				cache:false,
				data:{
					equipType:equipType
				},
				beforeSend:function(xhr){
					//$("#loading").show();
				},
				complete:function (XMLHttpRequest, textStatus) {
					//$("#loading").hide();
				},
				success:function(backData,status){
					pmms.buildDropDown(backData.data.list,ele);
				},
				error:function(XmlHttpRequest){
					alert('error!!!');
				}
	    	});
		};
		/*
		 * 给下拉列表注入选项。
		 * 注：此方法值适用于设备名称类表下拉的使用。
		 * @param data
		 * 		变量类型：JSON字符串 
		 * 		说明：后台查出的数组集合。
		 * @param ele
		 * 		变量类型：js对象 
		 * 		说明：供用户选择设备名称的select元素。可以是多个select下拉对象组成的数组。
		 */
		pmms.buildDropDown = function buildDropDown(data,ele){
			//alert("in buildDropDown..."+targetId);
			for(var i=0;i<ele.length;i++){
				$(ele[i]).empty();
				if(data.length>0){
					$(ele[i]).append("<option value=''> --- 请选择 --- </option>");
					$.each(data,function(index,item){
						var selectOption = "<option value='"+item.equipmentNameId+"'>"+item.equipmentName+"</option>";
						$(ele[i]).append(selectOption);
					});
				}
			}
		}
		
		/*
		 * 将一个下拉列表的所选option赋给另一个下拉列表。
		 * 注：目标下拉的选项只有一个。
		 * @param srcEle
		 * 		变量类型：js对象  
		 * 		说明：原select对象。
		 * @param tgtEle
		 * 		变量类型：js对象 
		 * 		说明：原目标select对象。
		 */
		pmms.buildDropDownOne = function buildDropDownOne(srcEle,tgtEle){
			$(tgtEle).empty();
			var selectOption = "<option value='"+$(srcEle).find("option:selected").val()+"'>"+$(srcEle).find("option:selected").text()+"</option>";
			$(tgtEle).append(selectOption);
		}
		
		/**
		 * chart图表数据表格导出
		 * @param attrName
		 * 		变量类型：字符串 
		 * 		说明：要导出的报表类型。
		 * 		PM：保养计划完成率，EA：设备可利用率，
		 * 		MTBF：平均维修间隔时间，MTTR：平均维修时间，
		 * 		MSPC:维修配件费用,MSPQU:维修配件使用数量
		 * @param queryTypeFlag
		 * 		变量类型：字符串 
		 * 		说明：统计（查询）的条件。DEPT_ONE_YEAR：按部门查询一年的数据，
		 * 		DEPT_FIVE_YEAR：按部门查询五年的数据，
		 * 		SINGLE_EQ：按单个设备,SINGLE_SP：按单个备件。
		 */
		pmms.chartTableExport = function chartTableExport(attrName,queryTypeFlag){
			window.location.href="/PMMS/restful/chart/exportDataToExcel?attrName="+attrName+"&queryTypeFlag="+queryTypeFlag;
		}
		
		
		
		//加载设备型号
		pmms.getEquipModels = function getEquipModels(equitTypeVal,tarObj){
			$.ajax({
	    		url:'/PMMS/restful/EquipInfo/getEquipInfo',
				type:'post',
				dataType:'json',
				cache:false,
				data:{
					equipType:equitTypeVal
				},
				beforeSend:function(xhr){
					//$("#loading").show();
				},
				complete:function (XMLHttpRequest, textStatus) {
					//$("#loading").hide();
				},
				success:function(backData,status){
					var data = backData.data.detail;
					tarObj.empty();
						if(data.length>0){
							tarObj.append("<option value=''> --- 请选择 --- </option>");
							$.each(data,function(index,item){
								var selectOption = "<option value='"+item.equipModel+"'>"+item.equipModel+"</option>";
								tarObj.append(selectOption);
							});
						}
				},
				error:function(XmlHttpRequest){
					alert('error!!!');
				}
	    	});
		};
		
		
		//加载设备型号
		pmms.getEquipNo = function getEquipNo(equitTypeVal,tarObj){
			$.ajax({
	    		url:'/PMMS/restful/EquipInfo/getEquipInfo',
				type:'post',
				dataType:'json',
				cache:false,
				data:{
					equipType:equitTypeVal
				},
				beforeSend:function(xhr){
					//$("#loading").show();
				},
				complete:function (XMLHttpRequest, textStatus) {
					//$("#loading").hide();
				},
				success:function(backData,status){
					var data = backData.data.detail;
					tarObj.empty();
						if(data.length>0){
							tarObj.append("<option value=''> --- 请选择 --- </option>");
							$.each(data,function(index,item){
								var selectOption = "<option value='"+item.equipId+"'>"+item.equipId+"</option>";
								tarObj.append(selectOption);
							});
						}
				},
				error:function(XmlHttpRequest){
					alert('error!!!');
				}
	    	});
		};
		
		//加载部门信息
		pmms.getDeptInfo = function getDeptInfo(SelectObj){
			$.ajax({
				type:"POST",
				url:"../restful/EquipInfo/getDeptInfo",
				dataType:"json",
				async:true,
				success:function(jsonData){
					if(jsonData.success){
						var deptList = jsonData.data.detail;
						SelectObj.empty();
						SelectObj.append('<option value="">-----全部-----</option>');
						$.each(deptList,function(index,item){
							//console.log(item.deptId);
							//console.log(item.deptNm);
							SelectObj.append("<option value='" + item.deptId + "'>" + item.deptNm + "</option>");
						});
						//console.log(deptList);
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert("error"+textStatus);
				}
			});
		};
		
		/*
		 * 判断用户输入的SSO,是否是维修team人员
		 * @param {Object} jqueryObj
		 */
		pmms.verifySSO = function verifySSO(jqueryObj,jquerySpan){
			jqueryObj.unbind('blur').on('blur',function(){
				var sso = jqueryObj.val();
				if(pmms.isEmpty(sso)){
					alert("SSO不能为空，请输入!");
					return false;
				}
				var ssoReg = /^[0-9]*$/;
				var ssoRegFlag = ssoReg.test(sso);
				if(!ssoRegFlag){
					jqueryObj.val("");
					alert("SSO输入无效，请重新输入!!!");
					return false;
				}
				$.ajax({
					type:"POST",
					url:"../restful/workOrder/isMaintUser",
					dataType:"json",
					async:false,
					data:{
						sso:sso
					},
					success:function(jsonData){
						if(jsonData.success){
							var maintUser = jsonData.data.maintUser;
							if(maintUser != null){
								jquerySpan.text("("+maintUser.lastName+" "+maintUser.firstName+")");
							}else{
								jqueryObj.val("");
								jquerySpan.text("");
								alert("SSO不正确，请重新输入");
							}
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						alert(textStatus);
					}
				});
			});
		};
		
		/**
		 * 获取故障种类列表
		 * @param {Object} jqueryObj
		 */
		pmms.getFaultType = function getFaultType(SelectObj){
			$.ajax({
				type:"POST",
				url:"../restful/workOrder/faultType",
				dataType:"json",
				async:true,
				success:function(jsonData){
					if(jsonData.success){
						var faultTypes = jsonData.data.typeList;
						SelectObj.empty();
						$.each(faultTypes,function(index,item){
							SelectObj.append("<option value='" + item.faultId + "'>" + item.faultNM + "</option>");
						});
					}
				},
				error:function(XMLHttpRequest, textStatus, errorThrown){
					alert("error"+textStatus);
				}
			});
		}
		
		/**
		 * 时间对比
		 * @param {Object} startDate
		 * @param {Object} endDate
		 */
        pmms.compareDate = function compareDate(startDate, endDate){
            if (startDate != "" && endDate != "") {
                var arrStart = new Array();
                arrStart = startDate.split("/");
                var arrEnd = new Array();
                arrEnd = endDate.split("/");
                var fromDate = new Date();
                fromDate.setFullYear(arrStart[2], arrStart[0] - 1, arrStart[1]);
                var toDate = new Date();
                toDate.setFullYear(arrEnd[2], arrEnd[0] - 1, arrEnd[1]);
                if (toDate >= fromDate) {
                    return true;
                }
                else {
                    alert("开始时间应该小于结束时间.");
                    return false;
                }
            }
            else {
                return true;
            }
        };
		
		pmms.isEmpty = function isEmpty(str) {
		    if (typeof (str) == "undefined" || str == '' || str== null) {
		    	return true;
		    }
		    return false;
		};
		
		return pmms;
	}();
	return pmms;
});





