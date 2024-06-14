package com.cafehaland.Dao;

public class StatisticProduct {
	private String nameProduct;
	private int quantity;
	private double totalPrice;
	
	public StatisticProduct() {}
	
	
	public StatisticProduct(String nameProduct, int quantity, double totalPrice) {
		super();
		this.nameProduct = nameProduct;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
	}


	public String getNameProduct() {
		return nameProduct;
	}
	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
