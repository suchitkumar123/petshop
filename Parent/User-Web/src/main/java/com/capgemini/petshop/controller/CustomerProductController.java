package com.capgemini.petshop.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import com.capgemini.petshop.business.entities.Category;
import com.capgemini.petshop.business.entities.Product;
import com.capgemini.petshop.business.logics.CategoryLogics;
import com.capgemini.petshop.business.logics.ProductLogics;

@RequestScoped
@ManagedBean(name = "customerProduct")
public class CustomerProductController {

	@Inject
	private Product product;

	@Inject
	private FacesContext facesContext;

	private String username;

	private static final String USERNAME = "username";

	@Inject
	private ProductLogics productLogics;

	@Inject
	private CategoryLogics categoryLogics;

	private List<Product> productList;

	private int tempcatId;

	private String t;

	private Category temproryCatId;

	private List<Product> productsPerCategory;

	private Product selectedViewProduct;

	private List<Category> categoryList;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getUsername() {
		return username;
	}

	@PostConstruct
	public void setUsername() {
		HttpSession session = SessionBean.getSession();
		username = (String) session.getAttribute(USERNAME);
	}

	public ProductLogics getProductLogics() {
		return productLogics;
	}

	public void setProductLogics(ProductLogics productLogics) {
		this.productLogics = productLogics;
	}

	public CategoryLogics getCategoryLogics() {
		return categoryLogics;
	}

	public void setCategoryLogics(CategoryLogics categoryLogics) {
		this.categoryLogics = categoryLogics;
	}

	public List<Product> getProductList() {
		productList = productLogics.findAllOrderedByProductName();
		return productList;
	}

	public List<Category> getCategoryList() {
		categoryList = categoryLogics.findAllOrderedByCategoryName();
		return categoryList;
	}

	public Category getTemproryCatId() {
		return temproryCatId;
	}

	public void setTemproryCatId(Category temproryCatId) {
		this.temproryCatId = temproryCatId;
	}

	public String getT() {
		Map<String, String> params = facesContext.getExternalContext()
				.getRequestParameterMap();
		t = params.get("temp");
		return t;
	}

	public void setT(String t) {
		this.t = t;
	}

	public int getTempcatId() {
		Map<String, String> params = facesContext.getExternalContext()
				.getRequestParameterMap();
		t = params.get("temp");
		return tempcatId;
	}

	public void setTempcatId(int tempcatId) {
		this.tempcatId = tempcatId;
	}

	public void util(Category category) {
		tempcatId = category.getCategoryId();
	}

	public List<Product> getProductsPerCategory() {
		productsPerCategory = productLogics.findAllOrderedByProductName();
		return productsPerCategory;
	}

	public void setProductsPerCategory(List<Product> productsPerCategory) {
		this.productsPerCategory = productsPerCategory;
	}

	public void setCategoryList() {
		categoryList = categoryLogics.findAllOrderedByCategoryName();
	}

	public void viewSelected(Product product) {
		this.selectedViewProduct = product;
	}

	public String goToViewProduct() {
		return "view";
	}

	public Product getSelectedViewProduct() {
		return selectedViewProduct;
	}

	public void setSelectedViewProduct(Product selectedViewProduct) {
		this.selectedViewProduct = selectedViewProduct;
	}

	public void onTabChange(TabChangeEvent event) {
		FacesMessage msg = new FacesMessage("Tab Changed", "Active Tab: "
				+ event.getTab().getTitle());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onTabClose(TabCloseEvent event) {
		FacesMessage msg = new FacesMessage("Tab Closed", "Closed tab: "
				+ event.getTab().getTitle());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void remove(Category category) {
		categoryList.remove(category);
	}

	public List<Category> getTabs() {
		return categoryList;
	}

}
