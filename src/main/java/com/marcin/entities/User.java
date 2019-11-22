package com.marcin.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.marcin.enums.UserType;

import javax.persistence.Enumerated;
import javax.persistence.EnumType;


@Entity
public class User {
	 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name="email")
	private String email;

	@Column(name="username")
	private String username;

	@Column(name="password")
	private String password;

	@Column(name="type")
	@Enumerated(EnumType.STRING)
	private UserType type;

	@Column(name="blocked")
	private String blocked;
	public User() {
		super();
	}

	public User(String email, String username, String password, UserType type,String blocked) {
		super();
		this.email = email;
		this.username = username;
		this.password = password;
		this.type = type;
		this.blocked = blocked;
	}

	public Long getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public UserType getType() {
		return type;
	}
	public void setType(UserType type) {
		this.type = type;
	}
	
	
	public String getBlocked() {
		return blocked;
	}

	public void setBlocked(String blocked) {
		this.blocked = blocked;
	}

	@Override
	public String toString() {
		return this.id+" "+this.username;
	}
}
