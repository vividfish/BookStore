package com.vivid.BookStore.user.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vivid.BookStore.user.service.VerifyCode;

@Controller
public class VerifyController {

	@RequestMapping("VerifyCodeServlet")
	public void getImage(HttpSession session, HttpServletResponse resp) throws ServletException, IOException {
		VerifyCode vc = new VerifyCode();
		BufferedImage image = vc.getImage();// 获取一次性验证码图片
		// 该方法必须在getImage()方法之后来调用
		// System.out.println(vc.getText());//获取图片上的文本

		// 把文本保存到session中，为LoginServlet验证做准备
		session.setAttribute("vCode", vc.getText());
		VerifyCode.output(image, resp.getOutputStream());
	}
}
