package com.jld.base.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jld.base.model.Userpreference;

@Repository
public class UserpreferenceDao extends AbstractJpaDAO<Userpreference> {

	public UserpreferenceDao() {
		super();
		setClazz(Userpreference.class);
	}

	public Userpreference findOne(Integer userpreferenceId) {
		return entityManager.find(Userpreference.class, userpreferenceId);
	}

	public Userpreference findUserpreference(String username) {

		String sql = "SELECT u FROM Userpreference u WHERE 1=1 and u.user.username='" + username + "'";
		
		Query query = this.entityManager.createQuery(sql, Userpreference.class);
		
		return (Userpreference) query.getSingleResult();		
	}
}
