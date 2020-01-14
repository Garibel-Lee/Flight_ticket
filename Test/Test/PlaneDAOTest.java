package Test;

import com.lcqjoyce.dao.PlaneDAO;
import com.lcqjoyce.dao.impl.PlaneDAOImpl;
import com.lcqjoyce.domain.Plane;

import java.util.ArrayList;
import java.util.List;

class PlaneDAOTest {
	private PlaneDAO plane_dao=new PlaneDAOImpl();
	

	void testSave() {
	}


	void testUpdate() {
	}


	void testDelete() {
	}


	void testGet() {
	
		Plane plane=plane_dao.get(747);
//		Date date = DateUtil.parseDate(plane.getPl_rst(),"yyyy-MM-dd HH:mm:ss");
//		int a=DateUtil.getDistanceByUnit(DateUtil.parseDate(plane.getPl_rst(),"yyyy-MM-dd HH:mm:ss"),DateUtil.parseDate(plane.getPl_ret(),"yyyy-MM-dd HH:mm:ss"), 3);
//		
//		
//		System.out.println(date);
//		System.out.println(a);
		System.out.println(plane.toString());
	}


	void testList() {
		List<Plane> list=new ArrayList<Plane>();
		list=plane_dao.list();
		for(Plane ps:list) {
			System.out.println(ps);
		}
	}

}
