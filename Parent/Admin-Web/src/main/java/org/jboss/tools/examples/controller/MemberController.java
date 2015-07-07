/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.tools.examples.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.capgemini.petshop.business.entities.Admin;
import com.capgemini.petshop.business.entities.Customers;
import com.capgemini.petshop.business.entities.Orders;
import com.capgemini.petshop.business.logics.AdminLogics;
import com.capgemini.petshop.business.logics.CustomersLogics;
import com.capgemini.petshop.business.logics.OrdersLogics;

// The @Model stereotype is a convenience mechanism to make this a request-scoped bean that has an
// EL name
// Read more about the @Model stereotype in this FAQ:
// http://sfwk.org/Documentation/WhatIsThePurposeOfTheModelAnnotation
@Model
public class MemberController {

	@Inject
	private FacesContext facesContext;

	@Inject
	private AdminLogics adminLogics;
	
	@Inject
	private CustomersLogics custLogics;
	
	@Inject
	private OrdersLogics orderLogic;

	@Produces
	@Named
	private Admin admin;
	
	private static final String LOGOUT = "logout";

	private List<Customers> userList;

	private List<Orders> orderList;
	
	@PostConstruct
	public void initAdmin() {
		admin = new Admin();
		userList = custLogics.findAllOrderedByCustomersName();
		orderList = orderLogic.findAllOrderedByOrdersName();
	}

	public String goToCategory() {
		return "page";
	}

	public String goToProduct() {
		return "page";
	}

	public String goToCategory1() {
		return "category";
	}

	public String goToProduct1() {
		return "product";
	}
	
	public String goToUsers() {
		return "users";
	}

	public String goToOrders() {
		return "orders";
	}

	/********************** VIEW USERS *************************/

	public List<Customers> getUserList() {
		return userList;
	}

	public void setUserList(List<Customers> userList) {
		this.userList = userList;
	}
	
	public void remove(Customers customer) {
		custLogics.removeCustomer(customer);
		userList.remove(customer);
	}

	/*********************************************************/
	/*************VIEW ORDERS*********************************/	
	
	public List<Orders> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Orders> orderList) {
		this.orderList = orderList;
	}
	
	public void removeOrder(Orders order) {
		orderLogic.removeOrder(order);
		orderList.remove(order);
	}

	/*********************************************************/

	public String validateLogin() {
		try {
			boolean check = adminLogics.loginValidation(admin.getUserName(),
					admin.getPassword());
			if (check) {
				HttpSession session = SessionBean.getSession();
				session.setAttribute("username", admin.getUserName());
				return "admin";
			} else {
				return "notadmin";
			}
		} catch (RuntimeException e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Login unsuccessful");
			facesContext.addMessage(null, m);
		}
		return "notadmin";
	}

	public String goLogout() {
		try {
			HttpSession session = SessionBean.getSession();
			session.setAttribute("username", null);
			return LOGOUT;
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Login unsuccessful");
			facesContext.addMessage(null, m);
		}
		return LOGOUT;
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Login failed. See server log for more information";
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
