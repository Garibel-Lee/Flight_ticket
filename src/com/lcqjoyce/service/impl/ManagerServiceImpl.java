package com.lcqjoyce.service.impl;

import com.lcqjoyce.dao.ManagerDAO;
import com.lcqjoyce.dao.impl.ManagerDAOImpl;
import com.lcqjoyce.domain.Manager;
import com.lcqjoyce.service.ManagerService;

public class ManagerServiceImpl implements ManagerService {
	private ManagerDAO m_dao = new ManagerDAOImpl();

	public Manager login(Manager user) {
		return	m_dao.FindUser(user);
	}

}
