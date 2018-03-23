package com.jld.base.dao;

import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.jld.base.core.utils.Constants;
import com.jld.base.model.Picture;
import com.jld.base.model.vo.ModelAttribute;
import com.jld.base.model.vo.ModelFilter;

@Repository
public class PictureDao extends AbstractJpaDAO<Picture> {

	public PictureDao() {
		super();
		setClazz(Picture.class);
	}

	public Picture findOne(Integer pictureId) {
		return entityManager.find(Picture.class, pictureId);
	}

	@SuppressWarnings("unchecked")
	public List<Picture> findPicture(ModelFilter modelFilter) {

		String sql = "SELECT p FROM Picture p WHERE 1=1";
		
		List<ModelAttribute> listOfAttributes = modelFilter.getAttributes();
		for(ModelAttribute attribute: listOfAttributes) {
			String andStatement = "";
			switch (attribute.getOperation()) {
			case Constants.MODEL_OPERATION_EQUAL:
				andStatement = andStatement + " and p." + attribute.getField() + Constants.MODEL_OPERATION_EQUAL + "'" + attribute.getValue() + "'";
				break;
			case Constants.MODEL_OPERATION_LIKE:
				andStatement = andStatement + " and p." + attribute.getField() + " " + Constants.MODEL_OPERATION_LIKE + " '" + attribute.getValue() + "'";
				break;
			case Constants.MODEL_OPERATION_GREATER:
				andStatement = andStatement + " and p." + attribute.getField() + Constants.MODEL_OPERATION_GREATER + "'" + attribute.getValue() + "'";
				break;
			case Constants.MODEL_OPERATION_LITTLE:
				andStatement = andStatement + " and p." + attribute.getField() + Constants.MODEL_OPERATION_LITTLE + "'" + attribute.getValue() + "'";
				break;
			default:
				break;
			}
			sql = sql + andStatement;
		}
		
		if(StringUtils.isNotEmpty(modelFilter.getOrderByField())) {
			String orderByStatment = " ORDER BY p." + modelFilter.getOrderByField() + " " + modelFilter.getOrderByValue();
			sql = sql + orderByStatment;
		}
		
		Query query = this.entityManager.createQuery(sql, Picture.class);
		
		return (List<Picture>) query.getResultList();		
	}
	
	public Integer countPicture(ModelFilter modelFilter) {

		String sql = "SELECT COUNT(p.idpicture) FROM Picture p WHERE 1=1";
		
		List<ModelAttribute> listOfAttributes = modelFilter.getAttributes();
		for(ModelAttribute attribute: listOfAttributes) {
			String andStatement = "";
			switch (attribute.getOperation()) {
			case Constants.MODEL_OPERATION_EQUAL:
				andStatement = andStatement + " and p." + attribute.getField() + Constants.MODEL_OPERATION_EQUAL + "'" + attribute.getValue() + "'";
				break;
			case Constants.MODEL_OPERATION_LIKE:
				andStatement = andStatement + " and p." + attribute.getField() + " " + Constants.MODEL_OPERATION_LIKE + " '" + attribute.getValue() + "'";
				break;
			case Constants.MODEL_OPERATION_GREATER:
				andStatement = andStatement + " and p." + attribute.getField() + Constants.MODEL_OPERATION_GREATER + "'" + attribute.getValue() + "'";
				break;
			case Constants.MODEL_OPERATION_LITTLE:
				andStatement = andStatement + " and p." + attribute.getField() + Constants.MODEL_OPERATION_LITTLE + "'" + attribute.getValue() + "'";
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
