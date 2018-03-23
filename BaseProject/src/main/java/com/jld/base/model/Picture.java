package com.jld.base.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the picture database table.
 * 
 */
@Entity
@Table(name="picture")
@NamedQuery(name="Picture.findAll", query="SELECT p FROM Picture p")
public class Picture implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer idpicture;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationdate;

	@Column(length=45)
	private String fileextension;

	@Column(nullable=false, length=32)
	private String filekey;

	@Column(nullable=false, length=255)
	private String filename;

	@Column(length=255)
	private String filepath;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_iduser", nullable=false)
	private User user;

	//bi-directional many-to-one association to Userpreference
	@OneToMany(mappedBy="picture")
	private List<Userpreference> userpreferences;

	public Picture() {
	}

	public Integer getIdpicture() {
		return this.idpicture;
	}

	public void setIdpicture(Integer idpicture) {
		this.idpicture = idpicture;
	}

	public Date getCreationdate() {
		return this.creationdate;
	}

	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}

	public String getFileextension() {
		return this.fileextension;
	}

	public void setFileextension(String fileextension) {
		this.fileextension = fileextension;
	}

	public String getFilekey() {
		return this.filekey;
	}

	public void setFilekey(String filekey) {
		this.filekey = filekey;
	}

	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Userpreference> getUserpreferences() {
		return this.userpreferences;
	}

	public void setUserpreferences(List<Userpreference> userpreferences) {
		this.userpreferences = userpreferences;
	}

	public Userpreference addUserpreference(Userpreference userpreference) {
		getUserpreferences().add(userpreference);
		userpreference.setPicture(this);

		return userpreference;
	}

	public Userpreference removeUserpreference(Userpreference userpreference) {
		getUserpreferences().remove(userpreference);
		userpreference.setPicture(null);

		return userpreference;
	}

}