package com.ssxt.common.util;

import java.util.ArrayList;
import java.util.List;

public class StatusCode {

	public static final int Report = 0;
	public static final String ReportText = "报修,待指派";

	public static final int send = 10;
	public static final String sendText = "待接收";

	public static final int WaitDeal = 20;
	public static final String WaitDealText = "待维修";

	public static final int deal = 30;
	public static final String dealText = "执行维修";

	public static final int reviewed = 40;
	public static final String reviewedText = "待审核";

	public static final int reviewedNo = 50;
	public static final String reviewedNoText = "审核不通过";

	public static final int owners = 60;
	public static final String ownersText = "维修完成";

	public static final int cancle = 70;
	public static final String cancleText = "撤回结束";

	public static List<StatusCode> getSelct() {
		List<StatusCode> list = new ArrayList<StatusCode>();
		list.add(new StatusCode(StatusCode.ReportText, StatusCode.Report));

		list.add(new StatusCode(StatusCode.sendText, StatusCode.send));

		list.add(new StatusCode(StatusCode.WaitDealText, StatusCode.WaitDeal));

		list.add(new StatusCode(StatusCode.dealText, StatusCode.deal));

		// list.add(new StatusCode(StatusCode.reviewedText,
		// StatusCode.reviewed));
		//
		// list.add(new StatusCode(StatusCode.reviewedNoText,
		// StatusCode.reviewedNo));

		list.add(new StatusCode(StatusCode.ownersText, StatusCode.owners));

		list.add(new StatusCode(StatusCode.cancleText, StatusCode.cancle));

		return list;
	}

	private String name;

	private int value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public StatusCode(String name, int value) {
		super();
		this.name = name;
		this.value = value;
	}

}
