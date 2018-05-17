package com.ssxt.common.info;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;

import org.hibernate.cfg.Comment;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSONObject;
import com.ssxt.common.json.JsonHelper;
import com.ssxt.common.util.DataUtil;

public class BeanComparer<T> {

	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(BeanComparer.class);
	private T src;
	private Object target;
	private JSONObject json_src;
	private JSONObject json_target;
	private Class<T> entityClass;
	private List<String>  ignoreProps;
	/**
	 * 通过oldBean-Map比较，获得变量比较的访问字符串
	 * @param src
	 * @param target
	 * @param format
	 * @return 
	 *String
	 */
	public  String  compareFileldChange(String format,boolean ignoreNull) {
		boolean formatNotNull=!DataUtil.isNull(format);
		StringBuilder sb = new StringBuilder();
		json_src=(JSONObject) JsonHelper.toJSONBasic(src);
		json_target=(JSONObject) JsonHelper.toJSONBasic(target);
		Class<T> cls_src = entityClass;
//		Class<Object> cls_target = (Class<Object>) target.getClass();
		
		for (Map.Entry<String, Object> entry : json_target.entrySet()) {
			boolean updaFlag=true;
			String key=entry.getKey();
			if(isIgnoreProps(key))
				continue;				
			Object value_new=json_target.get(key);
            Object value_old = json_src.get(key);
            log.trace("value_new:{},value_old:{}",value_new,value_old);
			if(ignoreNull&&DataUtil.isNull(value_new))
				continue;
			if(value_old!=null && value_old.equals(value_new)){
				continue;
			}
			String comment=key;
			String getMethodName = "get"
					+ key.substring(0, 1).toUpperCase()
					+ key.substring(1);
			Method mhd_src = (Method) BeanUtils.findMethod(cls_src, getMethodName);
//			Method mhd_target = (Method) BeanUtils.findMethod(cls_target, getMethodName);

			String setMethodName = "set"
					+ key.substring(0, 1).toUpperCase()
					+ key.substring(1);
			Method mhd_set =null;
			Class<?> trueType=null;
			try {
				trueType = value_new.getClass();
				mhd_set = (Method) BeanUtils.findMethod(cls_src, setMethodName, trueType);
			} catch (Exception e) {
				log.error("findMethod:{}-{}", cls_src, setMethodName, e);
			}
			if (mhd_set == null)
			try {
				trueType = getTrueType(value_new);
				mhd_set = (Method) BeanUtils.findMethod(cls_src, setMethodName, trueType);
			} catch (Exception e) {
				log.error("findMethod:{}-{}", cls_src, setMethodName, e);
			}

			if(mhd_set==null){
				log.warn("找不到set方法:{},类型:{}",setMethodName,trueType);
				continue;
			}
			try {
				mhd_set.invoke(src, value_new);
			} catch (Exception e) {
				log.warn("反射设置值异常",e);
			}
			Comment metac = mhd_src.getAnnotation(Comment.class);  
			if(metac!= null){
				comment=metac.value();
			}
			else{
				Column meta = mhd_src.getAnnotation(javax.persistence.Column.class);  
				if(meta!= null){
					comment=getCommentFromDef(meta.columnDefinition());
				}
			}
			if(DataUtil.isNull(comment)){
				comment=key;
			}
			
//			if(value_old!=null)
//			log.debug("class {}:{}=>:{}",key,value_old.getClass(),value_new.getClass());
			updaFlag=true;
			if (value_new instanceof java.util.Date) {
				try{

					java.util.Date date_new = (java.util.Date) value_new;
					java.util.Date date_old = (java.util.Date) value_old;
					log.trace("日期:{}-{}",date_old,date_new);
					if(date_new!=null && date_old!=null)
					if(date_new.getTime()==date_old.getTime()){
						log.debug("日期一致:{}",date_new);
						updaFlag=false;
						continue;
					}
				}
				catch (Exception e) {
					log.debug("日期对比出错!",e);
				}
			}
			log.debug("修改 {}:{}=>:{}",key,value_old,value_new);
			if(formatNotNull)
			sb.append(String.format(format, comment ,key,value_old, value_new));	
			json_src.put(key, value_new);
			
		}
		return sb.toString();
	}

	/**
	 * 获得变量比较的访问字符串
	 * @param src
	 * @param target
	 * @param format
	 * @return 
	 *String
	 */
	public  String  compareFileldChangeByHbm(String format,boolean ignoreNull) {
		boolean formatNotNull=!DataUtil.isNull(format);
		StringBuilder sb = new StringBuilder();
		json_src=(JSONObject) JsonHelper.toJSONBasic(src);
		json_target=(JSONObject) JsonHelper.toJSONBasic(target);
		Class<T> cls_src = entityClass;
//		Class<Object> cls_target = (Class<Object>) target.getClass();
		

		Map<String, String> commentsMap=HibernateCfgHelper.getComments(entityClass.getClass());
		
		for (Map.Entry<String, Object> entry : json_target.entrySet()) {
			String key=entry.getKey();
			if(isIgnoreProps(key))
				continue;				
			Object value_new=json_target.get(key);
            Object value_old = json_src.get(key);
            log.trace("value_new:{},value_old:{}",value_new,value_old);
			if(ignoreNull&&DataUtil.isNull(value_new))
				continue;
			if(value_old!=null && value_old.equals(value_new)){
				continue;
			}
			String comment=key;
//			String getMethodName = "get"
//					+ key.substring(0, 1).toUpperCase()
//					+ key.substring(1);
//			Method mhd_src = (Method) BeanUtils.findMethod(cls_src, getMethodName);
//			Method mhd_target = (Method) BeanUtils.findMethod(cls_target, getMethodName);

			String setMethodName = "set"
					+ key.substring(0, 1).toUpperCase()
					+ key.substring(1);
			Method mhd_set =null;
			Class<?> trueType=null;
			try{
				trueType=value_new.getClass();
				mhd_set=(Method) BeanUtils.findMethod(cls_src, setMethodName,trueType);
			}catch (Exception e) {
				log.error("findMethod:{}-{}",cls_src,setMethodName,e);
			}
			if(mhd_set==null)
			try {
				trueType = getTrueType(value_new);
				mhd_set = (Method) BeanUtils.findMethod(cls_src, setMethodName, trueType);
			} catch (Exception e) {
				log.error("findMethod:{}-{}", cls_src, setMethodName, e);
			}
			try {
				mhd_set.invoke(src, value_new);
			} catch (Exception e) {
				log.warn("反射设置值异常",e);
			}
			//从配置文件中读取备注信息
			comment=commentsMap.get(key);
			
			if(DataUtil.isNull(comment)){
				comment=key;
			}
			log.debug("修改 {}:{}=>:{}",key,value_old,value_new);
			if(formatNotNull)
			sb.append(String.format(format, comment ,key,value_old, value_new));	
			json_src.put(key, value_new);
			
		}
		return sb.toString();
	}
	/**
	 * 获取某类所有字段，包括上级字段
	 * @param cls
	 * @return 
	 *List<Field>
	 * @exception 
	 * @since  1.0.0
	 */
	public static List<Field> getAllFieldsList(Class<?> cls) {
		List<Field> allFields = new LinkedList<Field>();
		Class<?> currentClass = cls;
		while (currentClass != null) {
			Field[] declaredFields = currentClass.getDeclaredFields();
			for (Field field : declaredFields) {
				allFields.add(field);
			}
			currentClass = currentClass.getSuperclass();
		}
		return allFields;
	}

	/**
	 * 通过oldBean-Map比较，获得变量比较的访问字符串
	 * @param src
	 * @param target
	 * @param format
	 * @return 
	 *String
	 */
	public String compareMapChange(String format, boolean ignoreNull) {
		boolean formatNotNull=!DataUtil.isNull(format);
		StringBuilder sb = new StringBuilder();
		json_src=(JSONObject) JsonHelper.toJSONBasic(src);
		json_target=(JSONObject) JsonHelper.toJSONBasic(target);
		try {
			List<Field> fields = getAllFieldsList(entityClass);
			for (Field field : fields) {
				String key = field.getName();
				PropertyDescriptor property = BeanUtils.getPropertyDescriptor(entityClass, key);
				// log.trace("key:{}",key);
				if (isIgnoreProps(key))
					continue;
				Object value_new = json_target.get(key);
				Object value_old = json_src.get(key);
				log.trace("value_new:{},value_old:{}", value_new, value_old);
				if (ignoreNull && DataUtil.isNull(value_new))
					continue;
    			String comment=key;
                // 得到property对应的setter方法  
                Method mhd_set = property.getWriteMethod();  

    			if(mhd_set==null){
    				log.warn("找不到set方法:{}",key,value_old.getClass());
    				continue;
    			}
    			try {
    				mhd_set.invoke(src, value_new);
    			} catch (Exception e) {
    				log.warn("反射设置值异常",e);
    			}
                Method mhd_src = property.getReadMethod(); 
    			Column meta = mhd_src.getAnnotation(javax.persistence.Column.class);  
    			if(meta!= null){
    				comment=getCommentFromDef(meta.columnDefinition());
    			}
    			if(DataUtil.isNull(comment)){
    				comment=key;
    			}
    			log.debug("修改 {}:{}=>:{}",key,value_old,value_new);
    			if(formatNotNull)
    			sb.append(String.format(format, comment ,key,value_old, value_new));	
    			json_src.put(key, value_new);
			}

		} catch (Exception e) {
			// log.warn("比较Map设置值异常",e);
			throw new RuntimeException("比较Map设置值异常", e);
		}
		return sb.toString();
	}
	public boolean isIgnoreProps(String propName){
		if(ignoreProps==null||ignoreProps.size()==0)
			return false;
		return ignoreProps.contains(propName);		
	}

	public static String getCommentFromDef(String str){
		String bak=str;
		boolean flag=bak.contains("COMMENT");
		if(!flag)
			return null;
		String tmp="COMMENT";
		int idx=bak.indexOf(tmp);
		int start=bak.indexOf("'",idx);
		int end=bak.indexOf("'",start+1);
		if(start==-1)start=idx;
		if(end==-1)end=bak.length();
		if(start>0)
		bak=bak.substring(start+1,end);
//		String[] arr=new String[]{"COMMENT","'"};
//		for (int i = 0; i < arr.length; i++) {
//			String tmp=arr[i];
//			if(bak.contains(tmp)){
//				bak=bak.substring(bak.indexOf(tmp)+tmp.length());
//			}
//		}
//		String tmp="'";
//		if(bak.contains(tmp)){
//			bak=bak.substring(bak.lastIndexOf(tmp)+tmp.length());
//		}
		return bak;
	}
	@SuppressWarnings("unchecked")
	public BeanComparer(T src, T target) {
		super();
		this.src = src;
		this.target = target;
		this.entityClass =(Class<T>) src.getClass();
	}

	@SuppressWarnings("unchecked")
	public BeanComparer(T src, T target,String[] ignoreNames) {
		super();
		this.src = src;
		this.target = target;
		this.entityClass =(Class<T>) src.getClass();
		this.ignoreProps=Arrays.asList(ignoreNames);
	}

	@SuppressWarnings("unchecked")
	public BeanComparer(T src, Map<String,Object> target,String[] ignoreNames) {
		super();
		this.src = src;
		this.target = target;
		this.entityClass =(Class<T>) src.getClass();
		this.ignoreProps=Arrays.asList(ignoreNames);
	}

    public  T getResult() {
    	log.warn("{}结果:{}",entityClass,JsonHelper.toJSONString(json_src));;
    	return (T) JsonHelper.toJavaObject(json_src,entityClass);
    }

	/**
	 * ignoreProps
	 *
	 * @return  the ignoreProps
	 * @since   1.0.0
	*/
	
	public List<String> getIgnoreProps() {
		return ignoreProps;
	}

	/**
	 * @param ignoreProps the ignoreProps to set
	 */
	public void setIgnoreProps(List<String> ignoreProps) {
		this.ignoreProps = ignoreProps;
	}
	
	/**
	 * 获取真实类型
	 * @param obj
	 * @return 
	 *Class<?>
	 * @exception 
	 * @since  1.0.0
	 */
	public Class<?> getTrueType(Object obj) {
		Class<?> type = null;
		Class<?> clazz = obj.getClass();
		if (clazz.equals(Integer.class)) {
			type = Integer.TYPE;
		}
		if (clazz.equals(Boolean.class)) {
			type = Boolean.TYPE;
		}
		if (clazz.equals(Double.class)) {
			type = Double.TYPE;
		}
		if (clazz.equals(Float.class)) {
			type = Float.TYPE;
		}
		if (clazz.equals(Byte.class)) {
			type = Byte.TYPE;
		}
		if (clazz.equals(Long.class)) {
			type = Long.TYPE;
		}
		if (type == null) {
			type = clazz;
		}
		return type;
	}

}
