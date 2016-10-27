package com.ge.pmms.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ge.pmms.po.User;
import com.ge.pmms.utils.PMMSConstants;

public class SessionListener implements HttpSessionListener{

	protected Log logger = LogFactory.getLog(this.getClass());
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		User user = (User) event.getSession().getAttribute(PMMSConstants.USER_SESSION_DATA);
		if(user != null){
			logger.info("User logged out with sso as "+user.getSso()+" at time "+event.getSession().getLastAccessedTime());
		}
		event.getSession().removeAttribute(PMMSConstants.USER_SESSION_DATA);
	}

}
