package com.capgemini.petshop.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.TabChangeEvent;

import com.capgemini.petshop.business.entities.CartItems;
import com.capgemini.petshop.business.entities.Category;
import com.capgemini.petshop.business.entities.Customers;
import com.capgemini.petshop.business.entities.DeliveryAddress;
import com.capgemini.petshop.business.entities.Orders;
import com.capgemini.petshop.business.entities.Product;
import com.capgemini.petshop.business.logics.CartItemsLogics;
import com.capgemini.petshop.business.logics.CategoryLogics;
import com.capgemini.petshop.business.logics.CustomersLogics;
import com.capgemini.petshop.business.logics.DeliveryAddressLogics;
import com.capgemini.petshop.business.logics.OrdersLogics;
import com.capgemini.petshop.business.logics.ProductLogics;

@ManagedBean(name = "dndProductsView")
@SessionScoped
public class MainController {

	@Inject
	@RequestScoped
	private FacesContext facesContext;

	@Inject
	private ProductLogics productLogics;

	private List<Product> productList;

	private List<Product> droppedProducts;

	private List<Product> boughtProduct;

	private Product selectedProduct;

	private List<Category> categorylist;

	@Inject
	private CategoryLogics categoryLogics;

	private String user;

	@PostConstruct
	public void init() {
		user = SessionBean.getUserName();
		categorylist = categoryLogics.findAllOrderedByCategoryName();
		productList = productLogics.findAllOrderedByProductName();
		droppedProducts = new ArrayList<Product>();
	}

	public void onProductDrop(DragDropEvent ddEvent) {
		Product product = (Product) ddEvent.getData();
		droppedProducts.add(product);
		productList.remove(product);
	}

	public List<Product> getProductList() {
		return productList;
	}

	public List<Product> getDroppedProducts() {
		return droppedProducts;
	}

	public Product getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Product selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void removeBack(Product product) {
		droppedProducts.remove(product);
		productList.add(product);
	}

	public void onEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Category Edited",
				((Category) event.getObject()).getCategoryName());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Category Removed");
		FacesContext.getCurrentInstance().addMessage(null, msg);
		Product product = (Product) event.getObject();
		droppedProducts.remove(product);
		productList.add(product);
	}

	/*************** Navigation **************************************/

	public String viewProfile() {
		return "profile";
	}

	public String ordersHistory() {
		return "history";
	}

	public String shoppingCart() {
		return "cart";
	}

	public String goLogin() {
		return "login";
	}

	public String goRegister() {
		return "register";
	}

	public String logout() {
		try {
			HttpSession session = SessionBean.getSession();
			session.setAttribute("username", null);
			return "logout";
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Login unsuccessful");
			facesContext.addMessage(null, m);
		}
		return "logout";
	}

	public String goHome() {
		return "home";
	}

	private String getRootErrorMessage(Exception e) {

		String errorMessage = "Login failed. See server log for more information";
		if (e == null) {

			return errorMessage;
		}

		Throwable t = e;
		while (t != null) {

			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}

		return errorMessage;
	}

	/***************************************************************/

	/****************** Category Tabs Control ***********************/

	private Category category;

	public List<Category> getCategorylist() {
		return categorylist;
	}

	public void setCategorylist(List<Category> categorylist) {
		this.categorylist = categorylist;
	}

	public void onLinkClick(String c) {
		category = categoryLogics.findBycategoryName(c);
		productList = productLogics.getProductsbyCategoryId(category
				.getCategoryId());
	}

	/****************************************************************/

	/****************** Shopping Cart Controller ***********************/

	private List<CartItems> cartItems;

	@Inject
	private CartItemsLogics cartItemsLogic;

	public String addToCart() {
		// cartItemsLogic.removeAllCartItem();
		this.boughtProduct = this.droppedProducts;
		for (Product p : boughtProduct) {
			CartItems c = new CartItems();
			c.setName(p.getProductName());
			c.setCategoryName(p.getCategory().getCategoryName());
			c.setPrice(p.getPrice());
			cartItemsLogic.addCartItem(c);
		}
		cartItems = cartItemsLogic.findAllOrderedByCartItemsName();
		return "cart";
	}

	public List<Product> getBoughtProduct() {
		return boughtProduct;
	}

	public void setBoughtProduct(List<Product> boughtProduct) {
		this.boughtProduct = boughtProduct;
	}

	public List<CartItems> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItems> cartItems) {
		this.cartItems = cartItems;
	}

	private double total;

	public double getTotal() {
		for (Product p : boughtProduct) {
			total = total + p.getPrice();
		}
		return total;
	}

	private double totalPrice = 0.0;

	private int quantity;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		totalPrice = 0.0;
		for (CartItems cIt : cartItems) {
			totalPrice = totalPrice + (cIt.getPrice() * cIt.getQuantity());
		}
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void onQuantityChange(CartItems c) {
		CartItems temp = cartItems.get(c.getCartItemsId());
		cartItems.remove(temp);
		cartItems.add(c);
		for (CartItems cIt : cartItems) {
			totalPrice = totalPrice + (cIt.getPrice() * cIt.getQuantity());
		}
	}
	
	public void onValueChanged(ValueChangeEvent e) {
		CartItems c = (CartItems) e.getSource();
		System.out.println(c.getCategoryName()+"");
	}
	

	public CartItemsLogics getCartItemsLogic() {
		return cartItemsLogic;
	}

	public void setCartItemsLogic(CartItemsLogics cartItemsLogic) {
		this.cartItemsLogic = cartItemsLogic;
	}

	public void remove(CartItems cartItem) {
		cartItems.remove(cartItem);
	}

	/****************************************************************/
	/******************** Place the Order *****************************/

	@Inject
	private DeliveryAddress address;

	@Inject
	CustomersLogics custLogics;

	@Inject
	DeliveryAddressLogics delAddLogics;

	public DeliveryAddress getAddress() {
		return address;
	}

	public void setAddress(DeliveryAddress address) {
		this.address = address;
	}

	public String addDeliveryAddress() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String orderDate = "" + dateFormat.format(date);
		Customers customer = custLogics.findBycustomersName(user);
		this.address.setCustomersId(customer.getCustomersId());
		delAddLogics.addDeliveryAddress(this.address);
		this.order.setCustomersId(customer.getCustomersId());
		this.order.setCartItems(cartItems);
		this.order.setStatus("Order Placed!");
		this.order.setOrderDate(orderDate);
		orderLogics.addOrders(this.order);
		cartItemsLogic.removeAllCartItem();
		backToShoppingMenu();
		orderId = this.order.getOrdersId();
		return "report";

	}
	
	private int orderId;
	
	public int getOrderId() {
		return orderId;
	}
	
	public void backToShoppingMenu() {
		for(Product p : droppedProducts) {
			productList.add(p);
			droppedProducts.remove(p);
		}
		
	}

	/****************************************************************/
	/******************* Report ******************************/

	@Inject
	private Orders order;

	@Inject
	private OrdersLogics orderLogics;

	public Orders getOrder() {
		return order;
	}

	public void setOrder(Orders order) {
		this.order = order;
	}

	/****************************************************************/

}
