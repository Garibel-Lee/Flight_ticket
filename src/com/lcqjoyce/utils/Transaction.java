package com.lcqjoyce.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction {

	public void beginTransaction() {
		Connection conn = JdbcUtil.getConnection();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void commit() {
		Connection conn = JdbcUtil.getConnection();
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close();
		}
	}

	public void rollback() {
		Connection conn = JdbcUtil.getConnection();
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close();
		}
	}
}
