package com.lcqjoyce.UI;

import com.lcqjoyce.domain.User;
import com.lcqjoyce.service.UserLoginService;
import com.lcqjoyce.service.impl.UserLoginServiceImpl;
import com.lcqjoyce.utils.MD5;
import com.lcqjoyce.utils.MyScanner;

public class Manager_Client {
  	private ThreadLocal<User> threadLocal = new ThreadLocal<User>();
	private UserLoginService userservice = new UserLoginServiceImpl();

	/**
	 * 后台主页
	 */

	public void U_index() {
		System.out.println();
		System.out.println("==================欢迎登录飞机页面==================");
		loginDisplay();	
	}

	/**
	 * 管理员不支持注册保证后台安全
	 */

	/**
	 * 登陆界面
	 */
	private void loginDisplay() {
		System.out.println("==================管理员登录==================");
		User User = new User();
		while (true) {
			System.out.print("请输入登录名:");
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
					ManagerSpace();
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
	 * 登陆成功之后进入 管理员空间 管理员空间功能UserFunction loginUser 登录成功的管理员保存
	 */
	private void ManagerSpace() {
		User loginUser = threadLocal.get();
		if (null != loginUser) {
			System.out.println("==================欢迎使用航空购票系统==================");
			System.out.println("当前登陆管理员: " + loginUser.getP_name());
			String flag = "y";
			while ("y".equalsIgnoreCase(flag)) {
				UserFunction UserFunction = new UserFunction(threadLocal);
				System.out.println("1.查询航班");
				System.out.println("2.增加航班");
				System.out.println("3.更新航班");
				System.out.println("4.删除航班");
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
					ManagerFunction.getFlight();
					break;
				case 2:
					ManagerFunction.addFlight();
					break;
				case 3:
					ManagerFunction.updateFlight();
					break;
				case 4:
					ManagerFunction.deleteFlight();
					break;
				default:
					System.out.println("输入有误!");
				}
				System.out.print("y进入管理员空间   其他任意键退出");
				flag = new MyScanner().getString();
			}
		
		} else {
			// 没有登陆,拦截并跳转到登陆界面
			loginDisplay();
		}
	}

}