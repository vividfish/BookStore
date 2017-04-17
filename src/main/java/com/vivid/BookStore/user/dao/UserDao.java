package com.vivid.BookStore.user.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.vivid.BookStore.user.domain.User;

@Mapper
@Repository
public interface UserDao {

	/**
	 * 按uid和password查询
	 * 
	 * @param uid
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public int findByUidAndPassword(@Param("uid") String uid, @Param("password") String password);

	/**
	 * 修改密码
	 * 
	 * @param uid
	 * @param password
	 * @throws SQLException
	 */
	public int updatePassword(@Param("uid") String uid, @Param("password") String password);

	/**
	 * 按用户名和密码查询
	 * 
	 * @param loginname
	 * @param loginpass
	 * @return
	 * @throws SQLException
	 */
	public User findByLoginnameAndLoginpass(@Param("loginname") String loginname, @Param("loginpass") String loginpass);

	/**
	 * 通过激活码查询用户
	 * 
	 * @param code
	 * @return
	 * @throws SQLException
	 */
	public User findByCode(String code);

	/**
	 * 修改用户状态
	 * 
	 * @param uid
	 * @param status
	 * @throws SQLException
	 */
	public int updateStatus(String uid, boolean status);

	/**
	 * 校验用户名是否注册
	 * 
	 * @param loginname
	 * @return
	 * @throws SQLException
	 */
	public boolean ajaxValidateLoginname(String loginname);

	/**
	 * 校验Email是否注册
	 * 
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public boolean ajaxValidateEmail(String email);

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @throws SQLException
	 */
	public void add(User user);

}
