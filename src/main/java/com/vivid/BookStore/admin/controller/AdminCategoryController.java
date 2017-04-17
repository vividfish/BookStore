package com.vivid.BookStore.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.vivid.BookStore.book.service.BookService;
import com.vivid.BookStore.category.domain.Category;
import com.vivid.BookStore.category.service.CategoryService;

@Controller
public class AdminCategoryController {
	@Autowired
	CategoryService categoryService;
	@Autowired
	BookService bookService;

	/**
	 * 查询所有分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest req) {
		req.setAttribute("parents", categoryService.findAll());
		return "adminjsps/admin/category/list.jsp";
	}

	/**
	 * 添加一级分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addParent(Category parent, HttpServletRequest req) {
		/*
		 * 1. 封装表单数据到Category中 2. 调用service的add()方法完成添加 3.
		 * 调用findAll()，返回list.jsp显示所有分类
		 */
		parent.setCid(UUID.randomUUID().toString().replace("-", ""));// 设置cid
		categoryService.add(parent);
		return findAll(req);
	}

	public String addChild(Category child, HttpServletRequest req) {
		/*
		 * 1. 封装表单数据到Category中 2. 需要手动的把表单中的pid映射到child对象中 2.
		 * 调用service的add()方法完成添加 3. 调用findAll()，返回list.jsp显示所有分类
		 */
		child.setCid(UUID.randomUUID().toString().replace("-", ""));// 设置cid

		// 手动映射pid
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);

		categoryService.add(child);
		return findAll(req);
	}

	/**
	 * 添加第二分类：第一步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addChildPre(HttpServletRequest req) {
		String pid = req.getParameter("pid");// 当前点击的父分类id
		List<Category> parents = categoryService.findParents();
		req.setAttribute("pid", pid);
		req.setAttribute("parents", parents);

		return "f:/adminjsps/admin/category/add2.jsp";
	}

	/**
	 * 修改一级分类：第一步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParentPre(HttpServletRequest req) {
		/*
		 * 1. 获取链接中的cid 2. 使用cid加载Category 3. 保存Category 4.
		 * 转发到edit.jsp页面显示Category
		 */
		String cid = req.getParameter("cid");
		Category parent = categoryService.load(cid);
		req.setAttribute("parent", parent);
		return "f:/adminjsps/admin/category/edit.jsp";
	}

	/**
	 * 修改一级分类：第二步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editParent(Category parent, HttpServletRequest req) {
		/*
		 * 1. 封装表单数据到Category中 2. 调用service方法完成修改 3. 转发到list.jsp显示所有分类（return
		 * findAll();）
		 */
		categoryService.edit(parent);
		return findAll(req);
	}

	/**
	 * 修改二级分类：第一步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChildPre(HttpServletRequest req) {
		/*
		 * 1. 获取链接参数cid，通过cid加载Category，保存之 2. 查询出所有1级分类，保存之 3. 转发到edit2.jsp
		 */
		String cid = req.getParameter("cid");
		Category child = categoryService.load(cid);
		req.setAttribute("child", child);
		req.setAttribute("parents", categoryService.findParents());

		return "f:/adminjsps/admin/category/edit2.jsp";
	}

	/**
	 * 修改二级分类：第二步
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String editChild(Category child, HttpServletRequest req) {
		/*
		 * 1. 封装表单参数到Category child 2. 把表单中的pid封装到child, ... 3.
		 * 调用service.edit()完成修改 4. 返回到list.jsp
		 */
		String pid = req.getParameter("pid");
		Category parent = new Category();
		parent.setCid(pid);
		child.setParent(parent);

		categoryService.edit(child);
		return findAll(req);
	}

	/**
	 * 删除一级分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteParent(HttpServletRequest req) {
		/*
		 * 1. 获取链接参数cid，它是一个1级分类的id 2. 通过cid，查看该父分类下子分类的个数 3.
		 * 如果大于零，说明还有子分类，不能删除。保存错误信息，转发到msg.jsp 4. 如果等于零，删除之，返回到list.jsp
		 */
		String cid = req.getParameter("cid");
		int cnt = categoryService.findChildrenCountByParent(cid);
		if (cnt > 0) {
			req.setAttribute("msg", "该分类下还有子分类，不能删除！");
			return "f:/adminjsps/msg.jsp";
		} else {
			categoryService.delete(cid);
			return findAll(req);
		}
	}

	/**
	 * 删除2级分类
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String deleteChild(HttpServletRequest req) {
		/*
		 * 1. 获取cid，即2级分类id 2. 获取该分类下的图书个数 3. 如果大于零，保存错误信息，转发到msg.jsp 4.
		 * 如果等于零，删除之，返回到list.jsp
		 */
		String cid = req.getParameter("cid");
		int cnt = bookService.findBookCountByCategory(cid);
		if (cnt > 0) {
			req.setAttribute("msg", "该分类下还存在图书，不能删除！");
			return "f:/adminjsps/msg.jsp";
		} else {
			categoryService.delete(cid);
			return findAll(req);
		}
	}
}
