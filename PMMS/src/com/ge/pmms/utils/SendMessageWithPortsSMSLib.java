package com.ge.pmms.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.ofbiz.entity.GenericDelegator;
import org.smslib.GatewayException;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOutboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.Message.MessageEncodings;
import org.smslib.Message.MessageTypes;
import org.smslib.modem.SerialModemGateway;

public class SendMessageWithPortsSMSLib {
	//自身类
	static SendMessageWithPortsSMSLib smwps=null;
	//读取全部消息
	public static final org.smslib.InboundMessage.MessageClasses ALL_MESSAGE=org.smslib.InboundMessage.MessageClasses.ALL;
	//读取已读消息
	public static final  org.smslib.InboundMessage.MessageClasses READ_MESSAGE=org.smslib.InboundMessage.MessageClasses.READ;
	//读取未读消息
	public static final org.smslib.InboundMessage.MessageClasses UNREAD_MESSAGE =org.smslib.InboundMessage.MessageClasses.UNREAD;
	//消息服务
	private static Service srv=null;
	//gateway为单例
	private static SerialModemGateway gateway = null;
	//发送消息回调实现类
	OutboundNotification outboundNotification = new OutboundNotification();
	//读取消息回调实现类
	InboundNotification  inboundNotification=new InboundNotification();
	//数据库柄
	private  GenericDelegator delegator=null;
	
	//设备名称
	private static String gateName="SMS";

	private SendMessageWithPortsSMSLib(){}
	
	//构造类的实例，只产生一个对象实例
	
	public static SendMessageWithPortsSMSLib newInstanceSend(String com) throws TimeoutException, GatewayException, SMSLibException, IOException, InterruptedException{
		if(smwps==null)
			smwps=new SendMessageWithPortsSMSLib();
		if (smwps.srv==null)
			smwps.open(com,gateName);		
		return smwps;
	}
	public static SendMessageWithPortsSMSLib newInstanceSend(String com,GenericDelegator delegator) throws TimeoutException, GatewayException, SMSLibException, IOException, InterruptedException{
		if(smwps==null)
			smwps=new SendMessageWithPortsSMSLib();
		if (smwps.srv==null)
			smwps.open(com,gateName);		
		smwps.delegator=delegator;
		return smwps;
	}
	public static SendMessageWithPortsSMSLib newInstanceSend(String com,GenericDelegator delegator,String gateName) throws TimeoutException, GatewayException, SMSLibException, IOException, InterruptedException{
		if(smwps==null)
			smwps=new SendMessageWithPortsSMSLib();
		if (smwps.srv==null)
			smwps.open(com,gateName);		
		smwps.delegator=delegator;
		SendMessageWithPortsSMSLib.gateName=gateName;
		return smwps;
	}
	
	//打开端口，开启服务
	private void open(String com,String gateName) throws TimeoutException, GatewayException, SMSLibException, IOException, InterruptedException {
			  //单例。Service.startService();只能调用一次，否则抛出端口异常！
			  if(srv==null){
				  srv = new Service();
				//comPort     串口名，比如COM1或者/dev/ttyS1
				  //baudRate   端口速度，WAVECOM是9600
				  //manufacturer,model 制造商和型号随便填
				  SerialModemGateway gateway= new SerialModemGateway(gateName,com,9600,"",srv.getLogger().toString());
				  gateway.setInbound(true);
				  gateway.setOutbound(true);
				  srv.addGateway(gateway);			 
				  srv.startService();
			  }
			  
			  //gateway.setSimPin("0000");
			 // gateway.setOutboundNotification(outboundNotification);
			 
			  //gateway.setInboundNotification(inboundNotification);
			  //srv.setOutboundNotification(outboundNotification);
			  //srv.setInboundNotification(inboundNotification);
			 // srv.S.SERIAL_POLLING_INTERVAL=10;
			  //srv.S.SERIAL_POLLING=true;
			 	
	}
	//读取信息
	public List <org.smslib.InboundMessage> readSms(org.smslib.InboundMessage.MessageClasses messageType) throws TimeoutException, GatewayException, IOException, InterruptedException{
		List <InboundMessage>  smss=new LinkedList<InboundMessage>();
		//InboundMessage inm=null;		
		srv.readMessages(smss,messageType,gateName);
		//System.out.println(smss);		
		//System.out.println(msg);
		return smss;
	}	 
	//发送单条消息
	public boolean sendSms(String mobile,String content) {				
		OutboundMessage msg = new OutboundMessage(mobile, content);
		msg.setEncoding(MessageEncodings.ENCUCS2);
		//msg.setStatusReport(true);
		try {
			
			srv.sendMessage(msg);
			// System.out.println(msg);
			} catch (Exception e) {
				 // System.out.println("--"+e);
				  return false;
			}
		return true;
	}
	//群发消息
	public List<Map<String, String>>  sendSms(List<Map<String,String>> messages) {
		List <Map<String, String>>   failList=new ArrayList <Map<String, String>>();
		Iterator<Map<String, String>> itr= messages.iterator();
		while(itr.hasNext()){
			Map<String, String> message=(Map<String, String>)itr.next();
			String mobile=(String)message.get("mobile");
			String content=(String)message.get("content");
			if(!sendSms(mobile,content))
				failList.add(message);
		}
		return failList;
	}
	//关闭服务
	public void close() {
		try {
			  srv.stopService();
			}catch (Exception ex) {
				  System.out.println("**"+ex);
			 }
	 }
			 
	public class OutboundNotification implements IOutboundMessageNotification{
		public void process(String gatewayId, OutboundMessage msg){
			System.out.println("Outbound handler called from Gateway: " + gatewayId);
			System.out.println(msg);
		 }
	 }
	public class InboundNotification implements IInboundMessageNotification{

		public void process(String arg0, MessageTypes arg1, InboundMessage arg2) {
			//System.out.println(arg0);
			//System.out.println(arg1);
			//System.out.println(arg2);
			
		}
		
	}	 
	public static void main(String[] args) throws TimeoutException, GatewayException, IOException, InterruptedException {       
		System.out.println("=============================");
		String mob="18932334628";
		String content="xx一只小熊去山里创业，农夫给了他一把镰刀，木匠给了他一把锤子， 小熊来到山里遇到老虎，吓得把镰刀、锤子举在头顶， 老虎说：没看出来，就你这熊样还是个党员来！";
		SendMessageWithPortsSMSLib sms=new SendMessageWithPortsSMSLib();//newInstance();		 
		 try {
			sms.open(Constants.MSG_PORT,"SMS");
		} catch (TimeoutException e) {
			System.out.println("============================="+e.getMessage());
			e.printStackTrace();
		} catch (GatewayException e) {
			e.printStackTrace();
		} catch (SMSLibException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("1、发送短消息");
		System.out.println("quit、退出");
		String str="";
		System.out.println("1、发送短消息");
		System.out.println("2、");
		
		System.out.println("quit、退出");	
//		sms.sendSms(mob,content);
		while(true){				
			System.out.print   ("请选择：   "); 
		    InputStreamReader   stdin   =   new   InputStreamReader(System.in);//键盘输入   
			BufferedReader   bufin   =   new   BufferedReader(stdin);
			try {
				str   =   bufin.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(str.equals("quit")){
				sms.close();
				break;
			}else if(str.equals("1")){
				System.out.println(sms.sendSms(mob,content));
				sms.close();
			}
			else if(str.equals("2")){
				try {
					sms.open(Constants.MSG_PORT,"SMS");
					sms.sendSms(mob,"又新建了Service，这个会导致端口占用，后台跑出异常！！！");
					sms.close();
				}
				catch (SMSLibException e) {
					e.printStackTrace();
				}}
			else{
				System.out.println("短消息!");
				sms.readSms(SendMessageWithPortsSMSLib.ALL_MESSAGE);
			}
		}
//		sms.readSms();
		sms.sendSms(mob, content);
				
	}	

}
