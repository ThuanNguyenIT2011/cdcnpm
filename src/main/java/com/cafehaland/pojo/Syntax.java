package com.cafehaland.pojo;

import com.cafehaland.pojo.id.SyntaxId;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "Syntaxs")
public class Syntax {
	@EmbeddedId
	private SyntaxId id;
	
	@ManyToOne
	@JsonBackReference
	@MapsId("ProductSizeId")
	@JoinColumn(name = "ProductSizeId", referencedColumnName = "id")
	private ProductSize productSize;
	
	@ManyToOne(fetch = FetchType.EAGER)
//	@JsonBackReference
	@MapsId("IngredientId")
	@JoinColumn(name = "IngredientId", referencedColumnName = "id")
	private Ingredient ingredient;
	
	@Column
	private double quantity;

	public Syntax() {}

	public Syntax(ProductSize productSize, Ingredient ingredient, double quantity) {
		this.productSize = productSize;
		this.ingredient = ingredient;
		this.quantity = quantity;
	}



	public Syntax(SyntaxId id, ProductSize productSize, Ingredient ingredient, double quantity) {
		this.id = id;
		this.productSize = productSize;
		this.ingredient = ingredient;
		this.quantity = quantity;
	}



	public SyntaxId getId() {
		return id;
	}

	public void setId(SyntaxId id) {
		this.id = id;
	}

	public ProductSize getProductSize() {
		return productSize;
	}

	public void setProductSize(ProductSize productSize) {
		this.productSize = productSize;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
}
