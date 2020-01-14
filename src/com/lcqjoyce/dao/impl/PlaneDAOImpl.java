package com.lcqjoyce.dao.impl;

import java.util.Date;
import java.util.List;

import com.lcqjoyce.dao.PlaneDAO;
import com.lcqjoyce.domain.Plane;
import com.lcqjoyce.handler.BeanHandler;
import com.lcqjoyce.handler.BeanlistHandler;
import com.lcqjoyce.utils.JdbcTemplate;

public class PlaneDAOImpl implements PlaneDAO {

	public void save(Plane obj) {

	}

	public void update(Plane obj) {

	}

	public void delete(int o_id) {

	}

	public Plane get(int o_id) {
		return JdbcTemplate.query(
				"SELECT\n" + "pl_id,\n" + "pl_name,\n" + "pl_rid,\n" + "pl_st,\n" + "pl_rsp,\n" + "pl_rep,\n"
						+ "pl_rst,\n" + "pl_ret,\n" + "pl_ysize,\n" + "pl_fsize,\n" + "pl_dsize,\n" + "pl_ps,\n"
						+ "r_yprice,\n" + "r_fprice,\n" + "r_dprice\n" + "FROM\n" + "plane_info where pl_rid= ?",
				new BeanHandler<>(Plane.class), o_id);
	}

	public List<Plane> list() {
		return JdbcTemplate.query(
				"SELECT\n" + "pl_id,\n" + "pl_name,\n" + "pl_rid,\n" + "pl_st,\n" + "pl_rsp,\n" + "pl_rep,\n"
						+ "pl_rst,\n" + "pl_ret,\n" + "pl_ysize,\n" + "pl_fsize,\n" + "pl_dsize,\n" + "pl_ps,\n"
						+ "r_yprice,\n" + "r_fprice,\n" + "r_dprice\n" + "FROM\n" + "plane_info",
				new BeanlistHandler<>(Plane.class));
	}

	@Override
	public List<Plane> getDate(Date date) {
		String sql = "SELECT\n" + "pl_id,\n" + "pl_name,\n" + "pl_rid,\n" + "pl_st,\n" + "pl_rsp,\n" + "pl_rep,\n"
				+ "pl_rst,\n" + "pl_ret,\n" + "pl_ysize,\n" + "pl_fsize,\n" + "pl_dsize,\n" + "pl_ps,\n" + "r_yprice,\n"
				+ "r_fprice,\n" + "r_dprice\n" + "FROM\n" + "plane_info where (pl_rst >= ? and pl_rst <= ?) ";
		return JdbcTemplate.query(sql, new BeanlistHandler<Plane>(Plane.class),
				com.lcqjoyce.utils.DateUtil.beginOfDaySecond(date), com.lcqjoyce.utils.DateUtil.endOfDaySecond(date));
	}

	@Override
	public List<Plane> getPlace(String place) {
		String sql = "SELECT\n" + "pl_id,\n" + "pl_name,\n" + "pl_rid,\n" + "pl_st,\n" + "pl_rsp,\n" + "pl_rep,\n"
				+ "pl_rst,\n" + "pl_ret,\n" + "pl_ysize,\n" + "pl_fsize,\n" + "pl_dsize,\n" + "pl_ps,\n" + "r_yprice,\n"
				+ "r_fprice,\n" + "r_dprice\n" + "FROM\n" + "plane_info where pl_rsp LIKE ? or pl_rep LIKE ? ";

		return JdbcTemplate.query(sql, new BeanlistHandler<Plane>(Plane.class), "%" + place + "%", "%" + place + "%");
	}

	public List<Plane> getFlightNumber(int p_rid) {

		String sql = "SELECT\n" + "pl_id,\n" + "pl_name,\n" + "pl_rid,\n" + "pl_st,\n" + "pl_rsp,\n" + "pl_rep,\n"
				+ "pl_rst,\n" + "pl_ret,\n" + "pl_ysize,\n" + "pl_fsize,\n" + "pl_dsize,\n" + "pl_ps,\n" + "r_yprice,\n"
				+ "r_fprice,\n" + "r_dprice\n" + "FROM\n" + "plane_info where pl_rid= ? ";
		return JdbcTemplate.query(sql, new BeanlistHandler<Plane>(Plane.class), p_rid);
	}

	public void updateTickets(int pl_ps, Integer pl_rid) {
		String sql = "update plane_info set pl_ps = ? where pl_rid = ?";
		JdbcTemplate.update(sql, pl_ps, pl_rid);

	}

}
