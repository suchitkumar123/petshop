package com.capgemini.petshop.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.component.tabview.Tab;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;
import org.primefaces.model.StreamedContent;

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
	public void setUsername(String username) {
		HttpSession session = SessionBean.getSession();
		username = (String) session.getAttribute("username");
	}

	@Inject
	private ProductLogics productLogics;

	public ProductLogics getProductLogics() {
		return productLogics;
	}

	public void setProductLogics(ProductLogics productLogics) {
		this.productLogics = productLogics;
	}

	@Inject
	private CategoryLogics categoryLogics;

	public CategoryLogics getCategoryLogics() {
		return categoryLogics;
	}

	public void setCategoryLogics(CategoryLogics categoryLogics) {
		this.categoryLogics = categoryLogics;
	}

	private List<Product> productList;

	public List<Product> getProductList() {
		productList = productLogics.findAllOrderedByProductName();
		return productList;
	}

	private List<Category> categoryList;

	public List<Category> getCategoryList() {
		categoryList = categoryLogics.findAllOrderedByCategoryName();
		return categoryList;
	}

	private int tempcatId;

	private String t;

	private Category temproryCatId;

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

	private List<Product> productsPerCategory;

	public void util(Category category) {
		tempcatId = category.getCategoryId();
	}

	public List<Product> getProductsPerCategory() {
		// productsPerCategory =
		// productLogics.getProductsbyCategoryId(tempcatId);
		productsPerCategory = productLogics.findAllOrderedByProductName();
		return productsPerCategory;
	}

	public void setProductsPerCategory(List<Product> productsPerCategory) {
		this.productsPerCategory = productsPerCategory;
	}

	public void setCategoryList(List<Category> categoryList) {
		categoryList = categoryLogics.findAllOrderedByCategoryName();
	}

	public void viewSelected(Product product) {
		this.selectedViewProduct = product;
	}

	public String goToViewProduct() {
		return "view";
	}

	private Product selectedViewProduct;

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
		System.out.println(""+this.temproryCatId.getCategoryName());
		System.out.println(""+this.temproryCatId.getCategoryName());
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
