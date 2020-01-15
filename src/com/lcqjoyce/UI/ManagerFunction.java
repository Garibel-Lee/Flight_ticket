package com.lcqjoyce.UI;

import com.lcqjoyce.dao.PlaneDAO;
import com.lcqjoyce.dao.impl.PlaneDAOImpl;
import com.lcqjoyce.domain.Manager;
import com.lcqjoyce.domain.Plane;
import com.lcqjoyce.service.FlightService;
import com.lcqjoyce.service.impl.FlightServiceImpl;
import com.lcqjoyce.utils.DateUtil;
import com.lcqjoyce.utils.MyScanner;

import java.text.ParseException;
import java.util.Date;

public class ManagerFunction {
	/**
	 * note 管理主頁調用了 登錄的管理信息 保证了当前管理者的信息
	 * 采用前后端分离避免 threadlocal作用域问题
	 */
	@SuppressWarnings("unused")
	private ThreadLocal<Manager> threadLocal = new ThreadLocal<Manager>();
	private FlightService flightservice = new FlightServiceImpl();
	private PlaneDAO p_dao = new PlaneDAOImpl();

	/**
	 * 仿spring的管理方式绑定资源到当前线程
	 * 
	 * @param threadLocal
	 */
	public ManagerFunction(ThreadLocal<Manager> threadLocal) {
		this.threadLocal = threadLocal;
	}

	// 查询航班的主页
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

	// 查询之航班号查询
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

	// 查询之地点模糊查询 sql语句拼接
	public void queryForPlace() {
		System.out.println("请输入城市，进行地点模糊查询");
		String str = new MyScanner().getString();
		flightservice.viewFlight(p_dao.getPlace(str));

	}

	// 查询之日期固定格式查询
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

	// 添加航班
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
		if(1==result) {
			System.out.println("保存航班成功");
		}else {
			System.out.println("保存航班失败");
		}
	}

	// 更新航班
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
				}else {
					System.out.println("不存在该航班号,请重新输入");
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
			if (sdate != null && new Date().before(sdate)&&DateUtil.isValidDate(str) ) {
				try {
					plane.setPl_rst(sdate);
				} catch (ParseException e) {
					System.out.println("请输入正确大于当前时间的日期格式yyyyMMddHHmm");
					continue;
				}
				break;
			}else
				System.out.println("请输入正确大当前时间的日期格式yyyyMMddHHmm");
		}

		System.out.println("请输入降落时间yyyyMMddHHmm");
		Date edate;
		String estr;
		while (true) {
			estr = new MyScanner().getString();
			edate = DateUtil.parseDate(estr, "yyyyMMddHHmm");
			if (sdate != null && sdate.before(edate) && DateUtil.isValidDate(str)) {
				try {
					plane.setPl_ret(edate);
				} catch (ParseException e) {
					System.out.println("请输入正确大于起飞时间的日期格式yyyyMMddHHmm");
					continue;
				}
				break;
			}else
				System.out.println("请输入正确大于起飞时间的日期格式yyyyMMddHHmm");
		}
		int result = p_dao.update(plane);
		if(1==result) {
			System.out.println("起飞降落时间修改成功");
		}else {
			System.out.println("起飞降落时间修改失败");
		}

	}
	// 刪除航班
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
		if(1==result) {
			System.out.println("删除航班成功");
		}else {
			System.out.println("删除航班失败");
		}

	}
}
