package com.lcqjoyce.dao;

import java.util.Date;
import java.util.List;

import com.lcqjoyce.domain.Plane;

public interface PlaneDAO {
	int save(Plane obj);

	int update(Plane obj);

	int delete(int o_id);

	public Plane get(int o_id);

	public List<Plane> getDate(Date date);

	public List<Plane> getPlace(String place);

	public List<Plane> getFlightNumber(int o_id);

	public List<Plane> list();

	public void updateTickets(int i, Integer pl_rid);
}
