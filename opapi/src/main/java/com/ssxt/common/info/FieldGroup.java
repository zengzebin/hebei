/**
 * <b>项目名：</b>DMS+运维系统平台<br/>
 * <b>包名：</b>com.ssxt.rdbox.common.info<br/>
 * <b>文件名：</b>FieldGroup.java<br/>

 * <b>版本信息：</b><br/>
 * <b>日期：</b>2016年9月14日-上午10:57:46<br/>
 * <b>Copyright (c)</b> 2016圣世信通-版权所有<br/>
 * 
 */
package com.ssxt.common.info;

/**
 * <b>类名称：</b>FieldGroup<br/>
 * <b>类描述：</b><br/>
 * <b>创建人：</b>杨培新<br/>
 * <b>修改人：</b>杨培新<br/>
 * 
 * <b>修改时间：</b>2016年9月14日 上午10:57:46<br/>
 * <b>修改备注：</b><br/>
 * @version 1.0.0<br/>
 */
public class FieldGroup {

	public String name; 
	public FieldComment[] pk; 
	public FieldComment[] fields; 
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public FieldComment[] getPk() {
		return pk;
	}
	public void setPk(FieldComment[] pk) {
		this.pk = pk;
	}
	public FieldComment[] getFields() {
		return fields;
	}
	public void setFields(FieldComment[] fields) {
		this.fields = fields;
	}

	
	/**
	 * 创建一个新的实例 FieldGroup.
	 *
	 * @param name
	 * @param pk
	 * @param fields
	 */
	public FieldGroup(String name, FieldComment[] pk, FieldComment[] fields) {
		super();
		this.name = name;
		this.pk = pk;
		this.fields = fields;
	}

	/**
	 * 创建一个新的实例 FieldGroup.
	 *
	 * @param name
	 * @param pk
	 * @param fields
	 */
	public FieldGroup(String name, FieldComment pk, FieldComment[] fields) {
		super();
		this.name = name;
		this.pk = new FieldComment[]{pk};
		this.fields = fields;
	}
}
