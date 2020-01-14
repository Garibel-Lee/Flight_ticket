package Test;


import com.lcqjoyce.dao.UserDAO;
import com.lcqjoyce.dao.impl.UserDAOImpl;
import com.lcqjoyce.domain.User;
import com.lcqjoyce.utils.DateUtil;
import com.lcqjoyce.utils.MD5;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//import com.lcqjoyce.utils.MD5;

class UserDAOTest {

	private UserDAO o_user=new UserDAOImpl();

	/**
	 * 新建用户
	 * 提供姓名密码
	 * 
	*/
	void testSave() {
		User user=new User();
		user.setP_name("张提升");
		user.setP_pwd(MD5.encode("1223"));
		user.setP_locktime(DateUtil.getCurrentDate());
		o_user.save(user);
	}


	void testUpdate() {
	}


	void testDelete() {
	
	}


	void testGet() {
		User user=o_user.get(14);
		System.out.println(user.toString());
	}


	void testList() {
		List<User> list=new ArrayList<User>();
		list=o_user.list();
		for(User us:list) {
			System.out.println(us);
		}
	}

	void testGETName() {
		System.out.println(new Date());
		User user=o_user.getByName("王二小");
		System.out.println(user.toString());
	}
}
