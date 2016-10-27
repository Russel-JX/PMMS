package com.ge.pmms.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.dao.CommonDao;
import com.ge.pmms.po.EquipNameInfo;
import com.ge.pmms.po.SysParamsInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.po.WorkOrderInfo;

@Repository
public class CommonDaoImpl<E> implements CommonDao {

	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	
	public List<EquipNameInfo> getEquipNameList(String equipType){
		Query query = getSession().createQuery("FROM equipNmInfo equipName where equipName.equipType=?");
		query.setParameter(0, equipType);
		@SuppressWarnings("unchecked")
		List<EquipNameInfo> list = query.list();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public SysParamsInfo getSysParamInfoById(String itemId){
		Query query = getSession().createQuery("FROM sysParams where id=?");
		query.setParameter(0, itemId);
		List<SysParamsInfo> list = query.list();
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
  public List<SysParamsInfo> getAllSysItems(){
	  Query query = getSession().createQuery("FROM sysParams");
    return (List<SysParamsInfo>)query.list();
	}
	
	public String getItemValueById(String itemId){
		SysParamsInfo info = getSysParamInfoById(itemId);
		String value = "";
		if(null != info){
			value = info.getItemValue();
		}
		return value;
	}

	public List<WorkOrderInfo> getWOrdersInfoByType(String orderType) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SysParamsInfo> getParamsList(String moduleId) {
		// TODO Auto-generated method stub
		return null;
	}

	private Session getSession(){
		return hibernateTemplate.getSessionFactory().getCurrentSession();
	}

	@Override
	public User getUserBySso(String sso) {
		String hql = "from User where sso=:sso";
		Query query = getSession().createQuery(hql);
		query.setString("sso", sso);
		List<?> list = query.list();
		if(!CollectionUtils.isEmpty(list)){
			return (User) list.get(0);
		}
		return null;
	}
}
