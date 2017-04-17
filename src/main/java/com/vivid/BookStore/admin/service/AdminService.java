package com.vivid.BookStore.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vivid.BookStore.admin.dao.AdminDao;
import com.vivid.BookStore.admin.domain.Admin;

@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	/**
	 * 登录功能
	 * 
	 * @param admin
	 * @return
	 */
	public Admin login(Admin admin) {
		return adminDao.find(admin.getAdminname(), admin.getAdminpwd());
	}
}
