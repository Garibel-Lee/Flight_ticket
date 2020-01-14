package com.lcqjoyce.UI;

import java.text.ParseException;
import java.util.Date;

import com.lcqjoyce.dao.PlaneDAO;
import com.lcqjoyce.dao.UserDAO;
import com.lcqjoyce.dao.impl.PlaneDAOImpl;
import com.lcqjoyce.dao.impl.UserDAOImpl;
import com.lcqjoyce.domain.Manager;
import com.lcqjoyce.domain.Plane;
import com.lcqjoyce.service.FlightService;
import com.lcqjoyce.service.OrderService;
import com.lcqjoyce.service.impl.FlightServiceImpl;
import com.lcqjoyce.service.impl.OrderServiceImpl;
import com.lcqjoyce.utils.DateUtil;
import com.lcqjoyce.utils.MyScanner;

public class ManagerFunction {
	private ThreadLocal<Manager> threadLocal = new ThreadLocal<Manager>();
	private FlightService flightservice = new FlightServiceImpl();
	private PlaneDAO p_dao = new PlaneDAOImpl();

	public ManagerFunction(ThreadLocal<Manager> threadLocal) {
		this.threadLocal = threadLocal;
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
				System.out.println(date);
				flightservice.viewFlight(p_dao.getDate(date));
				break;
			}
		}
	}

	public void addFlight() {
		Plane plane = new Plane();

		while (true) {
			System.out.println("请输入航班号");
			int item = 0;
			try {
				item = new MyScanner().getInt();
				if (item != 0) {
					plane.setPl_rid(item);
					break;
				}
			} catch (Exception e) {
				System.out.println("输入有误请输入一个有效数字");
				continue;
			}
		}

		System.out.println("请输入起点");
		plane.setPl_rsp(new MyScanner().getString());

		System.out.println("请输入终点");
		plane.setPl_rep(new MyScanner().getString());

		System.out.println("请输入起飞时间yyyyMMddHHmm");
		Date sdate;
		String str;
		while (true) {

			str = new MyScanner().getString();
			sdate = DateUtil.parseDate(str, "yyyyMMddHHmm");
			if (sdate != null) {
				try {
					plane.setPl_rst(sdate);
				} catch (ParseException e) {
					System.out.println("请输入正确日期格式yyyyMMddHHmm");
					continue;
				}
				break;
			}
		}

		System.out.println("请输入降落时间yyyyMMddHHmm");
		Date edate;
		String estr;
		while (true) {
			estr = new MyScanner().getString();
			edate = DateUtil.parseDate(estr, "yyyyMMddHHmm");
			if (sdate != null) {
				try {
					plane.setPl_ret(edate);
				} catch (ParseException e) {
					System.out.println("请输入正确日期格式yyyyMMddHHmm");
					continue;
				}
				break;
			}
		}

		while (true) {
			System.out.println("请输入座位总数");
			int item = 0;
			try {
				item = new MyScanner().getInt();
				if (item != 0) {
					plane.setPl_ps(item);
					break;
				}
			} catch (Exception e) {
				System.out.println("输入有误请输入一个有效数字");
				continue;
			}
		}
		int result = p_dao.save(plane);
		System.out.println(result);
	}

	public void updateFlight() {
		Plane plane = new Plane();
		while (true) {
			System.out.println("请输入航班号");
			int item = 0;
			try {
				item = new MyScanner().getInt();
				plane = flightservice.getFlightRID(item);
				if (plane.getPl_id() != null) {
					break;
				}

			} catch (Exception e) {
				System.out.println("输入有误或者不存在该航班号");
				continue;
			}
		}

		System.out.println("请输入起飞时间yyyyMMddHHmm");
		Date sdate;
		String str;
		while (true) {

			str = new MyScanner().getString();
			sdate = DateUtil.parseDate(str, "yyyyMMddHHmm");
			if (sdate != null) {
				try {
					plane.setPl_rst(sdate);
				} catch (ParseException e) {
					System.out.println("请输入正确日期格式yyyyMMddHHmm");
					continue;
				}
				break;
			}
		}

		System.out.println("请输入降落时间yyyyMMddHHmm");
		Date edate;
		String estr;
		while (true) {
			estr = new MyScanner().getString();
			edate = DateUtil.parseDate(estr, "yyyyMMddHHmm");
			if (sdate != null) {
				try {
					plane.setPl_ret(edate);
				} catch (ParseException e) {
					System.out.println("请输入正确日期格式yyyyMMddHHmm");
					continue;
				}
				break;
			}
		}
		int result = p_dao.update(plane);
		System.out.println(result);

	}

	public void deleteFlight() {
		Plane plane = new Plane();
		while (true) {
			System.out.println("请输入航班号");
			int item = 0;
			try {
				item = new MyScanner().getInt();
				plane = flightservice.getFlightRID(item);
				if (plane.getPl_id() != null) {
					break;
				}

			} catch (Exception e) {
				System.out.println("输入有误或者不存在该航班号");
				continue;
			}
		}

		int result = p_dao.delete(plane.getPl_rid());
		System.out.println(result);

	}

	/**
	 * 不用写了
	 */
	public static void getFlight() {

	}

}
