/**
 * ============================================================
 * File : IdmInfoDao.java
 * Description : 
 * 
 * Package : com.ge.pmms.dao
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

package com.ge.pmms.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ge.pmms.po.IdmInfo;
import com.ge.pmms.po.IdmPoInfo;
import com.ge.pmms.po.IdmTransInfo;
import com.ge.pmms.po.SafetyStockInfo;


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
public interface IdmInfoDao {
	public List<IdmInfo> getAllIdmInfo();
	public List<IdmInfo> getIdmInfoByType(IdmInfo idmInfo);
	public IdmInfo getIdmInfoByIdmId(String idmId);
	public List<IdmTransInfo> getAllIdmTransRecords();
	public IdmTransInfo getTransInfoByIdmId(String idmId);
	public int deleIdmbyIdmId(IdmInfo idmInfo); 
	public List<IdmInfo> getAllUnSaftyIdm(); 
	public int updateStockNum(String idmId,int amount);
	
	public String getLastIdmId(IdmInfo idmInfo);
	
	public List<IdmTransInfo> getIdmRecordByPeriod(IdmTransInfo idmTransInfoReq);
	
	public List<SafetyStockInfo> getVariance(Date startDt,Date endDt);
	
	public Map<String,Double> getL(Date startDt,Date endDt);
	
	public void updateSafetyNum(List<SafetyStockInfo> ssList);
	
	public List<String> getIdmId();
	
	public List<IdmTransInfo> getPoDetail(IdmTransInfo idmTransInfoReq);
	
	public int getOutAmount(IdmTransInfo idmTransInfoReq);
	
	public List<IdmPoInfo> getPoInfo(IdmTransInfo idmTransInfoReq);
	
	public int updateIdmPoInfo(IdmTransInfo idmTransInfoReq);
	
	public int saveIdmPoInfo(IdmTransInfo idmTransInfoReq);
	
	//public List<IdmPoInfo> getAllPoInfo(IdmTransInfo idmTransInfoReq);
}
