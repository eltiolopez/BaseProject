package com.jld.base.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jld.base.form.GuestBookSearchForm;
import com.jld.base.model.Guestbook;

@Repository
public class GuestbookDao extends AbstractJpaDAO<Guestbook> {

	public GuestbookDao() {
		super();
		setClazz(Guestbook.class);
	}

	public Guestbook findOne(Integer guestbookId) {
		return entityManager.find(Guestbook.class, guestbookId);
	}
	
	@SuppressWarnings("unchecked")
	public List<Guestbook> findByForm(GuestBookSearchForm form) {
		String sql = "SELECT g FROM Guestbook g WHERE 1=1";
		
		// Where:
		if(form.getUsername() != null && !"".equals(form.getUsername())) {
			sql = sql + " and g.user.username='" + form.getUsername() + "'";
		}
		
		// Order:
		String orderBy = "idguestbook";
		String order = "ASC";
		if(form.getOrderBy() != null && !"".equals(form.getOrderBy())) {
			orderBy = form.getOrderBy();
		}
		if("ASC".equals(form.getOrder()) || "DES".equals(form.getOrder())) {
			order = form.getOrder();
		}
		sql = sql + " order by g." + orderBy + " " + order;
				
		Query query = this.entityManager.createQuery(sql, Guestbook.class);
		
		// Pagination:
		if(form.getFrom() >= 0 && form.getPageSize() >= 0) {
			query.setFirstResult(form.getFrom());
			query.setMaxResults(form.getPageSize());
		}
		
		return (List<Guestbook>) query.getResultList();
	}

	public Integer countByForm(GuestBookSearchForm form) {
		String sql = "SELECT COUNT(g.idguestbook) FROM Guestbook g WHERE 1=1";
		
		// Where:
		if(form.getUsername() != null && !"".equals(form.getUsername())) {
			sql = sql + " and g.user.username='" + form.getUsername() + "'";
		}
				
		Query query = this.entityManager.createQuery(sql, Long.class);
		
		return Integer.valueOf(query.getSingleResult().toString());
	}
}
