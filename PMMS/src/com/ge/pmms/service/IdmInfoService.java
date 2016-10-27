/**
 * ============================================================
 * File : IdmInfoService.java
 * Description : 
 * 
 * Package : com.ge.pmms.service
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 26, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.service;


import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.json.JSONObject;

import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.po.IdmInfo;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 26, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public interface IdmInfoService {
	/**
	 * 根据间接物料类别获取间接物料信息
	 * @Author:  iGATE
	 * @param idmInfo 间接物料对象
	 * @return ServiceReturns
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns getIdmInfoByType(IdmInfo idmInfo);
	/**
	 * 根据间接物料编号获取间接物料信息
	 * @Author:  iGATE
	 * @param idmInfo 间接物料对象
	 * @return ServiceReturns
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns getIdmInfoByIdmId(IdmInfo idmInfo);
	/**
	 * 更新间接物料信息
	 * @Author:  iGATE
	 * @param json json对象
	 * @return ServiceReturns
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns updateIdmInfo(JSONObject json);
	/**
	 * 获取间接物料的所有出入库记录
	 * @Author:  iGATE
	 * @param 
	 * @return ServiceReturns
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns getAllIdmTransRecords(); 
	/**
	 * 更新间接物料的出入库记录信息
	 * @Author:  iGATE
	 * @param json json对象
	 * @return ServiceReturns
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns updateTransRecord(JSONObject json); 
	/**
	 * 往库存表插入一条记录
	 * @Author:  iGATE
	 * @param json json对象
	 * @return ServiceReturns
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns saveIdm(JSONObject json); 
	/**
	 * 根据间接物料编号删除间接物料
	 * @Author:  iGATE
	 * @param idmInfo 间接物料对象
	 * @return ServiceReturns
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns deleIdmbyIdmId(IdmInfo idmInfo); 
	/**
	 *获取不满足安全库存条件的间接物料信息
	 * @Author:  iGATE
	 * @param 
	 * @return ServiceReturns
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns getUnSaftyIdm();
	/**
	 * 间接物料基本信息页面导出excel
	 * @Author:  iGATE
	 * @param 
	 * @return HSSFWorkbook
	 * @throws 
	 * @Description:
	 */
	public HSSFWorkbook  idmInfoExportToExcel();
	/**
	 * 间接物料安全管理页面导出excel
	 * @Author:  iGATE
	 * @param 
	 * @return HSSFWorkbook
	 * @throws 
	 * @Description:
	 */
	public HSSFWorkbook  idmSafetyExportToExcel();
	/**
	 * 间接物料安全管理页面发短信跟邮件提醒
	 * @Author:  iGATE
	 * @param idmInfoLst 安全库存管理页面的物料信息列表
	 * @return 
	 * @throws 
	 * @Description:
	 */
	public void emailSafetyIdmReminder(List<IdmInfo> idmInfoLst)throws Exception;
	/**
	 * 计算间接物料的安全库存量
	 * @Author:  iGATE
	 * @param
	 * @return ServiceReturns
	 * @throws 
	 * @Description:
	 */
	public ServiceReturns calcSafetyNum();
}
