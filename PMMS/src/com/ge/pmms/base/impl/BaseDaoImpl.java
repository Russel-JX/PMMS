/**
 * ============================================================
 * File : BaseDaoImpl.java
 * Description : 
 * 
 * Package : com.ge.pmms.base.impl
 * Author : iGATE
 * Last Edited By :
 * Version : 1.0
 * Created on : Jan 20, 2015
 * History
 * Modified By : Initial Release
 * Classification : GE Confidential
 * Copyright (C) 2015 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.pmms.base.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.ge.pmms.base.BaseDao;


/*******************************************************************************
 * 
 * @Author : iGATE
 * @Version : 1.0
 * @Date Created: Jan 20, 2015
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 * 
 ******************************************************************************/
@Repository("baseDao")
public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

//	private Class<T> clazz;
	protected Log logger;
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		// log日志的支持 
		logger = LogFactory.getLog(this.getClass());
//		// 获取当前new的对象的泛型父类 类型
//		ParameterizedType pt = (ParameterizedType) this.getClass()
//				.getGenericSuperclass();
//		this.clazz = (Class<T>) pt.getActualTypeArguments()[0];
	}

	public void save(T entity) {
		hibernateTemplate.save(entity);
//		this.logger.info("保存"+this.clazz.getSimpleName());
	}

	public void saveAll(List<T> list) {
		hibernateTemplate.saveOrUpdateAll(list);
//		this.logger.info("保存"+this.clazz.getSimpleName());
	}
	
	
	public void delete(Class<T> clazz,Integer id) {
		T entity = hibernateTemplate.load(clazz, id);
		if(entity!=null){
			hibernateTemplate.delete(entity);
		}
//		logger.info("删除"+this.clazz.getSimpleName());
	}
	
	public int deleteByIds(Class<T> clazz,List<Integer> ids){
		String className = clazz.getSimpleName();
		String fClassName = className.substring(0, 1);
		String lClasname = className.substring(1);
		String clazzName = fClassName.toLowerCase()+lClasname;
		//logger.info("className  "+className);
		//logger.info("fClassName  "+fClassName);
		//logger.info("lClasname  "+lClasname);
		//logger.info("clazzName  "+clazzName);
		Query query = hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(//
				"DELETE FROM "+clazzName+" WHERE id IN (:ids)"//
				);
		query.setParameterList("ids", ids);
		return query.executeUpdate();
	}


	public void update(T entity) {
		hibernateTemplate.update(entity);
//		logger.info("更新"+this.clazz.getSimpleName());
	}
	
	public Query updateByHql(String hql) {
		return hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(hql);
//		logger.info("更新"+this.clazz.getSimpleName());
	}

	@SuppressWarnings("unchecked")
	public T getById(Class<T> clazz,Integer id) {
//		logger.info("根据id查询 "+this.clazz.getSimpleName());
		return hibernateTemplate.get(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	public T getByIdNoSession(Class<T> clazz,Integer id) {
//		logger.info("根据id查询 "+this.clazz.getSimpleName());
		return hibernateTemplate.get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> getByIds(Class<T> clazz,Integer[] ids) {
//		logger.info("根据ids查询 "+this.clazz.getSimpleName());
		return hibernateTemplate.getSessionFactory().getCurrentSession().createQuery(//
				"FROM " + clazz.getSimpleName() + " WHERE id IN (:ids)")//
				.setParameterList("ids", ids).list();
	}	

	@SuppressWarnings("unchecked")
	public List<T> getAll(Class<T> clazz) {
//		logger.info("查询所有  "+this.clazz.getSimpleName());
		//return hibernateTemplate.loadAll(clazz);
		return hibernateTemplate.find("from "+clazz.getSimpleName());
	}
	
	@SuppressWarnings("unchecked")
	public List<T> loadAll(Class<T> clazz) {
		return hibernateTemplate.loadAll(clazz);
	}

	public void delete(T t){
		hibernateTemplate.delete(t);
	}

	
	public List<T> findByExample(T t) {
		return hibernateTemplate.findByExample(t);
	}

	public T findOneByExample(T t) {
		List<T> list = findByExample(t);
		if(list==null ||list.size()==0){
			return null;
		}
		return list.get(0);
	}
	


	public List findBySql(String sql, Object... params) {
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		int i = 0;
		for(Object param : params){
			query.setParameter(i, param);
			i++;
		}
		return query.list();
	}

	public List findByHql(String hql) {
		return hibernateTemplate.find(hql);
	}
	
	public List findByHql(String hql, Object... params) {
		return hibernateTemplate.find(hql,params);
	}

	public int executeBySql(String sql){
		return hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
	}

	public int executeBySql(String sql, Object... params) {
		SQLQuery query = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery(sql);
		int i = 0;
		for(Object param : params){
			query.setParameter(i, param);
			i++;
		}
		return query.executeUpdate();
	}

	public T findOneBySql(String sql) {
		List<T> list = findBySql(sql);
		if(list==null || list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public T findOneBySql(String sql, Object... params) {
		List<T> list = findBySql(sql,params);
		if(list==null || list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public T findOneByHql(String hql) {
		List<T> list = findByHql(hql);
		if(list==null || list.size()==0){
			return null;
		}
		return list.get(0);
	}

	public T findOneByHql(String hql, Object... params) {
		List<T> list = findByHql(hql,params);
		if(list==null || list.size()==0){
			return null;
		}
		return list.get(0);
	}
	

	public HibernateTemplate getHiberanteTemplate() {
		return hibernateTemplate;
	}

	public void setHiberanteTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
}
