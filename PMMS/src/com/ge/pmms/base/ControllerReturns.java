package com.ge.pmms.base;


import java.util.HashMap;
import java.util.Map;

import com.ge.pmms.utils.CacheUtils;
import com.ge.pmms.utils.GsonSingleton;
import com.ge.pmms.utils.Tools;


@SuppressWarnings("unchecked")
//@Component
//@Scope("prototype")
//Controller返回类型
public class ControllerReturns{
	protected boolean success = true;
	protected boolean successFlag = true;
	protected Map<Object, Object> data = new HashMap<Object, Object>();
	protected String forwardUrl = "";
	protected MessageContent message = new MessageContent("10000");
	protected String messageType = "info";//info | error | warning 
	
	//following three attributes are essential for loading data from server side for datatables.
	protected int sEcho = 0;
	protected int iTotalRecords = 0;
	protected int iTotalDisplayRecords = 0;

	
	/**
	 * @return the sEcho
	 */
	public int getsEcho() {
		return sEcho;
	}

	
	/**
	 * @param sEcho the sEcho to set
	 */
	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	
	/**
	 * @return the iTotalRecords
	 */
	public int getiTotalRecords() {
		return iTotalRecords;
	}

	
	/**
	 * @param iTotalRecords the iTotalRecords to set
	 */
	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	
	/**
	 * @return the iTotalDisplayRecords
	 */
	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	
	/**
	 * @param iTotalDisplayRecords the iTotalDisplayRecords to set
	 */
	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Map<Object, Object> getData() {
		return data;
	}
	
	public void putData(String key, Object value) {
		data.put(key, value);
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public MessageContent getMessage() {
		return message;
	}

	public void setMessage(MessageContent message) {
		this.message = message;
	}
	
	public void setMessage(String message) {
		this.message = new MessageContent(message);
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	
	
  public boolean isSuccessFlag() {
    return successFlag;
  }


  
  public void setSuccessFlag(boolean successFlag) {
    this.successFlag = successFlag;
  }


  public ControllerReturns(ServiceReturns serviceReturns){
		messageType = serviceReturns.getMessageType();
		message = serviceReturns.getMessage();
		success = serviceReturns.isSuccess();
		forwardUrl = serviceReturns.getForwardUrl();
		data = serviceReturns.getData();
	}
	
	public ControllerReturns(){}
	
	public String generateJsonData(){
		messageType = Tools.isNull(CacheUtils.getSpringRequest().getAttribute("messageType"), "info");
		message = (MessageContent)CacheUtils.getSpringRequest().getAttribute("message");
		success = Tools.isNull((Boolean)CacheUtils.getSpringRequest().getAttribute("success"),true);
		return GsonSingleton.getGsonInstance().toJson(this);
	}
}
