package com.jld.base.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name="role")
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, length=20)
	private String idrole;

	@Column(length=255)
	private String description;

	//bi-directional many-to-many association to Usergroup
	@ManyToMany(mappedBy="roles")
	private List<Usergroup> usergroups;

	public Role() {
	}

	public String getIdrole() {
		return this.idrole;
	}

	public void setIdrole(String idrole) {
		this.idrole = idrole;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Usergroup> getUsergroups() {
		return this.usergroups;
	}

	public void setUsergroups(List<Usergroup> usergroups) {
		this.usergroups = usergroups;
	}

}