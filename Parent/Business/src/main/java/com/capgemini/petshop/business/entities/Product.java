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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "PRODUCT", uniqueConstraints = @UniqueConstraint(columnNames = "productId"))
public class Product implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	private String productName;

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
	private String productDesc;

	private byte[] image;

	private double price;

	@JoinColumn(name = "CATEGORYID", referencedColumnName = "CATEGORYID")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Category productCategory;

	private static final int PRIME = 31;

	public Product(String productName, String productDesc, double price,
			byte[] image, Category productCategory) {
		super();
		this.productName = productName;
		this.productDesc = productDesc;
		this.image = image;
		this.price = price;
		this.productCategory = productCategory;
	}

	public Product() {

	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public Category getProductCategory() {
		return productCategory;
	}

	public void setProductCategory() {
		if (this.productCategory != null) {
			this.productCategory.getProducts().add(this);
		}
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getProductId() {
		return productId;
	}

	public Category getCategory() {
		return productCategory;
	}

	public void setCategory(Category productCategory) {
		this.productCategory = productCategory;
	}

	@Override
	public int hashCode() {
		final int prime = PRIME;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> PRIME + 1));
		result = prime * result
				+ ((productCategory == null) ? 0 : productCategory.hashCode());
		result = prime * result
				+ ((productDesc == null) ? 0 : productDesc.hashCode());
		result = prime * result
				+ ((productName == null) ? 0 : productName.hashCode());
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
		Product other = (Product) obj;
		if (!productName.equals(other.productName)) {
			return false;
		}
		return true;
	}

}
