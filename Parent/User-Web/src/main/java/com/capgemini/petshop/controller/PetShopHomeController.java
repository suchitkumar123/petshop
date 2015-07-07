package com.capgemini.petshop.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import com.capgemini.petshop.business.entities.Category;
import com.capgemini.petshop.business.entities.Product;
import com.capgemini.petshop.business.logics.CategoryLogics;
import com.capgemini.petshop.business.logics.ProductLogics;

@RequestScoped
@ManagedBean(name = "homeControl")
public class PetShopHomeController {

	private List<Category> categorylist;

	@Inject
	private CategoryLogics categoryLogics;

	@Inject
	private ProductLogics productLogics;

	private Category category;

	private List<Product> productList;

	private Product selectedProduct;

	@PostConstruct
	public void init() {
		categorylist = categoryLogics.findAllOrderedByCategoryName();
		productList = productLogics.findAllOrderedByProductName();
	}

	public List<Category> getCategorylist() {
		return categorylist;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void onLinkClick(String c) {
		category = categoryLogics.findBycategoryName(c);
		productList = productLogics.getProductsbyCategoryId(category
				.getCategoryId());
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public String goToRegister() {
		return "register";
	}

	public String goToLogin() {
		return "login";
	}

	public String goToHome() {
		return "petShopHome";
	}

}
