package com.vivid.BookStore.admin.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vivid.BookStore.admin.domain.Admin;
import com.vivid.BookStore.admin.service.AdminService;

@Controller
public class AdminController {

	@Autowired
	AdminService adminService;

	/**
	 * 登录功能
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("AdminLogin")
	public String login(Admin form, HttpServletRequest req) {
		/*
		 * 1. 封装表单数据到Admin
		 */
		Admin admin = adminService.login(form);
		if (admin == null) {
			req.setAttribute("msg", "用户名或密码错误！");
			return "adminjsps/login.jsp";
		}
		req.getSession().setAttribute("admin", admin);
		return "adminjsps/admin/index.jsp";
	}

}
