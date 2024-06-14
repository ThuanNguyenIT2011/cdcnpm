package com.cafehaland.users.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cafehaland.pojo.Receipt;
import com.cafehaland.pojo.User;

@Repository
public interface ReceiptRepository  extends JpaRepository<Receipt, Integer>{
	@Query("UPDATE Receipt r SET r.date=?2, r.user=?3 WHERE r.id=?1")
	@Modifying
	public void update(Integer id, Date date, User user);
	
	@Query("UPDATE Receipt r SET r.status=?2 WHERE r.id=?1")
	@Modifying
	public void updateStatus(Integer id, String status);
}
