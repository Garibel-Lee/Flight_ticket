package com.lcqjoyce.UI;

import com.lcqjoyce.dao.PlaneDAO;
import com.lcqjoyce.dao.UserDAO;
import com.lcqjoyce.dao.impl.PlaneDAOImpl;
import com.lcqjoyce.dao.impl.UserDAOImpl;
import com.lcqjoyce.domain.Order;
import com.lcqjoyce.domain.Plane;
import com.lcqjoyce.domain.User;
import com.lcqjoyce.service.FlightService;
import com.lcqjoyce.service.OrderService;
import com.lcqjoyce.service.impl.FlightServiceImpl;
import com.lcqjoyce.service.impl.OrderServiceImpl;
import com.lcqjoyce.utils.DateUtil;
import com.lcqjoyce.utils.MD5;
import com.lcqjoyce.utils.MyScanner;
import com.lcqjoyce.utils.Regular;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserFunction {
	private ThreadLocal<User> threadlocal = new ThreadLocal<User>();
	private FlightService flightservice = new FlightServiceImpl();
	private OrderService orderservice = new OrderServiceImpl();
	private UserDAO o_user = new UserDAOImpl();
	private PlaneDAO p_dao = new PlaneDAOImpl();

	public UserFunction(ThreadLocal<User> threadLocal) {
		this.threadlocal = threadLocal;
	}

	// 查询航班
	public void queryForFlightInfo() {
		flightservice.viewFlight(p_dao.list());

		do {
			System.out.println("1.按出发地,目的地   2. 按航班号查询   3.日期查询  ");
			System.out.print("请输入序号:");

			int item = 0;
			try {
				item = new MyScanner().getInt();
			} catch (Exception e) {
				System.out.println("输入有误请输入一个有效数字");
				continue;
			}
  			switch (item) {
			case 1:
				queryForPlace();
				break;
			case 2:
				queryForFlightNumber();
				break;
			case 3:
				queryForDate();
				break;
			default:
				System.out.println("输入有误!");
				break;
			}
			System.out.print(" y再次进入当前功能  其他任意键退出: ");
		} while ("y".equalsIgnoreCase(new MyScanner().getString()));
	}

	// 航班号查询
	public void queryForFlightNumber() {

		int item = 0;
		while (true) {
			try {
				System.out.println("请输入航班号，进行精确查询");
				item = new MyScanner().getInt();
				break;
			} catch (Exception e) {
				System.out.println("输入有误请输入一个有效数字");
				continue;
			}
		}

		flightservice.viewFlight(p_dao.getFlightNumber(item));

	}

	// 地点模糊查询 sql语句拼接
	public void queryForPlace() {
		System.out.println("请输入城市，进行地点模糊查询");
		String str = new MyScanner().getString();
		flightservice.viewFlight(p_dao.getPlace(str));

	}

	// 日期固定格式查询
	public void queryForDate() {
		Date date;
		String str;
		while (true) {
			System.out.println("请输入日期格式yyyyMMdd，进行当天航班查询");
			str = new MyScanner().getString();
			date = DateUtil.parseDate(str, DateUtil.ACCURACY_PATTERN_DAY);
			if (date != null) {
				//System.out.println(date);
				if(p_dao.getDate(date).isEmpty())
				{
					System.out.println("抱歉未查询到");

				}
				flightservice.viewFlight(p_dao.getDate(date));
				break;
			}
		}
	}

	// 买票预定机票
	public void buyTicket() {
		User userinfo = o_user.get(threadlocal.get().getP_id());
		if (userinfo.getP_name() == null || userinfo.getP_id() == null) {
			System.out.println("前往更新用户頁面，请先实名认证!");
			updateUser();
		}
		List<Plane> list = flightservice.getFlightlist();
		// 输出机票信息
		if (null != list) {

			flightservice.viewFlight(list);
			System.out.println("请输入航班ID预定: ");
			int pl_rid = new MyScanner().getInt();
			System.out.println("您确定预定吗(y/n): ");
			String flag = new MyScanner().getString();

			if ("y".equalsIgnoreCase(flag)) {
				User li = threadlocal.get();
				Order order = null;
				try {
					// 预定
					System.out.println("航班id:  " + pl_rid);
					order = orderservice.saveOrder(pl_rid, li.getP_id().intValue());
					List<Order> orders = new ArrayList<Order>();
					orders.add(order);
					// 打印订单详情
					orderservice.viewOrder((orders));
					System.out.println("\n请付款" + order.getO_eprice() + "元\n");
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

			} else {
				System.out.println("取消预定!");
			}
		} else {
			System.out.println("暂无航班信息,请等待!");
		}

	}

	// 改签机票
	public void updateTicket() {

		User current = threadlocal.get();
		List<Order> orders = orderservice.listOrderByUid(current.getP_id());

		if (!orders.isEmpty()) {
			orderservice.viewOrder(orders);
			System.out.print("请输入要改签的订单号: ");
			String orderNumber = new MyScanner().getString();
			Order order = orderservice.OrderByOrderNumber(orderNumber);
			Plane upplane = p_dao.getFlightNumber(order.getO_rid()).get(0);

			if (order.getO_id() == null || order.getO_pid() != current.getP_id()) {
				// 检查订单号是否存在,并且防止用户把订单号输入成别人的订单号
				System.out.println("订单号不存在!");
			} else {
				if (DateUtil.getDateDistance(upplane.getPl_rst(), new Date(), 1) <= 2 * 60 * 60) {
					System.out.println("离出发时间小于2小时,不能改签!");
				} else {
					// 获得所有為起飛的航班信息
					System.out.println("查询未起飞航班 且排除当前的航班号暂定");
					List<Plane> fs = flightservice.getFlightlist();
					// 显示所有的未起飞的航班信息
					if (!fs.isEmpty()) {
						flightservice.viewFlight(fs);
						System.out.println(order.getO_rid());
						System.out.println("\n请输入航班ID: ");
						int pl_rid = new MyScanner().getInt();
						try {
							// 改签 输入航班号 传参 订单实体
							orderservice.update(pl_rid, order);
							
							System.out.println("改签成功");
						} catch (Exception e) {
							System.out.println(e.getMessage());
						}
					} else {
						System.out.println("暂无航班信息,请等待!");
					}
				}
			}
		} else {
			System.out.println("抱歉当前没有您的订单!");
		}
	}

	// 退订机票
	public void returnTicket() {
		User current = threadlocal.get();
		List<Order> orders = orderservice.listOrderByUid(current.getP_id());

		if (!orders.isEmpty()) {
			orderservice.viewOrder(orders);
			System.out.print("请输入要退票的订单号: ");
			String orderNumber = new MyScanner().getString();
			try {
				orderservice.returnTicket(orderNumber, current.getP_id());
				System.out.println("退票成功");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("抱歉，您没有暂时没有订票订单 !");
		}

	}

	// 修改用户信息
	public void updateUser() {
		User current = threadlocal.get();
		System.out.println(current.toString());
		current.toString();
		int item = 0;

		do {
			System.out.println("请输入你修改类别，只能修改1为密码或者2为手机号");
			try {
				item = new MyScanner().getInt();
			} catch (Exception e) {
				System.out.println("输入有误请输入一个有效数字");
				continue;
			}

			switch (item) {
			case 1:
				System.out.print("请输入密码:");
				String password = new MyScanner().getString();
				System.out.print("确认密码:");
				String password2 = new MyScanner().getString();
				while (!password.equals(password2)) {
					System.out.println("两次密码输入不一致!");
					System.out.print("请输入密码:");
					password = new MyScanner().getString();
					System.out.print("确认密码:");
					password2 = new MyScanner().getString();
				}
				current.setP_pwd(MD5.encode(password));
				System.out.println(new UserDAOImpl().update(current));
				break;
			case 2:
				while (true) {
					System.out.print("请输入手机号:");
					String tel = new MyScanner().getString();
					//正则手机号
					if (!Regular.IsHandset(tel)) {
						System.out.println("手机号码格式不对");
					} else {
						current.setP_number(tel);
						new UserDAOImpl().update(current);
						break;
					}
				}
				break;
			default:
				break;
			}
			System.out.println("y可以继续 其他键退出");
		} while ("y".equalsIgnoreCase(new MyScanner().getString()));
	}

}
