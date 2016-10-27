package com.ge.pmms.service;

import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.po.IdmTypeDetail;


public interface IdmTypeService {
	
	public ServiceReturns insertSubType(IdmTypeDetail  idmTypeDetail);
	
	public ServiceReturns insertDetailType(IdmTypeDetail  idmTypeDetail);
	
	public ServiceReturns getSubTypeByTypeId(IdmTypeDetail  idmTypeDetail);
	
	public ServiceReturns getdetailType(IdmTypeDetail  idmTypeDetail);
	
	public ServiceReturns getAssignDetailTypeId(IdmTypeDetail  idmTypeDetail);
}
