/**
 * <b>项目名：</b>新指南平台<br/>
 * <b>包名：</b>com.ssxt.rdbox.common.info<br/>
 * <b>文件名：</b>ParaInfo.java<br/>

 * <b>版本信息：</b><br/>
 * <b>日期：</b>2016年12月14日-下午10:21:34<br/>
 * <b>Copyright (c)</b> 2016立新公司-版权所有<br/>
 * 
 */
package com.ssxt.common.info;

/**
 * <b>类名称：</b>ParaInfo<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2016年12月14日 下午10:21:34<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 */
public class ParamInfo {

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public boolean isRequire() {
		return require;
	}
	public void setRequire(boolean require) {
		this.require = require;
	}
	public String name="";
	public String type="";
	public String comment="";
	public boolean require=false;
}
