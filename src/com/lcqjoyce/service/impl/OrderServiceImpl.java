package com.lcqjoyce.service.impl;

import com.lcqjoyce.dao.OrderDAO;
import com.lcqjoyce.dao.PlaneDAO;
import com.lcqjoyce.dao.impl.OrderDAOImpl;
import com.lcqjoyce.dao.impl.PlaneDAOImpl;
import com.lcqjoyce.domain.Order;
import com.lcqjoyce.domain.Plane;
import com.lcqjoyce.service.OrderService;
import com.lcqjoyce.utils.DateUtil;
import com.lcqjoyce.utils.Transaction;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.UUID;

//import com.lcqjoyce.dao.UserDAO;
//import com.lcqjoyce.dao.impl.UserDAOImpl;
//import com.lcqjoyce.domain.User;
//import com.lcqjoyce.utils.JdbcUtil;

public class OrderServiceImpl implements OrderService {
	private PlaneDAO p_dao = new PlaneDAOImpl();
	private OrderDAO o_dao = new OrderDAOImpl();
	// private UserDAO u_dao = new UserDAOImpl();
	private static Logger logger = Logger.getLogger(OrderServiceImpl.class);

	/**
	 * 打印order
	 */
	public void viewOrder(List<Order> list) {

		for (Order ps : list) {
		
			System.out.print("订单号  ：" + ps.getO_number() + "\t");
			System.out.print("舱位级别    ：" + ps.getO_sslevel() + "\t");
			System.out.print("舱位价格    	： " + ps.getO_price() + "\t");
			System.out.print("最终价格	： " + ps.getO_eprice() + "\t\t");
			System.out.println("航班号：" + ps.getO_rid() + "\t\t");

		}
	}

	public Order saveOrder(int pl_rid, int uid) {
		Plane plane = p_dao.get(pl_rid);
		logger.info("下单之前的查询飞机航班是否存在：" + plane + "\n");
		Order order = null;
		if (null != plane) {
			Order order_in = o_dao.queryPl_idAndU_id(plane.getPl_rid(), uid);
			logger.info("下单之前的查询订单是否存在：" + order_in + "\n");
			if (null == order_in.getO_number()) {
				logger.info("查询票数:   " + plane.getPl_ps());
				if (plane.getPl_ps() > 0) {
					// 有票,可以预定
					// User u = u_dao.get(uid);
					order = new Order();
					// 订单号,使用uuid表示
					order.setO_number(UUID.randomUUID().toString());
					order.setO_rid(pl_rid);
					order.setO_pid(uid);
					// 暂时写死
					order.setO_sslevel(1);
					order.setO_price(100.78);
					;
					order.setO_eprice(99.99);
					;
					// 座位号用余票号简单模拟

					// 保存订单
					int result = o_dao.save(order);
					logger.info("下单是否成功：" + result + "\n");
					// 更新余票
					p_dao.updateTickets(plane.getPl_ps() - 1, plane.getPl_rid());
				} else {
					throw new RuntimeException("该航班没票了!");
				}
			} else {
				throw new RuntimeException("您已购买了本次航班,不能重复购买!");
			}

		} else {
			throw new RuntimeException("该航班不存在!");
		}
		// 返回订单信息
		return order;
	}

	public void update(int pl_rid, Order order) {
		if (pl_rid != order.getO_rid()) {
			Plane newFlight = p_dao.get(pl_rid);
			if (null != newFlight.getPl_id()) {
				// 有票才能改
				if (newFlight.getPl_ps() > 0) {
					Plane oldFlight = p_dao.getFlightNumber(order.getO_rid()).get(0);
					// 改签
					try {
						new Transaction().beginTransaction();
						logger.info("开启事务");
						order.setO_rid(pl_rid);
						o_dao.update(order);
						// 原航班余票+1,该次航班余票-1
						p_dao.updateTickets(oldFlight.getPl_ps() + 1, oldFlight.getPl_rid());
						// 事務測試
						// int i = 1 / 0;
						p_dao.updateTickets(newFlight.getPl_ps() - 1, newFlight.getPl_rid());
						new Transaction().commit();
						logger.info("手动提交");
					} catch (Exception e) {
						logger.info("回滚事务");
						new Transaction().rollback();
						throw new RuntimeException("出现改签异常，请联系管理员!");
					}

				} else {
					throw new RuntimeException("该次航班没票了!");
				}
			} else {
				throw new RuntimeException("该航班不存在!");
			}
		} else {
			throw new RuntimeException("该航班id和原航班相同,禁止修改!");
		}
	}

	public void returnTicket(String orderNumber, int uid) {
		Order order = o_dao.queryByOrderNumber(orderNumber);
		logger.info("待退訂單  ：  " + order);
		// 订单的用户id必须是当前用户id,才能退票,防止用别人的订单号把别人的票退了
		if (null != order.getO_id() && order.getO_pid().equals(uid)) {

			Plane replane = p_dao.getFlightNumber(order.getO_rid()).get(0);
			logger.info("进入第二层replane " + replane);
			// 1代表是时间单位秒
			if (DateUtil.getDateDistance(replane.getPl_rst(), new Date(), 1) <= 2 * 60 * 60) {
				throw new RuntimeException("离出发时间小于2小时,不能退票!");
			} else {
				logger.info("进入退票");
				// 退票
				// 检查完毕开启事务
				try {
					new Transaction().beginTransaction();
					logger.info("开启事务");
					// 余票数+1
					p_dao.updateTickets(replane.getPl_ps() + 1, replane.getPl_rid());
					// 测试回滚 成功
					// int i=1/0;
					o_dao.delete(order.getO_id());
					new Transaction().commit();
				} catch (Exception e) {
					logger.info("进入事务回滾");
					new Transaction().rollback();
					throw new RuntimeException("出现退票异常，请联系管理员!");
				}
			}
		} else {
			throw new RuntimeException("订单号不存在!");
		}
	}

	public List<Order> listOrderByUid(int uid) {

		return o_dao.queryListByUid(uid);
	}

	public Order OrderByOrderNumber(String orderNumber) {

		return o_dao.queryByOrderNumber(orderNumber);
	}

	public void getOrder() {

	}

}
