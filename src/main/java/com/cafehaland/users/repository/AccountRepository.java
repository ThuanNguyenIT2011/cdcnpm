package com.cafehaland.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cafehaland.pojo.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>{
	@Query("UPDATE Account a SET a.enabled = ?2 WHERE a.email = ?1")
	@Modifying
	public void updateEnabledStatus(String email, boolean status);
}
