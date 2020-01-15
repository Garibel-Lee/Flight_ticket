package com.lcqjoyce.dao;

import com.lcqjoyce.domain.Manager;


public interface ManagerDAO {

	/**
	 * 查詢管理員用戶
	 * @param obj
	 * @return
	 */
	public Manager FindUser(Manager obj);
}
