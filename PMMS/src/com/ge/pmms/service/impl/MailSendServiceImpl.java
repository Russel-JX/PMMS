package com.ge.pmms.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ge.pmms.po.MailConfigVO;
import com.ge.pmms.po.MailMessageVO;
import com.ge.pmms.service.IMailSendService;
import com.ge.pmms.utils.Constants;
import com.ge.pmms.utils.MailSender;

@Service
@Transactional
public class MailSendServiceImpl implements IMailSendService {

	// private JavaMailSender mailSender;
	@Autowired
	private MailSender mailSender = new MailSender() ;
	/*private static final Log LOGGER = LogFactory
			.getLog(MailSendServiceImpl.class);*/
	private static final Logger LOGGER = Logger.getLogger(MailSendServiceImpl.class);
	private MailConfigVO bulidMailConfig() {

		MailConfigVO config = new MailConfigVO();
		config.setMailHost(Constants.MAIL_HOST_URL);
		config.setMailPort(25);

		config.setMailSmtpAuth(true);
		config.setMailSmtpTimeout(60);
		config.setMailScheduleInterval(2);
		config.setErrMailInterval(24);
		config.setMaxSentCount(5);
		config.setMailEncoding("UTF-8");
		config.setStrMailSmtpAuth("false");
		config.setStrMailSmtpEhlo("true");
		config.setDomain("e2k");

		return config;
	}

	private MailConfigVO delegateMailConfig() {

		MailConfigVO config = new MailConfigVO();
		config.setMailHost(Constants.MAIL_HOST_URL);
		config.setMailPort(25);

		config.setMailSmtpAuth(true);
		config.setMailSmtpTimeout(60);
		config.setMailScheduleInterval(2);
		config.setErrMailInterval(24);
		config.setMaxSentCount(5);
		config.setMailEncoding("UTF-8");
		config.setStrMailSmtpAuth("false");
		config.setStrMailSmtpEhlo("true");
		config.setDomain("e2k");
		config.setMailHtmlFormatter(true);

		return config;
	}

	public void sendDelegateTaskMail(MailMessageVO message){
		MailConfigVO config = delegateMailConfig();

		//boolean sendSuccess = false;
		try {
			mailSender.send(config, message);
			//sendSuccess = true;
		} /*catch (MailAuthenticationException mae) {
		    LOGGER.error(mae);
            throw new Exception(ARODConstants.ERROR_CODE_SYSTEM_ERROR,mae);
		} catch (MailSendException mse) {
		    LOGGER.error(mse);
            throw new Exception(ARODConstants.ERROR_CODE_SYSTEM_ERROR,mse);
		} */catch (Exception mae) {
		    LOGGER.error(mae);
//            throw new Exception(ARODConstants.ERROR_CODE_SYSTEM_ERROR,mae);
		}
	}

	/*public void sendMail(MailMessageVO message) throws Exception{

		LOGGER
				.info("---- MailSendServiceImpl.sendMail() ---- start ----");

		// MailMessageVO message = buildMailMessage();
		MailConfigVO config = bulidMailConfig();

		try {
			mailSender.send(config, message);
		} catch (MailAuthenticationException mae) {
		    LOGGER.error(mae);
            throw new Exception(ARODConstants.ERROR_CODE_SYSTEM_ERROR,mae);
		} catch (MailSendException mse) {
		    LOGGER.error(mse);
            throw new Exception(ARODConstants.ERROR_CODE_SYSTEM_ERROR,mse);
		} catch (Exception e) {
		    LOGGER.error(e);
            throw new Exception(ARODConstants.ERROR_CODE_SYSTEM_ERROR,e);
		}
		LOGGER.info("---- MailSendServiceImpl.sendMail()) ----  end  ----");
	}*/

	public void sendMail(MailMessageVO message){

//		LOGGER
//				.info("---- MailSendServiceImpl.sendMail() ---- start ----");

		// MailMessageVO message = buildMailMessage();
		MailConfigVO config = bulidMailConfig();

		try {
			mailSender.send(config, message);
		} /*catch (MailAuthenticationException mae) {
		    LOGGER.error(mae);
            throw new Exception(ARODConstants.ERROR_CODE_SYSTEM_ERROR,mae);
		} catch (MailSendException mse) {
		    LOGGER.error(mse);
            throw new Exception(ARODConstants.ERROR_CODE_SYSTEM_ERROR,mse);
		}*/ catch (Exception e) {
		    LOGGER.error(e);
//            throw new Exception(ARODConstants.ERROR_CODE_SYSTEM_ERROR,e);
		}
//		LOGGER.info("---- MailSendServiceImpl.sendMail()) ----  end  ----");
	
	}
	
	public void sendOfsErrorRecordMail(MailMessageVO message){
		MailConfigVO config = bulidMailConfig();
		try {
			mailSender.send(config, message);
		} /*catch (MailAuthenticationException mae) {
		    LOGGER.error(mae);
            throw new Exception(ARODConstants.ERROR_CODE_SYSTEM_ERROR,mae);
		} catch (MailSendException mse) {
		    LOGGER.error(mse);
            throw new Exception(ARODConstants.ERROR_CODE_SYSTEM_ERROR,mse);
		} */catch (Exception e) {
		    LOGGER.error(e);
//            throw new Exception(ARODConstants.ERROR_CODE_SYSTEM_ERROR,e);
		}
	}

	public MailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

}
