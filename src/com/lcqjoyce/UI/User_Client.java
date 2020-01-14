package com.lcqjoyce.UI;

import com.lcqjoyce.domain.User;
import com.lcqjoyce.service.UserLoginService;
import com.lcqjoyce.service.impl.UserLoginServiceImpl;
import com.lcqjoyce.utils.MD5;
import com.lcqjoyce.utils.MyScanner;

public class User_Client {
	private ThreadLocal<User> threadLocal = new ThreadLocal<User>();
	private UserLoginService userservice = new UserLoginServiceImpl();

	/**
	 * 用户主页
	 */

	public void U_index() {
		int item = 0;
		System.out.println();
		System.out.println("==================首页==================");
		System.out.println("1.用户登录");
		System.out.println("2.用户注册");
		System.out.println("==================首页==================");

		while (true) {
			System.out.print("请输入序号: ");
			try {
				item = new MyScanner().getInt();
			} catch (Exception e) {
				System.out.println("输入有误请输入数字1或2");
				continue;
			}

			switch (item) {
			case 1:
				// 进入用户登录
				loginDisplay();
				break;
			case 2:
				// 进入用户注册
				registerDisplay();
				break;
			default:
				System.out.println("输入有误");
				break;
			}
			if (1 == item || 2 == item) {
				break;
			}
		}
	}

	/**
	 * 注册界面
	 */
	private void registerDisplay() {
		System.out.println("==================用户注册==================");
		User user = new User();
		while (true) {
			System.out.print("请输入用户名:");
			user.setP_name(new MyScanner().getString());
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
			user.setP_pwd(password);

			try {

				userservice.Register(user);
				System.out.println("注册成功,跳转到登陆界面");
				// 跳转到登陆界面,出口
				loginDisplay();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * 登陆界面
	 */
	private void loginDisplay() {
		System.out.println("==================用户登录==================");
		User User = new User();
		while (true) {
			System.out.print("请输入用户名:");
			User.setP_name(new MyScanner().getString());
			System.out.print("请输入密码:");
			User.setP_pwd(MD5.encode(new MyScanner().getString()));
			try {
				User current = userservice.login(User);
				if (null != current.getP_id()) {
					System.out.println("登陆成功,跳转到主界面");
					// 绑定到当前线程
					threadLocal.set(current);
					// 登陆成功,跳转到主界面
					UserSpace();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			System.out.println("y可以返回主界面哦");
			String flag = new MyScanner().getString();
			if ("y".equalsIgnoreCase(flag)) {
				U_index();
			}
		}
	}

	/**
	 * 登陆成功之后进入 用户空间 用户空间功能UserFunction loginUser 登录成功的用户保存
	 */
	private void UserSpace() {
		User loginUser = threadLocal.get();
		if (null != loginUser) {
			System.out.println("==================欢迎使用航空购票系统==================");
			System.out.println("当前登陆用户: " + loginUser.getP_name());
			String flag = "y";
			while ("y".equalsIgnoreCase(flag)) {
				UserFunction UserFunction = new UserFunction(threadLocal);
				System.out.println("1.查询航班");
				System.out.println("2.选购机票");
				System.out.println("3.机票改签");
				System.out.println("4.退订机票");
				System.out.println("5.修改信息");
				System.out.print("请输入序号:");
				int item=0;
				try {
					item = new MyScanner().getInt();
				} catch (Exception e) {
					System.out.println("输入有误请输入一个有效数字");
					continue;
				}
				switch (item) {
				case 1:
					UserFunction.queryForFlightInfo();
					break;
				case 2:
					UserFunction.buyTicket();
					break;
				case 3:
					UserFunction.updateTicket();
					break;
				case 4:
					UserFunction.returnTicket();
					break;
				case 5:
					UserFunction.updateUser();
					break;
				default:
					System.out.println("输入有误!");
				}
				System.out.print("y进入用户空间   其他任意键退出");
				flag = new MyScanner().getString();
			}
			// 退出系统 清楚当前用户线程绑定
			logout();
		} else {
			// 没有登陆,拦截并跳转到登陆界面
			loginDisplay();
		}
	}

	/**
	 * 注销
	 */
	private void logout() {
		ThreadLocal<User> threadLocal = new ThreadLocal<User>();
		// 清除登录信息,并跳转到登陆界面
		threadLocal.remove();
		loginDisplay();
	}

}
