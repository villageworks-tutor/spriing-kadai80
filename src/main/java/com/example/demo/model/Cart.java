package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.entity.Item;

@Component
@SessionScope
public class Cart {

	/**
	 * フィールド
	 */
	private List<Item> itemList = new ArrayList<>();

	/**
	 * 商品リストフィールドを取得する
	 * @return List<Item> 商品リスト
	 */
	public List<Item> getItemList() {
		return this.itemList;
	}
	
	/**
	 * カート内商品の金額の総合計を種痘する
	 * @return カート内商品の金額の総合計 
	 */
	public Integer getTotalPrice() {
		Integer total = 0;
		for (Item item : this.itemList) {
			total += item.getPrice() * item.getQuantity();
		}
		return total;
	}

	/**
	 * カートに商品を追加する
	 * @param target 追加対象の商品
	 */
	public void add(Item target) {
		// 追加する商品がカート内にあるかどうかをチェック
		Item exists = null;
		for (Item item : this.itemList) {
			if (item.getId() == target.getId()) {
				// カート内に同じ商品が見つかった場合：
				exists = item;
				break;
			}
			continue;
		}
		// カート内にが見つかったかどうかで処理を分岐
		if (exists == null) {
			// カート内にが見つからなかった場合：単純に商品リストに追加
			this.itemList.add(target);
		} else {
			// カートが見つかった場合：当該商品の数量を変更
			int quantity = exists.quantity + target.quantity;
			exists.setQuantity(quantity);
		}
	}

	/**
	 * カート内の商品を削除する
	 * @param itemId 削除対象商品の商品番号
	 */
	public void delete(Integer itemId) {
		for (Item item : this.itemList) {
			if (item.getId() == itemId) {
				this.itemList.remove(item);
				break; // TODO: ここの行の意味を考えてみよう
			}
		}
	}

	/**
	 * カートの商品リストを初期化する
	 */
	public void clear() {
		this .itemList = new ArrayList<>();
	}

}
