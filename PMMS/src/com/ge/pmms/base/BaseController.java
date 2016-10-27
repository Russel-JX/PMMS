/**
 * ============================================================
 * File : BaseController.java
 * Description : 
 * 
 * Package : com.ge.pmms.base.impl
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 20, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.pmms.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseController {
	protected static Log LOGGER;
	protected ControllerReturns returns;
	
	public BaseController(){
		LOGGER = LogFactory.getLog(this.getClass());
	}
	
}	
