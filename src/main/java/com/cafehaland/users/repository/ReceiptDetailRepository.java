package com.cafehaland.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cafehaland.pojo.Ingredient;
import com.cafehaland.pojo.Receipt;
import com.cafehaland.pojo.ReceiptDetail;

@Repository
public interface ReceiptDetailRepository extends JpaRepository<ReceiptDetail, Integer>{
	@Query("DELETE ReceiptDetail r WHERE r.receipt=?1 AND r.ingredient=?2")
	@Modifying
	public void delete(Receipt receipt, Ingredient ingredient);
	
	@Query("SELECT r FROM ReceiptDetail r WHERE r.receipt=?1 AND r.ingredient=?2")
	public ReceiptDetail findReceiptDetail(Receipt receipt, Ingredient ingredient);
	
	@Query("UPDATE ReceiptDetail r SET r.quantity=?3, r.price=?4 WHERE r.receipt=?1 AND r.ingredient=?2")
	@Modifying
	public void update(Receipt receipt, Ingredient ingredient, double quantity, double price);
	
}
