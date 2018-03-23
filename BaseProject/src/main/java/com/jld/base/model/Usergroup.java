package com.jld.base.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usergroup database table.
 * 
 */
@Entity
@Table(name="usergroup")
@NamedQuery(name="Usergroup.findAll", query="SELECT u FROM Usergroup u")
public class Usergroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer idusergroup;

	@Column(length=255)
	private String description;

	@Column(length=255)
	private String groupname;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="usergroup")
	private List<User> users;

	//bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(
		name="usergroup_has_role"
		, joinColumns={
			@JoinColumn(name="usergroup_idusergroup", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="role_idrole", nullable=false)
			}
		)
	private List<Role> roles;

	public Usergroup() {
	}

	public Integer getIdusergroup() {
		return this.idusergroup;
	}

	public void setIdusergroup(Integer idusergroup) {
		this.idusergroup = idusergroup;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setUsergroup(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setUsergroup(null);

		return user;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

}