package com.jld.base.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jld.base.form.UserSearchForm;
import com.jld.base.model.User;

@Repository
public class UserDao extends AbstractJpaDAO<User> {
	
	@PersistenceContext
    private EntityManager entityManager;

	public UserDao() {
		super();
		setClazz(User.class);
	}
	
	/*public User findOne(final Integer id) {
        return entityManager.find(User.class, id);
    }*/
	
	public void updateOne(User user) {
		entityManager.persist(user);
	}
	
	@SuppressWarnings("unchecked")
	public List<User> searchUsersByFieldsPaginated(User user, int firstResult, int maxResults) {
		String sql = "SELECT u FROM User u WHERE 1=1";

		if(user.getUsername() != null && !"".equals(user.getUsername())) {
			sql = sql + " and UPPER(u.username) like '" + user.getUsername().trim().toUpperCase() + "%'";
		}
		
		if(user.getEmail() != null && !"".equals(user.getEmail())) {
			sql = sql + " and UPPER(u.email) like '" + user.getEmail().trim().toUpperCase() + "%'";
		}
		
		if(user.getUsergroup() != null && user.getUsergroup().getIdusergroup().intValue() > 0) {
			sql = sql + " and u.usergroup.idusergroup=" + user.getUsergroup().getIdusergroup().intValue();
		}
		

		Query query = this.entityManager.createQuery(sql, User.class);
		
		//If Pagination:
		if(firstResult != 0 && maxResults != 0) {
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
		}
		
        return (List<User>) query.getResultList();
	}

	public void create(User newUser) {
		this.entityManager.persist(newUser);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		Query q = entityManager.createQuery(" from " + User.class);
        return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<User> findByForm(UserSearchForm form) {
		String sql = "SELECT u FROM User u WHERE 1=1";
		
		// Where:
		if(form.getUsername() != null && !"".equals(form.getUsername())) {
			sql = sql + " and UPPER(u.username) like '" + form.getUsername().trim().toUpperCase() + "%'";
		}
		if(form.getEmail() != null && !"".equals(form.getEmail())) {
			sql = sql + " and UPPER(u.email) like '" + form.getEmail().trim().toUpperCase() + "%'";
		}
		if(form.getGroup() != null) {
			sql = sql + " and u.usergroup.idusergroup='" + form.getGroup().intValue() + "'";
		}
		
		// Order:
		String orderBy = "username";
		String order = "ASC";
		if(form.getOrderBy() != null && !"".equals(form.getOrderBy())) {
			orderBy = form.getOrderBy();
		}
		if("ASC".equals(form.getOrder()) || "DES".equals(form.getOrder())) {
			order = form.getOrder();
		}
		sql = sql + " order by u." + orderBy + " " + order;
				
		Query query = this.entityManager.createQuery(sql, User.class);
		
		// Pagination:
		if(form.getFrom() >= 0 && form.getPageSize() >= 0) {
			query.setFirstResult(form.getFrom());
			query.setMaxResults(form.getPageSize());
		}
		
		return (List<User>) query.getResultList();
	}

	public Integer countByForm(UserSearchForm form) {
		String sql = "SELECT COUNT(u.iduser) FROM User u WHERE 1=1";
		
		// Where:
		if(form.getUsername() != null && !"".equals(form.getUsername())) {
			sql = sql + " and UPPER(u.username) like '" + form.getUsername().trim().toUpperCase() + "%'";
		}
		if(form.getEmail() != null && !"".equals(form.getEmail())) {
			sql = sql + " and UPPER(u.email) like '" + form.getEmail().trim().toUpperCase() + "%'";
		}
		if(form.getGroup() != null) {
			sql = sql + " and u.usergroup.idusergroup='" + form.getGroup().intValue() + "'";
		}
				
		Query query = this.entityManager.createQuery(sql, Long.class);
		
		return Integer.valueOf(query.getSingleResult().toString());
	}

	public User findUserByField(String fieldName, String fieldValue) {
		String sql = "SELECT u from User u WHERE u." + fieldName + "='" + fieldValue + "'";
		
		Query query = this.entityManager.createQuery(sql, User.class);
		
		return (User) query.getSingleResult();
	}

	public Long countUserByField(String fieldName, String fieldValue) {
		String sql = "SELECT COUNT(u.iduser) FROM User u WHERE u." + fieldName + "='" + fieldValue + "'";
		
		Query query = this.entityManager.createQuery(sql, Long.class);
		
		return (Long) query.getSingleResult();
	}
}
