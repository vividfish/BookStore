package com.vivid.BookStore.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

//<mvc:interceptors>
//<mvc:interceptor>
//	<mvc:mapping path="/**" />
//	<mvc:exclude-mapping path="/login.jsp" />
//	<mvc:exclude-mapping path="/**/main.jsp" />
//	<mvc:exclude-mapping path="/Admin*" />
//	<mvc:exclude-mapping path="/admin/*" />
//	<mvc:exclude-mapping path="/CategoryFindAll" />
//	<mvc:exclude-mapping path="/userRegist" />
//	<mvc:exclude-mapping path="/userLogin" />
//	<mvc:exclude-mapping path="/findBook*" />
//	<mvc:exclude-mapping path="/loadBook*" />
//	<mvc:exclude-mapping path="/ajaxValidate" />
//	<mvc:exclude-mapping path="/VerifyCodeServlet" />
//	<bean class="com.vivid.BookStore.interceptor.LoginInterceptor" />
//</mvc:interceptor>
//<mvc:interceptor>
//	<mvc:mapping path="/adminjsps/**" />
//	<mvc:mapping path="/Admin*" />
//	<mvc:exclude-mapping path="/AdminLogin" />
//	<bean class="com.vivid.BookStore.interceptor.AdminInterceptor" />
//</mvc:interceptor>
//</mvc:interceptors>
public class LoginFilter implements Filter {
	private static final String paths[] = { "index.jsp", "login.jsp", "userLogin", "userRegist", "userRegist", "main,jsp",
			"findBook", "loadBook", "ajaxValidate", "VerifyCodeServlet", "CategoryFindAll", "contact.html" };

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		Object user = request.getSession().getAttribute("sessionUser");
		System.out.println(request.getServletPath()+"----------login");
		for (String path : paths) {
			if (request.getServletPath().contains(path)) {
				//System.out.println(request.getServletPath()+"------confirm ");
				chain.doFilter(request, resp);
				return;
			}
		}
		if (user == null) {
			req.setAttribute("code", "error");// 为了显示X图片
			req.setAttribute("msg", "您还没有登录，不能访问本资源");
			req.getRequestDispatcher("/jsps/msg.jsp").forward(req, resp);
			return;
		} else {
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void destroy() {

	}
}
