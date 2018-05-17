package com.ssxt.common.info;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.cfg.Comment;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.PrimaryKey;
import org.hibernate.mapping.Property;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;

import com.ssxt.common.util.DataUtil;
import com.ssxt.common.util.SpringBeanUtil;  
  
  
/** 
 * 功能描述：根据实体类得到对应的表名、主键名、字段名工具类 </p> 
 * 注：po类名须与对应映射文件名一致，即Student.java与Student.hbm.xml 
 *  
 * @Date：Nov 10, 2008 
 * @Time：3:13:07 PM 
 *  
 */  
public class HibernateCfgHelper {  
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HibernateCfgHelper.class);
    public static Configuration configuration;  
    
    /**
     * 获得Hibernate的配置
     * (适用条件):<br/>
     * (执行流程):<br/>
     * (使用方法):<br/>
     * (注意事项):<br/>
     * @return 
     *Configuration Hibernate的配置
     * @exception 
     * @since  1.0.0
     */
    private static Configuration getHibernateConf() {  
        if (configuration == null) {  
            // 取sessionFactory的时候要加上&  
        	LocalSessionFactoryBean factory = (LocalSessionFactoryBean) 
        			SpringBeanUtil.getBean("&sessionFactory",LocalSessionFactoryBean.class);  
            configuration = factory.getConfiguration();  
        }  
        return configuration;  
    }  
    
    /**
     * 获得持久化的类（属性配置）
     * (适用条件):<br/>
     * (执行流程):<br/>
     * (使用方法):<br/>
     * (注意事项):<br/>
     * @param clazz	类的Class类型
     * @return
     *PersistentClass 持久化的类（属性配置）
     * @exception 
     * @since  1.0.0
     */
    public static PersistentClass getPersistentClass(Class<?> clazz) {  
        synchronized (HibernateCfgHelper.class) {  
        	Configuration configuration = getHibernateConf();
            PersistentClass pc = configuration.getClassMapping(  
                    clazz.getName());  
            if (pc == null) {  
                configuration.addClass(clazz);  
                pc = configuration.getClassMapping(clazz.getName());  
  
            }  
            return pc;  
        }  
    }  
    /** 
     * 功能描述：获取实体对应的表名 
     *  
     * @param clazz 
     *            实体类 
     * @return 表名 
     */  
    public static String getTableName(Class<?> clazz) {  
        return getPersistentClass(clazz).getTable().getName();  
    }  
    /** 
     * 功能描述：获取实体对应的表名注释
     *  
     * @param clazz 
     *            实体类 
     * @return 表名 
     */  
    public static String getTableComment(Class<?> clazz) {  
        return getPersistentClass(clazz).getTable().getComment();  
    }  
    /** 
     * 功能描述：获取实体对应表的主键字段名称 
     *  
     * @param clazz 
     *            实体类 
     * @return 主键字段名称 
     */  
    public static String getPkColumnName(Class<?> clazz) {  
  
        return getPersistentClass(clazz).getTable().getPrimaryKey()  
                .getColumn(0).getName();
  
    }  
    /** 
     * 功能描述：获取实体对应表的主键字段
     *  
     * @param clazz 
     *            实体类 
     * @return 主键字段名称 
     */  
    public static FieldComment getPkColumn(Class<?> clazz) {  
    	PrimaryKey pk=getPersistentClass(clazz).getTable().getPrimaryKey();
        Column col= pk.getColumn(0);  
        Class<?> returnType=col.getClass();

		String name = col.getName();
		String comment = col.getComment();
		if (DataUtil.isNull(comment)) {
			comment = name;
		}		
		FieldComment fc=new FieldComment();
		fc.setName(name);
		fc.setComment(comment);
		fc.setType(returnType);
		return fc;
  
    }  

    /** 
     * 根据实体类获取所有列的备注信息 
     * (适用条件):<br/>
     * (执行流程):<br/>
     * (使用方法):<br/>
     * (注意事项):<br/>
     * @param clazz
     * @return 
     *Map<String,String>
     * @exception 
     * @since  1.0.0
     */
    public static Map<String,String> getComments(Class<?> clazz){  
        PersistentClass persistentClass = getPersistentClass(clazz);  
        Iterator<Property> it=persistentClass.getPropertyIterator();  
        Map<String, String> comments=new HashMap<String, String>();  
        while(it.hasNext()){  
            Property propert= it.next();  
            Iterator<Column> columns=propert.getColumnIterator();  
            while(columns.hasNext()){  
				Column column = columns.next();
				
				String name = propert.getName();
				String comment = column.getComment();
				if (DataUtil.isNull(comment)) {
					comment = name;
				} else
//					comment = NCRUtils.decode(comment, '_');
				comments.put(name, column.getSqlType()+":"+comment);
            }  
        }  
        return comments;  
    }  
    /** 
     * 根据实体类获取所有列的备注信息 
     * (适用条件):<br/>
     * (执行流程):<br/>
     * (使用方法):<br/>
     * (注意事项):<br/>
     * @param clazz
     * @return 
     *Map<String,String>
     * @exception 
     * @since  1.0.0
     */
    public static FieldComment[] getCommentsArr(Class<?> clazz){  
        PersistentClass persistentClass = getPersistentClass(clazz);  
        Iterator<Property> it=persistentClass.getPropertyIterator();  
//        Map<String, FieldComment> comments=new HashMap<String, FieldComment>();  
        List<FieldComment> comments=new ArrayList<FieldComment>();
        while(it.hasNext()){  
            Property propert= it.next();  
			Class<?> returnType=propert.getType().getClass();
            Iterator<Column> columns=propert.getColumnIterator();  
            while(columns.hasNext()){  
				Column column = columns.next();
				
				String name = propert.getName();
				String comment = column.getComment();
				if (DataUtil.isNull(comment)) {
					comment = name;
				} 
//				else comment = NCRUtils.decode(comment, '_');
				
//				String value=comment;
//				String value=String.format("%s:%s", returnType,comment);
//				comments.put(name, value);

				FieldComment fc=new FieldComment();
				fc.setName(name);
				fc.setComment(comment);
				fc.setType(returnType);
//				comments.put(name, fc);
				comments.add(fc);
            }  
        }  
        return DataUtil.toArray(FieldComment.class,comments);  
    }  

    /**
     * 
     * getCommentsByAnnotation(作用)<br/>
     * (适用条件):<br/>
     * (执行流程):<br/>
     * (使用方法):<br/>
     * (注意事项):<br/>
     * @param clazz
     * @return 
     *Map<String,String>
     * @exception 
     * @since  1.0.0
     */
    public static Map<String,String> getCommentsByAnnotation(Class<?> clazz){  
        Map<String, String> comments=new HashMap<String, String>();
		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(clazz);
//		List<String> ignoreList =  null;
		String[] ignoreProperties = null;
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
		for (PropertyDescriptor targetPd : targetPds) {
			Method mhd_src=targetPd.getReadMethod();
			if (mhd_src != null &&
					(ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {

				String key=targetPd.getName();
				if("class".equals(key)){
					continue;
				}
				String comment=key;
				Comment metac = mhd_src.getAnnotation(Comment.class);  
				if(metac!= null){
					comment=metac.value();
				}
				else{
					javax.persistence.Column meta = mhd_src.getAnnotation(javax.persistence.Column.class);  
					if(meta!= null){
						comment=BeanComparer.getCommentFromDef(meta.columnDefinition());
					}
				}
				if(DataUtil.isNull(comment)){
					comment=key;
				}
				comments.put(key, comment);
			}
		}
        return comments;  
    }  
    /** 
     * 根据实体类获取所有列的备注信息 
     * (适用条件):<br/>
     * (执行流程):<br/>
     * (使用方法):<br/>
     * (注意事项):<br/>
     * @param clazz
     * @return 
     *Map<String,String>
     * @exception 
     * @since  1.0.0
     */
    public static FieldComment[] getCommentsArrByAnnotation(Class<?> clazz){  
//        Map<String, FieldComment> comments=new HashMap<String, FieldComment>();
    	List<FieldComment> comments=new ArrayList<FieldComment>();
		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(clazz);
//		List<String> ignoreList =  null;
		String[] ignoreProperties = null;
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;
		for (PropertyDescriptor targetPd : targetPds) {
			String key=targetPd.getName();
			if("class".equals(key)){
				continue;
			}
			String comment=key;
			
			Method mhd_src=targetPd.getReadMethod();
			if (mhd_src != null &&
					(ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				Class<?> returnType=mhd_src.getReturnType();
				Comment metac = mhd_src.getAnnotation(Comment.class);  
				if(metac!= null){
					comment=metac.value();
					
				}
				else{
					javax.persistence.Column meta = mhd_src.getAnnotation(javax.persistence.Column.class);  
					if(meta!= null){
						comment=BeanComparer.getCommentFromDef(meta.columnDefinition());
					}
				}
				if(DataUtil.isNull(comment)){
					comment=key;
				}

				FieldComment fc=new FieldComment();
				fc.setName(key);
				fc.setComment(comment);
				fc.setType(returnType);
//				String value=String.format("%s:%s", returnType,comment);
//				comments.put(key, fc);
				comments.add(fc);
			}
		}
        return DataUtil.toArray(FieldComment.class,comments);  
    }  

    /** 
     * 根据实体类和属性获取列的备注 (评论内容)
     * (适用条件):<br/>
     * (执行流程):<br/>
     * (使用方法):<br/>
     * (注意事项):<br/>
     * @param clazz 
     *            实体类 
     * @param propertyName 
     *            属性名称 
     * @return 
     *String	备注
     * @exception 
     * @since  1.0.0
     */
    public static String getComment(Class<?> clazz,String propertyName){  
        PersistentClass persistentClass = getPersistentClass(clazz);  
        Property property = persistentClass.getProperty(propertyName);  
        Iterator<Column> it = property.getColumnIterator();  
        if (it.hasNext()) {  
            Column column = (Column) it.next();  
            return column.getComment();  
        }  
        return null;  
    }  
    /** 
     * 
     * 功能描述：通过实体类和属性，获取实体类属性对应的表字段名称 
     * (适用条件):<br/>
     * (执行流程):<br/>
     * (使用方法):<br/>
     * (注意事项):<br/>
	 * @param clazz 
	 *            实体类 
	 * @param propertyName 
	 *            属性名称 
	 * @return 字段名称 
     *String
     * @exception 
     * @since  1.0.0
     */
    @SuppressWarnings("unchecked")  
    public static String getColumnName(Class<?> clazz, String propertyName) {  
        PersistentClass persistentClass = getPersistentClass(clazz);  
        Property property = persistentClass.getProperty(propertyName);  
          
        Iterator<Column> i=property.getColumnIterator();  
        while(i.hasNext()){  
            System.out.println(((Column)i.next()).getComment());  
        }  
        System.out.println(property.getMetaAttributes());  
        Iterator<Column> it = property.getColumnIterator();  
        if (it.hasNext()) {  
            Column column = (Column) it.next();  
            return column.getName();  
        }  
        return null;  
    }  
}  