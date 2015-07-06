package com.capgemini.petshop.business.logics;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.capgemini.petshop.business.entities.Customers;
import com.capgemini.petshop.business.entities.Logins;

@Stateless
public class LoginsLogics {

	@Inject
	private EntityManager em;

	@Inject
	private Event<Logins> loginsEventSrc;

	public boolean findCustomers(Logins login) throws Exception {
		// log.info("Validating Login " + admin.getUserName());
		@SuppressWarnings("unchecked")
		List<Logins> loginList = em
				.createQuery(
						"from Logins L where L.username = :username and L.password = :password ")
				.setParameter("username", login.getUsername())
				.setParameter("password", login.getPassword()).getResultList();
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
		criteria.select(login).where(cb.equal(login.get("username"), userName),
				cb.equal(login.get("password"), password));
		List<Logins> loginTempList = em.createQuery(criteria).getResultList();
		if (loginTempList.size() > 0) {
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
				cb.equal(logins.get("username"), username));
		return em.createQuery(criteria).getSingleResult();
	}

}
