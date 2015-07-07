package com.capgemini.petshop.business.logics;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.capgemini.petshop.business.entities.Logins;

@Stateless
public class LoginsLogics {

	@Inject
	private EntityManager em;

	@Inject
	private Event<Logins> loginsEventSrc;
	
	private static final String USERNAME = "username";
	
	private static final String PASSWORD = "password";

	public boolean findCustomers(Logins login) {
		@SuppressWarnings("unchecked")
		List<Logins> loginList = em
				.createQuery(
						"from Logins L where L.username = :username and L.password = :password ")
				.setParameter(USERNAME, login.getUsername())
				.setParameter(PASSWORD, login.getPassword()).getResultList();
		if (loginList != null) {
			return true;
		}
		loginsEventSrc.fire(login);
		return false;
	}

	public boolean loginValidation(String userName, String password) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Logins> criteria = cb.createQuery(Logins.class);
		Root<Logins> login = criteria.from(Logins.class);
		criteria.select(login).where(cb.equal(login.get(USERNAME), userName),
				cb.equal(login.get(PASSWORD), password));
		List<Logins> loginTempList = em.createQuery(criteria).getResultList();
		if (!loginTempList.isEmpty()) {
			return true;
		}
		return false;
	}

	public boolean addLogins(Logins login) {
		em.merge(login);
		return true;
	}

	public Logins findByloginsName(String username) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Logins> criteria = cb.createQuery(Logins.class);
		Root<Logins> logins = criteria.from(Logins.class);
		criteria.select(logins).where(
				cb.equal(logins.get(USERNAME), username));
		return em.createQuery(criteria).getSingleResult();
	}

}
