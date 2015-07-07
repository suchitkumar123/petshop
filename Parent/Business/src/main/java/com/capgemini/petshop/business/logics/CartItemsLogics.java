package com.capgemini.petshop.business.logics;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.capgemini.petshop.business.entities.CartItems;

@Stateless
public class CartItemsLogics {

	@Inject
	private EntityManager em;

	public boolean addCartItem(CartItems cartItem) {
		em.persist(cartItem);
		return true;
	}

	public void removeCartItem(CartItems cartItem) {
		em.remove(em.merge(cartItem));
	}

	public void removeAllCartItem() {
		List<CartItems> clist = findAllOrderedByCartItemsName();
		for (CartItems c : clist) {
			em.remove(c);
		}
	}

	public List<CartItems> findAllOrderedByCartItemsName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CartItems> criteria = cb.createQuery(CartItems.class);
		Root<CartItems> cartItems = criteria.from(CartItems.class);
		criteria.select(cartItems).orderBy(cb.asc(cartItems.get("name")));
		return em.createQuery(criteria).getResultList();
	}

	public CartItems findById(int cartItemsId) {
		return em.find(CartItems.class, cartItemsId);
	}

}
