package com.jld.base.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the userpreferences database table.
 * 
 */
@Entity
@Table(name="userpreferences")
@NamedQuery(name="Userpreference.findAll", query="SELECT u FROM Userpreference u")
public class Userpreference implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer iduserpreferences;

	@Column(nullable=false)
	private int numresultsinpage;

	@Column(nullable=false, length=255)
	private String repositorypath;

	//bi-directional many-to-one association to Picture
	@ManyToOne
	@JoinColumn(name="picture_idpicture", nullable=true)
	private Picture picture;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_iduser", nullable=false)
	private User user;

	public Userpreference() {
	}

	public Integer getIduserpreferences() {
		return this.iduserpreferences;
	}

	public void setIduserpreferences(Integer iduserpreferences) {
		this.iduserpreferences = iduserpreferences;
	}

	public int getNumresultsinpage() {
		return this.numresultsinpage;
	}

	public void setNumresultsinpage(int numresultsinpage) {
		this.numresultsinpage = numresultsinpage;
	}

	public String getRepositorypath() {
		return this.repositorypath;
	}

	public void setRepositorypath(String repositorypath) {
		this.repositorypath = repositorypath;
	}

	public Picture getPicture() {
		return this.picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}