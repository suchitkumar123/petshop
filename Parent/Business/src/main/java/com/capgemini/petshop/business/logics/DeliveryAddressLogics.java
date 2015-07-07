package com.capgemini.petshop.business.logics;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.capgemini.petshop.business.entities.DeliveryAddress;

@Stateless
public class DeliveryAddressLogics {

	@Inject
    private EntityManager em;
	
	public boolean addDeliveryAddress(DeliveryAddress address){
    	em.merge(address);
    	return true;
    }
	
	public void removeDeliveryAddress(DeliveryAddress address){
    	em.remove(em.merge(address));
    }
	
	public void removeAllDeliveryAddress() {
		List<DeliveryAddress> list = findAllOrderedByDeliveryAddress();
		for( DeliveryAddress d : list) {
			em.remove(d);
		}
	}
	
	public List<DeliveryAddress> findAllOrderedByDeliveryAddress() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DeliveryAddress> criteria = cb.createQuery(DeliveryAddress.class);
		Root<DeliveryAddress> deliveryAddresses = criteria.from(DeliveryAddress.class);
		criteria.select(deliveryAddresses).orderBy(cb.asc(deliveryAddresses.get("lineOne")));
		return em.createQuery(criteria).getResultList();
	}
	
}
