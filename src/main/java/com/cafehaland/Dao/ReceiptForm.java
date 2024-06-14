package com.cafehaland.Dao;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.cafehaland.pojo.Receipt;
import com.cafehaland.pojo.ReceiptDetail;
import com.cafehaland.pojo.Supplier;

public class ReceiptForm {
	private Integer id;
	private Map<Integer, Double> quantity;
	private Map<Integer, Double> price;
	private Supplier supplier;
	
	private String status;
	
	public ReceiptForm() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Map<Integer, Double> getQuantity() {
		return quantity;
	}
	public void setQuantity(Map<Integer, Double> quantity) {
		this.quantity = quantity;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Map<Integer, Double> getPrice() {
		return price;
	}

	public void setPrice(Map<Integer, Double> price) {
		this.price = price;
	}

	public static ReceiptForm changeReceiptToReceiptForm(Receipt receipt) {
		ReceiptForm formReceiptForm = new ReceiptForm();
		formReceiptForm.setId(receipt.getId());
		formReceiptForm.setSupplier(receipt.getSupplier());
		formReceiptForm.setStatus(receipt.getStatus());
		formReceiptForm.setQuantity(new TreeMap<>());
		formReceiptForm.setPrice(new HashMap<>());
		for (ReceiptDetail receiptDetail : receipt.getReceiptDetails()) {
			formReceiptForm.getQuantity().put(receiptDetail.getIngredient().getId(), receiptDetail.getQuantity());
			formReceiptForm.getPrice().put(receiptDetail.getIngredient().getId(), receiptDetail.getPrice());
		}
		
		return formReceiptForm;
	}
	
	
	public boolean isCanncel() {
		return this.status.equalsIgnoreCase("canncel");
	}
	
	public boolean isReceived() {
		return this.status.equalsIgnoreCase("received");
	}
	
	public boolean isOrdered() {
		return this.status.equalsIgnoreCase("ordered");
	}
	
	public boolean isOReceived() {
		return this.status.equalsIgnoreCase("Received");
	}
	
	
}
