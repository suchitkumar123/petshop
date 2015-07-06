package com.capgemini.petshop.business.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
@Table(name = "CUSTOMERS", uniqueConstraints = @UniqueConstraint(columnNames = "customersId"))
public class Customers implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customersId;

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	private String firstName;

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	private String lastName;

	@NotNull
	private String address1;

	@NotNull
	private String address2;

	@NotNull
	private String address3;

	@NotNull
	private int postcode;

	@NotNull
	private String email;

	@NotNull
	private long phoneNumber;

	@NotNull
	private int registered;

	@OneToOne(mappedBy = "customer", cascade = CascadeType.ALL)
	private Logins login;

	public Logins getLogin() {
		return login;
	}

	public void setLogin(Logins login) {
		this.login = login;
	}

	public int getCustomersId() {
		return customersId;
	}

	public void setCustomersId(int customersId) {
		this.customersId = customersId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getRegistered() {
		return registered;
	}

	public void setRegistered(int registered) {
		this.registered = registered;
	}

	public Customers(String firstName, String lastName, String address1,
			String address2, String address3, int postcode, String email,
			long phoneNumber, int registered, Logins login) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address1 = address1;
		this.address2 = address2;
		this.address3 = address3;
		this.postcode = postcode;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.registered = registered;
		this.login = login;
	}

	public Customers() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((address1 == null) ? 0 : address1.hashCode());
		result = prime * result
				+ ((address2 == null) ? 0 : address2.hashCode());
		result = prime * result
				+ ((address3 == null) ? 0 : address3.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + (int) (phoneNumber ^ (phoneNumber >>> 32));
		result = prime * result + postcode;
		result = prime * result + registered;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customers other = (Customers) obj;
		if (address1 == null) {
			if (other.address1 != null)
				return false;
		} else if (!address1.equals(other.address1))
			return false;
		if (address2 == null) {
			if (other.address2 != null)
				return false;
		} else if (!address2.equals(other.address2))
			return false;
		if (address3 == null) {
			if (other.address3 != null)
				return false;
		} else if (!address3.equals(other.address3))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (phoneNumber != other.phoneNumber)
			return false;
		if (postcode != other.postcode)
			return false;
		if (registered != other.registered)
			return false;
		return true;
	}

}
