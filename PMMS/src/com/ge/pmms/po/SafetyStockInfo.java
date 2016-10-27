package com.ge.pmms.po;

public class SafetyStockInfo {

	private String deviceId;     //备件或间接物料的编号
	private int num ;            //有数据月的个数
	private double sums;         //有数据月的和（xi-x）^2 +......
	private double avgAmount;    //月平均消耗，即标准方差里面的平均值
	private double safetyStock;  //安全库存
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public int getNum() {
		return num;
	}
	
	public void setNum(int num) {
		this.num = num;
	}
	
	public double getSums() {
		return sums;
	}
	
	public void setSums(double sums) {
		this.sums = sums;
	}
	
	public double getAvgAmount() {
		return avgAmount;
	}
	
	public void setAvgAmount(double avgAmount) {
		this.avgAmount = avgAmount;
	}

	
	public double getSafetyStock() {
		return safetyStock;
	}

	public void setSafetyStock(double safetyStock) {
		this.safetyStock = safetyStock;
	}
	
}
