package com.lcqjoyce.dao.impl;

import com.lcqjoyce.dao.ManagerDAO;
import com.lcqjoyce.domain.Manager;
import com.lcqjoyce.handler.BeanHandler;
import com.lcqjoyce.utils.JdbcTemplate;

public class ManagerDAOImpl implements ManagerDAO {

	public Manager FindUser(Manager obj) {
		return JdbcTemplate.query("select * from admin_info where m_name=? and  m_pwd=?",
				new BeanHandler<>(Manager.class), obj.getM_name(), obj.getM_pwd());
	}

}
