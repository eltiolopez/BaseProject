package com.jld.base.dao;

import org.springframework.stereotype.Repository;

import com.jld.base.model.Usergroup;

@Repository
public class UsergroupDao extends AbstractJpaDAO<Usergroup> {

	public UsergroupDao() {
		super();
		setClazz(Usergroup.class);
	}

	public Usergroup findOne(Integer groupId) {
		return entityManager.find(Usergroup.class, groupId);
	}
}
