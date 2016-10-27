/**
 * ============================================================
 * File : EquipInfoService.java
 * Description : 
 * 
 * Package : com.ge.pmms.service
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 14, 2015
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
import com.ge.pmms.po.EquipInfo;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 14, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public interface EquipInfoService {
	/**
	 * 通过该设备类型获取设备信息
	 * @Author:  iGATE
	 * @param equipInfo 设备信息对象
	 * @return ServiceReturns
	 * @
	 * @Description:
	 */
	public ServiceReturns getEquipInfoByType(EquipInfo equipInfo);
	/**
	 * 根据设备编号获取设备信息
	 * @Author: iGATE 
	 * @param equipId 设备编号
	 * @return EquipInfo
	 * @
	 * @Description:
	 */
	public EquipInfo getEquipInfoByEpId(String equipId);
	/**
	 * 保存设备信息
	 * @Author: iGATE 
	 * @param jsonObj json对象
	 * @return ServiceReturns
	 * @
	 * @Description:
	 */
	public ServiceReturns saveEquip(JSONObject jsonObj);
	/**
	 * 根据设备编号删除设备信息
	 * @Author: iGATE 
	 * @param equipInfo 设备信息对象
	 * @return ServiceReturns
	 * @
	 * @Description:
	 */
	public ServiceReturns deleteEquipByEpId(EquipInfo equipInfo);
	/**
	 * 更新设备信息
	 * @Author: iGATE 
	 * @param json json对象
	 * @return ServiceReturns
	 * @
	 * @Description:
	 */
	public ServiceReturns updateEquip(JSONObject json);
	/**
	 * 根据设备编号更新设备表的文档关联
	 * @Author: iGATE 
	 * @param equipDocId 设备文档ID
	 * @param equipId 设备编号
	 * @return int 更新影响的记录数
	 * @Description:
	 */
	public int updateEpDocByEpID(String equipDocId,String equipId);
	/**
	 * 根据设备名称编号更新设备表的文档关联
	 * @Author: iGATE 
	 * @param equipDocId 设备文档ID
	 * @param equipNmId 设备名称编号
	 * @return int 更新影响的记录数
	 * @Description:
	 */
	public int updateEpDocByEpModelId(String equipDocId,String equipModel);
	/**
	 * 获取五大部门信息
	 * @Author: iGATE 
	 * @param 
	 * @param 
	 * @return ServiceReturns
	 * @Description:
	 */
	public ServiceReturns getDeptInfo(); 
	/**
	 * 根据设备型号获取设备编号
	 * @Author: iGATE 
	 * @param epModel 设备型号
	 * @param 
	 * @return List 
	 * @Description:
	 */
	public List getEquipIdByEpModel(String epModel);
	/**
	 * 设备设施管理页面导出excel
	 * @Author: iGATE 
	 * @param 
	 * @param 
	 * @return HSSFWorkbook 
	 * @Description:
	 */
	public HSSFWorkbook  exportToExcel();
	
	/**
	 * 根据设备名称模糊查询设备
	 * @Author: iGATE 
	 * @param 
	 * @param 
	 * @return ServiceReturns 
	 * @Description:
	 */
	public ServiceReturns getEquipsByEQName(String eqName);
}
