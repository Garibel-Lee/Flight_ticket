package Test;

import com.lcqjoyce.dao.OrderDAO;
import com.lcqjoyce.dao.impl.OrderDAOImpl;
import com.lcqjoyce.domain.Order;

import java.util.List;

class OrderDAOTest {

	private OrderDAO o_order = new OrderDAOImpl();



	void testSave() {

	}


	void testUpdate() {
	}


	void testDelete() {
		
		

	}


	void testGet() {
		Order ord = o_order.get(1);
		System.out.println(ord.toString());
	}


	void testList() {
		List<Order> list = o_order.list();
		for(Order or:list) {
			System.out.println(or);
		}
	}

}
