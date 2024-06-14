package com.cafehaland.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafehaland.pojo.OrderDetail;
import com.cafehaland.pojo.id.OrderDetailId;
import com.cafehaland.users.repository.OrderDetailRepository;

@Service
public class OrderDetailService {
	@Autowired
	private OrderDetailRepository orderDetailRepo;
	
	public void save(List<OrderDetail> listOrderDetails) {
		listOrderDetails.forEach(ele -> orderDetailRepo.save(ele));
	}
	
	public List<OrderDetail> listAll(){
		return orderDetailRepo.findAll();
	}
	
	public OrderDetail find(){
		return orderDetailRepo.findById(new OrderDetailId(29, 28)).get();
	}
}
