package com.vivid.BookStore.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vivid.BookStore.order.domain.Order;
import com.vivid.BookStore.order.service.OrderService;
import com.vivid.BookStore.pager.domain.Page;

@Controller
public class AdminOrderController {
	@Autowired
	OrderService orderService;

	/**
	 * 查看所有订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("admin/AdminFindAllOrder")
	public String findAll(Integer pc, HttpServletRequest req) {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		if (pc == null) {
			pc = 1;
		}
		/*
		 * 2. 得到url：...
		 */
		String url = "AdminFindAllOrder";

		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		Page<Order> pb = orderService.findAll(pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "/adminjsps/admin/order/list.jsp";
	}

	/**
	 * 按状态查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("admin/AdminFindByStatus")
	public String findByStatus(Integer pc, HttpServletRequest req) {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		if (pc == null) {
			pc = 1;
		}
		/*
		 * 2. 得到url：...
		 */
		String url = "/BookStore/AdminFindByStatus?";
		/*
		 * 3. 获取链接参数：status
		 */
		String status = req.getParameter("status");
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		Page<Order> pb = orderService.findByStatus(status, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "/adminjsps/admin/order/list.jsp";
	}

	/**
	 * 查看订单详细信息
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("admin/AdminLoadOrder")
	public String load(HttpServletRequest req) {
		String oid = req.getParameter("oid");
		Order order = orderService.load(oid);
		req.setAttribute("order", order);
		String btn = req.getParameter("btn");// btn说明了用户点击哪个超链接来访问本方法的
		req.setAttribute("btn", btn);
		return "/adminjsps/admin/order/desc.jsp";
	}

	/**
	 * 取消订单
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("admin/AdminCancel")
	public String cancel(HttpServletRequest req) {
		String oid = req.getParameter("oid");
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(oid);
		if (status != 1) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能取消！");
			return "/adminjsps/msg.jsp";
		}
		orderService.updateStatus(oid, 5);// 设置状态为取消！
		req.setAttribute("code", "success");
		req.setAttribute("msg", "此订单已取消");
		return "/adminjsps/msg.jsp";
	}

	/**
	 * 发货功能
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("admin/AdminDeliver")
	public String deliver(HttpServletRequest req) {
		String oid = req.getParameter("oid");
		/*
		 * 校验订单状态
		 */
		int status = orderService.findStatus(oid);
		if (status != 2) {
			req.setAttribute("code", "error");
			req.setAttribute("msg", "状态不对，不能发货！");
			return "/adminjsps/msg.jsp";
		}
		orderService.updateStatus(oid, 3);// 设置状态为取消！
		req.setAttribute("code", "success");
		req.setAttribute("msg", "订单已发货");
		return "/adminjsps/msg.jsp";
	}
}
