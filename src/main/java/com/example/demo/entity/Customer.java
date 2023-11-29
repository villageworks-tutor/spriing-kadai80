package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	private String address;
	private String tel;
	private String email;
	private String password;
	
	/**
	 * デフォルトコンストラクタ
	 */
	public Customer() {}

	/**
	 * コンストラクタ
	 * @param email
	 * @param password
	 */
	public Customer(String email, String password) {
		this.email = email;
		this.password = password;
	}

	/**
	 * コンストラクタ
	 * @param name
	 * @param address
	 * @param tel
	 * @param email
	 */
	public Customer(String name, String address, String tel, String email) {
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.email = email;
	}

	/**
	 * コンストラクタ
	 * @param name
	 * @param address
	 * @param tel
	 * @param email
	 * @param password
	 */
	public Customer(String name, String address, String tel, String email, String password) {
		this(name, address, tel, email);
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getTel() {
		return tel;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}
	
}
