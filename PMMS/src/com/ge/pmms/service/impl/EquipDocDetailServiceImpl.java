/**
 * ============================================================
 * File : EquipDocDetailServiceImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.service.impl
 * Author : Flash
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 13, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.pmms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.dao.EquipDocDetailDao;
import com.ge.pmms.po.EquipDocDetail;
import com.ge.pmms.service.EquipDocDetailService;

/*******************************************************************************
*
* @Author 		: Flash
* @Version 	: 1.0
* @Date Created: Jan 13, 2015
* @Date Modified : 
* @Modified By : 
* @Contact 	:
* @Description : 
* @History		:
*
******************************************************************************/
@Service
@Transactional
public class EquipDocDetailServiceImpl extends BaseService<EquipDocDetail>
		implements EquipDocDetailService {

	@Autowired
	private EquipDocDetailDao equipDocDetailDao;
	
	public void bulkSaveEquipDocDetails(List<EquipDocDetail> lstDocDetail) throws PmmsException {
		super.saveAll(lstDocDetail);
	}
	
	public ServiceReturns getEquipDocsInfo(EquipDocDetail detail){
		serviceReturns = new ServiceReturns();
		List<EquipDocDetail> list = equipDocDetailDao.getEquipDocsInfo(detail); 
		serviceReturns.putData("details", list);
		return serviceReturns;
	}
	
	
	public ServiceReturns getLstByDocInfoId(String equipDocInfoId){
		serviceReturns = new ServiceReturns();
		EquipDocDetail detail = new EquipDocDetail();
		detail.setEquipDocId(equipDocInfoId);
		List<EquipDocDetail> list = equipDocDetailDao.getEquipDocsInfo(detail);
		serviceReturns.putData("details", list);
		
		return serviceReturns;
	}

	public ServiceReturns getDocDetailByDocId(String equipDocId) {
		serviceReturns = new ServiceReturns();
		List list = equipDocDetailDao.getDocDetailByDocId(equipDocId);
		
		serviceReturns.putData("docDetails", list);
		return serviceReturns;
	}
	
	public EquipDocDetail getEqDocDetailById(String id){
		return getById(EquipDocDetail.class, Integer.parseInt(id));
	}
	
	
	public void delDetail(EquipDocDetail equipDocDetail){
		try {
			deleteById(equipDocDetail);
		}
		catch (Exception e) {
			LOGGER.error("delete equipment document detail error, id:"+equipDocDetail.getId(),e);
		}
	}

	public List<EquipDocDetail> getDetailListByDocInfoId(String equipDocInfoId) {
		return equipDocDetailDao.getDocDetailByDocId(equipDocInfoId);
	}

	public int deletlBulk(List<Integer> ids) {
		if(!CollectionUtils.isEmpty(ids)){
			return equipDocDetailDao.deletlBulk(ids);	
		}
		return 0;
		
	}

	
}
