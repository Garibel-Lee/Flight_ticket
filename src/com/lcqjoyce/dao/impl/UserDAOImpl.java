package com.lcqjoyce.dao.impl;

import com.lcqjoyce.dao.UserDAO;
import com.lcqjoyce.domain.User;
import com.lcqjoyce.handler.BeanHandler;
import com.lcqjoyce.handler.BeanlistHandler;
import com.lcqjoyce.utils.JdbcTemplate;

import java.util.List;



public class UserDAOImpl implements UserDAO{

	
	/**
	 * 锁机制通过线程绑定资源  当前用户resudie小等于0时候
	 * 设置用户表的locktime
	 * 一小时范围之内无法登录
	 */
	//更新锁操作
	/**
	 * 更新登录锁信息
	 * 设置被锁的时间和剩余输入次数
	 */
	public int updateLock(User obj) {
		return JdbcTemplate.update("update passenger_info  set p_locktime=? ,p_resudie=? where p_id=?",
				obj.getP_locktime(),obj.getP_resudie(),obj.getP_id());
		
	}	
	/**
	 * 登录时候
	 * 寻找用户
	 */
	public User FindUser(User obj) {
		return JdbcTemplate.query("select * from passenger_info where p_name=? and  p_pwd=?",new BeanHandler<>(User.class),obj.getP_name(),obj.getP_pwd());
	}
	
	
	public int save(User obj) {
		return JdbcTemplate.update("insert into passenger_info (p_name,p_pwd,p_locktime) values(?,?,?)",obj.getP_name(),obj.getP_pwd(),obj.getP_locktime());
	}
	
	
	
	public int update(User obj) {
		return JdbcTemplate.update("insert into passenger_info (p_name,p_pwd) values(?,?)",obj.getP_name(),obj.getP_pwd());
		
	}

	public int delete(int id) {
		return JdbcTemplate.update("delete from passenger_info  where p_pid=?",id);	
	}

	public User get(int id) {
		return  JdbcTemplate.query("select * from passenger_info where p_id = ?",new BeanHandler<>(User.class),id);
	}

	public User getByName(String name) {
		return  JdbcTemplate.query("select * from passenger_info where p_name = ?",new BeanHandler<>(User.class),name);
	}
	
	public List<User> list() {
		return JdbcTemplate.query("select * from passenger_info ",new BeanlistHandler<>(User.class));
	}

}
