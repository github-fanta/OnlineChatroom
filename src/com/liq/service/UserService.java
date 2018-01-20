package com.liq.service;

import com.liq.vo.User;

public interface UserService {
	User login(User user);

	User findUserById(String id);
}
