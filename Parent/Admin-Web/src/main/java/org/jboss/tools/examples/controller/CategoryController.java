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
import com.capgemini.petshop.business.logics.CategoryLogics;

@ManagedBean(name = "catControl")
@RequestScoped
public class CategoryController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private CategoryLogics catLogics;

	private List<Category> catList;

	private String catName;

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	@PostConstruct
	public void initCategory() {
		try {
			catList = catLogics.findAllOrderedByCategoryName();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Data import unsuccessful");
			facesContext.addMessage(null, m);
		}
	}

	public List<Category> getCatList() {
		return catList;
	}

	public void setCatList(List<Category> catList) {
		this.catList = catList;
	}

	public String addCategory() {
		Category newCategory = new Category(this.catName);
		catLogics.addCategory(newCategory);
		catList = catLogics.findAllOrderedByCategoryName();
		return null;
	}

	public void onEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Category Edited",
				((Category) event.getObject()).getCategoryName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Category Removed");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		catLogics.removeCategory((Category) event.getObject());
		catList = catLogics.findAllOrderedByCategoryName();
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Category importing failed. See server log for more information";
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
