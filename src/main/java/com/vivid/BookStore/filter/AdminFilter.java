package com.vivid.BookStore.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.vivid.BookStore.admin.domain.Admin;

public class AdminFilter implements Filter {
	private static final String paths[] = { "Admin", "adminjsps" };

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		Admin admin = (Admin) ((HttpServletRequest) req).getSession().getAttribute("admin");
		HttpServletRequest request = (HttpServletRequest) req;
		System.out.println(request.getServletPath()+"admin");
		for (String path : paths) {
			if (request.getContextPath().contains(path)) {
				if (admin == null) {
					req.setAttribute("code", "error");// 为了显示X图片
					req.setAttribute("msg", "检测到非法操作");
					req.getRequestDispatcher("/jsps/msg.jsp").forward(req, resp);
					return;
				}
			}
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void destroy() {

	}
}
