package com.capgemini.petshop.business.logics;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.capgemini.petshop.business.entities.Category;
import com.capgemini.petshop.business.entities.Product;

@Stateless
public class ProductLogics {

	@Inject
	private EntityManager em;

	private List<String> categoryNames;

	public List<String> getCategoryNames() {
		return categoryNames;
	}

	public void setCategoryNames(List<String> categoryNames) {
		this.categoryNames = categoryNames;
	}
	
	@Inject
	private CategoryLogics catLogics;

	public Product findById(Long productId) {
		return em.find(Product.class, productId);
	}

	public Product findByProductName(String productName) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
		Root<Product> product = criteria.from(Product.class);
		criteria.select(product).where(
				cb.equal(product.get("productName"), productName));
		return em.createQuery(criteria).getSingleResult();
	}

	public List<Product> findAllOrderedByProductName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
		Root<Product> product = criteria.from(Product.class);
		criteria.select(product).orderBy(cb.asc(product.get("productName")));
		return em.createQuery(criteria).getResultList();
	}

	public List<String> getAllCategoryName() {
		categoryNames = new ArrayList<String>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Category> criteria = cb.createQuery(Category.class);
		Root<Category> category = criteria.from(Category.class);
		criteria.select(category).orderBy(cb.asc(category.get("categoryName")));
		List<Category> catList = em.createQuery(criteria).getResultList();
		for (Category c : catList) {
			categoryNames.add(c.getCategoryName());
		}
		return categoryNames;
	}
	
	public List<Product> getProductsbyCategoryId(int catId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
		Root<Product> product = criteria.from(Product.class);
		Category cat = catLogics.findById(catId);
		criteria.select(product).where(
				cb.equal(product.get("productCategory"), cat));
		return em.createQuery(criteria).getResultList();
		
	}

	public void addProduct(Product product) {
		//em.persist(product);
		em.merge(product);
	}

	public void removeProduct(Product product) {
		em.remove(em.merge(product));
	}

}
