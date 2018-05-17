package com.ssxt.web.vo;

public class Analysis {
	private int deviceTypeId = 0;
	private String name;
	private int surplusNum = 0;
	private int demandNum = 0;

	public int getDeviceTypeId() {
		return deviceTypeId;
	}

	public void setDeviceTypeId(int deviceTypeId) {
		this.deviceTypeId = deviceTypeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSurplusNum() {
		return surplusNum;
	}

	public void setSurplusNum(int surplusNum) {
		this.surplusNum = surplusNum;
	}

	public int getDemandNum() {
		return demandNum;
	}

	public void setDemandNum(int demandNum) {
		this.demandNum = demandNum;
	}

}
