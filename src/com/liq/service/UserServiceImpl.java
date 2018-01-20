package com.liq.service;

import com.liq.dao.UserDao;
import com.liq.dao.UserDaoImpl;
import com.liq.vo.User;

public class UserServiceImpl implements UserService {

	private UserDao userDao = new UserDaoImpl();
	
	@Override
	public User login(User user) {
		return userDao.login(user);
	}

	@Override
	public User findUserById(String id) {
		// TODO Auto-generated method stub
		return userDao.findUserById(id);
	}

}
