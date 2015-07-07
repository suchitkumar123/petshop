package com.capgemini.petshop.business.logics;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.capgemini.petshop.business.entities.Category;

@Stateless
public class CategoryLogics {

	@Inject
	private EntityManager em;
	
	private static final String CATEGORYNAME = "categoryName";

	public Category findById(int categoryId) {
		return em.find(Category.class, categoryId);
	}

	public Category findBycategoryName(String categoryName) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Category> criteria = cb.createQuery(Category.class);
		Root<Category> category = criteria.from(Category.class);
		criteria.select(category).where(
				cb.equal(category.get(CATEGORYNAME), categoryName));
		return em.createQuery(criteria).getSingleResult();
	}

	public int findIdBycategoryName(String categoryName) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Category> criteria = cb.createQuery(Category.class);
		Root<Category> category = criteria.from(Category.class);
		criteria.select(category).where(
				cb.equal(category.get(CATEGORYNAME), categoryName));
		Category c = em.createQuery(criteria).getSingleResult();
		if (c != null) {
			return c.getCategoryId();
		}
		return 0;
	}

	public List<Category> findAllOrderedByCategoryName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Category> criteria = cb.createQuery(Category.class);
		Root<Category> category = criteria.from(Category.class);
		criteria.select(category).orderBy(cb.asc(category.get(CATEGORYNAME)));
		return em.createQuery(criteria).getResultList();
	}

	public void addCategory(Category category) {
		em.persist(category);
	}

	public void removeCategory(Category category) {
		em.remove(em.merge(category));
	}

}
