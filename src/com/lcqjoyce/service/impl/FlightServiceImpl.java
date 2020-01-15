package com.lcqjoyce.service.impl;

import com.lcqjoyce.dao.PlaneDAO;
import com.lcqjoyce.dao.impl.PlaneDAOImpl;
import com.lcqjoyce.domain.Order;
import com.lcqjoyce.domain.Plane;
import com.lcqjoyce.service.FlightService;
import com.lcqjoyce.utils.DateUtil;

import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class FlightServiceImpl implements FlightService {

	
	private DateFormat df = DateFormat.getDateTimeInstance();
	private PlaneDAO p_dao = new PlaneDAOImpl();

	public List<Plane> getFlightlist() {
		//查詢航班都是當前的為起飛的
		
		return p_dao.list();
	}

	public Plane getFlightRID(int rid) {
		//查詢航班都是當前的為起飛的
		
		return p_dao.get(rid);
	}
	
	
	//效果不行在下面重新构思
	public List<Plane> getNoTakeofflist(Order order ) {
		//查詢航班都是當前的為起飛的
		List<Plane> list=p_dao.getDate(DateUtil.transDateFormat(new Date(), DateUtil.ACCURACY_PATTERN_DAY));
		Iterator<Plane> iterator = list.iterator();
		while (iterator.hasNext()) {
			Plane plane = iterator.next();
			if (plane.getPl_rid().equals(order.getO_rid())) {
				iterator.remove();//使用迭代器的删除方法删除
			}
		}
		for(Plane ps:list) {
			System.out.println(ps);
		}
		return list;
	}

	
	
	public void viewFlight(List<Plane> list) {
		
		for (Plane ps : list) {
			if(ps.getPl_rst().after(new Date())) {
			System.out.print("航班号  ：" + ps.getPl_rid()+"\t");
			System.out.print("起点     ：" + ps.getPl_rsp()+"\t");
			System.out.print("终点    	： " + ps.getPl_rep()+"\t");
			System.out.print("起飞时间	： " + df.format(ps.getPl_rst())+"\t\t");
			System.out.print("降落时间	：" + df.format(ps.getPl_ret())+"\t\t");
			System.out.print("余票数	："+ps.getPl_ps()+"\t\t");
			System.out.print("经济舱价格	：" + ps.getR_dprice()+"\t");
			System.out.print("公务舱价格	：" + ps.getR_yprice()+"\t");
			System.out.println("头等舱价格	：" + ps.getR_fprice()+"\t");
			}else {
				System.out.println("一个过期航班未显示");
				continue;
			}
		}
	}

}
