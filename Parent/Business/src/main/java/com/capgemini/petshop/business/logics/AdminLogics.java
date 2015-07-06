package com.capgemini.petshop.business.logics;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.capgemini.petshop.business.entities.Admin;

@Stateless
public class AdminLogics {

//	@Inject
//    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Admin> adminEventSrc;

    public boolean findAdmin(Admin admin) throws Exception {
//        log.info("Validating Login " + admin.getUserName());
        @SuppressWarnings("unchecked")
        List<Admin> adminList = em.createQuery("from Admin A where A.userName = :userName and A.password = :password ").setParameter("userName",admin.getUserName()).setParameter("password",admin.getPassword()).getResultList();
        if ( adminList != null) {
           return true;
        }
        adminEventSrc.fire(admin);
        return false;
    }
    
    public boolean loginValidation(String userName, String password){
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Admin> criteria = cb.createQuery(Admin.class);
        Root<Admin> admin = criteria.from(Admin.class);
        criteria.select(admin).where(cb.equal(admin.get("userName"), userName), cb.equal(admin.get("password"), password));
        List<Admin> adminTempList = em.createQuery(criteria).getResultList();
        if(adminTempList.size() > 0){
        	return true;
        }
        return false;
    }
	
}
