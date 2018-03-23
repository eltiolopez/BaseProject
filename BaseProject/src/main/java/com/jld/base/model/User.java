package com.jld.base.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer iduser;

	@Column(nullable=false, length=255)
	private String email;

	@Column(length=255)
	private String name;

	@Column(length=255)
	private String password;

	@Column(length=255)
	private String surname1;

	@Column(length=255)
	private String surname2;

	@Column(nullable=false, length=255)
	private String username;

	//bi-directional many-to-one association to Guestbook
	@OneToMany(mappedBy="user")
	private List<Guestbook> guestbooks;

	//bi-directional many-to-one association to Picture
	@OneToMany(mappedBy="user")
	private List<Picture> pictures;

	//bi-directional many-to-one association to Usergroup
	@ManyToOne
	@JoinColumn(name="group_idgroup", nullable=false)
	private Usergroup usergroup;

	//bi-directional many-to-one association to Userpreference
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	private List<Userpreference> userpreferences;

	public User() {
	}

	public Integer getIduser() {
		return this.iduser;
	}

	public void setIduser(Integer iduser) {
		this.iduser = iduser;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSurname1() {
		return this.surname1;
	}

	public void setSurname1(String surname1) {
		this.surname1 = surname1;
	}

	public String getSurname2() {
		return this.surname2;
	}

	public void setSurname2(String surname2) {
		this.surname2 = surname2;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Guestbook> getGuestbooks() {
		return this.guestbooks;
	}

	public void setGuestbooks(List<Guestbook> guestbooks) {
		this.guestbooks = guestbooks;
	}

	public Guestbook addGuestbook(Guestbook guestbook) {
		getGuestbooks().add(guestbook);
		guestbook.setUser(this);

		return guestbook;
	}

	public Guestbook removeGuestbook(Guestbook guestbook) {
		getGuestbooks().remove(guestbook);
		guestbook.setUser(null);

		return guestbook;
	}

	public List<Picture> getPictures() {
		return this.pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	public Picture addPicture(Picture picture) {
		getPictures().add(picture);
		picture.setUser(this);

		return picture;
	}

	public Picture removePicture(Picture picture) {
		getPictures().remove(picture);
		picture.setUser(null);

		return picture;
	}

	public Usergroup getUsergroup() {
		return this.usergroup;
	}

	public void setUsergroup(Usergroup usergroup) {
		this.usergroup = usergroup;
	}

	public List<Userpreference> getUserpreferences() {
		return this.userpreferences;
	}

	public void setUserpreferences(List<Userpreference> userpreferences) {
		this.userpreferences = userpreferences;
	}

	public Userpreference addUserpreference(Userpreference userpreference) {
		getUserpreferences().add(userpreference);
		userpreference.setUser(this);

		return userpreference;
	}

	public Userpreference removeUserpreference(Userpreference userpreference) {
		getUserpreferences().remove(userpreference);
		userpreference.setUser(null);

		return userpreference;
	}

}