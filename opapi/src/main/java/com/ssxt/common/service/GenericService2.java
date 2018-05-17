package com.ssxt.common.service;
//package com.ssxt.rdbox.common.service;
//
//import java.io.Serializable;
//import java.util.Collection;
//import java.util.List;
//
//import com.ssxt.rdbox.common.page.PageControl;
//import com.ssxt.rdbox.common.page.SqlCondGroup;
//
///**
// * @ClassName：GenericService
// * @Description：TODO(GenericService Service层泛型接口，定义基本的Service功能)
// * @param <T>
// *            实体类
// * @param <PK>
// *            主键类，必须实现Serializable接口
// * @author zhengxican
// * @date 2015-08-25
// * @version V1.0
// */
//public interface GenericService2<T extends Serializable, PK extends Serializable> {
//
//	/**
//	 * 按主键取记录
//	 * 
//	 * @param id
//	 *            主键值
//	 * @return 记录实体对象，如果没有符合主键条件的记录，则返回null
//	 */
//	public T get(PK id);
//
//	/**
//	 * 按主键取记录
//	 * 
//	 * @param id
//	 *            主键值
//	 * @return 记录实体对象，如果没有符合主键条件的记录，则 throw DataAccessException
//	 */
//	public T load(PK id);
//
//	/**
//	 * 获取全部实体
//	 * 
//	 * @return 返回一个list集合数据
//	 */
//	public List<T> loadAll();
//	/**
//	 * 根据实例查询符合条件的记录。
//	 * @param entity
//	 * @return
//	 */
//	public List<T> queryByExample(T entity);
//	/**
//	 * 根据实例查询第一个符合条件的记录。
//	 * @param entity
//	 * @return
//	 */
//	public T getFirstByExample(T entity);
//
//	/**
//	 * 插入一个实体（在数据库INSERT一条记录）
//	 * 
//	 * @param entity
//	 *            实体对象
//	 */
//	public PK save(T entity);
//
//	/**
//	 * 增加或更新实体
//	 * 
//	 * @param entity
//	 *            实体对象
//	 */
//	public void saveOrUpdate(T entity);
//
//	/**
//	 * 修改一个实体对象（UPDATE一条记录）
//	 * 
//	 * @param entity
//	 *            实体对象
//	 */
//	public void update(T entity);
//
//	/**
//	 * 删除指定的实体
//	 * 
//	 * @param entity
//	 *            实体对象
//	 */
//	public void delete(T entity);
//	
//	/**
//	 * 根据主键删除记录
//	 * @param id
//	 */
//	public void deleteByKey(PK id);
//	
//	/**
//	 * 删除多个实体
//	 * @param entities
//	 */
//	public void deleteAll(Collection<T> entities);
//	
//	
//	
//	//ypx
//
//	/**
//	 * 
//	 * findByCondition(这里用一句话描述这个方法的作用)<br/>
//	 * (这里描述这个方法适用条件 – 可选)<br/>
//	 * (这里描述这个方法的执行流程 – 可选)<br/>
//	 * (这里描述这个方法的使用方法 – 可选)<br/>
//	 * (这里描述这个方法的注意事项 – 可选)<br/>
//	 * @param pageControl
//	 * @param conList
//	 * @return 
//	 *List
//	 * @exception 
//	 * @since  1.0.0
//	 */
//	@SuppressWarnings("unchecked")
//	public List findByCondition(PageControl pageControl,List<SqlCondGroup> conList);
//	/**
//	 * 返回按属性条件查询的结果列表,采用in方式,即propertyName中第一个propertyName对应valueList中的第一个List，以此类推
//	 * @param pageControl    用来控制排序和分页的参数类
//	 * @param propertyName   属性名
//	 * @param valueList          属性值列表
//	 * @param sign           符号
//	 * @param type           条件连接类型(and还是or)
//	 * @return               查询结果(List列表)
//	 */	
//	@SuppressWarnings("unchecked")
//	public List<T> findByInProperties(PageControl pageControl,
//			String[] propertyName, List[] valueList, String[] sign, String[] type);
//	/**
//	 * 返回带统计函数、按属性条件查询的结果列表（List<Map>）,采用in方式,即propertyName中第一个propertyName对应valueList中的第一个List，以此类推
//	 * 如果是多表且有重复列，必须在列前带表的别名如a.colmn1,c.colmn2
//	 * @param pageControl    用来控制排序和分页的参数类
//	 * @param fromTable    	 from表名，多表如A m left outer join B n
//	 * @param useProperty    需要获得的参数名
//	 * @param alias			 别名，用map.get(别名)获取参数值:
//	 * @param propertyName   属性名
//	 * @param valueList      属性值列表
//	 * @param sign           符号
//	 * @param type           条件连接类型(and还是or)
//	 * @return               查询结果(List列表)
//	 */	
//	@SuppressWarnings("unchecked")
//	public List findByStatistics(PageControl pageControl,String fromTable,String[] useProperty,String[] alias, String[] propertyName, List[] valueList, String[] sign, String[] type);
//	
//	/**
//	 * 
//	 * sql返回按SqlCondGroup条件查询的结果列表<br/>
//	 * (这里描述这个方法适用条件 – 可选)<br/>
//	 * (这里描述这个方法的执行流程 – 可选)<br/>
//	 * (这里描述这个方法的使用方法 – 可选)<br/>
//	 * (这里描述这个方法的注意事项 – 可选)<br/>
//	 * @param pageControl
//	 * @param conList
//	 * @param preWhereSql where之前的语句,如"select * from abc"
//	 * @return 
//	 *List
//	 * @exception 
//	 * @since  1.0.0
//	 */
//	@SuppressWarnings("unchecked")
//	public List findByNativeCondition(PageControl pageControl,
//			List<SqlCondGroup> conList,String preWhereSql,Class returnType);
//	
//	/**
//	 * 使用SQL语句查询数据，附加查询参数
//	 * @param pageControl    用来控制排序和分页的参数类
//	 * @param sql   查询语句
//	 * @param returnType 返回的类型，为Map,List或者Bean类型(null默认)
//	 * @return      查询结果(List列表)
//	 */
//	@SuppressWarnings("unchecked")
//	public List findByNativeSQL(PageControl pageControl,String sql,Class returnType);
//
//	/**
//	 * 使用SQL语句查询数据，附加查询参数
//	 * @param pageControl    用来控制排序和分页的参数类
//	 * @param sql   查询语句
//	 * @param value 属性值
//	 * @param returnType 返回的类型，为Map,List或者Bean类型(null默认)
//	 * @return      查询结果(List列表)
//	 */
//	@SuppressWarnings("unchecked")
//	public List findByNativeSQL(PageControl pageControl,String sql,Object[] object,Class returnType);
//
//	/**
//	 * 
//	 * sql返回按propertyName,valueList,sign,type条件查询的结果列表<br/>
//	 * @param pageControl
//	 * @param preWhereSql where之前的语句,如"select * from abc"
//	 * @param propertyName
//	 * @param valueList
//	 * @param sign
//	 * @param type
//	 * @return 
//	 *List<T>
//	 * @exception 
//	 * @since  1.0.0
//	 */
//	@SuppressWarnings("unchecked")
//	public List findByNativeInProperties(PageControl pageControl,String preWhereSql,
//			String[] propertyName, List[] valueList, String[] sign, String[] type,Class returnType);
//	
////	/**
////	 * 
////	 * putSchoolNameBySchoolId(这里用一句话描述这个方法的作用)<br/>
////	 * (这里描述这个方法适用条件 – 可选)<br/>
////	 * (这里描述这个方法的执行流程 – 可选)<br/>
////	 * (这里描述这个方法的使用方法 – 可选)<br/>
////	 * (这里描述这个方法的注意事项 – 可选)<br/>
////	 * @param list
////	 * @return 
////	 *List<Map>
////	 * @exception 
////	 * @since  1.0.0
////	 */
////	@SuppressWarnings("unchecked")
////	public List<Map> putSchoolNameBySchoolId(List<Map> list);
////	/**
////	 * 
////	 * putDptNameByDptId(这里用一句话描述这个方法的作用)<br/>
////	 * (这里描述这个方法适用条件 – 可选)<br/>
////	 * (这里描述这个方法的执行流程 – 可选)<br/>
////	 * (这里描述这个方法的使用方法 – 可选)<br/>
////	 * (这里描述这个方法的注意事项 – 可选)<br/>
////	 * @param list
////	 * @return 
////	 *List<Map>
////	 * @exception 
////	 * @since  1.0.0
////	 */
////	@SuppressWarnings("unchecked")
////	public List<Map> putDptNameByDptId(List<Map> list);
////	
////	
////	
////	/**
////	 * 
////	 * getMapListGroupByInProperties(获取符合条件的映射组合列表，查询当前表)<br/>
////	 * 系统设定Group by需要获得的字段，不能自设置Group by
////	 * 非常适合极其复杂的查询<br/>
////	 * (这里描述这个方法的执行流程 – 可选)<br/>
////	 * (这里描述这个方法的使用方法 – 可选)<br/>
////	 * 外连接支持，但需要配置映射字段
////	 * 若带有统计函数，请不要使用分页中的统计总数功能<br/>
////	 * @param pageControl
////	 * @param alias
////	 * @param propertyName
////	 * @param valueList
////	 * @param sign
////	 * @param type
////	 * @return 
////	 *List<Map>
////	 * @exception 
////	 * @since  1.0.0
////	 */
////	@SuppressWarnings("unchecked")
////	public List<Map> getMapListGroupByInProperties(PageControl pageControl,String[] alias,String[] propertyName,List[] valueList,String[] sign,String[] type );
////
////	/**
////	 * 
////	 * getMapListGroupByInProperties(获取符合条件的映射组合列表)<br/>
////	 * 系统设定Group by需要获得的字段，不能自设置Group by
////	 * 非常适合极其复杂的查询<br/>
////	 * (这里描述这个方法的执行流程 – 可选)<br/>
////	 * (这里描述这个方法的使用方法 – 可选)<br/>
////	 * 外连接支持，但需要配置映射字段
////	 * 若带有统计函数，请不要使用分页中的统计总数功能<br/>
////	 * @param pageControl
////	 * @param alias
////	 * @param useProperty
////	 * @param propertyName
////	 * @param valueList
////	 * @param sign
////	 * @param type
////	 * @return 
////	 *List<Map>
////	 * @exception 
////	 * @since  1.0.0
////	 */
////	@SuppressWarnings("unchecked")
////	public List<Map> getMapListGroupByInProperties(PageControl pageControl,String[] alias,String[] useProperty,String[] propertyName,List[] valueList,String[] sign,String[] type );
////	
////
////
////	/**
////	 * 
////	 * getMapListGroupByInProperties(获取符合条件的映射组合列表)<br/>
////	 * 系统设定Group by需要获得的字段，不能自设置Group by
////	 * 非常适合多表并且所得字段分布于多表的查询<br/>
////	 * 非常适合极其复杂的查询<br/>
////	 * (这里描述这个方法的执行流程 – 可选)<br/>
////	 * (这里描述这个方法的使用方法 – 可选)<br/>
////	 * 外连接支持，但需要配置映射字段
////	 * 若带有统计函数，请不要使用分页中的统计总数功能<br/>
////	 * @param pageControl
////	 * @param fromTable
////	 * @param useProperty
////	 * @param alias
////	 * @param propertyName
////	 * @param valueList
////	 * @param sign
////	 * @param type
////	 * @return 
////	 *List<Map>
////	 * @exception 
////	 * @since  1.0.0
////	 */
////	@SuppressWarnings("unchecked")
////	public List<Map> getMapListGroupByInProperties(PageControl pageControl,String fromTable,String[] useProperty,String[] alias,String[] propertyName,List[] valueList,String[] sign,String[] type );
////
////	/**
////	 * 
////	 * 
////	 * getSpecifiedInfoByThisViewProperties(这里用一句话描述这个方法的作用)<br/>
////	 * (这里描述这个方法适用条件 – 可选)<br/>
////	 * (这里描述这个方法的执行流程 – 可选)<br/>
////	 * (这里描述这个方法的使用方法 – 可选)<br/>
////	 * (这里描述这个方法的注意事项 – 可选)<br/>
////	 * @param pageControl
////	 * @param notNullproperty
////	 * @param propertyName
////	 * @param valueList
////	 * @param sign
////	 * @param type
////	 * @return 
////	 *List<T>
////	 * @exception 
////	 * @since  1.0.0
////	 */
////	@SuppressWarnings("unchecked")
////	public List<T> getSpecifiedInfoByThisViewProperties(PageControl pageControl,String[] notNullproperty,List<String> propertyName,List<List> valueList,List<String> sign,List<String> type);
////	
//}
