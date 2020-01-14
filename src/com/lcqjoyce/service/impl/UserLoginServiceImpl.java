package com.lcqjoyce.service.impl;

import com.lcqjoyce.dao.UserDAO;
import com.lcqjoyce.dao.impl.UserDAOImpl;
import com.lcqjoyce.domain.User;
import com.lcqjoyce.service.UserLoginService;
import com.lcqjoyce.utils.DateUtil;
import com.lcqjoyce.utils.MD5;

import java.util.Date;

public class UserLoginServiceImpl implements UserLoginService {

	private UserDAO o_user = new UserDAOImpl();

	public void Register(User obj) {
		// 注册第一步检测用户名是否存在
		User user = new User();
		user = o_user.getByName(obj.getP_name());
		if (null == user) {
			User us=new User();
			// 不存在保存对象 进行插入
			us.setP_name(obj.getP_name());
			us.setP_pwd(MD5.encode(obj.getP_pwd()));
			o_user.save(us);
		}else
		{
			throw new RuntimeException("用户名已存在！");
		}

	}

	
	public User login(User User) {
		// 根据用户名查询用户信息
		
		User currentUser = o_user.getByName(User.getP_name());
		if (null == currentUser.getP_id()) {
			throw new RuntimeException("用户名不存在!");
		}
		// 检查登陆失败次数 当剩余次数小于0
		if (currentUser.getP_resudie() <= 0) {
			// 次数用完
			if (currentUser.getP_locktime() == null) {
				// 如果没有locktime  就重新设置代替锁
				currentUser.setP_locktime(DateUtil.getCurrentDate());
				o_user.updateLock(currentUser);
			}
			long secondBtn = DateUtil.getDateDistance(new Date(),
					currentUser.getP_locktime(), DateUtil.ACCURACY_SECOND);
			if (secondBtn <= 60 * 60) {
				throw new RuntimeException(
						"该账户已被锁定,请在" + (59 - secondBtn / 60) + "分" + (59 - secondBtn % 60) + "秒后重试!");
			} else {
				// 超过一个小时,恢复三次机会
				currentUser.setP_resudie(com.lcqjoyce.domain.User.LockRemain);
				o_user.updateLock(currentUser);
			}
		}

		if (!currentUser.getP_pwd().equals(User.getP_pwd())) {
			
			System.out.println("密码不对当前剩余次数"+currentUser.getP_resudie());			
			currentUser.setP_resudie(currentUser.getP_resudie() - 1);
			// 更新账户状态
			System.out.println("updateLock更新影响行数"+o_user.updateLock(currentUser));
			// 检查登陆失败次数,在第三次错误后就锁上
			if (currentUser.getP_resudie() <= 0) {
				// 次数用完
				if (currentUser.getP_locktime() == null || DateUtil.getDateDistance(new Date(),
						currentUser.getP_locktime(),
						DateUtil.ACCURACY_SECOND) > 60 * 60 ) {
					// 如果没有锁则锁上或者超过一小时后任然错误
					currentUser.setP_locktime(DateUtil.getCurrentDate());
					o_user.updateLock(currentUser);
					long secondBtn = DateUtil.getDateDistance(new Date(),
							currentUser.getP_locktime(),
							DateUtil.ACCURACY_SECOND);
					throw new RuntimeException(
							"账户已被锁定,请在" + (59 - secondBtn / 60) + "分" + (59 - secondBtn % 60) + "秒后重试!");
				}
			}
			throw new RuntimeException("密码输入错误,还有" + currentUser.getP_resudie() + "次机会");
		}

		// 登陆成功,恢复次数和锁定时间
		currentUser.setP_locktime(null);
		currentUser.setP_resudie(com.lcqjoyce.domain.User.LockRemain);
		o_user.updateLock(currentUser);
		return currentUser;
	}

	
	public User updateInfo(User user) {
		int result =o_user.update(user);
		User updateUser=new User();
		if(result!=0) {
		updateUser=o_user.getByName(user.getP_name());					}
		return updateUser;
	}
}
