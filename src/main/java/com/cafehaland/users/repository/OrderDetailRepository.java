package com.cafehaland.users.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cafehaland.pojo.Order;
import com.cafehaland.pojo.OrderDetail;
import com.cafehaland.pojo.id.OrderDetailId;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId>{
	@Query("SELECT o FROM OrderDetail o WHERE o.order=?1")
	public List<OrderDetail> getOrderByOrderId(Order order);
}
