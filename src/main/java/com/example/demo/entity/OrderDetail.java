package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_details")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "order_id")
	private Integer orderId;
	@Column(name = "item_id")
	private Integer itenId;
	private Integer quantity;
	
	/**
	 * デフォルトコンストラクタ
	 */
	public OrderDetail() {}

	/**
	 * コンストラクタ
	 * @param orderId
	 * @param itenId
	 * @param quantity
	 */
	public OrderDetail(Integer orderId, Integer itenId, Integer quantity) {
		this.orderId = orderId;
		this.itenId = itenId;
		this.quantity = quantity;
	}

	public Integer getId() {
		return id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public Integer getItenId() {
		return itenId;
	}

	public Integer getQuantity() {
		return quantity;
	}
	
}
