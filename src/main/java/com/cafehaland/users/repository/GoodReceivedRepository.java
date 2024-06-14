package com.cafehaland.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cafehaland.pojo.GoodReceived;

@Repository
public interface GoodReceivedRepository extends JpaRepository<GoodReceived, Integer>{

}
