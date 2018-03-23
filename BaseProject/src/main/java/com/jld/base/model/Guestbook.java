package com.jld.base.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the guestbook database table.
 * 
 */
@Entity
@Table(name="guestbook")
@NamedQuery(name="Guestbook.findAll", query="SELECT g FROM Guestbook g")
public class Guestbook implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private Integer idguestbook;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Column(length=255)
	private String message;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="user_iduser", nullable=false)
	private User user;

	public Guestbook() {
	}

	public Integer getIdguestbook() {
		return this.idguestbook;
	}

	public void setIdguestbook(Integer idguestbook) {
		this.idguestbook = idguestbook;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}