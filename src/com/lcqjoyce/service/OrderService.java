package com.lcqjoyce.service;

import java.util.List;

import com.lcqjoyce.domain.Order;

public interface OrderService {

	/**
	 * 下单即使用户购买飞机票
	 * 需要提交的信息有 
	 * 用户ID	
	 * 舱位等级
	 * 舱位号			
	 * 航线id  通过用户选择进来     
	 *  
	 *
	 */
	void getOrder( );
	Order saveOrder(int pl_id,int uid);
	public void viewOrder(List<Order> list);
	/**
	 * 查询该用户所有订单	 * 
	 * @param uid
	 * @return
	 */
	public List<Order> listOrderByUid(int uid);
	public Order OrderByOrderNumber(String orderNumber);
	public void returnTicket(String orderNumber, int uid);
	/**
	 * 改签
	 * @param pl_id
	 * @param order
	 */
	public void update(int pl_id, Order order);
}
