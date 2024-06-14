package com.cafehaland.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ReceiptIngredient")
public class ReceiptDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private double quantity;
	
	@Column
	private double price;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ReceiptId", nullable = false)
	private Receipt receipt;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "IngredientId", nullable = false)
	private Ingredient ingredient;

	public ReceiptDetail() {}
	
	public ReceiptDetail(double quantity, Ingredient ingredient) {
		this.quantity = quantity;
		this.ingredient = ingredient;
	}
	
	public ReceiptDetail(double quantity, double price, Receipt receipt, Ingredient ingredient) {
		this.quantity = quantity;
		this.price = price;
		this.receipt = receipt;
		this.ingredient = ingredient;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Receipt getReceipt() {
		return receipt;
	}

	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "ReceiptDetail [id=" + id + ", quantity=" + quantity + ", price=" + price + ", receipt=" + receipt
				+ ", ingredient=" + ingredient + "]";
	}
}
