package org.jboss.tools.examples.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.RowEditEvent;

import com.capgemini.petshop.business.entities.Category;
import com.capgemini.petshop.business.entities.Product;
import com.capgemini.petshop.business.logics.CategoryLogics;
import com.capgemini.petshop.business.logics.ProductLogics;

@ManagedBean(name = "productControl")
@RequestScoped
public class ProductController {

	@Inject
	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Inject
	private ProductLogics productLogics;

	@Inject
	private FacesContext facesContext;

	@Inject
	private CategoryLogics catLogics;

	private List<Product> productList;

	private List<String> categoryList;

	private String tempCategory;

	private String currentUser;

	@PostConstruct
	public void initProduct() {
		currentUser = SessionBean.getUserName();
		try {
			productList = productLogics.findAllOrderedByProductName();
			categoryList = productLogics.getAllCategoryName();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Data import unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	public String getCurrentUser() {
		return currentUser;
	}

	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}

	public String getTempCategory() {
		return tempCategory;
	}

	public void setTempCategory(String tempCategory) {
		this.tempCategory = tempCategory;
	}

	public void convertCategoryNameToId() {
		catLogics.findIdBycategoryName(tempCategory);
	}

	public List<String> getCategoryList() {
		return categoryList;
	}

	public List<Product> getProductList() {
		productList = productLogics.findAllOrderedByProductName();
		return productList;
	}

	public String addProduct() {
		this.product.setCategory(catLogics.findBycategoryName(tempCategory));
		productLogics.addProduct(this.product);
		return null;
	}

	public void addNewProduct() {
		this.product.setCategory(catLogics.findBycategoryName(tempCategory));
		productLogics.addProduct(this.product);
	}

	public void onEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Product Edited",
				((Product) event.getObject()).getProductName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Product Removed");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		productLogics.removeProduct((Product) event.getObject());
		productList = productLogics.findAllOrderedByProductName();
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Product importing failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}

}
