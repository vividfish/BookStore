package com.vivid.BookStore.category.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vivid.BookStore.category.domain.Category;
import com.vivid.BookStore.category.service.CategoryService;


@Controller
public class CategoryController {

	@Autowired
	CategoryService categoryService;

	/**
	 * 查询所有分类
	 */
	@RequestMapping("CategoryFindAll")
	public String findAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		/*
		 * 1. 通过service得到所有的分类 2. 保存到request中，转发到left.jsp
		 */
		List<Category> parents = categoryService.findAll();
		req.setAttribute("parents", parents);
		return "jsps/left.jsp";
	}
}
