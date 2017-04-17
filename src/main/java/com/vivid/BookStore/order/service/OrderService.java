package com.vivid.BookStore.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivid.BookStore.order.dao.OrderDao;
import com.vivid.BookStore.order.domain.Order;
import com.vivid.BookStore.pager.domain.Page;

@Service
public class OrderService {
	@Autowired
	OrderDao orderDao;

	/**
	 * 修改订单状态
	 * 
	 * @param oid
	 * @param status
	 */
	public void updateStatus(String oid, int status) {
		orderDao.updateStatus(oid, status);
	}

	/**
	 * 查询订单状态
	 * 
	 * @param oid
	 * @return
	 */
	public int findStatus(String oid) {
		return orderDao.findStatus(oid);
	}

	/**
	 * 加载订单
	 * 
	 * @param oid
	 * @return
	 */
	@Transactional
	public Order load(String oid) {
		Order order = orderDao.load(oid);
		return order;
	}

	/**
	 * 生成订单
	 * 
	 * @param order
	 */
	@Transactional
	public void createOrder(Order order) {
		// JdbcUtils.beginTransaction();
		orderDao.add(order);
		// JdbcUtils.commitTransaction();
	}

	/**
	 * 我的订单
	 * 
	 * @param uid
	 * @param pc
	 * @return
	 */
	@Transactional
	public Page<Order> myOrders(String uid, int pc) {
		// JdbcUtils.beginTransaction();
		Page<Order> pb = orderDao.findByUser(uid, pc);
		// JdbcUtils.commitTransaction();
		return pb;
	}

	/**
	 * 按状态查询
	 * 
	 * @param status
	 * @param pc
	 * @return
	 */
	@Transactional
	public Page<Order> findByStatus(int status, int pc) {
		// JdbcUtils.beginTransaction();
		Page<Order> pb = orderDao.findByStatus(status, pc);
		// JdbcUtils.commitTransaction();
		return pb;
	}

	/**
	 * 查询所有
	 * 
	 * @param pc
	 * @return
	 */
	@Transactional
	public Page<Order> findAll(int pc) {
		// JdbcUtils.beginTransaction();
		Page<Order> pb = orderDao.findAll(pc);
		// JdbcUtils.commitTransaction();
		return pb;
	}
}
