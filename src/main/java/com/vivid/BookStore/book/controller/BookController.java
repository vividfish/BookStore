package com.vivid.BookStore.book.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vivid.BookStore.book.domain.Book;
import com.vivid.BookStore.book.service.BookService;
import com.vivid.BookStore.pager.domain.Page;

@Controller
public class BookController {
	@Autowired
	private BookService bookService;

	/**
	 * 获取当前页码
	 * 
	 * @param req
	 * @return
	 */

	/**
	 * 截取url，页面中的分页导航中需要使用它做为超链接的目标！
	 * 
	 * @param req
	 * @return
	 */
	/*
	 * http://localhost:8080/goods/BookServlet?methed=findByCategory&cid=xxx&pc=
	 * 3 /goods/BookServlet + methed=findByCategory&cid=xxx&pc=3
	 */

	/**
	 * 按bid查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("loadBook")
	public String loadBook(HttpServletRequest req) {
		String bid = req.getParameter("bid");// 获取链接的参数bid
		Book book = bookService.load(bid);// 通过bid得到book对象
		req.setAttribute("book", book);// 保存到req中
		return "jsps/book/desc.jsp";// 转发到desc.jsp
	}

	/**
	 * 按分类查
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("findBookByCategory")
	public String findByCategory(String cid, Integer pc, HttpServletRequest req) {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		if (pc == null) {
			pc = 1;
		}
		/*
		 * 2. 得到url：...
		 */
		String url = "findBookByCategory?cid="+cid;
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		Page<Book> pb = bookService.findByCategory(cid, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "jsps/book/list.jsp";
	}

	/**
	 * 按作者查
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("findBookByAuthor")
	public String findBookByAuthor(String author, Integer pc, HttpServletRequest req) {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		if (pc == null) {
			pc = 1;
		}
		/*
		 * 2. 得到url：...
		 */
		String url = "findBookByAuthor?author=" + author;
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		Page<Book> pb = bookService.findByAuthor(author, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "jsps/book/list.jsp";
	}

	/**
	 * 按出版社查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("findBookByPress")
	public String findBookByPress(String press, Integer pc, HttpServletRequest req) {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		if (pc == null) {
			pc = 1;
		}
		/*
		 * 2. 得到url：...
		 */
		String url = "findBookByPress?press=" + press;
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		Page<Book> pb = bookService.findByPress(press, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "jsps/book/list.jsp";
	}

	/**
	 * 按图名查
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("findBookByBname")
	public String findBookByBname(String bname, Integer pc, HttpServletRequest req) {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		if (pc == null) {
			pc = 1;
		}
		/*
		 * 2. 得到url：...
		 */
		String url = "findBookByBname?bname=" + bname;
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		Page<Book> pb = bookService.findByBname(bname, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "jsps/book/list.jsp";
	}

	/**
	 * 多条件组合查询
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("findBookByCombination")
	public String findByCombination(Book book, Integer pc, HttpServletRequest req) {
		/*
		 * 1. 得到pc：如果页面传递，使用页面的，如果没传，pc=1
		 */
		if (pc == null) {
			pc = 1;
		}
		/*
		 * 2. 得到url：...
		 */
		String url = "findBookByCombination?bname=" + book.getBname() + "&author=" + book.getAuthor() + "&press="
				+ book.getPress();
		/*
		 * 3. 获取查询条件，本方法就是cid，即分类的id
		 */
		/*
		 * 4. 使用pc和cid调用service#findByCategory得到PageBean
		 */
		Page<Book> pb = bookService.findByCombination(book, pc);
		/*
		 * 5. 给PageBean设置url，保存PageBean，转发到/jsps/book/list.jsp
		 */
		pb.setUrl(url);
		req.setAttribute("pb", pb);
		return "jsps/book/list.jsp";
	}
}
