/**
 * ============================================================
 * File : SparePartInfoDao.java
 * Description : 
 * 
 * Package : com.ge.pmms.dao
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 1, 2015
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
import com.ge.pmms.po.EquipInfo;
import com.ge.pmms.po.SparePartInfo;

/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Feb 1, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public interface SparePartInfoDao extends BaseDao<SparePartInfo> {
	public List<SparePartInfo> getSparePartInfo(SparePartInfo sparePartInfo);
	public SparePartInfo getSPInfoBySPId(String sparePartId);
	public int getSPInfoCnt(String sparePartName);
	
	//根据备件名称模糊查询设备
	public List<SparePartInfo> getSPsBySPName(String spName);
}
