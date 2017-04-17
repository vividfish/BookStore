package com.vivid.BookStore.cart.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.vivid.BookStore.cart.domain.CartItem;

@Mapper
@Repository
public interface CartItemDao {

	/**
	 * 加载多个CartItem
	 * 
	 * @param cartItemIds
	 * @return
	 * @throws SQLException
	 */
	public List<CartItem> loadCartItems(String[] cartItemIds);

	/**
	 * 按id查询
	 * 
	 * @param cartItemId
	 * @return
	 * @throws SQLException
	 */
	public CartItem findByCartItemId(String cartItemId);

	/**
	 * 批量删除
	 * 
	 * @param cartItemIds
	 * @throws SQLException
	 */
	public boolean delete(String[] cartItemId);

	/**
	 * 查询某个用户的某本图书的购物车条目是否存在
	 * 
	 * @throws SQLException
	 */
	public CartItem findByUidAndBid(@Param("uid") String uid, @Param("bid") String bid);

	/**
	 * 修改指定条目的数量
	 * 
	 * @param cartItemId
	 * @param quantity
	 * @throws SQLException
	 */
	public boolean updateQuantity(@Param("cartItemId") String cartItemId, @Param("quantity") int quantity);

	/**
	 * 添加条目
	 * 
	 * @param cartItem
	 * @throws SQLException
	 */
	public boolean addCartItem(CartItem cartItem);

	/*
	 * 把一个Map映射成一个Cartitem
	 */

	/**
	 * 通过用户查询购物车条目
	 * 
	 * @param uid
	 * @return
	 * @throws SQLException
	 */
	public List<CartItem> findByUser(String uid);

}
