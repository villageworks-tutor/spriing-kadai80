package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "items")
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "category_id")
	private Integer categoryId;
	
	private String name;
	private Integer price;
	
	@Transient // 永続化対象外：関連したテーブルに定義されていないフィールドに宣言
	public int quantity;
	
	/**
	 * デフォルトコンストラクタ
	 */
	public Item() {}
	
	/**
	 * コンストラクタ
	 * @param categoryId
	 * @param name
	 * @param price
	 */
	public Item(Integer categoryId, String name, Integer price) {
		this.categoryId = categoryId;
		this.name = name;
		this.price = price;
		this.quantity = 1;
	}

	/**
	 * コンストラクタ
	 * @param id
	 * @param categoryId
	 * @param name
	 * @param price
	 */
	public Item(Integer id, Integer categoryId, String name, Integer price) {
		this(categoryId, name, price);
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Item [");
		builder.append("id=" + id + ", ");
		builder.append("categoryId=" + categoryId + ", ");
		builder.append("name=" + name + ", ");
		builder.append("price=" + price + ", ");
		builder.append("quantity=" + quantity + "]");
		return builder.toString();
	}
	
	
	
}
