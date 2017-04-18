package com.vivid.BookStore.user.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vivid.BookStore.user.domain.User;
import com.vivid.BookStore.user.service.UserService;
import com.vivid.BookStore.user.service.exception.UserException;

@Controller
public class UserController {
	@Autowired
	UserService userService;

	/**
	 * ajax用户名是否注册校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("ajaxValidateLoginname")
	@ResponseBody
	public String ajaxValidateLoginname(String loginname) {
		/*
		 * 1. 获取用户名
		 */
		/*
		 * 2. 通过service得到校验结果
		 */
		System.out.println(loginname);
		boolean b = userService.ajaxValidateLoginname(loginname);
		/*
		 * 3. 发给客户端
		 */
		System.out.println(b);
		return String.valueOf(b);
	}

	/**
	 * ajax Email是否注册校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("ajaxValidateEmail")
	@ResponseBody
	public String ajaxValidateEmail(String email) {

		/*
		 * 2. 通过service得到校验结果
		 */
		boolean b = userService.ajaxValidateEmail(email);
		/*
		 * 3. 发给客户端
		 */
		return String.valueOf(b);
	}

	/**
	 * 注册功能
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("userRegist")
	public String regist(User formUser, HttpServletRequest req) {

		String vCode = (String) req.getSession().getAttribute("vCode");
		Map<String, String> errors = userService.validateRegist(formUser, vCode);
		if (errors.size() > 0) {
			req.setAttribute("form", formUser);
			req.setAttribute("errors", errors);
			return "jsps/user/regist.jsp";
		}
		/*
		 * 3. 使用service完成业务
		 */
		userService.regist(formUser);
		/*
		 * 4. 保存成功信息，转发到msg.jsp显示！
		 */
		req.setAttribute("code", "success");
		req.setAttribute("msg", "注册功能，请马上到邮箱激活！");
		return "jsps/msg.jsp";
	}

	/**
	 * 激活功能
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("activation")
	public String activation(String activationCode, HttpServletRequest req) {
		/*
		 * 1. 获取参数激活码 2. 用激活码调用service方法完成激活 > service方法有可能抛出异常,
		 * 把异常信息拿来，保存到request中，转发到msg.jsp显示 3. 保存成功信息到request，转发到msg.jsp显示。
		 */
		try {
			userService.activatioin(activationCode);
			req.setAttribute("code", "success");// 通知msg.jsp显示对号
			req.setAttribute("msg", "恭喜，激活成功，请马上登录！");
		} catch (UserException e) {
			// 说明service抛出了异常
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("code", "error");// 通知msg.jsp显示X
		}
		return "jsps/msg.jsp";
	}

	/**
	 * 修改密码
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("updatePassword")
	public String updatePassword(User formUser, HttpSession session, HttpServletRequest req) {
		/*
		 * 1. 封装表单数据到user中 2. 从session中获取uid 3.
		 * 使用uid和表单中的oldPass和newPass来调用service方法 >
		 * 如果出现异常，保存异常信息到request中，转发到pwd.jsp 4. 保存成功信息到rquest中 5. 转发到msg.jsp
		 */
		User user = (User) session.getAttribute("sessionUser");
		// 如果用户没有登录，返回到登录页面，显示错误信息
		if (user == null) {
			req.setAttribute("msg", "您还没有登录！");
			return "jsps/user/login.jsp";
		}

		try {
			userService.updatePassword(user.getUid(), formUser.getNewpass(), formUser.getLoginpass());
			req.setAttribute("msg", "修改密码成功");
			req.setAttribute("code", "success");
			return "jsps/msg.jsp";
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());// 保存异常信息到request
			req.setAttribute("user", formUser);// 为了回显
			return "jsps/user/pwd.jsp";
		}
	}

	/**
	 * 退出功能
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("quit")
	public String quit(HttpSession session) {
		session.invalidate();
		return "jsps/user/login.jsp";
	}

	/**
	 * 登录功能
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("userLogin")
	public String login(User formUser, HttpServletRequest req, HttpServletResponse resp) {
		/*
		 * 1. 封装表单数据到User 2. 校验表单数据 3. 使用service查询，得到User 4. 查看用户是否存在，如果不存在： *
		 * 保存错误信息：用户名或密码错误 * 保存用户数据：为了回显 * 转发到login.jsp 5. 如果存在，查看状态，如果状态为false：
		 * * 保存错误信息：您没有激活 * 保存表单数据：为了回显 * 转发到login.jsp 6. 登录成功： *
		 * 保存当前查询出的user到session中 * 保存当前用户的名称到cookie中，注意中文需要编码处理。
		 */
		String vCode = (String) req.getSession().getAttribute("vCode");
		Map<String, String> errors = userService.validateLogin(formUser, vCode);
		if (errors.size() > 0) {
			req.setAttribute("form", formUser);
			req.setAttribute("msg", errors);
			return "jsps/user/login.jsp";
		}

		/*
		 * 3. 调用userService#login()方法
		 */
		User user = userService.login(formUser);
		/*
		 * 4. 开始判断
		 */
		if (user == null) {
			req.setAttribute("msg", "用户名或密码错误！");
			req.setAttribute("user", formUser);
			return "jsps/user/login.jsp";
		} else {
			if (!user.isStatus()) {
				req.setAttribute("msg", "您还没有激活！");
				req.setAttribute("user", formUser);
				return "jsps/user/login.jsp";
			} else {
				// 保存用户到session
				req.getSession().setAttribute("sessionUser", user);
				// 获取用户名保存到cookie中
				String loginname = user.getLoginname();
				try {
					loginname = URLEncoder.encode(loginname, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				Cookie cookie = new Cookie("loginname", loginname);
				cookie.setMaxAge(60 * 60 * 24 * 10);// 保存10天
				resp.addCookie(cookie);
				return "redirect:index.jsp";// 重定向到主页
			}
		}
	}

	/**
	 * ajax验证码是否正确校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("ajaxValidateVerifyCode")
	@ResponseBody
	public String ajaxValidateVerifyCode(String verifyCode, HttpSession session) throws ServletException, IOException {
		String vcode = (String) session.getAttribute("vCode");
		boolean b = verifyCode.equalsIgnoreCase(vcode);
		String result = String.valueOf(b);
		return result;
	}
	/*
	 * 注册校验 对表单的字段进行逐个校验，如果有错误，使用当前字段名称为key，错误信息为value，保存到map中 返回map
	 */

}
