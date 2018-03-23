package com.jld.base.dao;

import org.springframework.stereotype.Repository;

import com.jld.base.model.Role;

@Repository
public class RoleDao extends AbstractJpaDAO<Role> {

	public RoleDao() {
		super();
		setClazz(Role.class);
	}
}
