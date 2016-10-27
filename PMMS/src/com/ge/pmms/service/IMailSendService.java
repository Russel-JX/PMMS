
package com.ge.pmms.service;

import com.ge.pmms.po.MailMessageVO;


public interface IMailSendService {

	public void sendMail(MailMessageVO message);

	public void sendDelegateTaskMail(MailMessageVO message);

	public void sendOfsErrorRecordMail(MailMessageVO message);

}
