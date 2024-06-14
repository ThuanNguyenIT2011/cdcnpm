package com.cafehaland.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cafehaland.pojo.Order;

@Repository
public interface OrderRepoisitory extends JpaRepository<Order, Integer>{
	@Query("UPDATE Order o SET o.status=?2 WHERE o.id=?1")
	@Modifying
	public void updateStatus(Integer id, String status);
}
