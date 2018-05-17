package com.ssxt.common.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.LockMode;
import org.springframework.dao.DataAccessException;

import com.ssxt.common.page.PageControl;
import com.ssxt.common.page.SqlCondGroup;
import com.ssxt.common.util.DataUtil;

/**
 *
 */
public interface GenericDao<T extends Serializable, PK extends Serializable> {
	// -------------------- 基本检索、增加、修改、删除操作 --------------------
	// 根据主键获取实体。如果没有相应的实体，返回 null。
	public T get(PK id) throws DataAccessException;

	// 根据主键获取实体并加锁。如果没有相应的实体，返回 null。
	public T getWithLock(PK id, LockMode lock);

	// 根据主键获取实体。如果没有相应的实体，抛出异常。
	public T load(PK id);

	// 根据主键获取实体并加锁。如果没有相应的实体，抛出异常。
	public T loadWithLock(PK id, LockMode lock);

	// 获取全部实体。
	public List<T> loadAll();

	// 根据实例查询
	public List<T> queryByExample(T exampleInstance) throws DataAccessException;

	// 根据实例查询第一个实体
	public T getFirstByExample(T entity);

	// 存储实体到数据库
	public PK save(T entity);

	// 增加或更新实体
	public void saveOrUpdate(T entity);

	// 增加或更新集合中的全部实体
	public void saveOrUpdateAll(Collection<T> entities);

	// 更新实体
	public void update(T entity);

	// 更新实体并加锁
	public void updateWithLock(T entity, LockMode lock);

	// 删除指定的实体
	public void delete(T entity);

	// 加锁并删除指定的实体
	public void deleteWithLock(T entity, LockMode lock);

	// 根据主键删除指定实体
	public void deleteByKey(PK id);

	// 根据主键加锁并删除指定的实体
	public void deleteByKeyWithLock(PK id, LockMode lock);

	// 删除集合中的全部实体
	public void deleteAll(Collection<T> entities);

	// -------------------- HSQL ----------------------------------------------

	// 使用HSQL语句检索数据，返回 Iterator，使用缓存，推荐只读对象使用
	public Iterator iterate(String queryString);

	// 使用带参数HSQL语句检索数据，返回 Iterator，使用缓存，推荐只读对象使用
	public Iterator iterate(String queryString, Object[] values);

	// 关闭检索返回的 Iterator，使用缓存，推荐只读对象使用
	public void closeIterator(Iterator it);

	// 使用HSQL语句直接增加、更新、删除实体
	public int bulkUpdate(String queryString);

	// 使用带参数的HSQL语句增加、更新、删除实体
	public int bulkUpdate(String queryString, Object[] values);

	// 使用带参数的HSQL语句增加、更新、删除实体
	public int executeUpdate(String queryString, Object[] values);

	// -------------------------------- Others --------------------------------

	// 加锁指定的实体
	public void lock(T entity, LockMode lockMode);

	// 强制初始化指定的实体
	public void initialize(Object proxy);

	// 强制立即更新缓冲数据到数据库（否则仅在事务提交时才更新）
	public void flush();

	// 查询实例的数量
	public long getCount();

	// 清除旧对象的session
	public void clearSession();

	// 接管实例（未实现）
	public void reattach(T persistentObject);

	// 脱管实例（未实现）
	public void detach(T persistentObject);

	// ypx
	/**
	 * 
	 * hql返回按SqlCondGroup条件查询的结果列表<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * (这里描述这个方法的执行流程 – 可选)<br/>
	 * (这里描述这个方法的使用方法 – 可选)<br/>
	 * (这里描述这个方法的注意事项 – 可选)<br/>
	 * 
	 * @param pageControl
	 * @param conList
	 * @return List
	 * @exception @since
	 *                1.0.0
	 */
	@SuppressWarnings("unchecked")
	public List findByCondition(PageControl pageControl, List<SqlCondGroup> conList);

	/**
	 * 
	 * sql返回按SqlCondGroup条件查询的结果列表<br/>
	 * (这里描述这个方法适用条件 – 可选)<br/>
	 * (这里描述这个方法的执行流程 – 可选)<br/>
	 * (这里描述这个方法的使用方法 – 可选)<br/>
	 * (这里描述这个方法的注意事项 – 可选)<br/>
	 * 
	 * @param pageControl
	 * @param conList
	 * @param preWhereSql
	 *            where之前的语句,如"select * from abc"
	 * @return List
	 * @exception @since
	 *                1.0.0
	 */
	@SuppressWarnings("unchecked")
	public List findByNativeCondition(PageControl pageControl, List<SqlCondGroup> conList, String preWhereSql,
			Class returnType);

	/**
	 * 
	 * 创建数据<br/>
	 * 
	 * @param schoolId
	 * @param userId
	 * @param userName
	 * @param reason
	 * @param domain
	 * @return
	 */
	public Serializable saveDomain(String schoolId, Integer userId, String userName, String reason, T domain);

	/**
	 * 修改数据
	 * 
	 * @param schoolId
	 * @param userId
	 * @param userName
	 * @param reason
	 * @param oldBean
	 * @param newBean
	 */
	public void updateDomain(String schoolId, Integer userId, String userName, String reason, T oldBean, T newBean);

	/**
	 * 
	 * 判断该实体类是否存在<br/>
	 * (适用条件):<br/>
	 * (执行流程):内部调用findByExample执行<br/>
	 * (使用方法):<br/>
	 * (注意事项):<br/>
	 * 
	 * @param domain
	 * @return boolean
	 * @exception @since
	 *                1.0.0
	 */
	public boolean existDomain(T domain);

	/**
	 * 
	 * 判断id是否存在<br/>
	 * (适用条件):<br/>
	 * (执行流程):内部调用getById方法<br/>
	 * (使用方法):<br/>
	 * (注意事项):<br/>
	 * 
	 * @param id
	 * @return boolean
	 * @exception @since
	 *                1.0.0
	 */
	public boolean existId(PK id);

	// public void commonConList(List<SqlCondGroup> conList, T bean);

	/**
	 * 
	 * 根据sql条件更新数据
	 * 
	 * @param updateSql
	 *            sql
	 * @param conList
	 *            条件列表
	 * @return int
	 * @exception @since
	 *                1.0.0
	 */
	public int updateByNativeCondition(String updateSql, List<SqlCondGroup> conList);

	/**
	 * 根据条件更新数据 (注意事项):执行完，需要手动清空有关缓存<br/>
	 * 
	 * @param updateSql
	 *            hql
	 * @param conList
	 *            条件列表
	 * @return int
	 * @exception @since
	 *                1.0.0
	 */
	public int updateByCondition(String updatehql, List<SqlCondGroup> conList);

	/**
	 * 
	 * 根据条件更新数据 需要手动清空有关缓存 (注意事项):执行完，需要手动清空有关缓存<br/>
	 * 
	 * @param setList
	 *            设置值列表
	 * @param conList
	 *            条件列表
	 * @return int
	 * @exception @since
	 *                1.0.0
	 */
	public int updateByCondition(List<SqlCondGroup> setList, List<SqlCondGroup> conList);

	/**
	 * 估計sql條件刪除
	 * 
	 * @param sql
	 * @param conList
	 * @return
	 */
	public int deleteSql(String sql, List<SqlCondGroup> conList);

	/**
	 * 估計hql條件刪除
	 * 
	 * @param sql
	 * @param conList
	 * @return
	 */
	public int deleteHql(List<SqlCondGroup> conList);

	/**
	 * 估计hql 查询
	 * 
	 * @param hql
	 * @return
	 */
	public List<T> findSQL(String hql);

	/**
	 * 存储过程
	 * 
	 * @param name
	 */
	public void prepareCall(final String name);
	
	/**
	 * 插入sql
	 * @param sql
	 * @return
	 */
	public int insert(final String sql) ;

}