package com.ge.pmms.dao;

import java.util.List;

import com.ge.pmms.base.BaseDao;
import com.ge.pmms.base.PmmsException;
import com.ge.pmms.po.EquipNameInfo;
import com.ge.pmms.po.MaintItem;


public interface MaintItemDao extends BaseDao<MaintItem> {
	
	/**
	 * Find maintenance items by equipment name.
	 * @param equipNameID
	 * @return
	 * @throws PmmsException
	 */
	public List<MaintItem> findMaintItemsByName(String equipNameID)throws PmmsException;
	
	/**
	 * Find equip name info by equipment name id.
	 * @param equipNameID
	 * @return
	 * @throws PmmsException
	 */
	public List<EquipNameInfo> findEquipNameInfoById(String equipNameID)throws PmmsException;
	
	
	/**
	 * Add maintenance item
	 * @param maintItem
	 * @return
	 * @throws PmmsException
	 */
	public MaintItem addMaintItemByName(MaintItem maintItem)throws PmmsException;
	
	/**
	 * Modify maintenance item
	 * @param maintItem
	 * @return
	 * @throws PmmsException
	 */
	public MaintItem modifyMaintItem(MaintItem maintItem)throws PmmsException;
	
	/**
	 * Remove maintenance item
	 * @param equipNameID
	 * @return
	 * @throws PmmsException
	 */
	public int removeMaintItems(Integer[] ids)throws PmmsException;
	
}
