package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.model.Account;

@Aspect
@Component
public class CheckLoginAspect {
	@Autowired
	Account account;
	
	// ログ出力処理：すべてのControllerのすべてのメソッド処理前に実行指定
	@Before("execution(* com.example.demo.controller.*Controller.*(..))")
	public void writeLog(JoinPoint jp) {
		// ログインしたアカウント情報を取得
		if (account == null || account.getName() == null || account.getName().isEmpty()) {
			System.out.print("ゲスト：");
		} else {
			System.out.print(account.getName() + "：");
		}
		System.out.println(jp.getSignature());
	}
	
	// 未ログインの場合：ログイン画面にリダイレクト
	@Around("execution(* com.example.demo.controller.ItemController.*(..)) ||"
		  + "execution(* com.example.demo.controller.CartController.*(..)) ||"
		  + "execution(* com.example.demo.controller.OrderController.*(..))")
	public Object checkLogin(ProceedingJoinPoint jp) throws Throwable {
		if (account == null  || account.getName() == null  || account.getName().isEmpty()) {
			System.out.println("ログインしていません！");
			// リダイレクト先を指定するパラメータを渡すことでログインControllerで個別のメッセージをThymeleafに渡すことも可能
			return "redirect:/login?error=notLoggedIn";
		}
		// Controller内のメソッドの実行
		return jp.proceed();
	}
	
}
