package com.lcqjoyce.service;

import com.lcqjoyce.domain.User;

/**
 * 用户服务包括
 * @author 13059
 *
 */
public interface UserLoginService {
	void Register(User user);
	User login(User user);
	User updateInfo(User user);
	
}
