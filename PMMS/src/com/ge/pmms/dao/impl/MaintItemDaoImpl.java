package com.ge.pmms.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ge.pmms.base.PmmsException;
import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.MaintItemDao;
import com.ge.pmms.po.EquipNameInfo;
import com.ge.pmms.po.MaintItem;

@Repository
public class MaintItemDaoImpl extends BaseDaoImpl<MaintItem> implements MaintItemDao {
	
	public List<MaintItem> findMaintItemsByName(String equipNameID)throws PmmsException{
		Query query = getSession().createQuery("FROM maintItem item where item.equip_name_id=?");
		query.setParameter(0, equipNameID);
		@SuppressWarnings("unchecked")
		List<MaintItem> list = query.list();
		
		return list;
	}
	
	public List<EquipNameInfo> findEquipNameInfoById(String equipNameID)throws PmmsException{
		Query query = getSession().createQuery("FROM equipNmInfo eni where eni.equipmentNameId=?");
		query.setParameter(0, equipNameID);
		@SuppressWarnings("unchecked")
		List<EquipNameInfo> list = query.list();
		
		return list;
	}
	
	public MaintItem addMaintItemByName(MaintItem maintItem)throws PmmsException{
		
		try{
			save(maintItem);
		}catch(Exception e){
			e.printStackTrace();
		}
		return maintItem;
	}
	
	public MaintItem modifyMaintItem(MaintItem maintItem)throws PmmsException{
		try{
			update(maintItem);
		}catch(Exception e){
			e.printStackTrace();
		}
		return maintItem;
	}
	
	public int removeMaintItems(Integer[] ids)throws PmmsException{
		String hql = "delete from maintItem where id in:ids";
		Query query = getSession().createQuery(hql);
		query.setParameterList("ids", ids);
		return query.executeUpdate();
	}
	
}
