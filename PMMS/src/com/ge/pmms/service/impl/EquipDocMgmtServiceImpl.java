/**
 * ============================================================
 * File : EquipDocMgmtServiceImpl.java
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

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.dao.EquipDocInfoDao;
import com.ge.pmms.po.EquipDocDetail;
import com.ge.pmms.po.EquipDocInfo;
import com.ge.pmms.service.EquipDocDetailService;
import com.ge.pmms.service.EquipDocMgmtService;
import com.ge.pmms.service.EquipInfoService;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.Tools;

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
public class EquipDocMgmtServiceImpl extends BaseService<EquipDocInfo> implements EquipDocMgmtService {

	
	@Autowired
	private EquipDocDetailService equipDocDetailService;
	
	@Autowired
	private EquipDocInfoDao equipDocInfoDao;
	
	@Autowired
	private EquipInfoService equipInfoService;
	
	public ServiceReturns saveDocsInfo(EquipDocInfo docInfo,
			List<EquipDocDetail> lstDocDetail) throws PmmsException {
		serviceReturns =  new ServiceReturns();
		docInfo.setCreateTime(Tools.getToday());
		//save equip document info
		save(docInfo);
		//save equip document detail info;
		if(!CollectionUtils.isEmpty(lstDocDetail)){
			equipDocDetailService.bulkSaveEquipDocDetails(lstDocDetail);	
		}
		
		if(Constants.EQUIPDOC_EQMODEL.equals(docInfo.getDocType())){
			//update equip info
			equipInfoService.updateEpDocByEpModelId(docInfo.getEquipDocId(), docInfo.getEquipModelId());
		}
		
		if(Constants.EQUIPDOC_EQNO.equals(docInfo.getDocType())){
			//update equip info
			equipInfoService.updateEpDocByEpID(docInfo.getEquipDocId(), docInfo.getEquipNo());
		}
		
		return null;
	}
	
	
	public ServiceReturns editEquipDocs(EquipDocInfo docInfo,
			List<EquipDocDetail> lstDocDetail) throws PmmsException {
		//update equip document info
		equipDocInfoDao.updateDocFileInfo(docInfo);
		//如果有新增的文档，需要往detail表插入数据
		
		if(!CollectionUtils.isEmpty(lstDocDetail)){
			equipDocDetailService.bulkSaveEquipDocDetails(lstDocDetail);	
		}
		return serviceReturns;
	}

	public ServiceReturns getEquipDocslst(){
		return serviceReturns = getAll(EquipDocInfo.class);
	}
	
	
	public ServiceReturns getEquipDocsInfo(String id){
		return get(EquipDocInfo.class, Integer.parseInt(id));
	}
	
	public EquipDocInfo getEquipDocs(String id){
		return getById(EquipDocInfo.class, Integer.parseInt(id));
	}
	
	public ServiceReturns delDocDetail(String id){
		serviceReturns = new ServiceReturns();
		EquipDocDetail detail = equipDocDetailService.getEqDocDetailById(id);
		String filePath = detail.getFilePath();
		equipDocDetailService.delDetail(detail);
		//deletel file
		File file = new File(filePath);
		boolean isDel = file.delete();
		if(!isDel){
			LOGGER.error("File not deleted,path:"+filePath);
		}
		
		return serviceReturns;
	}
	
    public ServiceReturns delEqDocInfo(String id){
    	serviceReturns = new ServiceReturns();
    	if(!StringUtils.isEmpty(id)){
    		EquipDocInfo equipDocInfo = getById(EquipDocInfo.class, Integer.parseInt(id));
    		if(null != equipDocInfo){
    			String equipDocInfoId = equipDocInfo.getEquipDocId();
    			try {
					deleteById(equipDocInfo);
					
					List<EquipDocDetail> list = equipDocDetailService.getDetailListByDocInfoId(equipDocInfoId);
					if(!CollectionUtils.isEmpty(list)){
					  boolean tmpBl = false;
						Iterator<EquipDocDetail> itor = list.iterator();
						
						while (itor.hasNext()){
							EquipDocDetail detail = itor.next();
							File file = new File(detail.getFilePath());
							
							tmpBl = file.delete();
							if(!tmpBl){
							  LOGGER.error("delete equipment documents failed, path:"+detail.getFilePath());
							}
						}
					}
				}
				catch (Exception e) {
					LOGGER.error("delete equipment documents failed, equipment key Id:"+id, e);
				}
    		}
    	}
    	return serviceReturns;
	}
	
}
