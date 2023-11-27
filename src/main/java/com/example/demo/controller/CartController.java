package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Item;
import com.example.demo.model.Cart;
import com.example.demo.repository.ItemRepository;

@Controller
public class CartController {
	
	@Autowired
	private Cart cart;
	@Autowired
	ItemRepository itemRepository;
	
	// カート画面表示
	@GetMapping("/cart")
	public String  index() {
		return "cart";
	}
	
	// カートへ商品を追加
	@PostMapping("/cart/add")
	public String addCart(
			@RequestParam("itemId") Integer itemId,
			@RequestParam(name = "quantity", defaultValue = "1") Integer quantity,
			Model model) {
		// リクエストパラメータをもとにして商品を取得
		Item item = itemRepository.findById(itemId).get();
		// 数量を設定
		item.setQuantity(quantity);
		// セッションに登録されているカートに追加
		cart.add(item);
		// 画面遷移
		return "cart";
	}
	
	// カートから商品を削除
	@PostMapping("/cart/delete")
	public String deleteCart(
			@RequestParam("itemId") Integer itemId) {
		// リクエストパラメータをもとに商品を削除
		cart.delete(itemId);
		// 画面遷移
		return "cart";
	}
	
}
