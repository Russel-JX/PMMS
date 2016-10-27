

package com.ge.pmms.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.ge.pmms.po.MailAttachmentVO;
import com.ge.pmms.po.MailConfigVO;
import com.ge.pmms.po.MailMessageVO;
import com.ge.pmms.service.impl.MailSendServiceImpl;
@Component
public class MailSender {

//    private String mailEncoding;
//    private String mail_attachment_file_name_Encoding;

//    private JavaMailSender javaMailSender;
//
//    public void setJavaMailSender(JavaMailSender javaMailSender) {
//        this.javaMailSender = javaMailSender;
//    }
	private static final Logger LOGGER = Logger.getLogger(MailSender.class);
    public void send(MailConfigVO mc, MailMessageVO mm) throws UnsupportedEncodingException{
        try {
         //   JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl) this.javaMailSender;

            // set mail conf

            /*javaMailSenderImpl.setHost(mc.getMailHost());
            javaMailSenderImpl.setUsername(mc.getMailSmtpUser());
            javaMailSenderImpl.setPassword(mc.getMailSmtpPassword());
            javaMailSenderImpl.setDefaultEncoding(mc.getMailEncoding());
            javaMailSenderImpl.setPort(mc.getMailPort());*/
        	InternetAddress[] ccAddress=null;
//        	InternetAddress[] toAddress=new InternetAddress[1];
        	
        	InternetAddress[] toAddress=new InternetAddress[mm.getToEmail().length];
        	for(int i=0;i<mm.getToEmail().length;i++){
        		toAddress[i] = new InternetAddress(mm.getToEmail()[i]);
        	}
    		
    		/*toAddress[0]=new InternetAddress("Panneer.arumugam@ge.com");
			toAddress[1]=new InternetAddress("amol1.kulkarni@ge.com");
			toAddress[2]=new InternetAddress("anindita.chakravarty@ge.com");*/
			
//			toAddress[0] = new InternetAddress(mm.getToEmail()[0]);
        	boolean sessionDebug = false;
        	Properties props = System.getProperties();
        	props.put("mail.host", mc.getMailHost());
    		props.put("mail.transport.protocol", "smtp");
    		Session session = Session.getDefaultInstance(props, null);
    		session.setDebug(sessionDebug);
    		Message msg = new MimeMessage(session);
    		msg.setFrom(new InternetAddress(mm.getFrom()));
    		msg.setRecipients(Message.RecipientType.TO,toAddress); 
    		
    		if(!Tools.isNull(mm.getToCc())){
        		ccAddress=new InternetAddress[mm.getToCc().length];
        		for(int i=0;i<mm.getToCc().length;i++){
    				ccAddress[i]=new InternetAddress(mm.getToCc()[i]);
    			}
        		msg.setRecipients(Message.RecipientType.CC,ccAddress);  
        	}
    		
    		
    		msg.setSubject(mm.getSubject() == null ? "" : mm.getSubject());
    		  		
    		BodyPart messageBodyPart = new MimeBodyPart();
    		 messageBodyPart.setText(mm.getMessage());
    		Multipart multipart = new MimeMultipart();
    		 multipart.addBodyPart(messageBodyPart);
    		for (MailAttachmentVO attachment : mm.getAttachments()) {
    			messageBodyPart = new MimeBodyPart();
    			DataSource source = new FileDataSource(attachment.getFile());
    			messageBodyPart.setDataHandler(new DataHandler(source));
    			messageBodyPart.setFileName(attachment.getName());
    			multipart.addBodyPart(messageBodyPart);
    		}
    		/*for (MailAttachmentVO attachment : mm.getAttachments()) {
    			msg.addAttachment(MimeUtility.encodeWord(attachment.getName(), this.mail_attachment_file_name_Encoding, "B"),
                        attachment.getFile());
            }*/
			/*Properties javaMailProperties = new Properties();
			javaMailProperties.put(ARODConstants.MAIL_SMTP_AUTH, mc.getStrMailSmtpAuth());
			javaMailProperties.put(ARODConstants.MAIL_SMTP_EHLO, mc.getStrMailSmtpEhlo());
			javaMailProperties.put(ARODConstants.MAIL_SMTP_TIMEOUT, mc.getMailSmtpTimeout());
			javaMailProperties.put(ARODConstants.MAIL_SMTP_AUTH_NTLM_DISABLE, "true");
			javaMailProperties.put(ARODConstants.MAIL_SMTP_AUTH_NTLM_DOMAIN, mc.getDomain());*/

            /*javaMailSenderImpl.setJavaMailProperties(javaMailProperties);*/

            // set encoding
//            this.mailEncoding = "UTF-8";
//            this.mail_attachment_file_name_Encoding = "UTF-8";

            // set mail send conf

           /* MimeMessage msg = javaMailSenderImpl.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, this.mailEncoding);
            helper.setFrom(mm.getFrom());
            helper.setSubject(mm.getSubject() == null ? "" : mm.getSubject());
            helper.setTo(mm.getToEmail() == null ? new String[] {} : mm.getToEmail());
            helper.setBcc(mm.getToBcc() == null ? new String[] {} : mm.getToBcc());
            helper.setCc(mm.getToCc() == null ? new String[] {} : mm.getToCc());

            if (mm.getReplyTo() != null) {
                helper.setReplyTo(mm.getReplyTo());
            }
            helper.setText(mm.getMessage(), mc.isMailHtmlFormatter());

            for (MailAttachmentVO attachment : mm.getAttachments()) {
                helper.addAttachment(MimeUtility.encodeWord(attachment.getName(), this.mail_attachment_file_name_Encoding, "B"),
                        attachment.getFile());
            }*/

         //   javaMailSenderImpl.send(msg);
            msg.setContent(multipart);
            Transport.send(msg);
            
        } catch (MessagingException e) {
        	LOGGER.error("send() method occures exception......Exception:"+e.getMessage());
        }
    }
}
