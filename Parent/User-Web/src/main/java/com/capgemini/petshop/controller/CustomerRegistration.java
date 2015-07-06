package com.capgemini.petshop.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.capgemini.petshop.business.entities.Customers;
import com.capgemini.petshop.business.entities.Logins;
import com.capgemini.petshop.business.logics.CustomersLogics;
import com.capgemini.petshop.business.logics.LoginsLogics;

@Model
@SessionScoped
@ManagedBean(name = "registration")
public class CustomerRegistration {

	@Inject
	@RequestScoped
	private FacesContext facesContext;

	@Produces
	@Named
	private Customers customer;

	@Produces
	@Named
	private Logins login;

	@Inject
	private CustomersLogics custLogics;

	@Inject
	private LoginsLogics loginLogics;

	@PostConstruct
	public void initCustomer() {
		customer = new Customers();
		login = new Logins();
	}

	public Customers getCustomer() {
		return customer;
	}

	public void setCustomer(Customers customer) {
		this.customer = customer;
	}

	public Logins getLogin() {
		return login;
	}

	public void setLogin(Logins login) {
		this.login = login;
	}

	public String customerRegister() {
		boolean check = false;
		check = custLogics.addCustomer(customer);
		if (check) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap()
					.put("customerId", customer.getCustomersId());
			return "success";
		}
		return null;
	}

	public String addLogins() {
		login.setCustomer(customer);
		loginLogics.addLogins(login);
		return "done";
	}

}
