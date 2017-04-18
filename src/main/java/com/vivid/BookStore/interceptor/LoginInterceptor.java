package com.vivid.BookStore.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse response, Object handler)
			throws Exception {
		/*
		 * 1. 获取session中的user 2. 判断是否为null > 如果为null：保存错误信息，转发到msg.jsp >
		 * 如果不为null：放行
		 */
		Object user = req.getSession().getAttribute("sessionUser");
		if (user == null) {
			req.setAttribute("code", "error");// 为了显示X图片
			req.setAttribute("msg", "您还没有登录，不能访问本资源");
			req.getRequestDispatcher("/jsps/msg.jsp").forward(req, response);
			return false;
		} else {
			return true;
		}

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
}
