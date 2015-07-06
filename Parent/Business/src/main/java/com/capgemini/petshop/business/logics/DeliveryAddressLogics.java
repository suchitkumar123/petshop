package com.capgemini.petshop.business.logics;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

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
	
}
