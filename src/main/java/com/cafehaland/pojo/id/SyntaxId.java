package com.cafehaland.pojo.id;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class SyntaxId implements Serializable {
	private Integer productSizeId;
	private Integer ingredientId;
	
	public SyntaxId() {}

	public SyntaxId(Integer productSizeId, Integer ingredientId) {
		this.productSizeId = productSizeId;
		this.ingredientId = ingredientId;
	}

	public Integer getProductSizeId() {
		return productSizeId;
	}

	public void setProductSizeId(Integer productSizeId) {
		this.productSizeId = productSizeId;
	}

	public Integer getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Integer ingredientId) {
		this.ingredientId = ingredientId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ingredientId, productSizeId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SyntaxId other = (SyntaxId) obj;
		return Objects.equals(ingredientId, other.ingredientId) && Objects.equals(productSizeId, other.productSizeId);
	}
}
