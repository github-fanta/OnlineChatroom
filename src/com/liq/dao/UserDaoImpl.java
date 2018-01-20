package com.liq.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.liq.utils.JDBCUtils;
import com.liq.vo.User;

public class UserDaoImpl implements UserDao {

	@Override
	public User login(User user) {

		if (user == null) {
			return null;
		}
		
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		User existUser = null;
		try {
			existUser = queryRunner.query(sql, new BeanHandler<>(User.class), user.getUsername(), user.getPassword());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return existUser;
	}

	@Override
	public User findUserById(String id) {
		if (id == null) {
			return null;
		}
		
		QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where id=?";
		User existUser = null;
		try {
			existUser = queryRunner.query(sql, new BeanHandler<>(User.class), id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return existUser;
	}

}
