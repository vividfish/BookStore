package com.vivid.BookStore.admin.dao;

import org.apache.ibatis.annotations.Param;

import com.vivid.BookStore.admin.domain.Admin;

public interface AdminDao {

	/**
	 * 通过管理员登录名和登录密码查询
	 * 
	 * @param adminname
	 * @param adminpwd
	 * @return
	 * @throws SQLException
	 */
	public Admin find(@Param("adminname") String adminname, @Param("adminpwd") String adminpwd);
}
