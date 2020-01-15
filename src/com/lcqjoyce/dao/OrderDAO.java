package com.lcqjoyce.dao;

import com.lcqjoyce.domain.Order;

import java.util.List;

public interface OrderDAO {
	/**
	 * 保存訂單
	 * 
	 * @param 实体类
	 * @return
	 */
	int save(Order obj);

	/**
	 * 更新订单
	 * 
	 * @param 实体类
	 * @return
	 */
	int update(Order obj);

	/**
	 * 刪除訂單
	 * 
	 * @param 訂單主鍵id
	 */
	void delete(int o_id);

	/**
	 * 获取订单
	 * 
	 * @param 订单主键id
	 * @return
	 */
	public Order get(int o_id);

	/**
	 * 通过航班号用户id找订单
	 * @param pl_id  航班号
	 * @param uid 	用户id
	 * @return
	 */
	public Order queryPl_idAndU_id(int pl_id, int uid);
	/**
	 * 通过订单的uuid查找订单
	 * @param ordernumber
	 * @return 实体类
	 */
	public Order queryByOrderNumber(String ordernumber);
	/**
	 * 查找当前用户一个订单
	 * @param uid
	 * @return 
	 */
	public Order queryByUid(int uid);
	/**
	 * 查找当前用户所有订单
	 * @param uid
	 * @return 
	 */
	public List<Order> queryListByUid(int uid);
	/**
	 * 查询存在所有订单
	 * @return
	 */
	List<Order> list();
}
