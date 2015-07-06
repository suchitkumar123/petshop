package com.capgemini.petshop.business.logics;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.capgemini.petshop.business.entities.CartItems;
import com.capgemini.petshop.business.entities.Customers;
import com.capgemini.petshop.business.entities.Orders;

@Stateless
public class OrdersLogics {
	
	@Inject
    private EntityManager em;
	
	public boolean addOrders(Orders order){
    	em.merge(order);
    	return true;
    }
	
	public void removeOrder(Orders order){
    	em.remove(em.merge(order));
    }
	
	public void savetheDate(Orders order, Date date) {
		
	}
	
	public List<Orders> findAllOrderedByOrdersName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Orders> criteria = cb.createQuery(Orders.class);
		Root<Orders> orders = criteria.from(Orders.class);
		criteria.select(orders).orderBy(cb.asc(orders.get("orderDate")));
		return em.createQuery(criteria).getResultList();
	}

	
}
