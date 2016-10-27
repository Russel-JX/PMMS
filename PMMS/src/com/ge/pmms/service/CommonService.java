package com.ge.pmms.service;

import java.util.List;

import com.ge.pmms.po.EquipNameInfo;
import com.ge.pmms.po.SysParamsInfo;
import com.ge.pmms.po.User;
import com.ge.pmms.po.WorkOrderInfo;

public interface CommonService {

	//用户信息
	/**
	 * 根据用户的sso,获取用户的信息
	 * @Author: iGATE 
	 * @param sso
	 * @return
	 * @Description:
	 */
	public User getUserBySso(String sso);
	
	//权限相关
	
	
	//设备信相关
	/**
	 * 根据设备类型获取设别名称信息(应包含设备名称，设备编号等相关信息，用于填充页面下拉框)
	 * @param equipType
	 * @return
	 */
	public List<EquipNameInfo> getEquipNameList(String equipType);
	
	
	
	//工单相关
	
	/**
	 *根据工单类型获取工单信息
	 * @param orderType
	 * @return
	 */
	public List<WorkOrderInfo> getWOrdersInfoByType(String orderType);
	
	//系统参数相关
	/**
	 * 根据ID系统参数信息
	 * @param itemId
	 * @return
	 */
	public SysParamsInfo getSysParamInfoById(String itemId);
	
	
	/**
   * 根据ID获取相系统参数配置
   * @param itemId
   * @return
   */
  public List<SysParamsInfo> getAllSysItems();
	
	
}
