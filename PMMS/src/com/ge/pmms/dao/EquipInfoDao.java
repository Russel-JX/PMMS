/**
 * ============================================================
 * File : EquipInfoDao.java
 * Description : 
 * 
 * Package : com.ge.pmms.dao
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 16, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.dao;

import java.util.List;

import com.ge.pmms.base.BaseDao;
import com.ge.pmms.po.DeptInfo;
import com.ge.pmms.po.EquipInfo;



/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 16, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public interface EquipInfoDao extends BaseDao<EquipInfo>{
	public List<EquipInfo> getEquipInfoByType(EquipInfo equipInfoReq);
	public List<EquipInfo> getAllEquipInfo(EquipInfo equipInfoReq);
	public EquipInfo getEquipInfoByEpId(String equipId);

	public int deleteEquipByEpId(EquipInfo equipInfoReq);
	public int updateEquip(EquipInfo equipInfoReq);
	public int updateEpDocByEpId(String equipDocId,String para);
	
	public int updateEpDocByEpModelId(String equipDocId,String equipModel);
	public List<DeptInfo> getDeptInfo();
	public List getEquipIdByEpModel(String epModel);
	
	//根据设备名称模糊查询设备
	public List<EquipInfo> getEquipsByEQName(String eqName);
}
