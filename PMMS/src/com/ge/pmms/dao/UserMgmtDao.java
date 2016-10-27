/**
 * ============================================================
 * File : UserMgmtDao.java
 * Description : 
 * 
 * Package : com.ge.pmms.dao
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

package com.ge.pmms.dao;

import java.util.List;

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
public interface UserMgmtDao {
	
	public List<UserInfo> getAllUser();
	public UserInfo getUserBySso(String sso);
	public int deleteUserById(String id);
}
