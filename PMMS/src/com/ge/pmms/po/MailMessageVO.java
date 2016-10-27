
package com.ge.pmms.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ge.pmms.utils.Tools;

public class MailMessageVO {

    private String from;
    private String subject;
    private String[] toEmail;
    private String[] toBcc;
    private String[] toCc;
    private String replyTo;
    private String message;
    private List<MailAttachmentVO> attachments = new ArrayList<MailAttachmentVO>();

    public boolean hasAttachment() {
        if (this.attachments == null || this.attachments.size() == 0) {
            return false;
        }
        return true;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String[] getToEmail() {
        if(toEmail==null){
			return null;
		}
		return (String[])toEmail.clone();
    }

    public void setToEmail(String[] toEmail) {
        if(!Tools.isNull(toEmail)){
			this.toEmail = (String[])toEmail.clone();
		}
    }

    public String[] getToBcc() {
        if(toBcc==null){
			return null;
		}
		return (String[])toBcc.clone();
    }

    public void setToBcc(String[] toBcc) {
        if(!Tools.isNull(toBcc)){
			this.toBcc = (String[])toBcc.clone();
		}
    }

    public String[] getToCc() {
        if(toCc==null){
			return null;
		}
		return (String[])toCc.clone();
    }

    public void setToCc(String[] toCc) {
        if(!Tools.isNull(toCc)){
			this.toCc = (String[])toCc.clone();
		}
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<MailAttachmentVO> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<MailAttachmentVO> attachments) {
        this.attachments = attachments;
    }

}
