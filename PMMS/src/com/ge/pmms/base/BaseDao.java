
package com.ge.pmms.base;

import java.util.List;

import org.hibernate.Query;


public interface BaseDao<T> {
	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	public void save(T entity);

	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	public void saveAll(List<T> list);
	
	
	/**
	 * 删除实体
	 * 
	 * @param id
	 */

	public void delete(Class<T> clazz,Integer id);
	/**
	 * 删除多个实体
	 * @Author: iGATE 
	 * @param clazz
	 * @param ids
	 * @return
	 * @Description:
	 */
	public int deleteByIds(Class<T> clazz,List<Integer> ids);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	public void update(T entity);

	/**
	 * 根据传入的hql更新
	 * @param hql
	 */
	public Query updateByHql(String hql);
	/**
	 * 按id查询
	 * 
	 * @param id
	 * @return
	 */

	public T getById(Class<T> clazz,Integer id);
	
	public T getByIdNoSession(Class<T> clazz,Integer id);

	/**
	 * 按ids查询
	 * 
	 * @param ids
	 * @return
	 */

	public List<T> getByIds(Class<T> clazz,Integer[] ids);

	/**
	 * 查询所有数据(只从数据库查询数据)
	 * (注意实体对象的entity name必须和类名一致 )
	 * @return 所有数据
	 */
	public List<T> getAll(Class<T> clazz);
	
	
	/**
	 * 查询所有数据(先从缓存中查询，如果查询不到，再从数据库查询数据)
	 * 
	 * @return 所有数据
	 */
	public List<T> loadAll(Class<T> clazz);
	
	/**
	 * 删除实体
	 * 
	 * @param id
	 */

	public void delete(T t) ;
	
	/**
	 * 根据模板去查找
	 * 
	 * @param id
	 */

	public List<T> findByExample(T t);
	
	/**
	 * 根据模板去查找一条
	 * 
	 * @param id
	 */
	
	public T findOneByExample(T t);
	

}
