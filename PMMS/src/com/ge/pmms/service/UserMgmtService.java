/**
 * ============================================================
 * File : UserMgmtService.java
 * Description : 
 * 
 * Package : com.ge.pmms.service
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Feb 11, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */

package com.ge.pmms.service;

import com.ge.pmms.base.ServiceReturns;
import com.ge.pmms.po.IdmInfo;
import com.ge.pmms.po.UserInfo;


/*******************************************************************************
 *
 * @Author 		: iGATE
 * @Version 	: 1.0
 * @Date Created: Feb 11, 2015
 * @Date Modified : 
 * @Modified By : 
 * @Contact 	:
 * @Description : 
 * @History		:
 *
 ******************************************************************************/
public interface UserMgmtService {
	
	public ServiceReturns saveUser(UserInfo userInfo);
	public ServiceReturns getAllUser();
	public ServiceReturns getUserBySso(String sso);  
	public ServiceReturns deleteUserById(String id); 
	public ServiceReturns updateUser(UserInfo userInfo);
}
