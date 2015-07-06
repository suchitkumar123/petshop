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
package com.capgemini.petshop.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import com.capgemini.petshop.business.entities.Admin;
import com.capgemini.petshop.business.entities.Customers;
import com.capgemini.petshop.business.entities.Logins;
import com.capgemini.petshop.business.logics.AdminLogics;
import com.capgemini.petshop.business.logics.LoginsLogics;

@Model
@ManagedBean
public class CustomerController {

	@Inject
	@RequestScoped
	private FacesContext facesContext;

	@Produces
	@Named
	private Customers customers;

	@Produces
	@Named
	private Logins logins;

	@Inject
	private LoginsLogics loginsLogics;

	@PostConstruct
	public void initAdmin() {
		logins = new Logins();
	}

	public Customers getCustomers() {
		return customers;
	}

	public void setCustomers(Customers customers) {
		this.customers = customers;
	}

	public Logins getLogins() {
		return logins;
	}

	public void setLogins(Logins logins) {
		this.logins = logins;
	}

	public String goToRegistration() {
		return "page";
	}

	public String validateLogin() throws Exception {
		try {
			boolean check = loginsLogics.loginValidation(logins.getUsername(),
					logins.getPassword());
			if (check) {
				System.out.println("LOGIN SUCCESSFUL!!");
				HttpSession session = SessionBean.getSession();
				session.setAttribute("username", logins.getUsername());
				return "user";
			} else {
				System.out.println("LOGIN UNSUCCESSFUL!!");
				return "notuser";
			}
			// memberRegistration.register(newMember);
			// FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO,
			// "Registered!", "Registration successful");
			// facesContext.addMessage(null, m);
			// initNewMember();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Login unsuccessful");
			facesContext.addMessage(null, m);
		}
		return "notuser";
	}

	public String goLogout() {
		try {
			HttpSession session = SessionBean.getSession();
			session.setAttribute("username", null);
			return "logout";
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Login unsuccessful");
			facesContext.addMessage(null, m);
		}
		return "logout";
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

	@SuppressWarnings("unused")
	private String logoutAdmin() {
		return "logout";
	}

}
