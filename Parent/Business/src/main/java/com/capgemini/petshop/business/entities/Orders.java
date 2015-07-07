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
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
public class Orders implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ordersId;

	@NotNull
	private String status;

	private String orderDate;

	@OneToMany(mappedBy = "orders", cascade = { CascadeType.ALL })
	private List<CartItems> cartItems = new ArrayList<CartItems>();

	private int customersId;
	
	private static final int PRIME = 31;

	public Orders() {

	}

	public Orders(String status, String orderDate, List<CartItems> cartItems,
			int customersId) {
		super();
		this.status = status;
		this.orderDate = orderDate;
		this.cartItems = cartItems;
		this.customersId = customersId;
	}

	public int getCustomersId() {
		return customersId;
	}

	public void setCustomersId(int customersId) {
		this.customersId = customersId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(int ordersId) {
		this.ordersId = ordersId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CartItems> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItems> cartItems) {
		this.cartItems = cartItems;
	}

	@Override
	public int hashCode() {
		final int prime = PRIME;
		int result = 1;
		result = prime * result
				+ ((cartItems == null) ? 0 : cartItems.hashCode());
		result = prime * result + customersId;
		result = prime * result
				+ ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Orders other = (Orders) obj;
		if (cartItems == null && other.cartItems != null) {
			return false;
		}
		return true;
	}

}
