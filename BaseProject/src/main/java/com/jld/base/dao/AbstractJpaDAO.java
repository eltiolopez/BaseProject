package com.jld.base.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.jld.base.core.utils.Constants;
import com.jld.base.exception.EntityNotFoundException;
import com.jld.base.exception.ExceptionErrors;
import com.jld.base.model.vo.ModelAttribute;
import com.jld.base.model.vo.ModelFilter;

public abstract class AbstractJpaDAO<T extends Serializable> {

    private Class<T> clazz;

    @PersistenceContext
    protected EntityManager entityManager;

    public final void setClazz(final Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public T findOne(final Integer id) {
        return entityManager.find(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
    	Query q = entityManager.createQuery(" from " + clazz.getName());
        return q.getResultList();
    }

    public void create(final T entity) {
        entityManager.persist(entity);
    }

    public T update(final T entity) {
        return entityManager.merge(entity);
    }

    public void delete(final T entity) {
        entityManager.remove(entity);
    }

    public void deleteById(final Integer entityId) throws Exception {
        final T entity = findOne(entityId);
        if(entity == null) {
        	throw new EntityNotFoundException(ExceptionErrors.ERROR_1001.getCode(), ExceptionErrors.ERROR_1001.getMessage());
        }
        delete(entity);
    }

    @SuppressWarnings("unchecked")
	public List<T> findList(ModelFilter modelFilter) {
    	String sql = "SELECT x FROM " + clazz.getName() + " x WHERE 1=1";
    	
    	List<ModelAttribute> listOfAttributes = modelFilter.getAttributes();
		for(ModelAttribute attribute: listOfAttributes) {
			String andStatement = "";
			switch (attribute.getOperation()) {
			case Constants.MODEL_OPERATION_EQUAL:
				andStatement = andStatement + " and x." + attribute.getField() + Constants.MODEL_OPERATION_EQUAL + "'" + attribute.getValue() + "'";
				break;
			case Constants.MODEL_OPERATION_LIKE:
				andStatement = andStatement + " and x." + attribute.getField() + " " + Constants.MODEL_OPERATION_LIKE + " '" + attribute.getValue() + "'";
				break;
			case Constants.MODEL_OPERATION_GREATER:
				andStatement = andStatement + " and x." + attribute.getField() + Constants.MODEL_OPERATION_GREATER + "'" + attribute.getValue() + "'";
				break;
			case Constants.MODEL_OPERATION_LITTLE:
				andStatement = andStatement + " and x." + attribute.getField() + Constants.MODEL_OPERATION_LITTLE + "'" + attribute.getValue() + "'";
				break;
			default:
				break;
			}
			sql = sql + andStatement;
		}
		
		if(StringUtils.isNotEmpty(modelFilter.getOrderByField())) {
			String orderByStatment = " ORDER BY x." + modelFilter.getOrderByField() + " " + modelFilter.getOrderByValue();
			sql = sql + orderByStatment;
		}
		
		Query query = this.entityManager.createQuery(sql, clazz);
		
		//If Pagination:
		if(modelFilter.getFirstResult() >= 0 && modelFilter.getMaxResults() >= 0) {
			query.setFirstResult(modelFilter.getFirstResult());
			query.setMaxResults(modelFilter.getMaxResults());
		}
		
		return query.getResultList();
    }
    
    public Integer findCount(ModelFilter modelFilter) {
    	String sql = "SELECT COUNT(x) FROM " + clazz.getName() + " x WHERE 1=1";
    	
    	List<ModelAttribute> listOfAttributes = modelFilter.getAttributes();
		for(ModelAttribute attribute: listOfAttributes) {
			String andStatement = "";
			switch (attribute.getOperation()) {
			case Constants.MODEL_OPERATION_EQUAL:
				andStatement = andStatement + " and x." + attribute.getField() + Constants.MODEL_OPERATION_EQUAL + "'" + attribute.getValue() + "'";
				break;
			case Constants.MODEL_OPERATION_LIKE:
				andStatement = andStatement + " and x." + attribute.getField() + " " + Constants.MODEL_OPERATION_LIKE + " '" + attribute.getValue() + "'";
				break;
			case Constants.MODEL_OPERATION_GREATER:
				andStatement = andStatement + " and x." + attribute.getField() + Constants.MODEL_OPERATION_GREATER + "'" + attribute.getValue() + "'";
				break;
			case Constants.MODEL_OPERATION_LITTLE:
				andStatement = andStatement + " and x." + attribute.getField() + Constants.MODEL_OPERATION_LITTLE + "'" + attribute.getValue() + "'";
				break;
			default:
				break;
			}
			sql = sql + andStatement;
		}
		
		Query query = this.entityManager.createQuery(sql, Long.class);
		
		return Integer.valueOf(query.getSingleResult().toString());
    }
}