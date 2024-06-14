package com.cafehaland.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cafehaland.pojo.Order;
import com.cafehaland.pojo.OrderDetail;
import com.cafehaland.pojo.Product;
import com.cafehaland.users.repository.IngredientRepository;
import com.cafehaland.users.repository.OrderDetailRepository;
import com.cafehaland.users.repository.OrderRepoisitory;
import com.cafehaland.users.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class OrderService {
	@Autowired
	private OrderRepoisitory orderRepo;
	
	@Autowired
	private OrderDetailRepository orderDetailRepo;
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private IngredientRepository ingredientRepo;
	
	
	public List<Order> listAll() {
		return orderRepo.findAll();
	}
	
	public Order save(Order order) {
		return orderRepo.save(order);
	}
	
	public List<Product> listProduct(){
		return productRepo.findAll();
	}
	
	public List<OrderDetail> getOrderByOrderId(Integer id){
		return orderDetailRepo.getOrderByOrderId(new Order(id));
	}
	
	public void updateStatusById(Integer id, String status) {
		orderRepo.updateStatus(id, status);
	}
	
	public void updateIngredient(Integer id, double quantity) {
		ingredientRepo.updateStockMinus(id, quantity);
	}

	
}
