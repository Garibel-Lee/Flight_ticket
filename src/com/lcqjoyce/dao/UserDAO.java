package com.lcqjoyce.dao;

import java.util.List;

import com.lcqjoyce.domain.User;

public interface UserDAO {
	/**
	 * @description
	 * @param obj
	 */

	// 注册用户 初始化注册 上线之后检查信息是否完整进行不全不然不让继续

	public int save(User obj);

	public int updateLock(User obj);

	public int update(User obj);

	public int delete(int id);

	/**
	 * 寻找用户是否存在 1用户名 2手机号 3邮箱
	 * 
	 * @return
	 */

	/**
	 * 根据用户名查找
	 * 
	 * @param name
	 * @return
	 */
	public User getByName(String name);

	public User FindUser(User obj);

	public User get(int id);

	public List<User> list();
}
