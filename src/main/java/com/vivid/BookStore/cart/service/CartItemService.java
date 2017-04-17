package com.vivid.BookStore.cart.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivid.BookStore.cart.dao.CartItemDao;
import com.vivid.BookStore.cart.domain.CartItem;

@Service
public class CartItemService {

	@Autowired
	private CartItemDao cartItemDao;

	/*
	 * 加载多个CartItem
	 */
	public List<CartItem> loadCartItems(String cartItemIds) {
		return cartItemDao.loadCartItems(cartItemIds.split(","));
	}

	/**
	 * 修改购物车条目数量
	 * 
	 * @param cartItemId
	 * @param quantity
	 * @return
	 */
	public CartItem updateQuantity(String cartItemId, int quantity) {
		cartItemDao.updateQuantity(cartItemId, quantity);
		return cartItemDao.findByCartItemId(cartItemId);
	}

	/**
	 * 批量删除功能
	 * 
	 * @param cartItemIds
	 */
	public void batchDelete(String cartItemIds) {
		cartItemDao.delete(cartItemIds.split(","));

	}

	/**
	 * 添加条目
	 * 
	 * @param cartItem
	 */
	public void add(CartItem cartItem) {
		try {
			/*
			 * 1. 使用uid和bid去数据库中查询这个条目是否存在
			 */
			CartItem _cartItem = cartItemDao.findByUidAndBid(cartItem.getUser().getUid(), cartItem.getBook().getBid());
			if (_cartItem == null) {// 如果原来没有这个条目，那么添加条目
				cartItem.setCartItemId(UUID.randomUUID().toString().replace("-", ""));
				System.out.println(cartItem);
				cartItemDao.addCartItem(cartItem);
			} else {// 如果原来有这个条目，修改数量
				// 使用原有数量和新条目数量之各，来做为新的数量
				int quantity = cartItem.getQuantity() + _cartItem.getQuantity();
				// 修改这个老条目的数量
				cartItemDao.updateQuantity(_cartItem.getCartItemId(), quantity);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 我的购物车功能
	 * 
	 * @param uid
	 * @return
	 */
	public List<CartItem> myCart(String uid) {
		return cartItemDao.findByUser(uid);
	}
}
