package com.capgemini.petshop.business.logics;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.capgemini.petshop.business.entities.Admin;
import com.capgemini.petshop.business.entities.Category;
import com.capgemini.petshop.business.entities.Customers;
import com.capgemini.petshop.business.entities.Logins;

@Stateless
public class CustomersLogics {
	
	@Inject
    private EntityManager em;

    @Inject
    private Event<Customers> customersEventSrc;
    
    @Inject
    LoginsLogics loginsLogics;
    
    public boolean addCustomer(Customers customer){
    	em.persist(customer);
    	return true;
    }
    
    public void removeCustomer(Customers customer){
    	em.remove(em.merge(customer));
    }
    
    public Customers findById(int customersId) {
		return em.find(Customers.class, customersId);
	}
    
    public Customers findBycustomersName(String username) {
    	Logins login = loginsLogics.findByloginsName(username);
    	int id = login.getCustomer().getCustomersId();
		return findById(id);
	}
    
    public List<Customers> findAllOrderedByCustomersName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Customers> criteria = cb.createQuery(Customers.class);
		Root<Customers> customers = criteria.from(Customers.class);
		criteria.select(customers).orderBy(cb.asc(customers.get("firstName")));
		return em.createQuery(criteria).getResultList();
	}

}
