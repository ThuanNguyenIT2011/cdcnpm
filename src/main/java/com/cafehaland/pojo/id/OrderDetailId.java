package com.cafehaland.pojo.id;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrderDetailId implements Serializable{
	private Integer productSizeId;
	private Integer orderId;
	
	public OrderDetailId() {}
	
	public OrderDetailId(Integer productSizeId, Integer orderId) {
		this.productSizeId = productSizeId;
		this.orderId = orderId;
	}
	public Integer getProductSizeId() {
		return productSizeId;
	}
	public void setProductSizeId(Integer productSizeId) {
		this.productSizeId = productSizeId;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	
}
