package com.ge.pmms.service;

import java.util.List;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.po.EquipDocDetail;

public interface EquipDocDetailService {

	/**
	 * bulk update equipment documents detail
	 * @Author: Flash 
	 * @param lstDocDetail PO
	 * @throws PmmsException
	 * @Description:
	 */
	public void bulkSaveEquipDocDetails(List<EquipDocDetail> lstDocDetail) throws PmmsException;
	
	/**
	 * find  equipment document detail list
	 * @Author: iGATE 
	 * @param detail search criteria
	 * @return
	 * @Description:
	 */
	public ServiceReturns getEquipDocsInfo(EquipDocDetail detail);
	
	/**
	 * find equipment documents by equip documents Id
	 * @Author: iGATE 
	 * @param equipDocInfoId equipDocnfoId
	 * @return ServiceReturns
	 * @Description:
	 */
	public ServiceReturns getLstByDocInfoId(String equipDocInfoId);
	
	
	/**
	 * find equipment documents by equip documents Id
	 * @Author: iGATE 
	 * @param equipDocInfoId equipDocnfoId
	 * @return ServiceReturns
	 * @Description:
	 */
	public List<EquipDocDetail> getDetailListByDocInfoId(String equipDocInfoId);
	
	/**
	 * find equipment detail info by equipemnt docuemnt id
	 * @Author: iGATE 
	 * @param equipDocId
	 * @return ServiceReturns
	 * @Description:
	 */
	public ServiceReturns getDocDetailByDocId(String equipDocId);
	
	
	/**
	 * get detail info by key id
	 * @Author: iGATE 
	 * @param id
	 * @return EquipDocDetail
	 * @Description:
	 */
	public EquipDocDetail getEqDocDetailById(String id);
	
	
	/**
	 * delete equipment detail by key id
	 * @Author: iGATE 
	 * @param equipDocDetail equipment detail id
	 * @Description:
	 */
	public void delDetail(EquipDocDetail equipDocDetail);
	
	
	/**
	 * bulk delete equipment document detail info
	 * @Author: iGATE 
	 * @param ids
	 * @return int
	 * @Description:
	 */
	public int deletlBulk(List<Integer> ids);
}
