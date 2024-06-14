package com.cafehaland.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cafehaland.pojo.GoodReceived;
import com.cafehaland.pojo.Receipt;
import com.cafehaland.users.repository.GoodReceivedRepository;
import com.cafehaland.users.repository.IngredientRepository;
import com.cafehaland.users.repository.ReceiptRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GoodReceivedService {
	@Autowired
	private GoodReceivedRepository goodReceivedRepo;
	
	@Autowired
	private ReceiptRepository receiptRepo;
	
	@Autowired
	private IngredientRepository ingredientRepo;
	
	public List<GoodReceived> listAll(){
		List<GoodReceived> listGoodReceiveds = goodReceivedRepo.findAll(Sort.by("date").descending());
		return listGoodReceiveds;
	}
	
	public void save(GoodReceived goodReceived) {
		goodReceivedRepo.save(goodReceived);
	}
	
	public void updateStatus(Integer id, String status) {
		receiptRepo.updateStatus(id, status);
	}
	
	public Receipt getReceipt(Integer id) {
		return receiptRepo.findById(id).get();
	}
	
	public void updateStock(Integer id, double quantity) {
		ingredientRepo.updateStock(id, quantity);
	}
}
