package com.lcqjoyce.dao;

import java.util.List;
import com.lcqjoyce.domain.Order;

public interface OrderDAO {
	int save(Order obj);

	int update(Order obj);

	void delete(int o_id);

	public Order get(int o_id);

	public Order queryPl_idAndU_id(int pl_id, int uid);

	public Order queryByOrderNumber(String ordernumber);

	public Order queryByUid(int uid);

	public List<Order> queryListByUid(int uid);

	List<Order> list();
}
