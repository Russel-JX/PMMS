package com.ge.pmms.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ge.pmms.base.impl.BaseDaoImpl;
import com.ge.pmms.dao.IdmTypeDao;
import com.ge.pmms.po.IdmInfo;
import com.ge.pmms.po.IdmTypeDetail;

@Repository
public class IdmTypeDaoImpl extends BaseDaoImpl<IdmTypeDetail>implements IdmTypeDao{
	
	public String getLastDetailTypeId(IdmTypeDetail  idmTypeDetail){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("select TYPE_DETAIL_ID FROM IdmTypeDetail where SUB_TYPE_ID=:subTypeId and TYPE_ID=:typeId order by TYPE_DETAIL_ID desc");
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("subTypeId",idmTypeDetail.getSUB_TYPE_ID());
		query.setParameter("typeId",idmTypeDetail.getTYPE_ID());
		List<IdmInfo> list = query.list();
		if(list.size()>0){
			return String.valueOf(list.get(0));
		}
		return null;	
	}
	
	public List<IdmTypeDetail> getSubTypeByTypeId(IdmTypeDetail  idmTypeDetail){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("select distinct SUB_TYPE_ID,SUB_TYPE_NM from IdmTypeDetail where TYPE_ID=:typeId ");
		if(!StringUtils.isEmpty(idmTypeDetail.getSUB_TYPE_ID())){
			sbQuery.append(" and SUB_TYPE_ID=:subTypeId");
		}
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("typeId", idmTypeDetail.getTYPE_ID());
		if(!StringUtils.isEmpty(idmTypeDetail.getSUB_TYPE_ID())){
			query.setParameter("subTypeId", idmTypeDetail.getSUB_TYPE_ID());
		}
		List<?> list = query.list();
		List<IdmTypeDetail> subTypeLst = new ArrayList<IdmTypeDetail>();
		if(!CollectionUtils.isEmpty(list)){
			Iterator itor = list.iterator();
			while(itor.hasNext()){
				IdmTypeDetail po = new IdmTypeDetail();
				Object[] obj = (Object[])itor.next();
				po.setSUB_TYPE_ID(String.valueOf(obj[0]));
				po.setSUB_TYPE_NM(String.valueOf(obj[1]));
				subTypeLst.add(po);
			}
	}
		return subTypeLst;
	}
	
	public List<IdmTypeDetail> getdetailType(IdmTypeDetail  idmTypeDetail){
		StringBuffer sbQuery = new StringBuffer();
		sbQuery.append("select distinct TYPE_DETAIL_ID,TYPE_DETAIL_NM from IdmTypeDetail where TYPE_ID=:typeId and SUB_TYPE_ID=:subTypeId ");
		Query query = getSession().createQuery(sbQuery.toString());
		query.setParameter("typeId", idmTypeDetail.getTYPE_ID());
		query.setParameter("subTypeId", idmTypeDetail.getSUB_TYPE_ID());
		List<?> list = query.list();
		List<IdmTypeDetail> subTypeLst = new ArrayList<IdmTypeDetail>();
		if(!CollectionUtils.isEmpty(list)){
			Iterator itor = list.iterator();
			while(itor.hasNext()){
				IdmTypeDetail po = new IdmTypeDetail();
				Object[] obj = (Object[])itor.next();
				po.setTYPE_DETAIL_ID(String.valueOf(obj[0]));
				po.setTYPE_DETAIL_NM(String.valueOf(obj[1]));
				subTypeLst.add(po);
			}
	}
		return subTypeLst;
	}
}
