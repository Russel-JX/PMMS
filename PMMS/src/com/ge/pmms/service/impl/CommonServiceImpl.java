package com.ge.pmms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ge.pmms.dao.CommonDao;
import com.ge.pmms.po.EquipNameInfo;
import com.ge.pmms.po.SysParamsInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.po.WorkOrderInfo;
import com.ge.pmms.service.CommonService;

@Service
@Transactional
public class CommonServiceImpl implements CommonService {
	@Autowired
	private CommonDao commonDao;
	
	public List<EquipNameInfo> getEquipNameList(String equipType) {
		List<EquipNameInfo> list = commonDao.getEquipNameList(equipType);
		return list;
	}

	public List<WorkOrderInfo> getWOrdersInfoByType(String orderType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SysParamsInfo getSysParamInfoById(String itemId) {
		return commonDao.getSysParamInfoById(itemId);
	}

	public String getItemValueById(String itemId) {
		return commonDao.getItemValueById(itemId);
	}

	@Override
	public User getUserBySso(String sso) {
		return commonDao.getUserBySso(sso);
	}

  @Override
  public List<SysParamsInfo> getAllSysItems() {
    return commonDao.getAllSysItems();
  }


}
