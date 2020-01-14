package com.lcqjoyce.dao.impl;

import java.util.List;

import com.lcqjoyce.dao.OrderDAO;
import com.lcqjoyce.domain.Order;
import com.lcqjoyce.handler.BeanHandler;
import com.lcqjoyce.handler.BeanlistHandler;
import com.lcqjoyce.utils.JdbcTemplate;

public class OrderDAOImpl implements OrderDAO {
	/**
	 * 下单 创建飞机票订单需要几个
	 */

	public int save(Order obj) {
		return JdbcTemplate.update(
				"INSERT INTO order_info" + "(o_number, o_pid, o_rid, o_sslevel, o_price,o_eprice) VALUES (?,?,?,?,?,?)",
				obj.getO_number(), obj.getO_pid(), obj.getO_rid(), obj.getO_sslevel(), obj.getO_price(),
				obj.getO_eprice());

	}

	public int update(Order obj) {
		return JdbcTemplate.update(
				"Update order_info set  o_rid=? where o_number=?",
				 obj.getO_rid(), obj.getO_number());
	}

	public Order queryPl_idAndU_id(int pl_rid, int uid) {
		// 查一下订单中是否有存在用户的对应航班
		return JdbcTemplate.query("select * from order_info where o_rid=? and o_pid=? LIMIT 1",
				new BeanHandler<>(Order.class), pl_rid, uid);

	}

	public Order queryByUid(int uid) {
		// 查一下订单中是否有存在用户的对应航班
		return JdbcTemplate.query("select * from order_info where o_pid=? LIMIT 1", new BeanHandler<>(Order.class),
				uid);

	}

	public void delete(int o_id) {
		JdbcTemplate.update("delete from order_info where o_id =?", o_id);
	}

	public Order get(int o_id) {
		return JdbcTemplate.query("select * from order_info where o_id = ?", new BeanHandler<>(Order.class), o_id);

	}

	public List<Order> list() {
		return JdbcTemplate.query("select * from order_info ", new BeanlistHandler<>(Order.class));

	}

	public Order queryByOrderNumber(String ordernumber) {

		return JdbcTemplate.query("select * from order_info where o_number=? LIMIT 1", new BeanHandler<>(Order.class),
				ordernumber);
	}

	public List<Order> queryListByUid(int uid) {
		return JdbcTemplate.query("select * from order_info where o_pid=? ", new BeanlistHandler<>(Order.class), uid);
	}
}
