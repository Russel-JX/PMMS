package com.ge.pmms.dao;

import java.util.List;

import com.ge.pmms.po.IdmTypeDetail;


public interface IdmTypeDao {
	
	public String getLastDetailTypeId(IdmTypeDetail  idmTypeDetail);
	
	public List<IdmTypeDetail> getSubTypeByTypeId(IdmTypeDetail  idmTypeDetail);
	
	public List<IdmTypeDetail> getdetailType(IdmTypeDetail  idmTypeDetail);
}
