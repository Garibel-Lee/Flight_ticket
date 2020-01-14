package com.lcqjoyce.utils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.lcqjoyce.handler.IResultSetHandler;

public class JdbcTemplate {
	private static Logger logger=Logger.getLogger(JdbcTemplate.class);
	/**
	 * 查询DQL模板
	 * 查询多个用户，返回一个list<user>
	 * 查询一个用户，返回长度为一的list
	 * @param sql
	 * @param params
	 * @return	返回list集合
	 * @throws Exception 
	 */
	public static <T>T query(String sql,IResultSetHandler<T> rsh,Object... params ){
	
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs = null;
		try {
			conn=JdbcUtil.getConnection();
			ps = conn.prepareStatement(sql);
			for(int index=0; index<params.length;index++) {
				
				ps.setObject(index+1, params[index]);
			}
		
			rs=ps.executeQuery();	
		
			logger.info("executeQuery查询结果："+rs.isBeforeFirst());
//			while (rs.next()) {
//			System.out.println(rs.getInt("o_id"));
//			System.out.println(rs.getInt("o_fid"));
//			System.out.println(rs.getDate("o_Stime"));
//			System.out.println(rs.getFloat("o_price"));
//			}
			return rsh.handle(rs);
			
		} catch ( Exception e) {
			e.printStackTrace();
		}finally {
			
			JdbcUtil.close(conn,ps,rs);
		}
		throw new RuntimeException("查询操作有误");
	}

	/**
	 * DML操作模板            增删改
	 * @param sql		DML操作的SQL模板带有占位符
	 * @param params	SQL？对应参数值
	 * @return 			受影响行数
	 */
	public static int update(String sql,Object... params ) {
		Connection conn = null;
		PreparedStatement ps=null ;
		try {
			conn=JdbcUtil.getConnection();//贾琏
			ps=conn.prepareStatement(sql);//欲
			for(int index=0; index<params.length;index++) {
				System.out.println("DML操作："+params[index]);
				ps.setObject(index+1,params[index]);
			}
			int rs=ps.executeUpdate();
			logger.info("更新结果："+rs);
			return rs;			
		} catch (Exception e) {
		}finally {
			JdbcUtil.close(null,ps);
		}
		return 0;
	}
}
