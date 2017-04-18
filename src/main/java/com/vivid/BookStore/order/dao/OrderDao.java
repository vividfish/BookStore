package com.vivid.BookStore.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.vivid.BookStore.order.domain.Order;
import com.vivid.BookStore.order.domain.OrderItem;

@Mapper
@Repository
public interface OrderDao {

	public int findStatus(String oid);

	public void updateStatus(@Param("oid") String oid, @Param("status") int status);

	public Order load(String oid);

	public void addOrder(Order order);

	public int addOrderItem(OrderItem orderItem);

	List<OrderItem> loadOrderItem(String oid);

	public List<Order> findByCriteria(@Param("order") Order order, @Param("pos") int pos, @Param("ps") int ps);

	public int countByCriteria(Order order);

	public List<Order> findAll(@Param("pos")int i,@Param("ps") int ps);

	public int countAll();

}
