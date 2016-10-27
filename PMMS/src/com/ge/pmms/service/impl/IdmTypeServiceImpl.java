package com.ge.pmms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ge.pmms.base.BaseService;
import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.dao.IdmTypeDao;
import com.ge.pmms.po.IdmTypeDetail;
import com.ge.pmms.service.IdmTypeService;
import com.ge.pmms.utils.Tools;

@Service
@Transactional
public class IdmTypeServiceImpl extends BaseService<IdmTypeDetail> implements IdmTypeService{
	
	@Autowired
	private IdmTypeDao idmTypeDao;
	
	public ServiceReturns insertSubType(IdmTypeDetail  idmTypeDetail){
		serviceReturns = new ServiceReturns();
		//String lastDetailId=idmTypeDao.getLastDetailTypeId(idmTypeDetail);
		//if(!Tools.isEmpty(lastDetailId)){
		//	int detailTypeId=Integer.parseInt(lastDetailId)+1;
		//	String detailId=null;
		//	if(detailTypeId<10){
		//		 detailId="0"+detailTypeId;
		//	}else{
		//		detailId=String.valueOf(detailTypeId);
		//	}
		//	idmTypeDetail.setTYPE_DETAIL_ID(detailId);
		//}else{
			idmTypeDetail.setTYPE_DETAIL_ID("01");
		//}	
		List<IdmTypeDetail> rsLst=idmTypeDao.getSubTypeByTypeId(idmTypeDetail);
		if(rsLst.size()==0){
			super.save(idmTypeDetail);
			serviceReturns.putData("detail",null);
		}else{
			serviceReturns.putData("detail",rsLst);
		}
		return serviceReturns;
		
	}
	
	public ServiceReturns insertDetailType(IdmTypeDetail  idmTypeDetail){
		serviceReturns = new ServiceReturns();
		
		IdmTypeDetail typeDetail=idmTypeDao.getSubTypeByTypeId(idmTypeDetail).get(0);
		String subTypeNm=typeDetail.getSUB_TYPE_NM();
		idmTypeDetail.setSUB_TYPE_NM(subTypeNm);
		
		String lastDetailId=idmTypeDao.getLastDetailTypeId(idmTypeDetail);
		if(!Tools.isEmpty(lastDetailId)){
			int detailTypeId=Integer.parseInt(lastDetailId)+1;
			String detailId=null;
			if(detailTypeId<10){
				 detailId="0"+detailTypeId;
			}else{
				detailId=String.valueOf(detailTypeId);
			}
			idmTypeDetail.setTYPE_DETAIL_ID(detailId);
		}else{
			idmTypeDetail.setTYPE_DETAIL_ID("01");
		}	
		return serviceReturns=super.save(idmTypeDetail);
		
	}
	
	public ServiceReturns getAssignDetailTypeId(IdmTypeDetail  idmTypeDetail){
		serviceReturns = new ServiceReturns();
	String lastDetailId=idmTypeDao.getLastDetailTypeId(idmTypeDetail);
	String detailId="01";
	if(!Tools.isEmpty(lastDetailId)){
		int detailTypeId=Integer.parseInt(lastDetailId)+1;
		if(detailTypeId<10){
			 detailId="0"+detailTypeId;
		}else{
			detailId=String.valueOf(detailTypeId);
		}
	}	
	serviceReturns.putData("assignDetailTypeId", detailId);
	return serviceReturns;	
	}
	
	public ServiceReturns getSubTypeByTypeId(IdmTypeDetail  idmTypeDetail){
		serviceReturns = new ServiceReturns();
		List<IdmTypeDetail> rsLst=idmTypeDao.getSubTypeByTypeId(idmTypeDetail);
		if(rsLst.size()==0){
			serviceReturns.putData("detail",null);
		}else{
			serviceReturns.putData("detail",rsLst);
		}
		return serviceReturns;
	}
	
	public ServiceReturns getdetailType(IdmTypeDetail  idmTypeDetail){
		serviceReturns = new ServiceReturns();
		serviceReturns.putData("detail",idmTypeDao.getdetailType(idmTypeDetail));
		return serviceReturns;
	}

}
