package com.lcqjoyce.service;

import com.lcqjoyce.domain.Order;
import com.lcqjoyce.domain.Plane;

import java.util.List;

/**
 * 
 * 
 * 基础功能 
 * 	增加航班
 * 
 * 查询航班
 * 提供 三种查询  
 *  时间日期   
 *  始发目的地    
 *  航班号
 * service 层与DAO层交互
 * @author 13059
 *
 */
public interface FlightService {
	/**
	 * 飞机航班的打印工具
	 * @param list
	 */
	void viewFlight(List<Plane> list);
	/**
	 * 查询所有航班
	 * @return
	 */
	public List<Plane> getFlightlist();

	/**
	 * 为起飞航班查询所有的信息
	 * 
	 * @return
	 */
	public List<Plane> getNoTakeofflist(Order order);
}
