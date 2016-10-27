package com.ge.pmms.base;

import com.ge.pmms.utils.MessagesTool;

public class MessageContent{
	private String messageCode = "";
	private String messageContent = "";
	
	public MessageContent(String messageCode){
		this.messageCode = messageCode;
		this.messageContent = MessagesTool.getMessage(messageCode);
	}
	
	public String getMessageCode() {
		return messageCode;
	}
	
	public String getMessageContent() {
		return messageContent;
	}
	
}