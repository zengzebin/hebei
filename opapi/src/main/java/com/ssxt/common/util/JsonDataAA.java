/**
 * <b>项目名：</b>DMS+运维系统平台<br/>
 * <b>包名：</b>com.ssxt.rdbox.common.util<br/>
 * <b>文件名：</b>JsonDataAA.java<br/>

 * <b>版本信息：</b><br/>
 * <b>日期：</b>2016年12月9日-上午11:08:03<br/>
 * <b>Copyright (c)</b> 2016圣世信通-版权所有<br/>
 * 
 */
package com.ssxt.common.util;

/**
 * <b>类名称：</b>JsonDataAA<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2016年12月9日 上午11:08:03<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 */
public class JsonDataAA {
	private String aaData;
	private int errcode;
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getAaData() {
		return aaData;
	}
	public void setAaData(String aaData) {
		this.aaData = aaData;
	}
	private String phone;
	private String step;
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
}
