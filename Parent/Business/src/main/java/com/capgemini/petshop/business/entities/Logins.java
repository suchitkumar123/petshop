package com.capgemini.petshop.business.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@SuppressWarnings("serial")
@Entity
@Table(name = "LOGINS", uniqueConstraints = @UniqueConstraint(columnNames = "loginId"))
public class Logins implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int loginId;

	private String username;

	private String password;

	@JoinColumn(name = "CUSTOMERSID", referencedColumnName = "CUSTOMERSID")
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Customers customer;
	
	private static final int PRIME = 31;

	public Logins(String username, String password, Customers customer) {
		super();
		this.username = username;
		this.password = password;
		this.customer = customer;
	}

	public Logins() {
		// TODO Auto-generated constructor stub
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
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

	public Customers getCustomer() {
		return customer;
	}

	public void setCustomer(Customers customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		final int prime = PRIME;
		int result = 1;
		result = prime * result
				+ ((customer == null) ? 0 : customer.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Logins other = (Logins) obj;
		if (customer == null && other.customer != null) {
			return false;
		}
		return true;
	}

}
