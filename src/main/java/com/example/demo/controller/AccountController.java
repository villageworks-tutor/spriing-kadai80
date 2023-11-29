package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Customer;
import com.example.demo.model.Account;
import com.example.demo.repository.CustomerRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AccountController {
	
	@Autowired
	HttpSession session;
	@Autowired
	Account account;
	@Autowired
	CustomerRepository customerRepository;
	
	// ログイン画面表示およびログアウト処理
	@GetMapping({"/", "/login", "/logout"})
	public String index(Model model) {
		// セッションスコープを破棄
		session.invalidate();
		// スコープにヘッダの表示モードを登録
		model.addAttribute("isAccount", true);
		// 画面遷移
		return "login";
	}
	
	// ログイン処理
	@PostMapping("/login")
	public String login(
			@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model) {
		// リクエストパラメータに一致する顧客インスタンスを取得
		Customer customer = customerRepository.findByEmailAndPassword(email, password);
		// 取得した顧客インスタンスによって処理を分岐
		if (customer == null) {
			// メールアドレスとパスワードが一致していない場合
			model.addAttribute("error", "メールアドレスとパスワードが一致しませんでした");
			// スコープにヘッダの表示モードを登録
			model.addAttribute("isAccount", true);
			return "login";
		}
		// メールアドレスとパスワードが一致している場合：セッションスコープに登録されているアカウント情報にIDと名前を設定
		account.setId(customer.getId());
		account.setName(customer.getName());
		// 遷移先URLの設定
		return "redirect:/items";
	}
	
	// ログイン処理（URLを変更して仮保存）
	@PostMapping("/logins")
	public String login(@RequestParam("name") String name) {
		// セッションスコープに登録されているアカウント情報にリクエストパラメータを設定
		account.setName(name);
		// 画面遷移
		return "redirect:/items";
	}
	
	// 会員登録画面表示
	@GetMapping("/account")
	public String create(Model model) {
		// スコープにヘッダの表示モードを登録
		model.addAttribute("isAccount", true);
		// 画面遷移
		return "accountForm";
	}
	
	// 会員登録処理
	@PostMapping("/account")
	public  String store(
			@RequestParam(name = "name", defaultValue = "") String name,
			@RequestParam(name = "address", defaultValue = "") String address,
			@RequestParam(name = "tel", defaultValue = "") String tel,
			@RequestParam(name = "email", defaultValue = "") String email,
			@RequestParam(name = "password", defaultValue = "") String password,
			Model model) {
		// リクエストパラメータの入力チェック
		List<String> errors = new ArrayList<String>();
		// 名前項目のチェック：必須入力チェック
		if (name.isEmpty()) {
			errors.add("名前は必須です");
		}
		// 住所項目のチェック：必須入力チェック
		if (address.isEmpty()) {
			errors.add("住所は必須です");
		}
		// 電話番号項目のチェック：必須入力チェック
		if (tel.isEmpty()) {
			errors.add("電話番号は必須です");
		}
		// email項目のチェック：必須入力チェック・登録済みチェック
		if (email.isEmpty()) {
			// email項目が空文字列である場合：必須入力チェック
			errors.add("emailは必須です");
		} else if (customerRepository.findByEmail(email) != null) {
			// emailに合致する顧客がある場合：登録済チェック
			errors.add("登録済のメールアドレスです");
		}
		// パスワード項目のチェック：必須入力チェック
		if (password.isEmpty()) {
			errors.add("パスワードは必須です");
		}
		
		// エラーチェックの結果によって処理を分岐
		String nextPage = "accountForm";
		if (errors.size() > 0) {
			// スコープにエラーチェックの結果を登録
			model.addAttribute("errors", errors);
			// スコープにヘッダの表示モードを登録
			model.addAttribute("isAccount", true);
			// スコープにリクエストパラメータを登録
			model.addAttribute("name", name);
			model.addAttribute("address", address);
			model.addAttribute("tel", tel);
			model.addAttribute("email", email);
			// 画面遷移
			// return "accountForm";
		} else {
			// データベースに登録
			customerRepository.save(new Customer(name, address, tel, email, password));
			// 遷移先画面の設定
			nextPage = "redirect:/login";
		}
		
		// 画面遷移
		return nextPage;
	}
	
}
