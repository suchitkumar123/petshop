package com.capgemini.petshop.business.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Table(name = "DELIVERYADDRESSES", uniqueConstraints = @UniqueConstraint(columnNames = "deliveryAddressesId"))
public class DeliveryAddress implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int deliveryAddressesId;

	@NotNull
	private String lineOne;

	@NotNull
	private String lineTwo;

	@NotNull
	private String lineThree;

	@NotNull
	private String email;

	@NotNull
	private long phoneNumber;

	@NotNull
	private long pincode;

	private int customersId;

	public int getCustomersId() {
		return customersId;
	}

	public void setCustomersId(int customersId) {
		this.customersId = customersId;
	}

	public String getLineOne() {
		return lineOne;
	}

	public void setLineOne(String lineOne) {
		this.lineOne = lineOne;
	}

	public String getLineTwo() {
		return lineTwo;
	}

	public void setLineTwo(String lineTwo) {
		this.lineTwo = lineTwo;
	}

	public String getLineThree() {
		return lineThree;
	}

	public void setLineThree(String lineThree) {
		this.lineThree = lineThree;
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

	public long getPincode() {
		return pincode;
	}

	public void setPincode(long pincode) {
		this.pincode = pincode;
	}

	public DeliveryAddress() {
	}

	public DeliveryAddress(String lineOne, String lineTwo, String lineThree,
			String email, long phoneNumber, long pincode, int customersId) {
		super();
		this.lineOne = lineOne;
		this.lineTwo = lineTwo;
		this.lineThree = lineThree;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.pincode = pincode;
		this.customersId = customersId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customersId;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((lineOne == null) ? 0 : lineOne.hashCode());
		result = prime * result
				+ ((lineThree == null) ? 0 : lineThree.hashCode());
		result = prime * result + ((lineTwo == null) ? 0 : lineTwo.hashCode());
		result = prime * result + (int) (phoneNumber ^ (phoneNumber >>> 32));
		result = prime * result + (int) (pincode ^ (pincode >>> 32));
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
		DeliveryAddress other = (DeliveryAddress) obj;
		if (customersId != other.customersId)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (lineOne == null) {
			if (other.lineOne != null)
				return false;
		} else if (!lineOne.equals(other.lineOne))
			return false;
		if (lineThree == null) {
			if (other.lineThree != null)
				return false;
		} else if (!lineThree.equals(other.lineThree))
			return false;
		if (lineTwo == null) {
			if (other.lineTwo != null)
				return false;
		} else if (!lineTwo.equals(other.lineTwo))
			return false;
		if (phoneNumber != other.phoneNumber)
			return false;
		if (pincode != other.pincode)
			return false;
		return true;
	}

}
