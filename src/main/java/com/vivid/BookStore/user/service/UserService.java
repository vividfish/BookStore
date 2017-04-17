package com.vivid.BookStore.user.service;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivid.BookStore.user.dao.UserDao;
import com.vivid.BookStore.user.domain.User;
import com.vivid.BookStore.user.service.exception.UserException;
import com.vivid.BookStore.util.Mail;
import com.vivid.BookStore.util.MailUtil;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;

	/**
	 * 修改密码
	 * 
	 * @param uid
	 * @param newPass
	 * @param oldPass
	 * @throws UserException
	 */
	public void updatePassword(String uid, String newPass, String oldPass) throws UserException {

		/*
		 * 1. 校验老密码
		 */
		int result = userDao.findByUidAndPassword(uid, oldPass);
		if (result == 0) {// 如果老密码错误
			throw new UserException("老密码错误！");
		}

		/*
		 * 2. 修改密码
		 */
		userDao.updatePassword(uid, newPass);
	}

	/**
	 * 登录功能
	 * 
	 * @param user
	 * @return
	 */
	public User login(User user) {
		return userDao.findByLoginnameAndLoginpass(user.getLoginname(), user.getLoginpass());
	}

	/**
	 * 激活功能
	 * 
	 * @param code
	 * @throws UserException
	 */
	public void activatioin(String code) throws UserException {
		User user = userDao.findByCode(code);
		if (user == null)
			throw new UserException("无效的激活码！");
		if (user.isStatus())
			throw new UserException("您已经激活过了，不要二次激活！");
		userDao.updateStatus(user.getUid(), true);// 修改状态
	}

	/**
	 * 用户名注册校验
	 * 
	 * @param loginname
	 * @return
	 */
	public boolean ajaxValidateLoginname(String loginname) {
		return userDao.ajaxValidateLoginname(loginname);
	}

	/**
	 * Email校验
	 * 
	 * @param email
	 * @return
	 */
	public boolean ajaxValidateEmail(String email) {
		return userDao.ajaxValidateEmail(email);
	}

	/**
	 * 注册功能
	 * 
	 * @param user
	 */
	public void regist(User user) {
		/*
		 * 1. 数据的补齐
		 */
		user.setUid(UUID.randomUUID().toString().replace("-", ""));
		user.setStatus(false);
		user.setActivationCode(UUID.randomUUID().toString().replace("-", ""));
		userDao.add(user);
		/*
		 * 3. 发邮件
		 */
		/*
		 * 把配置文件内容加载到prop中
		 */
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getResourceAsStream("/conf/email_template.properties"));
		} catch (IOException e1) {
			throw new RuntimeException(e1);
		}
		/*
		 * 登录邮件服务器，得到session
		 */
		String host = prop.getProperty("host");// 服务器主机名
		String name = prop.getProperty("username");// 登录名
		String pass = prop.getProperty("password");// 登录密码
		Session session = MailUtil.createSession(host, name, pass);

		/*
		 * 创建Mail对象
		 */
		String from = prop.getProperty("from");
		String to = user.getEmail();
		String subject = prop.getProperty("subject");
		// MessageForm.format方法会把第一个参数中的{0},使用第二个参数来替换。
		// 例如MessageFormat.format("你好{0}, 你{1}!", "张三", "去死吧"); 返回“你好张三，你去死吧！”
		String content = MessageFormat.format(prop.getProperty("content"), user.getActivationCode());
		Mail mail = new Mail(from, to, subject, content);
		/*
		 * 发送邮件
		 */
		try {
			MailUtil.send(session, mail);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Map<String, String> validateRegist(User formUser, String vCode) {
		Map<String, String> errors = new HashMap<String, String>();
		/*
		 * 1. 校验登录名
		 */
		String loginname = formUser.getLoginname();
		if (loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if (loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在3~20之间！");
		} else if (ajaxValidateLoginname(loginname)) {
			errors.put("loginname", "用户名已被注册！");
		}

		/*
		 * 2. 校验登录密码
		 */
		String loginpass = formUser.getLoginpass();
		if (loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if (loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}

		/*
		 * 3. 确认密码校验
		 */
		String reloginpass = formUser.getReloginpass();
		if (reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "确认密码不能为空！");
		} else if (!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次输入不一致！");
		}

		/*
		 * 4. 校验email
		 */
		String email = formUser.getEmail();
		if (email == null || email.trim().isEmpty()) {
			errors.put("email", "Email不能为空！");
		} else if (!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "Email格式错误！");
		} else if (ajaxValidateEmail(email)) {
			errors.put("email", "Email已被注册！");
		}

		/*
		 * 5. 验证码校验
		 */
		String verifyCode = formUser.getVerifyCode();
		if (verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if (!verifyCode.equalsIgnoreCase(vCode)) {
			errors.put("verifyCode", "验证码错误！");
		}

		return errors;
	}

	public Map<String, String> validateLogin(User formUser, String vCode) {
		Map<String, String> errors = new HashMap<String, String>();
		String loginname = formUser.getLoginname();
		if (loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if (loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在3~20之间！");
		} else if (!ajaxValidateLoginname(loginname)) {
			errors.put("loginname", "用户名已被注册！");
		}
		String loginpass = formUser.getLoginpass();
		if (loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if (loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}
		String verifyCode = formUser.getVerifyCode();
		if (verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if (!verifyCode.equalsIgnoreCase(vCode)) {
			System.out.println(verifyCode + vCode);
			errors.put("verifyCode", "验证码错误！");
		}
		return errors;
	}
}
