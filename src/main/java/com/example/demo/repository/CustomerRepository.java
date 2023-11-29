package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	/**
	 * 電子メールアドレスが一致する顧客を取得する
	 * SELECT * FROM customers WHERE email = ?
	 */
	Customer findByEmail(String email);

	Customer findByEmailAndPassword(String email, String password);

}
