package com.ge.pmms.po;

public class MailConfigVO {
    
    private String mailHost;
    private int mailPort;
    private String strMailPort;
    private String mailSmtpUser;
    private String mailSmtpPassword;
    private String mailEncoding;
    private boolean mailSmtpAuth;
    private String strMailSmtpAuth;
    private String strMailSmtpEhlo;
    
    private int mailSmtpTimeout;
    private int mailScheduleInterval;
    private int errMailInterval;
    private int maxSentCount;
    private boolean mailHtmlFormatter = false;
    private String domain;
    
    public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getMailHost() {
        return mailHost;
    }
    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }
    public int getMailPort() {
        return mailPort;
    }
    public void setMailPort(int mailPort) {
        this.mailPort = mailPort;
    }
    public String getStrMailPort() {
        return strMailPort;
    }
    public void setStrMailPort(String strMailPort) {
        this.strMailPort = strMailPort;
    }
    public String getMailSmtpUser() {
        return mailSmtpUser;
    }
    public void setMailSmtpUser(String mailSmtpUser) {
        this.mailSmtpUser = mailSmtpUser;
    }
    public String getMailSmtpPassword() {
        return mailSmtpPassword;
    }
    public void setMailSmtpPassword(String mailSmtpPassword) {
        this.mailSmtpPassword = mailSmtpPassword;
    }
    public String getMailEncoding() {
        return mailEncoding;
    }
    public void setMailEncoding(String mailEncoding) {
        this.mailEncoding = mailEncoding;
    }
    public boolean isMailSmtpAuth() {
        return mailSmtpAuth;
    }
    public void setMailSmtpAuth(boolean mailSmtpAuth) {
        this.mailSmtpAuth = mailSmtpAuth;
    }
    public String getStrMailSmtpAuth() {
        return strMailSmtpAuth;
    }
    public void setStrMailSmtpAuth(String strMailSmtpAuth) {
        this.strMailSmtpAuth = strMailSmtpAuth;
    }
    public String getStrMailSmtpEhlo() {
        return strMailSmtpEhlo;
    }
    public void setStrMailSmtpEhlo(String strMailSmtpEhlo) {
        this.strMailSmtpEhlo = strMailSmtpEhlo;
    }
    public int getMailSmtpTimeout() {
        return mailSmtpTimeout;
    }
    public void setMailSmtpTimeout(int mailSmtpTimeout) {
        this.mailSmtpTimeout = mailSmtpTimeout;
    }
    public int getMailScheduleInterval() {
        return mailScheduleInterval;
    }
    public void setMailScheduleInterval(int mailScheduleInterval) {
        this.mailScheduleInterval = mailScheduleInterval;
    }
    public int getErrMailInterval() {
        return errMailInterval;
    }
    public void setErrMailInterval(int errMailInterval) {
        this.errMailInterval = errMailInterval;
    }
    public int getMaxSentCount() {
        return maxSentCount;
    }
    public void setMaxSentCount(int maxSentCount) {
        this.maxSentCount = maxSentCount;
    }
    public boolean isMailHtmlFormatter() {
        return mailHtmlFormatter;
    }
    public void setMailHtmlFormatter(boolean mailHtmlFormatter) {
        this.mailHtmlFormatter = mailHtmlFormatter;
    }
    
}
