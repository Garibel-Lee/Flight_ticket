package com.lcqjoyce.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class JdbcUtil {
	private static DataSource ds = null;
	private static Logger logger=Logger.getLogger(JdbcUtil.class);
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	static {
		try {
			Properties p = new Properties();
			p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties"));
			ds = DruidDataSourceFactory.createDataSource(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		logger.info("获取connection连接绑定当前线程");
		Connection conn = threadLocal.get();
		if (conn == null) {
			try {
				//{dataSource-1} inited
				conn = ds.getConnection();
				threadLocal.set(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	

	public static void close() {
		
		Connection conn = (Connection) threadLocal.get();
		threadLocal.remove();
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	//模板类中关闭的方式
	public static void close(ResultSet rs, PreparedStatement ps) {
		logger.info("关闭ResultSet PreparedStatement资源");
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// 释放资源 还给连接池
	public static void close(Connection conn, Statement st, ResultSet rs) {
		logger.info("关闭Connectio, Statement,ResultSet资源");
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (Exception e) {
			} finally {
				try {
					if (conn != null)
					threadLocal.remove();
					conn.close();
				} catch (Exception e) {
				}
			}
		}
	}
}


