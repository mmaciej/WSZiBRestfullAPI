/**
 * @(#)User.java
 *
 *
 * @author Maciej Maciaszek
 * @version 1.00 2018/07/21
 * @description: DB User Entity description class
 */

package com.mm.wszib.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name="users")
public class User {
	
	@JsonIgnore
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
	@Column(name = "id_user", columnDefinition = "UNSIGNED INT(11)")
	private int id;
	
	@JsonIgnore
	@Column(name = "active")
	private int active;
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="role")
	private Role role;
	
	@Column(name = "username", unique = true, nullable = false)
	@Length(min = 3, message = "*Your username must have at least 3 characters")
	@NotEmpty(message = "*Please provide an username")
	private String userName;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name = "password", nullable = false)
	@Length(min = 8, message = "*Your password must have at least 8 characters")
	@NotEmpty(message = "*Please provide an password")
	@Transient
	private String password;
	
	@Column(name = "email", unique = true, nullable = false)
	@Email(message = "*Please provide a valid Email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	
	@Column(name = "phone_number", unique = true, nullable = false)
	@NotEmpty(message = "*Please provide an phone number")
	private String phoneNumber;
	
	public User() {};	
	
	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
