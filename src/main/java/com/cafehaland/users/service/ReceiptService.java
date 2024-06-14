package com.cafehaland.users.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.cafehaland.exception.ReceiptNotFoundException;
import com.cafehaland.pojo.Ingredient;
import com.cafehaland.pojo.Receipt;
import com.cafehaland.pojo.ReceiptDetail;
import com.cafehaland.pojo.Supplier;
import com.cafehaland.pojo.User;
import com.cafehaland.users.repository.IngredientRepository;
import com.cafehaland.users.repository.ReceiptDetailRepository;
import com.cafehaland.users.repository.ReceiptRepository;
import com.cafehaland.users.repository.SupplierRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ReceiptService {
	@Autowired
	private ReceiptRepository receiptRepo;
	
	@Autowired
	private ReceiptDetailRepository receiptDetailRepo;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	public List<Receipt> listAll(){
		List<Receipt> listReceipts = receiptRepo.findAll(Sort.by("date").descending());
		return listReceipts;
 	}
	
	public List<Supplier> listSuppler(){
		return supplierRepository.findAll();
	}
	
	public Receipt save(Receipt receipt) {
		return receiptRepo.save(receipt);
	}
	
	public Receipt get(Integer id) throws ReceiptNotFoundException {
		try {
			Receipt receipt = receiptRepo.findById(id).get();
			return receipt;
		} catch (Exception e) {
			throw new ReceiptNotFoundException("Could not find any Receipt with ID " + id);
		}
	}
	
	public void updateStatus(Integer id, String status) throws ReceiptNotFoundException {
		try {
			Receipt receipt = receiptRepo.findById(id).get();
			receiptRepo.updateStatus(id, status);
		} catch (Exception e) {
			throw new ReceiptNotFoundException("Could not find any Receipt with ID " + id);
		}
	}
	
	public void deleteRecipt(Integer id) {
		receiptRepo.deleteById(id);
	}
	
	public void update(Integer id, Date date, User user) {
		receiptRepo.update(id, date, user);
	}
	
	public boolean inReceiptDetail(Receipt receipt, Ingredient ingredient) {
		ReceiptDetail receiptDetail = receiptDetailRepo.findReceiptDetail(receipt, ingredient);
		return receiptDetail != null;
	}
	
	public void deleteReceiptDetail(Receipt receipt, Ingredient ingredient) {
		receiptDetailRepo.delete(receipt, ingredient);
	}
	
	public void updateReceiptDetail(Receipt receipt, Ingredient ingredient, double quantity, double price) {
		receiptDetailRepo.update(receipt, ingredient, quantity, price);
	}
	
	public void saveReceiptDetail(ReceiptDetail receipt) {
		receiptDetailRepo.save(receipt);
	}
}
