package com.example.demo.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDetail;
import com.example.demo.model.Cart;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderDetailrepository;
import com.example.demo.repository.OrderRepository;

@Controller
public class OrderController {
	
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	Cart cart;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderDetailrepository orderDetailRepository;
	
	// 顧客情報入力画面表示
	@GetMapping("/order")
	public String index() {
		return "customerForm";
	}
	
	// 注文内容確認画面表示
	@PostMapping("/order/confirm")
	public String confirm(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			Model model) {
		// リクエストパラメータをもとにして顧客のインスタンスを生成
		Customer customer = new Customer(name, address, tel, email);
		// スコープに登録
		model.addAttribute("customer", customer);
		// 画面遷移
		return "orderConfirm";
	}
	
	// 注文確定処理
	@PostMapping("/order")
	public String doOrder(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			Model model) {
		// リクエストパラメータをもとにして顧客のインスタンスを生成
		Customer customer = new Customer(name, address, tel, email);
		// 生成した顧客インスタンスをデータベースに登録
		customerRepository.save(customer);
		
		// セッションスコープに登録されているカートを取得して注文のインスタンスを生成
		Date today = new Date(System.currentTimeMillis()); // 現在日を取得
		Order order = new Order(customer.getId(), today, cart.getTotalPrice());
		// 生成した注文インスタンスをデータベースに登録
		orderRepository.save(order);
		
		// カート内商品をデータベースに登録
		List<OrderDetail> orderDetailList = new ArrayList<>();
		for (Item item : cart.getItemList()) {
			orderDetailList.add(new OrderDetail(order.getId(), item.getId(), item.getQuantity()));
		}
		// データベースに一括登録
		orderDetailRepository.saveAll(orderDetailList);
		
		// セッションスコープに登録差rているカート情報を初期化
		cart.clear();
		
		// スコープに注文番号を登録
		model.addAttribute("orderNumber", order.getId());
		// 画面遷移
		return "ordered";
	}
	
}
