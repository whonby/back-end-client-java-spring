package com.rg.backendclientapp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name="clients")
public class Clients implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	
	@NotEmpty
	@Size(min=2,max=5)
	private String username;
	
	@Email
	@Column(nullable = false,unique= true)
	private String email;
	
	@NotEmpty
	private String telephone;
	
	@Column(name="create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	
	public Clients() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Clients(String name, String username, String email, String telephone, Date createdAt) {
		super();
		this.name = name;
		this.username = username;
		this.email = email;
		this.telephone = telephone;
		this.createdAt = createdAt;
	}

@PrePersist
public void prePersist() {
	createdAt=new Date();
}
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
}
