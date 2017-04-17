package com.vivid.BookStore.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vivid.BookStore.order.dao.OrderDao;
import com.vivid.BookStore.order.domain.Order;
import com.vivid.BookStore.order.domain.OrderItem;
import com.vivid.BookStore.pager.PageConstants;
import com.vivid.BookStore.pager.domain.Page;
import com.vivid.BookStore.user.domain.User;

@Service
public class OrderService {
	int ps = PageConstants.ORDER_PAGE_SIZE;
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
		List<OrderItem> orderItemList = orderDao.loadOrderItem(order.getOid());
		order.setOrderItemList(orderItemList);
		return order;
	}

	/**
	 * 生成订单
	 * 
	 * @param order
	 */
	@Transactional
	public void createOrder(Order order) {
		orderDao.addOrder(order);
		for (OrderItem orderItem : order.getOrderItemList()) {
			orderDao.addOrderItem(orderItem);
		}
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
		Order order = new Order();
		User user = new User();
		user.setUid(uid);
		order.setOwner(user);
		List<Order> list = orderDao.findByCriteria(order, (pc - 1) * ps, ps);
		int tr = orderDao.countByCriteria(order);
		return getPage(list, tr, pc);
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
		Order order = new Order();
		order.setStatus(status);
		List<Order> list = orderDao.findByCriteria(order, (pc - 1) * ps, ps);
		for (Order morder : list) {
			morder.setOrderItemList(orderDao.loadOrderItem(morder.getOid()));
		}
		int tr = orderDao.countByCriteria(order);
		return getPage(list, tr, pc);
	}

	public Page<Order> getPage(List<Order> list, int tr, int pc) {
		Page<Order> pb = new Page<Order>();
		pb.setBeanList(list);
		pb.setPc(pc);
		pb.setPs(ps);
		pb.setTr(tr);

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
		Order order = new Order();
		List<Order> list = orderDao.findByCriteria(order, (pc - 1) * ps, ps);
		int tr = orderDao.countByCriteria(order);
		return getPage(list, tr, pc);
	}
}
