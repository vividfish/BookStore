package com.vivid.BookStore.cart.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vivid.BookStore.book.domain.Book;
import com.vivid.BookStore.book.service.BookService;
import com.vivid.BookStore.cart.domain.CartItem;
import com.vivid.BookStore.cart.service.CartItemService;
import com.vivid.BookStore.user.domain.User;

@Controller
public class CartItemController {
	@Autowired
	private CartItemService cartItemService;
	@Autowired
	BookService bookService;

	/**
	 * 加载多个CartItem
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("loadCartItems")
	public String loadCartItems(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1. 获取cartItemIds参数
		 */
		String cartItemIds = req.getParameter("cartItemIds");
		double total = Double.parseDouble(req.getParameter("total"));
		/*
		 * 2. 通过service得到List<CartItem>
		 */
		List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
		/*
		 * 3. 保存，然后转发到/cart/showitem.jsp
		 */
		req.setAttribute("cartItemList", cartItemList);
		req.setAttribute("total", total);
		req.setAttribute("cartItemIds", cartItemIds);
		return "jsps/cart/showitem.jsp";
	}

	@RequestMapping("updateQuantity")
	@ResponseBody
	public String updateQuantity(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cartItemId = req.getParameter("cartItemId");
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		CartItem cartItem = cartItemService.updateQuantity(cartItemId, quantity);

		// 给客户端返回一个json对象
		StringBuilder sb = new StringBuilder("{");
		sb.append("\"quantity\"").append(":").append(cartItem.getQuantity());
		sb.append(",");
		sb.append("\"subtotal\"").append(":").append(cartItem.getSubtotal());
		sb.append("}");

		return sb.toString();
	}

	/**
	 * 批量删除功能
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("batchDelete")
	public String batchDelete(String cartItemIds, HttpSession session, HttpServletRequest req) {
		/*
		 * 1. 获取cartItemIds参数 2. 调用service方法完成工作 3. 返回到list.jsp
		 */
		cartItemService.batchDelete(cartItemIds);
		return myCart(req);
	}

	/**
	 * 添加购物车条目
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("addCartItem")
	public String add(String bid, int quantity, HttpSession session, HttpServletRequest req) {
		/*
		 * 1. 封装表单数据到CartItem(bid, quantity)
		 */
		User user = (User) session.getAttribute("sessionUser");
		if (user == null) {
			req.setAttribute("msg", "请先登录");
			return "jsps/user/login.jsp";
		}
		Book book = bookService.finByBid(bid);
		CartItem cartItem = new CartItem();
		cartItem.setBook(book);
		cartItem.setUser(user);
		cartItem.setQuantity(quantity);
		/*
		 * 2. 调用service完成添加
		 */
		cartItemService.add(cartItem);
		/*
		 * 3. 查询出当前用户的所有条目，转发到list.jsp显示
		 */
		return myCart(req);
	}

	/**
	 * 我的购物车
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("myCart")
	public String myCart(HttpServletRequest req) {
		/*
		 * 1. 得到uid
		 */
		User user = (User) req.getSession().getAttribute("sessionUser");
		if (user == null) {
			req.setAttribute("msg", "请先登录");
			return "jsps/user/login.jsp";
		}
		String uid = user.getUid();
		/*
		 * 2. 通过service得到当前用户的所有购物车条目
		 */
		List<CartItem> cartItemLIst = cartItemService.myCart(uid);
		/*
		 * 3. 保存起来，转发到/cart/list.jsp
		 */
		req.setAttribute("cartItemList", cartItemLIst);
		return "jsps/cart/list.jsp";
	}
}
