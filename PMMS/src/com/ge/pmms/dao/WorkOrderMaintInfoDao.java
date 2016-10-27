/**
 * ============================================================
 * File : WorkOrderMaintInfoDao.java
 * Description : 
 * 
 * Package : com.ge.pmms.dao
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 21, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.dao;

import com.ge.pmms.base.BaseDao;
import com.ge.pmms.po.WorkOrderMaintenanceInfo;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Jan 21, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public interface WorkOrderMaintInfoDao extends BaseDao<WorkOrderMaintenanceInfo>{
	public void save(WorkOrderMaintenanceInfo workOrderMaintenanceInfo);
	
}
