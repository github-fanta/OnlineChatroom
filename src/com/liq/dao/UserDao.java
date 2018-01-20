package com.liq.dao;

import com.liq.vo.User;

public interface UserDao {

	User login(User user);

	User findUserById(String id);
}
